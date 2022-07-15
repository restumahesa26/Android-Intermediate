package com.example.storyapp.ui.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.storyapp.model.UserPreference
import com.example.storyapp.factory.ViewModelFactory
import com.example.storyapp.ui.liststory.ListStoryActivity
import com.example.storyapp.databinding.ActivityLoginBinding
import com.example.storyapp.helper.Result
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding

    private lateinit var sharedPreferences: UserPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = UserPreference(this)

        setupView()
        setupViewModel()

        setupAction()
        setupAnimation()
        showLoading(false)
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

    private fun setupAnimation() {
        val login = ObjectAnimator.ofFloat(binding.loginBtn, View.ALPHA, 1f).setDuration(500)
        val email = ObjectAnimator.ofFloat(binding.layoutEmailEditText, View.ALPHA, 1f).setDuration(500)
        val password = ObjectAnimator.ofFloat(binding.layoutPasswordEditText, View.ALPHA, 1f).setDuration(500)
        val app = ObjectAnimator.ofFloat(binding.textView, View.ALPHA, 1f).setDuration(500)
        val image = ObjectAnimator.ofFloat(binding.imageView2, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
            playSequentially(app, image, email, password, login)
            start()
        }
    }

    private fun setupViewModel() {
        loginViewModel = ViewModelProvider(
            this,
            ViewModelFactory(this, )
        )[LoginViewModel::class.java]
    }

    private fun setupAction() {
        binding.passwordEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Do nothing
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.length < 6) {
                    binding.passwordEditText.error = "Not less than 6"
                }
            }

            override fun afterTextChanged(s: Editable?) {
                // Do nothing
            }

        })
        binding.emailEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Do nothing
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (!isValidString(s.toString())) {
                    binding.emailEditText.error = "Please make sure your email format"
                }
            }

            override fun afterTextChanged(s: Editable?) {
                // Do nothing
            }

        })
        binding.loginBtn.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            when {
                email.isEmpty() -> {
                    binding.emailEditText.error = "Please fill your email"
                }
                !isValidString(email) -> {
                    binding.emailEditText.error = "Please make sure your email format"
                }
                password.isEmpty() -> {
                    binding.passwordEditText.error = "Please fill your password"
                }
                password.length < 6 -> {
                    binding.passwordEditText.error = "Not less than 6"
                }
                else -> {
                    loginViewModel.login(email, password).observe(this,  { result ->
                        if (result != null) {
                            when(result) {
                                is Result.Loading -> {
                                    binding.progressBar.visibility = View.VISIBLE
                                }
                                is Result.Success -> {
                                    val intent = Intent(this, ListStoryActivity::class.java)
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                    intent.putExtra("EXIT", true)
                                    startActivity(intent)
                                    finish()
                                }
                                is Result.Error -> {
                                    Toast.makeText(this@LoginActivity, "Gagal Login", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    })
                }
            }
        }
    }

    val EMAIL_ADDRESS_PATTERN = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )

    fun isValidString(str: String): Boolean{
        return EMAIL_ADDRESS_PATTERN.matcher(str).matches()
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}