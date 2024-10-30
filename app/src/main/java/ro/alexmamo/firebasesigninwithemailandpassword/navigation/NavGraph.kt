package ro.alexmamo.firebasesigninwithemailandpassword.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ro.alexmamo.firebasesigninwithemailandpassword.navigation.Screen.ForgotPasswordScreen
import ro.alexmamo.firebasesigninwithemailandpassword.navigation.Screen.ProfileScreen
import ro.alexmamo.firebasesigninwithemailandpassword.navigation.Screen.SignInScreen
import ro.alexmamo.firebasesigninwithemailandpassword.navigation.Screen.SignUpScreen
import ro.alexmamo.firebasesigninwithemailandpassword.navigation.Screen.SplashScreen
import ro.alexmamo.firebasesigninwithemailandpassword.presentation.forgot_password.ForgotPasswordScreen
import ro.alexmamo.firebasesigninwithemailandpassword.presentation.profile.ProfileScreen
import ro.alexmamo.firebasesigninwithemailandpassword.presentation.sign_in.SignInScreen
import ro.alexmamo.firebasesigninwithemailandpassword.presentation.sign_up.SignUpScreen
import ro.alexmamo.firebasesigninwithemailandpassword.presentation.splash.SplashScreen

@Composable
fun NavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = SplashScreen
    ) {
        composable<SplashScreen>  {
            SplashScreen(
                navigateToAndClear = { screen ->
                    navController.navigateToAndClear(screen)
                }
            )
        }
        composable<SignInScreen>  {
            SignInScreen(
                navigateTo = { screen ->
                    navController.navigateTo(screen)
                },
                navigateToAndClear = { screen ->
                    navController.navigateToAndClear(screen)
                }
            )
        }
        composable<ForgotPasswordScreen> {
            ForgotPasswordScreen(
                navigateBack = {
                    navController.navigateUp()
                }
            )
        }
        composable<SignUpScreen> {
            SignUpScreen(
                navigateBack = {
                    navController.navigateUp()
                },
                navigateToAndClear = { screen ->
                    navController.navigateToAndClear(screen)
                }
            )
        }
        composable<ProfileScreen> {
            ProfileScreen(
                navigateToAndClear = { screen ->
                    navController.navigateToAndClear(screen)
                }
            )
        }
    }
}

fun NavHostController.navigateTo(screen: Screen) = navigate(screen)

fun NavHostController.navigateToAndClear(screen: Screen) = navigate(screen) {
    popUpTo(graph.startDestinationId) {
        inclusive = true
    }
    graph.setStartDestination(screen)
}