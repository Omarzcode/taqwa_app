package com.taqwa.journal.data.knowledge

object HarmsArticles {
    fun getAll(): List<KnowledgeArticle> = listOf(
        article1MentalHealth(),
        article2SpiritualDisconnection(),
        article3RelationshipDamage(),
        article4PhysicalHarms()
    )

    // ═══════════════════════════════════════════════════════════
    // ARTICLE 1: Mental Health Destruction (English)
    // ═══════════════════════════════════════════════════════════
    private fun article1MentalHealth() = KnowledgeArticle(
        id = "harms_1",
        categoryId = "harms",
        title = "Mental Health Destruction",
        summary = "Anxiety, depression, brain fog, shame spirals — the hidden mental health crisis caused by pornography",
        readTimeMinutes = 7,
        isArabic = false,
        references = listOf(
            "Mattebo, M., et al. (2018). Pornography consumption and psychosomatic and depressive symptoms among Swedish adolescents. Journal of Sex Research, 55(4-5), 522-531.",
            "de Alarcón, R., et al. (2019). Online Porn Addiction: What We Know and What We Don't. Journal of Clinical Medicine, 8(1), 91.",
            "Lam, L.T. & Peng, Z.W. (2010). Effect of pathological use of the internet on adolescent mental health. Archives of Disease in Childhood, 95(3), 138-142.",
            "Perry, S.L. (2018). Pornography use and depressive symptoms: Examining the role of moral incongruence. Society and Mental Health, 8(3), 195-213."
        ),
        content = listOf(
            ContentBlock.Header("The Hidden Mental Health Crisis"),
            ContentBlock.Paragraph(
                "Most people who struggle with pornography focus on the moral dimension — the guilt, the sin, the spiritual consequences. But pornography is simultaneously waging a silent war on your mental health. Users report significantly higher rates of depression, anxiety, social withdrawal, and cognitive impairment — often without connecting these symptoms to their consumption."
            ),
            ContentBlock.ScientificFact(
                "A comprehensive study of 1,471 adolescents found that pornography consumption was significantly associated with higher levels of depression, anxiety, and stress, with a clear dose-response relationship — the more frequently consumed, the worse the symptoms.",
                "Mattebo et al., 2018 — Journal of Sex Research"
            ),
            ContentBlock.Header("The Anxiety-Addiction Cycle"),
            ContentBlock.Paragraph(
                "Pornography creates one of the most vicious anxiety cycles in behavioral addiction:"
            ),
            ContentBlock.BulletPoints(
                title = "The Cycle:",
                points = listOf(
                    "1. You feel stressed, anxious, or emotionally uncomfortable",
                    "2. You use pornography to escape — it provides temporary dopamine relief",
                    "3. Dopamine crashes below baseline after use — anxiety returns WORSE than before",
                    "4. Guilt and shame compound the anxiety",
                    "5. You now have more emotional pain than when you started",
                    "6. You use pornography again to numb the increased pain",
                    "7. Each cycle deepens the depression and strengthens the addiction"
                )
            ),
            ContentBlock.Paragraph(
                "This is not a cycle you can moderate your way out of. Each round makes the next one worse. The only exit is to break the cycle entirely."
            ),
            ContentBlock.Header("Depression: The Dopamine Desert"),
            ContentBlock.Paragraph(
                "Chronic pornography use depletes your brain's dopamine system. When your reward circuit is constantly overstimulated, it downregulates — producing less dopamine and reducing the number of dopamine receptors. The result is that normal life feels flat, grey, and meaningless."
            ),
            ContentBlock.BulletPoints(
                title = "Signs of dopamine depletion:",
                points = listOf(
                    "Persistent low mood with no clear external cause",
                    "Loss of interest in hobbies, goals, and activities you once enjoyed",
                    "Difficulty feeling pleasure from food, nature, music, or social connection",
                    "Chronic fatigue and low energy despite adequate sleep",
                    "Feeling that life is meaningless or purposeless",
                    "Emotional numbness — inability to feel deeply",
                    "Lack of motivation to pursue goals or improve yourself"
                )
            ),
            ContentBlock.ScientificFact(
                "Perry (2018) found that moral incongruence — the gap between a person's values and their behavior — is a significant predictor of depressive symptoms in pornography users. For Muslims, this gap is particularly large, making the depressive effect potentially more severe.",
                "Perry, S.L. (2018) — Society and Mental Health"
            ),
            ContentBlock.Header("Brain Fog: The Cognitive Tax"),
            ContentBlock.Paragraph(
                "If you've noticed your memory getting worse, your concentration weakening, your ability to study or work declining — pornography may be the cause. Chronic overstimulation of the reward circuit impairs the prefrontal cortex, which is responsible for focus, working memory, and cognitive clarity."
            ),
            ContentBlock.BulletPoints(
                title = "Cognitive symptoms reported by users:",
                points = listOf(
                    "Difficulty concentrating on tasks for extended periods",
                    "Forgetfulness and poor short-term memory",
                    "Difficulty reading long texts (ironic, given what you're doing right now — keep going!)",
                    "Procrastination and inability to start tasks",
                    "Mental 'cloudiness' that lifts temporarily after abstinence",
                    "Decreased academic or work performance",
                    "Difficulty following conversations or retaining new information"
                )
            ),
            ContentBlock.Header("The Shame Spiral"),
            ContentBlock.Paragraph(
                "Perhaps the most destructive mental health consequence is the shame spiral. You commit the sin, feel crushing shame, the shame creates emotional pain, and you return to the addiction to numb that pain. The very emotion designed to protect you becomes fuel for the addiction."
            ),
            ContentBlock.Paragraph(
                "This is compounded for Muslims by the spiritual dimension: not only do you feel shame before yourself, but you feel shame before Allah. The weight of knowing you sinned against your Creator while He was watching — this can be psychologically devastating if not channeled into repentance rather than despair."
            ),
            ContentBlock.Warning(
                "If you are experiencing persistent depression, anxiety, or suicidal thoughts, please seek professional help immediately. The healing journey includes both spiritual AND psychological support. There is no shame in seeking help — the Prophet ﷺ said: 'Make use of medical treatment, for Allah has not created a disease without creating a cure for it.'"
            ),
            ContentBlock.Tip(
                "Here is the good news: many men report dramatic improvements in mental health within 30-90 days of abstinence. The brain fog lifts, anxiety decreases, mood stabilizes, and motivation returns. Your brain is resilient. Give it a chance to heal."
            )
        )
    )

