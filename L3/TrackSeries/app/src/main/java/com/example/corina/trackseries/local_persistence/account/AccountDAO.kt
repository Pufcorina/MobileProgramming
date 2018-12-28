package com.example.corina.trackseries.local_persistence.account

import android.arch.persistence.room.*
import com.example.corina.trackseries.model.Account
import io.reactivex.Flowable

@Dao
interface AccountDAO {
    @get:Query("SELECT * FROM accounts")
    val allAccounts: Flowable<List<Account>>

    @Query("SELECT * From accounts WHERE accountId=:accountId")
    fun getAccountById(accountId: Int): Flowable<Account>

    @Insert
    fun insertAccount(vararg accounts: Account)

    @Update
    fun updateAccount(vararg accounts: Account)

    @Delete
    fun deleteAccount(account: Account)
}