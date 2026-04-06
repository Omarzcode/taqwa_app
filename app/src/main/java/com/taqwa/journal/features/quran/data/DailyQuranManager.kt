package com.taqwa.journal.features.quran.data

import android.content.Context
import android.content.SharedPreferences
import java.time.LocalDate

class DailyQuranManager(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("taqwa_daily_quran", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_LAST_DATE = "last_date"
        private const val KEY_LAST_INDEX = "last_index"
    }

    // Curated ayahs about patience, taqwa, self-control, mercy
    private val dailyAyahs = listOf(
        DailyAyah(
            arabic = "يَا أَيُّهَا الَّذِينَ آمَنُوا اسْتَعِينُوا بِالصَّبْرِ وَالصَّلَاةِ",
            translation = "O you who believe, seek help through patience and prayer.",
            reference = "Al-Baqarah 2:153"
        ),
        DailyAyah(
            arabic = "إِنَّ مَعَ الْعُسْرِ يُسْرًا",
            translation = "Indeed, with hardship comes ease.",
            reference = "Ash-Sharh 94:6"
        ),
        DailyAyah(
            arabic = "وَمَن يَتَوَكَّلْ عَلَى اللَّهِ فَهُوَ حَسْبُهُ",
            translation = "And whoever relies upon Allah - then He is sufficient for him.",
            reference = "At-Talaq 65:3"
        )
    )

    fun getTodaysAyah(): DailyAyah {
        val today = LocalDate.now().toString()
        val lastDate = prefs.getString(KEY_LAST_DATE, "") ?: ""

        return if (today == lastDate) {
            val index = prefs.getInt(KEY_LAST_INDEX, 0)
            dailyAyahs[index % dailyAyahs.size]
        } else {
            val lastIndex = prefs.getInt(KEY_LAST_INDEX, -1)
            val newIndex = (lastIndex + 1) % dailyAyahs.size
            prefs.edit()
                .putString(KEY_LAST_DATE, today)
                .putInt(KEY_LAST_INDEX, newIndex)
                .apply()
            dailyAyahs[newIndex]
        }
    }
}

data class DailyAyah(
    val arabic: String,
    val translation: String,
    val reference: String
)
