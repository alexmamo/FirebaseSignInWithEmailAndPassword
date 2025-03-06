package ro.alexmamo.firebasesigninwithemailandpassword.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
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
        startDestination = Route.Splash
    ) {
        composable<Route.Splash>  {
            SplashScreen(
                navigateAndClear = navController::navigateAndClear
            )
        }
        composable<Route.SignIn>  {
            SignInScreen(
                navigate = navController::navigate,
                navigateAndClear = navController::navigateAndClear
            )
        }
        composable<Route.ForgotPassword> {
            ForgotPasswordScreen(
                navigateBack = navController::navigateUp
            )
        }
        composable<Route.SignUp> {
            SignUpScreen(
                navigateBack = navController::navigateUp,
                navigateAndClear = navController::navigateAndClear
            )
        }
        composable<Route.VerifyEmail> {
            VerifyEmailScreen(
                navigateAndClear = navController::navigateAndClear
            )
        }
        composable<Route.Profile> {
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