package com.example.newsapp.database


import android.content.Context
import androidx.room.*
import com.example.newsapp.data.model.Articles
import java.io.Serializable

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<Articles>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(post: Articles?)

    @Query("SELECT*FROM articles_table")
    fun getAll(): List<Articles>

    @Query("SELECT*FROM articles_table where `like`=1")
    fun getAllLiked(): List<Articles>

    @Query("SELECT `like` FROM articles_table where id=:id")
    fun getLiked(id: Int?): Int
}

@Database(entities = [Articles::class], version = 1, exportSchema = false)
abstract class ArticleDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao

}