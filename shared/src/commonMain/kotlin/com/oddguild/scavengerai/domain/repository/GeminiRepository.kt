package com.oddguild.scavengerai.domain.repository

interface GeminiRepository {

    suspend fun generate(prompt: String, images: List<ByteArray> = emptyList()): Result<String>

    // suspend fun generateScavengerItems(location: String, count: Int = 5): List<String>

    // fun validateCapturedItem(item: String, image: String): Boolean
}