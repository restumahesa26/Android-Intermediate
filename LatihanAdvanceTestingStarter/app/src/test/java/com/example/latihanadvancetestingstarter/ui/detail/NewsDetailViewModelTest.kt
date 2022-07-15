package com.example.latihanadvancetestingstarter.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.latihanadvancetestingstarter.MainCoroutineRule
import com.example.latihanadvancetestingstarter.NewsDetailViewModel
import com.example.latihanadvancetestingstarter.NewsRepository
import com.example.latihanadvancetestingstarter.DataDummy
import com.example.latihanadvancetestingstarter.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
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
class NewsDetailViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var newsRepository: NewsRepository
    private lateinit var newsDetailViewModel: NewsDetailViewModel
    private val dummyDetailNews = DataDummy.generateDummyNewsEntity()[0]

    @Before
    fun setUp() {
        newsDetailViewModel = NewsDetailViewModel(newsRepository)
        newsDetailViewModel.setNewsData(dummyDetailNews)
    }

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Test
    fun `when bookmarkStatus false Should call saveNews`() = mainCoroutineRule.runBlockingTest {
        val expectedBoolean = MutableLiveData<Boolean>()
        expectedBoolean.value = false
        `when`(newsRepository.isNewsBookmarked(dummyDetailNews.title)).thenReturn(expectedBoolean)
        newsDetailViewModel.bookmarkStatus.getOrAwaitValue()
        newsDetailViewModel.changeBookmark(dummyDetailNews)
        Mockito.verify(newsRepository).saveNews(dummyDetailNews)
    }

    @Test
    fun `when bookmarkStatus true Should call deleteNews`() = mainCoroutineRule.runBlockingTest {
        val expectedBoolean = MutableLiveData<Boolean>()
        expectedBoolean.value = true
        `when`(newsRepository.isNewsBookmarked(dummyDetailNews.title)).thenReturn(expectedBoolean)
        newsDetailViewModel.bookmarkStatus.getOrAwaitValue()
        newsDetailViewModel.changeBookmark(dummyDetailNews)
        Mockito.verify(newsRepository).deleteNews(dummyDetailNews.title)
    }
}