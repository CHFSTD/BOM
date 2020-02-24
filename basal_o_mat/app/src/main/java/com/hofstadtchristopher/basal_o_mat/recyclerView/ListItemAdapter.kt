package com.hofstadtchristopher.basal_o_mat.recyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hofstadtchristopher.basal_o_mat.R
import kotlinx.android.synthetic.main.recyclerview_item_list.view.*

class ListItemAdapter : RecyclerView.Adapter<ListItemAdapter.ListItemHolder>() {

    private var items = emptyList<ListItem>()

    inner class ListItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView =itemView.rVI_list_text
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_item_list, parent, false)

        return ListItemHolder(itemView)
    }

    override fun onBindViewHolder(holder: ListItemHolder, position: Int) {
        val current = items[position]
        holder.title.text = current.title
    }

    internal  fun setItems(items: List<ListItem>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun getItemCount() = items.size
}