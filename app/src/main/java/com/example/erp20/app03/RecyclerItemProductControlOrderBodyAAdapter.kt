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


class RecyclerItemProductControlOrderBodyAAdapter() :
    RecyclerView.Adapter<RecyclerItemProductControlOrderBodyAAdapter.ViewHolder>() {
    private var itemData: ShowProductControlOrderBody_A =Gson().fromJson(cookie_data.response_data, ShowProductControlOrderBody_A::class.java)
    private var data: ArrayList<ProductControlOrderBody_A> =itemData.data
    var relativeCombobox01=cookie_data.MeHeader_id_ComboboxData
    var relativeCombobox02=cookie_data.semi_finished_product_number_ComboboxData
    var relativeCombobox03=cookie_data.pline_name_ComboboxData

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemProductControlOrderBodyAAdapter.ViewHolder {
        val v= LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_product_control_order_body_a,parent,false)
        return ViewHolder(v)

    }

    override fun onBindViewHolder(holder: RecyclerItemProductControlOrderBodyAAdapter.ViewHolder, position: Int) {

        //Log.d("GSON", "msg: ${itemData.data[position]}\n")
        holder.header_id.setText(data[position].header_id)
        holder.header_id.inputType=InputType.TYPE_NULL
        holder.prod_ctrl_order_number.setText(data[position].prod_ctrl_order_number)
        holder.prod_ctrl_order_number.inputType=InputType.TYPE_NULL
        holder.me_code.setText(data[position].me_code)
        holder.me_code.inputType=InputType.TYPE_NULL
        holder.semi_finished_prod_number.setText(data[position].semi_finished_prod_number)
        holder.semi_finished_prod_number.inputType=InputType.TYPE_NULL
        holder.pline_id.setText(data[position].pline_id)
        holder.pline_id.inputType=InputType.TYPE_NULL
        holder.latest_inspection_day.setText(data[position].latest_inspection_day)
        holder.latest_inspection_day.inputType=InputType.TYPE_NULL
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
        holder.est_output.setText(data[position].est_output.toString())
        holder.est_output.inputType=InputType.TYPE_NULL
        holder.unit_of_timer.setText(data[position].unit_of_timer)
        holder.unit_of_timer.inputType=InputType.TYPE_NULL
        holder.start_date.setText(data[position].start_date)
        holder.start_date.inputType=InputType.TYPE_NULL
        holder.complete_date.setText(data[position].complete_date)
        holder.complete_date.inputType=InputType.TYPE_NULL
        holder.actual_output.setText(data[position].actual_output.toString())
        holder.actual_output.inputType=InputType.TYPE_NULL
        holder.is_request_support.setText(data[position].is_request_support.toString())
        holder.is_request_support.isClickable=false
        holder.is_request_support.inputType=InputType.TYPE_NULL
        holder.number_of_support.setText(data[position].number_of_support.toString())
        holder.number_of_support.inputType=InputType.TYPE_NULL
        holder.number_of_supported.setText(data[position].number_of_supported.toString())
        holder.number_of_supported.inputType=InputType.TYPE_NULL
        holder.request_support_time.setText(data[position].request_support_time)
        holder.request_support_time.inputType=InputType.TYPE_NULL
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
        var header_id=itemView.findViewById<TextInputEditText>(R.id.edit_header_id)
        var prod_ctrl_order_number=itemView.findViewById<TextInputEditText>(R.id.edit_prod_ctrl_order_number)
        var me_code=itemView.findViewById<AutoCompleteTextView>(R.id.edit_me_code)
        var semi_finished_prod_number=itemView.findViewById<AutoCompleteTextView>(R.id.edit_semi_finished_prod_number)
        var pline_id=itemView.findViewById<AutoCompleteTextView>(R.id.edit_pline_id)
        var latest_inspection_day =itemView.findViewById<TextInputEditText>(R.id.edit_latest_inspection_day )
        var is_re_make=itemView.findViewById<AutoCompleteTextView>(R.id.edit_is_re_make)
        var qc_date=itemView.findViewById<TextInputEditText>(R.id.edit_qc_date)
        var qc_number=itemView.findViewById<TextInputEditText>(R.id.edit_qc_number)
        var unit_of_measurement=itemView.findViewById<TextInputEditText>(R.id.edit_unit_of_measurement)
        var est_start_date=itemView.findViewById<TextInputEditText>(R.id.edit_est_start_date)
        var est_complete_date=itemView.findViewById<TextInputEditText>(R.id.edit_est_complete_date)
        var est_output=itemView.findViewById<TextInputEditText>(R.id.edit_est_output)
        var unit_of_timer=itemView.findViewById<TextInputEditText>(R.id.edit_unit_of_timer)
        var start_date=itemView.findViewById<TextInputEditText>(R.id.edit_start_date)
        var complete_date=itemView.findViewById<TextInputEditText>(R.id.edit_complete_date)
        var actual_output=itemView.findViewById<TextInputEditText>(R.id.edit_actual_output)
        var is_request_support=itemView.findViewById<AutoCompleteTextView>(R.id.edit_is_request_support)
        var number_of_support=itemView.findViewById<TextInputEditText>(R.id.edit_number_of_support)
        var number_of_supported=itemView.findViewById<TextInputEditText>(R.id.edit_number_of_supported)
        var request_support_time=itemView.findViewById<TextInputEditText>(R.id.edit_request_support_time)
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
        lateinit var oldData:ProductControlOrderBody_A
        lateinit var newData:ProductControlOrderBody_A
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
                        //header_id.inputType=InputType.TYPE_CLASS_TEXT
                        //prod_ctrl_order_number.inputType=InputType.TYPE_CLASS_TEXT
                        me_code.setAdapter(arrayAdapter01)
                        semi_finished_prod_number.setAdapter(arrayAdapter02)
                        pline_id.setAdapter(arrayAdapter03)
                        latest_inspection_day.inputType=InputType.TYPE_DATETIME_VARIATION_DATE
                        is_re_make.setAdapter(arrayAdapter)
                        is_re_make.isClickable=true
                        qc_date.inputType=InputType.TYPE_DATETIME_VARIATION_DATE
                        qc_number.inputType=InputType.TYPE_CLASS_TEXT
                        unit_of_measurement.inputType=InputType.TYPE_CLASS_TEXT
                        est_start_date.inputType=InputType.TYPE_DATETIME_VARIATION_DATE
                        est_complete_date.inputType=InputType.TYPE_DATETIME_VARIATION_DATE
                        est_output.inputType=InputType.TYPE_CLASS_NUMBER
                        unit_of_timer.inputType=InputType.TYPE_CLASS_TEXT
                        start_date.inputType=InputType.TYPE_DATETIME_VARIATION_DATE
                        complete_date.inputType=InputType.TYPE_DATETIME_VARIATION_DATE
                        actual_output.inputType=InputType.TYPE_CLASS_NUMBER
                        is_request_support.setAdapter(arrayAdapter)
                        is_request_support.isClickable=true
                        number_of_support.inputType=InputType.TYPE_CLASS_NUMBER
                        number_of_supported.inputType=InputType.TYPE_CLASS_NUMBER
                        request_support_time.inputType=InputType.TYPE_CLASS_DATETIME
                        is_urgent.setAdapter(arrayAdapter)
                        is_urgent.isClickable=true
                        urgent_deadline.inputType=InputType.TYPE_CLASS_DATETIME



                        remark.isClickable=true
                        remark.isFocusable=true
                        remark.isFocusableInTouchMode=true
                        remark.isTextInputLayoutFocusedRectEnabled=true
                        me_code.requestFocus()
                        edit_btn.text = "完成"
                    }
                    "完成"->{
                        newData= ProductControlOrderBody_A()
                        newData.header_id=header_id.text.toString()
                        header_id.inputType=InputType.TYPE_NULL
                        newData.prod_ctrl_order_number=prod_ctrl_order_number.text.toString()
                        prod_ctrl_order_number.inputType=InputType.TYPE_NULL
                        newData.me_code=me_code.text.toString()
                        me_code.setAdapter(null)
                        newData.semi_finished_prod_number=semi_finished_prod_number.text.toString()
                        semi_finished_prod_number.setAdapter(null)
                        newData.pline_id=pline_id.text.toString()
                        pline_id.setAdapter(null)
                        if(latest_inspection_day.text.toString()==""){
                            newData.latest_inspection_day=null
                        }
                        else{
                            newData.latest_inspection_day=latest_inspection_day.text.toString()
                        }
                        latest_inspection_day.inputType=InputType.TYPE_NULL
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
                        newData.est_output=est_output.text.toString().toInt()
                        est_output.inputType=InputType.TYPE_NULL
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
                        newData.is_request_support=is_request_support.text.toString().toBoolean()
                        is_request_support.isClickable=false
                        is_request_support.setAdapter(null)
                        newData.number_of_support=number_of_support.text.toString().toInt()
                        number_of_support.inputType=InputType.TYPE_NULL
                        newData.number_of_supported=number_of_supported.text.toString().toInt()
                        number_of_supported.inputType=InputType.TYPE_NULL
                        if(request_support_time.text.toString()==""){
                            newData.request_support_time=null
                        }
                        else{
                            newData.request_support_time=request_support_time.text.toString()
                        }
                        request_support_time.inputType=InputType.TYPE_NULL
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
                        edit_ProductControlOrder("ProductControlOrderBody_A",oldData,newData)//更改資料庫資料
                        when(cookie_data.status){
                            0-> {//成功
                                itemData.data[adapterPosition] = newData//更改渲染資料
                                editor.setText(cookie_data.username)
                                Toast.makeText(itemView.context, cookie_data.msg, Toast.LENGTH_SHORT).show()
                            }
                            1->{//失敗
                                Toast.makeText(itemView.context, cookie_data.msg, Toast.LENGTH_SHORT).show()
                                header_id.setText(oldData.header_id)
                                prod_ctrl_order_number.setText(oldData.prod_ctrl_order_number)
                                me_code.setText(oldData.me_code)
                                semi_finished_prod_number.setText(oldData.semi_finished_prod_number)
                                pline_id.setText(oldData.pline_id)
                                latest_inspection_day.setText(oldData.latest_inspection_day)
                                is_re_make.setText(oldData.is_re_make.toString())
                                qc_date.setText(oldData.qc_date)
                                qc_number.setText(oldData.qc_number)
                                unit_of_measurement.setText(oldData.unit_of_measurement)
                                est_start_date.setText(oldData.est_start_date)
                                est_complete_date.setText(oldData.est_complete_date)
                                est_output.setText(oldData.est_output.toString())
                                unit_of_timer.setText(oldData.unit_of_timer)
                                start_date.setText(oldData.start_date)
                                complete_date.setText(oldData.complete_date)
                                actual_output.setText(oldData.actual_output.toString())
                                is_request_support.setText(oldData.is_request_support.toString())
                                number_of_support.setText(oldData.number_of_support.toString())
                                number_of_supported.setText(oldData.number_of_supported.toString())
                                request_support_time.setText(oldData.request_support_time)
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
                    delete_ProductControlOrder("ProductControlOrderBody_A",data[adapterPosition])
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
                    lock_ProductControlOrder("ProductControlOrderBody_A",data[adapterPosition])
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
                    over_ProductControlOrder("ProductControlOrderBody_A",data[adapterPosition])
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
        private fun edit_ProductControlOrder(operation:String,oldData:ProductControlOrderBody_A,newData:ProductControlOrderBody_A) {
            val old =JSONObject()
            old.put("header_id",oldData.header_id)
            old.put("prod_ctrl_order_number",oldData.prod_ctrl_order_number)
            old.put("me_code",oldData.me_code)
            old.put("semi_finished_prod_number",oldData.semi_finished_prod_number)
            old.put("pline_id",oldData.pline_id)
            old.put("latest_inspection_day",oldData.latest_inspection_day)
            old.put("is_re_make",oldData.is_re_make)
            old.put("qc_date",oldData.qc_date)
            old.put("qc_number",oldData.qc_number)
            old.put("unit_of_measurement",oldData.unit_of_measurement)
            old.put("est_start_date",oldData.est_start_date)
            old.put("est_complete_date",oldData.est_complete_date)
            old.put("est_output",oldData.est_output)
            old.put("unit_of_timer",oldData.unit_of_timer)
            old.put("start_date",oldData.start_date)
            old.put("complete_date",oldData.complete_date)
            old.put("actual_output",oldData.actual_output)
            old.put("is_request_support",oldData.is_request_support)
            old.put("number_of_support",oldData.number_of_support)
            old.put("number_of_supported",oldData.number_of_supported)
            old.put("request_support_time",oldData.request_support_time)
            old.put("is_urgent",oldData.is_urgent)
            old.put("urgent_deadline",oldData.urgent_deadline)

            old.put("remark",oldData.remark)
            val new =JSONObject()
            new.put("header_id",newData.header_id)
            new.put("prod_ctrl_order_number",newData.prod_ctrl_order_number)
            new.put("me_code",newData.me_code)
            new.put("semi_finished_prod_number",newData.semi_finished_prod_number)
            new.put("pline_id",newData.pline_id)
            new.put("latest_inspection_day",newData.latest_inspection_day)
            new.put("is_re_make",newData.is_re_make)
            new.put("qc_date",newData.qc_date)
            new.put("qc_number",newData.qc_number)
            new.put("unit_of_measurement",newData.unit_of_measurement)
            new.put("est_start_date",newData.est_start_date)
            new.put("est_complete_date",newData.est_complete_date)
            new.put("est_output",newData.est_output)
            new.put("unit_of_timer",newData.unit_of_timer)
            new.put("start_date",newData.start_date)
            new.put("complete_date",newData.complete_date)
            new.put("actual_output",newData.actual_output)
            new.put("is_request_support",newData.is_request_support)
            new.put("number_of_support",newData.number_of_support)
            new.put("number_of_supported",newData.number_of_supported)
            new.put("request_support_time",newData.request_support_time)
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
        private fun delete_ProductControlOrder(operation:String,deleteData:ProductControlOrderBody_A) {
            val delete = JSONObject()
            delete.put("header_id",deleteData.header_id)
            delete.put("prod_ctrl_order_number",deleteData.prod_ctrl_order_number)
            delete.put("me_code",deleteData.me_code)
            delete.put("semi_finished_prod_number",deleteData.semi_finished_prod_number)
            delete.put("pline_id",deleteData.pline_id)
            delete.put("latest_inspection_day",deleteData.latest_inspection_day)
            delete.put("is_re_make",deleteData.is_re_make)
            delete.put("qc_date",deleteData.qc_date)
            delete.put("qc_number",deleteData.qc_number)
            delete.put("unit_of_measurement",deleteData.unit_of_measurement)
            delete.put("est_start_date",deleteData.est_start_date)
            delete.put("est_complete_date",deleteData.est_complete_date)
            delete.put("est_output",deleteData.est_output)
            delete.put("unit_of_timer",deleteData.unit_of_timer)
            delete.put("start_date",deleteData.start_date)
            delete.put("complete_date",deleteData.complete_date)
            delete.put("actual_output",deleteData.actual_output)
            delete.put("is_request_support",deleteData.is_request_support)
            delete.put("number_of_support",deleteData.number_of_support)
            delete.put("number_of_supported",deleteData.number_of_supported)
            delete.put("request_support_time",deleteData.request_support_time)
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
        private fun lock_ProductControlOrder(operation:String,lockData:ProductControlOrderBody_A) {
            val lock = JSONObject()
            lock.put("header_id",lockData.header_id)
            lock.put("prod_ctrl_order_number",lockData.prod_ctrl_order_number)
            lock.put("me_code",lockData.me_code)
            lock.put("semi_finished_prod_number",lockData.semi_finished_prod_number)
            lock.put("pline_id",lockData.pline_id)
            lock.put("latest_inspection_day",lockData.latest_inspection_day)
            lock.put("is_re_make",lockData.is_re_make)
            lock.put("qc_date",lockData.qc_date)
            lock.put("qc_number",lockData.qc_number)
            lock.put("unit_of_measurement",lockData.unit_of_measurement)
            lock.put("est_start_date",lockData.est_start_date)
            lock.put("est_complete_date",lockData.est_complete_date)
            lock.put("est_output",lockData.est_output)
            lock.put("unit_of_timer",lockData.unit_of_timer)
            lock.put("start_date",lockData.start_date)
            lock.put("complete_date",lockData.complete_date)
            lock.put("actual_output",lockData.actual_output)
            lock.put("is_request_support",lockData.is_request_support)
            lock.put("number_of_support",lockData.number_of_support)
            lock.put("number_of_supported",lockData.number_of_supported)
            lock.put("request_support_time",lockData.request_support_time)
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
        private fun over_ProductControlOrder(operation:String,overData:ProductControlOrderBody_A) {
            val over = JSONObject()
            over.put("header_id",overData.header_id)
            over.put("prod_ctrl_order_number",overData.prod_ctrl_order_number)
            over.put("me_code",overData.me_code)
            over.put("semi_finished_prod_number",overData.semi_finished_prod_number)
            over.put("pline_id",overData.pline_id)
            over.put("latest_inspection_day",overData.latest_inspection_day)
            over.put("is_re_make",overData.is_re_make)
            over.put("qc_date",overData.qc_date)
            over.put("qc_number",overData.qc_number)
            over.put("unit_of_measurement",overData.unit_of_measurement)
            over.put("est_start_date",overData.est_start_date)
            over.put("est_complete_date",overData.est_complete_date)
            over.put("est_output",overData.est_output)
            over.put("unit_of_timer",overData.unit_of_timer)
            over.put("start_date",overData.start_date)
            over.put("complete_date",overData.complete_date)
            over.put("actual_output",overData.actual_output)
            over.put("is_request_support",overData.is_request_support)
            over.put("number_of_support",overData.number_of_support)
            over.put("number_of_supported",overData.number_of_supported)
            over.put("request_support_time",overData.request_support_time)
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
    fun addItem(addData:ProductControlOrderBody_A){
        data.add(itemData.count,addData)
        notifyItemInserted(itemData.count)
        itemData.count+=1//cookie_data.itemCount+=1
    }

}


