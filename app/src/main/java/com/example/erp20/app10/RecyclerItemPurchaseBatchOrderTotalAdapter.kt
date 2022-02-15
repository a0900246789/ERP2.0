package com.example.erp20.app10
import android.app.DatePickerDialog
import android.graphics.Color
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.erp20.Model.*
import com.example.erp20.R
import com.example.erp20.cookie_data
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import kotlinx.coroutines.*
import okhttp3.FormBody
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList
import android.widget.ArrayAdapter
import androidx.constraintlayout.widget.ConstraintLayout
import java.text.SimpleDateFormat
import kotlin.properties.Delegates


class RecyclerItemPurchaseBatchOrderTotalAdapter(FilterDate:String,FilterVender:String,FilterItem:String,Filter_Prod_ctrl_order_number:String,Filter_isClose:String,VenderShipment_PoNo:String) :
    RecyclerView.Adapter<RecyclerItemPurchaseBatchOrderTotalAdapter.ViewHolder>() {
    private var itemData: ShowPurchaseBatchOrder =Gson().fromJson(cookie_data.response_data, ShowPurchaseBatchOrder::class.java)
    private var data: ArrayList<PurchaseBatchOrder> =itemData.data
    var relativeCombobox01=cookie_data.ProductControlOrderHeader_id_ComboboxData
    val dateF = SimpleDateFormat("yyyy-MM-dd(EEEE)", Locale.TAIWAN)
    val timeF = SimpleDateFormat("HH:mm:ss", Locale.TAIWAN)
    val dateF2 = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    var D1:Date?=null
    var D2:Date?=null
    var venderShipment_PoNo:String = VenderShipment_PoNo

    init {
        // println(SelectFilter)
        data=filter(data,FilterDate,FilterVender,FilterItem,Filter_Prod_ctrl_order_number,Filter_isClose)
        data=sort(data)

    }

    fun filter(data: java.util.ArrayList<PurchaseBatchOrder>, filterDate:String, filterVender:String,filterItem:String,filter_Prod_ctrl_order_number:String,filter_isClose:String): java.util.ArrayList<PurchaseBatchOrder> {
        var ArrayList: java.util.ArrayList<PurchaseBatchOrder> =
            java.util.ArrayList<PurchaseBatchOrder>()
        if(filterDate!=""){
            for(i in 0 until data.size){
                if(data[i].v_pre_delivery_date!=null){
                    var date1=dateF2.parse(data[i].v_pre_delivery_date)
                    var date2=dateF2.parse(filterDate)
                    if(date1.compareTo(date2)>=0){
                        ArrayList.add(data[i])
                    }
                }

            }
        }
        var ArrayList2: java.util.ArrayList<PurchaseBatchOrder> =
            java.util.ArrayList<PurchaseBatchOrder>()
        if(filterItem!=""){
            for(i in 0 until ArrayList.size){
                if(cookie_data.PurchaseOrderBody_item_id_ComboboxData[cookie_data.PurchaseOrderBody_body_id_ComboboxData.indexOf(ArrayList[i].purchase_order_id)]==filterItem ){
                    ArrayList2.add(ArrayList[i])
                }
            }
        }
        else{
            for(i in 0 until ArrayList.size){
                ArrayList2.add(ArrayList[i])
            }
        }
        var ArrayList3: java.util.ArrayList<PurchaseBatchOrder> =
            java.util.ArrayList<PurchaseBatchOrder>()
        if(filterVender!=""){
            for(i in 0 until ArrayList2.size){
                if(cookie_data.vender_id_ComboboxData[
                            cookie_data.vender_abbreviation_ComboboxData.indexOf(
                                cookie_data.PurchaseOrderHeader_vender_name_ComboboxData[
                                        cookie_data.PurchaseOrderHeader_poNo_ComboboxData.indexOf(
                                            cookie_data.PurchaseOrderBody_poNo_ComboboxData[
                                                    cookie_data.PurchaseOrderBody_body_id_ComboboxData.indexOf(
                                                        ArrayList2[i].purchase_order_id)])])]==filterVender ){
                    ArrayList3.add(ArrayList2[i])
                }
            }
        }
        else{
            for(i in 0 until ArrayList2.size){
                ArrayList3.add(ArrayList2[i])
            }
        }
        var ArrayList4: java.util.ArrayList<PurchaseBatchOrder> =
            java.util.ArrayList<PurchaseBatchOrder>()
        if(filter_Prod_ctrl_order_number!=""){
            for(i in 0 until ArrayList3.size){
                if(ArrayList3[i].prod_ctrl_order_number==filter_Prod_ctrl_order_number ){
                    ArrayList4.add(ArrayList3[i])
                }
            }
        }
        else{
            for(i in 0 until ArrayList3.size){
                ArrayList4.add(ArrayList3[i])
            }
        }
        var ArrayList5: java.util.ArrayList<PurchaseBatchOrder> =
            java.util.ArrayList<PurchaseBatchOrder>()
        if(filter_isClose=="true"){
            for(i in 0 until ArrayList4.size){
                if(ArrayList4[i].is_closed==false){
                    ArrayList5.add(ArrayList4[i])
                }
            }
        }
        else{
            for(i in 0 until ArrayList4.size){
                ArrayList5.add(ArrayList4[i])
            }
        }
        return ArrayList5
    }
    fun sort(data: java.util.ArrayList<PurchaseBatchOrder>): java.util.ArrayList<PurchaseBatchOrder> {
        var sortedList:List<PurchaseBatchOrder> = data

        sortedList  = data.sortedWith(
            compareBy(
                { it.pre_delivery_date },
                { cookie_data.vender_id_ComboboxData[
                        cookie_data.vender_abbreviation_ComboboxData.indexOf(
                            cookie_data.PurchaseOrderHeader_vender_name_ComboboxData[
                                    cookie_data.PurchaseOrderHeader_poNo_ComboboxData.indexOf(
                                        cookie_data.PurchaseOrderBody_poNo_ComboboxData[
                                                cookie_data.PurchaseOrderBody_body_id_ComboboxData.indexOf(
                                                    it.purchase_order_id)])])]},
                { cookie_data.item_id_name_ComboboxData[
                        cookie_data.item_id_ComboboxData.indexOf(
                            cookie_data.PurchaseOrderBody_item_id_ComboboxData[
                                    cookie_data.PurchaseOrderBody_body_id_ComboboxData.indexOf(
                                        it.purchase_order_id)])]},
                { it.batch_id }
            )
        )

        return sortedList.toCollection(java.util.ArrayList())
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v= LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_purchase_batch_order_total,parent,false)
        return ViewHolder(v)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        //Log.d("GSON", "msg: ${itemData.data[position]}\n")
        if(data[position].pre_delivery_date!=null){
            //date
            var readDateString=data[position].pre_delivery_date
            //println(readDateString)
            val readDate=Calendar.getInstance()
            //println(readDateString.subSequence(0,4))
            readDate.set(Calendar.YEAR,readDateString!!.subSequence(0,4).toString().toInt())//yyyy
            // println(readDateString.subSequence(5,7))
            readDate.set(Calendar.MONTH,readDateString!!.subSequence(5,7).toString().toInt()-1)//MM
            // println(readDateString.subSequence(8,10))
            readDate.set(Calendar.DAY_OF_MONTH,readDateString!!.subSequence(8,10).toString().toInt())//dd
            D1=readDate.time
            var date=dateF.format(readDate.time)
            holder.pre_delivery_date.setText(date)
        }
        else{
            holder.pre_delivery_date.setText(null)
        }
        holder.pre_delivery_date.inputType=InputType.TYPE_NULL

        if(data[position].v_pre_delivery_date!=null){
            //date
            var readDateString=data[position].v_pre_delivery_date
            //println(readDateString)
            val readDate=Calendar.getInstance()
            //println(readDateString.subSequence(0,4))
            readDate.set(Calendar.YEAR,readDateString!!.subSequence(0,4).toString().toInt())//yyyy
            // println(readDateString.subSequence(5,7))
            readDate.set(Calendar.MONTH,readDateString!!.subSequence(5,7).toString().toInt()-1)//MM
            // println(readDateString.subSequence(8,10))
            readDate.set(Calendar.DAY_OF_MONTH,readDateString!!.subSequence(8,10).toString().toInt())//dd
            D2=readDate.time
            var date=dateF.format(readDate.time)
            holder.v_pre_delivery_date.setText(date)
        }
        else{
            holder.v_pre_delivery_date.setText(null)
        }
        holder.v_pre_delivery_date.inputType=InputType.TYPE_NULL

        if(data[position].pre_delivery_date==null || data[position].v_pre_delivery_date==null){
            holder.is_early.setText("false")
        }
        else{
            var DiffDay :Long=(D1!!.time-D2!!.time)/86400000
            if(DiffDay>cookie_data.limited_days_ComboboxData[0]){
                holder.is_early.setText("true")
            }
            else{
                holder.is_early.setText("false")
            }
        }
        holder.is_early.inputType=InputType.TYPE_NULL
        holder.is_urgent.setText(data[position].is_urgent.toString())
        holder.is_urgent.inputType=InputType.TYPE_NULL
        holder.is_close.setText(data[position].is_closed.toString())
        holder.is_close.inputType=InputType.TYPE_NULL

        holder.vender_id_name.setText(cookie_data.vender_id_ComboboxData[cookie_data.vender_abbreviation_ComboboxData.indexOf(cookie_data.PurchaseOrderHeader_vender_name_ComboboxData[cookie_data.PurchaseOrderHeader_poNo_ComboboxData.indexOf(cookie_data.PurchaseOrderBody_poNo_ComboboxData[cookie_data.PurchaseOrderBody_body_id_ComboboxData.indexOf(data[position].purchase_order_id)])])]+" "+
            cookie_data.PurchaseOrderHeader_vender_name_ComboboxData[cookie_data.PurchaseOrderHeader_poNo_ComboboxData.indexOf(cookie_data.PurchaseOrderBody_poNo_ComboboxData[cookie_data.PurchaseOrderBody_body_id_ComboboxData.indexOf(data[position].purchase_order_id)])])
        holder.vender_id_name.inputType=InputType.TYPE_NULL

        holder.staff_name.setText(cookie_data.name_ComboboxData[
                cookie_data.card_number_ComboboxData.indexOf(
                    cookie_data.vender_card_number_ComboboxData[
                            cookie_data.vender_abbreviation_ComboboxData.indexOf(
                                cookie_data.PurchaseOrderHeader_vender_name_ComboboxData[
                                        cookie_data.PurchaseOrderHeader_poNo_ComboboxData.indexOf(
                                            cookie_data.PurchaseOrderBody_poNo_ComboboxData[
                                                    cookie_data.PurchaseOrderBody_body_id_ComboboxData.indexOf(
                                                        data[position].purchase_order_id)])])])])
       /* holder.staff_name.setText(
                cookie_data.card_number_ComboboxData.indexOf(
                    cookie_data.vender_card_number_ComboboxData[
                            cookie_data.vender_abbreviation_ComboboxData.indexOf(
                                cookie_data.PurchaseOrderHeader_vender_name_ComboboxData[
                                        cookie_data.PurchaseOrderHeader_poNo_ComboboxData.indexOf(
                                            cookie_data.PurchaseOrderBody_poNo_ComboboxData[
                                                    cookie_data.PurchaseOrderBody_body_id_ComboboxData.indexOf(
                                                        data[position].purchase_order_id)])])]).toString())*/

        holder.staff_name.inputType=InputType.TYPE_NULL

        holder.item_id_name.setText(cookie_data.item_id_name_ComboboxData[
                cookie_data.item_id_ComboboxData.indexOf(
                    cookie_data.PurchaseOrderBody_item_id_ComboboxData[
                            cookie_data.PurchaseOrderBody_body_id_ComboboxData.indexOf(
                                                            data[position].purchase_order_id)])])

        holder.item_id_name.inputType=InputType.TYPE_NULL

        holder.count.setText(data[position].count.toString())
        holder.count.inputType=InputType.TYPE_NULL

        holder.no_delivered.setText((data[position].count-data[position].quantity_delivered).toString())
        holder.no_delivered.inputType=InputType.TYPE_NULL


        holder.v_count.setText(data[position].v_count.toString())
        holder.v_count.inputType=InputType.TYPE_NULL

        holder.quantity_delivered.setText(data[position].quantity_delivered.toString())
        holder.quantity_delivered.inputType=InputType.TYPE_NULL

        holder.purchase_order_id.setText(data[position].purchase_order_id)
        holder.purchase_order_id.inputType=InputType.TYPE_NULL
        holder.section.setText(data[position].section)
        holder.section.inputType=InputType.TYPE_NULL

        holder.prod_ctrl_order_number.setText(data[position].prod_ctrl_order_number)
        holder.prod_ctrl_order_number.inputType=InputType.TYPE_NULL



        holder.notice_matter.setText(data[position].notice_matter)
        holder.notice_matter.isClickable=false
        holder.notice_matter.isFocusable=false
        holder.notice_matter.isFocusableInTouchMode=false
        holder.notice_matter.isTextInputLayoutFocusedRectEnabled=false

        holder.next_btn.isVisible=true
        if(data[position].is_closed || venderShipment_PoNo==""){
            holder.edit_btn.isVisible=false
            holder.receive_btn.isVisible=false
        }
        else{
            holder.edit_btn.isVisible=true
            holder.receive_btn.isVisible=true
        }

        //holder.overbtn.isVisible=true
    }

    override fun getItemCount(): Int {
        return data.size//cookie_data.itemCount
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var pre_delivery_date=itemView.findViewById<TextInputEditText>(R.id.edit_pre_delivery_date)
        var v_pre_delivery_date=itemView.findViewById<TextInputEditText>(R.id.edit_v_pre_delivery_date)
        var is_early=itemView.findViewById<TextInputEditText>(R.id.edit_is_early)
        var is_urgent=itemView.findViewById<TextInputEditText>(R.id.edit_is_urgent)
        var is_close=itemView.findViewById<TextInputEditText>(R.id.edit_is_close)
        var vender_id_name=itemView.findViewById<TextInputEditText>(R.id.edit_vender_id_name)
        var staff_name=itemView.findViewById<TextInputEditText>(R.id.edit_staff_name)
        var item_id_name=itemView.findViewById<TextInputEditText>(R.id.edit_item_id_name)
        var count=itemView.findViewById<TextInputEditText>(R.id.edit_count)
        var no_delivered=itemView.findViewById<TextInputEditText>(R.id.edit_no_delivered)
        var v_count =itemView.findViewById<TextInputEditText>(R.id.edit_v_count)
        var quantity_delivered=itemView.findViewById<TextInputEditText>(R.id.edit_quantity_delivered)
        var purchase_order_id=itemView.findViewById<TextInputEditText>(R.id.edit_purchase_order_id)
        var section=itemView.findViewById<TextInputEditText>(R.id.edit_section)
        var prod_ctrl_order_number=itemView.findViewById<TextInputEditText>(R.id.edit_prod_ctrl_order_number)
        var notice_matter=itemView.findViewById<TextInputEditText>(R.id.edit_notice_matter)


        var edit_btn=itemView.findViewById<Button>(R.id.edit_btn)
        var next_btn=itemView.findViewById<Button>(R.id.next_btn)
        var receive_btn=itemView.findViewById<Button>(R.id.receive_btn)
        var overbtn=itemView.findViewById<Button>(R.id.over_btn)
        var layout=itemView.findViewById<ConstraintLayout>(R.id.constraint_layout)
        var edit=false
        var tempdate=""
        var max=0.0
        lateinit var oldData:PurchaseBatchOrder
        lateinit var newData:PurchaseBatchOrder
        init {
            itemView.setOnClickListener{
                val position:Int=adapterPosition
                Log.d("GSON", "msg: ${position}\n")

            }

            val combobox=itemView.resources.getStringArray(R.array.combobox_yes_no)
            val arrayAdapter= ArrayAdapter(itemView.context,R.layout.combobox_item,combobox)
            val arrayAdapter01= ArrayAdapter(itemView.context,R.layout.combobox_item,relativeCombobox01)

            v_pre_delivery_date.setOnClickListener {
                if(edit==true){
                    var c= Calendar.getInstance()
                    val year= c.get(Calendar.YEAR)
                    val month = c.get(Calendar.MONTH)
                    val day = c.get(Calendar.DAY_OF_MONTH)
                    var datePicker = DatePickerDialog(itemView.context,
                        DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                            val SelectedDate=Calendar.getInstance()
                            SelectedDate.set(Calendar.YEAR,mYear)
                            SelectedDate.set(Calendar.MONTH,mMonth)
                            SelectedDate.set(Calendar.DAY_OF_MONTH,mDay)
                            val date= dateF.format(SelectedDate.time)
                            v_pre_delivery_date.setText(date)
                        },year,month,day).show()

                }
            }

            //編輯按鈕
            edit_btn.setOnClickListener {
                when(edit_btn.text){
                    "編輯"->{
                        receive_btn.isEnabled=false
                        edit=true
                        oldData=data[adapterPosition]

                        //v_pre_delivery_date.inputType=InputType.TYPE_DATETIME_VARIATION_DATE
                        tempdate=v_pre_delivery_date.text.toString()
                        v_count.inputType=(InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL)
                        v_count.setSelectAllOnFocus(true)
                       /* notice_matter.isClickable=true
                        notice_matter.isFocusable=true
                        notice_matter.isFocusableInTouchMode=true
                        notice_matter.isTextInputLayoutFocusedRectEnabled=true*/

                        v_pre_delivery_date.requestFocus()
                        edit_btn.text = "完成"
                        layout.setBackgroundColor(Color.parseColor("#6E5454"))
                        edit_btn.setBackgroundColor(Color.parseColor("#FF3700B3"))
                    }
                    "完成"->{
                        receive_btn.isEnabled=true
                        edit=false
                        newData= oldData.copy()//PurchaseBatchOrder()
                        max=newData.count-newData.quantity_delivered
                        if(v_count.text.toString().toDouble()>max)
                        {
                            newData.v_count=max
                        }
                        else{
                            max=v_count.text.toString().toDouble()
                            newData.v_count=max
                        }
                        v_count.inputType=InputType.TYPE_NULL
                        v_count.setSelectAllOnFocus(false)
                        if(v_pre_delivery_date.text.toString()==""){
                            newData.v_pre_delivery_date=null
                        }
                        else{
                            newData.v_pre_delivery_date=v_pre_delivery_date.text.toString().substring(0,v_pre_delivery_date.text.toString().indexOf("("))
                        }
                        v_pre_delivery_date.inputType=InputType.TYPE_NULL


                       /*
                        quantity_delivered.inputType=InputType.TYPE_NULL


                        newData.notice_matter=notice_matter.text.toString()
                        notice_matter.isClickable=false
                        notice_matter.isFocusable=false
                        notice_matter.isFocusableInTouchMode=false
                        notice_matter.isTextInputLayoutFocusedRectEnabled=false*/


                        //Log.d("GSON", "msg: ${oldData}\n")
                        //Log.d("GSON", "msg: ${newData.remark}\n")
                        edit_Purchase("PurchaseBatchOrder",oldData,newData)//更改資料庫資料
                        when(cookie_data.status){
                            0-> {//成功
                                data[adapterPosition] = newData//更改渲染資料
                                //editor.setText(cookie_data.username)
                                v_count.setText(max.toString())
                                if(data[adapterPosition].pre_delivery_date==null || data[adapterPosition].v_pre_delivery_date==null){
                                    is_early.setText("false")
                                }
                                else{
                                    var d1=dateF2.parse(data[adapterPosition].pre_delivery_date)
                                    var d2=dateF2.parse(data[adapterPosition].v_pre_delivery_date)
                                    var DiffDay :Long=(d1.time-d2.time)/86400000
                                    //println(d1.toString()+"\n"+d2.toString()+"\n"+DiffDay)
                                    if(DiffDay>cookie_data.limited_days_ComboboxData[0]){
                                        is_early.setText("true")
                                    }
                                    else{
                                        is_early.setText("false")
                                    }
                                }
                                Toast.makeText(itemView.context, cookie_data.msg, Toast.LENGTH_SHORT).show()
                            }
                            1->{//失敗
                                Toast.makeText(itemView.context, cookie_data.msg, Toast.LENGTH_SHORT).show()

                                v_count.setText(oldData.v_count.toString())
                                v_pre_delivery_date.setText(tempdate)


                               // notice_matter.setText(oldData.notice_matter)

                            }
                        }
                        //Log.d("GSON", "msg: ${itemData.data}\n")
                        edit_btn.text = "編輯"
                        layout.setBackgroundColor(Color.parseColor("#977C7C"))
                        edit_btn.setBackgroundColor(Color.parseColor("#FF6200EE"))
                    }

                }

            }
            //收料確認
            receive_btn.setOnClickListener {
                oldData=data[adapterPosition]
                newData=oldData.copy()
                var c= Calendar.getInstance()
                val date= dateF.format(c.time)
                v_pre_delivery_date.setText(date)
                newData.v_pre_delivery_date=v_pre_delivery_date.text.toString().substring(0,v_pre_delivery_date.text.toString().indexOf("("))
                max=newData.count-newData.quantity_delivered
                if(v_count.text.toString().toDouble()>max)
                {
                    newData.v_count=max
                }
                else{
                    max=v_count.text.toString().toDouble()
                    newData.v_count=max
                }
                newData.quantity_delivered=quantity_delivered.text.toString().toDouble()+max

                edit_Purchase("PurchaseBatchOrder", oldData, newData)//更改資料庫資料
                when (cookie_data.status) {
                    0 -> {//成功
                        data[adapterPosition] = newData//更改渲染資料
                        v_count.setText(max.toString())
                        quantity_delivered.setText(newData.quantity_delivered.toString())
                        no_delivered.setText((newData.count-newData.quantity_delivered).toString())
                        Toast.makeText(itemView.context, "收料成功", Toast.LENGTH_SHORT).show()
                        //產生 廠商出貨單
                        var d=v_pre_delivery_date.text.toString().substring(0,v_pre_delivery_date.text.toString().indexOf("(")).replace("-","")
                        var is_poNo=false
                        for(i in 0 until cookie_data.VenderShipmentHeader_poNo_ComboboxData.size){
                            if(venderShipment_PoNo+"-"+d==cookie_data.VenderShipmentHeader_poNo_ComboboxData[i]){
                                is_poNo=true
                                break
                            }
                        }
                        if(!is_poNo){//新增單頭單身
                            val addData= VenderShipmentHeader()
                            addData.poNo=venderShipment_PoNo+"-"+d
                            addData.purchase_date=v_pre_delivery_date.text.toString().substring(0,v_pre_delivery_date.text.toString().indexOf("("))
                            addData.vender_id=vender_id_name.text.toString().substring(0,vender_id_name.text.toString().indexOf(" "))
                            addData.vender_shipment_id=venderShipment_PoNo
                            //println(addData)
                            add_header_body("VenderShipmentHeader",addData)
                            when(cookie_data.status){
                                0-> {//成功
                                    Toast.makeText(itemView.context, cookie_data.msg, Toast.LENGTH_SHORT).show()
                                    show_relative_combobox("VenderShipmentHeader","all","False", VenderShipmentHeader())
                                    val addData2= VenderShipmentBody()
                                    addData2.poNo=venderShipment_PoNo+"-"+d
                                    addData2.item_id=item_id_name.text.toString().substring(0,item_id_name.text.toString().indexOf(" "))
                                    addData2.purchase_count=v_count.text.toString().toDouble()
                                    addData2.batch_id=data[adapterPosition].batch_id
                                    //println(addData2)
                                    add_header_body("VenderShipmentBody",addData2)
                                    when(cookie_data.status){
                                        0-> {//成功
                                            Toast.makeText(itemView.context, cookie_data.msg, Toast.LENGTH_SHORT).show()
                                        }
                                        1->{//失敗
                                            Toast.makeText(itemView.context, cookie_data.msg, Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                }
                                1->{//失敗
                                    Toast.makeText(itemView.context, cookie_data.msg, Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                        else{//新增單身
                            val addData2= VenderShipmentBody()
                            addData2.poNo=venderShipment_PoNo+"-"+d
                            addData2.item_id=item_id_name.text.toString().substring(0,item_id_name.text.toString().indexOf(" "))
                            addData2.purchase_count=data[adapterPosition].v_count
                            addData2.batch_id=data[adapterPosition].batch_id
                            //println(addData2)
                            add_header_body("VenderShipmentBody",addData2)
                            when(cookie_data.status){
                                0-> {//成功
                                    Toast.makeText(itemView.context, cookie_data.msg, Toast.LENGTH_SHORT).show()
                                }
                                1->{//失敗
                                    Toast.makeText(itemView.context, cookie_data.msg, Toast.LENGTH_SHORT).show()
                                }
                            }
                        }

                        //結案
                        if(data[adapterPosition].quantity_delivered>=data[adapterPosition].count){
                            over_Purchase("PurchaseBatchOrder",data[adapterPosition])
                            when(cookie_data.status){
                                0-> {//成功
                                    //is_closed.setText("true")
                                    //close_time.setText(Calendar.getInstance().getTime().toString())
                                    is_close.setText("true")
                                    edit_btn.isVisible=false
                                    receive_btn.isVisible=false
                                    Toast.makeText(itemView.context, cookie_data.msg, Toast.LENGTH_SHORT).show()

                                }
                                1->{//失敗
                                    Toast.makeText(itemView.context, cookie_data.msg, Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                    1 -> {//失敗
                        Toast.makeText(itemView.context, cookie_data.msg, Toast.LENGTH_SHORT).show()
                        quantity_delivered.setText(oldData.quantity_delivered.toString())
                    }
                }
            }
            //下一筆
            next_btn.setOnClickListener {
                if(adapterPosition+1<=data.size){
                    cookie_data.recyclerView.smoothScrollToPosition(adapterPosition+1)
                }

            }
           /* //刪除按鈕
            deletebtn.setOnClickListener {
                //Log.d("GSON", "msg: ${data}\n")
                val mAlertDialog = AlertDialog.Builder(itemView.context)
                mAlertDialog.setIcon(R.mipmap.ic_launcher_round) //set alertdialog icon
                mAlertDialog.setTitle("刪除") //set alertdialog title
                mAlertDialog.setMessage("確定要刪除?") //set alertdialog message
                mAlertDialog.setPositiveButton("NO") { dialog, id ->
                    dialog.dismiss()
                }
                mAlertDialog.setNegativeButton("YES") { dialog, id ->
                    delete_Purchase("PurchaseBatchOrder",data[adapterPosition])
                    when(cookie_data.status){
                        0-> {//成功
                            data.removeAt(adapterPosition)
                            notifyItemRemoved(adapterPosition)
                            itemData.count-=1//cookie_data.itemCount-=1
                            Toast.makeText(itemView.context, cookie_data.msg, Toast.LENGTH_SHORT).show()

                        }
                        1->{//失敗
                            Toast.makeText(itemView.context, cookie_data.msg, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                mAlertDialog.show()

            }

            //鎖定按鈕
            lockbtn.setOnClickListener {
                //Log.d("GSON", "msg: ${data}\n")
                val mAlertDialog = AlertDialog.Builder(itemView.context)
                mAlertDialog.setIcon(R.mipmap.ic_launcher_round) //set alertdialog icon
                mAlertDialog.setTitle("鎖定") //set alertdialog title
                mAlertDialog.setMessage("確定要鎖定?\n經鎖定後無法再編輯或刪除！") //set alertdialog message
                mAlertDialog.setPositiveButton("NO") { dialog, id ->
                    dialog.dismiss()
                }
                mAlertDialog.setNegativeButton("YES") { dialog, id ->
                    lock_Purchase("PurchaseBatchOrder",data[adapterPosition])
                    when(cookie_data.status){
                        0-> {//成功
                            //lock.setText("true")
                            //lock_time.setText(Calendar.getInstance().getTime().toString())
                            Toast.makeText(itemView.context, cookie_data.msg, Toast.LENGTH_SHORT).show()

                        }
                        1->{//失敗
                            Toast.makeText(itemView.context, cookie_data.msg, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                mAlertDialog.show()

            }*/

            //結案按鈕
            overbtn.setOnClickListener {
                //Log.d("GSON", "msg: ${data}\n")
                val mAlertDialog = AlertDialog.Builder(itemView.context)
                mAlertDialog.setIcon(R.mipmap.ic_launcher_round) //set alertdialog icon
                mAlertDialog.setTitle("結案") //set alertdialog title
                mAlertDialog.setMessage("確定要結案?！") //set alertdialog message
                mAlertDialog.setPositiveButton("NO") { dialog, id ->
                    dialog.dismiss()
                }
                mAlertDialog.setNegativeButton("YES") { dialog, id ->
                    over_Purchase("PurchaseBatchOrder",data[adapterPosition])
                    when(cookie_data.status){
                        0-> {//成功
                            //is_closed.setText("true")
                            //close_time.setText(Calendar.getInstance().getTime().toString())
                            Toast.makeText(itemView.context, cookie_data.msg, Toast.LENGTH_SHORT).show()

                        }
                        1->{//失敗
                            Toast.makeText(itemView.context, cookie_data.msg, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                mAlertDialog.show()

            }
        }
        private fun edit_Purchase(operation:String,oldData:PurchaseBatchOrder,newData:PurchaseBatchOrder) {
            val old =JSONObject()
            old.put("batch_id",oldData.batch_id)
            old.put("purchase_order_id",oldData.purchase_order_id)
            old.put("section",oldData.section)
            old.put("count",oldData.count)
            old.put("pre_delivery_date",oldData.pre_delivery_date)
            old.put("v_count",oldData.v_count)
            old.put("v_pre_delivery_date",oldData.v_pre_delivery_date)
            old.put("quantity_delivered",oldData.quantity_delivered)
            old.put("prod_ctrl_order_number",oldData.prod_ctrl_order_number)
            old.put("is_warning",oldData.is_warning)
            old.put("is_urgent",oldData.is_urgent)
            old.put("urgent_deadline",oldData.urgent_deadline)
            old.put("notice_matter",oldData.notice_matter)
            old.put("v_notice_matter",oldData.v_notice_matter)
            old.put("is_request_reply",oldData.is_request_reply)
            old.put("notice_reply_time",oldData.notice_reply_time)
            old.put("is_vender_reply",oldData.is_vender_reply)
            old.put("vender_reply_time",oldData.vender_reply_time)
            old.put("is_argee",oldData.is_argee)
            old.put("argee_time",oldData.argee_time)
            old.put("is_v_argee",oldData.is_v_argee)
            old.put("v_argee_time",oldData.v_argee_time)
            old.put("number_of_standard_measurements",oldData.number_of_standard_measurements)

            old.put("remark",oldData.remark)
            val new =JSONObject()
            new.put("batch_id",newData.batch_id)
            new.put("purchase_order_id",newData.purchase_order_id)
            new.put("section",newData.section)
            new.put("count",newData.count)
            new.put("pre_delivery_date",newData.pre_delivery_date)
            new.put("v_count",newData.v_count)
            new.put("v_pre_delivery_date",newData.v_pre_delivery_date)
            new.put("quantity_delivered",newData.quantity_delivered)
            new.put("prod_ctrl_order_number",newData.prod_ctrl_order_number)
            new.put("is_warning",newData.is_warning)
            new.put("is_urgent",newData.is_urgent)
            new.put("urgent_deadline",newData.urgent_deadline)
            new.put("notice_matter",newData.notice_matter)
            new.put("v_notice_matter",newData.v_notice_matter)
            new.put("is_request_reply",newData.is_request_reply)
            new.put("notice_reply_time",newData.notice_reply_time)
            new.put("is_vender_reply",newData.is_vender_reply)
            new.put("vender_reply_time",newData.vender_reply_time)
            new.put("is_argee",newData.is_argee)
            new.put("argee_time",newData.argee_time)
            new.put("is_v_argee",newData.is_v_argee)
            new.put("v_argee_time",newData.v_argee_time)
            new.put("number_of_standard_measurements",newData.number_of_standard_measurements)

            new.put("editor",cookie_data.username)
            new.put("remark",newData.remark)
            val data = JSONArray()
            data.put(old)
            data.put((new))
            val body = FormBody.Builder()
                .add("operation",operation)
                .add("data",data.toString())
                .add("username", cookie_data.username)
                .add("action",cookie_data.Actions.CHANGE)
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

            }
            val responseInfo = Gson().fromJson(cookie_data.response_data, Response::class.java)
            cookie_data.status=responseInfo.status
            cookie_data.msg=responseInfo.msg


        }
        private fun delete_Purchase(operation:String,deleteData:PurchaseBatchOrder) {
            val delete = JSONObject()
            delete.put("batch_id",deleteData.batch_id)
            delete.put("purchase_order_id",deleteData.purchase_order_id)
            delete.put("section",deleteData.section)
            delete.put("count",deleteData.count)
            delete.put("pre_delivery_date",deleteData.pre_delivery_date)
            delete.put("v_count",deleteData.v_count)
            delete.put("v_pre_delivery_date",deleteData.v_pre_delivery_date)
            delete.put("quantity_delivered",deleteData.quantity_delivered)
            delete.put("prod_ctrl_order_number",deleteData.prod_ctrl_order_number)
            delete.put("is_warning",deleteData.is_warning)
            delete.put("is_urgent",deleteData.is_urgent)
            delete.put("urgent_deadline",deleteData.urgent_deadline)
            delete.put("notice_matter",deleteData.notice_matter)
            delete.put("v_notice_matter",deleteData.v_notice_matter)
            delete.put("is_request_reply",deleteData.is_request_reply)
            delete.put("notice_reply_time",deleteData.notice_reply_time)
            delete.put("is_vender_reply",deleteData.is_vender_reply)
            delete.put("vender_reply_time",deleteData.vender_reply_time)
            delete.put("is_argee",deleteData.is_argee)
            delete.put("argee_time",deleteData.argee_time)
            delete.put("is_v_argee",deleteData.is_v_argee)
            delete.put("v_argee_time",deleteData.v_argee_time)
            delete.put("number_of_standard_measurements",deleteData.number_of_standard_measurements)

            delete.put("remark",deleteData.remark)
            //Log.d("GSON", "msg:${delete}")
            val body = FormBody.Builder()
                .add("operation", operation)
                .add("username", cookie_data.username)
                .add("action",cookie_data.Actions.DELETE)
                .add("data",delete.toString())
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
                cookie_data.msg=responseInfo.msg
            }

        }
        private fun lock_Purchase(operation:String,lockData:PurchaseBatchOrder) {
            val lock = JSONObject()
            lock.put("batch_id",lockData.batch_id)
            lock.put("purchase_order_id",lockData.purchase_order_id)
            lock.put("section",lockData.section)
            lock.put("count",lockData.count)
            lock.put("pre_delivery_date",lockData.pre_delivery_date)
            lock.put("v_count",lockData.v_count)
            lock.put("v_pre_delivery_date",lockData.v_pre_delivery_date)
            lock.put("quantity_delivered",lockData.quantity_delivered)
            lock.put("prod_ctrl_order_number",lockData.prod_ctrl_order_number)
            lock.put("is_warning",lockData.is_warning)
            lock.put("is_urgent",lockData.is_urgent)
            lock.put("urgent_deadline",lockData.urgent_deadline)
            lock.put("notice_matter",lockData.notice_matter)
            lock.put("v_notice_matter",lockData.v_notice_matter)
            lock.put("is_request_reply",lockData.is_request_reply)
            lock.put("notice_reply_time",lockData.notice_reply_time)
            lock.put("is_vender_reply",lockData.is_vender_reply)
            lock.put("vender_reply_time",lockData.vender_reply_time)
            lock.put("is_argee",lockData.is_argee)
            lock.put("argee_time",lockData.argee_time)
            lock.put("is_v_argee",lockData.is_v_argee)
            lock.put("v_argee_time",lockData.v_argee_time)
            lock.put("number_of_standard_measurements",lockData.number_of_standard_measurements)

            lock.put("remark",lockData.remark)
            //Log.d("GSON", "msg:${lock}")
            val body = FormBody.Builder()
                .add("operation", operation)
                .add("username", cookie_data.username)
                .add("action",cookie_data.Actions.LOCK)
                .add("data",lock.toString())
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
                cookie_data.msg=responseInfo.msg
            }

        }
        private fun over_Purchase(operation:String,overData:PurchaseBatchOrder) {
            val over = JSONObject()
            over.put("batch_id",overData.batch_id)
            over.put("purchase_order_id",overData.purchase_order_id)
            over.put("section",overData.section)
            over.put("count",overData.count)
            over.put("pre_delivery_date",overData.pre_delivery_date)
            over.put("v_count",overData.v_count)
            over.put("v_pre_delivery_date",overData.v_pre_delivery_date)
            over.put("quantity_delivered",overData.quantity_delivered)
            over.put("prod_ctrl_order_number",overData.prod_ctrl_order_number)
            over.put("is_warning",overData.is_warning)
            over.put("is_urgent",overData.is_urgent)
            over.put("urgent_deadline",overData.urgent_deadline)
            over.put("notice_matter",overData.notice_matter)
            over.put("v_notice_matter",overData.v_notice_matter)
            over.put("is_request_reply",overData.is_request_reply)
            over.put("notice_reply_time",overData.notice_reply_time)
            over.put("is_vender_reply",overData.is_vender_reply)
            over.put("vender_reply_time",overData.vender_reply_time)
            over.put("is_argee",overData.is_argee)
            over.put("argee_time",overData.argee_time)
            over.put("is_v_argee",overData.is_v_argee)
            over.put("v_argee_time",overData.v_argee_time)
            over.put("number_of_standard_measurements",overData.number_of_standard_measurements)

            over.put("remark",overData.remark)
            //Log.d("GSON", "msg:${delete}")
            val body = FormBody.Builder()
                .add("username", cookie_data.username)
                .add("operation", operation)
                .add("action",cookie_data.Actions.CLOSE)
                .add("data",over.toString())
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
                cookie_data.msg=responseInfo.msg
            }

        }

        private fun <T>add_header_body(operation:String,addData:T) {

            when(addData)
            {
                is VenderShipmentHeader ->{
                    val add = JSONObject()
                    add.put("poNo",addData.poNo)
                    add.put("purchase_date",addData.purchase_date)
                    add.put("vender_id",addData.vender_id)
                    add.put("vender_shipment_id",addData.vender_shipment_id)

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
                        cookie_data.msg=responseInfo.msg
                    }
                }
                is VenderShipmentBody ->{
                    val add = JSONObject()
                    add.put("body_id",addData.body_id)
                    add.put("poNo",addData.poNo)
                    add.put("section",addData.section)
                    add.put("item_id",addData.item_id)
                    add.put("purchase_count",addData.purchase_count)
                    add.put("batch_id",addData.batch_id)
                    add.put("prod_batch_code",addData.prod_batch_code)
                    add.put("qc_date",addData.qc_date)
                    add.put("qc_number",addData.qc_number)
                    add.put("is_tobe_determined",addData.is_tobe_determined)
                    add.put("is_acceptance",addData.is_acceptance)
                    add.put("is_reject",addData.is_reject)
                    add.put("is_special_case",addData.is_special_case)

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
                        cookie_data.msg=responseInfo.msg
                    }
                }
            }


        }
        private fun <T>show_relative_combobox(operation:String,view_type:String,view_hide:String,fmt:T) {
            when(fmt)
            {
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
    }
    fun addItem(addData:PurchaseBatchOrder){
        data.add(itemData.count,addData)
        notifyItemInserted(itemData.count)
        itemData.count+=1//cookie_data.itemCount+=1
    }

}


