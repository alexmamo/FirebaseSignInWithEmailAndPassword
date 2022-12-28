package ro.alexmamo.firebasesigninwithemailandpassword.presentation.sign_in

import android.widget.Toast.LENGTH_LONG
import android.widget.Toast.makeText
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import ro.alexmamo.firebasesigninwithemailandpassword.presentation.sign_in.components.SignIn
import ro.alexmamo.firebasesigninwithemailandpassword.presentation.sign_in.components.SignInContent
import ro.alexmamo.firebasesigninwithemailandpassword.presentation.sign_in.components.SignInTopBar

@Composable
@ExperimentalComposeUiApi
fun SignInScreen(
    viewModel: SignInViewModel = hiltViewModel(),
    navigateToForgotPasswordScreen: () -> Unit,
    navigateToSignUpScreen: () -> Unit,
) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            SignInTopBar()
        },
        content = { padding ->
            SignInContent(
                padding = padding,
                signIn = { email, password ->
                    viewModel.signInWithEmailAndPassword(email, password)
                },
                navigateToForgotPasswordScreen = navigateToForgotPasswordScreen,
                navigateToSignUpScreen = navigateToSignUpScreen
            )
        }
    )

    SignIn(
        showErrorMessage = { e ->
            makeText(context, e.message, LENGTH_LONG).show()
        }
    )
}