package com.hofstadtchristopher.basal_o_mat.dir_Test


import android.content.DialogInterface
import android.opengl.Visibility
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
        fTR_advice_text.text = vMdl.res.recommendation

        if(vMdl.res.recommendation == "Keine Korrektur nötig, Basalrate ist OK"
            || vMdl.res.recommendation == "No need for adjustments, Basalrate is OK") {
            fTR_result_note.visibility= View.GONE
            fTR_btn_update_prof.isEnabled = false
        }

        fTR_btn_exit.setOnClickListener {
            Navigation.findNavController(it).navigate(FragmentTestResultDirections.actionToNavigationStart())
        }

        fTR_btn_update_prof.setOnClickListener {
            vMdl.update(vMdl.res.adjustedRate)
            MaterialAlertDialogBuilder(context)
                .setMessage(getString(R.string.profile_updated, vMdl.chosenBRate.name))
                .setNeutralButton(getString(R.string.ok)) { _, _ ->
                    vMdl.update(vMdl.res.adjustedRate)
                    Navigation.findNavController(it).navigate(FragmentTestResultDirections.actionToNavigationStart())
                }
                .show()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val adapter = ResultItemAdapter(vMdl.res, context!!)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter


    }


}
