package com.example.mvvm.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable


//data class ApiPost(
//    @PrimaryKey
//    @SerializedName("id")
//    val id: Int,
//    @SerializedName("albumId")
//    val albumId: Int,
//    @SerializedName("title")
//    val title: String,
//    @SerializedName("url")
//    val url: String,
//    @SerializedName("thumbnailUrl")
//    val thumbnailUrl: String
//) : Serializable

data class ApiPost(
    @SerializedName("status")
    val status: String,
    @SerializedName("totalResults")
    val totalResults: Int,
    @SerializedName("articles")
    val articles: List<Articles>
) : Serializable

@Entity(tableName = "articleS_table")


data class Articles(
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    var id: Long = 0,
    @ColumnInfo(name = "author", defaultValue = "default")
    @SerializedName("author")
    val author: String,
    @ColumnInfo(name = "title", defaultValue = "default")
    @SerializedName("title")
    val title: String,
    @ColumnInfo(name = "description", defaultValue = "default")
    @SerializedName("description")
    val description: String,
    @ColumnInfo(name = "url", defaultValue = "default")
    @SerializedName("url")
    val url: String,
    @ColumnInfo(name = "urlToImage", defaultValue = "default")
    @SerializedName("urlToImage")
    val urlToImage: String,
    @ColumnInfo(name = "publishedAt", defaultValue = "default")
    @SerializedName("publishedAt")
    val publishedAt: String,
    @ColumnInfo(name = "content", defaultValue = "default")
    @SerializedName("content")
    val content: String
) : Serializable

