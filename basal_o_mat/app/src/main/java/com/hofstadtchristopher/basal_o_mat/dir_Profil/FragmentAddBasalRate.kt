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
import com.hofstadtchristopher.basal_o_mat.viewModel.FProfileViewModel
import kotlinx.android.synthetic.main.fragment_add_basal_rate.*

/**
 * A simple [Fragment] subclass.
 */
class FragmentAddBasalRate : Fragment() {

    private lateinit var vMdl: FProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vMdl = ViewModelProvider(activity!!).get(FProfileViewModel::class.java)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_basal_rate, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fAddBr_time.text = getString(R.string.fAddBr_time_low, vMdl.upldSelectedHour, vMdl.upldSelectedHour+1)
        setUnit()
        fAddBr_profileNameET.editText!!.setText(vMdl.upldProfilName)

        fAddBr_profileNameET.editText!!.setOnFocusChangeListener { _, hasFocus ->
            if(!hasFocus) {
                if (validateProfileName()) {
                    vMdl.upldProfilName = fAddBr_profileNameET.editText!!.text.toString().trim()
                    checkEnableSaveBtn()
                }
                //hide keyboard when editText lost focus
                val imm: InputMethodManager = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(fAddBr_profileNameET.windowToken, 0)
            }
        }

        fAddBr_profileNameET.editText!!.setOnEditorActionListener { v, i, _ ->
            if(i == EditorInfo.IME_ACTION_DONE) {
                v.clearFocus()
            }
            false
        }

        fAddBr_btn_next.setOnClickListener {
            nextTime()
            fAddBr_unitDisplay.error = null
            fAddBr_unitDisplay.isErrorEnabled = false
            setUnitForBtnNext()
        }

        fAddBr_btn_prev.setOnClickListener {
            prevTime()
            fAddBr_unitDisplay.error = null
            fAddBr_unitDisplay.isErrorEnabled = false
            setUnit()
        }

        fAddBr_btn_minusOne.setOnClickListener {
            if (vMdl.upldBasalProfile[vMdl.upldSelectedHour] < 1F) {
                vMdl.upldBasalProfile[vMdl.upldSelectedHour] = 0.0F
                fAddBr_unitDisplay.error = getString(R.string.input_basalunit_to_low)
            } else {
                vMdl.upldBasalProfile[vMdl.upldSelectedHour]-= 1.0F
                fAddBr_unitDisplay.error = null
                fAddBr_unitDisplay.isErrorEnabled = false
            }
            setUnit()
        }

        fAddBr_btn_minusPointOne.setOnClickListener {
            if (vMdl.upldBasalProfile[vMdl.upldSelectedHour] < 0.10F) {
                vMdl.upldBasalProfile[vMdl.upldSelectedHour] = 0.0F
                fAddBr_unitDisplay.error = getString(R.string.input_basalunit_to_low)
            } else {
                vMdl.upldBasalProfile[vMdl.upldSelectedHour]-= 0.10F
                fAddBr_unitDisplay.error = null
                fAddBr_unitDisplay.isErrorEnabled = false
            }
            setUnit()
        }

        fAddBr_btn_minusPointZeroOne.setOnClickListener {
            if (vMdl.upldBasalProfile[vMdl.upldSelectedHour] < 0.010F) {
                vMdl.upldBasalProfile[vMdl.upldSelectedHour] = 0.0F
                fAddBr_unitDisplay.error = getString(R.string.input_basalunit_to_low)
            } else {
                vMdl.upldBasalProfile[vMdl.upldSelectedHour]-= 0.010F
                fAddBr_unitDisplay.error = null
                fAddBr_unitDisplay.isErrorEnabled = false
            }
            setUnit()
        }

        fAddBr_btn_plusOne.setOnClickListener {
            if (vMdl.upldBasalProfile[vMdl.upldSelectedHour] > 3F) {
                vMdl.upldBasalProfile[vMdl.upldSelectedHour] = 4F
                fAddBr_unitDisplay.error = getString(R.string.input_basalunit_to_high)
            } else {
                vMdl.upldBasalProfile[vMdl.upldSelectedHour]+=1.0F
                fAddBr_unitDisplay.error = null
                fAddBr_unitDisplay.isErrorEnabled = false
            }
            setUnit()
        }

