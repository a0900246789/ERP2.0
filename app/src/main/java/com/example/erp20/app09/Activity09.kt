package com.example.erp20.app09

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
import com.example.erp20.RecyclerAdapter.RecyclerItemProductControlOrderBodyADetailAdapter
import com.example.erp20.RecyclerAdapter.RecyclerItemProductControlOrderBodyADetailAdapter3
import com.example.erp20.RecyclerAdapter.RecyclerItemProductControlOrderBodyDAdapter
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

class Activity09 : AppCompatActivity() {
    private lateinit var ProductControlOrderBody_A_Detail_adapter3: RecyclerItemProductControlOrderBodyADetailAdapter3
    private lateinit var StockTransOrderHeaderSimplify_adapter: RecyclerItemStockTransOrderHeaderSimplifyAdapter
    private lateinit var StockTransOrderHeaderSimplify_adapter2: RecyclerItemStockTransOrderHeaderSimplifyAdapter2
    var comboboxData: MutableList<String> = mutableListOf<String>()
    lateinit var selectFilter:String
    lateinit var recyclerView:RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_09)
    }

    override fun onResume() {
        super.onResume()
        val theTextView = findViewById<TextView>(R.id._text)

        val recyclerView=findViewById<RecyclerView>(R.id.recyclerView)

        //combobox選單內容
        val autoCompleteTextView=findViewById<AutoCompleteTextView>(R.id.autoCompleteText)
        val combobox=resources.getStringArray(R.array.combobox09)
        val arrayAdapter= ArrayAdapter(this,R.layout.combobox_item,combobox)
        autoCompleteTextView.setAdapter(arrayAdapter)

        val searchbtn=findViewById<Button>(R.id.search_btn)
        val addbtn=findViewById<Button>(R.id.add_btn)
        //每次換選單內容add_btn失靈
        autoCompleteTextView?.setOnItemClickListener { parent, view, position, id ->
            addbtn?.isEnabled=false
        }
        //搜尋按鈕
        searchbtn?.setOnClickListener {
            cookie_data.recyclerView=recyclerView
            theTextView?.text = autoCompleteTextView?.text
            when(autoCompleteTextView?.text.toString()){
                "生產完工報工(生產線)"->{
                    show_relative_combobox("PLineBasicInfo","all","False", PLineBasicInfo())
                    show_combobox("ProductControlOrderBody_A","condition","False",
                        ProductControlOrderBody_A()
                    )
                    //println(comboboxData)
                    val data = comboboxData.toMutableList()
                    comboboxData.removeAll(comboboxData)
                    for(i in 0 until data.size){
                        comboboxData.add( data[i]+" "+ cookie_data.pline_name_ComboboxData[cookie_data.pline_id_ComboboxData.indexOf(data[i])] )
                    }
                    var comboboxData_set=comboboxData.distinct()
                    //println(comboboxData_set)

                    //combobox show
                    val item = LayoutInflater.from(this).inflate(R.layout.filter_combobox2, null)
                    val mAlertDialog = AlertDialog.Builder(this)
                    mAlertDialog.setTitle("篩選排序") //set alertdialog title
                    mAlertDialog.setView(item)
                    //filter_combobox選單內容
                    val comboboxView=item.findViewById<AutoCompleteTextView>(R.id.autoCompleteText)
                    val comboboxView2=item.findViewById<AutoCompleteTextView>(R.id.autoCompleteText2)
                    val combobox2=resources.getStringArray(R.array.productControlSort2)
                    comboboxView.setText(comboboxData_set.first())
                    comboboxView.setAdapter(ArrayAdapter(this,R.layout.combobox_item,comboboxData_set))
                    comboboxView2.setAdapter(ArrayAdapter(this,R.layout.combobox_item,combobox2))
                    mAlertDialog.setPositiveButton("取消") { dialog, id ->
                        dialog.dismiss()
                    }
                    mAlertDialog.setNegativeButton("確定") { dialog, id ->
                        //println(comboboxView.text)
                        selectFilter =comboboxView.text.toString().substring(0,comboboxView.text.toString().indexOf(" "))
                        //println(selectFilter)
                        show_relative_combobox("ItemBasicInfo","all","False", ItemBasicInfo())
                        show_relative_combobox("ProductControlOrderHeader","all","False", ProductControlOrderHeader())
                        show_relative_combobox("MeBody","all","False", MeBody())
                        show_header_body_filter("ProductControlOrderBody_A","condition","False",selectFilter)//type=all or condition
                        when(cookie_data.status)
                        {
                            0->{
                                Toast.makeText(this, "資料載入", Toast.LENGTH_SHORT).show()
                                recyclerView.layoutManager= LinearLayoutManager(this)//設定Linear格式
                                ProductControlOrderBody_A_Detail_adapter3= RecyclerItemProductControlOrderBodyADetailAdapter3(comboboxView2.text.toString())
                                recyclerView.adapter=ProductControlOrderBody_A_Detail_adapter3//找對應itemAdapter
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
                "退料作業(不合格品)"->{
                    val item = LayoutInflater.from(this).inflate(R.layout.filter_combobox, null)
                    val mAlertDialog = AlertDialog.Builder(this)
                    //mAlertDialog.setIcon(R.mipmap.ic_launcher_round) //set alertdialog icon
                    mAlertDialog.setTitle("篩選日期") //set alertdialog title
                    //mAlertDialog.setMessage("確定要登出?") //set alertdialog message
                    mAlertDialog.setView(item)
                    //filter_combobox選單內容
                    val comboboxView=item.findViewById<AutoCompleteTextView>(R.id.autoCompleteText)
                    val dateF = SimpleDateFormat("yyyy-MM-dd(EEEE)", Locale.TAIWAN)
                    var C= Calendar.getInstance()
                    C.set(Calendar.DAY_OF_MONTH,1)
                    var Date= dateF.format(C.time)
                    comboboxView.setText(Date)
                    comboboxView.setOnClickListener {
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
                                val Date1= dateF.format(SelectedDate.time)
                                comboboxView.setText(Date1)
                            },year,month,day).show()
                    }
                    //comboboxView.setAdapter(ArrayAdapter(this,R.layout.combobox_item,comboboxData))
                    mAlertDialog.setPositiveButton("取消") { dialog, id ->
                        dialog.dismiss()
                    }
                    mAlertDialog.setNegativeButton("確定") { dialog, id ->
                        //println(comboboxView.text)
                        selectFilter=comboboxView.text.toString().substring(0,comboboxView.text.toString().indexOf("("))
                        //println(selectFilter)
                        show_header_body("StockTransOrderHeader","all","False")//type=combobox or all
                        show_relative_combobox("PLineBasicInfo","all","False", PLineBasicInfo())
                        show_relative_combobox("InvChangeTypeM","all","False", InvChangeTypeM())
                        show_relative_combobox("InvChangeTypeS","all","False", InvChangeTypeS())
                        show_relative_combobox("PurchaseOrderHeader","all","False", PurchaseOrderHeader())
                        show_relative_combobox("PurchaseOrderBody","all","False", PurchaseOrderBody())
                        show_relative_combobox("PurchaseBatchOrder","all","False", PurchaseBatchOrder())
                        show_relative_combobox("ProductControlOrderBody_A","all","False", ProductControlOrderBody_A())
                        show_relative_combobox("MeBody","all","False", MeBody())
                        show_relative_combobox("ItemBasicInfo","all","False", ItemBasicInfo())
                        when(cookie_data.status)
                        {
                            0->{
                                Toast.makeText(this, "資料載入", Toast.LENGTH_SHORT).show()
                                recyclerView.layoutManager= LinearLayoutManager(this)//設定Linear格式
                                StockTransOrderHeaderSimplify_adapter= RecyclerItemStockTransOrderHeaderSimplifyAdapter(selectFilter)
                                recyclerView.adapter=StockTransOrderHeaderSimplify_adapter//找對應itemAdapter
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
                "退料作業(報廢品)"->{
                    val item = LayoutInflater.from(this).inflate(R.layout.filter_combobox, null)
                    val mAlertDialog = AlertDialog.Builder(this)
                    //mAlertDialog.setIcon(R.mipmap.ic_launcher_round) //set alertdialog icon
                    mAlertDialog.setTitle("篩選日期") //set alertdialog title
                    //mAlertDialog.setMessage("確定要登出?") //set alertdialog message
                    mAlertDialog.setView(item)
                    //filter_combobox選單內容
                    val comboboxView=item.findViewById<AutoCompleteTextView>(R.id.autoCompleteText)
                    val dateF = SimpleDateFormat("yyyy-MM-dd(EEEE)", Locale.TAIWAN)
                    var C= Calendar.getInstance()
                    C.set(Calendar.DAY_OF_MONTH,1)
                    var Date= dateF.format(C.time)
                    comboboxView.setText(Date)
                    comboboxView.setOnClickListener {
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
                                val Date1= dateF.format(SelectedDate.time)
                                comboboxView.setText(Date1)
                            },year,month,day).show()
                    }
                    //comboboxView.setAdapter(ArrayAdapter(this,R.layout.combobox_item,comboboxData))
                    mAlertDialog.setPositiveButton("取消") { dialog, id ->
                        dialog.dismiss()
                    }
                    mAlertDialog.setNegativeButton("確定") { dialog, id ->
                        //println(comboboxView.text)
                        selectFilter=comboboxView.text.toString().substring(0,comboboxView.text.toString().indexOf("("))
                        //println(selectFilter)
                        show_header_body("StockTransOrderHeader","all","False")//type=combobox or all
                        show_relative_combobox("PLineBasicInfo","all","False", PLineBasicInfo())
                        show_relative_combobox("InvChangeTypeM","all","False", InvChangeTypeM())
                        show_relative_combobox("InvChangeTypeS","all","False", InvChangeTypeS())
                        show_relative_combobox("PurchaseOrderHeader","all","False", PurchaseOrderHeader())
                        show_relative_combobox("PurchaseOrderBody","all","False", PurchaseOrderBody())
                        show_relative_combobox("PurchaseBatchOrder","all","False", PurchaseBatchOrder())
                        show_relative_combobox("ProductControlOrderBody_A","all","False", ProductControlOrderBody_A())
                        show_relative_combobox("MeBody","all","False", MeBody())
                        show_relative_combobox("ItemBasicInfo","all","False", ItemBasicInfo())
                        when(cookie_data.status)
                        {
                            0->{
                                Toast.makeText(this, "資料載入", Toast.LENGTH_SHORT).show()
                                recyclerView.layoutManager= LinearLayoutManager(this)//設定Linear格式
                                StockTransOrderHeaderSimplify_adapter2= RecyclerItemStockTransOrderHeaderSimplifyAdapter2(selectFilter)
                                recyclerView.adapter=StockTransOrderHeaderSimplify_adapter2//找對應itemAdapter
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

            }
        }
        //新增按鈕
        addbtn?.setOnClickListener {
            cookie_data.recyclerView=recyclerView
            when(autoCompleteTextView?.text.toString()){
                "退料作業(不合格品)"->{
                    show_relative_combobox("StockTransOrderHeader","all","False", StockTransOrderHeader())
                    val item = LayoutInflater.from(this).inflate(R.layout.recycler_item_stock_trans_order_header_simplify, null)
                    val mAlertDialog = AlertDialog.Builder(this)
                    //mAlertDialog.setIcon(R.mipmap.ic_launcher_round) //set alertdialog icon
                    mAlertDialog.setTitle("新增") //set alertdialog title
                    //mAlertDialog.setMessage("確定要登出?") //set alertdialog message
                    mAlertDialog.setView(item)

                    val combobox=item.resources.getStringArray(R.array.combobox_yes_no)
                    val arrayAdapter= ArrayAdapter(item.context,R.layout.combobox_item,combobox)
                    val arrayAdapter01= ArrayAdapter(item.context,R.layout.combobox_item,cookie_data.pline_id_name_ComboboxData)
                    //val arrayAdapter02= ArrayAdapter(item.context,R.layout.combobox_item,cookie_data.inv_code_name_m_ComboboxData)
                    var arrayAdapter03= ArrayAdapter(item.context,R.layout.combobox_item,cookie_data.inv_code_name_s_ComboboxData)
                    var relativeCombobox04:ArrayList<String> = ArrayList<String>()
                    for(i in 0 until cookie_data.PurchaseBatchOrder_batch_id_ComboboxData.size){
                        if(cookie_data.PurchaseBatchOrder_is_closed_ComboboxData[i]){
                            relativeCombobox04.add(cookie_data.PurchaseBatchOrder_purchase_order_id_ComboboxData[i]+" "+
                                    cookie_data.PurchaseBatchOrder_batch_id_ComboboxData[i]+" "+
                                    cookie_data.PurchaseOrderHeader_vender_name_ComboboxData[cookie_data.PurchaseOrderHeader_poNo_ComboboxData.indexOf(cookie_data.PurchaseOrderBody_poNo_ComboboxData[cookie_data.PurchaseOrderBody_body_id_ComboboxData.indexOf(cookie_data.PurchaseBatchOrder_purchase_order_id_ComboboxData[i])])] )
                        }
                    }
                    val arrayAdapter04= ArrayAdapter(item.context,R.layout.combobox_item,relativeCombobox04)
                   /* var relativeCombobox05:ArrayList<String> = ArrayList<String>()
                    for(i in 0 until cookie_data.ProductControlOrderBody_A_complete_date_ComboboxData.size){
                        if(cookie_data.ProductControlOrderBody_A_complete_date_ComboboxData[i]!=null){
                            relativeCombobox05.add(cookie_data.ProductControlOrderBody_A_prod_ctrl_order_number_ComboboxData[i]+"\n"+
                                    cookie_data.item_name_ComboboxData[cookie_data.semi_finished_product_number_ComboboxData.indexOf(cookie_data.ProductControlOrderBody_A_semi_finished_prod_number_ComboboxData[i])]+"\n"+
                                    cookie_data.pline_name_ComboboxData[cookie_data.pline_id_ComboboxData.indexOf(cookie_data.ProductControlOrderBody_A_pline_id_ComboboxData[i])]+"\n"+
                                    cookie_data.MeBody_work_option_ComboboxData[cookie_data.MeBody_process_number_ComboboxData.indexOf(cookie_data.ProductControlOrderBody_A_me_code_ComboboxData[i])]
                            )
                        }
                    }
                    val arrayAdapter05= ArrayAdapter(item.context,R.layout.combobox_item,relativeCombobox05)*/

                    var _id=item.findViewById<TextInputEditText>(R.id.edit_id)
                    _id.inputType=InputType.TYPE_CLASS_NUMBER
                    var date=item.findViewById<TextInputEditText>(R.id.edit_date)
                    date.inputType=InputType.TYPE_NULL
                    var dept=item.findViewById<AutoCompleteTextView>(R.id.edit_dept)
                    dept.setAdapter(arrayAdapter01)
                    var main_trans_code=item.findViewById<AutoCompleteTextView>(R.id.edit_main_trans_code)
                    main_trans_code.setText("M03 退倉")
                    //main_trans_code.setAdapter(arrayAdapter02)
                    var sec_trans_code=item.findViewById<AutoCompleteTextView>(R.id.edit_sec_trans_code)
                    sec_trans_code.isClickable=true
                    sec_trans_code.setText("M03-2 來料不良")
                    var inv_code_m=main_trans_code.text.toString().substring(0,main_trans_code.text.toString().indexOf(" "))
                    //println(inv_code_m)
                    var ArrayList:ArrayList<String> = ArrayList<String>()
                    for(i in 0 until cookie_data.inv_code_s_inv_code_m_ComboboxData.size){
                        if(cookie_data.inv_code_s_inv_code_m_ComboboxData[i]==inv_code_m){
                            ArrayList.add(cookie_data.inv_code_name_s_ComboboxData[i])
                        }
                    }
                    arrayAdapter03= ArrayAdapter(item.context,R.layout.combobox_item,ArrayList)
                    sec_trans_code.setAdapter(arrayAdapter03)
                    var purchase_order_id=item.findViewById<AutoCompleteTextView>(R.id.edit_purchase_order_id)
                    purchase_order_id.setAdapter(arrayAdapter04)
                    var prod_ctrl_order_number=item.findViewById<AutoCompleteTextView>(R.id.edit_prod_ctrl_order_number)
                   // prod_ctrl_order_number.setAdapter(arrayAdapter05)
                    var illustrate=item.findViewById<TextInputEditText>(R.id.edit_illustrate)

                    //異動日期
                    date.setOnClickListener {
                        val dateF = SimpleDateFormat("yyyy-MM-dd(EEEE)", Locale.TAIWAN)
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
                                date.setText(Date)

                                //自動填單號
                                var maxNum=1
                                for(i in 0 until cookie_data.StockTransOrderHeader_id_ComboboxData.size){
                                    if(cookie_data.StockTransOrderHeader_id_ComboboxData[i].indexOf(mYear.toString()+(mMonth+1).toString()+"-")>=0){
                                        if(cookie_data.StockTransOrderHeader_id_ComboboxData[i].substring(cookie_data.StockTransOrderHeader_id_ComboboxData[i].indexOf("-")+1,
                                                cookie_data.StockTransOrderHeader_id_ComboboxData[i].length).toInt()>=maxNum){
                                            maxNum=cookie_data.StockTransOrderHeader_id_ComboboxData[i].substring(cookie_data.StockTransOrderHeader_id_ComboboxData[i].indexOf("-")+1,
                                                cookie_data.StockTransOrderHeader_id_ComboboxData[i].length).toInt()+1
                                        }
                                    }
                                }
                                _id.setText(mYear.toString()+(mMonth+1).toString()+"-"+maxNum.toString())
                            },year,month,day).show()
                    }
                    //主異動次別
                   main_trans_code.setOnItemClickListener { parent, view, position, id ->

                    }



                    //val remark=item.findViewById<TextInputEditText>(R.id.edit_remark)

                    mAlertDialog.setPositiveButton("取消") { dialog, id ->
                        dialog.dismiss()
                    }
                    mAlertDialog.setNegativeButton("確定") { dialog, id ->
                        //新增不能為空
                        if( _id.text.toString().trim().isEmpty() ||
                            date.text.toString().trim().isEmpty()  ||
                            dept.text.toString().trim().isEmpty()       ||
                            main_trans_code.text.toString().trim().isEmpty() ||
                            sec_trans_code.text.toString().trim().isEmpty()  ){
                            Toast.makeText(this,"Input required", Toast.LENGTH_LONG).show()
                        }
                        else{
                            val addData= StockTransOrderHeader()
                            addData._id=_id.text.toString()
                            addData.date=date.text.toString().substring(0,date.text.toString().indexOf("("))
                            addData.dept=dept.text.toString().substring(0,dept.text.toString().indexOf(" "))
                            addData.main_trans_code=main_trans_code.text.toString().substring(0,main_trans_code.text.toString().indexOf(" "))
                            addData.sec_trans_code=sec_trans_code.text.toString().substring(0,sec_trans_code.text.toString().indexOf(" "))
                            if(purchase_order_id.text.toString()==""){
                                addData.purchase_order_id=""
                            }
                            else{
                                addData.purchase_order_id=purchase_order_id.text.toString().substring(0,purchase_order_id.text.toString().indexOf(" "))
                            }
                            if(prod_ctrl_order_number.text.toString()==""){
                                addData.prod_ctrl_order_number=""
                            }
                            else{
                                addData.prod_ctrl_order_number=prod_ctrl_order_number.text.toString().substring(0,prod_ctrl_order_number.text.toString().indexOf("\n"))
                            }


                            addData.illustrate=illustrate.text.toString()


                            /*addData.remark=remark.text.toString()
                            addData.creator= cookie_data.username
                            addData.create_time= Calendar.getInstance().getTime().toString()
                            addData.editor= cookie_data.username
                            addData.edit_time= Calendar.getInstance().getTime().toString()*/
                            add_header_body("StockTransOrderHeader",addData)
                            when(cookie_data.status){
                                0-> {//成功

                                    StockTransOrderHeaderSimplify_adapter.addItem(addData)
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
                "退料作業(報廢品)"->{
                    show_relative_combobox("StockTransOrderHeader","all","False", StockTransOrderHeader())
                    val item = LayoutInflater.from(this).inflate(R.layout.recycler_item_stock_trans_order_header_simplify, null)
                    val mAlertDialog = AlertDialog.Builder(this)
                    //mAlertDialog.setIcon(R.mipmap.ic_launcher_round) //set alertdialog icon
                    mAlertDialog.setTitle("新增") //set alertdialog title
                    //mAlertDialog.setMessage("確定要登出?") //set alertdialog message
                    mAlertDialog.setView(item)

                    val combobox=item.resources.getStringArray(R.array.combobox_yes_no)
                    val arrayAdapter= ArrayAdapter(item.context,R.layout.combobox_item,combobox)
                    val arrayAdapter01= ArrayAdapter(item.context,R.layout.combobox_item,cookie_data.pline_id_name_ComboboxData)
                    //val arrayAdapter02= ArrayAdapter(item.context,R.layout.combobox_item,cookie_data.inv_code_name_m_ComboboxData)
                    var arrayAdapter03= ArrayAdapter(item.context,R.layout.combobox_item,cookie_data.inv_code_name_s_ComboboxData)
                    var relativeCombobox04:ArrayList<String> = ArrayList<String>()
                    for(i in 0 until cookie_data.PurchaseBatchOrder_batch_id_ComboboxData.size){
                        if(cookie_data.PurchaseBatchOrder_is_closed_ComboboxData[i]){
                            relativeCombobox04.add(cookie_data.PurchaseBatchOrder_purchase_order_id_ComboboxData[i]+" "+
                                    cookie_data.PurchaseBatchOrder_batch_id_ComboboxData[i]+" "+
                                    cookie_data.PurchaseOrderHeader_vender_name_ComboboxData[cookie_data.PurchaseOrderHeader_poNo_ComboboxData.indexOf(cookie_data.PurchaseOrderBody_poNo_ComboboxData[cookie_data.PurchaseOrderBody_body_id_ComboboxData.indexOf(cookie_data.PurchaseBatchOrder_purchase_order_id_ComboboxData[i])])] )
                        }
                    }
                    val arrayAdapter04= ArrayAdapter(item.context,R.layout.combobox_item,relativeCombobox04)
                  /*  var relativeCombobox05:ArrayList<String> = ArrayList<String>()
                    for(i in 0 until cookie_data.ProductControlOrderBody_A_complete_date_ComboboxData.size){
                        if(cookie_data.ProductControlOrderBody_A_complete_date_ComboboxData[i]!=null){
                            relativeCombobox05.add(cookie_data.ProductControlOrderBody_A_prod_ctrl_order_number_ComboboxData[i]+"\n"+
                                    cookie_data.item_name_ComboboxData[cookie_data.semi_finished_product_number_ComboboxData.indexOf(cookie_data.ProductControlOrderBody_A_semi_finished_prod_number_ComboboxData[i])]+"\n"+
                                    cookie_data.pline_name_ComboboxData[cookie_data.pline_id_ComboboxData.indexOf(cookie_data.ProductControlOrderBody_A_pline_id_ComboboxData[i])]+"\n"+
                                    cookie_data.MeBody_work_option_ComboboxData[cookie_data.MeBody_process_number_ComboboxData.indexOf(cookie_data.ProductControlOrderBody_A_me_code_ComboboxData[i])]
                            )
                        }
                    }
                    val arrayAdapter05= ArrayAdapter(item.context,R.layout.combobox_item,relativeCombobox05)*/

                    var _id=item.findViewById<TextInputEditText>(R.id.edit_id)
                    _id.inputType=InputType.TYPE_CLASS_NUMBER
                    var date=item.findViewById<TextInputEditText>(R.id.edit_date)
                    date.inputType=InputType.TYPE_NULL
                    var dept=item.findViewById<AutoCompleteTextView>(R.id.edit_dept)
                    dept.setAdapter(arrayAdapter01)
                    var main_trans_code=item.findViewById<AutoCompleteTextView>(R.id.edit_main_trans_code)
                    main_trans_code.setText("M06 報廢")
                   // main_trans_code.setAdapter(arrayAdapter02)
                    var sec_trans_code=item.findViewById<AutoCompleteTextView>(R.id.edit_sec_trans_code)
                    sec_trans_code.isClickable=true
                    sec_trans_code.setText("M06-1 不良品報廢")
                    var inv_code_m=main_trans_code.text.toString().substring(0,main_trans_code.text.toString().indexOf(" "))
                    //println(inv_code_m)
                    var ArrayList:ArrayList<String> = ArrayList<String>()

                    for(i in 0 until cookie_data.inv_code_s_inv_code_m_ComboboxData.size){
                        if(cookie_data.inv_code_s_inv_code_m_ComboboxData[i]==inv_code_m){
                            ArrayList.add(cookie_data.inv_code_name_s_ComboboxData[i])
                        }
                    }

                    arrayAdapter03= ArrayAdapter(item.context,R.layout.combobox_item,ArrayList)
                    sec_trans_code.setAdapter(arrayAdapter03)
                    var purchase_order_id=item.findViewById<AutoCompleteTextView>(R.id.edit_purchase_order_id)
                    purchase_order_id.setAdapter(arrayAdapter04)
                    var prod_ctrl_order_number=item.findViewById<AutoCompleteTextView>(R.id.edit_prod_ctrl_order_number)
                   // prod_ctrl_order_number.setAdapter(arrayAdapter05)
                    var illustrate=item.findViewById<TextInputEditText>(R.id.edit_illustrate)

                    //異動日期
                    date.setOnClickListener {
                        val dateF = SimpleDateFormat("yyyy-MM-dd(EEEE)", Locale.TAIWAN)
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
                                date.setText(Date)

                                //自動填單號
                                var maxNum=1
                                for(i in 0 until cookie_data.StockTransOrderHeader_id_ComboboxData.size){
                                    if(cookie_data.StockTransOrderHeader_id_ComboboxData[i].indexOf(mYear.toString()+(mMonth+1).toString()+"-")>=0){
                                        if(cookie_data.StockTransOrderHeader_id_ComboboxData[i].substring(cookie_data.StockTransOrderHeader_id_ComboboxData[i].indexOf("-")+1,
                                                cookie_data.StockTransOrderHeader_id_ComboboxData[i].length).toInt()>=maxNum){
                                            maxNum=cookie_data.StockTransOrderHeader_id_ComboboxData[i].substring(cookie_data.StockTransOrderHeader_id_ComboboxData[i].indexOf("-")+1,
                                                cookie_data.StockTransOrderHeader_id_ComboboxData[i].length).toInt()+1
                                        }
                                    }
                                }
                                _id.setText(mYear.toString()+(mMonth+1).toString()+"-"+maxNum.toString())
                            },year,month,day).show()
                    }

                    //主異動次別
                    main_trans_code.setOnItemClickListener { parent, view, position, id ->

                    }



                    //val remark=item.findViewById<TextInputEditText>(R.id.edit_remark)

                    mAlertDialog.setPositiveButton("取消") { dialog, id ->
                        dialog.dismiss()
                    }
                    mAlertDialog.setNegativeButton("確定") { dialog, id ->
                        //新增不能為空
                        if( _id.text.toString().trim().isEmpty() ||
                            date.text.toString().trim().isEmpty()  ||
                            dept.text.toString().trim().isEmpty()       ||
                            main_trans_code.text.toString().trim().isEmpty() ||
                            sec_trans_code.text.toString().trim().isEmpty()  ){
                            Toast.makeText(this,"Input required", Toast.LENGTH_LONG).show()
                        }
                        else{
                            val addData= StockTransOrderHeader()
                            addData._id=_id.text.toString()
                            addData.date=date.text.toString().substring(0,date.text.toString().indexOf("("))
                            addData.dept=dept.text.toString().substring(0,dept.text.toString().indexOf(" "))
                            addData.main_trans_code=main_trans_code.text.toString().substring(0,main_trans_code.text.toString().indexOf(" "))
                            addData.sec_trans_code=sec_trans_code.text.toString().substring(0,sec_trans_code.text.toString().indexOf(" "))
                            if(purchase_order_id.text.toString()==""){
                                addData.purchase_order_id=""
                            }
                            else{
                                addData.purchase_order_id=purchase_order_id.text.toString().substring(0,purchase_order_id.text.toString().indexOf(" "))
                            }
                            if(prod_ctrl_order_number.text.toString()==""){
                                addData.prod_ctrl_order_number=""
                            }
                            else{
                                addData.prod_ctrl_order_number=prod_ctrl_order_number.text.toString().substring(0,prod_ctrl_order_number.text.toString().indexOf("\n"))
                            }


                            addData.illustrate=illustrate.text.toString()


                            /*addData.remark=remark.text.toString()
                            addData.creator= cookie_data.username
                            addData.create_time= Calendar.getInstance().getTime().toString()
                            addData.editor= cookie_data.username
                            addData.edit_time= Calendar.getInstance().getTime().toString()*/
                            add_header_body("StockTransOrderHeader",addData)
                            when(cookie_data.status){
                                0-> {//成功

                                    StockTransOrderHeaderSimplify_adapter2.addItem(addData)
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
            "StockTransOrderHeader"->{
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
        }
    }

    //異動資料單頭單身過濾
    private fun show_header_body_filter(operation:String,view_type:String,view_hide:String,selectedfilter:String) {
        when(operation){
            "ProductControlOrderBody_A"->{
                val filter = JSONObject()
                filter.put("is_closed",false)
                filter.put("pline_id",selectedfilter)
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
                                cookie_data.semi_finished_product_number_ComboboxData.removeAll(cookie_data.semi_finished_product_number_ComboboxData)
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
                                cookie_data.pline_id_name_ComboboxData.removeAll(cookie_data.pline_id_name_ComboboxData)
                                cookie_data.out_sourceing_ComboboxData.removeAll(cookie_data.out_sourceing_ComboboxData)
                                for(i in 0 until data.size){
                                    cookie_data.pline_name_ComboboxData.add(data[i].name)
                                    cookie_data.pline_id_ComboboxData.add(data[i]._id)
                                    cookie_data.pline_id_name_ComboboxData.add(data[i]._id+" "+data[i].name)
                                    cookie_data.out_sourceing_ComboboxData.add(data[i].is_outsourcing)
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

                                for(i in 0 until data.size){
                                    cookie_data.ProductControlOrderBody_A_prod_ctrl_order_number_ComboboxData.add(data[i].prod_ctrl_order_number)
                                    cookie_data.ProductControlOrderBody_A_me_code_ComboboxData.add(data[i].me_code)
                                    cookie_data.ProductControlOrderBody_A_semi_finished_prod_number_ComboboxData.add(data[i].semi_finished_prod_number)
                                    cookie_data.ProductControlOrderBody_A_pline_id_ComboboxData.add(data[i].pline_id)
                                    cookie_data.ProductControlOrderBody_A_complete_date_ComboboxData.add(data[i].complete_date)


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

                                for(i in 0 until data.size){
                                    cookie_data.PurchaseOrderBody_poNo_ComboboxData.add(data[i].poNo)
                                    cookie_data.PurchaseOrderBody_body_id_ComboboxData.add(data[i].body_id)

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
                                cookie_data.StockTransOrderHeader_id_ComboboxData.removeAll(
                                    cookie_data.StockTransOrderHeader_id_ComboboxData)

                                for(i in 0 until data.size){
                                    cookie_data.StockTransOrderHeader_id_ComboboxData.add(data[i]._id)
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