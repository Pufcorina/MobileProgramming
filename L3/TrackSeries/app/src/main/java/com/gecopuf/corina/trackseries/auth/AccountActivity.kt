package com.gecopuf.corina.trackseries.auth

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.gecopuf.corina.trackseries.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_account.*

class AccountActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var authListener: FirebaseAuth.AuthStateListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)

        //get firebase auth instance
        auth = FirebaseAuth.getInstance()

        val user = FirebaseAuth.getInstance().currentUser

        authListener = FirebaseAuth.AuthStateListener {
            val userAuth = FirebaseAuth.getInstance().currentUser
            if (userAuth == null) {
                // user auth state is changed - user is null
                // launch login activity
                startActivity(Intent(this@AccountActivity, LoginActivity::class.java))
                finish()
            }
        }

        old_email.visibility = View.GONE
        new_email.visibility = View.GONE
        password.visibility = View.GONE
        newPassword.visibility = View.GONE
        changeEmail.visibility = View.GONE
        changePass.visibility = View.GONE
        send.visibility = View.GONE
        remove.visibility = View.GONE

        change_email_button.setOnClickListener {
            old_email.visibility = View.GONE
            new_email.visibility = View.VISIBLE
            password.visibility = View.GONE
            newPassword.visibility = View.GONE
            changeEmail.visibility = View.VISIBLE
            changePass.visibility = View.GONE
            send.visibility = View.GONE
            remove.visibility = View.GONE
        }

        changeEmail.setOnClickListener {
            progressBar.visibility = View.VISIBLE

            if (user != null && new_email.text.toString().trim() == "") {
                user.updateEmail(new_email.text.toString().trim())
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                this@AccountActivity,
                                "Email address is updated. Please sign in with new email id!",
                                Toast.LENGTH_LONG
                            ).show()
                            signOut()
                            progressBar.visibility = View.GONE
                        } else {
                            Toast.makeText(this@AccountActivity, "Failed to update email!", Toast.LENGTH_LONG).show()
                            progressBar.visibility = View.GONE
                        }
                    }
            } else if (new_email.getText().toString().trim() == "") {
                new_email.error = "Enter email"
                progressBar.visibility = View.GONE
            }
        }

        change_password_button.setOnClickListener {
            old_email.visibility = View.GONE
            new_email.visibility = View.GONE
            password.visibility = View.GONE
            newPassword.visibility = View.VISIBLE
            changeEmail.visibility = View.GONE
            changePass.visibility = View.VISIBLE
            send.visibility = View.GONE
            remove.visibility = View.GONE
        }

        changePass.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            if (user != null && newPassword.text.toString().trim() != "") {
                if (newPassword.text.toString().trim().length < 6) {
                    newPassword.error = "Password too short, enter minimum 6 characters"
                    progressBar.visibility = View.GONE
                } else {
                    user.updatePassword(newPassword.text.toString().trim())
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(
                                    this@AccountActivity,
                                    "Password is updated, sign in with new password!",
                                    Toast.LENGTH_SHORT
                                ).show()
                                signOut()
                                progressBar.visibility = View.GONE
                            } else {
                                Toast.makeText(this@AccountActivity, "Failed to update password!", Toast.LENGTH_SHORT)
                                    .show()
                                progressBar.visibility = View.GONE
                            }
                        }
                }
            } else if (newPassword.text.toString().trim() == "") {
                newPassword.error = "Enter password"
                progressBar.visibility = View.GONE
            }
        }

        sending_pass_reset_button.setOnClickListener {
            old_email.visibility = View.VISIBLE
            new_email.visibility = View.GONE
            password.visibility = View.GONE
            newPassword.visibility = View.GONE
            changeEmail.visibility = View.GONE
            changePass.visibility = View.GONE
            send.visibility = View.VISIBLE
            remove.visibility = View.GONE
        }


        send.setOnClickListener {
            progressBar.visibility = View.VISIBLE

            if (old_email.text.toString().trim() != "") {
                auth.sendPasswordResetEmail(old_email.text.toString().trim())
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                this@AccountActivity,
                                "Reset password email is sent!",
                                Toast.LENGTH_SHORT
                            ).show()
                            progressBar.visibility = View.GONE
                        } else {
                            Toast.makeText(
                                this@AccountActivity,
                                "Failed to send reset email!",
                                Toast.LENGTH_SHORT
                            ).show()
                            progressBar.visibility = View.GONE
                        }
                    }
            } else {
                old_email.error = "Enter email"
                progressBar.visibility = View.GONE
            }
        }

        remove_user_button.setOnClickListener {
            progressBar.visibility = View.VISIBLE

            user?.delete()?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        this@AccountActivity,
                        "Your profile is deleted:( Create a account now!",
                        Toast.LENGTH_SHORT
                    ).show()
                    startActivity(Intent(this@AccountActivity, SignupActivity::class.java))
                    finish()
                    progressBar.visibility = View.GONE
                } else {
                    Toast.makeText(this@AccountActivity, "Failed to delete your account!", Toast.LENGTH_SHORT)
                        .show()
                    progressBar.visibility = View.GONE
                }
            }
        }

        back_button.setOnClickListener {
            finish()
        }
    }

    //sign out method
    private fun signOut() {
        auth.signOut()
    }

    override fun onResume() {
        super.onResume()
        progressBar.visibility = View.GONE
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
