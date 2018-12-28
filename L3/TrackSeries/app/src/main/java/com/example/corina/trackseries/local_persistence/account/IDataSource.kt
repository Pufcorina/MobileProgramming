package com.example.corina.trackseries.local_persistence.account

import io.reactivex.Flowable

interface IDataSource<T> {
    val all: Flowable<List<T>>
    fun getById(id: Int): Flowable<T>
    fun insert(vararg elements: T)
    fun update(vararg elements: T)
    fun delete(element: T)
}