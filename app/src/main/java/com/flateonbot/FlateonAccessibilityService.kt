package com.flateonbot

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent

class FlateonAccessibilityService : AccessibilityService() {

    companion object {
        var instancia: FlateonAccessibilityService? = null
            private set
    }

    override fun onServiceConnected() {
        super.onServiceConnected()
        instancia = this
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        // Os comandos do bot serão adicionados aqui posteriormente.
    }

    override fun onInterrupt() {
        instancia = null
    }

    override fun onDestroy() {
        instancia = null
        super.onDestroy()
    }
}
