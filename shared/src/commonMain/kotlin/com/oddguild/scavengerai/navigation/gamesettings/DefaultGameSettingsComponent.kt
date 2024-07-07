package com.oddguild.scavengerai.navigation.gamesettings

import com.arkivanov.decompose.value.Value
import com.oddguild.scavengerai.feature.gamesettings.GameSettingsContract


class DefaultGameSettingsComponent : GameSettingsComponent {
    override val uiState: Value<GameSettingsContract.State>
        get() = TODO("Not yet implemented")

    override fun onLocationSelected(location: String) {
        TODO("Not yet implemented")
    }

    override fun onItemsGenerated(items: List<String>) {
        TODO("Not yet implemented")
    }

}