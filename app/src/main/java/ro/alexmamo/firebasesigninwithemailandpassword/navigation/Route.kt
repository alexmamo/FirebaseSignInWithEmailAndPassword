package ro.alexmamo.firebasesigninwithemailandpassword.navigation

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    object Splash : Route

    @Serializable
    object SignIn : Route

    @Serializable
    object ForgotPassword : Route

    @Serializable
    object SignUp : Route

    @Serializable
    object VerifyEmail : Route

    @Serializable
    object Profile : Route
}