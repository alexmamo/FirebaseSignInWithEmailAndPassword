package ro.alexmamo.firebasesigninwithemailandpassword.presentation.forgot_password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ro.alexmamo.firebasesigninwithemailandpassword.domain.model.Response
import ro.alexmamo.firebasesigninwithemailandpassword.domain.repository.AuthRepository
import javax.inject.Inject

typealias SendPasswordResetEmailResponse = Response<Unit>

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val repo: AuthRepository
): ViewModel() {
    private val _sendPasswordResetEmailState = MutableStateFlow<SendPasswordResetEmailResponse>(Response.Idle)
    val sendPasswordResetEmailState: StateFlow<SendPasswordResetEmailResponse> = _sendPasswordResetEmailState.asStateFlow()

    fun sendPasswordResetEmail(email: String) = viewModelScope.launch {
        try {
            _sendPasswordResetEmailState.value = Response.Idle
            _sendPasswordResetEmailState.value = Response.Success(repo.sendPasswordResetEmail(email))
        } catch (e: Exception) {
            _sendPasswordResetEmailState.value = Response.Failure(e)
        }
    }
}