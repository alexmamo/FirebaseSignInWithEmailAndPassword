package ro.alexmamo.firebasesigninwithemailandpassword.presentation.sign_up

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
                onArrowBackIconClick = navigateBack
            )
        }
    ) { innerPadding ->
        SignUpContent(
            innerPadding = innerPadding,
            onSigningUp = { email, password ->
                viewModel.signUpWithEmailAndPassword(email, password)
                signingUp = true
            },
            signingUp = signingUp,
            sendingEmailVerification = sendingEmailVerification,
            onSignInTextClick = navigateBack
        )
    }

    if (signingUp) {
        when(val signUpResponse = viewModel.signUpResponse) {
            is Loading -> LoadingIndicator()
            is Success -> {
                viewModel.sendEmailVerification()
                signingUp = false
                sendingEmailVerification = true
            }
            is Failure -> signUpResponse.e.let { e ->
                printError(e)
                showToastError(context, e)
                signingUp = false
            }
        }
    }

    if (sendingEmailVerification) {
        when(val sendEmailVerificationResponse = viewModel.sendEmailVerificationResponse) {
            is Loading -> LoadingIndicator()
            is Success -> {
                showToastMessage(context, R.string.email_verification_sent_message)
                navigateAndClear(Profile)
                sendingEmailVerification = false
            }
            is Failure -> sendEmailVerificationResponse.e.let { e ->
                printError(e)
                showToastError(context, e)
                sendingEmailVerification = false
            }
        }
    }
}