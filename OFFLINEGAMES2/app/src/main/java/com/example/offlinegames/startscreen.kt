package com.example.offlinegames

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity


class startscreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.start)

        val tictac=findViewById<Button>(R.id.button2)
        tictac.setOnClickListener{
            val intent= Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        val snakeg=findViewById<Button>(R.id.button3)
        snakeg.setOnClickListener{
            val intent= Intent(this, snakegame::class.java)
            startActivity(intent)

        }


        val videoView: VideoView = findViewById(R.id.videoView)

        val videoPath = "android.resource://" + packageName + "/" + R.raw.purplebg
        val uri: Uri = Uri.parse(videoPath)

        videoView.setMediaController(null)
        videoView.setVideoURI(uri)

        videoView.setOnPreparedListener { mp ->
            mp.isLooping = true // Enable looping
            mp.start()
        }

        videoView.setOnCompletionListener { mp ->
            mp.start()
        }

    }

    override fun onDestroy() {
        val videoView: VideoView = findViewById(R.id.videoView)
        videoView.stopPlayback()
        super.onDestroy()
    }

    }


