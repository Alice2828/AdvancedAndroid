package com.example.newsapp.view

import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.newsapp.BR
import com.example.newsapp.R
import com.example.newsapp.data.model.Articles
import com.example.newsapp.databinding.FragmentDetailBinding
import com.example.newsapp.utils.Utils
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.fragment_detail.view.*
import kotlin.math.abs


class DetailActivity : AppCompatActivity(), AppBarLayout.OnOffsetChangedListener {
    private lateinit var viewDataBinding: FragmentDetailBinding
    private lateinit var itemDetail: Articles
    private var isHideToolBarView = false
    private val utils = Utils()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewDataBinding =
            DataBindingUtil.setContentView(this, R.layout.fragment_detail);
        val view = viewDataBinding.root
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        appbar.addOnOffsetChangedListener(this)
        itemDetail = intent.getSerializableExtra("itemData") as Articles
        viewDataBinding.setVariable(BR.itemDetail, itemDetail)

        initWebView(itemDetail.url)
        val imageView = view.backdrop
        Glide.with(this)
            .load(itemDetail.urlToImage)
            .into(imageView)
        time.text = utils.DateToTimeFormat(itemDetail.publishedAt)
     }

    override fun onBackPressed() {
        super.onBackPressed()
        supportFinishAfterTransition()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        var maxScroll = appbar.totalScrollRange
        var percentage = (abs(verticalOffset).toFloat()) / (maxScroll.toFloat())

        if (percentage == 1f && isHideToolBarView) {
            date_behavior.visibility = View.GONE
            title_appbar.visibility = View.VISIBLE
            isHideToolBarView = !isHideToolBarView
        } else if (percentage < 1f && isHideToolBarView) {
            date_behavior.visibility = View.VISIBLE
            title_appbar.visibility = View.GONE
            isHideToolBarView = !isHideToolBarView
        }
    }

    fun initWebView(url: String) {
        webView.settings.loadsImagesAutomatically = true
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.settings.supportZoom()
        webView.settings.builtInZoomControls = true
        webView.settings.displayZoomControls = true
        webView.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
        webView.webViewClient = WebViewClient()
        webView.loadUrl(url)
    }
}