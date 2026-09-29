package com.flateonbot

import android.accessibilityservice.AccessibilityService
import android.os.Handler
import android.os.Looper
import android.view.accessibility.AccessibilityEvent
import android.widget.Toast

class FlateonAccessibilityService : AccessibilityService() {

    companion object {
        var instancia: FlateonAccessibilityService? = null
            private set
    }

    private val handler = Handler(Looper.getMainLooper())

    private var executando = false

    override fun onServiceConnected() {
        super.onServiceConnected()

        instancia = this
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        // Nenhuma ação automática ainda.
    }

    fun executarRota(rota: List<MovimentoRota>) {

        if (executando) {
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
            movimento.direcao,
            Toast.LENGTH_SHORT
        ).show()

        handler.postDelayed(
            {
                executarMovimento(
                    rota,
                    indice + 1
                )
            },
            movimento.duracao
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
