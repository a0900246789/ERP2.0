package com.example.erp20.app07
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
import androidx.constraintlayout.widget.ConstraintLayout
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
import java.text.SimpleDateFormat
import java.util.*

class RecyclerItemStockTransOrderBodyWithHeaderAdapter(FilterTopic:String,FilterContent:String) :
    RecyclerView.Adapter<RecyclerItemStockTransOrderBodyWithHeaderAdapter.ViewHolder>() {
    private var itemData: ShowStockTransOrderBody =Gson().fromJson(cookie_data.response_data, ShowStockTransOrderBody::class.java)
    private var data: ArrayList<StockTransOrderBody> =itemData.data
    val dateF = SimpleDateFormat("yyyy-MM-dd(EEEE)", Locale.TAIWAN)
    val timeF = SimpleDateFormat("HH:mm:ss", Locale.TAIWAN)
    val dateF2 = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    init {
        // println(SelectFilter)
        data=filter(data,FilterTopic,FilterContent)//過濾date<filter && main_trans_code!=M03
        data=sort(data)

    }

    fun filter(data:ArrayList<StockTransOrderBody>,filterTopic:String,filterContent:String): ArrayList<StockTransOrderBody>{
        var ArrayList:ArrayList<StockTransOrderBody> = ArrayList<StockTransOrderBody>()
        if(filterTopic=="需求日期"){
            for(i in 0 until data.size){
                var date1=dateF2.parse(cookie_data.StockTransOrderHeader_date_ComboboxData[cookie_data.StockTransOrderHeader_id_ComboboxData.indexOf(data[i].header_id)])
                var date2=dateF2.parse(filterContent)
                if(date1.compareTo(date2)>=0 && cookie_data.StockTransOrderHeader_main_trans_code_ComboboxData[cookie_data.StockTransOrderHeader_id_ComboboxData.indexOf(data[i].header_id)]=="M02"){
                    ArrayList.add(data[i])
                }
            }
        }
        else if(filterTopic=="料件編號/名稱"){
            for(i in 0 until data.size){
                if(data[i].item_id==filterContent && cookie_data.StockTransOrderHeader_main_trans_code_ComboboxData[cookie_data.StockTransOrderHeader_id_ComboboxData.indexOf(data[i].header_id)]=="M02"){
                    ArrayList.add(data[i])
                }
            }
        }
        else if(filterTopic=="生產管制單編號"){
            for(i in 0 until data.size){
                if(cookie_data.StockTransOrderHeader_prod_ctrl_order_number_ComboboxData[cookie_data.StockTransOrderHeader_id_ComboboxData.indexOf(data[i].header_id)]==filterContent && cookie_data.StockTransOrderHeader_main_trans_code_ComboboxData[cookie_data.StockTransOrderHeader_id_ComboboxData.indexOf(data[i].header_id)]=="M02"){
                    ArrayList.add(data[i])
                }
            }
        }
        else if(filterTopic=="需求單位"){
            for(i in 0 until data.size){
                if(cookie_data.StockTransOrderHeader_dept_ComboboxData[cookie_data.StockTransOrderHeader_id_ComboboxData.indexOf(data[i].header_id)]==filterContent && cookie_data.StockTransOrderHeader_main_trans_code_ComboboxData[cookie_data.StockTransOrderHeader_id_ComboboxData.indexOf(data[i].header_id)]=="M02"){
                    ArrayList.add(data[i])
                }
            }
        }


        return ArrayList
    }
    fun sort(data:ArrayList<StockTransOrderBody>): ArrayList<StockTransOrderBody>{
        var sortedList:List<StockTransOrderBody> = data

        sortedList  = data.sortedWith(
            compareBy(
                { cookie_data.StockTransOrderHeader_date_ComboboxData[cookie_data.StockTransOrderHeader_id_ComboboxData.indexOf(it.header_id)]  },
                { it.store_area},
                { it.item_id},
                { cookie_data.StockTransOrderHeader_prod_ctrl_order_number_ComboboxData[cookie_data.StockTransOrderHeader_id_ComboboxData.indexOf(it.header_id)] }
            )
        )

        return sortedList.toCollection(java.util.ArrayList())
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v= LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_stock_trans_order_body_with_header,parent,false)
        return ViewHolder(v)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //Log.d("GSON", "msg: ${itemData.data[position]}\n")
        if(cookie_data.StockTransOrderHeader_date_ComboboxData[cookie_data.StockTransOrderHeader_id_ComboboxData.indexOf(data[position].header_id)]!=null){
            //date
            var readDateString=cookie_data.StockTransOrderHeader_date_ComboboxData[cookie_data.StockTransOrderHeader_id_ComboboxData.indexOf(data[position].header_id)]
            //println(readDateString)
            val readDate=Calendar.getInstance()
            //println(readDateString.subSequence(0,4))
            readDate.set(Calendar.YEAR,readDateString!!.subSequence(0,4).toString().toInt())//yyyy
            // println(readDateString.subSequence(5,7))
            readDate.set(Calendar.MONTH,readDateString!!.subSequence(5,7).toString().toInt()-1)//MM
            // println(readDateString.subSequence(8,10))
            readDate.set(Calendar.DAY_OF_MONTH,readDateString!!.subSequence(8,10).toString().toInt())//dd
            var date=dateF.format(readDate.time)
            holder.date.setText(date)
        }
        else{
            holder.date.setText(null)
        }
        holder.date.inputType=InputType.TYPE_NULL
        holder.item_id.setText(data[position].item_id)
        holder.item_id.inputType=InputType.TYPE_NULL
        holder.item_name.setText(cookie_data.item_name_ComboboxData[cookie_data.item_id_ComboboxData.indexOf(data[position].item_id)])
        holder.item_name.inputType=InputType.TYPE_NULL
        holder.modify_count.setText(data[position].modify_count.toString())
        holder.modify_count.inputType=InputType.TYPE_NULL
        if(data[position].store_area!=""){
            holder.store_area.setText(data[position].store_area+" "+
                                      cookie_data.store_area_name_ComboboxData[cookie_data.store_area_ComboboxData.indexOf(data[position].store_area)])
        }
        else{
            holder.store_area.setText("")
        }
        holder.store_area.inputType=InputType.TYPE_NULL
        holder.store_local.setText(data[position].store_local)
        holder.store_local.inputType=InputType.TYPE_NULL
        holder.qc_insp_number.setText(data[position].qc_insp_number)
        holder.qc_insp_number.inputType=InputType.TYPE_NULL
        holder.prod_ctrl_order_number.setText(cookie_data.StockTransOrderHeader_prod_ctrl_order_number_ComboboxData[cookie_data.StockTransOrderHeader_id_ComboboxData.indexOf(data[position].header_id)])
        holder.prod_ctrl_order_number.inputType=InputType.TYPE_NULL
        holder.purchase_order_id.setText(cookie_data.StockTransOrderHeader_purchase_order_id_ComboboxData[cookie_data.StockTransOrderHeader_id_ComboboxData.indexOf(data[position].header_id)])
        holder.purchase_order_id.inputType=InputType.TYPE_NULL
        holder.body_id.setText(data[position].body_id)
        holder.body_id.inputType=InputType.TYPE_NULL
        holder.is_closed.setText(data[position].is_closed.toString())
        holder.is_closed.inputType=InputType.TYPE_NULL
        holder.is_rework.setText(data[position].is_rework.toString())
        holder.is_rework.inputType=InputType.TYPE_NULL
        holder.dept.setText(cookie_data.StockTransOrderHeader_dept_ComboboxData[cookie_data.StockTransOrderHeader_id_ComboboxData.indexOf(data[position].header_id)]+" "+
                            cookie_data.pline_name_ComboboxData[cookie_data.pline_id_ComboboxData.indexOf(cookie_data.StockTransOrderHeader_dept_ComboboxData[cookie_data.StockTransOrderHeader_id_ComboboxData.indexOf(data[position].header_id)])]
        )
        holder.dept.inputType=InputType.TYPE_NULL
        holder.main_trans_code.setText(cookie_data.inv_code_name_m_ComboboxData[cookie_data.inv_code_m_ComboboxData.indexOf(cookie_data.StockTransOrderHeader_main_trans_code_ComboboxData[cookie_data.StockTransOrderHeader_id_ComboboxData.indexOf(data[position].header_id)])]
        )
        holder.main_trans_code.inputType=InputType.TYPE_NULL
        holder.sec_trans_code.setText(cookie_data.inv_code_name_s_ComboboxData[cookie_data.inv_code_s_ComboboxData.indexOf(cookie_data.StockTransOrderHeader_sec_trans_code_ComboboxData[cookie_data.StockTransOrderHeader_id_ComboboxData.indexOf(data[position].header_id)])]
        )
        holder.sec_trans_code.inputType=InputType.TYPE_NULL


       /* holder.creator.setText(data[position].creator)
        holder.creator_time.setText(data[position].create_time)
        holder.editor.setText(data[position].editor)
        holder.editor_time.setText(data[position].edit_time)
        holder.lock.setText(data[position].Lock.toString())
        holder.lock_time.setText(data[position].lock_time)
        holder.invalid.setText(data[position].invalid.toString())
        holder.invalid_time.setText(data[position].invalid_time)
        holder.close_time.setText(data[position].close_time)*/
        holder.remark.setText(data[position].remark)
        holder.remark.isClickable=false
        holder.remark.isFocusable=false
        holder.remark.isFocusableInTouchMode=false
        holder.remark.isTextInputLayoutFocusedRectEnabled=false
        holder.deletebtn.isVisible=true
        holder.edit_btn.isVisible=true
        holder.next_btn.isVisible=true
        holder.overbtn.isVisible=true
    }

    override fun getItemCount(): Int {
        return data.size//cookie_data.itemCount
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var date=itemView.findViewById<TextInputEditText>(R.id.edit_date)
        var dept=itemView.findViewById<TextInputEditText>(R.id.edit_dept)
        var body_id=itemView.findViewById<TextInputEditText>(R.id.edit_body_id)
        var item_id=itemView.findViewById<TextInputEditText>(R.id.edit_item_id)
        var item_name=itemView.findViewById<TextInputEditText>(R.id.edit_item_name)
        var modify_count=itemView.findViewById<TextInputEditText>(R.id.edit_modify_count)
        var main_trans_code=itemView.findViewById<TextInputEditText>(R.id.edit_main_trans_code)
        var sec_trans_code=itemView.findViewById<TextInputEditText>(R.id.edit_sec_trans_code)
        var store_area =itemView.findViewById<AutoCompleteTextView>(R.id.edit_store_area)
        var store_local=itemView.findViewById<AutoCompleteTextView>(R.id.edit_store_local)
        var qc_insp_number=itemView.findViewById<TextInputEditText>(R.id.edit_qc_insp_number)
        var prod_ctrl_order_number =itemView.findViewById<TextInputEditText>(R.id.edit_prod_ctrl_order_number)
        var purchase_order_id =itemView.findViewById<TextInputEditText>(R.id.edit_purchase_order_id)
        var is_rework=itemView.findViewById<TextInputEditText>(R.id.edit_is_rework)
        var is_closed=itemView.findViewById<TextInputEditText>(R.id.edit_is_closed)

        var edit_btn=itemView.findViewById<Button>(R.id.edit_btn)
        var deletebtn=itemView.findViewById<Button>(R.id.delete_btn)
        var next_btn=itemView.findViewById<Button>(R.id.next_btn)
        var overbtn=itemView.findViewById<Button>(R.id.over_btn)
        /*var creator=itemView.findViewById<AutoCompleteTextView>(R.id.edit_creator)
        var creator_time=itemView.findViewById<AutoCompleteTextView>(R.id.edit_create_time)
        var editor=itemView.findViewById<AutoCompleteTextView>(R.id.edit_editor)
        var editor_time=itemView.findViewById<AutoCompleteTextView>(R.id.edit_edit_time)
        var lock=itemView.findViewById<AutoCompleteTextView>(R.id.edit_lock)
        var lock_time=itemView.findViewById<AutoCompleteTextView>(R.id.edit_lock_time)
        var invalid=itemView.findViewById<AutoCompleteTextView>(R.id.edit_invalid)
        var invalid_time=itemView.findViewById<AutoCompleteTextView>(R.id.edit_invalid_time)
        var close_time=itemView.findViewById<AutoCompleteTextView>(R.id.edit_close_time)*/

        var remark=itemView.findViewById<TextInputEditText>(R.id.edit_remark)
        var layout=itemView.findViewById<ConstraintLayout>(R.id.constraint_layout)
        var tempArea=""
        lateinit var oldData:StockTransOrderBody
        lateinit var newData:StockTransOrderBody
        init {
            itemView.setOnClickListener{
                val position:Int=adapterPosition
                Log.d("GSON", "msg: ${position}\n")

            }

            val combobox=itemView.resources.getStringArray(R.array.combobox_yes_no)
            val arrayAdapter= ArrayAdapter(itemView.context,R.layout.combobox_item,combobox)
            val arrayAdapter01= ArrayAdapter(itemView.context,R.layout.combobox_item,cookie_data.store_area_id_name_ComboboxData)
            val arrayAdapter02= ArrayAdapter(itemView.context,R.layout.combobox_item,cookie_data.store_local_ComboboxData)


            //編輯按鈕
            edit_btn.setOnClickListener {
                when(edit_btn.text){
                    "編輯"->{
                        cookie_data.itemposition=adapterPosition
                        oldData=data[adapterPosition]
                        //Log.d("GSON", "msg: ${oldData.id}\n")
                        //header_id.inputType=InputType.TYPE_CLASS_TEXT
                        //item_id.setAdapter(arrayAdapter01)
                        //modify_count.inputType=InputType.TYPE_CLASS_NUMBER
                        //main_trans_code.setAdapter(arrayAdapter02)
                        //sec_trans_code.setAdapter(arrayAdapter03)
                        tempArea=store_area.text.toString()
                        store_area.setAdapter(arrayAdapter01)
                        store_local.setAdapter(arrayAdapter02)
                        qc_insp_number.inputType=InputType.TYPE_CLASS_TEXT
                        //qc_time.inputType=InputType.TYPE_CLASS_DATETIME
                        //ok_count.inputType=InputType.TYPE_CLASS_NUMBER
                        //ng_count.inputType=InputType.TYPE_CLASS_NUMBER
                        //scrapped_count.inputType=InputType.TYPE_CLASS_NUMBER
                        //is_rework.setAdapter(arrayAdapter)
                        //is_rework.isClickable=true


                        /*remark.isClickable=true
                        remark.isFocusable=true
                        remark.isFocusableInTouchMode=true
                        remark.isTextInputLayoutFocusedRectEnabled=true*/
                        store_area.requestFocus()
                        edit_btn.text = "完成"
                        layout.setBackgroundColor(Color.parseColor("#6E5454"))
                        edit_btn.setBackgroundColor(Color.parseColor("#FF3700B3"))
                    }
                    "完成"->{
                        newData= oldData.copy()//StockTransOrderBody()
                        /*newData.header_id=header_id.text.toString()
                        header_id.inputType=InputType.TYPE_NULL
                        newData.item_id=item_id.text.toString()
                        item_id.setAdapter(null)
                        newData.modify_count=modify_count.text.toString().toInt()
                        modify_count.inputType=InputType.TYPE_NULL
                        newData.main_trans_code=main_trans_code.text.toString()
                        main_trans_code.setAdapter(null)
                        newData.sec_trans_code=sec_trans_code.text.toString()
                        sec_trans_code.setAdapter(null)*/
                        if(store_area.text.toString()==""){
                            newData.store_area=""
                        }
                        else{
                            newData.store_area=store_area.text.toString().substring(0,store_area.text.toString().indexOf(" "))
                        }
                        store_area.setAdapter(null)
                        newData.store_local=store_local.text.toString()
                        store_local.setAdapter(null)
                        newData.qc_insp_number=qc_insp_number.text.toString()
                        qc_insp_number.inputType=InputType.TYPE_NULL
                        /*if(qc_time.text.toString()==""){
                            newData.qc_time=null
                        }
                        else{
                            newData.qc_time=qc_time.text.toString()
                        }
                        qc_time.inputType=InputType.TYPE_NULL
                        newData.ok_count=ok_count.text.toString().toInt()
                        ok_count.inputType=InputType.TYPE_NULL
                        newData.ng_count=ng_count.text.toString().toInt()
                        ng_count.inputType=InputType.TYPE_NULL
                        newData.scrapped_count=scrapped_count.text.toString().toInt()
                        scrapped_count.inputType=InputType.TYPE_NULL
                        newData.is_rework=is_rework.text.toString().toBoolean()
                        is_rework.isClickable=false
                        is_rework.setAdapter(null)


                        newData.remark=remark.text.toString()
                        remark.isClickable=false
                        remark.isFocusable=false
                        remark.isFocusableInTouchMode=false
                        remark.isTextInputLayoutFocusedRectEnabled=false*/
                        //Log.d("GSON", "msg: ${oldData}\n")
                        //Log.d("GSON", "msg: ${newData.remark}\n")
                        edit_ProductControlOrder("StockTransOrderBody",oldData,newData)//更改資料庫資料
                        when(cookie_data.status){
                            0-> {//成功
                                data[adapterPosition] = newData//更改渲染資料
                                //editor.setText(cookie_data.username)
                                Toast.makeText(itemView.context, cookie_data.msg, Toast.LENGTH_SHORT).show()
                            }
                            1->{//失敗
                                Toast.makeText(itemView.context, cookie_data.msg, Toast.LENGTH_SHORT).show()
                                /*header_id.setText(oldData.header_id)
                                item_id.setText(oldData.item_id)
                                modify_count.setText(oldData.modify_count.toString())
                                main_trans_code.setText(oldData.main_trans_code)
                                sec_trans_code.setText(oldData.sec_trans_code)*/
                                store_area.setText(tempArea)
                                store_local.setText(oldData.store_local)
                                qc_insp_number.setText(oldData.qc_insp_number)
                               /* qc_time.setText(oldData.qc_time)
                                ok_count.setText(oldData.ok_count.toString())
                                ng_count.setText(oldData.ng_count.toString())
                                scrapped_count.setText(oldData.scrapped_count.toString())
                                is_rework.setText(oldData.is_rework.toString())

                                remark.setText(oldData.remark)*/
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
                    delete_ProductControlOrder("StockTransOrderBody",data[adapterPosition])
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
            //下一筆
            next_btn.setOnClickListener {
                if(adapterPosition+1<=data.size){
                    cookie_data.recyclerView.smoothScrollToPosition(adapterPosition+1)
                }

            }

           /* //鎖定按鈕
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
                    lock_ProductControlOrder("StockTransOrderBody",data[adapterPosition])
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
                mAlertDialog.setTitle("發料") //set alertdialog title
                mAlertDialog.setMessage("確定要發料?！") //set alertdialog message
                mAlertDialog.setPositiveButton("NO") { dialog, id ->
                    dialog.dismiss()
                }
                mAlertDialog.setNegativeButton("YES") { dialog, id ->
                    over_ProductControlOrder("StockTransOrderBody",data[adapterPosition])
                    when(cookie_data.status){
                        0-> {//成功
                            //is_closed.setText("true")
                           // close_time.setText(Calendar.getInstance().getTime().toString())
                            data.removeAt(adapterPosition)
                            notifyItemRemoved(adapterPosition)
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
        private fun edit_ProductControlOrder(operation:String,oldData:StockTransOrderBody,newData:StockTransOrderBody) {
            val old =JSONObject()
            old.put("header_id",oldData.header_id)
            old.put("body_id",oldData.body_id)
            old.put("item_id",oldData.item_id)
            old.put("modify_count",oldData.modify_count)
            old.put("main_trans_code",oldData.main_trans_code)
            old.put("sec_trans_code",oldData.sec_trans_code)
            old.put("store_area",oldData.store_area)
            old.put("store_local",oldData.store_local)
            old.put("qc_insp_number",oldData.qc_insp_number)
            old.put("qc_time",oldData.qc_time)
            old.put("ok_count",oldData.ok_count)
            old.put("ng_count",oldData.ng_count)
            old.put("scrapped_count",oldData.scrapped_count)
            old.put("is_rework",oldData.is_rework)


            old.put("remark",oldData.remark)
            val new =JSONObject()
            new.put("header_id",newData.header_id)
            new.put("body_id",newData.body_id)
            new.put("item_id",newData.item_id)
            new.put("modify_count",newData.modify_count)
            new.put("main_trans_code",newData.main_trans_code)
            new.put("sec_trans_code",newData.sec_trans_code)
            new.put("store_area",newData.store_area)
            new.put("store_local",newData.store_local)
            new.put("qc_insp_number",newData.qc_insp_number)
            new.put("qc_time",newData.qc_time)
            new.put("ok_count",newData.ok_count)
            new.put("ng_count",newData.ng_count)
            new.put("scrapped_count",newData.scrapped_count)
            new.put("is_rework",newData.is_rework)

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

            }
            val responseInfo = Gson().fromJson(cookie_data.response_data, Response::class.java)
            cookie_data.status=responseInfo.status
            cookie_data.msg=responseInfo.msg


        }
        private fun delete_ProductControlOrder(operation:String,deleteData:StockTransOrderBody) {
            val delete = JSONObject()
            delete.put("header_id",deleteData.header_id)
            delete.put("body_id",deleteData.body_id)
            delete.put("item_id",deleteData.item_id)
            delete.put("modify_count",deleteData.modify_count)
            delete.put("main_trans_code",deleteData.main_trans_code)
            delete.put("sec_trans_code",deleteData.sec_trans_code)
            delete.put("store_area",deleteData.store_area)
            delete.put("store_local",deleteData.store_local)
            delete.put("qc_insp_number",deleteData.qc_insp_number)
            delete.put("qc_time",deleteData.qc_time)
            delete.put("ok_count",deleteData.ok_count)
            delete.put("ng_count",deleteData.ng_count)
            delete.put("scrapped_count",deleteData.scrapped_count)
            delete.put("is_rework",deleteData.is_rework)

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
        private fun lock_ProductControlOrder(operation:String,lockData:StockTransOrderBody) {
            val lock = JSONObject()
            lock.put("header_id",lockData.header_id)
            lock.put("body_id",lockData.body_id)
            lock.put("item_id",lockData.item_id)
            lock.put("modify_count",lockData.modify_count)
            lock.put("main_trans_code",lockData.main_trans_code)
            lock.put("sec_trans_code",lockData.sec_trans_code)
            lock.put("store_area",lockData.store_area)
            lock.put("store_local",lockData.store_local)
            lock.put("qc_insp_number",lockData.qc_insp_number)
            lock.put("qc_time",lockData.qc_time)
            lock.put("ok_count",lockData.ok_count)
            lock.put("ng_count",lockData.ng_count)
            lock.put("scrapped_count",lockData.scrapped_count)
            lock.put("is_rework",lockData.is_rework)

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
        private fun over_ProductControlOrder(operation:String,overData:StockTransOrderBody) {
            val over = JSONObject()
            over.put("header_id",overData.header_id)
            over.put("body_id",overData.body_id)
            over.put("item_id",overData.item_id)
            over.put("modify_count",overData.modify_count)
            over.put("main_trans_code",overData.main_trans_code)
            over.put("sec_trans_code",overData.sec_trans_code)
            over.put("store_area",overData.store_area)
            over.put("store_local",overData.store_local)
            over.put("qc_insp_number",overData.qc_insp_number)
            over.put("qc_time",overData.qc_time)
            over.put("ok_count",overData.ok_count)
            over.put("ng_count",overData.ng_count)
            over.put("scrapped_count",overData.scrapped_count)
            over.put("is_rework",overData.is_rework)

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
    fun addItem(addData:StockTransOrderBody){
        data.add(itemData.count,addData)
        notifyItemInserted(itemData.count)
        itemData.count+=1//cookie_data.itemCount+=1
    }


}


