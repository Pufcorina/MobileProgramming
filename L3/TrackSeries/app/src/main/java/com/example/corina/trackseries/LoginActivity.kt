package com.example.corina.trackseries

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.corina.trackseries.local_persistence.account.AccountDataSource
import com.example.corina.trackseries.local_persistence.account.AccountDatabase
import com.example.corina.trackseries.local_persistence.account.AccountRepository
import com.example.corina.trackseries.model.Account
import com.example.corina.trackseries.model.AccountInfo
import com.example.corina.trackseries.network.LoginNetworkApiAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity

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
        }

        forgotPassword.onClick {
            val forgotPasswordActivity = Intent(this@LoginActivity, ForgotPasswordActivity::class.java)
            startActivity(forgotPasswordActivity)
        }
    }

    private fun loginUser(username: String, password: String) {
        val networkApiAdapter = LoginNetworkApiAdapter.instance


        doAsync {
            account = networkApiAdapter.login(username, password)
            if(account.roleId == 1) {
                startActivity(Intent(baseContext, UserActivity::class.java))
            }
            if(account.roleId == 2) {
                var adminActivity = Intent(baseContext, AdminActivity::class.java)
                adminActivity.putExtra("accountId", account.accountId)
                adminActivity.putExtra("email", account.email)
                adminActivity.putExtra("username", account.username)
                startActivity(adminActivity)
            }
        }
    }

    override fun onBackPressed() {
        finish()
    }
}
