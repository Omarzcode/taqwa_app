package com.taqwa.journal.data.knowledge

object MotivationArticles {
    fun getAll(): List<KnowledgeArticle> = listOf(
        article1WhatYouGain(),
        article2RewardOfPatience(),
        article3LetterToYourself(),
        article4YouAreNotAlone()
    )

    // ═══════════════════════════════════════════════════════════
    // ARTICLE 1: What You Gain by Quitting
    // ═══════════════════════════════════════════════════════════
    private fun article1WhatYouGain() = KnowledgeArticle(
        id = "motivation_1",
        categoryId = "motivation",
        title = "What You Gain by Quitting",
        summary = "The incredible benefits waiting for you on the other side — mental, physical, spiritual, and social transformation",
        readTimeMinutes = 7,
        isArabic = false,
        references = listOf(
            "Wilson, G. (2014). Your Brain on Porn. Commonwealth Publishing.",
            "Park, B.Y., et al. (2016). Is Internet Pornography Causing Sexual Dysfunctions? Behavioral Sciences, 6(3), 17.",
            "NoFap community surveys, 2019-2023 (self-reported benefits aggregated from 10,000+ participants)"
        ),
        content = listOf(
            ContentBlock.Header("The Other Side is Beautiful"),
            ContentBlock.Paragraph(
                "It's easy to focus on what you're giving up. The sacrifice. The struggle. The discomfort. But let's talk about what you GAIN when you break free. The benefits are so profound that men who have recovered consistently say quitting pornography was the single best decision of their lives."
            ),
            ContentBlock.Header("Mental Benefits"),
            ContentBlock.BulletPoints(
                title = "",
                points = listOf(
                    "Mental clarity — the brain fog lifts completely, like putting on glasses for the first time",
                    "Sharper memory — you can retain information and recall it easily",
                    "Improved concentration — you can focus on a task for hours without drifting",
                    "Reduced anxiety — the constant background hum of guilt disappears",
                    "Emotional stability — no more wild mood swings from dopamine crashes",
                    "Increased creativity — your brain has bandwidth for ideas again",
                    "Better academic and work performance — the cognitive tax is lifted"
                )
            ),
            ContentBlock.Header("Physical Benefits"),
            ContentBlock.BulletPoints(
                title = "",
                points = listOf(
                    "More physical energy — you wake up ready to move",
                    "Better sleep — deeper, more restful, more restorative",
                    "Clearer skin and brighter eyes — people notice the difference",
                    "Improved physical fitness — more motivation to exercise",
                    "Natural confidence in body language — you stand taller, speak clearer",
                    "Sexual health normalizes — real attraction patterns return",
                    "Stronger immune system — fewer illnesses, faster recovery"
                )
            ),
            ContentBlock.Header("Spiritual Benefits"),
            ContentBlock.BulletPoints(
                title = "",
                points = listOf(
                    "Khushu in prayer returns — you can actually feel your salah",
                    "Quran affects your heart — you can cry during recitation again",
                    "Du'a feels connected — you can face Allah without shame",
                    "Love for the masjid returns — you want to be in Allah's house",
                    "Ramadan becomes transformative — not torturous",
                    "You feel the sweetness of iman described in the hadith",
                    "Night prayer becomes possible — and beautiful",
                    "Barakah returns to your time, money, and relationships"
                )
            ),
            ContentBlock.Header("Social Benefits"),
            ContentBlock.BulletPoints(
                title = "",
                points = listOf(
                    "Confidence in social situations — no more avoiding eye contact",
                    "Genuine respect for women — seeing souls, not objects",
                    "Deeper friendships — you're no longer hiding a secret life",
                    "Better family relationships — you're fully present",
                    "Integrity — your public self and private self become one person",
                    "Natural charisma — people are drawn to authenticity and inner peace",
                    "Readiness for marriage — you can offer your best self to your future spouse"
                )
            ),
            ContentBlock.QuranVerse(
                arabic = "مَنْ عَمِلَ صَالِحًا مِّن ذَكَرٍ أَوْ أُنثَىٰ وَهُوَ مُؤْمِنٌ فَلَنُحْيِيَنَّهُ حَيَاةً طَيِّبَةً",
                translation = "Whoever does righteousness, whether male or female, while being a believer — We will surely cause them to live a good life.",
                reference = "Surah An-Nahl 16:97"
            ),
            ContentBlock.Paragraph(
                "حياة طيّبة — a good life. Not a perfect life, but a GOOD life. A life of clarity, peace, purpose, and connection. This is what Allah promises those who choose righteousness. And it begins the moment you choose to stop."
            ),
            ContentBlock.Tip(
                "Save this article. On your hardest days — when the urge is screaming and your willpower is crumbling — come back and read this list. Remember what you're fighting FOR, not just what you're fighting against. The prize is worth every moment of struggle."
            )
        )
    )

