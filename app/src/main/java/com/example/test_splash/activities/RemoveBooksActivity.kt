package com.example.test_splash.activities

import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.EditText
import android.widget.Toast
import com.example.test_splash.R
import com.example.test_splash.databinding.ActivityRemovebooksBinding
import com.example.test_splash.firebase.FirestoreClass
import com.example.test_splash.models.Book

class RemoveBooksActivity : BaseActivity() {
    private var binding : ActivityRemovebooksBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRemovebooksBinding.inflate(layoutInflater)

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

        binding?.deleteBookButton?.setOnClickListener {
            deleteThisBook()
        }
    }

    fun bookDeletedSuccessfully(){
        Toast.makeText(
            this, "you have successfully" +
                    " deleted this book", Toast.LENGTH_LONG
        ).show()
        hideProgressDialog()
        finish()
    }

    fun bookDeletedFailed(){
        Toast.makeText(
            this, "you have failed to" +
                    " delete this book", Toast.LENGTH_LONG
        ).show()
        hideProgressDialog()
        finish()
    }

    private fun deleteThisBook(){
        val etTitle: EditText = findViewById(R.id.bookTitleToDelete)
        val title: String = etTitle.text.toString().trim { it <= ' '}

        if(validateTitleEntry(title)){
            showProgressDialog(resources.getString(R.string.please_wait))
            FirestoreClass().deleteBook(this, title)
        }
    }

    private fun validateTitleEntry(title: String) : Boolean{
        return when{
            TextUtils.isEmpty(title)->{
                showErrorSnackBar("Please enter a title...")
                false
            }else ->{
                true
            }
        }
    }
}