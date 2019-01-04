package com.example.corina.trackseries.show

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.example.corina.trackseries.R
import com.example.corina.trackseries.model.Show
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.android.synthetic.main.activity_show_list.*

class ShowListActivity : AppCompatActivity() {
    private val TAG = "ShowListActivity"

    private var mAdapter: ShowAdapter? = null

    private var firestoreDB: FirebaseFirestore? = null
    private var firestoreListener: ListenerRegistration? = null

    companion object {
        var animationItem: Int = 0
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_list)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        // add back arrow to toolbar
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowHomeEnabled(true)
        }

        firestoreDB = FirebaseFirestore.getInstance()

        loadShowsList()

        firestoreListener = firestoreDB!!.collection("shows")
            .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                if (firebaseFirestoreException != null) {
                    Log.e(TAG, "Listen failed!", firebaseFirestoreException)
                    return@addSnapshotListener
                }

                val showsList = mutableListOf<Show>()

                for (doc in querySnapshot!!) {
                    val show = doc.toObject(Show::class.java)
                    show.id = doc.id
                    showsList.add(show)
                }

                mAdapter = ShowAdapter(showsList, applicationContext, firestoreDB!!)
                show_list.adapter = mAdapter
            }
    }

    override fun onDestroy() {
        super.onDestroy()

        firestoreListener!!.remove()
    }

    private fun loadShowsList() {
        firestoreDB!!.collection("shows")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val showsList = mutableListOf<Show>()

                    for (doc in task.result!!) {
                        val show = doc.toObject<Show>(Show::class.java)
                        show.id = doc.id
                        showsList.add(show)
                    }

                    mAdapter = ShowAdapter(showsList, applicationContext, firestoreDB!!)
                    show_list.adapter = mAdapter
                } else {
                    Log.d(TAG, "Error getting documents: ", task.exception)
                }
            }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // handle arrow click here
        if (item.getItemId() === android.R.id.home) {
            finish() // close this activity and return to preview activity (if there is any)
        }

        animationItem = item.itemId

        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.list_animations, menu)
        return true
    }
}
