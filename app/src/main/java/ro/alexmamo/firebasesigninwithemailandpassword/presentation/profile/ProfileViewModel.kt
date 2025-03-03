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
    private val _authState = MutableStateFlow<Boolean>(repo.currentUser == null)
    val authState: StateFlow<Boolean> = _authState.asStateFlow()

    private val _deleteUserState = MutableStateFlow<DeleteUserResponse>(Response.Idle)
    val deleteUserState: StateFlow<DeleteUserResponse> = _deleteUserState.asStateFlow()

    init {
        getAuthState()
    }

    private fun getAuthState() = viewModelScope.launch {
        repo.getAuthState().collect { isUserSignedOut ->
            _authState.value = isUserSignedOut
        }
    }

    fun signOut() = repo.signOut()

    fun deleteUser() = viewModelScope.launch {
        try {
            _deleteUserState.value = Response.Loading
            _deleteUserState.value = Response.Success(repo.deleteUser())
        } catch (e: Exception) {
            _deleteUserState.value = Response.Failure(e)
        }
    }
}