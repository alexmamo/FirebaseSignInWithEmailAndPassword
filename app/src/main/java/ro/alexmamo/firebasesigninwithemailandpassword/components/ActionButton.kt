package ro.alexmamo.firebasesigninwithemailandpassword.components

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp

@Composable
fun ActionButton(
    onActionButtonClick: () -> Unit,
    enabled: Boolean,
    resourceId: Int
) {
    Button(
        onClick = onActionButtonClick,
        enabled = enabled
    ) {
        Text(
            text = stringResource(
                id = resourceId
            ),
            fontSize = 15.sp
        )
    }
}