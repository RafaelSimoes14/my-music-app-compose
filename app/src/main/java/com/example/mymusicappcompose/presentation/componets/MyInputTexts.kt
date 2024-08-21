package com.example.mymusicappcompose.presentation.componets

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.example.mymusicappcompose.R
import com.example.mymusicappcompose.ui.theme.MyMusicAppComposeTheme

@Composable
fun MyPasswordInput(
    value: String,
    onValueChange: (String) -> Unit,
    @StringRes label: Int,
    @StringRes trailingIconDescription: Int,
    modifier: Modifier = Modifier
) {
    var isPasswordVisible by remember { mutableStateOf(true) }

    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = { onValueChange(it) },
        label = {
            Text(stringResource(id = label))
        },
        trailingIcon = {
            IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                Icon(
                    imageVector = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                    contentDescription = stringResource(
                        id = trailingIconDescription
                    )
                )
            }
        },
        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()
    )
}

@Composable
fun MyInput(
    value: String,
    onValueChange: (String) -> Unit,
    trailingIcon: ImageVector,
    @StringRes label: Int,
    @StringRes trailingIconDescription: Int,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = { onValueChange(it) },
        label = {
            Text(stringResource(id = label))
        },
        trailingIcon = {
            IconButton(onClick = {}) {
                Icon(
                    imageVector = trailingIcon,
                    contentDescription = stringResource(
                        id = trailingIconDescription
                    )
                )
            }
        }
    )
}

@Preview
@Composable
fun MyInputPreview() {
    MyMusicAppComposeTheme {
        MyInput(
            modifier = Modifier.fillMaxWidth(),
            value = "",
            onValueChange = { },
            trailingIcon = Icons.Default.Email,
            label = R.string.email,
            trailingIconDescription = R.string.email_icon_description
        )
    }
}

@Preview
@Composable
fun MyPasswordInputPreview() {
    MyMusicAppComposeTheme {
        MyPasswordInput(
            modifier = Modifier.fillMaxWidth(),
            value = "",
            onValueChange = { },
            label = R.string.password,
            trailingIconDescription = R.string.password_icon_description
        )
    }
}
