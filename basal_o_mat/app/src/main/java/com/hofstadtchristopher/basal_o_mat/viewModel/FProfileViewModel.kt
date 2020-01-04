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

    init {
        val basalRateDao = BomDatabase.getDatabase(application, viewModelScope).basalRateDao()
        repository = BomRepository(basalRateDao)    //TODO add testDAO
        allBasalRates = repository.bRateList
    }
}