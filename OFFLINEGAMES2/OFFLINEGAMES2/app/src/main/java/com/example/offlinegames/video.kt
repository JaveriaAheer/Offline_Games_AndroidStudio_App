package com.example.offlinegames

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.MediaController
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import android.widget.RelativeLayout


class video : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.video)

        val back=findViewById<Button>(R.id.button)
        back.setOnClickListener{
            val intent= Intent(this, startscreen::class.java)
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


