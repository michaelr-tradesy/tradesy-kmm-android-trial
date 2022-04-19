package com.example.tradesykmmandroidtrial

interface AppThemeState {
    val isDarkTheme: Boolean
    val colorPalette: ColorPalette
    val isSystemModeEnable: Boolean
}

data class DefaultAppThemeState(
    override var isDarkTheme: Boolean = true,
    override var isSystemModeEnable: Boolean = true,
    override var colorPalette: ColorPalette = ColorPalette.Bamboo
) : AppThemeState