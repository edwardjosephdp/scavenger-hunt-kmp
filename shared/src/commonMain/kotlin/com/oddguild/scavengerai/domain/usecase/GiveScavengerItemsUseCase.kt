package com.oddguild.scavengerai.domain.usecase

import kotlinx.coroutines.flow.Flow

interface GiveScavengerItemsUseCase {
    operator fun invoke(location: String, count: Int): Flow<List<String>>
}
