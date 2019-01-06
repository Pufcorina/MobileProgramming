package com.gecopuf.corina.trackseries.auth

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.gecopuf.corina.trackseries.MainActivity
import com.gecopuf.corina.trackseries.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_signup.*

class SignupActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        auth = FirebaseAuth.getInstance()

        btn_reset_password.setOnClickListener {
            startActivity(Intent(this@SignupActivity, ResetPasswordActivity::class.java))
        }

        sign_in_button.setOnClickListener {
            finish()
        }

        sign_up_button.setOnClickListener {
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

            if (passwordText.length < 6) {
                Toast.makeText(
                    applicationContext,
                    "Password too short, enter minimum 6 characters!",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            progressBar.visibility = View.VISIBLE

            //create user
            auth.createUserWithEmailAndPassword(emailText, passwordText)
                .addOnCompleteListener(this@SignupActivity) { task ->
                    Toast.makeText(this@SignupActivity, "createUserWithEmail:onComplete:" + task.isSuccessful, Toast.LENGTH_SHORT).show()
                    progressBar.visibility = View.GONE
                    // If sign in fails, display a message to the user. If sign in succeeds
                    // the auth state listener will be notified and logic to handle the
                    // signed in user can be handled in the listener.
                    if (!task.isSuccessful) {
                        Toast.makeText(this@SignupActivity, "Authentication failed." + task.exception!!,
                            Toast.LENGTH_SHORT).show()
                    } else {
                        startActivity(Intent(this@SignupActivity, MainActivity::class.java))
                        finish()
                    }
                }
        }
    }
}
