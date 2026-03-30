# Taqwa App: Arabic Language Support & i18n Analysis

**Analysis Date:** March 30, 2026  
**Analyzed Scope:** 25 screens, 20+ data files, UI components, data models  
**Language Focus:** Arabic (عربي) + general i18n infrastructure  

---

## EXECUTIVE SUMMARY

The Taqwa app has **minimal Arabic support** with **zero i18n infrastructure**:

| Aspect | Current State | Readiness |
|--------|---------------|-----------|
| **Arabic Content** | 5% coverage (religious content only) | 🔴 Critical Gap |
| **RTL Layout** | Zero RTL handling | 🔴 Not Ready |
| **i18n Framework** | None (hardcoded strings) | 🔴 Non-existent |
| **Data Model Support** | 10% (ShieldPlan only) | 🟡 Partial |
| **UI Localization** | 150+ hardcoded English strings | 🔴 No Framework |

---

## 1. CURRENT ARABIC IMPLEMENTATION

### 1.1 What Arabic Content EXISTS ✅

#### A. Islamic Content (Quranic Verses & Hadith)
**File:** `QuestionData.kt`  
**Coverage:** ~35 Islamic reminders with Arabic text

Example structure:
```kotlin
data class IslamicReminder(
    val arabic: String,           // ✅ Quranic/Hadith Arabic
    val translation: String,      // ✅ English translation
    val reference: String,        // ✅ Surah/Hadith reference
    val reflection: String        // ❌ English only (no Arabic)
)
```

**Hardcoded Arabic examples:**
- "أَلَمْ يَعْلَم بِأَنَّ اللَّهَ يَرَىٰ" (Al-Alaq 96:14)
- "إِنَّ اللَّهَ مَعَ الصَّابِرِينَ" (Al-Baqarah 2:153)
- ~33 more Quranic ayat stored alongside translations

**Display:** IslamicReminderScreen.kt shows:
```kotlin
Text(text = reminder.arabic, style = TaqwaType.arabicLarge, ...)
Text(text = reminder.translation, ...)  // English below
```

#### B. Daily Quranic Verses
**File:** `DailyQuranManager.kt`  
**Coverage:** 20 daily ayahs with bilingual support

```kotlin
data class DailyAyah(
    val arabic: String,        // ✅ Arabic text
    val translation: String,   // ✅ English translation
    val reference: String      // ✅ Reference
)
```

#### C. Shield Plan Trigger Names (Partially)
**File:** `ShieldPlanManager.kt`  
**Coverage:** 5 default triggers with Arabic names only

```kotlin
TriggerType(
    id = "boredom",
    name = "Boredom / Emptiness",
    nameAr = "الفراغ + الكسل",                   // ✅ Arabic name
    description = "When you're idle...",        // ❌ English only
    defaultSteps = listOf(...)                  // ❌ English only
)
```

**Default triggers with Arabic names:**
1. الفراغ + الكسل (Boredom/Emptiness)
2. الغضب + الحزن (Anger/Sadness)
3. البصر → الخاطرة (Visual Trigger)
4. التذكير (Memory/Association)
5. الليل + الوحدة (Late Night/Alone)

**⚠️ CRITICAL ISSUE:** These `nameAr` fields are **stored but NEVER displayed** in the UI. ShieldPlanScreen.kt only shows `triggerName` (English).

---

### 1.2 What Arabic Content DOES NOT EXIST ❌

#### A. No Language Selection Infrastructure
- ❌ No Locale switcher in Settings
- ❌ No language preference stored (SharedPreferences.getString("language") not used)
- ❌ No system language detection + user override pattern
- ❌ No language context passed through the app

#### B. No UI Localization Framework
- ❌ No `strings.xml` or equivalent resource files
- ❌ No getStringResource() or translations lookup system
- ❌ No translation keys (e.g., "label_urge", "button_continue")
- ❌ No Locale-aware string loading

#### C. No UI Labels in Arabic
**150+ hardcoded English strings with zero Arabic variants:**

