package com.example.corina.trackseries.local_persistence.account

import com.example.corina.trackseries.model.Account
import io.reactivex.Flowable

class AccountDataSource(private val accountDAO: AccountDAO) : IDataSource<Account> {
    override val all: Flowable<List<Account>>
        get() = accountDAO.allAccounts

    override fun getById(id: Int): Flowable<Account> {
        return accountDAO.getAccountById(id)
    }

    override fun insert(vararg elements: Account) {
        accountDAO.insertAccount(*elements)
    }

    override fun update(vararg elements: Account) {
        accountDAO.updateAccount(*elements)
    }

    override fun delete(element: Account) {
        accountDAO.deleteAccount(element)
    }

    companion object {
        private var mInstance: AccountDataSource?=null
        fun getInstance(accountDAO: AccountDAO): AccountDataSource{
            if(mInstance==null)
                mInstance =AccountDataSource(accountDAO)
            return mInstance!!
        }
    }
}