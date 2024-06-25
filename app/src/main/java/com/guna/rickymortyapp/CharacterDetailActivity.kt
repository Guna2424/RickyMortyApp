package com.guna.rickymortyapp

import android.content.ContentValues.TAG
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.json.JSONObject

class CharacterDetailActivity : AppCompatActivity() {
    private lateinit var mImageView: ImageView
    private lateinit var mStatus: TextView
    private lateinit var mGender: TextView
    private lateinit var mType: TextView
    private lateinit var mSpecies: TextView
    private lateinit var mLocation: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_character_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        mImageView = findViewById(R.id.imageview)
        mStatus = findViewById(R.id.status)
        mGender = findViewById(R.id.gender)
        mType = findViewById(R.id.type)
        mSpecies = findViewById(R.id.species)
        mLocation = findViewById(R.id.location)

        val status :String = intent.getStringExtra("status").toString()
        val gender :String = intent.getStringExtra("gender").toString()
        val type :String = intent.getStringExtra("type").toString()
        val species :String = intent.getStringExtra("species").toString()
        val imageview :String = intent.getStringExtra("imageview").toString()
        val location :String = intent.getStringExtra("location").toString()

        Log.e(TAG, location)

        Glide.with(this)
            .load(imageview)
            .into(mImageView)

        mStatus.text =status
        mGender.text =gender
        mType.text = if(type.contains("")) "NONE" else type
        mSpecies.text =species

        val responseJson = JSONObject(location)
        val loc = responseJson.getString("name")

        mLocation.text = loc.toString()

    }
}