package com.example.tradesykmmandroidtrial.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import com.example.tradesykmmandroidtrial.AppThemeState
import com.google.accompanist.systemuicontroller.SystemUiController

@Composable
fun TradesyKMMAndroidTrialTheme(
    systemUiController: SystemUiController? = null,
    appThemeState: AppThemeState? = null,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val isDarkTheme = (appThemeState?.isSystemModeEnable == false
            && appThemeState.isDarkTheme) || darkTheme
    val colorPalette = appThemeState?.colorPalette ?: ColorPalette.Pebble
    val colors = getAppThemeColors(isDarkTheme, colorPalette)

    systemUiController?.apply {
        setStatusBarColor(color = colors.primary, darkIcons = darkTheme)
        setNavigationBarColor(color = colors.primary, darkIcons = darkTheme)
        setSystemBarsColor(color = colors.primary, darkIcons = darkTheme)
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}

fun getAppThemeColors(
    isDarkTheme: Boolean,
    colorPalette: ColorPalette
): Colors {
    val colors = if (isDarkTheme) {
        when (colorPalette) {
            ColorPalette.Bamboo -> DarkBambooColorPalette
            ColorPalette.Coral -> DarkCoralColorPalette
            ColorPalette.Fuji -> DarkFujiColorPalette
            ColorPalette.Jade -> DarkJadeColorPalette
            ColorPalette.Orenji -> DarkOrenjiColorPalette
            ColorPalette.Pebble -> DarkPebbleColorPalette
            ColorPalette.Sakura -> DarkSakuraColorPalette
            ColorPalette.Sand -> DarkSandColorPalette
            ColorPalette.Sun -> DarkSunColorPalette
            ColorPalette.Wave -> DarkWaveColorPalette
        }
    } else {
        when (colorPalette) {
            ColorPalette.Bamboo -> LightBambooColorPalette
            ColorPalette.Coral -> LightCoralColorPalette
            ColorPalette.Fuji -> LightFujiColorPalette
            ColorPalette.Jade -> LightJadeColorPalette
            ColorPalette.Orenji -> LightOrenjiColorPalette
            ColorPalette.Pebble -> LightPebbleColorPalette
            ColorPalette.Sakura -> LightSakuraColorPalette
            ColorPalette.Sand -> LightSandColorPalette
            ColorPalette.Sun -> LightSunColorPalette
            ColorPalette.Wave -> LightWaveColorPalette
        }
    }
    return colors
}