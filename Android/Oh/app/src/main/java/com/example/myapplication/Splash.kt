package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity

class Splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try
        {
            Thread.sleep(3000)
            var intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
        catch(e: Exception){
            return;
        }

    }
}