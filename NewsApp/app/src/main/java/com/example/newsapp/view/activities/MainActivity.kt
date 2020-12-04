package com.example.newsapp.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Slide
import android.view.Gravity
import android.view.animation.DecelerateInterpolator
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.example.newsapp.R
import com.example.newsapp.utils.CommonUtils
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
        //check for nightMode
//        if (CommonUtils.isNightModeEnabled(this)) {
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//        } else {
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//        }
//        if (!CommonUtils.isToogleEnabled(this)) {
//            if (CommonUtils.isDarkMode(this)) {
//                CommonUtils.setIsNightModeEnabled(this, true);
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//            } else {
//                CommonUtils.setIsNightModeEnabled(this, false);
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//            }
//        } else {
//            if (CommonUtils.isNightModeEnabled(this)) {
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//            } else {
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//            }
//        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //create all fragments
        val fm = supportFragmentManager
        fm.beginTransaction().add(R.id.main_container, profileFragment, "4")
            .commit()
        fm.beginTransaction().add(R.id.main_container, likesFragment, "3").hide(
            likesFragment
        ).commit()
        fm.beginTransaction().add(R.id.main_container, generalListFragment, "2")
            .hide(generalListFragment).commit()
        fm.beginTransaction().add(R.id.main_container, repoListFragment, "1").hide(repoListFragment)
            .commit()
        //choose active
        active = profileFragment
        //bottomNav
        bottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.top -> {
                    fm.beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                        .hide(active).show(repoListFragment).commit()
                    active = repoListFragment
                }
                R.id.all -> {
                    fm.beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                        .hide(active).show(generalListFragment).commit()
                    active = generalListFragment
                }
                R.id.likes -> {
                    fm.beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                        .hide(active).show(likesFragment).commit()
                    active = likesFragment
                }
                R.id.profile -> {
                    fm.beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                        .hide(active).show(profileFragment).commit()
                    active = profileFragment
                }
            }
            false
        }
    }
}
