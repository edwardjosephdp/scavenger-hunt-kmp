package com.oddguild.scavengerai.data.source.remote

import com.oddguild.scavengerai.BuildKonfig
import com.oddguild.scavengerai.data.model.GeminiRequest
import com.oddguild.scavengerai.data.model.GeminiResponse
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.post
import io.ktor.client.statement.bodyAsText
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.InternalAPI
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

private const val TIMEOUT = 30000L

@OptIn(ExperimentalSerializationApi::class, InternalAPI::class)
class GeminiService {

    // region Setup
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                isLenient = true
                explicitNulls = false
                encodeDefaults = true
                ignoreUnknownKeys = true
            })
        }
        install(HttpTimeout) {
            connectTimeoutMillis = TIMEOUT
            socketTimeoutMillis = TIMEOUT
            requestTimeoutMillis = TIMEOUT
        }
    }
    // endregion

    // region API calls
    suspend fun generateContent(prompt: String): GeminiResponse {
        return makeApiRequest("${BuildKonfig.BASE_URL}/v1beta/models/gemini-1.5-flash-latest:generateContent?key=${BuildKonfig.GEMINI_API_KEY}") {
            addText(prompt)
        }
    }

    suspend fun generateContentWithMedia(prompt: String, images: List<ByteArray>): GeminiResponse {
        return makeApiRequest("${BuildKonfig.BASE_URL}/v1beta/models/gemini-1.5-flash-latest:generateContent?key=${BuildKonfig.GEMINI_API_KEY}") {
            addText(prompt)
            addImages(images)
        }
    }

    private suspend fun makeApiRequest(url: String, requestBuilder: GeminiRequest.RequestBuilder.() -> Unit): GeminiResponse {
        val request = GeminiRequest.RequestBuilder().apply(requestBuilder).build()

        val response: String = client.post(url) {
            body = Json.encodeToString(request)
        }.bodyAsText()

        return Json.decodeFromString(response)
    }

    // endregion

}