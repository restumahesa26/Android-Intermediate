package com.example.storyapp.ui.login

import androidx.lifecycle.ViewModel
import com.example.storyapp.factory.AuthenticationRepository

class LoginViewModel(private val loginRepository: AuthenticationRepository) : ViewModel() {

    fun login(email: String, password: String) = loginRepository.login(email, password)

}