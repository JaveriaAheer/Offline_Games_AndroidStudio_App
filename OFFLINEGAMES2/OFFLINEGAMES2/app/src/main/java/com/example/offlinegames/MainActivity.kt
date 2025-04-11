package com.example.offlinegames

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.VideoView
import androidx.appcompat.app.AlertDialog
import com.example.offlinegames.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    enum class Turn {
        Circle,
        CROSS
    }

    private var firstTurn = Turn.CROSS
    private var currentTurn = Turn.CROSS

    private var crossesScore = 0
    private var circleScore = 0

    private var boardList = mutableListOf<Button>()

    private lateinit var binding: ActivityMainBinding
    private lateinit var videoView2: VideoView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        videoView2 = findViewById(R.id.videoViewtictac)

        val videoPath = "android.resource://" + packageName + "/" + R.raw.purplebg
        val uri: Uri = Uri.parse(videoPath)

        videoView2.setMediaController(null)
        videoView2.setVideoURI(uri)

        videoView2.setOnPreparedListener { mp ->
            mp.isLooping = true
            mp.start()
        }

        videoView2.setOnCompletionListener { mp ->
            mp.start()
        }

        initBoar()
    }

    private fun initBoar() {
        boardList.add(binding.a1)
        boardList.add(binding.a2)
        boardList.add(binding.a3)
        boardList.add(binding.b1)
        boardList.add(binding.b2)
        boardList.add(binding.b3)
        boardList.add(binding.c1)
        boardList.add(binding.c2)
        boardList.add(binding.c3)
    }

    fun boardTapped(view: View) {
        if (view !is Button) {
            return
        }

        addToBoar(view)

        if (checkForVictory(Circle)) {
            circleScore++
            result("Circles Win!")
        }

        if (checkForVictory(CROSS)) {
            crossesScore++
            result("Crosses Win!")
        }

        if (fullBoard()) {
            result("Draw")
        }
    }

    private fun checkForVictory(s: String): Boolean {
        if (match(binding.a1, s) && match(binding.a2, s) && match(binding.a3, s)) {
            return true
        }
        if (match(binding.b1, s) && match(binding.b2, s) && match(binding.b3, s)) {
            return true
        }
        if (match(binding.c1, s) && match(binding.c2, s) && match(binding.c3, s)) {
            return true
        }
        if (match(binding.a1, s) && match(binding.b1, s) && match(binding.c1, s)) {
            return true
        }
        if (match(binding.a2, s) && match(binding.b2, s) && match(binding.c2, s)) {
            return true
        }
        if (match(binding.a3, s) && match(binding.b3, s) && match(binding.c3, s)) {
            return true
        }
        if (match(binding.a1, s) && match(binding.b2, s) && match(binding.c3, s)) {
            return true
        }
        if (match(binding.a3, s) && match(binding.b2, s) && match(binding.c1, s)) {
            return true
        }
        return false
    }

    private fun match(button: Button, symbol: String): Boolean = button.text == symbol

    private fun result(title: String) {
        val message = "\nCircle $circleScore\n\nCrosses $crossesScore"

        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("Reset") { _, _ ->
                resetBoard()
            }
            .setCancelable(false)
            .show()
    }

    private fun resetBoard() {
        for (button in boardList) {
            button.text = ""
        }

        if (firstTurn == Turn.Circle) {
            firstTurn = Turn.CROSS
        } else if (firstTurn == Turn.CROSS) {
            firstTurn = Turn.Circle
        }

        currentTurn = firstTurn

        setTurnLabel()
    }

    private fun fullBoard(): Boolean {
        for (button in boardList) {
            if (button.text == "") {
                return false
            }
        }

        return true
    }

    private fun addToBoar(button: Button) {
        if (button.text != "") {
            return
        }

        if (currentTurn == Turn.Circle) {
            button.text = Circle
            currentTurn = Turn.CROSS
        } else if (currentTurn == Turn.CROSS) {
            button.text = CROSS
            currentTurn = Turn.Circle
        }

        setTurnLabel()
    }

    private fun setTurnLabel() {
        var turnText = ""

        if (currentTurn == Turn.CROSS) {
            turnText = "Turn $CROSS"
        } else if (currentTurn == Turn.Circle) {
            turnText = "Turn $Circle"
        }

        binding.turnTV.text = turnText
    }

    companion object {
        const val Circle = "O"
        const val CROSS = "X"
    }
}