    // ═══════════════════════════════════════════════════════════
    // ARTICLE 2: The Reward of Patience (Arabic)
    // ═══════════════════════════════════════════════════════════
    private fun article2RewardOfPatience() = KnowledgeArticle(
        id = "motivation_2",
        categoryId = "motivation",
        title = "The Reward of Patience",
        titleAr = "ثمرات الصبر: ما أعدّه الله للصابرين",
        summary = "What Allah has promised those who endure",
        summaryAr = "ما وعد الله به الصابرين من الأجر العظيم الذي لا حدود له",
        readTimeMinutes = 7,
        isArabic = true,
        references = listOf(
            "ابن القيم، عدّة الصابرين وذخيرة الشاكرين، ص 40-65",
            "السعدي، تيسير الكريم الرحمن، تفسير آيات الصبر",
            "صحيح مسلم (2999)",
            "صحيح البخاري (5641)"
        ),
        content = listOf(
            ContentBlock.Header("الصبر: أعلى مقامات الإيمان"),
            ContentBlock.Paragraph(
                "ما تمرّ به من مجاهدة لهذا الإدمان — الألم، والحرمان، والصراع الداخلي — كلّ هذا عند الله صبرٌ مأجور. وليس مجرّد صبر عادي، بل هو من أعظم أنواع الصبر: الصبر عن المعصية."
            ),
            ContentBlock.ScholarQuote(
                text = "الصبر ثلاثة أنواع: صبر على الطاعة، وصبر عن المعصية، وصبر على أقدار الله المؤلمة. وأعلاها وأشدّها: الصبر عن المعصية؛ لأنّ داعي الشهوة قائم، والنفس مشتاقة، والفرصة متاحة، ولا رقيب من الخلق — فيصبر لله. هذا هو الصدّيق حقًا.",
                scholar = "ابن القيم الجوزية",
                source = "عدّة الصابرين وذخيرة الشاكرين، ص 45"
            ),
            ContentBlock.Header("أجر الصبر بغير حساب"),
            ContentBlock.QuranVerse(
                arabic = "إِنَّمَا يُوَفَّى الصَّابِرُونَ أَجْرَهُم بِغَيْرِ حِسَابٍ",
                translation = "Indeed, the patient will be given their reward without account.",
                reference = "سورة الزمر: 10"
            ),
            ContentBlock.Paragraph(
                "بغير حساب! كلّ الأعمال لها أجر محدّد — الحسنة بعشر أمثالها إلى سبعمائة ضعف. لكنّ الصبر استثناه الله من هذا الحساب وجعل أجره بلا حدود. تأمّل: كلّ لحظة تصبر فيها عن النظر إلى الحرام، أجرها عند الله بغير حساب."
            ),
            ContentBlock.Header("الله مع الصابرين"),
            ContentBlock.QuranVerse(
                arabic = "وَاصْبِرُوا ۚ إِنَّ اللَّهَ مَعَ الصَّابِرِينَ",
                translation = "And be patient. Indeed, Allah is with the patient.",
                reference = "سورة الأنفال: 46"
            ),
            ContentBlock.Paragraph(
                "معيّة الله الخاصّة — ليست المعيّة العامّة التي تشمل كلّ الخلق، بل معيّة النصرة والتأييد والحفظ. عندما تصبر عن المعصية، الله معك — يحفظك ويُعينك ويُسدّدك."
            ),
            ContentBlock.Hadith(
                text = "عَجَبًا لأمرِ المؤمنِ، إنَّ أمرَه كلَّه خيرٌ، وليسَ ذاكَ لأحدٍ إلَّا للمؤمنِ؛ إنْ أصابَتْه سرَّاءُ شكرَ فكانَ خيرًا لَه، وإنْ أصابَتْه ضرَّاءُ صبرَ فكانَ خيرًا لَه.",
                narrator = "صحيح مسلم (2999)",
                grading = "صحيح"
            ),
            ContentBlock.Header("لحظة الصبر تُساوي الدنيا كلّها"),
            ContentBlock.Paragraph(
                "تخيّل اللحظة: أنت وحدك، الشاشة أمامك، لا أحد يراك من البشر، الشهوة تشتعل، كلّ شيء يدفعك نحو المعصية. ثمّ تقول: لا. أنا أخاف الله. وتُغلق الشاشة. هذه اللحظة — هذه اللحظة بالذات — أعظم عند الله من كثير من العبادات. لأنّك تركتَ المعصية لا خوفًا من الناس، بل خوفًا من الله وحده."
            ),
            ContentBlock.ScholarQuote(
                text = "أفضل الصبر: الصبر عن المحارم في الخلوة مع القدرة عليها. فإنّ هذا يدلّ على صدق الإيمان وقوّة المراقبة لله. وصاحب هذا المقام من السبعة الذين يُظلّهم الله في ظلّه يوم لا ظلّ إلّا ظلّه.",
                scholar = "ابن القيم الجوزية",
                source = "عدّة الصابرين، ص 52"
            ),
            ContentBlock.Hadith(
                text = "سبعةٌ يُظلُّهمُ اللهُ في ظلِّه يومَ لا ظلَّ إلَّا ظلُّه... ورجلٌ دعتْه امرأةٌ ذاتُ منصبٍ وجمالٍ فقال: إنِّي أخافُ اللهَ.",
                narrator = "صحيح البخاري (5641)",
                grading = "متفق عليه"
            ),
            ContentBlock.Paragraph(
                "الرجل الذي دعته امرأة ذات منصب وجمال — الفرصة متاحة، والشهوة قائمة، والخلوة حاصلة — لكنّه قال كلمة واحدة غيّرت مصيره الأبدي: «إنّي أخاف الله». جزاؤه: ظلّ الله يوم القيامة حين تدنو الشمس من الرؤوس. هذه منزلتك عندما تقول «لا» لله في خلوتك."
            ),
            ContentBlock.Header("ثمرات الصبر في الدنيا"),
            ContentBlock.BulletPoints(
                title = "",
                points = listOf(
                    "صفاء الذهن وقوّة التركيز — عقلك يعود إلى طبيعته",
                    "طمأنينة القلب — لا أرق، لا قلق، لا خوف من الفضيحة",
                    "حلاوة الإيمان — ستشعر بلذّة الصلاة والقرآن من جديد",
                    "هيبة ووقار — الناس يلاحظون التغيير حتّى لو لم يعرفوا سببه",
                    "بركة في الوقت والرزق والعلاقات",
                    "راحة الضمير — لا حياة مزدوجة، لا أسرار مُخزية",
                    "استعداد حقيقي للزواج — يمكنك أن تُقدّم أفضل نسخة من نفسك"
                )
            ),
            ContentBlock.QuranVerse(
                arabic = "فَاصْبِرْ إِنَّ الْعَاقِبَةَ لِلْمُتَّقِينَ",
                translation = "So be patient. Indeed, the best outcome is for the righteous.",
                reference = "سورة هود: 49"
            ),
            ContentBlock.Tip(
                "في كلّ مرّة تصبر فيها عن النظر إلى الحرام، قل في قلبك: «هذا لله. والله لا يُضيع أجر من أحسن عملًا». ثمّ ابتسم — لأنّك في تلك اللحظة من أولياء الله الذين يُظلّهم في ظلّه."
            )
        )
    )

