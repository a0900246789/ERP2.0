package com.example.erp20.ClassFragment.Produce

import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
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
import java.lang.reflect.Array
import java.util.*
import kotlin.collections.ArrayList

class Produce02maintainFragment : Fragment() {

    private lateinit var ItemBasicInfo_adapter:RecyclerItemItemBasicInfoAdapter
    private lateinit var PLineBasicInfo_adapter:RecyclerItemPLineBasicInfoAdapter
    private lateinit var ProductionEquipmentBasicInfo_adapter:RecyclerItemProductionEquipmentBasicInfoAdapter
    private lateinit var MeWorkstationBasicInfo_adapter:RecyclerItemMeWorkstationBasicInfoAdapter
    private lateinit var MeWorkstationEquipmentBasicInfo_adapter:RecyclerItemMeWorkstationEquipmentBasicInfoAdapter
    private lateinit var MeHeader_adapter:RecyclerItemMeHeaderAdapter
    private lateinit var MeBody_adapter:RecyclerItemMeBodyAdapter
    var comboboxData: MutableList<String> = mutableListOf<String>()
    lateinit var selectFilter:String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_produce02maintain, container, false)
    }

    override fun onResume() {
        super.onResume()
        val theTextView = getView()?.findViewById<TextView>(R.id._text)

        val recyclerView= getView()?.findViewById<RecyclerView>(R.id.recyclerView)

        //combobox選單內容
        val autoCompleteTextView=getView()?.findViewById<AutoCompleteTextView>(R.id.autoCompleteText)
        val combobox=resources.getStringArray(R.array.comboboxProduce02maintain)
        val arrayAdapter=ArrayAdapter(requireContext(),R.layout.combobox_item,combobox)
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

                /*"港口維護"->{
                    showDef("Port","all","False")//type=combobox or all
                    when(cookie_data.status)
                    {
                        0->{
                            Toast.makeText(activity, "資料載入", Toast.LENGTH_SHORT).show()
                            recyclerView?.layoutManager=LinearLayoutManager(context)//設定Linear格式
                            Port_adapter= RecyclerItemPortAdapter()
                            recyclerView?.adapter=Port_adapter//找對應itemAdapter
                            addbtn?.isEnabled=true
                        }
                        1->{
                            Toast.makeText(activity, "載入失敗", Toast.LENGTH_SHORT).show()
                        }
                    }
                }*/
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
                "生產線維護"->{
                    showBasic("PLineBasicInfo","all","False")//type=combobox or all
                    when(cookie_data.status)
                    {
                        0->{
                            Toast.makeText(activity, "資料載入", Toast.LENGTH_SHORT).show()
                            recyclerView?.layoutManager=LinearLayoutManager(context)//設定Linear格式
                            PLineBasicInfo_adapter= RecyclerItemPLineBasicInfoAdapter()
                            recyclerView?.adapter=PLineBasicInfo_adapter//找對應itemAdapter
                            addbtn?.isEnabled=true
                        }
                        1->{
                            Toast.makeText(activity, cookie_data.msg, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                "生產設備基本資料維護"->{
                    showBasic("ProductionEquipmentBasicInfo","all","False")//type=combobox or all
                    when(cookie_data.status)
                    {
                        0->{
                            Toast.makeText(activity, "資料載入", Toast.LENGTH_SHORT).show()
                            recyclerView?.layoutManager=LinearLayoutManager(context)//設定Linear格式
                            ProductionEquipmentBasicInfo_adapter= RecyclerItemProductionEquipmentBasicInfoAdapter()
                            recyclerView?.adapter=ProductionEquipmentBasicInfo_adapter//找對應itemAdapter
                            addbtn?.isEnabled=true
                        }
                        1->{
                            Toast.makeText(activity, cookie_data.msg, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                "工程編制表維護(單頭)"->{
                    show_S_Basic("Me","header","all","False")//type=combobox or all
                    when(cookie_data.status)
                    {
                        0->{
                            Toast.makeText(activity, "資料載入", Toast.LENGTH_SHORT).show()
                            recyclerView?.layoutManager=LinearLayoutManager(context)//設定Linear格式
                            MeHeader_adapter= RecyclerItemMeHeaderAdapter()
                            recyclerView?.adapter=MeHeader_adapter//找對應itemAdapter
                            addbtn?.isEnabled=true
                        }
                        1->{
                            Toast.makeText(activity, cookie_data.msg, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                "工程編制表維護(單身)"->{
                    show_S_Basic_combobox("Me","header","all","False",MeHeader())//type=combobox or all
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
                                show_S_Basic("Me","body","condition","False")//type=all or condition
                                when(cookie_data.status)
                                {
                                    0->{
                                        Toast.makeText(activity, "資料載入", Toast.LENGTH_SHORT).show()
                                        recyclerView?.layoutManager=LinearLayoutManager(context)//設定Linear格式
                                        MeBody_adapter= RecyclerItemMeBodyAdapter()
                                        recyclerView?.adapter=MeBody_adapter//找對應itemAdapter
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

                }
                "工程編制表維護(工作站)"->{
                    showBasic("MeWorkstationBasicInfo","all","False")//type=combobox or all
                    when(cookie_data.status)
                    {
                        0->{
                            Toast.makeText(activity, "資料載入", Toast.LENGTH_SHORT).show()
                            recyclerView?.layoutManager=LinearLayoutManager(context)//設定Linear格式
                            MeWorkstationBasicInfo_adapter= RecyclerItemMeWorkstationBasicInfoAdapter()
                            recyclerView?.adapter=MeWorkstationBasicInfo_adapter//找對應itemAdapter
                            addbtn?.isEnabled=true
                        }
                        1->{
                            Toast.makeText(activity, cookie_data.msg, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                "工程編制表維護(站別-設備)"->{
                    showBasic("MeWorkstationEquipmentBasicInfo","all","False")//type=combobox or all
                    when(cookie_data.status)
                    {
                        0->{
                            Toast.makeText(activity, "資料載入", Toast.LENGTH_SHORT).show()
                            recyclerView?.layoutManager=LinearLayoutManager(context)//設定Linear格式
                            MeWorkstationEquipmentBasicInfo_adapter= RecyclerItemMeWorkstationEquipmentBasicInfoAdapter()
                            recyclerView?.adapter=MeWorkstationEquipmentBasicInfo_adapter//找對應itemAdapter
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

                /*"港口維護"->{
                    val item = LayoutInflater.from(activity).inflate(R.layout.recycler_item_port, null)
                    val mAlertDialog = AlertDialog.Builder(requireView().context)
                    //mAlertDialog.setIcon(R.mipmap.ic_launcher_round) //set alertdialog icon
                    mAlertDialog.setTitle("新增") //set alertdialog title
                    //mAlertDialog.setMessage("確定要登出?") //set alertdialog message
                    mAlertDialog.setView(item)
                    mAlertDialog.setPositiveButton("取消") { dialog, id ->
                        dialog.dismiss()
                    }
                    mAlertDialog.setNegativeButton("確定") { dialog, id ->
                        val port_id =item.findViewById<TextInputEditText>(R.id.edit_port_id)
                        val port_name=item.findViewById<TextInputEditText>(R.id.edit_port_name)

                        val remark=item.findViewById<TextInputEditText>(R.id.edit_remark)
                        //新增不能為空
                        if( port_id.text.toString().trim().isEmpty() || port_name.text.toString().trim().isEmpty()){
                            Toast.makeText(requireView().context,"Input required",Toast.LENGTH_LONG).show()
                        }
                        else{
                            val addData=Port("","")
                            addData.port_id=port_id.text.toString()
                            addData.port_name=port_name.text.toString()
                            addData.remark=remark.text.toString()
                            addData.creator=cookie_data.username
                            addData.create_time=Calendar.getInstance().getTime().toString()
                            addData.editor=cookie_data.username
                            addData.edit_time=Calendar.getInstance().getTime().toString()
                            addDef("Port",addData)
                            when(cookie_data.status){
                                0-> {//成功
                                    Port_adapter.addItem(addData)
                                    Toast.makeText(activity, cookie_data.msg, Toast.LENGTH_SHORT).show()
                                }
                                1->{//失敗
                                    Toast.makeText(activity, cookie_data.msg, Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                    mAlertDialog.show()
                }*/
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
                "生產線維護"->{
                    val item = LayoutInflater.from(activity).inflate(R.layout.recycler_item__p_line_basic_info, null)
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
                    var is_selfmade=item.findViewById<AutoCompleteTextView>(R.id.edit_is_selfmade)
                    is_selfmade.setAdapter(arrayAdapter_yes_no)
                    var is_outsourcing=item.findViewById<AutoCompleteTextView>(R.id.edit_is_outsourcing)
                    is_outsourcing.setAdapter(arrayAdapter_yes_no)
                    var vender_id=item.findViewById<TextInputEditText>(R.id.edit_vender_id)


                    val remark=item.findViewById<TextInputEditText>(R.id.edit_remark)

                    mAlertDialog.setPositiveButton("取消") { dialog, id ->
                        dialog.dismiss()
                    }
                    mAlertDialog.setNegativeButton("確定") { dialog, id ->
                        //新增不能為空
                        if( _id.text.toString().trim().isEmpty() ||
                            name.text.toString().trim().isEmpty()  ||
                            is_selfmade.text.toString().trim().isEmpty() ||
                            is_outsourcing.text.toString().trim().isEmpty()  ||
                            vender_id.text.toString().trim().isEmpty()    ){
                            Toast.makeText(requireView().context,"Input required",Toast.LENGTH_LONG).show()
                        }
                        else{
                            val addData=PLineBasicInfo()
                            addData._id=_id.text.toString()
                            addData.name=name.text.toString()
                            addData.is_selfmade=is_selfmade.text.toString().toBoolean()
                            addData.is_outsourcing=is_outsourcing.text.toString().toBoolean()
                            addData.vender_id=vender_id.text.toString()


                            addData.remark=remark.text.toString()
                            addData.creator=cookie_data.username
                            addData.create_time=Calendar.getInstance().getTime().toString()
                            addData.editor=cookie_data.username
                            addData.edit_time=Calendar.getInstance().getTime().toString()
                            addBasic("PLineBasicInfo",addData)
                            when(cookie_data.status){
                                0-> {//成功

                                    PLineBasicInfo_adapter.addItem(addData)
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
                "生產設備基本資料維護"->{
                    val item = LayoutInflater.from(activity).inflate(R.layout.recycler_item_production_equipment_basic_info, null)
                    val mAlertDialog = AlertDialog.Builder(requireView().context)
                    //mAlertDialog.setIcon(R.mipmap.ic_launcher_round) //set alertdialog icon
                    mAlertDialog.setTitle("新增") //set alertdialog title
                    //mAlertDialog.setMessage("確定要登出?") //set alertdialog message
                    mAlertDialog.setView(item)

                    //true false combobbox
                    val combobox_yes_no=resources.getStringArray(R.array.combobox_yes_no)
                    val arrayAdapter_yes_no= ArrayAdapter(requireContext(),R.layout.combobox_item,combobox_yes_no)


                    var production_equipment_number=item.findViewById<TextInputEditText>(R.id.edit_production_equipment_number)
                    var full_name=item.findViewById<TextInputEditText>(R.id.edit_full_name)
                    var abbreviation=item.findViewById<TextInputEditText>(R.id.edit_abbreviation)
                    var pline_id=item.findViewById<TextInputEditText>(R.id.edit_pline_id)
                    var asset_number=item.findViewById<TextInputEditText>(R.id.edit_asset_number)
                    var equipment_type=item.findViewById<TextInputEditText>(R.id.edit_equipment_type)
                    var is_production_equipment=item.findViewById<AutoCompleteTextView>(R.id.edit_is_production_equipment)
                    is_production_equipment.setAdapter(arrayAdapter_yes_no)
                    var is_mould=item.findViewById<AutoCompleteTextView>(R.id.edit_is_mould)
                    is_mould.setAdapter(arrayAdapter_yes_no)
                    var is_fixture=item.findViewById<AutoCompleteTextView>(R.id.edit_is_fixture)
                    is_fixture.setAdapter(arrayAdapter_yes_no)
                    var is_checking_fixture=item.findViewById<AutoCompleteTextView>(R.id.edit_is_checking_fixture)
                    is_checking_fixture.setAdapter(arrayAdapter_yes_no)


                    val remark=item.findViewById<TextInputEditText>(R.id.edit_remark)

                    mAlertDialog.setPositiveButton("取消") { dialog, id ->
                        dialog.dismiss()
                    }
                    mAlertDialog.setNegativeButton("確定") { dialog, id ->
                        //新增不能為空
                        if( production_equipment_number.text.toString().trim().isEmpty() ||
                            full_name.text.toString().trim().isEmpty()  ||
                            abbreviation.text.toString().trim().isEmpty() ||
                            pline_id.text.toString().trim().isEmpty()  ||
                            asset_number.text.toString().trim().isEmpty() ||
                            equipment_type.text.toString().trim().isEmpty() ||
                            is_production_equipment.text.toString().trim().isEmpty()  ||
                            is_mould.text.toString().trim().isEmpty() ||
                            is_fixture.text.toString().trim().isEmpty()  ||
                            is_checking_fixture.text.toString().trim().isEmpty()     ){
                            Toast.makeText(requireView().context,"Input required",Toast.LENGTH_LONG).show()
                        }
                        else{
                            val addData=ProductionEquipmentBasicInfo()
                            addData.production_equipment_number=production_equipment_number.text.toString()
                            addData.full_name=full_name.text.toString()
                            addData.abbreviation=abbreviation.text.toString()
                            addData.pline_id=pline_id.text.toString()
                            addData.asset_number=asset_number.text.toString()
                            addData.equipment_type=equipment_type.text.toString()
                            addData.is_production_equipment=is_production_equipment.text.toString().toBoolean()
                            addData.is_mould=is_mould.text.toString().toBoolean()
                            addData.is_fixture=is_fixture.text.toString().toBoolean()
                            addData.is_checking_fixture=is_checking_fixture.text.toString().toBoolean()


                            addData.remark=remark.text.toString()
                            addData.creator=cookie_data.username
                            addData.create_time=Calendar.getInstance().getTime().toString()
                            addData.editor=cookie_data.username
                            addData.edit_time=Calendar.getInstance().getTime().toString()
                            addBasic("ProductionEquipmentBasicInfo",addData)
                            when(cookie_data.status){
                                0-> {//成功

                                    ProductionEquipmentBasicInfo_adapter.addItem(addData)
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
                "工程編制表維護(單頭)"->{
                    val item = LayoutInflater.from(activity).inflate(R.layout.recycler_item_me_header, null)
                    val mAlertDialog = AlertDialog.Builder(requireView().context)
                    //mAlertDialog.setIcon(R.mipmap.ic_launcher_round) //set alertdialog icon
                    mAlertDialog.setTitle("新增") //set alertdialog title
                    //mAlertDialog.setMessage("確定要登出?") //set alertdialog message
                    mAlertDialog.setView(item)


                    var _id=item.findViewById<TextInputEditText>(R.id.edit_id)
                    var product_id=item.findViewById<TextInputEditText>(R.id.edit_product_id)
                    var theoretical_output=item.findViewById<TextInputEditText>(R.id.edit_theoretical_output)
                    var unit_of_timer=item.findViewById<TextInputEditText>(R.id.edit_unit_of_timer)
                    var issue_date=item.findViewById<TextInputEditText>(R.id.edit_issue_date)
                    var item_id=item.findViewById<TextInputEditText>(R.id.edit_item_id)

                    val remark=item.findViewById<TextInputEditText>(R.id.edit_remark)

                    mAlertDialog.setPositiveButton("取消") { dialog, id ->
                        dialog.dismiss()
                    }
                    mAlertDialog.setNegativeButton("確定") { dialog, id ->
                        //新增不能為空
                        if( _id.text.toString().trim().isEmpty() ||
                            product_id.text.toString().trim().isEmpty()  ||
                            theoretical_output.text.toString().trim().isEmpty()       ||
                            unit_of_timer.text.toString().trim().isEmpty() ||
                            issue_date.text.toString().trim().isEmpty()  ||
                            item_id.text.toString().trim().isEmpty()   ){
                            Toast.makeText(requireView().context,"Input required",Toast.LENGTH_LONG).show()
                        }
                        else{
                            val addData=MeHeader()
                            addData._id=_id.text.toString()
                            addData.product_id=product_id.text.toString()
                            addData.theoretical_output=theoretical_output.text.toString().toInt()
                            addData.unit_of_timer=unit_of_timer.text.toString()
                            addData.issue_date=issue_date.text.toString()
                            addData.item_id=item_id.text.toString()


                            addData.remark=remark.text.toString()
                            addData.creator=cookie_data.username
                            addData.create_time=Calendar.getInstance().getTime().toString()
                            addData.editor=cookie_data.username
                            addData.edit_time=Calendar.getInstance().getTime().toString()
                            add_S_Basic("Me","header",addData)
                            when(cookie_data.status){
                                0-> {//成功

                                    MeHeader_adapter.addItem(addData)
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
                "工程編制表維護(單身)"->{
                    val item = LayoutInflater.from(activity).inflate(R.layout.recycler_item_me_body, null)
                    val mAlertDialog = AlertDialog.Builder(requireView().context)
                    //mAlertDialog.setIcon(R.mipmap.ic_launcher_round) //set alertdialog icon
                    mAlertDialog.setTitle("新增") //set alertdialog title
                    //mAlertDialog.setMessage("確定要登出?") //set alertdialog message
                    mAlertDialog.setView(item)

                    var _id=item.findViewById<TextInputEditText>(R.id.edit_id)
                    _id.setText(selectFilter)
                    _id.inputType=InputType.TYPE_NULL
                    var process_number=item.findViewById<TextInputEditText>(R.id.edit_process_number)
                    var processing_sequence=item.findViewById<TextInputEditText>(R.id.edit_processing_sequence)
                    var process_code=item.findViewById<TextInputEditText>(R.id.edit_process_code)
                    var item_id=item.findViewById<TextInputEditText>(R.id.edit_item_id)
                    var pline_id=item.findViewById<TextInputEditText>(R.id.edit_pline_id)
                    var standard_working_hours_optimal=item.findViewById<TextInputEditText>(R.id.edit_standard_working_hours_optimal)
                    var unit_of_timer_optimal=item.findViewById<TextInputEditText>(R.id.edit_unit_of_timer_optimal)
                    var number_of_staff_optimal=item.findViewById<TextInputEditText>(R.id.edit_number_of_staff_optimal)
                    var theoretical_output_optimal=item.findViewById<TextInputEditText>(R.id.edit_theoretical_output_optimal)
                    var standard_working_hours_less=item.findViewById<TextInputEditText>(R.id.edit_standard_working_hours_less)
                    var unit_of_timer_less=item.findViewById<TextInputEditText>(R.id.edit_unit_of_timer_less)
                    var number_of_staff_less=item.findViewById<TextInputEditText>(R.id.edit_number_of_staff_less)
                    var theoretical_output_less=item.findViewById<TextInputEditText>(R.id.edit_theoretical_output_less)

                    val remark=item.findViewById<TextInputEditText>(R.id.edit_remark)

                    mAlertDialog.setPositiveButton("取消") { dialog, id ->
                        dialog.dismiss()
                    }
                    mAlertDialog.setNegativeButton("確定") { dialog, id ->
                        //新增不能為空
                        if( _id.text.toString().trim().isEmpty()                            ||
                            process_number.text.toString().trim().isEmpty()                 ||
                            processing_sequence.text.toString().trim().isEmpty()            ||
                            process_code.text.toString().trim().isEmpty()                   ||
                            item_id.text.toString().trim().isEmpty()                        ||
                            pline_id.text.toString().trim().isEmpty()                       ||
                            standard_working_hours_optimal.text.toString().trim().isEmpty() ||
                            unit_of_timer_optimal.text.toString().trim().isEmpty()          ||
                            number_of_staff_optimal.text.toString().trim().isEmpty()        ||
                            theoretical_output_optimal.text.toString().trim().isEmpty()     ||
                            standard_working_hours_less.text.toString().trim().isEmpty()    ||
                            unit_of_timer_less.text.toString().trim().isEmpty()             ||
                            number_of_staff_less.text.toString().trim().isEmpty()           ||
                            theoretical_output_less.text.toString().trim().isEmpty()        ){
                            Toast.makeText(requireView().context,"Input required",Toast.LENGTH_LONG).show()
                        }
                        else{
                            val addData=MeBody()
                            addData._id=_id.text.toString()
                            addData.process_number=process_number.text.toString()
                            addData.processing_sequence=processing_sequence.text.toString()
                            addData.process_code=process_code.text.toString()
                            addData.item_id=item_id.text.toString()
                            addData.pline_id=pline_id.text.toString()
                            addData.standard_working_hours_optimal=standard_working_hours_optimal.text.toString().toInt()
                            addData.unit_of_timer_optimal=unit_of_timer_optimal.text.toString()
                            addData.number_of_staff_optimal=number_of_staff_optimal.text.toString()
                            addData.theoretical_output_optimal=theoretical_output_optimal.text.toString().toInt()
                            addData.standard_working_hours_less=standard_working_hours_less.text.toString().toInt()
                            addData.unit_of_timer_less=unit_of_timer_less.text.toString()
                            addData.number_of_staff_less=number_of_staff_less.text.toString()
                            addData.theoretical_output_less=theoretical_output_less.text.toString().toInt()

                            addData.remark=remark.text.toString()
                            addData.creator=cookie_data.username
                            addData.create_time=Calendar.getInstance().getTime().toString()
                            addData.editor=cookie_data.username
                            addData.edit_time=Calendar.getInstance().getTime().toString()
                            add_S_Basic("Me","body",addData)
                            when(cookie_data.status){
                                0-> {//成功

                                    MeBody_adapter.addItem(addData)
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
                "工程編制表維護(工作站)"->{
                    val item = LayoutInflater.from(activity).inflate(R.layout.recycler_item_me_workstation_basic_info, null)
                    val mAlertDialog = AlertDialog.Builder(requireView().context)
                    //mAlertDialog.setIcon(R.mipmap.ic_launcher_round) //set alertdialog icon
                    mAlertDialog.setTitle("新增") //set alertdialog title
                    //mAlertDialog.setMessage("確定要登出?") //set alertdialog message
                    mAlertDialog.setView(item)

                    //true false combobbox
                    val combobox_yes_no=resources.getStringArray(R.array.combobox_yes_no)
                    val arrayAdapter_yes_no= ArrayAdapter(requireContext(),R.layout.combobox_item,combobox_yes_no)

                    var workstation_number=item.findViewById<TextInputEditText>(R.id.edit_workstation_number)
                    var product_id=item.findViewById<TextInputEditText>(R.id.edit_product_id)
                    var workstation_code=item.findViewById<TextInputEditText>(R.id.edit_workstation_code)
                    var is_body_condition_ok=item.findViewById<AutoCompleteTextView>(R.id.edit_is_body_condition_ok)
                    is_body_condition_ok.setAdapter(arrayAdapter_yes_no)
                    var is_vision_condition_ok=item.findViewById<AutoCompleteTextView>(R.id.edit_is_vision_condition_ok)
                    is_vision_condition_ok.setAdapter(arrayAdapter_yes_no)
                    var is_technology_ok=item.findViewById<AutoCompleteTextView>(R.id.edit_is_technology_ok)
                    is_technology_ok.setAdapter(arrayAdapter_yes_no)
                    var is_normal=item.findViewById<AutoCompleteTextView>(R.id.edit_is_normal)
                    is_normal.setAdapter(arrayAdapter_yes_no)
                    var job_description=item.findViewById<TextInputEditText>(R.id.edit_job_description)
                    var standard_working_hours_optimal=item.findViewById<TextInputEditText>(R.id.edit_standard_working_hours_optimal)
                    var unit_of_timer_optimal=item.findViewById<TextInputEditText>(R.id.edit_unit_of_timer_optimal)
                    var number_of_staff_optimal=item.findViewById<TextInputEditText>(R.id.edit_number_of_staff_optimal)
                    var theoretical_output=item.findViewById<TextInputEditText>(R.id.edit_theoretical_output)
                    var standard_working_hours_less=item.findViewById<TextInputEditText>(R.id.edit_standard_working_hours_less)
                    var unit_of_timer_less=item.findViewById<TextInputEditText>(R.id.edit_unit_of_timer_less)
                    var number_of_staff_less=item.findViewById<TextInputEditText>(R.id.edit_number_of_staff_less)
                    var theoretical_output_less=item.findViewById<TextInputEditText>(R.id.edit_theoretical_output_less)

                    val remark=item.findViewById<TextInputEditText>(R.id.edit_remark)

                    mAlertDialog.setPositiveButton("取消") { dialog, id ->
                        dialog.dismiss()
                    }
                    mAlertDialog.setNegativeButton("確定") { dialog, id ->
                        //新增不能為空
                        if( workstation_number.text.toString().trim().isEmpty() ||
                            product_id.text.toString().trim().isEmpty()  ||
                            workstation_code.text.toString().trim().isEmpty() ||
                            is_body_condition_ok.text.toString().trim().isEmpty()  ||
                            is_vision_condition_ok.text.toString().trim().isEmpty() ||
                            is_technology_ok.text.toString().trim().isEmpty() ||
                            is_normal.text.toString().trim().isEmpty() ||
                            job_description.text.toString().trim().isEmpty()  ||
                            standard_working_hours_optimal.text.toString().trim().isEmpty() ||
                            unit_of_timer_optimal.text.toString().trim().isEmpty()  ||
                            number_of_staff_optimal.text.toString().trim().isEmpty()||
                            theoretical_output.text.toString().trim().isEmpty() ||
                            standard_working_hours_less.text.toString().trim().isEmpty()  ||
                            unit_of_timer_less.text.toString().trim().isEmpty() ||
                            number_of_staff_less.text.toString().trim().isEmpty()  ||
                            theoretical_output_less.text.toString().trim().isEmpty() ){
                            Toast.makeText(requireView().context,"Input required",Toast.LENGTH_LONG).show()
                        }
                        else{
                            val addData=MeWorkstationBasicInfo()
                            addData.workstation_number=workstation_number.text.toString()
                            addData.product_id=product_id.text.toString()
                            addData.workstation_code=workstation_code.text.toString()
                            addData.is_body_condition_ok=is_body_condition_ok.text.toString().toBoolean()
                            addData.is_vision_condition_ok=is_vision_condition_ok.text.toString().toBoolean()
                            addData.is_technology_ok=is_technology_ok.text.toString().toBoolean()
                            addData.is_normal=is_normal.text.toString().toBoolean()
                            addData.job_description=job_description.text.toString()
                            addData.standard_working_hours_optimal=standard_working_hours_optimal.text.toString().toInt()
                            addData.unit_of_timer_optimal=unit_of_timer_optimal.text.toString()
                            addData.number_of_staff_optimal=number_of_staff_optimal.text.toString()
                            addData.theoretical_output=theoretical_output.text.toString().toInt()
                            addData.standard_working_hours_less=standard_working_hours_less.text.toString().toInt()
                            addData.unit_of_timer_less=unit_of_timer_less.text.toString()
                            addData.number_of_staff_less=number_of_staff_less.text.toString()
                            addData.theoretical_output_less=theoretical_output_less.text.toString().toInt()


                            addData.remark=remark.text.toString()
                            addData.creator=cookie_data.username
                            addData.create_time=Calendar.getInstance().getTime().toString()
                            addData.editor=cookie_data.username
                            addData.edit_time=Calendar.getInstance().getTime().toString()
                            addBasic("MeWorkstationBasicInfo",addData)
                            when(cookie_data.status){
                                0-> {//成功

                                    MeWorkstationBasicInfo_adapter.addItem(addData)
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
                "工程編制表維護(站別-設備)"->{
                    val item = LayoutInflater.from(activity).inflate(R.layout.recycler_item_me_workstation_equipment_basic_info, null)
                    val mAlertDialog = AlertDialog.Builder(requireView().context)
                    //mAlertDialog.setIcon(R.mipmap.ic_launcher_round) //set alertdialog icon
                    mAlertDialog.setTitle("新增") //set alertdialog title
                    //mAlertDialog.setMessage("確定要登出?") //set alertdialog message
                    mAlertDialog.setView(item)


                    var equipment_number=item.findViewById<TextInputEditText>(R.id.edit_equipment_number)
                    var workstation_number=item.findViewById<TextInputEditText>(R.id.edit_workstation_number)
                    var device_id=item.findViewById<TextInputEditText>(R.id.edit_device_id)

                    val remark=item.findViewById<TextInputEditText>(R.id.edit_remark)

                    mAlertDialog.setPositiveButton("取消") { dialog, id ->
                        dialog.dismiss()
                    }
                    mAlertDialog.setNegativeButton("確定") { dialog, id ->
                        //新增不能為空
                        if( equipment_number.text.toString().trim().isEmpty() ||
                            workstation_number.text.toString().trim().isEmpty()  ||
                            device_id.text.toString().trim().isEmpty()  ){
                            Toast.makeText(requireView().context,"Input required",Toast.LENGTH_LONG).show()
                        }
                        else{
                            val addData=MeWorkstationEquipmentBasicInfo()
                            addData.equipment_number=equipment_number.text.toString()
                            addData.workstation_number=workstation_number.text.toString()
                            addData.device_id=device_id.text.toString()



                            addData.remark=remark.text.toString()
                            addData.creator=cookie_data.username
                            addData.create_time=Calendar.getInstance().getTime().toString()
                            addData.editor=cookie_data.username
                            addData.edit_time=Calendar.getInstance().getTime().toString()
                            addBasic("MeWorkstationEquipmentBasicInfo",addData)
                            when(cookie_data.status){
                                0-> {//成功

                                    MeWorkstationEquipmentBasicInfo_adapter.addItem(addData)
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

    private fun showDef(operation:String,view_type:String,view_hide:String) {
        val body = FormBody.Builder()
            .add("username", cookie_data.username)
            .add("operation", operation)
            .add("action","0")
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
    private fun <T>addDef(operation:String,addData:T) {

        when(addData)
        {
            is ProdType->{
                val add = JSONObject()
                add.put("product_type_name",addData.product_type_name)
                add.put("product_type_id",addData.product_type_id)
                add.put("remark",addData.remark)
                add.put("creator",cookie_data.username)
                add.put("editor",cookie_data.username)
                Log.d("GSON", "msg:${add}")
                val body = FormBody.Builder()
                    .add("username", cookie_data.username)
                    .add("operation", operation)
                    .add("action","1")
                    .add("data",add.toString())
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
                        cookie_data.response_data=response.body?.string().toString()
                        Log.d("GSON", "msg:${cookie_data.response_data}")
                    }
                    job.join()
                    val responseInfo = Gson().fromJson(cookie_data.response_data, Response::class.java)
                    cookie_data.status=responseInfo.status
                    cookie_data.msg=responseInfo.msg
                }
            }
            is ContNo->{
                val add = JSONObject()
                add.put("cont_code",addData.cont_code)
                add.put("customer_code",addData.customer_code)
                add.put("age",addData.age)
                add.put("serial_number",addData.serial_number)
                add.put("cont_code_name",addData.cont_code_name)
                add.put("remark",addData.remark)
                add.put("creator",cookie_data.username)
                add.put("editor",cookie_data.username)
                Log.d("GSON", "msg:${add}")
                val body = FormBody.Builder()
                    .add("username", cookie_data.username)
                    .add("operation", operation)
                    .add("action","1")
                    .add("data",add.toString())
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
                        cookie_data.response_data=response.body?.string().toString()
                        Log.d("GSON", "msg:${cookie_data.response_data}")

                    }
                    job.join()
                    val responseInfo = Gson().fromJson(cookie_data.response_data, Response::class.java)
                    cookie_data.status=responseInfo.status
                    cookie_data.msg=responseInfo.msg
                }

            }
            is ContType->{
                val add = JSONObject()
                add.put("cont_type_code",addData.cont_type_code)
                add.put("cont_type",addData.cont_type)
                add.put("order",addData.order)
                add.put("remark",addData.remark)
                add.put("creator",cookie_data.username)
                add.put("editor",cookie_data.username)
                Log.d("GSON", "msg:${add}")
                val body = FormBody.Builder()
                    .add("username", cookie_data.username)
                    .add("operation", operation)
                    .add("action","1")
                    .add("data",add.toString())
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
                        cookie_data.response_data=response.body?.string().toString()
                        Log.d("GSON", "msg:${cookie_data.response_data}")

                    }
                    job.join()
                    val responseInfo = Gson().fromJson(cookie_data.response_data, Response::class.java)
                    cookie_data.status=responseInfo.status
                    cookie_data.msg=responseInfo.msg
                }

            }
            is Port->{
                val add = JSONObject()
                add.put("port_id",addData.port_id)
                add.put("port_name",addData.port_name)
                add.put("remark",addData.remark)
                add.put("creator",cookie_data.username)
                add.put("editor",cookie_data.username)
                Log.d("GSON", "msg:${add}")
                val body = FormBody.Builder()
                    .add("username", cookie_data.username)
                    .add("operation", operation)
                    .add("action","1")
                    .add("data",add.toString())
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

    private fun showBasic(operation:String,view_type:String,view_hide:String) {
        val body = FormBody.Builder()
            .add("card_number", cookie_data.card_number)
            .add("username", cookie_data.username)
            .add("operation", operation)
            .add("action","0")
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
            is PLineBasicInfo->{
                val add = JSONObject()
                add.put("_id",addData._id)
                add.put("name",addData.name)
                add.put("is_selfmade",addData.is_selfmade)
                add.put("is_outsourcing",addData.is_outsourcing)
                add.put("vender_id",addData.vender_id)

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
            is ProductionEquipmentBasicInfo->{
                val add = JSONObject()
                add.put("production_equipment_number",addData.production_equipment_number)
                add.put("full_name",addData.full_name)
                add.put("abbreviation",addData.abbreviation)
                add.put("pline_id",addData.pline_id)
                add.put("asset_number",addData.asset_number)
                add.put("equipment_type",addData.equipment_type)
                add.put("is_production_equipment",addData.is_production_equipment)
                add.put("is_mould",addData.is_mould)
                add.put("is_fixture",addData.is_fixture)
                add.put("is_checking_fixture",addData.is_checking_fixture)

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
            is MeWorkstationBasicInfo->{
                val add = JSONObject()
                add.put("workstation_number",addData.workstation_number)
                add.put("product_id",addData.product_id)
                add.put("workstation_code",addData.workstation_code)
                add.put("is_body_condition_ok",addData.is_body_condition_ok)
                add.put("is_vision_condition_ok",addData.is_vision_condition_ok)
                add.put("is_technology_ok",addData.is_technology_ok)
                add.put("is_normal",addData.is_normal)
                add.put("job_description",addData.job_description)
                add.put("standard_working_hours_optimal",addData.standard_working_hours_optimal)
                add.put("unit_of_timer_optimal",addData.unit_of_timer_optimal)
                add.put("number_of_staff_optimal",addData.number_of_staff_optimal)
                add.put("theoretical_output",addData.theoretical_output)
                add.put("standard_working_hours_less",addData.standard_working_hours_less)
                add.put("unit_of_timer_less",addData.unit_of_timer_less)
                add.put("number_of_staff_less",addData.number_of_staff_less)
                add.put("theoretical_output_less",addData.theoretical_output_less)

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
            is MeWorkstationEquipmentBasicInfo->{
                val add = JSONObject()
                add.put("equipment_number",addData.equipment_number)
                add.put("workstation_number",addData.workstation_number)
                add.put("device_id",addData.device_id)


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

    private fun show_S_Basic(operation:String,target:String,view_type:String,view_hide:String) {
        when(view_type)
        {
            "all"->{
                val body = FormBody.Builder()
                    .add("card_number", cookie_data.card_number)
                    .add("username", cookie_data.username)
                    .add("operation", operation)
                    .add("target", target)
                    .add("action","0")
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
            "condition"->{
                val filter = JSONObject()
                filter.put("_id",selectFilter)
                val body = FormBody.Builder()
                    .add("card_number", cookie_data.card_number)
                    .add("username", cookie_data.username)
                    .add("operation", operation)
                    .add("target", target)
                    .add("action","0")
                    .add("view_type",view_type)
                    .add("view_hide",view_hide)
                    .add("data",filter.toString())
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
    private fun <T>add_S_Basic(operation:String,target:String,addData:T) {

        when(addData)
        {
            is MeHeader->{
                val add = JSONObject()
                add.put("_id",addData._id)
                add.put("product_id",addData.product_id)
                add.put("theoretical_output",addData.theoretical_output)
                add.put("unit_of_timer",addData.unit_of_timer)
                add.put("issue_date",addData.issue_date)
                add.put("item_id",addData.item_id)


                add.put("remark",addData.remark)
                add.put("creator",cookie_data.username)
                add.put("editor",cookie_data.username)
                Log.d("GSON", "msg:${add}")
                val body = FormBody.Builder()
                    .add("card_number", cookie_data.card_number)
                    .add("username", cookie_data.username)
                    .add("operation", operation)
                    .add("target", target)
                    .add("action","1")
                    .add("data",add.toString())
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
                        cookie_data.response_data=response.body?.string().toString()
                        Log.d("GSON", "msg:${cookie_data.response_data}")
                    }
                    job.join()
                    val responseInfo = Gson().fromJson(cookie_data.response_data, Response::class.java)
                    cookie_data.status=responseInfo.status
                    cookie_data.msg=responseInfo.msg
                }
            }
            is MeBody->{
                val add = JSONObject()
                add.put("_id",addData._id)
                add.put("process_number",addData.process_number)
                add.put("processing_sequence",addData.processing_sequence)
                add.put("process_code",addData.process_code)
                add.put("item_id",addData.item_id)
                add.put("pline_id",addData.pline_id)
                add.put("standard_working_hours_optimal",addData.standard_working_hours_optimal)
                add.put("unit_of_timer_optimal",addData.unit_of_timer_optimal)
                add.put("number_of_staff_optimal",addData.number_of_staff_optimal)
                add.put("theoretical_output_optimal",addData.theoretical_output_optimal)
                add.put("standard_working_hours_less",addData.standard_working_hours_less)
                add.put("unit_of_timer_less",addData.unit_of_timer_less)
                add.put("number_of_staff_less",addData.number_of_staff_less)
                add.put("theoretical_output_less",addData.theoretical_output_less)


                add.put("remark",addData.remark)
                add.put("creator",cookie_data.username)
                add.put("editor",cookie_data.username)
                Log.d("GSON", "msg:${add}")
                val body = FormBody.Builder()
                    .add("card_number", cookie_data.card_number)
                    .add("username", cookie_data.username)
                    .add("operation", operation)
                    .add("target", target)
                    .add("action","1")
                    .add("data",add.toString())
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


    private fun <T>show_S_Basic_combobox(operation:String,target:String,view_type:String,view_hide:String,fmt:T) {
        when(fmt)
        {
            is MeHeader->{
                val body = FormBody.Builder()
                    .add("card_number", cookie_data.card_number)
                    .add("username", cookie_data.username)
                    .add("operation", operation)
                    .add("target", target)
                    .add("action","0")
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
                        var json=Gson().fromJson(responseinfo, ShowMeHeader::class.java)
                        when(json.status){
                            0->{
                                cookie_data.status=json.status
                                var data: java.util.ArrayList<MeHeader> =json.data
                                comboboxData.removeAll(comboboxData)
                                for(i in 0 until data.size){
                                    comboboxData.add(data[i]._id)
                                }
                                //Log.d("GSON", "msg:${comboboxData}")
                            }
                            1->{
                                var fail=Gson().fromJson(responseinfo, Response::class.java)
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
}




