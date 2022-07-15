package com.example.storyapp.ui.liststory

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import com.example.storyapp.api.StoriesResultResponse
import com.example.storyapp.factory.StoryRepository
import com.example.storyapp.DataDummy
import com.example.storyapp.getOrAwaitValue
import com.example.storyapp.utils.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ListStoryViewModelTest() {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var storyRepository: StoryRepository
    private lateinit var listStoryViewModel: ListStoryViewModel
    private val dummyStory = DataDummy.generateDummyListStory()
    private val dummyStory2 = DataDummy.generateEmptyListStory()

    @Before
    fun setUp() {
        listStoryViewModel = ListStoryViewModel(storyRepository)
    }

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Test
    fun `when get List Story Should Not Null and Return Success`() = mainCoroutineRule.runBlockingTest {
        val expectedStory = MutableLiveData<PagingData<StoriesResultResponse>>()
        expectedStory.value = dummyStory

        `when`(listStoryViewModel.story()).thenReturn(expectedStory)

        val actualStory = listStoryViewModel.story().getOrAwaitValue()
        Mockito.verify(storyRepository).showStory()
        Assert.assertNotNull(actualStory)
        Assert.assertEquals(dummyStory, actualStory)
    }

    @Test
    fun `when get List Story Null and Return Error`() = mainCoroutineRule.runBlockingTest {
        val expectedStory = MutableLiveData<PagingData<StoriesResultResponse>>()
        expectedStory.value = dummyStory2

        `when`(listStoryViewModel.story()).thenReturn(expectedStory)

        val actualStory = listStoryViewModel.story().getOrAwaitValue()
        Mockito.verify(storyRepository).showStory()
        Assert.assertNotNull(actualStory)
        Assert.assertEquals(dummyStory2, actualStory)
    }
}