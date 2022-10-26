package com.zqf.pomodoroschedule

import com.intellij.openapi.Disposable
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.zqf.pomodoroschedule.model.PomodoroModel
import com.zqf.pomodoroschedule.model.time.Time

@Service
class PomodoroService: Disposable {
    val model = PomodoroModel(service(), service()).also { it.onIdeStartup(Time.now()) }
    override fun dispose() {
        TODO("Not yet implemented")
    }
}