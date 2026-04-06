package com.taqwa.journal.core.utilities

import android.content.Context
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * Simple in-app caching utility for temporary data storage.
 * Not for persistent storage - use preferences/database for that.
 */
object CacheManager {
    
    private val memoryCache = mutableMapOf<String, CacheEntry>()

    data class CacheEntry(
        val value: Any,
        val expiresAt: Long
    )

    fun put(
        key: String,
        value: Any,
        ttlMinutes: Int = 60
    ) {
        val expiresAt = System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(ttlMinutes.toLong())
        memoryCache[key] = CacheEntry(value, expiresAt)
    }

    fun get(key: String): Any? {
        val entry = memoryCache[key]
        
        return if (entry != null && System.currentTimeMillis() < entry.expiresAt) {
            entry.value
        } else {
            memoryCache.remove(key)
            null
        }
    }

    inline fun <reified T> getAs(key: String): T? {
        return get(key) as? T
    }

    fun contains(key: String): Boolean {
        return get(key) != null
    }

    fun remove(key: String) {
        memoryCache.remove(key)
    }

    fun clear() {
        memoryCache.clear()
    }

    fun clearExpired() {
        val now = System.currentTimeMillis()
        memoryCache.entries.removeAll { it.value.expiresAt < now }
    }

    fun getSize(): Int {
        clearExpired()
        return memoryCache.size
    }

    // File-based cache for images/documents
    fun getCacheDir(context: Context, subDir: String = "app_cache"): File {
        return File(context.cacheDir, subDir).apply {
            if (!exists()) mkdirs()
        }
    }

    fun clearCacheDir(context: Context, subDir: String = "app_cache") {
        val dir = File(context.cacheDir, subDir)
        if (dir.exists() && dir.isDirectory) {
            dir.deleteRecursively()
        }
    }

    fun getCacheDirSize(context: Context, subDir: String = "app_cache"): Long {
        val dir = File(context.cacheDir, subDir)
        return if (dir.exists()) getDirectorySize(dir) else 0
    }

    private fun getDirectorySize(dir: File): Long {
        var size = 0L
        val files = dir.listFiles() ?: return 0
        
        for (file in files) {
            size += if (file.isDirectory) {
                getDirectorySize(file)
            } else {
                file.length()
            }
        }
        
        return size
    }
}
