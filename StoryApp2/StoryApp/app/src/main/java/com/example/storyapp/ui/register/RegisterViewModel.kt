package com.example.storyapp.ui.register

import androidx.lifecycle.ViewModel
import com.example.storyapp.factory.AuthenticationRepository

class RegisterViewModel(private val repository: AuthenticationRepository) : ViewModel() {

    fun register(nama:String, email: String, password: String) = repository.register(nama, email, password)
}