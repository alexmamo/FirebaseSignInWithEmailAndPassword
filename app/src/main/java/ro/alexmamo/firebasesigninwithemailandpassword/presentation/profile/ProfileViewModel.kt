package ro.alexmamo.firebasesigninwithemailandpassword.presentation.profile

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
import ro.alexmamo.firebasesigninwithemailandpassword.domain.repository.ReloadUserResponse
import ro.alexmamo.firebasesigninwithemailandpassword.domain.repository.DeleteUserResponse
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repo: AuthRepository
): ViewModel() {
    var deleteUserResponse by mutableStateOf<DeleteUserResponse>(Success(false))
        private set
    var reloadUserResponse by mutableStateOf<ReloadUserResponse>(Success(false))
        private set

    fun getAuthState(navigateToSignInScreen: () -> Unit) = viewModelScope.launch {
        repo.getAuthState().collect { isUserSignedOut ->
            if (isUserSignedOut) {
                navigateToSignInScreen()
            }
        }
    }

    fun reloadUser() = viewModelScope.launch {
        reloadUserResponse = Loading
        reloadUserResponse = repo.reloadUser()
    }

    val isEmailVerified get() = repo.currentUser?.isEmailVerified == true

    fun signOut() = repo.signOut()

    fun deleteUser() = viewModelScope.launch {
        deleteUserResponse = Loading
        deleteUserResponse = repo.deleteUser()
    }
}