    // ═══════════════════════════════════════════════════════════
    // ARTICLE 2: Spiritual Disconnection (Arabic)
    // ═══════════════════════════════════════════════════════════
    private fun article2SpiritualDisconnection() = KnowledgeArticle(
        id = "harms_2",
        categoryId = "harms",
        title = "Spiritual Disconnection",
        titleAr = "الانقطاع الروحي: لماذا صلاتك ميّتة ودعاؤك محجوب؟",
        summary = "Why your salah feels empty and your du'a feels blocked",
        summaryAr = "أثر النظر إلى المحرّمات على العبادات والصلة بالله",
        readTimeMinutes = 8,
        isArabic = true,
        references = listOf(
            "ابن القيم، الجواب الكافي، ص 60-72",
            "ابن القيم، الداء والدواء، ص 85",
            "ابن الجوزي، صيد الخاطر، ص 310",
            "الغزالي، إحياء علوم الدين، كتاب أسرار الصلاة، 1/180",
            "مسند الإمام أحمد (22386)"
        ),
        content = listOf(
            ContentBlock.Header("عندما ينقطع الخطّ مع الله"),
            ContentBlock.Paragraph(
                "هل لاحظتَ أنّك بعد مشاهدة المحرّمات لا تستطيع أن تخشع في صلاتك؟ دعاؤك يبدو كأنّه يصطدم بالسقف ولا يصعد؟ لا تستطيع أن تبكي في سجودك؟ تشعر ببُعد عن الله وجفاء في قلبك؟ هذا ليس وهمًا — هذه عقوبة حقيقية من عقوبات الذنوب."
            ),
            ContentBlock.ScholarQuote(
                text = "من عقوبات الذنوب: أنّها تُطفئ نور القلب، وتُظلم وجه صاحبها، وتحول بينه وبين حلاوة الطاعة ولذّة المناجاة. فالعبد يقوم إلى الصلاة وقلبه في وادٍ وبدنه في وادٍ آخر. يقرأ القرآن فلا يتجاوز حنجرته. يدعو فلا يجد في قلبه خشوعًا ولا انكسارًا.",
                scholar = "ابن القيم الجوزية",
                source = "الجواب الكافي، ص 62"
            ),
            ContentBlock.Header("ماذا تخسر بسبب هذا الذنب؟"),
            ContentBlock.BulletPoints(
                title = "الخسائر الروحية:",
                points = listOf(
                    "الخشوع في الصلاة يختفي — تصبح الصلاة حركات بلا روح",
                    "القرآن لا يؤثّر في القلب — تقرأه ولا تشعر بشيء",
                    "الدعاء يُحسّ كأنّه بلا معنى — لا تستطيع أن تواجه الله",
                    "تتجنّب المسجد — الشعور بالنفاق يمنعك",
                    "صلاة الليل تصبح مستحيلة — أنت منهك من الذنب",
                    "صور الحرام تومض أثناء الصلاة — وهذا أخطر ضرر روحي",
                    "قلبك يقسو — لا تستطيع أن تبكي من خشية الله",
                    "البركة تنزع من وقتك ومالك وعلاقاتك"
                )
            ),
            ContentBlock.Hadith(
                text = "إيّاكم والذنوب، فإنّ العبد ليُحرَم الرزقَ بالذنب يُصيبه.",
                narrator = "مسند الإمام أحمد (22386)",
                grading = "حسّنه الألباني"
            ),
            ContentBlock.Header("القلب المحجوب"),
            ContentBlock.Paragraph(
                "القلب له سعة محدودة. عندما تملؤه بصور الحرام، لا يبقى مكان لنور القرآن. عندما تقضي الليل في المعصية، تصبح صلاة الفجر عبئًا ثقيلًا بدل أن تكون نورًا وراحة."
            ),
            ContentBlock.ScholarQuote(
                text = "كيف يجتمع في قلبٍ واحد أُنسُ المناجاة وظلمة المعصية؟ كيف يطمع في لذّة الطاعة مَن قلبه معلّق بالشهوات؟ إنّ هذا كمن يطلب الماء الصافي من البئر القذرة.",
                scholar = "ابن الجوزي",
                source = "صيد الخاطر، ص 310"
            ),
            ContentBlock.Header("كيف يعود الاتّصال؟"),
            ContentBlock.Paragraph(
                "الخبر الطيّب: الاتّصال الروحي يمكن أن يبدأ في العودة فورًا. على عكس المسارات العصبية التي تحتاج شهورًا للشفاء، صلتك بالله يمكن أن تُعاد بسجدة واحدة صادقة. فهو أقرب إليك من حبل الوريد."
            ),
            ContentBlock.QuranVerse(
                arabic = "وَإِذَا سَأَلَكَ عِبَادِي عَنِّي فَإِنِّي قَرِيبٌ ۖ أُجِيبُ دَعْوَةَ الدَّاعِ إِذَا دَعَانِ",
                translation = "And when My servants ask you about Me — indeed I am near. I respond to the invocation of the supplicant when he calls upon Me.",
                reference = "سورة البقرة: 186"
            ),
            ContentBlock.BulletPoints(
                title = "خطوات استعادة الصلة:",
                points = listOf(
                    "صلِّ ركعتين فورًا بنيّة خالصة: «يا ربّ أعدني إليك»",
                    "اقرأ القرآن ولو صفحة واحدة يوميًا — ابدأ بسورة الرحمن أو يس",
                    "أكثر من الذكر — «سبحان الله وبحمده» مئة مرّة تُصقل القلب",
                    "صلِّ في المسجد مع الجماعة — لا تهرب من بيت الله بسبب ذنبك",
                    "ابكِ بين يدي الله — أو تباكَ — في السجود",
                    "تصدّق — الصدقة تُطفئ الخطيئة كما يُطفئ الماء النار"
                )
            ),
            ContentBlock.Tip(
                "الليلة، بعد صلاة العشاء، صلِّ ركعتين لهذه النيّة فقط: «يا ربّ ردّني إليك ردًّا جميلًا». ابكِ إن استطعت. إن لم تستطع البكاء، فتباكَ. الباب مفتوح دائمًا."
            )
        )
    )

