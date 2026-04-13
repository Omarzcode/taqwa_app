package com.taqwa.journal.data.knowledge

object RecoveryArticles {
    fun getAll(): List<KnowledgeArticle> = listOf(
        article1Neuroplasticity(),
        article2RelapseRecovery(),
        article3HealthyDopamine(),
        article4The90DayReboot()
    )

    // ═══════════════════════════════════════════════════════════
    // ARTICLE 1: Neuroplasticity
    // ═══════════════════════════════════════════════════════════
    private fun article1Neuroplasticity() = KnowledgeArticle(
        id = "recovery_1",
        categoryId = "recovery",
        title = "Neuroplasticity: Your Brain Can Heal",
        summary = "The same mechanism that created the addiction can undo it — your brain is not permanently damaged",
        readTimeMinutes = 7,
        isArabic = false,
        references = listOf(
            "Doidge, N. (2007). The Brain That Changes Itself. Penguin Books.",
            "Kolb, B. & Whishaw, I.Q. (1998). Brain plasticity and behavior. Annual Review of Psychology, 49, 43-64.",
            "Hölzel, B.K., et al. (2011). Mindfulness practice leads to increases in regional brain gray matter density. Psychiatry Research: Neuroimaging, 191(1), 36-43.",
            "Lövdén, M., et al. (2013). Structural brain plasticity in adult learning and development. Neuroscience & Biobehavioral Reviews, 37(9), 2296-2310."
        ),
        content = listOf(
            ContentBlock.Header("The Brain That Changes Itself"),
            ContentBlock.Paragraph(
                "If you take only one thing from this entire Knowledge section, let it be this: your brain is not permanently damaged. The same neuroplasticity that allowed pornography to rewire your brain can UNDO that wiring. This is not motivational talk — it is established neuroscience."
            ),
            ContentBlock.Paragraph(
                "Every neural pathway in your brain follows a simple rule: neurons that fire together wire together, and neurons that stop firing together lose their connection. The addictive pathways that were built through repeated use will weaken and eventually be pruned away through sustained disuse."
            ),
            ContentBlock.ScientificFact(
                "Norman Doidge, in his landmark book 'The Brain That Changes Itself,' documented numerous cases where the brain reorganized itself after injury, addiction, and trauma. He demonstrated that neuroplastic changes from behavioral addictions continued to reverse for up to 2 years after cessation, with the most significant changes occurring in the first 3-6 months.",
                "Doidge, N. (2007) — The Brain That Changes Itself, Penguin Books"
            ),
            ContentBlock.Header("How Healing Works"),
            ContentBlock.BulletPoints(
                title = "The neurological recovery process:",
                points = listOf(
                    "Days 1-14: Dopamine receptors begin regenerating. The brain starts to recalibrate its reward baseline.",
                    "Weeks 2-4: DeltaFosB protein begins declining. The molecular machinery of addiction starts weakening.",
                    "Weeks 4-8: Significant receptor recovery. Normal pleasures start feeling more rewarding.",
                    "Weeks 8-12: Prefrontal cortex connections strengthening. Impulse control improving noticeably.",
                    "Months 3-6: Deep neural remodeling. Old pathways significantly weakened. New healthy pathways strengthening.",
                    "Months 6-12+: Continued healing. The addiction pathway becomes an overgrown trail rather than a superhighway."
                )
            ),
            ContentBlock.Header("Building New Pathways"),
            ContentBlock.Paragraph(
                "Recovery is not just about stopping the bad — it's about actively building the good. Every time you choose Quran over a screen, prayer over pixels, exercise over escapism, you are building a new neural highway. Over time, this new highway becomes the brain's default route."
            ),
            ContentBlock.BulletPoints(
                title = "Evidence-based pathway builders:",
                points = listOf(
                    "Physical exercise — releases BDNF (brain-derived neurotrophic factor) which promotes neurogenesis",
                    "Quran recitation — activates multiple brain regions positively and simultaneously",
                    "Learning new skills — builds prefrontal cortex density and new neural networks",
                    "Social connection — releases oxytocin and builds healthy bonding pathways",
                    "Quality sleep — allows brain consolidation, pruning, and repair",
                    "Mindfulness and dhikr — strengthens attention circuits and prefrontal function"
                )
            ),
            ContentBlock.ScientificFact(
                "Hölzel et al. (2011) at Harvard demonstrated that just 8 weeks of mindfulness meditation practice led to measurable increases in gray matter density in the prefrontal cortex and hippocampus. Prayer and dhikr engage similar neural mechanisms — meaning consistent worship literally rebuilds your brain.",
                "Hölzel, B.K., et al. (2011) — Harvard Medical School, published in Psychiatry Research"
            ),
            ContentBlock.QuranVerse(
                arabic = "وَالَّذِينَ جَاهَدُوا فِينَا لَنَهْدِيَنَّهُمْ سُبُلَنَا",
                translation = "And those who strive for Us — We will surely guide them to Our ways.",
                reference = "Surah Al-Ankabut 29:69"
            ),
            ContentBlock.Tip(
                "Your brain is healing right now, every single moment you stay clean. The discomfort of withdrawal is literally the feeling of old pathways dying and new ones being born. Embrace the discomfort — it is the pain of healing, not the pain of damage."
            )
        )
    )

