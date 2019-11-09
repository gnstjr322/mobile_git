package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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
import org.json.JSONArray
import org.json.JSONException
import java.io.IOException
import java.lang.Exception
import java.lang.reflect.Array.set
import java.util.ArrayList
import java.util.concurrent.TimeUnit



class RegisterActivity : AppCompatActivity() { // 여기서 서버에 아이디 비밀번호를 넘겨야댐

    private var et_id: EditText? = null
    private var et_pass: EditText? = null
    private var btn_register: Button? = null
    private var mWeatherListView: ListView? = null


    var nameList2: List<Name> = ArrayList() // 넘겨줄걸 여기다 저장
    var nameList3: List<Cal> = ArrayList() // 넘겨줄걸 여기다 저장
    var nameList4: List<Subject> = ArrayList() // 넘겨줄걸 여기다 저장

    var autoId: String? = null
    var autoPwd: String? = null
    var auto_nameList2: List<Name> = ArrayList()
    var auto_nameList3: List<Cal> = ArrayList()
    var auto_nameList4: List<Subject> = ArrayList()


    fun <E> getList(key: String): List<E> { //전역변수를 받아오는 함수(자동로그인)
        val auto = getSharedPreferences("auto", Activity.MODE_PRIVATE)

        var plz: List<E> = ArrayList()
        val json = auto.getString(key, null)
        val valueList = ArrayList<E>()

        if (json != null) {
            try {
                val jsonArray = JSONArray(json)
                for (i in 0 until jsonArray.length()) {
                    valueList.add(jsonArray.opt(i) as E)
                }
            } catch (e: JSONException) {

            }
        }
        return valueList
    }


    override fun onCreate(savedInstanceState: Bundle?) { // 액티비티 처음 실행되는 생명주기
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val dbHelper = DBHelper(applicationContext, "LOGIN.db", null, 1)
        val result = findViewById<View>(R.id.result) as TextView


        et_id = findViewById(R.id.et_id)
        et_pass = findViewById(R.id.et_pass)
        btn_register = findViewById(R.id.btn_register)

        val auto = getSharedPreferences("auto", MODE_PRIVATE)
        //처음에는 SharedPreferences에 아무런 정보도 없으므로 값을 저장할 키들을 생성한다.
        // getString의 첫 번째 인자는 저장될 키, 두 번쨰 인자는 값( 저장된 전역변수를 불러온다 라고 생각해)
        // 첨엔 값이 없으므로 키값은 원하는 것으로 하고 값을 null을 준다.

        autoId = auto.getString("inputId", null) // 불러오기
        autoPwd = auto.getString("inputPwd", null)
        auto_nameList2 = getList("nameList2")
        auto_nameList3 = getList("nameList3")
        auto_nameList4 = getList("nameList4")
        Log.d("확1-1", " $auto_nameList2")
        Log.d("확2-2", "$auto_nameList3")//nameList의 형식은 List<Name>
        Log.d("확3-3", "$auto_nameList4")//nameList의 형식은 List<Name>

        if (autoId != null && autoPwd != null ) { //로그인한 이력이 있으면
            Toast.makeText(this@RegisterActivity, autoId + "님, 다시만나서 반가워요!", Toast.LENGTH_SHORT).show()
            val intent = Intent(this@RegisterActivity, Main::class.java)
            intent.putExtra("nameList2", auto_nameList2 as ArrayList<List<Name>>)
            intent.putExtra("nameList3", auto_nameList3 as ArrayList<List<Cal>>)
            intent.putExtra("nameList4", auto_nameList4 as ArrayList<List<Subject>>)
            intent.putExtra("userID", autoId)
            intent.putExtra("userPass", autoPwd)
            startActivity(intent)


        } else if (autoId == null && autoPwd == null) { //만약 처음 입장할때면(로그인 이력 x)
            //로그인 버튼 클릭시 수행
            btn_register!!.setOnClickListener {
                //Edit Text에 현재 입력되어있는값을 가져온다.

                var userID = et_id?.text.toString()
                var userPass = et_pass?.text.toString()
                mWeatherListView = findViewById(R.id.list_view) as? ListView

                result.text = dbHelper.result
                println("$result")


                HttpAsyncTask(userID, userPass).execute("http:/192.168.164.96:8080")

            }
        }
    }


    private inner class HttpAsyncTask(var userID: String, var userPass: String) : AsyncTask<String, Void, List<Name>>() { //첫번재
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
                //Toast.makeText(applicationContext, "로그인에 실패하였습니다.", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }

            //Log.d(TAG, nameList.toString())//nameList의 형식은 List<Name>

            nameList2 = nameList
            return nameList
        }