    // ═══════════════════════════════════════════════════════════
    // ARTICLE 3: Relationship Damage (English)
    // ═══════════════════════════════════════════════════════════
    private fun article3RelationshipDamage() = KnowledgeArticle(
        id = "harms_3",
        categoryId = "harms",
        title = "Destroying Your Future Marriage",
        summary = "How pornography rewires your attraction, creates unrealistic expectations, and poisons relationships before they begin",
        readTimeMinutes = 7,
        isArabic = false,
        references = listOf(
            "Zillmann, D. & Bryant, J. (1988). Pornography's impact on sexual satisfaction. Journal of Applied Social Psychology, 18(5), 438-453.",
            "Park, B.Y., et al. (2016). Is Internet Pornography Causing Sexual Dysfunctions? A Review with Clinical Reports. Behavioral Sciences, 6(3), 17.",
            "Bridges, A.J., et al. (2010). Aggression and Sexual Behavior in Best-Selling Pornography Videos. Violence Against Women, 16(10), 1065-1085.",
            "Lambert, N.M., et al. (2012). A love that doesn't last: Pornography consumption and weakened commitment to one's romantic partner. Journal of Social and Clinical Psychology, 31(4), 410-438."
        ),
        content = listOf(
            ContentBlock.Header("Training Your Brain for Failure"),
            ContentBlock.Paragraph(
                "Every time you watch pornography, you are training your brain to be aroused by pixels on a screen rather than a real human being. Over time, this fundamentally rewires your attraction circuits. Real people — with their imperfections, emotions, complexity, and humanity — cannot compete with the artificial, curated, endlessly novel stimulation of pornography."
            ),
            ContentBlock.ScientificFact(
                "Research by Zillmann & Bryant (1988) demonstrated that regular pornography users rated their real-life partners as significantly less attractive and reported substantially less satisfaction in their intimate relationships compared to non-users.",
                "Zillmann & Bryant, 1988 — Journal of Applied Social Psychology"
            ),
            ContentBlock.Header("The Damage Before Marriage Even Begins"),
            ContentBlock.Paragraph(
                "For unmarried Muslims, this harm is particularly devastating. You are spending years training your brain to respond to something artificial, and then expecting it to function properly with something real. The consequences are predictable and widespread:"
            ),
            ContentBlock.BulletPoints(
                title = "How pornography damages your future marriage:",
                points = listOf(
                    "Unrealistic physical expectations — real women don't look like edited, surgically enhanced performers",
                    "Novelty addiction — your brain craves variety, making commitment to one person feel 'boring'",
                    "Sexual dysfunction — pornography-induced erectile dysfunction (PIED) is increasingly common in young men",
                    "Emotional disconnect — you learn to separate physical attraction from emotional intimacy",
                    "Objectification — you train yourself to see women as bodies rather than complete human beings with souls",
                    "Performance anxiety — comparing yourself to unrealistic scenarios creates intense anxiety",
                    "Secret life — addiction builds a hidden world that prevents genuine trust and vulnerability",
                    "Reduced commitment — studies show pornography users have weaker relationship commitment"
                )
            ),
            ContentBlock.ScientificFact(
                "A landmark review by Park et al. (2016) documented a dramatic increase in sexual dysfunction among young men under 40, coinciding with the rise of high-speed internet pornography. The study reported that many young men could only achieve arousal with pornography, not with a real partner. Recovery occurred after sustained abstinence.",
                "Park, B.Y., et al. (2016) — Behavioral Sciences"
            ),
            ContentBlock.Header("What Allah Designed vs. What Pornography Creates"),
            ContentBlock.QuranVerse(
                arabic = "وَمِنْ آيَاتِهِ أَنْ خَلَقَ لَكُم مِّنْ أَنفُسِكُمْ أَزْوَاجًا لِّتَسْكُنُوا إِلَيْهَا وَجَعَلَ بَيْنَكُم مَّوَدَّةً وَرَحْمَةً",
                translation = "And of His signs is that He created for you from yourselves mates that you may find tranquility in them; and He placed between you affection and mercy.",
                reference = "Surah Ar-Rum 30:21"
            ),
            ContentBlock.Paragraph(
                "Allah designed marriage to bring three things: tranquility (سكن), affection (مودّة), and mercy (رحمة). Pornography is the antithesis of every single one:"
            ),
            ContentBlock.BulletPoints(
                title = "",
                points = listOf(
                    "Instead of tranquility → anxiety, guilt, and restlessness",
                    "Instead of affection → objectification and selfishness",
                    "Instead of mercy → exploitation and dehumanization"
                )
            ),
            ContentBlock.Header("Rebuilding Healthy Attraction"),
            ContentBlock.Paragraph(
                "The good news: the brain can rewire back to healthy attraction patterns. Men who quit pornography consistently report that over time — typically 60-120 days — their attraction to real people returns, emotional connection deepens, and the artificial fantasies lose their power."
            ),
            ContentBlock.BulletPoints(
                title = "Steps to rebuild:",
                points = listOf(
                    "Complete abstinence from all pornographic content — no half measures",
                    "Reduce overall screen stimulation — social media, suggestive content, even excessive entertainment",
                    "Practice seeing women as complete human beings — someone's daughter, someone's sister",
                    "If married: rebuild emotional intimacy before physical — talk, listen, be vulnerable",
                    "If unmarried: invest in becoming the spouse YOU would want to marry",
                    "Be patient — your brain needs time to recalibrate"
                )
            ),
            ContentBlock.Tip(
                "Your future spouse deserves the best version of you. Every day you stay clean is a gift you are building for your future marriage. Every day you spend in addiction is a debt you will have to repay. Start building the gift today."
            )
        )
    )

