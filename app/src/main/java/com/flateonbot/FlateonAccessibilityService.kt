package com.flateonbot

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent

class FlateonAccessibilityService : AccessibilityService() {

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        // Futuramente vamos controlar o joystick aqui.
    }

    override fun onInterrupt() {
        // Serviço interrompido.
    }
}
