package com.example.mymusicappcompose.genres

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.mymusicappcompose.ui.theme.MyMusicAppComposeTheme

class GenresActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            MyMusicAppComposeTheme {
                GenresScreen()
            }
        }
    }
}