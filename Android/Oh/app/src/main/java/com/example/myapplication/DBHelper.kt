package com.example.myapplication

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DBHelper// DBHelper 생성자로 관리할 DB 이름과 버전 정보를 받음
(context: Context, name: String, factory: SQLiteDatabase.CursorFactory?, version: Int) : SQLiteOpenHelper(context, name, factory, version) {

    // 읽기가 가능하게 DB 열기
    // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력

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
    // DB를 새로 생성할 때 호출되는 함수
    override fun onCreate(db: SQLiteDatabase) {
        // 새로운 테이블 생성
        /* 이름은 LOGIN이고, 자동으로 값이 증가하는 num 정수형 기본키 컬럼과
        아이디 문자열 컬럼, 비밀번호 문자열 컬럼으로 구성된 테이블을 생성. */
        //db.execSQL("CREATE TABLE LOGIN (num INTEGER PRIMARY KEY AUTOINCREMENT, id TEXT, pwd TEXT);")
        db.execSQL("CREATE TABLE NAME (FORM TEXT, TITLE TEXT, LINK TEXT, DAY TEXT);")
        db.execSQL("CREATE TABLE SECURE (ID TEXT, PW TEXT);")
    }

    // DB 업그레이드를 위해 버전이 변경될 때 호출되는 함수
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

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
    fun deleteSecure(id:String,pw:String){
        val db = readableDatabase
        db.execSQL("DELETE FROM SECURE WHERE ID = ${id}")
        db.close()
    }
}




