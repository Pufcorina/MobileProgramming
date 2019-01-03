package com.example.corina.trackseries.login

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import com.example.corina.trackseries.R
import com.example.corina.trackseries.network.LoginNetworkApiAdapter
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        signUp.onClick {
            val usernameText = username.text.toString()
            val passwordText = password.text.toString()
            val passwordSecondText = passwordSecond.text.toString()
            val emailText = email.text.toString()
            if (usernameText != "" && passwordText != "" && passwordSecondText != "" && emailText != "")
            {
                if (passwordText == passwordSecondText)
                    if (isEmail(emailText))
                        registerUser(usernameText, passwordText, emailText)
                    else
                        Toast.makeText(baseContext, "Invalid email", Toast.LENGTH_LONG).show()
                else
                    Toast.makeText(baseContext, "Passwords should match", Toast.LENGTH_LONG).show()
            }
            else
                Toast.makeText(baseContext, "Fill all data", Toast.LENGTH_LONG).show()
        }

        signIn.onClick { finish() }
    }

    private fun registerUser(username: String, password: String, email: String) {
        val networkApiAdapter = LoginNetworkApiAdapter.instance
        var result: Boolean

        doAsync {

            result = networkApiAdapter.register(username, password, email)

            if (result)
                finish()
            else
                uiThread {
                    toast("Email already used")
                }
        }

    }

    fun isEmail(email: String) : Boolean {
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches())
    }

    override fun onBackPressed() {
        finish()
    }
}