| Screen | Hardcoded English | Arabic Needed |
|--------|-------------------|--------------|
| HomeScreen | "Milestone Reached!", "Alhamdulillah!", streak labels | ❌ None |
| QuestionsScreen | "Question X of 6", "JOURNAL" | ❌ None |
| BreathingScreen | "STOP.", "You are on autopilot" | ❌ None |
| ShieldPlanScreen | "Your Pre-Written Defense", "When a trigger hits" | ❌ None |
| SettingsScreen | "Settings", "Clear Data", stats labels | ❌ None |
| BottomNavBar | "Home", "Journal", "Calendar", "Insights", "Settings" | ❌ None |
| All screens | Back button label, placeholder text, validation messages | ❌ None |

---

## 2. UI/LAYOUT FOR RTL (Right-to-Left)

### 2.1 RTL Readiness: 🔴 **ZERO**

The app is **completely LTR-centric** with zero accommodations for RTL languages like Arabic.

### 2.2 Layout Issues

#### A. Hardcoded Left Alignment
```kotlin
// ❌ Not RTL-aware
Text(text = "...", textAlign = TextAlign.Center)  // OK
Text(text = "...", textAlign = TextAlign.Start)   // BREAKS in RTL
Row(horizontalArrangement = Arrangement.Start)    // BREAKS in RTL
```

**Problematic patterns found:**
- `Arrangement.Start` (assumed LTR start) — appears 30+ times
- `Arrangement.SpaceBetween` — needs reversal in RTL
- Hardcoded column/row orders without flex reasoning

#### B. Navigation Back Button
**File:** `TaqwaTopBar.kt`
```kotlin
TopAppBar(
    navigationIcon = {
        if (onBack != null) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        }
    }
)
```

❌ Issues:
- Back button is always on the LEFT
- Should be on RIGHT in RTL
- Text content description is English

#### C. Bottom Navigation Bar
**File:** `BottomNavBar.kt`
```kotlin
Row(
    modifier = Modifier.fillMaxWidth()
) {
    // 5 nav items: Home, Journal, Calendar, Insights, Settings
}
```

❌ Layout:
- Fixed LTR order: [Home] [Journal] [Calendar] [Insights] [Settings]
- In RTL, should be reversed or mirrored
- No LayoutDirection parameter

#### D. List/Scroll Layouts
Most screens use:
```kotlin
Column(horizontalAlignment = Alignment.CenterHorizontally) { ... }
LazyColumn { ... }
```

❌ Issues:
- Items always arranged LTR
- No RTL handling for horizontal lists
- Text always left-aligned by default

### 2.3 Component RTL Support Check

| Component | RTL Support | Status |
|-----------|------------|--------|
| **TaqwaTopBar** | Back arrow position, title alignment | ❌ Hardcoded LTR |
| **TaqwaCard** | Content alignment, padding | ❌ No RTL modifiers |
| **BottomNavBar** | Tab order, icon placement | ❌ Hardcoded LTR |
| **SectionHeader** | Icon/text alignment | ❌ No RTL handling |
| **UrgeFlowProgressBar** | Progress direction | ❌ Likely LTR only |
| **Text fields** | Cursor position, keyboard | ❌ Default behavior |

### 2.4 What Would Break in RTL

If user sets device language to Arabic:

1. **Navigation** - Back button appears on wrong side
2. **Tab order** - Bottom nav items in wrong order
3. **Text alignment** - All labels misaligned
4. **Punch cards/lists** - Items flow in wrong direction
5. **Keyboard** - Would be RTL, but TextFields not configured
6. **Numbers/dates** - Would need special handling
7. **Visual hierarchy** - Top bars, buttons appear misaligned

---

## 3. TEXT CONTENT ANALYSIS

### 3.1 Hardcoded English Strings: Volume Assessment

**Rough inventory across 25 screens:**

| Category | Quantity | Examples |
|----------|----------|----------|
| Screen titles | 25 | HomeScreen, SettingsScreen, ShieldPlanScreen, etc. |
| Button labels | 40+ | "Continue", "Save", "Delete", "Reset", "Export" |
| Form labels | 30+ | "Enter your name", "Select a trigger", "Add step" |
| Error messages | 15+ | Validation errors, empty state messages |
| Progress text | 10+ | "Question X of 6", "Step 2 of 7" |
| Status text | 15+ | "No entries yet", "Loading...", "Streak active" |
| Milestone messages | 8+ | "Milestone Reached!", "Day 7!", "Victory!" |
| Placeholder text | 20+ | Text field hints, empty state descriptions |
| Help/instruction text | 50+ | Screen explanations, onboarding content |

