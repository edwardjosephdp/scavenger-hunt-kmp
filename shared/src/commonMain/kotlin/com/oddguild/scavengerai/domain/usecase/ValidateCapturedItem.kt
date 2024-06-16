package com.oddguild.scavengerai.domain.usecase

interface ValidateCapturedItem {
    suspend operator fun invoke(item: String, image: List<ByteArray>): Boolean
}