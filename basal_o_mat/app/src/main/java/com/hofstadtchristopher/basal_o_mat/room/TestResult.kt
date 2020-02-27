package com.hofstadtchristopher.basal_o_mat.room

import android.graphics.Color
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.hofstadtchristopher.basal_o_mat.R

@Entity(tableName = "TestResults")
data class TestResult(
    @ColumnInfo(name = "Data1")
    var measData1 : Int,

    @ColumnInfo(name = "Data2")
    var measData2 : Int,

    @ColumnInfo(name = "Data3")
    var measData3 : Int,

    @ColumnInfo(name = "Data4")
    var measData4 : Int,

    @ColumnInfo(name = "Data5")
    var measData5 : Int,

    @ColumnInfo(name = "Data6")
    var measData6 : Int,

    @ColumnInfo(name = "Data7")
    var measData7 : Int,

    @ColumnInfo(name = "HourAtStart")
    var hourAtStart: Int,

    @ColumnInfo(name = "Date")
    var testDate: String,

    @Ignore
    var bRate: BasalRate,

    @ColumnInfo(name = "Profile")
    var testProfile: String = ""
    ) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    var id: Int = 0

    @Ignore
    val MAX_TEST_PROGRESS: Int = 6

    var termPos: Int = MAX_TEST_PROGRESS+1

    @Ignore
    val trends: Array<Int> = Array(MAX_TEST_PROGRESS+1) { R.drawable.ic_arrow_right_24px}

    @Ignore
    val trendColor: Array<Int> = Array(MAX_TEST_PROGRESS+1) { Color.WHITE}

    @Ignore
    var measuredData: Array<Int> = createMeasuredData()

    @Ignore
    var result: Array<Int> = createResult()

 //   init {
 //       measuredData = createMeasuredData()
 //       result = createResult()
 //   }

    @Ignore
    fun createMeasuredData(): Array<Int> {
        return arrayOf(
            measData1,
            measData2,
            measData3,
            measData4,
            measData5,
            measData6,
            measData7
        )
    }

    @Ignore
    fun createResult(): Array<Int> {
        //val measuredData: Array<Int> = createMesuredData()
        val testResult: Array<Int> = Array(MAX_TEST_PROGRESS) {6000}

        testResult.forEachIndexed {
                index, _ ->
            if (index >= termPos ) {
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
                    trends[index] = R.drawable.ic_thumb_up_black_24dp
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