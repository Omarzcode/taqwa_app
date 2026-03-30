package com.taqwa.journal.data.utilities

/**
 * Unified CSV handling for SharedPreferences.
 * Handles escaping, quoting, and parsing of delimited values.
 * Fixes silent parse failures and delimiter collision bugs.
 *
 * Uses '|||' as delimiter and '"' for quoting (standard CSV-like approach).
 * Example: "item1|||item with """quotes""" inside|||item3"
 */
object CsvPreferenceParser {

    const val DELIMITER = "|||"
    private const val QUOTE_CHAR = '"'
    private const val ESCAPE_CHAR = '"'

    // ════════════════════════════════════════
    // PARSING (String → List)
    // ════════════════════════════════════════

    /**
     * Parse CSV string into list of items.
     * Handles quoted fields and escaped quotes.
     * Returns empty list if input is empty or invalid.
     */
    fun parseList(csvString: String?): List<String> {
        if (csvString.isNullOrBlank()) return emptyList()

        return csvString.split(DELIMITER)
            .map { it.unquoteValue() }
            .filter { it.isNotEmpty() }
    }

    /**
     * Parse CSV string with validation.
     * Throws exception if parsing fails.
     */
    fun parseListStrict(csvString: String?): List<String> {
        try {
            return parseList(csvString)
        } catch (e: Exception) {
            throw IllegalArgumentException("Failed to parse CSV preference: ${e.message}", e)
        }
    }

    // ════════════════════════════════════════
    // SERIALIZATION (List → String)
    // ════════════════════════════════════════

    /**
     * Convert list to CSV string.
     * Properly quotes fields containing delimiters or quotes.
     * Returns empty string if list is empty.
     */
    fun serializeList(items: List<String>?): String {
        if (items.isNullOrEmpty()) return ""
        return items.map { it.quoteValue() }.joinToString(DELIMITER)
    }

    /**
     * Safely add item to existing CSV string.
     * Prevents duplicate entries (idempotent).
     */
    fun addToList(csvString: String?, newItem: String): String {
        if (newItem.isBlank()) return csvString ?: ""
        val list = parseList(csvString).toMutableList()
        if (newItem !in list) {
            list.add(newItem)
        }
        return serializeList(list)
    }

    /**
     * Remove item from CSV string by exact match.
     * Returns original if item not found.
     */
    fun removeFromList(csvString: String?, itemToRemove: String): String {
        val list = parseList(csvString).toMutableList()
        list.remove(itemToRemove)
        return serializeList(list)
    }

    /**
     * Remove item by index.
     * Returns original if index out of bounds.
     */
    fun removeFromListByIndex(csvString: String?, index: Int): String {
        val list = parseList(csvString).toMutableList()
        if (index in list.indices) {
            list.removeAt(index)
        }
        return serializeList(list)
    }

    /**
     * Update item in list.
     * Replaces first occurrence of oldValue with newValue.
     */
    fun updateInList(csvString: String?, oldValue: String, newValue: String): String {
        val list = parseList(csvString).toMutableList()
        val index = list.indexOf(oldValue)
        if (index >= 0) {
            list[index] = newValue
        }
        return serializeList(list)
    }

    // ════════════════════════════════════════
    // HELPER FUNCTIONS
    // ════════════════════════════════════════