**TOTAL: ~213 hardcoded English strings** across the codebase

### 3.2 Where Hardcoded Strings Are Found

```
HomeScreen.kt:
  - "🎉 Milestone Reached!"
  - "Alhamdulillah! 🤲"
  - "Urges Defeated", "Current Streak", "Longest Streak"
  - Button labels: "I'm Facing an Urge", "Morning Check-in"

QuestionsScreen.kt:
  - "✍️  JOURNAL"
  - "Question $currentQuestion of 6"
  - "What are you feeling?" (Q2)
  - "What do you really need?" (Q3)
  - "What will you do instead?" (Q4)

BreathingScreen.kt:
  - "STOP."
  - "You are on autopilot right now."
  - "Your brain is lying to you."
  - "Breathe IN (4 seconds)"

ShieldPlanScreen.kt:
  - "Shield Plans"
  - "Your Pre-Written Defense"
  - "When a trigger hits, your brain goes offline."
  - Add/Edit/Delete buttons

SettingsScreen.kt:
  - "⚙️  Settings"
  - "Total Entries", "Total Relapses"
  - "Clear All Data", "Relapse History", "Export"
```

### 3.3 Data-Driven vs Hardcoded

**Data-Driven (from Kotlin classes):**
- Islamic reminders (stored in QuestionData)
- Daily Quranic verses (stored in DailyQuranManager)
- Shield plan triggers & steps (stored in ShieldPlan)
- Journal entry questions (stored in QuestionData)
- Memory bank entries (stored in database)

**Hardcoded (in UI):**
- ALL screen titles, buttons, labels
- ALL navigation text
- ALL form labels and placeholders
- ALL error/success messages
- ALL instructional text

---

## 4. DATA STRUCTURE SUPPORT FOR MULTILINGUAL TEXT

### 4.1 Current Data Models

#### ✅ ShieldPlan (Partial Support)
```kotlin
data class ShieldPlan(
    val triggerId: String,
    val emoji: String,
    val triggerName: String,        // ✅ English
    val triggerNameAr: String,      // ✅ Arabic (STORED but not used)
    val description: String,        // ❌ English only
    val steps: List<String>,        // ❌ English only
    val personalNote: String,       // ❌ English only (user-generated)
    val isActive: Boolean,
    val isCustom: Boolean
)
```

**Problem:** `triggerNameAr` is persisted but **never displayed** in ShieldPlanScreen.kt
- Only `triggerName` is shown in UI
- nameAr field is essentially unused

#### ❌ JournalEntry (No Arabic Support)
```kotlin
data class JournalEntry(
    val id: Int,
    val timestamp: Long,
    val situationContext: String,      // ❌ No contextAr
    val feelings: String,               // ❌ No feelingsAr
    val realNeed: String,               // ❌ No needAr
    val alternativeChosen: String,      // ❌ No alternativeAr
    val urgeStrength: Int,
    val freeText: String,               // ❌ No freeTextAr
    val completed: Boolean
)
```

Users can write entries in any language, but the app provides no Arabic labels/prompts for questions.

#### ❌ MemoryEntry (No Arabic Support)
```kotlin
data class MemoryEntry(
    val id: Int,
    val timestamp: Long,
    val type: String,                   // ❌ No typeAr
    val message: String,                // ❌ No messageAr (user-generated)
    val trigger: String,                // ❌ No triggerAr
    val streakAtTime: Int,
    val urgeStrengthAtTime: Int,
    val isPinned: Boolean
)
```

Memory entries are user-written, but UI provides no Arabic guidance.

#### ❌ CheckInEntry (Likely No Arabic Support)
```kotlin
// Inferred from morning check-in context:
// - mood: String (mood emoji/label in English)
// - riskLevel: String (English: "Low", "Medium", "High")
// - intention: String (user text)
```

#### ❌ QuestionData (Hardcoded Lists)
```kotlin
val feelings = listOf(
    "😔 Lonely",       // ❌ English only
    "😐 Bored",
    // ... 14 more
)

val realNeeds = listOf(
    "🤝 Connection / Companionship",    // ❌ English only
    // ... 11 more
)

val alternatives = listOf(
    "🤲 Make wudu & pray 2 rakaat",     // ❌ English only
    // ... 15 more
)
```

