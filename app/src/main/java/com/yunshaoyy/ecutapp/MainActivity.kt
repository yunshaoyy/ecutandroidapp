package com.yunshaoyy.ecutapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.KeyEvent
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var myWebView: WebView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        myWebView = findViewById(R.id.webview)
        setupWebView()
    }
    private fun setupWebView() {
        myWebView.settings.javaScriptEnabled = true
        myWebView.webViewClient = MyWebViewClient()
        myWebView.loadUrl("file:///android_asset/main.html")
    }
    private inner class MyWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            if (url.isNullOrBlank()) {
                return false
            }
            val uri = Uri.parse(url)
            if (uri.host == "172.21.255.105") {
                return false
            }
            else if (uri.host == "172.30.1.100") {
                return false
            }
            Intent(Intent.ACTION_VIEW, uri).apply {
                startActivity(this)
            }
            return true
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && myWebView.canGoBack()) {
            myWebView.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}