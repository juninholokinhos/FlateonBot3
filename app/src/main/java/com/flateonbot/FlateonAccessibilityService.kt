package com.flateonbot

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.graphics.Path
import android.view.accessibility.AccessibilityEvent
import android.widget.Toast

class FlateonAccessibilityService : AccessibilityService() {

    companion object {
        var instancia: FlateonAccessibilityService? = null
            private set
    }

    private var executando = false

    override fun onServiceConnected() {
        super.onServiceConnected()
        instancia = this
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        // Nenhuma ação automática.
    }

    fun executarRota(rota: List<MovimentoRota>) {

        if (executando) {
            Toast.makeText(
                applicationContext,
                "Teste já está executando",
                Toast.LENGTH_LONG
            ).show()
            return
        }

        executando = true

        Toast.makeText(
            applicationContext,
            "TESTE: micro arrasto",
            Toast.LENGTH_LONG
        ).show()

        testarMicroArrasto()
    }

    private fun testarMicroArrasto() {

        val inicioX = 160f
        val inicioY = 565f

        val fimX = 180f
        val fimY = 565f

        val path = Path()

        path.moveTo(inicioX, inicioY)
        path.lineTo(fimX, fimY)

        val gesto = GestureDescription.Builder()
            .addStroke(
                GestureDescription.StrokeDescription(
                    path,
                    0,
                    100L
                )
            )
            .build()

        val enviado = dispatchGesture(
            gesto,
            object : GestureResultCallback() {

                override fun onCompleted(
                    gestureDescription: GestureDescription?
                ) {

                    executando = false

                    Toast.makeText(
                        applicationContext,
                        "MICRO ARRASTO CONCLUÍDO",
                        Toast.LENGTH_LONG
                    ).show()
                }

                override fun onCancelled(
                    gestureDescription: GestureDescription?
                ) {

                    executando = false

                    Toast.makeText(
                        applicationContext,
                        "MICRO ARRASTO CANCELADO",
                        Toast.LENGTH_LONG
                    ).show()
                }
            },
            null
        )

        if (!enviado) {

            executando = false

            Toast.makeText(
                applicationContext,
                "ANDROID RECUSOU O GESTO",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    fun pararRota() {

        executando = false

        Toast.makeText(
            applicationContext,
            "Teste parado",
            Toast.LENGTH_LONG
        ).show()
    }

    override fun onInterrupt() {

        executando = false
        instancia = null
    }

    override fun onDestroy() {

        executando = false
        instancia = null

        super.onDestroy()
    }
}
