package com.example.toshiba.propark.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.toshiba.propark.R
import com.example.toshiba.propark.model.Location
import com.example.toshiba.propark.model.spotID
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class EditLotActivity : AppCompatActivity() {
    var locationname: EditText? = null
    var bkspots: EditText? = null
    private var avspots: EditText? = null
    private var LoName: String? = null
    private var Lobkspots: String? = null
    private var Loavspots: String? = null
    private var updatelot: Button? = null
    private var currentUser: FirebaseUser? = null
    var databaseReference: FirebaseFirestore? = null


    //firebase
    private var mAuth: FirebaseAuth? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editlot)

        locationname = findViewById(R.id.newlocation)
        //bkspots = findViewById(R.id.bookedpsots)
        avspots = findViewById(R.id.availablespots)
        updatelot = findViewById(R.id.editlotbutton)
        currentUser = FirebaseAuth.getInstance().currentUser
        databaseReference = Firebase.firestore
        databaseReference!!.collection("Parking Lot").whereEqualTo("areaName", currentUser!!.uid).get()
            .addOnSuccessListener {
                fun onDataChange(dataSnapshot: DataSnapshot) {

                    val areas: MutableList<String?> = ArrayList()
                    for (areaSnapshot in dataSnapshot.children) {
                        val areaName = areaSnapshot.child("areaName").getValue(
                            String::class.java
                        )
                        areas.add(areaName)
                    }
                    val areaSpinner: Spinner = findViewById<View>(R.id.spinner) as Spinner
                    val areasAdapter =
                        ArrayAdapter(
                            this@EditLotActivity,
                            android.R.layout.simple_spinner_item,
                            areas
                        )
                    areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    areaSpinner.setAdapter(areasAdapter)
                }

                fun onCancelled(databaseError: DatabaseError) {}
            }

//       val progressDialog : ProgressBar = findViewById(R.id.progressbar)
        //  progressDialog.visibility = View.GONE
        //firebase
        mAuth = FirebaseAuth.getInstance()
        updatelot?.setOnClickListener {
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
                        "Updates Changed! - " + locationname.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                    startActivity(
                        Intent(
                            applicationContext,
                            AdminParkingActivity::class.java
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