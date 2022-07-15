package com.example.storyapp.ui.liststory

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.storyapp.api.ApiConfig
import com.example.storyapp.api.StoriesResponse
import com.example.storyapp.helper.Constanta
import com.example.storyapp.model.ListUserModel
import com.example.storyapp.model.UserPreference
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListStoryViewModel(mContext: Context) : ViewModel() {

    private val PREFS_NAME = "user_preferences"
    val listUser = ArrayList<ListUserModel>()

    private val _listUsers = MutableLiveData<ArrayList<ListUserModel>>()
    val listUsers: LiveData<ArrayList<ListUserModel>> = _listUsers

    val listUserArray = ArrayList<ListUserModel>()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isMessage = MutableLiveData<String>()
    val isMessage: LiveData<String> = _isMessage

    val sharedPreferences: UserPreference by lazy { UserPreference(mContext) }

    init {
        showAllStories()
    }

    fun showAllStories() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getAllStories(token = "Bearer ${sharedPreferences.getToken()}")
        client.enqueue(object : Callback<StoriesResponse> {
            override fun onResponse(
                call: Call<StoriesResponse>,
                response: Response<StoriesResponse>
            ) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        _isLoading.value = false
                        if (response.body()?.error == false) {
                            for (i in response.body()?.listStory!!) {
                                val user = ListUserModel(i.id, i.name, i.description, i.photoUrl)
                                listUserArray.add(user)
                            }
                            _listUsers.value = listUserArray
                        }
                    }
                }
            }
            override fun onFailure(call: Call<StoriesResponse>, t: Throwable) {
                _isLoading.value = false
                _isMessage.value = "Terjadi kesalahan terhadap server"
            }
        })
    }

    fun logout() {
        sharedPreferences.put(Constanta.NAME, "")
        sharedPreferences.put(Constanta.USER_ID, "")
        sharedPreferences.put(Constanta.TOKEN, "")
    }
}