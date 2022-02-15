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


class RecyclerItemPurchaseOrderBodyAdapter() :
    RecyclerView.Adapter<RecyclerItemPurchaseOrderBodyAdapter.ViewHolder>() {
    private var itemData: ShowPurchaseOrderBody =Gson().fromJson(cookie_data.response_data, ShowPurchaseOrderBody::class.java)
    private var data: ArrayList<PurchaseOrderBody> =itemData.data
    var relativeCombobox01=cookie_data.item_id_ComboboxData
    var relativeCombobox02=cookie_data.MasterScheduledOrderHeader_id_ComboboxData
    var relativeCombobox03=cookie_data.PurchasePreparationListBody_id_ComboboxData
    var relativeCombobox04=cookie_data.PurchaseInlineOrderBody_id_ComboboxData

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemPurchaseOrderBodyAdapter.ViewHolder {
        val v= LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_purchase_order_body,parent,false)
        return ViewHolder(v)

    }

    override fun onBindViewHolder(holder: RecyclerItemPurchaseOrderBodyAdapter.ViewHolder, position: Int) {

        //Log.d("GSON", "msg: ${itemData.data[position]}\n")
        holder.body_id.setText(data[position].body_id)
        holder.body_id.inputType=InputType.TYPE_NULL
        holder.poNo.setText(data[position].poNo)
        holder.poNo.inputType=InputType.TYPE_NULL
        holder.section.setText(data[position].section)
        holder.section.inputType=InputType.TYPE_NULL
        holder.item_id.setText(data[position].item_id)
        holder.item_id.inputType=InputType.TYPE_NULL
        holder.purchase_count.setText(data[position].purchase_count.toString())
        holder.purchase_count.inputType=InputType.TYPE_NULL
        holder.purchase_in_count.setText(data[position].purchase_in_count.toString())
        holder.purchase_in_count.inputType=InputType.TYPE_NULL
        holder.purchase_undelivered_count.setText(data[position].purchase_undelivered_count.toString())
        holder.purchase_undelivered_count.inputType=InputType.TYPE_NULL
        holder.unit_of_measurement.setText(data[position].unit_of_measurement)
        holder.unit_of_measurement.inputType=InputType.TYPE_NULL
        holder.pre_delivery_date.setText(data[position].pre_delivery_date)
        holder.pre_delivery_date.inputType=InputType.TYPE_NULL
        holder.total_batch.setText(data[position].total_batch.toString())
        holder.total_batch.inputType=InputType.TYPE_NULL
        holder.purchase_date.setText(data[position].purchase_date)
        holder.purchase_date.inputType=InputType.TYPE_NULL
        holder.master_order_numer.setText(data[position].master_order_numer)
        holder.master_order_numer.inputType=InputType.TYPE_NULL
        holder.material_preparation_number.setText(data[position].material_preparation_number)
        holder.material_preparation_number.inputType=InputType.TYPE_NULL
        holder.inline_number.setText(data[position].inline_number)
        holder.inline_number.inputType=InputType.TYPE_NULL
        holder.is_done.setText(data[position].is_done.toString())
        holder.is_done.inputType=InputType.TYPE_NULL
        holder.done_reason.setText(data[position].done_reason)
        holder.done_reason.inputType=InputType.TYPE_NULL
        holder.done_time.setText(data[position].done_time)
        holder.done_time.inputType=InputType.TYPE_NULL



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
        var poNo=itemView.findViewById<TextInputEditText>(R.id.edit_poNo)
        var section=itemView.findViewById<TextInputEditText>(R.id.edit_section)
        var item_id=itemView.findViewById<AutoCompleteTextView>(R.id.edit_item_id)
        var purchase_count=itemView.findViewById<TextInputEditText>(R.id.edit_purchase_count)
        var purchase_in_count =itemView.findViewById<TextInputEditText>(R.id.edit_purchase_in_count)
        var purchase_undelivered_count=itemView.findViewById<TextInputEditText>(R.id.edit_purchase_undelivered_count)
        var unit_of_measurement=itemView.findViewById<TextInputEditText>(R.id.edit_unit_of_measurement)
        var pre_delivery_date=itemView.findViewById<TextInputEditText>(R.id.edit_pre_delivery_date)
        var total_batch=itemView.findViewById<TextInputEditText>(R.id.edit_total_batch)
        var purchase_date=itemView.findViewById<TextInputEditText>(R.id.edit_purchase_date)
        var master_order_numer=itemView.findViewById<AutoCompleteTextView>(R.id.edit_master_order_numer)
        var material_preparation_number=itemView.findViewById<AutoCompleteTextView>(R.id.edit_material_preparation_number)
        var inline_number=itemView.findViewById<AutoCompleteTextView>(R.id.edit_inline_number)
        var is_done=itemView.findViewById<AutoCompleteTextView>(R.id.edit_is_done)
        var done_reason=itemView.findViewById<TextInputEditText>(R.id.edit_done_reason)
        var done_time=itemView.findViewById<TextInputEditText>(R.id.edit_done_time)

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

        lateinit var oldData:PurchaseOrderBody
        lateinit var newData:PurchaseOrderBody
        init {
            itemView.setOnClickListener{
                val position:Int=adapterPosition
                Log.d("GSON", "msg: ${position}\n")

            }

            val combobox=itemView.resources.getStringArray(R.array.combobox_yes_no)
            val arrayAdapter= ArrayAdapter(itemView.context,R.layout.combobox_item,combobox)
            val arrayAdapter01= ArrayAdapter(itemView.context,R.layout.combobox_item,relativeCombobox01)
            val arrayAdapter02= ArrayAdapter(itemView.context,R.layout.combobox_item,relativeCombobox02)
            val arrayAdapter03= ArrayAdapter(itemView.context,R.layout.combobox_item,relativeCombobox03)
            val arrayAdapter04= ArrayAdapter(itemView.context,R.layout.combobox_item,relativeCombobox04)

            //編輯按鈕
            edit_btn.setOnClickListener {
                when(edit_btn.text){
                    "編輯"->{
                        oldData=itemData.data[adapterPosition]
                        //Log.d("GSON", "msg: ${oldData.id}\n")
                        //body_id.inputType=InputType.TYPE_CLASS_TEXT
                        //poNo.inputType=InputType.TYPE_CLASS_TEXT
                        //section.inputType=InputType.TYPE_CLASS_NUMBER
                        item_id.setAdapter(arrayAdapter01)
                        purchase_count.inputType=(InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL)
                        purchase_in_count.inputType=(InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL)
                        purchase_undelivered_count.inputType=(InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL)
                        unit_of_measurement.inputType=InputType.TYPE_CLASS_TEXT
                        pre_delivery_date.inputType=InputType.TYPE_DATETIME_VARIATION_DATE
                        total_batch.inputType=InputType.TYPE_CLASS_NUMBER
                        purchase_date.inputType=InputType.TYPE_DATETIME_VARIATION_DATE
                        master_order_numer.setAdapter(arrayAdapter02)
                        material_preparation_number.setAdapter(arrayAdapter03)
                        inline_number.setAdapter(arrayAdapter04)
                        is_done.setAdapter(arrayAdapter)
                        is_done.isClickable=true
                        done_reason.inputType=InputType.TYPE_CLASS_TEXT
                        done_time.inputType=InputType.TYPE_CLASS_DATETIME

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
                        newData= PurchaseOrderBody()
                        newData.body_id=body_id.text.toString()
                        body_id.inputType=InputType.TYPE_NULL
                        newData.poNo=poNo.text.toString()
                        poNo.inputType=InputType.TYPE_NULL
                        newData.section=section.text.toString()
                        section.inputType=InputType.TYPE_NULL
                        newData.item_id=item_id.text.toString()
                        item_id.setAdapter(null)
                        newData.purchase_count=purchase_count.text.toString().toDouble()
                        purchase_count.inputType=InputType.TYPE_NULL
                        newData.purchase_in_count=purchase_in_count.text.toString().toDouble()
                        purchase_in_count.inputType=InputType.TYPE_NULL
                        newData.purchase_undelivered_count=purchase_undelivered_count.text.toString().toDouble()
                        purchase_undelivered_count.inputType=InputType.TYPE_NULL
                        newData.unit_of_measurement=unit_of_measurement.text.toString()
                        unit_of_measurement.inputType=InputType.TYPE_NULL
                        if(pre_delivery_date.text.toString()==""){
                            newData.pre_delivery_date=null
                        }
                        else{
                            newData.pre_delivery_date=pre_delivery_date.text.toString()
                        }
                        pre_delivery_date.inputType=InputType.TYPE_NULL
                        newData.total_batch=total_batch.text.toString().toInt()
                        total_batch.inputType=InputType.TYPE_NULL
                        if(purchase_date.text.toString()==""){
                            newData.purchase_date=null
                        }
                        else{
                            newData.purchase_date=purchase_date.text.toString()
                        }
                        purchase_date.inputType=InputType.TYPE_NULL
                        newData.master_order_numer=master_order_numer.text.toString()
                        master_order_numer.setAdapter(null)
                        newData.material_preparation_number=material_preparation_number.text.toString()
                        material_preparation_number.setAdapter(null)
                        newData.inline_number=inline_number.text.toString()
                        inline_number.setAdapter(null)
                        newData.is_done=is_done.text.toString().toBoolean()
                        is_done.setAdapter(null)
                        newData.done_reason=done_reason.text.toString()
                        done_reason.inputType=InputType.TYPE_NULL
                        if(done_time.text.toString()==""){
                            newData.done_time=null
                        }
                        else{
                            newData.done_time=done_time.text.toString()
                        }
                        done_time.inputType=InputType.TYPE_NULL


                        newData.remark=remark.text.toString()
                        remark.isClickable=false
                        remark.isFocusable=false
                        remark.isFocusableInTouchMode=false
                        remark.isTextInputLayoutFocusedRectEnabled=false
                        //Log.d("GSON", "msg: ${oldData}\n")
                        //Log.d("GSON", "msg: ${newData.remark}\n")
                        edit_Purchase("PurchaseOrderBody",oldData,newData)//更改資料庫資料
                        when(cookie_data.status){
                            0-> {//成功
                                itemData.data[adapterPosition] = newData//更改渲染資料
                                editor.setText(cookie_data.username)
                                Toast.makeText(itemView.context, cookie_data.msg, Toast.LENGTH_SHORT).show()
                            }
                            1->{//失敗
                                Toast.makeText(itemView.context, cookie_data.msg, Toast.LENGTH_SHORT).show()
                                body_id.setText(oldData.body_id)
                                poNo.setText(oldData.poNo)
                                section.setText(oldData.section)
                                item_id.setText(oldData.item_id)
                                purchase_count.setText(oldData.purchase_count.toString())
                                purchase_in_count.setText(oldData.purchase_in_count.toString())
                                purchase_undelivered_count.setText(oldData.purchase_undelivered_count.toString())
                                unit_of_measurement.setText(oldData.unit_of_measurement)
                                pre_delivery_date.setText(oldData.pre_delivery_date)
                                total_batch.setText(oldData.total_batch.toString())
                                purchase_date.setText(oldData.purchase_date)
                                master_order_numer.setText(oldData.master_order_numer)
                                material_preparation_number.setText(oldData.material_preparation_number)
                                inline_number.setText(oldData.inline_number)
                                is_done.setText(oldData.is_done.toString())
                                done_reason.setText(oldData.done_reason)
                                done_time.setText(oldData.done_time)

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
                    delete_Purchase("PurchaseOrderBody",data[adapterPosition])
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
                    lock_Purchase("PurchaseOrderBody",data[adapterPosition])
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
                    over_Purchase("PurchaseOrderBody",data[adapterPosition])
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
        private fun edit_Purchase(operation:String,oldData:PurchaseOrderBody,newData:PurchaseOrderBody) {
            val old =JSONObject()
            old.put("body_id",oldData.body_id)
            old.put("poNo",oldData.poNo)
            old.put("section",oldData.section)
            old.put("item_id",oldData.item_id)
            old.put("purchase_count",oldData.purchase_count)
            old.put("purchase_in_count",oldData.purchase_in_count)
            old.put("purchase_undelivered_count",oldData.purchase_undelivered_count)
            old.put("unit_of_measurement",oldData.unit_of_measurement)
            old.put("pre_delivery_date",oldData.pre_delivery_date)
            old.put("total_batch",oldData.total_batch)
            old.put("purchase_date",oldData.purchase_date)
            old.put("master_order_numer",oldData.master_order_numer)
            old.put("material_preparation_number",oldData.material_preparation_number)
            old.put("inline_number",oldData.inline_number)
            old.put("is_done",oldData.is_done)
            old.put("done_reason",oldData.done_reason)
            old.put("done_time",oldData.done_time)

            old.put("remark",oldData.remark)
            val new =JSONObject()
            new.put("body_id",newData.body_id)
            new.put("poNo",newData.poNo)
            new.put("section",newData.section)
            new.put("item_id",newData.item_id)
            new.put("purchase_count",newData.purchase_count)
            new.put("purchase_in_count",newData.purchase_in_count)
            new.put("purchase_undelivered_count",newData.purchase_undelivered_count)
            new.put("unit_of_measurement",newData.unit_of_measurement)
            new.put("pre_delivery_date",newData.pre_delivery_date)
            new.put("total_batch",newData.total_batch)
            new.put("purchase_date",newData.purchase_date)
            new.put("master_order_numer",newData.master_order_numer)
            new.put("material_preparation_number",newData.material_preparation_number)
            new.put("inline_number",newData.inline_number)
            new.put("is_done",newData.is_done)
            new.put("done_reason",newData.done_reason)
            new.put("done_time",newData.done_time)

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
        private fun delete_Purchase(operation:String,deleteData:PurchaseOrderBody) {
            val delete = JSONObject()
            delete.put("body_id",deleteData.body_id)
            delete.put("poNo",deleteData.poNo)
            delete.put("section",deleteData.section)
            delete.put("item_id",deleteData.item_id)
            delete.put("purchase_count",deleteData.purchase_count)
            delete.put("purchase_in_count",deleteData.purchase_in_count)
            delete.put("purchase_undelivered_count",deleteData.purchase_undelivered_count)
            delete.put("unit_of_measurement",deleteData.unit_of_measurement)
            delete.put("pre_delivery_date",deleteData.pre_delivery_date)
            delete.put("total_batch",deleteData.total_batch)
            delete.put("purchase_date",deleteData.purchase_date)
            delete.put("master_order_numer",deleteData.master_order_numer)
            delete.put("material_preparation_number",deleteData.material_preparation_number)
            delete.put("inline_number",deleteData.inline_number)
            delete.put("is_done",deleteData.is_done)
            delete.put("done_reason",deleteData.done_reason)
            delete.put("done_time",deleteData.done_time)

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
        private fun lock_Purchase(operation:String,lockData:PurchaseOrderBody) {
            val lock = JSONObject()
            lock.put("body_id",lockData.body_id)
            lock.put("poNo",lockData.poNo)
            lock.put("section",lockData.section)
            lock.put("item_id",lockData.item_id)
            lock.put("purchase_count",lockData.purchase_count)
            lock.put("purchase_in_count",lockData.purchase_in_count)
            lock.put("purchase_undelivered_count",lockData.purchase_undelivered_count)
            lock.put("unit_of_measurement",lockData.unit_of_measurement)
            lock.put("pre_delivery_date",lockData.pre_delivery_date)
            lock.put("total_batch",lockData.total_batch)
            lock.put("purchase_date",lockData.purchase_date)
            lock.put("master_order_numer",lockData.master_order_numer)
            lock.put("material_preparation_number",lockData.material_preparation_number)
            lock.put("inline_number",lockData.inline_number)
            lock.put("is_done",lockData.is_done)
            lock.put("done_reason",lockData.done_reason)
            lock.put("done_time",lockData.done_time)

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
        private fun over_Purchase(operation:String,overData:PurchaseOrderBody) {
            val over = JSONObject()
            over.put("body_id",overData.body_id)
            over.put("poNo",overData.poNo)
            over.put("section",overData.section)
            over.put("item_id",overData.item_id)
            over.put("purchase_count",overData.purchase_count)
            over.put("purchase_in_count",overData.purchase_in_count)
            over.put("purchase_undelivered_count",overData.purchase_undelivered_count)
            over.put("unit_of_measurement",overData.unit_of_measurement)
            over.put("pre_delivery_date",overData.pre_delivery_date)
            over.put("total_batch",overData.total_batch)
            over.put("purchase_date",overData.purchase_date)
            over.put("master_order_numer",overData.master_order_numer)
            over.put("material_preparation_number",overData.material_preparation_number)
            over.put("inline_number",overData.inline_number)
            over.put("is_done",overData.is_done)
            over.put("done_reason",overData.done_reason)
            over.put("done_time",overData.done_time)

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
    fun addItem(addData:PurchaseOrderBody){
        data.add(itemData.count,addData)
        notifyItemInserted(itemData.count)
        itemData.count+=1//cookie_data.itemCount+=1
    }

}


