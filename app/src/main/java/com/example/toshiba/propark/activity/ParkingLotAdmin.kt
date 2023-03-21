package com.example.toshiba.propark.activity

import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.toshiba.propark.R
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class ParkingLotAdmin : AppCompatActivity() {
  //private lateinit var database: FirebaseFirestore
  //var location: EditText? = null
  //var spots: EditText? = null
  //private var addLocation: String? = null
  //private var addSpots: String? = null
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_parking_lot_admin)
    //location = findViewById(R.id.name_field)
   // spots = findViewById(R.id.available_spot)
   // database = FirebaseFirestore.getInstance()
  }}
  //private fun sendData()
  /*{
    // addLocation = location?.text.toString().trim()
    // addSpots = spots?.text.toString().trim()
   // if (location.isEmpty() && spots.isNotEmpty()){
   //   var mode= DatabaseModel(location,spots)
     // database.collection("Parking Lot").add(mode).addOnSuccessListener { object :OnSuccessListener<DocumentReference>
      {
        override fun onSuccess(p0: DocumentReference?) {
         // location?.setText("")
        // spots?.setText("")
        }
      }
      }
    }
  }
}*/