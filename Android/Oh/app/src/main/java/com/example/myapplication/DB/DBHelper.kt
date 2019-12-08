package com.example.myapplication.DB

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.myapplication.DataType.Exam
import com.example.myapplication.DataType.Name
import com.example.myapplication.DataType.Subject

class DBHelper// DBHelper 생성자로 관리할 DB 이름과 버전 정보를 받음
(context: Context?, name: String, factory: SQLiteDatabase.CursorFactory?, version: Int) : SQLiteOpenHelper(context, name, factory, version) {

    // 읽기가 가능하게 DB 열기
    // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
    val getName:List<Name>
        get(){
            var noticeList:List<Name> = mutableListOf()
            //var temp_Name : Name = Name("a","a","a","a")
            val db = readableDatabase
            var cursor :Cursor = db.rawQuery("SELECT * FROM NAME",null)
            while(cursor.moveToNext() ){
                var form = cursor.getString(0)
                var name = cursor.getString(1)
                var link = cursor.getString(2)
                var day = cursor.getString(3)
                var temp_Name : Name = Name(form, name, link, day)
                Log.d("name", "$temp_Name")
                if(temp_Name!=null){
                    noticeList += temp_Name
                }

            }
            cursor.close();
            Log.d("namelist DB저장", "$noticeList")
            return noticeList
        }
    val getSubject:List<Subject>
        get(){
            var subjectList:List<Subject> = mutableListOf()
            val db = readableDatabase
            var cursor :Cursor = db.rawQuery("SELECT * FROM SUBJECT",null)
            while(cursor.moveToNext() ){
                var subject = cursor.getString(0)
                var link = cursor.getString(1)
                var temp_Subject : Subject = Subject(subject, link)
                Log.d("subject", "$temp_Subject")
                if(temp_Subject!=null){
                    subjectList += temp_Subject
                }
            }
            cursor.close();
            Log.d("subjectlist DB저장", "$subjectList")
            return subjectList
        }
    val getExam:List<Exam>
        get(){
            var examList:List<Exam> = mutableListOf()
            val db = readableDatabase
            var cursor :Cursor = db.rawQuery("SELECT * FROM CAL",null)
            while(cursor.moveToNext() ){
                var date = cursor.getString(0)
                var mon = cursor.getString(1)
                var tue = cursor.getString(2)
                var wed = cursor.getString(3)
                var thu = cursor.getString(4)
                var fri = cursor.getString(5)
                var temp_Cal : Exam = Exam(date, mon, tue, wed, thu, fri)
                Log.d("cal", "$temp_Cal")
                if(temp_Cal!=null){
                    examList += temp_Cal
                }
            }
            cursor.close();
            Log.d("callist DB저장", "$examList")
            return examList
        }

    val result: String
        get() {
            val db = readableDatabase
            var result = ""
            val cursor = db.rawQuery("SELECT * FROM NAME", null)
            while (cursor.moveToNext()) {
                result += cursor.getString(1)
            }
            return result
        }
    val resultExam: String
        get() {
            val db = readableDatabase
            var result = ""
            val cursor = db.rawQuery("SELECT * FROM CAL", null)
            while (cursor.moveToNext()) {
                result += cursor.getString(0)
                result += cursor.getString(1)
                result += cursor.getString(2)
                result += cursor.getString(3)
                result += cursor.getString(4)
                result += cursor.getString(5)
            }
            return result
        }
    val resultSett: String
        get() {
            val db = readableDatabase
            var result = ""
            val cursor = db.rawQuery("SELECT * FROM SETT", null)
            while (cursor.moveToNext()) {
                result += cursor.getString(0)
                result += cursor.getString(1)
            }
            return result
        }
    /*
     * DB 생성 총 4개의 table을 생성
     */
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE SETT (N INT, C INT);")
        db.execSQL("CREATE TABLE NAME (FORM TEXT, TITLE TEXT, LINK TEXT, DAY TEXT);")
        db.execSQL("CREATE TABLE SECURE (ID TEXT, PW TEXT);")
        db.execSQL("CREATE TABLE SUBJECT (SUBJECT TEXT, LINK TEXT);")
        db.execSQL("CREATE TABLE CAL (DATE TEXT, MON TEXT, TUE TEXT, WED TEXT, THU TEXT, FRI TEXT);")
    }

    // DB 업그레이드를 위해 버전이 변경될 때 호출되는 함수
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

    }
    /*
     * insert 구문
     */
    fun settInsert(N: Int, C: Int) {
        // 읽고 쓰기가 가능하게 DB 열기
        val db = writableDatabase
        // DB에 입력한 값으로 행 추가
        db.execSQL("INSERT INTO SETT(N, C) VALUES('$N', '$C');")
        db.close()
    }
    fun secureInsert(id: String?, pwd: String?) {
        // 읽고 쓰기가 가능하게 DB 열기
        val db = writableDatabase
        // DB에 입력한 값으로 행 추가
        db.execSQL("INSERT INTO SECURE(ID, PW) VALUES('$id', '$pwd');")
        db.close()
    }
    fun nameInsert(form : String?, name : String?, link : String?, day :String?){
        val db = writableDatabase
        // DB에 입력한 값으로 행 추가
        db.execSQL("INSERT INTO NAME(FORM, TITLE, LINK, DAY) VALUES('$form','$name','$link','$day');")
        db.close()
    }
    fun subjectInsert(subject : String?, link : String?){
        val db = writableDatabase
        // DB에 입력한 값으로 행 추가
        db.execSQL("INSERT INTO SUBJECT(SUBJECT, LINK) VALUES('$subject','$link');")
        db.close()
    }
    fun calInsert(date: String?, mon: String?, tue: String?, wed: String?, thu: String?, fri: String?){
        val db = writableDatabase
        // DB에 입력한 값으로 행 추가
        db.execSQL("INSERT INTO CAL(DATE, MON, TUE, WED, THU, FRI) VALUES('$date','$mon','$tue','$wed','$thu','$fri' );")
        db.close()
    }
    /*
     * query문
     */

    fun selectDate():String?{
        val db = readableDatabase
        var fd : String?
        var cursor :Cursor = db.rawQuery("SELECT DAY FROM NAME",null)
        if( cursor != null && cursor.moveToFirst() ){
            fd = cursor.getString(0)
            cursor.close();
            Log.d("확3", "$fd")
            return fd
        }
        return null
    }
    fun getSecure():String? {
        val db = readableDatabase
        var fd : String?
        var cursor :Cursor = db.rawQuery("SELECT ID,PW FROM SECURE",null)
        if( cursor != null && cursor.moveToFirst() ){
            fd = cursor.getString(0) + '/' + cursor.getString(1)
            cursor.close();
            Log.d("확3", "$fd")
            return fd
        }
        return null
    }
    /*
     * delete 구문
     */
    fun deleteSett(){
        val db = readableDatabase
        db.execSQL("DELETE FROM SETT")
        db.close()
    }
    fun deleteSecure(id:String,pw:String){
        val db = readableDatabase
        db.execSQL("DELETE FROM SECURE WHERE ID = ${id}")
        db.close()
    }
    fun deleteName(){
        val db = readableDatabase
        db.execSQL("DELETE FROM NAME")
        db.close()
    }
    fun deleteCal(){
        val db = readableDatabase
        db.execSQL("DELETE FROM CAL")
        db.close()
    }
    fun deleteAll(){
        val db = readableDatabase
        db.execSQL("DELETE FROM NAME")
        db.execSQL("DELETE FROM SUBJECT")
        db.execSQL("DELETE FROM CAL")
        db.close()
    }
}

