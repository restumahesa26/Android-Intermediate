package com.example.storyapp.ui.addnewstory

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.storyapp.DataDummy
import com.example.storyapp.api.AddNewStoryResponse
import com.example.storyapp.factory.StoryRepository
import com.example.storyapp.getOrAwaitValue
import com.example.storyapp.utils.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner
import java.io.File
import com.example.storyapp.helper.Result
import org.junit.Assert
import org.mockito.Mockito
import org.mockito.Mockito.`when`

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class AddNewStoryViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var storyRepository: StoryRepository
    private lateinit var addNewStoryViewModel: AddNewStoryViewModel
    private val file = mock(File::class.java)
    private val description = "desc".toString().toRequestBody("text/plain".toMediaType())
    private val description2 = "desc".toString().toRequestBody("image/*".toMediaType())
    private val lat = "2390.3".toString().toRequestBody("text/plain".toMediaType())
    private val lon = "2390.3".toString().toRequestBody("text/plain".toMediaType())
    private val requestImageFile = file.asRequestBody("image/*".toMediaTypeOrNull())
    private val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
        "photo", file.name, requestImageFile
    )
    private val dataDummy = DataDummy.addNewStoryResponse()

    @Before
    fun setUp() {
        addNewStoryViewModel = AddNewStoryViewModel(storyRepository)
    }

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Test
    fun `when add new story is success`() = mainCoroutineRule.runBlockingTest {
        val expectedNewStory = MutableLiveData<Result<AddNewStoryResponse>>()
        expectedNewStory.value = Result.Success(dataDummy)

        `when`(addNewStoryViewModel.addNewStory(imageMultipart, description, lat,  lon)).thenReturn(expectedNewStory)

        val addNewStory = addNewStoryViewModel.addNewStory(imageMultipart, description, lat, lon).getOrAwaitValue()
        Mockito.verify(storyRepository).addNewStory(imageMultipart, description, lat, lon)
        Assert.assertNotNull(addNewStory)
        Assert.assertTrue(addNewStory is Result.Success)
    }

    @Test
    fun `when add new story is error`() = mainCoroutineRule.runBlockingTest {
        val expectedNewStory = MutableLiveData<Result<AddNewStoryResponse>>()
        expectedNewStory.value = Result.Error("Error")

        `when`(addNewStoryViewModel.addNewStory(imageMultipart, description2, lat, lon)).thenReturn(expectedNewStory)

        val addNewStory = addNewStoryViewModel.addNewStory(imageMultipart, description2, lat, lon).getOrAwaitValue()
        Mockito.verify(storyRepository).addNewStory(imageMultipart, description2, lat, lon)
        Assert.assertNotNull(addNewStory)
        Assert.assertTrue(addNewStory is Result.Error)
    }
}