package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment


//로그인 화면 참고: 홍드로이드

class RegisterActivity : AppCompatActivity() { // 여기서 서버에 아이디 비밀번호를 넘겨야댐

    private var et_id: EditText? = null
    private var et_pass: EditText? = null
    private var btn_register: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) { // 액티비티 처음 실행되는 생명주기
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        et_id = findViewById(R.id.et_id)
        et_pass = findViewById(R.id.et_pass)
        btn_register = findViewById(R.id.btn_register)

        //회원가입 버튼 클릭시 수행
        btn_register!!.setOnClickListener {
            //Edit Text에 현재 입력되어있는값을 가져온다.

            val userID = et_id?.text.toString()
            val userPass = et_pass?.text.toString()


            Toast.makeText(applicationContext, "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show()
            val intent = Intent(this@RegisterActivity, Main::class.java)
            intent.putExtra("userID", userID)
            intent.putExtra("userPass", userPass)
            startActivity(intent)


        }
    }

}

