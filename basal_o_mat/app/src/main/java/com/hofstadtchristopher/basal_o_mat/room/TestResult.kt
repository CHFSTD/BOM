package com.hofstadtchristopher.basal_o_mat.room

import android.content.res.Resources
import android.graphics.Color
import android.util.Log
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.hofstadtchristopher.basal_o_mat.R
import java.util.*

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

    @ColumnInfo(name = "Profile")
    var testProfileName: String = ""
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
    var bRate: BasalRate = BasalRate(
        10.0,
        10.0,
        10.0,
        10.0,
        10.0,
        10.0,
        10.0,
        10.0,
        10.0,
        10.0,
        10.0,
        10.0,
        10.0,
        10.0,
        10.0,
        10.0,
        10.0,
        10.0,
        10.0,
        10.0,
        10.0,
        10.0,
        10.0,
        10.0,
        ""
    )

    @Ignore
    var adjustedRate: BasalRate = bRate

    @Ignore
    var result: Array<Int> = createResult()

    @Ignore
    var recommendation: String = generateRecommendation()

    //we have to save recommendation persistent to show it late, because room can't save columns with user made classes(BasalRate) persistent
    var savedRecommendation: String = ""

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
        val testResult: Array<Int> = Array(MAX_TEST_PROGRESS) {6000}

        testResult.forEachIndexed {
                index, _ ->
            if (index >= termPos ) {
                testResult[index] = 5000
            } else {
                testResult[index] = measuredData[index+1] - measuredData[index]
            }
        }

        testResult.forEachIndexed { index, el ->
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
                    trends[index] = R.drawable.shape_rectangle
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
                    trends[index] = R.drawable.shape_rectangle
                    trendColor[index] = Color.WHITE
                }
            }
        }
        return testResult
    }

    //returns the time where we will adjust the basal unit, in dependence of the given time
    fun getTimeForAdjustment(startHour: Int): Int {
        return when {
            startHour == 4 -> {
                0
            }
            startHour < 4 -> {
                (24 + startHour ) -4
            }
            else -> {
                startHour -4
            }
        }
    }

    fun generateRecommendation(): String {
        var rec: String = ""
        var tmp: Double
        var tmpTime: Int
        result.forEachIndexed{ index, el ->
                //var rateFirst: Double = bRate.getRate(hourAtStart + index-4)
                //var rateSecond: Double = bRate.getRate(hourAtStart + index-4)
                when {
                    el <= -26 -> {
                        //down much
                        tmpTime = getTimeForAdjustment(hourAtStart + 1 + index)
                        tmp = bRate.getRate(tmpTime) - 0.2
                        adjustedRate.setRate(tmpTime, tmp)
                        val brt = bRate.getRate(tmpTime)
                        Log.i("Result", "bRate is $brt and tmp is $tmp")
                        rec += if (Locale.getDefault().isO3Language == "deu"){
                            String.format("Basalwert von %.2f um %02d:00 Uhr auf %.2f ändern.\n", bRate.getRate(tmpTime), tmpTime, tmp)
                        } else {
                            String.format("Adjust basal unit at %02d:00 o'clock with value %.2f to %.2f.\n", tmpTime, bRate.getRate(tmpTime), tmp)
                        }
                    }
                    el in -25..-16 -> {
                        //down
                        tmpTime = getTimeForAdjustment(hourAtStart + 1 + index)
                        tmp = bRate.getRate(tmpTime) - 0.1
                        adjustedRate.setRate(tmpTime, tmp)
                        val brt = bRate.getRate(tmpTime)
                        Log.i("Result", "bRate is $brt and tmp is $tmp")
                        rec += if (Locale.getDefault().isO3Language == "deu"){
                            String.format("Basalwert von %.2f um %02d:00 Uhr auf %.2f ändern.\n", bRate.getRate(tmpTime), tmpTime, tmp)
                        } else {
                            String.format("Adjust basal unit at %02d:00 o'clock with value %.2f to %.2f.\n", tmpTime, bRate.getRate(tmpTime), tmp)
                        }
                    }
                    el in 16..25 -> {
                        //higher
                        tmpTime = getTimeForAdjustment(hourAtStart + 1 + index)
                        tmp = bRate.getRate(tmpTime) + 0.1
                        adjustedRate.setRate(tmpTime, tmp)
                        val brt = bRate.getRate(tmpTime)
                        Log.i("Result", "bRate is $brt and tmp is $tmp")
                        rec += if (Locale.getDefault().isO3Language == "deu"){
                            String.format("Basalwert von %.2f um %02d:00 Uhr auf %.2f ändern.\n", bRate.getRate(tmpTime), tmpTime, tmp)
                        } else {
                            String.format("Adjust basal unit at %02d:00 o'clock with value %f.2 to %f.2.\n", tmpTime, bRate.getRate(tmpTime), tmp)
                        }
                    }
                    el in 26..4999 -> {
                        //higher much
                        tmpTime = getTimeForAdjustment(hourAtStart + 1 + index)
                        tmp = bRate.getRate(tmpTime) + 0.2
                        adjustedRate.setRate(tmpTime, tmp)
                        val brt = bRate.getRate(tmpTime)
                        Log.i("Result", "bRate is $brt and tmp is $tmp")
                        rec += if (Locale.getDefault().isO3Language == "deu"){
                            String.format("Basalwert von %.2f um %02d:00 Uhr auf %.2f ändern.\n", bRate.getRate(tmpTime), tmpTime, tmp)
                        } else {
                            String.format("Adjust basal unit at %02d:00 o'clock with value %.2f to %.2f.\n", tmpTime, bRate.getRate(tmpTime), tmp)
                        }
                    }
                }
        }

        if (rec.isEmpty()){
            rec = if (Locale.getDefault().isO3Language == "deu") {
                "Keine Korrektur nötig, Basalrate ist OK"
            } else {
                "No need for adjustments, Basalrate is OK"
            }
        }

        savedRecommendation = rec
        return rec
    }

    fun setBasalRate(basalRate: BasalRate) {
        bRate = basalRate
        adjustedRate = basalRate
        recommendation = generateRecommendation()
    }
}

