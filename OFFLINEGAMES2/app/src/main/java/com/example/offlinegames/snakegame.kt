package com.example.offlinegames

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.VideoView
import java.util.Random
import kotlin.math.pow
import kotlin.math.sqrt

class snakegame : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_snakegame)

        val videoView3: VideoView = findViewById(R.id.videoViewsnake)

        val videoPath = "android.resource://" + packageName + "/" + R.raw.purplebg
        val uri: Uri = Uri.parse(videoPath)

        videoView3.setMediaController(null)
        videoView3.setVideoURI(uri)

        videoView3.setOnPreparedListener { mp ->
            mp.isLooping = true // Enable looping
            mp.start()
        }

        videoView3.setOnCompletionListener { mp ->
            mp.start()
        }


        val board = findViewById<RelativeLayout>(R.id.board)
        val border = findViewById<RelativeLayout>(R.id.relativeLayout)
        val lilu = findViewById<LinearLayout>(R.id.lilu)
        val upButton = findViewById<Button>(R.id.up)
        val downButton = findViewById<Button>(R.id.down)
        val leftButton = findViewById<Button>(R.id.left)
        val rightButton = findViewById<Button>(R.id.right)
        val pauseButton = findViewById<Button>(R.id.pause)
        val newgame = findViewById<Button>(R.id.new_game)
        val resume = findViewById<Button>(R.id.resume)
        val playagain = findViewById<Button>(R.id.playagain)
        val score = findViewById<Button>(R.id.score)
        val score2 = findViewById<Button>(R.id.score2)
        val meat = ImageView(this)
        val snake = ImageView(this)
        val snakeSegments =
            mutableListOf(snake)
        val handler = Handler()
        var delayMillis = 30L
        var currentDirection = "right"
        var scorex = 0



        board.visibility = View.INVISIBLE
        playagain.visibility = View.INVISIBLE
        score.visibility = View.INVISIBLE
        score2.visibility = View.INVISIBLE

        newgame.setOnClickListener {

            board.visibility = View.VISIBLE
            newgame.visibility = View.INVISIBLE
            resume.visibility = View.INVISIBLE
            score2.visibility = View.VISIBLE


            snake.setImageResource(R.drawable.snake)
            snake.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            board.addView(snake)
            snakeSegments.add(snake)

            var snakeX = snake.x
            var snakeY = snake.y

            meat.setImageResource(R.drawable.baseline_wifi_1_bar_24)
            meat.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            board.addView(meat)

            val random = Random()
            val randomX = random.nextInt(801) - 400
            val randomY = random.nextInt(801) - 400

            meat.x = randomX.toFloat()
            meat.y = randomY.toFloat()


            fun checkFoodCollision() {
                val distanceThreshold = 50

                val distance = sqrt((snake.x - meat.x).pow(2) + (snake.y - meat.y).pow(2))

                if (distance < distanceThreshold) {
                    val newSnake =
                        ImageView(this)
                    newSnake.setImageResource(R.drawable.snake)
                    newSnake.layoutParams = ViewGroup.LayoutParams(  // to display snake of same size
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                    board.addView(newSnake)

                    snakeSegments.add(newSnake)

                    val randomX = random.nextInt(801) - -100
                    val randomY = random.nextInt(801) - -100
                    meat.x = randomX.toFloat()
                    meat.y = randomY.toFloat()
                    delayMillis-- //reduce delay value by 1 (increasing speed of game)
                    scorex++
                    score2.text =   "score : " + scorex.toString()

                }
            }

            val runnable = object : Runnable {
                override fun run() {
                for (i in snakeSegments.size - 1 downTo 1) {
                        snakeSegments[i].x = snakeSegments[i - 1].x
                        snakeSegments[i].y = snakeSegments[i - 1].y
                    }
                when (currentDirection) {
                "up" -> {
                    snakeY -= 10
                    if (snakeY < -490) {
                        snakeY = -490f
                        border.setBackgroundColor(getResources().getColor(R.color.red))
                        playagain.visibility = View.VISIBLE
                        currentDirection = "pause"
                        lilu.visibility = View.INVISIBLE

                        score.text =   "your score is  " + scorex.toString()
                        score.visibility = View.VISIBLE
                        score2.visibility = View.INVISIBLE

                    }
                    snake.translationY = snakeY
                }
            "down" -> {
                snakeY += 10
                val maxY =
                    board.height / 2 - snake.height + 30
                if (snakeY > maxY) {
                    snakeY = maxY.toFloat()
                    border.setBackgroundColor(getResources().getColor(R.color.red))
                    playagain.visibility = View.VISIBLE
                    currentDirection = "pause"
                    lilu.visibility = View.INVISIBLE

                    score.text =  "your score is  " + scorex.toString()
                    score.visibility = View.VISIBLE
                    score2.visibility = View.INVISIBLE
                }
                snake.translationY = snakeY
            }
                        "left" -> {
                            snakeX -= 10
                            if (snakeX < -490) {
                                snakeX = -490f
                                border.setBackgroundColor(getResources().getColor(R.color.red))
                                playagain.visibility = View.VISIBLE
                                currentDirection = "pause"
                                lilu.visibility = View.INVISIBLE
                                score.text =   "your score is  " + scorex.toString()
                                score.visibility = View.VISIBLE
                                score2.visibility = View.INVISIBLE

                            }
                            snake.translationX = snakeX
                        }
                "right" -> {
                    snakeX += 10
                    val maxX =
                        board.height / 2 - snake.height + 30
                    if (snakeX > maxX) {
                        snakeX = maxX.toFloat()
                        border.setBackgroundColor(getResources().getColor(R.color.red))
                        playagain.visibility = View.VISIBLE
                        currentDirection = "pause"
                        lilu.visibility = View.INVISIBLE

                        score.text =   "your score is  " + scorex.toString() // Update delay text view
                        score.visibility = View.VISIBLE
                        score2.visibility = View.INVISIBLE

                    }
                    snake.translationX = snakeX
                }
                        "pause" -> {
                            snakeX += 0
                            snake.translationX = snakeX
                        }
                    }
                    checkFoodCollision()
                    handler.postDelayed(this, delayMillis)
                }
            }
            handler.postDelayed(runnable, delayMillis)

            upButton.setOnClickListener {
                currentDirection = "up"
            }
            downButton.setOnClickListener {
                currentDirection = "down"
            }
            leftButton.setOnClickListener {
                currentDirection = "left"
            }
            rightButton.setOnClickListener {
                currentDirection = "right"
            }
            pauseButton.setOnClickListener {
                currentDirection = "pause"
                board.visibility = View.INVISIBLE
                newgame.visibility = View.VISIBLE
                resume.visibility = View.VISIBLE

            }
            resume.setOnClickListener {
                currentDirection = "right"
                board.visibility = View.VISIBLE
                newgame.visibility = View.INVISIBLE
                resume.visibility = View.INVISIBLE

            }
            playagain.setOnClickListener {

                recreate()
            }

        }

    }
    override fun onDestroy() {
        val videoView3: VideoView = findViewById(R.id.videoViewsnake)
        videoView3.stopPlayback()
        super.onDestroy()
    }

}