package ro.alexmamo.firebasesigninwithemailandpassword.presentation.splash

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import ro.alexmamo.firebasesigninwithemailandpassword.navigation.Route
import ro.alexmamo.firebasesigninwithemailandpassword.navigation.Route.Profile
import ro.alexmamo.firebasesigninwithemailandpassword.navigation.Route.SignIn

@Composable
fun SplashScreen(
    viewModel: SplashViewModel = hiltViewModel(),
    navigateAndClear: (Route) -> Unit
) {
    if (viewModel.isUserSignedOut) {
        navigateAndClear(SignIn)
    } else {
        navigateAndClear(Profile)
    }
}