package com.example.myapplication

import android.app.*
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.*
import android.util.Log

import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.annotation.RequiresApi

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import java.io.IOException
import java.lang.Exception
import java.util.ArrayList
import java.util.concurrent.TimeUnit


class RegisterActivity : AppCompatActivity() { //  서버에 아이디 비밀번호 전송


    val dbHelper : DBHelper = DBHelper(this,"NAME.db",null,2)
    var customAnimationDialog: CustomAnimationDialog? = null
    val handler = Handler()


    private var et_id: EditText? = null
    private var et_pass: EditText? = null
    private lateinit var btn_register: Button
    private var mainListView: ListView? = null
    var linLayout: LinearLayout? = null

    var noticeList: List<Name> = ArrayList() // 넘겨줄걸 여기다 저장
    var examList: List<Exam> = ArrayList() // 넘겨줄걸 여기다 저장
    var subjectList: List<Subject> = ArrayList() // 넘겨줄걸 여기다 저장

    var autoId: String? = null
    var autoPwd: String? = null



    var Str_url : String = "http:/13.124.174.165:6060/kau"
    var lastTimeBackPressed : Long = 0


    var imm: InputMethodManager? = null

    @RequiresApi(Build.VERSION_CODES.CUPCAKE)


    override fun onCreate(savedInstanceState: Bundle?) { // 액티비티 처음 실행되는 생명주기

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        //val dbHelper = DBHelper(applicationContext, "NAME", null, 1)
        //val result = findViewById<View>(R.id.result) as TextView

        //로그인시 불편함 제거를 위해 터치또는 클릭시 키보드 내리기
        imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        et_id = findViewById(R.id.et_id)
        et_pass = findViewById(R.id.et_pass)
        btn_register = findViewById(R.id.btn_register)
        linLayout = findViewById(R.id.con)//register activity의 전체 레이아웃 이름
        linLayout?.setOnClickListener(myClickListener)
        btn_register.setOnClickListener(myClickListener)

        val auto = getSharedPreferences("auto", MODE_PRIVATE)
        //처음에는 SharedPreferences에 아무런 정보도 없으므로 값을 저장할 키들을 생성한다.
        // getString의 첫 번째 인자는 저장될 키, 두 번쨰 인자는 값( 저장된 전역변수를 불러온다 라고 생각해)
        // 첨엔 값이 없으므로 키값은 원하는 것으로 하고 값을 null을 준다.
        autoId = auto.getString("inputId", null) // 불러오기
        autoPwd = auto.getString("inputPwd", null)



        //로그인한 이력이 있었으면
        if (autoId != null && autoPwd != null ) {
            Toast.makeText(this@RegisterActivity, autoId + "님, 다시만나서 반가워요!", Toast.LENGTH_SHORT).show()
            val intent = Intent(this@RegisterActivity, Main::class.java)

            intent.putExtra("userID", autoId)
            intent.putExtra("userPass", autoPwd)
            intent.putExtra("url", Str_url)
            startActivity(intent)


        } else if (autoId == null && autoPwd == null) { //만약 처음 입장할때면(로그인 이력 x)
            //로그인 버튼 클릭시 수행
            btn_register.setOnClickListener {
                //Edit Text에 현재 입력되어있는값을 가져온다.

                var userID = et_id?.text.toString()
                var userPass = et_pass?.text.toString()
                mainListView = findViewById(R.id.list_view) as? ListView
                HttpAsyncTask(userID, userPass).execute(Str_url)

            }
        }

    }


    //바깥화면 터치, 로그인 버튼 클릭시 키보드 내리기

    var myClickListener: View.OnClickListener = View.OnClickListener { v ->
        hideKeyboard()
        when (v.id) {
            //case 하나더 여기는 전체 레이아웃 id
            R.id.btn_register -> {

            }
            R.id.con -> {

            }

        }
    }

    //바깥화면 터치시 키보드가 내려가는 함수
    private fun hideKeyboard() {
        imm?.hideSoftInputFromWindow(et_id?.getWindowToken(), 0)
        imm?.hideSoftInputFromWindow(et_pass?.getWindowToken(), 0)
    }


    //customDialog를 시작 메소드
    private fun makeDialog(){
        CustomAnimationDialog.Builder(this)
                .show()
    }

    //customDialog를 종료 메소드
    private fun deleteDialog(){
        CustomAnimationDialog.Builder(this)
                .dismiss()
    }

    //customdialog code 끝

    @RequiresApi(Build.VERSION_CODES.CUPCAKE)
    private inner class HttpAsyncTask(var userID: String, var userPass: String) : AsyncTask<String, Void, List<Name>>() { //첫번재


        override fun onPreExecute() {
            super.onPreExecute()
            //custom dialog 실행
            handler.postDelayed({
                customAnimationDialog = CustomAnimationDialog(this@RegisterActivity)
                makeDialog()
            },0)
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

            var get_notice: List<Name> = ArrayList()
            val strUrl = params[0]
            try {

                val request = Request.Builder()
                        .url(strUrl)
                        .post(formBody)
                        .build()

                // 응답
                val response = client.newCall(request).execute()
                val gson = Gson()
                val listType = object : TypeToken<ArrayList<Name>>() {

                }.type
                get_notice = gson.fromJson<List<Name>>(response.body!!.string(), listType)

            } catch (e: IOException) {
                e.printStackTrace()
            }

            noticeList = get_notice
            for (i in noticeList){
                if(noticeList!=null) {
                    dbHelper.nameInsert(i.form,i.name,i.link,i.day)
                }
            }
            return get_notice
        }


        @RequiresApi(Build.VERSION_CODES.CUPCAKE)
        override fun onPostExecute(get_notice: List<Name>?) {
            super.onPostExecute(get_notice)

            HttpAsyncTask3(userID, userPass).execute(Str_url)
            //통신 완료시에 커스텀다이얼로그 종료
            handler.postDelayed({
                Thread(Runnable {
                    try{
                        if(customAnimationDialog != null && customAnimationDialog!!.isShowing){
                            deleteDialog()
                        }
                    }catch (e: Exception){
                        deleteDialog()
                    }

                }).start()

            },0)
        }
    }

