package com.flateonbot

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : Activity() {

    private lateinit var status: TextView
    private var gravando = false
    private var movimentos = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mostrarTelaPrincipal()
    }

    private fun mostrarTelaPrincipal() {

        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.gravity = Gravity.CENTER
        layout.setPadding(40, 40, 40, 40)

        val titulo = TextView(this)
        titulo.text = "FLATEON BOT"
        titulo.textSize = 28f
        titulo.setTextColor(Color.BLACK)
        titulo.gravity = Gravity.CENTER

        val criarRota = Button(this)
        criarRota.text = "CRIAR ROTA"

        val minhasRotas = Button(this)
        minhasRotas.text = "MINHAS ROTAS"

        val iniciar = Button(this)
        iniciar.text = "▶ INICIAR"

        val parar = Button(this)
        parar.text = "■ PARAR"

        criarRota.setOnClickListener {
            mostrarTelaCriarRota()
        }

        layout.addView(titulo)
        layout.addView(criarRota)
        layout.addView(minhasRotas)
        layout.addView(iniciar)
        layout.addView(parar)

        setContentView(layout)
    }

    private fun mostrarTelaCriarRota() {

        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.gravity = Gravity.CENTER
        layout.setPadding(40, 40, 40, 40)

        val titulo = TextView(this)
        titulo.text = "CRIAR ROTA"
        titulo.textSize = 26f
        titulo.setTextColor(Color.BLACK)
        titulo.gravity = Gravity.CENTER

        status = TextView(this)
        status.text = "Pronto para gravar"
        status.textSize = 18f
        status.gravity = Gravity.CENTER
        status.setPadding(0, 30, 0, 30)

        val gravar = Button(this)
        gravar.text = "🔴 GRAVAR ROTA"

        val pararGravacao = Button(this)
        pararGravacao.text = "⏹ PARAR GRAVAÇÃO"

        val salvar = Button(this)
        salvar.text = "💾 SALVAR ROTA"

        val voltar = Button(this)
        voltar.text = "VOLTAR"

        gravar.setOnClickListener {
            gravando = true
            movimentos = 0
            status.text = "🔴 GRAVANDO...\nMovimentos: $movimentos"
        }

        pararGravacao.setOnClickListener {
            gravando = false
            status.text = "⏹ Gravação parada\nMovimentos: $movimentos"
        }

        salvar.setOnClickListener {
            gravando = false
            status.text = "💾 Rota salva!\nMovimentos: $movimentos"
        }

        voltar.setOnClickListener {
            mostrarTelaPrincipal()
        }

        layout.addView(titulo)
        layout.addView(status)
        layout.addView(gravar)
        layout.addView(pararGravacao)
        layout.addView(salvar)
        layout.addView(voltar)

        setContentView(layout)
    }
}
