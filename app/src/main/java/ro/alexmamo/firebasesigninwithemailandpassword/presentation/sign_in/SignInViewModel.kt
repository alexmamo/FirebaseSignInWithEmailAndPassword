package ro.alexmamo.firebasesigninwithemailandpassword.presentation.sign_in

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

typealias SignInResponse = Response<Unit>

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val repo: AuthRepository
): ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _signInState = MutableStateFlow<SignInResponse>(Response.Idle)
    val signInState: StateFlow<SignInResponse> = _signInState.asStateFlow()

    fun signInWithEmailAndPassword(email: String, password: String) = viewModelScope.launch {
        _isLoading.value = true
        try {
            _signInState.value = Response.Loading
            _signInState.value = Response.Success(repo.signInWithEmailAndPassword(email, password))
        } catch (e: Exception) {
            _signInState.value = Response.Failure(e)
        } finally {
            _isLoading.value = false
        }
    }
}