**Issue:** These are hardcoded lists. To support Arabic, would need:
```kotlin
data class LocalizedOption(
    val emoji: String,
    val en: String,
    val ar: String
)
```

### 4.2 Database Schema Implications

**Current:** All text fields are single-language (assumed English)

**For Arabic Support:**
Would need to add columns:
- `situationContext` → add `situationContextAr`
- `feelings` → add `feelingsAr` (store selected labels in Arabic)
- `realNeed` → add `realNeedAr`
- etc.

OR use a centralized translation key system:
```kotlin
data class JournalEntry(
    val id: Int,
    val situation_key: String,         // e.g., "question.situation"
    val situationContent: String,      // User's actual answer
    // ... with i18n framework handling labels
)
```

---

## 5. LANGUAGE & LOCALE CONFIGURATION

### 5.1 Current Setup

**Date/Time Handling:**
```kotlin
// EntryDetailScreen.kt
SimpleDateFormat("EEEE, MMM dd, yyyy", Locale.getDefault())

// ExportManager.kt
SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH)  // ❌ Hardcoded English
```

**Issue:** ExportManager uses `Locale.ENGLISH` regardless of device language. Exports will always be in English.

### 5.2 Missing Pieces

- ❌ No `LocalizationManager` class
- ❌ No shared preference for language selection
- ❌ No system language detection
- ❌ No Locale.ENGLISH fallback with Arabic override
- ❌ No language change listener
- ❌ No context/activity-level LocalConfiguration

### 5.3 No i18n Framework
Unlike modern Android apps, Taqwa does not use:
- ❌ `strings.xml` (Android resources)
- ❌ Jetpack Compose `LocalContext.current.getString()`
- ❌ Third-party i18n library (e.g., Tolgee, Phrase, OneSky)
- ❌ Custom translation injection (e.g., Hilt dependency)

---

## 6. FINDINGS SUMMARY

### 6.1 Arabic Content Coverage

| Area | Coverage | Status |
|------|----------|--------|
| **Religious Content** | 100% | ✅ Quranic verses + Hadith (Arabic + English) |
| **Shield Plan Trigger Names** | 20% stored, 0% displayed | 🟡 Data exists but unused |
| **UI Labels & Buttons** | 0% | 🔴 All English |
| **Form Fields & Placeholders** | 0% | 🔴 All English |
| **Help Text & Instructions** | 0% | 🔴 All English |
| **Navigation & Menus** | 0% | 🔴 All English |
| **User-Generated Content** | Variable | 🟡 Users can write in any language, but app doesn't guide them |

**Overall Coverage: ~5%** (only religious content)

### 6.2 RTL Readiness Score

| Component | Readiness |
|-----------|-----------|
| **Compose RTL Modifiers** | 0% — None used |
| **LayoutDirection Configuration** | 0% — Not implemented |
| **Back Button Mirroring** | 0% — Hardcoded LTR |
| **Navigation Order Reversal** | 0% — Hardcoded LTR |
| **Text Alignment Adaptation** | 0% — Default LTR |
| **Component Mirroring** | 0% — No adaptation |

**RTL Readiness Score: 0/100**

### 6.3 i18n Infrastructure Gaps

| Component | Current | Needed |
|-----------|---------|--------|
| **Translation Framework** | None | Strings.xml or custom system |
| **Language Selector** | None | Settings UI + storage |
| **Locale Detection** | Partial (dates only) | Full system integration |
| **Resource Loading** | Hardcoded | Locale-aware lookup |
| **Component Localization** | None | RTL support + mirroring |
| **Data Model Support** | 10% | All entities multilingual |

**Infrastructure Readiness: 5% (dates only)**

---

## 7. SCOPE OF WORK ESTIMATE

