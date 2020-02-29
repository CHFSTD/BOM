package com.hofstadtchristopher.basal_o_mat.viewModel

import android.app.Application
import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.*
import com.hofstadtchristopher.basal_o_mat.room.BasalRate
import com.hofstadtchristopher.basal_o_mat.room.TestResult
import com.hofstadtchristopher.basal_o_mat.room.BomDatabase
import com.hofstadtchristopher.basal_o_mat.room.BomRepository
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.util.*


class FTestViewModel(application: Application) : AndroidViewModel(application) {
    val MAX_TEST_PROGRESS: Int = 6
    val START_TIME_IN_MILLIS: Long = 3000 //Demo modus = 3000, Normal is 3600000 for 1 Hour

    private val repository: BomRepository
    var allBasalRates: LiveData<List<BasalRate>>

    var bRateNames = emptyList<BasalRate>()
    var size = 0
    lateinit var chosenBRate: BasalRate
    var chosenBRatePos: Int = 0


    var isTestMode: Boolean = false
    var measuredData: Array<Int> = Array(MAX_TEST_PROGRESS+1) {0}
    var testProgress: Int = 0
    var testResult: Array<Any> = Array(MAX_TEST_PROGRESS) {"UNINITIALIZED"}
    var termPos: Int = MAX_TEST_PROGRESS+1
    var hourAtStart: Int = 0
    var testDate: String = ""
    lateinit var res: TestResult

    lateinit var timer: CountDownTimer
    var timeLeftInMillis: Long = START_TIME_IN_MILLIS
    var isTimerRunning: Boolean = false
    var timeLeftInSecs: Long = 0


    init {
        val basalRateDao = BomDatabase.getDatabase(application, viewModelScope).basalRateDao()
        val basalRateTestResultDao = BomDatabase.getDatabase(application, viewModelScope).testResultDao()
        repository = BomRepository(basalRateDao, basalRateTestResultDao)
        allBasalRates = repository.bRateList

    }

    fun setSize() = viewModelScope.launch {
        size = repository.getSize()

    }

    fun createResult() {
        res = TestResult(
            measuredData[0],
            measuredData[1],
            measuredData[2],
            measuredData[3],
            measuredData[4],
            measuredData[5],
            measuredData[6],
            hourAtStart,
            testDate,
            chosenBRate.name
            )
        res.setBasalRate(chosenBRate)
        insert(res)

    }

    fun setDateAndTime() {
        hourAtStart = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        testDate = DateFormat.getDateInstance().format(Calendar.getInstance().time)
    }

    fun createSingleChoiceList(): Array<String> {
        val singleChoiceList: ArrayList<String> = ArrayList()
        bRateNames.forEach {
                bRate -> singleChoiceList.add(bRate.name)
        }
        val res = Array(size) {""}
        singleChoiceList.toArray(res)
        return res
    }

    fun resetTest() {
        measuredData = Array(MAX_TEST_PROGRESS+1) {0}
        testProgress = 0
        testResult = Array(MAX_TEST_PROGRESS) {"UNINITIALIZED"}
        termPos = MAX_TEST_PROGRESS+1
        hourAtStart = 0
        testDate = ""
    }

    fun isFinished(): Boolean {
        val res: Boolean
        if(testProgress >= MAX_TEST_PROGRESS) {
            testProgress = 0
            createResult()
            res = true
        } else {
            res = false
        }
        return res
    }

    private fun insert(tResult: TestResult) = viewModelScope.launch {
        repository.insert(tResult)
    }

    fun update(bRate: BasalRate) = viewModelScope.launch {
        repository.update(bRate)
    }
}