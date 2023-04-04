package com.example.toshiba.propark.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.toshiba.propark.R

class AreaListActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_area_list)
        val intent = intent
        val benjaminbannekera = findViewById<View>(R.id.benjamin_bannekera) as TextView
        benjaminbannekera.setOnClickListener {
            val benjaminbannekeraIntent = Intent(this@AreaListActivity, AreaStatusActivity::class.java)
            benjaminbannekeraIntent.putExtra("Area", "Benjamin Banneker A")
            startActivity(benjaminbannekeraIntent)
        }
        val benjaminbannekerb = findViewById<View>(R.id.benjamin_bannekerb) as TextView
        benjaminbannekerb.setOnClickListener {
            val benjaminbannekerbIntent = Intent(this@AreaListActivity, AreaStatusActivity::class.java)
            benjaminbannekerbIntent.putExtra("Area", "Benjamin Banneker B")
            startActivity(benjaminbannekerbIntent)
        }
        val welcomecenter = findViewById<View>(R.id.welcome_center) as TextView
        welcomecenter.setOnClickListener {
            val welcomecenterIntent = Intent(this@AreaListActivity, AreaStatusActivity::class.java)
            welcomecenterIntent.putExtra("Area", "Welcome Center")
            startActivity(welcomecenterIntent)
        }
    }

    override fun onBackPressed() {
        finish()
    }
}