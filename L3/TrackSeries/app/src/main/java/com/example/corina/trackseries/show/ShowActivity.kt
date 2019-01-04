package com.example.corina.trackseries.show

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.corina.trackseries.R
import com.example.corina.trackseries.model.Show
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_show.*

class ShowActivity : AppCompatActivity() {
    private val TAG = "AddShowActivity"

    private var firestoreDB: FirebaseFirestore? = null
    internal var id: String = ""

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show)

        firestoreDB = FirebaseFirestore.getInstance()

        val bundle = intent.extras
        if (bundle != null) {
            id = bundle.getString("UpdateShowId")

            title_show.setText(bundle.getString("UpdateShowTitle"))
            producer_show.setText(bundle.getString("UpdateShowProducer"))
            description_show.setText(bundle.getString("UpdateShowDescription"))
            rating_show.setText(bundle.getString("UpdateShowRating"))
        }

        edit_button.visibility = View.GONE
        add_button.visibility = View.GONE

        if (id.isNotEmpty()) {
            edit_button.visibility = View.VISIBLE
            add_button.visibility = View.GONE
        } else {
            edit_button.visibility = View.GONE
            add_button.visibility = View.VISIBLE
        }


        add_button.setOnClickListener {
            val title = title_show.text.toString()
            val producer = producer_show.text.toString()
            val description = description_show.text.toString()
            val rating = rating_show.text.toString()

            addShow(title, producer, description, rating)
            finish()
        }

        edit_button.setOnClickListener {
            val title = title_show.text.toString()
            val producer = producer_show.text.toString()
            val description = description_show.text.toString()
            val rating = rating_show.text.toString()

            updateShow(id, title, producer, description, rating)
            finish()
        }
    }

    private fun checkFields(title: String, producer: String, description: String, rating: String): Boolean {
        if (title.isEmpty()) {
            Toast.makeText(this@ShowActivity, "Fill title field!", Toast.LENGTH_LONG).show()
            return false
        }
        if (producer.isEmpty()) {
            Toast.makeText(this@ShowActivity, "Fill producer field!", Toast.LENGTH_LONG).show()
            return false
        }
        if (description.isEmpty()) {
            Toast.makeText(this@ShowActivity, "Fill description field!", Toast.LENGTH_LONG).show()
            return false
        }
        if (rating.isEmpty()) {
            Toast.makeText(this@ShowActivity, "Fill rating field!", Toast.LENGTH_LONG).show()
            return false
        }

        return true
    }

    private fun updateShow(
        id: String,
        title: String,
        producer: String,
        description: String,
        rating: String
    ) {


        val show = Show(id, title, producer, description, rating.toInt()).toMap()

        firestoreDB!!.collection("shows")
            .document(id)
            .set(show)
            .addOnSuccessListener {
                Log.e(TAG, "Show document update successful!")
                Toast.makeText(applicationContext, "Show has been updated!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Error adding Show document", e)
                Toast.makeText(applicationContext, "Show could not be updated!", Toast.LENGTH_SHORT).show()
            }
    }

    private fun addShow(title: String, producer: String, description: String, rating: String) {

        val show = Show(title, producer, description, rating.toInt()).toMap()

        firestoreDB!!.collection("shows")
            .add(show)
            .addOnSuccessListener { documentReference ->
                Log.e(TAG, "DocumentSnapshot written with ID: " + documentReference.id)
                Toast.makeText(applicationContext, "Show has been added!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Error adding Show document", e)
                Toast.makeText(applicationContext, "Show could not be added!", Toast.LENGTH_SHORT).show()
            }
    }
}
