package com.hofstadtchristopher.basal_o_mat.dir_Profil


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
import com.google.android.material.dialog.MaterialAlertDialogBuilder

import com.hofstadtchristopher.basal_o_mat.R
import com.hofstadtchristopher.basal_o_mat.viewModel.FProfileViewModel
import kotlinx.android.synthetic.main.fragment_update_basal_rate.*

/**
 * A simple [Fragment] subclass.
 */
class FragmentUpdateBasalRate : Fragment() {

    private lateinit var vMdl: FProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vMdl = ViewModelProvider(activity!!).get(FProfileViewModel::class.java)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update_basal_rate, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            //bRateID = FragmentUpdateBasalRateArgs.fromBundle(it).bRateID
            vMdl.bRatePos = FragmentUpdateBasalRateArgs.fromBundle(it).bRatePos
            vMdl.initProfToBeModified()
        }

        fUpdateBr_time.text = getString(R.string.fAddBr_time_low, vMdl.modSelectedHour, vMdl.modSelectedHour+1)
        setUnit()
        fUpdateBr_profileNameET.editText!!.setText(vMdl.modProfilname)

        fUpdateBr_profileNameET.editText!!.setOnFocusChangeListener { _, hasFocus ->
            if(!hasFocus) {
                if (validateProfileName()) {
                    vMdl.upldProfilName = fUpdateBr_profileNameET.editText!!.text.toString().trim()
                    checkEnableSaveBtn()
                }
                //hide keyboard when editText lost focus
                val imm: InputMethodManager = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(fUpdateBr_profileNameET.windowToken, 0)
            }
        }

        fUpdateBr_profileNameET.editText!!.setOnEditorActionListener { v, i, _ ->
            if(i == EditorInfo.IME_ACTION_DONE) {
                v.clearFocus()
            }
            false
        }

        fUpdateBr_btn_next.setOnClickListener {
            nextTime()
            fUpdateBr_unitDisplay.error = null
            fUpdateBr_unitDisplay.isErrorEnabled = false
            setUnit()
        }

        fUpdateBr_btn_prev.setOnClickListener {
            prevTime()
            fUpdateBr_unitDisplay.error = null
            fUpdateBr_unitDisplay.isErrorEnabled = false
            setUnit()
        }

        fUpdateBr_btn_minusOne.setOnClickListener {
            if (vMdl.modBasalProfile[vMdl.modSelectedHour] < 1F) {
                vMdl.modBasalProfile[vMdl.modSelectedHour] = 0.0F
                fUpdateBr_unitDisplay.error = getString(R.string.input_basalunit_to_low)
            } else {
                vMdl.modBasalProfile[vMdl.modSelectedHour]-= 1.0F
                fUpdateBr_unitDisplay.error = null
                fUpdateBr_unitDisplay.isErrorEnabled = false
            }
            setUnit()
        }

        fUpdateBr_btn_minusPointOne.setOnClickListener {
            if (vMdl.modBasalProfile[vMdl.modSelectedHour] < 0.10F) {
                vMdl.modBasalProfile[vMdl.modSelectedHour] = 0.0F
                fUpdateBr_unitDisplay.error = getString(R.string.input_basalunit_to_low)
            } else {
                vMdl.modBasalProfile[vMdl.modSelectedHour]-= 0.10F
                fUpdateBr_unitDisplay.error = null
                fUpdateBr_unitDisplay.isErrorEnabled = false
            }
            setUnit()
        }

        fUpdateBr_btn_minusPointZeroOne.setOnClickListener {
            if (vMdl.modBasalProfile[vMdl.modSelectedHour] < 0.010F) {
                vMdl.modBasalProfile[vMdl.modSelectedHour] = 0.0F
                fUpdateBr_unitDisplay.error = getString(R.string.input_basalunit_to_low)
            } else {
                vMdl.modBasalProfile[vMdl.modSelectedHour]-= 0.010F
                fUpdateBr_unitDisplay.error = null
                fUpdateBr_unitDisplay.isErrorEnabled = false
            }
            setUnit()
        }

        fUpdateBr_btn_plusOne.setOnClickListener {
            if (vMdl.modBasalProfile[vMdl.modSelectedHour] > 3F) {
                vMdl.modBasalProfile[vMdl.modSelectedHour] = 4F
                fUpdateBr_unitDisplay.error = getString(R.string.input_basalunit_to_high)
            } else {
                vMdl.modBasalProfile[vMdl.modSelectedHour]+=1.0F
                fUpdateBr_unitDisplay.error = null
                fUpdateBr_unitDisplay.isErrorEnabled = false
            }
            setUnit()
        }

        fUpdateBr_btn_plusPointOne.setOnClickListener {
            if (vMdl.modBasalProfile[vMdl.modSelectedHour] > 3.9F) {
                vMdl.modBasalProfile[vMdl.modSelectedHour] = 4F
                fUpdateBr_unitDisplay.error = getString(R.string.input_basalunit_to_high)
            } else {
                vMdl.modBasalProfile[vMdl.modSelectedHour]+=0.10F
                fUpdateBr_unitDisplay.error = null
                fUpdateBr_unitDisplay.isErrorEnabled = false
            }
            setUnit()
        }

        fUpdateBr_btn_plusPointZeroOne.setOnClickListener {
            if (vMdl.modBasalProfile[vMdl.modSelectedHour] > 3.99F) {
                vMdl.modBasalProfile[vMdl.modSelectedHour] = 4F
                fUpdateBr_unitDisplay.error = getString(R.string.input_basalunit_to_high)
            } else {
                vMdl.modBasalProfile[vMdl.modSelectedHour]+=0.010F
                fUpdateBr_unitDisplay.error = null
                fUpdateBr_unitDisplay.isErrorEnabled = false
            }
            setUnit()
        }

        fUpdateBr_btn_saveProfile.setOnClickListener {
            vMdl.update(vMdl.modBasalRate)
            Navigation.findNavController(it).navigate(FragmentUpdateBasalRateDirections.actionToNavigationProfil())
        }

        fUpdateBr_btn_deleteProfile.setOnClickListener {
            MaterialAlertDialogBuilder(context)
                .setTitle(getString(R.string.profile_delete_question, vMdl.modProfilname))
                .setMessage(getString(R.string.are_u_sure))
                .setPositiveButton(getString(R.string.delete)) { _, _ ->
                    vMdl.delete(vMdl.modBasalRate.id)
                    Navigation.findNavController(it).navigate(FragmentUpdateBasalRateDirections.actionToNavigationProfil())
                }
                .setNegativeButton(getString(R.string.cancel), null)
                .show()

        }

    }

    private fun validateProfileName(): Boolean {
        val input = fUpdateBr_profileNameET.editText!!.text.toString().trim()

        return if (input.isEmpty()) {
            fUpdateBr_profileNameET.error = getString(R.string.error_profilename)
            false
        } else {
            fUpdateBr_profileNameET.error = null
            fUpdateBr_profileNameET.isErrorEnabled = false
            true
        }
    }

    private fun setTime() {
        val selTime = vMdl.modSelectedHour
        when {
            selTime > 23 -> {
                vMdl.modSelectedHour = 0
                setDisplay(vMdl.modSelectedHour)
            }
            selTime < 0 -> {
                vMdl.modSelectedHour = 23
                setDisplay(vMdl.modSelectedHour)
            }
            selTime in 0..23 -> {
                setDisplay(selTime)
            }
        }
    }

    private fun setDisplay(t: Int) {
        when (t) {
            in 0..8 ->      fUpdateBr_time.text = getString(R.string.fAddBr_time_low, t, t+1)
            9 ->            fUpdateBr_time.text = getString(R.string.fAddBr_time_nine, t, t+1)
            in 10..23 ->    fUpdateBr_time.text = getString(R.string.fAddBr_time_high, t, t+1)
        }
    }

    private fun nextTime() {
        vMdl.modSelectedHour++
        setTime()
    }

    private fun prevTime() {
        vMdl.modSelectedHour--
        setTime()
    }

    //displays the basalunit
    private fun setUnit() {
        fUpdateBr_unitTV.text = getString(R.string.number_to_string, vMdl.modBasalProfile[vMdl.modSelectedHour])
        checkEnableSaveBtn()
    }

    private fun checkEnableSaveBtn() {
        fUpdateBr_btn_saveProfile.isEnabled = isRdyToSave()
    }

    private fun isRdyToSave(): Boolean {
        val pNameInput = fUpdateBr_profileNameET.editText!!.text.toString().trim()
        if (pNameInput.isEmpty()) {
            return false
        } else {
            for (el in vMdl.modBasalProfile) {
                if (el == 0F) {
                    return false
                }
            }
        }
        return true
    }

}
