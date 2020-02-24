package com.hofstadtchristopher.basal_o_mat.dir_Test


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation

import com.hofstadtchristopher.basal_o_mat.R
import com.hofstadtchristopher.basal_o_mat.viewModel.FTestViewModel
import kotlinx.android.synthetic.main.fragment_bloodsugar_input.*

/**
 * A simple [Fragment] subclass.
 */
class FragmentBloodsugarInput : Fragment() {
    private lateinit var vMdl: FTestViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vMdl = ViewModelProvider(activity!!).get(FTestViewModel::class.java)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bloodsugar_input, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fTest_btn_continue.setOnClickListener {
            vMdl.testProgress++
            if (vMdl.isFinished()){
                vMdl.isTestMode = false
                Navigation.findNavController(it).navigate(FragmentBloodsugarInputDirections.actionToFragmentTestResult())
            } else {
                Navigation.findNavController(it).navigate(FragmentBloodsugarInputDirections.actionToNavigationTest())
            }
        }

        fBLSI_input.editText!!.setOnFocusChangeListener { _, hasFocus ->
            if(!hasFocus) {
                if (validateInput()) {
                    vMdl.measuredData[vMdl.testProgress] =  fBLSI_input.editText!!.text.toString().toInt()
                }
                //hide keyboard when editText lost focus
                val imm: InputMethodManager = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow( fBLSI_input.windowToken, 0)
            }
        }

        fBLSI_input.editText!!.setOnEditorActionListener { v, i, _ ->
            if(i == EditorInfo.IME_ACTION_DONE) {
                v.clearFocus()
            }
            false
        }
    }

    fun validateInput(): Boolean{
        val input = fBLSI_input.editText!!.text.toString()

        return if (input.isEmpty()) {
            fBLSI_input.error = getString(R.string.error_bloodsugar)
            false
        } else {
            fTest_btn_continue.isEnabled = true
            fBLSI_input.error = null
            fBLSI_input.isErrorEnabled = false
            true
        }
    }
}
