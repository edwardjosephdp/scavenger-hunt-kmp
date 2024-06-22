package com.oddguild.scavengerai.domain.usecase

interface ValidateCapturedItemUseCase {
    suspend operator fun invoke(item: String, image: List<ByteArray>): Boolean
}