    // ═══════════════════════════════════════════════════════════
    // ARTICLE 3: Letter to Yourself
    // ═══════════════════════════════════════════════════════════
    private fun article3LetterToYourself() = KnowledgeArticle(
        id = "motivation_3",
        categoryId = "motivation",
        title = "A Letter to Your Future Self",
        summary = "Read this when you're about to give in — a message from your strongest moment to your weakest",
        readTimeMinutes = 5,
        isArabic = false,
        references = listOf(),
        content = listOf(
            ContentBlock.Header("Read This When You're About to Give In"),
            ContentBlock.Paragraph(
                "You're reading this because the urge is strong right now. Maybe the strongest it's been in a while. Your brain is screaming for relief. Shaytan is whispering that one time won't hurt. Everything in you wants to give in."
            ),
            ContentBlock.Paragraph(
                "But you opened this app instead. You navigated to this article instead. That means part of you — the REAL you — is still fighting. And that part is stronger than you think."
            ),
            ContentBlock.Header("Remember Who You Were After the Last Time"),
            ContentBlock.Paragraph(
                "Remember how you felt the last time you gave in. Not during — AFTER. The crushing guilt. The emptiness. The disgust with yourself. The dead prayer. The inability to look at yourself in the mirror. The promise you made that 'this was the last time' — again."
            ),
            ContentBlock.Paragraph(
                "Now ask yourself: Do you want to feel that way again in 10 minutes? Because that's exactly what's waiting on the other side of this urge. A few seconds of empty pleasure followed by hours, maybe days, of spiritual darkness."
            ),
            ContentBlock.Header("This Urge Will Pass"),
            ContentBlock.Paragraph(
                "The urge you feel right now is a wave. It will rise, peak, and fall. It ALWAYS does. No urge lasts forever. Most peak within 15-20 minutes and then begin to weaken. You don't need to fight it for the rest of your life — you just need to outlast this one wave."
            ),
            ContentBlock.BulletPoints(
                title = "What to do RIGHT NOW:",
                points = listOf(
                    "Put your phone face-down on a table",
                    "Stand up and leave the room you're in",
                    "Make wudu — let the water cool the fire",
                    "Do 20 push-ups — redirect the physical energy",
                    "Call someone — anyone — and talk about anything",
                    "Read Surah An-Nas out loud — it was revealed for exactly this moment",
                    "Look at the sky and say: 'Ya Allah, I choose You over this'"
                )
            ),
            ContentBlock.Header("Think About Tomorrow Morning"),
            ContentBlock.Paragraph(
                "Tomorrow morning, you will wake up. If you resist now, you will wake up with a clean streak, a clear conscience, and the knowledge that you won a battle. You will pray Fajr with a light heart. You will look at yourself with respect."
            ),
            ContentBlock.Paragraph(
                "If you give in, you will wake up with regret. Your streak will be gone. Your prayers will feel hollow. You will start the day in a pit of shame, already planning 'when to start over.'"
            ),
            ContentBlock.Paragraph(
                "Which tomorrow do you want? You get to choose. Right now. In this moment."
            ),
            ContentBlock.Header("You Are Stronger Than This"),
            ContentBlock.Paragraph(
                "You have survived every urge before this one. Every single one. Your success rate at surviving urges is 100% — because you're still here, still fighting, still reading this."
            ),
            ContentBlock.Paragraph(
                "This urge is not special. It is not stronger than the others. It just feels that way because it's happening NOW. But it will pass. They all pass."
            ),
            ContentBlock.QuranVerse(
                arabic = "لَا يُكَلِّفُ اللَّهُ نَفْسًا إِلَّا وُسْعَهَا",
                translation = "Allah does not burden a soul beyond that it can bear.",
                reference = "Surah Al-Baqarah 2:286"
            ),
            ContentBlock.Paragraph(
                "Allah is telling you: you CAN bear this. He would not have allowed this test if you could not pass it. The fact that this urge is in front of you right now means Allah KNOWS you have the strength to overcome it. Trust His knowledge of you."
            ),
            ContentBlock.Warning(
                "Close this app and do something else RIGHT NOW. Don't sit and debate with the urge. Don't negotiate. Don't peek 'just a little.' Stand up. Move your body. Change your environment. The urge feeds on stillness and isolation. Starve it."
            ),
            ContentBlock.Tip(
                "You will thank yourself tomorrow. You will wake up proud. You will pray with a light heart. You will look in the mirror and see someone who fought — and won. Choose that person. Choose him right now."
            )
        )
    )

