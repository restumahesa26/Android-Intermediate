package com.example.storyapp.di

import android.content.Context
import com.example.storyapp.api.ApiConfig
import com.example.storyapp.factory.AuthenticationRepository
import com.example.storyapp.factory.StoryRepository

object Injection {
    fun provideRepository(context: Context): StoryRepository {
        val apiService = ApiConfig.getApiService()
        return StoryRepository.getInstance(apiService, context)
    }

    fun loginRepository(context: Context): AuthenticationRepository {
        val apiService = ApiConfig.getApiService()
        return AuthenticationRepository.getInstance(apiService, context)
    }
}