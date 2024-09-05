package com.example.mymusicappcompose.presentation.componets

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mymusicappcompose.R
import com.example.mymusicappcompose.ui.theme.MyMusicAppComposeTheme

@Composable
fun MySearch(
    onBack: () -> Unit,
    onClear: () -> Unit,
    value: String,
    onValueChange: (String) -> Unit
) {
    Row {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = value,
            onValueChange = onValueChange,
            label = {
                Row {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = stringResource(id = R.string.search),
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = stringResource(id = R.string.search),
                        style = TextStyle(
                            textAlign = TextAlign.Center,
                            fontSize = 12.sp
                        )
                    )
                }
            },
            leadingIcon = {
                if (value.isEmpty()) {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.button_back_description)
                        )
                    }
                } else {
                    IconButton(onClick = onClear) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = stringResource(id = R.string.button_close_description)
                        )
                    }
                }
            }
        )

    }
}

@Preview
@Composable
fun MySearchPreview() {
    MyMusicAppComposeTheme {
        MySearch(value = "", onBack = {}, onClear = {}, onValueChange = {})
    }
}


@Preview
@Composable
fun MySearchBackPreview() {
    MyMusicAppComposeTheme {
        MySearch(value = "Texto", onBack = {}, onClear = {}, onValueChange = {})
    }
}