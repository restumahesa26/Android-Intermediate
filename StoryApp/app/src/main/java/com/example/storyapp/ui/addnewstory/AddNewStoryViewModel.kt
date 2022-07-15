package com.example.storyapp.ui.addnewstory

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.storyapp.api.AddNewStoryResponse
import com.example.storyapp.api.ApiConfig
import com.example.storyapp.model.UserPreference
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddNewStoryViewModel(mContext: Context) : ViewModel() {

    val sharedPreferences: UserPreference by lazy { UserPreference(mContext) }

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> = _isSuccess

    private val _isMessage = MutableLiveData<String>()
    val isMessage: LiveData<String> = _isMessage

    fun addNewStory(image: MultipartBody.Part, description: RequestBody) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().addNewStory(token = "Bearer ${sharedPreferences.getToken()}", image, description)
        client.enqueue(object : Callback<AddNewStoryResponse> {
            override fun onResponse(
                call: Call<AddNewStoryResponse>,
                response: Response<AddNewStoryResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    _isLoading.value = false
                    if (responseBody?.error == false) {
                        _isSuccess.value = true
                        _isMessage.value = response.body()?.message.toString()
                    }
                } else {
                    _isLoading.value = false
                    _isSuccess.value = false
                    _isMessage.value = "Error, please make sure your input"
                }
            }
            override fun onFailure(call: Call<AddNewStoryResponse>, t: Throwable) {
                _isLoading.value = false
                _isSuccess.value = false
                _isMessage.value = "Terjadi kesalahan terhadap server"
            }
        })
    }
}