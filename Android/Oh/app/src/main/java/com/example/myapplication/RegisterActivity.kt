package com.example.myapplication

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.*

import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.annotation.RequiresApi
import com.example.myapplication.Http.HttpRegister


class RegisterActivity : AppCompatActivity() { //  서버에 아이디 비밀번호 전송


    private var et_id: EditText? = null
    private var et_pass: EditText? = null
    private lateinit var btn_register: Button
    private var mainListView: ListView? = null
    var linLayout: LinearLayout? = null

    var autoId: String? = null
    var autoPwd: String? = null



    var Str_url : String = "http:/13.124.174.165:6060/kau"
    var lastTimeBackPressed : Long = 0


    var imm: InputMethodManager? = null

    @RequiresApi(Build.VERSION_CODES.CUPCAKE)


    override fun onCreate(savedInstanceState: Bundle?) { // 액티비티 처음 실행되는 생명주기

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
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
            val intent = Intent(this@RegisterActivity, MainActivity::class.java)

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
                HttpRegister(this@RegisterActivity,userID,userPass).startHttp("0")
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

    override  fun onBackPressed() {
        if (System.currentTimeMillis() - lastTimeBackPressed < 1500) {
            finish()
            return
        }
        lastTimeBackPressed = System.currentTimeMillis()
        Toast.makeText(this, "'뒤로' 버튼을 한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
    }

}









