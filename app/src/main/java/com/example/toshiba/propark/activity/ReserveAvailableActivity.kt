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
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ReserveAvailableActivity : AppCompatActivity() {
    val db = Firebase.firestore
    var databaseReference: FirebaseFirestore? = null
    private var progressDialog: ProgressBar? = null
    var spot: TextView? = null
    var av = 0
    var bk = 0
    private var currentUser: FirebaseUser? = null
    var dref: DatabaseReference? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reserveavailable)
        //databaseReference = FirebaseDatabase.getInstance().reference
        val intent = intent
        val ftime = intent.getIntExtra("fromtime", 0)
        val ttime = intent.getIntExtra("totime", 0)
        val spotID = intent.getStringExtra("spotID")
        val areaName = intent.getStringExtra("areaName")
        var docId = ""
        //ftime?.let { Log.w("from", it.toString()) }
        spot = findViewById<View>(R.id.availability_status) as TextView
        currentUser = FirebaseAuth.getInstance().currentUser
        dref = FirebaseDatabase.getInstance().reference
        progressDialog = findViewById(R.id.progressbar)
        databaseReference = Firebase.firestore
        databaseReference!!.collection("Parking Lot").document(areaName.toString())
            .collection("spotID").whereEqualTo("spotID", spotID).get()
            .addOnSuccessListener { documents ->
                Log.w("docsLenght", documents.size().toString())
                for (doc in documents) {
                    Log.w("docData", doc.data.toString())
                    av = doc.getLong("Available")?.toInt() ?: 0
                    bk = doc.getLong("Booked")?.toInt() ?: 0
                    docId = doc.id
                }
                progressDialog?.visibility = View.GONE
                spot!!.text = av?.toString()
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
            Log.w("avail", av.toString())
            if (av == 0) {
                Toast.makeText(
                    applicationContext,
                    "SPOT NOT AVAILABLE!",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                  //  dref!!.child("User").child(currentUser!!.uid).child("from").setValue(ftime)
                 //   dref!!.child("User").child(currentUser!!.uid).child("to").setValue(ttime)
                //databaseReference!!.collection("User").whereEqualTo("to", to).setValue(ttime)
               /* if (spotID == "BBA SPOT 1") {
                    dref!!.child("User").child(currentUser!!.uid).child("spotid").setValue(1)
                } else if (spotID == "BBA SPOT 2") {
                    dref!!.child("User").child(currentUser!!.uid).child("spotid").setValue(2)
                } else if (spotID == "BBA SPOT 3") {
                    dref!!.child("User").child(currentUser!!.uid).child("spotid").setValue(3)
                }
                else if (spotID == "BBB SPOT 1") {
                    dref!!.child("User").child(currentUser!!.uid).child("spotid").setValue(4)
                } else if (spotID == "BBB SPOT 2") {
                    dref!!.child("User").child(currentUser!!.uid).child("spotid").setValue(5)
                } else if (spotID == "BBB SPOT 3") {
                    dref!!.child("User").child(currentUser!!.uid).child("spotid").setValue(6)
                } else if (spotID == "WC SPOT 1") {
                    dref!!.child("User").child(currentUser!!.uid).child("spotid").setValue(7)
                } else if (spotID == "WC SPOT 2") {
                    dref!!.child("User").child(currentUser!!.uid).child("spotid").setValue(8)
                } else if (spotID == "WC SPOT 3") {
                    dref!!.child("User").child(currentUser!!.uid).child("spotid").setValue(9)

                */



                dref!!.child("User").child(currentUser!!.uid).child("spotid").setValue(spotID)
                if (!spotID.isNullOrEmpty()) {
                    /*dref!!.child("Parking Lot").child(areaName.toString())
                        .child("spotID").child(docId).child("Available").setValue(av - 1)
                    dref!!.child("Parking Lot").child(areaName.toString())
                        .child("spotID").child(docId).child("Booked").setValue(bk + 1)

                     */
                    db.collection("Parking Lot").document(areaName.toString())
                        .collection("spotID").document(docId)
                        .update("Available",FieldValue.increment(-1))
                    db.collection("Parking Lot").document(areaName.toString())
                        .collection("spotID").document(docId)
                        .update("Booked",FieldValue.increment(1))
                }

                Toast.makeText(applicationContext, "SPOT RESERVED!", Toast.LENGTH_LONG).show()
                val back = Intent(this, ProfileActivity::class.java)
                startActivity(back)
            }
            val backButton = findViewById<View>(R.id.back) as Button
            backButton.setOnClickListener {
                val backIntent = Intent(this, ProfileActivity::class.java)
                startActivity(backIntent)
            }
            val back1 = findViewById<View>(R.id.back1) as Button
            back1.setOnClickListener {
                val back1Intent = Intent(this, ReserveSpotsActivity::class.java)
                startActivity(back1Intent)
            }


        }
    }
}

/*
        databaseReference!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                progressDialog.visibility = View.GONE
                if (!areaName.isNullOrEmpty()) {
                    val spot = dataSnapshot.child("Parking Lot").child(areaName).child("Available")
                        .getValue(Int::class.java)!!
                    Log.d("TAG", "onvaluereceival: $spot")
                    area!!.text = Integer.toString(spot) + " Slots"
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                progressDialog.visibility = View.GONE
                Log.e("TAG", "Failed to read value.")
            }
        })


       /* val backButton = findViewById<View>(R.id.back) as Button
        backButton.setOnClickListener {
            val backIntent = Intent(this, ProfileActivity::class.java)
            startActivity(backIntent)
            finish()
        }
        val back1 = findViewById<View>(R.id.back1) as Button
        back1.setOnClickListener {
            val back = Intent(this, ListActivity::class.java)
            startActivity(back)
        }
       /* val bookAndPay = findViewById<View>(R.id.booknpay) as Button
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
                    dref!!.child("Parking Lot").child(areaName).child("Available").setValue(av - 1)
                    dref!!.child("Parking Lot").child(areaName).child("Booked").setValue(bk + 1)
                }

                Toast.makeText(applicationContext, "Booking Successful!", Toast.LENGTH_LONG).show()
                val back = Intent(this, ProfileActivity::class.java)
                startActivity(back)
            }
        }
    }

    override fun onBackPressed() {
        finish()
    }
}*/