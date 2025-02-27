package ro.alexmamo.firebasesigninwithemailandpassword.presentation.verify_email.components

import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import ro.alexmamo.firebasesigninwithemailandpassword.R

@Composable
fun VerifyEmailTopBar() {
    TopAppBar (
        title = {
            Text(
                text = stringResource(
                    id = R.string.verify_email_screen_title
                )
            )
        }
    )
}