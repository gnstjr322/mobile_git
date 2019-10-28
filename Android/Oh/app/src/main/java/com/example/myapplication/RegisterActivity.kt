package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.app.Activity
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import java.io.IOException
import java.util.ArrayList
import java.util.concurrent.TimeUnit


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


            var userID = et_id?.text.toString()
            var userPass = et_pass?.text.toString()

            //HttpAsyncTask(userID,userPass).execute("http://172.30.1.32:8080")
            Toast.makeText(applicationContext, "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show()
            val intent = Intent(this@RegisterActivity, Main::class.java)
            intent.putExtra("userID", userID)
            intent.putExtra("userPass", userPass)
            startActivity(intent)

        }
    }
/*
    private inner class HttpAsyncTask(var userID: String = et_id?.text.toString(), var userPass: String = et_pass?.text.toString()) : AsyncTask<String, Void, List<Name>>() {
        //private val TAG = HttpAsyncTask::class.java.simpleName
        //val intent = intent


        // OkHttp 클라이언트
        internal var client = OkHttpClient.Builder()
                .connectTimeout(600, TimeUnit.SECONDS)
                .writeTimeout(600, TimeUnit.SECONDS)
                .readTimeout(600, TimeUnit.SECONDS)
                .build()

        internal var formBody: RequestBody = FormBody.Builder()
                .add("id", userID)
                .add("pw", userPass)
                .build()


        override fun doInBackground(vararg params: String): List<Name>? {

            var nameList: List<Name> = ArrayList()
            val strUrl = params[0]
            try {
                //notify2()
                //Response response2 = client.newCall(request2).execute();
                // 요청

                val request = Request.Builder()
                        .url(strUrl)
                        .post(formBody)
                        .build()

                // 응답
                val response = client.newCall(request).execute()
                val gson = Gson()
                val listType = object : TypeToken<ArrayList<Name>>() {

                }.type
                nameList = gson.fromJson<List<Name>>(response.body!!.string(), listType)
                //Log.d(TAG, "onCreate: " + weatherList.toString());
                // notify2()
            } catch (e: IOException) {
                e.printStackTrace()
                Toast.makeText(applicationContext, "로그인에 실패하였습니다.", Toast.LENGTH_SHORT).show()
            }

            Log.d("여기가 먼저냐", nameList.toString())//nameList의 형식은 List<Name>

            return nameList

        }

        override fun onPostExecute(nameList: List<Name>?) {
            super.onPostExecute(nameList)

            if(nameList != null){
                Toast.makeText(applicationContext, "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@RegisterActivity, Main::class.java)
                intent.putExtra("userID", userID)
                intent.putExtra("userPass", userPass)
                startActivity(intent)
            } else{
                Toast.makeText(applicationContext, "로그인에 실패하였습니다.", Toast.LENGTH_SHORT).show()
            }


        }
    }

 */

}

