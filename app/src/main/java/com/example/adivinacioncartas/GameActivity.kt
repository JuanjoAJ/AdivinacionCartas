package com.example.adivinacioncartas

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.OnClickListener
import android.view.View.VISIBLE
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import com.example.adivinacioncartas.databinding.ActivityGameBinding
import com.google.android.material.snackbar.Snackbar
import kotlin.random.Random

class GameActivity : AppCompatActivity(), OnClickListener {
    private lateinit var binding: ActivityGameBinding
    private var bundleRecuperado: Bundle? = null
    private var hasBegan = false
    private var score = 0
    private var numCurrent = 0
    private var numFuture = 0

    private val cards = arrayListOf(
        R.drawable.c2,
        R.drawable.c3,
        R.drawable.c4,
        R.drawable.c5,
        R.drawable.c6,
        R.drawable.c7,
        R.drawable.c8,
        R.drawable.c9,
        R.drawable.c10,
        R.drawable.c11,
        R.drawable.c12,
        R.drawable.c13,
        R.drawable.c1
    )
    private val cardsLand = arrayListOf(

        R.drawable.c2_land,
        R.drawable.c3_land,
        R.drawable.c4_land,
        R.drawable.c5_land,
        R.drawable.c6_land,
        R.drawable.c7_land,
        R.drawable.c8_land,
        R.drawable.c9_land,
        R.drawable.c10_land,
        R.drawable.c11_land,
        R.drawable.c12_land,
        R.drawable.c13_land,
        R.drawable.c1_land
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //SaveOnInstance
        hasBegan = savedInstanceState?.getBoolean("hasBegan") ?: false
        numCurrent = savedInstanceState?.getInt("numCurrent") ?: 0
        numFuture = savedInstanceState?.getInt("numFuture") ?: 0
        score = savedInstanceState?.getInt("score") ?: 0

        //Bundle-Intent
        bundleRecuperado = intent.extras?.getBundle("datos")
        val nombreUsuario = bundleRecuperado?.getString("nombre")!!
        binding.txtHello.text = String.format(getString(R.string.welcome), nombreUsuario)

       hideShowGame(hasBegan)

        //Botones
        binding.btnGo.setOnClickListener(this)
        binding.btnUp.setOnClickListener(this)
        binding.btnDown.setOnClickListener(this)
        binding.btnReturn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            binding.btnGo.id -> {
                hasBegan = true
                numCurrent = Random.nextInt(0, 14)
                hideShowGame(hasBegan)

            }

            binding.btnUp.id -> {
                upDownGame("up")
            }

            binding.btnDown.id -> {
                upDownGame("down")
            }

            binding.btnReturn.id -> {
                startActivity(Intent(applicationContext, MainActivity::class.java))
            }

        }
    }

    private fun hideShowGame(hasBegan: Boolean) {
        if (hasBegan) {
            binding.btnGo.visibility = GONE
            binding.txtHello.visibility = GONE
            binding.btnReturn.visibility = GONE
            binding.btnUp.visibility = VISIBLE
            binding.btnDown.visibility = VISIBLE
            if (resources.configuration.orientation==1) binding.main.background = getDrawable(cards.get(numCurrent))
            else binding.main.background = getDrawable(cardsLand.get(numCurrent))
        } else {
            binding.btnGo.visibility = VISIBLE
            binding.txtHello.visibility = VISIBLE
            binding.btnReturn.visibility = VISIBLE
            binding.btnUp.visibility = GONE
            binding.btnDown.visibility = GONE
            if (resources.configuration.orientation==1) binding.main.background = getDrawable(R.drawable.cf)
            else binding.main.background = getDrawable(R.drawable.cf_land)
        }
    }

    private fun snackbar(winLose: String) {
        when (winLose) {
            "win" -> return Snackbar.make(
                binding.root,
                String.format(getString(R.string.youWin), score),
                Snackbar.LENGTH_LONG
            ).setAction(getString(R.string.reboot)) {
                score = 0
                hasBegan = false
                hideShowGame(hasBegan)
            }.show()

            "lose" -> return Snackbar.make(
                binding.root,
                String.format(getString(R.string.youLose), score),
                Snackbar.LENGTH_LONG
            ).setAction(getString(R.string.reboot)) {
                score = 0
                hasBegan = false
                hideShowGame(hasBegan)
            }.show()

            "draw" -> return Snackbar.make(
                binding.root,
                getString(R.string.draw),
                Snackbar.LENGTH_LONG
            ).setAction(getString(R.string.reboot)) {
                score = 0
                hasBegan = false
                hideShowGame(hasBegan)
            }.show()
        }
    }

    private fun upDownGame(upOrDown: String) {
        val numFuture = Random.nextInt(0, 13)
        if (numFuture == numCurrent) {
            snackbar("draw")
        } else {
            when (upOrDown) {
                "up" -> {
                    if (numFuture > numCurrent) {
                        score++
                        snackbar("win")
                    } else {
                        score--
                        if (score < 0) score = 0
                        snackbar("lose")
                    }
                }

                "down" -> {
                    if (numFuture < numCurrent) {
                        score++
                        snackbar("win")
                    } else {
                        score--
                        if (score < 0) score = 0
                        snackbar("lose")
                    }
                }

            }
            numCurrent = numFuture

            if (resources.configuration.orientation==1) binding.main.background = getDrawable(cards.get(numCurrent))
            else binding.main.background = getDrawable(cardsLand.get(numCurrent))
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("hasBegan", hasBegan)
        outState.putInt("numCurrent", numCurrent)
        outState.putInt("numFuture", numFuture)
        outState.putInt("score", score)
    }
}





