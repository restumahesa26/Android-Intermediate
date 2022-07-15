package com.example.storyapp.ui.login

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.storyapp.api.ApiConfig
import com.example.storyapp.api.LoginResponse
import com.example.storyapp.helper.Constanta
import com.example.storyapp.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(private val mContext: Context) : ViewModel() {

    private lateinit var sharedPreferences: UserPreference

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> = _isSuccess

    private val _isMessage = MutableLiveData<String>()
    val isMessage: LiveData<String> = _isMessage

    init {
        sharedPreferences = UserPreference(mContext)
    }

    fun checkLogin(email: String, password: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().login(email, password)
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    if (response.body()?.error == false) {
                        val name = response.body()?.loginResult?.name.toString()
                        val userId = response.body()?.loginResult?.userId.toString()
                        val token = response.body()?.loginResult?.token.toString()

                        sharedPreferences.put(Constanta.NAME, name)
                        sharedPreferences.put(Constanta.USER_ID, userId)
                        sharedPreferences.put(Constanta.TOKEN, token)

                        _isSuccess.value = true
                        _isMessage.value = response.body()?.message.toString()
                    }
                } else {
                    _isLoading.value = false
                    _isSuccess.value = false
                    _isMessage.value = "Email and Password does not match"
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _isLoading.value = false
                _isSuccess.value = false
                _isMessage.value = "Terjadi kesalahan terhadap server"
            }
        })
    }
}