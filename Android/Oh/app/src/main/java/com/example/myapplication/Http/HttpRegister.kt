package com.example.myapplication.Http


import android.app.*
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Build
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.myapplication.CustomDialog.CustomAnimationDialog
import com.example.myapplication.DB.DBHelper
import com.example.myapplication.DataType.Exam
import com.example.myapplication.DataType.Name
import com.example.myapplication.DataType.Subject
import com.example.myapplication.MainActivity
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

//이거 실험용으로 통신파트 빼봄

class HttpRegister(private val context: Context, private val id: String, private val pw: String) {
    val dbHelper : DBHelper = DBHelper(context, "NAME.db", null, 2)
    var dbHelper1 : DBHelper = DBHelper(context, "SECURE.db", null, 1)
    val url = "http:/13.124.174.165:6060/kau"
    var customAnimationDialog: CustomAnimationDialog.Builder? = null
    val handler = Handler()

    fun startHttp(num: String) {
        HttpAsyncTask(id, pw, num).execute(url)
    }

    private fun makeDialog(){
        CustomAnimationDialog.Builder(context)
                .show()
    }

    //customDialog를 종료 메소드
    private fun deleteDialog(){
        CustomAnimationDialog.Builder(context)
                .dismiss()
    }

    @RequiresApi(Build.VERSION_CODES.CUPCAKE)
    inner class HttpAsyncTask(userID: String, userPass: String, var num: String) : AsyncTask<String, Void, String>() { //첫번재

        override fun onPreExecute() {
            super.onPreExecute()


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
                .add("num", num)
                .build()


        override fun doInBackground(vararg params: String): String {

            handler.postDelayed({
                customAnimationDialog = CustomAnimationDialog.Builder(context)
                customAnimationDialog!!.show()
            },0)

            var subjectList: List<Subject> = ArrayList()
            var noticeList: List<Name> = ArrayList()
            var examList: List<Exam> = ArrayList()
            var result:String = "error"
            val strUrl = params[0]
            try {

                val request = Request.Builder()
                        .url(strUrl)
                        .post(formBody)
                        .build()

                // 응답
                val response = client.newCall(request).execute()
                val gson = Gson()
                //val checkjson = object : TypeToken<String>() {}.type // error 잡기
                //val check = gson.fromJson<String>(response.body!!.string(), checkjson)
                var check = response.body!!.string()
                Log.d("asdasd", " ${check}")
                if (check.toString() != "1") {
                    Log.d("22222", " ${check}")
                   if(num == "0"){
                        val listTypeName = object : TypeToken<ArrayList<Name>>() {}.type
                        noticeList = gson.fromJson<List<Name>>(check, listTypeName)
                        for (i in noticeList){
                            if(noticeList!=null) {
                                dbHelper.nameInsert(i.form,i.name,i.link,i.day)
                            }
                        }
                        result = "0"
                        startHttp("1")
                    }else if(num == "1"){
                        val listTypeExam = object : TypeToken<ArrayList<Exam>>() {}.type
                        examList = gson.fromJson<List<Exam>>(check, listTypeExam)
                        for (i in examList){
                            if(examList!=null) {
                                dbHelper.calInsert(i.date,i.mon,i.tue,i.wed,i.thu,i.fri)
                            }
                        }
                        result = "1"
                        startHttp("3")
                    }else if(num == "3"){
                        val listTypeSubject = object : TypeToken<ArrayList<Subject>>() {}.type
                        subjectList = gson.fromJson<List<Subject>>(check, listTypeSubject)
                        for (i in subjectList){
                            if(subjectList!=null) {
                                dbHelper.subjectInsert(i.subject,i.link)
                            }
                        }
                        result = "3"
                    }

                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
            customAnimationDialog!!.dismiss()


            return result
        }


        @RequiresApi(Build.VERSION_CODES.CUPCAKE)
        override fun onPostExecute(result :String) {
            super.onPostExecute(result)
            if(result == "error"){
                Toast.makeText(context, "Login에 실패하였습니다.", Toast.LENGTH_SHORT).show()
                dbHelper.deleteAll()
            } else if(result == "3"){
                dbHelper1.settInsert(1,1) // 세팅 2개가 되어있는 상태
                dbHelper1.secureInsert(id,pw) // id / pw 저장
                val auto = context.getSharedPreferences("auto", Activity.MODE_PRIVATE)
                val autoLogin = auto.edit()

                autoLogin.putString("inputId", id) //저장
                autoLogin.putString("inputPwd", pw) //저장

                //꼭 commit()을 해줘야 값이 저장됨
                autoLogin.commit()
                Toast.makeText(context, id + "님, 환영합니다.", Toast.LENGTH_SHORT).show()
                val intent = Intent(context, MainActivity::class.java)
                intent.putExtra("userID", id)
                intent.putExtra("userPass", pw)
                intent.putExtra("url", url)
                context.startActivity(intent)
            }

        }

    }

}