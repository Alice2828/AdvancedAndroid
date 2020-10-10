package com.example.newsapp.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.newsapp.BR
import com.example.newsapp.R
import com.example.newsapp.data.model.Articles
import com.example.newsapp.databinding.FragmentDetailBinding
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_detail.view.*

class DetailActivity():AppCompatActivity() {
    private lateinit var viewDataBinding: FragmentDetailBinding
    private lateinit var itemDetail: Articles
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewDataBinding =
            DataBindingUtil.setContentView(this, R.layout.fragment_detail);
        val view = viewDataBinding.root
        itemDetail= intent.getSerializableExtra("itemData") as Articles
        viewDataBinding.setVariable(BR.itemDetail, itemDetail)
        val avatarImage = view.item_avatar
        Picasso.get().load(itemDetail.urlToImage).into(avatarImage)
    }
}