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
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import kotlinx.android.synthetic.main.fragment_third.*
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
        thirdViewModel.liveData.observe(viewLifecycleOwner, { result ->
            when (result) {
                is ThirdViewModel.CoronaState.ShowLoading -> {
                    swipe_refresh_view.isRefreshing = true
                }
                is ThirdViewModel.CoronaState.HideLoading -> {
                    swipe_refresh_view.isRefreshing = false
                }
                is ThirdViewModel.CoronaState.Result -> {

                    val coronaInfoForBar = ArrayList<BarEntry>()
                    val coronaInfoForBarDeath = ArrayList<BarEntry>()
                    val coronaInfoForBarActive = ArrayList<BarEntry>()
                    val coronaInfoForBarRecovered = ArrayList<BarEntry>()

                    val list = result.list as List<DataCorona>

                    val coronaInfo = list.subList(
                        list.size - 4,
                        list.size
                    )
                    val labels = ArrayList<String>()

                    for ((index, info) in coronaInfo.withIndex()) {
                        coronaInfoForBar.add(BarEntry(index.toFloat(), info.Confirmed.toFloat()))
                        coronaInfoForBarActive.add(BarEntry(index.toFloat(), info.Active.toFloat()))
                        coronaInfoForBarDeath.add(BarEntry(index.toFloat(), info.Deaths.toFloat()))
                        coronaInfoForBarRecovered.add(
                            BarEntry(
                                index.toFloat(),
                                info.Recovered.toFloat()
                            )
                        )

                        labels.add((info.Date).substring(0, 10))
                    }

                    val barDataSet = BarDataSet(coronaInfoForBar, "Confirmed")
                    barDataSet.color = Color.BLUE
                    barDataSet.valueTextColor = Color.BLUE
                    barDataSet.valueTextSize = 10f

                    val barDataSetActive = BarDataSet(coronaInfoForBarActive, "Active")
                    barDataSetActive.color = Color.MAGENTA
                    barDataSetActive.valueTextColor = Color.MAGENTA
                    barDataSetActive.valueTextSize = 10f

                    val barDataSetDeath = BarDataSet(coronaInfoForBarDeath, "Deaths")
                    barDataSetDeath.color = Color.RED
                    barDataSetDeath.valueTextColor = Color.RED
                    barDataSetDeath.valueTextSize = 10f

                    val barDataSetRecovered = BarDataSet(coronaInfoForBarRecovered, "Recovered")
                    barDataSetRecovered.color = Color.GREEN
                    barDataSetRecovered.valueTextColor = Color.GREEN
                    barDataSetRecovered.valueTextSize = 10f

                    val groupSpace = 0.4f
                    val barSpace = 0f // x4 dataset
                    val barWidth = 0.15f // x4 dataset

                    val dataSets = ArrayList<IBarDataSet>()
                    dataSets.add(barDataSetActive)
                    dataSets.add(barDataSetRecovered)
                    dataSets.add(barDataSet)
                    dataSets.add(barDataSetDeath)

                    val barData = BarData(dataSets)
                    barChart.description.isEnabled = false
                    barData.barWidth = barWidth
                    barChart.data = barData
                    barChart.animateY(2000)
                    val xAxisFormatter = IndexAxisValueFormatter(labels)
                    val xAxis: XAxis = barChart.xAxis
                    xAxis.setCenterAxisLabels(true)
                    xAxis.position = XAxis.XAxisPosition.BOTTOM
                    xAxis.granularity = 1f

                    xAxis.textColor = Color.BLACK
                    xAxis.textSize = 8f
                    xAxis.axisLineColor = Color.BLACK
                    xAxis.axisMinimum = 0f
                    xAxis.axisMaximum = labels.size - 1f
                    xAxis.valueFormatter = xAxisFormatter

                    val leftAxis: YAxis = barChart.axisLeft
                    leftAxis.textColor = Color.BLACK
                    leftAxis.textSize = 8f
                    leftAxis.axisLineColor = Color.BLACK
                    leftAxis.granularity = 2f
                    leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
                    leftAxis.axisMinimum = 0f
                    barChart.setScaleEnabled(false)
                    barChart.setVisibleXRangeMaximum(30F)
                    barChart.moveViewToX(20F)
                    barChart.axisRight.isEnabled = false
                    barChart.setTouchEnabled(false)
                    barChart.groupBars(
                        0.1f,
                        groupSpace,
                        barSpace
                    )
                    barChart.invalidate()
                }
            }
        })
    }
}