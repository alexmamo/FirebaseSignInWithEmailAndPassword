package ro.alexmamo.firebasesigninwithemailandpassword.presentation.sign_up.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.sp
import ro.alexmamo.firebasesigninwithemailandpassword.components.EmailField
import ro.alexmamo.firebasesigninwithemailandpassword.components.PasswordField
import ro.alexmamo.firebasesigninwithemailandpassword.components.VerticalSpacer
import ro.alexmamo.firebasesigninwithemailandpassword.core.Constants.ALREADY_USER
import ro.alexmamo.firebasesigninwithemailandpassword.core.Constants.EMPTY_STRING
import ro.alexmamo.firebasesigninwithemailandpassword.core.Constants.SIGN_UP_BUTTON

@Composable
fun SignUpContent(
    padding: PaddingValues,
    signUp: (email: String, password: String) -> Unit,
    signingUp: Boolean,
    navigateBack: () -> Unit,
    sendingEmailVerification: Boolean
) {
    var email by rememberSaveable(
        stateSaver = TextFieldValue.Saver
    ) { mutableStateOf(TextFieldValue(EMPTY_STRING)) }
    var password by rememberSaveable(
        stateSaver = TextFieldValue.Saver
    ) { mutableStateOf(TextFieldValue(EMPTY_STRING)) }
    val keyboard = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier.fillMaxSize().padding(padding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EmailField(
            email = email,
            onEmailValueChange = { newValue ->
                email = newValue
            }
        )
        VerticalSpacer()
        PasswordField(
            password = password,
            onPasswordValueChange = { newValue ->
                password = newValue
            }
        )
        VerticalSpacer()
        Button(
            onClick = {
                keyboard?.hide()
                signUp(email.text, password.text)
            },
            enabled = !signingUp && !sendingEmailVerification
        ) {
            Text(
                text = SIGN_UP_BUTTON,
                fontSize = 15.sp
            )
        }
        Text(
            modifier = Modifier.clickable {
                navigateBack()
            },
            text = ALREADY_USER,
            fontSize = 15.sp
        )
    }
}