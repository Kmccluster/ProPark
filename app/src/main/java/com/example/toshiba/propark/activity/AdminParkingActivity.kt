package com.example.toshiba.propark.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
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
import com.example.toshiba.propark.R
import com.example.toshiba.propark.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AdminParkingActivity : AppCompatActivity() {
    private var parking_locations: Button? = null
    private var edit_locations: Button? = null
    private var addlocation: Button? = null
    private var thename: TextView? = null
    private var progressDialog: ProgressBar? = null

    private var currentUser: FirebaseUser? = null
    var databaseReference: FirebaseFirestore? = null
    private var user : User? = null
    var from = 0
    var to = 0

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adminparking)
        parking_locations = findViewById<View>(R.id.parkinglotbutton) as Button
        edit_locations = findViewById<View>(R.id.editlots) as Button
        addlocation = findViewById<View>(R.id.addlocation) as Button
        thename = findViewById(R.id.nameid)
        currentUser = FirebaseAuth.getInstance().currentUser
        Log.w("currentUser", currentUser!!.uid)
        databaseReference = Firebase.firestore
        progressDialog = findViewById(R.id.progressbar)
        parking_locations!!.setOnClickListener {
            val listIntent = Intent(this@AdminParkingActivity, ParkingLotListActivity::class.java)
            if (!(from == 0 && to == 0)) {
                Toast.makeText(applicationContext, "CHECK OUT THE PARKING LOTS!", Toast.LENGTH_LONG).show()
            } else {
                startActivity(listIntent)
            }
        }
        addlocation!!.setOnClickListener {
            val intent = Intent(this@AdminParkingActivity, ParkingLotAdmin::class.java)
            startActivity(intent)
        }
        edit_locations!!.setOnClickListener {
                    val listIntent = Intent(this@AdminParkingActivity, EditLotActivity::class.java)
                    startActivity(listIntent)
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
}