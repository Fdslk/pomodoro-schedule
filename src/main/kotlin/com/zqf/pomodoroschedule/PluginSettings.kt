package com.zqf.pomodoroschedule

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage

@State(
    name = "pomodoro schedule",
    storages = [Storage("pomodoro-schedule-settings.xml")]
)
class PluginSettings: PersistentStateComponent<PluginState> {
    var pluginState = PluginState()

    override fun getState(): PluginState? {
        return pluginState
    }

    override fun loadState(state: PluginState) {
        pluginState = state
    }

    companion object {
        @JvmStatic
        fun getInstance(): PersistentStateComponent<PluginState> {
            return ServiceManager.getService(PluginSettings::class.java)
        }
    }
}