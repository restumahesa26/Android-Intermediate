package com.example.storyapp.ui.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.storyapp.api.LoginResponse
import com.example.storyapp.factory.AuthenticationRepository
import com.example.storyapp.helper.Result
import com.example.storyapp.DataDummy
import com.example.storyapp.utils.MainCoroutineRule
import com.example.storyapp.getOrAwaitValue
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
class LoginViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var authenticationRepository: AuthenticationRepository
    private lateinit var loginViewModel: LoginViewModel
    private val dataDummy = DataDummy.loginResponse()

    @Before
    fun setUp() {
        loginViewModel = LoginViewModel(authenticationRepository)
    }

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Test
    fun `login using correct username & password`() = mainCoroutineRule.runBlockingTest {
        val email = "anonim@gmail.com"
        val password = "password"

        val expectedResponse = MutableLiveData<Result<LoginResponse>>()
        expectedResponse.value = Result.Success(dataDummy)

        `when`(loginViewModel.login(email, password)).thenReturn(expectedResponse)

        val login = loginViewModel.login(email, password).getOrAwaitValue()
        Mockito.verify(authenticationRepository).login(email, password)
        Assert.assertNotNull(login)
        Assert.assertTrue(login is Result.Success)
    }

    @Test
    fun `login using incorrect username & password`() = mainCoroutineRule.runBlockingTest {
        val email = "anonim@gmail.com"
        val password = "password12345"

        val expectedResponse = MutableLiveData<Result<LoginResponse>>()
        expectedResponse.value = Result.Error("Email and Password does not match")

        `when`(loginViewModel.login(email, password)).thenReturn(expectedResponse)

        val login = loginViewModel.login(email, password).getOrAwaitValue()
        Mockito.verify(authenticationRepository).login(email, password)
        Assert.assertNotNull(login)
        Assert.assertTrue(login is Result.Error)
    }
}