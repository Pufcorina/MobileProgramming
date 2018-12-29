package com.example.corina.trackseries

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
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
                        uiThread { toast(result) }

                        val message = "Your password for this account is: $result"

                        EmailSender.instance.sendMessage("Track Series password", message, "trackseries.cwori@gmail.com", emailText)
                        finish()

                    }
                } else
                    uiThread { toast("Fill in email field") }

            }
        }
    }


}
