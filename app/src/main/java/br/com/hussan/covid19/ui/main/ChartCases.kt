package br.com.hussan.covid19.ui.main

import android.content.Context
import androidx.core.content.ContextCompat
import br.com.hussan.covid19.R
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.component1
import kotlin.collections.component2

class ChartCases(val context: Context) {

    fun drawChart(
        dataChart: LinkedHashMap<String, Integer>,
        chart: LineChart
    ) {
        val entriesMin = ArrayList<Entry>()
        var i = 0
        dataChart.forEach { (index, cases) ->
            entriesMin.add(Entry(i.toFloat(), cases.toFloat()))
            i++
        }
        val dataSetMin = LineDataSet(entriesMin, "").apply {
            color = ContextCompat.getColor(context, R.color.colorPrimary)
            setCircleColor(color)
            setDrawValues(false)
        }
        val lineData = LineData(listOf(dataSetMin))
        chart.apply {
            data = lineData
            description = Description().apply { text = "" }
            axisLeft.apply {
                textSize = 12f
            }
            axisRight.apply {
                isEnabled = false
            }
            xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                labelRotationAngle = -25f
                textSize = 12f
                valueFormatter = object : ValueFormatter() {
                    override fun getAxisLabel(value: Float, axis: AxisBase?): String {
                        return try {
                            dataChart.keys.toTypedArray()[value.toInt()]
                        } catch (e: ArrayIndexOutOfBoundsException) {
                            ""
                        }
                    }
                }
            }
            invalidate()
        }

    }

}
