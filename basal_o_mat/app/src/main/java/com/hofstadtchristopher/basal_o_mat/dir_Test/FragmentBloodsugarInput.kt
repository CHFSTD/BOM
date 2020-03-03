package com.hofstadtchristopher.basal_o_mat.dir_Test


import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder

import com.hofstadtchristopher.basal_o_mat.R
import com.hofstadtchristopher.basal_o_mat.viewModel.FTestViewModel
import kotlinx.android.synthetic.main.fragment_bloodsugar_input.*

/**
 * Fragment shown after timer finished to input bloodsugar value
 */
class FragmentBloodsugarInput : Fragment() {
    private lateinit var vMdl: FTestViewModel
    private lateinit var recyclerView: RecyclerView

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

        /*if user entered this fragment and moving to another destination without entering an input,
        * user will be returned to this fragment, where he left and continuing entering an input.
        */
        vMdl.measuredData[vMdl.testProgress] = -1

        fTest_btn_continue.setOnClickListener {
            vMdl.measuredData[vMdl.testProgress] = fBLSI_input.editText!!.text.toString().toInt()

            if (vMdl.isFinished()){
                vMdl.isTestMode = false
                Navigation.findNavController(it).navigate(FragmentBloodsugarInputDirections.actionToFragmentTestResult())
            } else {
                val x = vMdl.measuredData[vMdl.testProgress]
                //terminate test when bloodsugar is too high or low
                when {
                    x > 200 -> {
                        MaterialAlertDialogBuilder(context)
                            .setTitle(getString(R.string.bs_high))
                            .setMessage(getString(R.string.bs_low_message))
                            .setNeutralButton(getString(R.string.ok)) { _,_ ->
                                vMdl.isTestMode = false
                                vMdl.resetTest()
                                //vMdl.termPos = vMdl.testProgress    //save terminated position
                                //vMdl.testProgress = vMdl.MAX_TEST_PROGRESS+1
                                Navigation.findNavController(it).navigate(FragmentBloodsugarInputDirections.actionToNavigationTest())
                            }
                            .show()
                    }
                    x < 65 -> {
                        MaterialAlertDialogBuilder(context)
                            .setTitle(getString(R.string.bs_low))
                            .setMessage(getString(R.string.bs_high_message))
                            .setNeutralButton(getString(R.string.ok)) { _,_ ->
                                vMdl.isTestMode = false
                                vMdl.resetTest()
                                //vMdl.termPos = vMdl.testProgress    //save terminated position
                                //vMdl.testProgress = vMdl.MAX_TEST_PROGRESS+1
                                Navigation.findNavController(it).navigate(FragmentBloodsugarInputDirections.actionToNavigationTest())
                            }
                            .show()
                    }
                    else -> {
                        vMdl.testProgress++
                        Navigation.findNavController(it).navigate(FragmentBloodsugarInputDirections.actionToNavigationTest())
                    }
                }
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
