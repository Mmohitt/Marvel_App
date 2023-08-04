package com.example.marvel_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class Splash_Activity : AppCompatActivity() {
    private val SPLASH_DELAY = 2000 // 3 seconds (adjust this time as needed)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Finish the Splash Activity so that the user cannot go back to it using the back button
        }, SPLASH_DELAY.toLong())
    }
}