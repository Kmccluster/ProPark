package com.example.toshiba.propark.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences.Editor
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.toshiba.propark.Constants
import com.example.toshiba.propark.Constants.loginSharedPref
import com.example.toshiba.propark.R
import com.example.toshiba.propark.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*


class ProfileActivity : AppCompatActivity() {

    private var list_button: Button? = null
    private var reserve_button: Button? = null
    private var signout_button: Button? = null
    private var status: Button? = null
    private var editprofile: Button? = null
    private var thename: TextView? = null
    private var progressDialog: ProgressBar? = null

    private var currentUser: FirebaseUser? = null
    var databaseReference: FirebaseFirestore? = null
    private var user : User? = null
    var from = 0
    var to = 0
    var loc = ""
    var avv = 0
    var bkk = 0

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        thename = findViewById(R.id.nameid)
        currentUser = FirebaseAuth.getInstance().currentUser
        //Log.w("currentUser", currentUser!!.uid)
        databaseReference = Firebase.firestore
        progressDialog = findViewById(R.id.progressbar)
        list_button = findViewById<View>(R.id.list) as Button
        reserve_button = findViewById<View>(R.id.reserve) as Button
        signout_button = findViewById<View>(R.id.logout_button) as Button
        status = findViewById<View>(R.id.statusbutton) as Button
        editprofile = findViewById<View>(R.id.editprofile) as Button
        list_button!!.setOnClickListener {
            val listIntent = Intent(this@ProfileActivity, BookActivity::class.java)
            if (!(from == 0 && to == 0)) {
                Toast.makeText(applicationContext, "ALREADY BOOKED! CHECK STATUS", Toast.LENGTH_LONG).show()
            } else {
                startActivity(listIntent)
            }
        }
        reserve_button!!.setOnClickListener {
            val intent = Intent(this@ProfileActivity, ReserveSpotsActivity::class.java)
            startActivity(intent)
        }
        status!!.setOnClickListener {
            val intent = Intent(this@ProfileActivity, StatusActivity::class.java)
            startActivity(intent)
        }
        editprofile!!.setOnClickListener {
            val intent = Intent(this@ProfileActivity, EditProfileActivity::class.java)
            startActivity(intent)
        }
        signout_button!!.setOnClickListener { //Log out action
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            builder.setMessage("If you log out, your booking will be cancelled, if any. Are you sure you want to proceed?").setTitle("WARNING!")
                    .setCancelable(false)
                    .setPositiveButton("Yes, Log me out", DialogInterface.OnClickListener { _, _ ->
                        FirebaseAuth.getInstance().signOut()
                        val prefs = getSharedPreferences(loginSharedPref, Context.MODE_PRIVATE)
                        val edit: Editor = prefs.edit()
                        edit.clear()
                        edit.putString(Constants.userLoginStatus, Constants.isNotLoggedIn)
                        edit.apply()
                        resetParams()
                        finish()
                        Toast.makeText(applicationContext, "SIGNED OUT", Toast.LENGTH_LONG).show()
                        val listIntent = Intent(this@ProfileActivity, MainActivity::class.java)
                        startActivity(listIntent)
                    })
                    .setNegativeButton("No, I'll stay") { dialog, _ ->
                        dialog.cancel()
                    }
            val alertDialog: AlertDialog = builder.create()
            alertDialog.show()
        }
    }

    override fun onBackPressed() {
        FirebaseAuth.getInstance().signOut()
        finish()
    }

    override fun onResume() {
        super.onResume()

        databaseReference!!.collection("User").whereEqualTo("uid", currentUser!!.uid).get()
            .addOnSuccessListener { documents ->
                Log.w("docsLenght", documents.size().toString())

                for (doc in documents) {
                    Log.w("docs", doc.data.toString())
                    user = doc.toObject(User::class.java)
                    user!!.name?.let { Log.w("userObj", it) }
                    progressDialog?.visibility = View.GONE
                }
                thename!!.text = user?.name ?: "unable to load"


            }
    }

    private fun resetParams() {

    }

    private fun updateStatus() {
        val cal = Calendar.getInstance()
        val hourOfDay = cal[Calendar.HOUR_OF_DAY]
        if (to <= hourOfDay && to != 0) {
            resetParams()
        }
    }
}