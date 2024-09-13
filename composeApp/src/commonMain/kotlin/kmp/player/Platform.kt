package kmp.player

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform