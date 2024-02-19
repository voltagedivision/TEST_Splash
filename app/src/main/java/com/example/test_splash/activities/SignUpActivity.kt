
package com.example.test_splash.activities

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.EditText
import android.widget.Toast
import com.example.test_splash.R
import com.example.test_splash.databinding.ActivitySignUpBinding

class SignUpActivity : BaseActivity() {
    private var binding : ActivitySignUpBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            @Suppress("DEPRECATION")
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        setContentView(binding?.root)
        setupActionBar()
    }

    private fun setupActionBar(){
        setSupportActionBar(binding?.toolbarSignUpActivity)
        val actionBar = supportActionBar
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)
        }

        binding?.toolbarSignUpActivity?.setNavigationOnClickListener{ onBackPressedDispatcher.onBackPressed() }

        binding?.btnSignUp?.setOnClickListener {
            registerUser()
        }

    }

    private fun registerUser(){
        val etName: EditText = findViewById(R.id.et_name)
        val name: String = etName.text.toString().trim { it <= ' '}

        val etEmail: EditText = findViewById(R.id.et_email)
        val email: String = etEmail.text.toString().trim { it <= ' '}

        val etPassword: EditText = findViewById(R.id.et_password)
        val password: String = etPassword.text.toString().trim { it <= ' '}

        if(validateForm(name, email, password)){
            Toast.makeText(
                this@SignUpActivity,
                "Now we can register a new user",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun validateForm(name: String, email: String, password: String) : Boolean{
        return when{
            TextUtils.isEmpty(name)->{
                showErrorSnackBar("Please enter a name")
                false
            }

            TextUtils.isEmpty(email)->{
                showErrorSnackBar("Please enter an email")
                false
            }

            TextUtils.isEmpty(password)->{
                showErrorSnackBar("Please enter a password")
                false
            }else ->{
                true
            }


        }
    }
}