        override fun onPostExecute(nameList: List<Name>?) {
            super.onPostExecute(nameList)


            HttpAsyncTask2(userID, userPass).execute("http:/192.168.164.96:8080")

            Thread(Runnable {
                try {
                    if (dialog != null && dialog.isShowing) {
                        dialog.dismiss()
                    }
                } catch (e: Exception) {
                    Toast.makeText(applicationContext, "로그인에 실패하였습니다.", Toast.LENGTH_SHORT).show()
                }
                dialog.dismiss()
            }).start()
        }
    }

    private inner class HttpAsyncTask3(var userID: String, var userPass: String) : AsyncTask<String, Void, List<Subject>>() { //두번째
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
                .add("num", "3")
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

        override fun doInBackground(vararg params: String): List<Subject>? {

            var nameList: List<Subject> = ArrayList()
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
                val listType = object : TypeToken<ArrayList<Subject>>() {

                }.type
                nameList = gson.fromJson<List<Subject>>(response.body!!.string(), listType)
                //Log.d(TAG, "onCreate: " + weatherList.toString());
                // notify2()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            //Log.d("프레그먼트2", nameList.toString())//nameList의 형식은 List<Name>

            nameList4 = nameList
            return nameList
        }


        override fun onPostExecute(nameList: List<Subject>?) {
            super.onPostExecute(nameList)

            HttpAsyncTask2(userID, userPass).execute("http:/192.168.164.96:8080")

            Thread(Runnable {
                try {
                    if (dialog != null && dialog.isShowing) {
                        dialog.dismiss()
                    }
                } catch (e: Exception) {

                }
                dialog.dismiss()
            }).start()
            Log.d("확3", "$nameList4")//nameList의 형식은 List<Name>
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



        /*fun <E> setJsonArrayList(key: String, valueList: List<E>) { //List를 put
            val auto = getSharedPreferences("auto", Activity.MODE_PRIVATE)

            val jsonArray = JSONArray()
            for (value in valueList) {
                jsonArray.put(value)
            }

            if (!valueList.isEmpty()) {
                auto.edit().putString(key, jsonArray.toString()).commit()
            } else {
                auto.edit().putString(key, null).commit()
            }
        }*/

        fun <T> setList(key: String, list: List<T>) {
            val gson = Gson()
            val json = gson.toJson(list)

            set(key, json)
        }

        operator fun set(key: String, value: String) {
            val auto = getSharedPreferences("auto", Activity.MODE_PRIVATE)
            auto.edit().putString(key, value)
            auto.edit().commit()
        }

        override fun onPostExecute(nameList: List<Cal>?) {
            super.onPostExecute(nameList)

            Thread(Runnable {
                try {
                    if (dialog != null && dialog.isShowing) {
                        dialog.dismiss()
                    }
                } catch (e: Exception) {

                }
                dialog.dismiss()
            }).start()


            //progressdialog 종료 코드 -> 위에 백그라운드 함수에서 nameList를 받아오기 전까지는
            //onPreExecute로 pregressDialog가 계속 돌고 있을 것이고 서버에서 lms받아오면
            //그 뒤로 5초 뒤에 보여지도록 설정하였다.
            Thread(Runnable {
                try {
                    //Thread.sleep(5000)
                    if (dialog != null && dialog.isShowing) {
                        dialog.dismiss()
                    }
                } catch (e: Exception) {

                }
                dialog.dismiss()
            }).start()

            //progressdialog 코드 끝

            val auto = getSharedPreferences("auto", Activity.MODE_PRIVATE)
            //val tinyDB : TinyDB = TinyDB(applicationContext)
            val autoLogin = auto.edit()
            autoLogin.putString("inputId", userID) //저장
            autoLogin.putString("inputPwd", userPass) //저장
            setList("nameList2", nameList2)  //저장
            setList("nameList3", nameList3 ) //저장
            setList("nameList4", nameList4) //저장
            //꼭 commit()을 해줘야 값이 저장됨
            autoLogin.commit()

            Log.d("확1", " $nameList2")
            Log.d("확2", "$nameList3")//nameList의 형식은 List<Name>
            Log.d("확3", "$nameList4")//nameList의 형식은 List<Name>

            Toast.makeText(applicationContext, userID + "님, 환영합니다.", Toast.LENGTH_SHORT).show()
            val intent = Intent(this@RegisterActivity, Main::class.java)
            intent.putExtra("nameList2", nameList2 as ArrayList<List<Name>>)
            intent.putExtra("nameList3", nameList3 as ArrayList<List<Cal>>)
            intent.putExtra("nameList4", nameList4 as ArrayList<List<Subject>>)
            intent.putExtra("userID", userID)
            intent.putExtra("userPass", userPass)
            startActivity(intent)


        }



    }

}









