package com.example.erp20.ClassFragment.PUR

import android.os.Bundle
import android.text.InputType
import android.util.Log
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
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.FormBody
import okhttp3.Request
import org.json.JSONObject
import java.util.*

class PUR04maintainFragment : Fragment() {
    private lateinit var VenderBasicInfo_adapter:RecyclerItemVenderBasicInfoAdapter
    private lateinit var ItemBasicInfo_adapter:RecyclerItemItemBasicInfoAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pur04maintain, container, false)
    }

    override fun onResume() {
        super.onResume()
        val theTextView = getView()?.findViewById<TextView>(R.id._text)

        val recyclerView= getView()?.findViewById<RecyclerView>(R.id.recyclerView)

        //combobox選單內容
        val autoCompleteTextView=getView()?.findViewById<AutoCompleteTextView>(R.id.autoCompleteText)
        val combobox=resources.getStringArray(R.array.comboboxPUR04maintain)
        val arrayAdapter= ArrayAdapter(requireContext(),R.layout.combobox_item,combobox)
        autoCompleteTextView?.setAdapter(arrayAdapter)

        val searchbtn=getView()?.findViewById<Button>(R.id.search_btn)
        val addbtn=view?.findViewById<Button>(R.id.add_btn)
        //每次換選單內容add_btn失靈
        autoCompleteTextView?.setOnItemClickListener { parent, view, position, id ->
            addbtn?.isEnabled=false
        }
        //搜尋按鈕
        searchbtn?.setOnClickListener {
            theTextView?.text = autoCompleteTextView?.text

            when(autoCompleteTextView?.text.toString()){
                "廠商基本資料維護"->{
                    showBasic("VenderBasicInfo","all","False")//type=combobox or all
                    when(cookie_data.status)
                    {
                        0->{
                            Toast.makeText(activity, "資料載入", Toast.LENGTH_SHORT).show()
                            recyclerView?.layoutManager=LinearLayoutManager(context)//設定Linear格式
                            VenderBasicInfo_adapter= RecyclerItemVenderBasicInfoAdapter()
                            recyclerView?.adapter=VenderBasicInfo_adapter//找對應itemAdapter
                            addbtn?.isEnabled=true
                        }
                        1->{
                            Toast.makeText(activity, cookie_data.msg, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                "料件基本資料維護"->{
                    showBasic("ItemBasicInfo","all","False")//type=combobox or all
                    when(cookie_data.status)
                    {
                        0->{
                            Toast.makeText(activity, "資料載入", Toast.LENGTH_SHORT).show()
                            recyclerView?.layoutManager=LinearLayoutManager(context)//設定Linear格式
                            ItemBasicInfo_adapter= RecyclerItemItemBasicInfoAdapter()
                            recyclerView?.adapter=ItemBasicInfo_adapter//找對應itemAdapter
                            addbtn?.isEnabled=true
                        }
                        1->{
                            Toast.makeText(activity, cookie_data.msg, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
        //新增按鈕
        addbtn?.setOnClickListener {
            when(autoCompleteTextView?.text.toString()){
                "廠商基本資料維護"->{
                    val item = LayoutInflater.from(activity).inflate(R.layout.recycler_item_vender_basic_info, null)
                    val mAlertDialog = AlertDialog.Builder(requireView().context)
                    //mAlertDialog.setIcon(R.mipmap.ic_launcher_round) //set alertdialog icon
                    mAlertDialog.setTitle("新增") //set alertdialog title
                    //mAlertDialog.setMessage("確定要登出?") //set alertdialog message
                    mAlertDialog.setView(item)

                    //true false combobbox
                    val combobox_yes_no=resources.getStringArray(R.array.combobox_yes_no)
                    val arrayAdapter_yes_no= ArrayAdapter(requireContext(),R.layout.combobox_item,combobox_yes_no)


                    var _id=item.findViewById<TextInputEditText>(R.id.edit_id)
                    var abbreviation=item.findViewById<TextInputEditText>(R.id.edit_abbreviation)
                    var full_name=item.findViewById<TextInputEditText>(R.id.edit_full_name)
                    var card_number=item.findViewById<TextInputEditText>(R.id.edit_card_number)
                    var is_supplier=item.findViewById<AutoCompleteTextView>(R.id.edit_is_supplier)
                    is_supplier.setAdapter(arrayAdapter_yes_no)
                    var is_processor=item.findViewById<AutoCompleteTextView>(R.id.edit_is_processor)
                    is_processor.setAdapter(arrayAdapter_yes_no)
                    var is_other=item.findViewById<AutoCompleteTextView>(R.id.edit_is_other)
                    is_other.setAdapter(arrayAdapter_yes_no)
                    var rank=item.findViewById<TextInputEditText>(R.id.edit_rank)
                    var evaluation_date=item.findViewById<TextInputEditText>(R.id.edit_evaluation_date)
                    var days_before_delivery=item.findViewById<TextInputEditText>(R.id.edit_days_before_delivery)
                    var days_before_delivery_time=item.findViewById<TextInputEditText>(R.id.edit_days_before_delivery_time)


                    val remark=item.findViewById<TextInputEditText>(R.id.edit_remark)

                    mAlertDialog.setPositiveButton("取消") { dialog, id ->
                        dialog.dismiss()
                    }
                    mAlertDialog.setNegativeButton("確定") { dialog, id ->
                        //新增不能為空
                        if( _id.text.toString().trim().isEmpty() ||
                            abbreviation.text.toString().trim().isEmpty()  ||
                            full_name.text.toString().trim().isEmpty() ||
                            card_number.text.toString().trim().isEmpty()  ||
                            is_supplier.text.toString().trim().isEmpty() ||
                            is_processor.text.toString().trim().isEmpty()  ||
                            is_other.text.toString().trim().isEmpty() ||
                            rank.text.toString().trim().isEmpty()  ||
                            evaluation_date.text.toString().trim().isEmpty() ||
                            days_before_delivery.text.toString().trim().isEmpty()  ||
                            days_before_delivery_time.text.toString().trim().isEmpty() ){
                            Toast.makeText(requireView().context,"Input required",Toast.LENGTH_LONG).show()
                        }
                        else{
                            val addData=VenderBasicInfo()
                            addData._id=_id.text.toString()
                            addData.abbreviation=abbreviation.text.toString()
                            addData.full_name=full_name.text.toString()
                            addData.full_name=full_name.text.toString()
                            addData.is_supplier=is_supplier.text.toString().toBoolean()
                            addData.is_processor=is_processor.text.toString().toBoolean()
                            addData.is_other=is_other.text.toString().toBoolean()
                            addData.rank=rank.text.toString()
                            addData.evaluation_date=evaluation_date.text.toString()
                            addData.days_before_delivery=days_before_delivery.text.toString().toInt()
                            addData.days_before_delivery_time=days_before_delivery_time.text.toString()


                            addData.remark=remark.text.toString()
                            addData.creator=cookie_data.username
                            addData.create_time=Calendar.getInstance().getTime().toString()
                            addData.editor=cookie_data.username
                            addData.edit_time=Calendar.getInstance().getTime().toString()
                            addBasic("VenderBasicInfo",addData)
                            when(cookie_data.status){
                                0-> {//成功

                                    VenderBasicInfo_adapter.addItem(addData)
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
                "料件基本資料維護"->{
                    val item = LayoutInflater.from(activity).inflate(R.layout.recycler_item_item_basic_info, null)
                    val mAlertDialog = AlertDialog.Builder(requireView().context)
                    //mAlertDialog.setIcon(R.mipmap.ic_launcher_round) //set alertdialog icon
                    mAlertDialog.setTitle("新增") //set alertdialog title
                    //mAlertDialog.setMessage("確定要登出?") //set alertdialog message
                    mAlertDialog.setView(item)

                    //true false combobbox
                    val combobox_yes_no=resources.getStringArray(R.array.combobox_yes_no)
                    val arrayAdapter_yes_no= ArrayAdapter(requireContext(),R.layout.combobox_item,combobox_yes_no)


                    var _id=item.findViewById<TextInputEditText>(R.id.edit_id)
                    var name=item.findViewById<TextInputEditText>(R.id.edit_name)
                    var specification=item.findViewById<TextInputEditText>(R.id.edit_specification)
                    var size=item.findViewById<TextInputEditText>(R.id.edit_size)
                    var unit_of_measurement=item.findViewById<TextInputEditText>(R.id.edit_unit_of_measurement)
                    var item_type=item.findViewById<TextInputEditText>(R.id.edit_item_type)
                    var semi_finished_product_number=item.findViewById<TextInputEditText>(R.id.edit_semi_finished_product_number)
                    var is_purchased_parts=item.findViewById<AutoCompleteTextView>(R.id.edit_is_purchased_parts)
                    is_purchased_parts.setAdapter(arrayAdapter_yes_no)
                    var MOQ=item.findViewById<TextInputEditText>(R.id.edit_MOQ)
                    var MOQ_time=item.findViewById<TextInputEditText>(R.id.edit_MOQ_time)
                    var batch=item.findViewById<TextInputEditText>(R.id.edit_batch)
                    var batch_time=item.findViewById<TextInputEditText>(R.id.edit_batch_time)
                    var vender_id=item.findViewById<TextInputEditText>(R.id.edit_vender_id)
                    var is_machining_parts=item.findViewById<AutoCompleteTextView>(R.id.edit_is_machining_parts)
                    is_machining_parts.setAdapter(arrayAdapter_yes_no)
                    var pline_id=item.findViewById<TextInputEditText>(R.id.edit_pline_id)
                    var change_mold=item.findViewById<TextInputEditText>(R.id.edit_change_mold)
                    var mold_unit_of_timer=item.findViewById<TextInputEditText>(R.id.edit_mold_unit_of_timer)
                    var change_powder=item.findViewById<TextInputEditText>(R.id.edit_change_powder)
                    var powder_unit_of_timer=item.findViewById<TextInputEditText>(R.id.edit_powder_unit_of_timer)
                    var LT=item.findViewById<TextInputEditText>(R.id.edit_LT)
                    var LT_unit_of_timer=item.findViewById<TextInputEditText>(R.id.edit_LT_unit_of_timer)
                    var feed_in_advance_day=item.findViewById<TextInputEditText>(R.id.edit_feed_in_advance_day)
                    var feed_in_advance_time=item.findViewById<TextInputEditText>(R.id.edit_feed_in_advance_time)
                    var release_date=item.findViewById<TextInputEditText>(R.id.edit_release_date)
                    var is_schedule_adjustment_materials=item.findViewById<AutoCompleteTextView>(R.id.edit_is_schedule_adjustment_materials)
                    is_schedule_adjustment_materials.setAdapter(arrayAdapter_yes_no)
                    var schedule_adjustment_materials_time=item.findViewById<TextInputEditText>(R.id.edit_schedule_adjustment_materials_time)
                    var is_long_delivery=item.findViewById<AutoCompleteTextView>(R.id.edit_is_long_delivery)
                    is_long_delivery.setAdapter(arrayAdapter_yes_no)
                    var long_delivery_time=item.findViewById<TextInputEditText>(R.id.edit_long_delivery_time)
                    var is_low_yield=item.findViewById<AutoCompleteTextView>(R.id.edit_is_low_yield)
                    is_low_yield.setAdapter(arrayAdapter_yes_no)
                    var low_yield_time=item.findViewById<TextInputEditText>(R.id.edit_low_yield_time)
                    var is_min_manpower=item.findViewById<AutoCompleteTextView>(R.id.edit_is_min_manpower)
                    is_min_manpower.setAdapter(arrayAdapter_yes_no)
                    var min_manpower_reduce_ratio=item.findViewById<TextInputEditText>(R.id.edit_min_manpower_reduce_ratio)
                    var is_standard_manpower=item.findViewById<AutoCompleteTextView>(R.id.edit_is_standard_manpower)
                    is_standard_manpower.setAdapter(arrayAdapter_yes_no)
                    var standard_manpower=item.findViewById<TextInputEditText>(R.id.edit_standard_manpower)
                    var unit_of_timer=item.findViewById<TextInputEditText>(R.id.edit_unit_of_timer)
                    var open_line_time=item.findViewById<TextInputEditText>(R.id.edit_open_line_time)
                    var card_number=item.findViewById<TextInputEditText>(R.id.edit_card_number)
                    var number_of_accounts=item.findViewById<TextInputEditText>(R.id.edit_number_of_accounts)
                    var settlement_date=item.findViewById<TextInputEditText>(R.id.edit_settlement_date)
                    var is_production_materials=item.findViewById<AutoCompleteTextView>(R.id.edit_is_production_materials)
                    is_production_materials.setAdapter(arrayAdapter_yes_no)

                    val remark=item.findViewById<TextInputEditText>(R.id.edit_remark)

                    mAlertDialog.setPositiveButton("取消") { dialog, id ->
                        dialog.dismiss()
                    }
                    mAlertDialog.setNegativeButton("確定") { dialog, id ->
                        //新增不能為空
                        if( _id.text.toString().trim().isEmpty() ||
                            name.text.toString().trim().isEmpty()  ||
                            specification.text.toString().trim().isEmpty() ||
                            size.text.toString().trim().isEmpty()  ||
                            unit_of_measurement.text.toString().trim().isEmpty() ||
                            item_type.text.toString().trim().isEmpty()  ||
                            semi_finished_product_number.text.toString().trim().isEmpty() ||
                            is_purchased_parts.text.toString().trim().isEmpty()  ||
                            MOQ.text.toString().trim().isEmpty()  ||
                            MOQ_time.text.toString().trim().isEmpty() ||
                            batch.text.toString().trim().isEmpty() ||
                            batch_time.text.toString().trim().isEmpty()  ||
                            vender_id.text.toString().trim().isEmpty() ||
                            is_machining_parts.text.toString().trim().isEmpty()  ||
                            pline_id.text.toString().trim().isEmpty() ||
                            change_mold.text.toString().trim().isEmpty()  ||
                            mold_unit_of_timer.text.toString().trim().isEmpty() ||
                            change_powder.text.toString().trim().isEmpty()  ||
                            powder_unit_of_timer.text.toString().trim().isEmpty()  ||
                            LT.text.toString().trim().isEmpty() ||
                            LT_unit_of_timer.text.toString().trim().isEmpty() ||
                            feed_in_advance_day.text.toString().trim().isEmpty()  ||
                            feed_in_advance_time.text.toString().trim().isEmpty() ||
                            release_date.text.toString().trim().isEmpty()  ||
                            is_schedule_adjustment_materials.text.toString().trim().isEmpty() ||
                            schedule_adjustment_materials_time.text.toString().trim().isEmpty()  ||
                            is_long_delivery.text.toString().trim().isEmpty() ||
                            long_delivery_time.text.toString().trim().isEmpty()  ||
                            is_low_yield.text.toString().trim().isEmpty()  ||
                            low_yield_time.text.toString().trim().isEmpty() ||
                            is_min_manpower.text.toString().trim().isEmpty() ||
                            min_manpower_reduce_ratio.text.toString().trim().isEmpty()  ||
                            is_standard_manpower.text.toString().trim().isEmpty() ||
                            standard_manpower.text.toString().trim().isEmpty()  ||
                            unit_of_timer.text.toString().trim().isEmpty() ||
                            open_line_time.text.toString().trim().isEmpty()  ||
                            card_number.text.toString().trim().isEmpty() ||
                            number_of_accounts.text.toString().trim().isEmpty()  ||
                            settlement_date.text.toString().trim().isEmpty()  ||
                            is_production_materials.text.toString().trim().isEmpty()   ){
                            Toast.makeText(requireView().context,"Input required",Toast.LENGTH_LONG).show()
                        }
                        else{
                            val addData=ItemBasicInfo()
                            addData._id=_id.text.toString()
                            addData.name=name.text.toString()
                            addData.specification=specification.text.toString()
                            addData.size=size.text.toString()
                            addData.unit_of_measurement=unit_of_measurement.text.toString()
                            addData.item_type=item_type.text.toString()
                            addData.semi_finished_product_number=semi_finished_product_number.text.toString()
                            addData.is_purchased_parts=is_purchased_parts.text.toString().toBoolean()
                            addData.MOQ=MOQ.text.toString().toDouble()
                            addData.MOQ_time=MOQ_time.text.toString()

                            addData.batch=batch.text.toString().toDouble()
                            addData.batch_time=batch_time.text.toString()
                            addData.vender_id=vender_id.text.toString()
                            addData.is_machining_parts=is_machining_parts.text.toString().toBoolean()
                            addData.pline_id=pline_id.text.toString()
                            addData.change_mold=change_mold.text.toString().toDouble()
                            addData.mold_unit_of_timer=mold_unit_of_timer.text.toString()
                            addData.change_powder=change_powder.text.toString().toDouble()
                            addData.powder_unit_of_timer=powder_unit_of_timer.text.toString()
                            addData.LT=LT.text.toString()

                            addData.LT_unit_of_timer=LT_unit_of_timer.text.toString()
                            addData.feed_in_advance_day=feed_in_advance_day.text.toString().toInt()
                            addData.feed_in_advance_time=feed_in_advance_time.text.toString()
                            addData.release_date=release_date.text.toString()
                            addData.is_schedule_adjustment_materials=is_schedule_adjustment_materials.text.toString().toBoolean()
                            addData.schedule_adjustment_materials_time=schedule_adjustment_materials_time.text.toString()
                            addData.is_long_delivery=is_long_delivery.text.toString().toBoolean()
                            addData.long_delivery_time=long_delivery_time.text.toString()
                            addData.is_low_yield=is_low_yield.text.toString().toBoolean()
                            addData.low_yield_time=low_yield_time.text.toString()

                            addData.is_min_manpower=is_min_manpower.text.toString().toBoolean()
                            addData.min_manpower_reduce_ratio=min_manpower_reduce_ratio.text.toString().toDouble()
                            addData.is_standard_manpower=is_standard_manpower.text.toString().toBoolean()
                            addData.standard_manpower=standard_manpower.text.toString().toInt()
                            addData.unit_of_timer=unit_of_timer.text.toString()
                            addData.open_line_time=open_line_time.text.toString()
                            addData.card_number=card_number.text.toString()
                            addData.number_of_accounts=number_of_accounts.text.toString().toDouble()
                            addData.settlement_date=settlement_date.text.toString()
                            addData.is_production_materials=is_production_materials.text.toString().toBoolean()

                            addData.remark=remark.text.toString()
                            addData.creator=cookie_data.username
                            addData.create_time=Calendar.getInstance().getTime().toString()
                            addData.editor=cookie_data.username
                            addData.edit_time=Calendar.getInstance().getTime().toString()
                            addBasic("ItemBasicInfo",addData)
                            when(cookie_data.status){
                                0-> {//成功

                                    ItemBasicInfo_adapter.addItem(addData)
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

        }

    }



    //基本資料
    private fun showBasic(operation:String,view_type:String,view_hide:String) {
        val body = FormBody.Builder()
            .add("card_number", cookie_data.card_number)
            .add("username", cookie_data.username)
            .add("operation", operation)
            .add("action",cookie_data.Actions.VIEW)
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
                cookie_data.response_data=response.body?.string().toString()
                Log.d("GSON", "msg:${cookie_data.response_data}")

            }
            job.join()
            val responseInfo = Gson().fromJson(cookie_data.response_data, Response::class.java)
            cookie_data.status=responseInfo.status
            if(responseInfo.msg !=null){
                cookie_data.msg=responseInfo.msg
            }
            //Log.d("GSON", "msg:${responseInfo.data[0].create_time}")
        }

    }
    private fun <T>addBasic(operation:String,addData:T) {

        when(addData)
        {
            is VenderBasicInfo->{
                val add = JSONObject()
                add.put("_id",addData._id)
                add.put("abbreviation",addData.abbreviation)
                add.put("full_name",addData.full_name)
                add.put("card_number",addData.card_number)
                add.put("is_supplier",addData.is_supplier)
                add.put("is_processor",addData.is_processor)
                add.put("is_other",addData.is_other)
                add.put("rank",addData.rank)
                add.put("evaluation_date",addData.evaluation_date)
                add.put("days_before_delivery",addData.days_before_delivery)
                add.put("days_before_delivery_time",addData.days_before_delivery_time)

                add.put("remark",addData.remark)
                add.put("creator",cookie_data.username)
                add.put("editor",cookie_data.username)
                Log.d("GSON", "msg:${add}")
                val body = FormBody.Builder()
                    .add("card_number", cookie_data.card_number)
                    .add("username", cookie_data.username)
                    .add("operation", operation)
                    .add("action","1")
                    .add("data",add.toString())
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
                        cookie_data.response_data=response.body?.string().toString()
                        Log.d("GSON", "msg:${cookie_data.response_data}")
                    }
                    job.join()
                    val responseInfo = Gson().fromJson(cookie_data.response_data, Response::class.java)
                    cookie_data.status=responseInfo.status
                    cookie_data.msg=responseInfo.msg
                }
            }
            is ItemBasicInfo->{
                val add = JSONObject()
                add.put("_id",addData._id)
                add.put("name",addData.name)
                add.put("specification",addData.specification)
                add.put("size",addData.size)
                add.put("unit_of_measurement",addData.unit_of_measurement)
                add.put("item_type",addData.item_type)
                add.put("semi_finished_product_number",addData.semi_finished_product_number)
                add.put("is_purchased_parts",addData.is_purchased_parts)
                add.put("MOQ",addData.MOQ)
                add.put("MOQ_time",addData.MOQ_time)

                add.put("batch",addData.batch)
                add.put("batch_time",addData.batch_time)
                add.put("vender_id",addData.vender_id)
                add.put("is_machining_parts",addData.is_machining_parts)
                add.put("pline_id",addData.pline_id)
                add.put("change_mold",addData.change_mold)
                add.put("mold_unit_of_timer",addData.mold_unit_of_timer)
                add.put("change_powder",addData.change_powder)
                add.put("powder_unit_of_timer",addData.powder_unit_of_timer)
                add.put("LT",addData.LT)

                add.put("LT_unit_of_timer",addData.LT_unit_of_timer)
                add.put("feed_in_advance_day",addData.feed_in_advance_day)
                add.put("feed_in_advance_time",addData.feed_in_advance_time)
                add.put("release_date",addData.release_date)
                add.put("is_schedule_adjustment_materials",addData.is_schedule_adjustment_materials)
                add.put("schedule_adjustment_materials_time",addData.schedule_adjustment_materials_time)
                add.put("is_long_delivery",addData.is_long_delivery)
                add.put("long_delivery_time",addData.long_delivery_time)
                add.put("is_low_yield",addData.is_low_yield)
                add.put("low_yield_time",addData.low_yield_time)

                add.put("is_min_manpower",addData.is_min_manpower)
                add.put("min_manpower_reduce_ratio",addData.min_manpower_reduce_ratio)
                add.put("is_standard_manpower",addData.is_standard_manpower)
                add.put("standard_manpower",addData.standard_manpower)
                add.put("unit_of_timer",addData.unit_of_timer)
                add.put("open_line_time",addData.open_line_time)
                add.put("card_number",addData.card_number)
                add.put("number_of_accounts",addData.number_of_accounts)
                add.put("settlement_date",addData.settlement_date)
                add.put("is_production_materials",addData.is_production_materials)

                add.put("remark",addData.remark)
                add.put("creator",cookie_data.username)
                add.put("editor",cookie_data.username)
                Log.d("GSON", "msg:${add}")
                val body = FormBody.Builder()
                    .add("card_number", cookie_data.card_number)
                    .add("username", cookie_data.username)
                    .add("operation", operation)
                    .add("action","1")
                    .add("data",add.toString())
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
}