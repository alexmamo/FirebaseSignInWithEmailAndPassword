package ro.alexmamo.firebasesigninwithemailandpassword.domain.repository

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

typealias AuthStateResponse = Flow<Boolean>

interface AuthRepository {
    val currentUser: FirebaseUser?

    suspend fun signUpWithEmailAndPassword(
        email: String,
        password: String
    ): AuthResult?

    suspend fun sendEmailVerification(): Void?

    suspend fun signInWithEmailAndPassword(
        email: String,
        password: String
    ): AuthResult

    suspend fun deleteUser(): Void?

    suspend fun reloadUser(): Void?

    suspend fun sendPasswordResetEmail(
        email: String
    ): Void?

    fun signOut()

    fun getAuthState(): AuthStateResponse
}