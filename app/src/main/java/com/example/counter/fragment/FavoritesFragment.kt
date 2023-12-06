package com.example.counter.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.counter.R
import com.example.counter.Retrofithelper
import com.example.counter.activities.detailsActivity
import com.example.counter.adapter.favoriteadapter
import com.example.counter.datamodel.movie
import com.example.counter.datamodel.movieApi
import com.example.counter.datamodel.movieList
import com.parse.ParseUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FavoritesFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view : View = inflater.inflate(R.layout.fragment_favorites, container, false)
        var recyclerView : RecyclerView = view.findViewById(R.id.favRV)
        var movies = ArrayList<movie>()
        lateinit var favoriteAdapter : favoriteadapter

        var onLongClick = object : favoriteadapter.onLongClickListener{
            override fun onItemLongClicked(pos: Int) {
                movies.removeAt(pos)
                var temp = ArrayList<String>()
                temp.addAll(ParseUser.getCurrentUser().getList("favorites")!!)
                temp.removeAt(pos)
                var user = ParseUser.getCurrentUser()
                user.put("favorites", temp)
                user.saveInBackground {
                    if(it != null){
                        Log.e("FavoriteFragment", "SaveException: " + it)
                        return@saveInBackground
                    }
                    Toast.makeText(context, "Successfully removed", Toast.LENGTH_SHORT).show()
                }
                favoriteAdapter.notifyDataSetChanged()
            }
        }

        var onClick = object : favoriteadapter.OnClickListener{
            override fun onItemClicked(mov: movie) {
                var bundle = Bundle()
                bundle.putSerializable("mov", mov)
                var intent = Intent(context, detailsActivity::class.java)
            }
        }

        val moviesApi = Retrofithelper.getInstance().create(movieApi::class.java)
        val call: Call<movieList>? = moviesApi.getMovies()
        call!!.enqueue(object : Callback<movieList> {
            override fun onResponse(call: Call<movieList>, response: Response<movieList>) {
                if (response.isSuccessful()) {
                    if(ParseUser.getCurrentUser().get("favorites") != null){
                        var temp = ArrayList<Int>()
                        temp.addAll(ParseUser.getCurrentUser().getList("favorites")!!)
                        for(x in temp){
                            for(y in response.body()!!.results){
                                if(x == y.id){
                                    movies.add(y)
                                    break
                                }
                            }
                        }
                    }
                    favoriteAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<movieList>, t: Throwable) {
                Log.e("HomeFragment", "Failed Response: " + t.toString())
            }

        })

        favoriteAdapter = favoriteadapter(movies, requireContext(), onClick,onLongClick)
        recyclerView.adapter = favoriteAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        return view
    }
}