package com.flateonbot

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import android.widget.Toast

class FlateonAccessibilityService : AccessibilityService() {

    override fun onServiceConnected() {
        super.onServiceConnected()

        Toast.makeText(
            this,
            "FlateonBot: serviço ativo",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        // Por enquanto não fazemos nenhuma ação.
    }

    override fun onInterrupt() {
        Toast.makeText(
            this,
            "FlateonBot: serviço interrompido",
            Toast.LENGTH_SHORT
        ).show()
    }
}
