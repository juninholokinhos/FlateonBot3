package com.flateonbot

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

        layout.addView(titulo)
        layout.addView(criarRota)
        layout.addView(minhasRotas)
        layout.addView(iniciar)
        layout.addView(parar)

        setContentView(layout)
    }
}
