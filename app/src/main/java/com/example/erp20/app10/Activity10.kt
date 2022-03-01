package com.example.erp20.app10

import android.app.DatePickerDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.erp20.Model.*
import com.example.erp20.R
import com.example.erp20.app06.RecyclerItemStockTransOrderHeaderSimplifyAdapter3
import com.example.erp20.cookie_data
import com.google.android.material.textfield.TextInputEditText
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

class Activity10 : AppCompatActivity() {
    private lateinit var PurchaseBatchOrderTotal_adapter: RecyclerItemPurchaseBatchOrderTotalAdapter
    private lateinit var ProductControlOrderBodyDTotal_adapter: RecyclerItemProductControlOrderBodyDTotalAdapter
    var comboboxData: MutableList<String> = mutableListOf<String>()
    lateinit var  allComboboxData: java.util.ArrayList<StockTransOrderBody>
    lateinit var selectFilter:String
    lateinit var selectFilter2:String
    lateinit var selectFilter3:String
    lateinit var selectFilter4:String
    lateinit var selectFilter5:String
    lateinit var recyclerView: RecyclerView
    val dateF2 = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_10)
    }

    override fun onResume() {
        super.onResume()


        val recyclerView=findViewById<RecyclerView>(R.id.recyclerView)

        //combobox選單內容
        val autoCompleteTextView=findViewById<AutoCompleteTextView>(R.id.autoCompleteText)
        val combobox=resources.getStringArray(R.array.combobox10)
        val arrayAdapter= ArrayAdapter(this,R.layout.combobox_item,combobox)
        autoCompleteTextView.setAdapter(arrayAdapter)

        val searchbtn=findViewById<Button>(R.id.search_btn)
       // val find_btn=findViewById<Button>(R.id.find_btn)

        //搜尋按鈕
        searchbtn?.setOnClickListener {


            when(autoCompleteTextView?.text.toString()){
                "採購件"->{
                    //show_combobox("StockTransOrderBody","condition","False",StockTransOrderBody())
                    show_relative_combobox("ItemBasicInfo","all","False", ItemBasicInfo())
                    show_relative_combobox("VenderBasicInfo","all","False", VenderBasicInfo())
                    show_relative_combobox("ProductControlOrderBody_A","all","False", ProductControlOrderBody_A())
                    show_relative_combobox("VenderShipmentHeader","all","False", VenderShipmentHeader())
                    val item = LayoutInflater.from(this).inflate(R.layout.filter_combobox3, null)
                    val mAlertDialog = AlertDialog.Builder(this)
                    //mAlertDialog.setIcon(R.mipmap.ic_launcher_round) //set alertdialog icon
                    mAlertDialog.setTitle("篩選") //set alertdialog title
                    //mAlertDialog.setMessage("確定要登出?") //set alertdialog message
                    mAlertDialog.setView(item)
                    //filter_combobox選單內容
                    val comboboxView=item.findViewById<AutoCompleteTextView>(R.id.autoCompleteText)//進貨日期
                    val comboboxView2=item.findViewById<AutoCompleteTextView>(R.id.autoCompleteText2)//廠商
                    val comboboxView3=item.findViewById<AutoCompleteTextView>(R.id.autoCompleteText3)//料件
                    val comboboxView4=item.findViewById<AutoCompleteTextView>(R.id.autoCompleteText4)//生產管制單編號
                    val comboboxView5=item.findViewById<TextInputEditText>(R.id.autoCompleteText5)//廠商出貨單號
                    val comboboxView6=item.findViewById<AutoCompleteTextView>(R.id.autoCompleteText6)//僅顯示未進貨明細

                    val dateF = SimpleDateFormat("yyyy-MM-dd(EEEE)", Locale.TAIWAN)
                    var C= Calendar.getInstance()
                    //C.set(Calendar.DAY_OF_MONTH,1)
                    var Date= dateF.format(C.time)
                    comboboxView.setText(Date)
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

                                if(comboboxView6.text.toString()=="true"){
                                    comboboxData.removeAll(comboboxData)
                                    for(i in 0 until cookie_data.ProductControlOrderBody_A_prod_ctrl_order_number_ComboboxData.size){
                                        if(cookie_data.ProductControlOrderBody_A_est_complete_date_ComboboxData[i]!=null){
                                            var date1=dateF2.parse(cookie_data.ProductControlOrderBody_A_est_complete_date_ComboboxData[i])
                                            var date2=dateF2.parse(comboboxView.text.toString())
                                            if(date1.compareTo(date2)>=0 && cookie_data.ProductControlOrderBody_A_is_closed_ComboboxData[i]==false){
                                                comboboxData.add(cookie_data.ProductControlOrderBody_A_prod_ctrl_order_number_ComboboxData[i]+"\n"+
                                                        "實際完工日:"+cookie_data.ProductControlOrderBody_A_complete_date_ComboboxData[i]+"\n"+
                                                        "預計完工日:"+cookie_data.ProductControlOrderBody_A_est_complete_date_ComboboxData[i]//+"\n"+
                                                    //cookie_data.item_name_ComboboxData[cookie_data.semi_finished_product_number_ComboboxData.indexOf(cookie_data.ProductControlOrderBody_A_semi_finished_prod_number_ComboboxData[i])]
                                                )
                                            }
                                        }
                                    }
                                }
                                else if(comboboxView6.text.toString()=="false"){
                                    comboboxData.removeAll(comboboxData)
                                    for(i in 0 until cookie_data.ProductControlOrderBody_A_prod_ctrl_order_number_ComboboxData.size){
                                        if(cookie_data.ProductControlOrderBody_A_est_complete_date_ComboboxData[i]!=null){
                                            var date1=dateF2.parse(cookie_data.ProductControlOrderBody_A_est_complete_date_ComboboxData[i])
                                            var date2=dateF2.parse(comboboxView.text.toString())
                                            if(date1.compareTo(date2)>=0){
                                                comboboxData.add(cookie_data.ProductControlOrderBody_A_prod_ctrl_order_number_ComboboxData[i]+"\n"+
                                                        "實際完工日:"+cookie_data.ProductControlOrderBody_A_complete_date_ComboboxData[i]+"\n"+
                                                        "預計完工日:"+cookie_data.ProductControlOrderBody_A_est_complete_date_ComboboxData[i]//+"\n"+
                                                    //cookie_data.item_name_ComboboxData[cookie_data.semi_finished_product_number_ComboboxData.indexOf(cookie_data.ProductControlOrderBody_A_semi_finished_prod_number_ComboboxData[i])]
                                                )
                                            }
                                        }
                                    }
                                }

                            },year,month,day).show()
                    }

                    val arrayAdapter2= ArrayAdapter(this,R.layout.combobox_item,cookie_data.vender_id_halfname_ComboboxData)
                    val arrayAdapter3= ArrayAdapter(this,R.layout.combobox_item,cookie_data.item_id_name_ComboboxData)
                    var arrayAdapter4= ArrayAdapter(this,R.layout.combobox_item,comboboxData )
                    val arrayAdapter6= ArrayAdapter(this,R.layout.combobox_item,resources.getStringArray(R.array.combobox_yes_no))
                    comboboxView2.setAdapter(arrayAdapter2)
                    comboboxView3.setAdapter(arrayAdapter3)
                    comboboxView4.setAdapter(arrayAdapter4)
                    comboboxView6.setAdapter(arrayAdapter6)
                    comboboxView6.setOnItemClickListener { parent, view, position, id ->
                        if(position==0){//false
                            comboboxData.removeAll(comboboxData)
                            for(i in 0 until cookie_data.ProductControlOrderBody_A_prod_ctrl_order_number_ComboboxData.size){
                                if(cookie_data.ProductControlOrderBody_A_est_complete_date_ComboboxData[i]!=null){
                                    var date1=dateF2.parse(cookie_data.ProductControlOrderBody_A_est_complete_date_ComboboxData[i])
                                    var date2=dateF2.parse(comboboxView.text.toString())
                                    if(date1.compareTo(date2)>=0 ){
                                        comboboxData.add(cookie_data.ProductControlOrderBody_A_prod_ctrl_order_number_ComboboxData[i]+"\n"+
                                                "實際完工日:"+cookie_data.ProductControlOrderBody_A_complete_date_ComboboxData[i]+"\n"+
                                                "預計完工日:"+cookie_data.ProductControlOrderBody_A_est_complete_date_ComboboxData[i]//+"\n"+
                                            //cookie_data.item_name_ComboboxData[cookie_data.semi_finished_product_number_ComboboxData.indexOf(cookie_data.ProductControlOrderBody_A_semi_finished_prod_number_ComboboxData[i])]
                                        )
                                    }
                                }
                            }
                            arrayAdapter4= ArrayAdapter(this,R.layout.combobox_item,comboboxData )
                            comboboxView4.setAdapter(arrayAdapter4)
                        }
                        else if(position==1){//true
                            comboboxData.removeAll(comboboxData)
                            for(i in 0 until cookie_data.ProductControlOrderBody_A_prod_ctrl_order_number_ComboboxData.size){
                                if(cookie_data.ProductControlOrderBody_A_est_complete_date_ComboboxData[i]!=null){
                                    var date1=dateF2.parse(cookie_data.ProductControlOrderBody_A_est_complete_date_ComboboxData[i])
                                    var date2=dateF2.parse(comboboxView.text.toString())
                                    if(date1.compareTo(date2)>=0 && cookie_data.ProductControlOrderBody_A_is_closed_ComboboxData[i]==false){
                                        comboboxData.add(cookie_data.ProductControlOrderBody_A_prod_ctrl_order_number_ComboboxData[i]+"\n"+
                                                "實際完工日:"+cookie_data.ProductControlOrderBody_A_complete_date_ComboboxData[i]+"\n"+
                                                "預計完工日:"+cookie_data.ProductControlOrderBody_A_est_complete_date_ComboboxData[i]//+"\n"+
                                            //cookie_data.item_name_ComboboxData[cookie_data.semi_finished_product_number_ComboboxData.indexOf(cookie_data.ProductControlOrderBody_A_semi_finished_prod_number_ComboboxData[i])]
                                        )
                                    }
                                }
                            }
                            arrayAdapter4= ArrayAdapter(this,R.layout.combobox_item,comboboxData )
                            comboboxView4.setAdapter(arrayAdapter4)
                        }

                    }
                    mAlertDialog.setPositiveButton("取消") { dialog, id ->
                        dialog.dismiss()
                    }
                    mAlertDialog.setNegativeButton("確定") { dialog, id ->
                        //println(comboboxView.text)
                        selectFilter=comboboxView.text.toString().substring(0,comboboxView.text.toString().indexOf("("))//date
                        if(comboboxView2.text.toString()!=""){
                            selectFilter2=comboboxView2.text.toString().substring(0,comboboxView2.text.toString().indexOf(" "))//廠商
                        }
                        else{
                            selectFilter2=""
                        }
                        if(comboboxView3.text.toString()!=""){
                            selectFilter3=comboboxView3.text.toString().substring(0,comboboxView3.text.toString().indexOf(" "))//料件
                        }
                        else {
                            selectFilter3=""
                        }
                        if(comboboxView4.text.toString()!=""){
                            selectFilter4=comboboxView4.text.toString().substring(0,comboboxView4.text.toString().indexOf("&"))//生產管制單號
                        }
                        else {
                            selectFilter4=""
                        }

                        println(selectFilter+"\n"+selectFilter2+"\n"+selectFilter3+"\n"+selectFilter4)
                        show_header_body_filter("PurchaseBatchOrder","condition","False","123")
                        show_relative_combobox("SysArgPur","all","False", SysArgPur())
                        show_relative_combobox("staffBasicInfo","all","False", staffBasicInfo())
                        show_relative_combobox("PurchaseOrderHeader","all","False", PurchaseOrderHeader())
                        show_relative_combobox("PurchaseOrderBody","all","False", PurchaseOrderBody())
                        when(cookie_data.status)
                        {
                            0->{
                                Toast.makeText(this, "資料載入", Toast.LENGTH_SHORT).show()
                                recyclerView.layoutManager= LinearLayoutManager(this)//設定Linear格式
                                PurchaseBatchOrderTotal_adapter= RecyclerItemPurchaseBatchOrderTotalAdapter(selectFilter,selectFilter2,selectFilter3,selectFilter4,comboboxView6.text.toString(),comboboxView5.text.toString())
                                recyclerView.adapter=PurchaseBatchOrderTotal_adapter//找對應itemAdapter
                                cookie_data.recyclerView=recyclerView
                                //find_btn?.isEnabled=true
                            }
                            1->{
                                Toast.makeText(this, cookie_data.msg, Toast.LENGTH_SHORT).show()
                            }
                        }

                    }
                    mAlertDialog.show()

                }
                "託外加工件"->{
                    //show_combobox("StockTransOrderBody","condition","False",StockTransOrderBody())
                    show_relative_combobox("ItemBasicInfo","all","False", ItemBasicInfo())
                    show_relative_combobox("PLineBasicInfo","all","False", PLineBasicInfo())
                    show_relative_combobox("VenderBasicInfo","all","False", VenderBasicInfo())
                    show_relative_combobox("ProductControlOrderBody_A","all","False", ProductControlOrderBody_A())
                    show_relative_combobox("VenderShipmentHeader","all","False", VenderShipmentHeader())
                    val item = LayoutInflater.from(this).inflate(R.layout.filter_combobox3, null)
                    val mAlertDialog = AlertDialog.Builder(this)
                    //mAlertDialog.setIcon(R.mipmap.ic_launcher_round) //set alertdialog icon
                    mAlertDialog.setTitle("篩選") //set alertdialog title
                    //mAlertDialog.setMessage("確定要登出?") //set alertdialog message
                    mAlertDialog.setView(item)
                    //filter_combobox選單內容
                    val comboboxView=item.findViewById<AutoCompleteTextView>(R.id.autoCompleteText)//進貨日期
                    val comboboxView2=item.findViewById<AutoCompleteTextView>(R.id.autoCompleteText2)//廠商
                    val comboboxView3=item.findViewById<AutoCompleteTextView>(R.id.autoCompleteText3)//料件
                    val comboboxView4=item.findViewById<AutoCompleteTextView>(R.id.autoCompleteText4)//生產管制單編號
                    val comboboxView5=item.findViewById<TextInputEditText>(R.id.autoCompleteText5)//廠商出貨單號
                    val comboboxView6=item.findViewById<AutoCompleteTextView>(R.id.autoCompleteText6)//僅顯示未進貨明細

                    val dateF = SimpleDateFormat("yyyy-MM-dd(EEEE)", Locale.TAIWAN)
                    var C= Calendar.getInstance()
                    //C.set(Calendar.DAY_OF_MONTH,1)
                    var Date= dateF.format(C.time)
                    comboboxView.setText(Date)
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

                                if(comboboxView6.text.toString()=="true"){
                                    comboboxData.removeAll(comboboxData)
                                    for(i in 0 until cookie_data.ProductControlOrderBody_A_prod_ctrl_order_number_ComboboxData.size){
                                        if(cookie_data.ProductControlOrderBody_A_est_complete_date_ComboboxData[i]!=null){
                                            var date1=dateF2.parse(cookie_data.ProductControlOrderBody_A_est_complete_date_ComboboxData[i])
                                            var date2=dateF2.parse(comboboxView.text.toString())
                                            if(date1.compareTo(date2)>=0 && cookie_data.ProductControlOrderBody_A_is_closed_ComboboxData[i]==false){
                                                comboboxData.add(cookie_data.ProductControlOrderBody_A_prod_ctrl_order_number_ComboboxData[i]+"\n"+
                                                        "實際完工日:"+cookie_data.ProductControlOrderBody_A_complete_date_ComboboxData[i]+"\n"+
                                                        "預計完工日:"+cookie_data.ProductControlOrderBody_A_est_complete_date_ComboboxData[i]//+"\n"+
                                                    //cookie_data.item_name_ComboboxData[cookie_data.semi_finished_product_number_ComboboxData.indexOf(cookie_data.ProductControlOrderBody_A_semi_finished_prod_number_ComboboxData[i])]
                                                )
                                            }
                                        }
                                    }
                                }
                                else if(comboboxView6.text.toString()=="false"){
                                    comboboxData.removeAll(comboboxData)
                                    for(i in 0 until cookie_data.ProductControlOrderBody_A_prod_ctrl_order_number_ComboboxData.size){
                                        if(cookie_data.ProductControlOrderBody_A_est_complete_date_ComboboxData[i]!=null){
                                            var date1=dateF2.parse(cookie_data.ProductControlOrderBody_A_est_complete_date_ComboboxData[i])
                                            var date2=dateF2.parse(comboboxView.text.toString())
                                            if(date1.compareTo(date2)>=0){
                                                comboboxData.add(cookie_data.ProductControlOrderBody_A_prod_ctrl_order_number_ComboboxData[i]+"\n"+
                                                        "實際完工日:"+cookie_data.ProductControlOrderBody_A_complete_date_ComboboxData[i]+"\n"+
                                                        "預計完工日:"+cookie_data.ProductControlOrderBody_A_est_complete_date_ComboboxData[i]//+"\n"+
                                                    //cookie_data.item_name_ComboboxData[cookie_data.semi_finished_product_number_ComboboxData.indexOf(cookie_data.ProductControlOrderBody_A_semi_finished_prod_number_ComboboxData[i])]
                                                )
                                            }
                                        }
                                    }
                                }

                            },year,month,day).show()
                    }

                    val arrayAdapter2= ArrayAdapter(this,R.layout.combobox_item,cookie_data.vender_id_halfname_ComboboxData)
                    val arrayAdapter3= ArrayAdapter(this,R.layout.combobox_item,cookie_data.item_id_name_ComboboxData)
                    var arrayAdapter4= ArrayAdapter(this,R.layout.combobox_item,comboboxData )
                    val arrayAdapter6= ArrayAdapter(this,R.layout.combobox_item,resources.getStringArray(R.array.combobox_yes_no))
                    comboboxView2.setAdapter(arrayAdapter2)
                    comboboxView3.setAdapter(arrayAdapter3)
                    comboboxView4.setAdapter(arrayAdapter4)
                    comboboxView6.setAdapter(arrayAdapter6)
                    comboboxView6.setOnItemClickListener { parent, view, position, id ->
                        if(position==0){//false
                            comboboxData.removeAll(comboboxData)
                            for(i in 0 until cookie_data.ProductControlOrderBody_A_prod_ctrl_order_number_ComboboxData.size){
                                if(cookie_data.ProductControlOrderBody_A_est_complete_date_ComboboxData[i]!=null){
                                    var date1=dateF2.parse(cookie_data.ProductControlOrderBody_A_est_complete_date_ComboboxData[i])
                                    var date2=dateF2.parse(comboboxView.text.toString())
                                    if(date1.compareTo(date2)>=0 ){
                                        comboboxData.add(cookie_data.ProductControlOrderBody_A_prod_ctrl_order_number_ComboboxData[i]+"\n"+
                                                "實際完工日:"+cookie_data.ProductControlOrderBody_A_complete_date_ComboboxData[i]+"\n"+
                                                "預計完工日:"+cookie_data.ProductControlOrderBody_A_est_complete_date_ComboboxData[i]//+"\n"+
                                            //cookie_data.item_name_ComboboxData[cookie_data.semi_finished_product_number_ComboboxData.indexOf(cookie_data.ProductControlOrderBody_A_semi_finished_prod_number_ComboboxData[i])]
                                        )
                                    }
                                }
                            }
                            arrayAdapter4= ArrayAdapter(this,R.layout.combobox_item,comboboxData )
                            comboboxView4.setAdapter(arrayAdapter4)
                        }
                        else if(position==1){//true
                            comboboxData.removeAll(comboboxData)
                            for(i in 0 until cookie_data.ProductControlOrderBody_A_prod_ctrl_order_number_ComboboxData.size){
                                if(cookie_data.ProductControlOrderBody_A_est_complete_date_ComboboxData[i]!=null){
                                    var date1=dateF2.parse(cookie_data.ProductControlOrderBody_A_est_complete_date_ComboboxData[i])
                                    var date2=dateF2.parse(comboboxView.text.toString())
                                    if(date1.compareTo(date2)>=0 && cookie_data.ProductControlOrderBody_A_is_closed_ComboboxData[i]==false){
                                        comboboxData.add(cookie_data.ProductControlOrderBody_A_prod_ctrl_order_number_ComboboxData[i]+"\n"+
                                                "實際完工日:"+cookie_data.ProductControlOrderBody_A_complete_date_ComboboxData[i]+"\n"+
                                                "預計完工日:"+cookie_data.ProductControlOrderBody_A_est_complete_date_ComboboxData[i]//+"\n"+
                                            //cookie_data.item_name_ComboboxData[cookie_data.semi_finished_product_number_ComboboxData.indexOf(cookie_data.ProductControlOrderBody_A_semi_finished_prod_number_ComboboxData[i])]
                                        )
                                    }
                                }
                            }
                            arrayAdapter4= ArrayAdapter(this,R.layout.combobox_item,comboboxData )
                            comboboxView4.setAdapter(arrayAdapter4)
                        }

                    }
                    mAlertDialog.setPositiveButton("取消") { dialog, id ->
                        dialog.dismiss()
                    }
                    mAlertDialog.setNegativeButton("確定") { dialog, id ->
                        //println(comboboxView.text)
                        selectFilter=comboboxView.text.toString().substring(0,comboboxView.text.toString().indexOf("("))//date
                        if(comboboxView2.text.toString()!=""){
                            selectFilter2=comboboxView2.text.toString().substring(0,comboboxView2.text.toString().indexOf(" "))//廠商
                        }
                        else{
                            selectFilter2=""
                        }
                        if(comboboxView3.text.toString()!=""){
                            selectFilter3=comboboxView3.text.toString().substring(0,comboboxView3.text.toString().indexOf(" "))//料件
                        }
                        else {
                            selectFilter3=""
                        }
                        if(comboboxView4.text.toString()!=""){
                            selectFilter4=comboboxView4.text.toString().substring(0,comboboxView4.text.toString().indexOf("&"))//生產管制單號
                        }
                        else {
                            selectFilter4=""
                        }

                        println(selectFilter+"\n"+selectFilter2+"\n"+selectFilter3+"\n"+selectFilter4)
                        show_header_body_filter("ProductControlOrderBody_D","condition","False","123")
                        show_relative_combobox("staffBasicInfo","all","False", staffBasicInfo())
                        when(cookie_data.status)
                        {
                            0->{
                                Toast.makeText(this, "資料載入", Toast.LENGTH_SHORT).show()
                                recyclerView.layoutManager= LinearLayoutManager(this)//設定Linear格式
                                ProductControlOrderBodyDTotal_adapter= RecyclerItemProductControlOrderBodyDTotalAdapter(selectFilter,selectFilter2,selectFilter3,selectFilter4,comboboxView6.text.toString(),comboboxView5.text.toString())
                                recyclerView.adapter=ProductControlOrderBodyDTotal_adapter//找對應itemAdapter
                                cookie_data.recyclerView=recyclerView
                                //find_btn?.isEnabled=true
                            }
                            1->{
                                Toast.makeText(this, cookie_data.msg, Toast.LENGTH_SHORT).show()
                            }
                        }

                    }
                    mAlertDialog.show()

                }
            }
        }
        //查詢按鈕
        /*find_btn?.setOnClickListener {
            when(autoCompleteTextView?.text.toString()){
                "備料/發料作業"->{
                    show_combobox("StockTransOrderBody","all","False",StockTransOrderBody())
                    show_relative_combobox("ItemBasicInfo","all","False", ItemBasicInfo())
                    show_relative_combobox("PLineBasicInfo","all","False", PLineBasicInfo())
                    show_relative_combobox("StockTransOrderHeader","all","False", StockTransOrderHeader())
                    val item = LayoutInflater.from(this).inflate(R.layout.filter_combobox2, null)
                    val mAlertDialog = AlertDialog.Builder(this)
                    //mAlertDialog.setIcon(R.mipmap.ic_launcher_round) //set alertdialog icon
                    mAlertDialog.setTitle("篩選") //set alertdialog title
                    //mAlertDialog.setMessage("確定要登出?") //set alertdialog message
                    mAlertDialog.setView(item)
                    //filter_combobox選單內容
                    val comboboxView=item.findViewById<AutoCompleteTextView>(R.id.autoCompleteText)//選擇項目
                    val comboboxView2=item.findViewById<AutoCompleteTextView>(R.id.autoCompleteText2)//內容
                    comboboxView.setText("需求日期")
                    val arrayAdapter1= ArrayAdapter(this,R.layout.combobox_item,resources.getStringArray(R.array.combobox07_filter))
                    comboboxView.setAdapter(arrayAdapter1)
                    comboboxView.setOnItemClickListener { parent, view, position, id ->
                        comboboxView2.setText("")
                        if(position==1){//料件編號/名稱
                            comboboxData.removeAll(comboboxData)
                            for(i in 0 until allComboboxData.size){
                                if(cookie_data.StockTransOrderHeader_main_trans_code_ComboboxData[cookie_data.StockTransOrderHeader_id_ComboboxData.indexOf(allComboboxData[i].header_id)]=="M02"){
                                    comboboxData.add(allComboboxData[i].item_id+"\n"+cookie_data.item_name_ComboboxData[cookie_data.item_id_ComboboxData.indexOf(allComboboxData[i].item_id)])
                                }
                            }
                            comboboxData=comboboxData.distinct().toCollection(java.util.ArrayList())
                            //comboboxData.remove("")
                            //Log.d("GSON", "msg:${comboboxData}")
                            val arrayAdapter2= ArrayAdapter(this,R.layout.combobox_item,comboboxData)
                            comboboxView2.setAdapter(arrayAdapter2)
                        }
                        else if(position==2){//生產管制單編號
                            comboboxData.removeAll(comboboxData)
                            for(i in 0 until allComboboxData.size){
                                if(cookie_data.StockTransOrderHeader_main_trans_code_ComboboxData[cookie_data.StockTransOrderHeader_id_ComboboxData.indexOf(allComboboxData[i].header_id)]=="M02"){
                                    comboboxData.add(cookie_data.StockTransOrderHeader_prod_ctrl_order_number_ComboboxData[cookie_data.StockTransOrderHeader_id_ComboboxData.indexOf(allComboboxData[i].header_id)])
                                }
                            }
                            comboboxData=comboboxData.distinct().toCollection(java.util.ArrayList())
                            // comboboxData.remove("")
                            //Log.d("GSON", "msg:${comboboxData}")
                            val arrayAdapter2= ArrayAdapter(this,R.layout.combobox_item,comboboxData)
                            comboboxView2.setAdapter(arrayAdapter2)
                        }
                        else if(position==3){//需求單位
                            comboboxData.removeAll(comboboxData)
                            for(i in 0 until allComboboxData.size){
                                if(cookie_data.StockTransOrderHeader_main_trans_code_ComboboxData[cookie_data.StockTransOrderHeader_id_ComboboxData.indexOf(allComboboxData[i].header_id)]=="M02"){
                                    comboboxData.add(cookie_data.pline_id_name_ComboboxData[cookie_data.pline_id_ComboboxData.indexOf(cookie_data.StockTransOrderHeader_dept_ComboboxData[cookie_data.StockTransOrderHeader_id_ComboboxData.indexOf(allComboboxData[i].header_id)])])
                                }
                            }
                            comboboxData=comboboxData.distinct().toCollection(java.util.ArrayList())
                            //comboboxData.remove("")
                            //Log.d("GSON", "msg:${comboboxData}")
                            val arrayAdapter2= ArrayAdapter(this,R.layout.combobox_item,comboboxData)
                            comboboxView2.setAdapter(arrayAdapter2)
                        }
                    }
                    val dateF = SimpleDateFormat("yyyy-MM-dd(EEEE)", Locale.TAIWAN)
                    var C= Calendar.getInstance()
                    C.set(Calendar.DAY_OF_MONTH,1)
                    var Date= dateF.format(C.time)
                    comboboxView2.setText(Date)
                    comboboxView2.setOnClickListener {
                        if(comboboxView.text.toString()=="需求日期"){
                            comboboxView2.setAdapter(null)
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
                                    comboboxView2.setText(Date1)
                                },year,month,day).show()
                        }
                    }
                    mAlertDialog.setPositiveButton("取消") { dialog, id ->
                        dialog.dismiss()
                    }
                    mAlertDialog.setNegativeButton("確定") { dialog, id ->
                        //println(comboboxView.text)
                        selectFilter=comboboxView.text.toString()
                        if(comboboxView.text.toString()=="需求日期"){
                            selectFilter2=comboboxView2.text.toString().substring(0,comboboxView2.text.toString().indexOf("("))//date
                        }
                        else if(comboboxView.text.toString()=="料件編號/名稱"){
                            selectFilter2=comboboxView2.text.toString().substring(0,comboboxView2.text.toString().indexOf("\n"))
                        }
                        else if(comboboxView.text.toString()=="生產管制單編號"){
                            selectFilter2=comboboxView2.text.toString()
                        }
                        else if(comboboxView.text.toString()=="需求單位"){
                            selectFilter2=comboboxView2.text.toString().substring(0,comboboxView2.text.toString().indexOf(" "))//pline
                        }
                        //println(selectFilter)
                        show_header_body("StockTransOrderBody","all","False")
                        show_relative_combobox("StoreArea","all","False", StoreArea())
                        show_relative_combobox("StoreLocal","all","False", StoreLocal())
                        show_relative_combobox("PLineBasicInfo","all","False", PLineBasicInfo())
                        show_relative_combobox("InvChangeTypeM","all","False", InvChangeTypeM())
                        show_relative_combobox("InvChangeTypeS","all","False", InvChangeTypeS())
                        //println(cookie_data.StockTransOrderHeader_id_ComboboxData)
                        when(cookie_data.status)
                        {
                            0->{
                                Toast.makeText(this, "資料載入", Toast.LENGTH_SHORT).show()
                                recyclerView.layoutManager= LinearLayoutManager(this)//設定Linear格式
                                StockTransOrderBodyWithHeaderLookup_adapter= RecyclerItemStockTransOrderBodyWithHeaderLookupAdapter(selectFilter,selectFilter2)
                                recyclerView.adapter=StockTransOrderBodyWithHeaderLookup_adapter//找對應itemAdapter
                                cookie_data.recyclerView=recyclerView
                            }
                            1->{
                                Toast.makeText(this, cookie_data.msg, Toast.LENGTH_SHORT).show()
                            }
                        }

                    }
                    mAlertDialog.show()

                }

            }

        }*/

    }



    private fun show_header_body(operation:String,view_type:String,view_hide:String) {
        when(operation){
            "StockTransOrderBody"->{
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
                    .url(cookie_data.URL+"/inventory_management")
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
    private fun <T>add_header_body(operation:String,addData:T) {

        when(addData)
        {
            is StockTransOrderHeader ->{
                val add = JSONObject()
                add.put("_id",addData._id)
                add.put("dept",addData.dept)
                add.put("date",addData.date)
                add.put("main_trans_code",addData.main_trans_code)
                add.put("sec_trans_code",addData.sec_trans_code)
                add.put("prod_ctrl_order_number",addData.prod_ctrl_order_number)
                add.put("illustrate",addData.illustrate)


                add.put("remark",addData.remark)
                add.put("creator", cookie_data.username)
                add.put("editor", cookie_data.username)
                Log.d("GSON", "msg:${add}")
                val body = FormBody.Builder()
                    .add("username", cookie_data.username)
                    .add("operation", operation)
                    .add("action", cookie_data.Actions.ADD)
                    .add("data",add.toString())
                    .add("csrfmiddlewaretoken", cookie_data.tokenValue)
                    .add("login_flag", cookie_data.loginflag)
                    .build()

                val request = Request.Builder()
                    .url(cookie_data.URL+"/inventory_management")
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
                    cookie_data.msg=responseInfo.msg
                }
            }
            is StockTransOrderBody ->{
                val add = JSONObject()
                add.put("header_id",addData.header_id)
                add.put("item_id",addData.item_id)
                add.put("modify_count",addData.modify_count)
                add.put("main_trans_code",addData.main_trans_code)
                add.put("sec_trans_code",addData.sec_trans_code)
                add.put("store_area",addData.store_area)
                add.put("store_local",addData.store_local)
                add.put("qc_insp_number",addData.qc_insp_number)
                add.put("qc_time",addData.qc_time)
                add.put("ok_count",addData.ok_count)
                add.put("ng_count",addData.ng_count)
                add.put("scrapped_count",addData.scrapped_count)
                add.put("is_rework",addData.is_rework)


                add.put("remark",addData.remark)
                add.put("creator", cookie_data.username)
                add.put("editor", cookie_data.username)
                Log.d("GSON", "msg:${add}")
                val body = FormBody.Builder()
                    .add("username", cookie_data.username)
                    .add("operation", operation)
                    .add("action", cookie_data.Actions.ADD)
                    .add("data",add.toString())
                    .add("csrfmiddlewaretoken", cookie_data.tokenValue)
                    .add("login_flag", cookie_data.loginflag)
                    .build()

                val request = Request.Builder()
                    .url(cookie_data.URL+"/inventory_management")
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
                    cookie_data.msg=responseInfo.msg
                }
            }

        }


    }

    private fun <T>show_combobox(operation:String,view_type:String,view_hide:String,fmt:T) {
        when(fmt)
        {
            is StockTransOrderBody ->{
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
                    .url(cookie_data.URL+"/inventory_management")
                    .header("User-Agent", "ERP_MOBILE")
                    .post(body)
                    .build()
                runBlocking {
                    var job = CoroutineScope(Dispatchers.IO).launch {
                        var response = cookie_data.okHttpClient.newCall(request).execute()
                        var responseinfo=response.body?.string().toString()
                        //Log.d("GSON", "msg:${responseinfo}")
                        var json= Gson().fromJson(responseinfo, ShowStockTransOrderBody::class.java)
                        when(json.status){
                            0->{
                                cookie_data.status =json.status
                                var data: ArrayList<StockTransOrderBody> =json.data
                                allComboboxData=data.toMutableList().toCollection(java.util.ArrayList())

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
            "PurchaseBatchOrder"->{
                val filter = JSONObject()
                filter.put("is_argee",true)
                filter.put("is_v_argee",true)
                //filter.put("header_id",selectedfilter)
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
                    .url(cookie_data.URL+"/purchase_order_management")
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
                filter.put("is_argee",true)
                filter.put("is_v_argee",true)
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
            is SysArgPur ->{
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
                        var json= Gson().fromJson(responseinfo, ShowSysArgPur::class.java)
                        when(json.status){
                            0->{
                                cookie_data.status =json.status
                                var data: ArrayList<SysArgPur> =json.data
                                cookie_data.limited_days_ComboboxData.removeAll(cookie_data.limited_days_ComboboxData)

                                for(i in 0 until data.size){
                                    cookie_data.limited_days_ComboboxData.add(data[i].limited_days)
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
                                cookie_data.store_area_name_ComboboxData.removeAll(cookie_data.store_area_name_ComboboxData)
                                cookie_data.store_area_id_name_ComboboxData.removeAll(cookie_data.store_area_id_name_ComboboxData)
                                for(i in 0 until data.size){
                                    cookie_data.store_area_ComboboxData.add(data[i].store_area)
                                    cookie_data.store_area_name_ComboboxData.add(data[i].store_area_name)
                                    cookie_data.store_area_id_name_ComboboxData.add(data[i].store_area+" "+data[i].store_area_name)
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
                                    cookie_data.semi_finished_product_number_ComboboxData)
                                cookie_data.is_exemption_ComboboxData.removeAll(cookie_data.is_exemption_ComboboxData)
                                cookie_data.item_id_name_ComboboxData.removeAll(cookie_data.item_id_name_ComboboxData)
                                for(i in 0 until data.size){
                                    cookie_data.item_id_ComboboxData.add(data[i]._id)
                                    cookie_data.item_name_ComboboxData.add(data[i].name)
                                    cookie_data.semi_finished_product_number_ComboboxData.add(data[i].semi_finished_product_number)
                                    cookie_data.is_exemption_ComboboxData.add(data[i].is_exemption)
                                    cookie_data.item_id_name_ComboboxData.add(data[i]._id+" "+data[i].name)
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
                                cookie_data.pline_id_name_ComboboxData.removeAll(cookie_data.pline_id_name_ComboboxData)
                                cookie_data.out_sourceing_ComboboxData.removeAll(cookie_data.out_sourceing_ComboboxData)
                                cookie_data.pline_vender_id_ComboboxData.removeAll(cookie_data.pline_vender_id_ComboboxData)
                                for(i in 0 until data.size){
                                    cookie_data.pline_name_ComboboxData.add(data[i].name)
                                    cookie_data.pline_id_ComboboxData.add(data[i]._id)
                                    cookie_data.pline_id_name_ComboboxData.add(data[i]._id+" "+data[i].name)
                                    cookie_data.out_sourceing_ComboboxData.add(data[i].is_outsourcing)
                                    cookie_data.pline_vender_id_ComboboxData.add(data[i].vender_id)
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
                                cookie_data.inv_code_name_m_ComboboxData.removeAll(cookie_data.inv_code_name_m_ComboboxData)
                                for(i in 0 until data.size){
                                    cookie_data.inv_code_m_ComboboxData.add(data[i].inv_code_m)
                                    cookie_data.inv_code_name_m_ComboboxData.add(data[i].inv_code_m+" "+data[i].inv_name_m)
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
                                cookie_data.inv_code_name_s_ComboboxData.removeAll(cookie_data.inv_code_name_s_ComboboxData)
                                cookie_data.inv_code_s_inv_code_m_ComboboxData.removeAll(cookie_data.inv_code_s_inv_code_m_ComboboxData)
                                for(i in 0 until data.size){
                                    cookie_data.inv_code_s_ComboboxData.add(data[i].inv_code_s)
                                    cookie_data.inv_code_name_s_ComboboxData.add(data[i].inv_code_s+" "+data[i].inv_name_s)
                                    cookie_data.inv_code_s_inv_code_m_ComboboxData.add(data[i].inv_code_m)
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

                                for(i in 0 until data.size){
                                    cookie_data.ProductControlOrderBody_A_prod_ctrl_order_number_ComboboxData.add(data[i].prod_ctrl_order_number)
                                    cookie_data.ProductControlOrderBody_A_me_code_ComboboxData.add(data[i].me_code)
                                    cookie_data.ProductControlOrderBody_A_semi_finished_prod_number_ComboboxData.add(data[i].semi_finished_prod_number)
                                    cookie_data.ProductControlOrderBody_A_pline_id_ComboboxData.add(data[i].pline_id)
                                    cookie_data.ProductControlOrderBody_A_complete_date_ComboboxData.add(data[i].complete_date)
                                    cookie_data.ProductControlOrderBody_A_est_complete_date_ComboboxData.add(data[i].est_complete_date)
                                    cookie_data.ProductControlOrderBody_A_is_closed_ComboboxData.add(data[i].is_closed)
                                    cookie_data.ProductControlOrderBody_A_is_re_make_ComboboxData.add(data[i].is_re_make)
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
                                cookie_data.vender_card_number_ComboboxData.removeAll(cookie_data.vender_card_number_ComboboxData)
                                cookie_data.vender_id_halfname_ComboboxData.removeAll(cookie_data.vender_id_halfname_ComboboxData)
                                for(i in 0 until data.size){
                                    cookie_data.vender_id_ComboboxData.add(data[i]._id)
                                    cookie_data.vender_abbreviation_ComboboxData.add(data[i].abbreviation)
                                    cookie_data.vender_card_number_ComboboxData.add(data[i].card_number)
                                    cookie_data.vender_id_halfname_ComboboxData.add(data[i]._id+" "+data[i].abbreviation)
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
            is PurchaseOrderHeader ->{
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
                        var json= Gson().fromJson(responseinfo, ShowPurchaseOrderHeader::class.java)
                        when(json.status){
                            0->{
                                cookie_data.status=json.status
                                var data: ArrayList<PurchaseOrderHeader> =json.data
                                cookie_data.PurchaseOrderHeader_poNo_ComboboxData.removeAll(
                                    cookie_data.PurchaseOrderHeader_poNo_ComboboxData)
                                cookie_data.PurchaseOrderHeader_vender_name_ComboboxData.removeAll(
                                    cookie_data.PurchaseOrderHeader_vender_name_ComboboxData)

                                for(i in 0 until data.size){
                                    cookie_data.PurchaseOrderHeader_poNo_ComboboxData.add(data[i].poNo)
                                    cookie_data.PurchaseOrderHeader_vender_name_ComboboxData.add(data[i].vender_name)

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
            is PurchaseOrderBody ->{
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
                        var json= Gson().fromJson(responseinfo, ShowPurchaseOrderBody::class.java)
                        when(json.status){
                            0->{
                                cookie_data.status=json.status
                                var data: ArrayList<PurchaseOrderBody> =json.data
                                cookie_data.PurchaseOrderBody_poNo_ComboboxData.removeAll(
                                    cookie_data.PurchaseOrderBody_poNo_ComboboxData)
                                cookie_data.PurchaseOrderBody_body_id_ComboboxData.removeAll(
                                    cookie_data.PurchaseOrderBody_body_id_ComboboxData)
                                cookie_data.PurchaseOrderBody_item_id_ComboboxData.removeAll(
                                    cookie_data.PurchaseOrderBody_item_id_ComboboxData)

                                for(i in 0 until data.size){
                                    cookie_data.PurchaseOrderBody_poNo_ComboboxData.add(data[i].poNo)
                                    cookie_data.PurchaseOrderBody_body_id_ComboboxData.add(data[i].body_id)
                                    cookie_data.PurchaseOrderBody_item_id_ComboboxData.add(data[i].item_id)

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
            is PurchaseBatchOrder ->{
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
                        var json= Gson().fromJson(responseinfo, ShowPurchaseBatchOrder::class.java)
                        when(json.status){
                            0->{
                                cookie_data.status=json.status
                                var data: ArrayList<PurchaseBatchOrder> =json.data
                                cookie_data.PurchaseBatchOrder_batch_id_ComboboxData.removeAll(
                                    cookie_data.PurchaseBatchOrder_batch_id_ComboboxData)
                                cookie_data.PurchaseBatchOrder_purchase_order_id_ComboboxData.removeAll(
                                    cookie_data.PurchaseBatchOrder_purchase_order_id_ComboboxData)
                                cookie_data.PurchaseBatchOrder_is_closed_ComboboxData.removeAll(
                                    cookie_data.PurchaseBatchOrder_is_closed_ComboboxData)

                                for(i in 0 until data.size){
                                    cookie_data.PurchaseBatchOrder_batch_id_ComboboxData.add(data[i].batch_id)
                                    cookie_data.PurchaseBatchOrder_purchase_order_id_ComboboxData.add(data[i].purchase_order_id)
                                    cookie_data.PurchaseBatchOrder_is_closed_ComboboxData.add(data[i].is_closed)
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
            is StockTransOrderHeader ->{
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
                    .url(cookie_data.URL+"/inventory_management")
                    .header("User-Agent", "ERP_MOBILE")
                    .post(body)
                    .build()
                runBlocking {
                    var job = CoroutineScope(Dispatchers.IO).launch {
                        var response = cookie_data.okHttpClient.newCall(request).execute()
                        var responseinfo=response.body?.string().toString()
                        var json= Gson().fromJson(responseinfo, ShowStockTransOrderHeader::class.java)
                        when(json.status){
                            0->{
                                cookie_data.status=json.status
                                var data: ArrayList<StockTransOrderHeader> =json.data
                                cookie_data.StockTransOrderHeader_id_ComboboxData.removeAll(cookie_data.StockTransOrderHeader_id_ComboboxData)
                                cookie_data.StockTransOrderHeader_date_ComboboxData.removeAll(cookie_data.StockTransOrderHeader_date_ComboboxData)
                                cookie_data.StockTransOrderHeader_dept_ComboboxData.removeAll(cookie_data.StockTransOrderHeader_dept_ComboboxData)
                                cookie_data.StockTransOrderHeader_purchase_order_id_ComboboxData.removeAll(cookie_data.StockTransOrderHeader_purchase_order_id_ComboboxData)
                                cookie_data.StockTransOrderHeader_prod_ctrl_order_number_ComboboxData.removeAll(cookie_data.StockTransOrderHeader_prod_ctrl_order_number_ComboboxData)
                                cookie_data.StockTransOrderHeader_main_trans_code_ComboboxData.removeAll(cookie_data.StockTransOrderHeader_main_trans_code_ComboboxData)
                                cookie_data.StockTransOrderHeader_sec_trans_code_ComboboxData.removeAll(cookie_data.StockTransOrderHeader_sec_trans_code_ComboboxData)
                                for(i in 0 until data.size){
                                    cookie_data.StockTransOrderHeader_id_ComboboxData.add(data[i]._id)
                                    cookie_data.StockTransOrderHeader_date_ComboboxData.add(data[i].date)
                                    cookie_data.StockTransOrderHeader_dept_ComboboxData.add(data[i].dept)
                                    cookie_data.StockTransOrderHeader_purchase_order_id_ComboboxData.add(data[i].purchase_order_id)
                                    cookie_data.StockTransOrderHeader_prod_ctrl_order_number_ComboboxData.add(data[i].prod_ctrl_order_number)
                                    cookie_data.StockTransOrderHeader_main_trans_code_ComboboxData.add(data[i].main_trans_code)
                                    cookie_data.StockTransOrderHeader_sec_trans_code_ComboboxData.add(data[i].sec_trans_code)

                                }
                                //Log.d("GSON", "msg:${cookie_data.StockTransOrderHeader_id_ComboboxData}")
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