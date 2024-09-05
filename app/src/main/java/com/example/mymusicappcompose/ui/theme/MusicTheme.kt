package com.example.mymusicappcompose.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class MusicTheme(
    val action: Color = Color.Unspecified,
    val title: Color = Color.Unspecified,
    val subTitle: Color = Color.Unspecified,
    val background: Color = Color.Unspecified
)

val MusicLightTheme = MusicTheme(
    action = Color(PrimaryLight.value),
    title = Color(PrimaryLight.value),
    subTitle = Color(PrimaryLight.value),
    background = Color(BackgroundLight.value)
)

val MusicDarkTheme = MusicTheme(
    action = Color(PrimaryDark.value),
    title = Color(PrimaryDark.value),
    subTitle = Color(PrimaryDark.value),
    background = Color(BackgroundDark.value)
)

val MusicThemeStatic = staticCompositionLocalOf { MusicTheme() }
