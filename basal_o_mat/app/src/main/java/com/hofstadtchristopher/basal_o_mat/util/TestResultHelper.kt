package com.hofstadtchristopher.basal_o_mat.util

import android.graphics.Color
import com.hofstadtchristopher.basal_o_mat.R
import com.hofstadtchristopher.basal_o_mat.room.TestResult

class TestResultHelper(testResult: TestResult) {
    val MAX_TEST_PROGRESS: Int = testResult.MAX_TEST_PROGRESS

    val tResult = testResult

    val trends: Array<Int> = Array(MAX_TEST_PROGRESS+1) { R.drawable.ic_arrow_right_24px}

    val trendColor: Array<Int> = Array(MAX_TEST_PROGRESS+1) { Color.WHITE}

    var measuredData: Array<Int> = createMeasuredData()//Array(MAX_TEST_PROGRESS+1) {0}

    var resultFlow: Array<Int> = createResult()//Array(MAX_TEST_PROGRESS) {"UNINITIALIZED"}



    fun createMeasuredData(): Array<Int> {
        return arrayOf(
            tResult.measData1,
            tResult.measData2,
            tResult.measData3,
            tResult.measData4,
            tResult.measData5,
            tResult.measData6,
            tResult.measData7
        )
    }


    fun createResult(): Array<Int> {
        val testResult: Array<Int> = Array(MAX_TEST_PROGRESS) {6000}

        testResult.forEachIndexed {
                index, _ ->
            if (index >= tResult.termPos ) {
                testResult[index] = 5000
            } else {
                testResult[index] = measuredData[index+1] - measuredData[index]
            }
        }

        testResult.forEachIndexed{
                index, el ->
            when {
                el == 5000 -> {
                    trends[index] = R.drawable.ic_block_black_24dp
                    trendColor[index] = Color.WHITE
                }
                el <= -26 -> {
                    trends[index] = R.drawable.ic_arrow_down_24px
                    trendColor[index] = R.drawable.trend_bad
                }
                el in -25..-16 -> {
                    trends[index] = R.drawable.ic_arrow_diagonal_down_24px
                    trendColor[index] = R.drawable.trend_not_so_good
                }
                el in -15..15 -> {
                    trends[index] = R.drawable.ic_arrow_right_24px
                    trendColor[index] = R.drawable.trend_good
                }
                el in 16..25 -> {
                    trends[index] = R.drawable.ic_arrow_diagonal_up_24px
                    trendColor[index] = R.drawable.trend_not_so_good
                }
                el in 26..4999 -> {
                    trends[index] = R.drawable.ic_arrow_up_24px
                    trendColor[index] = R.drawable.trend_bad
                }
                else -> {
                    trends[index] = R.drawable.ic_assignment_black_24dp
                    trendColor[index] = Color.WHITE
                }
            }
        }
        return testResult
    }
}