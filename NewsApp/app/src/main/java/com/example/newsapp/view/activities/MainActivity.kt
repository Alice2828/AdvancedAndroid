package com.example.newsapp.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.newsapp.R
import com.example.newsapp.view.fragments.GeneralListFragment
import com.example.newsapp.view.fragments.LikesFragment
import com.example.newsapp.view.fragments.ProfileFragment
import com.example.newsapp.view.fragments.RepoListFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    private var repoListFragment = RepoListFragment()
    private var generalListFragment = GeneralListFragment()
    private var likesFragment = LikesFragment()
    private var profileFragment = ProfileFragment()
    private lateinit var active: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //create all fragments
        var fm = supportFragmentManager
        fm.beginTransaction().add(R.id.main_container, profileFragment, "4").hide(profileFragment)
            .commit()
        fm.beginTransaction().add(R.id.main_container, likesFragment, "3").hide(
            likesFragment
        ).commit()
        fm.beginTransaction().add(R.id.main_container, generalListFragment, "2")
            .hide(generalListFragment).commit()
        fm.beginTransaction().add(R.id.main_container, repoListFragment, "1").commit()
        //choose active
        active = repoListFragment
        //bottomNav
        bottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.top -> {
                    fm.beginTransaction().hide(active).show(repoListFragment).commit()
                    active = repoListFragment
                }
                R.id.all -> {
                    fm.beginTransaction().hide(active).show(generalListFragment).commit()
                    active = generalListFragment
                }
                R.id.likes -> {
                    fm.beginTransaction().hide(active).show(likesFragment).commit()
                    active = likesFragment
                }
                R.id.profile -> {
                    fm.beginTransaction().hide(active).show(profileFragment).commit()
                    active = profileFragment
                }
            }
            false
        }
    }
}
