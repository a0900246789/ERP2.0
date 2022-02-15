package com.example.erp20.app13
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.Button
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
import kotlin.collections.ArrayList
import android.widget.ArrayAdapter


class RecyclerItemBookingNoticeLogAdapter() :
    RecyclerView.Adapter<RecyclerItemBookingNoticeLogAdapter.ViewHolder>() {
    private var itemData: ShowBookingNoticeLog =Gson().fromJson(cookie_data.response_data, ShowBookingNoticeLog::class.java)
    private var data: ArrayList<BookingNoticeLog> =itemData.data

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v= LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_booking_notice_log,parent,false)
        return ViewHolder(v)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        //Log.d("GSON", "msg: ${itemData.data[position]}\n")
        holder.code.setText(data[position].code)
        holder.code.inputType=InputType.TYPE_NULL
        holder.date_time.setText(data[position].date_time)
        holder.date_time.inputType=InputType.TYPE_NULL
        holder.shipping_number.setText(data[position].shipping_number)
        holder.shipping_number.inputType=InputType.TYPE_NULL
        holder.shipping_order_number_old.setText(data[position].shipping_order_number_old)
        holder.shipping_order_number_old.inputType=InputType.TYPE_NULL
        holder.shipping_order_number_new.setText(data[position].shipping_order_number_new)
        holder.shipping_order_number_new.inputType=InputType.TYPE_NULL
        holder.header_id_old.setText(data[position].header_id_old)
        holder.header_id_old.inputType=InputType.TYPE_NULL
        holder.header_id_new.setText(data[position].header_id_new)
        holder.header_id_new.inputType=InputType.TYPE_NULL



        holder.creator.setText(data[position].creator)
        holder.creator_time.setText(data[position].create_time)
        holder.editor.setText(data[position].editor)
        holder.editor_time.setText(data[position].edit_time)
        holder.lock.setText(data[position].Lock.toString())
        holder.lock_time.setText(data[position].lock_time)
        holder.invalid.setText(data[position].invalid.toString())
        holder.invalid_time.setText(data[position].invalid_time)
        holder.remark.setText(data[position].remark)
        holder.remark.isClickable=false
        holder.remark.isFocusable=false
        holder.remark.isFocusableInTouchMode=false
        holder.remark.isTextInputLayoutFocusedRectEnabled=false
       // holder.deletebtn.isVisible=true
       // holder.edit_btn.isVisible=true
        //holder.lockbtn.isVisible=true

    }

    override fun getItemCount(): Int {
        return itemData.count//cookie_data.itemCount
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var code=itemView.findViewById<TextInputEditText>(R.id.edit_code)
        var date_time=itemView.findViewById<TextInputEditText>(R.id.edit_date_time)
        var shipping_number=itemView.findViewById<TextInputEditText>(R.id.edit_shipping_number)
        var shipping_order_number_old=itemView.findViewById<TextInputEditText>(R.id.edit_shipping_order_number_old)
        var shipping_order_number_new=itemView.findViewById<TextInputEditText>(R.id.edit_shipping_order_number_new)
        var header_id_old=itemView.findViewById<TextInputEditText>(R.id.edit_header_id_old)
        var header_id_new=itemView.findViewById<TextInputEditText>(R.id.edit_header_id_new)

        var edit_btn=itemView.findViewById<Button>(R.id.edit_btn)
        var deletebtn=itemView.findViewById<Button>(R.id.delete_btn)
        var lockbtn=itemView.findViewById<Button>(R.id.lock_btn)
        var creator=itemView.findViewById<AutoCompleteTextView>(R.id.edit_creator)
        var creator_time=itemView.findViewById<AutoCompleteTextView>(R.id.edit_create_time)
        var editor=itemView.findViewById<AutoCompleteTextView>(R.id.edit_editor)
        var editor_time=itemView.findViewById<AutoCompleteTextView>(R.id.edit_edit_time)
        var lock=itemView.findViewById<AutoCompleteTextView>(R.id.edit_lock)
        var lock_time=itemView.findViewById<AutoCompleteTextView>(R.id.edit_lock_time)
        var invalid=itemView.findViewById<AutoCompleteTextView>(R.id.edit_invalid)
        var invalid_time=itemView.findViewById<AutoCompleteTextView>(R.id.edit_invalid_time)
        var remark=itemView.findViewById<TextInputEditText>(R.id.edit_remark)
        lateinit var oldData:BookingNoticeLog
        lateinit var newData:BookingNoticeLog
        init {
            itemView.setOnClickListener{
                val position:Int=adapterPosition
                Log.d("GSON", "msg: ${position}\n")

            }

            val combobox=itemView.resources.getStringArray(R.array.combobox_yes_no)
            val arrayAdapter= ArrayAdapter(itemView.context,R.layout.combobox_item,combobox)

/*
            //編輯按鈕
            edit_btn.setOnClickListener {
                when(edit_btn.text){
                    "編輯"->{
                        oldData=itemData.data[adapterPosition]
                        //Log.d("GSON", "msg: ${oldData.id}\n")
                        //_id.inputType=InputType.TYPE_CLASS_TEXT
                        shipping_number.inputType=InputType.TYPE_CLASS_TEXT
                        shipping_order_number.inputType=InputType.TYPE_CLASS_TEXT
                        date.inputType=InputType.TYPE_DATETIME_VARIATION_DATE
                        customer_poNo.inputType=InputType.TYPE_CLASS_TEXT


                        remark.isClickable=true
                        remark.isFocusable=true
                        remark.isFocusableInTouchMode=true
                        remark.isTextInputLayoutFocusedRectEnabled=true
                        shipping_number.requestFocus()
                        edit_btn.text = "完成"
                    }
                    "完成"->{
                        newData= BookingNoticeHeader()
                        newData._id=_id.text.toString()
                        _id.inputType=InputType.TYPE_NULL
                        newData.shipping_number=shipping_number.text.toString()
                        shipping_number.inputType=InputType.TYPE_NULL
                        newData.shipping_order_number=shipping_order_number.text.toString()
                        shipping_order_number.inputType=InputType.TYPE_NULL
                        newData.date=date.text.toString()
                        date.inputType=InputType.TYPE_NULL
                        newData.customer_poNo=customer_poNo.text.toString()
                        customer_poNo.inputType=InputType.TYPE_NULL


                        newData.remark=remark.text.toString()
                        remark.isClickable=false
                        remark.isFocusable=false
                        remark.isFocusableInTouchMode=false
                        remark.isTextInputLayoutFocusedRectEnabled=false
                        //Log.d("GSON", "msg: ${oldData}\n")
                        //Log.d("GSON", "msg: ${newData.remark}\n")
                        edit_BookingNotice("BookingNoticeHeader",oldData,newData)//更改資料庫資料
                        when(cookie_data.status){
                            0-> {//成功
                                itemData.data[adapterPosition] = newData//更改渲染資料
                                editor.setText(cookie_data.username)
                                Toast.makeText(itemView.context, cookie_data.msg, Toast.LENGTH_SHORT).show()
                            }
                            1->{//失敗
                                Toast.makeText(itemView.context, cookie_data.msg, Toast.LENGTH_SHORT).show()
                                _id.setText(oldData._id)
                                shipping_number.setText(oldData.shipping_number)
                                shipping_order_number.setText(oldData.shipping_order_number)
                                date.setText(oldData.date)
                                customer_poNo.setText(oldData.customer_poNo)


                                remark.setText(oldData.remark)
                            }
                        }
                        //Log.d("GSON", "msg: ${itemData.data}\n")
                        edit_btn.text = "編輯"
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
                    delete_BookingNotice("BookingNoticeHeader",data[adapterPosition])
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
                    lock_BookingNotice("BookingNoticeHeader",data[adapterPosition])
                    when(cookie_data.status){
                        0-> {//成功
                            lock.setText("true")
                            lock_time.setText(Calendar.getInstance().getTime().toString())
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
        private fun edit_BookingNotice(operation:String,oldData:BookingNoticeHeader,newData:BookingNoticeHeader) {
            val old =JSONObject()
            old.put("_id",oldData._id)
            old.put("shipping_number",oldData.shipping_number)
            old.put("shipping_order_number",oldData.shipping_order_number)
            old.put("date",oldData.date)
            old.put("customer_poNo",oldData.customer_poNo)


            old.put("remark",oldData.remark)
            val new =JSONObject()
            new.put("_id",newData._id)
            new.put("shipping_number",newData.shipping_number)
            new.put("shipping_order_number",newData.shipping_order_number)
            new.put("date",newData.date)
            new.put("customer_poNo",newData.customer_poNo)


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

            }
            val responseInfo = Gson().fromJson(cookie_data.response_data, Response::class.java)
            cookie_data.status=responseInfo.status
            cookie_data.msg=responseInfo.msg


        }
        private fun delete_BookingNotice(operation:String,deleteData:BookingNoticeHeader) {
            val delete = JSONObject()
            delete.put("_id",deleteData._id)
            delete.put("shipping_number",deleteData.shipping_number)
            delete.put("shipping_order_number",deleteData.shipping_order_number)
            delete.put("date",deleteData.date)
            delete.put("customer_poNo",deleteData.customer_poNo)

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
        private fun lock_BookingNotice(operation:String,lockData:BookingNoticeHeader) {
            val lock = JSONObject()
            lock.put("_id",lockData._id)
            lock.put("shipping_number",lockData.shipping_number)
            lock.put("shipping_order_number",lockData.shipping_order_number)
            lock.put("date",lockData.date)
            lock.put("customer_poNo",lockData.customer_poNo)

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


    }

    fun addItem(addData:BookingNoticeLog){
        data.add(itemData.count,addData)
        notifyItemInserted(itemData.count)
        itemData.count+=1//cookie_data.itemCount+=1
    }

}


