package com.alonsorafael.quizapp_android_firebase.di

import android.content.Context
import com.alonsorafael.quizapp_android_firebase.data.local.QuizDatabase
import com.alonsorafael.quizapp_android_firebase.data.local.dao.QuizAttemptDao
import com.alonsorafael.quizapp_android_firebase.data.local.dao.QuizDao
import com.alonsorafael.quizapp_android_firebase.data.local.dao.QuestionDao
import com.alonsorafael.quizapp_android_firebase.data.local.dao.UserDao
import com.alonsorafael.quizapp_android_firebase.data.repository.AuthRepositoryImpl
import com.alonsorafael.quizapp_android_firebase.data.repository.QuizRepositoryImpl
import com.alonsorafael.quizapp_android_firebase.data.repository.QuizAttemptRepositoryImpl
import com.alonsorafael.quizapp_android_firebase.domain.repository.AuthRepository
import com.alonsorafael.quizapp_android_firebase.domain.repository.QuizRepository
import com.alonsorafael.quizapp_android_firebase.domain.repository.QuizAttemptRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideQuizDatabase(@ApplicationContext context: Context): QuizDatabase {
        return QuizDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideUserDao(database: QuizDatabase): UserDao = database.userDao()

    @Provides
    @Singleton
    fun provideQuizDao(database: QuizDatabase): QuizDao = database.quizDao()

    @Provides
    @Singleton
    fun provideQuestionDao(database: QuizDatabase): QuestionDao = database.questionDao()

    @Provides
    @Singleton
    fun provideQuizAttemptDao(database: QuizDatabase): QuizAttemptDao = database.quizAttemptDao()

    @Provides
    @Singleton
    fun provideAuthRepository(
        firebaseAuth: FirebaseAuth,
        firestore: FirebaseFirestore,
        userDao: UserDao
    ): AuthRepository {
        return AuthRepositoryImpl(firebaseAuth, firestore, userDao)
    }

    @Provides
    @Singleton
    fun provideQuizRepository(
        quizDao: QuizDao,
        questionDao: QuestionDao,
        firestore: FirebaseFirestore
    ): QuizRepository {
        return QuizRepositoryImpl(quizDao, questionDao, firestore)
    }

    @Provides
    @Singleton
    fun provideQuizAttemptRepository(
        quizAttemptDao: QuizAttemptDao,
        firestore: FirebaseFirestore
    ): QuizAttemptRepository {
        return QuizAttemptRepositoryImpl(quizAttemptDao, firestore)
    }
}
