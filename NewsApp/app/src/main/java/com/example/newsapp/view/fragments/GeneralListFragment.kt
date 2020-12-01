package com.example.newsapp.view.fragments

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.adapter.GeneralListAdapter
import com.example.newsapp.databinding.FragmentGeneralListBinding
import com.example.newsapp.viewmodel.GeneralListViewModel
import kotlinx.android.synthetic.main.error.*
import kotlinx.android.synthetic.main.fragment_repo_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class GeneralListFragment : Fragment() {
    private lateinit var viewDataBinding: FragmentGeneralListBinding
    private lateinit var adapter: GeneralListAdapter
    private val generalListViewModel: GeneralListViewModel by viewModel()
    private lateinit var keyword: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = FragmentGeneralListBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        setHasOptionsMenu(true)
        keyword = "default"
        viewDataBinding.viewmodel = generalListViewModel

        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipe_refresh_view.setOnRefreshListener {
            swipe_refresh_view.isRefreshing = true
            errorLayout.visibility = View.GONE
            viewDataBinding.viewmodel?.fetchRepoList(keyword)
            swipe_refresh_view.isRefreshing = false

        }
        setUpViewModel()
    }

    private fun setUpViewModel() {
        swipe_refresh_view.isRefreshing = true
        viewDataBinding.viewmodel?.fetchRepoList(keyword)
        setupAdapter()
        setObservers()
        swipe_refresh_view.isRefreshing = false
    }

    private fun setObservers() {
        viewDataBinding.viewmodel?.fetchRepoList(keyword)?.observe(viewLifecycleOwner, {
            adapter.updateRepoList(it)
        })

    }

    private fun setupAdapter() {
        val viewModel = viewDataBinding.viewmodel
        if (viewModel != null) {
            adapter = GeneralListAdapter()
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
            checkConnection()
        }
    }

    private fun checkConnection() {
        if (!hasConnection(context!!)) {
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
        val searchMenuItem = menu.findItem(R.id.action_search)
        val searchView = searchMenuItem?.actionView as SearchView
        searchView.queryHint = "Search"

        searchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextChange(qString: String): Boolean {
                if (qString.length > 2) {
                    keyword = qString
                    setUpViewModel()
                }
                return false
            }

            override fun onQueryTextSubmit(qString: String): Boolean {
                setUpViewModel()
                return false
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
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

