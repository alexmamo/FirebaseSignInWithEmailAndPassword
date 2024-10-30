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
import ro.alexmamo.firebasesigninwithemailandpassword.components.ProgressBar
import ro.alexmamo.firebasesigninwithemailandpassword.components.TopBar
import ro.alexmamo.firebasesigninwithemailandpassword.core.Constants.ACCESS_REVOKED_MESSAGE
import ro.alexmamo.firebasesigninwithemailandpassword.core.Constants.EMAIL_NOT_VERIFIED_MESSAGE
import ro.alexmamo.firebasesigninwithemailandpassword.core.Constants.PROFILE_SCREEN
import ro.alexmamo.firebasesigninwithemailandpassword.core.Constants.REVOKE_ACCESS_MESSAGE
import ro.alexmamo.firebasesigninwithemailandpassword.core.Constants.SENSITIVE_OPERATION_MESSAGE
import ro.alexmamo.firebasesigninwithemailandpassword.core.Constants.SIGN_OUT_ACTION_LABEL
import ro.alexmamo.firebasesigninwithemailandpassword.core.printError
import ro.alexmamo.firebasesigninwithemailandpassword.core.toastError
import ro.alexmamo.firebasesigninwithemailandpassword.core.toastMessage
import ro.alexmamo.firebasesigninwithemailandpassword.domain.model.Response.Failure
import ro.alexmamo.firebasesigninwithemailandpassword.domain.model.Response.Loading
import ro.alexmamo.firebasesigninwithemailandpassword.domain.model.Response.Success
import ro.alexmamo.firebasesigninwithemailandpassword.navigation.Screen
import ro.alexmamo.firebasesigninwithemailandpassword.navigation.Screen.SignInScreen
import ro.alexmamo.firebasesigninwithemailandpassword.presentation.profile.components.ProfileContent
import ro.alexmamo.firebasesigninwithemailandpassword.presentation.profile.components.VerifyEmailContent

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    navigateToAndClear: (screen: Screen) -> Unit,
) {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    var isEmailVerified by remember { mutableStateOf(viewModel.isEmailVerified) }
    var reloadingUser by remember { mutableStateOf(false) }
    var revokingAccess by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.getAuthState(
            navigateToSignInScreen = {
                navigateToAndClear(SignInScreen)
            }
        )
    }

    Scaffold(
        topBar = {
            TopBar(
                title = PROFILE_SCREEN,
                signOut = {
                    viewModel.signOut()
                },
                revokeAccess = {
                    viewModel.revokeAccess()
                    revokingAccess = true
                }
            )
        },
        content = { padding ->
            if (isEmailVerified) {
                ProfileContent(
                    padding = padding
                )
            } else {
                VerifyEmailContent(
                    padding = padding,
                    reloadUser = {
                        viewModel.reloadUser()
                        reloadingUser = true
                    }
                )
            }
        },
        scaffoldState = scaffoldState
    )

    if (reloadingUser) {
        when(val reloadUserResponse = viewModel.reloadUserResponse) {
            is Loading -> ProgressBar()
            is Success -> reloadUserResponse.data.let { isUserReloaded ->
                if (isUserReloaded) {
                    if (viewModel.isEmailVerified) {
                        isEmailVerified = true
                    } else {
                        toastMessage(context, EMAIL_NOT_VERIFIED_MESSAGE)
                    }
                    reloadingUser = false
                }
            }
            is Failure -> reloadUserResponse.e.let { e ->
                printError(e)
                toastError(context, e)
                reloadingUser = false
            }
        }
    }

    fun showRevokeAccessMessage() = coroutineScope.launch {
        val result = scaffoldState.snackbarHostState.showSnackbar(
            message = REVOKE_ACCESS_MESSAGE,
            actionLabel = SIGN_OUT_ACTION_LABEL
        )
        if (result == SnackbarResult.ActionPerformed) {
            viewModel.signOut()
        }
    }

    if (revokingAccess) {
        when(val revokeAccessResponse = viewModel.revokeAccessResponse) {
            is Loading -> ProgressBar()
            is Success -> revokeAccessResponse.data.let { isAccessRevoked ->
                if (isAccessRevoked) {
                    toastMessage(context, ACCESS_REVOKED_MESSAGE)
                }
                revokingAccess = false
            }
            is Failure -> revokeAccessResponse.e.let { e ->
                printError(e)
                if (e.message == SENSITIVE_OPERATION_MESSAGE) {
                    showRevokeAccessMessage()
                }
                revokingAccess = false
            }
        }
    }
}