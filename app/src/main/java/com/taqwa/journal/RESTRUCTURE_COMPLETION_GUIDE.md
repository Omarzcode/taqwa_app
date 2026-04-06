[//]: # (# Taqwa Feature-Based Restructure: Complete Implementation Guide)

[//]: # ()
[//]: # (> **Current Status**: 60% complete. This guide covers the remaining 40% with copy-paste patterns.)

[//]: # ()
[//]: # (---)

[//]: # ()
[//]: # (## ✅ What's Already Done)

[//]: # ()
[//]: # (- ✅ Full directory structure &#40;`core/` + 15 `features/`&#41;)

[//]: # (- ✅ Database layer &#40;`TaqwaDatabase`, 3 DAOs, 3 Entities, 3 Repositories&#41;)

[//]: # (- ✅ All 15 feature-specific ViewModels)

[//]: # (- ✅ 6 key managers in feature folders)

[//]: # ()
[//]: # (**New structure allows you to:**)

[//]: # (- Pick up entire features and move them without breaking others)

[//]: # (- Add new features without touching existing code)

[//]: # (- Clear separation of concerns)

[//]: # ()
[//]: # (---)

[//]: # ()
[//]: # (## 📋 Remaining Tasks)

[//]: # ()
[//]: # (### Phase 1: Move Remaining Managers &#40;~5 min&#41;)

[//]: # (### Phase 2: Copy Core Files &#40;~10 min&#41;)

[//]: # (### Phase 3: Copy Screens to Features &#40;~30 min&#41;)

[//]: # (### Phase 4: Create Feature Navigation &#40;~20 min&#41;)

[//]: # (### Phase 5: Update Imports &#40;~60 min&#41;)

[//]: # (### Phase 6: Test & Verify)

[//]: # ()
[//]: # (---)

[//]: # ()
[//]: # (## Phase 1: Move Remaining Managers)

[//]: # ()
[//]: # (### Pattern)

[//]: # (```)

[//]: # (OLD LOCATION: data/preferences/ or data/export/ or notification/)

[//]: # (NEW LOCATION: features/{feature}/data/ or features/{feature}/)

[//]: # (CHANGE: Update package statement)

[//]: # (```)

[//]: # ()
[//]: # (### Files to Move)

[//]: # ()
[//]: # (#### 1. `ExportManager.kt`)

[//]: # (```)

[//]: # (FROM: data/export/ExportManager.kt)

[//]: # (TO:   features/export/ExportManager.kt)

[//]: # (PACKAGE: change to com.taqwa.journal.features.export)

[//]: # (```)

[//]: # ()
[//]: # (**Action**: )

[//]: # (1. Read `data/export/ExportManager.kt`)

[//]: # (2. Create `features/export/ExportManager.kt`)

[//]: # (3. Change package to `com.taqwa.journal.features.export`)

[//]: # (4. Update any internal imports &#40;e.g., `ExportOptions`, `ReportData`&#41;)

[//]: # ()
[//]: # (#### 2. `ExportOptions.kt`)

[//]: # (```)

[//]: # (FROM: data/export/ExportOptions.kt)

[//]: # (TO:   features/export/ExportOptions.kt)

[//]: # (```)

[//]: # ()
[//]: # (#### 3. `ReportData.kt`)

[//]: # (```)

[//]: # (FROM: data/export/ReportData.kt)

[//]: # (TO:   features/export/ReportData.kt)

[//]: # (```)

[//]: # ()
[//]: # (#### 4. `NotificationScheduler.kt`)

[//]: # (```)

[//]: # (FROM: notification/NotificationScheduler.kt)

[//]: # (TO:   features/notifications/NotificationScheduler.kt)

[//]: # (PACKAGE: com.taqwa.journal.features.notifications)

[//]: # (```)

[//]: # ()
[//]: # (#### 5. `TaqwaNotificationManager.kt`)

[//]: # (```)

[//]: # (FROM: notification/TaqwaNotificationManager.kt)

[//]: # (TO:   features/notifications/TaqwaNotificationManager.kt)

[//]: # (```)

[//]: # ()
[//]: # (#### 6. `NotificationReceiver.kt`)

[//]: # (```)

[//]: # (FROM: notification/NotificationReceiver.kt)

[//]: # (TO:   features/notifications/NotificationReceiver.kt)

[//]: # (```)

[//]: # ()
[//]: # (#### 7. `BootReceiver.kt`)

[//]: # (```)

[//]: # (FROM: notification/BootReceiver.kt)

[//]: # (TO:   features/notifications/BootReceiver.kt)

[//]: # (```)

[//]: # ()
[//]: # (---)

[//]: # ()
[//]: # (## Phase 2: Copy Core Files &#40;Shared Foundation&#41;)

[//]: # ()
[//]: # (These files don't change — just copy with package updates.)

[//]: # ()
[//]: # (### Theme Files → `core/theme/`)

[//]: # (```)

[//]: # (Color.kt          → core/theme/Color.kt)

[//]: # (Dimensions.kt     → core/theme/Dimensions.kt)

[//]: # (Theme.kt          → core/theme/Theme.kt)

[//]: # (Type.kt           → core/theme/Type.kt)

[//]: # (```)

[//]: # ()
[//]: # (**Package update**: Change `com.taqwa.journal.ui.theme` → `com.taqwa.journal.core.theme`)

[//]: # ()
[//]: # (### Component Files → `core/components/`)

[//]: # (```)

[//]: # (BottomNavBar.kt          → core/components/BottomNavBar.kt)

[//]: # (TaqwaCard.kt             → core/components/TaqwaCard.kt)

[//]: # (TaqwaDialog.kt           → core/components/TaqwaDialog.kt)

[//]: # (TaqwaTopBar.kt           → core/components/TaqwaTopBar.kt)

[//]: # (FormTextField.kt         → core/components/FormTextField.kt)

[//]: # (LoadingOverlay.kt        → core/components/LoadingOverlay.kt)

[//]: # (SectionHeader.kt         → core/components/SectionHeader.kt)

[//]: # (EmptyStateCard.kt        → core/components/EmptyStateCard.kt)

[//]: # (WizardContainer.kt       → core/components/WizardContainer.kt)

[//]: # (UrgeFlowProgressBar.kt   → core/components/UrgeFlowProgressBar.kt)

[//]: # (```)

[//]: # ()
[//]: # (**Package:**  `com.taqwa.journal.ui.components` → `com.taqwa.journal.core.components`)

[//]: # ()
[//]: # (### Utilities → `core/utilities/`)

[//]: # (```)

[//]: # (Validators.kt              → core/utilities/Validators.kt)

[//]: # (CsvPreferenceParser.kt     → core/utilities/CsvPreferenceParser.kt)

[//]: # (PreferenceListManager.kt   → core/utilities/PreferenceListManager.kt)

[//]: # (PreferenceToggleManager.kt → core/utilities/PreferenceToggleManager.kt)

[//]: # (```)

[//]: # ()
[//]: # (**Package**: `com.taqwa.journal.data.utilities` → `com.taqwa.journal.core.utilities`)

[//]: # ()
[//]: # (### Navigation → `core/navigation/`)

[//]: # (```)

[//]: # (NavGraph.kt         → core/navigation/NavGraph.kt)

[//]: # (Routes.kt           → core/navigation/Routes.kt)

[//]: # (BottomNavBar.kt     → core/navigation/BottomNavBar.kt &#40;if separate&#41;)

[//]: # (```)

[//]: # ()
[//]: # (**Package**: `com.taqwa.journal.ui.navigation` → `com.taqwa.journal.core.navigation`)

[//]: # ()
[//]: # (---)

[//]: # ()
[//]: # (## Phase 3: Move Screens to Features)

[//]: # ()
[//]: # (### Pattern)

[//]: # (```)

[//]: # (OLD:  ui/screens/{ScreenName}.kt)

[//]: # (NEW:  features/{feature}/screens/{ScreenName}.kt)

[//]: # (      or features/{feature}/{ScreenName}.kt if single screen)

[//]: # ()
[//]: # (UPDATE IMPORTS:)

[//]: # (- Change ViewModel import to feature-specific VM)

[//]: # (- Update any component imports to core.components)

[//]: # (- Update navigation imports to core.navigation)

[//]: # (```)

[//]: # ()
[//]: # (### Screen → Feature Mapping)

[//]: # ()
[//]: # (#### Urge Flow Feature)

[//]: # (```)

[//]: # (features/urgeflow/screens/)

[//]: # (├── BreathingScreen.kt)

[//]: # (├── RealityCheckScreen.kt)

[//]: # (├── IslamicReminderScreen.kt  &#40;→ rename to QuranReminderScreen.kt&#41;)

[//]: # (├── PersonalReminderScreen.kt)

[//]: # (├── FutureSelfScreen.kt)

[//]: # (├── QuestionsScreen.kt)

[//]: # (└── VictoryScreen.kt)

[//]: # ()
[//]: # (ViewModel: UrgeFlowViewModel)

[//]: # (Navigation: UrgeFlowNav.kt)

[//]: # (```)

[//]: # ()
[//]: # (#### Quick Catch Feature)

[//]: # (```)

[//]: # (features/quickcatch/)

[//]: # (├── QuickCatchScreen.kt)

[//]: # ()
[//]: # (ViewModel: QuickCatchViewModel)

[//]: # (Navigation: QuickCatchNav.kt)

[//]: # (```)

[//]: # ()
[//]: # (#### Memory Feature)

[//]: # (```)

[//]: # (features/memory/screens/)

[//]: # (├── MemoryBankScreen.kt)

[//]: # (└── WriteMemoryScreen.kt)

[//]: # ()
[//]: # (ViewModel: MemoryViewModel)

[//]: # (Navigation: MemoryNav.kt)

[//]: # (```)

[//]: # ()
[//]: # (#### Check-In Feature)

[//]: # (```)

[//]: # (features/checkin/)

[//]: # (├── MorningCheckInScreen.kt)

[//]: # ()
[//]: # (ViewModel: CheckInViewModel)

[//]: # (Navigation: CheckInNav.kt)

[//]: # (```)

[//]: # ()
[//]: # (#### Streak Feature)

[//]: # (```)

[//]: # (features/streak/screens/)

[//]: # (├── ResetScreen.kt)

[//]: # (└── RelapseHistoryScreen.kt)

[//]: # ()
[//]: # (ViewModel: StreakViewModel)

[//]: # (Navigation: StreakNav.kt)

[//]: # (```)

[//]: # ()
[//]: # (#### Shield Plan Feature)

[//]: # (```)

[//]: # (features/shieldplan/screens/)

[//]: # (├── ShieldPlanScreen.kt)

[//]: # (└── EditShieldPlanScreen.kt)

[//]: # ()
[//]: # (ViewModel: ShieldPlanViewModel)

[//]: # (Navigation: ShieldPlanNav.kt)

[//]: # (```)

[//]: # ()
[//]: # (#### Promise Feature)

[//]: # (```)

[//]: # (features/promise/)

[//]: # (├── PromiseWallScreen.kt)

[//]: # ()
[//]: # (ViewModel: PromiseViewModel)

[//]: # (Navigation: PromiseNav.kt)

[//]: # (```)

[//]: # ()
[//]: # (#### Quran Feature)

[//]: # (```)

[//]: # (features/quran/)

[//]: # (├── QuranReminderScreen.kt &#40;renamed from IslamicReminderScreen&#41;)

[//]: # ()
[//]: # (ViewModel: QuranViewModel)

[//]: # (Navigation: QuranNav.kt)

[//]: # (```)

[//]: # ()
[//]: # (#### Browse Feature)

[//]: # (```)

[//]: # (features/browse/screens/)

[//]: # (├── PastEntriesScreen.kt)

[//]: # (├── EntryDetailScreen.kt)

[//]: # (└── CalendarScreen.kt)

[//]: # ()
[//]: # (ViewModel: BrowseViewModel)

[//]: # (Navigation: BrowseNav.kt)

[//]: # (```)

[//]: # ()
[//]: # (#### Analysis Feature)

[//]: # (```)

[//]: # (features/analysis/)

[//]: # (├── PatternAnalysisScreen.kt)

[//]: # ()
[//]: # (ViewModel: AnalysisViewModel)

[//]: # (Navigation: AnalysisNav.kt)

[//]: # (```)

[//]: # ()
[//]: # (#### Export Feature)

[//]: # (```)

[//]: # (features/export/)

[//]: # (├── ExportScreen.kt)

[//]: # ()
[//]: # (ViewModel: ExportViewModel)

[//]: # (Navigation: ExportNav.kt)

[//]: # (```)

[//]: # ()
[//]: # (#### Tools Feature)

[//]: # (```)

[//]: # (features/tools/)

[//]: # (├── ToolsScreen.kt)

[//]: # ()
[//]: # (ViewModel: ToolsViewModel)

[//]: # (Navigation: ToolsNav.kt)

[//]: # (```)

[//]: # ()
[//]: # (#### Settings Feature)

[//]: # (```)

[//]: # (features/settings/)

[//]: # (├── SettingsScreen.kt)

[//]: # ()
[//]: # (ViewModel: SettingsViewModel)

[//]: # (Navigation: SettingsNav.kt)

[//]: # (```)

[//]: # ()
[//]: # (#### Notifications Feature)

[//]: # (```)

[//]: # (features/notifications/)

[//]: # (├── NotificationSettingsScreen.kt)

[//]: # ()
[//]: # (ViewModel: NotificationSettingsViewModel)

[//]: # (Navigation: NotificationNav.kt)

[//]: # (```)

[//]: # ()
[//]: # (#### Home Feature)

[//]: # (```)

[//]: # (features/home/)

[//]: # (├── HomeScreen.kt)

[//]: # ()
[//]: # (ViewModel: HomeViewModel)

[//]: # (Navigation: HomeNav.kt)

[//]: # (```)

[//]: # ()
[//]: # (#### Onboarding Feature)

[//]: # (```)

[//]: # (features/onboarding/)

[//]: # (├── OnboardingScreen.kt)

[//]: # ()
[//]: # (ViewModel: OnboardingViewModel)

[//]: # (Navigation: OnboardingNav.kt)

[//]: # (```)

[//]: # ()
[//]: # (---)

[//]: # ()
[//]: # (## Phase 4: Create Feature Navigation Files)

[//]: # ()
[//]: # (### Pattern Each feature needs a NavGraph file:)

[//]: # ()
[//]: # (```kotlin)

[//]: # (// features/{feature}/{FeatureName}Nav.kt)

[//]: # ()
[//]: # (package com.taqwa.journal.features.{feature})

[//]: # ()
[//]: # (import androidx.navigation.NavController)

[//]: # (import androidx.navigation.NavGraphBuilder)

[//]: # (import androidx.navigation.compose.composable)

[//]: # (import com.taqwa.journal.core.navigation.Routes)

[//]: # ()
[//]: # (fun NavGraphBuilder.{feature}Section&#40;navController: NavController&#41; {)

[//]: # (    navigation&#40;)

[//]: # (        startDestination = Routes.FeatureHome,)

[//]: # (        route = "{feature}_root")

[//]: # (    &#41; {)

[//]: # (        composable&#40;Routes.FeatureHome&#41; {)

[//]: # (            {FeatureScreen}&#40;navController&#41;)

[//]: # (        })

[//]: # (        // Add other screens in this feature)

[//]: # (    })

[//]: # (})

[//]: # (```)

[//]: # ()
[//]: # (### Example: Memory Navigation)

[//]: # (```kotlin)

[//]: # (// features/memory/MemoryNav.kt)

[//]: # ()
[//]: # (package com.taqwa.journal.features.memory)

[//]: # ()
[//]: # (import androidx.hilt.navigation.compose.hiltViewModel)

[//]: # (import androidx.navigation.NavController)

[//]: # (import androidx.navigation.NavGraphBuilder)

[//]: # (import androidx.navigation.compose.composable)

[//]: # (import com.taqwa.journal.core.navigation.Routes)

[//]: # (import com.taqwa.journal.features.memory.screens.MemoryBankScreen)

[//]: # (import com.taqwa.journal.features.memory.screens.WriteMemoryScreen)

[//]: # ()
[//]: # (fun NavGraphBuilder.memorySection&#40;navController: NavController&#41; {)

[//]: # (    navigation&#40;)

[//]: # (        startDestination = Routes.MemoryBank,)

[//]: # (        route = "memory_root")

[//]: # (    &#41; {)

[//]: # (        composable&#40;Routes.MemoryBank&#41; {)

[//]: # (            val viewModel: MemoryViewModel = hiltViewModel&#40;&#41;)

[//]: # (            MemoryBankScreen&#40;)

[//]: # (                viewModel = viewModel,)

[//]: # (                onNavigateToWrite = { navController.navigate&#40;Routes.WriteMemory&#41; },)

[//]: # (                onBack = { navController.popBackStack&#40;&#41; })

[//]: # (            &#41;)

[//]: # (        })

[//]: # (        composable&#40;Routes.WriteMemory&#41; {)

[//]: # (            val viewModel: MemoryViewModel = hiltViewModel&#40;&#41;)

[//]: # (            WriteMemoryScreen&#40;)

[//]: # (                viewModel = viewModel,)

[//]: # (                onBack = { navController.popBackStack&#40;&#41; })

[//]: # (            &#41;)

[//]: # (        })

[//]: # (    })

[//]: # (})

[//]: # (```)

[//]: # ()
[//]: # (### Create ALL 15 Navfiles:)

[//]: # (1. `features/home/HomeNav.kt`)

[//]: # (2. `features/onboarding/OnboardingNav.kt`)

[//]: # (3. `features/urgeflow/UrgeFlowNav.kt`)

[//]: # (4. `features/quickcatch/QuickCatchNav.kt`)

[//]: # (5. `features/checkin/CheckInNav.kt`)

[//]: # (6. `features/memory/MemoryNav.kt`)

[//]: # (7. `features/streak/StreakNav.kt`)

[//]: # (8. `features/shieldplan/ShieldPlanNav.kt`)

[//]: # (9. `features/promise/PromiseNav.kt`)

[//]: # (10. `features/quran/QuranNav.kt`)

[//]: # (11. `features/browse/BrowseNav.kt`)

[//]: # (12. `features/analysis/AnalysisNav.kt`)

[//]: # (13. `features/export/ExportNav.kt`)

[//]: # (14. `features/tools/ToolsNav.kt`)

[//]: # (15. `features/settings/SettingsNav.kt`)

[//]: # (16. `features/notifications/NotificationNav.kt`)

[//]: # ()
[//]: # (---)

[//]: # ()
[//]: # (## Phase 5: Update Imports)

[//]: # ()
[//]: # (This is the largest task but mechanical.)

[//]: # ()
[//]: # (### Step 1: Update Core Navigation &#40;`core/navigation/NavGraph.kt`&#41;)

[//]: # ()
[//]: # (**Old pattern:**)

[//]: # (```kotlin)

[//]: # (NavHost&#40;...&#41; {)

[//]: # (    composable&#40;Routes.Home&#41; { HomeScreen&#40;&#41; })

[//]: # (    composable&#40;Routes.BreathingScreen&#41; { BreathingScreen&#40;&#41; })

[//]: # (    // ... 30+ screens scattered)

[//]: # (})

[//]: # (```)

[//]: # ()
[//]: # (**New pattern:**)

[//]: # (```kotlin)

[//]: # (NavHost&#40;...&#41; {)

[//]: # (    homeSection&#40;navController&#41;)

[//]: # (    onboardingSection&#40;navController&#41;)

[//]: # (    urgeflowSection&#40;navController&#41;)

[//]: # (    quickcatchSection&#40;navController&#41;)

[//]: # (    checkinSection&#40;navController&#41;)

[//]: # (    memorySection&#40;navController&#41;)

[//]: # (    streakSection&#40;navController&#41;)

[//]: # (    shieldplanSection&#40;navController&#41;)

[//]: # (    promiseSection&#40;navController&#41;)

[//]: # (    quranSection&#40;navController&#41;)

[//]: # (    browseSection&#40;navController&#41;)

[//]: # (    analysisSection&#40;navController&#41;)

[//]: # (    exportSection&#40;navController&#41;)

[//]: # (    toolsSection&#40;navController&#41;)

[//]: # (    settingsSection&#40;navController&#41;)

[//]: # (    notificationSection&#40;navController&#41;)

[//]: # (})

[//]: # (```)

[//]: # ()
[//]: # (### Step 2: Find & Replace Imports)

[//]: # ()
[//]: # (Use VS Code Find & Replace &#40;Ctrl+H&#41;:)

[//]: # ()
[//]: # (```)

[//]: # (FIND:    com.taqwa.journal.ui.components)

[//]: # (REPLACE: com.taqwa.journal.core.components)

[//]: # ()
[//]: # (FIND:    com.taqwa.journal.ui.theme)

[//]: # (REPLACE: com.taqwa.journal.core.theme)

[//]: # ()
[//]: # (FIND:    com.taqwa.journal.data.utilities)

[//]: # (REPLACE: com.taqwa.journal.core.utilities)

[//]: # ()
[//]: # (FIND:    com.taqwa.journal.ui.navigation)

[//]: # (REPLACE: com.taqwa.journal.core.navigation)

[//]: # ()
[//]: # (FIND:    com.taqwa.journal.data.preferences.StreakManager)

[//]: # (REPLACE: com.taqwa.journal.features.streak.data.StreakManager)

[//]: # ()
[//]: # (FIND:    com.taqwa.journal.data.preferences.PromiseManager)

[//]: # (REPLACE: com.taqwa.journal.features.promise.data.PromiseManager)

[//]: # ()
[//]: # (FIND:    com.taqwa.journal.data.preferences.ShieldPlanManager)

[//]: # (REPLACE: com.taqwa.journal.features.shieldplan.data.ShieldPlanManager)

[//]: # ()
[//]: # (... &#40;etc for all moved files&#41;)

[//]: # (```)

[//]: # ()
[//]: # (### Step 3: Update Screens to Use Feature ViewModels)

[//]: # ()
[//]: # (**Example - MemoryBankScreen:**)

[//]: # ()
[//]: # (```kotlin)

[//]: # (// OLD)

[//]: # (class MemoryBankScreen&#40;)

[//]: # (    viewModel: JournalViewModel  // ← WRONG)

[//]: # (&#41;)

[//]: # ()
[//]: # (// NEW)

[//]: # (class MemoryBankScreen&#40;)

[//]: # (    viewModel: MemoryViewModel   // ← CORRECT)

[//]: # (&#41;)

[//]: # (```)

[//]: # ()
[//]: # (Find all screen constructor calls and pass the correct feature ViewModel.)

[//]: # ()
[//]: # (### Step 4: Database File Changes)

[//]: # ()
[//]: # (Update `MainActivity` &#40;or wherever JournalDatabase is initialized&#41;:)

[//]: # ()
[//]: # (```kotlin)

[//]: # (// OLD)

[//]: # (val database = JournalDatabase.getDatabase&#40;context&#41;)

[//]: # ()
[//]: # (// NEW)

[//]: # (val database = TaqwaDatabase.getDatabase&#40;context&#41;)

[//]: # (val journalDao = database.journalDao&#40;&#41;)

[//]: # (val memoryDao = database.memoryDao&#40;&#41;)

[//]: # (val checkInDao = database.checkInDao&#40;&#41;)

[//]: # (```)

[//]: # ()
[//]: # (---)

[//]: # ()
[//]: # (## Phase 6: Test & Verify)

[//]: # ()
[//]: # (1. **Build the project**: `Build → Build Project`)

[//]: # (   - Fix any remaining import errors)

[//]: # (   - Should compile without errors)

[//]: # ()
[//]: # (2. **Test navigation**: Ensure all screen transitions work)

[//]: # ()
[//]: # (3. **Verify feature isolation**: Change one feature ViewModel, confirm other features unaffected)

[//]: # ()
[//]: # (4. **Delete old folders** &#40;only after testing&#41;:)

[//]: # (   - `data/preferences/` ← managers moved to features)

[//]: # (   - `data/export/` ← moved to features/export)

[//]: # (   - `data/database/` ← entities moved to features)

[//]: # (   - `data/repository/` ← repos moved to features)

[//]: # (   - `data/utilities/` ← moved to core/utilities &#40;keep ref&#41;)

[//]: # (   - `ui/components/` ← moved to core/components)

[//]: # (   - `ui/theme/` ← moved to core/theme)

[//]: # (   - `ui/navigation/` ← moved to core/navigation)

[//]: # (   - `ui/screens/` ← screens moved to features)

[//]: # (   - `ui/viewmodel/JournalViewModel.kt` ← split into features)

[//]: # (   - `notification/` ← moved to features/notifications)

[//]: # ()
[//]: # (---)

[//]: # ()
[//]: # (## Checklist for Completion)

[//]: # ()
[//]: # (- [ ] Phase 1: Managers moved &#40;5 files&#41;)

[//]: # (- [ ] Phase 2: Core files copied to core/ &#40;20 files&#41;)

[//]: # (- [ ] Phase 3: All 33 screens moved to features)

[//]: # (- [ ] Phase 4: All 15 feature navigation files created)

[//]: # (- [ ] Phase 5: All imports updated)

[//]: # (- [ ] Phase 5a: Core NavGraph updated to use feature sections)

[//]: # (- [ ] Phase 5b: Find & Replace for package imports completed)

[//]: # (- [ ] Phase 5c: All screens reference correct Feature ViewModels)

[//]: # (- [ ] Phase 6: Project builds without errors)

[//]: # (- [ ] Phase 6: Navigation works end-to-end)

[//]: # (- [ ] Phase 6: Delete old folders)

[//]: # (- [ ] Phase 6: Test app functionality)

[//]: # ()
[//]: # (---)

[//]: # ()
[//]: # (## Success Metrics)

[//]: # ()
[//]: # (✅ **Task Complete When:**)

[//]: # (1. Project builds without errors)

[//]: # (2. All screens accessible from navigation)

[//]: # (3. Each feature is self-contained &#40;could delete entire `features/memory` folder and rebuild&#41;)

[//]: # (4. ViewModels are feature-scoped &#40;no god objects&#41;)

[//]: # (5. No cross-feature imports &#40;only through core&#41;)

[//]: # (6. Screens in one feature can't directly reference ViewModels from another)

[//]: # ()
[//]: # (---)

[//]: # ()
[//]: # (## Need Help?)

[//]: # ()
[//]: # (For any stuck step:)

[//]: # (1. Check the pattern section &#40;copy-paste structure&#41;)

[//]: # (2. Refer to created files as examples &#40;MemoryViewModel.kt, MemoryRepository.kt&#41;)

[//]: # (3. Use find/replace systematically instead of manual edits)

[//]: # (4. Test after each phase, not at the end)
