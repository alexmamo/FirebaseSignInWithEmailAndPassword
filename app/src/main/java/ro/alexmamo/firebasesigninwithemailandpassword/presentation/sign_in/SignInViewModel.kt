package ro.alexmamo.firebasesigninwithemailandpassword.presentation.sign_in

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ro.alexmamo.firebasesigninwithemailandpassword.domain.model.Response.Loading
import ro.alexmamo.firebasesigninwithemailandpassword.domain.model.Response.Success
import ro.alexmamo.firebasesigninwithemailandpassword.domain.repository.AuthRepository
import ro.alexmamo.firebasesigninwithemailandpassword.domain.repository.SignInResponse
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val repo: AuthRepository
): ViewModel() {
    var signInResponse by mutableStateOf<SignInResponse>(Success(false))
        private set

    fun signInWithEmailAndPassword(
        email: String,
        password: String
    ) = viewModelScope.launch {
        signInResponse = Loading
        signInResponse = repo.signInWithEmailAndPassword(email, password)
    }
}