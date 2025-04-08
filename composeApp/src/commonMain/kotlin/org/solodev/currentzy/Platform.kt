package org.solodev.currentzy

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform