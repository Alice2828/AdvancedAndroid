package com.example.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.PagerAdapter
import com.example.newsapp.adapter.SlidePagerAdapter
import com.example.newsapp.view.LockableViewPager
import com.example.newsapp.view.RepoListFragment
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
        //     list.add(RepoListFragment())
//        list.add(fragmentLike)
//        list.add(fragmentProfile)
        pager = findViewById(R.id.pager)
        pager.setSwipable(false)
        bottomNavigationView = findViewById(R.id.bottom_navigation)
        pagerAdapter = SlidePagerAdapter(supportFragmentManager, list)
        pager.adapter = pagerAdapter
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> {
                    pager.setCurrentItem(0, false)
                    val bundle = Bundle()
                    bundle.putString("page_name", "Main_page")
                }
                R.id.like_posts -> {
//                    pager.setCurrentItem(1, false)
//                    val bundle = Bundle()
//                    bundle.putString("page_name", "Like_page")
                }
            }
            false
        }
//        fragmentManager = supportFragmentManager
//
//        fragmentManager.beginTransaction().replace(R.id.main_nav_fragment, RepoListFragment())
//            .commit()

    }
}
