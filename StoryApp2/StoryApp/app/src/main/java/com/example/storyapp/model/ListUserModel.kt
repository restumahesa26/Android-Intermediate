package com.example.storyapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ListUserModel(
    val id: String,
    val name: String,
    val description: String,
    val photoUrl: String,
    val lat: Double?,
    val lon: Double?
) : Parcelable