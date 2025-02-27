package ro.alexmamo.firebasesigninwithemailandpassword.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ro.alexmamo.firebasesigninwithemailandpassword.navigation.Route.ForgotPassword
import ro.alexmamo.firebasesigninwithemailandpassword.navigation.Route.Profile
import ro.alexmamo.firebasesigninwithemailandpassword.navigation.Route.SignIn
import ro.alexmamo.firebasesigninwithemailandpassword.navigation.Route.SignUp
import ro.alexmamo.firebasesigninwithemailandpassword.navigation.Route.Splash
import ro.alexmamo.firebasesigninwithemailandpassword.navigation.Route.VerifyEmail
import ro.alexmamo.firebasesigninwithemailandpassword.presentation.forgot_password.ForgotPasswordScreen
import ro.alexmamo.firebasesigninwithemailandpassword.presentation.profile.ProfileScreen
import ro.alexmamo.firebasesigninwithemailandpassword.presentation.sign_in.SignInScreen
import ro.alexmamo.firebasesigninwithemailandpassword.presentation.sign_up.SignUpScreen
import ro.alexmamo.firebasesigninwithemailandpassword.presentation.splash.SplashScreen
import ro.alexmamo.firebasesigninwithemailandpassword.presentation.verify_email.VerifyEmailScreen

@Composable
fun NavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Splash
    ) {
        composable<Splash>  {
            SplashScreen(
                navigateAndClear = navController::navigateAndClear
            )
        }
        composable<SignIn>  {
            SignInScreen(
                navigate = navController::navigate,
                navigateAndClear = navController::navigateAndClear
            )
        }
        composable<ForgotPassword> {
            ForgotPasswordScreen(
                navigateBack = navController::navigateUp
            )
        }
        composable<SignUp> {
            SignUpScreen(
                navigateBack = navController::navigateUp,
                navigateAndClear = navController::navigateAndClear
            )
        }
        composable<VerifyEmail> {
            VerifyEmailScreen(
                navigateAndClear = navController::navigateAndClear
            )
        }
        composable<Profile> {
            ProfileScreen(
                navigateAndClear = navController::navigateAndClear
            )
        }
    }
}

fun NavHostController.navigateAndClear(route: Route) = navigate(route) {
    popUpTo(graph.startDestinationId) {
        inclusive = true
    }
    graph.setStartDestination(route)
}