package com.example.toshiba.propark.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.toshiba.propark.R
import com.example.toshiba.propark.model.Location
import com.example.toshiba.propark.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AreaListActivity: AppCompatActivity() {
    private var currentuser: FirebaseUser? = null
    private var databaseUsers: DatabaseReference? = null
    var databaseReference: FirebaseFirestore? = null
    private var progressDialog: ProgressBar? = null
    private var user: User? = null
    private var xlocation: TextView? = null
    private var xavailable: TextView? = null
    private var xbooked: TextView? = null
    private var etlocation: EditText? = null
    private var etavailable: EditText? = null
    private var etbooked: EditText? = null
    private var slocation: TextView? = null
    private var savailable: TextView? = null
    private var sbooked: TextView? = null
    private var back: Button? = null
    var location: Location? = null
    var listlocations: Spinner? = null
    var record : DocumentReference? = null
    var dref: DatabaseReference? = null
    var av = 0
    var bk = 0

    @SuppressLint("CutPasteId", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set the content of the activity to use the activity_main.xml layout file
        setContentView(R.layout.activity_parkinglotlist)
        listlocations = findViewById<View>(R.id.spinner) as Spinner?
        xlocation = findViewById<View>(R.id.lotname) as TextView
        xavailable = findViewById<View>(R.id.availableslots) as TextView
        xbooked = findViewById<View>(R.id.bookedslots) as TextView
        back = findViewById<View>(R.id.backb) as Button?
        progressDialog = findViewById(R.id.progressbar)
        progressDialog = findViewById(R.id.progressbar2)
        currentuser = FirebaseAuth.getInstance().currentUser
        val arealocation: MutableList<Location?> = ArrayList()
        var selected: Location? = null
        databaseReference = Firebase.firestore
        databaseReference!!.collection("Parking Lot").get().addOnSuccessListener {
                documents ->
            // Is better to use a List, because you don't know the size
            // of the iterator returned by dataSnapshot.getChildren() to
            // initialize the array
            val areas: MutableList<String?> = ArrayList()
            for (doc in documents) {
                val areaName = doc.getString("areaName")
                areas.add(areaName)
                Log.w("available", doc.getLong("available").toString())
                arealocation.add(Location(doc.getLong("available")?.toInt(), doc.getLong("booked")?.toInt(), doc.getString("areaName")))
            }
            val areaSpinner = findViewById<View>(R.id.spinner) as Spinner
            val areasAdapter =
                ArrayAdapter(this@AreaListActivity, android.R.layout.simple_spinner_item, areas)
            areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            areaSpinner.adapter = areasAdapter
        }


        listlocations!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedItem = parent.getItemAtPosition(position).toString()
                for (item in arealocation) {
                    if(item?.areaName == selectedItem)
                    {

                        selected = item
                        xlocation!!.text = item.areaName
                        xavailable!!.text = item.Available.toString()
                        xbooked!!.text = item.Booked.toString()

                    }

                }
            } // to close the onItemSelected


            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
        val button2 = findViewById<View>(R.id.backb) as Button
        button2.setOnClickListener {
            val button2Intent = Intent(this@AreaListActivity, AdminParkingActivity::class.java)
            startActivity(button2Intent)
            finish()
        }
        fun onBackPressed() {
            finish()
        }
    }

}