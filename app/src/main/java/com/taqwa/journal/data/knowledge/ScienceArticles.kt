package com.taqwa.journal.data.knowledge

object ScienceArticles {
    fun getAll(): List<KnowledgeArticle> = listOf(
        article1HowPornHijacksYourBrain(),
        article2TheCoolidgeEffect(),
        article3DeltaFosB(),
        article4PrefrontalCortexDamage(),
        article5TheWithdrawalTimeline()
    )

    // ═══════════════════════════════════════════════════════════
    // ARTICLE 1: How Porn Hijacks Your Brain
    // ═══════════════════════════════════════════════════════════
    private fun article1HowPornHijacksYourBrain() = KnowledgeArticle(
        id = "science_1",
        categoryId = "science",
        title = "How Pornography Hijacks Your Brain",
        summary = "The dopamine trap that keeps you coming back \u2014 and why willpower alone cannot break it",
        readTimeMinutes = 7,
        isArabic = false,
        references = listOf(
            "Hilton, D.L. & Watts, C. (2011). Pornography addiction: A neuroscience perspective. Surgical Neurology International, 2, 19.",
            "Volkow, N.D., Koob, G.F., & McLellan, A.T. (2016). Neurobiologic advances from the brain disease model of addiction. New England Journal of Medicine, 374(4), 363\u2013371.",
            "Love, T., Laier, C., Brand, M., Hatch, L., & Hajela, R. (2015). Neuroscience of Internet Pornography Addiction: A Review and Update. Behavioral Sciences, 5(3), 388\u2013433.",
            "Voon, V., et al. (2014). Neural correlates of sexual cue reactivity in individuals with and without compulsive sexual behaviours. PLoS ONE, 9(7), e102419.",
            "Doidge, N. (2007). The Brain That Changes Itself. Penguin Books."
        ),
        content = listOf(
            ContentBlock.Header("Your Brain\u2019s Reward System"),
            ContentBlock.Paragraph(
                "Deep inside your brain lies a structure called the nucleus accumbens \u2014 the core of your reward circuit. This ancient system evolved over millions of years to reinforce behaviors essential for survival: eating when hungry, drinking when thirsty, bonding with others, and reproducing. It does this through a neurotransmitter called dopamine."
            ),
            ContentBlock.Paragraph(
                "Dopamine is not actually the \u201Cpleasure chemical\u201D as it is commonly described. More accurately, dopamine is the \u201Cwanting chemical\u201D \u2014 it creates desire, anticipation, and motivation. It\u2019s the feeling of \u201CI need this\u201D rather than \u201CI enjoy this.\u201D This distinction is crucial to understanding addiction."
            ),
            ContentBlock.ScientificFact(
                "Research by Berridge & Robinson (1998) at the University of Michigan demonstrated that dopamine drives \u201Cwanting\u201D (incentive salience) rather than \u201Cliking\u201D (hedonic pleasure). This explains why addicts desperately seek their substance even when they no longer enjoy it.",
                "Berridge & Robinson, 1998 \u2014 University of Michigan, published in Brain Research Reviews"
            ),
            ContentBlock.Header("How Pornography Exploits This System"),
            ContentBlock.Paragraph(
                "Your reward circuit was designed for a world of scarcity. Finding a potential mate required effort, time, and social investment. The dopamine reward was proportional to the effort and rarity of the event. Pornography shatters this balance entirely."
            ),
            ContentBlock.Paragraph(
                "With internet pornography, your brain encounters more potential \u201Cmates\u201D in ten minutes than your ancestors encountered in a lifetime. Each new image, each new video, each new tab triggers a fresh dopamine surge. Your brain cannot distinguish between pixels on a screen and a real person \u2014 the reward circuit fires the same way."
            ),
            ContentBlock.ScientificFact(
                "Brain scan studies by Voon et al. (2014) at Cambridge University found that the brains of compulsive pornography users showed the same patterns of neural activation as the brains of drug addicts when exposed to their substance. The ventral striatum (reward center) lit up identically.",
                "Voon, V., et al. (2014) \u2014 Cambridge University, published in PLoS ONE"
            ),
            ContentBlock.Header("The Dopamine Escalation"),
            ContentBlock.Paragraph(
                "Here is where the trap tightens. When your brain is repeatedly flooded with unnaturally high levels of dopamine, it defends itself through a process called downregulation. Your brain literally removes dopamine receptors \u2014 the \u201Cdocking stations\u201D that receive the dopamine signal."
            ),
            ContentBlock.Paragraph(
                "With fewer receptors, the same amount of dopamine produces a weaker signal. This is tolerance \u2014 the same mechanism that forces a drug addict to increase their dose. For a pornography user, tolerance manifests in several ways:"
            ),
            ContentBlock.BulletPoints(
                title = "Signs of Dopamine Tolerance:",
                points = listOf(
                    "You need longer sessions to feel satisfied",
                    "Content that once excited you no longer does",
                    "You escalate to more extreme, novel, or taboo content",
                    "You open multiple tabs simultaneously seeking the \u201Cperfect\u201D content",
                    "Normal real-life pleasures feel dull and unrewarding",
                    "You feel restless and bored when not consuming",
                    "The seeking phase (browsing) becomes more compelling than the content itself"
                )
            ),
            ContentBlock.ScientificFact(
                "A landmark study by Kuhn & Gallinat (2014) published in JAMA Psychiatry found that higher pornography consumption was directly correlated with reduced gray matter volume in the reward circuit (right caudate nucleus) and reduced functional connectivity to the prefrontal cortex. In simple terms: more porn = physically smaller and less connected reward center.",
                "Kuhn, S. & Gallinat, J. (2014) \u2014 Max Planck Institute, published in JAMA Psychiatry"
            ),
            ContentBlock.Header("The Binge Cycle"),
            ContentBlock.Paragraph(
                "Addiction creates a predictable cycle. Understanding this cycle is the first step to breaking it:"
            ),
            ContentBlock.BulletPoints(
                title = "",
                points = listOf(
                    "1. TRIGGER: Stress, boredom, loneliness, or an environmental cue",
                    "2. CRAVING: Dopamine surges in anticipation \u2014 the \u201Cwanting\u201D begins",
                    "3. RITUAL: Opening the browser, finding a private place, the anticipation builds",
                    "4. CONSUMPTION: Temporary relief and pleasure, but less than expected",
                    "5. SHAME/CRASH: Dopamine drops below baseline, guilt and regret flood in",
                    "6. NUMBING: Emotional pain from the crash creates vulnerability to the next trigger",
                    "7. REPEAT: The cycle shortens and intensifies over time"
                )
            ),
            ContentBlock.Header("Why Willpower Alone Fails"),
            ContentBlock.Paragraph(
                "Many people try to quit through sheer willpower. They white-knuckle through urges, relying on conscious resistance alone. This approach has a fundamental problem: the part of your brain that exerts willpower (the prefrontal cortex) is being actively weakened by the addiction, while the part driving the compulsive behavior (the reward circuit and habit pathways) is being strengthened."
            ),
            ContentBlock.Paragraph(
                "It\u2019s like trying to fight a war where your army gets weaker every day while your enemy gets stronger. This is why recovery requires a comprehensive strategy \u2014 not just resistance, but rewiring."
            ),
            ContentBlock.Warning(
                "If you have tried to quit multiple times and failed, this is not a reflection of your character or your faith. It is a reflection of how powerfully this addiction rewires the brain. Understanding the neuroscience is not an excuse \u2014 it is a weapon. You cannot fight an enemy you do not understand."
            ),
            ContentBlock.Tip(
                "Start by identifying your personal cycle. What are your top 3 triggers? What time of day are you most vulnerable? What emotions precede a relapse? Write these down. Awareness is the first crack in the addiction\u2019s armor."
            )
        )
    )

