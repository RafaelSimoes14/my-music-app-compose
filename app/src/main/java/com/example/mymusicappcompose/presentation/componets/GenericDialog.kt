package com.example.mymusicappcompose.presentation.componets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.mymusicappcompose.ui.theme.MyMusicAppComposeTheme

@Composable
fun GenericDialog(
    dismissOnBackPress: Boolean = false,
    dismissOnClickOutside: Boolean = false,
    onDismissRequest: (() -> Unit)? = null,
    icon: ImageVector = Icons.Outlined.Info,
    title: String,
    question: String? = null,
    onCancel: (() -> Unit)? = null,
    onConfirm: () -> Unit
) {
    Dialog(
        onDismissRequest = { onDismissRequest?.invoke() },
        DialogProperties(
            dismissOnBackPress = dismissOnBackPress,
            dismissOnClickOutside = dismissOnClickOutside
        )
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, shape = RoundedCornerShape(8.dp))
                .padding(vertical = 20.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier
                        .size(48.dp),
                    tint = Color.Black
                )

                Text(
                    text = title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(vertical = 8.dp),
                    style = TextStyle()
                )

                if (question != null) {
                    Text(
                        text = question,
                        fontSize = 10.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(start = 12.dp, end = 12.dp, bottom = 12.dp),
                        style = TextStyle()
                    )
                }


                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {

                    if (onCancel != null) {
                        OutlinedButton(
                            enabled = true,
                            onClick = { onCancel.invoke() },
                            border = BorderStroke(1.dp, Color.Black),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent,
                                disabledContainerColor = Color.Gray,
                                disabledContentColor = Color.White,
                            ),
                            modifier = Modifier.height(40.dp).padding(start = 6.dp)
                        ) {
                            Text(
                                text = "Cancel",
                                color = Color.Black,
                                style = TextStyle(
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight(400),
                                    textAlign = TextAlign.Center,
                                )
                            )
                        }
                    }

                    Button(
                        modifier = Modifier.padding(6.dp),
                        enabled = true,
                        onClick = { onConfirm.invoke() }
                    ) {
                        Text(
                            text = "Confirm",
                            color = Color.White,
                            style = TextStyle(
                                fontSize = 11.sp,
                                color = Color.White,
                                textAlign = TextAlign.Center,
                            )
                        )
                    }

                }
            }
        }
    }
}

@Preview
@Composable
fun SignOutDialogPreview() {
    MyMusicAppComposeTheme {
        GenericDialog(
            title = "Erro",
            question = "Ocorreu um erro",
            onConfirm = {}
        )
    }
}
