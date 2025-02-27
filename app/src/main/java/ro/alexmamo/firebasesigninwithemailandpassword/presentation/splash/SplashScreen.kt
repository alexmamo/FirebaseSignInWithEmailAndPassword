package ro.alexmamo.firebasesigninwithemailandpassword.presentation.splash

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import ro.alexmamo.firebasesigninwithemailandpassword.navigation.Route

@Composable
fun SplashScreen(
    viewModel: SplashViewModel = hiltViewModel(),
    navigateAndClear: (Route) -> Unit
) {
    if (viewModel.isUserSignedOut) {
        navigateAndClear(Route.SignIn)
    } else {
        if (viewModel.isEmailVerified) {
            navigateAndClear(Route.Profile)
        } else {
            navigateAndClear(Route.VerifyEmail)
        }
    }
}