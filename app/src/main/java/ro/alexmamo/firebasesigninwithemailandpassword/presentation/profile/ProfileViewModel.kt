package ro.alexmamo.firebasesigninwithemailandpassword.presentation.profile

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

typealias DeleteUserResponse = Response<Unit>
typealias ReloadUserResponse = Response<Unit>

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repo: AuthRepository
): ViewModel() {
    var deleteUserResponse by mutableStateOf<DeleteUserResponse>(Loading)
        private set
    var reloadUserResponse by mutableStateOf<ReloadUserResponse>(Loading)
        private set

    fun getAuthState(navigateToSignInScreen: () -> Unit) = viewModelScope.launch {
        repo.getAuthState().collect { isUserSignedOut ->
            if (isUserSignedOut) {
                navigateToSignInScreen()
            }
        }
    }

    fun deleteUser() = viewModelScope.launch {
        deleteUserResponse = launchCatching {
            repo.deleteUser()
        }
    }

    fun reloadUser() = viewModelScope.launch {
        reloadUserResponse = launchCatching {
            repo.reloadUser()
        }
    }

    val isEmailVerified get() = repo.currentUser?.isEmailVerified == true

    fun signOut() = repo.signOut()
}