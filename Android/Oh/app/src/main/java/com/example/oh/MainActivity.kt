package com.example.oh

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.nkzawa.emitter.Emitter
import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var raccount : String = "2015125054"
        var rpassword : String = "jooboo100@"
        var account2 = findViewById(R.id.account) as EditText
        var password2 = findViewById(R.id.password) as EditText
        btnSign.setOnClickListener({
            var account1 :String = account2.text.toString()
            var password1 :String = password2.text.toString()
            if(raccount == account1 && rpassword == password1){
                var intent = Intent(this,Alarm::class.java)
                startActivity(intent)
            }else{
                Toast.makeText(this, "Wrong Account!", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
