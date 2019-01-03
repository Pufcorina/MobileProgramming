package com.example.corina.trackseries.login

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.corina.trackseries.admin.AdminActivity
import com.example.corina.trackseries.R
import com.example.corina.trackseries.user.UserActivity
import com.example.corina.trackseries.local_persistence.account.AccountDataSource
import com.example.corina.trackseries.local_persistence.account.AccountDatabase
import com.example.corina.trackseries.local_persistence.account.AccountRepository
import com.example.corina.trackseries.model.AccountInfo
import com.example.corina.trackseries.network.LoginNetworkApiAdapter
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

class LoginActivity : AppCompatActivity() {
    private var accountRepository: AccountRepository?=null
    private var compositeDisposable: CompositeDisposable?=null

    private var account = AccountInfo()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        compositeDisposable = CompositeDisposable()
        val accountDatabase = AccountDatabase.getInstance(this)
        accountRepository = AccountRepository.getInstance(AccountDataSource.getInstance(accountDatabase.accountDAO()))



        login.onClick {
            val usernameText = username.text.toString()
            val passwordText = password.text.toString()
            if (usernameText != "" && passwordText != "")
            {
                loginUser(usernameText, passwordText)
            }
            else
                Toast.makeText(baseContext, "Fill all data", Toast.LENGTH_LONG).show()
        }

        signUp.onClick {
            val registerIntent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(registerIntent)
            clearFields()
        }

        forgotPassword.onClick {
            val forgotPasswordActivity = Intent(this@LoginActivity, ForgotPasswordActivity::class.java)
            startActivity(forgotPasswordActivity)
            clearFields()
        }
    }

    private fun clearFields() {
        username.setText("")
        password.setText("")

    }

    private fun loginUser(username: String, password: String) {
        val networkApiAdapter = LoginNetworkApiAdapter.instance


        doAsync {
            account = networkApiAdapter.login(username, password)
            if(account.roleId == 1) {
                val userActivity = Intent(baseContext, UserActivity::class.java)
                userActivity.putExtra("accountId", account.accountId.toString())
                userActivity.putExtra("email", account.email)
                userActivity.putExtra("username", account.username)
                startActivity(userActivity)
                clearFields()
            } else if(account.roleId == 2) {
                val adminActivity = Intent(baseContext, AdminActivity::class.java)
                adminActivity.putExtra("accountId", account.accountId.toString())
                adminActivity.putExtra("email", account.email)
                adminActivity.putExtra("username", account.username)
                startActivity(adminActivity)
                clearFields()
            } else
                uiThread { toast("No such account") }


        }
    }

    override fun onBackPressed() {
        finish()
    }
}
