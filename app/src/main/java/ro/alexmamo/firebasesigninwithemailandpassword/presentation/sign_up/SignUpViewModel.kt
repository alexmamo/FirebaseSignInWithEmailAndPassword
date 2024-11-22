package ro.alexmamo.firebasesigninwithemailandpassword.presentation.sign_up

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ro.alexmamo.firebasesigninwithemailandpassword.core.launchCatching
import ro.alexmamo.firebasesigninwithemailandpassword.domain.model.Response
import ro.alexmamo.firebasesigninwithemailandpassword.domain.model.Response.Loading
import ro.alexmamo.firebasesigninwithemailandpassword.domain.repository.AuthRepository
import javax.inject.Inject

typealias SignUpResponse = Response<Unit>
typealias SendEmailVerificationResponse = Response<Unit>

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val repo: AuthRepository
): ViewModel() {
    var signUpResponse by mutableStateOf<SignUpResponse>(Loading)
        private set
    var sendEmailVerificationResponse by mutableStateOf<SendEmailVerificationResponse>(Loading)
        private set

    fun signUpWithEmailAndPassword(email: String, password: String) = viewModelScope.launch {
        signUpResponse = launchCatching {
            repo.signUpWithEmailAndPassword(email, password)
        }
    }

    fun sendEmailVerification() = viewModelScope.launch {
        sendEmailVerificationResponse = launchCatching {
            repo.sendEmailVerification()
        }
    }
}