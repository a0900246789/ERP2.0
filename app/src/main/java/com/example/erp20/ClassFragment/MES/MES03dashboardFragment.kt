package com.example.erp20.ClassFragment.MES

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.erp20.Model.*
import com.example.erp20.R
import com.example.erp20.RecyclerAdapter.*
import com.example.erp20.cookie_data
import com.github.aachartmodel.aainfographics.aachartcreator.*
import com.github.aachartmodel.aainfographics.aaoptionsmodel.AAScrollablePlotArea
import com.github.aachartmodel.aainfographics.aaoptionsmodel.AASeries
import com.google.android.material.textfield.TextInputEditText
import java.util.*

class MES03dashboardFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_mes03dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    override fun onResume() {
        super.onResume()


        //combobox選單內容
        val autoCompleteTextView=getView()?.findViewById<AutoCompleteTextView>(R.id.autoCompleteText)
        val combobox=resources.getStringArray(R.array.comboboxMES03dashboard)
        val arrayAdapter= ArrayAdapter(requireContext(),R.layout.combobox_item,combobox)
        autoCompleteTextView?.setAdapter(arrayAdapter)

        val searchbtn=getView()?.findViewById<Button>(R.id.search_btn)
        val aaChartView = view?.findViewById<AAChartView>(R.id.aa_chart_view)!!


        //搜尋按鈕
        searchbtn?.setOnClickListener {


            when(autoCompleteTextView?.text.toString()){
                "生產線生產效率分析"->{
                    val aaChartModel = AAChartModel()
                        .chartType(AAChartType.Column)
                        .title("title")
                        .subtitle("subtitle")
                        .backgroundColor("#4b2b7f")
                        .dataLabelsEnabled(true)
                        .zoomType(AAChartZoomType.X)
                        .series(arrayOf(
                            AASeriesElement()
                                .name("Tokyo")
                                .data(arrayOf(7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6, 23.3, 18.3, 13.9, 9.6, 23.3, 18.3, 13.9, 9.6, 23.3, 18.3, 13.9, 9.6)),
                            AASeriesElement()
                                .name("NewYork")
                                .data(arrayOf(0.2, 0.8, 5.7, 11.3, 17.0, 22.0, 24.8, 24.1, 20.1, 14.1, 8.6, 2.5)),
                            AASeriesElement()
                                .name("London")
                                .data(arrayOf(0.9, 0.6, 3.5, 8.4, 13.5, 17.0, 18.6, 17.9, 14.3, 9.0, 3.9, 1.0)),
                            AASeriesElement()
                                .name("Berlin")
                                .data(arrayOf(3.9, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0, 16.6, 14.2, 10.3, 6.6, 4.8))
                        )
                        )

                    aaChartView.aa_drawChartWithChartModel(aaChartModel)
                }
                "產能負荷分析"->{
                    val aaChartModel = AAChartModel()
                        .chartType(AAChartType.Area)
                        .title("title")
                        .subtitle("123456")
                        .backgroundColor("#4b2b7f")
                        .dataLabelsEnabled(true)
                        .categories(arrayOf("123","455"))
                        .zoomType(AAChartZoomType.X)
                        //.scrollablePlotArea(AAScrollablePlotArea())
                        .series(arrayOf(
                            AASeriesElement()
                                .name("Tokyo")
                                .data(arrayOf(7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6, 23.3, 18.3, 13.9, 9.6, 23.3, 18.3, 13.9, 9.6, 23.3, 18.3, 13.9, 9.6)),
                            AASeriesElement()
                                .name("NewYork")
                                .data(arrayOf(0.2, 0.8, 5.7, 11.3, 17.0, 22.0, 24.8, 24.1, 20.1, 14.1, 8.6, 2.5)),
                            AASeriesElement()
                                .name("London")
                                .data(arrayOf(0.9, 0.6, 3.5, 8.4, 13.5, 17.0, 18.6, 17.9, 14.3, 9.0, 3.9, 1.0)),
                            AASeriesElement()
                                .name("Berlin")
                                .data(arrayOf(3.9, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0, 16.6, 14.2, 10.3, 6.6, 4.8))
                        )
                        )

                    aaChartView.aa_drawChartWithChartModel(aaChartModel)
                }


            }
        }


    }
    

}