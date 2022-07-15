package com.example.storyapp.factory

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.storyapp.api.ApiService
import com.example.storyapp.api.LoginResponse
import com.example.storyapp.helper.Result
import com.example.storyapp.api.RegisterResponse
import com.example.storyapp.helper.Constanta
import com.example.storyapp.helper.wrapEspressoIdlingResource
import com.example.storyapp.model.UserPreference
import java.lang.Exception

class AuthenticationRepository constructor(
    private val apiService: ApiService,
    private val context: Context
    ) {

    val sharedPreferences: UserPreference by lazy { UserPreference(context) }

    fun login(email: String, password: String): LiveData<Result<LoginResponse>> = liveData {
        emit(Result.Loading)
        wrapEspressoIdlingResource {
            try {
                val response = apiService.login(email, password)
                val body = response.loginResult

                val name = body.name
                val userId = body.userId
                val token = body.token

                sharedPreferences.put(Constanta.NAME, name)
                sharedPreferences.put(Constanta.USER_ID, userId)
                sharedPreferences.put(Constanta.TOKEN, token)

                emit(Result.Success(response))
            }catch (e: Exception) {
                emit(Result.Error(e.message.toString()))
            }
        }
    }

    fun register(nama: String, email: String, password: String): LiveData<Result<RegisterResponse>> = liveData {
        emit(Result.Loading)
        wrapEspressoIdlingResource {
            try {
                val response = apiService.register(nama, email, password)

                emit(Result.Success(response))
            }catch (e: Exception) {
                emit(Result.Error(e.message.toString()))
            }
        }
    }

    companion object {
        @Volatile
        private var instance: AuthenticationRepository? = null
        fun getInstance(
            apiService: ApiService,
            context: Context
        ): AuthenticationRepository =
            instance ?: synchronized(this) {
                instance ?: AuthenticationRepository(apiService, context)
            }.also { instance = it }
    }
}