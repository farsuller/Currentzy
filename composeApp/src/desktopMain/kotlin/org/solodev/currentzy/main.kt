package org.solodev.currentzy

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.solodev.currentzy.di.initializeKoin

fun main() = application {
    initializeKoin()
    Window(
        onCloseRequest = ::exitApplication,
        title = "Currentzy",
    ) {
        App()
    }
}