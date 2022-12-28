package ro.alexmamo.firebasesigninwithemailandpassword.presentation.profile.components

import android.widget.Toast.LENGTH_LONG
import android.widget.Toast.makeText
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ro.alexmamo.firebasesigninwithemailandpassword.components.ProgressBar
import ro.alexmamo.firebasesigninwithemailandpassword.core.Constants.ACCESS_REVOKED_MESSAGE
import ro.alexmamo.firebasesigninwithemailandpassword.core.Constants.REVOKE_ACCESS_MESSAGE
import ro.alexmamo.firebasesigninwithemailandpassword.core.Constants.SENSITIVE_OPERATION_MESSAGE
import ro.alexmamo.firebasesigninwithemailandpassword.core.Constants.SIGN_OUT
import ro.alexmamo.firebasesigninwithemailandpassword.core.Utils.Companion.print
import ro.alexmamo.firebasesigninwithemailandpassword.domain.model.Response.*
import ro.alexmamo.firebasesigninwithemailandpassword.presentation.profile.ProfileViewModel

@Composable
fun RevokeAccess(
    viewModel: ProfileViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState,
    coroutineScope: CoroutineScope,
    signOut: () -> Unit,
) {
    val context = LocalContext.current

    fun showAccessRevokedMessage() = makeText(context, ACCESS_REVOKED_MESSAGE, LENGTH_LONG).show()

    fun showRevokeAccessMessage() = coroutineScope.launch {
        val result = scaffoldState.snackbarHostState.showSnackbar(
            message = REVOKE_ACCESS_MESSAGE,
            actionLabel = SIGN_OUT
        )
        if (result == SnackbarResult.ActionPerformed) {
            signOut()
        }
    }

    when(val revokeAccessResponse = viewModel.revokeAccessResponse) {
        is Loading -> ProgressBar()
        is Success -> {
            val isAccessRevoked = revokeAccessResponse.data
            LaunchedEffect(isAccessRevoked) {
                if (isAccessRevoked) {
                    showAccessRevokedMessage()
                }
            }
        }
        is Failure -> revokeAccessResponse.apply {
            LaunchedEffect(e) {
                print(e)
                if (e.message == SENSITIVE_OPERATION_MESSAGE) {
                    showRevokeAccessMessage()
                }
            }
        }
    }
}