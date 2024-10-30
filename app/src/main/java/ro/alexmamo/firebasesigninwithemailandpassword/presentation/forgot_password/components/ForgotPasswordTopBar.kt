package ro.alexmamo.firebasesigninwithemailandpassword.presentation.forgot_password.components

import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import ro.alexmamo.firebasesigninwithemailandpassword.components.ArrowBackIcon
import ro.alexmamo.firebasesigninwithemailandpassword.core.Constants.FORGOT_PASSWORD_SCREEN

@Composable
fun ForgotPasswordTopBar(
    navigateBack: () -> Unit
) {
    TopAppBar (
        title = {
            Text(
                text = FORGOT_PASSWORD_SCREEN
            )
        },
        navigationIcon = {
            ArrowBackIcon(
                onArrowBackIconClick = navigateBack
            )
        }
    )
}