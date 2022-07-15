package com.example.storyapp.api

import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*
import java.util.ArrayList

data class RegisterResponse(
    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)

data class LoginResponse(
    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("loginResult")
    val loginResult: LoginResultResponse,
)

data class LoginResultResponse(
    @field:SerializedName("userId")
    val userId: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("token")
    val token: String,
)

data class StoriesResponse(
    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("listStory")
    val listStory: ArrayList<StoriesResultResponse>,
)

data class StoriesMapsResponse(
    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("listStory")
    val listStory: List<StoriesMapsResultResponse>,
)

data class StoriesResultResponse(
    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("description")
    val description: String,

    @field:SerializedName("photoUrl")
    val photoUrl: String? = null,
)

data class StoriesMapsResultResponse(
    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("description")
    val description: String,

    @field:SerializedName("lat")
    val lat: Double,

    @field:SerializedName("lon")
    val lon: Double,
)

data class AddNewStoryResponse(
    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)

interface ApiService {
    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ) : LoginResponse

    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ) : RegisterResponse

    @GET("stories")
    suspend fun getAllStories2(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Header("Authorization") token: String
    ): StoriesResponse

    @Multipart
    @POST("stories")
    suspend fun addNewStory(
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
        @Part("lat") lat: RequestBody?,
        @Part("lon") lon: RequestBody?,
        @Header("Authorization") token: String,
    ): AddNewStoryResponse

    @GET("stories?location=1")
    suspend fun getAllStoriesMaps(
        @Header("Authorization") token: String)
    : StoriesMapsResponse
}