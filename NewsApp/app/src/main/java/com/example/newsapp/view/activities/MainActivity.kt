package com.example.newsapp.view.activities

import android.content.Intent
import android.graphics.PorterDuff
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.newsapp.R
import com.example.newsapp.view.fragments.GeneralListFragment
import com.example.newsapp.view.fragments.LikesFragment
import com.example.newsapp.view.fragments.ProfileFragment
import com.example.newsapp.view.fragments.RepoListFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    private var repoListFragment = RepoListFragment()
    private var generalListFragment = GeneralListFragment()
    private var likesFragment = LikesFragment()
    private var profileFragment = ProfileFragment()
    private lateinit var active: Fragment
    private lateinit var toggle: ActionBarDrawerToggle


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //drawer setting
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        toggle.drawerArrowDrawable.color = ContextCompat.getColor(this, R.color.colorAccent)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.miItem1 -> {
                    val intent = Intent(this, SourcesActivity::class.java)
                    val bundle = Bundle()
                    bundle.putSerializable("active", Active.FIRST)
                    intent.putExtras(bundle)
                    startActivity(intent)
                }
                R.id.miItem2 -> {
                    val intent = Intent(this, SourcesActivity::class.java)
                    val bundle = Bundle()
                    bundle.putSerializable("active", Active.SECOND)
                    intent.putExtras(bundle)
                    startActivity(intent)
                }
                R.id.miItem3 -> {
                    val intent = Intent(this, SourcesActivity::class.java)
                    val bundle = Bundle()
                    bundle.putSerializable("active", Active.THIRD)
                    intent.putExtras(bundle)
                    startActivity(intent)
                }
            }
            true
        }
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}
