package com.example.newsapp.view.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.newsapp.R
import com.example.newsapp.data.model.DataCorona
import com.example.newsapp.viewmodel.ThirdViewModel
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import kotlinx.android.synthetic.main.fragment_third.*
import kotlinx.android.synthetic.main.fragment_third.swipe_refresh_view
import org.koin.androidx.viewmodel.ext.android.viewModel


class ThirdFragment : Fragment() {
    private val thirdViewModel: ThirdViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_third, container, false)
        thirdViewModel.getDataCorona()
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipe_refresh_view.setOnRefreshListener {
            thirdViewModel.getDataCorona()
        }
        thirdViewModel.liveData.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is ThirdViewModel.CoronaState.ShowLoading -> {
                    swipe_refresh_view.isRefreshing = true
                }
                is ThirdViewModel.CoronaState.HideLoading -> {
                    swipe_refresh_view.isRefreshing = false
                }
                is ThirdViewModel.CoronaState.Result -> {

                    val coronaInfoForBar = ArrayList<BarEntry>()
                    val coronaInfo = result.list as List<DataCorona>
                    for ((index, info) in coronaInfo.withIndex()) {
                        coronaInfoForBar.add(BarEntry(index.toFloat(), info.Cases.toFloat()))
                    }

                    val barDataSet = BarDataSet(coronaInfoForBar, "Confirmed")
                    barDataSet.setColors(Color.rgb(61, 165, 255), Color.rgb(23, 197, 255))
                    barDataSet.valueTextColor = Color.rgb(61, 165, 255)
                    barDataSet.valueTextSize = 16f
                    val barData = BarData(barDataSet)

                    barChart.setFitBars(true)
                    barChart.data = barData
                    barChart.description.text = "Coronavirus in Kazakhstan"
                    barChart.animateY(2000)

                }
            }
        })
    }
}