    // ═══════════════════════════════════════════════════════════
    // ARTICLE 2: The Coolidge Effect
    // ═══════════════════════════════════════════════════════════
    private fun article2TheCoolidgeEffect() = KnowledgeArticle(
        id = "science_2",
        categoryId = "science",
        title = "The Coolidge Effect: Why Your Brain Craves Novelty",
        summary = "The biological mechanism behind endless scrolling and why \u201Cone more click\u201D never satisfies",
        readTimeMinutes = 6,
        isArabic = false,
        references = listOf(
            "Wilson, G. (2014). Your Brain on Porn: Internet Pornography and the Emerging Science of Addiction. Commonwealth Publishing.",
            "Fiorino, D.F., Coury, A., & Phillips, A.G. (1997). Dynamic changes in nucleus accumbens dopamine efflux during the Coolidge effect in male rats. Journal of Neuroscience, 17(12), 4849\u20134855.",
            "Georgiadis, J.R. & Kringelbach, M.L. (2012). The human sexual response cycle: brain imaging evidence linking sex to other pleasures. Progress in Neurobiology, 98(1), 49\u201381."
        ),
        content = listOf(
            ContentBlock.Header("The Biology of Novelty-Seeking"),
            ContentBlock.Paragraph(
                "In the 1950s, researchers observed a fascinating phenomenon in mammals. A male rat, after mating with a female until he was completely exhausted and showed no further interest, would immediately become aroused again when a new female was introduced. This cycle could be repeated multiple times \u2014 each new female reignited the drive that was supposedly \u201Cspent.\u201D"
            ),
            ContentBlock.Paragraph(
                "This phenomenon was named the Coolidge Effect, and it exists across nearly all mammals studied. The biological purpose is clear: from a purely reproductive standpoint, mating with multiple novel partners spreads genes more effectively than mating repeatedly with the same partner."
            ),
            ContentBlock.ScientificFact(
                "Fiorino et al. (1997) measured dopamine levels in rats during the Coolidge Effect and found that dopamine surged to near-initial levels with each new partner, even after the animal was sexually exhausted. The reward circuit essentially \u201Creset\u201D with novelty.",
                "Fiorino, Coury & Phillips, 1997 \u2014 Journal of Neuroscience"
            ),
            ContentBlock.Header("Internet Pornography: Infinite Novelty"),
            ContentBlock.Paragraph(
                "In nature, the Coolidge Effect is constrained by reality. Finding a new mate takes time, energy, and risk. But internet pornography removes every constraint. With a single click, you can trigger the Coolidge Effect endlessly \u2014 new face, new body, new scenario, new dopamine surge."
            ),
            ContentBlock.Paragraph(
                "This is why a pornography session can last hours. Your conscious mind may want to stop, but your reward circuit is locked in a loop: novelty \u2192 dopamine \u2192 brief satisfaction \u2192 habituation \u2192 seek more novelty. Each new image promises the satisfaction that the last one failed to deliver."
            ),
            ContentBlock.BulletPoints(
                title = "How the Coolidge Effect Manifests in Pornography Use:",
                points = listOf(
                    "Opening multiple tabs or windows simultaneously",
                    "Spending more time searching than actually watching",
                    "Feeling that the \u201Cperfect\u201D content is always just one more click away",
                    "Rapidly cycling through videos, watching only seconds of each",
                    "Escalating to categories you never would have searched initially",
                    "The seeking/hunting phase becoming more exciting than the content itself",
                    "Feeling unable to finish with just one video or image"
                )
            ),
            ContentBlock.Header("The Seeking Circuit"),
            ContentBlock.Paragraph(
                "Neuroscientist Jaak Panksepp identified what he called the SEEKING system \u2014 a dopamine-driven neural circuit that creates the urge to search, explore, and anticipate rewards. This system evolved to motivate our ancestors to explore their environment, find food, and seek opportunities."
            ),
            ContentBlock.Paragraph(
                "Internet pornography puts this seeking circuit into overdrive. The endless scroll, the infinite thumbnails, the anticipation of what the next click might reveal \u2014 these all activate the seeking system far more powerfully than any natural stimulus. Your brain enters a state of compulsive searching that feels almost involuntary."
            ),
            ContentBlock.ScientificFact(
                "Research shows that the anticipation of a reward (the seeking phase) actually releases more dopamine than the reward itself. This is why pornography users spend more time browsing and searching than consuming \u2014 the hunt IS the high.",
                "Georgiadis & Kringelbach, 2012 \u2014 Progress in Neurobiology"
            ),
            ContentBlock.Header("Breaking the Novelty Trap"),
            ContentBlock.Paragraph(
                "Understanding the Coolidge Effect gives you a crucial cognitive tool. When you feel the pull to \u201Cjust check one more thing,\u201D you can now recognize it for what it is: a primitive biological reflex being exploited by artificial stimuli. The satisfaction you are seeking does not exist at the end of that click."
            ),
            ContentBlock.BulletPoints(
                title = "Counter-Strategies:",
                points = listOf(
                    "Name it: \u201CThis is the Coolidge Effect. My brain wants novelty, not satisfaction.\u201D",
                    "Remind yourself: the next click will not satisfy \u2014 it will only deepen the craving",
                    "Recognize the seeking state: if you are browsing/searching compulsively, you are already in the trap",
                    "Interrupt the pattern physically: stand up, leave the room, do push-ups, make wudu",
                    "Redirect the seeking energy: learn something new, read Quran, call a friend"
                )
            ),
            ContentBlock.Warning(
                "The Coolidge Effect means that pornography use will ALWAYS escalate without intervention. There is no \u201Cstable\u201D level of consumption. Your brain will always demand more novelty, more intensity, more stimulation. The only winning move is to stop feeding the cycle entirely."
            ),
            ContentBlock.Tip(
                "Next time you feel the urge pulling you toward a screen, pause and ask: \u201CWill this actually satisfy me, or will I still be searching 30 minutes from now?\u201D You already know the answer."
            )
        )
    )

    // ═══════════════════════════════════════════════════════════
    // ARTICLE 3: DeltaFosB \u2014 The Addiction Molecule
    // ═══════════════════════════════════════════════════════════
    private fun article3DeltaFosB() = KnowledgeArticle(
        id = "science_3",
        categoryId = "science",
        title = "DeltaFosB: The Molecular Switch of Addiction",
        summary = "The protein that physically wires addiction into your brain \u2014 and the timeline for unwiring it",
        readTimeMinutes = 7,
        isArabic = false,
        references = listOf(
            "Nestler, E.J. (2008). Transcriptional mechanisms of addiction: Role of \u0394FosB. Philosophical Transactions of the Royal Society B, 363(1507), 3245\u20133255.",
            "Nestler, E.J. (2005). Is there a common molecular pathway for addiction? Nature Neuroscience, 8(11), 1445\u20131449.",
            "Pitchers, K.K., et al. (2013). Natural and Drug Rewards Act on Common Neural Plasticity Mechanisms with \u0394FosB as a Key Mediator. Journal of Neuroscience, 33(8), 3434\u20133442.",
            "Hilton, D.L. (2013). Pornography addiction \u2014 a supranormal stimulus considered in the context of neuroplasticity. Socioaffective Neuroscience & Psychology, 3, 20767."
        ),
        content = listOf(
            ContentBlock.Header("The Molecular Basis of Addiction"),
            ContentBlock.Paragraph(
                "For decades, addiction was considered a moral failing \u2014 a weakness of character. Modern neuroscience has shattered this view. Addiction is a physical change in brain structure and chemistry, and at the molecular level, one protein plays a starring role: DeltaFosB (\u0394FosB)."
            ),
            ContentBlock.Paragraph(
                "DeltaFosB is a transcription factor \u2014 a protein that turns genes on and off. When you repeatedly overstimulate your reward circuit (through drugs, pornography, or other compulsive behaviors), DeltaFosB accumulates in the nucleus accumbens, your brain\u2019s reward center. Once there, it initiates a cascade of genetic changes that physically restructure your neural circuitry."
            ),
            ContentBlock.ScientificFact(
                "Dr. Eric Nestler at Mount Sinai School of Medicine, the world\u2019s leading researcher on DeltaFosB, demonstrated that this protein accumulates in the reward circuit with ALL forms of addiction and compulsive behavior. He called it a \u201Csustained molecular switch\u201D that transitions the brain from casual use to compulsive use.",
                "Nestler, E.J. (2008) \u2014 Mount Sinai School of Medicine, published in Philosophical Transactions of the Royal Society"
            ),
            ContentBlock.Header("What DeltaFosB Does to Your Brain"),
            ContentBlock.Paragraph(
                "Think of DeltaFosB as a construction foreman who arrives at your reward center and starts building permanent infrastructure for the addictive behavior. Specifically, it:"
            ),
            ContentBlock.BulletPoints(
                title = "",
                points = listOf(
                    "Strengthens the synaptic connections in the reward pathway, making the addictive behavior feel more rewarding",
                    "Increases the number of dendritic spines (connection points) on neurons in the reward circuit, creating more pathways for the compulsive behavior",
                    "Sensitizes the reward circuit to cues associated with the behavior (triggers), making them more powerful",
                    "Reduces the brain\u2019s response to natural rewards, making normal pleasures feel inadequate",
                    "Weakens the prefrontal cortex\u2019s ability to override impulses from the reward circuit"
                )
            ),
            ContentBlock.Paragraph(
                "The result is a brain that is simultaneously hypersensitive to addiction triggers and desensitized to normal life pleasures. The addictive behavior becomes the only thing that feels rewarding, while the brain\u2019s braking system is impaired."
            ),
            ContentBlock.Header("Pornography and DeltaFosB: The Direct Evidence"),
            ContentBlock.Paragraph(
                "In 2013, a landmark study by Pitchers et al. provided direct evidence that natural rewards \u2014 specifically sexual behavior \u2014 trigger the exact same DeltaFosB accumulation in the same brain regions as addictive drugs. This was the scientific nail in the coffin for anyone claiming pornography addiction \u201Cisn\u2019t real addiction.\u201D"
            ),
            ContentBlock.ScientificFact(
                "Pitchers et al. (2013) demonstrated that sexual experience caused DeltaFosB accumulation in the nucleus accumbens identical to that caused by drugs of abuse. They concluded: \u201CNatural and drug rewards act on common neural plasticity mechanisms with \u0394FosB as a key mediator.\u201D",
                "Pitchers, K.K., et al. (2013) \u2014 Published in Journal of Neuroscience"
            ),
            ContentBlock.Header("The Path Through a Forest"),
            ContentBlock.Paragraph(
                "Here\u2019s a helpful analogy. Imagine your brain is a dense forest. The first time you watch pornography, you\u2019re pushing through thick undergrowth \u2014 there\u2019s barely a trail. But each subsequent viewing wears the path down a little more. DeltaFosB is the crew that comes in and paves that trail into a road, then a highway."
            ),
            ContentBlock.Paragraph(
                "After months or years of use, you have a superhighway running directly from trigger to compulsive behavior. Walking that highway requires no effort \u2014 it\u2019s the path of least resistance. This is why addictive behaviors feel automatic, almost involuntary. The infrastructure has been built."
            ),
            ContentBlock.Header("The Good News: DeltaFosB Decays"),
            ContentBlock.Paragraph(
                "Unlike many proteins that are broken down within hours, DeltaFosB is remarkably stable \u2014 it persists for 6 to 8 weeks after the last exposure. This is why the first two months of recovery are the hardest. The molecular machinery of addiction is still physically present in your brain."
            ),
            ContentBlock.Paragraph(
                "But it DOES decay. After 6-8 weeks of abstinence, DeltaFosB levels begin to drop significantly. The superhighway starts to crack. The neural pathways begin to weaken through disuse. And the prefrontal cortex starts to reassert control."
            ),
            ContentBlock.BulletPoints(
                title = "The DeltaFosB Recovery Timeline:",
                points = listOf(
                    "Weeks 1-2: DeltaFosB levels remain high. Cravings are intense. The highway is fully operational.",
                    "Weeks 3-4: Slight decline begins. Urges are still strong but may come in waves rather than constantly.",
                    "Weeks 5-8: Significant decline in DeltaFosB. You\u2019ll notice urges becoming less frequent and less overwhelming.",
                    "Weeks 9-16: DeltaFosB approaching baseline levels. The old pathways are weakening substantially.",
                    "Months 4-12: Deep neural remodeling continues. New healthy pathways are strengthening. The superhighway is becoming an overgrown trail again."
                )
            ),
            ContentBlock.Warning(
                "A single relapse does NOT fully rebuild DeltaFosB to its peak levels. However, a binge (multiple sessions over days) can rapidly re-accumulate it. This is why preventing a single slip from becoming a binge is critical."
            ),
            ContentBlock.Tip(
                "Mark the date you started your current streak. Count the weeks. When you hit 6 weeks, know that DeltaFosB is declining. When you hit 8 weeks, know that the molecular machinery of your addiction is significantly weakened. Every day matters at the molecular level."
            )
        )
    )

    // ═══════════════════════════════════════════════════════════
    // ARTICLE 4: Prefrontal Cortex Damage
    // ═══════════════════════════════════════════════════════════
    private fun article4PrefrontalCortexDamage() = KnowledgeArticle(
        id = "science_4",
        categoryId = "science",
        title = "Your Weakened Control Tower: Prefrontal Cortex Damage",
        summary = "How addiction physically impairs your decision-making, impulse control, and ability to say no",
        readTimeMinutes = 7,
        isArabic = false,
        references = listOf(
            "Kuhn, S. & Gallinat, J. (2014). Brain structure and functional connectivity associated with pornography consumption: The brain on porn. JAMA Psychiatry, 71(7), 827\u2013834.",
            "Goldstein, R.Z. & Volkow, N.D. (2011). Dysfunction of the prefrontal cortex in addiction: neuroimaging findings and clinical implications. Nature Reviews Neuroscience, 12(11), 652\u2013669.",
            "Brand, M., Young, K.S., Laier, C., W\u00F6lfling, K., & Potenza, M.N. (2016). Integrating psychological and neurobiological considerations regarding the development and maintenance of specific Internet-use disorders. Neuroscience & Biobehavioral Reviews, 71, 252\u2013266.",
            "Arnsten, A.F.T. (2009). Stress signalling pathways that impair prefrontal cortex structure and function. Nature Reviews Neuroscience, 10(6), 410\u2013422."
        ),
        content = listOf(
            ContentBlock.Header("The Brain\u2019s CEO"),
            ContentBlock.Paragraph(
                "The prefrontal cortex (PFC) sits right behind your forehead and is the most recently evolved region of the human brain. It is responsible for what neuroscientists call \u201Cexecutive functions\u201D \u2014 the highest-level cognitive abilities that make us uniquely human:"
            ),
            ContentBlock.BulletPoints(
                title = "Executive Functions of the PFC:",
                points = listOf(
                    "Decision-making: Weighing options and choosing wisely",
                    "Impulse control: The ability to say \u201Cno\u201D to immediate desires",
                    "Long-term planning: Prioritizing future goals over instant gratification",
                    "Consequence prediction: Foreseeing the results of actions before taking them",
                    "Emotional regulation: Managing emotions rather than being controlled by them",
                    "Self-awareness: Monitoring your own behavior and thoughts",
                    "Moral reasoning: Aligning actions with your values and beliefs"
                )
            ),
            ContentBlock.Paragraph(
                "In essence, the PFC is the part of your brain that makes you YOU \u2014 your values, your intentions, your plans, your identity. When you say \u201CI want to quit but I can\u2019t,\u201D you are describing a battle between your PFC (the \u201CI want to quit\u201D) and your reward circuit (the \u201Cbut I can\u2019t\u201D)."
            ),
            ContentBlock.Header("How Addiction Damages the PFC"),
            ContentBlock.ScientificFact(
                "The landmark JAMA Psychiatry study by Kuhn & Gallinat (2014) found that men who consumed more pornography had measurably less gray matter volume in the right caudate nucleus AND weaker functional connectivity between the reward center and the prefrontal cortex. The brain\u2019s control tower was physically impaired.",
                "Kuhn & Gallinat (2014) \u2014 Published in JAMA Psychiatry, the highest-ranked psychiatry journal in the world"
            ),
            ContentBlock.Paragraph(
                "This finding is consistent with all other addiction research. Goldstein & Volkow (2011), in their comprehensive review published in Nature Reviews Neuroscience, identified PFC dysfunction as a hallmark of all addictions \u2014 a condition they termed \u201CImpaired Response Inhibition and Salience Attribution\u201D (iRISA)."
            ),
            ContentBlock.BulletPoints(
                title = "What PFC Damage Feels Like in Daily Life:",
                points = listOf(
                    "You \u201Cdecide\u201D to quit but find yourself watching again hours later",
                    "You cannot stop once you start, even when you desperately want to",
                    "You make promises to yourself that you consistently break",
                    "You underestimate the consequences of \u201Cjust one peek\u201D",
                    "You feel like you\u2019re watching yourself act against your own will",
                    "Your ability to focus, study, and work has noticeably declined",
                    "Small frustrations trigger disproportionate emotional reactions",
                    "You struggle to follow through on goals and commitments"
                )
            ),
            ContentBlock.Header("The Hypofrontality Trap"),
            ContentBlock.Paragraph(
                "Neuroscientists use the term \u201Chypofrontality\u201D to describe the weakened state of the PFC in addiction. It means your frontal lobes are operating below their normal capacity. The implications are profound and create a vicious cycle:"
            ),
            ContentBlock.BulletPoints(
                title = "The Hypofrontality Cycle:",
                points = listOf(
                    "1. Addiction weakens the PFC (less impulse control)",
                    "2. Weaker PFC means less ability to resist urges",
                    "3. Less resistance means more consumption",
                    "4. More consumption further weakens the PFC",
                    "5. The cycle accelerates \u2014 each round makes the next one worse"
                )
            ),
            ContentBlock.Header("Rebuilding the PFC"),
            ContentBlock.Paragraph(
                "The PFC is highly neuroplastic \u2014 it can be rebuilt. But it requires deliberate effort. Every time you successfully resist an urge, you are literally strengthening the neural connections in your PFC. Every time you choose a hard, healthy action over an easy, harmful one, you are doing PFC exercise."
            ),
            ContentBlock.BulletPoints(
                title = "Evidence-Based PFC Strengthening Activities:",
                points = listOf(
                    "Resisting urges: The single most powerful PFC exercise. Each successful resistance builds the muscle.",
                    "Meditation and mindfulness: Multiple studies show meditation increases PFC gray matter density within 8 weeks (H\u00F6lzel et al., 2011).",
                    "Physical exercise: Aerobic exercise promotes BDNF (brain-derived neurotrophic factor), which supports PFC neurogenesis.",
                    "Quality sleep: The PFC is the first region impaired by sleep deprivation and the first to benefit from recovery sleep.",
                    "Learning and reading: Complex cognitive tasks strengthen PFC circuits.",
                    "Delayed gratification practice: Choosing to wait for a better reward over an immediate one trains the PFC directly.",
                    "Reducing screen stimulation: Less passive scrolling allows the PFC to recover from chronic overstimulation."
                )
            ),
            ContentBlock.ScientificFact(
                "Arnsten (2009) showed that stress hormones (particularly cortisol and norepinephrine) directly impair PFC function. This means stress literally weakens your ability to resist urges \u2014 which is why most relapses happen during stressful periods. Managing stress is not optional in recovery; it is essential.",
                "Arnsten, A.F.T. (2009) \u2014 Published in Nature Reviews Neuroscience"
            ),
            ContentBlock.Warning(
                "Your PFC is weakened but not destroyed. It still functions \u2014 the fact that you feel guilt, that you want to change, that you are reading this article \u2014 all of this is your PFC at work. The goal is to strengthen it until it can consistently override the reward circuit\u2019s demands."
            ),
            ContentBlock.Tip(
                "Start a \u201Chard thing\u201D habit. Every day, deliberately do one thing that requires discipline and delayed gratification: cold shower, exercise, memorize an ayah, fast a voluntary day. Each one is a rep for your prefrontal cortex."
            )
        )
    )

