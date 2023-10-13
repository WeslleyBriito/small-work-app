package com.opensystem.smallwork.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.opensystem.smallwork.R
import com.opensystem.smallwork.models.Rating

class RatingAdapter(private val ratingList: List<Rating>)
    : RecyclerView.Adapter<RatingAdapter.RatingHolder>(){

    class RatingHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RatingHolder {
        return RatingHolder(LayoutInflater.from(parent.context).inflate(R.layout.adapter_rating, parent, false))
    }

    override fun onBindViewHolder(holder: RatingHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 6
    }

}