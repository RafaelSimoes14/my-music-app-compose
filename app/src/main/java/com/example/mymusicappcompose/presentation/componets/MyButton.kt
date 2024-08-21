package com.example.mymusicappcompose.presentation.componets

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mymusicappcompose.ui.theme.MyMusicAppComposeTheme

@Composable
fun MyButton(
    onConfirm: () -> Unit,
    text: String,
    enabled: Boolean = true
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        enabled = enabled,
        onClick = {
            //onConfirm() ou onConfirm.invoke() sao a mesma coisa, ou seja, uma chamada da funcao recebida por parametro
            onConfirm.invoke()
        }) {
        Text(text = text)
    }
}

@Preview
@Composable
fun MyButtonPreview() {
    MyMusicAppComposeTheme {
        MyButton(
            onConfirm = {},
            text = "Submit"
        )
    }
}
