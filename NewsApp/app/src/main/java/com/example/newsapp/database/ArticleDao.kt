package com.example.newsapp.database


import android.content.Context
import androidx.room.*
import com.example.newsapp.data.model.Articles
import com.example.newsapp.data.model.Likes

@Dao
interface LikesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<Likes>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(post: Likes?)

    @Query("SELECT COALESCE(MAX(likesId),0) from likes_table")
    fun queryLastInsert(): Long

    @Query("DELETE FROM likes_table where articleTitle=:title")
    fun remove(title: String)

    @Query("SELECT id,author,title,description,url,urlToImage,publishedAt,content FROM articles_table INNER JOIN likes_table ON title=articleTitle")
    fun getAll(): List<Articles>

}

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<Articles>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(post: Articles?)

    @Query("SELECT*FROM articles_table")
    fun getAll(): List<Articles>
}

@Database(entities = [Articles::class, Likes::class], version = 1, exportSchema = false)
abstract class ArticleDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
    abstract fun likesDao(): LikesDao

    companion object {
        private var INSTANCE: ArticleDatabase? = null

        fun getDatabase(context: Context, name: String): ArticleDatabase {
            val nameDB= "$name.db"
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    ArticleDatabase::class.java,
                    nameDB
                ).allowMainThreadQueries()
                    .build()
            }
            return INSTANCE!!
        }
    }

}