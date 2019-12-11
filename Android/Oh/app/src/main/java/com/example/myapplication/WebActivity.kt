package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient

//탭프레그먼트 1과 3에서 리스트뷰를 클릭하면 웹뷰로 연결시켜준다.(해당 링크는 통신으로 받은 정보를 이용한다).
class WebActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)

        var webView : WebView = findViewById(R.id.webview)
        var link = intent.getStringExtra("Link")

        Log.d("web", link)
        webView.webViewClient = WebViewClient()
        webView.loadUrl(link)

    }
}
