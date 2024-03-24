package com.example.test_splash.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import com.example.test_splash.R
import com.example.test_splash.databinding.ActivityMainBinding
import com.example.test_splash.databinding.ActivitySignInBinding
import com.google.android.material.navigation.NavigationView

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var binding : ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setActionBar()
    }

    private fun setActionBar(){
        setSupportActionBar(binding?.mainAppBarLayout?.appBarMainTb)
        binding?.mainAppBarLayout?.appBarMainTb?.setNavigationIcon(R.drawable.ic_action_naviagation_menu)

        binding?.mainAppBarLayout?.appBarMainTb?.setNavigationOnClickListener {
            toggleDrawerLayout()
        }
    }

    private fun toggleDrawerLayout(){
        if(binding?.drawerLayout!!.isDrawerOpen(GravityCompat.START)){
            binding?.drawerLayout!!.closeDrawer(GravityCompat.START)
        }
        else{
            binding?.drawerLayout!!.openDrawer(GravityCompat.START)
        }
    }

    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        if (binding?.drawerLayout?.isDrawerOpen(GravityCompat.START) == true) {
            binding?.drawerLayout?.closeDrawer(GravityCompat.START)
        } else {
            // A double back press function is added in Base Activity.
            doubleBackToExit()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

    }
}