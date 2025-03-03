package ro.alexmamo.firebasesigninwithemailandpassword.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import ro.alexmamo.firebasesigninwithemailandpassword.R

@Composable
fun EmailField(
    email: TextFieldValue,
    onEmailChange: (TextFieldValue) -> Unit
) {
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    OutlinedTextField(
        modifier = Modifier.focusRequester(focusRequester),
        value = email,
        onValueChange = onEmailChange,
        label = {
            Text(
                text = stringResource(
                    id = R.string.email_label
                )
            )
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email
        )
    )
}