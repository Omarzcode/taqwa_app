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
        "😮‍💨 Frustrated",
        "😕 Confused",
        "🥱 Restless",
        "💔 Heartbroken",
        "😒 Unmotivated"
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
        "🧘 Peace of mind",
        "💪 Control over something",
        "🎭 Distraction from pain",
        "🔥 Physical release"
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
        "🧘 Meditate / Dhikr",
        "🍎 Eat something healthy",
        "🛏️ Change your environment",
        "📿 Say Istighfar 100 times",
        "🫁 Do breathing exercises"
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

    // ========================
    // ISLAMIC REMINDERS
    // Quran Ayat + Sahih Hadith + Salaf Quotes
    // All sources verified for accuracy
    // ========================
    val islamicReminders = listOf(

        // ── QURAN AYAT ──

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
            reflection = "You fear Allah — that's why you opened this app.\nThat fear is TAQWA.\nAnd Allah promised He will make a way out.\nThis app is your way out right now.\nTrust His promise."
        ),
        IslamicReminder(
            arabic = "قُلْ يَا عِبَادِيَ الَّذِينَ أَسْرَفُوا عَلَىٰ أَنفُسِهِمْ لَا تَقْنَطُوا مِن رَّحْمَةِ اللَّهِ",
            translation = "Say: O My servants who have transgressed against themselves, do not despair of the mercy of Allah.",
            reference = "Surah Az-Zumar 39:53",
            reflection = "Even if you've fallen before, Allah is calling you back.\nHis mercy is bigger than any sin.\nHe loves that you're fighting.\nDon't let shame push you away from Him.\nLet it push you TOWARD Him."
        ),
        IslamicReminder(
            arabic = "وَالَّذِينَ جَاهَدُوا فِينَا لَنَهْدِيَنَّهُمْ سُبُلَنَا",
            translation = "And those who strive for Us — We will surely guide them to Our ways.",
            reference = "Surah Al-Ankabut 29:69",
            reflection = "You are in JIHAD right now.\nThe jihad against your own desires.\nThis is the greatest jihad.\nAnd Allah promised to guide those who strive.\nKeep striving. He will guide you through."
        ),
        IslamicReminder(
            arabic = "إِنَّ الصَّلَاةَ تَنْهَىٰ عَنِ الْفَحْشَاءِ وَالْمُنكَرِ",
            translation = "Indeed, prayer prohibits immorality and wrongdoing.",
            reference = "Surah Al-Ankabut 29:45",
            reflection = "Get up and pray right now.\nEven just two rakaat.\nAllah placed a shield in salah for you.\nThe act of standing before Him breaks the spell.\nLet the prayer do its work."
        ),
        IslamicReminder(
            arabic = "قُل لِّلْمُؤْمِنِينَ يَغُضُّوا مِنْ أَبْصَارِهِمْ وَيَحْفَظُوا فُرُوجَهُمْ ذَٰلِكَ أَزْكَىٰ لَهُمْ",
            translation = "Tell the believing men to lower their gaze and guard their private parts. That is purer for them.",
            reference = "Surah An-Nur 24:30",
            reflection = "Lowering the gaze is the first line of defense.\nAllah called it 'purer' — meaning it CLEANSES you.\nEvery time you look away, your heart gets cleaner.\nThis is not restriction — it is PURIFICATION.\nAllah wants purity for you."
        ),
        IslamicReminder(
            arabic = "أَلَا بِذِكْرِ اللَّهِ تَطْمَئِنُّ الْقُلُوبُ",
            translation = "Verily, in the remembrance of Allah do hearts find rest.",
            reference = "Surah Ar-Ra'd 13:28",
            reflection = "Your heart is restless right now.\nIt's looking for peace in the wrong place.\nTrue peace is in remembering Allah.\nSay SubhanAllah, Alhamdulillah, Allahu Akbar.\nFeel your heart slow down."
        ),
        IslamicReminder(
            arabic = "وَنَحْنُ أَقْرَبُ إِلَيْهِ مِنْ حَبْلِ الْوَرِيدِ",
            translation = "And We are closer to him than his jugular vein.",
            reference = "Surah Qaf 50:16",
            reflection = "Allah is closer to you than you think.\nCloser than your own heartbeat.\nHe knows what you're feeling right now.\nHe knows your struggle.\nTalk to Him. He is listening."
        ),
        IslamicReminder(
            arabic = "يَا أَيُّهَا الَّذِينَ آمَنُوا اسْتَعِينُوا بِالصَّبْرِ وَالصَّلَاةِ",
            translation = "O you who believe, seek help through patience and prayer.",
            reference = "Surah Al-Baqarah 2:153",
            reflection = "Allah gave you two weapons: patience and prayer.\nUse them BOTH right now.\nBe patient with this urge — it will pass.\nAnd pray — even if it's just two rakaat.\nThis is how believers fight."
        ),
        IslamicReminder(
            arabic = "إِنَّ مَعَ الْعُسْرِ يُسْرًا",
            translation = "Indeed, with hardship comes ease.",
            reference = "Surah Ash-Sharh 94:6",
            reflection = "This moment is hard. That's real.\nBut ease is ALREADY on its way.\nAllah didn't say 'after hardship' — He said 'WITH it.'\nThe ease is here, alongside the struggle.\nYou just need to hold on a little longer."
        ),
        IslamicReminder(
            arabic = "وَلَقَدْ خَلَقْنَا الْإِنسَانَ وَنَعْلَمُ مَا تُوَسْوِسُ بِهِ نَفْسُهُ",
            translation = "And We have already created man and know what his soul whispers to him.",
            reference = "Surah Qaf 50:16",
            reflection = "Allah KNOWS the whispers inside you right now.\nHe created you. He understands your weakness.\nHe doesn't expect perfection — He expects EFFORT.\nThe fact that you're here fighting is the effort.\nAllah sees it. And He values it."
        ),
        IslamicReminder(
            arabic = "وَاللَّهُ يُرِيدُ أَن يَتُوبَ عَلَيْكُمْ وَيُرِيدُ الَّذِينَ يَتَّبِعُونَ الشَّهَوَاتِ أَن تَمِيلُوا مَيْلًا عَظِيمًا",
            translation = "Allah wants to accept your repentance, but those who follow desires want you to deviate with great deviation.",
            reference = "Surah An-Nisa 4:27",
            reflection = "There are two forces pulling you right now.\nAllah wants to forgive you and lift you up.\nYour desires want to drag you down.\nChoose which force you follow.\nAllah is on YOUR side."
        ),
        IslamicReminder(
            arabic = "يُرِيدُ اللَّهُ أَن يُخَفِّفَ عَنكُمْ وَخُلِقَ الْإِنسَانُ ضَعِيفًا",
            translation = "Allah wants to lighten your burden, for man was created weak.",
            reference = "Surah An-Nisa 4:28",
            reflection = "Allah KNOWS you are weak. He created you that way.\nThis is not an excuse — it's a REASON for mercy.\nHe doesn't expect you to never struggle.\nHe expects you to turn to Him WHEN you struggle.\nTurn to Him now."
        ),
        IslamicReminder(
            arabic = "وَإِذَا سَأَلَكَ عِبَادِي عَنِّي فَإِنِّي قَرِيبٌ أُجِيبُ دَعْوَةَ الدَّاعِ إِذَا دَعَانِ",
            translation = "And when My servants ask you about Me — indeed I am near. I respond to the call of the caller when he calls upon Me.",
            reference = "Surah Al-Baqarah 2:186",
            reflection = "Call upon Allah RIGHT NOW.\nHe said He is NEAR.\nHe said He RESPONDS.\nYou don't need to be perfect to make dua.\nJust call Him. Ya Allah, help me."
        ),

        // ── SAHIH HADITH ──

        IslamicReminder(
            arabic = "مَنْ تَرَكَ شَيْئًا لِلَّهِ عَوَّضَهُ اللَّهُ خَيْرًا مِنْهُ",
            translation = "Whoever gives up something for the sake of Allah, Allah will replace it with something better.",
            reference = "Musnad Ahmad 22565 — Graded Sahih",
            reflection = "You are about to give up a moment of pleasure.\nBut Allah promised to REPLACE it with something better.\nBetter peace. Better self-respect. Better barakah.\nTrust this trade.\nWhat Allah gives is always better than what you give up."
        ),
        IslamicReminder(
            arabic = "سَبْعَةٌ يُظِلُّهُمُ اللَّهُ فِي ظِلِّهِ يَوْمَ لَا ظِلَّ إِلَّا ظِلُّهُ ... وَرَجُلٌ دَعَتْهُ امْرَأَةٌ ذَاتُ مَنْصِبٍ وَجَمَالٍ فَقَالَ إِنِّي أَخَافُ اللَّهَ",
            translation = "Seven people will be shaded by Allah on a day when there is no shade except His... and a man who is tempted by a woman of beauty and position, and he says: 'I fear Allah.'",
            reference = "Sahih al-Bukhari 1423, Sahih Muslim 1031",
            reflection = "This hadith is about YOU right now.\nYou are being tempted.\nAnd you are choosing to say 'I fear Allah.'\nImagine being under Allah's shade on Judgment Day.\nThat shade is earned in moments like THIS."
        ),
        IslamicReminder(
            arabic = "إِنَّ اللَّهَ يَبْسُطُ يَدَهُ بِاللَّيْلِ لِيَتُوبَ مُسِيءُ النَّهَارِ وَيَبْسُطُ يَدَهُ بِالنَّهَارِ لِيَتُوبَ مُسِيءُ اللَّيْلِ",
            translation = "Allah extends His Hand at night so that the sinner of the day may repent, and He extends His Hand during the day so that the sinner of the night may repent.",
            reference = "Sahih Muslim 2759",
            reflection = "Whether it's day or night right now,\nAllah's Hand is EXTENDED for you.\nHe is waiting for you to turn back.\nNot with anger — with MERCY.\nTake His Hand. Turn back now."
        ),
        IslamicReminder(
            arabic = "احْفَظِ اللَّهَ يَحْفَظْكَ احْفَظِ اللَّهَ تَجِدْهُ تُجَاهَكَ",
            translation = "Be mindful of Allah and He will protect you. Be mindful of Allah and you will find Him before you.",
            reference = "Sunan al-Tirmidhi 2516 — Graded Sahih",
            reflection = "If you protect Allah's boundaries right now,\nAllah will protect YOU.\nProtect your eyes — He'll protect your heart.\nProtect your actions — He'll protect your future.\nThis is a promise from the Prophet ﷺ."
        ),
        IslamicReminder(
            arabic = "الطُّهُورُ شَطْرُ الْإِيمَانِ",
            translation = "Purification is half of faith.",
            reference = "Sahih Muslim 223",
            reflection = "By resisting this urge, you are purifying yourself.\nThat is HALF your faith right there.\nGo make wudu right now.\nFeel the water wash away the whispers.\nPurify your body and your heart will follow."
        ),
        IslamicReminder(
            arabic = "مَا مِنْ عَبْدٍ مُؤْمِنٍ إِلَّا وَلَهُ ذَنْبٌ يَعْتَادُهُ الْفَيْنَةَ بَعْدَ الْفَيْنَةِ أَوْ ذَنْبٌ هُوَ مُقِيمٌ عَلَيْهِ لَا يُفَارِقُهُ حَتَّى يُفَارِقَ الدُّنْيَا إِنَّ الْمُؤْمِنَ خُلِقَ مُفَتَّنًا تَوَّابًا نَسَّاءً إِذَا ذُكِّرَ تَذَكَّرَ",
            translation = "There is no believer except that he has a sin that he commits from time to time... Indeed, the believer was created to be tried, repentant, and forgetful. When he is reminded, he remembers.",
            reference = "al-Mu'jam al-Kabir 11810 — Graded Hasan by al-Albani",
            reflection = "You were CREATED to be tested.\nStumbling is part of being human.\nBut you were also created to REPENT.\nAnd right now, you are being REMINDED.\nSo remember. And turn back."
        ),
        IslamicReminder(
            arabic = "كُلُّ ابْنِ آدَمَ خَطَّاءٌ وَخَيْرُ الْخَطَّائِينَ التَّوَّابُونَ",
            translation = "Every son of Adam sins, and the best of sinners are those who repent.",
            reference = "Sunan al-Tirmidhi 2499 — Graded Hasan",
            reflection = "The Prophet ﷺ said EVERY human sins.\nYou are not uniquely broken.\nWhat makes you special is that you REPENT.\nYou opened this app. That is repentance in action.\nYou are among the BEST of sinners."
        ),
        IslamicReminder(
            arabic = "اتَّقِ اللَّهَ حَيْثُمَا كُنْتَ وَأَتْبِعِ السَّيِّئَةَ الْحَسَنَةَ تَمْحُهَا وَخَالِقِ النَّاسَ بِخُلُقٍ حَسَنٍ",
            translation = "Fear Allah wherever you are, follow a bad deed with a good deed and it will erase it, and behave well toward people.",
            reference = "Sunan al-Tirmidhi 1987 — Graded Hasan",
            reflection = "Even if you've done bad today,\na good deed can ERASE it.\nResisting this urge IS that good deed.\nEvery second you hold on is hasanah.\nLet this moment erase what came before."
        ),
        IslamicReminder(
            arabic = "إِنَّمَا الصَّبْرُ عِنْدَ الصَّدْمَةِ الْأُولَى",
            translation = "Patience is at the first strike of a calamity.",
            reference = "Sahih al-Bukhari 1283, Sahih Muslim 926",
            reflection = "The hardest moment is RIGHT NOW.\nThis first wave of the urge.\nIf you can be patient NOW, it gets easier.\nThe Prophet ﷺ taught us: true patience is at the beginning.\nHold on through this first wave."
        ),
        IslamicReminder(
            arabic = "وَمَا يُلَقَّاهَا إِلَّا الَّذِينَ صَبَرُوا وَمَا يُلَقَّاهَا إِلَّا ذُو حَظٍّ عَظِيمٍ",
            translation = "None will attain it except those who are patient, and none will attain it except one of great fortune.",
            reference = "Surah Fussilat 41:35",
            reflection = "Self-control is for the FORTUNATE.\nNot everyone can do what you're doing right now.\nThe fact that you're fighting makes you special.\nThis is a great fortune that Allah gave you.\nDon't waste it."
        ),
        IslamicReminder(
            arabic = "لَا يَزْنِي الزَّانِي حِينَ يَزْنِي وَهُوَ مُؤْمِنٌ",
            translation = "The adulterer does not commit adultery while he is a believer at the time of the act.",
            reference = "Sahih al-Bukhari 2475, Sahih Muslim 57",
            reflection = "At the moment of sin, iman LEAVES the person.\nIt hovers above like a cloud, waiting to return.\nRight now your iman is WITH you.\nDon't let it leave.\nHold onto your faith in this very moment."
        ),
        IslamicReminder(
            arabic = "إِنَّ اللَّهَ تَعَالَى يَغَارُ وَغَيْرَةُ اللَّهِ أَنْ يَأْتِيَ الْمُؤْمِنُ مَا حَرَّمَ اللَّهُ",
            translation = "Allah has a sense of ghayrah (protective jealousy), and Allah's ghayrah is provocation when a believer does what Allah has forbidden.",
            reference = "Sahih al-Bukhari 5223, Sahih Muslim 2761",
            reflection = "Allah has ghayrah — protective honor — over you.\nLike a father protects his child from harm.\nHe forbade this to PROTECT you, not to restrict you.\nWhen you cross the line, it hurts HIS honor.\nRespect His protection. It's there because He loves you."
        ),

        // ── SALAF QUOTES ──

        IslamicReminder(
            arabic = "مَنْ عَرَفَ نَفْسَهُ اشْتَغَلَ بِإِصْلَاحِهَا عَنْ عُيُوبِ النَّاسِ",
            translation = "Whoever knows himself will be busy fixing himself rather than looking at the faults of others.",
            reference = "Ibn al-Qayyim — al-Fawa'id",
            reflection = "You are here fixing yourself.\nThat takes more courage than anything.\nMost people ignore their flaws.\nYou are facing yours head-on.\nIbn al-Qayyim would be proud of you."
        ),
        IslamicReminder(
            arabic = "لَذَّةُ الْمَعْصِيَةِ سَاعَةٌ وَعُقُوبَتُهَا أَيَّامٌ",
            translation = "The pleasure of sin is for an hour, but its punishment lasts for days.",
            reference = "Ibn al-Qayyim — al-Jawab al-Kafi",
            reflection = "Think about it honestly.\nHow long does the pleasure last? Seconds.\nHow long does the guilt last? Days.\nThe math doesn't add up.\nDon't trade days of peace for seconds of nothing."
        ),
        IslamicReminder(
            arabic = "إِذَا سَاءَتْكَ سَيِّئَةٌ فَسَرَّتْكَ حَسَنَةٌ فَأَنْتَ مُؤْمِنٌ",
            translation = "If your bad deeds make you sad and your good deeds make you happy, then you are a believer.",
            reference = "Musnad Ahmad 22166 — Graded Sahih by al-Albani",
            reflection = "The fact that this sin bothers you\nis PROOF that you are a believer.\nA hypocrite wouldn't care.\nYour guilt is actually a sign of FAITH.\nUse that faith to resist right now."
        ),
        IslamicReminder(
            arabic = "الدُّنْيَا سَاعَةٌ فَاجْعَلْهَا طَاعَةً",
            translation = "This life is but a moment, so make it a moment of obedience.",
            reference = "Attributed to Imam al-Hasan al-Basri",
            reflection = "This urge feels like everything right now.\nBut life is SHORT.\nThis moment will pass.\nWill you fill it with obedience or regret?\nMake this moment count."
        ),
        IslamicReminder(
            arabic = "لَا تَنْظُرْ إِلَى صِغَرِ الْمَعْصِيَةِ وَلَكِنِ انْظُرْ إِلَى عَظَمَةِ مَنْ تَعْصِيهِ",
            translation = "Do not look at the smallness of the sin, but look at the greatness of the One you are sinning against.",
            reference = "Attributed to Bilal ibn Sa'd — quoted by Ibn al-Qayyim",
            reflection = "Shaytan tells you 'it's just a small thing.'\nBut look at WHO you would be disobeying.\nThe Creator of the heavens and earth.\nThe One who gave you everything.\nIs this how you repay Him?"
        ),
        IslamicReminder(
            arabic = "مَا تَرَكَ عَبْدٌ شَيْئًا لِلَّهِ إِلَّا عَوَّضَهُ اللَّهُ فِي دِينِهِ وَدُنْيَاهُ مِنْ حَيْثُ لَا يَحْتَسِبُ",
            translation = "No servant leaves something for the sake of Allah except that Allah replaces it in his religion and his worldly life from where he does not expect.",
            reference = "Ibn al-Qayyim — al-Fawa'id",
            reflection = "Leave this sin for Allah.\nHe will replace it with something you can't even imagine.\nBetter sleep. Better focus. Better relationships.\nAllah's replacements come from unexpected places.\nTrust the trade."
        ),
        IslamicReminder(
            arabic = "غُضُّوا أَبْصَارَكُمْ وَاحْفَظُوا فُرُوجَكُمْ",
            translation = "Lower your gazes and guard your private parts.",
            reference = "Prophet Muhammad ﷺ — Sahih al-Bukhari 6229",
            reflection = "The Prophet ﷺ gave you a direct command.\nLower your gaze NOW.\nClose the tab. Lock the phone. Look away.\nThe gaze is where it all starts.\nCut it off at the source."
        ),
        IslamicReminder(
            arabic = "إِنَّ الْعَبْدَ إِذَا أَذْنَبَ ذَنْبًا نُكِتَتْ فِي قَلْبِهِ نُكْتَةٌ سَوْدَاءُ فَإِنْ هُوَ نَزَعَ وَاسْتَغْفَرَ وَتَابَ صُقِلَ قَلْبُهُ",
            translation = "When a servant commits a sin, a black spot appears on his heart. If he repents and seeks forgiveness, his heart is polished clean.",
            reference = "Sunan al-Tirmidhi 3334 — Graded Hasan",
            reflection = "Every sin leaves a mark on your heart.\nBut every repentance POLISHES it clean.\nYour heart can be clean again.\nIt starts with resisting this one urge.\nPolish your heart right now."
        ),
        IslamicReminder(
            arabic = "مَنِ اسْتَطَاعَ مِنْكُمُ الْبَاءَةَ فَلْيَتَزَوَّجْ فَإِنَّهُ أَغَضُّ لِلْبَصَرِ وَأَحْصَنُ لِلْفَرْجِ وَمَنْ لَمْ يَسْتَطِعْ فَعَلَيْهِ بِالصَّوْمِ فَإِنَّهُ لَهُ وِجَاءٌ",
            translation = "Whoever among you can afford to marry, let him do so. And whoever cannot, let him fast, for it will be a shield for him.",
            reference = "Sahih al-Bukhari 5066, Sahih Muslim 1400",
            reflection = "The Prophet ﷺ acknowledged this struggle is real.\nHe didn't say 'just don't feel it.'\nHe gave practical solutions: marriage or fasting.\nIf you can't marry yet, try fasting tomorrow.\nIt weakens the desires and strengthens the soul."
        ),
        IslamicReminder(
            arabic = "مَنْ غَضَّ بَصَرَهُ عَنْ مَحَارِمِ اللَّهِ أَوْرَثَهُ اللَّهُ حَلَاوَةً فِي قَلْبِهِ إِلَى يَوْمِ يَلْقَاهُ",
            translation = "Whoever lowers his gaze from what Allah has forbidden, Allah will grant him a sweetness of faith in his heart until the Day he meets Him.",
            reference = "Ibn al-Qayyim — al-Jawab al-Kafi, from the understanding of the Salaf",
            reflection = "There is a SWEETNESS that comes from lowering your gaze.\nA taste of iman that nothing else can give.\nPeople who resist feel it — a lightness in the chest.\nA peace that haram can never provide.\nChoose the sweetness of faith over the bitterness of sin."
        )
    )

    // ========================
    // VICTORY AYAHS
    // Shown on the Victory screen after defeating an urge
    // ========================
    val victoryAyahs = listOf(
        IslamicReminder(
            arabic = "وَأَمَّا مَنْ خَافَ مَقَامَ رَبِّهِ وَنَهَى النَّفْسَ عَنِ الْهَوَىٰ فَإِنَّ الْجَنَّةَ هِيَ الْمَأْوَىٰ",
            translation = "But as for he who feared standing before his Lord and restrained the soul from desire — then indeed, Paradise will be his refuge.",
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
        ),
        IslamicReminder(
            arabic = "قَدْ أَفْلَحَ الْمُؤْمِنُونَ الَّذِينَ هُمْ فِي صَلَاتِهِمْ خَاشِعُونَ وَالَّذِينَ هُمْ عَنِ اللَّغْوِ مُعْرِضُونَ",
            translation = "Successful indeed are the believers — those who are humble in their prayers, and those who turn away from vain talk.",
            reference = "Surah Al-Mu'minun 23:1-3",
            reflection = ""
        ),
        IslamicReminder(
            arabic = "وَالْكَاظِمِينَ الْغَيْظَ وَالْعَافِينَ عَنِ النَّاسِ وَاللَّهُ يُحِبُّ الْمُحْسِنِينَ",
            translation = "Those who restrain anger and pardon people — and Allah loves the doers of good.",
            reference = "Surah Aal-Imran 3:134",
            reflection = ""
        ),
        IslamicReminder(
            arabic = "وَلِمَنْ خَافَ مَقَامَ رَبِّهِ جَنَّتَانِ",
            translation = "But for he who has feared standing before his Lord, there will be two gardens.",
            reference = "Surah Ar-Rahman 55:46",
            reflection = ""
        ),
        IslamicReminder(
            arabic = "إِنَّ الَّذِينَ اتَّقَوْا إِذَا مَسَّهُمْ طَائِفٌ مِّنَ الشَّيْطَانِ تَذَكَّرُوا فَإِذَا هُم مُّبْصِرُونَ",
            translation = "Indeed, those who fear Allah — when a passing suggestion from Shaytan touches them, they remember Allah and immediately they have insight.",
            reference = "Surah Al-A'raf 7:201",
            reflection = ""
        ),
        IslamicReminder(
            arabic = "وَمَن يَتَّقِ اللَّهَ يُكَفِّرْ عَنْهُ سَيِّئَاتِهِ وَيُعْظِمْ لَهُ أَجْرًا",
            translation = "And whoever fears Allah — He will remove for him his misdeeds and make great for him his reward.",
            reference = "Surah At-Talaq 65:5",
            reflection = ""
        ),
        IslamicReminder(
            arabic = "وَسَارِعُوا إِلَىٰ مَغْفِرَةٍ مِّن رَّبِّكُمْ وَجَنَّةٍ عَرْضُهَا السَّمَاوَاتُ وَالْأَرْضُ أُعِدَّتْ لِلْمُتَّقِينَ",
            translation = "And hasten to forgiveness from your Lord and a garden as wide as the heavens and earth, prepared for the righteous.",
            reference = "Surah Aal-Imran 3:133",
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