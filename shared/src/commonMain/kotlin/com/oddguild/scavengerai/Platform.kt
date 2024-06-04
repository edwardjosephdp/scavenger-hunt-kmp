package com.oddguild.scavengerai

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform