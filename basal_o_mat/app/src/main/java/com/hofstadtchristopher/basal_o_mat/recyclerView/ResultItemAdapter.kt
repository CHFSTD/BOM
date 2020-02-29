package com.hofstadtchristopher.basal_o_mat.recyclerView

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hofstadtchristopher.basal_o_mat.R
import com.hofstadtchristopher.basal_o_mat.room.TestResult
import com.hofstadtchristopher.basal_o_mat.util.TestResultHelper
import kotlinx.android.synthetic.main.recyclerview_item_result.view.*

class ResultItemAdapter(var tResult: TestResult, private var context: Context) : RecyclerView.Adapter<ResultItemAdapter.ResultItemHolder>() {

    //private var helper: TestResultHelper = TestResultHelper(tResult)
    private var measItems : Array<Int> = tResult.measuredData //helper.measuredData

    inner class ResultItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val indicator: ImageView = itemView.rVR_indicator
        val trend: ImageView = itemView.rVR_trend
        val measurement: TextView = itemView.rVR_measurement
        val time: TextView = itemView.rVR_time
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultItemHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_item_result, parent, false)

        return ResultItemHolder(itemView)
    }

    override fun onBindViewHolder(holder: ResultItemHolder, position: Int) {
        val current = measItems[position]
        if (position == 0) {
            holder.measurement.text = context.getString(R.string.measurement_text, current)
            holder.time.text = context.getString(R.string.fTR_recyclerView_clock, tResult.hourAtStart + position)//(tResult.hourAtStart + position).toString()
            holder.indicator.setBackgroundResource(R.drawable.trend_good)
            holder.trend.setBackgroundResource(tResult.trends[position])//holder.trend.setBackgroundResource(helper.trends[position])
        } else {
            holder.measurement.text = context.getString(R.string.measurement_text, current)
            holder.time.text = context.getString(R.string.fTR_recyclerView_clock, tResult.hourAtStart + position) //(tResult.hourAtStart + position).toString()
            holder.indicator.setBackgroundResource(tResult.trendColor[position-1])
            holder.trend.setBackgroundResource(tResult.trends[position-1])
            //holder.indicator.setBackgroundResource(helper.trendColor[position-1])
            //holder.trend.setBackgroundResource(helper.trends[position-1])
        }
    }

    override fun getItemCount() = measItems.size

    internal fun setItems() {
        measItems = tResult.measuredData
        notifyDataSetChanged()
    }

}