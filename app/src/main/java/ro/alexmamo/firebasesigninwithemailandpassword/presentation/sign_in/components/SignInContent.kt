package ro.alexmamo.firebasesigninwithemailandpassword.presentation.sign_in.components

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ro.alexmamo.firebasesigninwithemailandpassword.components.EmailField
import ro.alexmamo.firebasesigninwithemailandpassword.components.PasswordField
import ro.alexmamo.firebasesigninwithemailandpassword.components.VerticalSpacer
import ro.alexmamo.firebasesigninwithemailandpassword.core.Constants.EMPTY_STRING
import ro.alexmamo.firebasesigninwithemailandpassword.core.Constants.FORGOT_PASSWORD
import ro.alexmamo.firebasesigninwithemailandpassword.core.Constants.NO_ACCOUNT
import ro.alexmamo.firebasesigninwithemailandpassword.core.Constants.SIGN_IN_BUTTON
import ro.alexmamo.firebasesigninwithemailandpassword.core.Constants.VERTICAL_DIVIDER

@Composable
fun SignInContent(
    padding: PaddingValues,
    signIn: (email: String, password: String) -> Unit,
    signingIn: Boolean,
    navigateToForgotPasswordScreen: () -> Unit,
    navigateToSignUpScreen: () -> Unit
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
            onEmailValueChange = { newEmail ->
                email = newEmail
            }
        )
        VerticalSpacer()
        PasswordField(
            password = password,
            onPasswordValueChange = { newPassword ->
                password = newPassword
            }
        )
        VerticalSpacer()
        Button(
            onClick = {
                signIn(email.text, password.text)
                keyboard?.hide()
            },
            enabled = !signingIn
        ) {
            Text(
                text = SIGN_IN_BUTTON,
                fontSize = 15.sp
            )
        }
        Row {
            Text(
                modifier = Modifier.clickable {
                    navigateToForgotPasswordScreen()
                },
                text = FORGOT_PASSWORD,
                fontSize = 15.sp
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
            Text(
                modifier = Modifier.clickable {
                    navigateToSignUpScreen()
                },
                text = NO_ACCOUNT,
                fontSize = 15.sp
            )
        }
    }
}