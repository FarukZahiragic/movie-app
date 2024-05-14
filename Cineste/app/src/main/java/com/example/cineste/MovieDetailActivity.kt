package com.example.cineste

import android.app.SearchManager
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class MovieDetailActivity : AppCompatActivity() {
    private lateinit var movie: Movie
    private lateinit var title : TextView
    private lateinit var overview : TextView
    private lateinit var releaseDate : TextView
    private lateinit var genre : TextView
    private lateinit var website : TextView
    private lateinit var poster : ImageView
    private lateinit var share : FloatingActionButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        title = findViewById(R.id.movie_title)
        overview = findViewById(R.id.movie_overview)
        releaseDate = findViewById(R.id.movie_release_date)
        genre = findViewById(R.id.movie_genre)
        poster = findViewById(R.id.movie_poster)
        website = findViewById(R.id.movie_website)
        share = findViewById(R.id.movie_share_button)
        movie = Movie(0,"Test","Test","Test","Test","Test", "", "")
        val extras = intent.extras
        if (extras != null) {
            if (extras.containsKey("movie_id")) {
                getMovieById(extras.getLong("movie_id"))
                populateDetails()
            }
            else  {
                movie = getMovieByTitle(extras.getString("movie_title",""))
                populateDetails()
            }
        }
        else {
            finish()
        }

        website.setOnClickListener{
            showWebsite()
        }

        title.setOnClickListener() {
            searchTitle()
        }

        share.setOnClickListener() {
            shareMovieInfo()
        }
    }
    private fun populateDetails() {
        title.text=movie.title
        releaseDate.text=movie.releaseDate
        genre.text=movie.genre
        website.text=movie.homepage
        overview.text=movie.overview
        val context: Context = poster.context
        var id: Int = context.resources
            .getIdentifier(movie.genre, "drawable", context.packageName)
        if (id==0) id=context.resources
            .getIdentifier("picture1", "drawable", context.packageName)
        poster.setImageResource(id)
    }
    private fun getMovieByTitle(name:String):Movie{
        val movies: ArrayList<Movie> = arrayListOf()
        movies.addAll(getRecentMovies())
        movies.addAll(getFavoriteMovies())
        val movie= movies.find { movie -> name == movie.title }
        return movie?:Movie(0,"Test","Test","Test","Test","Test", "", "")
    }

    private fun getMovieById(id: Long){
        val scope = CoroutineScope(Job() + Dispatchers.Main)
        // Kreira se Coroutine na UI
        scope.launch{
            // Vrti se poziv servisa i suspendira se rutina dok se `withContext` ne zavrsi
            // Prikaze se rezultat korisniku na glavnoj niti
            when (val result = MovieRepository.detailRequest(id)) {
                is Result.Success<Movie> -> {
                    movie = result.data
                }

                is Result.Error -> TODO()
            }
        }
    }



    private fun showWebsite(){
        val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(movie.homepage))
        try {
            startActivity(webIntent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, "Missing search engine!", Toast.LENGTH_SHORT).show();
        }
    }

    private fun searchTitle() {
        val viewSearch = Intent(Intent.ACTION_WEB_SEARCH)
        viewSearch.putExtra(SearchManager.QUERY, movie.title + " trailer")
        try {
            startActivity(viewSearch)
        }
        catch (e: ActivityNotFoundException) {
            Toast.makeText(this, "Missing search engine!", Toast.LENGTH_SHORT).show();
        }
    }

    private fun shareMovieInfo() {
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.setType("text/plain")
        sharingIntent.putExtra(Intent.EXTRA_TEXT, movie.overview)
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, movie.title + " movie details")
        try {
            startActivity(Intent.createChooser(sharingIntent, "Share using"))
        }
        catch (e: ActivityNotFoundException) {
            Toast.makeText(this, "Missing share app!", Toast.LENGTH_SHORT).show();
        }
    }


}