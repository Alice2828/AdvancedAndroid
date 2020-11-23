package com.example.newsapp.view.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.newsapp.R
import com.example.newsapp.adapter.RepoListAdapter
import com.example.newsapp.database.LikesDao
import com.example.newsapp.database.LikesDatabase
import com.example.newsapp.databinding.FragmentLikesBinding
import com.example.newsapp.databinding.FragmentRepoListBinding
import com.example.newsapp.viewmodel.LikesViewModel
import com.example.newsapp.viewmodel.RepoListViewModel
import com.example.newsapp.viewmodel.ViewModelProviderFactory
import kotlinx.android.synthetic.main.error.*
import kotlinx.android.synthetic.main.fragment_repo_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class LikesFragment : Fragment() {

    private lateinit var viewDataBinding: FragmentLikesBinding
    private lateinit var adapter: RepoListAdapter
    private lateinit var likesViewModel: LikesViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewDataBinding = FragmentLikesBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        val viewModelProviderFactory = ViewModelProviderFactory(context = this.activity as Context)
        likesViewModel =
            ViewModelProvider(this, viewModelProviderFactory).get(LikesViewModel::class.java)
        viewDataBinding.viewmodel = likesViewModel
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
        viewDataBinding.viewmodel?.liveData?.observe(viewLifecycleOwner, { result ->
            adapter.updateRepoList(result)
        })
        setupAdapter()
        swipe_refresh_view.isRefreshing = false

    }


    private fun setupAdapter() {
        val viewModel = viewDataBinding.viewmodel
        if (viewModel != null) {
            adapter = RepoListAdapter()
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

}
