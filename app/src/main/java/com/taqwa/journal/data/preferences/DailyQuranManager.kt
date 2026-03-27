package com.taqwa.journal.data.preferences

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
        ),
        DailyAyah(
            arabic = "وَاصْبِرْ فَإِنَّ اللَّهَ لَا يُضِيعُ أَجْرَ الْمُحْسِنِينَ",
            translation = "And be patient, for indeed Allah does not waste the reward of those who do good.",
            reference = "Hud 11:115"
        ),
        DailyAyah(
            arabic = "فَاذْكُرُونِي أَذْكُرْكُمْ وَاشْكُرُوا لِي وَلَا تَكْفُرُونِ",
            translation = "So remember Me; I will remember you. And be grateful to Me and do not deny Me.",
            reference = "Al-Baqarah 2:152"
        ),
        DailyAyah(
            arabic = "رَبَّنَا لَا تُزِغْ قُلُوبَنَا بَعْدَ إِذْ هَدَيْتَنَا",
            translation = "Our Lord, let not our hearts deviate after You have guided us.",
            reference = "Aal-Imran 3:8"
        ),
        DailyAyah(
            arabic = "قُلْ إِنَّ صَلَاتِي وَنُسُكِي وَمَحْيَايَ وَمَمَاتِي لِلَّهِ رَبِّ الْعَالَمِينَ",
            translation = "Say: Indeed, my prayer, my sacrifice, my living and my dying are for Allah, Lord of the worlds.",
            reference = "Al-An'am 6:162"
        ),
        DailyAyah(
            arabic = "وَلَا تَهِنُوا وَلَا تَحْزَنُوا وَأَنتُمُ الْأَعْلَوْنَ إِن كُنتُم مُّؤْمِنِينَ",
            translation = "So do not weaken and do not grieve, and you will be superior if you are believers.",
            reference = "Aal-Imran 3:139"
        ),
        DailyAyah(
            arabic = "وَاللَّهُ يُحِبُّ الصَّابِرِينَ",
            translation = "And Allah loves the patient.",
            reference = "Aal-Imran 3:146"
        ),
        DailyAyah(
            arabic = "أَلَا بِذِكْرِ اللَّهِ تَطْمَئِنُّ الْقُلُوبُ",
            translation = "Verily, in the remembrance of Allah do hearts find rest.",
            reference = "Ar-Ra'd 13:28"
        ),
        DailyAyah(
            arabic = "وَنَفْسٍ وَمَا سَوَّاهَا فَأَلْهَمَهَا فُجُورَهَا وَتَقْوَاهَا قَدْ أَفْلَحَ مَن زَكَّاهَا",
            translation = "And by the soul and He who proportioned it, and inspired it with its wickedness and its righteousness. He has succeeded who purifies it.",
            reference = "Ash-Shams 91:7-9"
        ),
        DailyAyah(
            arabic = "إِنَّ اللَّهَ لَا يُغَيِّرُ مَا بِقَوْمٍ حَتَّىٰ يُغَيِّرُوا مَا بِأَنفُسِهِمْ",
            translation = "Indeed, Allah will not change the condition of a people until they change what is in themselves.",
            reference = "Ar-Ra'd 13:11"
        ),
        DailyAyah(
            arabic = "وَإِذَا سَأَلَكَ عِبَادِي عَنِّي فَإِنِّي قَرِيبٌ",
            translation = "And when My servants ask you about Me - indeed I am near.",
            reference = "Al-Baqarah 2:186"
        ),
        DailyAyah(
            arabic = "وَمَا تَوْفِيقِي إِلَّا بِاللَّهِ عَلَيْهِ تَوَكَّلْتُ وَإِلَيْهِ أُنِيبُ",
            translation = "And my success is not but through Allah. Upon Him I have relied, and to Him I return.",
            reference = "Hud 11:88"
        ),
        DailyAyah(
            arabic = "وَلَقَدْ خَلَقْنَا الْإِنسَانَ وَنَعْلَمُ مَا تُوَسْوِسُ بِهِ نَفْسُهُ وَنَحْنُ أَقْرَبُ إِلَيْهِ مِنْ حَبْلِ الْوَرِيدِ",
            translation = "And We have already created man and know what his soul whispers to him, and We are closer to him than his jugular vein.",
            reference = "Qaf 50:16"
        ),
        DailyAyah(
            arabic = "رَبِّ اجْعَلْنِي مُقِيمَ الصَّلَاةِ وَمِن ذُرِّيَّتِي رَبَّنَا وَتَقَبَّلْ دُعَاءِ",
            translation = "My Lord, make me an establisher of prayer, and from my descendants. Our Lord, and accept my supplication.",
            reference = "Ibrahim 14:40"
        ),
        DailyAyah(
            arabic = "وَاسْتَغْفِرُوا رَبَّكُمْ ثُمَّ تُوبُوا إِلَيْهِ إِنَّ رَبِّي رَحِيمٌ وَدُودٌ",
            translation = "And seek forgiveness of your Lord and repent to Him. Indeed, my Lord is Merciful and Loving.",
            reference = "Hud 11:90"
        ),
        DailyAyah(
            arabic = "وَلَسَوْفَ يُعْطِيكَ رَبُّكَ فَتَرْضَىٰ",
            translation = "And your Lord is going to give you, and you will be satisfied.",
            reference = "Ad-Duha 93:5"
        ),
        DailyAyah(
            arabic = "فَإِنَّ مَعَ الْعُسْرِ يُسْرًا إِنَّ مَعَ الْعُسْرِ يُسْرًا",
            translation = "For indeed, with hardship comes ease. Indeed, with hardship comes ease.",
            reference = "Ash-Sharh 94:5-6"
        ),
        DailyAyah(
            arabic = "وَقُل رَّبِّ زِدْنِي عِلْمًا",
            translation = "And say: My Lord, increase me in knowledge.",
            reference = "Ta-Ha 20:114"
        ),
        DailyAyah(
            arabic = "حَسْبُنَا اللَّهُ وَنِعْمَ الْوَكِيلُ",
            translation = "Sufficient for us is Allah, and He is the best Disposer of affairs.",
            reference = "Aal-Imran 3:173"
        ),
        DailyAyah(
            arabic = "وَمَن يَتَّقِ اللَّهَ يَجْعَل لَّهُ مِنْ أَمْرِهِ يُسْرًا",
            translation = "And whoever fears Allah - He will make for him ease in his matter.",
            reference = "At-Talaq 65:4"
        ),
        DailyAyah(
            arabic = "رَبَّنَا آتِنَا فِي الدُّنْيَا حَسَنَةً وَفِي الْآخِرَةِ حَسَنَةً وَقِنَا عَذَابَ النَّارِ",
            translation = "Our Lord, give us good in this world and good in the Hereafter, and protect us from the torment of the Fire.",
            reference = "Al-Baqarah 2:201"
        ),
        DailyAyah(
            arabic = "وَتُوبُوا إِلَى اللَّهِ جَمِيعًا أَيُّهَ الْمُؤْمِنُونَ لَعَلَّكُمْ تُفْلِحُونَ",
            translation = "And turn to Allah in repentance, all of you, O believers, that you might succeed.",
            reference = "An-Nur 24:31"
        ),
        DailyAyah(
            arabic = "إِنَّ الْحَسَنَاتِ يُذْهِبْنَ السَّيِّئَاتِ",
            translation = "Indeed, good deeds remove bad deeds.",
            reference = "Hud 11:114"
        ),
        DailyAyah(
            arabic = "وَلَنَبْلُوَنَّكُم بِشَيْءٍ مِّنَ الْخَوْفِ وَالْجُوعِ وَنَقْصٍ مِّنَ الْأَمْوَالِ وَالْأَنفُسِ وَالثَّمَرَاتِ وَبَشِّرِ الصَّابِرِينَ",
            translation = "And We will surely test you with something of fear and hunger and loss of wealth and lives and fruits, but give good tidings to the patient.",
            reference = "Al-Baqarah 2:155"
        ),
        DailyAyah(
            arabic = "يَا أَيُّهَا الَّذِينَ آمَنُوا اتَّقُوا اللَّهَ وَكُونُوا مَعَ الصَّادِقِينَ",
            translation = "O you who believe, fear Allah and be with those who are truthful.",
            reference = "At-Tawbah 9:119"
        ),
        DailyAyah(
            arabic = "وَاذْكُر رَّبَّكَ فِي نَفْسِكَ تَضَرُّعًا وَخِيفَةً",
            translation = "And remember your Lord within yourself in humility and in fear.",
            reference = "Al-A'raf 7:205"
        ),
        DailyAyah(
            arabic = "إِنَّ اللَّهَ مَعَ الَّذِينَ اتَّقَوا وَّالَّذِينَ هُم مُّحْسِنُونَ",
            translation = "Indeed, Allah is with those who fear Him and those who are doers of good.",
            reference = "An-Nahl 16:128"
        ),
        DailyAyah(
            arabic = "وَمَا خَلَقْتُ الْجِنَّ وَالْإِنسَ إِلَّا لِيَعْبُدُونِ",
            translation = "And I did not create the jinn and mankind except to worship Me.",
            reference = "Adh-Dhariyat 51:56"
        )
    )

    // Get today's ayah
    fun getTodaysAyah(): DailyAyah {
        val today = LocalDate.now().toString()
        val lastDate = prefs.getString(KEY_LAST_DATE, "") ?: ""

        return if (today == lastDate) {
            // Same day, return same ayah
            val index = prefs.getInt(KEY_LAST_INDEX, 0)
            dailyAyahs[index % dailyAyahs.size]
        } else {
            // New day, pick next ayah
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