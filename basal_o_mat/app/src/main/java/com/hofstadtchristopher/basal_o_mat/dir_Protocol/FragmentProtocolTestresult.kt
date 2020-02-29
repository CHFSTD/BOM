package com.hofstadtchristopher.basal_o_mat.dir_Protocol


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder

import com.hofstadtchristopher.basal_o_mat.R
import com.hofstadtchristopher.basal_o_mat.recyclerView.ResultItemAdapter
import com.hofstadtchristopher.basal_o_mat.viewModel.FProtocolViewModel
import kotlinx.android.synthetic.main.fragment_protocol_testresult.*

/**
 * A simple [Fragment] subclass.
 */
class FragmentProtocolTestresult : Fragment() {

    private lateinit var vMdl: FProtocolViewModel
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vMdl = ViewModelProvider(activity!!).get(FProtocolViewModel::class.java)
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_protocol_testresult, container, false)
        recyclerView = view.findViewById(R.id.fPTr_recyclerView)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            vMdl.tResultPos = FragmentProtocolTestresultArgs.fromBundle(it).tResultPos
            vMdl.initToShowTestResult()
        }

        fPTr_label_tv.text = getString(R.string.fTR_label_tv, vMdl.selectedTestResult.testProfileName, vMdl.selectedTestResult.testDate)
        fPTr_advice_text.text = vMdl.selectedTestResult.savedRecommendation

        fPTr_btn_deleteResult.setOnClickListener {
            MaterialAlertDialogBuilder(context)
                .setTitle(getString(R.string.result_delete_question))
                .setMessage(R.string.result_are_u_sure)
                .setPositiveButton(getString(R.string.delete)) { _,_ ->
                    vMdl.delete(vMdl.selectedTestResult.id)
                    Navigation.findNavController(it).navigate(FragmentProtocolTestresultDirections.actionToNavigationProtocol())
                }
                .setNegativeButton(getString(R.string.cancel), null)
                .show()
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val adapter = ResultItemAdapter(vMdl.selectedTestResult, context!!)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

    }


}