    /**
     * Quote a value if it contains delimiter or quotes.
     * Example: "hello|||world" → ""hello|||world"""
     */
    private fun String.quoteValue(): String {
        return if (this.contains(DELIMITER) || this.contains(QUOTE_CHAR)) {
            "\"${this.replace("\"", "\"\"")}\""
        } else {
            this
        }
    }

    /**
     * Unquote a value and unescape quotes.
     * Example: ""hello|||world""" → "hello|||world"
     */
    private fun String.unquoteValue(): String {
        return if (this.startsWith(QUOTE_CHAR) && this.endsWith(QUOTE_CHAR)) {
            this.substring(1, this.length - 1)
                .replace("\"\"", "\"")
        } else {
            this
        }
    }

    /**
     * Check if value needs quoting (contains special chars).
     */
    private fun String.needsQuoting(): Boolean {
        return this.contains(DELIMITER) || this.contains(QUOTE_CHAR)
    }

    // ════════════════════════════════════════
    // NESTED STRUCTURE SUPPORT
    // ════════════════════════════════════════

    /**
     * For two-level CSV structures (entries separated by secondary delimiter, fields by primary).
     * Example: "date1|||reason1|||streak1;;;date2|||reason2|||streak2;;;..."
     *
     * Usage:
     * ```
     * val entries = parseNested(csvString, ";;;") { fields ->
     *     Triple(fields[0], fields[1], fields.getOrNull(2)?.toInt() ?: 0)
     * }
     * ```
     */
    internal inline fun <T> parseNested(
        csvString: String?,
        entryDelimiter: String,
        transform: (List<String>) -> T?
    ): List<T> {
        if (csvString.isNullOrBlank()) return emptyList()

        return csvString.split(entryDelimiter)
            .filter { it.isNotBlank() }
            .mapNotNull { entry ->
                val fields = entry.split(DELIMITER)
                    .map { it.unquoteValue() }
                    .filter { it.isNotEmpty() }
                transform(fields)
            }
    }

    /**
     * Serialize objects to two-level CSV structure.
     * Example: Converts list of (date, reason, streak) to the CSV format above.
     */
    fun <T> serializeNested(
        items: List<T>,
        entryDelimiter: String,
        fieldExtractor: (T) -> List<String>
    ): String {
        if (items.isEmpty()) return ""
        return items
            .map { item ->
                fieldExtractor(item)
                    .map { it.quoteValue() }
                    .joinToString(DELIMITER)
            }
            .joinToString(entryDelimiter)
    }

    // ════════════════════════════════════════
    // CUSTOM DELIMITER LINE PARSING
    // ════════════════════════════════════════

    /**
     * Parse a line with custom delimiter, respecting CSV-style quotes.
     * Handles quoted fields that may contain the delimiter.
     *
     * Example:
     * Input: `field1:::field2:::"field with ::: inside":::field4`
     * Delimiter: ":::"
     * Output: ["field1", "field2", "field with ::: inside", "field4"]
     */
    fun parseDelimitedLine(line: String, delimiter: String): List<String> {
        if (line.isBlank()) return emptyList()

        val result = mutableListOf<String>()
        var current = StringBuilder()
        var inQuotes = false
        var i = 0

        while (i < line.length) {
            when {
                line[i] == QUOTE_CHAR && i + 1 < line.length && line[i + 1] == QUOTE_CHAR -> {
                    current.append(QUOTE_CHAR)
                    i += 2
                }
                line[i] == QUOTE_CHAR -> {
                    inQuotes = !inQuotes
                    i++
                }
                !inQuotes && line.startsWith(delimiter, i) -> {
                    val fieldStr = current.toString()
                    result.add(fieldStr.unquoteValue())
                    current = StringBuilder()
                    i += delimiter.length
                }
                else -> {
                    current.append(line[i])
                    i++
                }
            }
        }

        val fieldStr = current.toString()
        result.add(fieldStr.unquoteValue())

        return result
    }

    /**
     * Serialize a list of fields into a delimited line with proper quoting.
     * Fields containing the delimiter will be quoted.
     *
     * Example:
     * Fields: ["field1", "field2", "field with ::: inside", "field4"]
     * Delimiter: ":::"
     * Output: `field1:::field2:::"field with ::: inside":::field4`
     */
    fun serializeDelimitedLine(fields: List<String>, delimiter: String): String {
        return fields.joinToString(delimiter) { field ->
            if (field.contains(delimiter) || field.contains(QUOTE_CHAR)) {
                "\"${field.replace("\"", "\"\"")}\""
            } else {
                field
            }
        }
    }
}