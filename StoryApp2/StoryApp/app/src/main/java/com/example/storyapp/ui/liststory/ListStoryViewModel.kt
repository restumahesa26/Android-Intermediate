package com.example.storyapp.ui.liststory

import androidx.lifecycle.ViewModel
import com.example.storyapp.factory.StoryRepository

class ListStoryViewModel(private val storyRepository: StoryRepository) : ViewModel() {

    fun story() = storyRepository.showStory()
}