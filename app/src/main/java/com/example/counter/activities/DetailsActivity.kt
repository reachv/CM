package com.example.counter.activities

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.content.ContentProviderCompat.requireContext
import com.bumptech.glide.Glide
import com.example.counter.R
import com.example.counter.datamodel.movie
import com.parse.ParseUser

class detailsActivity : AppCompatActivity() {
    @RequiresApi(33)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        var dbut : Button = findViewById(R.id.dbut)
        var tvtitle  : TextView = findViewById(R.id.dTitle)
        var tvOverview : TextView = findViewById(R.id.dOverview)
        var rating : RatingBar = findViewById(R.id.dratingBar)
        var poster : ImageView = findViewById(R.id.dposter)
        var button : Button = findViewById(R.id.fAdd)

        var bundle : Bundle? = intent.extras
        var mov : movie = bundle?.getSerializable("mov", movie::class.java)!!

        dbut.setOnClickListener {
            var intent = Intent(baseContext, trailerActivity::class.java)
            intent.putExtra("mov", mov)
            startActivity(intent)
        }
        tvtitle.setText(mov.title)
        tvOverview.setText(mov.overview)
        rating.rating = (mov.vote_average/2).toFloat()

        var imageUrl : String

        if(baseContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            imageUrl = String.format("https://image.tmdb.org/t/p/w342/%s", mov.backdrop_path)
        }else{
            imageUrl = String.format("https://image.tmdb.org/t/p/w342/%s", mov.poster_path)
        }

        Glide.with(baseContext).load(imageUrl).into(poster)

        button.setOnClickListener {
            var temp = ArrayList<Int>()
            if(ParseUser.getCurrentUser().getList<Int>("favorites") != null){
                temp.addAll(ParseUser.getCurrentUser().getList<Int>("favorites")!!)
            }
            for(x in temp){
                if(x == mov.id){
                    Toast.makeText(this, "This movie already exists in favorites", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
            }
            temp.add(mov.id)
            var user = ParseUser.getCurrentUser()
            user.put("favorites", temp)
            user.saveInBackground {
                if(it != null){
                    Log.e("detailsFragment", "SaveException: " + it)
                    return@saveInBackground
                }
                Toast.makeText(this, "Successfully saved", Toast.LENGTH_SHORT).show()
            }
        }
    }
}