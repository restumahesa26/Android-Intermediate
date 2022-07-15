package com.example.storyapp.ui.register

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.storyapp.api.RegisterResponse
import com.example.storyapp.DataDummy
import com.example.storyapp.factory.AuthenticationRepository
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
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class RegisterViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var authenticationRepository: AuthenticationRepository
    private lateinit var registerViewModel: RegisterViewModel
    private val dataDummy = DataDummy.registerResponse()

    @Before
    fun setUp() {
        registerViewModel = RegisterViewModel(authenticationRepository)
    }

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Test
    fun `register return success`() = mainCoroutineRule.runBlockingTest {
        val nama = "nama saya"
        val email = "email@gmail.com"
        val password = "password"

        val expectedResponse = MutableLiveData<Result<RegisterResponse>>()
        expectedResponse.value = Result.Success(dataDummy)

        Mockito.`when`(registerViewModel.register(nama, email, password)).thenReturn(expectedResponse)

        val register = registerViewModel.register(nama, email, password).getOrAwaitValue()
        Mockito.verify(authenticationRepository).register(nama, email, password)
        Assert.assertNotNull(register)
        Assert.assertTrue(register is Result.Success)
    }

    @Test
    fun `register return error`() = mainCoroutineRule.runBlockingTest {
        val nama = ""
        val email = "emailgmail"
        val password = "pass"

        val expectedResponse = MutableLiveData<Result<RegisterResponse>>()
        expectedResponse.value = Result.Error("Error Register")

        Mockito.`when`(registerViewModel.register(nama, email, password)).thenReturn(expectedResponse)

        val register = registerViewModel.register(nama, email, password).getOrAwaitValue()
        Mockito.verify(authenticationRepository).register(nama, email, password)
        Assert.assertNotNull(register)
        Assert.assertTrue(register is Result.Error)
    }

}