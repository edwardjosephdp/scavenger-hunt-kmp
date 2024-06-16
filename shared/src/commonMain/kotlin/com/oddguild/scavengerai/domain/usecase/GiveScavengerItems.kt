package com.oddguild.scavengerai.domain.usecase

interface GiveScavengerItems {
    suspend operator fun invoke(location: String, count: Int): List<String>
}
