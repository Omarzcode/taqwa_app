<div align="center">

# 🕌 Taqwa - Urge Journal App

### Your journey to purity, one urge at a time.

![Android](https://img.shields.io/badge/Platform-Android-3DDC84?logo=android&logoColor=white)
![Kotlin](https://img.shields.io/badge/Language-Kotlin-7F52FF?logo=kotlin&logoColor=white)
![Jetpack Compose](https://img.shields.io/badge/UI-Jetpack%20Compose-4285F4?logo=jetpackcompose&logoColor=white)
![License](https://img.shields.io/badge/License-MIT-yellow.svg)
![Version](https://img.shields.io/badge/Version-1.0.0-blue)

**Taqwa** (تقوى) is a personal Android app designed to help Muslims overcome porn addiction through guided self-reflection, Islamic reminders, and cognitive behavioral techniques.

*100% Offline • Zero Permissions • Complete Privacy*

</div>

---

## 📖 Table of Contents

- [About](#-about)
- [The Psychology Behind It](#-the-psychology-behind-it)
- [Features](#-features)
- [App Flow](#-app-flow)
- [Screenshots](#-screenshots)
- [Tech Stack](#-tech-stack)
- [Project Structure](#-project-structure)
- [Getting Started](#-getting-started)
- [Database Schema](#-database-schema)
- [Future Roadmap](#-future-roadmap)
- [Contributing](#-contributing)
- [License](#-license)
- [Acknowledgments](#-acknowledgments)

---

## 🎯 About

Taqwa is born from a real struggle and a real strategy. When an urge hits, the brain enters a "tunnel vision" state where:

- 🧠 The **Prefrontal Cortex** (rational thinking) goes offline
- 🔥 The **Limbic System** (emotions/desires) takes over
- ⚡ The **Dopamine System** screams for the hit

This app intervenes at that exact moment. It **interrupts the autopilot**, **reconnects you with your rational mind**, **guides you through self-reflection**, and **celebrates your victory**.

The strategy is based on a personal technique: *"Every time I think of watching porn, I ask myself — Why am I doing this? Why now? Why these feelings?"* — This helps study yourself and takes you away from acting on the urge.

Taqwa makes this process **fast, easy, and structured**.

---

## 🧠 The Psychology Behind It

### Cognitive Behavioral Technique (CBT)
The app uses a structured CBT approach:
1. **Awareness** — Recognize the urge (opening the app)
2. **Interruption** — Break the autopilot (breathing exercise)
3. **Cognitive Restructuring** — Challenge the distorted thinking (reality check)
4. **Spiritual Connection** — Reconnect with values (Islamic reminders)
5. **Personal Anchoring** — Your own words and promises
6. **Future Visualization** — See consequences of both paths
7. **Reflection** — Guided journaling to understand triggers
8. **Positive Reinforcement** — Victory celebration

### The 3-Phase Brain Recovery Model
```
Phase 1: INTERRUPT — Stop the autopilot brain (Breathing Screen)
Phase 2: RECONNECT — Bring back rational thinking (Reality Check + Reminders)
Phase 3: REFLECT  — Deep self-understanding (Guided Journal)
```

### The 7-Step Intervention Flow
```
When Urge Hits → User Reaches Out to App

STEP 1: ⏸️ Stop & Breathe (Breathing Screen)
└─ Activates parasympathetic nervous system
└─ 5-round guided breathing with visual animation
└─ Interrupts the automatic brain response

STEP 2: ❌ Reality Check (Reality Check Screen)
└─ Challenges distorted thinking patterns
└─ 7 truth statements to internalize
└─ Brings back rational prefrontal cortex

STEP 3: 🕌 Islamic Reminder (Islamic Reminder Screen)
└─ Reconnects with spiritual values
└─ Random Quranic ayah with reflection
└─ Taqwa as antidote to base desires

STEP 4: 💪 Personal Reminder (Personal Reminder Screen, Conditional)
└─ Shows user's own promises, duas, and why they're quitting
└─ Most powerful because it's their own voice
└─ Emotional anchor to personal values

STEP 5: 🎯 Future Self (Future Self Screen)
└─ Visualizes both outcomes (Path A vs Path B)
└─ Short-term pleasure vs long-term healing
└─ Strengthens commitment through visualization

STEP 6: 📋 Guided Questions (Questions Screen)
└─ 6-question journal captures:
   ├─ Situation context
   ├─ Feelings at the moment
   ├─ Real need behind urge
   ├─ Alternative activity chosen
   ├─ Urge strength rating (1-10)
   └─ Personal message to self
└─ Creates learning artifact
└─ Stored for pattern analysis

STEP 7: 🎉 Victory (Victory Screen)
└─ Celebration of success
└─ Counters incremented
└─ Milestone check (if streak milestone hit)
└─ Back to Home with updated stats

Result: User returns to Home Screen with VICTORY FEELING
├─ Dopamine from doing the right thing
├─ Entry saved in journal
├─ Pattern analysis updated
├─ New streak day recorded
└─ Motivation increased for next time
```

---

## ✨ Feature Breakdown

### 🔴 **Urge Intervention Flow** - The Core Feature
A revolutionary 7-step structured process that activates when experiencing an urge:
- **Full-screen experience** with no navigation bar distractions
- **Can't quit mid-flow** - Back button disabled, motivational dialog if attempted to exit
- **Guided from start to finish** with calming, supportive interface
- **Entry automatically saved** upon completion
- **Accounts for all brain states** - Physical (breathing), emotional (reality check), spiritual (Islamic reminder), personal (promises), future-focused (visualization), reflective (journaling), and rewarding (victory)
- **No judgment** - Compassionate, supportive language throughout
- **Mobile-optimized** - Quick to complete (5-10 minutes)

### 📋 **Guided Journal Questions** - The Learning Engine
6-question structured journal that captures critical context:

1. **Situation Context** (Q1) - Spatial awareness
   - Where you were (bedroom, work, car, street)
   - What you were doing (browsing, idle, stressed)
   - Environmental triggers
   
2. **Feelings** (Q2) - Emotional fingerprint
   - Multi-select from 12+ emotions
   - Identifies emotional patterns
   - Links feelings to urge strength
   
3. **Real Need** (Q3) - Root cause identification
   - What do you actually need? (Connection, escape, validation, dopamine, etc.)
   - Separates urge from need
   - Enables healthy alternative strategies
   
4. **Alternative Activity** (Q4) - Strategy tracking
   - What did you do instead?
   - Which alternatives work best for you?
   - Data-driven recommendation building
   
5. **Urge Strength** (Q5) - Intensity rating
   - 1-10 scale slider
   - Tracks if urges are weakening
   - Measures intervention effectiveness
   
6. **Personal Message** (Q6) - Raw journaling
   - Free-form text to yourself
   - Emotional expression
   - Creates personal artifact

### 📖 **Past Entries Browser** - Your Journal History
- Chronological list of all recorded urge responses
- Quick preview cards with key info (date, feelings, urge strength)
- Tap to view full entry details
- Swipe-to-delete with confirmation
- Statistics showing total entries count
- Filter/search by date range or criteria
- Visual color-coding by urge strength

### 🔥 **Streak Tracker** - Your Motivation Engine
- **Current Streak**: Days since last relapse (prominent display)
- **Longest Streak**: Peak achievement recorded
- **Total Relapses**: Honest counter
- **Brain Recovery Messages**: Contextual healing insights based on streak length
- **Milestone Celebrations**: Animated dialogs at key achievements:
  - 🔥 7 days (Challenge Yourself)
  - 🔥 14 days (Consistent Strength)
  - 🔥 30 days (30-Day Master)
  - 💪 60 days (Two Months Strong)
  - 💪 90 days (Ninety Days of Power)
  - 🏆 180 days (Half-Year Warrior)
  - 🏆 365 days (One Year Clean)
  - Each milestone has customized celebration message
  - Icons and visuals that inspire

### 📉 **Relapse Management** - Compassionate Handling
- **"I Relapsed" Button**: Accessible, non-judgmental
- **Documentation Screen**: Capture what triggered the relapse
- **Relapse History**: View all past relapses with:
  - Date of relapse
  - Documented reason
  - Streak that was lost
  - Time since previous relapse
- **Pattern Recognition**: Identify common relapse triggers
- **Trend Analysis**: Are relapses decreasing in frequency?
- **Motivational Support**: Immediate Islamic and psychological encouragement
- **Quick Recommitment**: Right back to new streak after reset

### 📊 **Pattern Analysis** - Data-Driven Insights
- Requires minimum 3 entries to activate
- **Time Patterns**: When urges occur most frequently
  - Morning (5am-12pm)
  - Afternoon (12pm-5pm)
  - Evening (5pm-11pm)
  - Night (11pm-3am)
  - Late Night (3am-5am)
- **Emotional Triggers**: Most common feelings during urges
  - Shows frequency and intensity
  - Which emotions are strongest triggers
- **Needs Analysis**: What you actually need behind urges
  - Connection/Intimacy
  - Escape/Numbing
  - Dopamine rush
  - Validation
  - Control
  - Relief from anxiety
  - Other
- **Alternative Effectiveness**: Which activities work best
  - Walk
  - Exercise
  - Meditation
  - Prayer
  - Quran reading
  - Social connection
  - And more
- **Urge Strength Trends**: Are urges getting weaker?
  - Graph showing progression
  - Weekly averages
  - Improvement percentage
- **Weekly Comparison**: This week vs last week
  - Entry frequency
  - Average urge strength
  - Relapse status
- **Auto-Generated Insights**: AI-powered personalized messages
  - "Your urges spike most during evenings"
  - "When you feel lonely, urges are 40% stronger"
  - "Calling a friend works for 80% of your urges"
  - "Your average urge strength has decreased by 25% this month"

### 📝 **Promise Wall** - Personal Accountability
- **Why I'm Quitting**: Core personal motivation
  - Your stated reason for recovery
  - Displayed during intervention flow (Personal Reminder Screen)
  - Emotional anchor to deeper purpose
  
- **My Promises**: Personal commitments
  - List of self-commitments to recovery
  - Examples: "Guard my eyes", "Be present with family", "Daily prayer"
  - Customizable - add/edit/delete
  - Shown randomly during intervention (Personal Reminder Screen)
  
- **My Duas**: Islamic prayers and supplications
  - Write your own dua
  - Or select from pre-written options
  - Arabic and English versions
  - Invocation of divine support
  - Displayed during intervention
  
- **Personal Reminders**: Custom motivational messages
  - Create messages that resonate with you
  - "Your future family needs your strength"
  - "Every clean day rewires my brain"
  - Randomly selected during intervention flow
  - Most powerful because they're YOUR words

### 🕌 **Islamic Integration** - Spiritual Grounding
- **Curated Quranic Verses**: 5 ayahs randomly selected during intervention
  - "Indeed, with hardship comes ease" (Ash-Sharh 94:6)
  - "And whoever fears Allah, He will make a way out" (At-Talaq 65:2)
  - "So remember Me; I will remember you" (Al-Baqarah 2:152)
  - And more contextually relevant verses
- **Daily Rotation**: 17+ ayahs cycle through home screen daily
- **Complete Verses**: Arabic text with English translation
- **Tafsir (Explanation)**: Brief theological interpretation
- **Victory Ayahs**: Special verses for milestone celebrations
- **Recovery Hadith**: Islamic teachings on struggle and perseverance
- **Compassion on Relapse**: Ayahs about repentance and divine mercy

### 🔒 **Privacy & Security** - You're Safe
- **100% Offline**: Zero internet connection required or used
- **Zero Permissions**: App requests nothing from your phone
  - No location tracking
  - No contacts access
  - No browser history
  - No audio/video recording
- **Local Storage Only**: All data stays exclusively on your device
- **No Analytics**: No tracking, no data collection
- **No Accounts**: No sign-up, no cloud sync, no usernames
- **Complete Anonymity**: App doesn't know who you are
- **Open Source**: Code transparency for security audit

### 🎓 **Onboarding** - Personalized Setup
- First-time users guided through setup
- Collects personal motivation, initial promise, and initial dua
- Data stored for later display in intervention flow
- Ensures every user has personalized reminders
- Optional skipping for returning users

### 📅 **Calendar View** - Visual Recovery Journey
- Month grid with entry indicators
- Streak visualization showing continuous days
- Color-coded: Green for entries, Red for relapses, Gray for no activity
- Tap individual days to view entries
- Navigate forward/backward through months
- Quick statistics for selected timeframe

---

## 🔄 App Flow

### Main Navigation
```
┌─────────────┐
│   HOME      │
│             │
│ 🔴 Urge ───┼──→ Intervention Flow
│ 📖 Entries ─┼──→ Past Entries → Entry Detail
│ 📊 Patterns─┼──→ Pattern Analysis
│ 📝 Promises─┼──→ Promise Wall
│ 📉 Relapses─┼──→ Relapse History
│ 😔 Reset ───┼──→ Reset Streak
└─────────────┘
```

### Intervention Flow
```
🔴 Urge Button
↓
⏸️ Stop & Breathe (5 breaths, animated)
↓
❌ Reality Check (7 lines, timed)
↓
🤲 Islamic Reminder (random ayah)
↓
📝 Personal Reminder (if content exists)
↓
📅 Future Self (Path A vs Path B)
↓
📋 Questions (6 guided questions)
↓
🏆 Victory Screen (celebration + save)
↓
🏠 Back to Home (counter updated)
```

---

## � Complete Screen Guide

### **Main Navigation Screens** (Bottom Navigation Bar Visible)

#### **1. Home Screen** 🏠
The central hub of Taqwa with complete overview:
- **Streak Counter** (Prominent): Display current consecutive days without relapse
- **Urges Defeated Counter**: Total count of successfully completed intervention flows
- **Daily Quranic Verse**: Today's rotating ayah (changes daily) with:
  - Arabic text of the verse
  - English translation
  - Surah and Ayah reference
  - Context/reflection on the verse
- **Milestone Celebration Dialogs**: Animated popups when hitting streaks of 7, 14, 30, 60, 90, 180, 365 days
- **Brain Recovery Messages**: Contextual messages about neurological healing based on streak
- **Quick Action Buttons**:
  - 🔴 "I'm Experiencing an Urge" → Starts 7-step intervention flow
  - 📖 "Past Entries" → Browse journal entries
  - 📊 "Pattern Analysis" → View analytics
  - 📝 "Promises" → Manage personal commitments
  - 📉 "Relapses" → View relapse history
  - 😔 "I Relapsed" → Reset streak with compassion
- **Statistics Summary**: Overview of key metrics
- **Motivational Quote**: Rotating Islamic quotes for daily encouragement

#### **2. Past Entries Screen** 📔
Browse and review all recorded urge interventions:
- **Chronological Entry List**: All entries sorted by date (newest first)
- **Entry Card Preview**:
  - Entry date and time (timestamp)
  - Quick summary of situation
  - Feelings involved
  - Urge strength (1-10 rating, color-coded)
- **Tap to Expand**: View complete entry details
- **Swipe to Delete**: Remove entries with confirmation
- **Empty State**: Friendly message when no entries yet
- **Search/Filter**: Sort by date range, urge strength, or feeling
- **Entry Statistics**: Show total entries count

#### **3. Calendar Screen** 📅
Visual representation of recovery journey:
- **Month View Calendar**: Full month grid display
- **Entry Indicators**: Highlighted dates where entries were made
- **Streak Visualization**: Continuous highlighting showing active streak
- **Color Coding**: 
  - 🟢 Green for days with entries
  - 🔴 Red for days with relapses
  - ⚪ Gray for days without activity
- **Month Navigation**: Forward/backward navigation through months
- **Statistics for Selected Period**:
  - Total entries in selected timeframe
  - Active streaks shown visually
  - Pattern highlights
- **Tap Day**: View entries for specific day

#### **4. Pattern Analysis Screen** 📊
Data-driven insights for self-understanding:
- **Time Pattern Analysis**: 
  - When urges occur most (Morning/Afternoon/Evening/Night/Late Night)
  - Visual chart showing peak danger hours
  - Suggestions for protection strategies for peak times
- **Feelings Pattern**: 
  - Most common emotions during urges (Lonely, Bored, Tired, Anxious, etc.)
  - Frequency breakdown
  - Which feelings are strongest triggers
- **Real Needs Pattern**:
  - What you actually need behind urges (Connection, Escape, Dopamine, etc.)
  - Frequency of each need
  - Insights about root causes
- **Alternative Activity Effectiveness**:
  - Which alternatives work best for you
  - Success rate of each activity
  - Recommendations based on data
- **Urge Strength Trends**:
  - Graph showing if urges are getting weaker over time
  - Average urge strength over weeks
  - Progress visualization
- **Weekly Comparison**: This week vs last week metrics
- **Auto-Generated Insights**: AI-powered summary sentences:
  - "Your urges are strongest during evenings"
  - "When you're lonely, the urge is 40% stronger"
  - "Going for a walk works 80% of the time"
- **Minimum Requirements**: Needs at least 3 entries to display data

#### **5. Settings Screen** ⚙️
Configure app behavior and manage data:
- **Notification Settings**:
  - Enable/disable reminders
  - Set reminder times
  - Custom notification messages
- **Theme Customization**:
  - Dark mode (default)
  - Light mode option
  - Color scheme selection
- **Data Management**:
  - Reset all data (with confirmation warning)
  - Clear history while keeping streaks
  - Backup data option
- **About Section**:
  - App version (v1.0.0)
  - Build number
  - Last update date
  - Credits and acknowledgments
- **Privacy Policy**: View complete privacy terms
- **Feedback & Support**: How to report bugs or request features
- **Developer Info**: Links to GitHub, contact, etc.

---

### **Urge Intervention Flow Screens** (Full Screen, Bottom Nav Hidden)

*A 7-step guided process that activates when experiencing an urge. Cannot exit mid-flow via back button.*

#### **6. Breathing Screen** 🫁
**Step 1**: Calm the nervous system

- **Large Visual Breathing Circle**: 
  - Animated expanding/contracting circle
  - Synchronized to breathing pattern
  - Guides user through 5-round breathing cycle (4-4-4 box breathing)
- **Breathing Instructions**:
  - "Breathe In for 4..."
  - "Hold for 4..."
  - "Breathe Out for 4..."
- **Progress Indicator**: Shows which breath round (1/5, 2/5, etc.)
- **Calming Background**: Soft blues/purples, minimal animation
- **Timer**: Visual countdown for each phase
- **Scientific Basis**: Activates parasympathetic nervous system (rest & digest)
- **Completion**: Auto-advances to Reality Check after 5 rounds
- **Purpose**: Interrupt autopilot brain and biological urge response

#### **7. Reality Check Screen** ✓
**Step 2**: Challenge distorted thinking and rationalize

- **7 Reality Check Lines**: Collection of truth statements
  - "I'm just bored, not actually in trouble"
  - "This feeling will pass in 30 minutes"
  - "Giving in will make me feel worse later"
  - "I have beaten urges before, I can do it again"
  - "My future self will be grateful I didn't do this"
  - "This doesn't define me"
  - "I'm stronger than this temporary feeling"
- **Timed Display**: Each line shown for reflection
- **Interactive Elements**: 
  - Ability to add custom truth statements
  - Select which resonate most
  - Countdown timer shows time passing (urges reduce over time)
- **Reflection Prompts**: "Is this really true? How do I know?"
- **Psychology**: Using CBT cognitive restructuring to battle distorted thoughts
- **Next Button**: Manually advance to Islamic Reminder when ready
- **Purpose**: Bring back rational prefrontal cortex thinking

#### **8. Islamic Reminder Screen** 🕌
**Step 3**: Spiritual grounding and Islamic perspective

- **Random Quranic Verse** (one of 5 curated ayahs):
  - "Indeed, with hardship comes ease" (Ash-Sharh 94:6)
  - "And whoever fears Allah, He will make a way out for him" (At-Talaq 65:2)
  - "So remember Me; I will remember you" (Al-Baqarah 2:152)
  - "Verily with hardship, there is relief" (Al-Insherah 94:5-6)
  - Additional relevant ayahs based on context
- **Arabic Text**: Original Quranic verse in Arabic
- **English Translation**: Clear, accurate English rendering
- **Surah & Ayah Reference**: Chapter and verse number (e.g., Al-Baqarah 2:152)
- **Tafsir (Interpretation)**: Brief theological explanation of what verse means
- **Personal Reflection**: 
  - How does this verse apply to my struggle?
  - What is Allah saying to me right now?
  - Space for personal notes
- **Brain Science Connection**: Explanation of how Islamic spirituality helps:
  - Activates region of brain associated with meaning
  - Connecting with something larger than urge
  - Taqwa (piety) as antidote to base desires
- **Next Button**: Move to Personal Reminders
- **Purpose**: Reconnect with Islamic values and spiritual strength

#### **9. Personal Reminder Screen** 💪
**Step 4**: Leverage user's own words and commitments (Conditional)

*Displays all content from Promise Wall. Only shows if user has input reminders.*

- **Why I'm Quitting** (Personal Motivation):
  - Display user's stated reason for recovery
  - Example: "I want to be a good husband, father, and servant of Allah"
  - Emotional anchor to deeper purpose
- **Personal Promises** (Random Selection):
  - Display 1-3 random promises from user's promise list
  - Example: "I promise to protect my eyes and mind"
  - "I commit to prayer and Quran daily"
  - "I will be honest and transparent"
  - Rotation prevents same promise every time
- **Personal Duas** (Islamic Prayers):
  - Display user's custom dua if they wrote one
  - Can be:
    - "O Allah, protect me from this weakness"
    - User's own Arabic/English prayers
    - Pre-written dua options
  - Invocation of divine help
- **Personal Reminders** (Random):
  - Motivational messages user defined during onboarding or setup
  - Example: "Remember your future family deserves your best"
  - "Your brain is healing, be patient with yourself"
  - "One day at a time"
  - "You've overcome this before, you can do it again"
- **Emotional Connection**: 
  - These are user's OWN words, more powerful than app-generated text
  - Direct connection to personal values and motivation
  - Accountability through self-commitment
- **Skip Option**: If minimal content, user can skip to next step
- **Next Button**: Move to Future Self
- **Purpose**: Anchor to personal values and motivation

#### **10. Future Self Screen** 🎯
**Step 5**: Visualize consequences and future aspirations

- **Two Paths Comparison**:
  
  **Path A - Give In** (Left side, red tint):
  - "5 minutes of pleasure"
  - "Feeling of shame and regret"
  - "Streak resets to 0"
  - "I'm back to square one"
  - "Brain dopamine system gets trained for this"
  - "I feel disgusted with myself"
  - "Family disappointment if discovered"
  
  **Path B - Resist** (Right side, green tint):
  - "Immediate sense of power"
  - "Streak grows (Day X becomes Day X+1)"
  - "Brain healing continues"
  - "I'm becoming the person I want to be"
  - "Sleep well tonight with no regret"
  - "One day closer to full recovery"
  - "Free from this chain"

- **Visual Representation**:
  - Timeline or branching path visualization
  - Consequence tree showing short & long-term effects
  - Emoticon faces showing feelings on each path
  - Stream of consciousness for each path

- **Reflection Questions**:
  - "Which version of myself do I want to be?"
  - "What will future me thank me for?"
  - "What's 5 minutes of pleasure vs. my entire healing?"
  
- **Personal Journaling**: Space to write what future self looks like
- **Activation**: Engages visualization centers of brain
- **Next Button**: Proceed to Guided Questions
- **Purpose**: Strengthen commitment through consequence visualization

#### **11. Questions Screen** ❓
**Step 6**: Structured self-reflection and pattern discovery

*6 guided questions capture rich context for later analysis*

- **Question 1 - Situation Context** 📍
  - "Where are you and what were you doing?"
  - Text input for detailed context
  - Examples: "At home in bed", "Work break room alone"
  - Captures trigger location/activity

- **Question 2 - Feelings** 💭
  - "What were you feeling?"
  - Multi-select from list:
    - Lonely, Bored, Stressed, Anxious, Tired, Angry
    - Sad, Envious, Rejected, Ashamed, Excited, Overwhelmed
  - Comma-separated storage for analysis
  - Connects urge to emotional state

- **Question 3 - Real Need** 🎯
  - "What's the real need behind the urge?"
  - Multi-select options:
    - Connection/Intimacy, Escape/Numbing, Dopamine hit
    - Validation, Control, Relief from anxiety, Self-soothing
    - Boredom distraction, Rebellion, Habit
  - Teaches self-awareness of actual need
  - Enables finding healthy alternatives

- **Question 4 - Alternative Activity** 🔄
  - "What alternative activity did you choose instead?"
  - Multi-select from options:
    - Walk, Exercise, Meditation, Prayer, Quran reading
    - Call friend, Shower, Sleep, Eat, Read book
    - Creative activity, Work/study, Video (non-sexual)
  - Tracks what actually works for user
  - Builds healthy habit associations

- **Question 5 - Urge Strength** 💢
  - "Rate your urge strength 1-10"
  - Interactive slider from 1-10
  - Visual feedback (color changes at different levels)
  - Tracks if urges are weakening over time
  - Data point for pattern analysis

- **Question 6 - Personal Message** ✍️
  - "Free text message to yourself"
  - Structured journaling prompt
  - Example: "I felt lonely tonight, but talking to a friend helped. The urge passed after 30 minutes."
  - Allows raw emotions and thoughts
  - Creates emotional artifact for reflection

- **Progress Indicator**: Shows current question number
- **Next Button**: Move through each question
- **Save and Continue**: Submit all answers to database
- **Purpose**: Create journaling artifact, discover patterns, build self-awareness

#### **12. Victory Screen** 🎉
**Step 7**: Celebration and positive reinforcement

- **Congratulations Message**: 
  - "You defeated the urge!"
  - "This victory was all you!"
  - "Your strength is remarkable!"
  - "You just made a choice for healing"

- **Animated Celebration**:
  - Confetti animation
  - Pulsing victory graphic
  - Uplifting colors and sound

- **Updated Statistics**:
  - NEW urges defeated count (incremented)
  - Current streak still showing
  - Brain healing progress message

- **Milestone Check**:
  - If hit a streak milestone (7, 14, 30, 60, 90, 180, 365 days):
    - Special animated dialog
    - Customized milestone message
    - Celebration fanfare
    - Quranic ayah specifically for that milestone

- **Reflection**:
  - "How do you feel now?"
  - "What helped the most?"
  - "What will you do next?"

- **Back to Home**: Button returns to home screen
  - All counters updated
  - New entry visible in past entries
  - Pattern analysis refreshed

- **Purpose**: Positive reinforcement, dopamine hit from doing the right thing
- **Neurological Effect**: Trains brain to associate defeating urge with reward

---

### **Secondary/Detail Screens**

#### **13. Entry Detail Screen** 🔍
View complete journal entry:
- **Full Entry Display**:
  - All 6 question answers in detail
  - Formatted and readable view
  - Timestamp (date and exact time)
- **Urge Strength Graph**: Visual bar showing intensity 1-10
- **Entry Statistics**: How long ago, where in streak, etc.
- **Reflection Section**: Personal notes added later
- **Edit Button**: Modify entry if needed
- **Delete Button**: Remove entry with confirmation
- **Navigation**:
  - Previous entry button
  - Next entry button
  - Back to past entries list

#### **14. Reset Screen** ↩️
Document relapses compassionately:
- **"I Relapsed" Message**: Non-judgmental, compassionate framing
- **What Led to Relapse**:
  - Text input for detailed explanation
  - Triggers that led to relapse
  - What happened in the moment
- **Reflection Questions**:
  - What triggered you?
  - What could you try differently?
  - What support do you need?
- **Islamic Encouragement**:
  - Quran ayah about repentance
  - Reminder that relapse doesn't define you
  - Call to immediate recommitment
- **Streak Reset Confirmation**:
  - Shows which streak will reset to 0
  - Documents reason in relapse history
  - Shows longest streak preserved (not lost)
- **Moving Forward**:
  - Tips for getting back on track immediately
  - Quick actions to take next
  - Call to action: "Let's start again, right now"
- **Psychology**: Prevents shame spiral, enables learning and recommitment

#### **15. Relapse History Screen** 📈
View all past relapses:
- **Chronological List**: All relapses with dates and details
- **Each Relapse Shows**:
  - Date of relapse
  - Reason documented
  - Streak lost (how many days)
  - Time since last relapse
- **Pattern Recognition**:
  - Most common relapse reasons
  - Time intervals between relapses
  - Relapse triggers
  - Seasonal patterns if applicable
- **Trend Analysis**:
  - Are relapses decreasing in frequency?
  - Are time gaps between relapses increasing?
  - Total relapse count
  - Current relapse-free period
- **Learning Points**: Extract lessons without judgment
- **Purpose**: Understand patterns, build protective strategies

#### **16. Promise Wall Screen** 🎨
Manage all personal commitments:
- **Why I'm Quitting**:
  - Large text display of personal motivation
  - Edit button to update
  - Example: "I want to be a good father, husband, and servant of Allah"
  - Shown during intervention flow as anchor
  
- **My Promises**:
  - List of personal commitments
  - Add new promise button
  - Edit/delete existing promises
  - Examples: 
    - "Guard my eyes and mind"
    - "Be present with family, not lost in screens"
    - "Daily Fajr prayer without fail"
    - "Honest conversations about struggles"
  - Sort/reorder by importance
  
- **My Duas** (Islamic Prayers):
  - Personal Islamic supplications
  - User can write custom duas
  - Or select from pre-written dua options
  - Arabic and English versions
  - Examples:
    - أَلْهَمْنِي رُشْدِي "O Allah, guide me to what's right"
  
- **Personal Reminders**:
  - Custom motivational messages
  - Shown randomly during intervention flow
  - Add/edit/delete messages
  - Examples:
    - "Your future family needs your strength"
    - "This is temporary, I am powerful"
    - "Every clean day rewires my brain"
  
- **Display Options**:
  - List view
  - Full card view
  - Print/export your wall
  
- **Purpose**: Personal accountability, emotional anchors, used in intervention flow

#### **17. Onboarding Screen** 🎓
First-time user setup guide:
- **Welcome Message**: 
  - Introduction to Taqwa
  - What the app does
  - How it will help
  
- **Safety & Privacy Assurance**:
  - "100% Offline"
  - "Zero Permissions"
  - "Complete Privacy"
  - "You're safe here"
  
- **Setup Step 1 - Why I'm Quitting** 🤔
  - "Why do you want to stop?"
  - Reflect on deep personal reasons
  - Examples:
    - "To be good Muslim and protect my iman"
    - "For my family's trust and my own dignity"
    - "To break free from addiction's chains"
  - Text input stores answer for future display
  
- **Setup Step 2 - Initial Promise** 💪
  - "Make a promise to yourself"
  - Personal commitment written down
  - Examples:
    - "I promise to guard my eyes and heart"
    - "I commit to building a healthy life"
  - Seen during intervention flows
  
- **Setup Step 3 - Initial Dua** 🤲
  - "What's your initial dua to Allah?"
  - Personal Islamic prayer
  - English or Arabic
  - Examples:
    - "O Allah, help me overcome this weakness"
    - "Guide me to purity and steadfastness"
  - Invocation of divine support
  
- **Progress Indicator**: Shows step 1/3, 2/3, 3/3
- **Skip Option**: For returning users with existing data
- **Save & Start**: Completion saves data and opens app
- **Purpose**: Personalize app, establish emotional commitment

---

## 📸 Screenshots

> *Add screenshots here after building the APK*
>
> Recommended screenshots set:
> 1. Home Screen - Main dashboard with streak counter
> 2. Breathing Screen - Animated breathing exercise (Step 1)
> 3. Reality Check Screen - Truth statement display (Step 2)
> 4. Islamic Reminder Screen - Quranic verse display (Step 3)
> 5. Personal Reminder Screen - User's own promises (Step 4)
> 6. Future Self Screen - Path A vs Path B comparison (Step 5)
> 7. Questions Screen - 6-question journal (Step 6)
> 8. Victory Screen - Celebration and milestone (Step 7)
> 9. Pattern Analysis Screen - Data insights and trends
> 10. Promise Wall Screen - Managing commitments
> 11. Calendar Screen - Visual recovery timeline
> 12. Past Entries Screen - Journal history

---

## � Prerequisites for Development
- Android Studio Hedgehog (2023.1.1) or later
- JDK 11 or later
- Android SDK 36
- Min SDK 26 (Android 8.0 Oreo)

## 🏗️ Developer Installation

1. **Clone the repository**
```bash
git clone https://github.com/Omarzcode/taqwa_app.git
```

2. **Open in Android Studio**
```
File → Open → Select the taqwa_app folder
```

3. **Sync Gradle**
```
Wait for automatic sync, or:
File → Sync Project with Gradle Files
```

4. **Run the app**
```
Click the green ▶️ Play button
Select your device/emulator
```

5. **Build APK for Testing/Distribution**
```
Build → Build Bundle(s) / APK(s) → Build APK(s)
APK location: app/build/outputs/apk/debug/app-debug.apk
```

---

## 📊 Daily Quranic Verses Feature

Taqwa includes a rotating collection of 17+ Quranic verses that appear on the home screen, changing daily:

**Current Rotation Pool:**
1. "Indeed, with hardship comes ease" - Ash-Sharh 94:5-6
2. "And whoever fears Allah, He will make a way out for him" - At-Talaq 65:2-3
3. "So remember Me; I will remember you" - Al-Baqarah 2:152
4. "Verily, in the remembrance of Allah do hearts find rest" - Ar-Ra'd 13:28
5. "Allah does not burden a soul beyond that it can bear" - Al-Baqarah 2:286
6. "O you who have believed, seek help through patience and prayer" - Al-Baqarah 2:153
7. "Indeed, Allah is with the patient" - Al-Baqarah 2:153
8. "And those who believe and do righteous deeds... there is no fear..." - Al-Baqarah 2:262
9. "The best of people are those who live the longest and do the best deeds" - At-Tirmidhi
10. "Satan's role is only to excite enmity and hatred" - Al-Maidah 5:91
11. "Indeed, the righteous will be in bliss" - Al-Infitar 82:13
12. "And hasten to forgiveness from your Lord" - Al-Imran 3:133
13. (Additional culturally relevant verses continue)

**Features:**
- **Daily Refresh**: Automatically changes at midnight
- **Storage**: Index and date stored in DailyQuranManager
- **Display**: Home screen shows current verse with:
  - 🕌 Arabic text (original Quranic language)
  - 📖 English translation
  - 📍 Surah and Ayah reference
  - 💭 Brief reflection/context
- **Thematic**: Verses selected for themes relevant to recovery:
  - Patience and perseverance
  - Taqwa and God-consciousness
  - Self-control and discipline
  - Mercy and compassion
  - Reliance on Allah (Tawakkul)
  - Hope and encouragement
- **Motivational**: Starts each day with spiritual grounding

---



| Technology | Purpose |
|-----------|---------|
| **Kotlin** | Primary programming language |
| **Jetpack Compose** | Modern declarative UI framework |
| **Material 3** | Design system and components |
| **Room Database** | Local SQLite database for journal entries |
| **SharedPreferences** | Streak data and promise wall storage |
| **Navigation Compose** | Screen navigation management |
| **ViewModel** | UI state management (MVVM architecture) |
| **Kotlin Coroutines** | Asynchronous operations |
| **Kotlin Flow** | Reactive data streams |

### Architecture
```
MVVM (Model-View-ViewModel)

┌──────────────────────────────────────────┐
│                  UI Layer                 │
│  (Compose Screens + Navigation)          │
├──────────────────────────────────────────┤
│              ViewModel Layer             │
│  (JournalViewModel - State Management)   │
├──────────────────────────────────────────┤
│               Data Layer                 │
│  ┌─────────────┐  ┌──────────────────┐  │
│  │    Room DB   │  │ SharedPreferences│  │
│  │  (Entries)   │  │ (Streak/Promise) │  │
│  └─────────────┘  └──────────────────┘  │
└──────────────────────────────────────────┘
```

---

## 📂 Project Structure

```
app/src/main/java/com/taqwa/journal/
│
├── 📁 data/
│   ├── 📁 database/
│   │   ├── JournalEntry.kt          # Room Entity - journal entry data model
│   │   ├── JournalDao.kt            # Data Access Object - database queries
│   │   └── JournalDatabase.kt       # Room Database singleton
│   │
│   ├── 📁 model/
│   │   └── QuestionData.kt          # Static data: feelings, needs, alternatives,
│   │                                 # reality check lines, Islamic reminders,
│   │                                 # victory ayahs
│   │
│   ├── 📁 preferences/
│   │   ├── StreakManager.kt          # Streak tracking, milestones, relapse history
│   │   └── PromiseManager.kt         # Promise wall data management
│   │
│   └── 📁 repository/
│       └── JournalRepository.kt      # Data repository - abstracts data sources
│
├── 📁 ui/
│   ├── 📁 theme/
│   │   ├── Color.kt                  # App color palette (dark theme)
│   │   ├── Theme.kt                  # Material 3 theme configuration
│   │   └── Type.kt                   # Typography definitions
│   │
│   ├── 📁 screens/
│   │   ├── HomeScreen.kt             # Main screen - stats, urge button, navigation
│   │   ├── BreathingScreen.kt        # Animated breathing exercise (5 breaths)
│   │   ├── RealityCheckScreen.kt     # Timed truth statements
│   │   ├── IslamicReminderScreen.kt  # Random Quran ayah + reflection
│   │   ├── PersonalReminderScreen.kt # User's own words shown during flow
│   │   ├── FutureSelfScreen.kt       # Path A vs Path B comparison
│   │   ├── QuestionsScreen.kt        # 6 guided journal questions
│   │   ├── VictoryScreen.kt          # Celebration + stats update
│   │   ├── PastEntriesScreen.kt      # Browse all journal entries
│   │   ├── EntryDetailScreen.kt      # Full view of a single entry
│   │   ├── ResetScreen.kt            # Compassionate relapse reset
│   │   ├── RelapseHistoryScreen.kt   # View all past relapses
│   │   ├── PatternAnalysisScreen.kt  # Auto-analyzed patterns & insights
│   │   └── PromiseWallScreen.kt      # Manage promises, duas, reminders
│   │
│   ├── 📁 components/
│   │   └── (reusable UI components)
│   │
│   ├── 📁 navigation/
│   │   └── NavGraph.kt               # Navigation routes & screen wiring
│   │
│   └── 📁 viewmodel/
│       └── JournalViewModel.kt       # Main ViewModel - all app state & logic
│
└── MainActivity.kt                    # Entry point - sets up theme & navigation
```

---

## 🚀 Getting Started

## 🚀 Getting Started

### **For New Users**

1. **Launch App** → Onboarding Screen appears automatically
2. **Complete Onboarding**:
   - Answer "Why are you quitting?" with personal motivation
   - Make an initial promise to yourself
   - Write an initial dua (Islamic prayer)
3. **Explore Home Screen** → Familiarize yourself with all buttons
4. **Review Your Promises** → Tap Promise Wall to set up reminders
5. **Set Personal Reminders** → Add custom motivational messages
6. **You're Ready!** → When an urge hits, tap the red urge button

### **When Experiencing an Urge**

1. **Open Taqwa** → Home Screen visible
2. **Tap "I'm Experiencing an Urge"** (prominent button)
3. **Follow the 7-Step Flow**:
   - ⏸️ **Breathing** - Calm your nervous system with guided breathing
   - ❌ **Reality Check** - Challenge your distorted thoughts
   - 🕌 **Islamic Reminder** - Connect with spirituality
   - 💪 **Personal Reminder** - Remember why you're doing this
   - 🎯 **Future Self** - Visualize both outcomes
   - 📋 **Questions** - Journal 6 reflection questions
   - 🎉 **Victory** - Celebrate defeating the urge!
4. **Entry Saved** → View in Past Entries anytime
5. **Return to Home** → Stats automatically updated
6. **Feel Proud** → You just made a powerful choice

### **Daily Practices**

- **Every Morning** ☀️
  - Check Home Screen for daily Quranic verse
  - Review current streak for motivation
  - Read your Promise Wall for daily reminders
  
- **Throughout the Day** 📱
  - When urges come, immediately open app
  - Follow intervention flow without skipping steps
  - Complete all journal questions thoroughly
  
- **Weekly Review** 📊
  - Check Pattern Analysis for insights
  - Review past entries to understand triggers
  - Update personal reminders based on learnings
  - Celebrate weekly streaks
  
- **Monthly Check-in** 📈
  - Review relapse history
  - Analyze which strategies work best
  - Update promises if needed
  - Celebrate monthly milestone if reached

### **When a Relapse Occurs** 😔

1. **Don't Beat Yourself Up** → This is part of recovery
2. **Open App** → Tap "I Relapsed" button
3. **Document on Reset Screen**:
   - Explain what triggered you
   - What feelings you had
   - What you could try differently next time
4. **Confirm Reset** → Streak resets to 0, saved in relapse history
5. **Read Islamic Encouragement** → Reminders you can come back
6. **Immediate Recommitment** → Start new streak RIGHT NOW
7. **Plan Better** → Use pattern analysis to prepare for next time
8. **Learn and Move Forward** → Every relapse has lessons

### **Using Pattern Analysis** 📊

- **Requires**: Minimum 3 journal entries
- **Shows**:
  - Time patterns (when urges happen most)
  - Emotional triggers (which feelings are strongest)
  - Real needs (what you actually need behind urge)
  - Effective alternatives (what activities work best)
  - Trends (are urges getting weaker?)
  - Auto-generated insights specific to YOU
- **How to Use**:
  - Identify your peak danger hours
  - Plan protective activities for those times
  - Notice your strongest trigger emotions
  - Develop healthy alternatives for your real needs
  - Track if your recovery is improving

---

## 🎯 How Everything Works Together

### **User Journey Overview**

```
New User Opens App
│
├─→ Onboarding Screen (First Time Only)
│   ├─ "Why are you quitting?" → Stored in PromiseManager
│   ├─ "Initial Promise" → Stored in PromiseManager
│   └─ "Initial Dua" → Stored in PromiseManager
│
└─→ Home Screen (Main Hub)
    │
    ├─→ 🔴 Urges Defeated Counter (updated weekly)
    ├─→ 🔥 Current Streak Display (from StreakManager)
    ├─→ 📖 Daily Quranic Verse (from DailyQuranManager)
    │
    └─→ When User Experiences Urge:
        │
        ├─→ Tap "I'm Experiencing an Urge"
        │
        └─→ 7-Step Intervention Flow
            (Full screen, bottom nav hidden)
            │
            ├─ Step 1: ⏸️ Breathing (Calm nervous system)
            ├─ Step 2: ❌ Reality Check (Challenge thoughts)
            ├─ Step 3: 🕌 Islamic Reminder (Spiritual ground)
            ├─ Step 4: 💪 Personal Reminder (Your promises, from PromiseManager)
            ├─ Step 5: 🎯 Future Self (Visualize outcomes)
            ├─ Step 6: 📋 Questions (6 questions)
            │           └─ Saved to JournalDatabase
            │           └─ Used for PatternAnalysis
            └─ Step 7: 🎉 Victory (Celebrate!)
                        └─ StreakManager: Increment urges defeated
                        └─ Return to Home with updated stats
                        └─ Check for milestone (StreakManager)
                        └─ Show milestone celebration if reached
```

### **Data Flow Architecture**

```
MVVM Architecture:
│
├─ UI Layer (Screens - Jetpack Compose)
│  ├─ HomeScreen
│  ├─ BreathingScreen, RealityCheckScreen, etc.
│  ├─ PastEntriesScreen, PatternAnalysisScreen
│  └─ PromiseWallScreen, SettingsScreen
│
├─ ViewModel Layer
│  └─ JournalViewModel (Central state management)
│     ├─ Exposes all data as StateFlow/Flow
│     ├─ Handles all business logic
│     └─ Manages navigation between screens
│
└─ Data Layer
   ├─ Room Database (JournalDatabase)
   │  └─ JournalEntry table
   │     ├─ Stores all journal entries
   │     ├─ Query: Get all entries by date range
   │     ├─ Query: Get entries by date for calendar
   │     └─ Accessed via JournalDao
   │
   ├─ SharedPreferences (4 Managers)
   │  ├─ StreakManager (Streak data)
   │  │  ├─ Current streak
   │  │  ├─ Longest streak
   │  │  ├─ Relapse history
   │  │  └─ Milestone tracking
   │  │
   │  ├─ PromiseManager (User commitments)
   │  │  ├─ Why I'm Quitting
   │  │  ├─ Promises list
   │  │  ├─ Personal duas
   │  │  └─ Personal reminders
   │  │
   │  ├─ DailyQuranManager (Daily verse)
   │  │  ├─ Current ayah index
   │  │  ├─ Last updated date
   │  │  └─ Auto-rotate daily
   │  │
   │  └─ OnboardingManager (Setup tracking)
   │     ├─ Completion flag
   │     └─ User inputs
   │
   └─ JournalRepository (Abstraction layer)
      ├─ getAll journals
      ├─ insertJournal
      ├─ deleteJournal
      └─ Exposes data as Flow for reactive updates
```

### **Data Persistence Strategy**

**Room Database** (for large, queryable data):
- JournalEntry records
- Each entry: 9 fields
- Grows over time as user journals more
- Enables complex queries for pattern analysis
- Survives app updates

**SharedPreferences** (for small, fast-access data):
- Streak information (current, longest, relapse history)
- Promise wall content (why quitting, promises, duas, reminders)
- Daily ayah index and date
- Onboarding completion flag
- Fast retrieval (no queries needed)

**Why This Split:**
- Database for evolving, queryable data (entries)
- Preferences for stable, frequently accessed data (streak, promises)
- Optimized for performance and battery life

---

## 🔐 Privacy & Security Deep Dive

### **What Taqwa NEVER Does**
- ❌ Never connects to internet
- ❌ Never sends data anywhere
- ❌ Never tracks location
- ❌ Never accesses contacts
- ❌ Never records calls
- ❌ Never requires account
- ❌ Never uses analytics
- ❌ Never stores cloud backup
- ❌ Never permission asks
- ❌ Never shows ads

### **What Taqwa ALWAYS Does**
- ✅ Always keeps data on device
- ✅ Always respects privacy
- ✅ Always operates offline
- ✅ Always maintains anonymity
- ✅ Always allows data deletion
- ✅ Always uses local storage only
- ✅ Always respects user control

### **Why This Matters**
Recovery is deeply personal. User should never worry if their recovery data is exposed. With Taqwa, there's literally no way for data to leave device unless user manually exports it.

---

## 📈 Understanding Your Data

### **What Gets Stored and Why**

**Journal Entries** (Room Database):
- **Timestamp**: Know when you had urges (time patterns)
- **Situation**: Understand environmental triggers (location patterns)
- **Feelings**: Identify emotional triggers (feeling patterns)
- **Real Need**: Know what you actually need (root cause analysis)
- **Alternative**: Track what works (behavior effectiveness)
- **Urge Strength**: See if urges weakening (progress measure)
- **Free Text**: Reflect on your own insights (self-discovery)

**Streak Data** (SharedPreferences):
- **Current/Longest Streak**: Motivation tracker
- **Total Relapses**: Honest self-assessment
- **Relapse History**: Learn from past (with dates and reasons)
- **Milestone Celebrations**: Acknowledge progress

**Promise Data** (SharedPreferences):
- **Why Quitting**: Keep purpose in focus
- **Promises**: Personal accountability
- **Duas**: Spiritual grounding
- **Reminders**: Daily motivation

**All Data** serves a purpose: **Self-understanding and growth**

---

## 🎨 UI/UX Philosophy

### **Design Principles**

1. **Compassion First**
   - Non-judgmental language
   - No shame or guilt messaging
   - Celebration of progress
   - Support on relapse

2. **Clarity Over Complexity**
   - Large, easy-to-tap buttons
   - Clear navigation flows
   - Minimal text, maximum impact
   - Dark theme reduces eye strain at night (peak urge time)

3. **Accessibility**
   - Large fonts
   - High contrast colors
   - Simple, single-purpose screens
   - Easy for all user types

4. **Speed**
   - App launches instantly
   - No loading screens
   - Intervention flow: 5-10 minutes max
   - Quick journal entry completion

5. **Spiritual Integration**
   - Islamic design elements
   - Quranic verses throughout
   - Respectful, dignified presentation
   - Aligned with Islamic values

---

## 💡 The Psychology Behind Each Screen

### **Why Each Step Exists**

**Breathing Screen**:
- 🧠 Activates parasympathetic nervous system
- ⏸️ Interrupts automatic brain response
- 🔄 Slows heart rate and breathing
- 💆 Creates moment to think clearly

**Reality Check Screen**:
- 🎯 Engages prefrontal cortex (rational mind)
- 💭 Challenges distorted thinking patterns
- 📝 Externalizes thoughts (makes them less powerful)
- ✓ Confirms reality vs. urge-driven thoughts

**Islamic Reminder Screen**:
- 🕌 Activates meaning-making brain regions
- 🙏 Connects to something larger than urge
- 📖 Provides perspective and wisdom
- ✨ Spiritual protection and grounding

**Personal Reminder Screen**:
- 💪 Uses your own voice (most powerful)
- 🎯 Connects to personal values
- 💝 Emotional engagement
- 🔗 Accountability to self

**Future Self Screen**:
- 🧠 Activates visualization centers
- 📊 Compares outcomes objectively
- ⏰ Future-focused thinking
- 🎯 Strengthens commitment

**Questions Screen**:
- 📚 Creates learning artifact
- 🔍 Builds self-awareness
- 📊 Enables pattern recognition
- ✍️ Externalizes experience

**Victory Screen**:
- 🎉 Positive reinforcement
- 🧬 Trains dopamine system
- 🏆 Celebrates willpower
- 📈 Motivates future resistance

---

## 💾 Database Schema

### Room Database: `taqwa_journal_database`

#### Table: `journal_entries`
| Column | Type | Description |
|--------|------|-------------|
| `id` | INT (PK, auto) | Unique entry identifier |
| `timestamp` | LONG | Unix timestamp of entry creation |
| `situationContext` | TEXT | Q1: Where/what before urge |
| `feelings` | TEXT | Q2: Comma-separated feelings |
| `realNeed` | TEXT | Q3: Comma-separated real needs |
| `alternativeChosen` | TEXT | Q4: Comma-separated alternatives |
| `urgeStrength` | INT | Q5: Urge strength 1-10 |
| `freeText` | TEXT | Q6: Personal message |
| `completed` | BOOLEAN | Whether full flow was completed |

### SharedPreferences: `taqwa_streak`
| Key | Type | Description |
|-----|------|-------------|
| `streak_start_date` | String | ISO date of current streak start |
| `longest_streak` | Int | Longest streak in days |
| `total_relapses` | Int | Total relapse count |
| `relapse_history` | String | Serialized relapse records |

### SharedPreferences: `taqwa_promises`
| Key | Type | Description |
|-----|------|-------------|
| `promises` | String | `|||` separated promises |
| `why_quitting` | String | Personal reason for quitting |
| `duas` | String | `|||` separated duas |
| `personal_reminders` | String | `|||` separated reminders |

---

## 🗺️ Future Roadmap

### Version 2.0 — Enhanced Experience
- [ ] 🔔 **Smart Notifications** — Motivational reminders + danger zone alerts based on pattern data
- [ ] 📖 **Daily Quran Dose** — One curated ayah per day on home screen
- [ ] 🛡️ **Quick Emergency Mode** — Home screen widget for one-tap access
- [ ] 🌙 **Night Mode Protection** — Persistent notification during danger hours
- [ ] 🏆 **Achievement Badges** — Gamification rewards for milestones

### Version 3.0 — Deep Features
- [ ] 💪 **Positive Habits Tracker** — Track Fajr, Quran, exercise + correlation with urges
- [ ] 📈 **Weekly/Monthly Reports** — Detailed progress summaries
- [ ] 🎯 **Personal Goals** — Set and track custom targets
- [ ] 📚 **Education Section** — How porn affects the brain + Islamic perspective
- [ ] 📅 **Calendar View** — Visual calendar with green/red days

### Version 4.0 — Community (Optional)
- [ ] 🤝 **Accountability Partner** — Share streaks with a trusted friend
- [ ] 📤 **Data Export** — Export journal entries as PDF
- [ ] 🌐 **Multi-language Support** — Arabic, Urdu, Malay, Turkish, French
- [ ] 🎨 **Custom Themes** — Light mode, color customization

---

## 🤝 Contributing

Contributions are welcome! This app was built to help people, and any improvement helps.

### How to Contribute

1. **Fork** the repository
2. **Create** a feature branch
```bash
git checkout -b feature/amazing-feature
```
3. **Commit** your changes
```bash
git commit -m 'Add amazing feature'
```
4. **Push** to the branch
```bash
git push origin feature/amazing-feature
```
5. **Open** a Pull Request

### Contribution Ideas
- 🐛 Bug fixes
- 🌐 Translations (Arabic, Urdu, Malay, etc.)
- 📖 More Quran ayahs and hadith
- 🎨 UI/UX improvements
- 📱 Home screen widget
- 🔔 Notification system
- 🧪 Unit tests and UI tests
- 📊 More advanced pattern analysis
- ♿ Accessibility improvements

### Code Guidelines
- Follow Kotlin coding conventions
- Use Jetpack Compose for all UI
- Maintain MVVM architecture
- Keep the app 100% offline
- Respect user privacy (no analytics, no tracking)
- Be culturally sensitive in all text content

---

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

```
MIT License

Copyright (c) 2024 Omarzcode

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

---

## 🙏 Acknowledgments

- **Allah (سبحانه وتعالى)** — For the strength to build this and fight this battle
- **The Quran** — For the ayahs that guide and heal
- **Prophet Muhammad ﷺ** — For teaching us that the greatest jihad is against the self
- **CBT (Cognitive Behavioral Therapy)** — For the psychological framework
- **Android/Google** — For Jetpack Compose and Material 3
- **Every person fighting this addiction** — You are not alone. Keep going.

---

<div align="center">

### 💚 A Message

*This app was built by someone going through the same struggle.*
*If it helps even one person, it was worth every line of code.*

**"وَأَمَّا مَنْ خَافَ مَقَامَ رَبِّهِ وَنَهَى النَّفْسَ عَنِ الْهَوَىٰ فَإِنَّ الْجَنَّةَ هِيَ الْمَأْوَىٰ"**

*"But as for he who feared standing before his Lord and restrained the soul from desire — then indeed, Paradise will be his refuge."*
— Surah An-Nazi'at 79:40-41

---
*
</div>
