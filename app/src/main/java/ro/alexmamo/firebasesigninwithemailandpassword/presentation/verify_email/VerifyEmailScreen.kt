package ro.alexmamo.firebasesigninwithemailandpassword.presentation.verify_email

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
import ro.alexmamo.firebasesigninwithemailandpassword.core.logErrorMessage
import ro.alexmamo.firebasesigninwithemailandpassword.core.showToastMessage
import ro.alexmamo.firebasesigninwithemailandpassword.domain.model.Response
import ro.alexmamo.firebasesigninwithemailandpassword.navigation.Route
import ro.alexmamo.firebasesigninwithemailandpassword.presentation.verify_email.components.VerifyEmailContent
import ro.alexmamo.firebasesigninwithemailandpassword.presentation.verify_email.components.VerifyEmailTopBar

@Composable
fun VerifyEmailScreen(
    viewModel: VerifyEmailViewModel = hiltViewModel(),
    navigateAndClear: (Route) -> Unit,
) {
    val context = LocalContext.current
    val reloadUserResponse by viewModel.reloadUserState.collectAsStateWithLifecycle()
    val isEmailVerified by viewModel.isEmailVerifiedState.collectAsStateWithLifecycle()
    val emailNotVerifiedMessage = stringResource(R.string.email_not_verified_message)

    Scaffold(
        topBar = {
            VerifyEmailTopBar()
        }
    ) { innerPadding ->
        VerifyEmailContent(
            innerPadding = innerPadding,
            onAlreadyVerifiedTextClick = viewModel::reloadUser
        )
    }

    when(val reloadUserResponse = reloadUserResponse) {
        is Response.Idle -> {}
        is Response.Loading -> LoadingIndicator()
        is Response.Success -> LaunchedEffect(Unit) {
            if (isEmailVerified) {
                navigateAndClear(Route.Profile)
            } else {
                showToastMessage(context, emailNotVerifiedMessage)
            }
        }
        is Response.Failure -> reloadUserResponse.e?.message?.let { errorMessage ->
            LaunchedEffect(errorMessage) {
                logErrorMessage(errorMessage)
                showToastMessage(context, errorMessage)
            }
        }
    }
}