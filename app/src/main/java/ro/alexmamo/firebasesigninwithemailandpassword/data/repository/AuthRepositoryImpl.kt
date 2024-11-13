package ro.alexmamo.firebasesigninwithemailandpassword.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import ro.alexmamo.firebasesigninwithemailandpassword.domain.repository.AuthRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
) : AuthRepository {
    override val currentUser get() = auth.currentUser

    override suspend fun signUpWithEmailAndPassword(
        email: String,
        password: String
    ) = auth.createUserWithEmailAndPassword(email, password).await()

    override suspend fun sendEmailVerification() = currentUser?.sendEmailVerification()?.await()

    override suspend fun signInWithEmailAndPassword(
        email: String,
        password: String
    ) = auth.signInWithEmailAndPassword(email, password).await()

    override suspend fun deleteUser() = currentUser?.delete()?.await()

    override suspend fun reloadUser() = currentUser?.reload()?.await()

    override suspend fun sendPasswordResetEmail(
        email: String
    ) = auth.sendPasswordResetEmail(email).await()

    override fun signOut() = auth.signOut()

    override fun getAuthState() = callbackFlow {
        val authStateListener = AuthStateListener { auth ->
            trySend(auth.currentUser == null)
        }
        auth.addAuthStateListener(authStateListener)
        awaitClose {
            auth.removeAuthStateListener(authStateListener)
        }
    }
}