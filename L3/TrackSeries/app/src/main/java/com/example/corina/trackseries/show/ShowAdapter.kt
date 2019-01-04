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
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.BaseAdapter
import android.widget.Toast
import com.example.corina.trackseries.R
import com.example.corina.trackseries.model.Show
import kotlinx.android.synthetic.main.show_view.view.*

class ShowAdapter(
    private val showsList: MutableList<Show>,
    private val context: Context,
    private val firestoreDB: FirebaseFirestore)

    : BaseAdapter() {


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view: View?
        var viewHolder: ShowViewHolder

        if(convertView == null) {
            var layout = LayoutInflater.from(context)
            view = layout.inflate(R.layout.show_view, parent, false)
            viewHolder = ShowViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ShowViewHolder
        }

        var show = this.getItem(position) as Show

        viewHolder.txtTitle.text = show.title
        viewHolder.txtRating.text = show.rating.toString()


        viewHolder.view.btnEdit.setOnClickListener { updateShow(show) }
        viewHolder.view.btnDelete.setOnClickListener { deleteShow(show.id!!, position) }

        val animation: Animation

        when (ShowListActivity.animationItem) {
            R.id.fade -> {
                animation = AnimationUtils.loadAnimation(context, R.anim.fade_in)
                convertView!!.startAnimation(animation)
            }
            R.id.slideleft -> {
                animation = AnimationUtils.loadAnimation(context, R.anim.slide_left)
                convertView!!.startAnimation(animation)
            }
            R.id.slideup -> {
                animation = AnimationUtils.loadAnimation(context, R.anim.slide_up)
                convertView!!.startAnimation(animation)
            }
            R.id.shake -> {
                animation = AnimationUtils.loadAnimation(context, R.anim.shake)
                convertView!!.startAnimation(animation)
            }
            R.id.scale -> {
                animation = AnimationUtils.loadAnimation(context, R.anim.scale)
                convertView!!.startAnimation(animation)
            }
        }


        return view!!
    }

    override fun getItem(position: Int): Any {
        return showsList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return showsList.count()
    }

    class ShowViewHolder(val view: View) {
        var txtTitle: TextView = view.title
        var txtRating: TextView = view.rating

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
//                notifyItemRemoved(position)
//                notifyItemRangeChanged(position, showsList.size)
                Toast.makeText(context, "Show has been deleted!", Toast.LENGTH_SHORT).show()
            }
    }
}