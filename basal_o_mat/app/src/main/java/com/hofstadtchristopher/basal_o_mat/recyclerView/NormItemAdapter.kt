package com.hofstadtchristopher.basal_o_mat.recyclerView

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hofstadtchristopher.basal_o_mat.R
import kotlinx.android.synthetic.main.recyclerview_item_normal.view.*

class NormItemAdapter internal constructor(
    context: Context
) : RecyclerView.Adapter<NormItemAdapter.NormItemHolder>() {

    private var items = emptyList<NormItem>()

    inner class NormItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.rVI_normal_iView
        val title: TextView = itemView.rVI_normal_title
        val subTitle: TextView = itemView.rVI_normal_subtitle
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NormItemHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_item_normal, parent, false)

        return NormItemHolder(itemView)
    }

    override fun onBindViewHolder(holder: NormItemHolder, position: Int) {
        val current = items[position]
        holder.imageView.setImageResource(current.image)
        holder.title.text = current.title
        holder.subTitle.text = current.subTitle
    }

    internal  fun setItems(items: List<NormItem>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun getItemCount() = items.size
}