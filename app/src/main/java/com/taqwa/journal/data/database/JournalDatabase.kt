package com.taqwa.journal.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [JournalEntry::class, MemoryEntry::class, CheckInEntry::class],
    version = 3,
    exportSchema = false
)
abstract class JournalDatabase : RoomDatabase() {

    abstract fun journalDao(): JournalDao

    companion object {
        @Volatile
        private var INSTANCE: JournalDatabase? = null

        // Migration from v1 (only JournalEntry) to v2 (+ MemoryEntry)
        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("""
                    CREATE TABLE IF NOT EXISTS memory_entries (
                        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        timestamp INTEGER NOT NULL,
                        type TEXT NOT NULL,
                        message TEXT NOT NULL,
                        `trigger` TEXT NOT NULL DEFAULT '',
                        streakAtTime INTEGER NOT NULL DEFAULT 0,
                        urgeStrengthAtTime INTEGER NOT NULL DEFAULT 0,
                        isPinned INTEGER NOT NULL DEFAULT 0
                    )
                """)
            }
        }

        // Migration from v2 to v3 (+ CheckInEntry)
        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("""
                    CREATE TABLE IF NOT EXISTS checkin_entries (
                        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        timestamp INTEGER NOT NULL,
                        date TEXT NOT NULL,
                        mood TEXT NOT NULL,
                        riskLevel TEXT NOT NULL,
                        intention TEXT NOT NULL DEFAULT '',
                        streakAtTime INTEGER NOT NULL DEFAULT 0
                    )
                """)
            }
        }

        // For fresh installs jumping from v1 to v3
        private val MIGRATION_1_3 = object : Migration(1, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("""
                    CREATE TABLE IF NOT EXISTS memory_entries (
                        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        timestamp INTEGER NOT NULL,
                        type TEXT NOT NULL,
                        message TEXT NOT NULL,
                        `trigger` TEXT NOT NULL DEFAULT '',
                        streakAtTime INTEGER NOT NULL DEFAULT 0,
                        urgeStrengthAtTime INTEGER NOT NULL DEFAULT 0,
                        isPinned INTEGER NOT NULL DEFAULT 0
                    )
                """)
                db.execSQL("""
                    CREATE TABLE IF NOT EXISTS checkin_entries (
                        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        timestamp INTEGER NOT NULL,
                        date TEXT NOT NULL,
                        mood TEXT NOT NULL,
                        riskLevel TEXT NOT NULL,
                        intention TEXT NOT NULL DEFAULT '',
                        streakAtTime INTEGER NOT NULL DEFAULT 0
                    )
                """)
            }
        }

        fun getDatabase(context: Context): JournalDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    JournalDatabase::class.java,
                    "taqwa_journal_database"
                )
                    .addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_1_3)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}