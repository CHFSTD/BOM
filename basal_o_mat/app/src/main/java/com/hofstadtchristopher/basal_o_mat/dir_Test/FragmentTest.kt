package com.hofstadtchristopher.basal_o_mat.dir_Test

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.hofstadtchristopher.basal_o_mat.R
import com.hofstadtchristopher.basal_o_mat.viewModel.FTestViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_test.*
import java.util.*

class FragmentTest : Fragment() {

    private lateinit var vMdl: FTestViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vMdl = ViewModelProvider(activity!!).get(FTestViewModel::class.java)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        vMdl.allBasalRates.observe(viewLifecycleOwner, Observer { bRates ->
            bRates?.let {
                vMdl.bRateNames = it
            }
        })

        vMdl.setSize()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        togglDefault()
        if (vMdl.measuredData[vMdl.testProgress] == -1) {
            Navigation.findNavController(view).navigate(FragmentTestDirections.actionToFragmentBloodsugarInput())
        } else {
            checkTimerStart()
        }

        fTest_Btn_js.setOnClickListener {
            val choiceArr: Array<String> = vMdl.createSingleChoiceList()
            MaterialAlertDialogBuilder(context)
                .setTitle("test")
                .setSingleChoiceItems(choiceArr, -1){
                        _, i ->
                        vMdl.chosenBRatePos = i
                        vMdl.chosenBRate = vMdl.bRateNames[i]
                }
                .setPositiveButton(getString(R.string.btn_continue)) { _, _ ->
                    Navigation.findNavController(it).navigate(FragmentTestDirections.actionToFragmentBloodsugarInput())
                    vMdl.isTestMode = true
                    togglDefault()
                    vMdl.setDateAndTime()
                }
                .setNegativeButton(getString(R.string.cancel), null)
                .show()
        }

        fTest_Btn_scdl.setOnClickListener {
            //vMdl.createSingleChoiceList()
            Log.i("hourAtStart", "hourAtStart is ${vMdl.hourAtStart}")
        }

        fTest_Btn_test.setOnClickListener {
            startTimer()
        }
    }

    fun togglDefault() {
        if(vMdl.isTestMode) {
            fTest_layout_default.visibility = View.GONE
            fTest_layout_tMode.visibility = View.VISIBLE
        } else {
            fTest_layout_default.visibility = View.VISIBLE
            fTest_layout_tMode.visibility = View.GONE
        }

    }

    fun checkTimerStart() {
        if(vMdl.isTestMode && !vMdl.isTimerRunning) {
                vMdl.isTimerRunning = true
                startTimer()
        }
    }

    fun startTimer() {
        fTest_progressBar.max = (vMdl.START_TIME_IN_MILLIS / 1000).toInt()
        vMdl.timer = object: CountDownTimer(vMdl.START_TIME_IN_MILLIS, 1000) {
            override fun onFinish() {
                vMdl.isTimerRunning = false

                activity!!.findNavController(R.id.nav_host_fragment).navigate(FragmentTestDirections.actionToFragmentBloodsugarInput())

            }

            override fun onTick(millisUntilFinished: Long) {
                vMdl.timeLeftInSecs = millisUntilFinished / 1000
                updateCountDown()
            }

        }.start()
    }

    fun updateCountDown() {
        val timeLeftInMins = vMdl.timeLeftInSecs / 60
        val timeLeftInSecs = vMdl.timeLeftInSecs % 60

        fTest_tv_countdown.text = getString(R.string.countDown, timeLeftInMins, timeLeftInSecs)

        fTest_progressBar.progress = vMdl.timeLeftInSecs.toInt()
    }


}
