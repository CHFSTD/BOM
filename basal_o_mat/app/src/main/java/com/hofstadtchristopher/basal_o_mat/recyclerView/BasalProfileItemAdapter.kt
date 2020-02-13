package com.hofstadtchristopher.basal_o_mat.recyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hofstadtchristopher.basal_o_mat.R
import com.hofstadtchristopher.basal_o_mat.room.BasalRate
import kotlinx.android.synthetic.main.recyclerview_item_basal_profile.view.*

class BasalProfileItemAdapter : RecyclerView.Adapter<BasalProfileItemAdapter.BasalProfileItemHolder>() {

    private var items = emptyList<BasalRate>()

    inner class BasalProfileItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val pName: TextView = itemView.rVProf_pName
        val dayRate: TextView = itemView.rVProf_dayDose
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BasalProfileItemHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_item_basal_profile, parent, false)

        return BasalProfileItemHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: BasalProfileItemHolder,
        position: Int
    ) {
        val current = items[position]
        holder.pName.text = current.name
        holder.dayRate.text = current.dayRate().toString()
    }

    override fun getItemCount() = items.size

    internal fun setItems(items: List<BasalRate>) {
        this.items = items
        notifyDataSetChanged()
    }
}