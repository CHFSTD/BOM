package com.hofstadtchristopher.basal_o_mat.dir_Test


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.hofstadtchristopher.basal_o_mat.R
import com.hofstadtchristopher.basal_o_mat.recyclerView.ResultItemAdapter
import com.hofstadtchristopher.basal_o_mat.viewModel.FTestViewModel
import kotlinx.android.synthetic.main.fragment_test_result.*

/**
 * A simple [Fragment] subclass.
 */
class FragmentTestResult : Fragment() {
    lateinit var vMdl: FTestViewModel
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vMdl = ViewModelProvider(activity!!).get(FTestViewModel::class.java)
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_test_result, container, false)
        recyclerView = view.findViewById(R.id.fTR_recyclerView)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fTR_label_tv.text = getString(R.string.fTR_label_tv, vMdl.chosenBRate.name, vMdl.testDate)

        fTR_btn_exit.setOnClickListener {
            vMdl.measuredData.forEachIndexed {
                    index, i ->
                        Log.i("measuredData", "measuredData at pos $index is $i")
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val adapter = ResultItemAdapter(vMdl.res, context!!)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }


}
