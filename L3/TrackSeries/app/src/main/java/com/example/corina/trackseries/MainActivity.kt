package com.example.corina.trackseries

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.example.corina.trackseries.admin.AdminActivity
import com.example.corina.trackseries.local_persistence.account.AccountDataSource
import com.example.corina.trackseries.local_persistence.account.AccountDatabase
import com.example.corina.trackseries.local_persistence.account.AccountRepository
import com.example.corina.trackseries.login.LoginActivity
import com.example.corina.trackseries.model.Account
import com.example.corina.trackseries.user.UserActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class MainActivity : AppCompatActivity() {
    private var accountRepository: AccountRepository?=null
    private var compositeDisposable: CompositeDisposable?=null

    private var account: Account?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        compositeDisposable = CompositeDisposable()
        val accountDatabase = AccountDatabase.getInstance(this@MainActivity)
        accountRepository = AccountRepository.getInstance(AccountDataSource.getInstance(accountDatabase.accountDAO()))

        var disposable = accountRepository!!.all
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({accounts -> onGetAllSuccess(accounts)})
            {
                    throwable -> Toast.makeText(this@MainActivity, "" + throwable.message, Toast.LENGTH_SHORT)
                .show()
            }
        compositeDisposable!!.add(disposable)

        val activityIntent: Intent
        // go straight to main if a token is stored
        Log.d("account", account.toString())
        if (account != null) {
            accountRepository!!.insert(account as Account)


            if (account!!.accountId == 1)
                activityIntent = Intent(this, UserActivity::class.java)
            else
                activityIntent = Intent(this, AdminActivity::class.java)
        } else {
            activityIntent = Intent(this, LoginActivity::class.java)
        }
        startActivity(activityIntent)
        finish()
    }

    private fun onGetAllSuccess(accounts: List<Account>?) {
        Log.d("accounts", accounts.toString())
        if (accounts != null)
            account = accounts[0]
    }

    override fun onDestroy() {
        compositeDisposable!!.clear()
        super.onDestroy()
    }
}
