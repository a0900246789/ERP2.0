package com.example.erp20.RecyclerAdapter
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


class RecyclerItemPurchaseInlineOrderBodyAdapter() :
    RecyclerView.Adapter<RecyclerItemPurchaseInlineOrderBodyAdapter.ViewHolder>() {
    private var itemData: ShowPurchaseInlineOrderBody =Gson().fromJson(cookie_data.response_data, ShowPurchaseInlineOrderBody::class.java)
    private var data: ArrayList<PurchaseInlineOrderBody> =itemData.data
    var relativeCombobox01=cookie_data.item_id_ComboboxData
    var relativeCombobox02=cookie_data.item_name_ComboboxData

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemPurchaseInlineOrderBodyAdapter.ViewHolder {
        val v= LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_purchase_inline_order_body,parent,false)
        return ViewHolder(v)

    }

    override fun onBindViewHolder(holder: RecyclerItemPurchaseInlineOrderBodyAdapter.ViewHolder, position: Int) {

        //Log.d("GSON", "msg: ${itemData.data[position]}\n")
        holder.body_id.setText(data[position].body_id)
        holder.body_id.inputType=InputType.TYPE_NULL
        holder.header_id.setText(data[position].header_id)
        holder.header_id.inputType=InputType.TYPE_NULL
        holder.section.setText(data[position].section)
        holder.section.inputType=InputType.TYPE_NULL
        holder.item_id.setText(data[position].item_id)
        holder.item_id.inputType=InputType.TYPE_NULL
        holder.item_name.setText(data[position].item_name)
        holder.item_name.inputType=InputType.TYPE_NULL
        holder.pre_delivery_date.setText(data[position].pre_delivery_date)
        holder.pre_delivery_date.inputType=InputType.TYPE_NULL
        holder.purchase_quantity.setText(data[position].purchase_quantity.toString())
        holder.purchase_quantity.inputType=InputType.TYPE_NULL
        holder.accumulated_deduction.setText(data[position].accumulated_deduction.toString())
        holder.accumulated_deduction.inputType=InputType.TYPE_NULL
        holder.unit_of_measurement.setText(data[position].unit_of_measurement)
        holder.unit_of_measurement.inputType=InputType.TYPE_NULL



        holder.creator.setText(data[position].creator)
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
        holder.remark.isTextInputLayoutFocusedRectEnabled=false
        holder.deletebtn.isVisible=true
        holder.edit_btn.isVisible=true
        holder.lockbtn.isVisible=true
        holder.overbtn.isVisible=true
    }

    override fun getItemCount(): Int {
        return itemData.count//cookie_data.itemCount
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var body_id=itemView.findViewById<TextInputEditText>(R.id.edit_body_id)
        var header_id=itemView.findViewById<TextInputEditText>(R.id.edit_header_id)
        var section=itemView.findViewById<TextInputEditText>(R.id.edit_section)
        var item_id=itemView.findViewById<AutoCompleteTextView>(R.id.edit_item_id)
        var item_name=itemView.findViewById<AutoCompleteTextView>(R.id.edit_item_name)
        var pre_delivery_date=itemView.findViewById<TextInputEditText>(R.id.edit_pre_delivery_date)
        var purchase_quantity=itemView.findViewById<TextInputEditText>(R.id.edit_purchase_quantity)
        var accumulated_deduction=itemView.findViewById<TextInputEditText>(R.id.edit_accumulated_deduction)
        var unit_of_measurement=itemView.findViewById<TextInputEditText>(R.id.edit_unit_of_measurement)


        var edit_btn=itemView.findViewById<Button>(R.id.edit_btn)
        var deletebtn=itemView.findViewById<Button>(R.id.delete_btn)
        var lockbtn=itemView.findViewById<Button>(R.id.lock_btn)
        var overbtn=itemView.findViewById<Button>(R.id.over_btn)
        var creator=itemView.findViewById<AutoCompleteTextView>(R.id.edit_creator)
        var creator_time=itemView.findViewById<AutoCompleteTextView>(R.id.edit_create_time)
        var editor=itemView.findViewById<AutoCompleteTextView>(R.id.edit_editor)
        var editor_time=itemView.findViewById<AutoCompleteTextView>(R.id.edit_edit_time)
        var lock=itemView.findViewById<AutoCompleteTextView>(R.id.edit_lock)
        var lock_time=itemView.findViewById<AutoCompleteTextView>(R.id.edit_lock_time)
        var invalid=itemView.findViewById<AutoCompleteTextView>(R.id.edit_invalid)
        var invalid_time=itemView.findViewById<AutoCompleteTextView>(R.id.edit_invalid_time)
        var is_closed=itemView.findViewById<AutoCompleteTextView>(R.id.edit_is_closed)
        var close_time=itemView.findViewById<AutoCompleteTextView>(R.id.edit_close_time)
        var remark=itemView.findViewById<TextInputEditText>(R.id.edit_remark)
        var layout=itemView.findViewById<ConstraintLayout>(R.id.constraint_layout)
        lateinit var oldData:PurchaseInlineOrderBody
        lateinit var newData:PurchaseInlineOrderBody
        init {
            itemView.setOnClickListener{
                val position:Int=adapterPosition
                Log.d("GSON", "msg: ${position}\n")

            }

            val combobox=itemView.resources.getStringArray(R.array.combobox_yes_no)
            val arrayAdapter= ArrayAdapter(itemView.context,R.layout.combobox_item,combobox)
            val arrayAdapter01= ArrayAdapter(itemView.context,R.layout.combobox_item,relativeCombobox01)
            val arrayAdapter02= ArrayAdapter(itemView.context,R.layout.combobox_item,relativeCombobox02)

            //編輯按鈕
            edit_btn.setOnClickListener {
                when(edit_btn.text){
                    "編輯"->{
                        oldData=itemData.data[adapterPosition]
                        //Log.d("GSON", "msg: ${oldData.id}\n")
                        //body_id.inputType=InputType.TYPE_CLASS_TEXT
                        //header_id.inputType=InputType.TYPE_DATETIME_VARIATION_DATE
                        //section.inputType=InputType.TYPE_CLASS_TEXT
                        item_id.setAdapter(arrayAdapter01)
                        item_name.setAdapter(arrayAdapter02)
                        pre_delivery_date.inputType=InputType.TYPE_DATETIME_VARIATION_DATE
                        purchase_quantity.inputType=(InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL)
                        accumulated_deduction.inputType=(InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL)
                        unit_of_measurement.inputType=InputType.TYPE_CLASS_TEXT


                        remark.isClickable=true
                        remark.isFocusable=true
                        remark.isFocusableInTouchMode=true
                        remark.isTextInputLayoutFocusedRectEnabled=true
                        item_id.requestFocus()
                        edit_btn.text = "完成"
                        layout.setBackgroundColor(Color.parseColor("#6E5454"))
                        edit_btn.setBackgroundColor(Color.parseColor("#FF3700B3"))
                    }
                    "完成"->{
                        newData= PurchaseInlineOrderBody()
                        newData.body_id=body_id.text.toString()
                        body_id.inputType=InputType.TYPE_NULL
                        newData.header_id=header_id.text.toString()
                        header_id.inputType=InputType.TYPE_NULL
                        newData.section=section.text.toString()
                        section.inputType=InputType.TYPE_NULL
                        newData.item_id=item_id.text.toString()
                        item_id.setAdapter(null)
                        newData.item_name=item_name.text.toString()
                        item_name.setAdapter(null)
                        if(pre_delivery_date.text.toString()==""){
                            newData.pre_delivery_date=null
                        }
                        else{
                            newData.pre_delivery_date=pre_delivery_date.text.toString()
                        }
                        pre_delivery_date.inputType=InputType.TYPE_NULL
                        newData.purchase_quantity=purchase_quantity.text.toString().toDouble()
                        purchase_quantity.inputType=InputType.TYPE_NULL
                        newData.accumulated_deduction=accumulated_deduction.text.toString().toDouble()
                        accumulated_deduction.inputType=InputType.TYPE_NULL
                        newData.unit_of_measurement=unit_of_measurement.text.toString()
                        unit_of_measurement.inputType=InputType.TYPE_NULL


                        newData.remark=remark.text.toString()
                        remark.isClickable=false
                        remark.isFocusable=false
                        remark.isFocusableInTouchMode=false
                        remark.isTextInputLayoutFocusedRectEnabled=false
                        //Log.d("GSON", "msg: ${oldData}\n")
                        //Log.d("GSON", "msg: ${newData.remark}\n")
                        edit_Purchase("PurchaseInlineOrderBody",oldData,newData)//更改資料庫資料
                        when(cookie_data.status){
                            0-> {//成功
                                itemData.data[adapterPosition] = newData//更改渲染資料
                                editor.setText(cookie_data.username)
                                Toast.makeText(itemView.context, cookie_data.msg, Toast.LENGTH_SHORT).show()
                            }
                            1->{//失敗
                                Toast.makeText(itemView.context, cookie_data.msg, Toast.LENGTH_SHORT).show()
                                body_id.setText(oldData.body_id)
                                header_id.setText(oldData.header_id)
                                section.setText(oldData.section)
                                item_id.setText(oldData.item_id)
                                item_name.setText(oldData.item_name)
                                pre_delivery_date.setText(oldData.pre_delivery_date)
                                purchase_quantity.setText(oldData.purchase_quantity.toString())
                                accumulated_deduction.setText(oldData.accumulated_deduction.toString())
                                unit_of_measurement.setText(oldData.unit_of_measurement)


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
                    delete_Purchase("PurchaseInlineOrderBody",data[adapterPosition])
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
                    lock_Purchase("PurchaseInlineOrderBody",data[adapterPosition])
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
                    over_Purchase("PurchaseInlineOrderBody",data[adapterPosition])
                    when(cookie_data.status){
                        0-> {//成功
                            is_closed.setText("true")
                            close_time.setText(Calendar.getInstance().getTime().toString())
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
        private fun edit_Purchase(operation:String,oldData:PurchaseInlineOrderBody,newData:PurchaseInlineOrderBody) {
            val old =JSONObject()
            old.put("body_id",oldData.body_id)
            old.put("header_id",oldData.header_id)
            old.put("section",oldData.section)
            old.put("item_id",oldData.item_id)
            old.put("item_name",oldData.item_name)
            old.put("pre_delivery_date",oldData.pre_delivery_date)
            old.put("purchase_quantity",oldData.purchase_quantity)
            old.put("accumulated_deduction",oldData.accumulated_deduction)
            old.put("unit_of_measurement",oldData.unit_of_measurement)


            old.put("remark",oldData.remark)
            val new =JSONObject()
            new.put("body_id",newData.body_id)
            new.put("header_id",newData.header_id)
            new.put("section",newData.section)
            new.put("item_id",newData.item_id)
            new.put("item_name",newData.item_name)
            new.put("pre_delivery_date",newData.pre_delivery_date)
            new.put("purchase_quantity",newData.purchase_quantity)
            new.put("accumulated_deduction",newData.accumulated_deduction)
            new.put("unit_of_measurement",newData.unit_of_measurement)


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
        private fun delete_Purchase(operation:String,deleteData:PurchaseInlineOrderBody) {
            val delete = JSONObject()
            delete.put("body_id",deleteData.body_id)
            delete.put("header_id",deleteData.header_id)
            delete.put("section",deleteData.section)
            delete.put("item_id",deleteData.item_id)
            delete.put("item_name",deleteData.item_name)
            delete.put("pre_delivery_date",deleteData.pre_delivery_date)
            delete.put("purchase_quantity",deleteData.purchase_quantity)
            delete.put("accumulated_deduction",deleteData.accumulated_deduction)
            delete.put("unit_of_measurement",deleteData.unit_of_measurement)

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
        private fun lock_Purchase(operation:String,lockData:PurchaseInlineOrderBody) {
            val lock = JSONObject()
            lock.put("body_id",lockData.body_id)
            lock.put("header_id",lockData.header_id)
            lock.put("section",lockData.section)
            lock.put("item_id",lockData.item_id)
            lock.put("item_name",lockData.item_name)
            lock.put("pre_delivery_date",lockData.pre_delivery_date)
            lock.put("purchase_quantity",lockData.purchase_quantity)
            lock.put("accumulated_deduction",lockData.accumulated_deduction)
            lock.put("unit_of_measurement",lockData.unit_of_measurement)

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
        private fun over_Purchase(operation:String,overData:PurchaseInlineOrderBody) {
            val over = JSONObject()
            over.put("body_id",overData.body_id)
            over.put("header_id",overData.header_id)
            over.put("section",overData.section)
            over.put("item_id",overData.item_id)
            over.put("item_name",overData.item_name)
            over.put("pre_delivery_date",overData.pre_delivery_date)
            over.put("purchase_quantity",overData.purchase_quantity)
            over.put("accumulated_deduction",overData.accumulated_deduction)
            over.put("unit_of_measurement",overData.unit_of_measurement)

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

    }
    fun addItem(addData:PurchaseInlineOrderBody){
        data.add(itemData.count,addData)
        notifyItemInserted(itemData.count)
        itemData.count+=1//cookie_data.itemCount+=1
    }

}


