package com.example.test_splash.firebase

import android.app.Activity
import android.util.Log
import android.widget.Toast
import com.example.test_splash.activities.AddBooksActivity
import com.example.test_splash.activities.MainActivity
import com.example.test_splash.activities.RemoveBooksActivity
import com.example.test_splash.activities.SignInActivity
import com.example.test_splash.activities.SignUpActivity
import com.example.test_splash.models.Book
import com.example.test_splash.models.User
import com.example.test_splash.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import java.util.Locale
import kotlin.math.log

class FirestoreClass {

    private val mFireStore = FirebaseFirestore.getInstance()

    fun addBook(activity: AddBooksActivity, book: Book){
        val documentId = book.title.lowercase(Locale.ROOT) // Convert title to lowercase
        mFireStore.collection(Constants.BOOKS)
            .document(book.title.lowercase())
            .set(book, SetOptions.merge())
            .addOnSuccessListener {
                Log.e(activity.javaClass.simpleName, "Book added successfully")
                Toast.makeText(activity, "Book added successfully.", Toast.LENGTH_SHORT).show()
                activity.bookAddedSuccessfully()
            }
            .addOnFailureListener{
                e ->
                Log.e(
                    activity.javaClass.simpleName,
                    "Error adding book",
                    e
                )
            }
    }

    fun deleteBook(activity: RemoveBooksActivity, title: String){
        mFireStore.collection(Constants.BOOKS)
            .whereEqualTo("title", title.lowercase())
            .get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    // No books found with the provided title
                    activity.bookDeletedFailed()
                } else {
                    for (document in documents) {
                        val documentID = document.id
                        // Delete book
                        mFireStore.collection(Constants.BOOKS)
                            .document(documentID)
                            .delete()
                            .addOnSuccessListener {
                                // Document successfully deleted
                                activity.bookDeletedSuccessfully()
                            }
                            .addOnFailureListener { e ->
                                // Handle any errors
                                activity.bookDeletedFailed()
                            }
                    }
                }
            }
            .addOnFailureListener { e ->
                // Handle any errors with the query
                activity.bookDeletedFailed()
            }
    }

    fun registerUser(activity: SignUpActivity, userInfo : User){
        mFireStore.collection(Constants.USERS)
            .document(getCurrentUserID())
            .set(userInfo, SetOptions.merge())
            .addOnSuccessListener {
                activity.userRegisteredSuccess()
            }
            .addOnFailureListener { e ->
                Log.e(
                    activity.javaClass.simpleName,
                    "Error writing document",
                    e
                )
            }
    }

    fun signInUser(activity: Activity){
        mFireStore.collection(Constants.USERS)
            .document(getCurrentUserID())
            .get()
            .addOnSuccessListener {document ->
                val loggedInUser = document.toObject(User::class.java)

                when(activity){
                    is SignInActivity ->{
                        if(loggedInUser != null)
                            activity.signInSuccess(loggedInUser)
                    }
                    is MainActivity->{
                        if(loggedInUser != null)
                            activity.updateNavigationUserDetails(loggedInUser)
                    }
                }
            }
            .addOnFailureListener {
                e ->
                when(activity){
                    is SignInActivity ->{
                        activity.hideProgressDialog()
                    }
                    is MainActivity->{
                        activity.hideProgressDialog()
                    }
                }
                Log.e(
                    activity.javaClass.simpleName,
                    "Error writing document",
                    e
                )
            }
    }
    fun getCurrentUserID(): String{
        var currentUser = FirebaseAuth.getInstance().currentUser
        var currentUserID = ""

        if(currentUser != null)
            currentUserID = currentUser.uid

        return currentUserID
    }

}