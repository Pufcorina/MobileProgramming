package com.example.corina.trackseries.login

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import com.example.corina.trackseries.R
import com.example.corina.trackseries.network.LoginNetworkApiAdapter
import kotlinx.android.synthetic.main.activity_forgot_password.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread
import com.example.corina.trackseries.email.EmailSender


class ForgotPasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        signIn.onClick { finish() }

        sendPassword.onClick {
            val networkApiAdapter = LoginNetworkApiAdapter.instance

            doAsync {
                var emailText = email.text.toString()
                if (emailText != "") {
                    val result = networkApiAdapter.forgotPassword(emailText)

                    if(result == "error")
                        uiThread { toast("Inexistent email") }
                    else
                    {
                        val message = "Your password for this account is: $result"
                        if (isEmail(emailText))
                            EmailSender.instance.sendMessage("Track Series password", message, "trackseries.cwori@gmail.com", emailText)
                        else
                            uiThread { toast("Invalid email") }
                        finish()

                    }
                } else
                    uiThread { toast("Fill in email field") }

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
