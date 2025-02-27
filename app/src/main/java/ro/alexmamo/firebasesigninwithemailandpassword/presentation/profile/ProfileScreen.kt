package ro.alexmamo.firebasesigninwithemailandpassword.presentation.profile

import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
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
    navigateAndClear: (Route) -> Unit,
) {
    val context = LocalContext.current
    val resources = context.resources
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val deleteUserResponse by viewModel.deleteUserState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getAuthState(
            navigateToSignInScreen = {
                navigateAndClear(Route.SignIn)
            }
        )
    }

    Scaffold(
        topBar = {
            ProfileTopBar(
                signOut = {
                    viewModel.signOut()
                },
                deleteUser = {
                    viewModel.deleteUser()
                }
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

    fun showSnackbarMessage(
        message: String,
        actionLabel: String,
        onActionClick: () -> Unit,
    ) = coroutineScope.launch {
        val result =  snackbarHostState.showSnackbar(
            message = message,
            actionLabel = actionLabel
        )
        if (result == SnackbarResult.ActionPerformed) {
            onActionClick()
        }
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
                    showSnackbarMessage(
                        message = resources.getString(R.string.reauthentication_required_message),
                        actionLabel = resources.getString(R.string.sign_out_action_label),
                        onActionClick = {
                            viewModel.signOut()
                        }
                    )
                } else {
                    showToastMessage(context, errorMessage)
                }
            }
        }
    }
}