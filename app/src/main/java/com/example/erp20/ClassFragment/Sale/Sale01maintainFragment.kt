package com.example.erp20.ClassFragment.Sale


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


class Sale01maintainFragment : Fragment() {

    //private  var recyclerView:RecyclerView ?=null
    private lateinit var ProdType_adapter:RecyclerItemProdtypeAdapter
    private lateinit var ContNo_adapter:RecyclerItemContNoAdapter
    private lateinit var ContType_adapter:RecyclerItemContTypeAdapter
    private lateinit var Port_adapter:RecyclerItemPortAdapter
    private lateinit var ProductBasicInfo_adapter:RecyclerItemProductBasicInfoAdapter
    private lateinit var CustomBasicInfo_adapter:RecyclerItemCustomBasicInfoAdapter
    private lateinit var CarCBasicInfo_adapter:RecyclerItemCarCBasicInfoAdapter
    private lateinit var ShippingCompanyBasicInfo_adapter:RecyclerItemShippingCompanyBasicInfoAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sale01maintain, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
    override fun onResume() {
        super.onResume()
        val theTextView = getView()?.findViewById<TextView>(R.id.sale01_maintain_text)

        val recyclerView= getView()?.findViewById<RecyclerView>(R.id.recyclerView)

        //combobox選單內容
        val autoCompleteTextView=getView()?.findViewById<AutoCompleteTextView>(R.id.autoCompleteText)
        val combobox=resources.getStringArray(R.array.comboboxSale01maintain)
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
                "產品型號"->{
                    showDef("ProdType","all","False")//type=combobox or all
                    when(cookie_data.status)
                    {
                        0->{
                            Toast.makeText(activity, "資料載入", Toast.LENGTH_SHORT).show()
                            recyclerView?.layoutManager=LinearLayoutManager(context)//設定Linear格式
                            ProdType_adapter=RecyclerItemProdtypeAdapter()
                              recyclerView?.adapter=ProdType_adapter//找對應itemAdapter
                              addbtn?.isEnabled=true
                        }
                        1->{
                            Toast.makeText(activity, cookie_data.msg, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                "櫃編維護"->{
                    showDef("ContNo","all","False")//type=combobox or all
                    when(cookie_data.status)
                    {
                        0->{
                            Toast.makeText(activity, "資料載入", Toast.LENGTH_SHORT).show()
                            recyclerView?.layoutManager=LinearLayoutManager(context)//設定Linear格式
                            ContNo_adapter=RecyclerItemContNoAdapter()
                            recyclerView?.adapter=ContNo_adapter//找對應itemAdapter
                            addbtn?.isEnabled=true
                        }
                        1->{
                            Toast.makeText(activity, cookie_data.msg, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                "櫃型維護"->{
                    showDef("ContType","all","False")//type=combobox or all
                    when(cookie_data.status)
                    {
                        0->{
                            Toast.makeText(activity, "資料載入", Toast.LENGTH_SHORT).show()
                            recyclerView?.layoutManager=LinearLayoutManager(context)//設定Linear格式
                            ContType_adapter=RecyclerItemContTypeAdapter()
                            recyclerView?.adapter=ContType_adapter//找對應itemAdapter
                            addbtn?.isEnabled=true
                        }
                        1->{
                            Toast.makeText(activity, cookie_data.msg, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                "港口維護"->{
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
                            Toast.makeText(activity, cookie_data.msg, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                "產品基本資料維護"->{
                    showBasic("ProductBasicInfo","all","False")//type=combobox or all
                    when(cookie_data.status)
                    {
                        0->{
                            Toast.makeText(activity, "資料載入", Toast.LENGTH_SHORT).show()
                            recyclerView?.layoutManager=LinearLayoutManager(context)//設定Linear格式
                            ProductBasicInfo_adapter= RecyclerItemProductBasicInfoAdapter()
                            recyclerView?.adapter=ProductBasicInfo_adapter//找對應itemAdapter
                            addbtn?.isEnabled=true
                        }
                        1->{
                            Toast.makeText(activity, cookie_data.msg, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                "客戶基本資料維護"->{
                    showBasic("CustomBasicInfo","all","False")//type=combobox or all
                    when(cookie_data.status)
                    {
                        0->{
                            Toast.makeText(activity, "資料載入", Toast.LENGTH_SHORT).show()
                            recyclerView?.layoutManager=LinearLayoutManager(context)//設定Linear格式
                            CustomBasicInfo_adapter= RecyclerItemCustomBasicInfoAdapter()
                            recyclerView?.adapter=CustomBasicInfo_adapter//找對應itemAdapter
                            addbtn?.isEnabled=true
                        }
                        1->{
                            Toast.makeText(activity, cookie_data.msg, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                "車行基本資料維護"->{
                    showBasic("CarCBasicInfo","all","False")//type=combobox or all
                    when(cookie_data.status)
                    {
                        0->{
                            Toast.makeText(activity, "資料載入", Toast.LENGTH_SHORT).show()
                            recyclerView?.layoutManager=LinearLayoutManager(context)//設定Linear格式
                            CarCBasicInfo_adapter= RecyclerItemCarCBasicInfoAdapter()
                            recyclerView?.adapter=CarCBasicInfo_adapter//找對應itemAdapter
                            addbtn?.isEnabled=true
                        }
                        1->{
                            Toast.makeText(activity, cookie_data.msg, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                "船公司基本資料維護"->{
                    showBasic("ShippingCompanyBasicInfo","all","False")//type=combobox or all
                    when(cookie_data.status)
                    {
                        0->{
                            Toast.makeText(activity, "資料載入", Toast.LENGTH_SHORT).show()
                            recyclerView?.layoutManager=LinearLayoutManager(context)//設定Linear格式
                            ShippingCompanyBasicInfo_adapter= RecyclerItemShippingCompanyBasicInfoAdapter()
                            recyclerView?.adapter=ShippingCompanyBasicInfo_adapter//找對應itemAdapter
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
                "產品型號"->{
                    val item = LayoutInflater.from(activity).inflate(R.layout.recycler_item_prodtype, null)
                    val mAlertDialog = AlertDialog.Builder(requireView().context)
                    //mAlertDialog.setIcon(R.mipmap.ic_launcher_round) //set alertdialog icon
                    mAlertDialog.setTitle("新增") //set alertdialog title
                    //mAlertDialog.setMessage("確定要登出?") //set alertdialog message
                    mAlertDialog.setView(item)
                    mAlertDialog.setPositiveButton("取消") { dialog, id ->
                        dialog.dismiss()
                    }
                    mAlertDialog.setNegativeButton("確定") { dialog, id ->
                        val name=item.findViewById<TextInputEditText>(R.id.Edit_name)
                        val id=item.findViewById<TextInputEditText>(R.id.Edit_id)
                        val remark=item.findViewById<TextInputEditText>(R.id.edit_remark)
                        val addData=ProdType("0","0")
                        addData.product_type_name=name.text.toString()
                        addData.product_type_id=id.text.toString()
                        addData.remark=remark.text.toString()
                        addData.creator=cookie_data.username
                        addData.create_time=Calendar.getInstance().getTime().toString()
                        addData.editor=cookie_data.username
                        addData.edit_time=Calendar.getInstance().getTime().toString()
                        addDef("ProdType",addData)
                        when(cookie_data.status){
                            0-> {//成功
                                ProdType_adapter.addItem(addData)
                                Toast.makeText(activity, cookie_data.msg, Toast.LENGTH_SHORT).show()
                            }
                            1->{//失敗
                                Toast.makeText(activity, cookie_data.msg, Toast.LENGTH_SHORT).show()
                            }
                        }
                        //Log.d("GSON", name?.text.toString())
                        //Log.d("GSON", id?.text.toString())
                    }
                    mAlertDialog.show()
                }
                "櫃編維護"->{
                    val item = LayoutInflater.from(activity).inflate(R.layout.recycler_item_contno, null)
                    val mAlertDialog = AlertDialog.Builder(requireView().context)
                    //mAlertDialog.setIcon(R.mipmap.ic_launcher_round) //set alertdialog icon
                    mAlertDialog.setTitle("新增") //set alertdialog title
                    //mAlertDialog.setMessage("確定要登出?") //set alertdialog message
                    mAlertDialog.setView(item)
                    mAlertDialog.setPositiveButton("取消") { dialog, id ->
                        dialog.dismiss()
                    }
                    mAlertDialog.setNegativeButton("確定") { dialog, id ->
                        val cont_code =item.findViewById<TextInputEditText>(R.id.edit_cont_code)
                        val customer_code=item.findViewById<TextInputEditText>(R.id.edit_customer_code)
                        val age=item.findViewById<TextInputEditText>(R.id.edit_age)
                        val serial_number=item.findViewById<TextInputEditText>(R.id.edit_serial_number)
                            serial_number.inputType= InputType.TYPE_CLASS_NUMBER
                        val cont_code_name=item.findViewById<TextInputEditText>(R.id.edit_cont_code_name)
                        val remark=item.findViewById<TextInputEditText>(R.id.edit_remark)
                        //新增不能為空
                        if( cont_code.text.toString().trim().isEmpty()     ||
                            customer_code.text.toString().trim().isEmpty() ||
                            age.text.toString().trim().isEmpty()           ||
                            serial_number.text.toString().trim().isEmpty() ||
                            cont_code_name.text.toString().trim().isEmpty()   ){
                            Toast.makeText(requireView().context,"Input required",Toast.LENGTH_LONG).show()
                        }
                        else{
                            val addData=ContNo("","","",0,"")
                            addData.cont_code=cont_code.text.toString()
                            addData.customer_code=customer_code.text.toString()
                            addData.age=age.text.toString()
                            addData.serial_number=serial_number.text.toString().toInt()
                            addData.cont_code_name=cont_code_name.text.toString()
                            addData.remark=remark.text.toString()
                            addData.creator=cookie_data.username
                            addData.create_time=Calendar.getInstance().getTime().toString()
                            addData.editor=cookie_data.username
                            addData.edit_time=Calendar.getInstance().getTime().toString()
                            addDef("ContNo",addData)
                            when(cookie_data.status){
                                0-> {//成功
                                    ContNo_adapter.addItem(addData)
                                    Toast.makeText(activity, cookie_data.msg, Toast.LENGTH_SHORT).show()
                                }
                                1->{//失敗
                                    Toast.makeText(activity, cookie_data.msg, Toast.LENGTH_SHORT).show()
                                }
                            }
                            //Log.d("GSON", name?.text.toString())
                            //Log.d("GSON", id?.text.toString())
                        }

                    }
                    mAlertDialog.show()
                }
                "櫃型維護"->{
                    val item = LayoutInflater.from(activity).inflate(R.layout.recycler_item_cont_type, null)
                    val mAlertDialog = AlertDialog.Builder(requireView().context)
                    //mAlertDialog.setIcon(R.mipmap.ic_launcher_round) //set alertdialog icon
                    mAlertDialog.setTitle("新增") //set alertdialog title
                    //mAlertDialog.setMessage("確定要登出?") //set alertdialog message
                    mAlertDialog.setView(item)
                    mAlertDialog.setPositiveButton("取消") { dialog, id ->
                        dialog.dismiss()
                    }
                    mAlertDialog.setNegativeButton("確定") { dialog, id ->
                        val cont_type_code =item.findViewById<TextInputEditText>(R.id.edit_cont_type_code)
                        val cont_type=item.findViewById<TextInputEditText>(R.id.edit_cont_type)
                        val order=item.findViewById<TextInputEditText>(R.id.edit_order)
                        val remark=item.findViewById<TextInputEditText>(R.id.edit_remark)
                        //新增不能為空
                        if( cont_type_code.text.toString().trim().isEmpty()   ||
                            cont_type.text.toString().trim().isEmpty() ||
                            order.text.toString().trim().isEmpty()
                                                                        ){
                            Toast.makeText(requireView().context,"Input required",Toast.LENGTH_LONG).show()
                        }
                        else{
                            val addData=ContType("","","")
                            addData.cont_type_code=cont_type_code.text.toString()
                            addData.cont_type=cont_type.text.toString()
                            addData.order=order.text.toString()
                            addData.remark=remark.text.toString()
                            addData.creator=cookie_data.username
                            addData.create_time=Calendar.getInstance().getTime().toString()
                            addData.editor=cookie_data.username
                            addData.edit_time=Calendar.getInstance().getTime().toString()
                            addDef("ContType",addData)
                            when(cookie_data.status){
                                0-> {//成功
                                    ContType_adapter.addItem(addData)
                                    Toast.makeText(activity, cookie_data.msg, Toast.LENGTH_SHORT).show()
                                }
                                1->{//失敗
                                    Toast.makeText(activity, cookie_data.msg, Toast.LENGTH_SHORT).show()
                                }
                            }
                            //Log.d("GSON", name?.text.toString())
                            //Log.d("GSON", id?.text.toString())
                        }

                    }
                    mAlertDialog.show()
                }
                "港口維護"->{
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
                }
                "產品基本資料維護"->{
                    val item = LayoutInflater.from(activity).inflate(R.layout.recycler_item_product_basic_info, null)
                    val mAlertDialog = AlertDialog.Builder(requireView().context)
                    //mAlertDialog.setIcon(R.mipmap.ic_launcher_round) //set alertdialog icon
                    mAlertDialog.setTitle("新增") //set alertdialog title
                    //mAlertDialog.setMessage("確定要登出?") //set alertdialog message
                    mAlertDialog.setView(item)

                    //true false combobbox
                    val combobox_yes_no=resources.getStringArray(R.array.combobox_yes_no)
                    val arrayAdapter_yes_no= ArrayAdapter(requireContext(),R.layout.combobox_item,combobox_yes_no)

                    val _id =item.findViewById<TextInputEditText>(R.id.edit_id)
                    val product_name=item.findViewById<TextInputEditText>(R.id.edit_product_name)
                    val product_type =item.findViewById<TextInputEditText>(R.id.edit_product_type)
                    val is_new=item.findViewById<AutoCompleteTextView>(R.id.edit_is_new)
                    is_new.setAdapter(arrayAdapter_yes_no)
                    val release_date =item.findViewById<TextInputEditText>(R.id.edit_release_date)
                    val is_inventory=item.findViewById<AutoCompleteTextView>(R.id.edit_is_inventory)
                    is_inventory.setAdapter(arrayAdapter_yes_no)
                    val discontinued_date =item.findViewById<TextInputEditText>(R.id.edit_discontinued_date)
                    val is_discontinue=item.findViewById<AutoCompleteTextView>(R.id.edit_is_discontinue)
                    is_discontinue.setAdapter(arrayAdapter_yes_no)
                    val inventory_date =item.findViewById<TextInputEditText>(R.id.edit_inventory_date)
                    val remark=item.findViewById<TextInputEditText>(R.id.edit_remark)

                    mAlertDialog.setPositiveButton("取消") { dialog, id ->
                        dialog.dismiss()
                    }
                    mAlertDialog.setNegativeButton("確定") { dialog, id ->
                        //新增不能為空
                        if( _id.text.toString().trim().isEmpty() ||
                            product_name.text.toString().trim().isEmpty()  ||
                            product_type.text.toString().trim().isEmpty() ||
                            is_new.text.toString().trim().isEmpty()  ||
                            release_date.text.toString().trim().isEmpty() ||
                            is_discontinue.text.toString().trim().isEmpty()  ||
                            discontinued_date.text.toString().trim().isEmpty() ||
                            is_inventory.text.toString().trim().isEmpty()  ||
                            inventory_date.text.toString().trim().isEmpty()  ){
                            Toast.makeText(requireView().context,"Input required",Toast.LENGTH_LONG).show()
                        }
                        else{
                            val addData=ProductBasicInfo("","","",false,"",false,"",false,"")
                            addData._id=_id.text.toString()
                            addData.product_name=product_name.text.toString()
                            addData.product_type=product_type.text.toString()
                            addData.is_new=is_new.text.toString().toBoolean()
                            addData.release_date=release_date.text.toString()
                            addData.is_discontinue=is_discontinue.text.toString().toBoolean()
                            addData.discontinued_date=discontinued_date.text.toString()
                            addData.is_inventory=is_inventory.text.toString().toBoolean()
                            addData.inventory_date=inventory_date.text.toString()

                            addData.remark=remark.text.toString()
                            addData.creator=cookie_data.username
                            addData.create_time=Calendar.getInstance().getTime().toString()
                            addData.editor=cookie_data.username
                            addData.edit_time=Calendar.getInstance().getTime().toString()
                            addBasic("ProductBasicInfo",addData)
                            when(cookie_data.status){
                                0-> {//成功

                                    ProductBasicInfo_adapter.addItem(addData)
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
                "客戶基本資料維護"->{
                    val item = LayoutInflater.from(activity).inflate(R.layout.recycler_item_custom_basic_info, null)
                    val mAlertDialog = AlertDialog.Builder(requireView().context)
                    //mAlertDialog.setIcon(R.mipmap.ic_launcher_round) //set alertdialog icon
                    mAlertDialog.setTitle("新增") //set alertdialog title
                    //mAlertDialog.setMessage("確定要登出?") //set alertdialog message
                    mAlertDialog.setView(item)

                    val _id =item.findViewById<TextInputEditText>(R.id.edit_id)
                    val abbreviation=item.findViewById<TextInputEditText>(R.id.edit_abbreviation)
                    val full_name =item.findViewById<TextInputEditText>(R.id.edit_full_name)
                    val code =item.findViewById<TextInputEditText>(R.id.edit_code)


                    val remark=item.findViewById<TextInputEditText>(R.id.edit_remark)

                    mAlertDialog.setPositiveButton("取消") { dialog, id ->
                        dialog.dismiss()
                    }
                    mAlertDialog.setNegativeButton("確定") { dialog, id ->
                        //新增不能為空
                        if( _id.text.toString().trim().isEmpty() ||
                            abbreviation.text.toString().trim().isEmpty()  ||
                            full_name.text.toString().trim().isEmpty() ||
                            code.text.toString().trim().isEmpty()    ){
                            Toast.makeText(requireView().context,"Input required",Toast.LENGTH_LONG).show()
                        }
                        else{
                            val addData=CustomBasicInfo("","","","")
                            addData._id=_id.text.toString()
                            addData.abbreviation=abbreviation.text.toString()
                            addData.full_name=full_name.text.toString()
                            addData.code=code.text.toString()


                            addData.remark=remark.text.toString()
                            addData.creator=cookie_data.username
                            addData.create_time=Calendar.getInstance().getTime().toString()
                            addData.editor=cookie_data.username
                            addData.edit_time=Calendar.getInstance().getTime().toString()
                            addBasic("CustomBasicInfo",addData)
                            when(cookie_data.status){
                                0-> {//成功

                                    CustomBasicInfo_adapter.addItem(addData)
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
                "車行基本資料維護"->{
                    val item = LayoutInflater.from(activity).inflate(R.layout.recycler_item_car_c_basic_info, null)
                    val mAlertDialog = AlertDialog.Builder(requireView().context)
                    //mAlertDialog.setIcon(R.mipmap.ic_launcher_round) //set alertdialog icon
                    mAlertDialog.setTitle("新增") //set alertdialog title
                    //mAlertDialog.setMessage("確定要登出?") //set alertdialog message
                    mAlertDialog.setView(item)

                    val _id =item.findViewById<TextInputEditText>(R.id.edit_id)
                    val abbreviation=item.findViewById<TextInputEditText>(R.id.edit_abbreviation)



                    val remark=item.findViewById<TextInputEditText>(R.id.edit_remark)

                    mAlertDialog.setPositiveButton("取消") { dialog, id ->
                        dialog.dismiss()
                    }
                    mAlertDialog.setNegativeButton("確定") { dialog, id ->
                        //新增不能為空
                        if( _id.text.toString().trim().isEmpty() ||
                            abbreviation.text.toString().trim().isEmpty()    ){
                            Toast.makeText(requireView().context,"Input required",Toast.LENGTH_LONG).show()
                        }
                        else{
                            val addData=CarCBasicInfo("","")
                            addData._id=_id.text.toString()
                            addData.abbreviation=abbreviation.text.toString()



                            addData.remark=remark.text.toString()
                            addData.creator=cookie_data.username
                            addData.create_time=Calendar.getInstance().getTime().toString()
                            addData.editor=cookie_data.username
                            addData.edit_time=Calendar.getInstance().getTime().toString()
                            addBasic("CarCBasicInfo",addData)
                            when(cookie_data.status){
                                0-> {//成功

                                    CarCBasicInfo_adapter.addItem(addData)
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
                "船公司基本資料維護"->{
                    val item = LayoutInflater.from(activity).inflate(R.layout.recycler_item_shipping_company_basic_info, null)
                    val mAlertDialog = AlertDialog.Builder(requireView().context)
                    //mAlertDialog.setIcon(R.mipmap.ic_launcher_round) //set alertdialog icon
                    mAlertDialog.setTitle("新增") //set alertdialog title
                    //mAlertDialog.setMessage("確定要登出?") //set alertdialog message
                    mAlertDialog.setView(item)

                    val shipping_number =item.findViewById<TextInputEditText>(R.id.edit_shipping_number)
                    val shipping_name=item.findViewById<TextInputEditText>(R.id.edit_shipping_name)



                    val remark=item.findViewById<TextInputEditText>(R.id.edit_remark)

                    mAlertDialog.setPositiveButton("取消") { dialog, id ->
                        dialog.dismiss()
                    }
                    mAlertDialog.setNegativeButton("確定") { dialog, id ->
                        //新增不能為空
                        if( shipping_number.text.toString().trim().isEmpty() ||
                            shipping_name.text.toString().trim().isEmpty()    ){
                            Toast.makeText(requireView().context,"Input required",Toast.LENGTH_LONG).show()
                        }
                        else{
                            val addData=ShippingCompanyBasicInfo("","")
                            addData.shipping_number=shipping_number.text.toString()
                            addData.shipping_name=shipping_name.text.toString()


                            addData.remark=remark.text.toString()
                            addData.creator=cookie_data.username
                            addData.create_time=Calendar.getInstance().getTime().toString()
                            addData.editor=cookie_data.username
                            addData.edit_time=Calendar.getInstance().getTime().toString()
                            addBasic("ShippingCompanyBasicInfo",addData)
                            when(cookie_data.status){
                                0-> {//成功

                                    ShippingCompanyBasicInfo_adapter.addItem(addData)
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
                    .add("action",cookie_data.Actions.ADD)
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
                    .add("action",cookie_data.Actions.ADD)
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
                    .add("action",cookie_data.Actions.ADD)
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
                    .add("action",cookie_data.Actions.ADD)
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
            is ProductBasicInfo->{
                val add = JSONObject()
                add.put("_id",addData._id)
                add.put("product_name",addData.product_name)
                add.put("product_type",addData.product_type)
                add.put("is_new",addData.is_new)
                add.put("release_date",addData.release_date)
                add.put("is_discontinue",addData.is_discontinue)
                add.put("discontinued_date",addData.discontinued_date)
                add.put("is_inventory",addData.is_inventory)
                add.put("inventory_date",addData.inventory_date)
                add.put("remark",addData.remark)
                add.put("creator",cookie_data.username)
                add.put("editor",cookie_data.username)
                Log.d("GSON", "msg:${add}")
                val body = FormBody.Builder()
                    .add("card_number", cookie_data.card_number)
                    .add("username", cookie_data.username)
                    .add("operation", operation)
                    .add("action",cookie_data.Actions.ADD)
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
            is CustomBasicInfo->{
                val add = JSONObject()
                add.put("_id",addData._id)
                add.put("abbreviation",addData.abbreviation)
                add.put("full_name",addData.full_name)
                add.put("code",addData.code)
                add.put("remark",addData.remark)
                add.put("creator",cookie_data.username)
                add.put("editor",cookie_data.username)
                Log.d("GSON", "msg:${add}")
                val body = FormBody.Builder()
                    .add("card_number", cookie_data.card_number)
                    .add("username", cookie_data.username)
                    .add("operation", operation)
                    .add("action",cookie_data.Actions.ADD)
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
            is CarCBasicInfo->{
                val add = JSONObject()
                add.put("_id",addData._id)
                add.put("abbreviation",addData.abbreviation)
                add.put("remark",addData.remark)
                add.put("creator",cookie_data.username)
                add.put("editor",cookie_data.username)
                Log.d("GSON", "msg:${add}")
                val body = FormBody.Builder()
                    .add("card_number", cookie_data.card_number)
                    .add("username", cookie_data.username)
                    .add("operation", operation)
                    .add("action",cookie_data.Actions.ADD)
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
            is ShippingCompanyBasicInfo->{
                val add = JSONObject()
                add.put("shipping_number",addData.shipping_number)
                add.put("shipping_name",addData.shipping_name)
                add.put("remark",addData.remark)
                add.put("creator",cookie_data.username)
                add.put("editor",cookie_data.username)
                Log.d("GSON", "msg:${add}")
                val body = FormBody.Builder()
                    .add("card_number", cookie_data.card_number)
                    .add("username", cookie_data.username)
                    .add("operation", operation)
                    .add("action",cookie_data.Actions.ADD)
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


