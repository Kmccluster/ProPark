package com.example.toshiba.propark.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.toshiba.propark.R

class ListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        val intent = intent
        val ftime = intent.getIntExtra("fromtime", 0)
        val ttime = intent.getIntExtra("totime", 0)
        val f1: Int
        val f2: Int
        val t1: Int
        val t2: Int
        f1 = ftime
        f2 = ftime
        t1 = ttime
        t2 = ttime
        val benjaminbannekera = findViewById<View>(R.id.benjamin_bannekera) as TextView
        benjaminbannekera.setOnClickListener {
            val benjaminbannekeraIntent = Intent(this@ListActivity, AreaAvailableActivity::class.java)
            benjaminbannekeraIntent.putExtra("Area", "SAM")
            benjaminbannekeraIntent.putExtra("fromtime", ftime)
            benjaminbannekeraIntent.putExtra("totime", ttime)
            startActivity(benjaminbannekeraIntent)
        }
        val benjaminbannekerb = findViewById<View>(R.id.benjamin_bannekerb) as TextView
        benjaminbannekerb.setOnClickListener {
            val benjaminbannekerbIntent = Intent(this@ListActivity, AreaAvailableActivity::class.java)
            benjaminbannekerbIntent.putExtra("Area", "RC")
            benjaminbannekerbIntent.putExtra("fromtime", f1)
            benjaminbannekerbIntent.putExtra("totime", t1)
            startActivity(benjaminbannekerbIntent)
        }
        val welcome_center = findViewById<View>(R.id.welcome_center) as TextView
        welcome_center.setOnClickListener {
            val welcome_centerIntent = Intent(this@ListActivity, AreaAvailableActivity::class.java)
            welcome_centerIntent.putExtra("Area", "Sd")
            welcome_centerIntent.putExtra("fromtime", f2)
            welcome_centerIntent.putExtra("totime", t2)
            startActivity(welcome_centerIntent)
        }
    }

    override fun onBackPressed() {
        finish()
    }
}