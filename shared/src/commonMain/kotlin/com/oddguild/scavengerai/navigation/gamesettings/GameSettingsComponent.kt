package com.oddguild.scavengerai.navigation.gamesettings

import com.arkivanov.decompose.value.Value
import com.oddguild.scavengerai.feature.gamesettings.GameSettingsContract

interface GameSettingsComponent {

    val uiState: Value<GameSettingsContract.State>

    fun onLocationSelected(location: String)

    fun onItemsGenerated(items: List<String>)
}