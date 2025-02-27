package ro.alexmamo.firebasesigninwithemailandpassword.presentation.verify_email.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ro.alexmamo.firebasesigninwithemailandpassword.R

@Composable
fun VerifyEmailContent(
    innerPadding: PaddingValues,
    onAlreadyVerifiedTextClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(innerPadding).padding(
            start = 32.dp,
            end = 32.dp
        ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.clickable {
                onAlreadyVerifiedTextClick()
            },
            text = stringResource(
                id = R.string.already_verified
            ),
            fontSize = 17.sp,
            textDecoration = TextDecoration.Underline
        )
        Spacer(
            modifier = Modifier.height(8.dp)
        )
        Text(
            text = stringResource(
                id = R.string.spam_email
            ),
            fontSize = 15.sp
        )
    }
}