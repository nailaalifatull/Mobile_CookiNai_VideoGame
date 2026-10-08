package com.cookinai.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val CookiNaiColorScheme = lightColorScheme(
    primary = SoftPink,
    onPrimary = OnPrimary,
    primaryContainer = SoftPinkDark,
    secondary = LightBlueDark,
    onSecondary = OnSurface,
    secondaryContainer = LightBlue,
    tertiary = PastelYellow,
    onTertiary = OnSurface,
    tertiaryContainer = PastelYellowDark,
    background = LightBlue,
    onBackground = OnBackground,
    surface = Surface,
    onSurface = OnSurface,
    error = ErrorColor,
    outline = Outline,
)

@Composable
fun CookiNaiTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = CookiNaiColorScheme,
        typography = CookiNaiTypography,
        content = content
    )
}
