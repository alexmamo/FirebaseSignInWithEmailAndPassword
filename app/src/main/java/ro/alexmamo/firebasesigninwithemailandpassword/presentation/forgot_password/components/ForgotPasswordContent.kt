package ro.alexmamo.firebasesigninwithemailandpassword.presentation.forgot_password.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import ro.alexmamo.firebasesigninwithemailandpassword.R
import ro.alexmamo.firebasesigninwithemailandpassword.components.ActionButton
import ro.alexmamo.firebasesigninwithemailandpassword.components.EmailField

@Composable
fun ForgotPasswordContent(
    innerPadding: PaddingValues,
    email: TextFieldValue,
    onEmailChange: (TextFieldValue) -> Unit,
    onEmailInvalid: () -> Unit,
    onSendPasswordResetEmail: (String) -> Unit,
    isLoading: Boolean,
) {
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
        ActionButton(
            onActionButtonClick = {
                val isEmailValid = email.text.isNotBlank()
                if (!isEmailValid) {
                    onEmailInvalid()
                } else {
                    onSendPasswordResetEmail(email.text)
                }
            },
            enabled = !isLoading,
            resourceId = R.string.reset_password_button
        )
    }
}