package com.hofstadtchristopher.basal_o_mat.dir_Test


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider

import com.hofstadtchristopher.basal_o_mat.R
import com.hofstadtchristopher.basal_o_mat.viewModel.FTestViewModel

/**
 * A simple [Fragment] subclass.
 */
class FragmentTestResult : Fragment() {
    lateinit var vMdl: FTestViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vMdl = ViewModelProvider(activity!!).get(FTestViewModel::class.java)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test_result, container, false)
    }


}
