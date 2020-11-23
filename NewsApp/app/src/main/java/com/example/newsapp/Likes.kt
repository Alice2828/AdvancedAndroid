package com.example.newsapp

import com.example.newsapp.data.model.Articles

class Likes {
    var list = ArrayList<Articles>()

    companion object {
        private var INSTANCE: Likes? = null

        fun getInstance(): Likes {
            if (INSTANCE == null) {
                INSTANCE = Likes()
            }
            return INSTANCE!!
        }
        fun addFavourite(article:Articles){
            INSTANCE?.list?.add(article)

        }
        fun removeFav(article:Articles){
            INSTANCE?.list?.remove(article)
        }
        fun getList(): ArrayList<Articles>? {
            return INSTANCE?.list
        }
    }
}