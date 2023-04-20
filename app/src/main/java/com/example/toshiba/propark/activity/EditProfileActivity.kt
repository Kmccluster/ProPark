package com.example.toshiba.propark.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
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
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class EditProfileActivity : AppCompatActivity() {
    private var currentuser: FirebaseUser? = null
    private var databaseUsers: DatabaseReference? = null
    var databaseReference: FirebaseFirestore? = null
    private var progressDialog: ProgressBar? = null
    private var user: User? = null
    private var xname: TextView? = null
    private var xemail: TextView? = null
    private var xphone: TextView? = null
    private var etName: EditText? = null
    private var etEmail: EditText? = null
    private var etPhone: EditText? = null
    private var sName: TextView? = null
    private var sEmail: TextView? = null
    private var sPhone: TextView? = null
    private var updateprofile: Button? = null
    var record : DocumentReference? = null
    var dref: DatabaseReference? = null

    @SuppressLint("CutPasteId", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set the content of the activity to use the activity_main.xml layout file
        setContentView(R.layout.activity_editprofile)
        xname = findViewById<View>(R.id.name1) as TextView
        xemail = findViewById<View>(R.id.email1) as TextView
        xphone = findViewById<View>(R.id.phonenumber1) as TextView
        etName = findViewById<View>(R.id.updatename) as EditText?
        etEmail = findViewById<View>(R.id.updateemail) as EditText?
        etPhone = findViewById<View>(R.id.updatephone) as EditText?
        updateprofile = findViewById(R.id.updateprofilebutton)
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
                    record = doc.reference
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

                        val sName = etName!!.text.toString()
                        val sEmail = etEmail!!.text.toString()
                        val sPhone = etPhone!!.text.toString()

                        Log.w("testing", "onCreate:$sName$sEmail$sPhone")

                        etName!!.setText(sName)
                        etEmail!!.setText(sEmail)
                        etPhone!!.setText(sPhone)

                        val editMap = mapOf(
                            "name" to sName,
                            "email" to sEmail,
                            "phoneNumber" to sPhone
                        )
                        databaseReference!!.collection("user").document(currentuser!!.uid).update(editMap)



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
//                    val progressDialog: ProgressBar = findViewById(R.id.progressbar)
                    val progressDialog2: ProgressBar = findViewById(R.id.progressbar2)
                    databaseUsers?.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                           // progressDialog.visibility = View.GONE
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

                                    }


                        override fun onCancelled(databaseError: DatabaseError) {
                           // progressDialog.visibility = View.GONE
                            progressDialog2.visibility = View.GONE
                        }

                    })
                    updateprofile!!.setOnClickListener {
                         databaseReference!!.collection("User").whereEqualTo("uid", currentuser!!.uid).get().addOnSuccessListener { docs ->

                           for (doc in docs)
                          {
                              val sName = etName!!.text.toString()
                              val sEmail = etEmail!!.text.toString()
                              val sPhone = etPhone!!.text.toString()
                              xname!!.text = sName
                              xemail!!.text = sEmail
                              xphone!!.text = sPhone
                              val ref = doc.reference
                              ref.update(mapOf("name" to sName, "email" to sEmail, "phoneNumber" to sPhone))

                          }
                       // databaseReference!!.collection("user").document(currentuser!!.uid).update(editMap)
                             dref!!.child("User").child(currentuser!!.uid).child("name").setValue(sName)
                             dref!!.child("User").child(currentuser!!.uid).child("email").setValue(sEmail)
                             dref!!.child("User").child(currentuser!!.uid).child("phoneNumber").setValue(sPhone)

                        Toast.makeText(this, "Updated Successfully!", Toast.LENGTH_SHORT).show()

                       // databaseReference!!.collection("User").whereEqualTo("uid", currentuser!!.uid).get().addOnSuccessListener { docs ->

                         //   for (doc in docs)
                          //  {
                                //val editMap = mapOf(
                                 //   "name" to name,
                                  //  "email" to email,
                                   // "phoneNumber" to phone
                               // )
                             //   val ref = doc.reference
                           //   ref.update(mapOf("name" to etName, "email" to etEmail, "phoneNumber" to etPhone))
                          //  }
                           // dref!!.child("User").child(currentuser!!.uid).child("name").setValue(sName)
                          //  dref!!.child("User").child(currentuser!!.uid).child("email").setValue(sEmail)
                          //  dref!!.child("User").child(currentuser!!.uid).child("phoneNumber").setValue(sPhone)

                        }
                    }

                    val button2 = findViewById<View>(R.id.backx) as Button
                    button2.setOnClickListener {
                        val button2Intent = Intent(this, ProfileActivity::class.java)
                        startActivity(button2Intent)
                        finish()
                    }


                    }
                }

                fun onBackPressed() {
                    finish()
                }
            }
    }
