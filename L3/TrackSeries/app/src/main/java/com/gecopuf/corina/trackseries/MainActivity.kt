package com.gecopuf.corina.trackseries

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.gecopuf.corina.trackseries.auth.LoginActivity
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var authListener: FirebaseAuth.AuthStateListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //get firebase auth instance
        auth = FirebaseAuth.getInstance()

        val user = FirebaseAuth.getInstance().currentUser

        authListener = FirebaseAuth.AuthStateListener {
            val userAuth = FirebaseAuth.getInstance().currentUser
            if (userAuth == null) {
                // user auth state is changed - user is null
                // launch login activity
                startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                finish()
            }
        }
        val activityIntent: Intent

        if (user!!.email.equals("todorananacorina13@gmail.com"))
            activityIntent = Intent(this@MainActivity, AdminActivity::class.java)
        else
            activityIntent = Intent(this@MainActivity, UserActivity::class.java)

        startActivity(activityIntent)
        finish()
    }

    public override fun onStart() {
        super.onStart()
        auth.addAuthStateListener(authListener)
    }


    public override fun onStop() {
        super.onStop()
        if (authListener != null) {
            auth.removeAuthStateListener(authListener)
        }
    }
}
