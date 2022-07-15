package com.example.latihanadvancetestingstarter.data

import com.example.latihanadvancetestingstarter.ApiService
import com.example.latihanadvancetestingstarter.DataDummy
import com.example.latihanadvancetestingstarter.NewsResponse

class FakeApiService : ApiService {

    private val dummyResponse = DataDummy.generateDummyNewsResponse()

    override suspend fun getNews(apiKey: String): NewsResponse {
        return dummyResponse
    }
}