package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient

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
