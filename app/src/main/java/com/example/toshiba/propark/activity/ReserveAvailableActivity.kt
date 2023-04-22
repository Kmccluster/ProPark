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
                    av = doc.getLong("available")?.toInt() ?: 0
                    bk = doc.getLong("booked")?.toInt() ?: 0
                    docId = doc.id
                }
                progressDialog?.visibility = View.GONE
                spot!!.text = av?.toString()
                progressDialog?.visibility = View.GONE
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
            } else
            {
                dref!!.child("User").child(currentUser!!.uid).child("spotid").setValue(spotID)
                if (!spotID.isNullOrEmpty()) {
                    db.collection("Parking Lot").document(areaName.toString())
                        .collection("spotID").document(docId)
                        .update("available",FieldValue.increment(-1))
                    db.collection("Parking Lot").document(areaName.toString())
                        .collection("spotID").document(docId)
                        .update("booked",FieldValue.increment(1))
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