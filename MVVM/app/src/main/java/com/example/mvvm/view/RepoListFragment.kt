package com.example.mvvm.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.example.mvvm.adapter.RepoListAdapter
import com.example.mvvm.databinding.FragmentRepoListBinding
import com.example.mvvm.viewmodel.RepoListViewModel
import kotlinx.android.synthetic.main.fragment_repo_list.*

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
        if (viewDataBinding.viewmodel != null) {
            adapter = RepoListAdapter(viewDataBinding.viewmodel!!, activity)
            val layoutManager = LinearLayoutManager(activity)
            repo_list_rv.layoutManager = layoutManager
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
        }
    }


}
