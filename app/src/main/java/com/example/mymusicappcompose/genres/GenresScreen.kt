package com.example.mymusicappcompose.genres

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mymusicappcompose.R
import com.example.mymusicappcompose.presentation.componets.MyTitle
import com.example.mymusicappcompose.ui.theme.MyMusicAppComposeTheme

@Composable
fun GenresScreen() {
    val genres: List<String> = List(1000) { "$it" }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            MyTitle(
                onBack = { },
                title = R.string.genres_title,
                btnBackDescription = R.string.button_back_description
            )

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp, 8.dp)
            ) {
                items(genres) { genre ->
                    GenreItem(genre = genre)
                }

            }
        }

    }

}

@Composable
fun GenreItem(genre: String) {
    Column(
        modifier = Modifier.padding(8.dp)
    ) {
        Text(
            text = genre,
            modifier = Modifier.fillMaxSize(),
            style = TextStyle(textAlign = TextAlign.Center, fontSize = 16.sp)
        )
    }
}

@Preview
@Composable
fun GenresScreenPreview() {
    MyMusicAppComposeTheme {
        GenresScreen()
    }
}

@Preview
@Composable
fun GenreItemPreview() {
    MyMusicAppComposeTheme {
        GenreItem(genre = "Rock")
    }
}