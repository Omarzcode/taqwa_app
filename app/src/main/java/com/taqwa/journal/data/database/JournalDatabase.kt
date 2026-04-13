package com.taqwa.journal.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [JournalEntry::class, MemoryEntry::class, CheckInEntry::class, EveningCheckInEntry::class],
    version = 6,
    exportSchema = false
)
abstract class JournalDatabase : RoomDatabase() {

    abstract fun journalDao(): JournalDao

    companion object {
        @Volatile
        private var INSTANCE: JournalDatabase? = null

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

        private val MIGRATION_3_4 = object : Migration(3, 4) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("""
                    CREATE TABLE IF NOT EXISTS checkin_entries_new (
                        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        timestamp INTEGER NOT NULL,
                        date TEXT NOT NULL,
                        mood TEXT NOT NULL,
                        risk_level TEXT NOT NULL,
                        intention TEXT NOT NULL DEFAULT '',
                        streak_at_time INTEGER NOT NULL DEFAULT 0
                    )
                """)
                db.execSQL("""
                    INSERT INTO checkin_entries_new (id, timestamp, date, mood, risk_level, intention, streak_at_time)
                    SELECT id, timestamp, date, mood, riskLevel, intention, streakAtTime
                    FROM checkin_entries
                """)
                db.execSQL("DROP TABLE checkin_entries")
                db.execSQL("ALTER TABLE checkin_entries_new RENAME TO checkin_entries")
                db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS index_checkin_entries_date ON checkin_entries (date)")
            }
        }

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

        val MIGRATION_4_5 = object : Migration(4, 5) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE checkin_entries ADD COLUMN sleep_quality TEXT NOT NULL DEFAULT ''")
                db.execSQL("ALTER TABLE checkin_entries ADD COLUMN gratitude TEXT NOT NULL DEFAULT ''")
                db.execSQL("ALTER TABLE checkin_entries ADD COLUMN yesterday_followed INTEGER NOT NULL DEFAULT 0")
                db.execSQL("ALTER TABLE checkin_entries ADD COLUMN is_morning INTEGER NOT NULL DEFAULT 1")
            }
        }

        val MIGRATION_5_6 = object : Migration(5, 6) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("""
                    CREATE TABLE IF NOT EXISTS evening_checkin_entries (
                        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        timestamp INTEGER NOT NULL,
                        date TEXT NOT NULL,
                        intention_followed TEXT,
                        intention_note TEXT,
                        prayed_five INTEGER NOT NULL DEFAULT 0,
                        morning_adhkar INTEGER NOT NULL DEFAULT 0,
                        evening_adhkar INTEGER NOT NULL DEFAULT 0,
                        read_quran INTEGER NOT NULL DEFAULT 0,
                        made_istighfar INTEGER NOT NULL DEFAULT 0,
                        lowered_gaze INTEGER NOT NULL DEFAULT 0,
                        spiritual_score INTEGER NOT NULL DEFAULT 0,
                        hardest_moment TEXT,
                        hardest_trigger TEXT,
                        wins TEXT,
                        tomorrow_concern TEXT,
                        streak_at_time INTEGER NOT NULL DEFAULT 0
                    )
                """)
                db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS index_evening_checkin_entries_date ON evening_checkin_entries (date)")
            }
        }

        fun getDatabase(context: Context): JournalDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    JournalDatabase::class.java,
                    "taqwa_journal_database"
                )
                    .addMigrations(
                        MIGRATION_1_2,
                        MIGRATION_2_3,
                        MIGRATION_1_3,
                        MIGRATION_3_4,
                        MIGRATION_4_5,
                        MIGRATION_5_6
                    )
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}