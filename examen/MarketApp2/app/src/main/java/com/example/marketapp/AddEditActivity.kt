package com.example.marketapp

import android.content.Context
import android.net.ConnectivityManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.marketapp.models.Product
import com.example.marketapp.networking.NController
import kotlinx.android.synthetic.main.activity_add_edit.*
import java.lang.Exception

class AddEditActivity : AppCompatActivity() {

    private lateinit var nc: NController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit)


        nc = NController(baseContext)

        add_button.setOnClickListener {
            addElement()
            finish()
        }
    }

    private fun addElement() {
        val cm = baseContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = cm.activeNetworkInfo
        if (networkInfo != null && networkInfo.isConnected) {
            if (name.text.toString() != "" && description.text.toString() != "") {
                try {
                    val a = nc.addElement(Product(name.text.toString(), description.text.toString(), price.text.toString().toInt(), quantity.text.toString().toInt()))
                    if (a != null) {
                        Log.d("added element", a.toString())
                    } else
                        Toast.makeText(baseContext, "Product already exists!", Toast.LENGTH_LONG).show()
                } catch (e: Exception) {
                    Log.d("error", e.message)
                }
            } else
                Toast.makeText(baseContext, "Fill data", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(baseContext, "No Internet Connection", Toast.LENGTH_LONG).show()
        }
    }
}