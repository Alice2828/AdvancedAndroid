package com.example.newsapp.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.PagerAdapter
import com.example.newsapp.R
import com.example.newsapp.adapter.SlidePagerAdapter
import com.example.newsapp.view.fragments.GeneralListFragment
import com.example.newsapp.view.LockableViewPager
import com.example.newsapp.view.fragments.ProfileFragment
import com.example.newsapp.view.fragments.RepoListFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var fragmentManager: FragmentManager
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var pager: LockableViewPager
    private var list: MutableList<Fragment> = ArrayList()
    private lateinit var pagerAdapter: PagerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        list.add(RepoListFragment())
        list.add(GeneralListFragment())
        list.add(ProfileFragment())
//        list.add(fragmentProfile)
        pager = findViewById(R.id.pager)
        pager.setSwipable(false)
        bottomNavigationView = findViewById(R.id.bottom_navigation)
        pagerAdapter = SlidePagerAdapter(supportFragmentManager, list)
        pager.adapter = pagerAdapter
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.top -> {
                    pager.setCurrentItem(0, false)
                }
                R.id.all -> {
                    pager.setCurrentItem(1, false)
                }
                R.id.profile -> {
                    pager.setCurrentItem(2, false)
                }
            }
            false
        }


    }


}
