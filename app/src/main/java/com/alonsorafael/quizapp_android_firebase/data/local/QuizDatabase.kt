package com.alonsorafael.quizapp_android_firebase.data.local

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import android.content.Context
import com.alonsorafael.quizapp_android_firebase.data.local.converter.Converters
import com.alonsorafael.quizapp_android_firebase.data.local.dao.QuizAttemptDao
import com.alonsorafael.quizapp_android_firebase.data.local.dao.QuizDao
import com.alonsorafael.quizapp_android_firebase.data.local.dao.QuestionDao
import com.alonsorafael.quizapp_android_firebase.data.local.dao.UserDao
import com.alonsorafael.quizapp_android_firebase.data.local.entity.*

@Database(
    entities = [
        UserEntity::class,
        QuizEntity::class,
        QuestionEntity::class,
        QuizAttemptEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class QuizDatabase : RoomDatabase() {
    
    abstract fun userDao(): UserDao
    abstract fun quizDao(): QuizDao
    abstract fun questionDao(): QuestionDao
    abstract fun quizAttemptDao(): QuizAttemptDao

    companion object {
        @Volatile
        private var INSTANCE: QuizDatabase? = null

        fun getDatabase(context: Context): QuizDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    QuizDatabase::class.java,
                    "quiz_database"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
