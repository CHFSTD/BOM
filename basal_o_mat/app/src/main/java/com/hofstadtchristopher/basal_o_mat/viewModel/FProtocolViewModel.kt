package com.hofstadtchristopher.basal_o_mat.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hofstadtchristopher.basal_o_mat.recyclerView.ListItem
import com.hofstadtchristopher.basal_o_mat.room.BomDatabase
import com.hofstadtchristopher.basal_o_mat.room.BomRepository
import com.hofstadtchristopher.basal_o_mat.room.TestResult
import kotlinx.coroutines.launch

class FProtocolViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: BomRepository

    var allTestResults: LiveData<List<TestResult>>

    //caches the test result position in allResults list which will be used to navigate to the right test result for FragmentProtocolTestresult
    var tResultPos: Int? = null

    lateinit var selectedTestResult: TestResult

    init {
        val basalRateDao = BomDatabase.getDatabase(application, viewModelScope).basalRateDao()
        val basalRateTestResultDao = BomDatabase.getDatabase(application, viewModelScope).testResultDao()
        repository = BomRepository(basalRateDao, basalRateTestResultDao)
        allTestResults = repository.getTestResults()
    }

    fun initToShowTestResult() {
        selectedTestResult = allTestResults.value!![tResultPos!!]
    }

    fun delete(id: Int) = viewModelScope.launch {
        repository.deleteTResult(id)
    }

}