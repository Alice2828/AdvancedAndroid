package com.example.newsapp.view.activities

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.newsapp.BR
import com.example.newsapp.R
import com.example.newsapp.data.model.Articles
import com.example.newsapp.data.model.Likes
import com.example.newsapp.databinding.FragmentDetailBinding
import com.example.newsapp.utils.Utils
import com.example.newsapp.viewmodel.DetailViewModel
import com.example.newsapp.viewmodel.ViewModelProviderFactory
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.fragment_detail.view.*
import kotlin.math.abs


class DetailActivity : AppCompatActivity(),
    AppBarLayout.OnOffsetChangedListener {
    private lateinit var viewDataBinding: FragmentDetailBinding
    private lateinit var itemDetail: Articles
    private var isHideToolBarView = false
    lateinit var detailViewModel: DetailViewModel
    private val utils = Utils()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewDataBinding =
            DataBindingUtil.setContentView(this, R.layout.fragment_detail)

        val viewModelProviderFactory = ViewModelProviderFactory(context = this)
        detailViewModel =
            ViewModelProvider(this, viewModelProviderFactory).get(DetailViewModel::class.java)

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

        detailViewModel.liveData.observe(this, { result ->
            if (result) {
                toolbar.menu.findItem(R.id.like).icon =
                    getDrawable(R.drawable.ic_favorite_liked)
            } else {
                toolbar.menu.findItem(R.id.like).icon =
                    getDrawable(R.drawable.ic_favorite_border)

            }
        })
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
        val maxScroll = appbar.totalScrollRange
        val percentage = (abs(verticalOffset).toFloat()) / (maxScroll.toFloat())

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        detailViewModel.haslike(itemDetail)
//        if (detailViewModel.haslike()) {
//            menu?.getItem(1)?.icon = getDrawable(R.drawable.ic_favorite_liked)
//        } else
//            menu?.getItem(1)?.icon = getDrawable(R.drawable.ic_favorite_border)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.share) {
            try {
                val i = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(
                        Intent.EXTRA_TEXT,
                        itemDetail.title + "\n" + itemDetail.url + "\n" + "Share from the NewsApp" + "\n"
                    )
                    type = "text/plain"

                }
                startActivity(Intent.createChooser(i, "Share with:"))
            } catch (e: Exception) {
                Toast.makeText(this, "Hmm...Sorry, /nCan not be shared", Toast.LENGTH_LONG).show()
            }
        }
        if (id == R.id.like) {
            val drawable: Drawable = item.icon.current
            if (drawable.constantState?.equals(getDrawable(R.drawable.ic_favorite_border)?.constantState) == true) {
                item.icon = getDrawable(R.drawable.ic_favorite_liked)
                detailViewModel.likeMovie(true, itemDetail)
            } else {
                item.icon = getDrawable(R.drawable.ic_favorite_border)
                detailViewModel.likeMovie(false, itemDetail)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}