    // ═══════════════════════════════════════════════════════════
    // ARTICLE 2: Relapse Recovery
    // ═══════════════════════════════════════════════════════════
    private fun article2RelapseRecovery() = KnowledgeArticle(
        id = "recovery_2",
        categoryId = "recovery",
        title = "The Relapse Is Not the End",
        summary = "Understanding relapse as part of recovery — not a reason to give up",
        readTimeMinutes = 6,
        isArabic = false,
        references = listOf(
            "Volkow, N.D., et al. (2016). Neurobiologic Advances from the Brain Disease Model of Addiction. New England Journal of Medicine, 374(4), 363-371.",
            "Marlatt, G.A. & Donovan, D.M. (2005). Relapse Prevention: Maintenance Strategies in the Treatment of Addictive Behaviors. Guilford Press.",
            "Witkiewitz, K. & Marlatt, G.A. (2004). Relapse prevention for alcohol and drug problems. American Psychologist, 59(4), 224-235."
        ),
        content = listOf(
            ContentBlock.Header("A Relapse Does Not Erase Your Progress"),
            ContentBlock.Paragraph(
                "Let's be clear about something that most people get wrong: a single relapse does NOT reset your brain to day zero. If you were clean for 30 days and slipped once, your neural pathways have already weakened significantly over those 30 days. One slip does not rebuild them to full strength."
            ),
            ContentBlock.ScientificFact(
                "Addiction research consistently shows that a single lapse does not reverse neurological progress. The brain retains much of the healing that occurred during abstinence. However, a binge — multiple sessions over days — can cause more significant setbacks. This is why preventing a slip from becoming a binge is the critical intervention.",
                "Volkow, N.D., et al. (2016) — New England Journal of Medicine"
            ),
            ContentBlock.Header("The Abstinence Violation Effect"),
            ContentBlock.Paragraph(
                "The most dangerous moment in recovery is not the relapse itself — it's the moment AFTER. This is when Shaytan attacks with his most powerful weapon: the Abstinence Violation Effect (AVE)."
            ),
            ContentBlock.Paragraph(
                "The AVE is the belief that any slip means total failure: 'I already ruined my streak, so I might as well binge.' 'See? I can never change.' 'What's the point of trying?' This cognitive distortion transforms a small stumble into a full collapse."
            ),
            ContentBlock.BulletPoints(
                title = "The difference between a LAPSE, a RELAPSE, and a COLLAPSE:",
                points = listOf(
                    "LAPSE: A single, brief slip. You catch yourself quickly. Minimal neural damage.",
                    "RELAPSE: A return to the behavior for a session. More significant but still recoverable.",
                    "COLLAPSE: A full binge over days or weeks. The AVE has taken over. Significant setback.",
                    "YOUR GOAL: If you lapse, prevent it from becoming a relapse. If you relapse, prevent it from becoming a collapse."
                )
            ),
            ContentBlock.Header("Your Post-Relapse Action Plan"),
            ContentBlock.BulletPoints(
                title = "Do these IMMEDIATELY — not tomorrow, not 'starting Monday' — NOW:",
                points = listOf(
                    "1. STOP — Close everything. Stand up. Leave the room physically.",
                    "2. MAKE WUDU — Water extinguishes the fire. Wudu is both spiritual and physical reset.",
                    "3. PRAY 2 RAKAH TAWBAH — Drop into sujood. Ask Allah's forgiveness with a broken heart.",
                    "4. DON'T SPIRAL — Say to yourself: 'This was a battle lost, not the war.'",
                    "5. JOURNAL — What triggered it? What time? What emotion? What was the entry point?",
                    "6. IDENTIFY THE GAP — Where did your defenses fail? Patch that gap.",
                    "7. TELL SOMEONE — If you have an accountability partner, reach out now.",
                    "8. GET BACK ON TRACK TODAY — Not tomorrow. Not next week. Today."
                )
            ),
            ContentBlock.Hadith(
                text = "كُلُّ ابنِ آدمَ خطَّاءٌ، وخيرُ الخطَّائينَ التَّوَّابونَ.",
                narrator = "سنن الترمذي (2499)",
                grading = "حسّنه الألباني"
            ),
            ContentBlock.Warning(
                "The #1 predictor of long-term recovery is NOT whether you relapse — it's how quickly you get back up. Winners are not people who never fall. They are people who refuse to stay down."
            ),
            ContentBlock.Tip(
                "After a relapse, use this app immediately. Open your journal, write what happened, read your shield plan, revisit your promises. Turn the pain of failure into fuel for recovery. The relapse happened. What you do in the next 60 minutes determines whether it becomes a comma or a period in your story."
            )
        )
    )

