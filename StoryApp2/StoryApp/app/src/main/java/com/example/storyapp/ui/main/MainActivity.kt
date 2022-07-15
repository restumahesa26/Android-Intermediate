package com.example.storyapp.ui.main

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import com.example.storyapp.helper.Constanta
import com.example.storyapp.model.UserPreference
import com.example.storyapp.ui.liststory.ListStoryActivity
import com.example.storyapp.databinding.ActivityMainBinding
import com.example.storyapp.ui.login.LoginActivity
import com.example.storyapp.ui.register.RegisterActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreference: SharedPreferences
    private var token: String? = String()

    private lateinit var sharedPreferences: UserPreference

    val sharedPreferencess: UserPreference by lazy { UserPreference(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = UserPreference(this)

        sharedPreference = getSharedPreferences("user_preferences", Context.MODE_PRIVATE)
        token = sharedPreference.getString(Constanta.TOKEN, null)

        setupView()
        setupAction()
        setupAnimation()

        if (sharedPreferencess.getToken().equals("") || !sharedPreference.contains(Constanta.TOKEN)) {
            // Do Nothing
        }else {
            startActivity(Intent(this, ListStoryActivity::class.java))
            finish()
        }
    }

    private fun setupAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -50f, 50f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val login = ObjectAnimator.ofFloat(binding.loginBtnMain, View.ALPHA, 1f).setDuration(500)
        val register = ObjectAnimator.ofFloat(binding.registerBtnMain, View.ALPHA, 1f).setDuration(500)
        val app = ObjectAnimator.ofFloat(binding.textView, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
            playSequentially(app, login, register)
            start()
        }
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
        supportActionBar?.hide()
    }

    private fun setupAction() {
        binding.loginBtnMain.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        binding.registerBtnMain.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}