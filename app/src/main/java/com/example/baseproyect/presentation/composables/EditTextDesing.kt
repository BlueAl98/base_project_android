package com.example.baseproyect.presentation.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun GeneralTextField(
    modifier: Modifier,
    text: TextFieldValue,
    onTextChange: (TextFieldValue) -> Unit,
    enabled: Boolean
) {
    OutlinedTextField(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth(),
        value = text,
        label = { Text(text = "Correo electrónico") },
        onValueChange = onTextChange,
        leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = "email") },
        enabled = enabled,
    /*    colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = secondaryColor,
            focusedBorderColor = secondaryColor,
            focusedLabelColor = secondaryColor,
            unfocusedLabelColor = secondaryColor

        )*/
    )
}

@Composable
fun PaswordTextField(
    modifier: Modifier,
    text: TextFieldValue,
    onTextChange: (TextFieldValue) -> Unit,
    enabled: Boolean
) {

    var passwordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth(),
        value = text,
        label = { Text(text = "Contraseña") },
        onValueChange = onTextChange,
        leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = "password") },
        visualTransformation = if (passwordVisible) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        enabled = enabled,
        trailingIcon = {
            val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(imageVector = image, contentDescription = if (passwordVisible) "Hide password" else "Show password")
            }
        },
      /*  colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = secondaryColor,
            focusedBorderColor = secondaryColor,
            focusedLabelColor = secondaryColor,
            unfocusedLabelColor = secondaryColor
        )*/

    )
}