package com.taqwa.journal.core.utilities

/**
 * JSON parsing and formatting helpers for structured data export/import.
 */
object JsonUtils {
    
    fun escapeJsonString(value: String): String {
        return value
            .replace("\\", "\\\\")
            .replace("\"", "\\\"")
            .replace("\b", "\\b")
            .replace("\u000c", "\\f")
            .replace("\n", "\\n")
            .replace("\r", "\\r")
            .replace("\t", "\\t")
    }

    fun unescapeJsonString(value: String): String {
        return value
            .replace("\\\"", "\"")
            .replace("\\\\", "\\")
            .replace("\\b", "\b")
            .replace("\\f", "\u000c")
            .replace("\\n", "\n")
            .replace("\\r", "\r")
            .replace("\\t", "\t")
    }

    fun toJson(key: String, value: Any?): String {
        return when (value) {
            null -> "\"$key\":null"
            is String -> "\"$key\":\"${escapeJsonString(value)}\""
            is Number -> "\"$key\":$value"
            is Boolean -> "\"$key\":$value"
            is List<*> -> "\"$key\":[${value.map { toJsonValue(it) }.joinToString(",")}]"
            is Map<*, *> -> "\"$key\":{${value.map { (k, v) -> toJson(k.toString(), v) }.joinToString(",")}}"
            else -> "\"$key\":\"${escapeJsonString(value.toString())}\""
        }
    }

    private fun toJsonValue(value: Any?): String {
        return when (value) {
            null -> "null"
            is String -> "\"${escapeJsonString(value)}\""
            is Number -> value.toString()
            is Boolean -> value.toString()
            is List<*> -> "[${value.map { toJsonValue(it) }.joinToString(",")}]"
            is Map<*, *> -> "{${value.map { (k, v) -> "\"$k\":${toJsonValue(v)}" }.joinToString(",")}}"
            else -> "\"${escapeJsonString(value.toString())}\""
        }
    }

    fun formatJson(json: String, indent: Int = 2): String {
        var indentLevel = 0
        val result = StringBuilder()
        var inString = false
        var escapeNext = false
        val indentStr = " ".repeat(indent)

        for (char in json) {
            when {
                escapeNext -> {
                    result.append(char)
                    escapeNext = false
                }
                char == '\\' && inString -> {
                    result.append(char)
                    escapeNext = true
                }
                char == '"' -> {
                    result.append(char)
                    inString = !inString
                }
                !inString && (char == '{' || char == '[') -> {
                    result.append(char)
                    indentLevel++
                    result.append('\n')
                    result.append(indentStr.repeat(indentLevel))
                }
                !inString && (char == '}' || char == ']') -> {
                    indentLevel--
                    result.append('\n')
                    result.append(indentStr.repeat(indentLevel))
                    result.append(char)
                }
                !inString && char == ',' -> {
                    result.append(char)
                    result.append('\n')
                    result.append(indentStr.repeat(indentLevel))
                }
                !inString && char == ':' -> {
                    result.append(char)
                    result.append(' ')
                }
                !inString && char.isWhitespace() -> {
                    // Skip whitespace outside strings
                }
                else -> {
                    result.append(char)
                }
            }
        }

        return result.toString()
    }

    fun isValidJson(json: String): Boolean {
        return try {
            val trimmed = json.trim()
            (trimmed.startsWith("{") && trimmed.endsWith("}")) ||
            (trimmed.startsWith("[") && trimmed.endsWith("]"))
        } catch (e: Exception) {
            false
        }
    }
}
