package com.example.mvvm.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "posts_table")
data class ApiPost(
    @PrimaryKey
    @SerializedName("id")
    val id: Int,
    @SerializedName("albumId")
    val albumId: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("thumbnailUrl")
    val thumbnailUrl: String
) : Serializable
