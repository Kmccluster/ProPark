package com.example.toshiba.propark.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.toshiba.propark.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ReserveSpotsActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservespots)
        val intent = intent
        val ftime = intent.getIntExtra("fromtime", 0)
        ftime?.let { Log.w("from", it.toString()) }
        val ttime = intent.getIntExtra("totime", 0)
        val f1: Int
        val f2: Int
        val t1: Int
        val t2: Int
        f1 = ftime
        f2 = ftime
        t1 = ttime
        t2 = ttime
        val spotone = findViewById<View>(R.id.spot_one) as TextView
        spotone.setOnClickListener {
            val spotoneIntent = Intent(this@ReserveSpotsActivity, ReserveAvailableActivity::class.java)
            spotoneIntent.putExtra("areaName", "Benjamin Banneker A")
            spotoneIntent.putExtra("spotID", "BBA Spot 1")
            spotoneIntent.putExtra("fromtime", ftime)
            spotoneIntent.putExtra("totime", ttime)
            startActivity(spotoneIntent)
        }
        val spottwo = findViewById<View>(R.id.spot_two) as TextView
       spottwo.setOnClickListener {
            val spottwoIntent = Intent(this@ReserveSpotsActivity, ReserveAvailableActivity::class.java)
           spottwoIntent.putExtra("areaName", "Benjamin Banneker A")
            spottwoIntent.putExtra("spotID", "BBA Spot 2")
            spottwoIntent.putExtra("fromtime", f1)
            spottwoIntent.putExtra("totime", t1)
            startActivity(spottwoIntent)
        }
        val spotthree = findViewById<View>(R.id.spot_three) as TextView
        spotthree.setOnClickListener {
            val spotthreeIntent = Intent(this@ReserveSpotsActivity, ReserveAvailableActivity::class.java)
            spotthreeIntent.putExtra("areaName", "Benjamin Banneker A")
            spotthreeIntent.putExtra("spotID", "BBA Spot 3")
            spotthreeIntent.putExtra("fromtime", f2)
            spotthreeIntent.putExtra("totime", t2)
            startActivity(spotthreeIntent)
        }
    val spotfour = findViewById<View>(R.id.spot_four) as TextView
        spotfour.setOnClickListener {
        val spotfourIntent = Intent(this@ReserveSpotsActivity, ReserveAvailableActivity::class.java)
            spotfourIntent.putExtra("areaName", "Benjamin Banneker B")
            spotfourIntent.putExtra("spotID", "BBB SPOT 1")
            spotfourIntent.putExtra("fromtime", f2)
            spotfourIntent.putExtra("totime", t2)
        startActivity(spotfourIntent)
    }
        val spotfive = findViewById<View>(R.id.spot_five) as TextView
        spotfive.setOnClickListener {
            val spotfiveIntent = Intent(this@ReserveSpotsActivity, ReserveAvailableActivity::class.java)
            spotfiveIntent.putExtra("areaName", "Benjamin Banneker B")
            spotfiveIntent.putExtra("spotID", "BBB SPOT 2")
            spotfiveIntent.putExtra("fromtime", f2)
            spotfiveIntent.putExtra("totime", t2)
            startActivity(spotfiveIntent)
        }
        val spotsix = findViewById<View>(R.id.spot_six) as TextView
        spotsix.setOnClickListener {
            val spotsixIntent = Intent(this@ReserveSpotsActivity, ReserveAvailableActivity::class.java)
            spotsixIntent.putExtra("areaName", "Benjamin Banneker B")
            spotsixIntent.putExtra("spotID", "BBB SPOT 3")
            spotsixIntent.putExtra("fromtime", f2)
            spotsixIntent.putExtra("totime", t2)
            startActivity(spotsixIntent)
        }
        val spotseven = findViewById<View>(R.id.spot_seven) as TextView
        spotseven.setOnClickListener {
            val spotsevenIntent = Intent(this@ReserveSpotsActivity, ReserveAvailableActivity::class.java)
            spotsevenIntent.putExtra("areaName", "Welcome Center")
            spotsevenIntent.putExtra("spotID", "WC SPOT 1")
            spotsevenIntent.putExtra("fromtime", f2)
            spotsevenIntent.putExtra("totime", t2)
            startActivity(spotsevenIntent)
        }
        val spoteight = findViewById<View>(R.id.spot_eight) as TextView
        spoteight.setOnClickListener {
            val spoteightIntent = Intent(this@ReserveSpotsActivity, ReserveAvailableActivity::class.java)
            spoteightIntent.putExtra("areaName", "Welcome Center")
            spoteightIntent.putExtra("spotID", "WC SPOT 2")
            spoteightIntent.putExtra("fromtime", f2)
            spoteightIntent.putExtra("totime", t2)
            startActivity(spoteightIntent)
        }
        val spotnine = findViewById<View>(R.id.spot_nine) as TextView
        spotnine.setOnClickListener {
            val spotnineIntent = Intent(this@ReserveSpotsActivity, ReserveAvailableActivity::class.java)
            spotnineIntent.putExtra("areaName", "Welcome Center")
            spotnineIntent.putExtra("spotID", "WC SPOT 3")
            spotnineIntent.putExtra("fromtime", f2)
            spotnineIntent.putExtra("totime", t2)
            startActivity(spotnineIntent)
        }
    }

    override fun onBackPressed() {
        finish()
    }
}