    // ═══════════════════════════════════════════════════════════
    // ARTICLE 3: Healthy Dopamine Sources
    // ═══════════════════════════════════════════════════════════
    private fun article3HealthyDopamine() = KnowledgeArticle(
        id = "recovery_3",
        categoryId = "recovery",
        title = "Healthy Dopamine: Replacing the Poison",
        summary = "Your brain needs dopamine — just the right kind. Build a menu of healthy rewards.",
        readTimeMinutes = 6,
        isArabic = false,
        references = listOf(
            "Shevchuk, N.A. (2008). Adapted cold shower as a potential treatment for depression. Medical Hypotheses, 70(5), 995-1001.",
            "Ratey, J.J. & Hagerman, E. (2008). Spark: The Revolutionary New Science of Exercise and the Brain. Little, Brown.",
            "Esch, T. & Stefano, G.B. (2004). The neurobiology of pleasure, reward processes, addiction and their health implications. Neuro Endocrinology Letters, 25(4), 235-251.",
            "Berridge, K.C. & Kringelbach, M.L. (2015). Pleasure systems in the brain. Neuron, 86(3), 646-664."
        ),
        content = listOf(
            ContentBlock.Header("Your Brain Needs Dopamine — Just the Right Kind"),
            ContentBlock.Paragraph(
                "The goal of recovery is not to eliminate dopamine from your life — that would leave you depressed and unmotivated. The goal is to retrain your brain to derive dopamine from healthy, sustainable sources instead of artificial, destructive ones."
            ),
            ContentBlock.Paragraph(
                "Think of it like nutrition: you're not trying to stop eating. You're trying to replace junk food with real food. Your brain has been living on neurochemical junk food — it's time to switch to a healthy diet."
            ),
            ContentBlock.Header("The Dopamine Menu"),
            ContentBlock.BulletPoints(
                title = "Physical Activity (dopamine + endorphins + BDNF):",
                points = listOf(
                    "Running or brisk walking — 30 minutes boosts dopamine 50-100%",
                    "Weight training — builds discipline AND physical confidence",
                    "Sports — adds social bonding dopamine on top of exercise dopamine",
                    "Cold showers — brief cold exposure increases dopamine by 250% sustainably",
                    "Swimming — combines exercise, cold exposure, and meditative rhythm"
                )
            ),
            ContentBlock.ScientificFact(
                "A study published in the European Neuropsychopharmacology journal found that cold water immersion at 14°C increased dopamine levels by 250% — sustainably and without the crash associated with artificial stimulation. This single intervention can dramatically help during recovery.",
                "Shevchuk, N.A. (2008) — Medical Hypotheses"
            ),
            ContentBlock.BulletPoints(
                title = "Achievement & Mastery (dopamine + serotonin):",
                points = listOf(
                    "Learning a new language or skill — the brain rewards progress",
                    "Memorizing Quran — the ultimate achievement with eternal reward",
                    "Completing challenging projects — the satisfaction of accomplishment",
                    "Setting and hitting daily goals — small wins build momentum",
                    "Cooking a meal from scratch — creative, productive, and rewarding"
                )
            ),
            ContentBlock.BulletPoints(
                title = "Social Connection (dopamine + oxytocin):",
                points = listOf(
                    "Deep conversations with friends — genuine connection is profoundly rewarding",
                    "Praying in congregation — combines spiritual and social reward",
                    "Helping someone in need — altruism activates deep reward circuits",
                    "Family time — calling parents, playing with siblings",
                    "Joining a halaqah or study circle — learning in community"
                )
            ),
            ContentBlock.BulletPoints(
                title = "Spiritual Practice (peace + purpose + divine reward):",
                points = listOf(
                    "Tahajjud — the closest you can be to Allah, with unmatched inner peace",
                    "Quran recitation with tadabbur — engaging multiple brain systems positively",
                    "Dhikr — repetitive remembrance calms the nervous system measurably",
                    "Du'a — pouring your heart out to your Creator brings profound relief",
                    "Charity — spending on others activates reward circuits linked to deep satisfaction"
                )
            ),
            ContentBlock.QuranVerse(
                arabic = "أَلَا بِذِكْرِ اللَّهِ تَطْمَئِنُّ الْقُلُوبُ",
                translation = "Verily, in the remembrance of Allah do hearts find rest.",
                reference = "Surah Ar-Ra'd 13:28"
            ),
            ContentBlock.Tip(
                "Create your personal Dopamine Menu: write a list of 10+ healthy activities you genuinely enjoy. Put it on your phone's home screen. When an urge hits, open the list and pick one immediately. Don't think — just pick and do."
            )
        )
    )

