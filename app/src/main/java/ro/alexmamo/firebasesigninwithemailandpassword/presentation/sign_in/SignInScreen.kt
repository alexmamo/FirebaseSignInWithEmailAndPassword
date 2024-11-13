package ro.alexmamo.firebasesigninwithemailandpassword.presentation.sign_in

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import ro.alexmamo.firebasesigninwithemailandpassword.components.LoadingIndicator
import ro.alexmamo.firebasesigninwithemailandpassword.core.printError
import ro.alexmamo.firebasesigninwithemailandpassword.core.showToastError
import ro.alexmamo.firebasesigninwithemailandpassword.domain.model.Response.Failure
import ro.alexmamo.firebasesigninwithemailandpassword.domain.model.Response.Loading
import ro.alexmamo.firebasesigninwithemailandpassword.domain.model.Response.Success
import ro.alexmamo.firebasesigninwithemailandpassword.navigation.Route
import ro.alexmamo.firebasesigninwithemailandpassword.navigation.Route.ForgotPassword
import ro.alexmamo.firebasesigninwithemailandpassword.navigation.Route.Profile
import ro.alexmamo.firebasesigninwithemailandpassword.navigation.Route.SignUp
import ro.alexmamo.firebasesigninwithemailandpassword.presentation.sign_in.components.SignInContent
import ro.alexmamo.firebasesigninwithemailandpassword.presentation.sign_in.components.SignInTopBar

@Composable
fun SignInScreen(
    viewModel: SignInViewModel = hiltViewModel(),
    navigate: (Route) -> Unit,
    navigateAndClear: (Route) -> Unit
) {
    val context = LocalContext.current
    var signingIn by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            SignInTopBar()
        }
    ) { innerPadding ->
        SignInContent(
            innerPadding = innerPadding,
            onSigningIn = { email, password ->
                viewModel.signInWithEmailAndPassword(email, password)
                signingIn = true
            },
            signingIn = signingIn,
            onForgotPasswordTextClick = {
                navigate(ForgotPassword)
            },
            onSignUpTextClick = {
                navigate(SignUp)
            }
        )
    }

    if (signingIn) {
        when(val signInResponse = viewModel.signInResponse) {
            is Loading -> LoadingIndicator()
            is Success -> {
                navigateAndClear(Profile)
                signingIn = false
            }
            is Failure -> signInResponse.e.let{ e ->
                printError(e)
                showToastError(context, e)
                signingIn = false
            }
        }
    }
}