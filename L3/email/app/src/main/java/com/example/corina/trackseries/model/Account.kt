package com.example.corina.trackseries.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull

@Entity(tableName="accounts")
class Account {
    @PrimaryKey
    var accountId: Int? = null

    @NonNull
    var connected: Int? = null

    @NonNull
    var email: String? = null

    @NonNull
    var password: String? = null

    @NonNull
    var roleId: Int? = null

    @NonNull
    var username: String? = null
}