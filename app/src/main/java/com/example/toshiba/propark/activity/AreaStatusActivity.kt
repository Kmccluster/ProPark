package com.example.toshiba.propark.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.toshiba.propark.R
import com.example.toshiba.propark.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AreaStatusActivity : AppCompatActivity() {
    var databaseReference: FirebaseFirestore? = null
    private var progressDialog: ProgressBar? = null
    var area: TextView? = null
    var areagone: TextView? = null
    var av = 0
    var bk = 0
    private var currentUser: FirebaseUser? = null
    var dref: DatabaseReference? = null
    var record : DocumentReference? = null
    //private lateinit var database: FirebaseFirestore
    private var bookAndPay: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_area_status)
        //databaseReference = FirebaseDatabase.getInstance().reference
        val intent = intent
        val ftime = intent.getIntExtra("fromtime", 0)
        val ttime = intent.getIntExtra("totime", 0)
        val areaName = intent.getStringExtra("Area")
        //ftime?.let { Log.w("from", it.toString()) }
        //database = Firebase.database.reference
        area = findViewById<View>(R.id.availability_status) as TextView
        areagone = findViewById(R.id.booked_status) as TextView
        currentUser = FirebaseAuth.getInstance().currentUser
        dref = FirebaseDatabase.getInstance().reference
        //val db = FirebaseFirestore.getInstance()
        progressDialog = findViewById(R.id.progressbar)
        databaseReference = Firebase.firestore
        databaseReference!!.collection("Parking Lot").whereEqualTo("areaName", areaName).get()
            .addOnSuccessListener { documents ->
                Log.w("docsLenght", documents.size().toString())
                for (doc in documents) {
                    Log.w("docData", doc.data.toString())
                    av = doc.getLong("available")?.toInt() ?: 0
                    bk = doc.getLong("booked")?.toInt() ?: 0
                    record = doc.reference
                }
                progressDialog?.visibility = View.GONE
                area!!.text = av?.toString()
                areagone!!.text = bk?.toString()
                progressDialog?.visibility = View.GONE
                progressDialog?.visibility = View.GONE

                val backButton = findViewById<View>(R.id.back) as Button
                backButton.setOnClickListener {
                    val backIntent = Intent(this, AdminActivity::class.java)
                    startActivity(backIntent)
                    finish()
                }
            }
            val back1 = findViewById<View>(R.id.back1) as Button
            back1.setOnClickListener {
            val back = Intent(this, AreaListActivity::class.java)
            startActivity(back)
            }
    }
    override fun onBackPressed() {
        finish()
    }
}
