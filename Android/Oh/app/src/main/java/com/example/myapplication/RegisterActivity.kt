package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import java.io.IOException
import java.lang.Exception
import java.util.ArrayList
import java.util.concurrent.TimeUnit



class RegisterActivity : AppCompatActivity() { // 여기서 서버에 아이디 비밀번호를 넘겨야댐

    private var et_id: EditText? = null
    private var et_pass: EditText? = null
    private var btn_register: Button? = null
    private var mWeatherListView: ListView? = null


    var nameList2: List<Name> = ArrayList() // 넘겨줄걸 여기다 저장
    var nameList3: List<Cal> = ArrayList() // 넘겨줄걸 여기다 저장


    override fun onCreate(savedInstanceState: Bundle?) { // 액티비티 처음 실행되는 생명주기
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val dbHelper = DBHelper(applicationContext, "LOGIN.db", null, 1)
        val result = findViewById<View>(R.id.result) as TextView

        et_id = findViewById(R.id.et_id)
        et_pass = findViewById(R.id.et_pass)
        btn_register = findViewById(R.id.btn_register)


        //로그인 버튼 클릭시 수행
        btn_register!!.setOnClickListener {
            //Edit Text에 현재 입력되어있는값을 가져온다.

            var userID = et_id?.text.toString()
            var userPass = et_pass?.text.toString()
            mWeatherListView = findViewById(R.id.list_view) as? ListView

            result.text = dbHelper.result
            println("$result")
            HttpAsyncTask(userID,userPass).execute("http:/172.30.1.7:8080")

        }
    }


    private inner class HttpAsyncTask(var userID: String, var userPass: String) : AsyncTask<String, Void, List<Name>>() {
        //private val TAG = HttpAsyncTask::class.java.simpleName
        //val intent = intent



        //progres dialog
        val dialog = ProgressDialog(this@RegisterActivity)

        override fun onPreExecute() {
            super.onPreExecute()
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
            dialog.setMessage("로딩중입니다...")
            dialog.setCanceledOnTouchOutside(false)
            dialog.setCancelable(false)
            dialog.show()
        }

        // OkHttp 클라이언트
        internal var client = OkHttpClient.Builder()
                .connectTimeout(600, TimeUnit.SECONDS)
                .writeTimeout(600, TimeUnit.SECONDS)
                .readTimeout(600, TimeUnit.SECONDS)
                .build()

        internal var formBody: RequestBody = FormBody.Builder()
                .add("id", userID)
                .add("pw", userPass)
                .add("num", "0")
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
                Toast.makeText(applicationContext, "로그인에 실패하였습니다.", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }

            //Log.d(TAG, nameList.toString())//nameList의 형식은 List<Name>

            nameList2 = nameList
            return nameList
        }


        override fun onPostExecute(nameList: List<Name>?) {
            super.onPostExecute(nameList)
            HttpAsyncTask2(userID, userPass).execute("http:/172.30.1.7:8080")
            Thread(Runnable {
                try{
                    if(dialog != null && dialog.isShowing){
                        dialog.dismiss()
                    }
                }catch (e: Exception){
                    Toast.makeText(applicationContext, "로그인에 실패하였습니다.", Toast.LENGTH_SHORT).show()
                }
                dialog.dismiss()
            }).start()
        }
    }


    private inner class HttpAsyncTask2(var userID: String, var userPass: String) : AsyncTask<String, Void, List<Cal>>() {
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
                .add("num", "1")
                .build()

        //progres dialog
        val dialog = ProgressDialog(this@RegisterActivity)

        override fun onPreExecute() {
            super.onPreExecute()
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
            dialog.setMessage("로딩중입니다...")
            dialog.setCanceledOnTouchOutside(false)
            dialog.setCancelable(false)
            dialog.show()
        }

        override fun doInBackground(vararg params: String): List<Cal>? {

            var nameList: List<Cal> = ArrayList()
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
                val listType = object : TypeToken<ArrayList<Cal>>() {

                }.type
                nameList = gson.fromJson<List<Cal>>(response.body!!.string(), listType)
                //Log.d(TAG, "onCreate: " + weatherList.toString());
                // notify2()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            //Log.d("프레그먼트2", nameList.toString())//nameList의 형식은 List<Name>

            nameList3 = nameList
            return nameList
        }


        override fun onPostExecute(nameList: List<Cal>?) {
            super.onPostExecute(nameList)

            Thread(Runnable {
                try{
                    if(dialog != null && dialog.isShowing){
                        dialog.dismiss()
                    }
                }catch (e: Exception){

                }
                dialog.dismiss()
            }).start()


            //progressdialog 종료 코드 -> 위에 백그라운드 함수에서 nameList를 받아오기 전까지는
            //onPreExecute로 pregressDialog가 계속 돌고 있을 것이고 서버에서 lms받아오면
            //그 뒤로 5초 뒤에 보여지도록 설정하였다.
            Thread(Runnable {
                try{
                    //Thread.sleep(5000)
                    if(dialog != null && dialog.isShowing){
                        dialog.dismiss()
                    }
                }catch (e: Exception) {

                }
                dialog.dismiss()
            }).start()

            //progressdialog 코드 끝


            if(nameList == null)
            {
                Toast.makeText(applicationContext, "로그인 실패", Toast.LENGTH_SHORT).show()
            }

            Log.d("확1", " $nameList2")
            Log.d("확2", "$nameList3")//nameList의 형식은 List<Name>

            Toast.makeText(applicationContext, "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show()
            val intent = Intent(this@RegisterActivity, Main::class.java)
            intent.putExtra("nameList2", nameList2 as ArrayList<List<Name>>)
            intent.putExtra("nameList3", nameList3 as ArrayList<List<Cal>>)
            intent.putExtra("userID",userID)
            intent.putExtra("userPass",userPass)
            startActivity(intent)


            }
        }

    }








