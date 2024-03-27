package com.example.test_splash.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import com.bumptech.glide.Glide
import com.example.test_splash.R
import com.example.test_splash.databinding.ActivityMainBinding
import com.example.test_splash.databinding.ActivitySignInBinding
import com.example.test_splash.databinding.ContentMainBinding
import com.example.test_splash.databinding.NavHeaderMainBinding
import com.example.test_splash.firebase.FirestoreClass
import com.example.test_splash.models.User
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var binding : ActivityMainBinding? = null
    //private var binding2 : ContentMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        //binding2 = ContentMainBinding.inflate(layoutInflater)

        setContentView(binding?.root)

        setActionBar()
        binding?.navView?.setNavigationItemSelectedListener(this)
        FirestoreClass().signInUser(this)

        binding?.btnAddBook?.setOnClickListener {
            startActivity(Intent(this, AddBooksActivity::class.java))
        }

        binding?.btnRemoveBook?.setOnClickListener {
            startActivity(Intent(this, RemoveBooksActivity::class.java))
        }
    }

    private fun setActionBar(){
        setSupportActionBar(binding?.appBarMainTb)
        binding?.appBarMainTb?.setNavigationIcon(R.drawable.ic_action_naviagation_menu)

        binding?.appBarMainTb?.setNavigationOnClickListener {
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

    fun updateNavigationUserDetails(user: User){
        // The instance of the header view of the navigation view.
        val viewHeader = binding?.navView?.getHeaderView(0)
        val headerBinding = viewHeader?.let { NavHeaderMainBinding.bind(it) }
        headerBinding?.navUserImage?.let {
            Glide
                .with(this)
                .load(user.image) // URL of the image
                .centerCrop() // Scale type of the image.
                .placeholder(R.drawable.ic_user_place_holder) // A default place holder
                .into(it)
        } // the view in which the image will be loaded.

        headerBinding?.tvUsername?.text = user.name
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_my_profile->{
                Toast.makeText(
                    this@MainActivity,
                    "My Profile",
                    Toast.LENGTH_SHORT).show()
            }
            R.id.nav_sign_out->{
                FirebaseAuth.getInstance().signOut()

                val intent = Intent(this, IntroActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_LAUNCH_ADJACENT)
                startActivity(intent)
                finish()
            }
        }
        binding?.drawerLayout!!.closeDrawer(GravityCompat.START)
        return true
    }
}