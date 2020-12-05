package com.example.newsapp.view.activities

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.newsapp.R
import com.example.newsapp.view.fragments.SecondFragmentDirections
import com.example.newsapp.view.fragments.SourcesListFragmentDirections
import com.example.newsapp.view.fragments.ThirdFragmentDirections
import kotlinx.android.synthetic.main.tabs.*

enum class Active {
    FIRST, SECOND, THIRD
}


class SourcesActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var def: ColorStateList
    var active = Active.FIRST

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sources)
        item1.setOnClickListener(this)
        item2.setOnClickListener(this)
        item3.setOnClickListener(this)
        def = item2.textColors

    }

    override fun onClick(v: View?) {
        if (v != null) {
            Navigation.setViewNavController(v, findNavController(R.id.nav_host_fragment))
        }
        if (v?.id == R.id.item1) {
            select.animate().x(0F).duration = 100;
            item1.setTextColor(Color.WHITE);
            item2.setTextColor(def)
            item3.setTextColor(def)
            if (active == Active.SECOND) {
                val action = SecondFragmentDirections.actionSecondToFirst()
                v.findNavController().navigate(action)
                active = Active.FIRST
            } else if (active == Active.THIRD) {
                val action = ThirdFragmentDirections.actionThirdToFirst()
                v.findNavController().navigate(action)
                active = Active.FIRST
            }
        } else if (v?.id == R.id.item2) {
            item1.setTextColor(def)
            item2.setTextColor(Color.WHITE)
            item3.setTextColor(def)
            val size = item2.width
            select.animate().x(size.toFloat()).duration = 100
            if (active == Active.FIRST) {
                val action = SourcesListFragmentDirections.actionFirstToSecond()
                v.findNavController().navigate(action)
                active = Active.SECOND
            } else
                if (active == Active.THIRD) {
                    val action = ThirdFragmentDirections.actionThirdToSecond()
                    v.findNavController().navigate(action)
                    active = Active.SECOND
                }
        } else if (v?.id == R.id.item3) {
            item1.setTextColor(def)
            item3.setTextColor(Color.WHITE)
            item2.setTextColor(def)
            val size = item2.width * 2
            select.animate().x(size.toFloat()).duration = 100
            if (active == Active.FIRST) {
                val action = SourcesListFragmentDirections.actionFirstToThird()
                v.findNavController().navigate(action)
                active = Active.THIRD
            } else
                if (active == Active.SECOND) {
                    val action = SecondFragmentDirections.actionSecondToThird()
                    v.findNavController().navigate(action)
                    active = Active.THIRD
                }
        }
    }


    override fun onSupportNavigateUp(): Boolean =
        findNavController(R.id.nav_host_fragment).navigateUp()
}