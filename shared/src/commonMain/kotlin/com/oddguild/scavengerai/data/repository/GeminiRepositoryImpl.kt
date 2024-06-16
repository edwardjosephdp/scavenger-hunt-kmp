package com.oddguild.scavengerai.data.repository

import com.oddguild.scavengerai.data.source.remote.GeminiRemoteDataSource
import com.oddguild.scavengerai.domain.repository.GeminiRepository
import io.ktor.utils.io.errors.IOException

class GeminiRepositoryImpl(
    private val source: GeminiRemoteDataSource
): GeminiRepository {
    /*override suspend fun generateScavengerItems(location: String, count: Int): List<String> {
        val prompt = """You are a scavenger hunt game where objects are found by taking a photo of them. 
            Generate a list of $count items that could be found in the following location: $location. 
            The difficulty to find the items should be easy, but some items could be a little bit more difficult to find. 
            Keep the item name concise. All letters should be uppercase. Do not include articles (a, an, the). 
            Provide your response as a JSON object with the following schema: {"items": ["", "", ...]}. 
            Do not return your result as Markdown.""".trimIndent()

        val response = source.generateContent(prompt)

        return Json.parseToJsonElement(response.getText() ?: "{}")
            .jsonObject["items"]
            ?.jsonArray
            ?.map { it.jsonPrimitive.content }
            ?: emptyList()
    }

    override fun validateCapturedItem(item: String, image: String): Boolean {
        TODO("Not yet implemented")
    }*/

    override suspend fun generate(prompt: String, images: List<ByteArray>): Result<String> {
        return runCatching {
            val response = if (images.isEmpty()) {
                source.generateContent(prompt)
            } else {
                source.generateContentWithMedia(prompt, images)
            }

            response.error?.let {
                throw Exception(it.message)
            } ?: response.getText() ?: throw Exception("An error occurred, please retry.")
        }.recoverCatching { e ->
            when (e) {
                is IOException -> throw Exception("Unable to connect to the server. Please check your internet connection and try again.")
                else -> throw Exception("ERROR: [${e.message}]")
            }
        }
    }
}