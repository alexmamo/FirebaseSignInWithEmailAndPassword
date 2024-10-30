package ro.alexmamo.firebasesigninwithemailandpassword.presentation.sign_up

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import ro.alexmamo.firebasesigninwithemailandpassword.components.ProgressBar
import ro.alexmamo.firebasesigninwithemailandpassword.core.Constants.EMAIL_VERIFICATION_SENT_MESSAGE
import ro.alexmamo.firebasesigninwithemailandpassword.core.printError
import ro.alexmamo.firebasesigninwithemailandpassword.core.toastError
import ro.alexmamo.firebasesigninwithemailandpassword.core.toastMessage
import ro.alexmamo.firebasesigninwithemailandpassword.domain.model.Response.Failure
import ro.alexmamo.firebasesigninwithemailandpassword.domain.model.Response.Loading
import ro.alexmamo.firebasesigninwithemailandpassword.domain.model.Response.Success
import ro.alexmamo.firebasesigninwithemailandpassword.navigation.Route
import ro.alexmamo.firebasesigninwithemailandpassword.navigation.Route.Profile
import ro.alexmamo.firebasesigninwithemailandpassword.presentation.sign_up.components.SignUpContent
import ro.alexmamo.firebasesigninwithemailandpassword.presentation.sign_up.components.SignUpTopBar

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
    navigateAndClear: (Route) -> Unit
) {
    val context = LocalContext.current
    var signingUp by remember { mutableStateOf(false) }
    var sendingEmailVerification by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            SignUpTopBar(
                navigateBack = navigateBack
            )
        },
        content = { padding ->
            SignUpContent(
                padding = padding,
                signUp = { email, password ->
                    viewModel.signUpWithEmailAndPassword(email, password)
                    signingUp = true
                },
                signingUp = signingUp,
                navigateBack = navigateBack,
                sendingEmailVerification = sendingEmailVerification
            )
        }
    )

    if (signingUp) {
        when(val signUpResponse = viewModel.signUpResponse) {
            is Loading -> ProgressBar()
            is Success -> signUpResponse.data.let { isSignedUp ->
                if (isSignedUp) {
                    viewModel.sendEmailVerification()
                    signingUp = false
                    sendingEmailVerification = true
                }
            }
            is Failure -> signUpResponse.e.let { e ->
                printError(e)
                toastError(context, e)
                signingUp = false
            }
        }
    }

    if (sendingEmailVerification) {
        when(val sendEmailVerificationResponse = viewModel.sendEmailVerificationResponse) {
            is Loading -> ProgressBar()
            is Success -> sendEmailVerificationResponse.data.let { isVerificationEmailSent ->
                if (isVerificationEmailSent) {
                    toastMessage(context, EMAIL_VERIFICATION_SENT_MESSAGE)
                    navigateAndClear(Profile)
                    sendingEmailVerification = false
                }
            }
            is Failure -> sendEmailVerificationResponse.e.let { e ->
                printError(e)
                toastError(context, e)
                sendingEmailVerification = false
            }
        }
    }
}