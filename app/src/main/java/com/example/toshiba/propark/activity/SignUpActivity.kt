package com.example.toshiba.propark.activity


import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.toshiba.propark.R
import com.example.toshiba.propark.databinding.ActivityMainBinding
import com.example.toshiba.propark.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

import java.util.regex.Pattern


class SignUpActivity : AppCompatActivity() {
    var name: EditText? = null
    var email: EditText? = null
    private var mobile: EditText? = null
    private var password: EditText? = null
    private var confpassword: EditText? = null
    private var userName: String? = null
    private var userEmail: String? = null
    private var userMobile: String? = null
    private var userPass: String? = null
    private var userCpass: String? = null
    private var signUp: Button? = null


    //firebase
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)


        name = findViewById(R.id.name_field)
        email = findViewById(R.id.email_field)
        mobile = findViewById(R.id.mobile_field)
        password = findViewById(R.id.password_field)
        confpassword = findViewById(R.id.password_field2)
        signUp = findViewById(R.id.submit1)

        val progressDialog : ProgressBar = findViewById(R.id.progressbar)
        progressDialog.visibility = View.GONE
        //firebase
        mAuth = FirebaseAuth.getInstance()
        signUp?.setOnClickListener {
            if (isValidate()) {
                //SignUp();
                progressDialog.visibility = View.VISIBLE
                mAuth!!.createUserWithEmailAndPassword(userEmail!!, userPass!!).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        progressDialog.visibility = View.GONE
                        val fUser = task.result!!.user
                        if (fUser != null) {
                            mAuth!!.signInWithEmailAndPassword(userEmail!!, userPass!!).addOnCompleteListener {
                                if (it.isSuccessful) {
                                   val databaseReference = Firebase.firestore;
                                    //var databaseReference = FirebaseDatabase.getInstance().reference;
                                    //databaseReference = databaseReference.child(fUser.uid)


                                    val user = User()
                                    user.name = userName
                                    user.email = userEmail
                                    user.phoneNumber = userMobile
                                    //user.password = userPass
                                    user.uid = fUser.uid
                                    //databaseReference.setValue(user)
                                    databaseReference.collection("User").add(user)
                                        .addOnSuccessListener { ref ->
                                            Log.w("newUser", ref.id)
                                        }


                                    Toast.makeText(this, "SignUp Successful! - " + fUser.uid, Toast.LENGTH_LONG).show()
                                    startActivity(Intent(applicationContext, ProfileActivity::class.java))
                                    finish()
                                } else {
                                    Toast.makeText(this, "SignIn Failure Occurred", Toast.LENGTH_LONG).show()
                                }
                            }
                        }
                    } else {
                        progressDialog.visibility = View.GONE
                        Toast.makeText(applicationContext, "SignUp Error Occurred", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun isValidate(): Boolean {
        var isValid = true
        userName = name?.text.toString().trim()
        userEmail = email?.text.toString().trim()
        userMobile = mobile?.text.toString().trim()
        userPass = password?.text.toString().trim()
        userCpass = confpassword?.text.toString().trim()

        if (userName!!.isEmpty()) {
            name!!.error = "Enter Name"
            isValid = false
        }
        if (userEmail!!.isEmpty()) {
            name!!.error = "Enter Email"
            isValid = false
        } else if (!isValidEmail(userEmail!!)) {
            email!!.error = "Please Enter valid Email ID"
            isValid = false
        }
        if (userMobile!!.isEmpty()) {
            name!!.error = "Enter Mobile number"
            isValid = false
        }
        if (userPass!!.isEmpty()) {
            password!!.error = "Enter Password"
            isValid = false
        } else if (userPass!!.length < 8) {
            password!!.error = "Password must contain Min 8 Char"
            isValid = false
        }
        if (userCpass!!.isEmpty()) {
            name!!.error = "Required"
            isValid = false
        } else if (!(userCpass?.equals(userPass) ?: (userPass == null))) {
            confpassword!!.error = "Passwords Don't match"
            isValid = false
        }
        return isValid
    }

    private fun isValidEmail(email: String): Boolean {
        val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
        val inputStr: CharSequence = email
        val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        return pattern.matcher(inputStr).matches()
    }
   override fun onBackPressed() {
        finish()
    }
}