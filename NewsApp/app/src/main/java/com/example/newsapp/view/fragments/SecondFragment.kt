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
import kotlinx.android.synthetic.main.fragment_second.*
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
        viewDataBinding.viewmodel?.getTotal()
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipe_refresh_view.setOnRefreshListener {
            viewDataBinding.viewmodel?.getTotal()
        }
        fetch()
    }

    private fun fetch() {
        viewDataBinding.viewmodel?.liveData?.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is CoronaViewModel.State.ShowLoading -> {
                    swipe_refresh_view.isRefreshing = true
                }
                is CoronaViewModel.State.HideLoading -> {
                    swipe_refresh_view.isRefreshing = false
                }
                is CoronaViewModel.State.Result -> {
                    viewDataBinding.setVariable(BR.coronaDetail, result.result as Total)
                }
            }
        })
    }

}