package ro.alexmamo.firebasesigninwithemailandpassword.presentation.sign_up

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
import ro.alexmamo.firebasesigninwithemailandpassword.presentation.sign_up.components.SignUpContent
import ro.alexmamo.firebasesigninwithemailandpassword.presentation.sign_up.components.SignUpTopBar

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
    navigateAndClear: (Route) -> Unit
) {
    val context = LocalContext.current
    val resources = context.resources
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val signUpResponse by viewModel.signUpState.collectAsStateWithLifecycle()
    val sendEmailVerificationResponse by viewModel.sendEmailVerificationState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            SignUpTopBar(
                onArrowBackIconClick = navigateBack
            )
        }
    ) { innerPadding ->
        SignUpContent(
            innerPadding = innerPadding,
            onEmptyEmail = {
                showToastMessage(context, resources.getString(R.string.empty_email_message))
            },
            onEmptyPassword = {
                showToastMessage(context, resources.getString(R.string.empty_password_message))
            },
            onSigningUp = { email, password ->
                viewModel.signUpWithEmailAndPassword(email, password)
            },
            isLoading = isLoading,
            onSignInTextClick = navigateBack
        )
    }

    when(val signUpResponse = signUpResponse) {
        is Response.Idle -> {}
        is Response.Loading -> LoadingIndicator()
        is Response.Success -> LaunchedEffect(Unit) {
            showToastMessage(context, resources.getString(R.string.account_created_message))
            viewModel.sendEmailVerification()
        }
        is Response.Failure -> signUpResponse.e?.message?.let { errorMessage ->
            LaunchedEffect(errorMessage) {
                logMessage(errorMessage)
                showToastMessage(context, errorMessage)
            }
        }
    }

    when(val sendEmailVerificationResponse = sendEmailVerificationResponse) {
        is Response.Idle -> {}
        is Response.Loading -> LoadingIndicator()
        is Response.Success -> LaunchedEffect(Unit) {
            showToastMessage(context, resources.getString(R.string.email_verification_sent_message))
            navigateAndClear(Route.VerifyEmail)
        }
        is Response.Failure -> sendEmailVerificationResponse.e?.message?.let { errorMessage ->
            LaunchedEffect(errorMessage) {
                logMessage(errorMessage)
                showToastMessage(context, errorMessage)
            }
        }
    }
}