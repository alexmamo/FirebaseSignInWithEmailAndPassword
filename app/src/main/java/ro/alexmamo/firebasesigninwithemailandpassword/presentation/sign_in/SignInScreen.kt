package ro.alexmamo.firebasesigninwithemailandpassword.presentation.sign_in

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ro.alexmamo.firebasesigninwithemailandpassword.R
import ro.alexmamo.firebasesigninwithemailandpassword.components.LoadingIndicator
import ro.alexmamo.firebasesigninwithemailandpassword.core.logErrorMessage
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
    val email by viewModel.email.collectAsStateWithLifecycle()
    val password by viewModel.password.collectAsStateWithLifecycle()
    val signInResponse by viewModel.signInState.collectAsStateWithLifecycle()
    val invalidEmailMessage = stringResource(R.string.invalid_email_message)
    val invalidPasswordMessage = stringResource(R.string.invalid_password_message)

    Scaffold(
        topBar = {
            SignInTopBar()
        }
    ) { innerPadding ->
        SignInContent(
            innerPadding = innerPadding,
            email = email,
            onEmailChange = viewModel::onEmailChange,
            onEmailInvalid = {
                showToastMessage(context, invalidEmailMessage)
            },
            password = password,
            onPasswordChange = viewModel::onPasswordChange,
            onPasswordInvalid = {
                showToastMessage(context, invalidPasswordMessage)
            },
            onSignIn = viewModel::signInWithEmailAndPassword,
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
                logErrorMessage(errorMessage)
                showToastMessage(context, errorMessage)
            }
        }
    }
}