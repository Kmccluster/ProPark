package com.example.toshiba.propark.activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.content.Intent
import android.widget.Button
import com.example.toshiba.propark.CustomAdapter
import com.example.toshiba.propark.ItemsViewModel
import com.example.toshiba.propark.R
import kotlinx.android.synthetic.main.activity_admin.*


class AdminActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)
        val back1: Button = findViewById(R.id.back1)
        back1.setOnClickListener {
            val back = Intent(this@AdminActivity, LoginActivity::class.java)
            startActivity(back)
            finish()
        }
            // getting the recyclerview by its id
            val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)

            // this creates a vertical layout Manager
            recyclerview.layoutManager = LinearLayoutManager(this)

            // ArrayList of class ItemsViewModel
            val data = ArrayList<ItemsViewModel>()

            // This loop will create 20 Views containing
            // the image with the count of view
            for (i in 1..20) {
                data.add(ItemsViewModel(R.drawable.ic_baseline_folder_24, "User " + i))
            }

            // This will pass the ArrayList to our Adapter
            val adapter = CustomAdapter(data)

            // Setting the Adapter with the recyclerview
            recyclerview.adapter = adapter


    }
}