### Option A: Full i18n (English + Arabic + RTL)
**Timeline:** 60–80 hours
**Scope:**
- ✅ Implement i18n framework (strings resource system or custom)
- ✅ Extract 213+ hardcoded strings to centralized system
- ✅ Create Arabic translations for all UI text
- ✅ Implement language selector in Settings
- ✅ Add RTL layout support (LayoutDirection, component mirroring)
- ✅ Implement LocalizationManager
- ✅ Update all data models (JournalEntry, MemoryEntry, QuestionData, etc.)
- ✅ Add database migrations for Arabic fields
- ✅ Test on Arabic device/emulator in RTL mode

### Option B: Arabic-Only (Minimal RTL)
**Timeline:** 30–40 hours
**Scope:**
- ✅ Implement custom translation system (LocalizationManager)
- ✅ Translate 213+ hardcoded strings to Arabic
- ✅ Basic RTL layout support (LayoutDirection toggle in code)
- ✅ Show Arabic nameAr fields in ShieldPlanScreen
- ✅ Add language toggle in Settings (Arabic/English)
- ✅ Update critical data models (ShieldPlan)
- ✅ Basic testing

### Option C: Minimal Implementation (Framework Only)
**Timeline:** 15–20 hours
**Scope:**
- ✅ Implement LocalizationManager
- ✅ Create Strings data class (key → value maps)
- ✅ Update main screens to use localized strings
- ✅ Add language preference storage
- ✅ Leave RTL, data models for later phases

---

## 8. NEXT STEPS RECOMMENDATIONS

### Priority 1: Foundation (Do First)
1. Create `LocalizationManager` class with English/Arabic string maps
2. Implement language preference in Settings
3. Create `Strings` data class or use `objects` pattern
4. Main app activity reads locale preference

### Priority 2: Critical UI (Users Will Notice)
1. Translate bottom navigation labels
2. Translate main screen titles
3. Translate button labels on core screens (Urge, Shield Plans)
4. Display `nameAr` in ShieldPlanScreen

### Priority 3: RTL Layout (Nice to Have, but Important for Arabic)
1. Add LayoutDirection to AppState
2. Apply `layoutDirection(LayoutDirection.Rtl)` conditionally
3. Mirror back button position in TaqwaTopBar
4. Reverse bottom nav tab order in RTL

### Priority 4: Full Coverage (Complete Implementation)
1. Translate all 213+ strings
2. Update data models with Arabic fields
3. Database migrations
4. Full RTL testing

---

## 9. ARCHITECTURAL RECOMMENDATION

**Suggested i18n Pattern for Taqwa:**

```kotlin
// 1. LocalizationManager (centralized)
object LocalizationManager {
    private var currentLocale: Locale = Locale.ENGLISH
    
    fun setLocale(locale: Locale) {
        currentLocale = locale
        // Notify observers
    }
    
    fun getString(key: String): String {
        return when(currentLocale) {
            Locale.ENGLISH -> Strings.en[key] ?: key
            Locale.ARABIC -> Strings.ar[key] ?: Strings.en[key] ?: key
            else -> Strings.en[key] ?: key
        }
    }
}

// 2. Strings object
object Strings {
    val en = mapOf(
        "label.urge" to "I'm Facing an Urge",
        "label.journal" to "Journal",
        "title.shield_plans" to "Shield Plans",
        // ... 213+ strings
    )
    
    val ar = mapOf(
        "label.urge" to "أواجه رغبة",
        "label.journal" to "دفتر اليوميات",
        "title.shield_plans" to "خطط الحماية",
        // ... Arabic translations
    )
}

// 3. Usage in composables
Text(text = LocalizationManager.getString("label.urge"))

// 4. RTL in Theme.kt
val isRtl = LocalizationManager.currentLocale == Locale.ARABIC
TaqwaTheme(
    isRtl = isRtl,
    content = { ... }
)
```

---

## CONCLUSION

**The Taqwa app is currently:**
- ✅ Great at storing Islamic texts bilingual (Arabic + English)
- ✅ Partially prepared for Arabic trigger names (data exists)
- ❌ Not ready for full Arabic UI
- ❌ Not ready for RTL layout
- ❌ Missing i18n framework entirely

**To support Arabic users properly would require:**
1. A complete i18n infrastructure rebuild (medium effort)
2. RTL layout adaptation across 25 screens (medium effort)
3. Database schema updates (low effort)
4. Professional Arabic translation of 200+ strings (external)

**Estimated timeline for full Arabic + RTL:** 8–10 weeks full-time development
