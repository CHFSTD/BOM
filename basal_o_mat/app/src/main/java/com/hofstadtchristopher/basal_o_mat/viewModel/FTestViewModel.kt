package com.hofstadtchristopher.basal_o_mat.viewModel

import android.app.Application
import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.*
import com.hofstadtchristopher.basal_o_mat.room.BasalRate
import com.hofstadtchristopher.basal_o_mat.room.BomDatabase
import com.hofstadtchristopher.basal_o_mat.room.BomRepository
import kotlinx.coroutines.launch
import java.util.*


class FTestViewModel(application: Application) : AndroidViewModel(application) {
    val MAX_TEST_PROGRESS: Int = 3
    val START_TIME_IN_MILLIS: Long = 6000

    private val repository: BomRepository
    var allBasalRates: LiveData<List<BasalRate>>

    var bRateNames = emptyList<BasalRate>()
    var size = 0
    lateinit var chosenBRate: BasalRate


    var isTestMode: Boolean = false
    var measuredData: Array<Int> = Array(MAX_TEST_PROGRESS+1) {0}
    var testProgress: Int = 0

    lateinit var timer: CountDownTimer
    var timeLeftInMillis: Long = START_TIME_IN_MILLIS
    var isTimerRunning: Boolean = false
    var timeLeftInSecs: Long = 0


    init {
        val basalRateDao = BomDatabase.getDatabase(application, viewModelScope).basalRateDao()
        repository = BomRepository(basalRateDao)    //TODO add testDAO
        allBasalRates = repository.bRateList

    }

    fun setSize() = viewModelScope.launch {
        size = repository.getSize()
        Log.i("Size", "Size is $size")

    }

    fun createSingleChoiceList(): Array<String> {
        val singleChoiceList: ArrayList<String> = ArrayList()
        bRateNames.forEach {
                bRate -> singleChoiceList.add(bRate.name)
                        Log.i("ArrayList", "add ${bRate.name}")
        }
        Log.i("Size", "Size before declaring array is $size")
        val res = Array(size) {""}
        Log.i("Size", "array size is ${res.size}")
        singleChoiceList.toArray(res)
        res.forEach { i -> Log.i("String", "Element is $i") }
        return res
    }

    fun isFinished(): Boolean {
        return testProgress > MAX_TEST_PROGRESS
    }
}