        fAddBr_btn_plusPointOne.setOnClickListener {
            if (vMdl.upldBasalProfile[vMdl.upldSelectedHour] > 3.9F) {
                vMdl.upldBasalProfile[vMdl.upldSelectedHour] = 4F
                fAddBr_unitDisplay.error = getString(R.string.input_basalunit_to_high)
            } else {
                vMdl.upldBasalProfile[vMdl.upldSelectedHour]+=0.10F
                fAddBr_unitDisplay.error = null
                fAddBr_unitDisplay.isErrorEnabled = false
            }
            setUnit()
        }

        fAddBr_btn_plusPointZeroOne.setOnClickListener {
            if (vMdl.upldBasalProfile[vMdl.upldSelectedHour] > 3.99F) {
                vMdl.upldBasalProfile[vMdl.upldSelectedHour] = 4F
                fAddBr_unitDisplay.error = getString(R.string.input_basalunit_to_high)
            } else {
                vMdl.upldBasalProfile[vMdl.upldSelectedHour]+=0.010F
                fAddBr_unitDisplay.error = null
                fAddBr_unitDisplay.isErrorEnabled = false
            }
            setUnit()
        }

        fAddBr_btn_saveProfile.setOnClickListener {
            vMdl.uploadProfile(isRdyToSave())
            Navigation.findNavController(it).navigate(FragmentAddBasalRateDirections.actionToNavigationProfil())
        }

    }

    private fun validateProfileName(): Boolean {
        val input = fAddBr_profileNameET.editText!!.text.toString().trim()

        return if (input.isEmpty()) {
            fAddBr_profileNameET.error = getString(R.string.error_profilename)
            false
        } else {
            fAddBr_profileNameET.error = null
            fAddBr_profileNameET.isErrorEnabled = false
            true
        }
    }

    /*method to set selected time, when selected time is 23:00 next selected time will be 00:00,
    * when selected time is 00:00 previous selected time will be 23:00
     */
    private fun setTime() {
        val selTime = vMdl.upldSelectedHour
        when {
            selTime > 23 -> {
                vMdl.upldSelectedHour = 0
                setDisplay(vMdl.upldSelectedHour)
            }
            selTime < 0 -> {
                vMdl.upldSelectedHour = 23
                setDisplay(vMdl.upldSelectedHour)
            }
            selTime in 0..23 -> {
                setDisplay(selTime)
            }
        }
    }

    private fun setDisplay(t: Int) {
        when (t) {
            in 0..8 ->      fAddBr_time.text = getString(R.string.fAddBr_time_low, t, t+1)
            9 ->            fAddBr_time.text = getString(R.string.fAddBr_time_nine, t, t+1)
            in 10..23 ->    fAddBr_time.text = getString(R.string.fAddBr_time_high, t, t+1)
        }
    }

    private fun nextTime() {
        vMdl.upldSelectedHour++
        setTime()
    }

    private fun prevTime() {
        vMdl.upldSelectedHour--
        setTime()
    }

    //displays the basalunit
    private fun setUnit() {
        fAddBr_unitTV.text = getString(R.string.number_to_string, vMdl.upldBasalProfile[vMdl.upldSelectedHour])
        checkEnableSaveBtn()
    }

    //this method copies the previous basalunit to the next if the next unit is empty (equal to 0.00) and the previous is not empty
    private fun setUnitForBtnNext() {
        val pos = vMdl.upldSelectedHour
        if(vMdl.upldBasalProfile[pos] == 0F) {
            if(pos == 0) {
                fAddBr_unitTV.text = getString(R.string.number_to_string, vMdl.upldBasalProfile[pos+23])
                vMdl.upldBasalProfile[pos]=vMdl.upldBasalProfile[pos+23]
            } else {
                fAddBr_unitTV.text = getString(R.string.number_to_string, vMdl.upldBasalProfile[pos-1])
                vMdl.upldBasalProfile[pos]=vMdl.upldBasalProfile[pos-1]
            }
        } else {
            fAddBr_unitTV.text = getString(R.string.number_to_string, vMdl.upldBasalProfile[pos])
        }
        checkEnableSaveBtn()
    }

    private fun checkEnableSaveBtn() {
        fAddBr_btn_saveProfile.isEnabled = isRdyToSave()
    }

    private fun isRdyToSave(): Boolean {
        val pNameInput = fAddBr_profileNameET.editText!!.text.toString().trim()
        if (pNameInput.isEmpty()) {
            return false
        } else {
            for (el in vMdl.upldBasalProfile) {
                if (el == 0F) {
                    return false
                }
            }
        }
        return true
    }

}
