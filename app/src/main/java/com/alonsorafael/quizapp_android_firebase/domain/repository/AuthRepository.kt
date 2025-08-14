package com.alonsorafael.quizapp_android_firebase.domain.repository

import com.alonsorafael.quizapp_android_firebase.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val currentUser: Flow<User?>
    val isAuthenticated: Flow<Boolean>
    
    suspend fun signInWithEmail(email: String, password: String): Result<User>
    suspend fun signInWithGoogle(idToken: String): Result<User>
    suspend fun signUpWithEmail(email: String, password: String, displayName: String): Result<User>
    suspend fun signOut()
    suspend fun updateUserProfile(displayName: String?, photoUrl: String?): Result<User>
    suspend fun deleteAccount(): Result<Unit>
}
