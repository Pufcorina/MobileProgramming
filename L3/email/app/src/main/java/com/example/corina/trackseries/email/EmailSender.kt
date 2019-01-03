package com.example.corina.trackseries.email

import android.support.v7.app.AlertDialog
import android.util.Log
import com.example.corina.trackseries.R

class EmailSender {
    companion object {
        val instance: EmailSender = EmailSender()
    }
    fun  sendMessage(subject: String, message: String, email: String, senders: String) {
//        val dialog = ProgressDialog(this)
//        dialog.setTitle("Sending Email")
//        dialog.setMessage("Please wait")
//        dialog.show()
        val sender = Thread(Runnable {
            try {
                val sender = GmailSender("trackseries.cwori@gmail.com", "trackseries13")
                sender.sendMail(
                    subject,
                    message,
                    email,
                    senders
                )
                //dialog.dismiss()
            } catch (e: Exception) {
                Log.e("mylog", "Error: " + e.message)
            }
        })
        sender.start()
    }
}