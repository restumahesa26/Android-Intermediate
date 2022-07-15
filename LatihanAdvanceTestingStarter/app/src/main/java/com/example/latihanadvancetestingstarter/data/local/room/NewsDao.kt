package com.example.latihanadvancetestingstarter

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NewsDao {
    @Query("SELECT * FROM news")
    fun getBookmarkedNews(): LiveData<List<NewsEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNews(news: List<NewsEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveNews(news: NewsEntity)

    @Query("DELETE FROM news WHERE title = :newsTitle")
    suspend fun deleteNews(newsTitle: String)

    @Query("SELECT EXISTS(SELECT * FROM news WHERE title = :title)")
    fun isNewsBookmarked(title: String): LiveData<Boolean>
}