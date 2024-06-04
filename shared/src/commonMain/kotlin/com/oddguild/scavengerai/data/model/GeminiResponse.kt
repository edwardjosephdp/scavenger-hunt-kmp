package com.oddguild.scavengerai.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GeminiResponse(
    @SerialName("candidates") val candidates: List<Candidate> = emptyList(),
    @SerialName("usageMetadata") val usageMetadata: UsageMetadata,
    @SerialName("error") val error: Error? = null,
) {
    fun getText(): String? = candidates.firstOrNull()?.content?.parts?.firstOrNull()?.text
}

@Serializable
data class Candidate(
    @SerialName("index") val index: Int,
    @SerialName("content") val content: Content? = null,
    @SerialName("finishReason") val finishReason: String? = null,
    @SerialName("safetyRatings") val safetyRatings: List<SafetyRating> = emptyList(),
    @SerialName("citationMetadata") val citationMetadata: CitationMetadata? = null,
)

@Serializable
data class CitationMetadata(
    @SerialName("citationSources") val citationSources: List<CitationSource> = emptyList()
)

@Serializable
data class CitationSource(
    @SerialName("startIndex") val startIndex: Int,
    @SerialName("endIndex") val endIndex: Int,
    @SerialName("uri") val uri: String,
    @SerialName("license") val license: String
)

@Serializable
data class UsageMetadata(
    @SerialName("promptTokenCount") val promptTokenCount: Int,
    @SerialName("candidatesTokenCount") val candidatesTokenCount: Int,
    @SerialName("totalTokenCount") val totalTokenCount: Int
)

@Serializable
data class Content(
    @SerialName("parts") val parts: List<Part> = emptyList(),
    @SerialName("role") val role: String? = null,
)

@Serializable
data class Part(
    @SerialName("text") val text: String? = null,
)

@Serializable
data class SafetyRating(
    @SerialName("category") val category: String, @SerialName("probability") val probability: String
)

@Serializable
data class Error(
    @SerialName("code") val code: Int,
    @SerialName("message") val message: String,
    @SerialName("status") val status: String
)
