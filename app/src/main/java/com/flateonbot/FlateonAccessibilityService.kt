package com.flateonbot

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import android.widget.Toast

class FlateonAccessibilityService : AccessibilityService() {

    override fun onServiceConnected() {
        super.onServiceConnected()

        Toast.makeText(
            applicationContext,
            "FLATEON BOT CONECTADO!",
            Toast.LENGTH_LONG
        ).show()
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
    }

    override fun onInterrupt() {
        Toast.makeText(
            applicationContext,
            "FLATEON BOT INTERROMPIDO!",
            Toast.LENGTH_LONG
        ).show()
    }
}
