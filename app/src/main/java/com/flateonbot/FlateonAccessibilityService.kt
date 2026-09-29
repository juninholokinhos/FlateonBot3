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

    // Centro aproximado do joystick na imagem enviada.
    private val joystickX = 160f
    private val joystickY = 565f

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

        if (rota.isEmpty()) {
            Toast.makeText(
                applicationContext,
                "Rota vazia",
                Toast.LENGTH_LONG
            ).show()
            return
        }

        executando = true

        val movimento = rota[0]

        Toast.makeText(
            applicationContext,
            "TESTE INICIADO: ${movimento.direcao}",
            Toast.LENGTH_LONG
        ).show()

        testarGesto(
            movimento.direcao,
            movimento.duracao
        )
    }

    private fun testarGesto(
        direcao: String,
        duracao: Long
    ) {

        val inicioX = joystickX
        val inicioY = joystickY

        val distancia = 100f

        val fimX: Float
        val fimY: Float

        when (direcao) {

            "CIMA" -> {
                fimX = inicioX
                fimY = inicioY - distancia
            }

            "BAIXO" -> {
                fimX = inicioX
                fimY = inicioY + distancia
            }

            "ESQUERDA" -> {
                fimX = inicioX - distancia
                fimY = inicioY
            }

            "DIREITA" -> {
                fimX = inicioX + distancia
                fimY = inicioY
            }

            else -> {

                executando = false

                Toast.makeText(
                    applicationContext,
                    "DIREÇÃO INVÁLIDA",
                    Toast.LENGTH_LONG
                ).show()

                return
            }
        }

        val path = Path()

        path.moveTo(
            inicioX,
            inicioY
        )

        path.lineTo(
            fimX,
            fimY
        )

        val gesto = GestureDescription.Builder()
            .addStroke(
                GestureDescription.StrokeDescription(
                    path,
                    0,
                    duracao
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
                        "GESTO CONCLUÍDO: $direcao",
                        Toast.LENGTH_LONG
                    ).show()
                }

                override fun onCancelled(
                    gestureDescription: GestureDescription?
                ) {

                    executando = false

                    Toast.makeText(
                        applicationContext,
                        "GESTO CANCELADO: $direcao",
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
