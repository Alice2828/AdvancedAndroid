package com.example.newsapp.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.newsapp.BR
import com.example.newsapp.data.model.Total
import com.example.newsapp.databinding.FragmentSecondBinding
import com.example.newsapp.viewmodel.CoronaViewModel
import kotlinx.android.synthetic.main.error.*
import kotlinx.android.synthetic.main.fragment_repo_list.*
import kotlinx.android.synthetic.main.fragment_second.*
import kotlinx.android.synthetic.main.fragment_second.swipe_refresh_view
import org.koin.androidx.viewmodel.ext.android.viewModel


class SecondFragment : Fragment() {
    private val coronaViewModel: CoronaViewModel by viewModel()
    private lateinit var viewDataBinding: FragmentSecondBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = FragmentSecondBinding.inflate(inflater, container, false)
        viewDataBinding.viewmodel = coronaViewModel
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipe_refresh_view.setOnRefreshListener {
            swipe_refresh_view.isRefreshing = true
            viewDataBinding.viewmodel?.getTotal()
            swipe_refresh_view.isRefreshing = false
        }
        fetch()
    }

    private fun fetch() {
        viewDataBinding.viewmodel?.getTotal()?.observe(viewLifecycleOwner, { result ->
            viewDataBinding.setVariable(BR.coronaDetail, result as Total)
        })
    }

}