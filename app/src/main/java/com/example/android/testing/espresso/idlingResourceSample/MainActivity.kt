package com.example.android.testing.espresso.idlingResourceSample

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.squareup.picasso.Picasso

class MainActivity : Activity() {

    private lateinit var image: ImageView
    private lateinit var btn: Button
    private val imageLoader = App.appComponent.imageLoader()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        image = findViewById(R.id.image)
        btn = findViewById(R.id.btn)


        btn.setOnClickListener {
            imageLoader.load("https://upload.wikimedia.org/wikipedia/commons/6/6a/PNG_Test.png").into(image)
        }
    }
}