package com.example.myapplication.Fragment

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.myapplication.Adapter.SearchAdapter
import com.example.myapplication.Adapter.SubjectAdapter
import com.example.myapplication.CustomDialog.CustomAnimationDialog
import com.example.myapplication.DataType.Subject
import com.example.myapplication.DataType.WeekDetail
import com.example.myapplication.DetailViewActivity
import com.example.myapplication.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import java.io.IOException
import java.util.ArrayList
import java.util.concurrent.TimeUnit

class TabFragment3(var subjectList: List<Subject>, var userID: String, var userPass: String, var Str_url: String?) : Fragment() { //검색 화면

    private var btn_search: Button? = null
    private var searchListView: ListView? = null
    private lateinit var spinner: Spinner
    val adapter = SubjectAdapter(subjectList)
    var positionthis: Int? = null // 프로퍼티는 전역변수같은 의미라 생각해
    var link: String? = null
    var back : TextView? = null
    var value :Int? = 0
    var searchList2 : List<WeekDetail> = ArrayList()
    var position2 : Int? = null

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        var view: View? = inflater.inflate(R.layout.fragment3, container, false)

        btn_search = view?.findViewById(R.id.btn_search)
        searchListView = view?.findViewById(R.id.search_view)
        if (view != null) {
            spinner = view.findViewById(R.id.subject_spinner)
        }
        back = view?.findViewById(R.id.back)
        Log.d("들어가라", " $subjectList")
        if (spinner != null) {
            spinner.adapter = adapter


        }
        spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                positionthis = position

                value = 0;
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }

        }



        btn_search?.setOnClickListener {
            link = subjectList[positionthis!!].link
            Log.d("들어가라", " $Str_url")
            HttpAsyncTask().execute(Str_url)
            value = 1
            if(value == 1){
                back?.text  = null
            }
        }

        searchListView?.setOnItemClickListener{ parent, view, position, id->

            this.position2 = position
            Log.d("aaadsd",searchList2[position2!!].toString())
            var a = searchList2[position2!!].link
            var b = searchList2[position2!!].linkname

            if(a.size == 0 && b.size == 0 ){
                Toast.makeText(activity, "내용이 존재하지않습니다.", Toast.LENGTH_SHORT).show()
            }
            else {
                val intent = Intent(activity, DetailViewActivity::class.java)

                intent.putExtra("LinkName", a)
                intent.putExtra("Link", b)
                startActivityForResult(intent, 1)
            }
        }
        return view
    }

    private inner class HttpAsyncTask : AsyncTask<String, Void, List<WeekDetail>>() {

        var title: String = "2"


        // OkHttp 클라이언트
        internal var client = OkHttpClient.Builder()
                .connectTimeout(600, TimeUnit.SECONDS)
                .writeTimeout(600, TimeUnit.SECONDS)
                .readTimeout(600, TimeUnit.SECONDS)
                .build()

        internal var formBody: RequestBody = FormBody.Builder()
                .add("link", link!!)
                .add("id", userID)
                .add("pw", userPass)
                .add("num", title)
                .build()

        //검색에 대한 웨이팅 progress dialog
        var customAnimationDialog: CustomAnimationDialog.Builder? = null
        val handler = Handler()
        override fun onPreExecute() {
            super.onPreExecute()
            handler.postDelayed({
                var context = getContext()
                if(context != null){
                    customAnimationDialog = CustomAnimationDialog.Builder(context)
                    customAnimationDialog!!.show()
                }
            },0)
        }

        override fun doInBackground(vararg params: String): List<WeekDetail>? {

            var searchList: List<WeekDetail> = ArrayList()
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
                val listType = object : TypeToken<ArrayList<WeekDetail>>() {

                }.type
                searchList = gson.fromJson<List<WeekDetail>>(response.body!!.string(), listType)

            } catch (e: IOException) {
                e.printStackTrace()
            }

            Log.d(TAG, searchList.toString())//nameList의 형식은 List<Name>
            searchList2 = searchList
            return searchList
        }


        override fun onPostExecute(searchList: List<WeekDetail>?) {
            super.onPostExecute(searchList)
            if(context!=null){
                customAnimationDialog!!.dismiss()
            }

            if (searchList != null) {
                val adapter = SearchAdapter(searchList)
                searchListView?.adapter = adapter

            }

        }
    }
}