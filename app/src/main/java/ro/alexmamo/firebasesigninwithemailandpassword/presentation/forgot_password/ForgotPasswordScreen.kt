package ro.alexmamo.firebasesigninwithemailandpassword.presentation.forgot_password

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
import ro.alexmamo.firebasesigninwithemailandpassword.presentation.forgot_password.components.ForgotPasswordContent
import ro.alexmamo.firebasesigninwithemailandpassword.presentation.forgot_password.components.ForgotPasswordTopBar

@Composable
fun ForgotPasswordScreen(
    viewModel: ForgotPasswordViewModel = hiltViewModel(),
    navigateBack: () -> Unit
) {
    val context = LocalContext.current
    val resources = context.resources
    val email by viewModel.email.collectAsStateWithLifecycle()
    val sendPasswordResetEmailResponse by viewModel.sendPasswordResetEmailState.collectAsStateWithLifecycle()

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
            onEmptyEmail = {
                showToastMessage(context, resources.getString(R.string.empty_email_message))
            },
            onSendingPasswordResetEmail = viewModel::sendPasswordResetEmail,
            isLoading = sendPasswordResetEmailResponse is Response.Loading
        )
    }

    when(val sendPasswordResetEmailResponse = sendPasswordResetEmailResponse) {
        is Response.Idle -> {}
        is Response.Loading -> LoadingIndicator()
        is Response.Success -> LaunchedEffect(Unit) {
            showToastMessage(context, resources.getString(R.string.reset_password_message))
            navigateBack()
        }
        is Response.Failure -> sendPasswordResetEmailResponse.e?.message?.let { errorMessage ->
            logMessage(errorMessage)
            showToastMessage(context, errorMessage)
        }
    }
}