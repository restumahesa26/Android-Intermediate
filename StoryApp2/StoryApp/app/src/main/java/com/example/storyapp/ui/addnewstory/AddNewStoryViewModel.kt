package com.example.storyapp.ui.addnewstory

import androidx.lifecycle.ViewModel
import com.example.storyapp.factory.StoryRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody

class AddNewStoryViewModel(private val storyRepository: StoryRepository) : ViewModel() {

    fun addNewStory(image: MultipartBody.Part, description: RequestBody, lat: RequestBody?, lon: RequestBody?) = storyRepository.addNewStory(image, description, lat, lon)
}