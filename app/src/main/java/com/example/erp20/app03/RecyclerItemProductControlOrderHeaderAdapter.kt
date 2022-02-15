package com.example.erp20.RecyclerAdapter
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


class RecyclerItemProductControlOrderHeaderAdapter() :
    RecyclerView.Adapter<RecyclerItemProductControlOrderHeaderAdapter.ViewHolder>() {
    private var itemData: ShowProductControlOrderHeader =Gson().fromJson(cookie_data.response_data, ShowProductControlOrderHeader::class.java)
    private var data: ArrayList<ProductControlOrderHeader> =itemData.data
    var relativeCombobox01=cookie_data.item_id_ComboboxData

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemProductControlOrderHeaderAdapter.ViewHolder {
        val v= LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_product_control_order_header,parent,false)
        return ViewHolder(v)

    }

    override fun onBindViewHolder(holder: RecyclerItemProductControlOrderHeaderAdapter.ViewHolder, position: Int) {

        //Log.d("GSON", "msg: ${itemData.data[position]}\n")
        holder._id.setText(data[position]._id)
        holder._id.inputType=InputType.TYPE_NULL
        holder.is_non_production.setText(data[position].is_non_production.toString())
        holder.is_non_production.isClickable=false
        holder.is_non_production.inputType=InputType.TYPE_NULL
        holder.item_id.setText(data[position].item_id)
        holder.item_id.inputType=InputType.TYPE_NULL
        holder.latest_inspection_day.setText(data[position].latest_inspection_day)
        holder.latest_inspection_day.inputType=InputType.TYPE_NULL
        holder.start_stock_up.setText(data[position].start_stock_up)
        holder.start_stock_up.inputType=InputType.TYPE_NULL
        holder.end_stock_up.setText(data[position].end_stock_up)
        holder.end_stock_up.inputType=InputType.TYPE_NULL
        holder.customer_poNo.setText(data[position].customer_poNo)
        holder.customer_poNo.inputType=InputType.TYPE_NULL
        holder.customer_code.setText(data[position].customer_code)
        holder.customer_code.inputType=InputType.TYPE_NULL
        holder.is_re_make.setText(data[position].is_re_make.toString())
        holder.is_re_make.isClickable=false
        holder.is_re_make.inputType=InputType.TYPE_NULL
        holder.qc_date.setText(data[position].qc_date)
        holder.qc_date.inputType=InputType.TYPE_NULL
        holder.qc_number.setText(data[position].qc_number)
        holder.qc_number.inputType=InputType.TYPE_NULL
        holder.unit_of_measurement.setText(data[position].unit_of_measurement)
        holder.unit_of_measurement.inputType=InputType.TYPE_NULL
        holder.est_start_date.setText(data[position].est_start_date)
        holder.est_start_date.inputType=InputType.TYPE_NULL
        holder.est_complete_date.setText(data[position].est_complete_date)
        holder.est_complete_date.inputType=InputType.TYPE_NULL
        holder.unit_of_timer.setText(data[position].unit_of_timer)
        holder.unit_of_timer.inputType=InputType.TYPE_NULL
        holder.start_date.setText(data[position].start_date)
        holder.start_date.inputType=InputType.TYPE_NULL
        holder.complete_date.setText(data[position].complete_date)
        holder.complete_date.inputType=InputType.TYPE_NULL
        holder.actual_output.setText(data[position].actual_output.toString())
        holder.actual_output.inputType=InputType.TYPE_NULL
        holder.is_inspected.setText(data[position].is_inspected.toString())
        holder.is_inspected.isClickable=false
        holder.is_inspected.inputType=InputType.TYPE_NULL
        holder.inspected_date.setText(data[position].inspected_date)
        holder.inspected_date.inputType=InputType.TYPE_NULL
        holder.is_passed.setText(data[position].is_passed.toString())
        holder.is_passed.isClickable=false
        holder.is_passed.inputType=InputType.TYPE_NULL
        holder.passed_time.setText(data[position].passed_time)
        holder.passed_time.inputType=InputType.TYPE_NULL
        holder.is_urgent.setText(data[position].is_urgent.toString())
        holder.is_urgent.isClickable=false
        holder.is_urgent.inputType=InputType.TYPE_NULL
        holder.urgent_deadline.setText(data[position].urgent_deadline)
        holder.urgent_deadline.inputType=InputType.TYPE_NULL



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
        var _id=itemView.findViewById<TextInputEditText>(R.id.edit_id)
        var is_non_production=itemView.findViewById<AutoCompleteTextView>(R.id.edit_is_non_production)
        var item_id=itemView.findViewById<AutoCompleteTextView>(R.id.edit_item_id)
        var latest_inspection_day=itemView.findViewById<TextInputEditText>(R.id.edit_latest_inspection_day)
        var start_stock_up=itemView.findViewById<TextInputEditText>(R.id.edit_start_stock_up)
        var end_stock_up=itemView.findViewById<TextInputEditText>(R.id.edit_end_stock_up)
        var customer_poNo=itemView.findViewById<TextInputEditText>(R.id.edit_customer_poNo)
        var customer_code=itemView.findViewById<TextInputEditText>(R.id.edit_customer_code)
        var is_re_make=itemView.findViewById<AutoCompleteTextView>(R.id.edit_is_re_make)
        var qc_date=itemView.findViewById<TextInputEditText>(R.id.edit_qc_date)
        var qc_number=itemView.findViewById<TextInputEditText>(R.id.edit_qc_number)
        var unit_of_measurement=itemView.findViewById<TextInputEditText>(R.id.edit_unit_of_measurement)
        var est_start_date=itemView.findViewById<TextInputEditText>(R.id.edit_est_start_date)
        var est_complete_date=itemView.findViewById<TextInputEditText>(R.id.edit_est_complete_date)
        var unit_of_timer=itemView.findViewById<TextInputEditText>(R.id.edit_unit_of_timer)
        var start_date=itemView.findViewById<TextInputEditText>(R.id.edit_start_date)
        var complete_date=itemView.findViewById<TextInputEditText>(R.id.edit_complete_date)
        var actual_output=itemView.findViewById<TextInputEditText>(R.id.edit_actual_output)
        var is_inspected=itemView.findViewById<AutoCompleteTextView>(R.id.edit_is_inspected)
        var inspected_date=itemView.findViewById<TextInputEditText>(R.id.edit_inspected_date)
        var is_passed=itemView.findViewById<AutoCompleteTextView>(R.id.edit_is_passed)
        var passed_time=itemView.findViewById<TextInputEditText>(R.id.edit_passed_time)
        var is_urgent=itemView.findViewById<AutoCompleteTextView>(R.id.edit_is_urgent)
        var urgent_deadline=itemView.findViewById<TextInputEditText>(R.id.edit_urgent_deadline)

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
        lateinit var oldData:ProductControlOrderHeader
        lateinit var newData:ProductControlOrderHeader
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
                        _id.inputType=InputType.TYPE_CLASS_TEXT
                        is_non_production.setAdapter(arrayAdapter)
                        is_non_production.isClickable=true
                        item_id.setAdapter(arrayAdapter01)
                        latest_inspection_day.inputType=InputType.TYPE_DATETIME_VARIATION_DATE
                        start_stock_up.inputType=InputType.TYPE_DATETIME_VARIATION_DATE
                        end_stock_up.inputType=InputType.TYPE_DATETIME_VARIATION_DATE
                        customer_poNo.inputType=InputType.TYPE_CLASS_TEXT
                        customer_code.inputType=InputType.TYPE_CLASS_TEXT
                        is_re_make.setAdapter(arrayAdapter)
                        is_re_make.isClickable=true
                        qc_date.inputType=InputType.TYPE_DATETIME_VARIATION_DATE
                        qc_number.inputType=InputType.TYPE_CLASS_TEXT
                        unit_of_measurement.inputType=InputType.TYPE_CLASS_TEXT
                        est_start_date.inputType=InputType.TYPE_DATETIME_VARIATION_DATE
                        est_complete_date.inputType=InputType.TYPE_DATETIME_VARIATION_DATE
                        unit_of_timer.inputType=InputType.TYPE_CLASS_TEXT
                        start_date.inputType=InputType.TYPE_DATETIME_VARIATION_DATE
                        complete_date.inputType=InputType.TYPE_DATETIME_VARIATION_DATE
                        actual_output.inputType=InputType.TYPE_CLASS_NUMBER
                        is_inspected.setAdapter(arrayAdapter)
                        is_inspected.isClickable=true
                        inspected_date.inputType=InputType.TYPE_DATETIME_VARIATION_DATE
                        is_passed.setAdapter(arrayAdapter)
                        is_passed.isClickable=true
                        passed_time.inputType=InputType.TYPE_CLASS_DATETIME
                        is_urgent.setAdapter(arrayAdapter)
                        is_urgent.isClickable=true
                        urgent_deadline.inputType=InputType.TYPE_CLASS_DATETIME



                        remark.isClickable=true
                        remark.isFocusable=true
                        remark.isFocusableInTouchMode=true
                        remark.isTextInputLayoutFocusedRectEnabled=true
                        _id.requestFocus()
                        edit_btn.text = "完成"
                    }
                    "完成"->{
                        newData= ProductControlOrderHeader()
                        newData._id=_id.text.toString()
                        _id.inputType=InputType.TYPE_NULL
                        newData.is_non_production=is_non_production.text.toString().toBoolean()
                        is_non_production.isClickable=false
                        is_non_production.setAdapter(null)
                        newData.item_id=item_id.text.toString()
                        item_id.setAdapter(null)
                        if(latest_inspection_day.text.toString()==""){
                            newData.latest_inspection_day=null
                        }
                        else{
                            newData.latest_inspection_day=latest_inspection_day.text.toString()
                        }

                        latest_inspection_day.inputType=InputType.TYPE_NULL
                        if(start_stock_up.text.toString()==""){
                            newData.start_stock_up=null
                        }
                        else{
                            newData.start_stock_up=start_stock_up.text.toString()
                        }
                        start_stock_up.inputType=InputType.TYPE_NULL
                        if(end_stock_up.text.toString()==""){
                            newData.end_stock_up=null
                        }
                        else{
                            newData.end_stock_up=end_stock_up.text.toString()
                        }
                        end_stock_up.inputType=InputType.TYPE_NULL
                        newData.customer_poNo=customer_poNo.text.toString()
                        customer_poNo.inputType=InputType.TYPE_NULL
                        newData.customer_code=customer_code.text.toString()
                        customer_code.inputType=InputType.TYPE_NULL
                        newData.is_re_make=is_re_make.text.toString().toBoolean()
                        is_re_make.isClickable=false
                        is_re_make.setAdapter(null)
                        if(qc_date.text.toString()==""){
                            newData.qc_date=null
                        }
                        else{
                            newData.qc_date=qc_date.text.toString()
                        }
                        qc_date.inputType=InputType.TYPE_NULL
                        newData.qc_number=qc_number.text.toString()
                        qc_number.inputType=InputType.TYPE_NULL
                        newData.unit_of_measurement=unit_of_measurement.text.toString()
                        unit_of_measurement.inputType=InputType.TYPE_NULL
                        if(est_start_date.text.toString()==""){
                            newData.est_start_date=null
                        }
                        else{
                            newData.est_start_date=est_start_date.text.toString()
                        }
                        est_start_date.inputType=InputType.TYPE_NULL
                        if(est_complete_date.text.toString()==""){
                            newData.est_complete_date=null
                        }
                        else{
                            newData.est_complete_date=est_complete_date.text.toString()
                        }
                        est_complete_date.inputType=InputType.TYPE_NULL
                        newData.unit_of_timer=unit_of_timer.text.toString()
                        unit_of_timer.inputType=InputType.TYPE_NULL
                        if(start_date.text.toString()==""){
                            newData.start_date=null
                        }
                        else{
                            newData.start_date=start_date.text.toString()
                        }
                        start_date.inputType=InputType.TYPE_NULL
                        if(complete_date.text.toString()==""){
                            newData.complete_date=null
                        }
                        else{
                            newData.complete_date=complete_date.text.toString()
                        }
                        complete_date.inputType=InputType.TYPE_NULL
                        newData.actual_output=actual_output.text.toString().toInt()
                        actual_output.inputType=InputType.TYPE_NULL
                        newData.is_inspected=is_inspected.text.toString().toBoolean()
                        is_inspected.isClickable=false
                        is_inspected.setAdapter(null)
                        if(inspected_date.text.toString()==""){
                            newData.inspected_date=null
                        }
                        else{
                            newData.inspected_date=inspected_date.text.toString()
                        }
                        inspected_date.inputType=InputType.TYPE_NULL
                        newData.is_passed=is_passed.text.toString().toBoolean()
                        is_passed.isClickable=false
                        is_passed.setAdapter(null)
                        if(passed_time.text.toString()==""){
                            newData.passed_time=null
                        }
                        else{
                            newData.passed_time=passed_time.text.toString()
                        }
                        passed_time.inputType=InputType.TYPE_NULL
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

                        newData.remark=remark.text.toString()
                        remark.isClickable=false
                        remark.isFocusable=false
                        remark.isFocusableInTouchMode=false
                        remark.isTextInputLayoutFocusedRectEnabled=false
                        //Log.d("GSON", "msg: ${oldData}\n")
                        //Log.d("GSON", "msg: ${newData.remark}\n")
                        edit_ProductControlOrder("ProductControlOrderHeader",oldData,newData)//更改資料庫資料
                        when(cookie_data.status){
                            0-> {//成功
                                itemData.data[adapterPosition] = newData//更改渲染資料
                                editor.setText(cookie_data.username)
                                Toast.makeText(itemView.context, cookie_data.msg, Toast.LENGTH_SHORT).show()
                            }
                            1->{//失敗
                                Toast.makeText(itemView.context, cookie_data.msg, Toast.LENGTH_SHORT).show()
                                _id.setText(oldData._id)
                                is_non_production.setText(oldData.is_non_production.toString())
                                item_id.setText(oldData.item_id)
                                latest_inspection_day.setText(oldData.latest_inspection_day)
                                start_stock_up.setText(oldData.start_stock_up)
                                end_stock_up.setText(oldData.end_stock_up)
                                customer_poNo.setText(oldData.customer_poNo)
                                customer_code.setText(oldData.customer_code)
                                is_re_make.setText(oldData.is_re_make.toString())
                                qc_date.setText(oldData.qc_date)
                                qc_number.setText(oldData.qc_number)
                                unit_of_measurement.setText(oldData.unit_of_measurement)
                                est_start_date.setText(oldData.est_start_date)
                                est_complete_date.setText(oldData.est_complete_date)
                                unit_of_timer.setText(oldData.unit_of_timer)
                                start_date.setText(oldData.start_date)
                                complete_date.setText(oldData.complete_date)
                                actual_output.setText(oldData.actual_output.toString())
                                is_inspected.setText(oldData.is_inspected.toString())
                                inspected_date.setText(oldData.inspected_date)
                                is_passed.setText(oldData.is_passed.toString())
                                passed_time.setText(oldData.passed_time)
                                is_urgent.setText(oldData.is_urgent.toString())
                                urgent_deadline.setText(oldData.urgent_deadline)


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
                    delete_ProductControlOrder("ProductControlOrderHeader",data[adapterPosition])
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
                    lock_ProductControlOrder("ProductControlOrderHeader",data[adapterPosition])
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
                    over_ProductControlOrder("ProductControlOrderHeader",data[adapterPosition])
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
        private fun edit_ProductControlOrder(operation:String,oldData:ProductControlOrderHeader,newData:ProductControlOrderHeader) {
            val old =JSONObject()
            old.put("_id",oldData._id)
            old.put("is_non_production",oldData.is_non_production)
            old.put("item_id",oldData.item_id)
            old.put("latest_inspection_day",oldData.latest_inspection_day)
            old.put("start_stock_up",oldData.start_stock_up)
            old.put("end_stock_up",oldData.end_stock_up)
            old.put("customer_poNo",oldData.customer_poNo)
            old.put("customer_code",oldData.customer_code)
            old.put("is_re_make",oldData.is_re_make)
            old.put("qc_date",oldData.qc_date)
            old.put("qc_number",oldData.qc_number)
            old.put("unit_of_measurement",oldData.unit_of_measurement)
            old.put("est_start_date",oldData.est_start_date)
            old.put("est_complete_date",oldData.est_complete_date)
            old.put("unit_of_timer",oldData.unit_of_timer)
            old.put("start_date",oldData.start_date)
            old.put("complete_date",oldData.complete_date)
            old.put("actual_output",oldData.actual_output)
            old.put("is_inspected",oldData.is_inspected)
            old.put("inspected_date",oldData.inspected_date)
            old.put("is_passed",oldData.is_passed)
            old.put("passed_time",oldData.passed_time)
            old.put("is_urgent",oldData.is_urgent)
            old.put("urgent_deadline",oldData.urgent_deadline)

            old.put("remark",oldData.remark)
            val new =JSONObject()
            new.put("_id",newData._id)
            new.put("is_non_production",newData.is_non_production)
            new.put("item_id",newData.item_id)
            new.put("latest_inspection_day",newData.latest_inspection_day)
            new.put("start_stock_up",newData.start_stock_up)
            new.put("end_stock_up",newData.end_stock_up)
            new.put("customer_poNo",newData.customer_poNo)
            new.put("customer_code",newData.customer_code)
            new.put("is_re_make",newData.is_re_make)
            new.put("qc_date",newData.qc_date)
            new.put("qc_number",newData.qc_number)
            new.put("unit_of_measurement",newData.unit_of_measurement)
            new.put("est_start_date",newData.est_start_date)
            new.put("est_complete_date",newData.est_complete_date)
            new.put("unit_of_timer",newData.unit_of_timer)
            new.put("start_date",newData.start_date)
            new.put("complete_date",newData.complete_date)
            new.put("actual_output",newData.actual_output)
            new.put("is_inspected",newData.is_inspected)
            new.put("inspected_date",newData.inspected_date)
            new.put("is_passed",newData.is_passed)
            new.put("passed_time",newData.passed_time)
            new.put("is_urgent",newData.is_urgent)
            new.put("urgent_deadline",newData.urgent_deadline)

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
                .url(cookie_data.URL+"/production_control_sheet_management")
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
        private fun delete_ProductControlOrder(operation:String,deleteData:ProductControlOrderHeader) {
            val delete = JSONObject()
            delete.put("_id",deleteData._id)
            delete.put("is_non_production",deleteData.is_non_production)
            delete.put("item_id",deleteData.item_id)
            delete.put("latest_inspection_day",deleteData.latest_inspection_day)
            delete.put("start_stock_up",deleteData.start_stock_up)
            delete.put("end_stock_up",deleteData.end_stock_up)
            delete.put("customer_poNo",deleteData.customer_poNo)
            delete.put("customer_code",deleteData.customer_code)
            delete.put("is_re_make",deleteData.is_re_make)
            delete.put("qc_date",deleteData.qc_date)
            delete.put("qc_number",deleteData.qc_number)
            delete.put("unit_of_measurement",deleteData.unit_of_measurement)
            delete.put("est_start_date",deleteData.est_start_date)
            delete.put("est_complete_date",deleteData.est_complete_date)
            delete.put("unit_of_timer",deleteData.unit_of_timer)
            delete.put("start_date",deleteData.start_date)
            delete.put("complete_date",deleteData.complete_date)
            delete.put("actual_output",deleteData.actual_output)
            delete.put("is_inspected",deleteData.is_inspected)
            delete.put("inspected_date",deleteData.inspected_date)
            delete.put("is_passed",deleteData.is_passed)
            delete.put("passed_time",deleteData.passed_time)
            delete.put("is_urgent",deleteData.is_urgent)
            delete.put("urgent_deadline",deleteData.urgent_deadline)

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
                .url(cookie_data.URL+"/production_control_sheet_management")
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
        private fun lock_ProductControlOrder(operation:String,lockData:ProductControlOrderHeader) {
            val lock = JSONObject()
            lock.put("_id",lockData._id)
            lock.put("is_non_production",lockData.is_non_production)
            lock.put("item_id",lockData.item_id)
            lock.put("latest_inspection_day",lockData.latest_inspection_day)
            lock.put("start_stock_up",lockData.start_stock_up)
            lock.put("end_stock_up",lockData.end_stock_up)
            lock.put("customer_poNo",lockData.customer_poNo)
            lock.put("customer_code",lockData.customer_code)
            lock.put("is_re_make",lockData.is_re_make)
            lock.put("qc_date",lockData.qc_date)
            lock.put("qc_number",lockData.qc_number)
            lock.put("unit_of_measurement",lockData.unit_of_measurement)
            lock.put("est_start_date",lockData.est_start_date)
            lock.put("est_complete_date",lockData.est_complete_date)
            lock.put("unit_of_timer",lockData.unit_of_timer)
            lock.put("start_date",lockData.start_date)
            lock.put("complete_date",lockData.complete_date)
            lock.put("actual_output",lockData.actual_output)
            lock.put("is_inspected",lockData.is_inspected)
            lock.put("inspected_date",lockData.inspected_date)
            lock.put("is_passed",lockData.is_passed)
            lock.put("passed_time",lockData.passed_time)
            lock.put("is_urgent",lockData.is_urgent)
            lock.put("urgent_deadline",lockData.urgent_deadline)

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
                .url(cookie_data.URL+"/production_control_sheet_management")
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
        private fun over_ProductControlOrder(operation:String,overData:ProductControlOrderHeader) {
            val over = JSONObject()
            over.put("_id",overData._id)
            over.put("is_non_production",overData.is_non_production)
            over.put("item_id",overData.item_id)
            over.put("latest_inspection_day",overData.latest_inspection_day)
            over.put("start_stock_up",overData.start_stock_up)
            over.put("end_stock_up",overData.end_stock_up)
            over.put("customer_poNo",overData.customer_poNo)
            over.put("customer_code",overData.customer_code)
            over.put("is_re_make",overData.is_re_make)
            over.put("qc_date",overData.qc_date)
            over.put("qc_number",overData.qc_number)
            over.put("unit_of_measurement",overData.unit_of_measurement)
            over.put("est_start_date",overData.est_start_date)
            over.put("est_complete_date",overData.est_complete_date)
            over.put("unit_of_timer",overData.unit_of_timer)
            over.put("start_date",overData.start_date)
            over.put("complete_date",overData.complete_date)
            over.put("actual_output",overData.actual_output)
            over.put("is_inspected",overData.is_inspected)
            over.put("inspected_date",overData.inspected_date)
            over.put("is_passed",overData.is_passed)
            over.put("passed_time",overData.passed_time)
            over.put("is_urgent",overData.is_urgent)
            over.put("urgent_deadline",overData.urgent_deadline)

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
                .url(cookie_data.URL+"/production_control_sheet_management")
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
    fun addItem(addData:ProductControlOrderHeader){
        data.add(itemData.count,addData)
        notifyItemInserted(itemData.count)
        itemData.count+=1//cookie_data.itemCount+=1
    }

}


