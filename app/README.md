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

---

## ✨ Features

### 🔴 Urge Intervention Flow
A multi-screen guided process that walks you through the urge:

| Screen | Purpose | Technique |
|--------|---------|-----------|
| ⏸️ Stop & Breathe | Interrupt autopilot | Box breathing (4-4-4) with animated circle |
| ❌ Reality Check | Challenge distorted thinking | Timed truth statements |
| 🤲 Islamic Reminder | Reconnect with Allah | Random curated ayah + reflection |
| 📝 Personal Reminder | Your own words | Shows promises, duas, reasons |
| 📅 Future Self | Visualize consequences | Path A (give in) vs Path B (resist) |
| 📋 Guided Questions | Self-reflection journal | 6 structured questions |
| 🏆 Victory | Positive reinforcement | Celebration + Quran ayah |

### 📋 Guided Journal Questions
1. **📍 Situation** — Where are you? What were you doing?
2. **💭 Feelings** — Multi-select from 12 emotions
3. **🎯 Real Need** — What do you actually need? (9 options)
4. **🔄 Alternative** — What will you do instead? (12 options)
5. **💢 Urge Strength** — Rate 1-10 with slider
6. **✍️ Free Text** — Raw message to yourself

### 📖 Past Entries Browser
- Chronological list of all journal entries
- Entry detail view with full information
- Swipe-to-delete with confirmation
- Urge strength color-coded badges

### 🔥 Streak Tracker
- Current streak (days since last relapse)
- Longest streak ever recorded
- Streak status messages
- Milestone celebrations at: 1, 3, 7, 14, 21, 30, 60, 90, 180, 365 days
- Brain recovery progress messaging

### 📉 Relapse Management
- Compassionate reset flow (no shaming)
- Reflection prompt: "What went wrong?"
- Relapse history with dates, reasons, and streaks lost
- Islamic encouragement on relapse
- Total relapse counter

### 📊 Pattern Analysis
- **Time Patterns** — When do urges hit most? (Morning/Afternoon/Evening/Night/Late Night)
- **Feelings Patterns** — Most common trigger emotions
- **Needs Patterns** — What you actually need behind urges
- **Activities Patterns** — Which alternatives work best for you
- **Urge Strength Trend** — Are urges getting weaker over time?
- **Weekly Comparison** — This week vs last week
- **Personal Insight** — Auto-generated summary of your patterns
- Requires minimum 3 entries to display

### 📝 Promise Wall
- **Why I'm Quitting** — Your core motivation
- **My Promises** — Commitments to yourself
- **My Duas** — Personal supplications
- **Personal Reminders** — Things to remember during urges
- All content shown during intervention flow
- Add/edit/delete functionality

### 🕌 Islamic Integration
- Curated Quran ayahs for intervention (5 ayahs)
- Victory celebration ayahs (4 ayahs)
- Relapse compassion ayahs
- Arabic text with English translation
- Contextual reflections for each ayah
- Hadith references where appropriate

### 🔒 Privacy First
- **100% Offline** — No internet connection needed or used
- **Zero Permissions** — App requests nothing from your phone
- **Local Storage Only** — All data stays on your device
- **No Analytics** — No tracking, no data collection
- **No Accounts** — No sign-up, no cloud sync

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

## 📸 Screenshots

> *Add screenshots here after building the APK*
>
> Recommended screenshots:
> 1. Home Screen
> 2. Breathing Screen
> 3. Islamic Reminder
> 4. Future Self (Path A vs B)
> 5. Questions Screen
> 6. Victory Screen
> 7. Pattern Analysis
> 8. Promise Wall

---

## 🛠️ Tech Stack

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

### Prerequisites
- Android Studio Hedgehog (2023.1.1) or later
- JDK 11 or later
- Android SDK 36
- Min SDK 26 (Android 8.0 Oreo)

### Installation

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

### Build APK
```
Build → Build Bundle(s) / APK(s) → Build APK(s)

APK location: app/build/outputs/apk/debug/app-debug.apk
```

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
