package com.example.newsapp.data.model

import androidx.room.*
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ApiPost(
    @SerializedName("status")
    val status: String,
    @SerializedName("totalResults")
    val totalResults: Int,
    @SerializedName("articles")
    val articles: List<Articles>
) : Serializable

@Entity(tableName = "articles_table")
data class Articles(
    @SerializedName("id")
    var id: Long = 0,
    @ColumnInfo(name = "author", defaultValue = "default")
    @SerializedName("author")
    val author: String,
    @ColumnInfo(name = "title", defaultValue = "default")
    @PrimaryKey
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


@Entity(
    tableName = "likes_table", foreignKeys = arrayOf(
        ForeignKey(
            entity = Articles::class,
            parentColumns = arrayOf("title"),
            childColumns = arrayOf("articleTitle")
        )
    )
)
data class Likes(
    @PrimaryKey(autoGenerate = true)
    @SerializedName("likesId")
    var likesId: Long? = 0,
    @ColumnInfo(index = true)
    @SerializedName("articleTitle")
    var articleTitle: String? = null,
    @SerializedName("emailName")
    var emailName: String?
) : Serializable


data class General(
    @SerializedName("Global")
    var Global: Total
) : Serializable

data class Total(
    @SerializedName("TotalConfirmed")
    var TotalConfirmed: Long? = 0,
    @SerializedName("NewConfirmed")
    var NewConfirmed: Long? = 0,
    @SerializedName("TotalDeaths")
    var TotalDeaths: Long? = 0,
    @SerializedName("TotalRecovered")
    var TotalRecovered: Long? = 0
) : Serializable
