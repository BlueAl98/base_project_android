package com.example.baseproyect.presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.DialogProperties
import com.example.baseproyect.R


@Composable
fun ErrorDialog(
    titleMessage: String = stringResource(id = R.string.title_error),
    errorMessage: String = stringResource(id = R.string.unexpected_error),
    onDismiss: () -> Unit
) {
    AlertDialog(
        containerColor = Color.White,
        onDismissRequest = {},
        title = {
            Text(text = titleMessage, style = TextStyle(color = Color.Black))
        },
        text = {
            Text(text = errorMessage, style = TextStyle(color = Color.Black))
        },
        confirmButton = {
            TextButton(
                colors = ButtonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.DarkGray,
                    disabledContentColor = Color.LightGray,
                    disabledContainerColor = Color.Transparent
                ),
                onClick = {
                    onDismiss()
                }
            ) {
                Text(text = "Cerrar")
            }
        },
        properties = DialogProperties(
            dismissOnClickOutside = false,
            dismissOnBackPress = false
        )
    )
}

@Composable
fun CustomAlertDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    confirmButtonText: String ,
    dismissButtonText: String
) {
    AlertDialog(
        containerColor = Color.White,
        title = {
            Text(text = dialogTitle, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
        },
        text = {
            Text(text = dialogText,  modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween, // Alinea los botones a los extremos
                modifier = Modifier.fillMaxWidth() // Asegura que la fila ocupe todo el ancho
            ) {
                TextButton(
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = Color.Red
                    ),
                    onClick = {
                        onDismissRequest()
                    }
                ) {
                    Text(dismissButtonText)
                }

                TextButton(
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = Color.Black
                    ),
                    onClick = {
                        onConfirmation()
                    }
                ) {
                    Text(confirmButtonText)
                }
            }


        },
        properties = DialogProperties(
            dismissOnClickOutside = false,
            dismissOnBackPress = false
        )
    )
}
