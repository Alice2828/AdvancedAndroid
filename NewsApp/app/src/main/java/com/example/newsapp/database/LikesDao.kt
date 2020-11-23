package com.example.newsapp.database

import android.content.Context
import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.newsapp.data.model.Articles
import com.example.newsapp.data.model.Likes


@Dao
interface LikesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<Likes>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(post: Likes?)

    @Query("DELETE FROM likes_table where id=:id")
    fun remove(id: Long)

    @Query("SELECT*FROM likes_table")
    fun getAll(): List<Likes>

}

@Database(entities = [Likes::class], version = 1, exportSchema = false)
abstract class LikesDatabase : RoomDatabase() {
    abstract fun likesDao(): LikesDao

    companion object {
        private var INSTANCE: LikesDatabase? = null


        fun getDatabase(context: Context): LikesDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    LikesDatabase::class.java,
                    "likes_database.db"
                ).allowMainThreadQueries()
                    .build()
            }
            return INSTANCE!!
        }
    }
}