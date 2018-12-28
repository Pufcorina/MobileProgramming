package com.example.corina.trackseries.local_persistence.account


import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.example.corina.trackseries.local_persistence.account.AccountDatabase.Companion.DATABASE_VERSION
import com.example.corina.trackseries.model.Account

@Database(entities= arrayOf(Account::class), version = DATABASE_VERSION)
abstract class AccountDatabase: RoomDatabase(){
    abstract fun accountDAO():AccountDAO

    companion object {
        const val DATABASE_VERSION=1
        val DATABASE_NAME= "Account-Database-Room"

        private var mInstance: AccountDatabase?=null

        fun getInstance(context: Context):AccountDatabase{
            if (mInstance==null)
                mInstance= Room.databaseBuilder(context,AccountDatabase::class.java, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build()
            return mInstance!!
        }
    }
}