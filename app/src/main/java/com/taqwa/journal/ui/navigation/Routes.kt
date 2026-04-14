package com.taqwa.journal.ui.navigation

object Routes {
    // -- Bottom Navigation (3 tabs) --
    const val HOME = "home"
    const val TOOLS = "tools"
    const val SETTINGS = "settings"

    // -- Urge Intervention Flow --
    const val BREATHING = "breathing"
    const val REALITY_CHECK = "reality_check"
    const val ISLAMIC_REMINDER = "islamic_reminder"
    const val PERSONAL_REMINDER = "personal_reminder"
    const val FUTURE_SELF = "future_self"
    const val QUESTIONS = "questions"
    const val VICTORY = "victory"

    // -- Memory Bank --
    const val QUICK_CATCH = "quick_catch"
    const val MEMORY_BANK = "memory_bank"
    const val WRITE_MEMORY = "write_memory/{memoryType}"

    // -- Entry Detail --
    const val ENTRY_DETAIL = "entry_detail/{entryId}"

    // -- Shield Plans --
    const val SHIELD_PLANS = "shield_plans"
    const val EDIT_SHIELD_PLAN = "edit_shield_plan/{planId}"
    const val NEW_CUSTOM_SHIELD = "new_custom_shield"

    // -- Browse (accessed from Tools) --
    const val PAST_ENTRIES = "past_entries"
    const val CALENDAR = "calendar"
    const val PATTERN_ANALYSIS = "pattern_analysis"
    const val RELAPSE_HISTORY = "relapse_history"

    // -- Knowledge --
    const val KNOWLEDGE_CATEGORIES = "knowledge_categories"
    const val KNOWLEDGE_ARTICLE_LIST = "knowledge_articles/{categoryId}"
    const val KNOWLEDGE_ARTICLE_READER = "knowledge_reader/{articleId}"

    // -- Check-Ins --
    const val MORNING_CHECK_IN = "morning_check_in"
    const val EVENING_CHECK_IN = "evening_check_in"

    // -- Relapse Recovery --
    const val RELAPSE_RECOVERY = "relapse_recovery"

    // -- Standalone --
    const val PROMISE_WALL = "promise_wall"
    const val RESET_STREAK = "reset_streak"
    const val NOTIFICATION_SETTINGS = "notification_settings"
    const val EXPORT = "export"

    // -- Route Builders --
    fun entryDetail(entryId: Int) = "entry_detail/$entryId"
    fun writeMemory(type: String) = "write_memory/$type"
    fun editShieldPlan(planId: String) = "edit_shield_plan/$planId"
    fun knowledgeArticleList(categoryId: String) = "knowledge_articles/$categoryId"
    fun knowledgeArticleReader(articleId: String) = "knowledge_reader/$articleId"

    // -- Route Sets --
    val bottomNavRoutes = setOf(HOME, TOOLS, SETTINGS)

    val urgeFlowRoutes = setOf(
        BREATHING, REALITY_CHECK, ISLAMIC_REMINDER,
        PERSONAL_REMINDER, FUTURE_SELF, QUESTIONS, VICTORY
    )
}