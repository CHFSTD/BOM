package com.hofstadtchristopher.basal_o_mat.recyclerView

import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hofstadtchristopher.basal_o_mat.R
import com.hofstadtchristopher.basal_o_mat.room.TestResult
import kotlinx.android.synthetic.main.recyclerview_item_result_protocol.view.*

class TestResultItemAdapter: RecyclerView.Adapter<TestResultItemAdapter.TestResultItemHolder>() {
    private var items = emptyList<TestResult>()
    private var listener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    inner class TestResultItemHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val tDate: TextView = itemView.rVR_prot_date
        val pTitle: TextView = itemView.rVR_prot_title

        init {
            itemView.setOnClickListener {
                if (listener != null && adapterPosition != RecyclerView.NO_POSITION) {
                    listener!!.onItemClick(adapterPosition)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TestResultItemHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item_result_protocol, parent , false)
        return TestResultItemHolder(itemView)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(
        holder: TestResultItemHolder,
        position: Int
    ) {
        val current = items[position]
        holder.tDate.text = current.testDate
        holder.pTitle.text = current.testProfileName
    }

    internal fun setItems(items: List<TestResult>) {
        this.items = items
        notifyDataSetChanged()
    }
}

