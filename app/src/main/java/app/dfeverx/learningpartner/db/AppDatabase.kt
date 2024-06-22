package app.dfeverx.learningpartner.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import app.dfeverx.learningpartner.db.converter.MarkdownStringListConverter
import app.dfeverx.learningpartner.db.converter.OptionTypeConverter
import app.dfeverx.learningpartner.db.dao.LevelDao
import app.dfeverx.learningpartner.db.dao.QuestionDao
import app.dfeverx.learningpartner.db.dao.StudyNoteDao
import app.dfeverx.learningpartner.db.pre.StudyPartnerDatabaseCallback
import app.dfeverx.learningpartner.models.local.Level
import app.dfeverx.learningpartner.models.local.Question
import app.dfeverx.learningpartner.models.local.StudyNote

@Database(
    entities = [
        StudyNote::class,
        Question::class,
        Level::class
    ],
    version = 2,

    )
@TypeConverters(MarkdownStringListConverter::class, OptionTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun studyNotesDao(): StudyNoteDao
    abstract fun questionDao(): QuestionDao
    abstract fun levelDao(): LevelDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context, dbName: String): AppDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        dbName
                    )
                        .addCallback(StudyPartnerDatabaseCallback())
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }

                return instance
            }
        }
    }


}