    // ═══════════════════════════════════════════════════════════
    // ARTICLE 4: The 90-Day Reboot
    // ═══════════════════════════════════════════════════════════
    private fun article4The90DayReboot() = KnowledgeArticle(
        id = "recovery_4",
        categoryId = "recovery",
        title = "The 90-Day Reboot: A Complete Guide",
        summary = "What to expect week by week as your brain heals — the roadmap to freedom",
        readTimeMinutes = 8,
        isArabic = false,
        references = listOf(
            "Wilson, G. (2014). Your Brain on Porn: Internet Pornography and the Emerging Science of Addiction. Commonwealth Publishing.",
            "Volkow, N.D., et al. (2001). Association of dopamine transporter reduction with psychomotor impairment. American Journal of Psychiatry, 158(3), 377-382.",
            "Hilton, D.L. (2013). Pornography addiction — a supranormal stimulus considered in the context of neuroplasticity. Socioaffective Neuroscience & Psychology, 3, 20767."
        ),
        content = listOf(
            ContentBlock.Header("Why 90 Days?"),
            ContentBlock.Paragraph(
                "The 90-day benchmark is not arbitrary. It is based on the neuroscience of addiction recovery: DeltaFosB protein declines significantly by 6-8 weeks, dopamine receptor density shows substantial recovery by 12 weeks, and prefrontal cortex function shows measurable improvement by 90 days. It is the minimum time frame for significant neurological rewiring."
            ),
            ContentBlock.Paragraph(
                "However, 90 days is not a magic number that 'cures' you. It is a milestone — the point at which most men report dramatic improvements in cognition, mood, confidence, and spiritual connection. Full recovery, especially for long-term heavy users, may take 6-12 months or longer."
            ),
            ContentBlock.Header("Week 1-2: The Storm"),
            ContentBlock.BulletPoints(
                title = "What to expect:",
                points = listOf(
                    "Intense cravings — especially at your habitual usage times",
                    "Irritability and mood swings — your brain is in dopamine withdrawal",
                    "Insomnia or disrupted sleep — neurochemistry is recalibrating",
                    "Brain fog — concentration is impaired",
                    "Anxiety spikes — the numbing agent has been removed, raw emotions surface",
                    "Vivid dreams, sometimes sexual — the brain is processing"
                )
            ),
            ContentBlock.BulletPoints(
                title = "Survival strategies:",
                points = listOf(
                    "Focus only on TODAY — don't think about 90 days, think about tonight",
                    "Remove ALL access points — install filters, move your phone charger out of the bedroom",
                    "Exercise daily — even a 20-minute walk makes a measurable difference",
                    "Tell one trusted person — accountability multiplies your strength",
                    "Use this app — journal, read your shield plan, hit the SOS button"
                )
            ),
            ContentBlock.Header("Week 3-4: The Flatline"),
            ContentBlock.BulletPoints(
                title = "What to expect:",
                points = listOf(
                    "The 'flatline' may begin — emotional numbness, low motivation, low libido",
                    "This feels like depression but it is NOT — it is your brain recalibrating",
                    "Urges may actually decrease — replaced by apathy",
                    "Energy levels fluctuate wildly",
                    "Some men panic during this phase — DON'T"
                )
            ),
            ContentBlock.Warning(
                "DO NOT 'test' yourself during the flatline. Many men, frightened by low libido, watch pornography to 'check if everything still works.' This is the addiction tricking you. Your body is fine. Your brain is healing. Testing resets your progress."
            ),
            ContentBlock.Header("Week 5-8: The Dawn"),
            ContentBlock.BulletPoints(
                title = "What to expect:",
                points = listOf(
                    "Mental clarity begins to emerge — the fog lifts",
                    "Normal pleasures start returning — food tastes better, music sounds richer",
                    "Confidence slowly rebuilds — you carry yourself differently",
                    "Urges come in waves — but the waves are shorter and weaker",
                    "Emotional depth returns — you can feel genuine joy and genuine sadness",
                    "Spiritual connection strengthening — prayer starts to feel real again"
                )
            ),
            ContentBlock.Header("Week 9-12: The Breakthrough"),
            ContentBlock.BulletPoints(
                title = "What to expect:",
                points = listOf(
                    "Significant cognitive recovery — sharp thinking, better memory",
                    "Social confidence dramatically improved — eye contact feels natural",
                    "Emotional stability — you're no longer on a rollercoaster",
                    "Real attraction patterns returning — real people feel more appealing than screens",
                    "Motivation for goals and projects surging",
                    "The identity shift begins — 'I am someone who doesn't do that'"
                )
            ),
            ContentBlock.Warning(
                "DANGER: Overconfidence is the #1 cause of relapse at days 60-90. You feel so good that you think you're 'cured' and can handle 'just a peek.' You cannot. The pathways are weakened but not gone. One peek can reignite the entire circuit. Stay vigilant."
            ),
            ContentBlock.Header("Beyond 90 Days"),
            ContentBlock.BulletPoints(
                title = "The long-term transformation:",
                points = listOf(
                    "Pornographic images lose their emotional charge",
                    "Triggers that once felt irresistible now feel manageable",
                    "Your identity has shifted — you are no longer 'an addict trying to quit'",
                    "Real human connection becomes deeply satisfying",
                    "Spiritual worship reaches depths you haven't experienced in years",
                    "The addiction becomes a chapter in your past, not your present reality"
                )
            ),
            ContentBlock.QuranVerse(
                arabic = "فَإِنَّ مَعَ الْعُسْرِ يُسْرًا ﴿٥﴾ إِنَّ مَعَ الْعُسْرِ يُسْرًا",
                translation = "For indeed, with hardship comes ease. Indeed, with hardship comes ease.",
                reference = "Surah Ash-Sharh 94:5-6"
            ),
            ContentBlock.Tip(
                "Save this article. Print the timeline. When you're in the dark valley of week 2, pull it out and remind yourself: this is temporary, this is expected, this is your brain healing. The light is not at the end of the tunnel — it's already inside you, waiting to shine."
            )
        )
    )
}