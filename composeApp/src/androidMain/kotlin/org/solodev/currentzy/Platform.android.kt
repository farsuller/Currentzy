package org.solodev.currentzy

class AndroidPlatform : Platform {
    override val name: String = "Android"
}

actual fun getPlatform(): Platform = AndroidPlatform()