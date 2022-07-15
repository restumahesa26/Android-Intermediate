package com.example.storyapp.factory

import android.content.Context
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.storyapp.api.ApiService
import com.example.storyapp.api.StoriesResultResponse
import com.example.storyapp.model.UserPreference

class StoryPagingSource(val apiService: ApiService, val pref: Context) : PagingSource<Int, StoriesResultResponse>() {

    val sharedPreferences: UserPreference by lazy { UserPreference(pref) }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override fun getRefreshKey(state: PagingState<Int, StoriesResultResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, StoriesResultResponse> {
        val position = params.key ?: INITIAL_PAGE_INDEX
        return try {
            val responseData = apiService.getAllStories2(position, params.loadSize, "Bearer ${sharedPreferences.getToken().toString()}")
            val data = responseData.listStory
            LoadResult.Page(
                data = data,
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = if (data.isNullOrEmpty()) null else position + 1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }
}