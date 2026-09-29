package com.flateonbot

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.graphics.Path
import android.view.accessibility.AccessibilityEvent
import android.widget.Toast
import android.os.Handler
import android.os.Looper

class FlateonAccessibilityService : AccessibilityService() {

    companion object {
        var instancia: FlateonAccessibilityService? = null
            private set
    }

    private val handler = Handler(Looper.getMainLooper())

    private var executando = false

    /*
     * POSIÇÃO DO JOYSTICK IDENTIFICADA NA IMAGEM
     *
     * Tela: 1600 x 720
     * Centro aproximado do joystick:
     *
     * X = 160
     * Y = 565
     */
    private val joystickX = 160f
    private val joystickY = 565f

    override fun onServiceConnected() {
        super.onServiceConnected()

        instancia = this
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        // Nenhuma ação automática aqui.
    }

    fun executarRota(rota: List<MovimentoRota>) {

        if (executando) {

            Toast.makeText(
                applicationContext,
                "Rota já está executando",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        if (rota.isEmpty()) {

            Toast.makeText(
                applicationContext,
                "Rota vazia",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        executando = true

        Toast.makeText(
            applicationContext,
            "Joystick encontrado",
            Toast.LENGTH_SHORT
        ).show()

        executarMovimento(rota, 0)
    }

    private fun executarMovimento(
        rota: List<MovimentoRota>,
        indice: Int
    ) {

        if (!executando) {
            return
        }

        if (indice >= rota.size) {

            executando = false

            Toast.makeText(
                applicationContext,
                "Rota finalizada",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        val movimento = rota[indice]

        Toast.makeText(
            applicationContext,
            "Movimento: ${movimento.direcao}",
            Toast.LENGTH_SHORT
        ).show()

        executarDirecao(
            movimento.direcao,
            movimento.duracao
        )

        handler.postDelayed(
            {
                executarMovimento(
                    rota,
                    indice + 1
                )
            },
            movimento.duracao + 100L
        )
    }

    private fun executarDirecao(
        direcao: String,
        duracao: Long
    ) {

        val inicioX = joystickX
        val inicioY = joystickY

        /*
         * Distância que o dedo virtual
         * vai deslocar o joystick.
         */
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

        dispatchGesture(
            gesto,
            null,
            null
        )
    }

    fun pararRota() {

        executando = false

        handler.removeCallbacksAndMessages(null)

        Toast.makeText(
            applicationContext,
            "Rota parada",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onInterrupt() {

        pararRota()

        instancia = null
    }

    override fun onDestroy() {

        handler.removeCallbacksAndMessages(null)

        executando = false

        instancia = null

        super.onDestroy()
    }
}
