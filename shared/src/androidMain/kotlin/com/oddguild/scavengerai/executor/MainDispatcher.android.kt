package com.oddguild.scavengerai.executor

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

actual class MainDispatcher actual constructor() {
    actual val dispatcher: CoroutineDispatcher = Dispatchers.Main
}
