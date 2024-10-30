package ro.alexmamo.firebasesigninwithemailandpassword.presentation.sign_in

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import ro.alexmamo.firebasesigninwithemailandpassword.components.ProgressBar
import ro.alexmamo.firebasesigninwithemailandpassword.core.printError
import ro.alexmamo.firebasesigninwithemailandpassword.core.toastError
import ro.alexmamo.firebasesigninwithemailandpassword.domain.model.Response.Failure
import ro.alexmamo.firebasesigninwithemailandpassword.domain.model.Response.Loading
import ro.alexmamo.firebasesigninwithemailandpassword.domain.model.Response.Success
import ro.alexmamo.firebasesigninwithemailandpassword.navigation.Screen
import ro.alexmamo.firebasesigninwithemailandpassword.navigation.Screen.ForgotPasswordScreen
import ro.alexmamo.firebasesigninwithemailandpassword.navigation.Screen.ProfileScreen
import ro.alexmamo.firebasesigninwithemailandpassword.navigation.Screen.SignUpScreen
import ro.alexmamo.firebasesigninwithemailandpassword.presentation.sign_in.components.SignInContent
import ro.alexmamo.firebasesigninwithemailandpassword.presentation.sign_in.components.SignInTopBar

@Composable
fun SignInScreen(
    viewModel: SignInViewModel = hiltViewModel(),
    navigateTo: (screen: Screen) -> Unit,
    navigateToAndClear: (screen: Screen) -> Unit
) {
    val context = LocalContext.current
    var signingIn by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            SignInTopBar()
        },
        content = { padding ->
            SignInContent(
                padding = padding,
                signIn = { email, password ->
                    viewModel.signInWithEmailAndPassword(email, password)
                    signingIn = true
                },
                signingIn = signingIn,
                navigateToForgotPasswordScreen = {
                    navigateTo(ForgotPasswordScreen)
                },
                navigateToSignUpScreen = {
                    navigateTo(SignUpScreen)
                }
            )
        }
    )

    if (signingIn) {
        when(val signInResponse = viewModel.signInResponse) {
            is Loading -> ProgressBar()
            is Success -> signInResponse.data.let { isSignedIn ->
                if (isSignedIn) {
                    navigateToAndClear(ProfileScreen)
                    signingIn = false
                }
            }
            is Failure -> signInResponse.e.let{ e ->
                printError(e)
                toastError(context, e)
                signingIn = false
            }
        }
    }
}