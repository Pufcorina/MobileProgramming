package com.example.corina.trackseries.local_persistence.account

import com.example.corina.trackseries.model.Account
import io.reactivex.Flowable
import java.io.Serializable

class AccountRepository(private val mLocationDataSource: IDataSource<Account>) : IDataSource<Account>, Serializable {
    override val all: Flowable<List<Account>>
        get() = mLocationDataSource.all

    override fun getById(accountId: Int): Flowable<Account> {
        return mLocationDataSource.getById(accountId)
    }

    override fun insert(vararg accounts: Account) {
        return mLocationDataSource.insert(*accounts)
    }

    override fun update(vararg accounts: Account) {
        mLocationDataSource.update(*accounts)
    }

    override fun delete(account: Account) {
        mLocationDataSource.delete(account)
    }

    companion object {
        private var mInstance: AccountRepository?=null
        fun getInstance(mLocationDataSource:IDataSource<Account>):AccountRepository {
            if (mInstance == null)
                mInstance = AccountRepository(mLocationDataSource)
            return mInstance!!
        }
    }


}