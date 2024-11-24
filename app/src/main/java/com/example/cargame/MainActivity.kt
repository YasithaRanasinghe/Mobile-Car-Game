package com.example.cargame

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), GameTask {
    private lateinit var rootLayout : LinearLayout
    private lateinit var startBtn :Button
    private lateinit var mGameView: GameView
    private lateinit var score: TextView
    private lateinit var highScore: TextView

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startBtn = findViewById(R.id.startBtn)
        rootLayout = findViewById(R.id.rootLayout)
        score = findViewById(R.id.score)
        mGameView = GameView(this,this)
        highScore = findViewById(R.id.highscore)

        val allTimeHighScore = getAllTimeHighScore()
        highScore.text = "All-Time HighScore : $allTimeHighScore"

        startBtn.setOnClickListener{
            mGameView.setBackgroundResource(R.drawable.road_0)
            rootLayout.addView(mGameView)
            startBtn.visibility = View.GONE
            score.visibility = View.GONE
        }
    }

    @SuppressLint("SetTextI18n")
    override fun closeGame(mScore: Int) {
        score.text = "Score : $mScore"
        rootLayout.removeView(mGameView)
        startBtn.visibility = View.VISIBLE
        score.visibility = View.VISIBLE
        if (mScore > getAllTimeHighScore()){
            setAllTimeHighScore(mScore)
            highScore.text = "HighScore : $mScore"
        }
        mGameView.restartGame()
    }

    private fun getAllTimeHighScore() : Int{
        val sharedPref = getSharedPreferences("game_prefs",Context.MODE_PRIVATE)
        return sharedPref.getInt("allTimeHighScore",0)
    }

    private fun setAllTimeHighScore(score: Int){
        val sharedPref = getSharedPreferences("game_prefs",Context.MODE_PRIVATE)
        with (sharedPref.edit()){
            putInt("allTimeHighScore",score)
            apply()
        }
    }

    private fun startGame(){
        mGameView.restartGame() // Restart the game
        mGameView.setBackgroundResource(R.drawable.road_0)
        rootLayout.addView(mGameView)
        startBtn.visibility = View.GONE
        score.visibility = View.GONE
    }
}