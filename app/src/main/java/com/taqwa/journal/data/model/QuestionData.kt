package com.taqwa.journal.data.model

object QuestionData {

    // Feelings options for Question 2
    val feelings = listOf(
        "😔 Lonely",
        "😐 Bored",
        "😰 Stressed",
        "😢 Sad",
        "😫 Tired",
        "😟 Anxious",
        "😤 Angry",
        "😞 Rejected",
        "😶 Overwhelmed",
        "😳 Ashamed",
        "🫠 Numb",
        "😮‍💨 Frustrated"
    )

    // Real needs options for Question 3
    val realNeeds = listOf(
        "🤝 Connection / Companionship",
        "🫂 Comfort / Relief",
        "🚪 Escape from reality",
        "⚡ Excitement / Stimulation",
        "❤️ Love / Validation",
        "😴 Rest / Sleep",
        "😮‍💨 Stress relief",
        "🎯 Sense of achievement",
        "🧘 Peace of mind"
    )

    // Alternative activities for Question 4
    val alternatives = listOf(
        "🤲 Make wudu & pray 2 rakaat",
        "🚶 Go for a walk",
        "📞 Call someone",
        "💪 Do 20 pushups",
        "🚿 Take a cold shower",
        "📖 Read Quran",
        "📚 Study for 25 minutes",
        "🎧 Listen to Quran",
        "✍️ Write my thoughts",
        "🍵 Make tea and sit quietly",
        "🏃 Go for a run",
        "🧘 Meditate / Dhikr"
    )

    // Reality check lines - shown one by one
    val realityCheckLines = listOf(
        "The last time you did this, you felt disgusted.",
        "You felt empty and ashamed for hours.",
        "You promised yourself NEVER AGAIN.",
        "That promise was REAL. This urge is FAKE.",
        "This feeling will pass in 15 minutes.",
        "The regret will last much longer.",
        "You are stronger than you think."
    )

    // Islamic reminders - random one shown each time
    val islamicReminders = listOf(
        IslamicReminder(
            arabic = "أَلَمْ يَعْلَم بِأَنَّ اللَّهَ يَرَىٰ",
            translation = "Does he not know that Allah sees?",
            reference = "Surah Al-Alaq 96:14",
            reflection = "Allah is watching you RIGHT NOW.\nNot to punish you, but because He CARES about you.\nHe wants you to win this battle.\nEvery time you resist, He is PROUD of you.\nYou are not alone in this room."
        ),
        IslamicReminder(
            arabic = "إِنَّ اللَّهَ مَعَ الصَّابِرِينَ",
            translation = "Indeed, Allah is with the patient.",
            reference = "Surah Al-Baqarah 2:153",
            reflection = "Patience is not about being strong all the time.\nIt's about turning to Allah when you feel weak.\nHe is WITH you right now.\nHe sees your struggle and He honors it.\nBe patient for just 15 more minutes."
        ),
        IslamicReminder(
            arabic = "وَمَن يَتَّقِ اللَّهَ يَجْعَل لَّهُ مَخْرَجًا",
            translation = "Whoever fears Allah, He will make a way out for him.",
            reference = "Surah At-Talaq 65:2",
            reflection = "You fear Allah - that's why you opened this app.\nThat fear is TAQWA.\nAnd Allah promised He will make a way out.\nThis app is your way out right now.\nTrust His promise."
        ),
        IslamicReminder(
            arabic = "قُلْ يَا عِبَادِيَ الَّذِينَ أَسْرَفُوا عَلَىٰ أَنفُسِهِمْ لَا تَقْنَطُوا مِن رَّحْمَةِ اللَّهِ",
            translation = "Say: O My servants who have transgressed against themselves, do not despair of the mercy of Allah.",
            reference = "Surah Az-Zumar 39:53",
            reflection = "Even if you've fallen before, Allah is calling you back.\nHis mercy is bigger than any sin.\nHe loves that you're fighting.\nDon't let shame push you away from Him.\nLet it push you TOWARD Him."
        ),
        IslamicReminder(
            arabic = "وَالَّذِينَ جَاهَدُوا فِينَا لَنَهْدِيَنَّهُمْ سُبُلَنَا",
            translation = "And those who strive for Us - We will surely guide them to Our ways.",
            reference = "Surah Al-Ankabut 29:69",
            reflection = "You are in JIHAD right now.\nThe jihad against your own desires.\nThis is the greatest jihad.\nAnd Allah promised to guide those who strive.\nKeep striving. He will guide you through."
        )
    )

    // Victory ayahs - shown on victory screen
    val victoryAyahs = listOf(
        IslamicReminder(
            arabic = "وَأَمَّا مَنْ خَافَ مَقَامَ رَبِّهِ وَنَهَى النَّفْسَ عَنِ الْهَوَىٰ فَإِنَّ الْجَنَّةَ هِيَ الْمَأْوَىٰ",
            translation = "But as for he who feared standing before his Lord and restrained the soul from desire - then indeed, Paradise will be his refuge.",
            reference = "Surah An-Nazi'at 79:40-41",
            reflection = ""
        ),
        IslamicReminder(
            arabic = "إِنَّ اللَّهَ يُحِبُّ التَّوَّابِينَ وَيُحِبُّ الْمُتَطَهِّرِينَ",
            translation = "Indeed, Allah loves those who are constantly repentant and loves those who purify themselves.",
            reference = "Surah Al-Baqarah 2:222",
            reflection = ""
        ),
        IslamicReminder(
            arabic = "وَالَّذِينَ إِذَا فَعَلُوا فَاحِشَةً أَوْ ظَلَمُوا أَنفُسَهُمْ ذَكَرُوا اللَّهَ فَاسْتَغْفَرُوا لِذُنُوبِهِمْ",
            translation = "And those who, when they commit an immorality or wrong themselves, remember Allah and seek forgiveness for their sins.",
            reference = "Surah Aal-Imran 3:135",
            reflection = ""
        ),
        IslamicReminder(
            arabic = "قَدْ أَفْلَحَ مَن زَكَّاهَا",
            translation = "He has succeeded who purifies his soul.",
            reference = "Surah Ash-Shams 91:9",
            reflection = ""
        )
    )
}

// Data class for Islamic reminders
data class IslamicReminder(
    val arabic: String,
    val translation: String,
    val reference: String,
    val reflection: String
)