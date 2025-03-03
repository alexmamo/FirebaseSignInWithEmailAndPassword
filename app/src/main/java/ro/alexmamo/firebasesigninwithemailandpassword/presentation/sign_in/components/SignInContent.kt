package ro.alexmamo.firebasesigninwithemailandpassword.presentation.sign_in.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ro.alexmamo.firebasesigninwithemailandpassword.R
import ro.alexmamo.firebasesigninwithemailandpassword.components.ActionButton
import ro.alexmamo.firebasesigninwithemailandpassword.components.ActionText
import ro.alexmamo.firebasesigninwithemailandpassword.components.EmailField
import ro.alexmamo.firebasesigninwithemailandpassword.components.PasswordField

const val VERTICAL_DIVIDER = "|"

@Composable
fun SignInContent(
    innerPadding: PaddingValues,
    email: TextFieldValue,
    onEmailChange: (TextFieldValue) -> Unit,
    onEmptyEmail: () -> Unit,
    password: TextFieldValue,
    onPasswordChange: (TextFieldValue) -> Unit,
    onEmptyPassword: () -> Unit,
    onSigningIn: (String, String) -> Unit,
    isLoading: Boolean,
    onForgotPasswordTextClick: () -> Unit,
    onSignUpTextClick: () -> Unit
) {
    val keyboard = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier.fillMaxSize().padding(innerPadding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EmailField(
            email = email,
            onEmailChange = onEmailChange
        )
        Spacer(
            modifier = Modifier.height(8.dp)
        )
        PasswordField(
            password = password,
            onPasswordChange = onPasswordChange
        )
        Spacer(
            modifier = Modifier.height(8.dp)
        )
        ActionButton(
            onActionButtonClick = {
                if (email.text.isEmpty()) {
                    onEmptyEmail()
                } else if (password.text.isEmpty()) {
                    onEmptyPassword()
                } else {
                    onSigningIn(email.text, password.text)
                    keyboard?.hide()
                }
            },
            enabled = !isLoading,
            resourceId = R.string.sign_in_button
        )
        Row {
            ActionText(
                onActionTextClick = onForgotPasswordTextClick,
                resourceId = R.string.forgot_password
            )
            Text(
                modifier = Modifier.padding(
                    start = 4.dp,
                    end = 4.dp
                ),
                text = VERTICAL_DIVIDER,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
            ActionText(
                onActionTextClick = onSignUpTextClick,
                resourceId = R.string.sign_up
            )
        }
    }
}