package com.oddguild.scavengerai.data.source.remote

import com.oddguild.scavengerai.BuildKonfig
import com.oddguild.scavengerai.data.model.GeminiRequest
import com.oddguild.scavengerai.data.model.GeminiResponse

class GeminiRemoteDataSource(
    private val apiClient: ApiClient
) {
    suspend fun generateContent(prompt: String): GeminiResponse {
        return buildApiRequest("${BuildKonfig.BASE_URL}/v1beta/models/gemini-1.5-flash-latest:generateContent?key=${BuildKonfig.GEMINI_API_KEY}") {
            addText(prompt)
        }
    }

    suspend fun generateContentWithMedia(prompt: String, images: List<ByteArray>): GeminiResponse {
        return buildApiRequest("${BuildKonfig.BASE_URL}/v1beta/models/gemini-1.5-flash-latest:generateContent?key=${BuildKonfig.GEMINI_API_KEY}") {
            addText(prompt)
            addImages(images)
        }
    }

    private suspend fun buildApiRequest(endpoint: String, requestBuilder: GeminiRequest.RequestBuilder.() -> Unit): GeminiResponse {
        val request = GeminiRequest.RequestBuilder().apply(requestBuilder).build()
        val response = apiClient.post<GeminiRequest, GeminiResponse>(
            endpoint = endpoint,
            body = request
        )

        return response
    }
}