package com.example.mvvm.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.example.mvvm.R


class MainActivity : AppCompatActivity() {

    private lateinit var fragmentManager: FragmentManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fragmentManager = supportFragmentManager

        fragmentManager.beginTransaction().replace(R.id.main_nav_fragment, RepoListFragment())
            .commit()

    }
}
