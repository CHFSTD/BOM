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

import com.hofstadtchristopher.basal_o_mat.R
import com.hofstadtchristopher.basal_o_mat.room.BasalRate
import com.hofstadtchristopher.basal_o_mat.viewModel.FProfileViewModel
import kotlinx.android.synthetic.main.fragment_update_basal_rate.*

/**
 * A simple [Fragment] subclass.
 */
class FragmentUpdateBasalRate : Fragment() {

    private var bRateID: Int? = null
    private lateinit var fProfileViewModel: FProfileViewModel
    private lateinit var tmpBasalRate: BasalRate

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fProfileViewModel = ViewModelProvider(activity!!).get(FProfileViewModel::class.java)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update_basal_rate, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            //bRateID = FragmentUpdateBasalRateArgs.fromBundle(it).bRateID
            fProfileViewModel.bRatePos = FragmentUpdateBasalRateArgs.fromBundle(it).bRatePos
            fProfileViewModel.initProfToBeModified()
        }

        fUpdateBr_time.text = getString(R.string.fAddBr_time_low, fProfileViewModel.modSelectedHour, fProfileViewModel.modSelectedHour+1)
        setUnit()
        fUpdateBr_profileNameET.editText!!.setText(fProfileViewModel.modProfilname)

        fUpdateBr_profileNameET.editText!!.setOnFocusChangeListener { _, hasFocus ->
            if(!hasFocus) {
                if (validateProfileName()) {
                    fProfileViewModel.upldProfilName = fUpdateBr_profileNameET.editText!!.text.toString().trim()
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
            if (fProfileViewModel.modBasalProfile[fProfileViewModel.modSelectedHour] < 1F) {
                fProfileViewModel.modBasalProfile[fProfileViewModel.modSelectedHour] = 0.0F
                fUpdateBr_unitDisplay.error = getString(R.string.input_basalunit_to_low)
            } else {
                fProfileViewModel.modBasalProfile[fProfileViewModel.modSelectedHour]-= 1.0F
                fUpdateBr_unitDisplay.error = null
                fUpdateBr_unitDisplay.isErrorEnabled = false
            }
            setUnit()
        }

        fUpdateBr_btn_minusPointOne.setOnClickListener {
            if (fProfileViewModel.modBasalProfile[fProfileViewModel.modSelectedHour] < 0.10F) {
                fProfileViewModel.modBasalProfile[fProfileViewModel.modSelectedHour] = 0.0F
                fUpdateBr_unitDisplay.error = getString(R.string.input_basalunit_to_low)
            } else {
                fProfileViewModel.modBasalProfile[fProfileViewModel.modSelectedHour]-= 0.10F
                fUpdateBr_unitDisplay.error = null
                fUpdateBr_unitDisplay.isErrorEnabled = false
            }
            setUnit()
        }

        fUpdateBr_btn_minusPointZeroOne.setOnClickListener {
            if (fProfileViewModel.modBasalProfile[fProfileViewModel.modSelectedHour] < 0.010F) {
                fProfileViewModel.modBasalProfile[fProfileViewModel.modSelectedHour] = 0.0F
                fUpdateBr_unitDisplay.error = getString(R.string.input_basalunit_to_low)
            } else {
                fProfileViewModel.modBasalProfile[fProfileViewModel.modSelectedHour]-= 0.010F
                fUpdateBr_unitDisplay.error = null
                fUpdateBr_unitDisplay.isErrorEnabled = false
            }
            setUnit()
        }

        fUpdateBr_btn_plusOne.setOnClickListener {
            if (fProfileViewModel.modBasalProfile[fProfileViewModel.modSelectedHour] > 3F) {
                fProfileViewModel.modBasalProfile[fProfileViewModel.modSelectedHour] = 4F
                fUpdateBr_unitDisplay.error = getString(R.string.input_basalunit_to_high)
            } else {
                fProfileViewModel.modBasalProfile[fProfileViewModel.modSelectedHour]+=1.0F
                fUpdateBr_unitDisplay.error = null
                fUpdateBr_unitDisplay.isErrorEnabled = false
            }
            setUnit()
        }

        fUpdateBr_btn_plusPointOne.setOnClickListener {
            if (fProfileViewModel.modBasalProfile[fProfileViewModel.modSelectedHour] > 3.9F) {
                fProfileViewModel.modBasalProfile[fProfileViewModel.modSelectedHour] = 4F
                fUpdateBr_unitDisplay.error = getString(R.string.input_basalunit_to_high)
            } else {
                fProfileViewModel.modBasalProfile[fProfileViewModel.modSelectedHour]+=0.10F
                fUpdateBr_unitDisplay.error = null
                fUpdateBr_unitDisplay.isErrorEnabled = false
            }
            setUnit()
        }

        fUpdateBr_btn_plusPointZeroOne.setOnClickListener {
            if (fProfileViewModel.modBasalProfile[fProfileViewModel.modSelectedHour] > 3.99F) {
                fProfileViewModel.modBasalProfile[fProfileViewModel.modSelectedHour] = 4F
                fUpdateBr_unitDisplay.error = getString(R.string.input_basalunit_to_high)
            } else {
                fProfileViewModel.modBasalProfile[fProfileViewModel.modSelectedHour]+=0.010F
                fUpdateBr_unitDisplay.error = null
                fUpdateBr_unitDisplay.isErrorEnabled = false
            }
            setUnit()
        }

        fUpdateBr_btn_saveProfile.setOnClickListener {
            fProfileViewModel.update(fProfileViewModel.modBasalRate)
            Navigation.findNavController(it).navigate(FragmentUpdateBasalRateDirections.actionToNavigationProfil())
        }

    }

    private fun validateProfileName(): Boolean {
        val input = fUpdateBr_profileNameET.editText!!.text.toString().trim()

        return if (input.isEmpty()) {
            fUpdateBr_profileNameET.error = "Bitte Profilname eingeben"
            false
        } else {
            fUpdateBr_profileNameET.error = null
            fUpdateBr_profileNameET.isErrorEnabled = false
            true
        }
    }

    private fun setTime() {
        val selTime = fProfileViewModel.modSelectedHour
        when {
            selTime > 23 -> {
                fProfileViewModel.modSelectedHour = 0
                setDisplay(fProfileViewModel.modSelectedHour)
            }
            selTime < 0 -> {
                fProfileViewModel.modSelectedHour = 23
                setDisplay(fProfileViewModel.modSelectedHour)
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
        fProfileViewModel.modSelectedHour++
        setTime()
    }

    private fun prevTime() {
        fProfileViewModel.modSelectedHour--
        setTime()
    }

    //displays the basalunit
    private fun setUnit() {
        fUpdateBr_unitTV.text = getString(R.string.number_to_string, fProfileViewModel.modBasalProfile[fProfileViewModel.modSelectedHour])
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
            for (el in fProfileViewModel.modBasalProfile) {
                if (el == 0F) {
                    return false
                }
            }
        }
        return true
    }

}
