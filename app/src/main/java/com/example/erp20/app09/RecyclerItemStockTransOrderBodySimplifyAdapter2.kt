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

class RecyclerItemStockTransOrderBodySimplifyAdapter2() :
    RecyclerView.Adapter<RecyclerItemStockTransOrderBodySimplifyAdapter2.ViewHolder>() {
    private var itemData: ShowStockTransOrderBody =Gson().fromJson(cookie_data.response_data, ShowStockTransOrderBody::class.java)
    private var data: ArrayList<StockTransOrderBody> =itemData.data
    //var relativeCombobox01=cookie_data.item_id_ComboboxData
    //var relativeCombobox02=cookie_data.item_name_ComboboxData


    val dateF = SimpleDateFormat("yyyy-MM-dd(EEEE)", Locale.TAIWAN)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v= LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_stock_trans_order_body_simplify2,parent,false)
        return ViewHolder(v)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //Log.d("GSON", "msg: ${itemData.data[position]}\n")
        holder.header_id.setText(data[position].header_id)
        holder.header_id.inputType=InputType.TYPE_NULL
        holder.body_id.setText(data[position].body_id.substring(data[position].body_id.indexOf("&")+1,data[position].body_id.length))
        holder.body_id.inputType=InputType.TYPE_NULL
        holder.item_id.setText(data[position].item_id+"\n"+cookie_data.item_name_ComboboxData[cookie_data.item_id_ComboboxData.indexOf(data[position].item_id)])
        holder.item_id.inputType=InputType.TYPE_NULL
        holder.modify_count.setText(data[position].modify_count.toString())
        holder.modify_count.inputType=InputType.TYPE_NULL
        holder.qc_insp_number.setText(data[position].qc_insp_number)
        holder.qc_insp_number.inputType=InputType.TYPE_NULL
        if(data[position].qc_time!=null){
            //date
            var readDateString=data[position].qc_time
            //println(readDateString)
            val readDate=Calendar.getInstance()
            //println(readDateString.subSequence(0,4))
            readDate.set(Calendar.YEAR,readDateString!!.subSequence(0,4).toString().toInt())//yyyy
            // println(readDateString.subSequence(5,7))
            readDate.set(Calendar.MONTH,readDateString!!.subSequence(5,7).toString().toInt()-1)//MM
            // println(readDateString.subSequence(8,10))
            readDate.set(Calendar.DAY_OF_MONTH,readDateString!!.subSequence(8,10).toString().toInt())//dd
            var date=dateF.format(readDate.time)
            holder.temp_qctime=date
            holder.qc_time.setText(date)
        }
        else{
            holder.qc_time.setText(null)
        }
        holder.qc_time.inputType=InputType.TYPE_NULL

        holder.scrapped_count.setText(data[position].scrapped_count.toString())
        holder.scrapped_count.inputType=InputType.TYPE_NULL

        holder.remark.setText(data[position].remark)
        holder.remark.isClickable=false
        holder.remark.isFocusable=false
        holder.remark.isFocusableInTouchMode=false
        holder.remark.isTextInputLayoutFocusedRectEnabled=false
        holder.deletebtn.isVisible=true
        holder.edit_btn.isVisible=true
        holder.next_btn.isVisible=true
        //holder.overbtn.isVisible=true
    }

    override fun getItemCount(): Int {
        return data.size//cookie_data.itemCount
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var header_id=itemView.findViewById<TextInputEditText>(R.id.edit_header_id)
        var body_id=itemView.findViewById<TextInputEditText>(R.id.edit_body_id)
        var item_id=itemView.findViewById<AutoCompleteTextView>(R.id.edit_item_id)
        var modify_count=itemView.findViewById<TextInputEditText>(R.id.edit_modify_count)

        var qc_insp_number=itemView.findViewById<TextInputEditText>(R.id.edit_qc_insp_number)
        var qc_time=itemView.findViewById<TextInputEditText>(R.id.edit_qc_time)

         var scrapped_count=itemView.findViewById<TextInputEditText>(R.id.edit_scrapped_count)


        var edit_btn=itemView.findViewById<Button>(R.id.edit_btn)
        var deletebtn=itemView.findViewById<Button>(R.id.delete_btn)
        var next_btn=itemView.findViewById<Button>(R.id.next_btn)

        var remark=itemView.findViewById<TextInputEditText>(R.id.edit_remark)
        var layout=itemView.findViewById<ConstraintLayout>(R.id.constraint_layout)
        var edit=false
        var temp_qctime=""
        var temp_item_id=""
        lateinit var oldData:StockTransOrderBody
        lateinit var newData:StockTransOrderBody
        init {
            itemView.setOnClickListener{
                val position:Int=adapterPosition
                Log.d("GSON", "msg: ${position}\n")

            }

            val combobox=itemView.resources.getStringArray(R.array.combobox_yes_no)
            val arrayAdapter= ArrayAdapter(itemView.context,R.layout.combobox_item,combobox)
            val arrayAdapter01= ArrayAdapter(itemView.context,R.layout.combobox_item,cookie_data.Filter_item_id_ComboboxData)


            //品檢日期
            qc_time.setOnClickListener {
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
                            qc_time.setText(Date)
                        },year,month,day).show()

                }
            }


            //編輯按鈕
            edit_btn.setOnClickListener {
                when(edit_btn.text){
                    "編輯"->{
                        cookie_data.itemposition=adapterPosition
                        oldData=data[adapterPosition]
                        item_id.setAdapter(arrayAdapter01)
                        item_id.inputType=InputType.TYPE_CLASS_TEXT
                        temp_item_id=item_id.text.toString()
                        qc_insp_number.inputType=InputType.TYPE_CLASS_TEXT
                        temp_qctime=qc_time.text.toString()
                        scrapped_count.inputType=(InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL)
                        scrapped_count.setSelectAllOnFocus(true)
                        remark.isClickable=true
                        remark.isFocusable=true
                        remark.isFocusableInTouchMode=true
                        remark.isTextInputLayoutFocusedRectEnabled=true
                        item_id.requestFocus()
                        edit_btn.text = "完成"
                        edit=true
                        layout.setBackgroundColor(Color.parseColor("#6E5454"))
                        edit_btn.setBackgroundColor(Color.parseColor("#FF3700B3"))
                    }
                    "完成"->{
                        if( item_id.text.toString().trim().isEmpty() || (cookie_data.item_id_name_ComboboxData.indexOf(item_id.text.toString())==-1) ){
                            Toast.makeText(itemView.context,"Item ID 輸入錯誤", Toast.LENGTH_LONG).show()
                        }
                        else {
                            edit=false
                            newData= oldData.copy()//StockTransOrderBody()
                            newData.header_id=header_id.text.toString()
                            header_id.inputType=InputType.TYPE_NULL
                            newData.item_id=item_id.text.toString().substring(0,item_id.text.toString().indexOf("\n"))
                            item_id.setAdapter(null)
                            item_id.inputType=InputType.TYPE_NULL
                            newData.modify_count=scrapped_count.text.toString().toDouble()
                            modify_count.inputType=InputType.TYPE_NULL
                            newData.scrapped_count=scrapped_count.text.toString().toDouble()
                            scrapped_count.inputType=InputType.TYPE_NULL

                            newData.qc_insp_number=qc_insp_number.text.toString()
                            qc_insp_number.inputType=InputType.TYPE_NULL

                            if(qc_time.text.toString()==""){
                                newData.qc_time=null
                            }
                            else{
                                newData.qc_time=qc_time.text.toString().substring(0,qc_time.text.toString().indexOf("("))
                            }
                            qc_time.inputType=InputType.TYPE_NULL

                            newData.remark=remark.text.toString()
                            remark.isClickable=false
                            remark.isFocusable=false
                            remark.isFocusableInTouchMode=false
                            remark.isTextInputLayoutFocusedRectEnabled=false
                            //Log.d("GSON", "msg: ${oldData}\n")
                            //Log.d("GSON", "msg: ${newData.remark}\n")
                            edit_ProductControlOrder("StockTransOrderBody",oldData,newData)//更改資料庫資料
                            when(cookie_data.status){
                                0-> {//成功
                                    modify_count.setText(scrapped_count.text)
                                    data[adapterPosition] = newData//更改渲染資料
                                    //editor.setText(cookie_data.username)
                                    Toast.makeText(itemView.context, cookie_data.msg, Toast.LENGTH_SHORT).show()
                                }
                                1->{//失敗
                                    Toast.makeText(itemView.context, cookie_data.msg, Toast.LENGTH_SHORT).show()
                                    item_id.setText(temp_item_id)
                                    qc_insp_number.setText(oldData.qc_insp_number)
                                    qc_time.setText(temp_qctime)
                                    scrapped_count.setText(oldData.scrapped_count.toString())
                                    remark.setText(oldData.remark)
                                }
                            }
                            //Log.d("GSON", "msg: ${itemData.data}\n")
                            edit_btn.text = "編輯"
                            layout.setBackgroundColor(Color.parseColor("#977C7C"))
                            edit_btn.setBackgroundColor(Color.parseColor("#FF6200EE"))
                        }

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
                    over_ProductControlOrder("StockTransOrderBody",data[adapterPosition])
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

            }*/
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
        data.add(data.size,addData)
        notifyItemInserted(data.size)
        cookie_data.recyclerView.smoothScrollToPosition(data.size)
    }


}


