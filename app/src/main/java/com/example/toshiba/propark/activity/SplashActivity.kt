package com.example.toshiba.propark.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.toshiba.propark.Constants
import com.example.toshiba.propark.R
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        var sharedPreferences: SharedPreferences? = null
        mAuth = FirebaseAuth.getInstance()
        val handler = Handler()
        handler.postDelayed({
            sharedPreferences = applicationContext.getSharedPreferences(Constants.loginSharedPref, Context.MODE_PRIVATE)
            val userLoginStatus = sharedPreferences?.getString(Constants.userLoginStatus, "no")
            val email = sharedPreferences?.getString(Constants.email1, "")
            val password = sharedPreferences?.getString(Constants.password1, "")
            if (userLoginStatus == Constants.isLoggedIn && !email.isNullOrEmpty() && !password.isNullOrEmpty()) {
                //the user is login
                mAuth!!.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) {
                    if (!it.isSuccessful) {
                        Toast.makeText(applicationContext, "error here", Toast.LENGTH_LONG).show()
                    } else {
                        startActivity(Intent(this, ProfileActivity::class.java))
                        finish()
                    }
                }
            } else {
                //user is logout
                val intent = Intent(this@SplashActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }

        }, 3000)

    }

}