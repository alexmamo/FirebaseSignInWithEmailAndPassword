package ro.alexmamo.firebasesigninwithemailandpassword.presentation.forgot_password

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
    val sendPasswordResetEmailResponse by viewModel.sendPasswordResetEmailState.collectAsStateWithLifecycle()
    //Really needed?!?!?!
    var sendingPasswordResetEmail by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            ForgotPasswordTopBar(
                onArrowBackIconClick = navigateBack
            )
        }
    ) { innerPadding ->
        ForgotPasswordContent(
            innerPadding = innerPadding,
            onSendingPasswordResetEmail = { email ->
                viewModel.sendPasswordResetEmail(email)
                sendingPasswordResetEmail = true
            },
            sendingPasswordResetEmail = sendingPasswordResetEmail
        )
    }

    when(val sendPasswordResetEmailResponse = sendPasswordResetEmailResponse) {
        is Response.Idle -> {}
        is Response.Loading -> LoadingIndicator()
        is Response.Success -> LaunchedEffect(Unit) {
            showToastMessage(context, resources.getString(R.string.reset_password_message))
            navigateBack()
            sendingPasswordResetEmail = false
        }
        is Response.Failure -> sendPasswordResetEmailResponse.e?.message?.let { errorMessage ->
            logMessage(errorMessage)
            showToastMessage(context, errorMessage)
            sendingPasswordResetEmail = false
        }
    }
}