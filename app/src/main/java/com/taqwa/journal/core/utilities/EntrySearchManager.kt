package com.taqwa.journal.core.utilities

/**
 * Provides entry filtering and search capabilities.
 * Supports full-text search, tag filtering, date range queries.
 */
object EntrySearchManager {
    
    data class SearchQuery(
        val text: String? = null,
        val tags: Set<String> = emptySet(),
        val fromDate: Long? = null,
        val toDate: Long? = null,
        val entryType: String? = null // "journal", "memory", "check_in"
    )

    fun normalizeSearchText(text: String): String {
        return text.lowercase().trim()
    }

    fun tokenizeSearchText(text: String): List<String> {
        return normalizeSearchText(text)
            .split(Regex("\\s+"))
            .filter { it.isNotEmpty() }
    }

    fun buildSearchQuery(
        text: String? = null,
        tags: Set<String> = emptySet(),
        fromDate: Long? = null,
        toDate: Long? = null,
        entryType: String? = null
    ): SearchQuery {
        return SearchQuery(
            text = text?.takeIf { it.isNotEmpty() },
            tags = tags.map { it.lowercase().trim() }.toSet(),
            fromDate = fromDate,
            toDate = toDate,
            entryType = entryType?.lowercase()?.trim()
        )
    }

    fun matchesQuery(
        entryText: String,
        entryTags: Set<String>,
        entryTimestamp: Long,
        entryType: String,
        query: SearchQuery
    ): Boolean {
        // Check text match
        if (query.text != null) {
            val tokens = tokenizeSearchText(query.text)
            val normalizedEntryText = normalizeSearchText(entryText)
            if (!tokens.all { normalizedEntryText.contains(it) }) return false
        }

        // Check tags match
        if (query.tags.isNotEmpty()) {
            val normalizedEntryTags = entryTags.map { it.lowercase().trim() }.toSet()
            if (!query.tags.any { it in normalizedEntryTags }) return false
        }

        // Check date range
        if (query.fromDate != null && entryTimestamp < query.fromDate) return false
        if (query.toDate != null && entryTimestamp > query.toDate) return false

        // Check entry type
        if (query.entryType != null && entryType.lowercase().trim() != query.entryType) return false

        return true
    }

    fun calculateRelevanceScore(
        entryText: String,
        entryTags: Set<String>,
        query: SearchQuery
    ): Float {
        var score = 0f

        if (query.text != null) {
            val tokens = tokenizeSearchText(query.text)
            val normalizedEntryText = normalizeSearchText(entryText)
            
            for (token in tokens) {
                if (normalizedEntryText.startsWith(token)) score += 2f
                else if (normalizedEntryText.contains(token)) score += 1f
            }
        }

        if (query.tags.isNotEmpty()) {
            val normalizedEntryTags = entryTags.map { it.lowercase().trim() }.toSet()
            val matchingTags = query.tags.filter { it in normalizedEntryTags }.size
            score += matchingTags * 1.5f
        }

        return score
    }
}
