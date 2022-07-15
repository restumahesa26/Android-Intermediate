package com.example.storyapp.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.storyapp.ui.addnewstory.AddNewStoryViewModel
import com.example.storyapp.ui.detail.DetailViewModel
import com.example.storyapp.ui.liststory.ListStoryViewModel
import com.example.storyapp.ui.login.LoginViewModel
import com.example.storyapp.ui.register.RegisterViewModel

class ViewModelFactory(private val mContext: Context) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(mContext) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(mContext) as T
            }
            modelClass.isAssignableFrom(ListStoryViewModel::class.java) -> {
                ListStoryViewModel(mContext) as T
            }
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel() as T
            }
            modelClass.isAssignableFrom(AddNewStoryViewModel::class.java) -> {
                AddNewStoryViewModel(mContext) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}