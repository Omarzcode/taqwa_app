package com.taqwa.journal.features.urgeflow.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "journal_entries")
data class JournalEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    // Timestamp when entry was created
    val timestamp: Long = System.currentTimeMillis(),

    // Q1: Where were you and what were you doing
    @ColumnInfo(name = "situation_context")
    val situationContext: String = "",

    // Q2: Feelings (stored as comma-separated: "Lonely,Bored,Tired")
    val feelings: String = "",

    // Q3: Real need behind the urge
    @ColumnInfo(name = "real_need")
    val realNeed: String = "",

    // Q4: Alternative activity chosen
    @ColumnInfo(name = "alternative_chosen")
    val alternativeChosen: String = "",

    // Q5: Urge strength 1-10 (MUST be 1-10)
    @ColumnInfo(name = "urge_strength")
    val urgeStrength: Int = 5,

    // Q6: Free text message to self
    @ColumnInfo(name = "free_text")
    val freeText: String = "",

    // Did user complete the full flow
    val completed: Boolean = true
)
