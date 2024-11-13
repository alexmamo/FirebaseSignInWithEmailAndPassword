package ro.alexmamo.firebasesigninwithemailandpassword.components

import androidx.compose.foundation.clickable
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp

@Composable
fun ActionText(
    onActionTextClick: () -> Unit,
    resourceId: Int
) {
    Text(
        modifier = Modifier.clickable {
            onActionTextClick()
        },
        text = stringResource(
            id = resourceId
        ),
        fontSize = 15.sp
    )
}