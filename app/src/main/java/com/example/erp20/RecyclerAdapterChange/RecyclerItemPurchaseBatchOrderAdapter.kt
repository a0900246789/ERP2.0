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


class RecyclerItemPurchaseBatchOrderAdapter() :
    RecyclerView.Adapter<RecyclerItemPurchaseBatchOrderAdapter.ViewHolder>() {
    private var itemData: ShowPurchaseBatchOrder =Gson().fromJson(cookie_data.response_data, ShowPurchaseBatchOrder::class.java)
    private var data: ArrayList<PurchaseBatchOrder> =itemData.data
    var relativeCombobox01=cookie_data.ProductControlOrderHeader_id_ComboboxData

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemPurchaseBatchOrderAdapter.ViewHolder {
        val v= LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_purchase_batch_order,parent,false)
        return ViewHolder(v)

    }

    override fun onBindViewHolder(holder: RecyclerItemPurchaseBatchOrderAdapter.ViewHolder, position: Int) {

        //Log.d("GSON", "msg: ${itemData.data[position]}\n")
        holder.batch_id.setText(data[position].batch_id)
        holder.batch_id.inputType=InputType.TYPE_NULL
        holder.purchase_order_id.setText(data[position].purchase_order_id)
        holder.purchase_order_id.inputType=InputType.TYPE_NULL
        holder.section.setText(data[position].section)
        holder.section.inputType=InputType.TYPE_NULL
        holder.count.setText(data[position].count.toString())
        holder.count.inputType=InputType.TYPE_NULL
        holder.pre_delivery_date.setText(data[position].pre_delivery_date)
        holder.pre_delivery_date.inputType=InputType.TYPE_NULL
        holder.v_count.setText(data[position].v_count.toString())
        holder.v_count.inputType=InputType.TYPE_NULL
        holder.v_pre_delivery_date.setText(data[position].v_pre_delivery_date)
        holder.v_pre_delivery_date.inputType=InputType.TYPE_NULL
        holder.quantity_delivered.setText(data[position].quantity_delivered.toString())
        holder.quantity_delivered.inputType=InputType.TYPE_NULL
        holder.prod_ctrl_order_number.setText(data[position].prod_ctrl_order_number)
        holder.prod_ctrl_order_number.inputType=InputType.TYPE_NULL
        holder.is_warning.setText(data[position].is_warning.toString())
        holder.is_warning.isClickable=false
        holder.is_warning.inputType=InputType.TYPE_NULL
        holder.is_urgent.setText(data[position].is_urgent.toString())
        holder.is_urgent.isClickable=false
        holder.is_urgent.inputType=InputType.TYPE_NULL
        holder.urgent_deadline.setText(data[position].urgent_deadline)
        holder.urgent_deadline.inputType=InputType.TYPE_NULL
        holder.notice_matter.setText(data[position].notice_matter)
        holder.notice_matter.isClickable=false
        holder.notice_matter.isFocusable=false
        holder.notice_matter.isFocusableInTouchMode=false
        holder.notice_matter.isTextInputLayoutFocusedRectEnabled=false
        holder.v_notice_matter.setText(data[position].v_notice_matter)
        holder.v_notice_matter.isClickable=false
        holder.v_notice_matter.isFocusable=false
        holder.v_notice_matter.isFocusableInTouchMode=false
        holder.v_notice_matter.isTextInputLayoutFocusedRectEnabled=false
        holder.is_request_reply.setText(data[position].is_request_reply.toString())
        holder.is_request_reply.isClickable=false
        holder.is_request_reply.inputType=InputType.TYPE_NULL
        holder.notice_reply_time.setText(data[position].notice_reply_time)
        holder.notice_reply_time.inputType=InputType.TYPE_NULL
        holder.is_vender_reply.setText(data[position].is_vender_reply.toString())
        holder.is_vender_reply.isClickable=false
        holder.is_vender_reply.inputType=InputType.TYPE_NULL
        holder.vender_reply_time.setText(data[position].vender_reply_time)
        holder.vender_reply_time.inputType=InputType.TYPE_NULL
        holder.is_argee.setText(data[position].is_argee.toString())
        holder.is_argee.isClickable=false
        holder.is_argee.inputType=InputType.TYPE_NULL
        holder.argee_time.setText(data[position].argee_time)
        holder.argee_time.inputType=InputType.TYPE_NULL
        holder.is_v_argee.setText(data[position].is_v_argee.toString())
        holder.is_v_argee.isClickable=false
        holder.is_v_argee.inputType=InputType.TYPE_NULL
        holder.v_argee_time.setText(data[position].v_argee_time)
        holder.v_argee_time.inputType=InputType.TYPE_NULL
        holder.number_of_standard_measurements.setText(data[position].number_of_standard_measurements.toString())
        holder.number_of_standard_measurements.inputType=InputType.TYPE_NULL


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
        var batch_id=itemView.findViewById<TextInputEditText>(R.id.edit_batch_id)
        var purchase_order_id=itemView.findViewById<TextInputEditText>(R.id.edit_purchase_order_id)
        var section=itemView.findViewById<TextInputEditText>(R.id.edit_section)
        var count=itemView.findViewById<TextInputEditText>(R.id.edit_count)
        var pre_delivery_date=itemView.findViewById<TextInputEditText>(R.id.edit_pre_delivery_date)
        var v_count =itemView.findViewById<TextInputEditText>(R.id.edit_v_count)
        var v_pre_delivery_date=itemView.findViewById<TextInputEditText>(R.id.edit_v_pre_delivery_date)
        var quantity_delivered=itemView.findViewById<TextInputEditText>(R.id.edit_quantity_delivered)
        var prod_ctrl_order_number=itemView.findViewById<AutoCompleteTextView>(R.id.edit_prod_ctrl_order_number)
        var is_warning=itemView.findViewById<AutoCompleteTextView>(R.id.edit_is_warning)
        var is_urgent=itemView.findViewById<AutoCompleteTextView>(R.id.edit_is_urgent)
        var urgent_deadline=itemView.findViewById<TextInputEditText>(R.id.edit_urgent_deadline)
        var notice_matter=itemView.findViewById<TextInputEditText>(R.id.edit_notice_matter)
        var v_notice_matter=itemView.findViewById<TextInputEditText>(R.id.edit_v_notice_matter)
        var is_request_reply=itemView.findViewById<AutoCompleteTextView>(R.id.edit_is_request_reply)
        var notice_reply_time=itemView.findViewById<TextInputEditText>(R.id.edit_notice_reply_time)
        var is_vender_reply=itemView.findViewById<AutoCompleteTextView>(R.id.edit_is_vender_reply)
        var vender_reply_time=itemView.findViewById<TextInputEditText>(R.id.edit_vender_reply_time)
        var is_argee=itemView.findViewById<AutoCompleteTextView>(R.id.edit_is_argee)
        var argee_time=itemView.findViewById<TextInputEditText>(R.id.edit_argee_time)
        var is_v_argee=itemView.findViewById<AutoCompleteTextView>(R.id.edit_is_v_argee)
        var v_argee_time=itemView.findViewById<TextInputEditText>(R.id.edit_v_argee_time)
        var number_of_standard_measurements=itemView.findViewById<TextInputEditText>(R.id.edit_number_of_standard_measurements)

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

            //編輯按鈕
            edit_btn.setOnClickListener {
                when(edit_btn.text){
                    "編輯"->{
                        oldData=itemData.data[adapterPosition]
                        //Log.d("GSON", "msg: ${oldData.id}\n")
                        batch_id.inputType=InputType.TYPE_CLASS_TEXT
                        //purchase_order_id.inputType=InputType.TYPE_CLASS_TEXT
                        section.inputType=InputType.TYPE_CLASS_NUMBER
                        count.inputType=(InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL)
                        pre_delivery_date.inputType=InputType.TYPE_DATETIME_VARIATION_DATE
                        v_count.inputType=(InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL)
                        v_pre_delivery_date.inputType=InputType.TYPE_DATETIME_VARIATION_DATE
                        quantity_delivered.inputType=(InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL)
                        prod_ctrl_order_number.setAdapter(arrayAdapter01)
                        is_warning.setAdapter(arrayAdapter)
                        is_warning.isClickable=true
                        is_urgent.setAdapter(arrayAdapter)
                        is_urgent.isClickable=true
                        urgent_deadline.inputType=InputType.TYPE_CLASS_DATETIME
                        notice_matter.isClickable=true
                        notice_matter.isFocusable=true
                        notice_matter.isFocusableInTouchMode=true
                        notice_matter.isTextInputLayoutFocusedRectEnabled=true
                        v_notice_matter.isClickable=true
                        v_notice_matter.isFocusable=true
                        v_notice_matter.isFocusableInTouchMode=true
                        v_notice_matter.isTextInputLayoutFocusedRectEnabled=true
                        is_request_reply.setAdapter(arrayAdapter)
                        is_request_reply.isClickable=true
                        notice_reply_time.inputType=InputType.TYPE_CLASS_DATETIME
                        is_vender_reply.setAdapter(arrayAdapter)
                        is_vender_reply.isClickable=true
                        vender_reply_time.inputType=InputType.TYPE_CLASS_DATETIME
                        is_argee.setAdapter(arrayAdapter)
                        is_argee.isClickable=true
                        argee_time.inputType=InputType.TYPE_CLASS_DATETIME
                        is_v_argee.setAdapter(arrayAdapter)
                        is_v_argee.isClickable=true
                        v_argee_time.inputType=InputType.TYPE_CLASS_DATETIME
                        number_of_standard_measurements.inputType=(InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL)


                        remark.isClickable=true
                        remark.isFocusable=true
                        remark.isFocusableInTouchMode=true
                        remark.isTextInputLayoutFocusedRectEnabled=true
                        count.requestFocus()
                        edit_btn.text = "完成"
                        layout.setBackgroundColor(Color.parseColor("#6E5454"))
                        edit_btn.setBackgroundColor(Color.parseColor("#FF3700B3"))
                    }
                    "完成"->{
                        newData= PurchaseBatchOrder()
                        newData.batch_id=batch_id.text.toString()
                        batch_id.inputType=InputType.TYPE_NULL
                        newData.purchase_order_id=purchase_order_id.text.toString()
                        purchase_order_id.inputType=InputType.TYPE_NULL
                        newData.section=section.text.toString()
                        section.inputType=InputType.TYPE_NULL
                        newData.count=count.text.toString().toDouble()
                        count.inputType=InputType.TYPE_NULL
                        if(pre_delivery_date.text.toString()==""){
                            newData.pre_delivery_date=null
                        }
                        else{
                            newData.pre_delivery_date=pre_delivery_date.text.toString()
                        }
                        pre_delivery_date.inputType=InputType.TYPE_NULL
                        newData.v_count=v_count.text.toString().toDouble()
                        v_count.inputType=InputType.TYPE_NULL
                        if(v_pre_delivery_date.text.toString()==""){
                            newData.v_pre_delivery_date=null
                        }
                        else{
                            newData.v_pre_delivery_date=v_pre_delivery_date.text.toString()
                        }
                        v_pre_delivery_date.inputType=InputType.TYPE_NULL
                        newData.quantity_delivered=quantity_delivered.text.toString().toDouble()
                        quantity_delivered.inputType=InputType.TYPE_NULL
                        newData.prod_ctrl_order_number=prod_ctrl_order_number.text.toString()
                        prod_ctrl_order_number.setAdapter(null)
                        newData.is_warning=is_warning.text.toString().toBoolean()
                        is_warning.isClickable=false
                        is_warning.setAdapter(null)
                        newData.is_urgent=is_urgent.text.toString().toBoolean()
                        is_urgent.isClickable=false
                        is_urgent.setAdapter(null)
                        if(urgent_deadline.text.toString()==""){
                            newData.urgent_deadline=null
                        }
                        else{
                            newData.urgent_deadline=urgent_deadline.text.toString()
                        }
                        urgent_deadline.inputType=InputType.TYPE_NULL
                        newData.notice_matter=notice_matter.text.toString()
                        notice_matter.isClickable=false
                        notice_matter.isFocusable=false
                        notice_matter.isFocusableInTouchMode=false
                        notice_matter.isTextInputLayoutFocusedRectEnabled=false
                        newData.v_notice_matter=v_notice_matter.text.toString()
                        v_notice_matter.isClickable=false
                        v_notice_matter.isFocusable=false
                        v_notice_matter.isFocusableInTouchMode=false
                        v_notice_matter.isTextInputLayoutFocusedRectEnabled=false
                        newData.is_request_reply=is_request_reply.text.toString().toBoolean()
                        is_request_reply.isClickable=false
                        is_request_reply.setAdapter(null)
                        if(notice_reply_time.text.toString()==""){
                            newData.notice_reply_time=null
                        }
                        else{
                            newData.notice_reply_time=notice_reply_time.text.toString()
                        }
                        notice_reply_time.inputType=InputType.TYPE_NULL
                        newData.is_vender_reply=is_vender_reply.text.toString().toBoolean()
                        is_vender_reply.isClickable=false
                        is_vender_reply.setAdapter(null)
                        if(vender_reply_time.text.toString()==""){
                            newData.vender_reply_time=null
                        }
                        else{
                            newData.vender_reply_time=vender_reply_time.text.toString()
                        }
                        vender_reply_time.inputType=InputType.TYPE_NULL
                        newData.is_argee=is_argee.text.toString().toBoolean()
                        is_argee.isClickable=false
                        is_argee.setAdapter(null)
                        if(argee_time.text.toString()==""){
                            newData.argee_time=null
                        }
                        else{
                            newData.argee_time=argee_time.text.toString()
                        }
                        argee_time.inputType=InputType.TYPE_NULL
                        newData.is_v_argee=is_v_argee.text.toString().toBoolean()
                        is_v_argee.isClickable=false
                        is_v_argee.setAdapter(null)
                        if(v_argee_time.text.toString()==""){
                            newData.v_argee_time=null
                        }
                        else{
                            newData.v_argee_time=v_argee_time.text.toString()
                        }
                        v_argee_time.inputType=InputType.TYPE_NULL
                        newData.number_of_standard_measurements=number_of_standard_measurements.text.toString().toDouble()
                        number_of_standard_measurements.inputType=InputType.TYPE_NULL


                        newData.remark=remark.text.toString()
                        remark.isClickable=false
                        remark.isFocusable=false
                        remark.isFocusableInTouchMode=false
                        remark.isTextInputLayoutFocusedRectEnabled=false
                        //Log.d("GSON", "msg: ${oldData}\n")
                        //Log.d("GSON", "msg: ${newData.remark}\n")
                        edit_Purchase("PurchaseBatchOrder",oldData,newData)//更改資料庫資料
                        when(cookie_data.status){
                            0-> {//成功
                                itemData.data[adapterPosition] = newData//更改渲染資料
                                editor.setText(cookie_data.username)
                                Toast.makeText(itemView.context, cookie_data.msg, Toast.LENGTH_SHORT).show()
                            }
                            1->{//失敗
                                Toast.makeText(itemView.context, cookie_data.msg, Toast.LENGTH_SHORT).show()
                                batch_id.setText(oldData.batch_id)
                                purchase_order_id.setText(oldData.purchase_order_id)
                                section.setText(oldData.section)
                                count.setText(oldData.count.toString())
                                pre_delivery_date.setText(oldData.pre_delivery_date)
                                v_count.setText(oldData.v_count.toString())
                                v_pre_delivery_date.setText(oldData.v_pre_delivery_date)
                                quantity_delivered.setText(oldData.quantity_delivered.toString())
                                prod_ctrl_order_number.setText(oldData.prod_ctrl_order_number)
                                is_warning.setText(oldData.is_warning.toString())
                                is_urgent.setText(oldData.is_urgent.toString())
                                urgent_deadline.setText(oldData.urgent_deadline)
                                notice_matter.setText(oldData.notice_matter)
                                v_notice_matter.setText(oldData.v_notice_matter)
                                is_request_reply.setText(oldData.is_request_reply.toString())
                                notice_reply_time.setText(oldData.notice_reply_time)
                                is_vender_reply.setText(oldData.is_vender_reply.toString())
                                vender_reply_time.setText(oldData.vender_reply_time)
                                is_argee.setText(oldData.is_argee.toString())
                                argee_time.setText(oldData.argee_time)
                                is_v_argee.setText(oldData.is_v_argee.toString())
                                v_argee_time.setText(oldData.v_argee_time)
                                number_of_standard_measurements.setText(oldData.number_of_standard_measurements.toString())

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
                    over_Purchase("PurchaseBatchOrder",data[adapterPosition])
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
                .url("http://140.125.46.125:8000/purchase_order_management")
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
                .url("http://140.125.46.125:8000/purchase_order_management")
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
                .url("http://140.125.46.125:8000/purchase_order_management")
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
                .url("http://140.125.46.125:8000/purchase_order_management")
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
    fun addItem(addData:PurchaseBatchOrder){
        data.add(itemData.count,addData)
        notifyItemInserted(itemData.count)
        itemData.count+=1//cookie_data.itemCount+=1
    }

}


