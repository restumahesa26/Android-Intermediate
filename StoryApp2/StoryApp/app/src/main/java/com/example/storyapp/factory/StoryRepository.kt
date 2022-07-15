package com.example.storyapp.factory

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.storyapp.api.*
import com.example.storyapp.helper.Result
import com.example.storyapp.helper.wrapEspressoIdlingResource
import com.example.storyapp.model.UserPreference
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.lang.Exception

class StoryRepository constructor(
    val apiService: ApiService,
    val context: Context
    ) {

    val sharedPreferences: UserPreference by lazy { UserPreference(context) }

    fun showStory(): LiveData<PagingData<StoriesResultResponse>> {
        return Pager(
            config = PagingConfig(
                pageSize = 3
            ),
            pagingSourceFactory = {
                StoryPagingSource(apiService, context)
            }
        ).liveData
    }

    fun showStoryMaps(): LiveData<Result<List<StoriesMapsResultResponse>>> = liveData {
        wrapEspressoIdlingResource {
            try {
                val response = apiService.getAllStoriesMaps(
                    "Bearer ${
                        sharedPreferences.getToken().toString()
                    }"
                )
                val result = response.listStory

                val mapsStory = result.map { result2 ->
                    StoriesMapsResultResponse(
                        result2.id,
                        result2.name,
                        result2.description,
                        result2.lat,
                        result2.lon
                    )
                }
                emit(Result.Success(mapsStory))
            } catch (e: Exception) {
                emit(Result.Error(e.message.toString()))
            }
        }
    }

    fun addNewStory(image: MultipartBody.Part, description: RequestBody, lat: RequestBody?, lon: RequestBody?): LiveData<Result<AddNewStoryResponse>> = liveData {
        emit(Result.Loading)
        wrapEspressoIdlingResource {
            try {
                val response = apiService.addNewStory(image, description, lat, lon, "Bearer ${sharedPreferences.getToken().toString()}")

                emit(Result.Success(response))
            }catch (e: Exception) {
                emit(Result.Error(e.message.toString()))
            }

        }
    }

    companion object {
        @Volatile
        private var instance: StoryRepository? = null
        fun getInstance(
            apiService: ApiService,
            context: Context
        ): StoryRepository =
            instance ?: synchronized(this) {
                instance ?: StoryRepository(apiService, context)
            }.also { instance = it }
    }
}