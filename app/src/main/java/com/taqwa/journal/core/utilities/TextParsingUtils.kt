package com.taqwa.journal.core.utilities

/**
 * Text parsing and manipulation utilities.
 */
object TextParsingUtils {
    
    fun extractHashtags(text: String): List<String> {
        val hashtagRegex = Regex("""#\w+""")
        return hashtagRegex.findAll(text).map { it.value.substring(1) }.toList()
    }

    fun extractMentions(text: String): List<String> {
        val mentionRegex = Regex("""@\w+""")
        return mentionRegex.findAll(text).map { it.value.substring(1) }.toList()
    }

    fun extractUrls(text: String): List<String> {
        val urlRegex = Regex("""https?://[^\s]+|www\.[^\s]+""")
        return urlRegex.findAll(text).map { it.value }.toList()
    }

    fun highlightText(text: String, searchTerm: String): String {
        if (searchTerm.isEmpty()) return text

        val regex = Regex(searchTerm, RegexOption.IGNORE_CASE)
        return text.replace(regex) { "**${it.value}**" }
    }

    fun stripMarkdown(text: String): String {
        return text
            .replace(Regex("""##?\s+"""), "")
            .replace(Regex("""\*\*(.+?)\*\*"""), "$1")
            .replace(Regex("""\*(.+?)\*"""), "$1")
            .replace(Regex("""__(.+?)__"""), "$1")
            .replace(Regex("""_(.+?)_"""), "$1")
            .replace(Regex("""`(.+?)`"""), "$1")
            .replace(Regex("""~~(.+?)~~"""), "$1")
    }

    fun truncate(text: String, maxLength: Int, suffix: String = "..."): String {
        if (text.length <= maxLength) return text
        return text.substring(0, maxOf(0, maxLength - suffix.length)) + suffix
    }

    fun capitalize(text: String): String {
        if (text.isEmpty()) return text
        return text[0].uppercase() + text.substring(1)
    }

    fun sentenceCase(text: String): String {
        if (text.isEmpty()) return text
        return text[0].uppercase() + text.substring(1).lowercase()
    }

    fun wordWrap(text: String, wrapLength: Int = 80): String {
        val words = text.split(" ")
        val lines = mutableListOf<String>()
        var currentLine = ""

        for (word in words) {
            if ((currentLine + word).length > wrapLength && currentLine.isNotEmpty()) {
                lines.add(currentLine.trim())
                currentLine = word
            } else {
                if (currentLine.isNotEmpty()) currentLine += " "
                currentLine += word
            }
        }

        if (currentLine.isNotEmpty()) lines.add(currentLine.trim())
        return lines.joinToString("\n")
    }

    fun isValidEmail(email: String): Boolean {
        return email.matches(Regex("""[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Z|a-z]{2,}"""))
    }

    fun slugify(text: String): String {
        return text
            .lowercase()
            .replace(Regex("""[^\w\s-]"""), "")
            .replace(Regex("""\s+"""), "-")
            .replace(Regex("""-+"""), "-")
            .trim('-')
    }

    fun getWordCount(text: String): Int {
        return text.trim().split(Regex("""\s+""")).filter { it.isNotEmpty() }.size
    }

    fun getCharacterCount(text: String, includeSpaces: Boolean = true): Int {
        return if (includeSpaces) text.length else text.replace(" ", "").length
    }

    fun highlight(text: String, pattern: String): String {
        val regex = Regex(pattern, RegexOption.IGNORE_CASE)
        return text.replace(regex) { "<<${it.value}>>" }
    }
}
