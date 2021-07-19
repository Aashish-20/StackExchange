package com.example.qa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        splash()
    }

    private fun splash(){
        Handler().postDelayed(
            {
                startActivity(Intent(this, SearchActivity::class.java))
            },
            2000)
    }
}