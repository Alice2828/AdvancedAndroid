package com.example.newsapp.view.activities

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.newsapp.R
import com.example.newsapp.view.fragments.SecondFragmentDirections
import com.example.newsapp.view.fragments.SourcesListFragmentDirections
import com.example.newsapp.view.fragments.ThirdFragmentDirections
import kotlinx.android.synthetic.main.activity_sources.*


enum class Active {
    FIRST, SECOND, THIRD
}

class SourcesActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var def: ColorStateList
    var active = Active.FIRST

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sources)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val navGraph = navController.navInflater.inflate(R.navigation.nav_graph)
        setActionBar()
        active = intent.getSerializableExtra("active") as Active
        def = item2.textColors

        when (active) {
            Active.SECOND -> {
                item1.setTextColor(def)
                item2.setTextColor(Color.WHITE)
                item3.setTextColor(def)
                select.setBackgroundResource(0)
                select2.background =
                    ResourcesCompat.getDrawable(resources, R.drawable.back_select, null)
                navGraph.startDestination = R.id.secondFragment

            }
            Active.THIRD -> {
                item1.setTextColor(def)
                item3.setTextColor(Color.WHITE)
                item2.setTextColor(def)
                select.setBackgroundResource(0)
                select3.background =
                    ResourcesCompat.getDrawable(resources, R.drawable.back_select, null)

                navGraph.startDestination = R.id.thirdFragment

            }
            else -> {
                navGraph.startDestination = R.id.sourcesFragment

            }
        }
        navController.graph = navGraph
        setOnClicks()
    }

    private fun setActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val upArrow = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_material);
        upArrow?.setColorFilter(
            ContextCompat.getColor(this, R.color.colorAccent),
            PorterDuff.Mode.SRC_ATOP
        );
        supportActionBar?.setHomeAsUpIndicator(upArrow)
    }

    private fun setOnClicks() {
        item1.setOnClickListener(this)
        item2.setOnClickListener(this)
        item3.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (v != null) {
            Navigation.setViewNavController(v, findNavController(R.id.nav_host_fragment))
        }
        if (v?.id == R.id.item1) {
            select.background =
                ResourcesCompat.getDrawable(resources, R.drawable.back_select, null)
            select2.setBackgroundResource(0)
            select3.setBackgroundResource(0)

//            select.animate().x(0F).duration = 100;
//            item1.setTextColor(Color.WHITE)
//            item2.setTextColor(def)
//            item3.setTextColor(def)
//            if (active == Active.SECOND) {
//                val action = SecondFragmentDirections.actionSecondToFirst()
//                v.findNavController().navigate(action)
//                active = Active.FIRST
//            } else if (active == Active.THIRD) {
//                val action = ThirdFragmentDirections.actionThirdToFirst()
//                v.findNavController().navigate(action)
//                active = Active.FIRST
//            }
            val intent = Intent(this, SourcesActivity::class.java)
            val bundle = Bundle()
            bundle.putSerializable("active", Active.FIRST)
            intent.putExtras(bundle)
            startActivity(intent)
            finish()
        } else if (v?.id == R.id.item2) {
            select.background =
                ResourcesCompat.getDrawable(resources, R.drawable.back_select, null)
            select2.setBackgroundResource(0)
            select3.setBackgroundResource(0)

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
            select.background =
                ResourcesCompat.getDrawable(resources, R.drawable.back_select, null)
            select2.setBackgroundResource(0)
            select3.setBackgroundResource(0)

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

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.itemId
        if (id == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}