    // ═══════════════════════════════════════════════════════════
    // ARTICLE 4: Physical Harms (English + scientific)
    // ═══════════════════════════════════════════════════════════
    private fun article4PhysicalHarms() = KnowledgeArticle(
        id = "harms_4",
        categoryId = "harms",
        title = "Physical Consequences",
        summary = "Erectile dysfunction, hormonal disruption, sleep damage, and the physical toll on your body",
        readTimeMinutes = 6,
        isArabic = false,
        references = listOf(
            "Park, B.Y., et al. (2016). Is Internet Pornography Causing Sexual Dysfunctions? Behavioral Sciences, 6(3), 17.",
            "Kühn, S. & Gallinat, J. (2014). Brain Structure and Functional Connectivity Associated With Pornography Consumption. JAMA Psychiatry, 71(7), 827-834.",
            "Banca, P., et al. (2016). Novelty, conditioning and attentional bias to sexual rewards. Journal of Psychiatric Research, 72, 91-101.",
            "Medic, G., et al. (2017). Short- and long-term health consequences of sleep disruption. Nature and Science of Sleep, 9, 151-161."
        ),
        content = listOf(
            ContentBlock.Header("It's Not Just in Your Head"),
            ContentBlock.Paragraph(
                "Pornography addiction doesn't only affect your brain and soul — it takes a measurable physical toll on your body. From sexual dysfunction to hormonal disruption to chronic sleep deprivation, the physical consequences are real and documented."
            ),
            ContentBlock.Header("Pornography-Induced Erectile Dysfunction (PIED)"),
            ContentBlock.Paragraph(
                "This is one of the most alarming physical consequences, and it's affecting men at younger and younger ages. PIED occurs when the brain becomes so conditioned to artificial sexual stimuli that it can no longer respond to real intimacy."
            ),
            ContentBlock.ScientificFact(
                "Park et al. (2016) documented that the rate of erectile dysfunction in men under 40 has risen dramatically from historical rates of 2-3% to current rates of 14-33% in various studies — a timeline that coincides precisely with the availability of high-speed internet pornography.",
                "Park, B.Y., et al. (2016) — Behavioral Sciences"
            ),
            ContentBlock.BulletPoints(
                title = "How PIED develops:",
                points = listOf(
                    "The brain becomes conditioned to respond only to the specific stimuli of pornography",
                    "Real intimacy doesn't provide the same intensity of visual novelty",
                    "The reward circuit requires increasingly extreme content to achieve the same arousal",
                    "Over time, the brain loses its ability to respond to normal, healthy stimulation",
                    "Performance anxiety compounds the problem, creating a vicious cycle"
                )
            ),
            ContentBlock.Paragraph(
                "The good news: PIED is reversible with sustained abstinence. Most men report significant recovery within 60-120 days of complete abstinence from pornography."
            ),
            ContentBlock.Header("Sleep Destruction"),
            ContentBlock.Paragraph(
                "Pornography consumption typically occurs late at night — often replacing hours of sleep. But even beyond lost sleep time, the neurochemical effects disrupt sleep quality:"
            ),
            ContentBlock.BulletPoints(
                title = "How pornography destroys your sleep:",
                points = listOf(
                    "Blue light from screens suppresses melatonin production",
                    "Dopamine surges create arousal states incompatible with sleep",
                    "The cortisol spike from guilt and shame activates the stress response",
                    "Hours are lost to the addiction itself — sessions often extend far beyond intended time",
                    "Sleep architecture is disrupted — less REM sleep means poorer emotional regulation",
                    "Morning exhaustion leads to poor decisions and increased vulnerability to triggers"
                )
            ),
            ContentBlock.ScientificFact(
                "Chronic sleep disruption is associated with impaired immune function, increased inflammation, hormonal imbalance, weight gain, cardiovascular risk, and — critically — weakened prefrontal cortex function, which further reduces your ability to resist urges.",
                "Medic, G., et al. (2017) — Nature and Science of Sleep"
            ),
            ContentBlock.Header("Hormonal Disruption"),
            ContentBlock.BulletPoints(
                title = "Pornography and compulsive sexual behavior affect hormones:",
                points = listOf(
                    "Chronic dopamine overstimulation disrupts the hypothalamic-pituitary axis",
                    "Cortisol (stress hormone) remains chronically elevated due to guilt cycles",
                    "Prolactin spikes after each session create fatigue and low motivation",
                    "Testosterone regulation may be affected by chronic overstimulation patterns",
                    "Oxytocin (bonding hormone) pathways become dysregulated — bonding with screens instead of humans"
                )
            ),
            ContentBlock.Header("Physical Signs of Recovery"),
            ContentBlock.BulletPoints(
                title = "Men who quit report these physical improvements:",
                points = listOf(
                    "Dramatically improved sleep quality within 1-2 weeks",
                    "Increased physical energy and reduced fatigue",
                    "Clearer eyes and healthier skin appearance",
                    "Improved physical workout performance",
                    "Sexual function normalizing within 60-120 days",
                    "Stronger immune system — fewer illnesses",
                    "Better appetite regulation and healthier eating patterns",
                    "Natural confidence reflected in posture, voice, and body language"
                )
            ),
            ContentBlock.Warning(
                "If you are experiencing persistent sexual dysfunction, hormonal issues, or chronic sleep problems, consult a medical professional. These physical symptoms are real and treatable. Abstinence from pornography is the foundation, but medical support can accelerate recovery."
            ),
            ContentBlock.Tip(
                "Tonight, put your phone in another room before bed. This single change eliminates the late-night trigger window AND improves your sleep quality. Two victories with one action."
            )
        )
    )
}