package ro.alexmamo.firebasesigninwithemailandpassword.presentation.verify_email

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
import ro.alexmamo.firebasesigninwithemailandpassword.presentation.verify_email.components.VerifyEmailContent
import ro.alexmamo.firebasesigninwithemailandpassword.presentation.verify_email.components.VerifyEmailTopBar

@Composable
fun VerifyEmailScreen(
    viewModel: VerifyEmailViewModel = hiltViewModel(),
    navigateAndClear: (Route) -> Unit,
) {
    val context = LocalContext.current
    val resources = context.resources
    val reloadUserResponse by viewModel.reloadUserState.collectAsStateWithLifecycle()
    val isEmailVerified by viewModel.isEmailVerifiedState.collectAsStateWithLifecycle()

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
                showToastMessage(context, resources.getString(R.string.email_not_verified_message))
            }
        }
        is Response.Failure -> reloadUserResponse.e?.message?.let { errorMessage ->
            LaunchedEffect(errorMessage) {
                logMessage(errorMessage)
                showToastMessage(context, errorMessage)
            }
        }
    }
}