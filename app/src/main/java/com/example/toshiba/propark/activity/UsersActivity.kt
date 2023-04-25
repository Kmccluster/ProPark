package com.example.toshiba.propark.activity
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.*
import com.example.toshiba.propark.CustomAdapter
import com.example.toshiba.propark.ItemsViewModel
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


class UsersActivity : AppCompatActivity() {
    private var currentuser: FirebaseUser? = null
    private var databaseUsers: DatabaseReference? = null
    var databaseReference: FirebaseFirestore? = null
    private var progressDialog: ProgressBar? = null
    private var user: User? = null
    private var xname: TextView? = null
    private var xemail: TextView? = null
    private var xphonenumber: TextView? = null
    private var etlocation: EditText? = null
    private var etavailable: EditText? = null
    private var etbooked: EditText? = null
    private var slocation: TextView? = null
    private var savailable: TextView? = null
    private var sbooked: TextView? = null
    private var back: Button? = null
    var location: Location? = null
    var listusers: Spinner? = null
    var record : DocumentReference? = null
    var dref: DatabaseReference? = null
    var av = 0
    var bk = 0

    @SuppressLint("CutPasteId", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set the content of the activity to use the activity_main.xml layout file
        setContentView(R.layout.activity_users)
        listusers = findViewById<View>(R.id.spinner) as Spinner?
        xname = findViewById<View>(R.id.name) as TextView
        xemail = findViewById<View>(R.id.email) as TextView
        xphonenumber = findViewById<View>(R.id.phoneNumber) as TextView
        back = findViewById<View>(R.id.backb) as Button?
        progressDialog = findViewById(R.id.progressbar)
        progressDialog = findViewById(R.id.progressbar2)
        currentuser = FirebaseAuth.getInstance().currentUser
        val userInfo: MutableList<User?> = ArrayList()
        var user: User? = null
        databaseReference = Firebase.firestore
        databaseReference!!.collection("User").get().addOnSuccessListener {
                documents ->
            // Is better to use a List, because you don't know the size
            // of the iterator returned by dataSnapshot.getChildren() to
            // initialize the array
            val users: MutableList<String?> = ArrayList()
            for (doc in documents) {
                val selecteduser = doc.getString("name")
                users.add(selecteduser)
                //Log.w("email", doc.getLong("phoneNumber").toString())
               // users.add(User(doc.getString("email"), doc.getString("phoneNumber"), doc.getString("name")).toString())
                userInfo.add(User(doc.getString("email"), doc.getString("phoneNumber"),doc.getString("name")))
            }
            val listusers = findViewById<View>(R.id.spinner) as Spinner
            val userAdapter =
                ArrayAdapter(this@UsersActivity, android.R.layout.simple_spinner_item, users)
            userAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            listusers.adapter = userAdapter
        }


        listusers!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                var selectedUser = parent.getItemAtPosition(position).toString()
                for (user in userInfo) {
                    if(user?.name == selectedUser)
                    {

                        var selected = user
                        xname!!.text = user.name
                        xemail!!.text = user.email
                        xphonenumber!!.text = user.phoneNumber

                    }

                }
            } // to close the onItemSelected


            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
        val button2 = findViewById<View>(R.id.backb) as Button
        button2.setOnClickListener {
            val button2Intent = Intent(this@UsersActivity, AdminActivity::class.java)
            startActivity(button2Intent)
            finish()
        }
        fun onBackPressed() {
            finish()
        }
    }

}

