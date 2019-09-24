package com.example.oh

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class Splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try
        {
            Thread.sleep(3000)
            var intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        catch(e: Exception){
            return;
        }

    }
}