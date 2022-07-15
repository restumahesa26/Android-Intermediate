package com.example.storyapp.ui.register

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.storyapp.api.ApiConfig
import com.example.storyapp.api.RegisterResponse
import com.example.storyapp.model.UserPreference
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel(private val mContext: Context) : ViewModel() {

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

    fun register(name: String, email: String, password: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().register(name, email, password)
        client.enqueue(object: Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    if (response.body()?.error == false) {
                        _isSuccess.value = true
                        _isMessage.value = response.body()?.message.toString()
                    }
                } else {
                    _isLoading.value = false
                    _isSuccess.value = false
                    _isMessage.value = "Please make sure your input is correct"
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                _isLoading.value = false
                _isSuccess.value = false
                _isMessage.value = "Terjadi kesalahan terhadap server"
            }
        })
    }
}