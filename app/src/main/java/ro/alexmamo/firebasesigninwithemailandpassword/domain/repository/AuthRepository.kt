package ro.alexmamo.firebasesigninwithemailandpassword.domain.repository

import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val currentUser: FirebaseUser?

    suspend fun signUpWithEmailAndPassword(email: String, password: String)

    suspend fun sendEmailVerification()

    suspend fun signInWithEmailAndPassword(email: String, password: String)

    suspend fun deleteUser()

    suspend fun reloadUser()

    suspend fun sendPasswordResetEmail(email: String)

    fun signOut()

    fun getAuthState(): Flow<Boolean>
}