package com.example.test_splash.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.test_splash.R
import com.example.test_splash.databinding.ActivityMainBinding
import com.example.test_splash.databinding.ActivityMyProfileBinding

class MyProfileActivity : BaseActivity() {

    private var binding : ActivityMyProfileBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_my_profile)
        binding = ActivityMyProfileBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setActionBar()
    }

    private fun setActionBar(){
        setSupportActionBar(binding?.toolbarMyProfileActivity)
        val actionBar = supportActionBar
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)
            actionBar.title = resources.getString(R.string.my_profile_title)
        }

        binding?.toolbarMyProfileActivity?.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }
    }
}