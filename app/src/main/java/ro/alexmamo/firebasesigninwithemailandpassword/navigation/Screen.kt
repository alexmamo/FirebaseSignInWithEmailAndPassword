package ro.alexmamo.firebasesigninwithemailandpassword.navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    object SplashScreen : Screen()

    @Serializable
    object SignInScreen : Screen()

    @Serializable
    object ForgotPasswordScreen : Screen()

    @Serializable
    object SignUpScreen : Screen()

    @Serializable
    object ProfileScreen : Screen()
}