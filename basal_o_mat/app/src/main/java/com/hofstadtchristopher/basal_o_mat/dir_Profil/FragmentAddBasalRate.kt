package com.hofstadtchristopher.basal_o_mat.dir_Profil

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider

import com.hofstadtchristopher.basal_o_mat.R
import com.hofstadtchristopher.basal_o_mat.viewModel.FProfileViewModel
import kotlinx.android.synthetic.main.fragment_add_basal_rate.*

/**
 * A simple [Fragment] subclass.
 */
class FragmentAddBasalRate : Fragment() {

    private lateinit var fProfileViewModel: FProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fProfileViewModel = ViewModelProvider(activity!!).get(FProfileViewModel::class.java)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_basal_rate, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fAddBr_time.text = getString(R.string.fAddBr_time_low, fProfileViewModel.selectedHour, fProfileViewModel.selectedHour+1)
        setUnit()

        fAddBr_profileNameET.editText!!.setOnFocusChangeListener { _, hasFocus ->
            if(!hasFocus) {
                if (validateProfileName()) {
                    fProfileViewModel.tmpProfilName = fAddBr_profileNameET.editText!!.text.toString().trim()
                    Toast.makeText(context, fProfileViewModel.tmpProfilName, Toast.LENGTH_LONG).show()
                    checkEnableSaveBtn()
                }
                //hide keyboard when editText lost focus
                val imm: InputMethodManager = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(fAddBr_profileNameET.windowToken, 0)
            }
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
            if (fProfileViewModel.tmpBasalProfile[fProfileViewModel.selectedHour] < 1F) {
                fProfileViewModel.tmpBasalProfile[fProfileViewModel.selectedHour] = 0F
                fAddBr_unitDisplay.error = getString(R.string.input_basalunit_to_low)
            } else {
                fProfileViewModel.tmpBasalProfile[fProfileViewModel.selectedHour]-= 1F
                fAddBr_unitDisplay.error = null
                fAddBr_unitDisplay.isErrorEnabled = false
            }
            setUnit()
        }

        fAddBr_btn_minusPointOne.setOnClickListener {
            if (fProfileViewModel.tmpBasalProfile[fProfileViewModel.selectedHour] < 0.10F) {
                fProfileViewModel.tmpBasalProfile[fProfileViewModel.selectedHour] = 0F
                fAddBr_unitDisplay.error = getString(R.string.input_basalunit_to_low)
            } else {
                fProfileViewModel.tmpBasalProfile[fProfileViewModel.selectedHour]-= 0.10F
                fAddBr_unitDisplay.error = null
                fAddBr_unitDisplay.isErrorEnabled = false
            }
            setUnit()
        }

        fAddBr_btn_minusPointZeroOne.setOnClickListener {
            if (fProfileViewModel.tmpBasalProfile[fProfileViewModel.selectedHour] < 0.01F) {
                fProfileViewModel.tmpBasalProfile[fProfileViewModel.selectedHour] = 0F
                fAddBr_unitDisplay.error = getString(R.string.input_basalunit_to_low)
            } else {
                fProfileViewModel.tmpBasalProfile[fProfileViewModel.selectedHour]-= 0.01F
                fAddBr_unitDisplay.error = null
                fAddBr_unitDisplay.isErrorEnabled = false
            }
            setUnit()
        }

        fAddBr_btn_plusOne.setOnClickListener {
            if (fProfileViewModel.tmpBasalProfile[fProfileViewModel.selectedHour] > 3F) {
                fProfileViewModel.tmpBasalProfile[fProfileViewModel.selectedHour] = 4F
                fAddBr_unitDisplay.error = getString(R.string.input_basalunit_to_high)
            } else {
                fProfileViewModel.tmpBasalProfile[fProfileViewModel.selectedHour]+=1F
                fAddBr_unitDisplay.error = null
                fAddBr_unitDisplay.isErrorEnabled = false
            }
            setUnit()
        }

        fAddBr_btn_plusPointOne.setOnClickListener {
            if (fProfileViewModel.tmpBasalProfile[fProfileViewModel.selectedHour] > 3.9F) {
                fProfileViewModel.tmpBasalProfile[fProfileViewModel.selectedHour] = 4F
                fAddBr_unitDisplay.error = getString(R.string.input_basalunit_to_high)
            } else {
                fProfileViewModel.tmpBasalProfile[fProfileViewModel.selectedHour]+=0.10F
                fAddBr_unitDisplay.error = null
                fAddBr_unitDisplay.isErrorEnabled = false
            }
            setUnit()
        }

        fAddBr_btn_plusPointZeroOne.setOnClickListener {
            if (fProfileViewModel.tmpBasalProfile[fProfileViewModel.selectedHour] > 3.99F) {
                fProfileViewModel.tmpBasalProfile[fProfileViewModel.selectedHour] = 4F
                fAddBr_unitDisplay.error = getString(R.string.input_basalunit_to_high)
            } else {
                fProfileViewModel.tmpBasalProfile[fProfileViewModel.selectedHour]+=0.01F
                fAddBr_unitDisplay.error = null
                fAddBr_unitDisplay.isErrorEnabled = false
            }
            setUnit()
        }

    }

    private fun validateProfileName(): Boolean {
        val input = fAddBr_profileNameET.editText!!.text.toString().trim()

        return if (input.isEmpty()) {
            fAddBr_profileNameET.error = "Bitte Profilname eingeben"
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
        val selTime = fProfileViewModel.selectedHour
        when {
            selTime > 23 -> {
                fProfileViewModel.selectedHour = 0
                setDisplay(fProfileViewModel.selectedHour)
            }
            selTime < 0 -> {
                fProfileViewModel.selectedHour = 23
                setDisplay(fProfileViewModel.selectedHour)
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
        fProfileViewModel.selectedHour++
        setTime()
    }

    private fun prevTime() {
        fProfileViewModel.selectedHour--
        setTime()
    }

    //displays the basalunit
    private fun setUnit() {
        fAddBr_unitTV.text = getString(R.string.number_to_string, fProfileViewModel.tmpBasalProfile[fProfileViewModel.selectedHour])
        checkEnableSaveBtn()
    }

    //this method copies the previous basalunit to the next if the next unit is empty (equal to 0.00) and the previous is not empty
    private fun setUnitForBtnNext() {
        val pos = fProfileViewModel.selectedHour
        if(fProfileViewModel.tmpBasalProfile[pos] == 0F) {
            if(pos == 0) {
                fAddBr_unitTV.text = getString(R.string.number_to_string, fProfileViewModel.tmpBasalProfile[pos+23])
                fProfileViewModel.tmpBasalProfile[pos]=fProfileViewModel.tmpBasalProfile[pos+23]
            } else {
                fAddBr_unitTV.text = getString(R.string.number_to_string, fProfileViewModel.tmpBasalProfile[pos-1])
                fProfileViewModel.tmpBasalProfile[pos]=fProfileViewModel.tmpBasalProfile[pos-1]
            }
        } else {
            fAddBr_unitTV.text = getString(R.string.number_to_string, fProfileViewModel.tmpBasalProfile[pos])
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
            for (v in fProfileViewModel.tmpBasalProfile) {
                if (v == 0F) {
                    return false
                }
            }
        }
        return true
    }

}
