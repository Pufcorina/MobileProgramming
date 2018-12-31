package com.example.corina.trackseries.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.corina.trackseries.R
import com.example.corina.trackseries.model.Show
import com.example.corina.trackseries.network.AdminNetworkApiAdapter
import kotlinx.android.synthetic.main.show_view.view.*
import org.jetbrains.anko.doAsync

class ShowAdapter(var context: Context) : RecyclerView.Adapter<ShowAdapter.ShowViewHolder>() {
    private val networkApiAdapter = AdminNetworkApiAdapter.instance
    var shows: List<Show> = emptyList()

    init { refreshShows() }

    fun refreshShows() {
        println("refresh")
        doAsync {
            shows = networkApiAdapter.getAllShows()
            println(shows.toString())
            notifyDataSetChanged()
        }
    }

    class ShowViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ShowViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.show_view, parent, false)

        return ShowViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShowViewHolder, position: Int) {
        var rating = "Rating: " + shows[position].rating.toString()
        holder.view.title.text = shows[position].title
        holder.view.rating.text = rating
    }

    override fun getItemCount() = shows.size
}