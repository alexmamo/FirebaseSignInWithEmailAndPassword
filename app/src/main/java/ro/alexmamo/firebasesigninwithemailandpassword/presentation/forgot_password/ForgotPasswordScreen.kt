package ro.alexmamo.firebasesigninwithemailandpassword.presentation.forgot_password

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import ro.alexmamo.firebasesigninwithemailandpassword.R
import ro.alexmamo.firebasesigninwithemailandpassword.components.LoadingIndicator
import ro.alexmamo.firebasesigninwithemailandpassword.core.printError
import ro.alexmamo.firebasesigninwithemailandpassword.core.showToastError
import ro.alexmamo.firebasesigninwithemailandpassword.core.showToastMessage
import ro.alexmamo.firebasesigninwithemailandpassword.domain.model.Response.Failure
import ro.alexmamo.firebasesigninwithemailandpassword.domain.model.Response.Loading
import ro.alexmamo.firebasesigninwithemailandpassword.domain.model.Response.Success
import ro.alexmamo.firebasesigninwithemailandpassword.presentation.forgot_password.components.ForgotPasswordContent
import ro.alexmamo.firebasesigninwithemailandpassword.presentation.forgot_password.components.ForgotPasswordTopBar

@Composable
fun ForgotPasswordScreen(
    viewModel: ForgotPasswordViewModel = hiltViewModel(),
    navigateBack: () -> Unit
) {
    val context = LocalContext.current
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

    if (sendingPasswordResetEmail) {
        when(val sendPasswordResetEmailResponse = viewModel.sendPasswordResetEmailResponse) {
            is Loading -> LoadingIndicator()
            is Success -> {
                showToastMessage(context, R.string.reset_password_message)
                navigateBack()
                sendingPasswordResetEmail = false
            }
            is Failure -> sendPasswordResetEmailResponse.e.let { e ->
                printError(e)
                showToastError(context, e)
                sendingPasswordResetEmail = false
            }
        }
    }
}