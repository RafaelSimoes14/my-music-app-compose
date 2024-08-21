package com.example.mymusicappcompose.presentation.componets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.mymusicappcompose.ui.theme.MyMusicAppComposeTheme

@Composable
fun LoadingDialog(text: String? = null) {
    return Dialog(
        onDismissRequest = { },
        DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(64.dp)
                .background(
                    Color.White,
                    shape = RoundedCornerShape(8.dp)
                )
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(4.dp)
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(40.dp)
                )
                if (text != null) {
                    Text(
                        text = text,
                        style = TextStyle(
                            fontSize = 11.sp,
                            fontWeight = FontWeight(600),
                            color = Color.White,
                        )
                    )
                }
            }
        }
    }
}


@Preview
@Composable
fun LoadingDialogPreview() {
    MyMusicAppComposeTheme {
        Surface {
            LoadingDialog()
        }
    }
}

@Preview
@Composable
fun LoadingDialogWithTextPreview() {
    MyMusicAppComposeTheme {
        Surface {
            LoadingDialog("Loading...")
        }
    }
}