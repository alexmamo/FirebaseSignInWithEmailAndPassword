package ro.alexmamo.firebasesigninwithemailandpassword.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ro.alexmamo.firebasesigninwithemailandpassword.core.Constants.DELETE_USER_ITEM
import ro.alexmamo.firebasesigninwithemailandpassword.core.Constants.SIGN_OUT_ITEM

@Composable
fun TopBar(
    title: String,
    signOut: () -> Unit,
    deleteUser: () -> Unit
) {
    var openMenu by remember { mutableStateOf(false) }

    TopAppBar (
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(
                        onClick = {
                            openMenu = !openMenu
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.MoreVert,
                            contentDescription = null,
                        )
                    }
                }
            }
        },
        actions = {
            DropdownMenu(
                expanded = openMenu,
                onDismissRequest = {
                    openMenu = !openMenu
                }
            ) {
                DropdownMenuItem(
                    onClick = {
                        signOut()
                        openMenu = !openMenu
                    }
                ) {
                    Text(
                        text = SIGN_OUT_ITEM
                    )
                }
                DropdownMenuItem(
                    onClick = {
                        deleteUser()
                        openMenu = !openMenu
                    }
                ) {
                    Text(
                        text = DELETE_USER_ITEM
                    )
                }
            }
        }
    )
}