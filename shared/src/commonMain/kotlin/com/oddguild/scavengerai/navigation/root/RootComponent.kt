package com.oddguild.scavengerai.navigation.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.oddguild.scavengerai.navigation.gamesettings.GameSettingsComponent

interface RootComponent {

    val stack: Value<ChildStack<*, Child>>

    fun onBackClicked(toIndex: Int)

    sealed class Child {
        class GameSettings(val component: GameSettingsComponent) : Child()
    }
}