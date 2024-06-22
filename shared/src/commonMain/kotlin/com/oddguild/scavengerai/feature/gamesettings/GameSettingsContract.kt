package com.oddguild.scavengerai.feature.gamesettings

import com.oddguild.scavengerai.presentation.UIEffect
import com.oddguild.scavengerai.presentation.UIEvent
import com.oddguild.scavengerai.presentation.UIState

interface GameSettingsContract {
    data class State(
        val location: String? = null,
        val isGameLoading: Boolean = false,
        val itemCount: Int? = null,
        val isError: Boolean = false,
    ): UIState

    sealed interface Event: UIEvent {
        data class OnLocationSet(val location: String): Event
        data class OnItemCountsSet(val count: Int): Event
        data class OnStartGame(val location: String, val itemCount: Int): Event
    }

    sealed interface Effect: UIEffect {
        data class ShowError(val message: String): Effect
        data class GotoCaptureScreen(val items: List<String>): Effect
    }
}
