package com.example.newsapp.database

import android.content.Context
import androidx.room.*
import com.example.newsapp.data.model.Articles
import com.example.newsapp.data.model.Likes
import com.example.newsapp.utils.Constants

@Dao
interface LikesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(post: Likes?)

    @Query("SELECT COALESCE(MAX(likesId),0) FROM " + Constants.LIKES_TABLE)
    fun queryLastInsert(): Long

    @Query("DELETE FROM " + Constants.LIKES_TABLE + " where articleTitle=:title AND emailName=:emailName")
    fun remove(title: String, emailName: String?)

    @Query("SELECT id,author,title,description,url,urlToImage,publishedAt,content FROM " + Constants.ARTICLE_TABLE + " INNER JOIN " + Constants.LIKES_TABLE + " ON title=articleTitle WHERE emailName=:emailName")
    fun getAll(emailName: String?): List<Articles>

    @Query("SELECT id,author,title,description,url,urlToImage,publishedAt,content FROM " + Constants.ARTICLE_TABLE + " INNER JOIN " + Constants.LIKES_TABLE + " ON title=articleTitle WHERE emailName=:emailName and title=:title")
    fun getByTitle(title: String?, emailName: String?): Articles

    @Query("SELECT * FROM " + Constants.LIKES_TABLE + " WHERE emailName=:emailName")
    fun getAllLikes(emailName: String?): List<Likes>

    @Query("SELECT * FROM " + Constants.LIKES_TABLE + " WHERE emailName=:emailName and articleTitle=:title")
    fun getByTitleLike(title: String?, emailName: String?): Likes
}

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<Articles>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(post: Articles?)

    @Query("SELECT * FROM " + Constants.ARTICLE_TABLE + " ORDER BY id DESC")
    fun getAll(): List<Articles>
}

@Database(entities = [Articles::class, Likes::class], version = 1, exportSchema = false)
abstract class ArticleDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
    abstract fun likesDao(): LikesDao

    companion object {
        private var INSTANCE: ArticleDatabase? = null
        fun getDatabase(context: Context): ArticleDatabase {

            if (INSTANCE == null) {
                INSTANCE = createDb(context)
            }
            return INSTANCE!!
        }

        private fun createDb(context: Context): ArticleDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                ArticleDatabase::class.java,
                "article.db"
            ).allowMainThreadQueries().build()
        }
    }

}