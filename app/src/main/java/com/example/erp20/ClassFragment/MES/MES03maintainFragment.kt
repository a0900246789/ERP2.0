package com.example.erp20.ClassFragment.MES

import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.erp20.Model.*
import com.example.erp20.PopUpWindow.QrCodeFragment
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

class MES03maintainFragment : Fragment() {
    private lateinit var MeWorkstationBasicInfo_adapter:RecyclerItemMeWorkstationBasicInfoAdapter
    private lateinit var MeWorkstationEquipmentBasicInfo_adapter:RecyclerItemMeWorkstationEquipmentBasicInfoAdapter
    private lateinit var MeHeader_adapter:RecyclerItemMeHeaderAdapter
    private lateinit var MeBody_adapter:RecyclerItemMeBodyAdapter
    private lateinit var staffBasicInfo_adapter:RecyclerItemstaffBasicInfoAdapter
    var comboboxData: MutableList<String> = mutableListOf<String>()
    lateinit var selectFilter:String
    lateinit var recyclerView:RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_mes03maintain, container, false)
    }
    override fun onResume() {
        super.onResume()
        val theTextView = getView()?.findViewById<TextView>(R.id._text)

        recyclerView= view?.findViewById<RecyclerView>(R.id.recyclerView)!!
        cookie_data.recyclerView=recyclerView

        //combobox選單內容
        val autoCompleteTextView=getView()?.findViewById<AutoCompleteTextView>(R.id.autoCompleteText)
        val combobox=resources.getStringArray(R.array.comboboxMES03maintain)
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
                            recyclerView?.layoutManager= LinearLayoutManager(context)//設定Linear格式
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
                "員工基本資料維護"->{
                    show_staff_Basic("all","False")//type= auth_list or all
                    when(cookie_data.status)
                    {
                        0->{
                            Toast.makeText(activity, "資料載入", Toast.LENGTH_SHORT).show()
                            recyclerView.layoutManager=LinearLayoutManager(context)//設定Linear格式
                            staffBasicInfo_adapter= RecyclerItemstaffBasicInfoAdapter()
                            recyclerView.adapter=staffBasicInfo_adapter//找對應itemAdapter
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
                    _id.inputType= InputType.TYPE_NULL
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
                            Toast.makeText(requireView().context,"Input required", Toast.LENGTH_LONG).show()
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
                            addData.create_time= Calendar.getInstance().getTime().toString()
                            addData.editor=cookie_data.username
                            addData.edit_time= Calendar.getInstance().getTime().toString()
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
                "員工基本資料維護"->{
                    val item = LayoutInflater.from(activity).inflate(R.layout.recycler_item_staff_basic_info, null)
                    cookie_data.Add_View=item
                    val mAlertDialog = AlertDialog.Builder(requireView().context)
                    //mAlertDialog.setIcon(R.mipmap.ic_launcher_round) //set alertdialog icon
                    mAlertDialog.setTitle("新增") //set alertdialog title
                    //mAlertDialog.setMessage("確定要登出?") //set alertdialog message
                    mAlertDialog.setView(item)

                    //true false combobbox
                    val combobox_yes_no=resources.getStringArray(R.array.combobox_yes_no)
                    val arrayAdapter_yes_no= ArrayAdapter(requireContext(),R.layout.combobox_item,combobox_yes_no)


                    var card_number=item.findViewById<TextInputEditText>(R.id.edit_card_number)
                    var password=item.findViewById<TextInputEditText>(R.id.edit_password)
                    var name=item.findViewById<TextInputEditText>(R.id.edit_name)
                    var dept=item.findViewById<TextInputEditText>(R.id.edit_dept)
                    var position=item.findViewById<TextInputEditText>(R.id.edit_position)
                    var is_work=item.findViewById<AutoCompleteTextView>(R.id.edit_is_work)
                    is_work.setAdapter(arrayAdapter_yes_no)
                    var date_of_employment=item.findViewById<TextInputEditText>(R.id.edit_date_of_employment)
                    var date_of_resignation=item.findViewById<TextInputEditText>(R.id.edit_date_of_resignation)
                    var skill_code=item.findViewById<TextInputEditText>(R.id.edit_skill_code)
                    var skill_rank=item.findViewById<TextInputEditText>(R.id.edit_skill_rank)
                    var qr_code=item.findViewById<TextInputEditText>(R.id.edit_qr_code)
                    qr_code.setOnClickListener {
                        var dialogF= QrCodeFragment()
                        dialogF.show( ( item.context as AppCompatActivity).supportFragmentManager ,"Qrcode")
                        cookie_data.currentAdapter="add"
                    }

                    val remark=item.findViewById<TextInputEditText>(R.id.edit_remark)

                    mAlertDialog.setPositiveButton("取消") { dialog, id ->
                        dialog.dismiss()
                    }
                    mAlertDialog.setNegativeButton("確定") { dialog, id ->
                        //新增不能為空
                        if( card_number.text.toString().trim().isEmpty() ||
                            password.text.toString().trim().isEmpty()  ||
                            name.text.toString().trim().isEmpty() ||
                            dept.text.toString().trim().isEmpty()  ||
                            position.text.toString().trim().isEmpty() ||
                            is_work.text.toString().trim().isEmpty() ||
                            date_of_employment.text.toString().trim().isEmpty()  ||
                            date_of_resignation.text.toString().trim().isEmpty() ||
                            skill_code.text.toString().trim().isEmpty()  ||
                            skill_rank.text.toString().trim().isEmpty() ||
                            qr_code.text.toString().trim().isEmpty()    ){
                            Toast.makeText(requireView().context,"Input required",Toast.LENGTH_LONG).show()
                        }
                        else{
                            val addData=staffBasicInfo()
                            addData.card_number=card_number.text.toString()
                            addData.password=password.text.toString()
                            addData.name=name.text.toString()
                            addData.dept=dept.text.toString()
                            addData.position=position.text.toString()
                            addData.is_work=is_work.text.toString().toBoolean()
                            addData.date_of_employment=date_of_employment.text.toString()
                            addData.date_of_resignation=date_of_resignation.text.toString()
                            addData.skill_code=skill_code.text.toString()
                            addData.skill_rank=skill_rank.text.toString()
                            addData.qr_code=qr_code.text.toString()


                            addData.remark=remark.text.toString()
                            addData.creator=cookie_data.username
                            addData.create_time=Calendar.getInstance().getTime().toString()
                            addData.editor=cookie_data.username
                            addData.edit_time=Calendar.getInstance().getTime().toString()
                            add_staff_Basic(addData)
                            when(cookie_data.status){
                                0-> {//成功

                                    staffBasicInfo_adapter.addItem(addData)
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

    //定義檔
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
            .url("http://140.125.46.125:8000/def_management")
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
            is ProdType ->{
                val add = JSONObject()
                add.put("product_type_name",addData.product_type_name)
                add.put("product_type_id",addData.product_type_id)
                add.put("remark",addData.remark)
                add.put("creator", cookie_data.username)
                add.put("editor", cookie_data.username)
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
                    .url("http://140.125.46.125:8000/def_management")
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
            is ContNo ->{
                val add = JSONObject()
                add.put("cont_code",addData.cont_code)
                add.put("customer_code",addData.customer_code)
                add.put("age",addData.age)
                add.put("serial_number",addData.serial_number)
                add.put("cont_code_name",addData.cont_code_name)
                add.put("remark",addData.remark)
                add.put("creator", cookie_data.username)
                add.put("editor", cookie_data.username)
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
                    .url("http://140.125.46.125:8000/def_management")
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
            is ContType ->{
                val add = JSONObject()
                add.put("cont_type_code",addData.cont_type_code)
                add.put("cont_type",addData.cont_type)
                add.put("order",addData.order)
                add.put("remark",addData.remark)
                add.put("creator", cookie_data.username)
                add.put("editor", cookie_data.username)
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
                    .url("http://140.125.46.125:8000/def_management")
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
            is Port ->{
                val add = JSONObject()
                add.put("port_id",addData.port_id)
                add.put("port_name",addData.port_name)
                add.put("remark",addData.remark)
                add.put("creator", cookie_data.username)
                add.put("editor", cookie_data.username)
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
                    .url("http://140.125.46.125:8000/def_management")
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

    //基本資料
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
            .url("http://140.125.46.125:8000/basic_management")
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
            is MeWorkstationBasicInfo ->{
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
                add.put("creator", cookie_data.username)
                add.put("editor", cookie_data.username)
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
                    .url("http://140.125.46.125:8000/basic_management")
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
            is MeWorkstationEquipmentBasicInfo ->{
                val add = JSONObject()
                add.put("equipment_number",addData.equipment_number)
                add.put("workstation_number",addData.workstation_number)
                add.put("device_id",addData.device_id)


                add.put("remark",addData.remark)
                add.put("creator", cookie_data.username)
                add.put("editor", cookie_data.username)
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
                    .url("http://140.125.46.125:8000/basic_management")
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

    //特殊基本資料
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
                    .url("http://140.125.46.125:8000/special_basic_management")
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
                    .url("http://140.125.46.125:8000/special_basic_management")
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
                    .url("http://140.125.46.125:8000/special_basic_management")
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
                    .url("http://140.125.46.125:8000/special_basic_management")
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

    //員工基本資料
    private fun show_staff_Basic(view_type:String,view_hide:String) {
        val body = FormBody.Builder()
            .add("username", cookie_data.username)
            .add("action","0")
            .add("view_type",view_type)
            .add("view_hide",view_hide)
            .add("csrfmiddlewaretoken", cookie_data.tokenValue)
            .add("login_flag", cookie_data.loginflag)
            .build()
        val request = Request.Builder()
            .url("http://140.125.46.125:8000/staff_management")
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

           // Log.d("GSON", "msg:${responseInfo}")
        }

    }
    private fun <T>add_staff_Basic(addData:T) {

        when(addData)
        {
            is staffBasicInfo ->{
                val add = JSONObject()
                add.put("card_number",addData.card_number)
                add.put("password",addData.password)
                add.put("name",addData.name)
                add.put("dept",addData.dept)
                add.put("position",addData.position)
                add.put("is_work",addData.is_work)
                add.put("date_of_employment",addData.date_of_employment)
                add.put("date_of_resignation",addData.date_of_resignation)
                add.put("skill_code",addData.skill_code)
                add.put("skill_rank",addData.skill_rank)
                add.put("qr_code",addData.qr_code)

                add.put("remark",addData.remark)
                add.put("creator", cookie_data.username)
                add.put("editor", cookie_data.username)
                Log.d("GSON", "msg:${add}")
                val body = FormBody.Builder()
                    .add("username", cookie_data.username)
                    .add("action","1")
                    .add("data",add.toString())
                    .add("csrfmiddlewaretoken", cookie_data.tokenValue)
                    .add("login_flag", cookie_data.loginflag)
                    .build()
                val request = Request.Builder()
                    .url("http://140.125.46.125:8000/staff_management")
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

    //顯示特殊基本資料combobox
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
                    .url("http://140.125.46.125:8000/special_basic_management")
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
                                var data: ArrayList<MeHeader> =json.data
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