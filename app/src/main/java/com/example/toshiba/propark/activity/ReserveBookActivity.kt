package com.example.toshiba.propark.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.toshiba.propark.R
import java.util.*

class ReserveBookActivity : AppCompatActivity() {
    private var fromReserve: TextView? = null
    private var reserveTo: EditText? = null
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservebook)

        //FROM TIME
        fromReserve = findViewById<View>(R.id.fromReserve) as TextView

        //get time
        val cal = Calendar.getInstance()
        val minute = cal[Calendar.MINUTE]
        //12 hour format
        //int hour = cal.get(Calendar.HOUR);
        //24 hour format
        val hourofday = cal[Calendar.HOUR_OF_DAY]
        fromReserve!!.text = Integer.toString(hourofday + 1) + "Hrs "

        //TO TIME
        reserveTo = findViewById<View>(R.id.changeTo) as EditText
        val check_button = findViewById<View>(R.id.check) as Button
        check_button.setOnClickListener {
            if (hourofday > 22)
            {
                Toast.makeText(applicationContext, "BOOKING CLOSED NOW. TRY_LATER", Toast.LENGTH_LONG).show()
            } else {
                val checkIntent = Intent(this@ReserveBookActivity, ReserveLotList::class.java)
                val data = reserveTo!!.text.toString()
                val endTime = data.toInt()
                if (endTime + hourofday + 1 <= 24) {
                    checkIntent.putExtra("fromtime", hourofday + 1)
                    checkIntent.putExtra("totime", endTime + hourofday + 1)
                    startActivity(checkIntent)
                } else {
                    Toast.makeText(applicationContext, "CANNOT BOOK FOR NEXT DAY", Toast.LENGTH_LONG).show()
                }
            }
        }
        val backButton = findViewById<View>(R.id.backbook) as Button
        backButton.setOnClickListener {
            val backIntent = Intent(this@ReserveBookActivity, ProfileActivity::class.java)
            startActivity(backIntent)
            finish()
        }
    }

    override fun onBackPressed() {
        finish()
    }
}