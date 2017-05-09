package com.sjd.intellijx

import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.MessageType
import com.intellij.openapi.ui.popup.Balloon
import com.intellij.openapi.ui.popup.JBPopupFactory
import com.intellij.openapi.wm.WindowManager
import com.intellij.ui.awt.RelativePoint

object Balloons {

    fun show(project: Project, messageType: MessageType, balloonText: String) {
        val statusBar = WindowManager.getInstance().getStatusBar(project)
        val relativePoint = RelativePoint.getCenterOf(statusBar.component)
        JBPopupFactory.getInstance()
                .createHtmlTextBalloonBuilder(balloonText, messageType, null)
                .setFadeoutTime(7500)
                .createBalloon()
                .show(relativePoint, Balloon.Position.atRight)
    }

}