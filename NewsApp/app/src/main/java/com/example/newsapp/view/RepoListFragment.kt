package com.example.newsapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableBoolean
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.newsapp.adapter.RepoListAdapter
import com.example.newsapp.data.model.Articles
import com.example.newsapp.databinding.FragmentRepoListBinding
import com.example.newsapp.viewmodel.RepoListViewModel
import kotlinx.android.synthetic.main.fragment_repo_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * A simple [Fragment] subclass.
 */
class RepoListFragment : Fragment() {
    private lateinit var viewDataBinding: FragmentRepoListBinding
    private lateinit var adapter: RepoListAdapter
    private val repoListViewModel: RepoListViewModel by viewModel()

    //  var mIsLoading = ObservableBoolean(false)

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
        }
    }


//    fun getOnRefreshListener(): SwipeRefreshLayout.OnRefreshListener {
//        return this
//    }
//
//    fun getIsLoading(): ObservableBoolean {
//        return mIsLoading
//    }

//    @BindingAdapter("bind:refreshState", "bind:onRefresh")
//    fun configureSwipeRefreshLayout(
//        layout: SwipeRefreshLayout, isLoading: Boolean,
//        listener: SwipeRefreshLayout.OnRefreshListener
//    ) {
//        layout.setOnRefreshListener(listener)
//        layout.post { layout.isRefreshing = isLoading }
//
//    }

//    override fun onRefresh() {
//            mIsLoading.set(true)
//            setupAdapter()
//            mIsLoading.set(false)
//    }

}

