package com.hofstadtchristopher.basal_o_mat.dir_Test

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.hofstadtchristopher.basal_o_mat.R
import kotlinx.android.synthetic.main.fragment_test.*

class FragmentTest : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fTest_Btn_js.setOnClickListener {
            Navigation.findNavController(it).navigate(FragmentTestDirections.actionToFragmentTestStart())
        }

        fTest_Btn_scdl.setOnClickListener {
            Navigation.findNavController(it).navigate(FragmentTestDirections.actionToFragmentTestSchedule())
        }
    }
}
