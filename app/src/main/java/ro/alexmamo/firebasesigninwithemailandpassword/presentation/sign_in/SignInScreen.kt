package ro.alexmamo.firebasesigninwithemailandpassword.presentation.sign_in

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ro.alexmamo.firebasesigninwithemailandpassword.R
import ro.alexmamo.firebasesigninwithemailandpassword.components.LoadingIndicator
import ro.alexmamo.firebasesigninwithemailandpassword.core.logMessage
import ro.alexmamo.firebasesigninwithemailandpassword.core.showToastMessage
import ro.alexmamo.firebasesigninwithemailandpassword.domain.model.Response
import ro.alexmamo.firebasesigninwithemailandpassword.navigation.Route
import ro.alexmamo.firebasesigninwithemailandpassword.presentation.sign_in.components.SignInContent
import ro.alexmamo.firebasesigninwithemailandpassword.presentation.sign_in.components.SignInTopBar

@Composable
fun SignInScreen(
    viewModel: SignInViewModel = hiltViewModel(),
    navigate: (Route) -> Unit,
    navigateAndClear: (Route) -> Unit
) {
    val context = LocalContext.current
    val resources = context.resources
    val email by viewModel.email.collectAsStateWithLifecycle()
    val password by viewModel.password.collectAsStateWithLifecycle()
    val signInResponse by viewModel.signInState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            SignInTopBar()
        }
    ) { innerPadding ->
        SignInContent(
            innerPadding = innerPadding,
            email = email,
            onEmailChange = viewModel::onEmailChange,
            onEmptyEmail = {
                showToastMessage(context, resources.getString(R.string.empty_email_message))
            },
            password = password,
            onPasswordChange = viewModel::onPasswordChange,
            onEmptyPassword = {
                showToastMessage(context, resources.getString(R.string.empty_password_message))
            },
            onSigningIn = viewModel::signInWithEmailAndPassword,
            isLoading = signInResponse is Response.Loading,
            onForgotPasswordTextClick = {
                navigate(Route.ForgotPassword)
            },
            onSignUpTextClick = {
                navigate(Route.SignUp)
            }
        )
    }

    when(val signInResponse = signInResponse) {
        is Response.Idle -> {}
        is Response.Loading -> LoadingIndicator()
        is Response.Success -> LaunchedEffect(Unit) {
            if (viewModel.isEmailVerified) {
                navigateAndClear(Route.Profile)
            } else {
                navigateAndClear(Route.VerifyEmail)
            }
        }
        is Response.Failure -> signInResponse.e?.message?.let { errorMessage ->
            LaunchedEffect(errorMessage) {
                logMessage(errorMessage)
                showToastMessage(context, errorMessage)
            }
        }
    }
}