    @RequiresApi(Build.VERSION_CODES.CUPCAKE)
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


        override fun onPreExecute() {
            super.onPreExecute()
            //custom dialog 실행
            handler.postDelayed({
                customAnimationDialog = CustomAnimationDialog(this@RegisterActivity)
                makeDialog()
            },0)
        }


        override fun doInBackground(vararg params: String): List<Subject>? {

            var get_subject: List<Subject> = ArrayList()
            val strUrl = params[0]
            try {
                val request = Request.Builder()
                        .url(strUrl)
                        .post(formBody)
                        .build()

                val response = client.newCall(request).execute()
                val gson = Gson()
                val listType = object : TypeToken<ArrayList<Subject>>() {

                }.type
                get_subject = gson.fromJson<List<Subject>>(response.body!!.string(), listType)

            } catch (e: IOException) {
                e.printStackTrace()
            }

            subjectList = get_subject
            for (i in subjectList){
                if(subjectList!=null) {
                    dbHelper.subjectInsert(i.subject,i.link)
                }
            }
            return get_subject
        }


        override fun onPostExecute(get_subject: List<Subject>?) {
            super.onPostExecute(get_subject)

            HttpAsyncTask2(userID, userPass).execute(Str_url)
            //통신 완료시에 커스텀다이얼로그 종료
            handler.postDelayed({
                Thread(Runnable {
                    try{
                        if(customAnimationDialog != null && customAnimationDialog!!.isShowing){
                            deleteDialog()
                        }
                    }catch (e: Exception){
                        deleteDialog()
                    }

                }).start()

            },0)
            Log.d("확3", "$subjectList")//nameList의 형식은 List<Name>
        }
    }


    @RequiresApi(Build.VERSION_CODES.CUPCAKE)
    private inner class HttpAsyncTask2(var userID: String, var userPass: String) : AsyncTask<String, Void, List<Exam>>() {

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

        override fun onPreExecute() {
            super.onPreExecute()
            //custom dialog 실행
            handler.postDelayed({
                customAnimationDialog = CustomAnimationDialog(this@RegisterActivity)
                makeDialog()
            },0)
        }


        override fun doInBackground(vararg params: String): List<Exam>? {

            var get_exam: List<Exam> = ArrayList()
            val strUrl = params[0]
            try {

                val request = Request.Builder()
                        .url(strUrl)
                        .post(formBody)
                        .build()

                // 응답
                val response = client.newCall(request).execute()
                val gson = Gson()
                val listType = object : TypeToken<ArrayList<Exam>>() {

                }.type
                get_exam = gson.fromJson<List<Exam>>(response.body!!.string(), listType)

            } catch (e: IOException) {
                e.printStackTrace()
            }

            examList = get_exam
            for (i in examList){
                if(examList!=null) {
                    dbHelper.calInsert(i.date,i.mon,i.tue,i.wed,i.thu,i.fri)
                }
            }
            return get_exam
        }




        @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
        override fun onPostExecute(get_exam: List<Exam>?) {
            super.onPostExecute(get_exam)
            //통신 완료시에 커스텀다이얼로그 종료
            handler.postDelayed({
                Thread(Runnable {
                    try{
                        if(customAnimationDialog != null && customAnimationDialog!!.isShowing){
                            deleteDialog()
                        }
                    }catch (e: Exception){
                        deleteDialog()
                    }
                }).start()

            },0)
            //통신 완료시에 커스텀다이얼로그 종료
            handler.postDelayed({
                Thread(Runnable {
                    try{
                        if(customAnimationDialog != null && customAnimationDialog!!.isShowing){
                            deleteDialog()
                        }
                    }catch (e: Exception){
                        deleteDialog()
                    }
                }).start()

            },0)

            //Customdialog 코드 끝

            val auto = getSharedPreferences("auto", Activity.MODE_PRIVATE)
            val autoLogin = auto.edit()

            autoLogin.putString("inputId", userID) //저장
            autoLogin.putString("inputPwd", userPass) //저장

            noticeList = dbHelper.getName
            subjectList = dbHelper.getSubject
            examList = dbHelper.getExam

            //꼭 commit()을 해줘야 값이 저장됨
            autoLogin.commit()


            Toast.makeText(applicationContext, userID + "님, 환영합니다.", Toast.LENGTH_SHORT).show()

            val intent = Intent(this@RegisterActivity, Main::class.java)

            intent.putExtra("userID", userID)
            intent.putExtra("userPass", userPass)
            intent.putExtra("url", Str_url)
            startActivity(intent)

        }
    }

    override  fun onBackPressed() {

        if (System.currentTimeMillis() - lastTimeBackPressed < 1500) {
            finish()
            return
        }
        lastTimeBackPressed = System.currentTimeMillis()
        Toast.makeText(this, "'뒤로' 버튼을 한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()































    }


}









