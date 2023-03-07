package com.example.toshiba.propark.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.toshiba.propark.Constants
import com.example.toshiba.propark.R
import com.example.toshiba.propark.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {
    private var spinnerlist: Spinner? = null
    private var mAuth: FirebaseAuth? = null
    private var mEmailField: EditText? = null
    private var mPasswordField: EditText? = null
    private var submitButton: Button? = null
    var sharedPreferences: SharedPreferences? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mEmailField = findViewById(R.id.name_field)
        mPasswordField = findViewById(R.id.password_field)
        submitButton = findViewById(R.id.submit112)
        mAuth = FirebaseAuth.getInstance()
        sharedPreferences =
            applicationContext.getSharedPreferences("loginDetail", Context.MODE_PRIVATE)
        val progressDialog: ProgressBar = findViewById(R.id.progressbar)
        progressDialog.visibility = View.GONE
        val users = resources.getStringArray(R.array.usertype)
        val spinner: Spinner = findViewById<Spinner>(R.id.spinner2)
        ArrayAdapter.createFromResource(this, R.array.usertype, R.layout.support_simple_spinner_dropdown_item
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
        spinner.setSelection(0)

        val spinnerList: Spinner = findViewById(R.id.spinner2)
        spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                var intent: Intent

            }


            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
        submitButton!!.setOnClickListener {
            val email = mEmailField!!.text.toString()
            val password = mPasswordField!!.text.toString()
            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(applicationContext, "Fields Are Empty", Toast.LENGTH_LONG).show()
            } else {
                progressDialog.visibility = View.VISIBLE
                mAuth!!.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this@LoginActivity) {
                        progressDialog.visibility = View.GONE
                        if (!it.isSuccessful) {
                            Toast.makeText(
                                applicationContext,
                                "Email/password incorrect",
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            val editor = sharedPreferences!!.edit()
                            editor.putString(Constants.userLoginStatus, Constants.isLoggedIn)
                            editor.putString(Constants.email1, email)
                            editor.putString(Constants.password1, password)
                            editor.apply()
                            //var databaseReference = FirebaseDatabase.getInstance().reference.child("Users")
                           // databaseReference = databaseReference.child(fUser.uid)

                            val position: String = spinner2.getSelectedItem().toString()
                            Toast.makeText(
                                applicationContext,
                                position,
                                Toast.LENGTH_LONG).show()
                            if (position == "Student" ) {
                                intent = Intent(this@LoginActivity, ProfileActivity::class.java)
                                startActivity(intent)
                                Toast.makeText(
                                    applicationContext,
                                    "Profile Activity Unsuccessful",
                                    Toast.LENGTH_LONG)
                            }
                            if (position == "Admin" ) {
                                intent = Intent(this@LoginActivity, AdminActivity::class.java)
                                startActivity(intent)
                                Toast.makeText(
                                    applicationContext,
                                    "Admin Activity Successful",
                                    Toast.LENGTH_LONG)
                            }
                            if (position == "Parking Lot Admin") {
                                intent = Intent(this@LoginActivity, ParkingLotAdmin::class.java)
                                startActivity(intent)
                                Toast.makeText(
                                    applicationContext,
                                    "Parking Lot Admin Successful",
                                    Toast.LENGTH_LONG)
                            }

                            finish()
                        }
                    }
            }
        }
    }

    override fun onBackPressed() {
        finish()
    }
}

private operator fun TextView.get(position: Int) {


}