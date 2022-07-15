package com.example.storyapp.factory

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.storyapp.DataDummy
import com.example.storyapp.api.ApiService
import com.example.storyapp.data.FakeApiService
import com.example.storyapp.utils.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner
import java.io.File

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class StoryRepositoryTest {

    @Mock
    private lateinit var mockContext: Context
    private val file = mock(File::class.java)
    private val description = "desc".toString().toRequestBody("text/plain".toMediaType())
    private val lat = "2390.3".toString().toRequestBody("text/plain".toMediaType())
    private val lon = "2390.3".toString().toRequestBody("text/plain".toMediaType())
    private val requestImageFile = file.asRequestBody("image/*".toMediaTypeOrNull())
    private val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
        "photo", file.name, requestImageFile
    )

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var apiService: ApiService
    private lateinit var storyRepository: StoryRepository

    @Before
    fun setUp() {
        mockContext = mock(Context::class.java)
        apiService = FakeApiService()
        storyRepository = StoryRepository(apiService, mockContext)
    }

    @Test
    fun `when story list not null`() = mainCoroutineRule.runBlockingTest {
        val expectedStory = DataDummy.generateDummyListStoryResponse()
        val actualStory = apiService.getAllStories2(1,20,"apiKey")
        Assert.assertNotNull(actualStory)
        Assert.assertEquals(expectedStory.listStory.size, actualStory.listStory.size)
    }

    @Test
    fun `when story maps not null`() = mainCoroutineRule.runBlockingTest {
        val expectedMaps = DataDummy.generateMapsStoryResponse()
        val actualMaps = apiService.getAllStoriesMaps("apiKey")
        Assert.assertNotNull(actualMaps)
        Assert.assertEquals(expectedMaps.listStory.size, actualMaps.listStory.size)
    }

    @Test
    fun `when add new story can work correctly`() = mainCoroutineRule.runBlockingTest {
        val expectedAddStory = DataDummy.addNewStoryResponse()
        val actualAddStory = apiService.addNewStory(imageMultipart, description, lat, lon, "apiKey")
        Assert.assertNotNull(actualAddStory)
        Assert.assertEquals(expectedAddStory.error, actualAddStory.error)
    }
}