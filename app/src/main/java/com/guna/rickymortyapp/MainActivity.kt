package com.guna.rickymortyapp

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.guna.rickymortyapp.Data.CharacterData
import com.guna.rickymortyapp.Presentation.RecyclerAdapter
import guna.pseudo.mobile.Domain.SendRequest
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject


class MainActivity : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerview: RecyclerView
    lateinit var context: Context
    lateinit var articless: List<CharacterData>
    private lateinit var articleAdapter: RecyclerAdapter
    private lateinit var adapter: RecyclerAdapter
    private var layoutManager: GridLayoutManager? = null

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?){
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContentView(R.layout.activity_main)

    actionBar?.hide()
    supportActionBar?.hide()

    progressBar = findViewById(R.id.progressBar)
    recyclerview = findViewById(R.id.recyclerview)

    showLoader()

    GlobalScope.launch {
        val response = SendRequest.sendRequest("")
        Log.e(TAG, "onStart: $response" )

        withContext(Dispatchers.Main) {
            hideLoader()
            if (response != null) {
                val gson = Gson()
                val responseJson = JSONObject(response)
                val articlesJsonArray = responseJson.getJSONArray("results")

                val articles = mutableListOf<CharacterData>()

                for (i in 0 until articlesJsonArray.length()) {
                    val articleJson = articlesJsonArray.getJSONObject(i)
                    val name = articleJson.getString("name")
                    val image = articleJson.getString("image")
                    val status = articleJson.getString("status")
                    val species = articleJson.getString("species")
                    val gender = articleJson.getString("gender")
                    val id = articleJson.getString("id")
                    val location = articleJson.getString("location")
                    val type = articleJson.getString("type")

                    val article = CharacterData(name, image, status, species, gender, id,location,type)
                    articles.add(article)
                }
                updateUi(articles)

            }
        }
    }

    layoutManager = GridLayoutManager(this, 2)
    recyclerview.layoutManager = layoutManager

    adapter = RecyclerAdapter(this@MainActivity , emptyList())
    recyclerview.adapter = adapter

    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main))
    {
        v, insets ->
        val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
        v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
        insets
    }

}

    @OptIn(DelicateCoroutinesApi::class)
    override fun onStart() {
        super.onStart()
    }

    private fun showLoader() {
        progressBar.visibility = View.VISIBLE
    }

    private fun hideLoader() {
        progressBar.visibility = View.GONE
    }

    private fun updateUi(articles: List<CharacterData>) {
        adapter.characterData = articles
        adapter.notifyDataSetChanged()
    }

}