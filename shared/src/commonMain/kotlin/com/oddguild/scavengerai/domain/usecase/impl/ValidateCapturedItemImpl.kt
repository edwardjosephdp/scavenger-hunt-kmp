package com.oddguild.scavengerai.domain.usecase.impl

import com.oddguild.scavengerai.domain.repository.GeminiRepository
import com.oddguild.scavengerai.domain.usecase.ValidateCapturedItem
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.boolean
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

class ValidateCapturedItemImpl(
    private val repository: GeminiRepository
): ValidateCapturedItem {
    override suspend fun invoke(item: String, image: List<ByteArray>): Boolean {
        val prompt = """You are a scavenger hunt game where objects are found by taking a photo of them. 
            You have been given the item "$item" and a photo of the item. 
            Determine if the photo is a valid photo of the item. 
            Provide your response as a JSON object with the following schema: {"valid": true/false}. 
            Do not return your result as Markdown.""".trimIndent()

        val result = repository.generate(prompt = prompt, images = image)

        return result.fold(
            onSuccess = {response ->
                Json.parseToJsonElement(response)
                    .jsonObject["valid"]
                    ?.jsonPrimitive
                    ?.boolean
                    ?: false
            },
            onFailure = {
                false
            }
        )
    }
}