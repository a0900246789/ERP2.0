package com.example.erp20.app01
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


class RecyclerItemVenderShipmentBodyAdapter() :
    RecyclerView.Adapter<RecyclerItemVenderShipmentBodyAdapter.ViewHolder>() {
    private var itemData: ShowVenderShipmentBody =Gson().fromJson(cookie_data.response_data, ShowVenderShipmentBody::class.java)
    private var data: ArrayList<VenderShipmentBody> =itemData.data
    var relativeCombobox01=cookie_data.item_id_ComboboxData
    var relativeCombobox02=cookie_data.PurchaseBatchOrder_batch_id_ComboboxData
    var relativeCombobox03=cookie_data.ProductControlOrderBody_D_prod_batch_code_ComboboxData

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v= LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_vender_shipment_body,parent,false)
        return ViewHolder(v)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

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
        holder.batch_id.setText(data[position].batch_id)
        holder.batch_id.inputType=InputType.TYPE_NULL
        holder.prod_batch_code.setText(data[position].prod_batch_code)
        holder.prod_batch_code.inputType=InputType.TYPE_NULL
        holder.qc_date.setText(data[position].qc_date)
        holder.qc_date.inputType=InputType.TYPE_NULL
        holder.qc_number.setText(data[position].qc_number)
        holder.qc_number.inputType=InputType.TYPE_NULL
        holder.is_tobe_determined.setText(data[position].is_tobe_determined.toString())
        holder.is_tobe_determined.inputType=InputType.TYPE_NULL
        holder.is_acceptance.setText(data[position].is_acceptance.toString())
        holder.is_acceptance.inputType=InputType.TYPE_NULL
        holder.is_reject.setText(data[position].is_reject.toString())
        holder.is_reject.inputType=InputType.TYPE_NULL
        holder.is_special_case.setText(data[position].is_special_case.toString())
        holder.is_special_case.inputType=InputType.TYPE_NULL


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
        var batch_id =itemView.findViewById<AutoCompleteTextView>(R.id.edit_batch_id)
        var prod_batch_code=itemView.findViewById<AutoCompleteTextView>(R.id.edit_prod_batch_code)
        var qc_date=itemView.findViewById<TextInputEditText>(R.id.edit_qc_date)
        var qc_number=itemView.findViewById<TextInputEditText>(R.id.edit_qc_number)
        var is_tobe_determined=itemView.findViewById<AutoCompleteTextView>(R.id.edit_is_tobe_determined)
        var is_acceptance=itemView.findViewById<AutoCompleteTextView>(R.id.edit_is_acceptance)
        var is_reject=itemView.findViewById<AutoCompleteTextView>(R.id.edit_is_reject)
        var is_special_case=itemView.findViewById<AutoCompleteTextView>(R.id.edit_is_special_case)

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

        lateinit var oldData:VenderShipmentBody
        lateinit var newData:VenderShipmentBody
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
                        purchase_count.inputType=(InputType.TYPE_CLASS_NUMBER)
                        batch_id.setAdapter(arrayAdapter02)
                        prod_batch_code.setAdapter(arrayAdapter03)
                        qc_date.inputType=InputType.TYPE_DATETIME_VARIATION_DATE
                        qc_number.inputType=InputType.TYPE_CLASS_TEXT
                        is_tobe_determined.setAdapter(arrayAdapter)
                        is_tobe_determined.isClickable=true
                        is_acceptance.setAdapter(arrayAdapter)
                        is_acceptance.isClickable=true
                        is_reject.setAdapter(arrayAdapter)
                        is_reject.isClickable=true
                        is_special_case.setAdapter(arrayAdapter)
                        is_special_case.isClickable=true


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
                        newData= VenderShipmentBody()
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
                        newData.batch_id=batch_id.text.toString()
                        batch_id.setAdapter(null)
                        newData.prod_batch_code=prod_batch_code.text.toString()
                        prod_batch_code.setAdapter(null)
                        if(qc_date.text.toString()==""){
                            newData.qc_date=null
                        }
                        else{
                            newData.qc_date=qc_date.text.toString()
                        }
                        qc_date.inputType=InputType.TYPE_NULL
                        newData.qc_number=qc_number.text.toString()
                        qc_number.inputType=InputType.TYPE_NULL
                        newData.is_tobe_determined=is_tobe_determined.text.toString().toBoolean()
                        is_tobe_determined.setAdapter(null)
                        newData.is_acceptance=is_acceptance.text.toString().toBoolean()
                        is_acceptance.setAdapter(null)
                        newData.is_reject=is_reject.text.toString().toBoolean()
                        is_reject.setAdapter(null)
                        newData.is_special_case=is_special_case.text.toString().toBoolean()
                        is_special_case.setAdapter(null)

                        newData.remark=remark.text.toString()
                        remark.isClickable=false
                        remark.isFocusable=false
                        remark.isFocusableInTouchMode=false
                        remark.isTextInputLayoutFocusedRectEnabled=false
                        //Log.d("GSON", "msg: ${oldData}\n")
                        //Log.d("GSON", "msg: ${newData.remark}\n")
                        edit_Purchase("VenderShipmentBody",oldData,newData)//更改資料庫資料
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
                                batch_id.setText(oldData.batch_id)
                                prod_batch_code.setText(oldData.prod_batch_code)
                                qc_date.setText(oldData.qc_date)
                                qc_number.setText(oldData.qc_number)
                                is_tobe_determined.setText(oldData.is_tobe_determined.toString())
                                is_acceptance.setText(oldData.is_acceptance.toString())
                                is_reject.setText(oldData.is_reject.toString())
                                is_special_case.setText(oldData.is_special_case.toString())


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
                    delete_Purchase("VenderShipmentBody",data[adapterPosition])
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
                    lock_Purchase("VenderShipmentBody",data[adapterPosition])
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
                    over_Purchase("VenderShipmentBody",data[adapterPosition])
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
        private fun edit_Purchase(operation:String,oldData:VenderShipmentBody,newData:VenderShipmentBody) {
            val old =JSONObject()
            old.put("body_id",oldData.body_id)
            old.put("poNo",oldData.poNo)
            old.put("section",oldData.section)
            old.put("item_id",oldData.item_id)
            old.put("purchase_count",oldData.purchase_count)
            old.put("batch_id",oldData.batch_id)
            old.put("prod_batch_code",oldData.prod_batch_code)
            old.put("qc_date",oldData.qc_date)
            old.put("qc_number",oldData.qc_number)
            old.put("is_tobe_determined",oldData.is_tobe_determined)
            old.put("is_acceptance",oldData.is_acceptance)
            old.put("is_reject",oldData.is_reject)
            old.put("is_special_case",oldData.is_special_case)


            old.put("remark",oldData.remark)
            val new =JSONObject()
            new.put("body_id",newData.body_id)
            new.put("poNo",newData.poNo)
            new.put("section",newData.section)
            new.put("item_id",newData.item_id)
            new.put("purchase_count",newData.purchase_count)
            new.put("batch_id",newData.batch_id)
            new.put("prod_batch_code",newData.prod_batch_code)
            new.put("qc_date",newData.qc_date)
            new.put("qc_number",newData.qc_number)
            new.put("is_tobe_determined",newData.is_tobe_determined)
            new.put("is_acceptance",newData.is_acceptance)
            new.put("is_reject",newData.is_reject)
            new.put("is_special_case",newData.is_special_case)

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
        private fun delete_Purchase(operation:String,deleteData:VenderShipmentBody) {
            val delete = JSONObject()
            delete.put("body_id",deleteData.body_id)
            delete.put("poNo",deleteData.poNo)
            delete.put("section",deleteData.section)
            delete.put("item_id",deleteData.item_id)
            delete.put("purchase_count",deleteData.purchase_count)
            delete.put("batch_id",deleteData.batch_id)
            delete.put("prod_batch_code",deleteData.prod_batch_code)
            delete.put("qc_date",deleteData.qc_date)
            delete.put("qc_number",deleteData.qc_number)
            delete.put("is_tobe_determined",deleteData.is_tobe_determined)
            delete.put("is_acceptance",deleteData.is_acceptance)
            delete.put("is_reject",deleteData.is_reject)
            delete.put("is_special_case",deleteData.is_special_case)

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
        private fun lock_Purchase(operation:String,lockData:VenderShipmentBody) {
            val lock = JSONObject()
            lock.put("body_id",lockData.body_id)
            lock.put("poNo",lockData.poNo)
            lock.put("section",lockData.section)
            lock.put("item_id",lockData.item_id)
            lock.put("purchase_count",lockData.purchase_count)
            lock.put("batch_id",lockData.batch_id)
            lock.put("prod_batch_code",lockData.prod_batch_code)
            lock.put("qc_date",lockData.qc_date)
            lock.put("qc_number",lockData.qc_number)
            lock.put("is_tobe_determined",lockData.is_tobe_determined)
            lock.put("is_acceptance",lockData.is_acceptance)
            lock.put("is_reject",lockData.is_reject)
            lock.put("is_special_case",lockData.is_special_case)

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
        private fun over_Purchase(operation:String,overData:VenderShipmentBody) {
            val over = JSONObject()
            over.put("body_id",overData.body_id)
            over.put("poNo",overData.poNo)
            over.put("section",overData.section)
            over.put("item_id",overData.item_id)
            over.put("purchase_count",overData.purchase_count)
            over.put("batch_id",overData.batch_id)
            over.put("prod_batch_code",overData.prod_batch_code)
            over.put("qc_date",overData.qc_date)
            over.put("qc_number",overData.qc_number)
            over.put("is_tobe_determined",overData.is_tobe_determined)
            over.put("is_acceptance",overData.is_acceptance)
            over.put("is_reject",overData.is_reject)
            over.put("is_special_case",overData.is_special_case)

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
    fun addItem(addData:VenderShipmentBody){
        data.add(itemData.count,addData)
        notifyItemInserted(itemData.count)
        itemData.count+=1//cookie_data.itemCount+=1
    }

}


