package com.example.myapplication


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.DrawableImageViewTarget

//로딩엑티비티
class LoadingActivity : AppCompatActivity() {
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splashani)
        //raw 디렉토리에 splani로써 gif를 저장해놓은것을 imgview를 통해 불러오고 Draw하여
        //gif 파일을 스플래시 화면으로 시작시킨다.
        val splashGif = findViewById<View>(R.id.splashani) as ImageView
        val gifImage = DrawableImageViewTarget(splashGif)
        Glide.with(this).load(R.raw.spani).into(gifImage)

        startLoading()
    }

    private fun startLoading() {
        val handler = Handler()
        handler.postDelayed({
            val intent = Intent(baseContext, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }, 5000)
    }
}