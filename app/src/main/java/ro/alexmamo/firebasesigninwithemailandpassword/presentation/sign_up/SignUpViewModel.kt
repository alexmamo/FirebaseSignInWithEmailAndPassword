package ro.alexmamo.firebasesigninwithemailandpassword.presentation.sign_up

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ro.alexmamo.firebasesigninwithemailandpassword.core.EMPTY_STRING
import ro.alexmamo.firebasesigninwithemailandpassword.domain.model.Response
import ro.alexmamo.firebasesigninwithemailandpassword.domain.repository.AuthRepository
import javax.inject.Inject

typealias SignUpResponse = Response<Unit>
typealias EmailVerificationResponse = Response<Unit>

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val repo: AuthRepository
): ViewModel() {
    private val _email = MutableStateFlow(TextFieldValue(EMPTY_STRING))
    val email: StateFlow<TextFieldValue> = _email.asStateFlow()

    private val _password = MutableStateFlow(TextFieldValue(EMPTY_STRING))
    val password: StateFlow<TextFieldValue> = _password.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _signUpState = MutableStateFlow<SignUpResponse>(Response.Idle)
    val signUpState: StateFlow<SignUpResponse> = _signUpState.asStateFlow()

    private val _emailVerificationState = MutableStateFlow<EmailVerificationResponse>(Response.Idle)
    val emailVerificationState: StateFlow<EmailVerificationResponse> = _emailVerificationState.asStateFlow()

    fun onEmailChange(newEmail: TextFieldValue) {
        _email.value = newEmail
    }

    fun onPasswordChange(newPassword: TextFieldValue) {
        _password.value = newPassword
    }

    fun signUpWithEmailAndPassword(email: String, password: String) = viewModelScope.launch {
        _isLoading.value = true
        try {
            _signUpState.value = Response.Loading
            _signUpState.value = Response.Success(repo.signUpWithEmailAndPassword(email, password))
        } catch (e: Exception) {
            _signUpState.value = Response.Failure(e)
            _isLoading.value = false
        }
    }

    fun sendEmailVerification() = viewModelScope.launch {
        try {
            _emailVerificationState.value = Response.Loading
            _emailVerificationState.value = Response.Success(repo.sendEmailVerification())
        } catch (e: Exception) {
            _emailVerificationState.value = Response.Failure(e)
        } finally {
            _isLoading.value = false
        }
    }
}