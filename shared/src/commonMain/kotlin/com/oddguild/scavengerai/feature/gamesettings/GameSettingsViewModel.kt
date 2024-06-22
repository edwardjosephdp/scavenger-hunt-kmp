package com.oddguild.scavengerai.feature.gamesettings

import com.oddguild.scavengerai.domain.usecase.GiveScavengerItemsUseCase
import com.oddguild.scavengerai.presentation.BaseViewModel
import com.oddguild.scavengerai.presentation.ViewModel

interface GameSettingsViewModel :
    BaseViewModel<GameSettingsContract.Event, GameSettingsContract.State, GameSettingsContract.Effect>

class GameSettingsViewModelImpl(
    private val useCase: GiveScavengerItemsUseCase
) : GameSettingsViewModel,
    ViewModel<GameSettingsContract.Event, GameSettingsContract.State, GameSettingsContract.Effect>() {
    override fun createInitialState() = GameSettingsContract.State()

    override fun handleEvent(event: GameSettingsContract.Event) {
        when (event) {
            is GameSettingsContract.Event.OnItemCountsSet -> setItemCount(event.count)
            is GameSettingsContract.Event.OnLocationSet -> setLocation(event.location)
            is GameSettingsContract.Event.OnStartGame -> generateScavengerItems(
                event.location,
                event.itemCount
            )
        }
    }

    private fun setItemCount(count: Int) {
        setState {
            copy(itemCount = count)
        }
    }

    private fun setLocation(location: String) {
        setState {
            copy(location = location)
        }
    }

    private fun generateScavengerItems(location: String, itemCount: Int) {
        setState {
            copy(isGameLoading = true)
        }
        collect(
            useCase(
                location = location,
                count = itemCount
            )
        ) { scavengerItems ->
            if (scavengerItems.isEmpty()) {
                setState {
                    copy(
                        isGameLoading = false,
                        isError = true
                    )
                }
                setEffect {
                    GameSettingsContract.Effect.ShowError("Unable to generate scavenger items.")
                }
            } else {
                setState {
                    copy(
                        isGameLoading = false,
                        isError = false
                    )
                }
                setEffect {
                    GameSettingsContract.Effect.GotoCaptureScreen(scavengerItems)
                }
            }
        }
    }
}