    // ═══════════════════════════════════════════════════════════
    // ARTICLE 5: The Withdrawal Timeline
    // ═══════════════════════════════════════════════════════════
    private fun article5TheWithdrawalTimeline() = KnowledgeArticle(
        id = "science_5",
        categoryId = "science",
        title = "The Withdrawal Timeline: What to Expect When Your Brain Reboots",
        summary = "A week-by-week guide to withdrawal symptoms, flatlines, and neurological recovery",
        readTimeMinutes = 8,
        isArabic = false,
        references = listOf(
            "Volkow, N.D., et al. (2001). Association of dopamine transporter reduction with psychomotor impairment in methamphetamine abusers. American Journal of Psychiatry, 158(3), 377\u2013382.",
            "Zhou, Y., et al. (2011). Voluntary and involuntary attention affect face recognition differently in abstinent heroin users. Neuropsychologia, 49(7), 2025\u20132032.",
            "Hilton, D.L. (2013). Pornography addiction \u2014 a supranormal stimulus considered in the context of neuroplasticity. Socioaffective Neuroscience & Psychology, 3, 20767.",
            "Doidge, N. (2007). The Brain That Changes Itself. Penguin Books.",
            "Wilson, G. (2014). Your Brain on Porn: Internet Pornography and the Emerging Science of Addiction. Commonwealth Publishing."
        ),
        content = listOf(
            ContentBlock.Header("Your Brain is Rebooting"),
            ContentBlock.Paragraph(
                "When you stop consuming pornography after regular use, your brain enters a withdrawal and recovery process. This is not metaphorical \u2014 it is a measurable neurological event. Your brain is recalibrating its dopamine system, rebuilding receptor density, weakening addictive pathways, and strengthening dormant ones."
            ),
            ContentBlock.Paragraph(
                "Knowing what to expect at each stage removes fear, prevents panic, and helps you push through the hardest moments. This timeline is compiled from neuroscience research and the reports of thousands of men who have successfully recovered."
            ),
            ContentBlock.Header("Days 1\u20137: The Acute Withdrawal Phase"),
            ContentBlock.Paragraph(
                "The first week is typically the most intense. Your brain, accustomed to regular dopamine surges, is suddenly deprived of its primary source of stimulation. It responds with a cascade of withdrawal symptoms."
            ),
            ContentBlock.BulletPoints(
                title = "Common Symptoms:",
                points = listOf(
                    "Intense cravings and urges, especially at habitual usage times",
                    "Irritability and mood swings \u2014 small things feel disproportionately frustrating",
                    "Anxiety that may seem to have no specific cause",
                    "Difficulty sleeping or disturbed sleep patterns",
                    "Brain fog \u2014 difficulty concentrating, forgetfulness",
                    "Restlessness and an inability to sit still",
                    "Vivid dreams, sometimes sexual in nature",
                    "Physical tension, particularly in the chest and abdomen"
                )
            ),
            ContentBlock.ScientificFact(
                "Research on dopamine recovery in substance addicts (Volkow et al., 2001) showed that dopamine transporter levels begin to normalize within 1-2 weeks of abstinence, with significant recovery by 4 weeks. While studied in drug addiction, the dopaminergic mechanism is identical in behavioral addictions.",
                "Volkow, N.D., et al. (2001) \u2014 Published in American Journal of Psychiatry"
            ),
            ContentBlock.Tip(
                "Days 1-7 survival strategy: Focus only on getting through TODAY. Don\u2019t think about forever. Go to bed clean tonight \u2014 that\u2019s your only goal. Stack enough clean nights and you\u2019ll build momentum."
            ),
            ContentBlock.Header("Days 8\u201330: The Adjustment Phase"),
            ContentBlock.Paragraph(
                "The second through fourth weeks are characterized by fluctuation. You\u2019ll have good days where you feel strong and clear, followed by bad days where cravings return forcefully. This is normal \u2014 recovery is not linear."
            ),
            ContentBlock.BulletPoints(
                title = "What to Expect:",
                points = listOf(
                    "Urges come in WAVES rather than being constant \u2014 this is progress",
                    "The \u201Cflatline\u201D may begin: a period of low mood, low motivation, and low libido",
                    "Sleep begins to improve, often with more vivid dreams",
                    "Moments of clarity appear \u2014 brief windows where the fog lifts and you feel alive",
                    "Emotional sensitivity increases \u2014 you start feeling things more deeply",
                    "Social anxiety may temporarily increase before improving",
                    "Energy levels fluctuate \u2014 some days you\u2019re exhausted, others you\u2019re restless"
                )
            ),
            ContentBlock.Header("The Flatline: Don\u2019t Panic"),
            ContentBlock.Paragraph(
                "The flatline is one of the most misunderstood and frightening stages of recovery. Typically occurring between weeks 2-6, it is characterized by a period of emotional numbness, low motivation, and significantly reduced libido. Many men panic during this phase, fearing something is permanently wrong."
            ),
            ContentBlock.Paragraph(
                "The flatline is actually a POSITIVE sign. It means your brain is in deep recovery mode. The dopamine system, after years of overstimulation, is recalibrating to normal levels. During this process, the system temporarily undershoots \u2014 producing less dopamine than baseline. This is what causes the numbness."
            ),
            ContentBlock.Warning(
                "CRITICAL: Do NOT \u201Ctest\u201D yourself during the flatline. Many men, frightened by the low libido, watch pornography to \u201Ccheck if everything still works.\u201D This is the addiction tricking you. Your body is fine. Your brain is healing. Testing sets your recovery back significantly."
            ),
            ContentBlock.Header("Days 31\u201390: The Rewiring Phase"),
            ContentBlock.Paragraph(
                "This is where the magic happens. Your brain is actively rewiring \u2014 DeltaFosB is declining, dopamine receptors are regenerating, the prefrontal cortex is strengthening, and the old addictive pathways are weakening through disuse."
            ),
            ContentBlock.BulletPoints(
                title = "Positive Changes You\u2019ll Notice:",
                points = listOf(
                    "Mental clarity improves dramatically \u2014 like putting on glasses for the first time",
                    "Normal pleasures return \u2014 food tastes better, music sounds richer, nature feels more alive",
                    "Confidence begins rebuilding \u2014 you carry yourself differently",
                    "Eye contact becomes comfortable rather than shameful",
                    "Emotional depth returns \u2014 you can feel genuine joy, and sometimes genuine sadness",
                    "Motivation for goals and projects increases",
                    "Social interactions feel more natural and rewarding",
                    "Urges become less frequent AND less intense"
                )
            ),
            ContentBlock.Paragraph(
                "However, this phase also carries a risk: overconfidence. Many men, feeling so much better, begin to believe they are \u201Ccured\u201D and can handle \u201Cjust a peek.\u201D This is the most common cause of relapse in the 60-90 day window."
            ),
            ContentBlock.Header("Days 90+: Deep Healing"),
            ContentBlock.Paragraph(
                "After 90 days, the most significant neurological recovery has occurred. But healing continues for months and even years afterward, particularly for long-term heavy users. The brain continues to strengthen new pathways and weaken old ones."
            ),
            ContentBlock.BulletPoints(
                title = "Long-Term Recovery Markers:",
                points = listOf(
                    "Pornographic images begin to lose their emotional charge",
                    "Triggers that once felt irresistible now feel manageable",
                    "Your identity shifts \u2014 you no longer see yourself as an addict but as a person who recovered",
                    "Real human connection becomes deeply satisfying",
                    "Spiritual worship reaches depths you haven\u2019t experienced in years",
                    "The addiction becomes a memory rather than a daily battle"
                )
            ),
            ContentBlock.ScientificFact(
                "Doidge (2007) documented cases where neuroplastic changes from behavioral addictions continued to reverse for up to 2 years after cessation. The brain\u2019s capacity for recovery is remarkable, but it requires sustained abstinence and the active building of healthy replacement behaviors.",
                "Doidge, N. (2007) \u2014 The Brain That Changes Itself, Penguin Books"
            ),
            ContentBlock.Header("Important Notes on This Timeline"),
            ContentBlock.BulletPoints(
                title = "",
                points = listOf(
                    "This timeline is approximate \u2014 individual experiences vary based on duration of use, intensity, age, and overall health",
                    "Recovery is not linear \u2014 expect good days and bad days throughout",
                    "Longer and more intense usage generally requires longer recovery",
                    "Active recovery (exercise, healthy habits, therapy) accelerates healing",
                    "Passive abstinence (just not watching, but not building new habits) is slower",
                    "A single relapse does not reset all progress \u2014 but a binge can cause significant setback"
                )
            ),
            ContentBlock.Tip(
                "Print or screenshot this timeline. When you\u2019re in the dark valley of week 2, pull it out and remind yourself: this is temporary, this is expected, and your brain is actively healing. The light is ahead."
            )
        )
    )
}