package org.solodev.currentzy

import DarkColors
import LightColors
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import cafe.adriel.voyager.navigator.Navigator
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.solodev.currentzy.di.initializeKoin
import org.solodev.currentzy.presentation.screen.HomeScreen


@Composable
@Preview
fun App() {
    val colors = if(!isSystemInDarkTheme()) LightColors else DarkColors
    MaterialTheme(colorScheme = colors) {
        Navigator(HomeScreen())
    }
}