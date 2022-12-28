package ro.alexmamo.firebasesigninwithemailandpassword.presentation.forgot_password

import android.widget.Toast.LENGTH_LONG
import android.widget.Toast.makeText
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import ro.alexmamo.firebasesigninwithemailandpassword.core.Constants.RESET_PASSWORD_MESSAGE
import ro.alexmamo.firebasesigninwithemailandpassword.presentation.forgot_password.components.ForgotPassword
import ro.alexmamo.firebasesigninwithemailandpassword.presentation.forgot_password.components.ForgotPasswordContent
import ro.alexmamo.firebasesigninwithemailandpassword.presentation.forgot_password.components.ForgotPasswordTopBar

@Composable
fun ForgotPasswordScreen(
    viewModel: ForgotPasswordViewModel = hiltViewModel(),
    navigateBack: () -> Unit
) {
    val context = LocalContext.current

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
                }
            )
        }
    )

    fun showResetPasswordMessage() = makeText(context, RESET_PASSWORD_MESSAGE, LENGTH_LONG).show()

    fun showErrorMessage(message: String?) = makeText(context, message, LENGTH_LONG).show()

    ForgotPassword(
        navigateBack = navigateBack,
        showResetPasswordMessage = {
            showResetPasswordMessage()
        },
        showErrorMessage = { e ->
            showErrorMessage(e.message)
        }
    )
}