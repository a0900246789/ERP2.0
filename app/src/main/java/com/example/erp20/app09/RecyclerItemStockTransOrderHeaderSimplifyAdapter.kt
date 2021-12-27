package com.example.erp20.app09
import android.app.DatePickerDialog
import android.graphics.Color
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.erp20.Model.*
import com.example.erp20.R
import com.example.erp20.app04.PopUpOutSourceFragment
import com.example.erp20.cookie_data
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import kotlinx.coroutines.*
import okhttp3.FormBody
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class RecyclerItemStockTransOrderHeaderSimplifyAdapter(Filter:String) :
    RecyclerView.Adapter<RecyclerItemStockTransOrderHeaderSimplifyAdapter.ViewHolder>() {
    private var itemData: ShowStockTransOrderHeader =Gson().fromJson(cookie_data.response_data, ShowStockTransOrderHeader::class.java)
    private var data: ArrayList<StockTransOrderHeader> =itemData.data
    var relativeCombobox01=cookie_data.pline_id_name_ComboboxData
    var relativeCombobox02=cookie_data.inv_code_name_m_ComboboxData
    var relativeCombobox03=cookie_data.inv_code_name_s_ComboboxData
    val dateF = SimpleDateFormat("yyyy-MM-dd(EEEE)", Locale.TAIWAN)
    val timeF = SimpleDateFormat("HH:mm:ss", Locale.TAIWAN)
    val dateF2 = SimpleDateFormat("yyyy-MM-dd", Locale.US)

    init {
        // println(SelectFilter)
        data=filter(data,Filter)//過濾date<filter && main_trans_code!=M03
        data=sort(data)

    }

    fun filter(data:ArrayList<StockTransOrderHeader>,filter:String): ArrayList<StockTransOrderHeader>{
        var ArrayList:ArrayList<StockTransOrderHeader> = ArrayList<StockTransOrderHeader>()

        for(i in 0 until data.size){
            var date1=dateF2.parse(data[i].date)
            var date2=dateF2.parse(filter)
            if(date1.compareTo(date2)>=0 && data[i].main_trans_code=="M03"){
                ArrayList.add(data[i])
            }
        }
        return ArrayList
    }
    fun sort(data:ArrayList<StockTransOrderHeader>): ArrayList<StockTransOrderHeader>{
        var sortedList:List<StockTransOrderHeader> = data

        sortedList  = data.sortedWith(
            compareBy(
                { it._id},
                { it.date}
            )
        )

        return sortedList.toCollection(java.util.ArrayList())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v= LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_stock_trans_order_header_simplify,parent,false)
        return ViewHolder(v)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //Log.d("GSON", "msg: ${itemData.data[position]}\n")
        holder._id.setText(data[position]._id)
        holder._id.inputType=InputType.TYPE_NULL
        if(data[position].date!=null){
            //date
            var readDateString=data[position].date
            //println(readDateString)
            val readDate=Calendar.getInstance()
            //println(readDateString.subSequence(0,4))
            readDate.set(Calendar.YEAR,readDateString!!.subSequence(0,4).toString().toInt())//yyyy
            // println(readDateString.subSequence(5,7))
            readDate.set(Calendar.MONTH,readDateString!!.subSequence(5,7).toString().toInt()-1)//MM
            // println(readDateString.subSequence(8,10))
            readDate.set(Calendar.DAY_OF_MONTH,readDateString!!.subSequence(8,10).toString().toInt())//dd
            var date=dateF.format(readDate.time)
            holder.temp_date=date
            holder.date.setText(date)
        }
        else{
            holder.date.setText(null)
        }
        holder.date.inputType=InputType.TYPE_NULL
        holder.dept.setText(data[position].dept+" "+cookie_data.pline_name_ComboboxData[cookie_data.pline_id_ComboboxData.indexOf(data[position].dept)] )
        holder.dept.inputType=InputType.TYPE_NULL
        holder.main_trans_code.setText(cookie_data.inv_code_name_m_ComboboxData[cookie_data.inv_code_m_ComboboxData.indexOf(data[position].main_trans_code)])
        holder.main_trans_code.inputType=InputType.TYPE_NULL
        holder.sec_trans_code.setText(cookie_data.inv_code_name_s_ComboboxData[cookie_data.inv_code_s_ComboboxData.indexOf(data[position].sec_trans_code)])
        holder.sec_trans_code.inputType=InputType.TYPE_NULL
        if(data[position].purchase_order_id==""){
            holder.purchase_order_id.setText("")
        }
        else{
            holder.purchase_order_id.setText(data[position].purchase_order_id+" "+
                    cookie_data.PurchaseBatchOrder_batch_id_ComboboxData[cookie_data.PurchaseBatchOrder_purchase_order_id_ComboboxData.indexOf(data[position].purchase_order_id)]+" "+
                    cookie_data.PurchaseOrderHeader_vender_name_ComboboxData[cookie_data.PurchaseOrderHeader_poNo_ComboboxData.indexOf(cookie_data.PurchaseOrderBody_poNo_ComboboxData[cookie_data.PurchaseOrderBody_body_id_ComboboxData.indexOf(data[position].purchase_order_id)])]
            )
        }
        holder.purchase_order_id.inputType=InputType.TYPE_NULL
        if(data[position].prod_ctrl_order_number==""){
            holder.prod_ctrl_order_number.setText("")
        }
        else{
            holder.prod_ctrl_order_number.setText(data[position].prod_ctrl_order_number+"\n"+
                    cookie_data.item_name_ComboboxData[cookie_data.semi_finished_product_number_ComboboxData.indexOf(cookie_data.ProductControlOrderBody_A_semi_finished_prod_number_ComboboxData[cookie_data.ProductControlOrderBody_A_prod_ctrl_order_number_ComboboxData.indexOf(data[position].prod_ctrl_order_number)])]+"\n"+
                    cookie_data.pline_name_ComboboxData[cookie_data.pline_id_ComboboxData.indexOf(cookie_data.ProductControlOrderBody_A_pline_id_ComboboxData[cookie_data.ProductControlOrderBody_A_prod_ctrl_order_number_ComboboxData.indexOf(data[position].prod_ctrl_order_number)])]+"\n"+
                    cookie_data.MeBody_work_option_ComboboxData[cookie_data.MeBody_process_number_ComboboxData.indexOf(cookie_data.ProductControlOrderBody_A_me_code_ComboboxData[cookie_data.ProductControlOrderBody_A_prod_ctrl_order_number_ComboboxData.indexOf(data[position].prod_ctrl_order_number)])]
            )
        }
        holder.prod_ctrl_order_number.inputType=InputType.TYPE_NULL
        holder.illustrate.setText(data[position].illustrate)
        holder.illustrate.isClickable=false
        holder.illustrate.isFocusable=false
        holder.illustrate.isFocusableInTouchMode=false
        holder.illustrate.isTextInputLayoutFocusedRectEnabled=false




        /*holder.creator.setText(data[position].creator)
        holder.creator_time.setText(data[position].create_time)
        holder.editor.setText(data[position].editor)
        holder.editor_time.setText(data[position].edit_time)
        holder.lock.setText(data[position].Lock.toString())
        holder.lock_time.setText(data[position].lock_time)
        holder.invalid.setText(data[position].invalid.toString())
        holder.invalid_time.setText(data[position].invalid_time)
        holder.is_closed.setText(data[position].is_closed.toString())
        holder.close_time.setText(data[position].close_time)
        holder.remark.setText(data[position].remark)
        holder.remark.isClickable=false
        holder.remark.isFocusable=false
        holder.remark.isFocusableInTouchMode=false
        holder.remark.isTextInputLayoutFocusedRectEnabled=false*/
        holder.deletebtn.isVisible=true
        holder.edit_btn.isVisible=true
        holder.lockbtn.isVisible=true
        holder.overbtn.isVisible=true
        holder.item_detail_btn.isVisible=true
    }

    override fun getItemCount(): Int {
        return data.size//cookie_data.itemCount
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var _id=itemView.findViewById<TextInputEditText>(R.id.edit_id)
        var date=itemView.findViewById<TextInputEditText>(R.id.edit_date)
        var dept=itemView.findViewById<AutoCompleteTextView>(R.id.edit_dept)
        var main_trans_code=itemView.findViewById<AutoCompleteTextView>(R.id.edit_main_trans_code)
        var sec_trans_code=itemView.findViewById<AutoCompleteTextView>(R.id.edit_sec_trans_code)
        var purchase_order_id =itemView.findViewById<AutoCompleteTextView>(R.id.edit_purchase_order_id)
        var prod_ctrl_order_number =itemView.findViewById<AutoCompleteTextView>(R.id.edit_prod_ctrl_order_number)
        var illustrate=itemView.findViewById<TextInputEditText>(R.id.edit_illustrate)



        var edit_btn=itemView.findViewById<Button>(R.id.edit_btn)
        var deletebtn=itemView.findViewById<Button>(R.id.delete_btn)
        var lockbtn=itemView.findViewById<Button>(R.id.lock_btn)
        var overbtn=itemView.findViewById<Button>(R.id.over_btn)
        var item_detail_btn=itemView.findViewById<Button>(R.id.item_detail_btn)
        /*var creator=itemView.findViewById<AutoCompleteTextView>(R.id.edit_creator)
        var creator_time=itemView.findViewById<AutoCompleteTextView>(R.id.edit_create_time)
        var editor=itemView.findViewById<AutoCompleteTextView>(R.id.edit_editor)
        var editor_time=itemView.findViewById<AutoCompleteTextView>(R.id.edit_edit_time)
        var lock=itemView.findViewById<AutoCompleteTextView>(R.id.edit_lock)
        var lock_time=itemView.findViewById<AutoCompleteTextView>(R.id.edit_lock_time)
        var invalid=itemView.findViewById<AutoCompleteTextView>(R.id.edit_invalid)
        var invalid_time=itemView.findViewById<AutoCompleteTextView>(R.id.edit_invalid_time)
        var is_closed=itemView.findViewById<AutoCompleteTextView>(R.id.edit_is_closed)
        var close_time=itemView.findViewById<AutoCompleteTextView>(R.id.edit_close_time)
        var remark=itemView.findViewById<TextInputEditText>(R.id.edit_remark)*/
        var layout=itemView.findViewById<ConstraintLayout>(R.id.constraint_layout)
        var edit=false
        var temp_date=""
        var temp_dept=""
        var temp_main_trans_code=""
        var temp_sec_trans_code=""
        var temp_purchase_order_id=""
        var temp_prod_ctrl_order_number=""
        lateinit var oldData:StockTransOrderHeader
        lateinit var newData:StockTransOrderHeader
        init {
            itemView.setOnClickListener{
                val position:Int=adapterPosition
                Log.d("GSON", "msg: ${position}\n")

            }

            val combobox=itemView.resources.getStringArray(R.array.combobox_yes_no)
            val arrayAdapter= ArrayAdapter(itemView.context,R.layout.combobox_item,combobox)
            val arrayAdapter01= ArrayAdapter(itemView.context,R.layout.combobox_item,relativeCombobox01)
            val arrayAdapter02= ArrayAdapter(itemView.context,R.layout.combobox_item,relativeCombobox02)
            var arrayAdapter03= ArrayAdapter(itemView.context,R.layout.combobox_item,relativeCombobox03)
            var relativeCombobox04:ArrayList<String> = ArrayList<String>()
            for(i in 0 until cookie_data.PurchaseBatchOrder_batch_id_ComboboxData.size){
                if(cookie_data.PurchaseBatchOrder_is_closed_ComboboxData[i]){
                    relativeCombobox04.add(cookie_data.PurchaseBatchOrder_purchase_order_id_ComboboxData[i]+" "+
                                           cookie_data.PurchaseBatchOrder_batch_id_ComboboxData[i]+" "+
                    cookie_data.PurchaseOrderHeader_vender_name_ComboboxData[cookie_data.PurchaseOrderHeader_poNo_ComboboxData.indexOf(cookie_data.PurchaseOrderBody_poNo_ComboboxData[cookie_data.PurchaseOrderBody_body_id_ComboboxData.indexOf(cookie_data.PurchaseBatchOrder_purchase_order_id_ComboboxData[i])])] )
                }
            }
            //println(relativeCombobox04)
            val arrayAdapter04= ArrayAdapter(itemView.context,R.layout.combobox_item,relativeCombobox04)
            /*var relativeCombobox05:ArrayList<String> = ArrayList<String>()
            for(i in 0 until cookie_data.ProductControlOrderBody_A_complete_date_ComboboxData.size){
                if(cookie_data.ProductControlOrderBody_A_complete_date_ComboboxData[i]!=null){
                    relativeCombobox05.add(cookie_data.ProductControlOrderBody_A_prod_ctrl_order_number_ComboboxData[i]+"\n"+
                            cookie_data.item_name_ComboboxData[cookie_data.semi_finished_product_number_ComboboxData.indexOf(cookie_data.ProductControlOrderBody_A_semi_finished_prod_number_ComboboxData[i])]+"\n"+
                            cookie_data.pline_name_ComboboxData[cookie_data.pline_id_ComboboxData.indexOf(cookie_data.ProductControlOrderBody_A_pline_id_ComboboxData[i])]+"\n"+
                            cookie_data.MeBody_work_option_ComboboxData[cookie_data.MeBody_process_number_ComboboxData.indexOf(cookie_data.ProductControlOrderBody_A_me_code_ComboboxData[i])]
                    )
                }
            }
            //println(relativeCombobox05)
            val arrayAdapter05= ArrayAdapter(itemView.context,R.layout.combobox_item,relativeCombobox05)*/

            //異動日期
            date.setOnClickListener {
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
                            val Date= dateF.format(SelectedDate.time)
                            date.setText(Date)
                            /*//自動填單號
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
                            _id.setText(mYear.toString()+(mMonth+1).toString()+"-"+maxNum.toString())*/
                        },year,month,day).show()

                }
            }

            //主異動次別
            main_trans_code.setOnItemClickListener { parent, view, position, id ->
                var inv_code_m=main_trans_code.text.toString().substring(0,main_trans_code.text.toString().indexOf(" "))
                //println(inv_code_m)
                var ArrayList:ArrayList<String> = ArrayList<String>()

                for(i in 0 until cookie_data.inv_code_s_inv_code_m_ComboboxData.size){
                    if(cookie_data.inv_code_s_inv_code_m_ComboboxData[i]==inv_code_m){
                        ArrayList.add(cookie_data.inv_code_name_s_ComboboxData[i])
                    }
                }

                arrayAdapter03= ArrayAdapter(itemView.context,R.layout.combobox_item,ArrayList)
                sec_trans_code.setAdapter(arrayAdapter03)
            }


            //編輯按鈕
            edit_btn.setOnClickListener {
                when(edit_btn.text){
                    "編輯"->{
                        cookie_data.itemposition=adapterPosition
                        oldData=data[adapterPosition]
                        //Log.d("GSON", "msg: ${oldData.id}\n")
                        _id.inputType=InputType.TYPE_CLASS_NUMBER
                        //date.inputType=InputType.TYPE_DATETIME_VARIATION_DATE

                        temp_dept=dept.text.toString()
                        dept.setAdapter(arrayAdapter01)
                        temp_main_trans_code=main_trans_code.text.toString()
                        main_trans_code.setAdapter(arrayAdapter02)
                        temp_sec_trans_code=sec_trans_code.text.toString()
                        //sec_trans_code.setAdapter(arrayAdapter03)
                        temp_purchase_order_id=purchase_order_id.text.toString()
                        purchase_order_id.setAdapter(arrayAdapter04)
                        temp_prod_ctrl_order_number=prod_ctrl_order_number.text.toString()
                       // prod_ctrl_order_number.setAdapter(arrayAdapter05)

                        illustrate.isClickable=true
                        illustrate.isFocusable=true
                        illustrate.isFocusableInTouchMode=true
                        illustrate.isTextInputLayoutFocusedRectEnabled=true

                        _id.requestFocus()
                        edit_btn.text = "完成"
                        layout.setBackgroundColor(Color.parseColor("#6E5454"))
                        edit_btn.setBackgroundColor(Color.parseColor("#FF3700B3"))
                        edit=true
                    }
                    "完成"->{
                        edit=false
                        newData= oldData.copy()//StockTransOrderHeader()
                        newData._id=_id.text.toString()
                        _id.inputType=InputType.TYPE_NULL
                        if(date.text.toString()==""){
                            newData.date=null
                        }
                        else{
                            newData.date=date.text.toString().substring(0,date.text.toString().indexOf("("))
                        }
                        date.inputType=InputType.TYPE_NULL
                        newData.dept=dept.text.toString().substring(0,dept.text.toString().indexOf(" "))
                        dept.setAdapter(null)
                        newData.main_trans_code=main_trans_code.text.toString().substring(0,main_trans_code.text.toString().indexOf(" "))
                        main_trans_code.setAdapter(null)
                        newData.sec_trans_code=sec_trans_code.text.toString().substring(0,sec_trans_code.text.toString().indexOf(" "))
                        sec_trans_code.setAdapter(null)
                        if(purchase_order_id.text.toString()==""){
                            newData.purchase_order_id=""
                        }
                        else{
                            newData.purchase_order_id=purchase_order_id.text.toString().substring(0,purchase_order_id.text.toString().indexOf(" "))
                        }
                        purchase_order_id.setAdapter(null)
                        if(prod_ctrl_order_number.text.toString()==""){
                            newData.prod_ctrl_order_number=""
                        }
                        else{
                            newData.prod_ctrl_order_number=prod_ctrl_order_number.text.toString().substring(0,prod_ctrl_order_number.text.toString().indexOf("\n"))
                        }
                        prod_ctrl_order_number.setAdapter(null)
                        newData.illustrate=illustrate.text.toString()
                        illustrate.isClickable=false
                        illustrate.isFocusable=false
                        illustrate.isFocusableInTouchMode=false
                        illustrate.isTextInputLayoutFocusedRectEnabled=false

                        //Log.d("GSON", "msg: ${oldData}\n")
                        //Log.d("GSON", "msg: ${newData.remark}\n")
                        edit_ProductControlOrder("StockTransOrderHeader",oldData,newData)//更改資料庫資料
                        when(cookie_data.status){
                            0-> {//成功
                                data[adapterPosition] = newData//更改渲染資料
                                //editor.setText(cookie_data.username)
                                Toast.makeText(itemView.context, cookie_data.msg, Toast.LENGTH_SHORT).show()
                            }
                            1->{//失敗
                                Toast.makeText(itemView.context, cookie_data.msg, Toast.LENGTH_SHORT).show()
                                _id.setText(oldData._id)
                                date.setText(temp_date)
                                dept.setText(temp_dept)
                                main_trans_code.setText(temp_main_trans_code)
                                sec_trans_code.setText(temp_sec_trans_code)
                                purchase_order_id.setText(temp_purchase_order_id)
                                prod_ctrl_order_number.setText(temp_prod_ctrl_order_number)
                                illustrate.setText(oldData.illustrate)



                                //remark.setText(oldData.remark)
                            }
                        }
                        //Log.d("GSON", "msg: ${itemData.data}\n")
                        edit_btn.text = "編輯"
                        layout.setBackgroundColor(Color.parseColor("#977C7C"))
                        edit_btn.setBackgroundColor(Color.parseColor("#FF6200EE"))
                    }

                }

            }
            //刪除按鈕
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
                    delete_ProductControlOrder("StockTransOrderHeader",data[adapterPosition])
                    when(cookie_data.status){
                        0-> {//成功
                            data.removeAt(adapterPosition)
                            notifyItemRemoved(adapterPosition)
                            //itemData.count-=1//cookie_data.itemCount-=1
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
                    lock_ProductControlOrder("StockTransOrderHeader",data[adapterPosition])
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

            }

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
                    over_ProductControlOrder("StockTransOrderHeader",data[adapterPosition])
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

            //單身
            item_detail_btn.setOnClickListener {
                var dialogF= PopUpItemDetailFragment(data[adapterPosition]._id,data[adapterPosition].purchase_order_id,data[adapterPosition].prod_ctrl_order_number)
                dialogF.show( ( itemView.context as AppCompatActivity).supportFragmentManager ,"料件明細")
            }
        }
        private fun edit_ProductControlOrder(operation:String,oldData:StockTransOrderHeader,newData:StockTransOrderHeader) {
            val old =JSONObject()
            old.put("_id",oldData._id)
            old.put("date",oldData.date)
            old.put("dept",oldData.dept)
            old.put("main_trans_code",oldData.main_trans_code)
            old.put("sec_trans_code",oldData.sec_trans_code)
            old.put("purchase_order_id",oldData.purchase_order_id)
            old.put("prod_ctrl_order_number",oldData.prod_ctrl_order_number)
            old.put("illustrate",oldData.illustrate)


            old.put("remark",oldData.remark)
            val new =JSONObject()
            new.put("_id",newData._id)
            new.put("date",newData.date)
            new.put("dept",newData.dept)
            new.put("main_trans_code",newData.main_trans_code)
            new.put("sec_trans_code",newData.sec_trans_code)
            new.put("purchase_order_id",newData.purchase_order_id)
            new.put("prod_ctrl_order_number",newData.prod_ctrl_order_number)
            new.put("illustrate",newData.illustrate)

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
                .url("http://140.125.46.125:8000/inventory_management")
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
        private fun delete_ProductControlOrder(operation:String,deleteData:StockTransOrderHeader) {
            val delete = JSONObject()
            delete.put("_id",deleteData._id)
            delete.put("date",deleteData.date)
            delete.put("dept",deleteData.dept)
            delete.put("main_trans_code",deleteData.main_trans_code)
            delete.put("sec_trans_code",deleteData.sec_trans_code)
            delete.put("purchase_order_id",deleteData.purchase_order_id)
            delete.put("prod_ctrl_order_number",deleteData.prod_ctrl_order_number)
            delete.put("illustrate",deleteData.illustrate)

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
                .url("http://140.125.46.125:8000/inventory_management")
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
        private fun lock_ProductControlOrder(operation:String,lockData:StockTransOrderHeader) {
            val lock = JSONObject()
            lock.put("_id",lockData._id)
            lock.put("date",lockData.date)
            lock.put("dept",lockData.dept)
            lock.put("main_trans_code",lockData.main_trans_code)
            lock.put("sec_trans_code",lockData.sec_trans_code)
            lock.put("purchase_order_id",lockData.purchase_order_id)
            lock.put("prod_ctrl_order_number",lockData.prod_ctrl_order_number)
            lock.put("illustrate",lockData.illustrate)

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
                .url("http://140.125.46.125:8000/inventory_management")
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
        private fun over_ProductControlOrder(operation:String,overData:StockTransOrderHeader) {
            val over = JSONObject()
            over.put("_id",overData._id)
            over.put("date",overData.date)
            over.put("dept",overData.dept)
            over.put("main_trans_code",overData.main_trans_code)
            over.put("sec_trans_code",overData.sec_trans_code)
            over.put("purchase_order_id",overData.purchase_order_id)
            over.put("prod_ctrl_order_number",overData.prod_ctrl_order_number)
            over.put("illustrate",overData.illustrate)

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
                .url("http://140.125.46.125:8000/inventory_management")
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
    fun addItem(addData:StockTransOrderHeader){
        data.add(data.size,addData)
        notifyItemInserted(data.size)
        //itemData.count+=1//cookie_data.itemCount+=1
    }


}


