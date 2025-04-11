package com.example.offlinegames

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager



class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)


        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, login::class.java))
            finish()
        }, 1000)


    }
}

