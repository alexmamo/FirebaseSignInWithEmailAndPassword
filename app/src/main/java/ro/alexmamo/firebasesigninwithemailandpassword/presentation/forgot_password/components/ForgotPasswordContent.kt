package ro.alexmamo.firebasesigninwithemailandpassword.presentation.forgot_password.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import ro.alexmamo.firebasesigninwithemailandpassword.R
import ro.alexmamo.firebasesigninwithemailandpassword.components.ActionButton
import ro.alexmamo.firebasesigninwithemailandpassword.components.EmailField
import ro.alexmamo.firebasesigninwithemailandpassword.core.EMPTY_STRING

@Composable
fun ForgotPasswordContent(
    innerPadding: PaddingValues,
    onSendingPasswordResetEmail: (email: String) -> Unit,
    sendingPasswordResetEmail: Boolean
) {
    var email by rememberSaveable(
        stateSaver = TextFieldValue.Saver
    ) {
        mutableStateOf( TextFieldValue(EMPTY_STRING))
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(innerPadding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EmailField(
            email = email,
            onEmailValueChange = { newValue ->
                email = newValue
            }
        )
        Spacer(
            modifier = Modifier.height(8.dp)
        )
        ActionButton(
            onActionButtonClick = {
                onSendingPasswordResetEmail(email.text)
            },
            enabled = !sendingPasswordResetEmail,
            resourceId = R.string.reset_password_button
        )
    }
}