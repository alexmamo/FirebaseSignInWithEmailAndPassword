package ro.alexmamo.firebasesigninwithemailandpassword.presentation.splash

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import ro.alexmamo.firebasesigninwithemailandpassword.navigation.Screen
import ro.alexmamo.firebasesigninwithemailandpassword.navigation.Screen.ProfileScreen
import ro.alexmamo.firebasesigninwithemailandpassword.navigation.Screen.SignInScreen

@Composable
fun SplashScreen(
    viewModel: SplashScreenViewModel = hiltViewModel(),
    navigateToAndClear: (screen: Screen) -> Unit
) {
    if (viewModel.isUserSignedOut) {
        navigateToAndClear(SignInScreen)
    } else {
        navigateToAndClear(ProfileScreen)
    }
}