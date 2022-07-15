package com.example.storyapp.factory

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.storyapp.DataDummy
import com.example.storyapp.api.ApiService
import com.example.storyapp.api.LoginResponse
import com.example.storyapp.data.FakeApiService
import com.example.storyapp.getOrAwaitValue
import com.example.storyapp.utils.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import com.example.storyapp.helper.Result

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class AuthenticationRepositoryTest {

    @Mock
    private lateinit var mockContext: Context
    private lateinit var apiService: ApiService
    private lateinit var authenticationRepository: AuthenticationRepository

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setUp() {
        mockContext = Mockito.mock(Context::class.java)
        apiService = FakeApiService()
        authenticationRepository = AuthenticationRepository(apiService, mockContext)
    }

    @Test
    fun `when login can work correctly`() = mainCoroutineRule.runBlockingTest {
        val email = "anonim@gmail.com"
        val password = "password"

        val expectedLogin = DataDummy.loginResponse()
        val actualLogin = apiService.login(email, password)
        Assert.assertNotNull(actualLogin)
        Assert.assertEquals(expectedLogin.loginResult, actualLogin.loginResult)
    }

    @Test
    fun `when register can work correctly`() = mainCoroutineRule.runBlockingTest {
        val nama = "Nama"
        val email = "nama@gmail.com"
        val password = "password@gmail.com"

        val expectedRegister = DataDummy.registerResponse()
        val actualRegister = apiService.register(nama, email, password)
        Assert.assertNotNull(actualRegister)
        Assert.assertEquals(expectedRegister.error, expectedRegister.error)
    }

}