package com.alonsorafael.quizapp_android_firebase.data.repository

import com.alonsorafael.quizapp_android_firebase.data.local.dao.UserDao
import com.alonsorafael.quizapp_android_firebase.data.mapper.toUser
import com.alonsorafael.quizapp_android_firebase.data.mapper.toUserEntity
import com.alonsorafael.quizapp_android_firebase.domain.model.User
import com.alonsorafael.quizapp_android_firebase.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val userDao: UserDao
) : AuthRepository {

    override val currentUser: Flow<User?> = callbackFlow {
        val listener = FirebaseAuth.AuthStateListener { auth ->
            val user = auth.currentUser
            if (user != null) {
                // Fetch user from local database
                userDao.getUserById(user.uid).collect { userEntity ->
                    trySend(userEntity?.toUser())
                }
            } else {
                trySend(null)
            }
        }
        firebaseAuth.addAuthStateListener(listener)
        awaitClose { firebaseAuth.removeAuthStateListener(listener) }
    }

    override val isAuthenticated: Flow<Boolean> = callbackFlow {
        val listener = FirebaseAuth.AuthStateListener { auth ->
            trySend(auth.currentUser != null)
        }
        firebaseAuth.addAuthStateListener(listener)
        awaitClose { firebaseAuth.removeAuthStateListener(listener) }
    }

    override suspend fun signInWithEmail(email: String, password: String): Result<User> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            val firebaseUser = result.user
            if (firebaseUser != null) {
                val user = User(
                    uid = firebaseUser.uid,
                    email = firebaseUser.email ?: "",
                    displayName = firebaseUser.displayName,
                    photoUrl = firebaseUser.photoUrl?.toString()
                )
                userDao.insertUser(user.toUserEntity())
                Result.success(user)
            } else {
                Result.failure(Exception("Authentication failed"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun signInWithGoogle(idToken: String): Result<User> {
        return try {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            val result = firebaseAuth.signInWithCredential(credential).await()
            val firebaseUser = result.user
            if (firebaseUser != null) {
                val user = User(
                    uid = firebaseUser.uid,
                    email = firebaseUser.email ?: "",
                    displayName = firebaseUser.displayName,
                    photoUrl = firebaseUser.photoUrl?.toString()
                )
                userDao.insertUser(user.toUserEntity())
                Result.success(user)
            } else {
                Result.failure(Exception("Google authentication failed"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun signUpWithEmail(email: String, password: String, displayName: String): Result<User> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            val firebaseUser = result.user
            if (firebaseUser != null) {
                val profileUpdates = com.google.firebase.auth.UserProfileChangeRequest.Builder()
                    .setDisplayName(displayName)
                    .build()
                firebaseUser.updateProfile(profileUpdates).await()
                
                val user = User(
                    uid = firebaseUser.uid,
                    email = firebaseUser.email ?: "",
                    displayName = displayName,
                    photoUrl = null
                )
                userDao.insertUser(user.toUserEntity())
                Result.success(user)
            } else {
                Result.failure(Exception("User creation failed"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun signOut() {
        firebaseAuth.signOut()
    }

    override suspend fun updateUserProfile(displayName: String?, photoUrl: String?): Result<User> {
        return try {
            val firebaseUser = firebaseAuth.currentUser
            if (firebaseUser != null) {
                val profileUpdates = com.google.firebase.auth.UserProfileChangeRequest.Builder()
                    .setDisplayName(displayName)
                    .setPhotoUri(photoUrl?.let { android.net.Uri.parse(it) })
                    .build()
                firebaseUser.updateProfile(profileUpdates).await()
                
                val user = User(
                    uid = firebaseUser.uid,
                    email = firebaseUser.email ?: "",
                    displayName = displayName,
                    photoUrl = photoUrl
                )
                userDao.updateUser(user.toUserEntity())
                Result.success(user)
            } else {
                Result.failure(Exception("No user signed in"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteAccount(): Result<Unit> {
        return try {
            val firebaseUser = firebaseAuth.currentUser
            if (firebaseUser != null) {
                firebaseUser.delete().await()
                userDao.deleteUserById(firebaseUser.uid)
                Result.success(Unit)
            } else {
                Result.failure(Exception("No user signed in"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
