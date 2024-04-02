package br.com.dfn.youtubeplayer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient

class MainActivity : AppCompatActivity() {
    private lateinit var webview: WebView

    private var youtubeEmbed =
        "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube-nocookie.com/embed/nuMsANoILVw?rel=0\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" referrerpolicy=\"strict-origin-when-cross-origin\" allowfullscreen></iframe>\n"
    private var youtubeEmbed2 = "<body> " +
            "<div id=\"player\"></div> " +
            " \n" +
            "<script> " +
            " \n" +
            "var tag = document.createElement('script'); " +
            " \n" +
            "tag.src = \"https://www.youtube.com/iframe_api\"; " +
            "var firstScriptTag = document.getElementsByTagName('script')[0]; " +
            "firstScriptTag.parentNode.insertBefore(tag, firstScriptTag); " +
            " \n" +
            "// 3. This function creates an <iframe> (and YouTube player) " +
            "//    after the API code downloads. " +
            " \n" +
            "var player; " +
            "function onYouTubeIframeAPIReady() { " +
            "   player = new YT.Player('player', { " +
            "       height: '100%', " +
            "       width: '100%', " +
            "       videoId: 'qcP8dUTD258', " +
            "       playerVars: { 'rel': 0, 'controls': 2 }," +
            "       events: { " +
            "       'onReady': onPlayerReady, " +
            "       'onStateChange': onPlayerStateChange " +
            "   } " +
            "   }); " +
            "} " +
            " \n" +
            "function onPlayerReady(event) { " +
            "    event.target.playVideo(); " +
            "} " +
            " \n" +
            "function onPlayerStateChange(event) {" +
            " \n" +
            "    if(event.data == YT.PlayerState.PLAYING){" +
            "        console.log(\"FIGUE:PLAYING\");" +
            "    }else if(event.data == YT.PlayerState.ENDED){" +
            "        console.log(\"---- ENDED ---\");" +
            "       Android.showToast('Acabou o fídeo'); "+
            "    }else if(event.data == YT.PlayerState.PAUSED){" +
            "        console.log(\" ---- Pausou --- \");" +
            "        Android.showToast('pausou'); " +
            "    }" +
            "}" +
            "function stopVideo() { " +
            "    player.stopVideo(); " +
            "} " +
            "</script> " +
            "</body> "

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        executeVideo()
    }

    @Suppress("SetJavaScriptEnabled")
    private fun executeVideo() {
        webview = findViewById(R.id.webview)
        webview.settings.javaScriptEnabled = true
        webview.addJavascriptInterface(YoutubeAppInterface(this), "Android")

        webview.loadData(youtubeEmbed2, "text/html", "utf-8")

        webview.webChromeClient = object : WebChromeClient() {
            override fun onShowCustomView(view: View?, callback: CustomViewCallback?) {
                super.onShowCustomView(view, callback)
            }

            override fun onHideCustomView() {
                super.onHideCustomView()

            }
        }

        webview.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView,
                request: WebResourceRequest
            ): Boolean {
                if (request.url.equals(youtubeEmbed2)) {
                    view.loadData(youtubeEmbed2, "text/html", "utf-8")
                }
                return true
            }
        }
    }

    override fun onDestroy() {
        webview.destroy()
        super.onDestroy()

    }
}