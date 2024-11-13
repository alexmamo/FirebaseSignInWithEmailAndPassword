package ro.alexmamo.firebasesigninwithemailandpassword.presentation.sign_in.components

import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import ro.alexmamo.firebasesigninwithemailandpassword.R

@Composable
fun SignInTopBar() {
    TopAppBar (
        title = {
            Text(
                text = stringResource(
                    id = R.string.sign_in_screen_title
                )
            )
        }
    )
}