package com.example.pagingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pagingapp.R
import com.example.pagingapp.UserViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = UserAdapter()

        recycler_view.layoutManager = LinearLayoutManager(this)

        val itemViewModel = ViewModelProviders.of(this)
                .get(UserViewModel::class.java)

        itemViewModel.userPagedList.observe(this, Observer {
            adapter.submitList(it)
        })

        recycler_view.adapter = adapter
    }
}
