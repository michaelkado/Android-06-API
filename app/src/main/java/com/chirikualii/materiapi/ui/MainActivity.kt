package com.chirikualii.materiapi.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.chirikualii.materiapi.R
import com.chirikualii.materiapi.data.dummy.DataDummy
import com.chirikualii.materiapi.data.model.Movie
import com.chirikualii.materiapi.data.remote.APIClient
import com.chirikualii.materiapi.databinding.ActivityMainBinding
import com.chirikualii.materiapi.ui.adapter.MovieListAdapter
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding :ActivityMainBinding
    private lateinit var adapter: MovieListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //setup adapter
        adapter = MovieListAdapter()
        binding.rvMovie.adapter = adapter

        GlobalScope.launch(Dispatchers.IO) {
            val response = APIClient.service.getMovie(
                page = "8"
            )
            Log.d(MainActivity::class.java.name,"List Movie: ${Gson().toJsonTree(response.body())}")
            response
            val data = response.body()
            val listmovie = data?.results?.map { result ->
                Movie(
                    title = result.title,
                    genre = result.releaseDate,
                    imagePoster = result.posterPath
                )
            }
            withContext(Dispatchers.Main){
            if (listmovie != null) {
                adapter.addItem(listmovie)
            }

            }
        }
    }
}