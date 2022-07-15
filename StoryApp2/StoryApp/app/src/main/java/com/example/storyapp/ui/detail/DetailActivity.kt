package com.example.storyapp.ui.detail

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import com.bumptech.glide.Glide
import com.example.storyapp.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    companion object {
        const val NAME_ID = "name_id"
        const val DESCRIPTION_ID = "description_id"
        const val PHOTO_URL_ID = "photo_url_id"
    }

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAction()
        setupView()
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
    }

    private fun setupAction() {
        val name = intent.getStringExtra(NAME_ID)
        val description = intent.getStringExtra(DESCRIPTION_ID)
        val photoUrl = intent.getStringExtra(PHOTO_URL_ID)

        binding.tvNameDetail.text = name
        binding.tvDescriptionDetail.text = description
        Glide.with(this)
            .load(photoUrl)
            .circleCrop()
            .into(binding.imageDetail)

        supportActionBar?.title = name
    }
}