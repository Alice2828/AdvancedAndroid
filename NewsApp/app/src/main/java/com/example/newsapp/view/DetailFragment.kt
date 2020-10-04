package com.example.newsapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.newsapp.BR
import com.example.newsapp.R
import com.example.newsapp.data.model.ApiPost
import com.example.newsapp.databinding.FragmentDetailBinding
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_detail.view.*

/**
 * A simple [Fragment] subclass.
 */
class DetailFragment constructor(private val itemDetail: ApiPost) : Fragment() {
    private lateinit var viewDataBinding: FragmentDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_detail, container, false)
        val view = viewDataBinding.root
        viewDataBinding.setVariable(BR.itemDetail, itemDetail)
        val avatarImage = view.item_avatar
        val photoImage = view.item_photo
        Picasso.get().load(itemDetail.thumbnailUrl).into(avatarImage)
        Picasso.get().load(itemDetail.url).into(photoImage)

        return view
    }

}
