package com.example.projetreveil.ui.theme.utilities

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.projetreveil.R

@Composable
fun ConfirmActionPopup(
    display: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    title: String,
    text: String,
    confirmText: String = stringResource(R.string.confirm_action_popup),
    dismissText: String = stringResource(R.string.dismiss_action_popup)
){
    if (!display)
        return

    AlertDialog(
        onDismissRequest = {
            onDismiss()
        },
        title =  {
            Text(title)
        },
        text = {
            Text(text)
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirm()
                }
            ) {
                Text(confirmText)
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismiss()
                }
            ) {
                Text(dismissText)
            }
        }
    )
}