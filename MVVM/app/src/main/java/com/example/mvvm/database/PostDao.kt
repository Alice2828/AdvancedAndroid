package com.example.mvvm.database

import androidx.room.*
import com.example.mvvm.data.model.ApiPost

@Dao
interface PostDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<ApiPost>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(post: ApiPost?)

    @Query("SELECT*FROM posts_table")
    fun getAll(): List<ApiPost>
}

@Database(entities = [ApiPost::class], version = 1, exportSchema = false)
abstract class PostsDatabase : RoomDatabase() {
    abstract fun postDao(): PostDao
}