package ro.alexmamo.firebasesigninwithemailandpassword.presentation.profile

import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarResult
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import ro.alexmamo.firebasesigninwithemailandpassword.R
import ro.alexmamo.firebasesigninwithemailandpassword.components.LoadingIndicator
import ro.alexmamo.firebasesigninwithemailandpassword.presentation.profile.components.ProfileTopBar
import ro.alexmamo.firebasesigninwithemailandpassword.core.printError
import ro.alexmamo.firebasesigninwithemailandpassword.core.showToastError
import ro.alexmamo.firebasesigninwithemailandpassword.core.showToastMessage
import ro.alexmamo.firebasesigninwithemailandpassword.domain.model.Response.Failure
import ro.alexmamo.firebasesigninwithemailandpassword.domain.model.Response.Loading
import ro.alexmamo.firebasesigninwithemailandpassword.domain.model.Response.Success
import ro.alexmamo.firebasesigninwithemailandpassword.navigation.Route
import ro.alexmamo.firebasesigninwithemailandpassword.navigation.Route.SignIn
import ro.alexmamo.firebasesigninwithemailandpassword.presentation.profile.components.ProfileContent
import ro.alexmamo.firebasesigninwithemailandpassword.presentation.profile.components.VerifyEmailContent

const val SIGN_OUT_ACTION_LABEL = "Sign out?"
const val DELETE_USER_MESSAGE = "You need to re-authenticate before deleting the user."
const val SENSITIVE_OPERATION_MESSAGE = "This operation is sensitive and requires recent authentication. Log in again before retrying this request."

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    navigateAndClear: (Route) -> Unit,
) {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    var isEmailVerified by remember { mutableStateOf(viewModel.isEmailVerified) }
    var reloadingUser by remember { mutableStateOf(false) }
    var deletingUser by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.getAuthState(
            navigateToSignInScreen = {
                navigateAndClear(SignIn)
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
                    deletingUser = true
                }
            )
        },
        scaffoldState = scaffoldState
    ) { innerPadding ->
        if (isEmailVerified) {
            ProfileContent(
                innerPadding = innerPadding
            )
        } else {
            VerifyEmailContent(
                innerPadding = innerPadding,
                reloadUser = {
                    viewModel.reloadUser()
                    reloadingUser = true
                }
            )
        }
    }

    if (reloadingUser) {
        when(val reloadUserResponse = viewModel.reloadUserResponse) {
            is Loading -> LoadingIndicator()
            is Success -> {
                if (viewModel.isEmailVerified) {
                    isEmailVerified = true
                } else {
                    showToastMessage(context, R.string.email_not_verified_message)
                }
                reloadingUser = false
            }
            is Failure -> reloadUserResponse.e.let { e ->
                printError(e)
                showToastError(context, e)
                reloadingUser = false
            }
        }
    }

    fun showDeleteUserMessage() = coroutineScope.launch {
        val result = scaffoldState.snackbarHostState.showSnackbar(
            message = DELETE_USER_MESSAGE,
            actionLabel = SIGN_OUT_ACTION_LABEL
        )
        if (result == SnackbarResult.ActionPerformed) {
            viewModel.signOut()
        }
    }

    if (deletingUser) {
        when(val deleteUserResponse = viewModel.deleteUserResponse) {
            is Loading -> LoadingIndicator()
            is Success -> {
                showToastMessage(context, R.string.user_deleted_message)
                deletingUser = false
            }
            is Failure -> deleteUserResponse.e.let { e ->
                printError(e)
                if (e.message == SENSITIVE_OPERATION_MESSAGE) {
                    showDeleteUserMessage()
                }
                deletingUser = false
            }
        }
    }
}