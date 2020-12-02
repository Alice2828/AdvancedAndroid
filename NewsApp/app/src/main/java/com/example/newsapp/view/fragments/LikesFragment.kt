package com.example.newsapp.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.adapter.RepoListAdapter
import com.example.newsapp.databinding.FragmentLikesBinding
import com.example.newsapp.view.activities.MainActivity
import com.example.newsapp.viewmodel.LikesViewModel
import kotlinx.android.synthetic.main.error.*
import kotlinx.android.synthetic.main.fragment_repo_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class LikesFragment : Fragment() {

    private lateinit var viewDataBinding: FragmentLikesBinding
    private lateinit var adapter: RepoListAdapter
    private val likesViewModel: LikesViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewDataBinding = FragmentLikesBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        viewDataBinding.viewmodel = likesViewModel
        return viewDataBinding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipe_refresh_view.setOnRefreshListener {
            swipe_refresh_view.isRefreshing = true
            errorLayout.visibility = View.GONE
            setUpViewModel()
            swipe_refresh_view.isRefreshing = false


        }
        setUpViewModel()
        viewDataBinding.viewmodel?.liveData?.observe(viewLifecycleOwner, { result ->
            adapter.updateRepoList(result)
        })
    }

    private fun setUpViewModel() {
        swipe_refresh_view.isRefreshing = true
        viewDataBinding.viewmodel?.fetchRepoList()
        setupAdapter()
        swipe_refresh_view.isRefreshing = false

    }


    private fun setupAdapter() {
        val viewModel = viewDataBinding.viewmodel
        if (viewModel != null) {
            adapter = RepoListAdapter(activity as MainActivity)
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
