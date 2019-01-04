package com.example.corina.trackseries.show

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.google.firebase.firestore.FirebaseFirestore

import android.content.Intent
import android.util.Log
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.example.corina.trackseries.R
import com.example.corina.trackseries.R.id.parent
import com.example.corina.trackseries.model.Show
import kotlinx.android.synthetic.main.show_view.view.*

class ShowRecyclerViewAdapter(
    private val showsList: MutableList<Show>,
    private val context: Context,
    private val firestoreDB: FirebaseFirestore)

    : RecyclerView.Adapter<ShowRecyclerViewAdapter.ShowViewHolder>() {

    class ShowViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.show_view, parent, false)

        val animation: Animation

        when (ShowListActivity.animationItem) {
            R.id.fade -> {
                animation = AnimationUtils.loadAnimation(context, R.anim.fade_in)
                view.startAnimation(animation)
            }
            R.id.slideleft -> {
                animation = AnimationUtils.loadAnimation(context, R.anim.slide_left)
                view.startAnimation(animation)
            }
            R.id.slideup -> {
                animation = AnimationUtils.loadAnimation(context, R.anim.slide_up)
                view.startAnimation(animation)
            }
            R.id.shake -> {
                animation = AnimationUtils.loadAnimation(context, R.anim.shake)
                view.startAnimation(animation)
            }
            R.id.scale -> {
                animation = AnimationUtils.loadAnimation(context, R.anim.scale)
                view.startAnimation(animation)
            }
        }
        return ShowViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShowViewHolder, position: Int) {
        val show = showsList[position]

        Log.d("elem", show.title)

        holder.view.title.text = show.title
        holder.view.rating.text = show.rating.toString()

        holder.view.btnEdit.setOnClickListener { updateShow(show) }
        holder.view.btnDelete.setOnClickListener { deleteShow(show.id!!, position) }
    }


    override fun getItemCount(): Int {
        return showsList.size
    }

    private fun updateShow(show: Show) {
        val intent = Intent(context, ShowActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra("UpdateShowId", show.id)
        intent.putExtra("UpdateShowTitle", show.title)
        intent.putExtra("UpdateShowRating", show.rating.toString())
        intent.putExtra("UpdateShowProducer", show.producer)
        intent.putExtra("UpdateShowDescription", show.description)
        context.startActivity(intent)
    }

    private fun deleteShow(id: String, position: Int) {
        firestoreDB.collection("shows")
            .document(id)
            .delete()
            .addOnCompleteListener {
                showsList.removeAt(position)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, showsList.size)
                Toast.makeText(context, "Show has been deleted!", Toast.LENGTH_SHORT).show()
            }
    }
}