package com.example.erp20.app13

import android.app.DatePickerDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.WindowManager
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
import com.google.android.material.textfield.TextInputLayout
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

class Activity13 : AppCompatActivity() {
    private lateinit var BookingNotice_adapter: RecyclerItemBookingNoticeAdapter
    private lateinit var OAReference_adapter: RecyclerItemOAReferenceAdapter
    private lateinit var OAFileDeliveryRecordMain_adapter: RecyclerItemOAFileDeliveryRecordMainAdapter
    private lateinit var OAFileDeliveryRecordBill_adapter: RecyclerItemOAFileDeliveryRecordBillAdapter
    private lateinit var CustomerOrderHeader_adapter: RecyclerItemCustomerOrderHeaderAdapter
    var comboboxData: MutableList<String> = mutableListOf<String>()
    lateinit var  allComboboxData: java.util.ArrayList<StockTransOrderBody>
    lateinit var selectFilter:String
    lateinit var selectFilter2:String
    lateinit var selectFilter3:String
    lateinit var selectFilter4:String
    lateinit var selectFilter5:String
    lateinit var recyclerView: RecyclerView
    val dateF = SimpleDateFormat("yyyy-MM-dd(EEEE)", Locale.TAIWAN)
    val dateF2 = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_13)
    }
    override fun onResume() {
        super.onResume()
        
        val recyclerView=findViewById<RecyclerView>(R.id.recyclerView)

        //combobox選單內容
        val autoCompleteTextView=findViewById<AutoCompleteTextView>(R.id.autoCompleteText)
        val combobox=resources.getStringArray(R.array.combobox13)
        val arrayAdapter= ArrayAdapter(this,R.layout.combobox_item,combobox)
        autoCompleteTextView.setAdapter(arrayAdapter)

        val searchbtn=findViewById<Button>(R.id.search_btn)
        val addbtn=findViewById<Button>(R.id.add_btn)
        autoCompleteTextView.setOnItemClickListener { parent, view, position, id ->
            addbtn.isEnabled=false
        }
        //搜尋按鈕
        searchbtn?.setOnClickListener {
            when(autoCompleteTextView?.text.toString()){
                "訂艙通知單(維護)"->{
                    show_relative_combobox("BookingNoticeHeader","all","False", BookingNoticeHeader())
                    val item = LayoutInflater.from(this).inflate(R.layout.filter_combobox2, null)
                    val mAlertDialog = AlertDialog.Builder(this)
                    //mAlertDialog.setIcon(R.mipmap.ic_launcher_round) //set alertdialog icon
                    mAlertDialog.setTitle("篩選") //set alertdialog title
                    //mAlertDialog.setMessage("確定要登出?") //set alertdialog message
                    mAlertDialog.setView(item)
                    //filter_combobox選單內容
                    val comboboxView=item.findViewById<AutoCompleteTextView>(R.id.autoCompleteText)//選擇項目
                    comboboxView.setText("訂艙管制單號")
                    val arrayAdapter= ArrayAdapter(this,R.layout.combobox_item,resources.getStringArray(R.array.combobox13_1_filter))
                    comboboxView.setAdapter(arrayAdapter)
                    val comboboxView2=item.findViewById<AutoCompleteTextView>(R.id.autoCompleteText2)//內容
                    comboboxView.setAdapter(arrayAdapter)
                    var arrayAdapter2= ArrayAdapter(this,R.layout.combobox_item,cookie_data.BookingNoticeHeader_id_ComboboxData)
                    comboboxView2.setAdapter(arrayAdapter2)
                    comboboxView2.inputType=InputType.TYPE_CLASS_TEXT
                    comboboxView.setOnItemClickListener { parent, view, position, id ->
                        comboboxView2.setText("")
                        if(position==0){//訂艙管制單號
                            var data=cookie_data.BookingNoticeHeader_id_ComboboxData.distinct().toCollection(java.util.ArrayList())
                            //data.remove("")
                            arrayAdapter2= ArrayAdapter(this,R.layout.combobox_item,data)
                            comboboxView2.setAdapter(arrayAdapter2)
                        }
                        else if(position==1){//訂艙通知號碼
                            var data=cookie_data.BookingNoticeHeader_notice_numbe_ComboboxData.distinct().toCollection(java.util.ArrayList())
                            //data.remove("")
                            arrayAdapter2= ArrayAdapter(this,R.layout.combobox_item,data)
                            comboboxView2.setAdapter(arrayAdapter2)
                        }
                        else if(position==2){//PO#
                            var data=cookie_data.BookingNoticeHeader_customer_poNo_ComboboxData.distinct().toCollection(java.util.ArrayList())
                            //data.remove("")
                            arrayAdapter2= ArrayAdapter(this,R.layout.combobox_item,data)
                            comboboxView2.setAdapter(arrayAdapter2)
                        }
                    }

                    mAlertDialog.setPositiveButton("取消") { dialog, id ->
                        dialog.dismiss()
                    }
                    mAlertDialog.setNegativeButton("確定") { dialog, id ->
                        //println(comboboxView.text)
                        selectFilter=comboboxView.text.toString()
                        selectFilter2=comboboxView2.text.toString()

                        println(selectFilter+"\n"+selectFilter2+"\n")
                        show_header_body("BookingNoticeHeader","all","False")//type=combobox or all
                        show_relative_combobox("ShippingCompanyBasicInfo","all","False", ShippingCompanyBasicInfo())
                        show_relative_combobox("CustomBasicInfo","all","False", CustomBasicInfo())
                        show_relative_combobox("OAReference","all","False", OAReference())
                        show_relative_combobox("CustomerOrderHeader","all","False", CustomerOrderHeader())
                        when(cookie_data.status)
                        {
                            0->{
                                Toast.makeText(this, "資料載入", Toast.LENGTH_SHORT).show()
                                recyclerView?.layoutManager= LinearLayoutManager(this)//設定Linear格式
                                BookingNotice_adapter= RecyclerItemBookingNoticeAdapter(selectFilter,selectFilter2)
                                recyclerView?.adapter=BookingNotice_adapter//找對應itemAdapter
                                cookie_data.recyclerView=recyclerView
                                addbtn?.isEnabled=true
                            }
                            1->{
                                Toast.makeText(this, cookie_data.msg, Toast.LENGTH_SHORT).show()
                            }
                        }

                    }
                    mAlertDialog.show()

                }
                /*"託外加工件"->{
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

                }*/
                /*"訂艙通知單"->{
                    show_header_body("BookingNoticeHeader","all","False")//type=combobox or all
                    show_relative_combobox("ShippingCompanyBasicInfo","all","False", ShippingCompanyBasicInfo())
                    show_relative_combobox("CustomBasicInfo","all","False", CustomBasicInfo())
                    show_relative_combobox("OAReference","all","False", OAReference())
                    show_relative_combobox("CustomerOrderHeader","all","False", CustomerOrderHeader())
                    when(cookie_data.status)
                    {
                        0->{
                            Toast.makeText(this, "資料載入", Toast.LENGTH_SHORT).show()
                            recyclerView?.layoutManager= LinearLayoutManager(this)//設定Linear格式
                            BookingNotice_adapter= RecyclerItemBookingNoticeAdapter()
                            recyclerView?.adapter=BookingNotice_adapter//找對應itemAdapter
                            cookie_data.recyclerView=recyclerView
                            addbtn?.isEnabled=true
                        }
                        1->{
                            Toast.makeText(this, cookie_data.msg, Toast.LENGTH_SHORT).show()
                        }
                    }
                }*/
                "OA reference no(維護)"->{
                    show_Header_combobox("OAReference","all","False", OAReference())//type=combobox or all
                    when(cookie_data.status)
                    {
                        0->{
                            val item = LayoutInflater.from(this).inflate(R.layout.filter_combobox, null)
                            val mAlertDialog = AlertDialog.Builder(this)
                            //mAlertDialog.setIcon(R.mipmap.ic_launcher_round) //set alertdialog icon
                            mAlertDialog.setTitle("篩選") //set alertdialog title
                            //mAlertDialog.setMessage("確定要登出?") //set alertdialog message
                            mAlertDialog.setView(item)
                            //filter_combobox選單內容
                            val comboboxView=item.findViewById<AutoCompleteTextView>(R.id.autoCompleteText)
                            comboboxView.setHint("OA Reference no(1)")
                            comboboxView.setAdapter(ArrayAdapter(this,R.layout.combobox_item,comboboxData))
                            mAlertDialog.setPositiveButton("取消") { dialog, id ->
                                dialog.dismiss()
                            }
                            mAlertDialog.setNegativeButton("確定") { dialog, id ->
                                //println(comboboxView.text)
                                selectFilter=comboboxView.text.toString()
                                show_header_body("OAReference","all","False")//type=combobox or all
                                when(cookie_data.status)
                                {
                                    0->{
                                        Toast.makeText(this, "資料載入", Toast.LENGTH_SHORT).show()
                                        recyclerView?.layoutManager= LinearLayoutManager(this)//設定Linear格式
                                        OAReference_adapter= RecyclerItemOAReferenceAdapter(selectFilter)
                                        recyclerView?.adapter=OAReference_adapter//找對應itemAdapter
                                        cookie_data.recyclerView=recyclerView
                                        addbtn?.isEnabled=true
                                    }
                                    1->{
                                        Toast.makeText(this, cookie_data.msg, Toast.LENGTH_SHORT).show()
                                    }
                                }

                            }
                            mAlertDialog.show()
                        }
                        1->{
                            Toast.makeText(this, cookie_data.msg, Toast.LENGTH_SHORT).show()
                        }
                    }

                }
                "OA文件寄送紀錄(查詢/維護)"->{
                    show_Header_combobox("OAFileDeliveryRecordHeader","all","False", OAFileDeliveryRecordHeader())//type=combobox or all
                    when(cookie_data.status)
                    {
                        0->{
                            val item = LayoutInflater.from(this).inflate(R.layout.filter_combobox, null)
                            val mAlertDialog = AlertDialog.Builder(this)
                            //mAlertDialog.setIcon(R.mipmap.ic_launcher_round) //set alertdialog icon
                            mAlertDialog.setTitle("篩選") //set alertdialog title
                            //mAlertDialog.setMessage("確定要登出?") //set alertdialog message
                            mAlertDialog.setView(item)
                            //filter_combobox選單內容
                            val comboboxView=item.findViewById<AutoCompleteTextView>(R.id.autoCompleteText)
                            comboboxView.setHint("Tracking#")
                            comboboxView.setAdapter(ArrayAdapter(this,R.layout.combobox_item,comboboxData))
                            mAlertDialog.setPositiveButton("取消") { dialog, id ->
                                dialog.dismiss()
                            }
                            mAlertDialog.setNegativeButton("確定") { dialog, id ->
                                //println(comboboxView.text)
                                selectFilter=comboboxView.text.toString()
                                show_header_body("OAFileDeliveryRecordHeader","all","False")//type=combobox or all
                                when(cookie_data.status)
                                {
                                    0->{
                                        Toast.makeText(this, "資料載入", Toast.LENGTH_SHORT).show()
                                        recyclerView?.layoutManager= LinearLayoutManager(this)//設定Linear格式
                                        OAFileDeliveryRecordMain_adapter= RecyclerItemOAFileDeliveryRecordMainAdapter(selectFilter)
                                        recyclerView?.adapter=OAFileDeliveryRecordMain_adapter//找對應itemAdapter
                                        cookie_data.recyclerView=recyclerView
                                        addbtn?.isEnabled=true
                                    }
                                    1->{
                                        Toast.makeText(this, cookie_data.msg, Toast.LENGTH_SHORT).show()
                                    }
                                }

                            }
                            mAlertDialog.show()
                        }
                        1->{
                            Toast.makeText(this, cookie_data.msg, Toast.LENGTH_SHORT).show()
                        }
                    }

                }
                "PO#相關資料(查詢)"->{
                    show_Header_combobox("CustomerOrder","all","True", CustomerOrderHeader())//type=combobox or all
                    when(cookie_data.status)
                    {
                        0->{
                            val item = LayoutInflater.from(this).inflate(R.layout.filter_combobox, null)
                            val mAlertDialog = AlertDialog.Builder(this)
                            //mAlertDialog.setIcon(R.mipmap.ic_launcher_round) //set alertdialog icon
                            mAlertDialog.setTitle("篩選") //set alertdialog title
                            //mAlertDialog.setMessage("確定要登出?") //set alertdialog message
                            mAlertDialog.setView(item)
                            //filter_combobox選單內容
                            val comboboxView=item.findViewById<AutoCompleteTextView>(R.id.autoCompleteText)
                            comboboxView.setHint("PO#")
                            var data=comboboxData.distinct().toCollection(java.util.ArrayList())
                            comboboxView.setAdapter(ArrayAdapter(this,R.layout.combobox_item,data))
                            mAlertDialog.setPositiveButton("取消") { dialog, id ->
                                dialog.dismiss()
                            }
                            mAlertDialog.setNegativeButton("確定") { dialog, id ->
                                //println(comboboxView.text)
                                selectFilter=comboboxView.text.toString()
                                show_CustomerOrder("CustomerOrder","header","all","True")//type=combobox or all
                                show_relative_combobox("BookingNoticeHeader","all","False", BookingNoticeHeader())
                                show_relative_combobox("OAFileDeliveryRecordBody","all","False", OAFileDeliveryRecordBody())
                                when(cookie_data.status)
                                {
                                    0->{
                                        Toast.makeText(this, "資料載入", Toast.LENGTH_SHORT).show()
                                        recyclerView?.layoutManager= LinearLayoutManager(this)//設定Linear格式
                                        CustomerOrderHeader_adapter= RecyclerItemCustomerOrderHeaderAdapter(selectFilter)
                                        recyclerView?.adapter=CustomerOrderHeader_adapter//找對應itemAdapter
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
                        1->{
                            Toast.makeText(this, cookie_data.msg, Toast.LENGTH_SHORT).show()
                        }
                    }

                }
                "快遞公司月對帳單(查詢)"->{
                    show_relative_combobox("OAFileDeliveryRecordHeader","all","False", OAFileDeliveryRecordHeader())
                    when(cookie_data.status)
                    {
                        0->{
                            val item = LayoutInflater.from(this).inflate(R.layout.filter_combobox2, null)
                            val mAlertDialog = AlertDialog.Builder(this)
                            //mAlertDialog.setIcon(R.mipmap.ic_launcher_round) //set alertdialog icon
                            mAlertDialog.setTitle("篩選") //set alertdialog title
                            //mAlertDialog.setMessage("確定要登出?") //set alertdialog message
                            mAlertDialog.setView(item)
                            //filter_combobox選單內容
                            val comboboxView=item.findViewById<AutoCompleteTextView>(R.id.autoCompleteText)
                            comboboxView.inputType=InputType.TYPE_CLASS_TEXT
                            val inputText=item.findViewById<TextInputLayout>(R.id.one)
                            inputText.hint=""
                            comboboxView.setHint("快遞公司")
                            var data1=cookie_data.OAFileDeliveryRecordHeader_courier_company_ComboboxData.distinct()
                            comboboxView.setAdapter(ArrayAdapter(this,R.layout.combobox_item,data1))
                            val comboboxView2=item.findViewById<AutoCompleteTextView>(R.id.autoCompleteText2)
                            comboboxView2.inputType=InputType.TYPE_CLASS_TEXT
                            val inputText2=item.findViewById<TextInputLayout>(R.id.two)
                            inputText2.hint=""
                            comboboxView2.setHint("結帳月份")
                            var data2=cookie_data.OAFileDeliveryRecordHeader_shippin_billing_month_ComboboxData.distinct()
                            comboboxView2.setAdapter(ArrayAdapter(this,R.layout.combobox_item,data2))
                            mAlertDialog.setPositiveButton("取消") { dialog, id ->
                                dialog.dismiss()
                            }
                            mAlertDialog.setNegativeButton("確定") { dialog, id ->
                                //println(comboboxView.text)
                                selectFilter=comboboxView.text.toString()
                                selectFilter2=comboboxView2.text.toString()
                                show_header_body("OAFileDeliveryRecordHeader","all","False")//type=combobox or all
                                when(cookie_data.status)
                                {
                                    0->{
                                        Toast.makeText(this, "資料載入", Toast.LENGTH_SHORT).show()
                                        recyclerView?.layoutManager= LinearLayoutManager(this)//設定Linear格式
                                        OAFileDeliveryRecordBill_adapter= RecyclerItemOAFileDeliveryRecordBillAdapter(selectFilter,selectFilter2)
                                        recyclerView?.adapter=OAFileDeliveryRecordBill_adapter//找對應itemAdapter
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
                        1->{
                            Toast.makeText(this, cookie_data.msg, Toast.LENGTH_SHORT).show()
                        }
                    }

                }

            }
        }

        //新增按鈕
        addbtn?.setOnClickListener {
            when(autoCompleteTextView?.text.toString()){
                "訂艙通知單(維護)"->{
                    val item = LayoutInflater.from(this).inflate(R.layout.recycler_item_booking_notice, null)
                    val mAlertDialog = AlertDialog.Builder(this)
                    //mAlertDialog.setIcon(R.mipmap.ic_launcher_round) //set alertdialog icon
                    mAlertDialog.setTitle("新增") //set alertdialog title
                    //mAlertDialog.setMessage("確定要登出?") //set alertdialog message
                    mAlertDialog.setView(item)
                    val combobox=item.resources.getStringArray(R.array.combobox_yes_no)
                    val arrayAdapter= ArrayAdapter(item.context,R.layout.combobox_item,combobox)
                    val arrayAdapter01= ArrayAdapter(item.context,R.layout.combobox_item,cookie_data.customer_booking_prefix_ComboboxData)
                    val arrayAdapter02= ArrayAdapter(item.context,R.layout.combobox_item,cookie_data.CustomerOrderHeader_poNo_ComboboxData)
                    val arrayAdapter03= ArrayAdapter(item.context,R.layout.combobox_item,cookie_data.OAReference_oa_referenceNO1_ComboboxData)
                    val arrayAdapter04= ArrayAdapter(item.context,R.layout.combobox_item,cookie_data.shipping_number_name_ComboboxData)

                    var _id=item.findViewById<TextInputEditText>(R.id.edit_id)
                    _id.inputType=InputType.TYPE_NULL
                    var invalid=item.findViewById<TextInputEditText>(R.id.edit_invalid)
                    invalid.inputType=InputType.TYPE_NULL
                    var notice_number=item.findViewById<AutoCompleteTextView>(R.id.edit_notice_number)
                    notice_number.setAdapter(arrayAdapter01)
                    var customer_poNo=item.findViewById<AutoCompleteTextView>(R.id.edit_customer_poNo)
                    customer_poNo.setAdapter(arrayAdapter02)
                    var swe=item.findViewById<TextInputEditText>(R.id.edit_swe)
                    swe.inputType=InputType.TYPE_NULL
                    //po#
                    customer_poNo.setOnItemClickListener { parent, view, position, id ->
                        if(cookie_data.CustomerOrderHeader_swe_ComboboxData[position]!=null){
                            var readDateString=cookie_data.CustomerOrderHeader_swe_ComboboxData[position]
                            //println(readDateString)
                            val readDate=Calendar.getInstance()
                            //println(readDateString.subSequence(0,4))
                            readDate.set(Calendar.YEAR,readDateString!!.subSequence(0,4).toString().toInt())//yyyy
                            // println(readDateString.subSequence(5,7))
                            readDate.set(Calendar.MONTH,readDateString!!.subSequence(5,7).toString().toInt()-1)//MM
                            // println(readDateString.subSequence(8,10))
                            readDate.set(Calendar.DAY_OF_MONTH,readDateString!!.subSequence(8,10).toString().toInt())//dd
                            var date=dateF.format(readDate.time)
                            swe.setText(date)
                        }
                        else{
                            swe.setText("")
                        }

                    }
                    var shipping_order_number=item.findViewById<TextInputEditText>(R.id.edit_shipping_order_number)
                    var act_clearance_date=item.findViewById<TextInputEditText>(R.id.edit_act_clearance_date)
                    act_clearance_date.inputType=InputType.TYPE_NULL
                    var pre_clearance_date=item.findViewById<TextInputEditText>(R.id.edit_pre_clearance_date)
                    pre_clearance_date.inputType=InputType.TYPE_NULL
                    //結關日期
                    act_clearance_date.setOnClickListener {
                        var c= Calendar.getInstance()
                        val year= c.get(Calendar.YEAR)
                        val month = c.get(Calendar.MONTH)
                        val day = c.get(Calendar.DAY_OF_MONTH)
                        var datePicker = DatePickerDialog(this,
                            DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                                val SelectedDate=Calendar.getInstance()
                                SelectedDate.set(Calendar.YEAR,mYear)
                                SelectedDate.set(Calendar.MONTH,mMonth)
                                SelectedDate.set(Calendar.DAY_OF_MONTH,mDay)
                                var date= dateF.format(SelectedDate.time)
                                act_clearance_date.setText(date)
                                SelectedDate.add(Calendar.DAY_OF_YEAR,7)
                                date=dateF.format(SelectedDate.time)
                                pre_clearance_date.setText(date)
                            },year,month,day).show()
                    }
                    var act_shipping_date=item.findViewById<TextInputEditText>(R.id.edit_act_shipping_date)
                    act_shipping_date.inputType=InputType.TYPE_NULL
                    //實際開船日
                    act_shipping_date.setOnClickListener {
                        var c= Calendar.getInstance()
                        val year= c.get(Calendar.YEAR)
                        val month = c.get(Calendar.MONTH)
                        val day = c.get(Calendar.DAY_OF_MONTH)
                        var datePicker = DatePickerDialog(this,
                            DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                                val SelectedDate=Calendar.getInstance()
                                SelectedDate.set(Calendar.YEAR,mYear)
                                SelectedDate.set(Calendar.MONTH,mMonth)
                                SelectedDate.set(Calendar.DAY_OF_MONTH,mDay)
                                val date= dateF.format(SelectedDate.time)
                                act_shipping_date.setText(date)
                            },year,month,day).show()
                    }
                    var is_last=item.findViewById<AutoCompleteTextView>(R.id.edit_is_last)
                    is_last.setAdapter(arrayAdapter)
                    var oa_referenceNO1=item.findViewById<AutoCompleteTextView>(R.id.edit_oa_referenceNO1)
                    oa_referenceNO1.inputType=InputType.TYPE_NULL
                    oa_referenceNO1.setAdapter(arrayAdapter03)
                    var oa_referenceNO2=item.findViewById<TextInputEditText>(R.id.edit_oa_referenceNO2)
                    oa_referenceNO2.inputType=InputType.TYPE_NULL
                    var oa_referenceNO3=item.findViewById<TextInputEditText>(R.id.edit_oa_referenceNO3)
                    oa_referenceNO3.inputType=InputType.TYPE_NULL
                    oa_referenceNO1.setOnItemClickListener { parent, view, position, id ->
                        oa_referenceNO2.setText(cookie_data.OAReference_oa_referenceNO2_ComboboxData[position])
                        oa_referenceNO3.setText(cookie_data.OAReference_oa_referenceNO3_ComboboxData[position])
                    }
                    var shipping_number=item.findViewById<AutoCompleteTextView>(R.id.edit_shipping_number)
                    shipping_number.setAdapter(arrayAdapter04)
                    var date=item.findViewById<TextInputEditText>(R.id.edit_date)
                    date.inputType=InputType.TYPE_NULL
                    //開單日期
                    date.setOnClickListener {
                        var c= Calendar.getInstance()
                        val year= c.get(Calendar.YEAR)
                        val month = c.get(Calendar.MONTH)
                        val day = c.get(Calendar.DAY_OF_MONTH)
                        var datePicker = DatePickerDialog(this,
                            DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                                val SelectedDate=Calendar.getInstance()
                                SelectedDate.set(Calendar.YEAR,mYear)
                                SelectedDate.set(Calendar.MONTH,mMonth)
                                    SelectedDate.set(Calendar.DAY_OF_MONTH,mDay)
                                val Date= dateF.format(SelectedDate.time)
                                date.setText(Date)
                            },year,month,day).show()
                    }


                    val remark=item.findViewById<TextInputEditText>(R.id.edit_remark)

                    mAlertDialog.setPositiveButton("取消") { dialog, id ->
                        dialog.dismiss()
                    }
                    mAlertDialog.setNegativeButton("確定") { dialog, id ->
                        //新增不能為空
                        if( shipping_order_number.text.toString().trim().isEmpty() ){
                            Toast.makeText(this,"s/o# required", Toast.LENGTH_LONG).show()
                        }
                        else {
                            val addData= BookingNoticeHeader()
                            addData.notice_number=notice_number.text.toString()
                            notice_number.setAdapter(null)
                            addData.customer_poNo=customer_poNo.text.toString()
                            customer_poNo.setAdapter(null)
                            addData.shipping_order_number=shipping_order_number.text.toString()
                            shipping_order_number.inputType=InputType.TYPE_NULL

                            if(act_clearance_date.text.toString()==""){
                                addData.act_clearance_date=null
                            }
                            else{
                                addData.act_clearance_date=act_clearance_date.text.toString().substring(0,act_clearance_date.text.toString().indexOf("("))
                            }
                            act_clearance_date.inputType=InputType.TYPE_NULL

                            if(act_shipping_date.text.toString()==""){
                                addData.act_shipping_date=null
                            }
                            else{
                                addData.act_shipping_date=act_shipping_date.text.toString().substring(0,act_shipping_date.text.toString().indexOf("("))
                            }
                            act_shipping_date.inputType=InputType.TYPE_NULL

                            addData.is_last=is_last.text.toString().toBoolean()
                            is_last.setAdapter(null)
                            addData.oa_referenceNO1=oa_referenceNO1.text.toString()
                            oa_referenceNO1.setAdapter(null)

                            if(shipping_number.text.toString()==""){
                                addData.shipping_number=""
                            }
                            else{
                                addData.shipping_number=shipping_number.text.toString().substring(0,shipping_number.text.toString().indexOf(" "))
                            }
                            shipping_number.setAdapter(null)

                            if(date.text.toString()==""){
                                addData.date=null
                            }
                            else{
                                addData.date=date.text.toString().substring(0,date.text.toString().indexOf("("))
                            }
                            date.inputType=InputType.TYPE_NULL

                            addData.remark=remark.text.toString()
                            add_header_body("BookingNoticeHeader",addData)
                            when(cookie_data.status){
                                0-> {//成功
                                    cookie_data.recyclerView=recyclerView
                                    BookingNotice_adapter.addItem(addData)
                                    Toast.makeText(this, cookie_data.msg, Toast.LENGTH_SHORT).show()
                                }
                                1->{//失敗
                                    Toast.makeText(this, cookie_data.msg, Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                    mAlertDialog.show()
                }
                "OA reference no(維護)"->{
                    val item = LayoutInflater.from(this).inflate(R.layout.recycler_item_oa_reference_no, null)
                    val mAlertDialog = AlertDialog.Builder(this)
                    //mAlertDialog.setIcon(R.mipmap.ic_launcher_round) //set alertdialog icon
                    mAlertDialog.setTitle("新增") //set alertdialog title
                    //mAlertDialog.setMessage("確定要登出?") //set alertdialog message
                    mAlertDialog.setView(item)

                    val combobox=item.resources.getStringArray(R.array.combobox_yes_no)
                    val arrayAdapter= ArrayAdapter(item.context,R.layout.combobox_item,combobox)


                    var oa_referenceNO1=item.findViewById<TextInputEditText>(R.id.edit_oa_referenceNO1)
                    var oa_referenceNO2=item.findViewById<TextInputEditText>(R.id.edit_oa_referenceNO2)
                    var oa_referenceNO3=item.findViewById<TextInputEditText>(R.id.edit_oa_referenceNO3)

                    var invalid=item.findViewById<TextInputEditText>(R.id.edit_invalid)
                    invalid.inputType=InputType.TYPE_NULL

                    val remark=item.findViewById<TextInputEditText>(R.id.edit_remark)

                    mAlertDialog.setPositiveButton("取消") { dialog, id ->
                        dialog.dismiss()
                    }
                    mAlertDialog.setNegativeButton("確定") { dialog, id ->
                        //新增不能為空
                        if( oa_referenceNO1.text.toString().trim().isEmpty() ||
                            oa_referenceNO2.text.toString().trim().isEmpty() ||
                            oa_referenceNO3.text.toString().trim().isEmpty()
                        ){
                            Toast.makeText(this,"Input required", Toast.LENGTH_LONG).show()
                        }
                        else{
                            val addData= OAReference()
                            addData.oa_referenceNO2=oa_referenceNO1.text.toString()
                            addData.oa_referenceNO3=oa_referenceNO2.text.toString()
                            addData.oa_referenceNO4=oa_referenceNO3.text.toString()


                            addData.remark=remark.text.toString()

                            add_header_body("OAReference",addData)
                            when(cookie_data.status){
                                0-> {//成功

                                    OAReference_adapter.addItem(addData)
                                    Toast.makeText(this, cookie_data.msg, Toast.LENGTH_SHORT).show()
                                }
                                1->{//失敗
                                    Toast.makeText(this, cookie_data.msg, Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                    mAlertDialog.show()
                }
                "OA文件寄送紀錄(查詢/維護)"->{
                    val item = LayoutInflater.from(this).inflate(R.layout.recycler_item_oa_file_delivery_record_main, null)
                    val mAlertDialog = AlertDialog.Builder(this)
                    //mAlertDialog.setIcon(R.mipmap.ic_launcher_round) //set alertdialog icon
                    mAlertDialog.setTitle("新增") //set alertdialog title
                    //mAlertDialog.setMessage("確定要登出?") //set alertdialog message
                    mAlertDialog.setView(item)

                    val combobox=item.resources.getStringArray(R.array.combobox_yes_no)
                    val arrayAdapter= ArrayAdapter(item.context,R.layout.combobox_item,combobox)


                    var trackingNo=item.findViewById<TextInputEditText>(R.id.edit_trackingNo)
                    var courier_company=item.findViewById<TextInputEditText>(R.id.edit_courier_company)
                    var delivery_date=item.findViewById<TextInputEditText>(R.id.edit_delivery_date)
                    delivery_date.inputType=InputType.TYPE_NULL
                    //寄送日期
                    delivery_date.setOnClickListener {
                        var c= Calendar.getInstance()
                        val year= c.get(Calendar.YEAR)
                        val month = c.get(Calendar.MONTH)
                        val day = c.get(Calendar.DAY_OF_MONTH)
                        var datePicker = DatePickerDialog(item.context,
                            DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                                val SelectedDate=Calendar.getInstance()
                                SelectedDate.set(Calendar.YEAR,mYear)
                                SelectedDate.set(Calendar.MONTH,mMonth)
                                SelectedDate.set(Calendar.DAY_OF_MONTH,mDay)
                                val Date= dateF.format(SelectedDate.time)
                                delivery_date.setText(Date)
                            },year,month,day).show()


                    }
                    var arrival_date=item.findViewById<TextInputEditText>(R.id.edit_arrival_date)
                    arrival_date.inputType=InputType.TYPE_NULL
                    //到件日期
                    arrival_date.setOnClickListener {

                        var c= Calendar.getInstance()
                        val year= c.get(Calendar.YEAR)
                        val month = c.get(Calendar.MONTH)
                        val day = c.get(Calendar.DAY_OF_MONTH)
                        var datePicker = DatePickerDialog(item.context,
                            DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                                val SelectedDate=Calendar.getInstance()
                                SelectedDate.set(Calendar.YEAR,mYear)
                                SelectedDate.set(Calendar.MONTH,mMonth)
                                SelectedDate.set(Calendar.DAY_OF_MONTH,mDay)
                                val Date= dateF.format(SelectedDate.time)
                                arrival_date.setText(Date)
                            },year,month,day).show()


                    }
                    var receiver=item.findViewById<TextInputEditText>(R.id.edit_receiver)
                    var shippin_billing_month=item.findViewById<TextInputEditText>(R.id.edit_shippin_billing_month)
                    shippin_billing_month.inputType=(InputType.TYPE_CLASS_TEXT or InputType.TYPE_CLASS_NUMBER)
                    var billing_date=item.findViewById<TextInputEditText>(R.id.edit_billing_date)
                    billing_date.inputType=InputType.TYPE_NULL
                    //結帳日其
                    billing_date.setOnClickListener {
                        var c= Calendar.getInstance()
                        val year= c.get(Calendar.YEAR)
                        val month = c.get(Calendar.MONTH)
                        val day = c.get(Calendar.DAY_OF_MONTH)
                        var datePicker = DatePickerDialog(item.context,
                            DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                                val SelectedDate=Calendar.getInstance()
                                SelectedDate.set(Calendar.YEAR,mYear)
                                SelectedDate.set(Calendar.MONTH,mMonth)
                                SelectedDate.set(Calendar.DAY_OF_MONTH,mDay)
                                val Date= dateF.format(SelectedDate.time)
                                billing_date.setText(Date)
                            },year,month,day).show()
                    }
                    var is_closed=item.findViewById<TextInputEditText>(R.id.edit_is_closed)
                    is_closed.inputType=InputType.TYPE_NULL
                    var invalid=item.findViewById<TextInputEditText>(R.id.edit_invalid)
                    invalid.inputType=InputType.TYPE_NULL

                    val remark=item.findViewById<TextInputEditText>(R.id.edit_remark)

                    mAlertDialog.setPositiveButton("取消") { dialog, id ->
                        dialog.dismiss()
                    }
                    mAlertDialog.setNegativeButton("確定") { dialog, id ->
                        //新增不能為空
                        if( trackingNo.text.toString().trim().isEmpty()

                        ){
                            Toast.makeText(this,"Input required", Toast.LENGTH_LONG).show()
                        }
                        else{
                            val addData= OAFileDeliveryRecordHeader()
                            addData.trackingNo=trackingNo.text.toString()
                            addData.courier_company=courier_company.text.toString()
                            if(delivery_date.text.toString()==""){
                                addData.delivery_date=null
                            }
                            else{
                                addData.delivery_date=delivery_date.text.toString().substring(0,delivery_date.text.toString().indexOf("("))
                            }
                            if(arrival_date.text.toString()==""){
                                addData.arrival_date=null
                            }
                            else{
                                addData.arrival_date=arrival_date.text.toString().substring(0,arrival_date.text.toString().indexOf("("))
                            }
                            addData.receiver=receiver.text.toString()
                            addData.shippin_billing_month=shippin_billing_month.text.toString()
                            if(billing_date.text.toString()==""){
                                addData.billing_date=null
                            }
                            else{
                                addData.billing_date=billing_date.text.toString().substring(0,billing_date.text.toString().indexOf("("))
                            }

                            addData.remark=remark.text.toString()

                            add_header_body("OAFileDeliveryRecordHeader",addData)
                            when(cookie_data.status){
                                0-> {//成功

                                    OAFileDeliveryRecordMain_adapter.addItem(addData)
                                    Toast.makeText(this, cookie_data.msg, Toast.LENGTH_SHORT).show()
                                }
                                1->{//失敗
                                    Toast.makeText(this, cookie_data.msg, Toast.LENGTH_SHORT).show()
                                }
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
            "BookingNoticeHeader"->{
                val body = FormBody.Builder()
                    .add("username", cookie_data.username)
                    .add("operation", operation)
                    .add("action",cookie_data.Actions.VIEW)
                    .add("view_type",view_type)
                    .add("view_hide",view_hide)
                    .add("csrfmiddlewaretoken", cookie_data.tokenValue)
                    .add("login_flag", cookie_data.loginflag)
                    .build()
                val request = Request.Builder()
                    .url(cookie_data.URL+"/shipping_order_management")
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
            "BookingNoticeBody"->{
                val filter = JSONObject()
                filter.put("header_id",selectFilter)
                val body = FormBody.Builder()
                    .add("username", cookie_data.username)
                    .add("operation", operation)
                    .add("action",cookie_data.Actions.VIEW)
                    .add("view_type",view_type)
                    .add("view_hide",view_hide)
                    .add("data",filter.toString())
                    .add("csrfmiddlewaretoken", cookie_data.tokenValue)
                    .add("login_flag", cookie_data.loginflag)
                    .build()
                val request = Request.Builder()
                    .url(cookie_data.URL+"/shipping_order_management")
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
            "BookingNoticeLog"->{
                val body = FormBody.Builder()
                    .add("username", cookie_data.username)
                    .add("operation", operation)
                    .add("action",cookie_data.Actions.VIEW)
                    .add("view_type",view_type)
                    .add("view_hide",view_hide)
                    .add("csrfmiddlewaretoken", cookie_data.tokenValue)
                    .add("login_flag", cookie_data.loginflag)
                    .build()
                val request = Request.Builder()
                    .url(cookie_data.URL+"/shipping_order_management")
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
            "OAReference"->{
            val body = FormBody.Builder()
                .add("username", cookie_data.username)
                .add("operation", operation)
                .add("action",cookie_data.Actions.VIEW)
                .add("view_type",view_type)
                .add("view_hide",view_hide)
                .add("csrfmiddlewaretoken", cookie_data.tokenValue)
                .add("login_flag", cookie_data.loginflag)
                .build()
            val request = Request.Builder()
                .url(cookie_data.URL+"/shipping_order_management")
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
            "OAFileDeliveryRecordHeader"->{
                val body = FormBody.Builder()
                    .add("username", cookie_data.username)
                    .add("operation", operation)
                    .add("action",cookie_data.Actions.VIEW)
                    .add("view_type",view_type)
                    .add("view_hide",view_hide)
                    .add("csrfmiddlewaretoken", cookie_data.tokenValue)
                    .add("login_flag", cookie_data.loginflag)
                    .build()
                val request = Request.Builder()
                    .url(cookie_data.URL+"/shipping_order_management")
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
            is BookingNoticeHeader ->{
                val add = JSONObject()
                add.put("_id",addData._id)
                add.put("shipping_number",addData.shipping_number)
                add.put("shipping_order_number",addData.shipping_order_number)
                add.put("date",addData.date)
                add.put("customer_poNo",addData.customer_poNo)
                add.put("notice_number",addData.notice_number)
                add.put("act_clearance_date",addData.act_clearance_date)
                add.put("act_shipping_date",addData.act_shipping_date)
                add.put("oa_referenceNO1",addData.oa_referenceNO1)
                add.put("is_last",addData.is_last)

                add.put("remark",addData.remark)
                add.put("creator", cookie_data.username)
                add.put("editor", cookie_data.username)
                Log.d("GSON", "msg:${add}")
                val body = FormBody.Builder()
                    .add("username", cookie_data.username)
                    .add("operation", operation)
                    .add("action",cookie_data.Actions.ADD)
                    .add("data",add.toString())
                    .add("csrfmiddlewaretoken", cookie_data.tokenValue)
                    .add("login_flag", cookie_data.loginflag)
                    .build()

                val request = Request.Builder()
                    .url(cookie_data.URL+"/shipping_order_management")
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
            is BookingNoticeBody ->{
                val add = JSONObject()
                add.put("header_id",addData.header_id)
                add.put("body_id",addData.body_id)
                add.put("cont_code",addData.cont_code)


                add.put("remark",addData.remark)
                add.put("creator", cookie_data.username)
                add.put("editor", cookie_data.username)
                Log.d("GSON", "msg:${add}")
                val body = FormBody.Builder()
                    .add("username", cookie_data.username)
                    .add("operation", operation)
                    .add("action",cookie_data.Actions.ADD)
                    .add("data",add.toString())
                    .add("csrfmiddlewaretoken", cookie_data.tokenValue)
                    .add("login_flag", cookie_data.loginflag)
                    .build()

                val request = Request.Builder()
                    .url(cookie_data.URL+"/shipping_order_management")
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
            is OAReference ->{
                val add = JSONObject()
                add.put("oa_referenceNO2",addData.oa_referenceNO2)
                add.put("oa_referenceNO3",addData.oa_referenceNO3)
                add.put("oa_referenceNO4",addData.oa_referenceNO4)

                add.put("remark",addData.remark)
                add.put("creator", cookie_data.username)
                add.put("editor", cookie_data.username)
                Log.d("GSON", "msg:${add}")
                val body = FormBody.Builder()
                    .add("username", cookie_data.username)
                    .add("operation", operation)
                    .add("action",cookie_data.Actions.ADD)
                    .add("data",add.toString())
                    .add("csrfmiddlewaretoken", cookie_data.tokenValue)
                    .add("login_flag", cookie_data.loginflag)
                    .build()

                val request = Request.Builder()
                    .url(cookie_data.URL+"/shipping_order_management")
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
            is OAFileDeliveryRecordHeader ->{
                val add = JSONObject()
                add.put("trackingNo",addData.trackingNo)
                add.put("courier_company",addData.courier_company)
                add.put("delivery_date",addData.delivery_date)
                add.put("arrival_date",addData.arrival_date)
                add.put("receiver",addData.receiver)
                add.put("shippin_billing_month",addData.shippin_billing_month)
                add.put("billing_date",addData.billing_date)

                add.put("remark",addData.remark)
                add.put("creator", cookie_data.username)
                add.put("editor", cookie_data.username)
                Log.d("GSON", "msg:${add}")
                val body = FormBody.Builder()
                    .add("username", cookie_data.username)
                    .add("operation", operation)
                    .add("action",cookie_data.Actions.ADD)
                    .add("data",add.toString())
                    .add("csrfmiddlewaretoken", cookie_data.tokenValue)
                    .add("login_flag", cookie_data.loginflag)
                    .build()

                val request = Request.Builder()
                    .url(cookie_data.URL+"/shipping_order_management")
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

    private fun <T>show_Header_combobox(operation:String,view_type:String,view_hide:String,fmt:T) {
        when(fmt)
        {
            is OAReference ->{
                val body = FormBody.Builder()
                    .add("username", cookie_data.username)
                    .add("operation", operation)
                    .add("action",cookie_data.Actions.VIEW)
                    .add("view_type",view_type)
                    .add("view_hide",view_hide)
                    .add("csrfmiddlewaretoken", cookie_data.tokenValue)
                    .add("login_flag", cookie_data.loginflag)
                    .build()
                val request = Request.Builder()
                    .url(cookie_data.URL+"/shipping_order_management")
                    .header("User-Agent", "ERP_MOBILE")
                    .post(body)
                    .build()
                runBlocking {
                    var job = CoroutineScope(Dispatchers.IO).launch {
                        var response = cookie_data.okHttpClient.newCall(request).execute()
                        var responseinfo=response.body?.string().toString()
                        //Log.d("GSON", "msg:${responseinfo}")
                        var json= Gson().fromJson(responseinfo, ShowOAReference::class.java)
                        when(json.status){
                            0->{
                                cookie_data.status=json.status
                                var data: ArrayList<OAReference> =json.data
                                comboboxData.removeAll(comboboxData)
                                for(i in 0 until data.size){
                                    comboboxData.add(data[i].oa_referenceNO2)
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
            is OAFileDeliveryRecordHeader ->{
                val body = FormBody.Builder()
                    .add("username", cookie_data.username)
                    .add("operation", operation)
                    .add("action",cookie_data.Actions.VIEW)
                    .add("view_type",view_type)
                    .add("view_hide",view_hide)
                    .add("csrfmiddlewaretoken", cookie_data.tokenValue)
                    .add("login_flag", cookie_data.loginflag)
                    .build()
                val request = Request.Builder()
                    .url(cookie_data.URL+"/shipping_order_management")
                    .header("User-Agent", "ERP_MOBILE")
                    .post(body)
                    .build()
                runBlocking {
                    var job = CoroutineScope(Dispatchers.IO).launch {
                        var response = cookie_data.okHttpClient.newCall(request).execute()
                        var responseinfo=response.body?.string().toString()
                        //Log.d("GSON", "msg:${responseinfo}")
                        var json= Gson().fromJson(responseinfo, ShowOAFileDeliveryRecordHeader::class.java)
                        when(json.status){
                            0->{
                                cookie_data.status=json.status
                                var data: ArrayList<OAFileDeliveryRecordHeader> =json.data
                                comboboxData.removeAll(comboboxData)
                                for(i in 0 until data.size){
                                    comboboxData.add(data[i].trackingNo)
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
            is CustomerOrderHeader ->{
                val body = FormBody.Builder()
                    .add("username", cookie_data.username)
                    .add("operation", operation)
                    .add("target", "header")
                    .add("action",cookie_data.Actions.VIEW)
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
                        //Log.d("GSON", "msg:${responseinfo}")
                        var json= Gson().fromJson(responseinfo, ShowCustomerOrderHeader::class.java)
                        when(json.status){
                            0->{
                                cookie_data.status=json.status
                                var data: ArrayList<CustomerOrderHeader> =json.data
                                comboboxData.removeAll(comboboxData)
                                for(i in 0 until data.size){
                                    comboboxData.add(data[i].poNo)
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

    private fun <T>show_CustomerOrder_combobox(operation:String,target:String,view_type:String,view_hide:String,fmt:T) {
        when(fmt)
        {
            is CustomerOrderHeader ->{
                val body = FormBody.Builder()
                    .add("username", cookie_data.username)
                    .add("operation", operation)
                    .add("target", target)
                    .add("action",cookie_data.Actions.VIEW)
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
                                cookie_data.status=json.status
                                var data: ArrayList<CustomerOrderHeader> =json.data
                                comboboxData.removeAll(comboboxData)
                                for(i in 0 until data.size){
                                    comboboxData.add(data[i].poNo)
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
            is CustomerForecastListHeader ->{
                val body = FormBody.Builder()
                    .add("username", cookie_data.username)
                    .add("operation", operation)
                    .add("target", target)
                    .add("action",cookie_data.Actions.VIEW)
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
                        var json= Gson().fromJson(responseinfo, ShowCustomerForecastListHeader::class.java)
                        when(json.status){
                            0->{
                                cookie_data.status=json.status
                                var data: ArrayList<CustomerForecastListHeader> =json.data
                                comboboxData.removeAll(comboboxData)
                                for(i in 0 until data.size){
                                    comboboxData.add(data[i]._id)
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
    private fun show_CustomerOrder(operation:String,target:String,view_type:String,view_hide:String) {
        when(operation+target){
            "CustomerOrderheader"->{
                val body = FormBody.Builder()
                    .add("username", cookie_data.username)
                    .add("operation", operation)
                    .add("target", target)
                    .add("action",cookie_data.Actions.VIEW)
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
            "CustomerOrderbody"->{
                val filter = JSONObject()
                filter.put("poNo",selectFilter)
                val body = FormBody.Builder()
                    .add("username", cookie_data.username)
                    .add("operation", operation)
                    .add("target", target)
                    .add("action",cookie_data.Actions.VIEW)
                    .add("view_type",view_type)
                    .add("view_hide",view_hide)
                    .add("data",filter.toString())
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
            "CustomerForecastListheader"->{
                val body = FormBody.Builder()
                    .add("username", cookie_data.username)
                    .add("operation", operation)
                    .add("target", target)
                    .add("action",cookie_data.Actions.VIEW)
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
            "CustomerForecastListbody"->{
                val filter = JSONObject()
                filter.put("header_id",selectFilter)
                val body = FormBody.Builder()
                    .add("username", cookie_data.username)
                    .add("operation", operation)
                    .add("target", target)
                    .add("action",cookie_data.Actions.VIEW)
                    .add("view_type",view_type)
                    .add("view_hide",view_hide)
                    .add("data",filter.toString())
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
                                cookie_data.customer_abbreviation_ComboboxData.removeAll(cookie_data.customer_abbreviation_ComboboxData)
                                cookie_data.customer_booking_prefix_ComboboxData.removeAll(cookie_data.customer_booking_prefix_ComboboxData)
                                for(i in 0 until data.size){
                                    cookie_data.customer_id_ComboboxData.add(data[i]._id)
                                    cookie_data.customer_abbreviation_ComboboxData.add(data[i].abbreviation)
                                    cookie_data.customer_booking_prefix_ComboboxData.add(data[i].booking_prefix)
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
                                cookie_data.shipping_name_ComboboxData.removeAll(cookie_data.shipping_name_ComboboxData)
                                cookie_data.shipping_number_name_ComboboxData.removeAll(cookie_data.shipping_number_name_ComboboxData)
                                for(i in 0 until data.size){
                                    cookie_data.shipping_number_ComboboxData.add(data[i].shipping_number)
                                    cookie_data.shipping_name_ComboboxData.add(data[i].shipping_name)
                                    cookie_data.shipping_number_name_ComboboxData.add(data[i].shipping_number+" "+data[i].shipping_name)
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
                                cookie_data.CustomerOrderHeader_swe_ComboboxData.removeAll(
                                    cookie_data.CustomerOrderHeader_swe_ComboboxData
                                )
                                for(i in 0 until data.size){
                                    cookie_data.CustomerOrderHeader_poNo_ComboboxData.add(data[i].poNo)
                                    cookie_data.CustomerOrderHeader_swe_ComboboxData.add(data[i].swe)
                                }
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
            is OAReference ->{
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
                    .url(cookie_data.URL+"/shipping_order_management")
                    .header("User-Agent", "ERP_MOBILE")
                    .post(body)
                    .build()
                runBlocking {
                    var job = CoroutineScope(Dispatchers.IO).launch {
                        var response = cookie_data.okHttpClient.newCall(request).execute()
                        var responseinfo=response.body?.string().toString()
                        var json= Gson().fromJson(responseinfo, ShowOAReference::class.java)
                        when(json.status){
                            0->{
                                cookie_data.status=json.status
                                var data: ArrayList<OAReference> =json.data
                                cookie_data.OAReference_oa_referenceNO_ComboboxData.removeAll(cookie_data.OAReference_oa_referenceNO_ComboboxData)
                                cookie_data.OAReference_oa_referenceNO1_ComboboxData.removeAll(cookie_data.OAReference_oa_referenceNO1_ComboboxData)
                                cookie_data.OAReference_oa_referenceNO2_ComboboxData.removeAll(cookie_data.OAReference_oa_referenceNO2_ComboboxData)
                                cookie_data.OAReference_oa_referenceNO3_ComboboxData.removeAll(cookie_data.OAReference_oa_referenceNO3_ComboboxData)
                                for(i in 0 until data.size){
                                    cookie_data.OAReference_oa_referenceNO_ComboboxData.add(data[i].oa_referenceNO1)
                                    cookie_data.OAReference_oa_referenceNO1_ComboboxData.add(data[i].oa_referenceNO2)
                                    cookie_data.OAReference_oa_referenceNO2_ComboboxData.add(data[i].oa_referenceNO3)
                                    cookie_data.OAReference_oa_referenceNO3_ComboboxData.add(data[i].oa_referenceNO4)
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
            is BookingNoticeHeader ->{
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
                    .url(cookie_data.URL+"/shipping_order_management")
                    .header("User-Agent", "ERP_MOBILE")
                    .post(body)
                    .build()
                runBlocking {
                    var job = CoroutineScope(Dispatchers.IO).launch {
                        var response = cookie_data.okHttpClient.newCall(request).execute()
                        var responseinfo=response.body?.string().toString()
                        var json= Gson().fromJson(responseinfo, ShowBookingNoticeHeader::class.java)
                        when(json.status){
                            0->{
                                cookie_data.status=json.status
                                var data: ArrayList<BookingNoticeHeader> =json.data
                                cookie_data.BookingNoticeHeader_id_ComboboxData.removeAll(cookie_data.BookingNoticeHeader_id_ComboboxData)
                                cookie_data.BookingNoticeHeader_notice_numbe_ComboboxData.removeAll(cookie_data.BookingNoticeHeader_notice_numbe_ComboboxData)
                                cookie_data.BookingNoticeHeader_customer_poNo_ComboboxData.removeAll(cookie_data.BookingNoticeHeader_customer_poNo_ComboboxData)
                                for(i in 0 until data.size){
                                    cookie_data.BookingNoticeHeader_id_ComboboxData.add(data[i]._id)
                                    cookie_data.BookingNoticeHeader_notice_numbe_ComboboxData.add(data[i].notice_number)
                                    cookie_data.BookingNoticeHeader_customer_poNo_ComboboxData.add(data[i].customer_poNo)
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
            is OAFileDeliveryRecordBody ->{
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
                    .url(cookie_data.URL+"/shipping_order_management")
                    .header("User-Agent", "ERP_MOBILE")
                    .post(body)
                    .build()
                runBlocking {
                    var job = CoroutineScope(Dispatchers.IO).launch {
                        var response = cookie_data.okHttpClient.newCall(request).execute()
                        var responseinfo=response.body?.string().toString()
                        var json= Gson().fromJson(responseinfo, ShowOAFileDeliveryRecordBody::class.java)
                        when(json.status){
                            0->{
                                cookie_data.status=json.status
                                var data: ArrayList<OAFileDeliveryRecordBody> =json.data
                                cookie_data.OAFileDeliveryRecordBody_trackingNo_ComboboxData.removeAll(cookie_data.OAFileDeliveryRecordBody_trackingNo_ComboboxData)
                                cookie_data.OAFileDeliveryRecordBody_booking_noticeNo_ComboboxData.removeAll(cookie_data.OAFileDeliveryRecordBody_booking_noticeNo_ComboboxData)
                                for(i in 0 until data.size){
                                    cookie_data.OAFileDeliveryRecordBody_trackingNo_ComboboxData.add(data[i].trackingNo)
                                    cookie_data.OAFileDeliveryRecordBody_booking_noticeNo_ComboboxData.add(data[i].booking_noticeNo)
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
            is OAFileDeliveryRecordHeader ->{
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
                    .url(cookie_data.URL+"/shipping_order_management")
                    .header("User-Agent", "ERP_MOBILE")
                    .post(body)
                    .build()
                runBlocking {
                    var job = CoroutineScope(Dispatchers.IO).launch {
                        var response = cookie_data.okHttpClient.newCall(request).execute()
                        var responseinfo=response.body?.string().toString()
                        var json= Gson().fromJson(responseinfo, ShowOAFileDeliveryRecordHeader::class.java)
                        when(json.status){
                            0->{
                                cookie_data.status=json.status
                                var data: ArrayList<OAFileDeliveryRecordHeader> =json.data
                                cookie_data.OAFileDeliveryRecordHeader_courier_company_ComboboxData.removeAll(cookie_data.OAFileDeliveryRecordHeader_courier_company_ComboboxData)
                                cookie_data.OAFileDeliveryRecordHeader_shippin_billing_month_ComboboxData.removeAll(cookie_data.OAFileDeliveryRecordHeader_shippin_billing_month_ComboboxData)
                                for(i in 0 until data.size){
                                    cookie_data.OAFileDeliveryRecordHeader_courier_company_ComboboxData.add(data[i].courier_company)
                                    cookie_data.OAFileDeliveryRecordHeader_shippin_billing_month_ComboboxData.add(data[i].shippin_billing_month)
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