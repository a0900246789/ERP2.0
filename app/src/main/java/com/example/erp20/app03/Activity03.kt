package com.example.erp20.app03

import android.app.DatePickerDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.erp20.Model.*
import com.example.erp20.R
import com.example.erp20.RecyclerAdapter.RecyclerItemPeopleWorkAdapterr
import com.example.erp20.RecyclerAdapter.RecyclerItemProductControlOrderBodyACardWorkAdapter
import com.example.erp20.RecyclerAdapter.RecyclerItemProductControlOrderBodyADetailAdapter
import com.example.erp20.RecyclerAdapter.RecyclerItemProductControlOrderBodyDAdapter
import com.example.erp20.cookie_data
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.FormBody
import okhttp3.Request
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class Activity03 : AppCompatActivity() {
    private lateinit var ProductControlOrderBody_A_Detail_adapter: RecyclerItemProductControlOrderBodyADetailAdapter
    private lateinit var ProductControlOrderBody_A_CardWork_adapter: RecyclerItemProductControlOrderBodyACardWorkAdapter
    private lateinit var PeopleWork_adapter: RecyclerItemPeopleWorkAdapterr
    var comboboxData: MutableList<String> = mutableListOf<String>()
    lateinit var selectFilter:String
    lateinit var selectFilter2:String
    lateinit var selectFilter3:String
    lateinit var selectFilter4:String
    lateinit var recyclerView:RecyclerView
    val dateF2 = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    val dateF = SimpleDateFormat("yyyy-MM-dd(EEEE)", Locale.TAIWAN)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_03)
    }

    override fun onResume() {
        super.onResume()


        val recyclerView=findViewById<RecyclerView>(R.id.recyclerView)
        //combobox選單內容
        val autoCompleteTextView=findViewById<AutoCompleteTextView>(R.id.autoCompleteText)
        //val combobox=resources.getStringArray(R.array.comboboxSale01change)
        //val arrayAdapter= ArrayAdapter(this,R.layout.combobox_item,combobox)
        //autoCompleteTextView?.setAdapter(arrayAdapter)
        val autoCompleteTextView2=findViewById<AutoCompleteTextView>(R.id.autoCompleteText2)
        val combobox2=resources.getStringArray(R.array.combobox03_cardwork)
        val arrayAdapter2= ArrayAdapter(this,R.layout.combobox_item,combobox2)
        autoCompleteTextView2.setAdapter(arrayAdapter2)
        autoCompleteTextView2.setOnItemClickListener { parent, view, position, id ->
            cookie_data.cardwork=autoCompleteTextView2.text.toString()
        }

        val cardwork_btn=findViewById<Button>(R.id.cardwork_btn)
        //刷卡
        cardwork_btn?.setOnClickListener{
            //show_combobox("StockTransOrderBody","condition","False",StockTransOrderBody())
            //show_relative_combobox("ItemBasicInfo","all","False", ItemBasicInfo())
           // show_relative_combobox("VenderBasicInfo","all","False", VenderBasicInfo())
            show_relative_combobox("PLineBasicInfo","all","False", PLineBasicInfo())
            show_relative_combobox("ItemBasicInfo","all","False", ItemBasicInfo())
            show_relative_combobox("ProductControlOrderBody_A","condition","False", ProductControlOrderBody_A())
            val item = LayoutInflater.from(this).inflate(R.layout.filter_combobox5, null)
            val mAlertDialog = AlertDialog.Builder(this)
            //mAlertDialog.setIcon(R.mipmap.ic_launcher_round) //set alertdialog icon
            mAlertDialog.setTitle("篩選") //set alertdialog title
            //mAlertDialog.setMessage("確定要登出?") //set alertdialog message
            mAlertDialog.setView(item)
            //filter_combobox選單內容
            val comboboxView=item.findViewById<AutoCompleteTextView>(R.id.autoCompleteText)//預計開工日期
            comboboxView.inputType=InputType.TYPE_NULL
            val comboboxView2=item.findViewById<AutoCompleteTextView>(R.id.autoCompleteText2)//生產線
            val comboboxView3=item.findViewById<AutoCompleteTextView>(R.id.autoCompleteText3)//料件
            val comboboxView4=item.findViewById<AutoCompleteTextView>(R.id.autoCompleteText4)//生產管制單編號


            var C= Calendar.getInstance()
            //C.set(Calendar.DAY_OF_MONTH,1)
            var Date= dateF.format(C.time)
            var prod_ctrl_order_number_detail_total:MutableList<String> = mutableListOf<String>()
            var prod_ctrl_order_number_detail_total_index:MutableList<String> = mutableListOf<String>()
            for(i in 0 until cookie_data.ProductControlOrderBody_A_prod_ctrl_order_number_ComboboxData.size){
                if(cookie_data.ProductControlOrderBody_A_est_start_date_ComboboxData[i]!=null){
                    var date1=dateF2.parse(cookie_data.ProductControlOrderBody_A_est_start_date_ComboboxData[i])
                    var date2=dateF2.parse(Date)
                    if(date1.compareTo(date2)>=0){
                        prod_ctrl_order_number_detail_total.add(cookie_data.ProductControlOrderBody_A_prod_ctrl_order_number_ComboboxData[i]+"\n"+
                                cookie_data.ProductControlOrderBody_A_semi_finished_prod_number_ComboboxData[i]+"\n"+"(預開工)"+
                                cookie_data.ProductControlOrderBody_A_est_start_date_ComboboxData[i]+"*(預計量)"+
                                cookie_data.ProductControlOrderBody_A_est_output_ComboboxData[i]
                        )
                        prod_ctrl_order_number_detail_total_index.add(i.toString())
                    }
                }
            }
            comboboxView.setText(Date)
            comboboxView.setOnClickListener {
                prod_ctrl_order_number_detail_total.removeAll(prod_ctrl_order_number_detail_total)
                var c= Calendar.getInstance()
                val year= c.get(Calendar.YEAR)
                val month = c.get(Calendar.MONTH)
                val day = c.get(Calendar.DAY_OF_MONTH)
                var datePicker = DatePickerDialog(item.context,
                    DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                        val SelectedDate= Calendar.getInstance()
                        SelectedDate.set(Calendar.YEAR,mYear)
                        SelectedDate.set(Calendar.MONTH,mMonth)
                        SelectedDate.set(Calendar.DAY_OF_MONTH,mDay)
                        val Date1= dateF.format(SelectedDate.time)
                        comboboxView.setText(Date1)

                        for(i in 0 until cookie_data.ProductControlOrderBody_A_prod_ctrl_order_number_ComboboxData.size){
                            if(cookie_data.ProductControlOrderBody_A_est_start_date_ComboboxData[i]!=null){
                                var date1=dateF2.parse(cookie_data.ProductControlOrderBody_A_est_start_date_ComboboxData[i])
                                var date2=dateF2.parse(Date1)
                                if(date1.compareTo(date2)>=0){
                                    prod_ctrl_order_number_detail_total.add(cookie_data.ProductControlOrderBody_A_prod_ctrl_order_number_ComboboxData[i]+"\n"+
                                            cookie_data.ProductControlOrderBody_A_semi_finished_prod_number_ComboboxData[i]+"\n"+"(預開工)"+
                                            cookie_data.ProductControlOrderBody_A_est_start_date_ComboboxData[i]+"*(預計量)"+
                                            cookie_data.ProductControlOrderBody_A_est_output_ComboboxData[i]
                                    )
                                    prod_ctrl_order_number_detail_total_index.add(i.toString())
                                }
                            }


                        }

                    },year,month,day).show()
            }
            var pline_id=cookie_data.ProductControlOrderBody_A_pline_id_ComboboxData.distinct()
            var pline_id_name:MutableList<String> = mutableListOf<String>()
            for(i in 0 until pline_id.size){
                if(cookie_data.pline_id_ComboboxData.indexOf(pline_id[i])!=-1){
                    pline_id_name.add(pline_id[i]+" "+cookie_data.pline_name_ComboboxData[cookie_data.pline_id_ComboboxData.indexOf(pline_id[i])])
                }
                else{
                    pline_id_name.add(pline_id[i]+" ")
                }
            }
            var semi_finished_prod_number=cookie_data.ProductControlOrderBody_A_semi_finished_prod_number_ComboboxData.distinct()
            var semi_finished_prod_number_name:MutableList<String> = mutableListOf<String>()
            for(i in 0 until semi_finished_prod_number.size){
                if(cookie_data.item_id_ComboboxData.indexOf(semi_finished_prod_number[i])!=-1){
                    semi_finished_prod_number_name.add(semi_finished_prod_number[i]+"\n"+cookie_data.item_name_ComboboxData[cookie_data.item_id_ComboboxData.indexOf(semi_finished_prod_number[i])])
                }
                else{
                    semi_finished_prod_number_name.add(semi_finished_prod_number[i]+"\n")
                }
            }
            val arrayAdapter2= ArrayAdapter(this,R.layout.combobox_item,pline_id_name)
            val arrayAdapter3= ArrayAdapter(this,R.layout.combobox_item,semi_finished_prod_number_name)
            var arrayAdapter4= ArrayAdapter(this,R.layout.combobox_item,prod_ctrl_order_number_detail_total )
            comboboxView2.setAdapter(arrayAdapter2)
            comboboxView3.setAdapter(arrayAdapter3)
            comboboxView4.setOnClickListener {
                if(comboboxView2.text.toString()=="" && comboboxView3.text.toString()=="")
                {
                    arrayAdapter4= ArrayAdapter(this,R.layout.combobox_item,prod_ctrl_order_number_detail_total )
                    println(prod_ctrl_order_number_detail_total)
                    for(i in 0 until prod_ctrl_order_number_detail_total_index.size){
                        println(cookie_data.ProductControlOrderBody_A_pline_id_ComboboxData[prod_ctrl_order_number_detail_total_index[i].toInt()])
                    }
                }
                else if(comboboxView2.text.toString()!="" && comboboxView3.text.toString()==""){
                    var prod_ctrl_order_number_detail_total_filter:MutableList<String> = mutableListOf<String>()
                    var prod_ctrl_order_number_detail_total_filter_index:MutableList<String> = mutableListOf<String>()
                    prod_ctrl_order_number_detail_total_filter_index.addAll(prod_ctrl_order_number_detail_total_index)
                    var pline_id=comboboxView2.text.toString().substring(0,comboboxView2.text.toString().indexOf(" "))
                    for(i in 0 until prod_ctrl_order_number_detail_total_index.size){
                        if(cookie_data.ProductControlOrderBody_A_pline_id_ComboboxData[prod_ctrl_order_number_detail_total_index[i].toInt()]!=pline_id){
                            prod_ctrl_order_number_detail_total_filter_index.remove(prod_ctrl_order_number_detail_total_index[i])
                        }
                    }
                    for(i in 0 until prod_ctrl_order_number_detail_total_filter_index.size){
                            prod_ctrl_order_number_detail_total_filter.add(cookie_data.ProductControlOrderBody_A_prod_ctrl_order_number_ComboboxData[prod_ctrl_order_number_detail_total_filter_index[i].toInt()]+"\n"+
                                    cookie_data.ProductControlOrderBody_A_semi_finished_prod_number_ComboboxData[prod_ctrl_order_number_detail_total_filter_index[i].toInt()]+"\n"+"(預開工)"+
                                    cookie_data.ProductControlOrderBody_A_est_start_date_ComboboxData[prod_ctrl_order_number_detail_total_filter_index[i].toInt()]+"*(預計量)"+
                                    cookie_data.ProductControlOrderBody_A_est_output_ComboboxData[prod_ctrl_order_number_detail_total_filter_index[i].toInt()]
                            )
                    }
                    arrayAdapter4= ArrayAdapter(this,R.layout.combobox_item,prod_ctrl_order_number_detail_total_filter )
                }
                else if(comboboxView2.text.toString()=="" && comboboxView3.text.toString()!=""){
                    var prod_ctrl_order_number_detail_total_filter:MutableList<String> = mutableListOf<String>()
                    var prod_ctrl_order_number_detail_total_filter_index:MutableList<String> = mutableListOf<String>()
                    prod_ctrl_order_number_detail_total_filter_index.addAll(prod_ctrl_order_number_detail_total_index)
                    var semi_finished_prod_number=comboboxView3.text.toString().substring(0,comboboxView3.text.toString().indexOf("\n"))
                    for(i in 0 until prod_ctrl_order_number_detail_total_index.size){
                        if(cookie_data.ProductControlOrderBody_A_semi_finished_prod_number_ComboboxData[prod_ctrl_order_number_detail_total_index[i].toInt()]!=semi_finished_prod_number){
                            prod_ctrl_order_number_detail_total_filter_index.remove(prod_ctrl_order_number_detail_total_index[i])
                        }
                    }
                    for(i in 0 until prod_ctrl_order_number_detail_total_filter_index.size){
                        prod_ctrl_order_number_detail_total_filter.add(cookie_data.ProductControlOrderBody_A_prod_ctrl_order_number_ComboboxData[prod_ctrl_order_number_detail_total_filter_index[i].toInt()]+"\n"+
                                cookie_data.ProductControlOrderBody_A_semi_finished_prod_number_ComboboxData[prod_ctrl_order_number_detail_total_filter_index[i].toInt()]+"\n"+"(預開工)"+
                                cookie_data.ProductControlOrderBody_A_est_start_date_ComboboxData[prod_ctrl_order_number_detail_total_filter_index[i].toInt()]+"*(預計量)"+
                                cookie_data.ProductControlOrderBody_A_est_output_ComboboxData[prod_ctrl_order_number_detail_total_filter_index[i].toInt()]
                        )
                    }
                    arrayAdapter4= ArrayAdapter(this,R.layout.combobox_item,prod_ctrl_order_number_detail_total_filter )

                }
                else if(comboboxView2.text.toString()!="" && comboboxView3.text.toString()!=""){
                    var prod_ctrl_order_number_detail_total_filter:MutableList<String> = mutableListOf<String>()
                    var prod_ctrl_order_number_detail_total_filter_index:MutableList<String> = mutableListOf<String>()
                    prod_ctrl_order_number_detail_total_filter_index.addAll(prod_ctrl_order_number_detail_total_index)
                    var semi_finished_prod_number=comboboxView3.text.toString().substring(0,comboboxView3.text.toString().indexOf("\n"))
                    var pline_id=comboboxView2.text.toString().substring(0,comboboxView2.text.toString().indexOf(" "))
                    for(i in 0 until prod_ctrl_order_number_detail_total_index.size){
                        if(cookie_data.ProductControlOrderBody_A_semi_finished_prod_number_ComboboxData[prod_ctrl_order_number_detail_total_index[i].toInt()]!=semi_finished_prod_number ||
                            cookie_data.ProductControlOrderBody_A_pline_id_ComboboxData[prod_ctrl_order_number_detail_total_index[i].toInt()]!=pline_id ){
                            prod_ctrl_order_number_detail_total_filter_index.remove(prod_ctrl_order_number_detail_total_index[i])
                        }
                    }
                    for(i in 0 until prod_ctrl_order_number_detail_total_filter_index.size){
                        prod_ctrl_order_number_detail_total_filter.add(cookie_data.ProductControlOrderBody_A_prod_ctrl_order_number_ComboboxData[prod_ctrl_order_number_detail_total_filter_index[i].toInt()]+"\n"+
                                cookie_data.ProductControlOrderBody_A_semi_finished_prod_number_ComboboxData[prod_ctrl_order_number_detail_total_filter_index[i].toInt()]+"\n"+"(預開工)"+
                                cookie_data.ProductControlOrderBody_A_est_start_date_ComboboxData[prod_ctrl_order_number_detail_total_filter_index[i].toInt()]+"*(預計量)"+
                                cookie_data.ProductControlOrderBody_A_est_output_ComboboxData[prod_ctrl_order_number_detail_total_filter_index[i].toInt()]
                        )
                    }
                    arrayAdapter4= ArrayAdapter(this,R.layout.combobox_item,prod_ctrl_order_number_detail_total_filter )
                }

                comboboxView4.setAdapter(arrayAdapter4)
            }
            mAlertDialog.setPositiveButton("取消") { dialog, id ->
                dialog.dismiss()
            }
            mAlertDialog.setNegativeButton("確定") { dialog, id ->
                if(comboboxView4.text.toString()!=""){
                    selectFilter4=comboboxView4.text.toString().substring(0,comboboxView4.text.toString().indexOf("\n"))//生產管制單號
                }
                else {
                    selectFilter4=""
                }
                show_relative_combobox("MeBody","all","False", MeBody())
                show_header_body_filter("ProductControlOrderBody_A","condition","False",selectFilter4)//type=all or condition
                when(cookie_data.status)
                {
                    0->{
                        Toast.makeText(this, "資料載入", Toast.LENGTH_SHORT).show()
                        recyclerView.layoutManager= LinearLayoutManager(this)//設定Linear格式
                        ProductControlOrderBody_A_CardWork_adapter= RecyclerItemProductControlOrderBodyACardWorkAdapter()
                        recyclerView.adapter=ProductControlOrderBody_A_CardWork_adapter//找對應itemAdapter
                        cookie_data.recyclerView=recyclerView
                        //addbtn?.isEnabled=true
                    }
                    1->{
                        Toast.makeText(this, cookie_data.msg, Toast.LENGTH_SHORT).show()
                    }
                }

            }
            mAlertDialog.show()
        }


        val searchbtn=findViewById<Button>(R.id.search_btn)
        val addbtn=findViewById<Button>(R.id.add_btn)
        //每次換選單內容add_btn失靈
        autoCompleteTextView?.setOnItemClickListener { parent, view, position, id ->
            addbtn?.isEnabled=false
        }
        //搜尋按鈕
        searchbtn?.setOnClickListener {
            when(autoCompleteTextView?.text.toString()){
                "(產線人員)人員每日工作事項(查詢)"->{
                    show_combobox("ProductControlOrderBody_B","all","False",ProductControlOrderBody_B())
                    //println(comboboxData)
                   /* val data = comboboxData.toMutableList()
                    comboboxData.removeAll(comboboxData)
                    for(i in 0 until data.size){
                        comboboxData.add( data[i]+" "+ cookie_data.pline_name_ComboboxData[cookie_data.pline_id_ComboboxData.indexOf(data[i])] )
                    }*/
                    var comboboxData_set=comboboxData.distinct()
                    //println(comboboxData_set)

                    //combobox show
                    val item = LayoutInflater.from(this).inflate(R.layout.filter_combobox2, null)
                    val mAlertDialog = AlertDialog.Builder(this)
                    mAlertDialog.setTitle("篩選") //set alertdialog title
                    mAlertDialog.setView(item)
                    //filter_combobox選單內容
                    val comboboxView=item.findViewById<AutoCompleteTextView>(R.id.autoCompleteText)
                    var C= Calendar.getInstance()
                    var Date= dateF.format(C.time)
                    comboboxView.setText(Date)
                    comboboxView.hint="上線日期(必填)"
                    comboboxView.inputType=InputType.TYPE_NULL
                    comboboxView.setOnClickListener {
                        var c= Calendar.getInstance()
                        val year= c.get(Calendar.YEAR)
                        val month = c.get(Calendar.MONTH)
                        val day = c.get(Calendar.DAY_OF_MONTH)
                        var datePicker = DatePickerDialog(item.context,
                            DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                                val SelectedDate= Calendar.getInstance()
                                SelectedDate.set(Calendar.YEAR,mYear)
                                SelectedDate.set(Calendar.MONTH,mMonth)
                                SelectedDate.set(Calendar.DAY_OF_MONTH,mDay)
                                val Date1= dateF.format(SelectedDate.time)
                                comboboxView.setText(Date1)
                            },year,month,day).show()
                    }
                    val comboboxView2=item.findViewById<AutoCompleteTextView>(R.id.autoCompleteText2)
                    comboboxView2.hint="員工姓名(必填)"
                    if(comboboxData_set.size!=0){
                        comboboxView2.setText(comboboxData_set.first())
                    }

                    comboboxView2.setAdapter(ArrayAdapter(this,R.layout.combobox_item,comboboxData_set))
                    mAlertDialog.setPositiveButton("取消") { dialog, id ->
                        dialog.dismiss()
                    }
                    mAlertDialog.setNegativeButton("確定") { dialog, id ->
                        //println(comboboxView.text)
                        selectFilter =comboboxView.text.toString()
                        selectFilter2=comboboxView2.text.toString()
                        //println(selectFilter)
                        show_relative_combobox("PLineBasicInfo","all","False", PLineBasicInfo())
                        show_relative_combobox("ProductControlOrderBody_A","all","False", ProductControlOrderBody_A())
                        show_relative_combobox("MeBody","all","False", MeBody())
                        show_header_body("ProductControlOrderBody_B","condition","False")//type=all or condition
                        when(cookie_data.status)
                        {
                            0->{
                                Toast.makeText(this, "資料載入", Toast.LENGTH_SHORT).show()
                                recyclerView.layoutManager= LinearLayoutManager(this)//設定Linear格式
                                PeopleWork_adapter= RecyclerItemPeopleWorkAdapterr(selectFilter)
                                recyclerView.adapter=PeopleWork_adapter//找對應itemAdapter
                                cookie_data.recyclerView=recyclerView
                                //addbtn?.isEnabled=true
                            }
                            1->{
                                Toast.makeText(this, cookie_data.msg, Toast.LENGTH_SHORT).show()
                            }
                        }

                    }
                    mAlertDialog.show()

                }
                /* "疊櫃排程"->{
                     val item = LayoutInflater.from(this).inflate(R.layout.filter_combobox, null)
                     val mAlertDialog = AlertDialog.Builder(this)
                     //mAlertDialog.setIcon(R.mipmap.ic_launcher_round) //set alertdialog icon
                     mAlertDialog.setTitle("排序") //set alertdialog title
                     //mAlertDialog.setMessage("確定要登出?") //set alertdialog message
                     mAlertDialog.setView(item)
                     //filter_combobox選單內容
                     val comboboxView=item.findViewById<AutoCompleteTextView>(R.id.autoCompleteText)
                     val combobox=resources.getStringArray(R.array.stackingControlSort)
                     comboboxView.setText("預開始時間")
                     comboboxView.setAdapter(ArrayAdapter(this, R.layout.combobox_item,combobox))
                     mAlertDialog.setPositiveButton("取消") { dialog, id ->
                         dialog.dismiss()
                     }
                     mAlertDialog.setNegativeButton("確定") { dialog, id ->
                         //println(comboboxView.text)
                         selectFilter=comboboxView.text.toString()
                         show_header_body("StackingControlListHeader","condition","False")//type=combobox or all
                         when(cookie_data.status)
                         {
                             0->{
                                 Toast.makeText(this, "資料載入", Toast.LENGTH_SHORT).show()
                                 recyclerView?.layoutManager= LinearLayoutManager(this)//設定Linear格式
                                 StackingControlListHeaderSimplify_adapter= RecyclerItemStackingControlListHeaderSimplifyAdapter(selectFilter)
                                 recyclerView?.adapter=StackingControlListHeaderSimplify_adapter//找對應itemAdapter
                                 //addbtn?.isEnabled=true
                             }
                             1->{
                                 Toast.makeText(this, cookie_data.msg, Toast.LENGTH_SHORT).show()
                             }
                         }
                     }
                     mAlertDialog.show()

                 }*/
                /* "疊櫃管制單(單身)"->{
                     show_Header_combobox("StackingControlListHeader","all","False", StackingControlListHeader())//type=combobox or all
                     when(cookie_data.status)
                     {
                         0->{
                             val item = LayoutInflater.from(activity).inflate(R.layout.filter_combobox, null)
                             val mAlertDialog = AlertDialog.Builder(requireView().context)
                             //mAlertDialog.setIcon(R.mipmap.ic_launcher_round) //set alertdialog icon
                             mAlertDialog.setTitle("篩選") //set alertdialog title
                             //mAlertDialog.setMessage("確定要登出?") //set alertdialog message
                             mAlertDialog.setView(item)
                             //filter_combobox選單內容
                             val comboboxView=item.findViewById<AutoCompleteTextView>(R.id.autoCompleteText)
                             comboboxView.setAdapter(ArrayAdapter(requireContext(),R.layout.combobox_item,comboboxData))
                             mAlertDialog.setPositiveButton("取消") { dialog, id ->
                                 dialog.dismiss()
                             }
                             mAlertDialog.setNegativeButton("確定") { dialog, id ->
                                 //println(comboboxView.text)
                                 selectFilter=comboboxView.text.toString()
                                 show_header_body("StackingControlListBody","condition","False")//type=all or condition
                                 show_relative_combobox("ProductBasicInfo","all","False", ProductBasicInfo())
                                 show_relative_combobox("MasterScheduledOrderHeader","all","False", MasterScheduledOrderHeader())
                                 show_relative_combobox("StoreArea","all","False", StoreArea())
                                 show_relative_combobox("StoreLocal","all","False", StoreLocal())
                                 when(cookie_data.status)
                                 {
                                     0->{
                                         Toast.makeText(activity, "資料載入", Toast.LENGTH_SHORT).show()
                                         recyclerView?.layoutManager=
                                             LinearLayoutManager(context)//設定Linear格式
                                         StackingControlListBody_adapter= RecyclerItemStackingControlListBodyAdapter()
                                         recyclerView?.adapter=StackingControlListBody_adapter//找對應itemAdapter
                                         addbtn?.isEnabled=true
                                     }
                                     1->{
                                         Toast.makeText(activity, cookie_data.msg, Toast.LENGTH_SHORT).show()
                                     }
                                 }

                             }
                             mAlertDialog.show()
                         }
                         1->{
                             Toast.makeText(activity, cookie_data.msg, Toast.LENGTH_SHORT).show()
                         }
                     }

                 }*/

            }
        }


    }



    private fun show_header_body(operation:String,view_type:String,view_hide:String) {
        when(operation){
            "ProductControlOrderHeader"->{
                val body = FormBody.Builder()
                    .add("username", cookie_data.username)
                    .add("operation", operation)
                    .add("action", cookie_data.Actions.VIEW)
                    .add("view_type",view_type)
                    .add("view_hide",view_hide)
                    .add("csrfmiddlewaretoken", cookie_data.tokenValue)
                    .add("login_flag", cookie_data.loginflag)
                    .build()
                val request = Request.Builder()
                    .url(cookie_data.URL+"/production_control_sheet_management")
                    .header("User-Agent", "ERP_MOBILE")
                    .post(body)
                    .build()
                runBlocking {
                    var job = CoroutineScope(Dispatchers.IO).launch {
                        var response = cookie_data.okHttpClient.newCall(request).execute()
                        cookie_data.response_data=response.body?.string().toString()
                        Log.d("GSON", "msg:${cookie_data.response_data}")

                    }
                    job.join()
                    val responseInfo = Gson().fromJson(cookie_data.response_data, Response::class.java)
                    cookie_data.status=responseInfo.status
                    if(responseInfo.msg !=null){
                        cookie_data.msg=responseInfo.msg
                    }
                    //Log.d("GSON", "msg:${responseInfo}")
                }
            }
            "ProductControlOrderBody_A"->{
                val filter = JSONObject()
                filter.put("is_closed",false)
                val body = FormBody.Builder()
                    .add("username", cookie_data.username)
                    .add("operation", operation)
                    .add("action", cookie_data.Actions.VIEW)
                    .add("view_type",view_type)
                    .add("view_hide",view_hide)
                    .add("data",filter.toString())
                    .add("csrfmiddlewaretoken", cookie_data.tokenValue)
                    .add("login_flag", cookie_data.loginflag)
                    .build()
                val request = Request.Builder()
                    .url(cookie_data.URL+"/production_control_sheet_management")
                    .header("User-Agent", "ERP_MOBILE")
                    .post(body)
                    .build()
                runBlocking {
                    var job = CoroutineScope(Dispatchers.IO).launch {
                        var response = cookie_data.okHttpClient.newCall(request).execute()
                        cookie_data.response_data=response.body?.string().toString()
                        Log.d("GSON", "msg:${cookie_data.response_data}")

                    }
                    job.join()
                    val responseInfo = Gson().fromJson(cookie_data.response_data, Response::class.java)
                    cookie_data.status=responseInfo.status
                    if(responseInfo.msg !=null){
                        cookie_data.msg=responseInfo.msg
                    }
                    //Log.d("GSON", "msg:${responseInfo}")
                }
            }
            "ProductControlOrderBody_B"->{
                val filter = JSONObject()
                filter.put("staff_name",selectFilter2)
                val body = FormBody.Builder()
                    .add("username", cookie_data.username)
                    .add("operation", operation)
                    .add("action", cookie_data.Actions.VIEW)
                    .add("view_type",view_type)
                    .add("view_hide",view_hide)
                    .add("data",filter.toString())
                    .add("csrfmiddlewaretoken", cookie_data.tokenValue)
                    .add("login_flag", cookie_data.loginflag)
                    .build()
                val request = Request.Builder()
                    .url(cookie_data.URL+"/production_control_sheet_management")
                    .header("User-Agent", "ERP_MOBILE")
                    .post(body)
                    .build()
                runBlocking {
                    var job = CoroutineScope(Dispatchers.IO).launch {
                        var response = cookie_data.okHttpClient.newCall(request).execute()
                        cookie_data.response_data=response.body?.string().toString()
                        Log.d("GSON", "msg:${cookie_data.response_data}")

                    }
                    job.join()
                    val responseInfo = Gson().fromJson(cookie_data.response_data, Response::class.java)
                    cookie_data.status=responseInfo.status
                    if(responseInfo.msg !=null){
                        cookie_data.msg=responseInfo.msg
                    }
                    //Log.d("GSON", "msg:${responseInfo}")
                }
            }
            "ProductControlOrderBody_D"->{
                val filter = JSONObject()
                filter.put("prod_ctrl_order_number",selectFilter)
                val body = FormBody.Builder()
                    .add("username", cookie_data.username)
                    .add("operation", operation)
                    .add("action", cookie_data.Actions.VIEW)
                    .add("view_type",view_type)
                    .add("view_hide",view_hide)
                    .add("data",filter.toString())
                    .add("csrfmiddlewaretoken", cookie_data.tokenValue)
                    .add("login_flag", cookie_data.loginflag)
                    .build()
                val request = Request.Builder()
                    .url(cookie_data.URL+"/production_control_sheet_management")
                    .header("User-Agent", "ERP_MOBILE")
                    .post(body)
                    .build()
                runBlocking {
                    var job = CoroutineScope(Dispatchers.IO).launch {
                        var response = cookie_data.okHttpClient.newCall(request).execute()
                        cookie_data.response_data=response.body?.string().toString()
                        Log.d("GSON", "msg:${cookie_data.response_data}")

                    }
                    job.join()
                    val responseInfo = Gson().fromJson(cookie_data.response_data, Response::class.java)
                    cookie_data.status=responseInfo.status
                    if(responseInfo.msg !=null){
                        cookie_data.msg=responseInfo.msg
                    }
                    //Log.d("GSON", "msg:${responseInfo}")
                }
            }

        }
    }

    private fun <T>show_combobox(operation:String,view_type:String,view_hide:String,fmt:T) {
        when(fmt)
        {
            is ProductControlOrderBody_A ->{
                val filter = JSONObject()
                filter.put("is_closed",false)
                val body = FormBody.Builder()
                    .add("username", cookie_data.username)
                    .add("operation", operation)
                    .add("action", cookie_data.Actions.VIEW)
                    .add("view_type",view_type)
                    .add("view_hide",view_hide)
                    .add("data",filter.toString())
                    .add("csrfmiddlewaretoken", cookie_data.tokenValue)
                    .add("login_flag", cookie_data.loginflag)
                    .build()
                val request = Request.Builder()
                    .url(cookie_data.URL+"/production_control_sheet_management")
                    .header("User-Agent", "ERP_MOBILE")
                    .post(body)
                    .build()
                runBlocking {
                    var job = CoroutineScope(Dispatchers.IO).launch {
                        var response = cookie_data.okHttpClient.newCall(request).execute()
                        var responseinfo=response.body?.string().toString()
                        //Log.d("GSON", "msg:${responseinfo}")
                        var json= Gson().fromJson(responseinfo, ShowProductControlOrderBody_A::class.java)
                        when(json.status){
                            0->{
                                cookie_data.status =json.status
                                var data: ArrayList<ProductControlOrderBody_A> =json.data
                                comboboxData.removeAll(comboboxData)
                                for(i in 0 until data.size){
                                    comboboxData.add(data[i].pline_id)
                                }
                                Log.d("GSON", "msg:${comboboxData}")
                            }
                            1->{
                                var fail= Gson().fromJson(responseinfo, Response::class.java)
                                cookie_data.msg =fail.msg
                                cookie_data.status =fail.status
                            }
                        }

                    }
                    job.join()


                }
            }
            is ProductControlOrderBody_B ->{
                val filter = JSONObject()
                val body = FormBody.Builder()
                    .add("username", cookie_data.username)
                    .add("operation", operation)
                    .add("action", cookie_data.Actions.VIEW)
                    .add("view_type",view_type)
                    .add("view_hide",view_hide)
                    .add("csrfmiddlewaretoken", cookie_data.tokenValue)
                    .add("login_flag", cookie_data.loginflag)
                    .build()
                val request = Request.Builder()
                    .url(cookie_data.URL+"/production_control_sheet_management")
                    .header("User-Agent", "ERP_MOBILE")
                    .post(body)
                    .build()
                runBlocking {
                    var job = CoroutineScope(Dispatchers.IO).launch {
                        var response = cookie_data.okHttpClient.newCall(request).execute()
                        var responseinfo=response.body?.string().toString()
                        //Log.d("GSON", "msg:${responseinfo}")
                        var json= Gson().fromJson(responseinfo, ShowProductControlOrderBody_B::class.java)
                        when(json.status){
                            0->{
                                cookie_data.status =json.status
                                var data: ArrayList<ProductControlOrderBody_B> =json.data
                                comboboxData.removeAll(comboboxData)
                                for(i in 0 until data.size){
                                    comboboxData.add(data[i].staff_name)
                                }
                                Log.d("GSON", "msg:${comboboxData}")
                            }
                            1->{
                                var fail= Gson().fromJson(responseinfo, Response::class.java)
                                cookie_data.msg =fail.msg
                                cookie_data.status =fail.status
                            }
                        }

                    }
                    job.join()


                }
            }
        }
    }

    //異動資料單頭單身過濾
    private fun show_header_body_filter(operation:String,view_type:String,view_hide:String,selectedfilter:String) {
        when(operation){
            "ProductControlOrderBody_A"->{
                val filter = JSONObject()
                filter.put("is_closed",false)
                filter.put("prod_ctrl_order_number",selectedfilter)
                val body = FormBody.Builder()
                    .add("username", cookie_data.username)
                    .add("operation", operation)
                    .add("action", cookie_data.Actions.VIEW)
                    .add("view_type",view_type)
                    .add("view_hide",view_hide)
                    .add("data",filter.toString())
                    .add("csrfmiddlewaretoken", cookie_data.tokenValue)
                    .add("login_flag", cookie_data.loginflag)
                    .build()
                val request = Request.Builder()
                    .url(cookie_data.URL+"/production_control_sheet_management")
                    .header("User-Agent", "ERP_MOBILE")
                    .post(body)
                    .build()
                runBlocking {
                    var job = CoroutineScope(Dispatchers.IO).launch {
                        var response = cookie_data.okHttpClient.newCall(request).execute()
                        cookie_data.response_data=response.body?.string().toString()
                        Log.d("GSON", "msg:${cookie_data.response_data}")

                    }
                    job.join()
                    val responseInfo = Gson().fromJson(cookie_data.response_data, Response::class.java)
                    cookie_data.status=responseInfo.status
                    if(responseInfo.msg !=null){
                        cookie_data.msg=responseInfo.msg
                    }
                    //Log.d("GSON", "msg:${responseInfo}")
                }
            }
        }
    }


    private fun <T>show_relative_combobox(operation:String,view_type:String,view_hide:String,fmt:T) {
        when(fmt)
        {
            is CustomBasicInfo ->{
                val body = FormBody.Builder()
                    .add("card_number", cookie_data.card_number)
                    .add("username", cookie_data.username)
                    .add("operation", operation)
                    .add("action", cookie_data.Actions.VIEW)
                    .add("view_type",view_type)
                    .add("view_hide",view_hide)
                    .add("csrfmiddlewaretoken", cookie_data.tokenValue)
                    .add("login_flag", cookie_data.loginflag)
                    .build()
                val request = Request.Builder()
                    .url(cookie_data.URL+"/basic_management")
                    .header("User-Agent", "ERP_MOBILE")
                    .post(body)
                    .build()
                runBlocking {
                    var job = CoroutineScope(Dispatchers.IO).launch {
                        var response = cookie_data.okHttpClient.newCall(request).execute()
                        var responseinfo=response.body?.string().toString()
                        var json= Gson().fromJson(responseinfo, ShowCustomBasicInfo::class.java)
                        when(json.status){
                            0->{
                                cookie_data.status =json.status
                                var data: ArrayList<CustomBasicInfo> =json.data
                                cookie_data.customer_id_ComboboxData.removeAll(cookie_data.customer_id_ComboboxData)
                                for(i in 0 until data.size){
                                    cookie_data.customer_id_ComboboxData.add(data[i]._id)
                                }
                                //Log.d("GSON", "msg:${comboboxData}")
                            }
                            1->{
                                var fail= Gson().fromJson(responseinfo, Response::class.java)
                                cookie_data.msg =fail.msg
                                cookie_data.status =fail.status
                            }
                        }

                    }
                    job.join()


                }
            }
            is ContNo ->{
                val body = FormBody.Builder()
                    .add("username", cookie_data.username)
                    .add("operation", operation)
                    .add("action", cookie_data.Actions.VIEW)
                    .add("view_type",view_type)
                    .add("view_hide",view_hide)
                    .add("csrfmiddlewaretoken", cookie_data.tokenValue)
                    .add("login_flag", cookie_data.loginflag)
                    .build()
                val request = Request.Builder()
                    .url(cookie_data.URL+"/def_management")
                    .header("User-Agent", "ERP_MOBILE")
                    .post(body)
                    .build()
                runBlocking {
                    var job = CoroutineScope(Dispatchers.IO).launch {
                        var response = cookie_data.okHttpClient.newCall(request).execute()
                        var responseinfo=response.body?.string().toString()
                        var json= Gson().fromJson(responseinfo, ShowContNo::class.java)
                        when(json.status){
                            0->{
                                cookie_data.status =json.status
                                var data: ArrayList<ContNo> =json.data
                                cookie_data.cont_id_ComboboxData.removeAll(cookie_data.cont_id_ComboboxData)
                                cookie_data.cont_code_ComboboxData.removeAll(cookie_data.cont_code_ComboboxData)
                                for(i in 0 until data.size){
                                    cookie_data.cont_id_ComboboxData.add(data[i].cont_code_name)
                                    cookie_data.cont_code_ComboboxData.add(data[i].cont_code)
                                }
                                //Log.d("GSON", "msg:${comboboxData}")
                            }
                            1->{
                                var fail= Gson().fromJson(responseinfo, Response::class.java)
                                cookie_data.msg =fail.msg
                                cookie_data.status =fail.status
                            }
                        }

                    }
                    job.join()


                }
            }
            is ProductBasicInfo ->{
                val body = FormBody.Builder()
                    .add("card_number", cookie_data.card_number)
                    .add("username", cookie_data.username)
                    .add("operation", operation)
                    .add("action", cookie_data.Actions.VIEW)
                    .add("view_type",view_type)
                    .add("view_hide",view_hide)
                    .add("csrfmiddlewaretoken", cookie_data.tokenValue)
                    .add("login_flag", cookie_data.loginflag)
                    .build()
                val request = Request.Builder()
                    .url(cookie_data.URL+"/basic_management")
                    .header("User-Agent", "ERP_MOBILE")
                    .post(body)
                    .build()
                runBlocking {
                    var job = CoroutineScope(Dispatchers.IO).launch {
                        var response = cookie_data.okHttpClient.newCall(request).execute()
                        var responseinfo=response.body?.string().toString()
                        var json= Gson().fromJson(responseinfo, ShowProductBasicInfo::class.java)
                        when(json.status){
                            0->{
                                cookie_data.status =json.status
                                var data: ArrayList<ProductBasicInfo> =json.data
                                cookie_data.product_id_ComboboxData.removeAll(cookie_data.product_id_ComboboxData)
                                for(i in 0 until data.size){
                                    cookie_data.product_id_ComboboxData.add(data[i]._id)
                                }
                                //Log.d("GSON", "msg:${comboboxData}")
                            }
                            1->{
                                var fail= Gson().fromJson(responseinfo, Response::class.java)
                                cookie_data.msg =fail.msg
                                cookie_data.status =fail.status
                            }
                        }

                    }
                    job.join()


                }
            }
            is ContType ->{
                val body = FormBody.Builder()
                    .add("username", cookie_data.username)
                    .add("operation", operation)
                    .add("action", cookie_data.Actions.VIEW)
                    .add("view_type",view_type)
                    .add("view_hide",view_hide)
                    .add("csrfmiddlewaretoken", cookie_data.tokenValue)
                    .add("login_flag", cookie_data.loginflag)
                    .build()
                val request = Request.Builder()
                    .url(cookie_data.URL+"/def_management")
                    .header("User-Agent", "ERP_MOBILE")
                    .post(body)
                    .build()
                runBlocking {
                    var job = CoroutineScope(Dispatchers.IO).launch {
                        var response = cookie_data.okHttpClient.newCall(request).execute()
                        var responseinfo=response.body?.string().toString()
                        var json= Gson().fromJson(responseinfo, ShowContType::class.java)
                        when(json.status){
                            0->{
                                cookie_data.status =json.status
                                var data: ArrayList<ContType> =json.data
                                cookie_data.cont_type_code_ComboboxData.removeAll(cookie_data.cont_type_code_ComboboxData)
                                for(i in 0 until data.size){
                                    cookie_data.cont_type_code_ComboboxData.add(data[i].cont_type_code)
                                }
                                //Log.d("GSON", "msg:${comboboxData}")
                            }
                            1->{
                                var fail= Gson().fromJson(responseinfo, Response::class.java)
                                cookie_data.msg =fail.msg
                                cookie_data.status =fail.status
                            }
                        }

                    }
                    job.join()


                }
            }
            is Port ->{
                val body = FormBody.Builder()
                    .add("username", cookie_data.username)
                    .add("operation", operation)
                    .add("action", cookie_data.Actions.VIEW)
                    .add("view_type",view_type)
                    .add("view_hide",view_hide)
                    .add("csrfmiddlewaretoken", cookie_data.tokenValue)
                    .add("login_flag", cookie_data.loginflag)
                    .build()
                val request = Request.Builder()
                    .url(cookie_data.URL+"/def_management")
                    .header("User-Agent", "ERP_MOBILE")
                    .post(body)
                    .build()
                runBlocking {
                    var job = CoroutineScope(Dispatchers.IO).launch {
                        var response = cookie_data.okHttpClient.newCall(request).execute()
                        var responseinfo=response.body?.string().toString()
                        var json= Gson().fromJson(responseinfo, ShowPort::class.java)
                        when(json.status){
                            0->{
                                cookie_data.status =json.status
                                var data: ArrayList<Port> =json.data
                                cookie_data.port_id_ComboboxData.removeAll(cookie_data.port_id_ComboboxData)
                                for(i in 0 until data.size){
                                    cookie_data.port_id_ComboboxData.add(data[i].port_id)
                                }
                                //Log.d("GSON", "msg:${comboboxData}")
                            }
                            1->{
                                var fail= Gson().fromJson(responseinfo, Response::class.java)
                                cookie_data.msg =fail.msg
                                cookie_data.status =fail.status
                            }
                        }

                    }
                    job.join()


                }
            }
            is StoreArea ->{
                val body = FormBody.Builder()
                    .add("username", cookie_data.username)
                    .add("operation", operation)
                    .add("action", cookie_data.Actions.VIEW)
                    .add("view_type",view_type)
                    .add("view_hide",view_hide)
                    .add("csrfmiddlewaretoken", cookie_data.tokenValue)
                    .add("login_flag", cookie_data.loginflag)
                    .build()
                val request = Request.Builder()
                    .url(cookie_data.URL+"/def_management")
                    .header("User-Agent", "ERP_MOBILE")
                    .post(body)
                    .build()
                runBlocking {
                    var job = CoroutineScope(Dispatchers.IO).launch {
                        var response = cookie_data.okHttpClient.newCall(request).execute()
                        var responseinfo=response.body?.string().toString()
                        var json= Gson().fromJson(responseinfo, ShowStoreArea::class.java)
                        when(json.status){
                            0->{
                                cookie_data.status =json.status
                                var data: ArrayList<StoreArea> =json.data
                                cookie_data.store_area_ComboboxData.removeAll(cookie_data.store_area_ComboboxData)
                                for(i in 0 until data.size){
                                    cookie_data.store_area_ComboboxData.add(data[i].store_area)
                                }
                                //Log.d("GSON", "msg:${comboboxData}")
                            }
                            1->{
                                var fail= Gson().fromJson(responseinfo, Response::class.java)
                                cookie_data.msg =fail.msg
                                cookie_data.status =fail.status
                            }
                        }

                    }
                    job.join()


                }
            }
            is StoreLocal ->{
                val body = FormBody.Builder()
                    .add("username", cookie_data.username)
                    .add("operation", operation)
                    .add("action", cookie_data.Actions.VIEW)
                    .add("view_type",view_type)
                    .add("view_hide",view_hide)
                    .add("csrfmiddlewaretoken", cookie_data.tokenValue)
                    .add("login_flag", cookie_data.loginflag)
                    .build()
                val request = Request.Builder()
                    .url(cookie_data.URL+"/def_management")
                    .header("User-Agent", "ERP_MOBILE")
                    .post(body)
                    .build()
                runBlocking {
                    var job = CoroutineScope(Dispatchers.IO).launch {
                        var response = cookie_data.okHttpClient.newCall(request).execute()
                        var responseinfo=response.body?.string().toString()
                        var json= Gson().fromJson(responseinfo, ShowStoreLocal::class.java)
                        when(json.status){
                            0->{
                                cookie_data.status =json.status
                                var data: ArrayList<StoreLocal> =json.data
                                cookie_data.store_local_ComboboxData.removeAll(cookie_data.store_local_ComboboxData)
                                for(i in 0 until data.size){
                                    cookie_data.store_local_ComboboxData.add(data[i].store_local)
                                }
                                //Log.d("GSON", "msg:${comboboxData}")
                            }
                            1->{
                                var fail= Gson().fromJson(responseinfo, Response::class.java)
                                cookie_data.msg =fail.msg
                                cookie_data.status =fail.status
                            }
                        }

                    }
                    job.join()


                }
            }
            is ProdType ->{
                val body = FormBody.Builder()
                    .add("username", cookie_data.username)
                    .add("operation", operation)
                    .add("action", cookie_data.Actions.VIEW)
                    .add("view_type",view_type)
                    .add("view_hide",view_hide)
                    .add("csrfmiddlewaretoken", cookie_data.tokenValue)
                    .add("login_flag", cookie_data.loginflag)
                    .build()
                val request = Request.Builder()
                    .url(cookie_data.URL+"/def_management")
                    .header("User-Agent", "ERP_MOBILE")
                    .post(body)
                    .build()
                runBlocking {
                    var job = CoroutineScope(Dispatchers.IO).launch {
                        var response = cookie_data.okHttpClient.newCall(request).execute()
                        var responseinfo=response.body?.string().toString()
                        var json= Gson().fromJson(responseinfo, ShowProdType::class.java)
                        when(json.status){
                            0->{
                                cookie_data.status =json.status
                                var data: ArrayList<ProdType> =json.data
                                cookie_data.product_type_id_ComboboxData.removeAll(cookie_data.product_type_id_ComboboxData)
                                for(i in 0 until data.size){
                                    cookie_data.product_type_id_ComboboxData.add(data[i].product_type_id)
                                }
                                //Log.d("GSON", "msg:${comboboxData}")
                            }
                            1->{
                                var fail= Gson().fromJson(responseinfo, Response::class.java)
                                cookie_data.msg =fail.msg
                                cookie_data.status =fail.status
                            }
                        }

                    }
                    job.join()


                }
            }
            is MasterScheduledOrderHeader ->{
                val body = FormBody.Builder()
                    .add("username", cookie_data.username)
                    .add("operation", operation)
                    .add("action", cookie_data.Actions.VIEW)
                    .add("view_type",view_type)
                    .add("view_hide",view_hide)
                    .add("csrfmiddlewaretoken", cookie_data.tokenValue)
                    .add("login_flag", cookie_data.loginflag)
                    .build()
                val request = Request.Builder()
                    .url(cookie_data.URL+"/master_scheduled_order_management")
                    .header("User-Agent", "ERP_MOBILE")
                    .post(body)
                    .build()
                runBlocking {
                    var job = CoroutineScope(Dispatchers.IO).launch {
                        var response = cookie_data.okHttpClient.newCall(request).execute()
                        var responseinfo=response.body?.string().toString()
                        var json= Gson().fromJson(responseinfo, ShowMasterScheduledOrderHeader::class.java)
                        when(json.status){
                            0->{
                                cookie_data.status =json.status
                                var data: ArrayList<MasterScheduledOrderHeader> =json.data
                                cookie_data.MasterScheduledOrderHeader_id_ComboboxData.removeAll(
                                    cookie_data.MasterScheduledOrderHeader_id_ComboboxData
                                )
                                for(i in 0 until data.size){
                                    cookie_data.MasterScheduledOrderHeader_id_ComboboxData.add(data[i]._id)
                                }
                                //Log.d("GSON", "msg:${comboboxData}")
                            }
                            1->{
                                var fail= Gson().fromJson(responseinfo, Response::class.java)
                                cookie_data.msg =fail.msg
                                cookie_data.status =fail.status
                            }
                        }

                    }
                    job.join()


                }
            }
            is ShippingCompanyBasicInfo ->{
                val body = FormBody.Builder()
                    .add("card_number", cookie_data.card_number)
                    .add("username", cookie_data.username)
                    .add("operation", operation)
                    .add("action", cookie_data.Actions.VIEW)
                    .add("view_type",view_type)
                    .add("view_hide",view_hide)
                    .add("csrfmiddlewaretoken", cookie_data.tokenValue)
                    .add("login_flag", cookie_data.loginflag)
                    .build()
                val request = Request.Builder()
                    .url(cookie_data.URL+"/basic_management")
                    .header("User-Agent", "ERP_MOBILE")
                    .post(body)
                    .build()
                runBlocking {
                    var job = CoroutineScope(Dispatchers.IO).launch {
                        var response = cookie_data.okHttpClient.newCall(request).execute()
                        var responseinfo=response.body?.string().toString()
                        var json= Gson().fromJson(responseinfo, ShowShippingCompanyBasicInfo::class.java)
                        when(json.status){
                            0->{
                                cookie_data.status =json.status
                                var data: ArrayList<ShippingCompanyBasicInfo> =json.data
                                cookie_data.shipping_number_ComboboxData.removeAll(cookie_data.shipping_number_ComboboxData)
                                for(i in 0 until data.size){
                                    cookie_data.shipping_number_ComboboxData.add(data[i].shipping_number)
                                }
                                //Log.d("GSON", "msg:${comboboxData}")
                            }
                            1->{
                                var fail= Gson().fromJson(responseinfo, Response::class.java)
                                cookie_data.msg =fail.msg
                                cookie_data.status =fail.status
                            }
                        }

                    }
                    job.join()


                }
            }
            is CustomerOrderHeader ->{
                val body = FormBody.Builder()
                    .add("username", cookie_data.username)
                    .add("operation", "CustomerOrder")
                    .add("target","header")
                    .add("action", cookie_data.Actions.VIEW)
                    .add("view_type",view_type)
                    .add("view_hide",view_hide)
                    .add("csrfmiddlewaretoken", cookie_data.tokenValue)
                    .add("login_flag", cookie_data.loginflag)
                    .build()
                val request = Request.Builder()
                    .url(cookie_data.URL+"/custom_order_management")
                    .header("User-Agent", "ERP_MOBILE")
                    .post(body)
                    .build()
                runBlocking {
                    var job = CoroutineScope(Dispatchers.IO).launch {
                        var response = cookie_data.okHttpClient.newCall(request).execute()
                        var responseinfo=response.body?.string().toString()
                        var json= Gson().fromJson(responseinfo, ShowCustomerOrderHeader::class.java)
                        when(json.status){
                            0->{
                                cookie_data.status =json.status
                                var data: ArrayList<CustomerOrderHeader> =json.data
                                cookie_data.CustomerOrderHeader_poNo_ComboboxData.removeAll(
                                    cookie_data.CustomerOrderHeader_poNo_ComboboxData
                                )
                                for(i in 0 until data.size){
                                    cookie_data.CustomerOrderHeader_poNo_ComboboxData.add(data[i].poNo)
                                }
                                //Log.d("GSON", "msg:${comboboxData}")
                            }
                            1->{
                                var fail= Gson().fromJson(responseinfo, Response::class.java)
                                cookie_data.msg =fail.msg
                                cookie_data.status =fail.status
                            }
                        }

                    }
                    job.join()


                }
            }
            is Department ->{
                val body = FormBody.Builder()
                    .add("username", cookie_data.username)
                    .add("operation", operation)
                    .add("action", cookie_data.Actions.VIEW)
                    .add("view_type",view_type)
                    .add("view_hide",view_hide)
                    .add("csrfmiddlewaretoken", cookie_data.tokenValue)
                    .add("login_flag", cookie_data.loginflag)
                    .build()
                val request = Request.Builder()
                    .url(cookie_data.URL+"/def_management")
                    .header("User-Agent", "ERP_MOBILE")
                    .post(body)
                    .build()
                runBlocking {
                    var job = CoroutineScope(Dispatchers.IO).launch {
                        var response = cookie_data.okHttpClient.newCall(request).execute()
                        var responseinfo=response.body?.string().toString()
                        var json= Gson().fromJson(responseinfo, ShowDepartment::class.java)
                        when(json.status){
                            0->{
                                cookie_data.status =json.status
                                var data: ArrayList<Department> =json.data
                                cookie_data.dept_name_ComboboxData.removeAll(cookie_data.dept_name_ComboboxData)
                                for(i in 0 until data.size){
                                    cookie_data.dept_name_ComboboxData.add(data[i].dept_name)
                                }
                                //Log.d("GSON", "msg:${comboboxData}")
                            }
                            1->{
                                var fail= Gson().fromJson(responseinfo, Response::class.java)
                                cookie_data.msg =fail.msg
                                cookie_data.status =fail.status
                            }
                        }

                    }
                    job.join()


                }
            }
            is ItemBasicInfo ->{
                val body = FormBody.Builder()
                    .add("card_number", cookie_data.card_number)
                    .add("username", cookie_data.username)
                    .add("operation", operation)
                    .add("action", cookie_data.Actions.VIEW)
                    .add("view_type",view_type)
                    .add("view_hide",view_hide)
                    .add("csrfmiddlewaretoken", cookie_data.tokenValue)
                    .add("login_flag", cookie_data.loginflag)
                    .build()
                val request = Request.Builder()
                    .url(cookie_data.URL+"/basic_management")
                    .header("User-Agent", "ERP_MOBILE")
                    .post(body)
                    .build()
                runBlocking {
                    var job = CoroutineScope(Dispatchers.IO).launch {
                        var response = cookie_data.okHttpClient.newCall(request).execute()
                        var responseinfo=response.body?.string().toString()
                        var json= Gson().fromJson(responseinfo, ShowItemBasicInfo::class.java)
                        when(json.status){
                            0->{
                                cookie_data.status =json.status
                                var data: ArrayList<ItemBasicInfo> =json.data
                                cookie_data.item_id_ComboboxData.removeAll(cookie_data.item_id_ComboboxData)
                                cookie_data.item_name_ComboboxData.removeAll(cookie_data.item_name_ComboboxData)
                                cookie_data.semi_finished_product_number_ComboboxData.removeAll(
                                    cookie_data.semi_finished_product_number_ComboboxData
                                )
                                cookie_data.is_exemption_ComboboxData.removeAll(cookie_data.is_exemption_ComboboxData)
                                for(i in 0 until data.size){
                                    cookie_data.item_id_ComboboxData.add(data[i]._id)
                                    cookie_data.item_name_ComboboxData.add(data[i].name)
                                    cookie_data.semi_finished_product_number_ComboboxData.add(data[i].semi_finished_product_number)
                                    cookie_data.is_exemption_ComboboxData.add(data[i].is_exemption)
                                }
                                //Log.d("GSON", "msg:${comboboxData}")
                            }
                            1->{
                                var fail= Gson().fromJson(responseinfo, Response::class.java)
                                cookie_data.msg =fail.msg
                                cookie_data.status =fail.status
                            }
                        }

                    }
                    job.join()


                }
            }
            is MeHeader ->{
                val body = FormBody.Builder()
                    .add("card_number", cookie_data.card_number)
                    .add("username", cookie_data.username)
                    .add("operation", "Me")
                    .add("target","header")
                    .add("action", cookie_data.Actions.VIEW)
                    .add("view_type",view_type)
                    .add("view_hide",view_hide)
                    .add("csrfmiddlewaretoken", cookie_data.tokenValue)
                    .add("login_flag", cookie_data.loginflag)
                    .build()
                val request = Request.Builder()
                    .url(cookie_data.URL+"/special_basic_management")
                    .header("User-Agent", "ERP_MOBILE")
                    .post(body)
                    .build()
                runBlocking {
                    var job = CoroutineScope(Dispatchers.IO).launch {
                        var response = cookie_data.okHttpClient.newCall(request).execute()
                        var responseinfo=response.body?.string().toString()
                        var json= Gson().fromJson(responseinfo, ShowMeHeader::class.java)
                        when(json.status){
                            0->{
                                cookie_data.status =json.status
                                var data: ArrayList<MeHeader> =json.data
                                cookie_data.MeHeader_id_ComboboxData.removeAll(cookie_data.MeHeader_id_ComboboxData)
                                for(i in 0 until data.size){
                                    cookie_data.MeHeader_id_ComboboxData.add(data[i]._id)
                                }
                                //Log.d("GSON", "msg:${comboboxData}")
                            }
                            1->{
                                var fail= Gson().fromJson(responseinfo, Response::class.java)
                                cookie_data.msg =fail.msg
                                cookie_data.status =fail.status
                            }
                        }

                    }
                    job.join()


                }
            }
            is MeBody ->{
                val body = FormBody.Builder()
                    .add("card_number", cookie_data.card_number)
                    .add("username", cookie_data.username)
                    .add("operation", "Me")
                    .add("target","body")
                    .add("action", cookie_data.Actions.VIEW)
                    .add("view_type",view_type)
                    .add("view_hide",view_hide)
                    .add("csrfmiddlewaretoken", cookie_data.tokenValue)
                    .add("login_flag", cookie_data.loginflag)
                    .build()
                val request = Request.Builder()
                    .url(cookie_data.URL+"/special_basic_management")
                    .header("User-Agent", "ERP_MOBILE")
                    .post(body)
                    .build()
                runBlocking {
                    var job = CoroutineScope(Dispatchers.IO).launch {
                        var response = cookie_data.okHttpClient.newCall(request).execute()
                        var responseinfo=response.body?.string().toString()
                        var json= Gson().fromJson(responseinfo, ShowMeBody::class.java)
                        when(json.status){
                            0->{
                                cookie_data.status =json.status
                                var data: ArrayList<MeBody> =json.data
                                cookie_data.MeBody_process_number_ComboboxData.removeAll(cookie_data.MeBody_process_number_ComboboxData)
                                cookie_data.MeBody_work_option_ComboboxData.removeAll(cookie_data.MeBody_work_option_ComboboxData)
                                for(i in 0 until data.size){
                                    cookie_data.MeBody_process_number_ComboboxData.add(data[i].process_number)
                                    cookie_data.MeBody_work_option_ComboboxData.add(data[i].work_option)
                                }
                                //Log.d("GSON", "msg:${comboboxData}")
                            }
                            1->{
                                var fail= Gson().fromJson(responseinfo, Response::class.java)
                                cookie_data.msg =fail.msg
                                cookie_data.status =fail.status
                            }
                        }

                    }
                    job.join()


                }
            }
            is PLineBasicInfo ->{
                val body = FormBody.Builder()
                    .add("card_number", cookie_data.card_number)
                    .add("username", cookie_data.username)
                    .add("operation", operation)
                    .add("action", cookie_data.Actions.VIEW)
                    .add("view_type",view_type)
                    .add("view_hide",view_hide)
                    .add("csrfmiddlewaretoken", cookie_data.tokenValue)
                    .add("login_flag", cookie_data.loginflag)
                    .build()
                val request = Request.Builder()
                    .url(cookie_data.URL+"/basic_management")
                    .header("User-Agent", "ERP_MOBILE")
                    .post(body)
                    .build()
                runBlocking {
                    var job = CoroutineScope(Dispatchers.IO).launch {
                        var response = cookie_data.okHttpClient.newCall(request).execute()
                        var responseinfo=response.body?.string().toString()
                        var json= Gson().fromJson(responseinfo, ShowPLineBasicInfo::class.java)
                        when(json.status){
                            0->{
                                cookie_data.status =json.status
                                var data: ArrayList<PLineBasicInfo> =json.data
                                cookie_data.pline_name_ComboboxData.removeAll(cookie_data.pline_name_ComboboxData)
                                cookie_data.pline_id_ComboboxData.removeAll(cookie_data.pline_id_ComboboxData)
                                cookie_data.out_sourceing_ComboboxData.removeAll(cookie_data.out_sourceing_ComboboxData)
                                cookie_data.pline_id_name_ComboboxData.removeAll(cookie_data.pline_id_name_ComboboxData)
                                for(i in 0 until data.size){
                                    cookie_data.pline_name_ComboboxData.add(data[i].name)
                                    cookie_data.pline_id_ComboboxData.add(data[i]._id)
                                    cookie_data.out_sourceing_ComboboxData.add(data[i].is_outsourcing)
                                    cookie_data.pline_id_name_ComboboxData.add(data[i]._id+" "+data[i].name)
                                }
                                //Log.d("GSON", "msg:${comboboxData}")
                            }
                            1->{
                                var fail= Gson().fromJson(responseinfo, Response::class.java)
                                cookie_data.msg =fail.msg
                                cookie_data.status =fail.status
                            }
                        }

                    }
                    job.join()


                }
            }
            is staffBasicInfo ->{
                val body = FormBody.Builder()
                    .add("username", cookie_data.username)
                    .add("operation", operation)
                    .add("action", cookie_data.Actions.VIEW)
                    .add("view_type",view_type)
                    .add("view_hide",view_hide)

                    .add("csrfmiddlewaretoken", cookie_data.tokenValue)
                    .add("login_flag", cookie_data.loginflag)
                    .build()
                val request = Request.Builder()
                    .url(cookie_data.URL+"/staff_management")
                    .header("User-Agent", "ERP_MOBILE")
                    .post(body)
                    .build()
                runBlocking {
                    var job = CoroutineScope(Dispatchers.IO).launch {
                        var response = cookie_data.okHttpClient.newCall(request).execute()
                        var responseinfo=response.body?.string().toString()
                        var json= Gson().fromJson(responseinfo, ShowstaffBasicInfo::class.java)
                        when(json.status){
                            0->{
                                cookie_data.status =json.status
                                var data: ArrayList<staffBasicInfo> =json.data
                                cookie_data.card_number_ComboboxData.removeAll(cookie_data.card_number_ComboboxData)
                                cookie_data.name_ComboboxData.removeAll(cookie_data.name_ComboboxData)
                                cookie_data.qrcode_ComboboxData.removeAll(cookie_data.qrcode_ComboboxData)
                                cookie_data.staff_dept_ComboboxData.removeAll(cookie_data.staff_dept_ComboboxData)
                                for(i in 0 until data.size){
                                    cookie_data.card_number_ComboboxData.add(data[i].card_number)
                                    cookie_data.name_ComboboxData.add(data[i].name)
                                    cookie_data.qrcode_ComboboxData.add(data[i].qr_code)
                                    cookie_data.staff_dept_ComboboxData.add(data[i].dept)
                                }

                                //Log.d("GSON", "msg:${comboboxData}")
                            }
                            1->{
                                var fail= Gson().fromJson(responseinfo, Response::class.java)
                                cookie_data.msg =fail.msg
                                cookie_data.status =fail.status
                            }
                        }

                    }
                    job.join()


                }
            }
            is InvChangeTypeM ->{
                val body = FormBody.Builder()
                    .add("username", cookie_data.username)
                    .add("operation", operation)
                    .add("action", cookie_data.Actions.VIEW)
                    .add("view_type",view_type)
                    .add("view_hide",view_hide)
                    .add("csrfmiddlewaretoken", cookie_data.tokenValue)
                    .add("login_flag", cookie_data.loginflag)
                    .build()
                val request = Request.Builder()
                    .url(cookie_data.URL+"/def_management")
                    .header("User-Agent", "ERP_MOBILE")
                    .post(body)
                    .build()
                runBlocking {
                    var job = CoroutineScope(Dispatchers.IO).launch {
                        var response = cookie_data.okHttpClient.newCall(request).execute()
                        var responseinfo=response.body?.string().toString()
                        var json= Gson().fromJson(responseinfo, ShowInvChangeTypeM::class.java)
                        when(json.status){
                            0->{
                                cookie_data.status =json.status
                                var data: ArrayList<InvChangeTypeM> =json.data
                                cookie_data.inv_code_m_ComboboxData.removeAll(cookie_data.inv_code_m_ComboboxData)
                                for(i in 0 until data.size){
                                    cookie_data.inv_code_m_ComboboxData.add(data[i].inv_code_m)
                                }
                                //Log.d("GSON", "msg:${comboboxData}")
                            }
                            1->{
                                var fail= Gson().fromJson(responseinfo, Response::class.java)
                                cookie_data.msg =fail.msg
                                cookie_data.status =fail.status
                            }
                        }

                    }
                    job.join()


                }
            }
            is InvChangeTypeS ->{
                val body = FormBody.Builder()
                    .add("username", cookie_data.username)
                    .add("operation", operation)
                    .add("action", cookie_data.Actions.VIEW)
                    .add("view_type",view_type)
                    .add("view_hide",view_hide)
                    .add("csrfmiddlewaretoken", cookie_data.tokenValue)
                    .add("login_flag", cookie_data.loginflag)
                    .build()
                val request = Request.Builder()
                    .url(cookie_data.URL+"/def_management")
                    .header("User-Agent", "ERP_MOBILE")
                    .post(body)
                    .build()
                runBlocking {
                    var job = CoroutineScope(Dispatchers.IO).launch {
                        var response = cookie_data.okHttpClient.newCall(request).execute()
                        var responseinfo=response.body?.string().toString()
                        var json= Gson().fromJson(responseinfo, ShowInvChangeTypeS::class.java)
                        when(json.status){
                            0->{
                                cookie_data.status =json.status
                                var data: ArrayList<InvChangeTypeS> =json.data
                                cookie_data.inv_code_s_ComboboxData.removeAll(cookie_data.inv_code_s_ComboboxData)
                                for(i in 0 until data.size){
                                    cookie_data.inv_code_s_ComboboxData.add(data[i].inv_code_s)
                                }
                                //Log.d("GSON", "msg:${comboboxData}")
                            }
                            1->{
                                var fail= Gson().fromJson(responseinfo, Response::class.java)
                                cookie_data.msg =fail.msg
                                cookie_data.status =fail.status
                            }
                        }

                    }
                    job.join()


                }
            }
            is ProductControlOrderHeader ->{
                val body = FormBody.Builder()
                    .add("username", cookie_data.username)
                    .add("operation", operation)
                    .add("action", cookie_data.Actions.VIEW)
                    .add("view_type",view_type)
                    .add("view_hide",view_hide)
                    .add("csrfmiddlewaretoken", cookie_data.tokenValue)
                    .add("login_flag", cookie_data.loginflag)
                    .build()
                val request = Request.Builder()
                    .url(cookie_data.URL+"/production_control_sheet_management")
                    .header("User-Agent", "ERP_MOBILE")
                    .post(body)
                    .build()
                runBlocking {
                    var job = CoroutineScope(Dispatchers.IO).launch {
                        var response = cookie_data.okHttpClient.newCall(request).execute()
                        var responseinfo=response.body?.string().toString()
                        var json= Gson().fromJson(responseinfo, ShowProductControlOrderHeader::class.java)
                        when(json.status){
                            0->{
                                cookie_data.status =json.status
                                var data: ArrayList<ProductControlOrderHeader> =json.data
                                cookie_data.ProductControlOrderHeader_id_ComboboxData.removeAll(
                                    cookie_data.ProductControlOrderHeader_id_ComboboxData)
                                cookie_data.ProductControlOrderHeader_poNo_ComboboxData.removeAll(
                                    cookie_data.ProductControlOrderHeader_poNo_ComboboxData)

                                for(i in 0 until data.size){
                                    cookie_data.ProductControlOrderHeader_id_ComboboxData.add(data[i]._id)
                                    cookie_data.ProductControlOrderHeader_poNo_ComboboxData.add(data[i].customer_poNo)
                                }
                                //Log.d("GSON", "msg:${comboboxData}")
                            }
                            1->{
                                var fail= Gson().fromJson(responseinfo, Response::class.java)
                                cookie_data.msg =fail.msg
                                cookie_data.status =fail.status
                            }
                        }

                    }
                    job.join()


                }
            }
            is ProductControlOrderBody_A ->{
                val filter = JSONObject()
                filter.put("is_closed",false)
                val body = FormBody.Builder()
                    .add("username", cookie_data.username)
                    .add("operation", operation)
                    .add("action", cookie_data.Actions.VIEW)
                    .add("view_type",view_type)
                    .add("view_hide",view_hide)
                    .add("data",filter.toString())
                    .add("csrfmiddlewaretoken", cookie_data.tokenValue)
                    .add("login_flag", cookie_data.loginflag)
                    .build()
                val request = Request.Builder()
                    .url(cookie_data.URL+"/production_control_sheet_management")
                    .header("User-Agent", "ERP_MOBILE")
                    .post(body)
                    .build()
                runBlocking {
                    var job = CoroutineScope(Dispatchers.IO).launch {
                        var response = cookie_data.okHttpClient.newCall(request).execute()
                        var responseinfo=response.body?.string().toString()
                        var json= Gson().fromJson(responseinfo, ShowProductControlOrderBody_A::class.java)
                        when(json.status){
                            0->{
                                cookie_data.status =json.status
                                var data: ArrayList<ProductControlOrderBody_A> =json.data
                                cookie_data.ProductControlOrderBody_A_prod_ctrl_order_number_ComboboxData.removeAll(
                                    cookie_data.ProductControlOrderBody_A_prod_ctrl_order_number_ComboboxData)
                                cookie_data.ProductControlOrderBody_A_me_code_ComboboxData.removeAll(
                                    cookie_data.ProductControlOrderBody_A_me_code_ComboboxData)
                                cookie_data.ProductControlOrderBody_A_semi_finished_prod_number_ComboboxData.removeAll(
                                    cookie_data.ProductControlOrderBody_A_semi_finished_prod_number_ComboboxData)
                                cookie_data.ProductControlOrderBody_A_pline_id_ComboboxData.removeAll(
                                    cookie_data.ProductControlOrderBody_A_pline_id_ComboboxData)
                                cookie_data.ProductControlOrderBody_A_complete_date_ComboboxData.removeAll(
                                    cookie_data.ProductControlOrderBody_A_complete_date_ComboboxData)
                                cookie_data.ProductControlOrderBody_A_est_complete_date_ComboboxData.removeAll(
                                    cookie_data.ProductControlOrderBody_A_est_complete_date_ComboboxData)
                                cookie_data.ProductControlOrderBody_A_is_closed_ComboboxData.removeAll(
                                    cookie_data.ProductControlOrderBody_A_is_closed_ComboboxData)
                                cookie_data.ProductControlOrderBody_A_is_re_make_ComboboxData.removeAll(
                                    cookie_data.ProductControlOrderBody_A_is_re_make_ComboboxData)
                                cookie_data.ProductControlOrderBody_A_est_output_ComboboxData.removeAll(
                                    cookie_data.ProductControlOrderBody_A_est_output_ComboboxData)
                                cookie_data.ProductControlOrderBody_A_est_start_date_ComboboxData.removeAll(
                                    cookie_data.ProductControlOrderBody_A_est_start_date_ComboboxData)

                                for(i in 0 until data.size){
                                    cookie_data.ProductControlOrderBody_A_prod_ctrl_order_number_ComboboxData.add(data[i].prod_ctrl_order_number)
                                    cookie_data.ProductControlOrderBody_A_me_code_ComboboxData.add(data[i].me_code)
                                    cookie_data.ProductControlOrderBody_A_semi_finished_prod_number_ComboboxData.add(data[i].semi_finished_prod_number)
                                    cookie_data.ProductControlOrderBody_A_pline_id_ComboboxData.add(data[i].pline_id)
                                    cookie_data.ProductControlOrderBody_A_complete_date_ComboboxData.add(data[i].complete_date)
                                    cookie_data.ProductControlOrderBody_A_est_complete_date_ComboboxData.add(data[i].est_complete_date)
                                    cookie_data.ProductControlOrderBody_A_is_closed_ComboboxData.add(data[i].is_closed)
                                    cookie_data.ProductControlOrderBody_A_is_re_make_ComboboxData.add(data[i].is_re_make)
                                    cookie_data.ProductControlOrderBody_A_est_output_ComboboxData.add(data[i].est_output)
                                    cookie_data.ProductControlOrderBody_A_est_start_date_ComboboxData.add(data[i].est_start_date)
                                }
                                //Log.d("GSON", "msg:${comboboxData}")
                            }
                            1->{
                                var fail= Gson().fromJson(responseinfo, Response::class.java)
                                cookie_data.msg =fail.msg
                                cookie_data.status =fail.status
                            }
                        }

                    }
                    job.join()


                }
            }
            is MeWorkstationBasicInfo ->{
                val body = FormBody.Builder()
                    .add("card_number", cookie_data.card_number)
                    .add("username", cookie_data.username)
                    .add("operation", operation)
                    .add("action", cookie_data.Actions.VIEW)
                    .add("view_type",view_type)
                    .add("view_hide",view_hide)
                    .add("csrfmiddlewaretoken", cookie_data.tokenValue)
                    .add("login_flag", cookie_data.loginflag)
                    .build()
                val request = Request.Builder()
                    .url(cookie_data.URL+"/basic_management")
                    .header("User-Agent", "ERP_MOBILE")
                    .post(body)
                    .build()
                runBlocking {
                    var job = CoroutineScope(Dispatchers.IO).launch {
                        var response = cookie_data.okHttpClient.newCall(request).execute()
                        var responseinfo=response.body?.string().toString()
                        var json= Gson().fromJson(responseinfo, ShowMeWorkstationBasicInfo::class.java)
                        when(json.status){
                            0->{
                                cookie_data.status =json.status
                                var data: ArrayList<MeWorkstationBasicInfo> =json.data
                                cookie_data.workstation_number_ComboboxData.removeAll(cookie_data.workstation_number_ComboboxData)

                                for(i in 0 until data.size){
                                    cookie_data.workstation_number_ComboboxData.add(data[i].workstation_number)

                                }
                                //Log.d("GSON", "msg:${comboboxData}")
                            }
                            1->{
                                var fail= Gson().fromJson(responseinfo, Response::class.java)
                                cookie_data.msg =fail.msg
                                cookie_data.status =fail.status
                            }
                        }

                    }
                    job.join()


                }
            }
            is MeWorkstationEquipmentBasicInfo ->{
                val body = FormBody.Builder()
                    .add("card_number", cookie_data.card_number)
                    .add("username", cookie_data.username)
                    .add("operation", operation)
                    .add("action", cookie_data.Actions.VIEW)
                    .add("view_type",view_type)
                    .add("view_hide",view_hide)
                    .add("csrfmiddlewaretoken", cookie_data.tokenValue)
                    .add("login_flag", cookie_data.loginflag)
                    .build()
                val request = Request.Builder()
                    .url(cookie_data.URL+"/basic_management")
                    .header("User-Agent", "ERP_MOBILE")
                    .post(body)
                    .build()
                runBlocking {
                    var job = CoroutineScope(Dispatchers.IO).launch {
                        var response = cookie_data.okHttpClient.newCall(request).execute()
                        var responseinfo=response.body?.string().toString()
                        var json= Gson().fromJson(responseinfo, ShowMeWorkstationEquipmentBasicInfo::class.java)
                        when(json.status){
                            0->{
                                cookie_data.status =json.status
                                var data: ArrayList<MeWorkstationEquipmentBasicInfo> =json.data
                                cookie_data.device_id_ComboboxData.removeAll(cookie_data.device_id_ComboboxData)

                                for(i in 0 until data.size){
                                    cookie_data.device_id_ComboboxData.add(data[i].device_id)

                                }
                                //Log.d("GSON", "msg:${comboboxData}")
                            }
                            1->{
                                var fail= Gson().fromJson(responseinfo, Response::class.java)
                                cookie_data.msg =fail.msg
                                cookie_data.status =fail.status
                            }
                        }

                    }
                    job.join()


                }
            }
            is VenderBasicInfo ->{
                val body = FormBody.Builder()
                    .add("card_number", cookie_data.card_number)
                    .add("username", cookie_data.username)
                    .add("operation", operation)
                    .add("action", cookie_data.Actions.VIEW)
                    .add("view_type",view_type)
                    .add("view_hide",view_hide)
                    .add("csrfmiddlewaretoken", cookie_data.tokenValue)
                    .add("login_flag", cookie_data.loginflag)
                    .build()
                val request = Request.Builder()
                    .url(cookie_data.URL+"/basic_management")
                    .header("User-Agent", "ERP_MOBILE")
                    .post(body)
                    .build()
                runBlocking {
                    var job = CoroutineScope(Dispatchers.IO).launch {
                        var response = cookie_data.okHttpClient.newCall(request).execute()
                        var responseinfo=response.body?.string().toString()
                        var json= Gson().fromJson(responseinfo, ShowVenderBasicInfo::class.java)
                        when(json.status){
                            0->{
                                cookie_data.status =json.status
                                var data: ArrayList<VenderBasicInfo> =json.data
                                cookie_data.vender_id_ComboboxData.removeAll(cookie_data.vender_id_ComboboxData)
                                cookie_data.vender_abbreviation_ComboboxData.removeAll(cookie_data.vender_abbreviation_ComboboxData)
                                for(i in 0 until data.size){
                                    cookie_data.vender_id_ComboboxData.add(data[i]._id)
                                    cookie_data.vender_abbreviation_ComboboxData.add(data[i].abbreviation)
                                }
                                //Log.d("GSON", "msg:${comboboxData}")
                            }
                            1->{
                                var fail= Gson().fromJson(responseinfo, Response::class.java)
                                cookie_data.msg =fail.msg
                                cookie_data.status =fail.status
                            }
                        }

                    }
                    job.join()


                }
            }
            is EquipmentMaintenanceType ->{
                val body = FormBody.Builder()
                    .add("username", cookie_data.username)
                    .add("operation", operation)
                    .add("action", cookie_data.Actions.VIEW)
                    .add("view_type",view_type)
                    .add("view_hide",view_hide)
                    .add("csrfmiddlewaretoken", cookie_data.tokenValue)
                    .add("login_flag", cookie_data.loginflag)
                    .build()
                val request = Request.Builder()
                    .url(cookie_data.URL+"/def_management")
                    .header("User-Agent", "ERP_MOBILE")
                    .post(body)
                    .build()
                runBlocking {
                    var job = CoroutineScope(Dispatchers.IO).launch {
                        var response = cookie_data.okHttpClient.newCall(request).execute()
                        var responseinfo=response.body?.string().toString()
                        var json= Gson().fromJson(responseinfo, ShowEquipmentMaintenanceType::class.java)
                        when(json.status){
                            0->{
                                cookie_data.status =json.status
                                var data: ArrayList<EquipmentMaintenanceType> =json.data
                                cookie_data.maintain_type_ComboboxData.removeAll(cookie_data.maintain_type_ComboboxData)
                                for(i in 0 until data.size){
                                    cookie_data.maintain_type_ComboboxData.add(data[i].maintain_type)
                                }
                                //Log.d("GSON", "msg:${comboboxData}")
                            }
                            1->{
                                var fail= Gson().fromJson(responseinfo, Response::class.java)
                                cookie_data.msg =fail.msg
                                cookie_data.status =fail.status
                            }
                        }

                    }
                    job.join()


                }
            }
            is VenderShipmentHeader ->{
                val body = FormBody.Builder()
                    .add("username", cookie_data.username)
                    .add("operation", operation)
                    .add("action", cookie_data.Actions.VIEW)
                    .add("view_type",view_type)
                    .add("view_hide",view_hide)
                    .add("csrfmiddlewaretoken", cookie_data.tokenValue)
                    .add("login_flag", cookie_data.loginflag)
                    .build()
                val request = Request.Builder()
                    .url(cookie_data.URL+"/purchase_order_management")
                    .header("User-Agent", "ERP_MOBILE")
                    .post(body)
                    .build()
                runBlocking {
                    var job = CoroutineScope(Dispatchers.IO).launch {
                        var response = cookie_data.okHttpClient.newCall(request).execute()
                        var responseinfo=response.body?.string().toString()
                        var json= Gson().fromJson(responseinfo, ShowVenderShipmentHeader::class.java)
                        when(json.status){
                            0->{
                                cookie_data.status=json.status
                                var data: ArrayList<VenderShipmentHeader> =json.data
                                cookie_data.VenderShipmentHeader_poNo_ComboboxData.removeAll(
                                    cookie_data.VenderShipmentHeader_poNo_ComboboxData)
                                cookie_data.VenderShipmentHeader_purchase_date_ComboboxData.removeAll(
                                    cookie_data.VenderShipmentHeader_purchase_date_ComboboxData)
                                cookie_data.VenderShipmentHeader_vender_id_ComboboxData.removeAll(
                                    cookie_data.VenderShipmentHeader_vender_id_ComboboxData)
                                cookie_data.VenderShipmentHeader_vender_shipment_id_ComboboxData.removeAll(
                                    cookie_data.VenderShipmentHeader_vender_shipment_id_ComboboxData)
                                for(i in 0 until data.size){
                                    cookie_data.VenderShipmentHeader_poNo_ComboboxData.add(data[i].poNo)
                                    cookie_data.VenderShipmentHeader_purchase_date_ComboboxData.add(data[i].purchase_date)
                                    cookie_data.VenderShipmentHeader_vender_id_ComboboxData.add(data[i].vender_id)
                                    cookie_data.VenderShipmentHeader_vender_shipment_id_ComboboxData.add(data[i].vender_shipment_id)

                                }
                                //Log.d("GSON", "msg:${comboboxData}")
                            }
                            1->{
                                var fail= Gson().fromJson(responseinfo, Response::class.java)
                                cookie_data.msg=fail.msg
                                cookie_data.status=fail.status
                            }
                        }

                    }
                    job.join()


                }
            }


        }


    }




    //點擊旁邊自動收起鍵盤
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }
}