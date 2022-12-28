package ro.alexmamo.firebasesigninwithemailandpassword.presentation.verify_email

import android.widget.Toast.LENGTH_LONG
import android.widget.Toast.makeText
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import ro.alexmamo.firebasesigninwithemailandpassword.components.TopBar
import ro.alexmamo.firebasesigninwithemailandpassword.core.Constants.EMAIL_NOT_VERIFIED_MESSAGE
import ro.alexmamo.firebasesigninwithemailandpassword.core.Constants.VERIFY_EMAIL_SCREEN
import ro.alexmamo.firebasesigninwithemailandpassword.presentation.profile.ProfileViewModel
import ro.alexmamo.firebasesigninwithemailandpassword.presentation.profile.components.RevokeAccess
import ro.alexmamo.firebasesigninwithemailandpassword.presentation.verify_email.components.ReloadUser
import ro.alexmamo.firebasesigninwithemailandpassword.presentation.verify_email.components.VerifyEmailContent

@Composable
fun VerifyEmailScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    navigateToProfileScreen: () -> Unit
) {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopBar(
                title = VERIFY_EMAIL_SCREEN,
                signOut = {
                    viewModel.signOut()
                },
                revokeAccess = {
                    viewModel.revokeAccess()
                }
            )
        },
        content = { padding ->
            VerifyEmailContent(
                padding = padding,
                reloadUser = {
                    viewModel.reloadUser()
                }
            )
        },
        scaffoldState = scaffoldState
    )

    fun showEmailNotVerifiedMessage() = makeText(context, EMAIL_NOT_VERIFIED_MESSAGE, LENGTH_LONG).show()

    ReloadUser(
        navigateToProfileScreen = {
            if (viewModel.isEmailVerified) {
                navigateToProfileScreen()
            } else {
                showEmailNotVerifiedMessage()
            }
        }
    )

    RevokeAccess(
        scaffoldState = scaffoldState,
        coroutineScope = coroutineScope,
        signOut = {
            viewModel.signOut()
        }
    )
}