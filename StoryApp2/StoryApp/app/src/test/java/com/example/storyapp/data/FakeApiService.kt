package com.example.storyapp.data

import com.example.storyapp.DataDummy
import com.example.storyapp.api.*
import okhttp3.MultipartBody
import okhttp3.RequestBody

class FakeApiService : ApiService {

    private val dummyResponseLogin = DataDummy.loginResponse()
    private val dummyResponseStory = DataDummy.generateDummyListStoryResponse()
    private val dummyResponseMaps = DataDummy.generateMapsStoryResponse()
    private val dummyResponseAddStory = DataDummy.addNewStoryResponse()
    private val dummyResponseRegister = DataDummy.registerResponse()

    override suspend fun login(email: String, password: String): LoginResponse {
        return dummyResponseLogin
    }

    override suspend fun register(name: String, email: String, password: String): RegisterResponse {
        return dummyResponseRegister
    }

    override suspend fun getAllStories2(page: Int, size: Int, token: String): StoriesResponse {
        return dummyResponseStory
    }

    override suspend fun addNewStory(
        file: MultipartBody.Part,
        description: RequestBody,
        lat: RequestBody?,
        lon: RequestBody?,
        token: String
    ): AddNewStoryResponse {
        return dummyResponseAddStory
    }

    override suspend fun getAllStoriesMaps(token: String): StoriesMapsResponse {
        return dummyResponseMaps
    }

}