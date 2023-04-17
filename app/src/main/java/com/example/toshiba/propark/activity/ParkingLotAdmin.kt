package com.example.toshiba.propark.activity

import android.annotation.SuppressLint
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
import com.example.toshiba.propark.model.Location
import com.example.toshiba.propark.model.User
import com.example.toshiba.propark.model.spotID
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.regex.Pattern

class ParkingLotAdmin : AppCompatActivity() {
    var locationname: EditText? = null
    var bkspots: EditText? = null
    private var avspots: EditText? = null
    private var password: EditText? = null
    private var confpassword: EditText? = null
    private var LoName: String? = null
    private var Lobkspots: String? = null
    private var Loavspots: String? = null
    private var addlocation: Button? = null
    private var currentUser: FirebaseUser? = null
    var databaseReference: FirebaseFirestore? = null


    //firebase
    private var mAuth: FirebaseAuth? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parking_lot_admin)

        locationname = findViewById(R.id.newlocation)
        //bkspots = findViewById(R.id.bookedpsots)
        avspots = findViewById(R.id.availablespots)
        addlocation = findViewById(R.id.addlocationbutton)
        currentUser = FirebaseAuth.getInstance().currentUser
        databaseReference = Firebase.firestore

//       val progressDialog : ProgressBar = findViewById(R.id.progressbar)
      //  progressDialog.visibility = View.GONE
        //firebase
        mAuth = FirebaseAuth.getInstance()
        addlocation?.setOnClickListener {
              //  progressDialog.visibility = View.VISIBLE
                                    val databaseReference = Firebase.firestore;
                                    //var databaseReference = FirebaseDatabase.getInstance().reference;
                                    //databaseReference = databaseReference.child(fUser.uid)


                                    val location = Location()
                                  //  location.locationname = LoName
                                    location.Booked = 0
                                    location.Available = avspots!!.text.toString().toInt()
                                    //user.password = userPass
                                    location.areaName = locationname!!.text.toString()
                                    //databaseReference.setValue(user)
                                    databaseReference.collection("Parking Lot").document(location.areaName!!).set(location)
                                        .addOnSuccessListener { ref ->
                                            //Log.w("newArea", ref.id)

                                            var batch = databaseReference.batch()

                                            for (i in 1..location.Available!!)
                                            {
                                                val obj = spotID("spot"+ i.toString(), 0, 1)
                                                var docRef = databaseReference.collection("Parking Lot").document(location.areaName!!).collection("spotID").document("spot"+ i.toString()); //automatically generate unique id
                                                batch.set(docRef, obj);
                                            }
                                            batch.commit()


                                            Toast.makeText(
                                                this,
                                                "Lot added! - " + locationname.toString(),
                                                Toast.LENGTH_LONG
                                            ).show()
                                            startActivity(
                                                Intent(
                                                    applicationContext,
                                                    ParkingLotAdmin::class.java
                                                )
                                            )
                                            finish()
                                        }
                            }
                        }
                            private fun isValidate() {
                             var isValid = true
                            LoName = locationname?.text.toString().trim()
                            Lobkspots = bkspots?.text.toString().trim()
                            Loavspots = avspots?.text.toString().trim()

                            if (LoName!!.isEmpty()) {
                                locationname!!.error = "Enter Location Name"
                                isValid = false
                            }
                        if (Lobkspots!!.isEmpty()) {
                            bkspots!!.error = "Enter Booked Spots"
                            isValid = false
        }
        if (Loavspots!!.isEmpty()) {
            avspots!!.error = "Enter Available Spots"
            isValid = false

        }

    }

    override fun onBackPressed() {
        finish()
    }
}