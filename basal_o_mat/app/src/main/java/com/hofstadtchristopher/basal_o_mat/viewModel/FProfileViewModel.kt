package com.hofstadtchristopher.basal_o_mat.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.hofstadtchristopher.basal_o_mat.room.BasalRate
import com.hofstadtchristopher.basal_o_mat.room.BomDatabase
import com.hofstadtchristopher.basal_o_mat.room.BomRepository
import kotlinx.coroutines.launch

class FProfileViewModel(application: Application) : AndroidViewModel(application) {
    // The ViewModel maintains a reference to the repository to get data.
    private val repository: BomRepository


    var allBasalRates: LiveData<List<BasalRate>>

    /* following variables will be for the addBasalRate fragment.
    * addBasalRate fragment input will be stored unitl finished and uploaded to the database
    * so user doesn't lose his input when moving to another fragment before finishing addBasalRate process
    */

    var upldProfilName: String = ""

    //stores the current hour where the user sets the basal rate
    var upldSelectedHour: Int = 0

    //stores input (0 <= input <= 4) for basalRate. position in array declare the hour, so [0] is 00:00 o'clock, [1] is 01:00 o'clock and so on.
    var upldBasalProfile: Array<Double> = Array(24) {0.0} //initialise every value with 0F

    /*
    * following variables will be for the updateBasalRate fragment.
    */

    lateinit var modProfilname: String

    var modSelectedHour: Int = 0

    lateinit var modBasalProfile: Array<Double>

    lateinit var modBasalRate: BasalRate

    //caches the basalrate position in allBasalRates list which will be modified in updateBasalRate fragment
    var bRatePos: Int? = null

    init {
        val basalRateDao = BomDatabase.getDatabase(application, viewModelScope).basalRateDao()
        val basalRateTestResultDao = BomDatabase.getDatabase(application, viewModelScope).testResultDao()
        repository = BomRepository(basalRateDao, basalRateTestResultDao)
        allBasalRates = repository.bRateList
    }

    private fun resetTmp() {
        upldProfilName = ""
        upldSelectedHour = 0
        upldBasalProfile.forEachIndexed { index, _ -> upldBasalProfile[index] = 0.0 }
    }

    //uploads basalrate profile to database
    fun uploadProfile(rdy: Boolean) {
        if (rdy) {
            val bRate = BasalRate(
                upldBasalProfile[0],
                upldBasalProfile[1],
                upldBasalProfile[2],
                upldBasalProfile[3],
                upldBasalProfile[4],
                upldBasalProfile[5],
                upldBasalProfile[6],
                upldBasalProfile[7],
                upldBasalProfile[8],
                upldBasalProfile[9],
                upldBasalProfile[10],
                upldBasalProfile[11],
                upldBasalProfile[12],
                upldBasalProfile[13],
                upldBasalProfile[14],
                upldBasalProfile[15],
                upldBasalProfile[16],
                upldBasalProfile[17],
                upldBasalProfile[18],
                upldBasalProfile[19],
                upldBasalProfile[20],
                upldBasalProfile[21],
                upldBasalProfile[22],
                upldBasalProfile[23],
                upldProfilName
                )
            insert(bRate)
            resetTmp()
        }
    }

    fun initProfToBeModified() {
        modBasalRate = allBasalRates.value!![bRatePos!!]

        modBasalProfile = arrayOf(
            modBasalRate.rate00,
            modBasalRate.rate01,
            modBasalRate.rate02,
            modBasalRate.rate03,
            modBasalRate.rate04,
            modBasalRate.rate05,
            modBasalRate.rate06,
            modBasalRate.rate07,
            modBasalRate.rate08,
            modBasalRate.rate09,
            modBasalRate.rate10,
            modBasalRate.rate11,
            modBasalRate.rate12,
            modBasalRate.rate13,
            modBasalRate.rate14,
            modBasalRate.rate15,
            modBasalRate.rate16,
            modBasalRate.rate17,
            modBasalRate.rate18,
            modBasalRate.rate19,
            modBasalRate.rate20,
            modBasalRate.rate21,
            modBasalRate.rate22,
            modBasalRate.rate23
        )

        modProfilname = modBasalRate.name
    }

    private fun refreshAllBasalRates() {
        if (allBasalRates != repository.getAllBRates()) {
            allBasalRates = repository.getAllBRates()
        }
    }

    //update cached bRate to update it on database
    private fun updateBasalRate() {
        modBasalProfile.forEachIndexed { index, value -> modBasalRate.setRate(index, value) }
    }

    private fun insert(bRate: BasalRate) = viewModelScope.launch {
        repository.insert(bRate)
    }

    fun getAll() {
        allBasalRates = repository.getAllBRates()
    }

    fun getBasalRate(id: Int): LiveData<List<BasalRate>> {
        return repository.getBasalRate(id)
    }

    fun update(bRate: BasalRate) = viewModelScope.launch {
        updateBasalRate()
        repository.update(bRate)
        modSelectedHour = 0 //reset selected Hour
    }

    fun delete(id: Int) = viewModelScope.launch {
        repository.deleteBRate(id)
    }
}