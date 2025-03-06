package ro.alexmamo.firebasesigninwithemailandpassword.presentation.sign_up

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
import ro.alexmamo.firebasesigninwithemailandpassword.core.logMessage
import ro.alexmamo.firebasesigninwithemailandpassword.core.showToastMessage
import ro.alexmamo.firebasesigninwithemailandpassword.domain.model.Response
import ro.alexmamo.firebasesigninwithemailandpassword.navigation.Route
import ro.alexmamo.firebasesigninwithemailandpassword.presentation.sign_up.components.SignUpContent
import ro.alexmamo.firebasesigninwithemailandpassword.presentation.sign_up.components.SignUpTopBar

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
    navigateAndClear: (Route) -> Unit
) {
    val context = LocalContext.current
    val email by viewModel.email.collectAsStateWithLifecycle()
    val password by viewModel.password.collectAsStateWithLifecycle()
    val signUpResponse by viewModel.signUpState.collectAsStateWithLifecycle()
    val emailVerificationResponse by viewModel.emailVerificationState.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val invalidEmailMessage = stringResource(R.string.invalid_email_message)
    val invalidPasswordMessage = stringResource(R.string.invalid_password_message)
    val accountCreatedMessage = stringResource(R.string.account_created_message)
    val emailVerificationSentMessage = stringResource(R.string.email_verification_sent_message)

    Scaffold(
        topBar = {
            SignUpTopBar(
                onArrowBackIconClick = navigateBack
            )
        }
    ) { innerPadding ->
        SignUpContent(
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
            onSignUp = viewModel::signUpWithEmailAndPassword,
            isLoading = isLoading,
            onSignInTextClick = navigateBack
        )
    }

    when(val signUpResponse = signUpResponse) {
        is Response.Idle -> {}
        is Response.Loading -> LoadingIndicator()
        is Response.Success -> LaunchedEffect(Unit) {
            showToastMessage(context, accountCreatedMessage)
            viewModel.sendEmailVerification()
        }
        is Response.Failure -> signUpResponse.e?.message?.let { errorMessage ->
            LaunchedEffect(errorMessage) {
                logMessage(errorMessage)
                showToastMessage(context, errorMessage)
            }
        }
    }

    when(val emailVerificationResponse = emailVerificationResponse) {
        is Response.Idle -> {}
        is Response.Loading -> LoadingIndicator()
        is Response.Success -> LaunchedEffect(Unit) {
            showToastMessage(context, emailVerificationSentMessage)
            navigateAndClear(Route.VerifyEmail)
        }
        is Response.Failure -> emailVerificationResponse.e?.message?.let { errorMessage ->
            LaunchedEffect(errorMessage) {
                logMessage(errorMessage)
                showToastMessage(context, errorMessage)
            }
        }
    }
}