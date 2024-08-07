package com.oddguild.scavengerai.domain.usecase.impl

import com.oddguild.scavengerai.domain.repository.GeminiRepository
import com.oddguild.scavengerai.domain.usecase.GiveScavengerItemsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

class GiveScavengerItemsUseCaseImpl(
    private val repository: GeminiRepository
): GiveScavengerItemsUseCase {
    override fun invoke(location: String, count: Int): Flow<List<String>> = flow {
        val prompt = """You are a scavenger hunt game where objects are found by taking a photo of them. 
            Generate a list of $count items that could be found in the following location: $location. 
            The difficulty to find the items should be easy, but some items could be a little bit more difficult to find. 
            Keep the item name concise. All letters should be uppercase. Do not include articles (a, an, the). 
            Provide your response as a JSON object with the following schema: {"items": ["", "", ...]}. 
            Do not return your result as Markdown.""".trimIndent()

        val result = repository.generate(prompt = prompt)

        emit(
            result.fold(
                onSuccess = { response ->
                    Json.parseToJsonElement(response)
                        .jsonObject["items"]
                        ?.jsonArray
                        ?.map { it.jsonPrimitive.content }
                        ?: emptyList()
                },
                onFailure = {
                    emptyList()
                }
            )
        )
    }
}