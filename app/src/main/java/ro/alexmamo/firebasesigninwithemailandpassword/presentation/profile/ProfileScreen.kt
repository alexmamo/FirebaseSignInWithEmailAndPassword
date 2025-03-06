package ro.alexmamo.firebasesigninwithemailandpassword.presentation.profile

import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ro.alexmamo.firebasesigninwithemailandpassword.R
import ro.alexmamo.firebasesigninwithemailandpassword.components.LoadingIndicator
import ro.alexmamo.firebasesigninwithemailandpassword.core.logMessage
import ro.alexmamo.firebasesigninwithemailandpassword.core.showToastMessage
import ro.alexmamo.firebasesigninwithemailandpassword.domain.model.Response
import ro.alexmamo.firebasesigninwithemailandpassword.navigation.Route
import ro.alexmamo.firebasesigninwithemailandpassword.presentation.profile.components.ProfileContent
import ro.alexmamo.firebasesigninwithemailandpassword.presentation.profile.components.ProfileTopBar

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    navigateAndClear: (Route) -> Unit
) {
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val isUserSignedOut by viewModel.authState.collectAsStateWithLifecycle()
    val deleteUserResponse by viewModel.deleteUserState.collectAsStateWithLifecycle()
    val userDeletedMessage = stringResource(R.string.user_deleted_message)
    val sensitiveKeyword = stringResource(R.string.sensitive_keyword)
    val reauthenticationRequiredMessage = stringResource(R.string.reauthentication_required_message)
    val signOutActionLabel = stringResource(R.string.sign_out_action_label)

    Scaffold(
        topBar = {
            ProfileTopBar(
                signOut = viewModel::signOut,
                deleteUser = viewModel::deleteUser
            )
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState
            )
        }
    ) { innerPadding ->
        ProfileContent(
            innerPadding = innerPadding
        )
    }

    if (isUserSignedOut) {
        navigateAndClear(Route.SignIn)
    }

    when(val deleteUserResponse = deleteUserResponse) {
        is Response.Idle -> {}
        is Response.Loading -> LoadingIndicator()
        is Response.Success -> LaunchedEffect(Unit) {
            showToastMessage(context, userDeletedMessage)
        }
        is Response.Failure -> deleteUserResponse.e?.message?.let { errorMessage ->
            LaunchedEffect(errorMessage) {
                logMessage(errorMessage)
                if (errorMessage.contains(sensitiveKeyword)) {
                    val result =  snackbarHostState.showSnackbar(
                        message = reauthenticationRequiredMessage,
                        actionLabel = signOutActionLabel
                    )
                    if (result == SnackbarResult.ActionPerformed) {
                        viewModel::signOut
                    }
                } else {
                    showToastMessage(context, errorMessage)
                }
            }
        }
    }
}