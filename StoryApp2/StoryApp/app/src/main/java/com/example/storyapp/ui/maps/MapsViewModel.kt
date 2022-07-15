package com.example.storyapp.ui.maps

import androidx.lifecycle.ViewModel
import com.example.storyapp.factory.StoryRepository

class MapsViewModel(private val storyRepository: StoryRepository) : ViewModel() {

    fun maps() = storyRepository.showStoryMaps()
}