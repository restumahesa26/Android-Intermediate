package com.example.storyapp

import androidx.paging.PagingData
import com.example.storyapp.api.*

object DataDummy {
    fun generateDummyListStory(): PagingData<StoriesResultResponse> {
        val storyList = ArrayList<StoriesResultResponse>()
        for (i in 0..10) {
            val story = StoriesResultResponse(
                "Id ${i}",
                "Story ke $i+1",
                "Description story ke ${i+1}",
                "https://story-api.dicoding.dev/images/stories/photos-1650496556202_fLUJpd6n.jpg",
            )
            storyList.add(story)
        }
        return PagingData.from(storyList)
    }

    fun generateDummyListStoryResponse(): StoriesResponse {
        val storyList = ArrayList<StoriesResultResponse>()
        for (i in 0..10) {
            val story = StoriesResultResponse(
                "Id ${i}",
                "Story ke $i+1",
                "Description story ke ${i+1}",
                "https://story-api.dicoding.dev/images/stories/photos-1650496556202_fLUJpd6n.jpg",
            )
            storyList.add(story)
        }
        val response = StoriesResponse(
            false,
            "Stories fetched succesfully",
            storyList
        )
        return response
    }

    fun generateEmptyListStory(): PagingData<StoriesResultResponse> {
        return PagingData.empty()
    }

    fun generateMapsStory(): List<StoriesMapsResultResponse> {
        val storyList = ArrayList<StoriesMapsResultResponse>()
        for (i in 0..10) {
            val story = StoriesMapsResultResponse(
                "Id ${i}",
                "Story ke $i+1",
                "Description story ke ${i+1}",
                1213.60,
                1313.80
            )
            storyList.add(story)
        }
        val storyList2 = storyList.map {
            StoriesMapsResultResponse(
                it.id,
                it.name,
                it.description,
                it.lat,
                it.lon
            )
        }

        return storyList2
    }

    fun generateMapsStoryResponse(): StoriesMapsResponse {
        val storyList = ArrayList<StoriesMapsResultResponse>()
        for (i in 0..10) {
            val story = StoriesMapsResultResponse(
                "Id ${i}",
                "Story ke $i+1",
                "Description story ke ${i+1}",
                1213.60,
                1313.80
            )
            storyList.add(story)
        }
        val storyList2 = storyList.map {
            StoriesMapsResultResponse(
                it.id,
                it.name,
                it.description,
                it.lat,
                it.lon
            )
        }
        val response = StoriesMapsResponse(
            false,
            "Stories fetched successfully",
            storyList2
        )

        return response
    }

    fun addNewStoryResponse(): AddNewStoryResponse {
        val result = AddNewStoryResponse(
            false,
            "Story created successfully"
        )

        return result
    }

    fun loginResponse(): LoginResponse {
        val result = LoginResponse(
            false,
            "success",
            LoginResultResponse(
                "user-sET4engGQTH7Aw_I",
                "Mufti Restu Mahesa",
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLXNFVDRlbmdHUVRIN0F3X0kiLCJpYXQiOjE2NTA2MzMwMzB9.nENqPAvqCdGBr1SqYDAO53FXNw4qPx2nGOg-NQYEdAE",
            )
        )

        return result
    }

    fun registerResponse(): RegisterResponse {
        val result = RegisterResponse(
            false,
            "User Created"
        )

        return result
    }
}