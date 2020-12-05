package com.example.newsapp.view.fragments

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.adapter.SourceListAdapter
import com.example.newsapp.databinding.FragmentSourcesListBinding
import com.example.newsapp.view.activities.SourcesActivity
import com.example.newsapp.viewmodel.SourcesListViewModel
import kotlinx.android.synthetic.main.error.*
import kotlinx.android.synthetic.main.fragment_sources_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class SourcesListFragment : Fragment() {
    private lateinit var viewDataBinding: FragmentSourcesListBinding
    private lateinit var adapter: SourceListAdapter
    private val repoListViewModel: SourcesListViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = FragmentSourcesListBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        viewDataBinding.viewmodel = repoListViewModel
        return viewDataBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipe_refresh_view.setOnRefreshListener {
           setUpViewModel()
        }
        setUpViewModel()
    }

    private fun setUpViewModel() {
        swipe_refresh_view.isRefreshing = true
        setupAdapter()
        swipe_refresh_view.isRefreshing = false

    }

    private fun setupAdapter() {
        val viewModel = viewDataBinding.viewmodel
        if (viewModel != null) {
            adapter = SourceListAdapter(activity as SourcesActivity)
         //   val layoutManager = GridLayoutManager(activity, 2)
            val layoutManager = LinearLayoutManager(activity)

            repo_list_rv.layoutManager = layoutManager
            repo_list_rv.itemAnimator = DefaultItemAnimator()
            repo_list_rv.isNestedScrollingEnabled
            repo_list_rv.addItemDecoration(
                DividerItemDecoration(
                    activity,
                    layoutManager.orientation
                )
            )
            viewDataBinding.viewmodel?.postsLiveData?.observe(viewLifecycleOwner, Observer {
                adapter.submitList(it)
            })
            repo_list_rv.adapter = adapter

            checkConnection()
        }
    }

    private fun checkConnection() {
        if (!hasConnection(requireContext())) {
            showErrorMessage(R.drawable.no_result, "No Result", "Please, swipe to refresh")
        } else {
            errorLayout.visibility = View.GONE
        }
    }

    private fun showErrorMessage(imageView: Int, title: String, message: String) {

        if (errorLayout.visibility == View.GONE) {
            errorLayout.visibility = View.VISIBLE
        }
        errorImage.setImageResource(imageView)
        errorTitle.text = title
        errorMessage.text = message
    }

    private fun hasConnection(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        if (wifiInfo != null && wifiInfo.isConnected) {
            return true
        }
        wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
        if (wifiInfo != null && wifiInfo.isConnected) {
            return true
        }
        wifiInfo = cm.activeNetworkInfo
        return wifiInfo != null && wifiInfo.isConnected
    }
}