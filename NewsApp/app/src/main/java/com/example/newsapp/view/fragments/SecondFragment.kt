package com.example.newsapp.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.newsapp.BR
import com.example.newsapp.data.model.Total
import com.example.newsapp.databinding.FragmentSecondBinding
import com.example.newsapp.viewmodel.CoronaViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class SecondFragment : Fragment() {
    private val coronaViewModel: CoronaViewModel by viewModel()
    lateinit var globalRes: Total
    private lateinit var viewDataBinding: FragmentSecondBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = FragmentSecondBinding.inflate(inflater, container, false)
        viewDataBinding.viewmodel = coronaViewModel
        fetch()
        return viewDataBinding.root
    }

    private fun fetch() {
        val viewModel = viewDataBinding.viewmodel
        if (viewModel != null) {
            globalRes = viewModel.getTotal()
            viewDataBinding.setVariable(BR.coronaDetail, globalRes)
        }
    }

}