package com.taqwa.journal.core.utilities

/**
 * CSV Preference Parser — منصّر تفضيلات CSV
 *
 * Safely serializes and parses CSV-like data structures with protection against
 * delimiters appearing in field values. Handles CSV quoting (RFC 4180).
 *
 * Key features:
 * - Quote-escaping for fields containing delimiters
 * - Support for nested structures
 * - Type transformation support
 * - No external dependencies
 */
object CsvPreferenceParser {

    /**
     * Parse a single delimited line with quote handling
     *
     * Example:
     * ```
     * parseDelimitedLine("field1,\"field,with,commas\",field3", ",")
     * // Returns: ["field1", "field,with,commas", "field3"]
     * ```
     */
    fun parseDelimitedLine(line: String, delimiter: String): List<String> {
        if (line.isEmpty()) return emptyList()

        val fields = mutableListOf<String>()
        var current = StringBuilder()
        var i = 0
        var inQuotes = false

        while (i < line.length) {
            val char = line[i]

            when {
                char == '"' -> {
                    // Handle quote escaping (RFC 4180: "" within quotes = single ")
                    if (inQuotes && i + 1 < line.length && line[i + 1] == '"') {
                        current.append('"')
                        i += 2
                    } else {
                        inQuotes = !inQuotes
                        i++
                    }
                }
                !inQuotes && line.startsWith(delimiter, i) -> {
                    // Found unquoted delimiter
                    fields.add(current.toString())
                    current = StringBuilder()
                    i += delimiter.length
                }
                else -> {
                    current.append(char)
                    i++
                }
            }
        }

        // Add final field
        fields.add(current.toString())

        return fields
    }

    /**
     * Serialize a list of fields to a delimited line with quote handling
     *
     * Example:
     * ```
     * serializeDelimitedLine(
     *   listOf("field1", "field,with,commas", "field3"),
     *   ","
     * )
     * // Returns: "field1,\"field,with,commas\",field3"
     * ```
     */
    fun serializeDelimitedLine(fields: List<String>, delimiter: String): String {
        return fields.joinToString(delimiter) { field ->
            if (field.contains(delimiter) || field.contains('"') || field.contains('\n')) {
                // Quote the field and escape any quotes inside (RFC 4180)
                val escaped = field.replace("\"", "\"\"")
                "\"$escaped\""
            } else {
                field
            }
        }
    }

    /**
     * Parse a nested CSV structure (records separated by outer delimiter)
     *
     * Type transformation example:
     * ```
     * data class Record(val name: String, val count: Int)
     *
     * val raw = "Alice,5;;;Bob,10;;;Charlie,3"
     * val records = parseNested<Record>(raw, ";;;") { fields ->
     *   if (fields.size >= 2) Record(fields[0], fields[1].toInt()) else null
     * }
     * ```
     */
    inline fun <T> parseNested(
        raw: String,
        outerDelimiter: String,
        fieldsPerRecord: Int = 0,
        transform: (List<String>) -> T?
    ): List<T> {
        if (raw.isEmpty()) return emptyList()

        return raw.split(outerDelimiter)
            .mapNotNull { recordStr ->
                if (recordStr.isEmpty()) return@mapNotNull null
                val fields = parseDelimitedLine(recordStr, "|||")
                if (fieldsPerRecord > 0 && fields.size < fieldsPerRecord) {
                    null
                } else {
                    transform(fields)
                }
            }
    }

    /**
     * Serialize a list of records to a nested CSV structure
     *
     * Type transformation example:
     * ```
     * data class Record(val name: String, val count: Int)
     *
     * val records = listOf(Record("Alice", 5), Record("Bob", 10))
     * val raw = serializeNested(records, ";;;") { record ->
     *   listOf(record.name, record.count.toString())
     * }
     * // Returns: "Alice,5;;;Bob,10"
     * ```
     */
    inline fun <T> serializeNested(
        items: List<T>,
        outerDelimiter: String,
        crossinline transform: (T) -> List<String>
    ): String {
        return items.joinToString(outerDelimiter) { item ->
            val fields = transform(item)
            serializeDelimitedLine(fields, "|||")
        }
    }

    /**
     * Escape a single field for use in delimited format
     * Useful when manually building delimited strings
     */
    fun escapeField(value: String, delimiter: String): String {
        return if (value.contains(delimiter) || value.contains('"') || value.contains('\n')) {
            val escaped = value.replace("\"", "\"\"")
            "\"$escaped\""
        } else {
            value
        }
    }

    /**
     * Unescape a single field (remove outer quotes if present, unescape inner quotes)
     * Useful when manually parsing delimited strings
     */
    fun unescapeField(value: String): String {
        if (value.startsWith('"') && value.endsWith('"') && value.length >= 2) {
            return value.substring(1, value.length - 1).replace("\"\"", "\"")
        }
        return value
    }
}
