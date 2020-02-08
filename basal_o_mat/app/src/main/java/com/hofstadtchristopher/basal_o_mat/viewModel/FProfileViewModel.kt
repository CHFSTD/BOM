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

    /*stores addBasalRate fragment input unitl finished and uploaded to the database
    * so user doesn't lose his input when moving to another fragment before finishing addBasalRate process
    */

    var tmpProfilName: String = ""

    //stores the current hour where the user sets the basal rate
    var selectedHour: Int = 0

    //stores input (0 <= input <= 4) for basalRate. position in array declare the hour, so [0] is 00:00 o'clock, [1] is 01:00 o'clock and so on.
    var tmpBasalProfile: Array<Float> = Array(24) {index -> 0F}

    init {
        val basalRateDao = BomDatabase.getDatabase(application, viewModelScope).basalRateDao()
        repository = BomRepository(basalRateDao)    //TODO add testDAO
        allBasalRates = repository.bRateList
    }

    fun uploadProfil(rdy: Boolean) {
        if (rdy) {
            val bRate = BasalRate(
                tmpBasalProfile[0],
                tmpBasalProfile[1],
                tmpBasalProfile[2],
                tmpBasalProfile[3],
                tmpBasalProfile[4],
                tmpBasalProfile[5],
                tmpBasalProfile[6],
                tmpBasalProfile[7],
                tmpBasalProfile[8],
                tmpBasalProfile[9],
                tmpBasalProfile[10],
                tmpBasalProfile[11],
                tmpBasalProfile[12],
                tmpBasalProfile[13],
                tmpBasalProfile[14],
                tmpBasalProfile[15],
                tmpBasalProfile[16],
                tmpBasalProfile[17],
                tmpBasalProfile[18],
                tmpBasalProfile[19],
                tmpBasalProfile[20],
                tmpBasalProfile[21],
                tmpBasalProfile[22],
                tmpBasalProfile[23],
                tmpProfilName
                )
            insert(bRate)
        }
    }

    fun insert(bRate: BasalRate) = viewModelScope.launch {
        repository.insert(bRate)
    }

    fun getAll() {
        allBasalRates = repository.getAll()
    }

    fun getBasalRate(id: Int): LiveData<List<BasalRate>> {
        return repository.getBasalRate(id)
    }

    fun update(bRate: BasalRate) = viewModelScope.launch {
        repository.update(bRate)
    }

    fun delete(id: Int) = viewModelScope.launch {
        repository.delete(id)
    }
}