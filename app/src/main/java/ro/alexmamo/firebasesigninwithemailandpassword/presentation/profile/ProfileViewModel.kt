package ro.alexmamo.firebasesigninwithemailandpassword.presentation.profile

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

typealias DeleteUserResponse = Response<Unit>

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repo: AuthRepository
): ViewModel() {
    private val _deleteUserState = MutableStateFlow<DeleteUserResponse>(Response.Idle)
    val deleteUserState: StateFlow<DeleteUserResponse> = _deleteUserState.asStateFlow()

    fun getAuthState(navigateToSignInScreen: () -> Unit) = viewModelScope.launch {
        repo.getAuthState().collect { isUserSignedOut ->
            if (isUserSignedOut) {
                navigateToSignInScreen()
            }
        }
    }

    fun deleteUser() = viewModelScope.launch {
        try {
            _deleteUserState.value = Response.Loading
            _deleteUserState.value = Response.Success(repo.deleteUser())
        } catch (e: Exception) {
            _deleteUserState.value = Response.Failure(e)
        }
    }

    fun signOut() = repo.signOut()
}