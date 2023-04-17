package com.example.toshiba.propark.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.toshiba.propark.Constants
import com.example.toshiba.propark.R
import com.example.toshiba.propark.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase



class LoginActivity : AppCompatActivity() {
    private var mAuth: FirebaseAuth? = null
    private var mEmailField: EditText? = null
    private var mPasswordField: EditText? = null
    private var submitButton: Button? = null
    var sharedPreferences: SharedPreferences? = null
    private var userName: String? = null
    private var userPass: String? = null
    private var userEmail: String? = null
    private var userMobile: String? = null
    private var currentUser: FirebaseUser? = null
    var databaseReference: FirebaseFirestore? = null
    private var user: User? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        databaseReference = Firebase.firestore
        currentUser = FirebaseAuth.getInstance().currentUser
        mEmailField = findViewById(R.id.name_field)
        mPasswordField = findViewById(R.id.password_field)
        submitButton = findViewById(R.id.submit112)
        mAuth = FirebaseAuth.getInstance()
        sharedPreferences =
            applicationContext.getSharedPreferences("loginDetail", Context.MODE_PRIVATE)
        val progressDialog: ProgressBar = findViewById(R.id.progressbar)
        progressDialog.visibility = View.GONE
        val users = resources.getStringArray(R.array.usertype)

        submitButton!!.setOnClickListener {
            val email = mEmailField!!.text.toString()
            val password = mPasswordField!!.text.toString()
            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(applicationContext, "Fields Are Empty", Toast.LENGTH_LONG).show()
            } else {
                progressDialog.visibility = View.VISIBLE
                mAuth!!.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this@LoginActivity) { it ->
                        progressDialog.visibility = View.GONE
                        if (!it.isSuccessful) {
                            Toast.makeText(
                                applicationContext,
                                "Email/password incorrect",
                                Toast.LENGTH_LONG
                            ).show()
                        } else {

                            //val fUser = task.result!!.user
                            //Log.w("user", mEmailField ?: "a")
                           // Log.w("pass", mPasswordField ?: "h")
                            mAuth!!.signInWithEmailAndPassword(email, password)
                                .addOnCompleteListener {
                                    if (it.isSuccessful) {

                                        //val databaseReference = Firebase.firestore;
                                        //var databaseReference = FirebaseDatabase.getInstance().reference;
                                        //databaseReference = databaseReference.child(fUser.uid)


                                        //databaseReference.setValue(user)
                                        Log.w("currentUser", currentUser.toString())
                                        databaseReference!!.collection("User")
                                            .whereEqualTo("uid", currentUser!!.uid).get()
                                            .addOnSuccessListener { docs ->

                                                var role = ""
                                                for (doc in docs) {
                                                    role = doc.getString("Role").toString()
                                                }

                                                if (role =="admin"){
                                                    val adminactivityintent= (Intent(this@LoginActivity, AdminActivity::class.java));
                                                    startActivity(adminactivityintent)
                                                }
                                                if (role == "ParkingAdmin") {
                                                    val parkingadminintent = (Intent(this@LoginActivity, AdminParkingActivity::class.java));
                                                    startActivity(parkingadminintent)
                                                }
                                                val editor = sharedPreferences!!.edit()
                                                editor.putString(
                                                    Constants.userLoginStatus,
                                                    Constants.isLoggedIn
                                                )
                                                editor.putString(Constants.email1, email)
                                                editor.putString(Constants.password1, password)
                                                editor.apply()
                                                finish()
                                            }
                                    }
                                }

                        }

                    }

            }

        }
    }
    override fun onBackPressed() {
        finish()
    }

}


