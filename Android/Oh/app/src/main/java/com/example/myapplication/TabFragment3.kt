package com.example.myapplication

import android.app.ProgressDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_register.*
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import java.io.IOException
import java.lang.Exception
import java.util.ArrayList
import java.util.concurrent.TimeUnit

class TabFragment3(var nameList4:List<Subject>,var userID : String, var userPass : String) : Fragment() {

    private var btn_search: Button? = null
    private var searchListView: ListView? = null
    private var mWeatherListView: ListView? = null
    val adapter = SubjectAdapter(nameList4)
    var position : Int? = null // 프로퍼티는 전역변수같은 의미라 생각해
    var link:String? = null

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        var view: View = inflater.inflate(R.layout.fragment3, container, false)
        btn_search = view?.findViewById(R.id.btn_search)
        searchListView = view?.findViewById<ListView>(R.id.search_view)
        mWeatherListView  = view?.findViewById<ListView>(R.id.subject_view)

        //val adapter = ArrayAdapter(context, android.R.layout.simple_list_item_1, weatherList)
        Log.d("들어가라", " $nameList4")
        if (mWeatherListView != null) {
            mWeatherListView!!.adapter = adapter
        }
        mWeatherListView!!.setOnItemClickListener({parent, itemView,position,id->
            this.position = position
            link = nameList4[position].link
           Log.d("확3", "$link")
            HttpAsyncTask().execute("http:/172.30.1.34:8080")
        })


        btn_search?.setOnClickListener {
            //HttpAsyncTask().execute("http://172.30.1.7:8080")
        }

        return view
    }

    private inner class HttpAsyncTask : AsyncTask<String, Void, List<Name2>>() {

        var title : String = "2"


        // OkHttp 클라이언트
        internal var client = OkHttpClient.Builder()
                .connectTimeout(600, TimeUnit.SECONDS)
                .writeTimeout(600, TimeUnit.SECONDS)
                .readTimeout(600, TimeUnit.SECONDS)
                .build()

        internal var formBody: RequestBody = FormBody.Builder()
                .add("link",link!!)
                .add("id", userID)
                .add("pw", userPass)
                .add("num", title)
                .build()

        val dialog = ProgressDialog(context)
        override fun onPreExecute() {
            super.onPreExecute()
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
            dialog.setMessage("로딩중입니다...")
            dialog.setCanceledOnTouchOutside(false)
            dialog.setCancelable(false)
            dialog.show()
        }

        override fun doInBackground(vararg params: String): List<Name2>? {

            var searchList: List<Name2> = ArrayList()
            val strUrl = params[0]
            try {

                // 요청
                val request = Request.Builder()
                        .url(strUrl)
                        .post(formBody)
                        .build()

                // 응답
                val response = client.newCall(request).execute()
                val gson = Gson()
                val listType = object : TypeToken<ArrayList<Name2>>() {

                }.type
                searchList = gson.fromJson<List<Name2>>(response.body!!.string(), listType)

            } catch (e: IOException) {
                e.printStackTrace()
            }

            Log.d(TAG, searchList.toString())//nameList의 형식은 List<Name>
            return searchList
        }


        override fun onPostExecute(searchList: List<Name2>?) {
            super.onPostExecute(searchList)

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


            if(searchList != null) {
                val adapter = SearchAdapter(searchList)
                searchListView?.adapter = adapter
            }

        }
    }
}
