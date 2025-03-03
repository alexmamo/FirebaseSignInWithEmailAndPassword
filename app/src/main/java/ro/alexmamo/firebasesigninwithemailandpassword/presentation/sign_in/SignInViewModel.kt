package ro.alexmamo.firebasesigninwithemailandpassword.presentation.sign_in

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

typealias SignInResponse = Response<Unit>

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val repo: AuthRepository
): ViewModel() {
    private val _email = MutableStateFlow(TextFieldValue(EMPTY_STRING))
    val email: StateFlow<TextFieldValue> = _email.asStateFlow()

    private val _password = MutableStateFlow(TextFieldValue(EMPTY_STRING))
    val password: StateFlow<TextFieldValue> = _password.asStateFlow()

    private val _signInState = MutableStateFlow<SignInResponse>(Response.Idle)
    val signInState: StateFlow<SignInResponse> = _signInState.asStateFlow()

    fun onEmailChange(newEmail: TextFieldValue) {
        _email.value = newEmail
    }

    fun onPasswordChange(newPassword: TextFieldValue) {
        _password.value = newPassword
    }

    fun signInWithEmailAndPassword(email: String, password: String) = viewModelScope.launch {
        try {
            _signInState.value = Response.Loading
            _signInState.value = Response.Success(repo.signInWithEmailAndPassword(email, password))
        } catch (e: Exception) {
            _signInState.value = Response.Failure(e)
        }
    }

    val isEmailVerified get() = repo.currentUser?.isEmailVerified == true
}