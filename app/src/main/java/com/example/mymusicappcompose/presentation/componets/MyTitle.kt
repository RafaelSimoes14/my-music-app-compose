package com.example.mymusicappcompose.presentation.componets

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.mymusicappcompose.R
import com.example.mymusicappcompose.ui.theme.MyMusicAppComposeTheme

@Composable
fun MyTitle(
    onBack: () -> Unit,
    @StringRes title: Int,
    @StringRes btnBackDescription: Int
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBack) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = stringResource(id = btnBackDescription)
            )
        }

        Text(
            modifier = Modifier.fillMaxWidth(.9f),
            text = stringResource(id = title),
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        )
    }
}

@Preview
@Composable
fun MyTitlePreview() {
    MyMusicAppComposeTheme {
        MyTitle(onBack = { }, title = R.string.sign_in_title, btnBackDescription = R.string.button_back_description)
    }
}