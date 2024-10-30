package ro.alexmamo.firebasesigninwithemailandpassword.presentation.forgot_password

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import ro.alexmamo.firebasesigninwithemailandpassword.components.ProgressBar
import ro.alexmamo.firebasesigninwithemailandpassword.core.Constants.RESET_PASSWORD_MESSAGE
import ro.alexmamo.firebasesigninwithemailandpassword.core.printError
import ro.alexmamo.firebasesigninwithemailandpassword.core.toastError
import ro.alexmamo.firebasesigninwithemailandpassword.core.toastMessage
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
                navigateBack = navigateBack
            )
        },
        content = { padding ->
            ForgotPasswordContent(
                padding = padding,
                sendPasswordResetEmail = { email ->
                    viewModel.sendPasswordResetEmail(email)
                    sendingPasswordResetEmail = true
                },
                sendingPasswordResetEmail = sendingPasswordResetEmail
            )
        }
    )

    if (sendingPasswordResetEmail) {
        when(val sendPasswordResetEmailResponse = viewModel.sendPasswordResetEmailResponse) {
            is Loading -> ProgressBar()
            is Success -> sendPasswordResetEmailResponse.data.let { isPasswordResetEmailSent ->
                if (isPasswordResetEmailSent) {
                    toastMessage(context, RESET_PASSWORD_MESSAGE)
                    navigateBack()
                }
                sendingPasswordResetEmail = false
            }
            is Failure -> sendPasswordResetEmailResponse.e.let { e ->
                printError(e)
                toastError(context, e)
                sendingPasswordResetEmail = false
            }
        }
    }
}