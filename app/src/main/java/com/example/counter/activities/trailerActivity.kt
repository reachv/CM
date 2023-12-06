package com.example.counter.activities

import android.os.Bundle
import android.util.Log
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.RatingBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.example.counter.R
import com.example.counter.Retrofithelper
import com.example.counter.datamodel.movie
import com.example.counter.datamodel.movieApi
import com.example.counter.datamodel.videoList
import com.google.android.youtube.player.YouTubeBaseActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class trailerActivity : YouTubeBaseActivity() {
    var YOUTUBE_API_KEY : String = "AIzaSyB_vq4tXUnpQs6u23YQ8hwK4inE5GdNGZs"
    @RequiresApi(33)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trailer)

        var youtubePlayer : WebView = findViewById(R.id.player)
        var tvtitle  : TextView = findViewById(R.id.tTitle)
        var tvOverview : TextView = findViewById(R.id.tOverview)
        var rating : RatingBar = findViewById(R.id.tratingBar)
        var key : String
        val moviesApi = Retrofithelper.getInstance().create(movieApi::class.java)

        var mov = intent.getParcelableExtra("mov", movie::class.java)
        val call: Call<videoList>? = moviesApi.getVideo(mov!!.id)

        tvtitle.setText(mov.title)
        tvOverview.setText(mov.overview)
        rating.rating = (mov.vote_average/2).toFloat()

        call!!.enqueue(object : Callback<videoList> {
            override fun onResponse(call: Call<videoList>, response: Response<videoList>) {
                key = response.body()!!.results.get(0).key
                playVideo(key, youtubePlayer)
            }

            override fun onFailure(call: Call<videoList>, t: Throwable) {
                Log.e("detailsFragment", "CallFailure: " + t)
            }
        })
    }

    private fun playVideo(key: String, youtubePlayer: WebView) {
        var url : String = "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/$key\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>"
        youtubePlayer.loadData(url, "text/html", "utf-8")
        youtubePlayer.settings.javaScriptEnabled = true
        youtubePlayer.webChromeClient = WebChromeClient()
    }
}