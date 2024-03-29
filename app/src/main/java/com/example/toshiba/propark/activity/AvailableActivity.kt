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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AvailableActivity : AppCompatActivity() {
    var databaseReference: FirebaseFirestore? = null
    private var progressDialog: ProgressBar? = null
    var area: TextView? = null
    var av = 0
    var bk = 0
    private var currentUser: FirebaseUser? = null
    var dref: DatabaseReference? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_available)
        //databaseReference = FirebaseDatabase.getInstance().reference
        val intent = intent
        val ftime = intent.getIntExtra("fromtime", 0)
        val ttime = intent.getIntExtra("totime", 0)
        val areaName = intent.getStringExtra("Area")
        //ftime?.let { Log.w("from", it.toString()) }
        area = findViewById<View>(R.id.availability_status) as TextView
        currentUser = FirebaseAuth.getInstance().currentUser
        dref = FirebaseDatabase.getInstance().reference
        progressDialog = findViewById(R.id.progressbar)
        databaseReference = Firebase.firestore
        databaseReference!!.collection("Parking Lot").whereEqualTo("areaName", areaName).get()
            .addOnSuccessListener { documents ->
                Log.w("docsLenght", documents.size().toString())
                for (doc in documents) {
                    Log.w("docData", doc.data.toString())
                    av = doc.getLong("available")?.toInt() ?: 0
                    bk = doc.getLong("booked")?.toInt() ?: 0
                }
                progressDialog?.visibility = View.GONE
                area!!.text = av?.toString()
                progressDialog?.visibility = View.GONE

                /*override fun onDataChange(dataSnapshot: DataSnapshot) {
                progressDialog.visibility = View.GONE
                if (!areaName.isNullOrEmpty()) {
                    av = dataSnapshot.child("Parking Lot").child(areaName).child("Available")
                        .getValue(Int::class.java)!!
                    bk = dataSnapshot.child("Parking Lot").child(areaName).child("Booked")
                        .getValue(Int::class.java)!!

             */
            }
        val bookAndPay = findViewById<View>(R.id.booknpay) as Button

        bookAndPay.setOnClickListener {
            if (av == 0) {
                Toast.makeText(
                    applicationContext,
                    "NO SLOTS AVAILABLE FOR BOOKING!",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                dref!!.child("User").child(currentUser!!.uid).child("from").setValue(ftime)
                dref!!.child("User").child(currentUser!!.uid).child("to").setValue(ttime)
                //databaseReference!!.collection("User").whereEqualTo("to", to).setValue(ttime)
                if (areaName == "Benjamin Banneker A") {
                    dref!!.child("User").child(currentUser!!.uid).child("loc").setValue(1)
                } else if (areaName == "Benjamin Banneker B") {
                    dref!!.child("User").child(currentUser!!.uid).child("loc").setValue(2)
                } else if (areaName == "Welcome Center") {
                    dref!!.child("User").child(currentUser!!.uid).child("loc").setValue(3)
                }
                if (!areaName.isNullOrEmpty()) {
                    dref!!.child("Parking Lot").child(areaName).child("available").setValue(av - 1)
                    dref!!.child("Parking Lot").child(areaName).child("booked").setValue(bk + 1)
                }

                Toast.makeText(applicationContext, "Booking Successful!", Toast.LENGTH_LONG).show()
                val back = Intent(this, ProfileActivity::class.java)
                startActivity(back)
            }

        }
        val backButton = findViewById<View>(R.id.back) as Button
        backButton.setOnClickListener {
            Log.w("back-clicked", "true")
            val backIntent = Intent(this@AvailableActivity, ProfileActivity::class.java)
            startActivity(backIntent)
            finish()

        }
        val back1 = findViewById<Button>(R.id.back1)
        back1.setOnClickListener {
            val back1Intent = Intent(this@AvailableActivity, ListActivity::class.java)
            startActivity(back1Intent)
        }
        val reserve = findViewById<View>(R.id.reserve) as Button
        reserve.setOnClickListener {
            val reserveIntent = Intent(this, ReserveBookActivity::class.java)
            startActivity(reserveIntent)
        }
    }
}