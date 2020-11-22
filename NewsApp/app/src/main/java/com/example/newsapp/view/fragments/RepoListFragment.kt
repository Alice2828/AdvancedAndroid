package com.example.newsapp.view.fragments

import android.app.SearchManager
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.newsapp.R
import com.example.newsapp.adapter.RepoListAdapter
import com.example.newsapp.databinding.FragmentRepoListBinding
import com.example.newsapp.viewmodel.RepoListViewModel
import kotlinx.android.synthetic.main.error.*
import kotlinx.android.synthetic.main.fragment_repo_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * A simple [Fragment] subclass.
 */
class RepoListFragment : Fragment() {
    private lateinit var viewDataBinding: FragmentRepoListBinding
    private lateinit var adapter: RepoListAdapter
    private val repoListViewModel: RepoListViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewDataBinding = FragmentRepoListBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        viewDataBinding.viewmodel = repoListViewModel
        return viewDataBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipe_refresh_view.setOnRefreshListener {
            errorLayout.visibility = View.GONE
            setUpViewModel()
        }
        setUpViewModel()
    }

    private fun setUpViewModel() {
        swipe_refresh_view.isRefreshing = true
        viewDataBinding.viewmodel?.fetchRepoList()
        setupAdapter()
        setObservers()
        swipe_refresh_view.isRefreshing = false

    }

    private fun setObservers() {
        viewDataBinding.viewmodel?.fetchRepoList()?.observe(viewLifecycleOwner, Observer {
            adapter.updateRepoList(it)
        })

    }

    private fun setupAdapter() {
        val viewModel = viewDataBinding.viewmodel
        if (viewModel != null) {
            adapter = RepoListAdapter(viewDataBinding.viewmodel!!, activity)
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
            repo_list_rv.adapter = adapter

            if (!hasConnection(context!!)) {
                showErrorMessage(R.drawable.no_result, "No Result", "Please, swipe to refresh")
            } else {
                errorLayout.visibility = View.GONE
            }
        }
    }

    fun showErrorMessage(imageView: Int, title: String, message: String) {

        if (errorLayout.visibility == View.GONE) {
            errorLayout.visibility = View.VISIBLE
        }
        errorImage.setImageResource(imageView)
        errorTitle.text = title
        errorMessage.text = message
    }


//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        val inflater = menuInflater
//        inflater.inflate(R.menu.menu_main, menu)
//        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
//        val searchView = menu?.findItem(R.id.action_search)?.actionView as SearchView
//        var searchMenuItem = menu.findItem(R.id.action_search)
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
//        searchView.queryHint = "Search"
//        searchView.setOnQueryTextListener(object :
//            SearchView.OnQueryTextListener {
//
//            override fun onQueryTextChange(qString: String): Boolean {
//                if (qString.length > 2) {
//                    keyword = qString
//                }
//                return false
//            }
//
//            override fun onQueryTextSubmit(qString: String): Boolean {
//                return false
//            }
//        })
//        return super.onCreateOptionsMenu(menu)
//    }
}


fun hasConnection(context: Context): Boolean {
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


