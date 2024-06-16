package com.oddguild.scavengerai.data.source.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

open class ApiClient(
    val httpClient: HttpClient,
    val baseUrl: String
) {
    suspend inline fun <reified T> get(endpoint: String): T? =
        httpClient.get("$baseUrl/$endpoint").body()

    suspend inline fun <reified IN : Any, reified OUT> post(endpoint: String, body: IN): OUT =
        Json.decodeFromString(
            httpClient.post {
                url("$baseUrl/$endpoint")
                contentType(ContentType.Application.Json)
                setBody(Json.encodeToString(body))
            }.bodyAsText()
        )
}
