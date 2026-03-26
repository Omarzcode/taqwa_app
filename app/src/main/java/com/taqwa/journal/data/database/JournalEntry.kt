package com.taqwa.journal.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "journal_entries")
data class JournalEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    // Timestamp when entry was created
    val timestamp: Long = System.currentTimeMillis(),

    // Q1: Where were you and what were you doing
    val situationContext: String = "",

    // Q2: Feelings (stored as comma-separated: "Lonely,Bored,Tired")
    val feelings: String = "",

    // Q3: Real need behind the urge
    val realNeed: String = "",

    // Q4: Alternative activity chosen
    val alternativeChosen: String = "",

    // Q5: Urge strength 1-10
    val urgeStrength: Int = 5,

    // Q6: Free text message to self
    val freeText: String = "",

    // Did user complete the full flow
    val completed: Boolean = true
)