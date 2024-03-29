package com.example.toshiba.propark.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.toshiba.propark.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class BookAgainActivity : AppCompatActivity() {
    var currentuser: FirebaseUser? = null
    var databaseReference: DatabaseReference? = null
    var databaseReference1: DatabaseReference? = null
    var fro = 0
    var tt = 0
    var fromView: TextView? = null
    var toView : TextView? = null
    var tvTo : TextView? = null
    var hours: EditText? = null
    var hrs = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book)
        hours = findViewById(R.id.editTo)
        fromView = findViewById(R.id.fromtime)
        toView = findViewById(R.id.to_time)
        tvTo = findViewById(R.id.textView12)
        toView?.visibility = View.VISIBLE
        tvTo?.visibility = View.VISIBLE
        currentuser = FirebaseAuth.getInstance().currentUser
        if (currentuser != null) {
            databaseReference = FirebaseDatabase.getInstance().reference
        }
        databaseReference!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                fro = dataSnapshot.child("User").child(currentuser!!.uid).child("from").getValue(Int::class.java)!!
                tt = dataSnapshot.child("User").child(currentuser!!.uid).child("to").getValue(Int::class.java)!!
                fromView?.text = Integer.toString(fro) + "Hrs"
                toView?.text = Integer.toString(tt) + "Hrs"
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
        val book = findViewById<View>(R.id.check) as Button
        book.setText(R.string.extendBooking)
        databaseReference1 = FirebaseDatabase.getInstance().reference
        book.setOnClickListener {
            val h = hours!!.text.toString()
            hrs = h.toInt()
            if (hrs + tt <= 24) {
                databaseReference1!!.child("User").child(currentuser!!.uid).child("to").setValue(hrs + tt)
                Toast.makeText(applicationContext, "Booking extended successfully!", Toast.LENGTH_LONG).show()
                val backIntent = Intent(this@BookAgainActivity, ProfileActivity::class.java)
                startActivity(backIntent)
            } else {
                Toast.makeText(applicationContext, "Booking for next day not available", Toast.LENGTH_LONG).show()
            }
        }
        val backButton = findViewById<View>(R.id.backbook) as Button
        backButton.setOnClickListener {
            val backIntent = Intent(this@BookAgainActivity, ProfileActivity::class.java)
            startActivity(backIntent)
            finish()
        }
    }

    override fun onBackPressed() {
        finish()
    }
}