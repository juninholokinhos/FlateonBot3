package com.flateonbot

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : Activity() {

    private lateinit var status: TextView
    private lateinit var listaMovimentos: TextView

    private val movimentos = ArrayList<MovimentoRota>()
    private val rotasSalvas = ArrayList<ArrayList<MovimentoRota>>()

    private var gravando = false

    private val nomeArquivo = "rotas.json"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        carregarRotas()

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
        titulo.setPadding(0, 0, 0, 30)

        val criarRota = Button(this)
        criarRota.text = "CRIAR ROTA"

        val minhasRotas = Button(this)
        minhasRotas.text = "MINHAS ROTAS"

        criarRota.setOnClickListener {
            mostrarTelaCriarRota()
        }

        minhasRotas.setOnClickListener {
            mostrarMinhasRotas()
        }

        layout.addView(titulo)
        layout.addView(criarRota)
        layout.addView(minhasRotas)

        setContentView(layout)
    }

    private fun mostrarTelaCriarRota() {

        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.gravity = Gravity.CENTER
        layout.setPadding(30, 30, 30, 30)

        val titulo = TextView(this)
        titulo.text = "CRIAR ROTA"
        titulo.textSize = 26f
        titulo.setTextColor(Color.BLACK)
        titulo.gravity = Gravity.CENTER

        status = TextView(this)
        status.text = "Pronto para gravar"
        status.textSize = 18f
        status.gravity = Gravity.CENTER
        status.setPadding(0, 20, 0, 20)

        listaMovimentos = TextView(this)
        listaMovimentos.text = "Nenhum movimento registrado"
        listaMovimentos.textSize = 16f
        listaMovimentos.gravity = Gravity.CENTER
        listaMovimentos.setPadding(0, 10, 0, 20)

        val cima = Button(this)
        cima.text = "⬆ CIMA"

        val baixo = Button(this)
        baixo.text = "⬇ BAIXO"

        val esquerda = Button(this)
        esquerda.text = "⬅ ESQUERDA"

        val direita = Button(this)
        direita.text = "➡ DIREITA"

        val gravar = Button(this)
        gravar.text = "🔴 GRAVAR"

        val parar = Button(this)
        parar.text = "⏹ PARAR"

        val salvar = Button(this)
        salvar.text = "💾 SALVAR ROTA"

        val limpar = Button(this)
        limpar.text = "🗑 LIMPAR"

        val voltar = Button(this)
        voltar.text = "VOLTAR"

        gravar.setOnClickListener {
            gravando = true
            movimentos.clear()
            atualizarLista()
            status.text = "🔴 GRAVANDO"
        }

        parar.setOnClickListener {
            gravando = false
            status.text = "⏹ GRAVAÇÃO PARADA"
        }

        cima.setOnClickListener {
            adicionarMovimento("CIMA")
        }

        baixo.setOnClickListener {
            adicionarMovimento("BAIXO")
        }

        esquerda.setOnClickListener {
            adicionarMovimento("ESQUERDA")
        }

        direita.setOnClickListener {
            adicionarMovimento("DIREITA")
        }

        salvar.setOnClickListener {

            if (movimentos.isEmpty()) {
                status.text = "Adicione movimentos antes de salvar"
                return@setOnClickListener
            }

            val novaRota = ArrayList<MovimentoRota>()

            movimentos.forEach {
                novaRota.add(
                    MovimentoRota(
                        direcao = it.direcao,
                        duracao = it.duracao
                    )
                )
            }

            rotasSalvas.add(novaRota)

            salvarRotas()

            gravando = false

            status.text = "💾 ROTA SALVA!\n${novaRota.size} movimentos"
        }

        limpar.setOnClickListener {
            movimentos.clear()
            gravando = false
            atualizarLista()
            status.text = "Rota limpa"
        }

        voltar.setOnClickListener {
            mostrarTelaPrincipal()
        }

        layout.addView(titulo)
        layout.addView(status)
        layout.addView(listaMovimentos)
        layout.addView(cima)
        layout.addView(baixo)
        layout.addView(esquerda)
        layout.addView(direita)
        layout.addView(gravar)
        layout.addView(parar)
        layout.addView(salvar)
        layout.addView(limpar)
        layout.addView(voltar)

        setContentView(layout)
    }

    private fun adicionarMovimento(direcao: String) {

        if (!gravando) {
            status.text = "Pressione GRAVAR primeiro"
            return
        }

        movimentos.add(
            MovimentoRota(
                direcao = direcao,
                duracao = 1000
            )
        )

        atualizarLista()
    }

    private fun atualizarLista() {

        if (movimentos.isEmpty()) {
            listaMovimentos.text = "Nenhum movimento registrado"
            return
        }

        val texto = StringBuilder()

        movimentos.forEachIndexed { index, movimento ->

            texto.append("${index + 1}. ")
            texto.append(movimento.direcao)
            texto.append(" - ")
            texto.append(movimento.duracao)
            texto.append(" ms\n")
        }

        listaMovimentos.text = texto.toString()
    }

    private fun mostrarMinhasRotas() {

        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.gravity = Gravity.CENTER
        layout.setPadding(30, 30, 30, 30)

        val titulo = TextView(this)
        titulo.text = "MINHAS ROTAS"
        titulo.textSize = 26f
        titulo.setTextColor(Color.BLACK)
        titulo.gravity = Gravity.CENTER
        titulo.setPadding(0, 0, 0, 30)

        if (rotasSalvas.isEmpty()) {

            val vazio = TextView(this)
            vazio.text = "Nenhuma rota salva"
            vazio.textSize = 18f
            vazio.gravity = Gravity.CENTER

            layout.addView(titulo)
            layout.addView(vazio)

        } else {

            layout.addView(titulo)

            rotasSalvas.forEachIndexed { index, rota ->

                val botao = Button(this)
                botao.text = "ROTA ${index + 1} - ${rota.size} movimentos"

                botao.setOnClickListener {
                    mostrarDetalhesRota(index)
                }

                layout.addView(botao)
            }
        }

        val voltar = Button(this)
        voltar.text = "VOLTAR"

        voltar.setOnClickListener {
            mostrarTelaPrincipal()
        }

        layout.addView(voltar)

        setContentView(layout)
    }

    private fun mostrarDetalhesRota(numeroRota: Int) {

        val rota = rotasSalvas[numeroRota]

        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.gravity = Gravity.CENTER
        layout.setPadding(30, 30, 30, 30)

        val titulo = TextView(this)
        titulo.text = "ROTA ${numeroRota + 1}"
        titulo.textSize = 26f
        titulo.setTextColor(Color.BLACK)
        titulo.gravity = Gravity.CENTER

        val lista = TextView(this)
        lista.textSize = 17f
        lista.setPadding(0, 30, 0, 30)

        val texto = StringBuilder()

        rota.forEachIndexed { index, movimento ->

            texto.append("${index + 1}. ")
            texto.append(movimento.direcao)
            texto.append(" - ")
            texto.append(movimento.duracao)
            texto.append(" ms\n")
        }

        lista.text = texto.toString()

        val voltar = Button(this)
        voltar.text = "VOLTAR"

        voltar.setOnClickListener {
            mostrarMinhasRotas()
        }

        layout.addView(titulo)
        layout.addView(lista)
        layout.addView(voltar)

        setContentView(layout)
    }

    private fun salvarRotas() {

        val jsonRotas = JSONArray()

        rotasSalvas.forEach { rota ->

            val jsonRota = JSONArray()

            rota.forEach { movimento ->

                val objeto = JSONObject()

                objeto.put("direcao", movimento.direcao)
                objeto.put("duracao", movimento.duracao)

                jsonRota.put(objeto)
            }

            jsonRotas.put(jsonRota)
        }

        openFileOutput(nomeArquivo, Context.MODE_PRIVATE).use {
            it.write(jsonRotas.toString().toByteArray())
        }
    }

    private fun carregarRotas() {

        try {

            val arquivo = openFileInput(nomeArquivo)

            val texto = arquivo.bufferedReader().use {
                it.readText()
            }

            arquivo.close()

            val jsonRotas = JSONArray(texto)

            rotasSalvas.clear()

            for (i in 0 until jsonRotas.length()) {

                val jsonRota = jsonRotas.getJSONArray(i)

                val rota = ArrayList<MovimentoRota>()

                for (j in 0 until jsonRota.length()) {

                    val objeto = jsonRota.getJSONObject(j)

                    rota.add(
                        MovimentoRota(
                            direcao = objeto.getString("direcao"),
                            duracao = objeto.getLong("duracao")
                        )
                    )
                }

                rotasSalvas.add(rota)
            }

        } catch (_: Exception) {
            rotasSalvas.clear()
        }
    }
}
