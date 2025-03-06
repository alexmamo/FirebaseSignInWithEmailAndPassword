package ro.alexmamo.firebasesigninwithemailandpassword.presentation.forgot_password

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
import ro.alexmamo.firebasesigninwithemailandpassword.presentation.forgot_password.components.ForgotPasswordContent
import ro.alexmamo.firebasesigninwithemailandpassword.presentation.forgot_password.components.ForgotPasswordTopBar

@Composable
fun ForgotPasswordScreen(
    viewModel: ForgotPasswordViewModel = hiltViewModel(),
    navigateBack: () -> Unit
) {
    val context = LocalContext.current
    val email by viewModel.email.collectAsStateWithLifecycle()
    val passwordResetEmailResponse by viewModel.passwordResetEmailState.collectAsStateWithLifecycle()
    val invalidEmailMessage = stringResource(R.string.invalid_email_message)
    val resetPasswordMessage = stringResource(R.string.reset_password_message)

    Scaffold(
        topBar = {
            ForgotPasswordTopBar(
                onArrowBackIconClick = navigateBack
            )
        }
    ) { innerPadding ->
        ForgotPasswordContent(
            innerPadding = innerPadding,
            email = email,
            onEmailChange = viewModel::onEmailChange,
            onEmailInvalid = {
                showToastMessage(context, invalidEmailMessage)
            },
            onSendPasswordResetEmail = viewModel::sendPasswordResetEmail,
            isLoading = passwordResetEmailResponse is Response.Loading
        )
    }

    when(val sendPasswordResetEmailResponse = passwordResetEmailResponse) {
        is Response.Idle -> {}
        is Response.Loading -> LoadingIndicator()
        is Response.Success -> LaunchedEffect(Unit) {
            showToastMessage(context, resetPasswordMessage)
            navigateBack()
        }
        is Response.Failure -> sendPasswordResetEmailResponse.e?.message?.let { errorMessage ->
            logMessage(errorMessage)
            showToastMessage(context, errorMessage)
        }
    }
}