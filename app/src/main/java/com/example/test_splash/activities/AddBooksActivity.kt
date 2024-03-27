package com.example.test_splash.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.EditText
import android.widget.Toast
import com.example.test_splash.R
import com.example.test_splash.databinding.ActivityAddbooksBinding
import com.example.test_splash.databinding.ActivityIntroBinding
import com.example.test_splash.databinding.ContentMainBinding
import com.example.test_splash.firebase.FirestoreClass
import com.example.test_splash.models.Book
import com.example.test_splash.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class AddBooksActivity : BaseActivity() {
    private var binding : ActivityAddbooksBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddbooksBinding.inflate(layoutInflater)

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
        binding?.saveBookButton?.setOnClickListener {
            addNewBook()
        }
    }

    fun bookAddedSuccessfully(){
        Toast.makeText(
            this, "you have successfully" +
                    " added this book", Toast.LENGTH_LONG
        ).show()
        hideProgressDialog()
        finish()
    }

    private fun addNewBook(){
        val etTitle: EditText = findViewById(R.id.uploadTitle)
        val title: String = etTitle.text.toString().trim { it <= ' '}

        val etAuthor: EditText = findViewById(R.id.uploadAuthor)
        val author: String = etAuthor.text.toString().trim { it <= ' '}

        val etShelfNumber: EditText = findViewById(R.id.uploadShelfNumber)
        val shelfNumber: String = etShelfNumber.text.toString().trim { it <= ' '}
        
        val book = Book(title, author, shelfNumber)

        if(validateBookEntry(title,author,shelfNumber)){
            showProgressDialog(resources.getString(R.string.please_wait))
            FirestoreClass().addBook(this, book)
        }
    }

    private fun validateBookEntry(title: String, author: String, shelf: String) : Boolean{
        return when{
            TextUtils.isEmpty(title)->{
                showErrorSnackBar("Please enter a title")
                false
            }

            TextUtils.isEmpty(author)->{
                showErrorSnackBar("Please enter an author")
                false
            }

            TextUtils.isEmpty(shelf)->{
                showErrorSnackBar("Please enter a shelf")
                false
            }else ->{
                true
            }
        }
    }
}

