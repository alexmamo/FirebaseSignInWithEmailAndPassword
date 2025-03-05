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
    val resources = context.resources
    val snackbarHostState = remember { SnackbarHostState() }
    val isUserSignedOut by viewModel.authState.collectAsStateWithLifecycle()
    val deleteUserResponse by viewModel.deleteUserState.collectAsStateWithLifecycle()

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
            showToastMessage(context, resources.getString(R.string.user_deleted_message))
        }
        is Response.Failure -> deleteUserResponse.e?.message?.let { errorMessage ->
            LaunchedEffect(errorMessage) {
                logMessage(errorMessage)
                if (errorMessage.contains(resources.getString(R.string.sensitive_keyword))) {
                    val result =  snackbarHostState.showSnackbar(
                        message = resources.getString(R.string.reauthentication_required_message),
                        actionLabel = resources.getString(R.string.sign_out_action_label)
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