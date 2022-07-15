package com.example.storyapp.ui.maps

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.storyapp.DataDummy
import com.example.storyapp.api.StoriesMapsResultResponse
import com.example.storyapp.factory.StoryRepository
import com.example.storyapp.getOrAwaitValue
import com.example.storyapp.helper.Result
import com.example.storyapp.utils.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MapsViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var storyRepository: StoryRepository
    private lateinit var mapsViewModel: MapsViewModel
    private val dataDummy = DataDummy.generateMapsStory()

    @Before
    fun setUp() {
        mapsViewModel = MapsViewModel(storyRepository)
    }

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Test
    fun `when maps not null and return success`() = mainCoroutineRule.runBlockingTest {
        val expectedMaps = MutableLiveData<Result<List<StoriesMapsResultResponse>>>()
        expectedMaps.value = Result.Success(dataDummy)

        `when`(mapsViewModel.maps()).thenReturn(expectedMaps)

        val actualMap = mapsViewModel.maps().getOrAwaitValue()
        Mockito.verify(storyRepository).showStoryMaps()
        Assert.assertNotNull(actualMap)
        Assert.assertTrue(actualMap is Result.Success)
    }

    @Test
    fun `when maps null and return error`() = mainCoroutineRule.runBlockingTest {
        val expectedMaps = MutableLiveData<Result<List<StoriesMapsResultResponse>>>()
        expectedMaps.value = Result.Error("Error")

        `when`(mapsViewModel.maps()).thenReturn(expectedMaps)

        val actualMap = mapsViewModel.maps().getOrAwaitValue()
        Mockito.verify(storyRepository).showStoryMaps()
        Assert.assertNotNull(actualMap)
        Assert.assertTrue(actualMap is Result.Error)
    }
}