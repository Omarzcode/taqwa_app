package com.taqwa.journal.core.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.taqwa.journal.features.urgeflow.data.JournalEntry
import com.taqwa.journal.features.memory.data.MemoryEntry
import com.taqwa.journal.features.checkin.data.CheckInEntry
import com.taqwa.journal.features.urgeflow.data.JournalDao
import com.taqwa.journal.features.memory.data.MemoryDao
import com.taqwa.journal.features.checkin.data.CheckInDao

@Database(
    entities = [JournalEntry::class, MemoryEntry::class, CheckInEntry::class],
    version = 4,
    exportSchema = false
)
abstract class TaqwaDatabase : RoomDatabase() {

    abstract fun journalDao(): JournalDao
    abstract fun memoryDao(): MemoryDao
    abstract fun checkInDao(): CheckInDao

    companion object {
        @Volatile
        private var INSTANCE: TaqwaDatabase? = null

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

        // Migration from v2 to v3 (+ CheckInEntry with wrong column name)
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

        // Migration from v3 to v4: Fix column name riskLevel -> risk_level
        // and streakAtTime -> streak_at_time to match @ColumnInfo annotations
        private val MIGRATION_3_4 = object : Migration(3, 4) {
            override fun migrate(db: SupportSQLiteDatabase) {
                // Recreate checkin_entries with correct column names
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

                // Copy data from old table to new (mapping old column names to new)
                db.execSQL("""
                    INSERT INTO checkin_entries_new (id, timestamp, date, mood, risk_level, intention, streak_at_time)
                    SELECT id, timestamp, date, mood, riskLevel, intention, streakAtTime
                    FROM checkin_entries
                """)

                // Drop old table
                db.execSQL("DROP TABLE checkin_entries")

                // Rename new table
                db.execSQL("ALTER TABLE checkin_entries_new RENAME TO checkin_entries")

                // Recreate unique index on date
                db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS index_checkin_entries_date ON checkin_entries (date)")
            }
        }

        // For fresh installs jumping from v1 to v4
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

        fun getDatabase(context: Context): TaqwaDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TaqwaDatabase::class.java,
                    "taqwa_database"
                )
                    .addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4, MIGRATION_1_3)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
