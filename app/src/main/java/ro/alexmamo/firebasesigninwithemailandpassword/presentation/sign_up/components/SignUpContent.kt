package ro.alexmamo.firebasesigninwithemailandpassword.presentation.sign_up.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import ro.alexmamo.firebasesigninwithemailandpassword.R
import ro.alexmamo.firebasesigninwithemailandpassword.components.ActionButton
import ro.alexmamo.firebasesigninwithemailandpassword.components.ActionText
import ro.alexmamo.firebasesigninwithemailandpassword.components.EmailField
import ro.alexmamo.firebasesigninwithemailandpassword.components.PasswordField
import ro.alexmamo.firebasesigninwithemailandpassword.core.EMPTY_STRING

@Composable
fun SignUpContent(
    innerPadding: PaddingValues,
    onEmptyEmail: () -> Unit,
    onEmptyPassword: () -> Unit,
    onSigningUp: (String, String) -> Unit,
    isLoading: Boolean,
    onSignInTextClick: () -> Unit
) {
    var email by rememberSaveable(
        stateSaver = TextFieldValue.Saver
    ) { mutableStateOf(TextFieldValue(EMPTY_STRING)) }
    var password by rememberSaveable(
        stateSaver = TextFieldValue.Saver
    ) { mutableStateOf(TextFieldValue(EMPTY_STRING)) }
    val keyboard = LocalSoftwareKeyboardController.current

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
        PasswordField(
            password = password,
            onPasswordValueChange = { newValue ->
                password = newValue
            }
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
                    onSigningUp(email.text, password.text)
                    keyboard?.hide()
                }
            },
            enabled = !isLoading,
            resourceId = R.string.sign_up_button
        )
        ActionText(
            onActionTextClick = onSignInTextClick,
            resourceId = R.string.sign_in
        )
    }
}