package ro.alexmamo.firebasesigninwithemailandpassword.presentation.splash

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ro.alexmamo.firebasesigninwithemailandpassword.domain.repository.AuthRepository
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val repo: AuthRepository
): ViewModel() {
    val isUserSignedOut get() = repo.currentUser == null
}