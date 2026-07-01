package com.example.skillforge.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(

    primary = Primary,
    secondary = PrimaryDark,

    background = Background,
    surface = CardColor,

    onPrimary = TextPrimary,
    onSecondary = TextPrimary,
    onBackground = TextPrimary,
    onSurface = TextPrimary

)

private val DarkColorScheme = darkColorScheme(

    primary = Primary,
    secondary = PrimaryDark,

    background = Background,
    surface = CardColor,

    onPrimary = TextPrimary,
    onSecondary = TextPrimary,
    onBackground = TextPrimary,
    onSurface = TextPrimary

)

@Composable
fun SkillForgeTheme(

    darkTheme: Boolean = false,
    content: @Composable () -> Unit

) {

    MaterialTheme(

        colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,

        typography = Typography,

        content = content

    )

}