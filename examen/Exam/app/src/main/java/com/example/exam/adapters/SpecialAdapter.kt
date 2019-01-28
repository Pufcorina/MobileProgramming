package com.example.exam.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.exam.R
import com.example.exam.local_db.DbManager
import com.example.exam.models.Entity
import com.example.exam.networking.ApiClient
import kotlinx.android.synthetic.main.special_view.view.*

class SpecialAdapter(val context: Context) :
    RecyclerView.Adapter<SpecialAdapter.SpecialViewAdapter>() {

    private val client by lazy { ApiClient.create() }
    var elementsList: ArrayList<Entity> = ArrayList()
    private var dbManager: DbManager = DbManager(context)

    init {
        loadQueryAll()
    }

    private fun loadQueryAll() {
        val cursor = dbManager.queryAll()
        elementsList.clear()
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex("id"))
                val title = cursor.getString(cursor.getColumnIndex("title"))
                val description = cursor.getString(cursor.getColumnIndex("description"))
                val album = cursor.getString(cursor.getColumnIndex("album"))
                val genre = cursor.getString(cursor.getColumnIndex("genre"))
                val year = cursor.getString(cursor.getColumnIndex("year"))
                elementsList.add(Entity(id = id, title = title, description = description, album = album, genre = genre, year = year.toInt()))
            } while (cursor.moveToNext())
        }
    }



    class SpecialViewAdapter(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SpecialAdapter.SpecialViewAdapter {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.special_view, parent, false)

        return SpecialViewAdapter(view)
    }

    override fun onBindViewHolder(holder: SpecialViewAdapter, position: Int) {

        holder.view.title.text = elementsList[position].title
        holder.view.album.text = elementsList[position].album
        holder.view.genre.text = elementsList[position].genre
        holder.view.year.text = elementsList[position].year.toString()
    }

    override fun getItemCount() = elementsList.size

}