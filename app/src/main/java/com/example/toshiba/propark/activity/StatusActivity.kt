package com.example.toshiba.propark.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.toshiba.propark.R
import com.example.toshiba.propark.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class StatusActivity : AppCompatActivity() {
    private var currentuser: FirebaseUser? = null
    private var databaseUsers: DatabaseReference? = null
    var databaseReference: FirebaseFirestore? = null
    private var progressDialog: ProgressBar? = null
    private var user: User? = null
    private var xname: TextView? = null
    private var xemail: TextView? = null
    private var xphone: TextView? = null
    private var xfrom: TextView? = null
    private var xtoo: TextView? = null
    private var tvLoc: TextView? = null
    private var from: Int? = 0
    private var to: Int? = 0

    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set the content of the activity to use the activity_main.xml layout file
        setContentView(R.layout.status_layout)
        xname = findViewById<View>(R.id.name1) as TextView
        xemail = findViewById<View>(R.id.email1) as TextView
        xphone = findViewById<View>(R.id.phonenumber1) as TextView
        xfrom = findViewById<View>(R.id.bookedfrom) as TextView
        xtoo = findViewById<View>(R.id.bookedto) as TextView
        tvLoc = findViewById<View>(R.id.location1) as TextView
        progressDialog = findViewById(R.id.progressbar)
        progressDialog = findViewById(R.id.progressbar2)
        currentuser = FirebaseAuth.getInstance().currentUser
        databaseReference = Firebase.firestore
        databaseReference!!.collection("User").whereEqualTo("uid", currentuser!!.uid).get()
            .addOnSuccessListener { documents ->
                Log.w("docsLenght", documents.size().toString())

                for (doc in documents) {
                    Log.w("docs", doc.data.toString())
                    user = doc.toObject(User::class.java)
                    user!!.name?.let {
                        Log.w("userObj", it)
                        user!!.email?.let {
                            Log.w("userObj", it)
                            user!!.phoneNumber?.let {
                                Log.w("userObj", it)
                            }
                            progressDialog?.visibility = View.GONE
                            progressDialog?.visibility = View.GONE
                            progressDialog?.visibility = View.GONE
                            progressDialog?.visibility = View.GONE
                            progressDialog?.visibility = View.GONE
                            progressDialog?.visibility = View.GONE

                        }

                            xname!!.text = user?.name ?: "unable to load"
                            xemail!!.text = user?.email ?: "unable to load"
                            xphone!!.text = user?.phoneNumber ?: "unable to load"

                            xfrom!!.text = user?.from.toString()
                            xtoo!!.text = user?.to.toString()
                            tvLoc!!.text = user?.loc ?: "Unable to load"

                        progressDialog?.visibility = View.GONE
                        progressDialog?.visibility = View.GONE
                        progressDialog?.visibility = View.GONE
                        progressDialog?.visibility = View.GONE
                        progressDialog?.visibility = View.GONE
                        progressDialog?.visibility = View.GONE
                        }
                        if (currentuser != null) {
                            databaseUsers = FirebaseDatabase.getInstance().reference
                        }
                        val progressDialog: ProgressBar = findViewById(R.id.progressbar)
                        val progressDialog2: ProgressBar = findViewById(R.id.progressbar2)
                        databaseUsers?.addValueEventListener(object : ValueEventListener {
                            override fun onDataChange(dataSnapshot: DataSnapshot) {
                                progressDialog.visibility = View.GONE
                                progressDialog2.visibility = View.GONE
                                val name =
                                    dataSnapshot.child("User").child(currentuser!!.uid)
                                        .child("name")
                                        .getValue(String::class.java) ?: "NA"
                                val email =
                                    dataSnapshot.child("User").child(currentuser!!.uid)
                                        .child("email")
                                        .getValue(String::class.java) ?: "NA"
                                val phone =
                                    dataSnapshot.child("User").child(currentuser!!.uid)
                                        .child("phoneNumber")
                                        .getValue(String::class.java) ?: "NA"
                                from = dataSnapshot.child("User").child(currentuser!!.uid)
                                    .child("from")
                                    .getValue(Int::class.java) ?: 0
                                to = dataSnapshot.child("User").child(currentuser!!.uid).child("to")
                                    .getValue(Int::class.java) ?: 0
                                val loc =
                                    dataSnapshot.child("User").child(currentuser!!.uid).child("loc")
                                        .getValue(Int::class.java) ?: 0
                                xname!!.text = name
                                xemail!!.text = email
                                xphone!!.text = phone
                                if (from == 0 && to == 0) {
                                    tvLoc!!.text = getString(R.string.noBooking)
                                    xfrom!!.text = getString(R.string.noBooking)
                                    xtoo!!.text = getString(R.string.noBooking)
                                } else {
                                    when (loc) {
                                        1 -> {
                                            tvLoc!!.text = "Welcome Center"
                                            xfrom!!.text = "$from" + "Hrs"
                                            xtoo!!.text = """${to}Hrs"""
                                        }
                                        2 -> {
                                            tvLoc!!.text = "Benjamin Banneker A"
                                            xfrom!!.text = """${from}Hrs"""
                                            xtoo!!.text = """${to}Hrs"""
                                        }
                                        3 -> {
                                            tvLoc!!.text = "Benjamin Bannker B"
                                            xfrom!!.text = """${from}Hrs"""
                                            xtoo!!.text = "${to}Hrs"
                                        }
                                    }
                                }
                            }

                            override fun onCancelled(databaseError: DatabaseError) {
                                progressDialog.visibility = View.GONE
                                progressDialog2.visibility = View.GONE
                            }

                        })


                        val button2 = findViewById<View>(R.id.backx) as Button
                        button2.setOnClickListener {
                            val button2Intent = Intent(this, ProfileActivity::class.java)
                            startActivity(button2Intent)
                            finish()
                        }
                        val button3 = findViewById<View>(R.id.bookagain) as Button
                        button3.setOnClickListener {
                            if (from == 0 && to == 0) {
                                val button = Intent(this, BookActivity::class.java)
                                startActivity(button)
                            } else {
                                val button = Intent(this, BookAgainActivity::class.java)
                                startActivity(button)
                            }
                        }
                    }

                    fun onBackPressed() {
                        finish()
                    }
                }
            }
    }
