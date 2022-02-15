package com.example.erp20.app09

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.res.Resources
import android.graphics.Rect
import android.os.Bundle
import android.text.InputType
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.erp20.Model.*
import com.example.erp20.R
import com.example.erp20.RecyclerAdapter.RecyclerItemProductControlOrderBodyDSimplifyAdapter
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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PopUpItemDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PopUpItemDetailFragment2(filter_id:String,filter_purchase_order_id:String,filter_prod_ctrl_order_number:String) : DialogFragment() {
    private lateinit var StockTransOrderBody_Simplify_adapter2: RecyclerItemStockTransOrderBodySimplifyAdapter2
    lateinit var Filter_id: String
    lateinit var Filter_purchase_order_id: String
    lateinit var Filter_prod_ctrl_order_number: String
    lateinit var recyclerView:RecyclerView
    init {
        Filter_id=filter_id
        Filter_purchase_order_id=filter_purchase_order_id
        Filter_prod_ctrl_order_number=filter_prod_ctrl_order_number

    }
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setWidthPercent(100)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pop_up_item_detail2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        show_header_body_filter("StockTransOrderBody","condition","False",Filter_id)
        show_relative_combobox("ItemBasicInfo","all","False", ItemBasicInfo())
        //show_relative_combobox("InvChangeTypeM","all","False", InvChangeTypeM())
        // show_relative_combobox("InvChangeTypeS","all","False", InvChangeTypeS())
        // show_relative_combobox("StoreArea","all","False", StoreArea())
        // show_relative_combobox("StoreLocal","all","False", StoreLocal())
        var popUpName=view.findViewById<TextView>(R.id.popUpName)
        popUpName.text="維護料件明細"

        val recyclerView=view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager= LinearLayoutManager(view.context)//設定Linear格式
        StockTransOrderBody_Simplify_adapter2= RecyclerItemStockTransOrderBodySimplifyAdapter2()
        recyclerView.adapter=StockTransOrderBody_Simplify_adapter2//找對應itemAdapter
        cookie_data.recyclerView=recyclerView
        //關閉
        var dismiss=view.findViewById<Button>(R.id.dismiss_btn)
        dismiss?.setOnClickListener {
            dismiss()
        }

        //刷新
        var refresh_btn=view.findViewById<Button>(R.id.refresh_btn)
        refresh_btn?.setOnClickListener {
            show_header_body_filter("StockTransOrderBody","condition","False",Filter_id)
            StockTransOrderBody_Simplify_adapter2= RecyclerItemStockTransOrderBodySimplifyAdapter2()
            recyclerView.adapter=StockTransOrderBody_Simplify_adapter2//找對應itemAdapter
        }
        Filter_item_id_combobox()//判斷料件編號的內容範圍
        //新增
        var add_btn=view.findViewById<Button>(R.id.add_btn)
        add_btn?.setOnClickListener {
            cookie_data.recyclerView=recyclerView
            val item = LayoutInflater.from(activity).inflate(R.layout.recycler_item_stock_trans_order_body_simplify2, null)
            val mAlertDialog = androidx.appcompat.app.AlertDialog.Builder(requireView().context)
            //mAlertDialog.setIcon(R.mipmap.ic_launcher_round) //set alertdialog icon
            mAlertDialog.setTitle("新增") //set alertdialog title
            //mAlertDialog.setMessage("確定要登出?") //set alertdialog message
            mAlertDialog.setView(item)

            val combobox=item.resources.getStringArray(R.array.combobox_yes_no)
            val arrayAdapter= ArrayAdapter(item.context,R.layout.combobox_item,combobox)
            val arrayAdapter01= ArrayAdapter(item.context,R.layout.combobox_item,cookie_data.Filter_item_id_ComboboxData)




            var header_id=item.findViewById<TextInputEditText>(R.id.edit_header_id)
            header_id.setText(Filter_id)
            header_id.inputType= InputType.TYPE_NULL
            var body_id=item.findViewById<TextInputEditText>(R.id.edit_body_id)
            body_id.inputType= InputType.TYPE_NULL
            var item_id=item.findViewById<AutoCompleteTextView>(R.id.edit_item_id)
            item_id.setAdapter(arrayAdapter01)

            var modify_count=item.findViewById<TextInputEditText>(R.id.edit_modify_count)
            modify_count.inputType= InputType.TYPE_NULL
            modify_count.isVisible=false

            var qc_insp_number=item.findViewById<TextInputEditText>(R.id.edit_qc_insp_number)
            qc_insp_number.isVisible=false
            var qc_time=item.findViewById<TextInputEditText>(R.id.edit_qc_time)
            qc_time.inputType=InputType.TYPE_NULL
            qc_time.isVisible=false
            //var ok_count=item.findViewById<TextInputEditText>(R.id.edit_ok_count)
            //var ng_count=item.findViewById<TextInputEditText>(R.id.edit_ng_count)
            //ng_count.setSelectAllOnFocus(true)
            var scrapped_count=item.findViewById<TextInputEditText>(R.id.edit_scrapped_count)
            scrapped_count.setSelectAllOnFocus(true)
            /*var is_rework=item.findViewById<AutoCompleteTextView>(R.id.edit_is_rework)
            is_rework.setAdapter(arrayAdapter)
            is_rework.isClickable=true*/

            val remark=item.findViewById<TextInputEditText>(R.id.edit_remark)

            //品檢日期
            qc_time.setOnClickListener {
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
                        qc_time.setText(Date)
                    },year,month,day).show()
            }



            mAlertDialog.setPositiveButton("取消") { dialog, id ->
                dialog.dismiss()
            }
            mAlertDialog.setNegativeButton("確定") { dialog, id ->
                //新增不能為空
                if( item_id.text.toString().trim().isEmpty() || (cookie_data.item_id_name_ComboboxData.indexOf(item_id.text.toString())==-1) ){
                    Toast.makeText(requireView().context,"Item ID 輸入錯誤", Toast.LENGTH_LONG).show()
                }
                else{
                    val addData= StockTransOrderBody()
                    addData.header_id=header_id.text.toString()
                    addData.item_id=item_id.text.toString().substring(0,item_id.text.toString().indexOf("\n"))
                    addData.modify_count=scrapped_count.text.toString().toDouble()
                    /*addData.main_trans_code=main_trans_code.text.toString()
                    addData.sec_trans_code=sec_trans_code.text.toString()
                    addData.store_area=store_area.text.toString()
                    addData.store_local=store_local.text.toString()*/
                    addData.qc_insp_number=qc_insp_number.text.toString()
                    if(qc_time.text.toString()==""){
                        addData.qc_time=null
                    }
                    else {
                        addData.qc_time=qc_time.text.toString().substring(0,qc_time.text.toString().indexOf("("))
                    }
                    //addData.ok_count=ok_count.text.toString().toInt()
                    //addData.ng_count=ng_count.text.toString().toInt()
                    addData.scrapped_count=scrapped_count.text.toString().toDouble()
                    //addData.is_rework=is_rework.text.toString().toBoolean()


                    addData.remark=remark.text.toString()
                    /*addData.creator= cookie_data.username
                    addData.create_time= Calendar.getInstance().getTime().toString()
                    addData.editor= cookie_data.username
                    addData.edit_time= Calendar.getInstance().getTime().toString()*/
                    add_header_body("StockTransOrderBody",addData)
                    when(cookie_data.status){
                        0-> {//成功

                            StockTransOrderBody_Simplify_adapter2.addItem(addData)
                            Toast.makeText(activity, cookie_data.msg, Toast.LENGTH_SHORT).show()
                        }
                        1->{//失敗
                            Toast.makeText(activity, cookie_data.msg, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
            mAlertDialog.show()
        }


    }

    private fun Filter_item_id_combobox(){
        var item1=""
        var item2=""
        item1=show_relative_combobox_filter("ProductionControlListRequisition","condition","False")
        item2=show_relative_combobox_filter("PurchaseOrderBody","condition","False")
        if(Filter_prod_ctrl_order_number=="" && Filter_purchase_order_id==""){
            cookie_data.Filter_item_id_ComboboxData.removeAll(cookie_data.Filter_item_id_ComboboxData)
            cookie_data.Filter_item_id_ComboboxData.addAll(cookie_data.item_id_name_ComboboxData)
        }
        else if(Filter_prod_ctrl_order_number!="" && Filter_purchase_order_id==""){
            cookie_data.Filter_item_id_ComboboxData.removeAll(cookie_data.Filter_item_id_ComboboxData)
            cookie_data.Filter_item_id_ComboboxData.add(item1+"\n"+cookie_data.item_name_ComboboxData[cookie_data.item_id_ComboboxData.indexOf(item1)])
        }
        else if(Filter_prod_ctrl_order_number=="" && Filter_purchase_order_id!=""){
            cookie_data.Filter_item_id_ComboboxData.removeAll(cookie_data.Filter_item_id_ComboboxData)
            cookie_data.Filter_item_id_ComboboxData.add(item2+"\n"+cookie_data.item_name_ComboboxData[cookie_data.item_id_ComboboxData.indexOf(item2)])
        }
        else if(Filter_prod_ctrl_order_number!="" && Filter_purchase_order_id!="")
        {
            cookie_data.Filter_item_id_ComboboxData.removeAll(cookie_data.Filter_item_id_ComboboxData)
            cookie_data.Filter_item_id_ComboboxData.add(item1+"\n"+cookie_data.item_name_ComboboxData[cookie_data.item_id_ComboboxData.indexOf(item1)])
            cookie_data.Filter_item_id_ComboboxData.add(item2+"\n"+cookie_data.item_name_ComboboxData[cookie_data.item_id_ComboboxData.indexOf(item2)])
        }
        cookie_data.Filter_item_id_ComboboxData=cookie_data.Filter_item_id_ComboboxData.distinct().toCollection(java.util.ArrayList())
    }
    //異動資料單頭單身過濾
    private fun show_header_body_filter(operation:String,view_type:String,view_hide:String,selectedfilter:String) {
        when(operation){
            "StockTransOrderBody"->{
                val filter = JSONObject()
                filter.put("is_closed",false)
                filter.put("header_id",selectedfilter)
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
                                cookie_data.item_id_name_ComboboxData.removeAll(cookie_data.item_id_name_ComboboxData)
                                for(i in 0 until data.size){
                                    cookie_data.item_id_ComboboxData.add(data[i]._id)
                                    cookie_data.item_name_ComboboxData.add(data[i].name)
                                    cookie_data.semi_finished_product_number_ComboboxData.add(data[i].semi_finished_product_number)
                                    cookie_data.is_exemption_ComboboxData.add(data[i].is_exemption)
                                    cookie_data.item_id_name_ComboboxData.add(data[i]._id+"\n"+data[i].name)
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

    private fun show_relative_combobox_filter(operation:String,view_type:String,view_hide:String):String {
        var temp=""
        when(operation)
        {
            "ProductionControlListRequisition" ->{
                val filter = JSONObject()
                //filter.put("is_closed",false)
                filter.put("prod_ctrl_order_number",Filter_prod_ctrl_order_number)
                println(filter)
                val body = FormBody.Builder()
                    .add("username", cookie_data.username)
                    .add("operation", operation)
                    .add("action", cookie_data.Actions.VIEW)
                    .add("view_type","condition")
                    .add("view_hide","False")
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
                        var json= Gson().fromJson(responseinfo, ShowProductionControlListRequisition::class.java)
                        when(json.status){
                            0->{
                                cookie_data.status =json.status
                                var data: ArrayList<ProductionControlListRequisition> =json.data
                                for(i in 0 until data.size){
                                    temp=data[i].item_id
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
            "PurchaseOrderBody" ->{
                val filter = JSONObject()
                //filter.put("is_closed",false)
                filter.put("body_id",Filter_purchase_order_id)
                val body = FormBody.Builder()
                    .add("username", cookie_data.username)
                    .add("operation", operation)
                    .add("action", cookie_data.Actions.VIEW)
                    .add("view_type","condition")
                    .add("view_hide","False")
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
                        var responseinfo=response.body?.string().toString()
                        var json= Gson().fromJson(responseinfo, ShowPurchaseOrderBody::class.java)
                        when(json.status){
                            0->{
                                cookie_data.status =json.status
                                var data: ArrayList<PurchaseOrderBody> =json.data
                                for(i in 0 until data.size){
                                    temp=data[i].item_id
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
        }
        return temp
    }

    fun DialogFragment.setWidthPercent(percentage: Int) {
        val percent = percentage.toFloat() / 100
        val dm = Resources.getSystem().displayMetrics
        val rect = dm.run { Rect(0, 0, widthPixels, heightPixels) }
        val percentWidth = rect.width() * percent
        dialog?.window?.setLayout(percentWidth.toInt(), ViewGroup.LayoutParams.WRAP_CONTENT)
    }


    /*companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PopUpItemDetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PopUpItemDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }*/
}