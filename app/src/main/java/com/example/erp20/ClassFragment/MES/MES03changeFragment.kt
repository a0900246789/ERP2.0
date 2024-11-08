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
import com.example.erp20.app02.RecyclerItemMealOrderListHeaderAdapter
import com.example.erp20.app09.RecyclerItemStockTransOrderBodyAdapter
import com.example.erp20.app09.RecyclerItemStockTransOrderHeaderAdapter
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

class MES03changeFragment : Fragment() {
    private lateinit var ProductControlOrderHeader_adapter:RecyclerItemProductControlOrderHeaderAdapter
    private lateinit var ProductControlOrderBody_A_adapter:RecyclerItemProductControlOrderBodyAAdapter
    private lateinit var ProductControlOrderBody_B_adapter:RecyclerItemProductControlOrderBodyBAdapter
    private lateinit var ProductControlOrderBody_C_adapter:RecyclerItemProductControlOrderBodyCAdapter
    private lateinit var ProductionControlListRequisition_adapter:RecyclerItemProductionControlListRequisitionAdapter
    private lateinit var MealOrderListHeader_adapter: RecyclerItemMealOrderListHeaderAdapter
    private lateinit var MealOrderListBody_adapter: RecyclerItemMealOrderListBodyAdapter
    private lateinit var StockTransOrderHeader_adapter: RecyclerItemStockTransOrderHeaderAdapter
    private lateinit var StockTransOrderBody_adapter: RecyclerItemStockTransOrderBodyAdapter
    private lateinit var EquipmentMaintenanceRecord_adapter:RecyclerItemEquipmentMaintenanceRecordAdapter
    private lateinit var staffBasicInfo_adapter:RecyclerItemstaffBasicInfoAdapter
    private lateinit var DMCILHeader_adapter:RecyclerItemDMCILHeaderAdapter
    private lateinit var DMCILBody_adapter:RecyclerItemDMCILBodyAdapter
    var comboboxData: MutableList<String> = mutableListOf<String>()
    lateinit var selectFilter:String
    lateinit var recyclerView:RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_mes03change, container, false)
    }


    override fun onResume() {
        super.onResume()
        val theTextView = getView()?.findViewById<TextView>(R.id._text)

        recyclerView= view?.findViewById<RecyclerView>(R.id.recyclerView)!!
        cookie_data.recyclerView=recyclerView

        //combobox選單內容
        val autoCompleteTextView=getView()?.findViewById<AutoCompleteTextView>(R.id.autoCompleteText)
        val combobox=resources.getStringArray(R.array.comboboxMES03change)
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


            when(autoCompleteTextView?.text.toString()){
                "員工每日訂餐單(單頭)"->{
                    show_header_body("MealOrderListHeader","all","False")//type=combobox or all
                    show_relative_combobox("Department","all","False", Department())
                    when(cookie_data.status)
                    {
                        0->{
                            Toast.makeText(activity, "資料載入", Toast.LENGTH_SHORT).show()
                            recyclerView?.layoutManager= LinearLayoutManager(context)//設定Linear格式
                            MealOrderListHeader_adapter= RecyclerItemMealOrderListHeaderAdapter()
                            recyclerView?.adapter=MealOrderListHeader_adapter//找對應itemAdapter
                            //addbtn?.isEnabled=true
                        }
                        1->{
                            Toast.makeText(activity, cookie_data.msg, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                "員工每日訂餐單(單身)"->{
                    show_Header_combobox("MealOrderListHeader","all","False", MealOrderListHeader())//type=combobox or all
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
                                show_header_body("MealOrderListBody","condition","False")//type=all or condition
                                show_relative_combobox("staffBasicInfo","all","False", staffBasicInfo())
                                when(cookie_data.status)
                                {
                                    0->{
                                        Toast.makeText(activity, "資料載入", Toast.LENGTH_SHORT).show()
                                        recyclerView?.layoutManager=
                                            LinearLayoutManager(context)//設定Linear格式
                                        MealOrderListBody_adapter= RecyclerItemMealOrderListBodyAdapter()
                                        recyclerView?.adapter=MealOrderListBody_adapter//找對應itemAdapter
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
                "生產管制單(單頭)"->{
                    show_header_body("ProductControlOrderHeader","all","False")//type=combobox or all
                    show_relative_combobox("ItemBasicInfo","all","False", ItemBasicInfo())
                    when(cookie_data.status)
                    {
                        0->{
                            Toast.makeText(activity, "資料載入", Toast.LENGTH_SHORT).show()
                            recyclerView?.layoutManager= LinearLayoutManager(context)//設定Linear格式
                            ProductControlOrderHeader_adapter= RecyclerItemProductControlOrderHeaderAdapter()
                            recyclerView?.adapter=ProductControlOrderHeader_adapter//找對應itemAdapter
                            //addbtn?.isEnabled=true
                        }
                        1->{
                            Toast.makeText(activity, cookie_data.msg, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                "生產管制單(單身)"->{
                    show_Header_combobox("ProductControlOrderHeader","all","False", ProductControlOrderHeader())//type=combobox or all
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
                                show_header_body("ProductControlOrderBody_A","condition","False")//type=all or condition
                                show_relative_combobox("MeHeader","all","False", MeHeader())
                                show_relative_combobox("ItemBasicInfo","all","False", ItemBasicInfo())
                                show_relative_combobox("PLineBasicInfo","all","False", PLineBasicInfo())
                                when(cookie_data.status)
                                {
                                    0->{
                                        Toast.makeText(activity, "資料載入", Toast.LENGTH_SHORT).show()
                                        recyclerView?.layoutManager=
                                            LinearLayoutManager(context)//設定Linear格式
                                        ProductControlOrderBody_A_adapter= RecyclerItemProductControlOrderBodyAAdapter()
                                        recyclerView?.adapter=ProductControlOrderBody_A_adapter//找對應itemAdapter
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
                "生產管制單(報工)"->{
                    show_Header_combobox("ProductControlOrderBody_A","all","False", ProductControlOrderBody_A())//type=combobox or all
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
                                show_header_body("ProductControlOrderBody_B","condition","False")//type=all or condition
                                show_relative_combobox("PLineBasicInfo","all","False", PLineBasicInfo())
                                show_relative_combobox("MeWorkstationBasicInfo","all","False", MeWorkstationBasicInfo())
                                show_relative_combobox("staffBasicInfo","all","False", staffBasicInfo())
                                when(cookie_data.status)
                                {
                                    0->{
                                        Toast.makeText(activity, "資料載入", Toast.LENGTH_SHORT).show()
                                        recyclerView.layoutManager=
                                            LinearLayoutManager(context)//設定Linear格式
                                        ProductControlOrderBody_B_adapter= RecyclerItemProductControlOrderBodyBAdapter()
                                        recyclerView.adapter=ProductControlOrderBody_B_adapter//找對應itemAdapter
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
                "生產管制單(派工)"->{
                    show_Header_combobox("ProductControlOrderBody_A","all","False", ProductControlOrderBody_A())//type=combobox or all
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
                                show_header_body("ProductControlOrderBody_C","condition","False")//type=all or condition
                                show_relative_combobox("PLineBasicInfo","all","False", PLineBasicInfo())
                                show_relative_combobox("MeWorkstationBasicInfo","all","False", MeWorkstationBasicInfo())
                                show_relative_combobox("staffBasicInfo","all","False", staffBasicInfo())
                                when(cookie_data.status)
                                {
                                    0->{
                                        Toast.makeText(activity, "資料載入", Toast.LENGTH_SHORT).show()
                                        recyclerView.layoutManager=
                                            LinearLayoutManager(context)//設定Linear格式
                                        ProductControlOrderBody_C_adapter= RecyclerItemProductControlOrderBodyCAdapter()
                                        recyclerView.adapter=ProductControlOrderBody_C_adapter//找對應itemAdapter
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
                "生產管制單(領料單)"->{
                    show_Header_combobox("ProductControlOrderBody_A","all","False", ProductControlOrderBody_A())//type=combobox or all
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
                                show_header_body("ProductionControlListRequisition","condition","False")//type=all or condition
                                show_relative_combobox("ItemBasicInfo","all","False", ItemBasicInfo())
                                show_relative_combobox("staffBasicInfo","all","False", staffBasicInfo())
                                when(cookie_data.status)
                                {
                                    0->{
                                        Toast.makeText(activity, "資料載入", Toast.LENGTH_SHORT).show()
                                        recyclerView.layoutManager=
                                            LinearLayoutManager(context)//設定Linear格式
                                        ProductionControlListRequisition_adapter= RecyclerItemProductionControlListRequisitionAdapter()
                                        recyclerView.adapter=ProductionControlListRequisition_adapter//找對應itemAdapter
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
                "庫存異動單(單頭)"->{
                    show_header_body("StockTransOrderHeader","all","False")//type=combobox or all
                    show_relative_combobox("Department","all","False", Department())
                    show_relative_combobox("InvChangeTypeM","all","False", InvChangeTypeM())
                    show_relative_combobox("InvChangeTypeS","all","False", InvChangeTypeS())
                    show_relative_combobox("ProductControlOrderHeader","all","False", ProductControlOrderHeader())
                    when(cookie_data.status)
                    {
                        0->{
                            Toast.makeText(activity, "資料載入", Toast.LENGTH_SHORT).show()
                            recyclerView.layoutManager= LinearLayoutManager(context)//設定Linear格式
                            StockTransOrderHeader_adapter= RecyclerItemStockTransOrderHeaderAdapter()
                            recyclerView.adapter=StockTransOrderHeader_adapter//找對應itemAdapter
                            addbtn?.isEnabled=true
                        }
                        1->{
                            Toast.makeText(activity, cookie_data.msg, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                "庫存異動單(單身)"->{
                    show_Header_combobox("StockTransOrderHeader","all","False", StockTransOrderHeader())//type=combobox or all
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
                                show_header_body("StockTransOrderBody","condition","False")//type=all or condition
                                show_relative_combobox("ItemBasicInfo","all","False", ItemBasicInfo())
                                show_relative_combobox("InvChangeTypeM","all","False", InvChangeTypeM())
                                show_relative_combobox("InvChangeTypeS","all","False", InvChangeTypeS())
                                show_relative_combobox("StoreArea","all","False", StoreArea())
                                show_relative_combobox("StoreLocal","all","False", StoreLocal())
                                when(cookie_data.status)
                                {
                                    0->{
                                        Toast.makeText(activity, "資料載入", Toast.LENGTH_SHORT).show()
                                        recyclerView.layoutManager=
                                            LinearLayoutManager(context)//設定Linear格式
                                        StockTransOrderBody_adapter= RecyclerItemStockTransOrderBodyAdapter()
                                        recyclerView.adapter=StockTransOrderBody_adapter//找對應itemAdapter
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
                "設備養護記錄"->{
                    show_header_body("EquipmentMaintenanceRecord","all","False")//type=combobox or all
                    show_relative_combobox("MeWorkstationEquipmentBasicInfo","all","False", MeWorkstationEquipmentBasicInfo())
                    show_relative_combobox("VenderBasicInfo","all","False", VenderBasicInfo())
                    show_relative_combobox("EquipmentMaintenanceType","all","False", EquipmentMaintenanceType())
                    when(cookie_data.status)
                    {
                        0->{
                            Toast.makeText(activity, "資料載入", Toast.LENGTH_SHORT).show()
                            recyclerView.layoutManager= LinearLayoutManager(context)//設定Linear格式
                            EquipmentMaintenanceRecord_adapter= RecyclerItemEquipmentMaintenanceRecordAdapter()
                            recyclerView.adapter=EquipmentMaintenanceRecord_adapter//找對應itemAdapter
                            addbtn?.isEnabled=true
                        }
                        1->{
                            Toast.makeText(activity, cookie_data.msg, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                "文管中心資訊庫(單頭)"->{
                    show_header_body("DMCILHeader","all","False")//type=combobox or all
                    show_relative_combobox("Department","all","False", Department())
                    show_relative_combobox("ItemBasicInfo","all","False", ItemBasicInfo())
                    when(cookie_data.status)
                    {
                        0->{
                            Toast.makeText(activity, "資料載入", Toast.LENGTH_SHORT).show()
                            recyclerView?.layoutManager= LinearLayoutManager(context)//設定Linear格式
                            DMCILHeader_adapter= RecyclerItemDMCILHeaderAdapter()
                            recyclerView?.adapter=DMCILHeader_adapter//找對應itemAdapter
                            addbtn?.isEnabled=true
                        }
                        1->{
                            Toast.makeText(activity, cookie_data.msg, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                "文管中心資訊庫(單身)"->{
                    show_Header_combobox("DMCILHeader","all","False", DMCILHeader())//type=combobox or all
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
                                show_header_body("DMCILBody","condition","False")//type=all or condition
                                when(cookie_data.status)
                                {
                                    0->{
                                        Toast.makeText(activity, "資料載入", Toast.LENGTH_SHORT).show()
                                        recyclerView?.layoutManager=
                                            LinearLayoutManager(context)//設定Linear格式
                                        DMCILBody_adapter= RecyclerItemDMCILBodyAdapter()
                                        recyclerView?.adapter=DMCILBody_adapter//找對應itemAdapter
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
            }
        }
        //新增按鈕
        addbtn?.setOnClickListener {
            when(autoCompleteTextView?.text.toString()){
                /*"員工每日訂餐單(單身)"->{
                    val item = LayoutInflater.from(activity).inflate(R.layout.recycler_item_meal_order_list_body, null)
                    val mAlertDialog = AlertDialog.Builder(requireView().context)
                    //mAlertDialog.setIcon(R.mipmap.ic_launcher_round) //set alertdialog icon
                    mAlertDialog.setTitle("新增") //set alertdialog title
                    //mAlertDialog.setMessage("確定要登出?") //set alertdialog message
                    mAlertDialog.setView(item)

                    val combobox=item.resources.getStringArray(R.array.combobox_yes_no)
                    val arrayAdapter= ArrayAdapter(item.context,R.layout.combobox_item,combobox)
                    val arrayAdapter01= ArrayAdapter(item.context,R.layout.combobox_item,cookie_data.card_number_ComboboxData)

                    var header_id=item.findViewById<TextInputEditText>(R.id.edit_header_id)
                    header_id.setText(selectFilter)
                    header_id.inputType= InputType.TYPE_NULL
                    var order_number=item.findViewById<TextInputEditText>(R.id.edit_order_number)
                    order_number.inputType= InputType.TYPE_NULL
                    var card_number=item.findViewById<AutoCompleteTextView>(R.id.edit_card_number)
                    card_number.setAdapter(arrayAdapter01)
                    var is_support=item.findViewById<AutoCompleteTextView>(R.id.edit_is_support)
                    is_support.setAdapter(arrayAdapter)
                    is_support.isClickable=true
                    var support_hours=item.findViewById<TextInputEditText>(R.id.edit_support_hours)
                    var is_morning_leave=item.findViewById<AutoCompleteTextView>(R.id.edit_is_morning_leave)
                    is_morning_leave.setAdapter(arrayAdapter)
                    is_morning_leave.isClickable=true
                    var is_lunch_leave=item.findViewById<AutoCompleteTextView>(R.id.edit_is_lunch_leave)
                    is_lunch_leave.setAdapter(arrayAdapter)
                    is_lunch_leave.isClickable=true
                    var is_all_day_leave=item.findViewById<AutoCompleteTextView>(R.id.edit_is_all_day_leave)
                    is_all_day_leave.setAdapter(arrayAdapter)
                    is_all_day_leave.isClickable=true
                    var leave_hours=item.findViewById<TextInputEditText>(R.id.edit_leave_hours)
                    var is_l_meat_meals=item.findViewById<AutoCompleteTextView>(R.id.edit_is_l_meat_meals)
                    is_l_meat_meals.setAdapter(arrayAdapter)
                    is_l_meat_meals.isClickable=true
                    var is_l_vegetarian_meals=item.findViewById<AutoCompleteTextView>(R.id.edit_is_l_vegetarian_meals)
                    is_l_vegetarian_meals.setAdapter(arrayAdapter)
                    is_l_vegetarian_meals.isClickable=true
                    var is_l_indonesia_meals=item.findViewById<AutoCompleteTextView>(R.id.edit_is_l_indonesia_meals)
                    is_l_indonesia_meals.setAdapter(arrayAdapter)
                    is_l_indonesia_meals.isClickable=true
                    var is_l_num_of_selfcare=item.findViewById<AutoCompleteTextView>(R.id.edit_is_l_num_of_selfcare)
                    is_l_num_of_selfcare.setAdapter(arrayAdapter)
                    is_l_num_of_selfcare.isClickable=true
                    var is_overtime=item.findViewById<AutoCompleteTextView>(R.id.edit_is_overtime)
                    is_overtime.setAdapter(arrayAdapter)
                    is_overtime.isClickable=true
                    var overtime_hours=item.findViewById<TextInputEditText>(R.id.edit_overtime_hours)
                    var is_d_meat_meals=item.findViewById<AutoCompleteTextView>(R.id.edit_is_d_meat_meals)
                    is_d_meat_meals.setAdapter(arrayAdapter)
                    is_d_meat_meals.isClickable=true
                    var is_d_vegetarian_meals=item.findViewById<AutoCompleteTextView>(R.id.edit_is_d_vegetarian_meals)
                    is_d_vegetarian_meals.setAdapter(arrayAdapter)
                    is_d_vegetarian_meals.isClickable=true
                    var is_d_indonesia_meals=item.findViewById<AutoCompleteTextView>(R.id.edit_is_d_indonesia_meals)
                    is_d_indonesia_meals.setAdapter(arrayAdapter)
                    is_d_indonesia_meals.isClickable=true
                    var is_d_num_of_selfcare=item.findViewById<AutoCompleteTextView>(R.id.edit_is_d_num_of_selfcare)
                    is_d_num_of_selfcare.setAdapter(arrayAdapter)
                    is_d_num_of_selfcare.isClickable=true



                    val remark=item.findViewById<TextInputEditText>(R.id.edit_remark)

                    mAlertDialog.setPositiveButton("取消") { dialog, id ->
                        dialog.dismiss()
                    }
                    mAlertDialog.setNegativeButton("確定") { dialog, id ->
                        //新增不能為空
                        if( card_number.text.toString().trim().isEmpty() ||
                            support_hours.text.toString().trim().isEmpty() ||
                            leave_hours.text.toString().trim().isEmpty() ||
                            overtime_hours.text.toString().trim().isEmpty() ){
                            Toast.makeText(requireView().context,"Input required", Toast.LENGTH_LONG).show()
                        }
                        else{
                            val addData= MealOrderListBody()
                            addData.header_id=header_id.text.toString()
                            addData.order_number=order_number.text.toString()
                            addData.card_number=card_number.text.toString()
                            addData.is_support=is_support.text.toString().toBoolean()
                            addData.support_hours=support_hours.text.toString().toDouble()
                            addData.is_morning_leave=is_morning_leave.text.toString().toBoolean()
                            addData.is_lunch_leave=is_lunch_leave.text.toString().toBoolean()
                            addData.is_all_day_leave=is_all_day_leave.text.toString().toBoolean()
                            addData.leave_hours=leave_hours.text.toString().toDouble()
                            addData.is_l_meat_meals=is_l_meat_meals.text.toString().toBoolean()
                            addData.is_l_vegetarian_meals=is_l_vegetarian_meals.text.toString().toBoolean()
                            addData.is_l_indonesia_meals=is_l_indonesia_meals.text.toString().toBoolean()
                            addData.is_l_num_of_selfcare=is_l_num_of_selfcare.text.toString().toBoolean()
                            addData.is_overtime=is_overtime.text.toString().toBoolean()
                            addData.overtime_hours=overtime_hours.text.toString().toDouble()
                            addData.is_d_meat_meals=is_d_meat_meals.text.toString().toBoolean()
                            addData.is_d_vegetarian_meals=is_d_vegetarian_meals.text.toString().toBoolean()
                            addData.is_d_indonesia_meals=is_d_indonesia_meals.text.toString().toBoolean()
                            addData.is_d_num_of_selfcare=is_d_num_of_selfcare.text.toString().toBoolean()



                            addData.remark=remark.text.toString()
                            addData.creator= cookie_data.username
                            addData.create_time= Calendar.getInstance().getTime().toString()
                            addData.editor= cookie_data.username
                            addData.edit_time= Calendar.getInstance().getTime().toString()
                            add_header_body("MealOrderListBody",addData)
                            when(cookie_data.status){
                                0-> {//成功

                                    MealOrderListBody_adapter.addItem(addData)
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
                "生產管制單(單身)"->{
                    val item = LayoutInflater.from(activity).inflate(R.layout.recycler_item_product_control_order_body_a, null)
                    val mAlertDialog = AlertDialog.Builder(requireView().context)
                    //mAlertDialog.setIcon(R.mipmap.ic_launcher_round) //set alertdialog icon
                    mAlertDialog.setTitle("新增") //set alertdialog title
                    //mAlertDialog.setMessage("確定要登出?") //set alertdialog message
                    mAlertDialog.setView(item)

                    val combobox=item.resources.getStringArray(R.array.combobox_yes_no)
                    val arrayAdapter01= ArrayAdapter(item.context,R.layout.combobox_item,cookie_data.MeHeader_id_ComboboxData)
                    val arrayAdapter02= ArrayAdapter(item.context,R.layout.combobox_item,cookie_data.semi_finished_product_number_ComboboxData)
                    val arrayAdapter03= ArrayAdapter(item.context,R.layout.combobox_item,cookie_data.pline_name_ComboboxData)

                    var header_id=item.findViewById<TextInputEditText>(R.id.edit_header_id)
                    header_id.setText(selectFilter)
                    header_id.inputType= InputType.TYPE_NULL
                    var prod_ctrl_order_number=item.findViewById<TextInputEditText>(R.id.edit_prod_ctrl_order_number)
                    prod_ctrl_order_number.inputType= InputType.TYPE_NULL
                    var me_code=item.findViewById<AutoCompleteTextView>(R.id.edit_me_code)
                    me_code.setAdapter(arrayAdapter01)
                    var semi_finished_prod_number=item.findViewById<AutoCompleteTextView>(R.id.edit_semi_finished_prod_number)
                    semi_finished_prod_number.setAdapter(arrayAdapter02)
                    var pline_id=item.findViewById<AutoCompleteTextView>(R.id.edit_pline_id)
                    pline_id.setAdapter(arrayAdapter03)
                    var latest_inspection_day=item.findViewById<TextInputEditText>(R.id.edit_latest_inspection_day)
                    var is_re_make=item.findViewById<AutoCompleteTextView>(R.id.edit_is_re_make)
                    is_re_make.setAdapter(arrayAdapter)
                    is_re_make.isClickable=true
                    var qc_date=item.findViewById<TextInputEditText>(R.id.edit_qc_date)
                    var qc_number=item.findViewById<TextInputEditText>(R.id.edit_qc_number)
                    var unit_of_measurement=item.findViewById<TextInputEditText>(R.id.edit_unit_of_measurement)
                    var est_start_date=item.findViewById<TextInputEditText>(R.id.edit_est_start_date)
                    var est_complete_date=item.findViewById<TextInputEditText>(R.id.edit_est_complete_date)
                    var est_output=item.findViewById<TextInputEditText>(R.id.edit_est_output)
                    var unit_of_timer=item.findViewById<TextInputEditText>(R.id.edit_unit_of_timer)
                    var start_date=item.findViewById<TextInputEditText>(R.id.edit_start_date)
                    var complete_date=item.findViewById<TextInputEditText>(R.id.edit_complete_date)
                    var actual_output=item.findViewById<TextInputEditText>(R.id.edit_actual_output)
                    var is_request_support=item.findViewById<AutoCompleteTextView>(R.id.edit_is_request_support)
                    is_request_support.setAdapter(arrayAdapter)
                    is_request_support.isClickable=true
                    var number_of_support=item.findViewById<TextInputEditText>(R.id.edit_number_of_support)
                    var number_of_supported=item.findViewById<TextInputEditText>(R.id.edit_number_of_supported)
                    var request_support_time=item.findViewById<TextInputEditText>(R.id.edit_request_support_time)
                    var is_urgent=item.findViewById<AutoCompleteTextView>(R.id.edit_is_urgent)
                    is_urgent.setAdapter(arrayAdapter)
                    is_urgent.isClickable=true
                    var urgent_deadline=item.findViewById<TextInputEditText>(R.id.edit_urgent_deadline)

                    val remark=item.findViewById<TextInputEditText>(R.id.edit_remark)

                    mAlertDialog.setPositiveButton("取消") { dialog, id ->
                        dialog.dismiss()
                    }
                    mAlertDialog.setNegativeButton("確定") { dialog, id ->
                        //新增不能為空
                        if( me_code.text.toString().trim().isEmpty() ||
                            semi_finished_prod_number.text.toString().trim().isEmpty() ||
                            pline_id.text.toString().trim().isEmpty() ||
                            latest_inspection_day.text.toString().trim().isEmpty() ||
                            is_re_make.text.toString().trim().isEmpty() ||
                            qc_date.text.toString().trim().isEmpty() ||
                            qc_number.text.toString().trim().isEmpty() ||
                            unit_of_measurement.text.toString().trim().isEmpty() ||
                            est_start_date.text.toString().trim().isEmpty() ||
                            est_complete_date.text.toString().trim().isEmpty() ||
                            est_output.text.toString().trim().isEmpty() ||
                            unit_of_timer.text.toString().trim().isEmpty() ||
                            start_date.text.toString().trim().isEmpty() ||
                            complete_date.text.toString().trim().isEmpty() ||
                            actual_output.text.toString().trim().isEmpty() ||
                            is_request_support.text.toString().trim().isEmpty() ||
                            number_of_support.text.toString().trim().isEmpty() ||
                            number_of_supported.text.toString().trim().isEmpty()  ){
                            Toast.makeText(requireView().context,"Input required", Toast.LENGTH_LONG).show()
                        }
                        else{
                            val addData= ProductControlOrderBody_A()
                            addData.header_id=header_id.text.toString()
                            addData.prod_ctrl_order_number=prod_ctrl_order_number.text.toString()
                            addData.me_code=me_code.text.toString()
                            addData.semi_finished_prod_number=semi_finished_prod_number.text.toString()
                            addData.pline_id=pline_id.text.toString()
                            addData.latest_inspection_day=latest_inspection_day.text.toString()
                            addData.is_re_make=is_re_make.text.toString().toBoolean()
                            addData.qc_date=qc_date.text.toString()
                            addData.qc_number=qc_number.text.toString()
                            addData.unit_of_measurement=unit_of_measurement.text.toString()
                            addData.est_start_date=est_start_date.text.toString()
                            addData.est_complete_date=est_complete_date.text.toString()
                            addData.est_output=est_output.text.toString().toInt()
                            addData.unit_of_timer=unit_of_timer.text.toString()
                            addData.start_date=start_date.text.toString()
                            addData.complete_date=complete_date.text.toString()
                            addData.actual_output=actual_output.text.toString().toInt()
                            addData.is_request_support=is_request_support.text.toString().toBoolean()
                            addData.number_of_support=number_of_support.text.toString().toInt()
                            addData.number_of_supported=number_of_supported.text.toString().toInt()
                            if(request_support_time.text.toString()==""){
                                addData.request_support_time=null
                            }
                            else{
                                addData.request_support_time=request_support_time.text.toString()
                            }
                            addData.is_urgent=is_urgent.text.toString().toBoolean()
                            if(urgent_deadline.text.toString()==""){
                                addData.urgent_deadline=null
                            }
                            else{
                                addData.urgent_deadline=urgent_deadline.text.toString()
                            }

                            addData.remark=remark.text.toString()
                            addData.creator= cookie_data.username
                            addData.create_time= Calendar.getInstance().getTime().toString()
                            addData.editor= cookie_data.username
                            addData.edit_time= Calendar.getInstance().getTime().toString()
                            add_header_body("ProductControlOrderBody_A",addData)
                            when(cookie_data.status){
                                0-> {//成功

                                    ProductControlOrderBody_A_adapter.addItem(addData)
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
                "生產管制單(報工)"->{
                    val item = LayoutInflater.from(activity).inflate(R.layout.recycler_item_product_control_order_body_b, null)
                    cookie_data.Add_View=item
                    val mAlertDialog = AlertDialog.Builder(requireView().context)
                    //mAlertDialog.setIcon(R.mipmap.ic_launcher_round) //set alertdialog icon
                    mAlertDialog.setTitle("新增") //set alertdialog title
                    //mAlertDialog.setMessage("確定要登出?") //set alertdialog message
                    mAlertDialog.setView(item)

                    val combobox=item.resources.getStringArray(R.array.combobox_yes_no)
                    val arrayAdapter= ArrayAdapter(item.context,R.layout.combobox_item,combobox)
                    val arrayAdapter01= ArrayAdapter(item.context,R.layout.combobox_item,cookie_data.pline_id_ComboboxData)
                    val arrayAdapter02= ArrayAdapter(item.context,R.layout.combobox_item,cookie_data.workstation_number_ComboboxData)
                    val arrayAdapter03= ArrayAdapter(item.context,R.layout.combobox_item,cookie_data.card_number_ComboboxData)
                    val arrayAdapter04= ArrayAdapter(item.context,R.layout.combobox_item,cookie_data.name_ComboboxData)

                    var code=item.findViewById<TextInputEditText>(R.id.edit_code)
                    code.inputType= InputType.TYPE_NULL
                    var prod_ctrl_order_number=item.findViewById<TextInputEditText>(R.id.edit_prod_ctrl_order_number)
                    prod_ctrl_order_number.setText(selectFilter)
                    prod_ctrl_order_number.inputType= InputType.TYPE_NULL
                    var pline=item.findViewById<AutoCompleteTextView>(R.id.edit_pline)
                    pline.setAdapter(arrayAdapter01)
                    var workstation_number=item.findViewById<AutoCompleteTextView>(R.id.edit_workstation_number)
                    workstation_number.setAdapter(arrayAdapter02)
                    var qr_code=item.findViewById<TextInputEditText>(R.id.edit_qr_code)
                    qr_code.setOnClickListener {
                        var dialogF= QrCodeFragment()
                        dialogF.show( ( item.context as AppCompatActivity).supportFragmentManager ,"Qrcode")
                        cookie_data.currentAdapter="add"
                    }
                    var card_number=item.findViewById<AutoCompleteTextView>(R.id.edit_card_number)
                    card_number.setAdapter(arrayAdapter03)
                    var staff_name=item.findViewById<AutoCompleteTextView>(R.id.edit_staff_name)
                    staff_name.setAdapter(arrayAdapter04)
                    var online_time=item.findViewById<TextInputEditText>(R.id.edit_online_time)
                    var offline_time=item.findViewById<TextInputEditText>(R.id.edit_offline_time)
                    var is_personal_report=item.findViewById<AutoCompleteTextView>(R.id.edit_is_personal_report)
                    is_personal_report.setAdapter(arrayAdapter)
                    is_personal_report.isClickable=true
                    var actual_output=item.findViewById<TextInputEditText>(R.id.edit_actual_output)

                    val remark=item.findViewById<TextInputEditText>(R.id.edit_remark)

                    mAlertDialog.setPositiveButton("取消") { dialog, id ->
                        dialog.dismiss()
                    }
                    mAlertDialog.setNegativeButton("確定") { dialog, id ->
                        //新增不能為空
                        if( pline.text.toString().trim().isEmpty() ||
                            workstation_number.text.toString().trim().isEmpty() ||
                            card_number.text.toString().trim().isEmpty() ||
                            staff_name.text.toString().trim().isEmpty() ||
                            actual_output.text.toString().trim().isEmpty() ){
                            Toast.makeText(requireView().context,"Input required", Toast.LENGTH_LONG).show()
                        }
                        else{
                            val addData= ProductControlOrderBody_B()
                            addData.code=code.text.toString()
                            addData.prod_ctrl_order_number=prod_ctrl_order_number.text.toString()
                            addData.pline=pline.text.toString()
                            addData.workstation_number=workstation_number.text.toString()
                            addData.qr_code=qr_code.text.toString()
                            addData.card_number=card_number.text.toString()
                            addData.staff_name=staff_name.text.toString()
                            if(online_time.text.toString()==""){
                                addData.online_time=null
                            }
                            else{
                                addData.online_time=online_time.text.toString()
                            }
                            if(offline_time.text.toString()==""){
                                addData.offline_time=null
                            }
                            else{
                                addData.offline_time=offline_time.text.toString()
                            }
                            addData.is_personal_report=is_personal_report.text.toString().toBoolean()
                            addData.actual_output=actual_output.text.toString().toInt()


                            addData.remark=remark.text.toString()
                            addData.creator= cookie_data.username
                            addData.create_time= Calendar.getInstance().getTime().toString()
                            addData.editor= cookie_data.username
                            addData.edit_time= Calendar.getInstance().getTime().toString()
                            add_header_body("ProductControlOrderBody_B",addData)
                            when(cookie_data.status){
                                0-> {//成功

                                    ProductControlOrderBody_B_adapter.addItem(addData)
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
                "生產管制單(派工)"->{
                    val item = LayoutInflater.from(activity).inflate(R.layout.recycler_item_product_control_order_body_c, null)
                    cookie_data.Add_View=item
                    val mAlertDialog = AlertDialog.Builder(requireView().context)
                    //mAlertDialog.setIcon(R.mipmap.ic_launcher_round) //set alertdialog icon
                    mAlertDialog.setTitle("新增") //set alertdialog title
                    //mAlertDialog.setMessage("確定要登出?") //set alertdialog message
                    mAlertDialog.setView(item)

                    val combobox=item.resources.getStringArray(R.array.combobox_yes_no)
                    val arrayAdapter= ArrayAdapter(item.context,R.layout.combobox_item,combobox)
                    val arrayAdapter01= ArrayAdapter(item.context,R.layout.combobox_item,cookie_data.pline_id_ComboboxData)
                    val arrayAdapter02= ArrayAdapter(item.context,R.layout.combobox_item,cookie_data.workstation_number_ComboboxData)
                    val arrayAdapter03= ArrayAdapter(item.context,R.layout.combobox_item,cookie_data.card_number_ComboboxData)
                    val arrayAdapter04= ArrayAdapter(item.context,R.layout.combobox_item,cookie_data.name_ComboboxData)

                    var code=item.findViewById<TextInputEditText>(R.id.edit_code)
                    code.inputType= InputType.TYPE_NULL
                    var prod_ctrl_order_number=item.findViewById<TextInputEditText>(R.id.edit_prod_ctrl_order_number)
                    prod_ctrl_order_number.setText(selectFilter)
                    prod_ctrl_order_number.inputType= InputType.TYPE_NULL
                    var pline=item.findViewById<AutoCompleteTextView>(R.id.edit_pline)
                    pline.setAdapter(arrayAdapter01)
                    var workstation_number=item.findViewById<AutoCompleteTextView>(R.id.edit_workstation_number)
                    workstation_number.setAdapter(arrayAdapter02)
                    var qr_code=item.findViewById<TextInputEditText>(R.id.edit_qr_code)
                    qr_code.setOnClickListener {
                        var dialogF= QrCodeFragment()
                        dialogF.show( ( item.context as AppCompatActivity).supportFragmentManager ,"Qrcode")
                        cookie_data.currentAdapter="add"
                    }
                    var card_number=item.findViewById<AutoCompleteTextView>(R.id.edit_card_number)
                    card_number.setAdapter(arrayAdapter03)
                    var staff_name=item.findViewById<AutoCompleteTextView>(R.id.edit_staff_name)
                    staff_name.setAdapter(arrayAdapter04)
                    var online_time=item.findViewById<TextInputEditText>(R.id.edit_online_time)
                    var offline_time=item.findViewById<TextInputEditText>(R.id.edit_offline_time)
                    var is_personal_report=item.findViewById<AutoCompleteTextView>(R.id.edit_is_personal_report)
                    is_personal_report.setAdapter(arrayAdapter)
                    is_personal_report.isClickable=true
                    var actual_output=item.findViewById<TextInputEditText>(R.id.edit_actual_output)

                    val remark=item.findViewById<TextInputEditText>(R.id.edit_remark)

                    mAlertDialog.setPositiveButton("取消") { dialog, id ->
                        dialog.dismiss()
                    }
                    mAlertDialog.setNegativeButton("確定") { dialog, id ->
                        //新增不能為空
                        if( pline.text.toString().trim().isEmpty() ||
                            workstation_number.text.toString().trim().isEmpty() ||
                            card_number.text.toString().trim().isEmpty() ||
                            staff_name.text.toString().trim().isEmpty() ||
                            actual_output.text.toString().trim().isEmpty() ){
                            Toast.makeText(requireView().context,"Input required", Toast.LENGTH_LONG).show()
                        }
                        else{
                            val addData= ProductControlOrderBody_C()
                            addData.code=code.text.toString()
                            addData.prod_ctrl_order_number=prod_ctrl_order_number.text.toString()
                            addData.pline=pline.text.toString()
                            addData.workstation_number=workstation_number.text.toString()
                            addData.qr_code=qr_code.text.toString()
                            addData.card_number=card_number.text.toString()
                            addData.staff_name=staff_name.text.toString()
                            if(online_time.text.toString()==""){
                                addData.online_time=null
                            }
                            else{
                                addData.online_time=online_time.text.toString()
                            }
                            if(offline_time.text.toString()==""){
                                addData.offline_time=null
                            }
                            else{
                                addData.offline_time=offline_time.text.toString()
                            }
                            addData.is_personal_report=is_personal_report.text.toString().toBoolean()
                            addData.actual_output=actual_output.text.toString().toInt()


                            addData.remark=remark.text.toString()
                            addData.creator= cookie_data.username
                            addData.create_time= Calendar.getInstance().getTime().toString()
                            addData.editor= cookie_data.username
                            addData.edit_time= Calendar.getInstance().getTime().toString()
                            add_header_body("ProductControlOrderBody_C",addData)
                            when(cookie_data.status){
                                0-> {//成功

                                    ProductControlOrderBody_C_adapter.addItem(addData)
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
                "生產管制單(領料單)"->{
                    val item = LayoutInflater.from(activity).inflate(R.layout.recycler_item_production_control_list_requisition, null)
                    cookie_data.Add_View=item
                    val mAlertDialog = AlertDialog.Builder(requireView().context)
                    //mAlertDialog.setIcon(R.mipmap.ic_launcher_round) //set alertdialog icon
                    mAlertDialog.setTitle("新增") //set alertdialog title
                    //mAlertDialog.setMessage("確定要登出?") //set alertdialog message
                    mAlertDialog.setView(item)

                    val combobox=item.resources.getStringArray(R.array.combobox_yes_no)
                    val arrayAdapter= ArrayAdapter(item.context,R.layout.combobox_item,combobox)
                    val arrayAdapter01= ArrayAdapter(item.context,R.layout.combobox_item,cookie_data.item_id_ComboboxData)
                    val arrayAdapter02= ArrayAdapter(item.context,R.layout.combobox_item,cookie_data.card_number_ComboboxData)

                    var requisition_number=item.findViewById<TextInputEditText>(R.id.edit_requisition_number)
                    requisition_number.inputType= InputType.TYPE_NULL
                    var prod_ctrl_order_number=item.findViewById<TextInputEditText>(R.id.edit_prod_ctrl_order_number)
                    prod_ctrl_order_number.setText(selectFilter)
                    prod_ctrl_order_number.inputType= InputType.TYPE_NULL
                    var header_id=item.findViewById<TextInputEditText>(R.id.edit_header_id)
                    header_id.setText(selectFilter.subSequence(0,selectFilter.indexOf("&")))
                    header_id.inputType= InputType.TYPE_NULL
                    var section=item.findViewById<TextInputEditText>(R.id.edit_section)
                    section.inputType= InputType.TYPE_NULL
                    var item_id=item.findViewById<AutoCompleteTextView>(R.id.edit_item_id)
                    item_id.setAdapter(arrayAdapter01)
                    var unit_of_measurement=item.findViewById<TextInputEditText>(R.id.edit_unit_of_measurement)
                    var estimated_picking_date=item.findViewById<TextInputEditText>(R.id.edit_estimated_picking_date)
                    var estimated_picking_amount=item.findViewById<TextInputEditText>(R.id.edit_estimated_picking_amount)
                    var is_inventory_lock=item.findViewById<AutoCompleteTextView>(R.id.edit_is_inventory_lock)
                    is_inventory_lock.setAdapter(arrayAdapter)
                    is_inventory_lock.isClickable=true
                    var inventory_lock_time=item.findViewById<TextInputEditText>(R.id.edit_inventory_lock_time)
                    var is_existing_stocks=item.findViewById<AutoCompleteTextView>(R.id.edit_is_existing_stocks)
                    is_existing_stocks.setAdapter(arrayAdapter)
                    is_existing_stocks.isClickable=true
                    var existing_stocks_amount=item.findViewById<TextInputEditText>(R.id.edit_existing_stocks_amount)
                    var existing_stocks_edit_time=item.findViewById<TextInputEditText>(R.id.edit_existing_stocks_edit_time)
                    var pre_delivery_date=item.findViewById<TextInputEditText>(R.id.edit_pre_delivery_date)
                    var pre_delivery_amount=item.findViewById<TextInputEditText>(R.id.edit_pre_delivery_amount)
                    var pre_delivery_edit_time=item.findViewById<TextInputEditText>(R.id.edit_pre_delivery_edit_time)
                    var is_vender_check=item.findViewById<AutoCompleteTextView>(R.id.edit_is_vender_check)
                    is_vender_check.setAdapter(arrayAdapter)
                    is_vender_check.isClickable=true
                    var vender_pre_delivery_date=item.findViewById<TextInputEditText>(R.id.edit_vender_pre_delivery_date)
                    var vender_pre_delivery_amount=item.findViewById<TextInputEditText>(R.id.edit_vender_pre_delivery_amount)
                    var vender_edit_time=item.findViewById<TextInputEditText>(R.id.edit_vender_edit_time)
                    var procurement_approval=item.findViewById<TextInputEditText>(R.id.edit_procurement_approval)
                    var approval_instructions=item.findViewById<TextInputEditText>(R.id.edit_approval_instructions)
                    var is_instock=item.findViewById<AutoCompleteTextView>(R.id.edit_is_instock)
                    is_instock.setAdapter(arrayAdapter)
                    is_instock.isClickable=true
                    var vender_instock_date=item.findViewById<TextInputEditText>(R.id.edit_vender_instock_date)
                    var is_materials_sent=item.findViewById<AutoCompleteTextView>(R.id.edit_is_materials_sent)
                    is_materials_sent.setAdapter(arrayAdapter)
                    is_materials_sent.isClickable=true
                    var materials_sent_date=item.findViewById<TextInputEditText>(R.id.edit_materials_sent_date)
                    var materials_sent_amount=item.findViewById<TextInputEditText>(R.id.edit_materials_sent_amount)
                    var ng_amount=item.findViewById<TextInputEditText>(R.id.edit_ng_amount)
                    var card_number=item.findViewById<AutoCompleteTextView>(R.id.edit_card_number)
                    card_number.setAdapter(arrayAdapter02)
                    var over_received_order=item.findViewById<AutoCompleteTextView>(R.id.edit_over_received_order)
                    over_received_order.setAdapter(arrayAdapter)
                    over_received_order.isClickable=true
                    var over_received_amount=item.findViewById<TextInputEditText>(R.id.edit_over_received_amount)
                    var is_urgent=item.findViewById<AutoCompleteTextView>(R.id.edit_is_urgent)
                    is_urgent.setAdapter(arrayAdapter)
                    is_urgent.isClickable=true
                    var urgent_deadline=item.findViewById<TextInputEditText>(R.id.edit_urgent_deadline)


                    val remark=item.findViewById<TextInputEditText>(R.id.edit_remark)

                    mAlertDialog.setPositiveButton("取消") { dialog, id ->
                        dialog.dismiss()
                    }
                    mAlertDialog.setNegativeButton("確定") { dialog, id ->
                        //新增不能為空
                        if( item_id.text.toString().trim().isEmpty() ||
                            unit_of_measurement.text.toString().trim().isEmpty() ||
                            estimated_picking_amount.text.toString().trim().isEmpty() ||
                            existing_stocks_amount.text.toString().trim().isEmpty() ||
                            pre_delivery_amount.text.toString().trim().isEmpty() ||
                            vender_pre_delivery_amount.text.toString().trim().isEmpty() ||
                            materials_sent_amount.text.toString().trim().isEmpty() ||
                            ng_amount.text.toString().trim().isEmpty() ||
                            over_received_amount.text.toString().trim().isEmpty() ){
                            Toast.makeText(requireView().context,"Input required", Toast.LENGTH_LONG).show()
                        }
                        else{
                            val addData= ProductionControlListRequisition()
                            addData.requisition_number=requisition_number.text.toString()
                            addData.prod_ctrl_order_number=prod_ctrl_order_number.text.toString()
                            addData.header_id=header_id.text.toString()
                            addData.section=section.text.toString()
                            addData.item_id=item_id.text.toString()
                            addData.card_number=card_number.text.toString()
                            addData.unit_of_measurement=unit_of_measurement.text.toString()
                            if(estimated_picking_date.text.toString()==""){
                                addData.estimated_picking_date=null
                            }
                            else{
                                addData.estimated_picking_date=estimated_picking_date.text.toString()
                            }
                            addData.estimated_picking_amount=estimated_picking_amount.text.toString().toInt()
                            addData.is_inventory_lock=is_inventory_lock.text.toString().toBoolean()
                            if(inventory_lock_time.text.toString()==""){
                                addData.inventory_lock_time=null
                            }
                            else{
                                addData.inventory_lock_time=inventory_lock_time.text.toString()
                            }
                            addData.is_existing_stocks=is_existing_stocks.text.toString().toBoolean()
                            addData.existing_stocks_amount=existing_stocks_amount.text.toString().toInt()
                            if(existing_stocks_edit_time.text.toString()==""){
                                addData.existing_stocks_edit_time=null
                            }
                            else{
                                addData.existing_stocks_edit_time=existing_stocks_edit_time.text.toString()
                            }
                            if(pre_delivery_date.text.toString()==""){
                                addData.pre_delivery_date=null
                            }
                            else{
                                addData.pre_delivery_date=pre_delivery_date.text.toString()
                            }
                            addData.pre_delivery_amount=pre_delivery_amount.text.toString().toInt()
                            if(pre_delivery_edit_time.text.toString()==""){
                                addData.pre_delivery_edit_time=null
                            }
                            else{
                                addData.pre_delivery_edit_time=pre_delivery_edit_time.text.toString()
                            }
                            addData.is_vender_check=is_vender_check.text.toString().toBoolean()
                            if(vender_pre_delivery_date.text.toString()==""){
                                addData.vender_pre_delivery_date=null
                            }
                            else{
                                addData.vender_pre_delivery_date=vender_pre_delivery_date.text.toString()
                            }
                            addData.vender_pre_delivery_amount=vender_pre_delivery_amount.text.toString().toInt()
                            if(vender_edit_time.text.toString()==""){
                                addData.vender_edit_time=null
                            }
                            else{
                                addData.vender_edit_time=vender_edit_time.text.toString()
                            }
                            addData.procurement_approval=procurement_approval.text.toString()
                            addData.approval_instructions=approval_instructions.text.toString()
                            addData.is_instock=is_instock.text.toString().toBoolean()
                            if(vender_instock_date.text.toString()==""){
                                addData.vender_instock_date=null
                            }
                            else{
                                addData.vender_instock_date=vender_instock_date.text.toString()
                            }
                            addData.is_materials_sent=is_materials_sent.text.toString().toBoolean()
                            if(materials_sent_date.text.toString()==""){
                                addData.materials_sent_date=null
                            }
                            else{
                                addData.materials_sent_date=materials_sent_date.text.toString()
                            }
                            addData.materials_sent_amount=materials_sent_amount.text.toString().toInt()
                            addData.ng_amount=ng_amount.text.toString().toInt()
                            addData.card_number=card_number.text.toString()
                            addData.over_received_order=over_received_order.text.toString().toBoolean()
                            addData.over_received_amount=over_received_amount.text.toString().toInt()
                            addData.is_urgent=is_urgent.text.toString().toBoolean()
                            if(urgent_deadline.text.toString()==""){
                                addData.urgent_deadline=null
                            }
                            else{
                                addData.urgent_deadline=urgent_deadline.text.toString()
                            }


                            addData.remark=remark.text.toString()
                            addData.creator= cookie_data.username
                            addData.create_time= Calendar.getInstance().getTime().toString()
                            addData.editor= cookie_data.username
                            addData.edit_time= Calendar.getInstance().getTime().toString()
                            add_header_body("ProductionControlListRequisition",addData)
                            when(cookie_data.status){
                                0-> {//成功

                                    ProductionControlListRequisition_adapter.addItem(addData)
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
                "庫存異動單(單頭)"->{
                    val item = LayoutInflater.from(activity).inflate(R.layout.recycler_item_stock_trans_order_header, null)
                    val mAlertDialog = AlertDialog.Builder(requireView().context)
                    //mAlertDialog.setIcon(R.mipmap.ic_launcher_round) //set alertdialog icon
                    mAlertDialog.setTitle("新增") //set alertdialog title
                    //mAlertDialog.setMessage("確定要登出?") //set alertdialog message
                    mAlertDialog.setView(item)

                    val combobox=item.resources.getStringArray(R.array.combobox_yes_no)
                    val arrayAdapter= ArrayAdapter(item.context,R.layout.combobox_item,combobox)
                    val arrayAdapter01= ArrayAdapter(item.context,R.layout.combobox_item,cookie_data.dept_name_ComboboxData)
                    val arrayAdapter02= ArrayAdapter(item.context,R.layout.combobox_item,cookie_data.inv_code_m_ComboboxData)
                    val arrayAdapter03= ArrayAdapter(item.context,R.layout.combobox_item,cookie_data.inv_code_s_ComboboxData)
                    val arrayAdapter04= ArrayAdapter(item.context,R.layout.combobox_item,cookie_data.ProductControlOrderHeader_id_ComboboxData)

                    var _id=item.findViewById<TextInputEditText>(R.id.edit_id)
                    var date=item.findViewById<TextInputEditText>(R.id.edit_date)
                    var dept=item.findViewById<AutoCompleteTextView>(R.id.edit_dept)
                    dept.setAdapter(arrayAdapter01)
                    var main_trans_code=item.findViewById<AutoCompleteTextView>(R.id.edit_main_trans_code)
                    main_trans_code.setAdapter(arrayAdapter02)
                    var sec_trans_code=item.findViewById<AutoCompleteTextView>(R.id.edit_sec_trans_code)
                    sec_trans_code.setAdapter(arrayAdapter03)
                    var prod_ctrl_order_number=item.findViewById<AutoCompleteTextView>(R.id.edit_prod_ctrl_order_number)
                    prod_ctrl_order_number.setAdapter(arrayAdapter04)
                    var illustrate=item.findViewById<TextInputEditText>(R.id.edit_illustrate)

                    val remark=item.findViewById<TextInputEditText>(R.id.edit_remark)

                    mAlertDialog.setPositiveButton("取消") { dialog, id ->
                        dialog.dismiss()
                    }
                    mAlertDialog.setNegativeButton("確定") { dialog, id ->
                        //新增不能為空
                        if( _id.text.toString().trim().isEmpty() ||
                            date.text.toString().trim().isEmpty()  ||
                            dept.text.toString().trim().isEmpty()       ||
                            main_trans_code.text.toString().trim().isEmpty() ||
                            sec_trans_code.text.toString().trim().isEmpty()  ||
                            prod_ctrl_order_number.text.toString().trim().isEmpty()  ){
                            Toast.makeText(requireView().context,"Input required", Toast.LENGTH_LONG).show()
                        }
                        else{
                            val addData= StockTransOrderHeader()
                            addData._id=_id.text.toString()
                            addData.date=date.text.toString()
                            addData.dept=dept.text.toString()
                            addData.main_trans_code=main_trans_code.text.toString()
                            addData.sec_trans_code=sec_trans_code.text.toString()
                            addData.prod_ctrl_order_number=prod_ctrl_order_number.text.toString()
                            addData.illustrate=illustrate.text.toString()


                            addData.remark=remark.text.toString()
                            addData.creator= cookie_data.username
                            addData.create_time= Calendar.getInstance().getTime().toString()
                            addData.editor= cookie_data.username
                            addData.edit_time= Calendar.getInstance().getTime().toString()
                            add_header_body("StockTransOrderHeader",addData)
                            when(cookie_data.status){
                                0-> {//成功

                                    StockTransOrderHeader_adapter.addItem(addData)
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
                "庫存異動單(單身)"->{
                    val item = LayoutInflater.from(activity).inflate(R.layout.recycler_item_stock_trans_order_body, null)
                    val mAlertDialog = AlertDialog.Builder(requireView().context)
                    //mAlertDialog.setIcon(R.mipmap.ic_launcher_round) //set alertdialog icon
                    mAlertDialog.setTitle("新增") //set alertdialog title
                    //mAlertDialog.setMessage("確定要登出?") //set alertdialog message
                    mAlertDialog.setView(item)

                    val combobox=item.resources.getStringArray(R.array.combobox_yes_no)
                    val arrayAdapter= ArrayAdapter(item.context,R.layout.combobox_item,combobox)
                    val arrayAdapter01= ArrayAdapter(item.context,R.layout.combobox_item,cookie_data.item_id_ComboboxData)
                    val arrayAdapter02= ArrayAdapter(item.context,R.layout.combobox_item,cookie_data.inv_code_m_ComboboxData)
                    val arrayAdapter03= ArrayAdapter(item.context,R.layout.combobox_item,cookie_data.inv_code_s_ComboboxData)
                    val arrayAdapter04= ArrayAdapter(item.context,R.layout.combobox_item,cookie_data.store_area_ComboboxData)
                    val arrayAdapter05= ArrayAdapter(item.context,R.layout.combobox_item,cookie_data.store_local_ComboboxData)

                    var header_id=item.findViewById<TextInputEditText>(R.id.edit_header_id)
                    header_id.setText(selectFilter)
                    header_id.inputType= InputType.TYPE_NULL
                    var item_id=item.findViewById<AutoCompleteTextView>(R.id.edit_item_id)
                    item_id.setAdapter(arrayAdapter01)
                    var modify_count=item.findViewById<TextInputEditText>(R.id.edit_modify_count)
                    modify_count.inputType= InputType.TYPE_NULL
                    var main_trans_code=item.findViewById<AutoCompleteTextView>(R.id.edit_main_trans_code)
                    main_trans_code.setAdapter(arrayAdapter02)
                    var sec_trans_code=item.findViewById<AutoCompleteTextView>(R.id.edit_sec_trans_code)
                    sec_trans_code.setAdapter(arrayAdapter03)
                    var store_area=item.findViewById<AutoCompleteTextView>(R.id.edit_store_area)
                    store_area.setAdapter(arrayAdapter04)
                    var store_local=item.findViewById<AutoCompleteTextView>(R.id.edit_store_local)
                    store_local.setAdapter(arrayAdapter05)
                    var qc_insp_number=item.findViewById<TextInputEditText>(R.id.edit_qc_insp_number)
                    var qc_time=item.findViewById<TextInputEditText>(R.id.edit_qc_time)
                    var ok_count=item.findViewById<TextInputEditText>(R.id.edit_ok_count)
                    var ng_count=item.findViewById<TextInputEditText>(R.id.edit_ng_count)
                    var scrapped_count=item.findViewById<TextInputEditText>(R.id.edit_scrapped_count)
                    var is_rework=item.findViewById<AutoCompleteTextView>(R.id.edit_is_rework)
                    is_rework.setAdapter(arrayAdapter)
                    is_rework.isClickable=true

                    val remark=item.findViewById<TextInputEditText>(R.id.edit_remark)

                    mAlertDialog.setPositiveButton("取消") { dialog, id ->
                        dialog.dismiss()
                    }
                    mAlertDialog.setNegativeButton("確定") { dialog, id ->
                        //新增不能為空
                        if( item_id.text.toString().trim().isEmpty() ||
                            main_trans_code.text.toString().trim().isEmpty()  ||
                            sec_trans_code.toString().trim().isEmpty()       ||
                            store_area.text.toString().trim().isEmpty()   ||
                            store_local.text.toString().trim().isEmpty()       ||
                            qc_insp_number.text.toString().trim().isEmpty()   ){
                            Toast.makeText(requireView().context,"Input required", Toast.LENGTH_LONG).show()
                        }
                        else{
                            val addData= StockTransOrderBody()
                            addData.header_id=header_id.text.toString()
                            addData.item_id=item_id.text.toString()
                            addData.modify_count=modify_count.text.toString().toDouble()
                            addData.main_trans_code=main_trans_code.text.toString()
                            addData.sec_trans_code=sec_trans_code.text.toString()
                            addData.store_area=store_area.text.toString()
                            addData.store_local=store_local.text.toString()
                            addData.qc_insp_number=qc_insp_number.text.toString()
                            if(qc_time.text.toString()==""){
                                addData.qc_time=null
                            }
                            else {
                                addData.qc_time = qc_time.text.toString()
                            }
                            addData.ok_count=ok_count.text.toString().toDouble()
                            addData.ng_count=ng_count.text.toString().toDouble()
                            addData.scrapped_count=scrapped_count.text.toString().toDouble()
                            addData.is_rework=is_rework.text.toString().toBoolean()


                            addData.remark=remark.text.toString()
                            addData.creator= cookie_data.username
                            addData.create_time= Calendar.getInstance().getTime().toString()
                            addData.editor= cookie_data.username
                            addData.edit_time= Calendar.getInstance().getTime().toString()
                            add_header_body("StockTransOrderBody",addData)
                            when(cookie_data.status){
                                0-> {//成功

                                    StockTransOrderBody_adapter.addItem(addData)
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
                "設備養護記錄"->{
                    val item = LayoutInflater.from(activity).inflate(R.layout.recycler_item_equipment_maintenance_record, null)
                    val mAlertDialog = AlertDialog.Builder(requireView().context)
                    //mAlertDialog.setIcon(R.mipmap.ic_launcher_round) //set alertdialog icon
                    mAlertDialog.setTitle("新增") //set alertdialog title
                    //mAlertDialog.setMessage("確定要登出?") //set alertdialog message
                    mAlertDialog.setView(item)

                    val combobox=item.resources.getStringArray(R.array.combobox_yes_no)
                    val arrayAdapter= ArrayAdapter(item.context,R.layout.combobox_item,combobox)
                    val arrayAdapter01= ArrayAdapter(item.context,R.layout.combobox_item,cookie_data.device_id_ComboboxData)
                    val arrayAdapter02= ArrayAdapter(item.context,R.layout.combobox_item,cookie_data.vender_id_ComboboxData)
                    val arrayAdapter03= ArrayAdapter(item.context,R.layout.combobox_item,cookie_data.maintain_type_ComboboxData)

                    var maintenance_id=item.findViewById<TextInputEditText>(R.id.edit_maintenance_id)
                    maintenance_id.inputType= InputType.TYPE_NULL
                    var equipment_id=item.findViewById<AutoCompleteTextView>(R.id.edit_equipment_id)
                    equipment_id.setAdapter(arrayAdapter01)
                    var date=item.findViewById<TextInputEditText>(R.id.edit_date)
                    var start_time=item.findViewById<TextInputEditText>(R.id.edit_start_time)
                    var end_time=item.findViewById<TextInputEditText>(R.id.edit_end_time)
                    var vender_id=item.findViewById<AutoCompleteTextView>(R.id.edit_vender_id)
                    vender_id.setAdapter(arrayAdapter02)
                    var maintenance_type=item.findViewById<AutoCompleteTextView>(R.id.edit_maintenance_type)
                    maintenance_type.setAdapter(arrayAdapter03)
                    var is_ok=item.findViewById<AutoCompleteTextView>(R.id.edit_is_ok)
                    is_ok.setAdapter(arrayAdapter)
                    is_ok.isClickable=true
                    var is_not_available=item.findViewById<AutoCompleteTextView>(R.id.edit_is_not_available)
                    is_not_available.setAdapter(arrayAdapter)
                    is_not_available.isClickable=true

                    val remark=item.findViewById<TextInputEditText>(R.id.edit_remark)

                    mAlertDialog.setPositiveButton("取消") { dialog, id ->
                        dialog.dismiss()
                    }
                    mAlertDialog.setNegativeButton("確定") { dialog, id ->
                        //新增不能為空
                        if( equipment_id.text.toString().trim().isEmpty() ||
                            vender_id.text.toString().trim().isEmpty()  ||
                            maintenance_type.toString().trim().isEmpty()   ){
                            Toast.makeText(requireView().context,"Input required", Toast.LENGTH_LONG).show()
                        }
                        else{
                            val addData= EquipmentMaintenanceRecord()
                            addData.maintenance_id=maintenance_id.text.toString()
                            addData.equipment_id=equipment_id.text.toString()
                            if(date.text.toString()==""){
                                addData.date=null
                            }
                            else {
                                addData.date = date.text.toString()
                            }
                            if(start_time.text.toString()==""){
                                addData.start_time=null
                            }
                            else {
                                addData.start_time = start_time.text.toString()
                            }
                            if(end_time.text.toString()==""){
                                addData.end_time=null
                            }
                            else {
                                addData.end_time = end_time.text.toString()
                            }
                            addData.vender_id=vender_id.text.toString()
                            addData.maintenance_type=maintenance_type.text.toString()
                            addData.is_ok=is_ok.text.toString().toBoolean()
                            addData.is_not_available=is_not_available.text.toString().toBoolean()


                            addData.remark=remark.text.toString()
                            addData.creator= cookie_data.username
                            addData.create_time= Calendar.getInstance().getTime().toString()
                            addData.editor= cookie_data.username
                            addData.edit_time= Calendar.getInstance().getTime().toString()
                            add_header_body("EquipmentMaintenanceRecord",addData)
                            when(cookie_data.status){
                                0-> {//成功

                                    EquipmentMaintenanceRecord_adapter.addItem(addData)
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
                "文管中心資訊庫(單頭)"->{
                    val item = LayoutInflater.from(activity).inflate(R.layout.recycler_item_dmcil_header, null)
                    val mAlertDialog = AlertDialog.Builder(requireView().context)
                    //mAlertDialog.setIcon(R.mipmap.ic_launcher_round) //set alertdialog icon
                    mAlertDialog.setTitle("新增") //set alertdialog title
                    //mAlertDialog.setMessage("確定要登出?") //set alertdialog message
                    mAlertDialog.setView(item)

                    val combobox=item.resources.getStringArray(R.array.combobox_yes_no)
                    val arrayAdapter= ArrayAdapter(item.context,R.layout.combobox_item,combobox)
                    val arrayAdapter01= ArrayAdapter(item.context,R.layout.combobox_item,cookie_data.dept_name_ComboboxData)
                    val arrayAdapter02= ArrayAdapter(item.context,R.layout.combobox_item,cookie_data.item_id_ComboboxData)

                    var _id=item.findViewById<TextInputEditText>(R.id.edit_id)
                    _id.inputType=InputType.TYPE_NULL
                    var dept=item.findViewById<AutoCompleteTextView>(R.id.edit_dept)
                    dept.setAdapter(arrayAdapter01)
                    var topic=item.findViewById<TextInputEditText>(R.id.edit_topic)
                    var info_type=item.findViewById<TextInputEditText>(R.id.edit_info_type)
                    var item_id=item.findViewById<AutoCompleteTextView>(R.id.edit_item_id)
                    item_id.setAdapter(arrayAdapter02)


                    val remark=item.findViewById<TextInputEditText>(R.id.edit_remark)

                    mAlertDialog.setPositiveButton("取消") { dialog, id ->
                        dialog.dismiss()
                    }
                    mAlertDialog.setNegativeButton("確定") { dialog, id ->
                        //新增不能為空
                        if( dept.text.toString().trim().isEmpty() ||
                            topic.text.toString().trim().isEmpty()  ||
                            info_type.text.toString().trim().isEmpty()       ||
                            item_id.text.toString().trim().isEmpty()    ){
                            Toast.makeText(requireView().context,"Input required", Toast.LENGTH_LONG).show()
                        }
                        else{
                            val addData= DMCILHeader()
                            addData._id=_id.text.toString()
                            addData.dept=dept.text.toString()
                            addData.topic=topic.text.toString()
                            addData.info_type=info_type.text.toString()
                            addData.item_id=item_id.text.toString()


                            addData.remark=remark.text.toString()
                            addData.creator= cookie_data.username
                            addData.create_time= Calendar.getInstance().getTime().toString()
                            addData.editor= cookie_data.username
                            addData.edit_time= Calendar.getInstance().getTime().toString()
                            add_header_body("DMCILHeader",addData)
                            when(cookie_data.status){
                                0-> {//成功

                                    DMCILHeader_adapter.addItem(addData)
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
                "文管中心資訊庫(單身)"->{
                    val item = LayoutInflater.from(activity).inflate(R.layout.recycler_item_dmcil_body, null)
                    val mAlertDialog = AlertDialog.Builder(requireView().context)
                    //mAlertDialog.setIcon(R.mipmap.ic_launcher_round) //set alertdialog icon
                    mAlertDialog.setTitle("新增") //set alertdialog title
                    //mAlertDialog.setMessage("確定要登出?") //set alertdialog message
                    mAlertDialog.setView(item)

                    val combobox=item.resources.getStringArray(R.array.combobox_yes_no)
                    val arrayAdapter= ArrayAdapter(item.context,R.layout.combobox_item,combobox)

                    var header_id=item.findViewById<TextInputEditText>(R.id.edit_header_id)
                    header_id.setText(selectFilter)
                    header_id.inputType= InputType.TYPE_NULL
                    var code=item.findViewById<TextInputEditText>(R.id.edit_code)
                    code.inputType= InputType.TYPE_NULL
                    var section=item.findViewById<TextInputEditText>(R.id.edit_section)
                    var outline=item.findViewById<TextInputEditText>(R.id.edit_outline)
                    var context=item.findViewById<TextInputEditText>(R.id.edit_context)
                    var dept=item.findViewById<TextInputEditText>(R.id.edit_dept)
                    var info_type=item.findViewById<TextInputEditText>(R.id.edit_info_type)
                    var item_id=item.findViewById<TextInputEditText>(R.id.edit_item_id)


                    val remark=item.findViewById<TextInputEditText>(R.id.edit_remark)

                    mAlertDialog.setPositiveButton("取消") { dialog, id ->
                        dialog.dismiss()
                    }
                    mAlertDialog.setNegativeButton("確定") { dialog, id ->
                        //新增不能為空
                        if( section.text.toString().trim().isEmpty() ||
                            outline.text.toString().trim().isEmpty()  ||
                            context.text.toString().trim().isEmpty()       ||
                            dept.text.toString().trim().isEmpty()   ||
                            info_type.text.toString().trim().isEmpty()       ||
                            item_id.text.toString().trim().isEmpty()   ){
                            Toast.makeText(requireView().context,"Input required", Toast.LENGTH_LONG).show()
                        }
                        else{
                            val addData= DMCILBody()
                            addData.header_id=header_id.text.toString()
                            addData.code=code.text.toString()
                            addData.section=section.text.toString()
                            addData.outline=outline.text.toString()
                            addData.dept=dept.text.toString()
                            addData.context=context.text.toString()
                            addData.info_type=info_type.text.toString()
                            addData.item_id=item_id.text.toString()


                            addData.remark=remark.text.toString()
                            addData.creator= cookie_data.username
                            addData.create_time= Calendar.getInstance().getTime().toString()
                            addData.editor= cookie_data.username
                            addData.edit_time= Calendar.getInstance().getTime().toString()
                            add_header_body("DMCILBody",addData)
                            when(cookie_data.status){
                                0-> {//成功

                                    DMCILBody_adapter.addItem(addData)
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
    //異動資料單頭單身
    private fun show_header_body(operation:String,view_type:String,view_hide:String) {
        when(operation){
            "StackingControlListHeader"->{
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
                    .url(cookie_data.URL+"/stacking_control_management")
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
            "StackingControlListBody"->{
                val filter = JSONObject()
                filter.put("header_id",selectFilter)
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
                    .url(cookie_data.URL+"/stacking_control_management")
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
            "BookingNoticeHeader"->{
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
                    .add("action", cookie_data.Actions.VIEW)
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
            "DMCILHeader"->{
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
                    .url(cookie_data.URL+"/dmcil_management")
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
            "DMCILBody"->{
                val filter = JSONObject()
                filter.put("header_id",selectFilter)
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
                    .url(cookie_data.URL+"/dmcil_management")
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
                filter.put("header_id",selectFilter)
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
            "ProductControlOrderBody_C"->{
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
            "ProductionControlListRequisition"->{
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
            "StockTransOrderBody"->{
                val filter = JSONObject()
                filter.put("header_id",selectFilter)
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
            "MealOrderListHeader"->{
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
            "MealOrderListBody"->{
                val filter = JSONObject()
                filter.put("header_id",selectFilter)
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
            "EquipmentMaintenanceRecord"->{
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
                    .url(cookie_data.URL+"/vender_log_management")
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
            is StackingControlListBody ->{
                val add = JSONObject()
                add.put("code",addData.code)
                add.put("header_id",addData.header_id)
                add.put("product_id",addData.product_id)
                add.put("master_order_number",addData.master_order_number)
                add.put("count",addData.count)
                add.put("store_area",addData.store_area)
                add.put("store_local",addData.store_local)

                add.put("remark",addData.remark)
                add.put("creator", cookie_data.username)
                add.put("editor", cookie_data.username)
                //Log.d("GSON", "msg:${add}")
                val body = FormBody.Builder()
                    .add("username", cookie_data.username)
                    .add("operation", operation)
                    .add("action", cookie_data.Actions.ADD)
                    .add("data",add.toString())
                    .add("csrfmiddlewaretoken", cookie_data.tokenValue)
                    .add("login_flag", cookie_data.loginflag)
                    .build()
                val request = Request.Builder()
                    .url(cookie_data.URL+"/stacking_control_management")
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
            is BookingNoticeHeader ->{
                val add = JSONObject()
                add.put("_id",addData._id)
                add.put("shipping_number",addData.shipping_number)
                add.put("shipping_order_number",addData.shipping_order_number)
                add.put("date",addData.date)
                add.put("customer_poNo",addData.customer_poNo)

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
                    .add("action", cookie_data.Actions.ADD)
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
            is DMCILHeader ->{
                val add = JSONObject()
                add.put("_id",addData._id)
                add.put("dept",addData.dept)
                add.put("topic",addData.topic)
                add.put("info_type",addData.info_type)
                add.put("item_id",addData.item_id)

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
                    .url(cookie_data.URL+"/dmcil_management")
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
            is DMCILBody ->{
                val add = JSONObject()
                add.put("header_id",addData.header_id)
                add.put("code",addData.code)
                add.put("section",addData.section)
                add.put("outline",addData.outline)
                add.put("dept",addData.dept)
                add.put("context",addData.context)
                add.put("info_type",addData.info_type)
                add.put("item_id",addData.item_id)

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
                    .url(cookie_data.URL+"/dmcil_management")
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
            is ProductControlOrderBody_A ->{
                val add = JSONObject()
                add.put("header_id",addData.header_id)
                add.put("prod_ctrl_order_number",addData.prod_ctrl_order_number)
                add.put("me_code",addData.me_code)
                add.put("semi_finished_prod_number",addData.semi_finished_prod_number)
                add.put("pline_id",addData.pline_id)
                add.put("latest_inspection_day",addData.latest_inspection_day)
                add.put("is_re_make",addData.is_re_make)
                add.put("qc_date",addData.qc_date)
                add.put("qc_number",addData.qc_number)
                add.put("unit_of_measurement",addData.unit_of_measurement)
                add.put("est_start_date",addData.est_start_date)
                add.put("est_complete_date",addData.est_complete_date)
                add.put("est_output",addData.est_output)
                add.put("unit_of_timer",addData.unit_of_timer)
                add.put("start_date",addData.start_date)
                add.put("complete_date",addData.complete_date)
                add.put("actual_output",addData.actual_output)
                add.put("is_request_support",addData.is_request_support)
                add.put("number_of_support",addData.number_of_support)
                add.put("number_of_supported",addData.number_of_supported)
                add.put("request_support_time",addData.request_support_time)
                add.put("is_urgent",addData.is_urgent)
                add.put("urgent_deadline",addData.urgent_deadline)

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
                    cookie_data.msg=responseInfo.msg
                }
            }
            is ProductControlOrderBody_B ->{
                val add = JSONObject()
                add.put("code",addData.code)
                add.put("prod_ctrl_order_number",addData.prod_ctrl_order_number)
                add.put("pline",addData.pline)
                add.put("workstation_number",addData.workstation_number)
                add.put("qr_code",addData.qr_code)
                add.put("card_number",addData.card_number)
                add.put("staff_name",addData.staff_name)
                add.put("online_time",addData.online_time)
                add.put("offline_time",addData.offline_time)
                add.put("is_personal_report",addData.is_personal_report)
                add.put("actual_output",addData.actual_output)

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
                    cookie_data.msg=responseInfo.msg
                }
            }
            is ProductControlOrderBody_C ->{
                val add = JSONObject()
                add.put("code",addData.code)
                add.put("prod_ctrl_order_number",addData.prod_ctrl_order_number)
                add.put("pline",addData.pline)
                add.put("workstation_number",addData.workstation_number)
                add.put("qr_code",addData.qr_code)
                add.put("card_number",addData.card_number)
                add.put("staff_name",addData.staff_name)
                add.put("online_time",addData.online_time)
                add.put("offline_time",addData.offline_time)
                add.put("is_personal_report",addData.is_personal_report)
                add.put("actual_output",addData.actual_output)

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
                    cookie_data.msg=responseInfo.msg
                }
            }
            is ProductionControlListRequisition ->{
                val add = JSONObject()
                add.put("prod_ctrl_order_number",addData.prod_ctrl_order_number)
                add.put("requisition_number",addData.requisition_number)
                add.put("header_id",addData.header_id)
                add.put("section",addData.section)
                add.put("item_id",addData.item_id)
                add.put("unit_of_measurement",addData.unit_of_measurement)
                add.put("estimated_picking_date",addData.estimated_picking_date)
                add.put("estimated_picking_amount",addData.estimated_picking_amount)
                add.put("is_inventory_lock",addData.is_inventory_lock)
                add.put("inventory_lock_time",addData.inventory_lock_time)
                add.put("is_existing_stocks",addData.is_existing_stocks)
                add.put("existing_stocks_amount",addData.existing_stocks_amount)
                add.put("existing_stocks_edit_time",addData.existing_stocks_edit_time)
                add.put("pre_delivery_date",addData.pre_delivery_date)
                add.put("pre_delivery_amount",addData.pre_delivery_amount)
                add.put("pre_delivery_edit_time",addData.pre_delivery_edit_time)
                add.put("is_vender_check",addData.is_vender_check)
                add.put("vender_pre_delivery_date",addData.vender_pre_delivery_date)
                add.put("vender_pre_delivery_amount",addData.vender_pre_delivery_amount)
                add.put("vender_edit_time",addData.vender_edit_time)
                add.put("procurement_approval",addData.procurement_approval)
                add.put("approval_instructions",addData.approval_instructions)
                add.put("is_instock",addData.is_instock)
                add.put("vender_instock_date",addData.vender_instock_date)
                add.put("is_materials_sent",addData.is_materials_sent)
                add.put("materials_sent_date",addData.materials_sent_date)
                add.put("materials_sent_amount",addData.materials_sent_amount)
                add.put("ng_amount",addData.ng_amount)
                add.put("card_number",addData.card_number)
                add.put("over_received_order",addData.over_received_order)
                add.put("over_received_amount",addData.over_received_amount)
                add.put("is_urgent",addData.is_urgent)
                add.put("urgent_deadline",addData.urgent_deadline)

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
                    cookie_data.msg=responseInfo.msg
                }
            }
            is MealOrderListBody ->{
                val add = JSONObject()
                add.put("header_id",addData.header_id)
                add.put("order_number",addData.order_number)
                add.put("card_number",addData.card_number)
                add.put("is_support",addData.is_support)
                add.put("support_hours",addData.support_hours)
                add.put("is_morning_leave",addData.is_morning_leave)
                add.put("is_lunch_leave",addData.is_lunch_leave)
                add.put("is_all_day_leave",addData.is_all_day_leave)
                add.put("leave_hours",addData.leave_hours)
                add.put("is_l_meat_meals",addData.is_l_meat_meals)
                add.put("is_l_vegetarian_meals",addData.is_l_vegetarian_meals)
                add.put("is_l_indonesia_meals",addData.is_l_indonesia_meals)
                add.put("is_l_num_of_selfcare",addData.is_l_num_of_selfcare)
                add.put("is_overtime",addData.is_overtime)
                add.put("overtime_hours",addData.overtime_hours)
                add.put("is_d_meat_meals",addData.is_d_meat_meals)
                add.put("is_d_vegetarian_meals",addData.is_d_vegetarian_meals)
                add.put("is_d_indonesia_meals",addData.is_d_indonesia_meals)
                add.put("is_d_num_of_selfcare",addData.is_d_num_of_selfcare)

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
                    cookie_data.msg=responseInfo.msg
                }
            }
            is EquipmentMaintenanceRecord ->{
                val add = JSONObject()
                add.put("maintenance_id",addData.maintenance_id)
                add.put("equipment_id",addData.equipment_id)
                add.put("date",addData.date)
                add.put("start_time",addData.start_time)
                add.put("end_time",addData.end_time)
                add.put("vender_id",addData.vender_id)
                add.put("maintenance_type",addData.maintenance_type)
                add.put("is_ok",addData.is_ok)
                add.put("is_not_available",addData.is_not_available)


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
                    .url(cookie_data.URL+"/vender_log_management")
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
            is StackingControlListHeader ->{
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
                    .url(cookie_data.URL+"/stacking_control_management")
                    .header("User-Agent", "ERP_MOBILE")
                    .post(body)
                    .build()
                runBlocking {
                    var job = CoroutineScope(Dispatchers.IO).launch {
                        var response = cookie_data.okHttpClient.newCall(request).execute()
                        var responseinfo=response.body?.string().toString()
                        //Log.d("GSON", "msg:${responseinfo}")
                        var json= Gson().fromJson(responseinfo, ShowStackingControlListHeader::class.java)
                        when(json.status){
                            0->{
                                cookie_data.status=json.status
                                var data: ArrayList<StackingControlListHeader> =json.data
                                comboboxData.removeAll(comboboxData)
                                for(i in 0 until data.size){
                                    comboboxData.add(data[i].code)
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
                        //Log.d("GSON", "msg:${responseinfo}")
                        var json= Gson().fromJson(responseinfo, ShowBookingNoticeHeader::class.java)
                        when(json.status){
                            0->{
                                cookie_data.status=json.status
                                var data: ArrayList<BookingNoticeHeader> =json.data
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
            is DMCILHeader ->{
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
                    .url(cookie_data.URL+"/dmcil_management")
                    .header("User-Agent", "ERP_MOBILE")
                    .post(body)
                    .build()
                runBlocking {
                    var job = CoroutineScope(Dispatchers.IO).launch {
                        var response = cookie_data.okHttpClient.newCall(request).execute()
                        var responseinfo=response.body?.string().toString()
                        //Log.d("GSON", "msg:${responseinfo}")
                        var json= Gson().fromJson(responseinfo, ShowDMCILHeader::class.java)
                        when(json.status){
                            0->{
                                cookie_data.status=json.status
                                var data: ArrayList<DMCILHeader> =json.data
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
                        //Log.d("GSON", "msg:${responseinfo}")
                        var json= Gson().fromJson(responseinfo, ShowProductControlOrderHeader::class.java)
                        when(json.status){
                            0->{
                                cookie_data.status=json.status
                                var data: ArrayList<ProductControlOrderHeader> =json.data
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
            is MealOrderListHeader ->{
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
                        var json= Gson().fromJson(responseinfo, ShowMealOrderListHeader::class.java)
                        when(json.status){
                            0->{
                                cookie_data.status=json.status
                                var data: ArrayList<MealOrderListHeader> =json.data
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
                        //Log.d("GSON", "msg:${responseinfo}")
                        var json= Gson().fromJson(responseinfo, ShowProductControlOrderBody_A::class.java)
                        when(json.status){
                            0->{
                                cookie_data.status=json.status
                                var data: ArrayList<ProductControlOrderBody_A> =json.data
                                comboboxData.removeAll(comboboxData)
                                for(i in 0 until data.size){
                                    comboboxData.add(data[i].prod_ctrl_order_number)
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
                        //Log.d("GSON", "msg:${responseinfo}")
                        var json= Gson().fromJson(responseinfo, ShowStockTransOrderHeader::class.java)
                        when(json.status){
                            0->{
                                cookie_data.status=json.status
                                var data: ArrayList<StockTransOrderHeader> =json.data
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
            .url(cookie_data.URL+"/staff_management")
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
                    .url(cookie_data.URL+"/staff_management")
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
                        var responseinfo=response.body?.string().toString()
                        var json= Gson().fromJson(responseinfo, ShowCustomBasicInfo::class.java)
                        when(json.status){
                            0->{
                                cookie_data.status=json.status
                                var data: java.util.ArrayList<CustomBasicInfo> =json.data
                                cookie_data.customer_id_ComboboxData.removeAll(cookie_data.customer_id_ComboboxData)
                                for(i in 0 until data.size){
                                    cookie_data.customer_id_ComboboxData.add(data[i]._id)
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
            is ContNo ->{
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
                                cookie_data.status=json.status
                                var data: java.util.ArrayList<ContNo> =json.data
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
                                cookie_data.msg=fail.msg
                                cookie_data.status=fail.status
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
                        var responseinfo=response.body?.string().toString()
                        var json= Gson().fromJson(responseinfo, ShowProductBasicInfo::class.java)
                        when(json.status){
                            0->{
                                cookie_data.status=json.status
                                var data: java.util.ArrayList<ProductBasicInfo> =json.data
                                cookie_data.product_id_ComboboxData.removeAll(cookie_data.product_id_ComboboxData)
                                for(i in 0 until data.size){
                                    cookie_data.product_id_ComboboxData.add(data[i]._id)
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
            is ContType ->{
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
                                cookie_data.status=json.status
                                var data: java.util.ArrayList<ContType> =json.data
                                cookie_data.cont_type_code_ComboboxData.removeAll(cookie_data.cont_type_code_ComboboxData)
                                for(i in 0 until data.size){
                                    cookie_data.cont_type_code_ComboboxData.add(data[i].cont_type_code)
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
            is Port ->{
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
                                cookie_data.status=json.status
                                var data: java.util.ArrayList<Port> =json.data
                                cookie_data.port_id_ComboboxData.removeAll(cookie_data.port_id_ComboboxData)
                                for(i in 0 until data.size){
                                    cookie_data.port_id_ComboboxData.add(data[i].port_id)
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
            is StoreArea ->{
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
                                cookie_data.status=json.status
                                var data: java.util.ArrayList<StoreArea> =json.data
                                cookie_data.store_area_ComboboxData.removeAll(cookie_data.store_area_ComboboxData)
                                for(i in 0 until data.size){
                                    cookie_data.store_area_ComboboxData.add(data[i].store_area)
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
            is StoreLocal ->{
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
                                cookie_data.status=json.status
                                var data: java.util.ArrayList<StoreLocal> =json.data
                                cookie_data.store_local_ComboboxData.removeAll(cookie_data.store_local_ComboboxData)
                                for(i in 0 until data.size){
                                    cookie_data.store_local_ComboboxData.add(data[i].store_local)
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
            is ProdType ->{
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
                                cookie_data.status=json.status
                                var data: java.util.ArrayList<ProdType> =json.data
                                cookie_data.product_type_id_ComboboxData.removeAll(cookie_data.product_type_id_ComboboxData)
                                for(i in 0 until data.size){
                                    cookie_data.product_type_id_ComboboxData.add(data[i].product_type_id)
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
            is MasterScheduledOrderHeader ->{
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
                                cookie_data.status=json.status
                                var data: java.util.ArrayList<MasterScheduledOrderHeader> =json.data
                                cookie_data.MasterScheduledOrderHeader_id_ComboboxData.removeAll(cookie_data.MasterScheduledOrderHeader_id_ComboboxData)
                                for(i in 0 until data.size){
                                    cookie_data.MasterScheduledOrderHeader_id_ComboboxData.add(data[i]._id)
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
            is ShippingCompanyBasicInfo ->{
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
                        var responseinfo=response.body?.string().toString()
                        var json= Gson().fromJson(responseinfo, ShowShippingCompanyBasicInfo::class.java)
                        when(json.status){
                            0->{
                                cookie_data.status=json.status
                                var data: java.util.ArrayList<ShippingCompanyBasicInfo> =json.data
                                cookie_data.shipping_number_ComboboxData.removeAll(cookie_data.shipping_number_ComboboxData)
                                for(i in 0 until data.size){
                                    cookie_data.shipping_number_ComboboxData.add(data[i].shipping_number)
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
                    .add("operation", "CustomerOrder")
                    .add("target","header")
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
                                var data: java.util.ArrayList<CustomerOrderHeader> =json.data
                                cookie_data.CustomerOrderHeader_poNo_ComboboxData.removeAll(cookie_data.CustomerOrderHeader_poNo_ComboboxData)
                                for(i in 0 until data.size){
                                    cookie_data.CustomerOrderHeader_poNo_ComboboxData.add(data[i].poNo)
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
            is Department ->{
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
                                cookie_data.status=json.status
                                var data: java.util.ArrayList<Department> =json.data
                                cookie_data.dept_name_ComboboxData.removeAll(cookie_data.dept_name_ComboboxData)
                                for(i in 0 until data.size){
                                    cookie_data.dept_name_ComboboxData.add(data[i].dept_name)
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
            is ItemBasicInfo ->{
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
                        var responseinfo=response.body?.string().toString()
                        var json= Gson().fromJson(responseinfo, ShowItemBasicInfo::class.java)
                        when(json.status){
                            0->{
                                cookie_data.status=json.status
                                var data: java.util.ArrayList<ItemBasicInfo> =json.data
                                cookie_data.item_id_ComboboxData.removeAll(cookie_data.item_id_ComboboxData)
                                cookie_data.semi_finished_product_number_ComboboxData.removeAll(cookie_data.semi_finished_product_number_ComboboxData)
                                for(i in 0 until data.size){
                                    cookie_data.item_id_ComboboxData.add(data[i]._id)
                                    cookie_data.semi_finished_product_number_ComboboxData.add(data[i].semi_finished_product_number)
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
            is MeHeader ->{
                val body = FormBody.Builder()
                    .add("card_number",cookie_data.card_number)
                    .add("username", cookie_data.username)
                    .add("operation", "Me")
                    .add("target","header")
                    .add("action",cookie_data.Actions.VIEW)
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
                                cookie_data.status=json.status
                                var data: java.util.ArrayList<MeHeader> =json.data
                                cookie_data.MeHeader_id_ComboboxData.removeAll(cookie_data.MeHeader_id_ComboboxData)
                                for(i in 0 until data.size){
                                    cookie_data.MeHeader_id_ComboboxData.add(data[i]._id)
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
            is PLineBasicInfo ->{
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
                        var responseinfo=response.body?.string().toString()
                        var json= Gson().fromJson(responseinfo, ShowPLineBasicInfo::class.java)
                        when(json.status){
                            0->{
                                cookie_data.status=json.status
                                var data: java.util.ArrayList<PLineBasicInfo> =json.data
                                cookie_data.pline_name_ComboboxData.removeAll(cookie_data.pline_name_ComboboxData)
                                cookie_data.pline_id_ComboboxData.removeAll(cookie_data.pline_id_ComboboxData)
                                for(i in 0 until data.size){
                                    cookie_data.pline_name_ComboboxData.add(data[i].name)
                                    cookie_data.pline_id_ComboboxData.add(data[i]._id)
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
            is staffBasicInfo ->{
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
                                cookie_data.status=json.status
                                var data: java.util.ArrayList<staffBasicInfo> =json.data
                                cookie_data.card_number_ComboboxData.removeAll(cookie_data.card_number_ComboboxData)
                                cookie_data.name_ComboboxData.removeAll(cookie_data.name_ComboboxData)
                                for(i in 0 until data.size){
                                    cookie_data.card_number_ComboboxData.add(data[i].card_number)
                                    cookie_data.name_ComboboxData.add(data[i].name)
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
            is InvChangeTypeM ->{
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
                                cookie_data.status=json.status
                                var data: java.util.ArrayList<InvChangeTypeM> =json.data
                                cookie_data.inv_code_m_ComboboxData.removeAll(cookie_data.inv_code_m_ComboboxData)
                                for(i in 0 until data.size){
                                    cookie_data.inv_code_m_ComboboxData.add(data[i].inv_code_m)
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
            is InvChangeTypeS ->{
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
                                cookie_data.status=json.status
                                var data: java.util.ArrayList<InvChangeTypeS> =json.data
                                cookie_data.inv_code_s_ComboboxData.removeAll(cookie_data.inv_code_s_ComboboxData)
                                for(i in 0 until data.size){
                                    cookie_data.inv_code_s_ComboboxData.add(data[i].inv_code_s)
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
            is ProductControlOrderHeader ->{
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
                                cookie_data.status=json.status
                                var data: java.util.ArrayList<ProductControlOrderHeader> =json.data
                                cookie_data.ProductControlOrderHeader_id_ComboboxData.removeAll(cookie_data.ProductControlOrderHeader_id_ComboboxData)
                                for(i in 0 until data.size){
                                    cookie_data.ProductControlOrderHeader_id_ComboboxData.add(data[i]._id)
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
            is MeWorkstationBasicInfo ->{
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
                        var responseinfo=response.body?.string().toString()
                        var json= Gson().fromJson(responseinfo, ShowMeWorkstationBasicInfo::class.java)
                        when(json.status){
                            0->{
                                cookie_data.status=json.status
                                var data: java.util.ArrayList<MeWorkstationBasicInfo> =json.data
                                cookie_data.workstation_number_ComboboxData.removeAll(cookie_data.workstation_number_ComboboxData)

                                for(i in 0 until data.size){
                                    cookie_data.workstation_number_ComboboxData.add(data[i].workstation_number)

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
            is MeWorkstationEquipmentBasicInfo ->{
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
                        var responseinfo=response.body?.string().toString()
                        var json= Gson().fromJson(responseinfo, ShowMeWorkstationEquipmentBasicInfo::class.java)
                        when(json.status){
                            0->{
                                cookie_data.status=json.status
                                var data: java.util.ArrayList<MeWorkstationEquipmentBasicInfo> =json.data
                                cookie_data.device_id_ComboboxData.removeAll(cookie_data.device_id_ComboboxData)

                                for(i in 0 until data.size){
                                    cookie_data.device_id_ComboboxData.add(data[i].device_id)

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
            is VenderBasicInfo ->{
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
                        var responseinfo=response.body?.string().toString()
                        var json= Gson().fromJson(responseinfo, ShowVenderBasicInfo::class.java)
                        when(json.status){
                            0->{
                                cookie_data.status=json.status
                                var data: java.util.ArrayList<VenderBasicInfo> =json.data
                                cookie_data.vender_id_ComboboxData.removeAll(cookie_data.vender_id_ComboboxData)

                                for(i in 0 until data.size){
                                    cookie_data.vender_id_ComboboxData.add(data[i]._id)

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
            is EquipmentMaintenanceType ->{
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
                                cookie_data.status=json.status
                                var data: java.util.ArrayList<EquipmentMaintenanceType> =json.data
                                cookie_data.maintain_type_ComboboxData.removeAll(cookie_data.maintain_type_ComboboxData)
                                for(i in 0 until data.size){
                                    cookie_data.maintain_type_ComboboxData.add(data[i].maintain_type)
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
}