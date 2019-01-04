package com.example.corina.trackseries.auth

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewAnimationUtils
import android.widget.Toast
import com.example.corina.trackseries.MainActivity
import com.example.corina.trackseries.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()

        if (auth.currentUser != null) {
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            finish()
        }


        setContentView(R.layout.activity_login)

        btn_signup.setOnClickListener {
            startActivity(Intent(this@LoginActivity, SignupActivity::class.java))
        }

        btn_reset_password.setOnClickListener {
            startActivity(Intent(this@LoginActivity, ResetPasswordActivity::class.java))
        }

        btn_login.setOnClickListener {
            val emailText = email.text.toString()
            val passwordText = password.text.toString()

            if (TextUtils.isEmpty(emailText)) {
                Toast.makeText(applicationContext, "Enter email address!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (TextUtils.isEmpty(passwordText)) {
                Toast.makeText(applicationContext, "Enter password!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            progressBar.visibility = View.VISIBLE

            auth.signInWithEmailAndPassword(emailText, passwordText)
                .addOnCompleteListener { task ->
                    progressBar.visibility = View.GONE
                    if (!task.isSuccessful) {
                        if (passwordText.length < 6) {
                            password.error = R.string.minimum_password.toString()
                        } else {
                            Toast.makeText(this@LoginActivity,
                                R.string.auth_failed, Toast.LENGTH_LONG).show()
                        }
                    } else {
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        finish()
                    }
                }
        }
    }
}