    // ═══════════════════════════════════════════════════════════
    // ARTICLE 4: You Are Not Alone
    // ═══════════════════════════════════════════════════════════
    private fun article4YouAreNotAlone() = KnowledgeArticle(
        id = "motivation_4",
        categoryId = "motivation",
        title = "You Are Not Alone",
        titleAr = "لستَ وحدك في هذه المعركة",
        summary = "Millions are fighting the same battle — and many have won",
        summaryAr = "الملايين يخوضون نفس المعركة — والكثيرون انتصروا",
        readTimeMinutes = 6,
        isArabic = false,
        references = listOf(
            "Barna Group (2016). The Porn Phenomenon: The Impact of Pornography in the Digital Age.",
            "Fight the New Drug (2023). Pornography Statistics: 250+ facts, quotes, and statistics.",
            "صحيح مسلم (2699)"
        ),
        content = listOf(
            ContentBlock.Header("The Lie of Isolation"),
            ContentBlock.Paragraph(
                "One of Shaytan's most effective weapons is making you believe you're the only one struggling. That everyone else has this figured out. That you're uniquely broken, uniquely sinful, uniquely beyond help. This is a LIE."
            ),
            ContentBlock.Paragraph(
                "The reality: this is one of the most widespread struggles of our generation. Millions of Muslims — practicing, praying, Quran-reading Muslims — are fighting this exact same battle right now. Your imam might be fighting it. Your teacher might be fighting it. The brother who leads the halaqah might be fighting it. This addiction does not discriminate."
            ),
            ContentBlock.ScientificFact(
                "Research indicates that approximately 70-80% of men aged 18-30 have consumed pornography regularly. Among religious communities, the rate of struggle (and the associated guilt) is equally high, though less discussed due to stigma.",
                "Barna Group (2016) — The Porn Phenomenon"
            ),
            ContentBlock.Header("Why Silence Makes It Worse"),
            ContentBlock.Paragraph(
                "The stigma around this topic in Muslim communities creates a devastating paradox: the more shameful it feels to talk about, the more isolated the addict becomes, and isolation is the #1 fuel for addiction. The cycle of silence feeds the cycle of sin."
            ),
            ContentBlock.BulletPoints(
                title = "The silence trap:",
                points = listOf(
                    "You sin in secret → shame prevents you from seeking help",
                    "Isolation deepens → no accountability, no support",
                    "Loneliness increases → you use pornography to cope with loneliness",
                    "The addiction strengthens → shame deepens further",
                    "You feel like a hypocrite → you distance from the masjid and community",
                    "More isolation → more vulnerability → more sin"
                )
            ),
            ContentBlock.Header("Breaking the Silence"),
            ContentBlock.Paragraph(
                "You don't need to announce your struggle to the world. Islam teaches us to conceal our sins. But you DO need at least one person — one trusted, wise, compassionate person — who knows what you're going through."
            ),
            ContentBlock.BulletPoints(
                title = "Who to consider telling:",
                points = listOf(
                    "A trusted friend who fears Allah — not one who will joke about it",
                    "An older mentor or scholar who understands addiction — many now do",
                    "A professional therapist, ideally Muslim, who specializes in this area",
                    "A trusted family member — a brother, a cousin, someone you respect",
                    "An online accountability partner from a recovery community"
                )
            ),
            ContentBlock.Hadith(
                text = "مَنْ نَفَّسَ عَنْ مُؤْمِنٍ كُرْبَةً مِنْ كُرَبِ الدُّنْيَا نَفَّسَ اللهُ عَنْهُ كُرْبَةً مِنْ كُرَبِ يَوْمِ القِيَامَةِ، وَمَنْ يَسَّرَ عَلَى مُعْسِرٍ يَسَّرَ اللهُ عَلَيْهِ فِي الدُّنْيَا وَالآخِرَةِ، وَمَنْ سَتَرَ مُسْلِمًا سَتَرَهُ اللهُ فِي الدُّنْيَا وَالآخِرَةِ، وَاللهُ فِي عَوْنِ العَبْدِ مَا كَانَ العَبْدُ فِي عَوْنِ أَخِيهِ.",
                narrator = "صحيح مسلم (2699)",
                grading = "صحيح"
            ),
            ContentBlock.Header("To Those Who Have Already Won"),
            ContentBlock.Paragraph(
                "Know that recovery IS possible. Many Muslim men who were deeply addicted for 5, 10, even 15+ years have broken free completely. They now pray with khushu. They have healthy marriages. They have reclaimed their dignity, their faith, and their futures."
            ),
            ContentBlock.Paragraph(
                "Their secret? They didn't do it alone. They combined tawbah with knowledge, self-awareness with accountability, spiritual practice with practical strategies. And they never, ever gave up — even when they relapsed dozens of times."
            ),
            ContentBlock.Header("Your Message to Read Every Day"),
            ContentBlock.Paragraph(
                "You are not your addiction. You are a believer who has been tested. You are not defined by your worst moments — you are defined by your refusal to give up. You are not weak because you struggle — you are strong because you keep fighting."
            ),
            ContentBlock.Paragraph(
                "Allah chose to test you with this — which means He knows you can overcome it. He does not burden a soul beyond its capacity. The very fact that you are reading this, right now, is proof that you have not given up. And as long as you don't give up, you cannot lose."
            ),
            ContentBlock.QuranVerse(
                arabic = "وَلَا تَهِنُوا وَلَا تَحْزَنُوا وَأَنتُمُ الْأَعْلَوْنَ إِن كُنتُم مُّؤْمِنِينَ",
                translation = "So do not weaken and do not grieve, and you will be superior if you are believers.",
                reference = "سورة آل عمران: 139"
            ),
            ContentBlock.Tip(
                "You made it to the end of this article. That alone shows your commitment. Now close this app, make wudu, pray two rakah, and tell Allah: 'I'm still here. I'm still fighting. And I will never stop.' He is listening. He is closer than you think."
            )
        )
    )
}