package com.example.erp20.app05
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


class RecyclerItemStackingControlListHeaderAdapter() :
    RecyclerView.Adapter<RecyclerItemStackingControlListHeaderAdapter.ViewHolder>() {
    private var itemData: ShowStackingControlListHeader =Gson().fromJson(cookie_data.response_data, ShowStackingControlListHeader::class.java)
    private var data: ArrayList<StackingControlListHeader> =itemData.data
    var relativeCombobox01=cookie_data.cont_id_ComboboxData
    var relativeCombobox02=cookie_data.cont_type_code_ComboboxData
    var relativeCombobox03=cookie_data.port_id_ComboboxData
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v= LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_stacking_control_list_header,parent,false)
        return ViewHolder(v)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        //Log.d("GSON", "msg: ${itemData.data[position]}\n")
        holder.code.setText(data[position].code)
        holder.code.inputType=InputType.TYPE_NULL
        holder.contNo.setText(data[position].contNo)
        holder.contNo.inputType=InputType.TYPE_NULL
        holder.work_year.setText(data[position].work_year)
        holder.work_year.inputType=InputType.TYPE_NULL
        holder.customer_poNo.setText(data[position].customer_poNo)
        holder.customer_poNo.inputType=InputType.TYPE_NULL
        holder.cont_type_code.setText(data[position].cont_type_code)
        holder.cont_type_code.inputType=InputType.TYPE_NULL
        holder.sws.setText(data[position].sws)
        holder.sws.inputType=InputType.TYPE_NULL
        holder.swe.setText(data[position].swe)
        holder.swe.inputType=InputType.TYPE_NULL
        holder.shipping_order_No.setText(data[position].shipping_order_No)
        holder.shipping_order_No.inputType=InputType.TYPE_NULL
        holder.last_swe.setText(data[position].last_swe)
        holder.last_swe.inputType=InputType.TYPE_NULL
        holder.sailing_date.setText(data[position].sailing_date)
        holder.sailing_date.inputType=InputType.TYPE_NULL
        holder.port_id.setText(data[position].port_id)
        holder.port_id.inputType=InputType.TYPE_NULL
        holder.car_id.setText(data[position].car_id)
        holder.car_id.inputType=InputType.TYPE_NULL
        holder.date_provided.setText(data[position].date_provided)
        holder.date_provided.inputType=InputType.TYPE_NULL
        holder.est_start_stacking_date.setText(data[position].est_start_stacking_date)
        holder.est_start_stacking_date.inputType=InputType.TYPE_NULL
        holder.est_stop_stacking_date.setText(data[position].est_stop_stacking_date)
        holder.est_stop_stacking_date.inputType=InputType.TYPE_NULL
        holder.est_work_hours.setText(data[position].est_work_hours.toString())
        holder.est_work_hours.inputType=InputType.TYPE_NULL
        holder.unit_of_timer.setText(data[position].unit_of_timer)
        holder.unit_of_timer.inputType=InputType.TYPE_NULL
        holder.worker.setText(data[position].worker.toString())
        holder.worker.inputType=InputType.TYPE_NULL
        holder.start_stacking_date.setText(data[position].start_stacking_date)
        holder.start_stacking_date.inputType=InputType.TYPE_NULL
        holder.stop_stacking_date.setText(data[position].stop_stacking_date)
        holder.stop_stacking_date.inputType=InputType.TYPE_NULL
        holder.is_urgent.setText(data[position].is_urgent.toString())
        holder.is_urgent.isClickable=false
        holder.is_urgent.inputType=InputType.TYPE_NULL
        holder.urgent_deadline.setText(data[position].urgent_deadline)
        holder.urgent_deadline.inputType=InputType.TYPE_NULL
        holder.is_finished.setText(data[position].is_finished.toString())
        holder.is_finished.isClickable=false
        holder.is_finished.inputType=InputType.TYPE_NULL
        holder.finished_date.setText(data[position].finished_date)
        holder.finished_date.inputType=InputType.TYPE_NULL



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
        var code=itemView.findViewById<TextInputEditText>(R.id.edit_code)
        var contNo=itemView.findViewById<AutoCompleteTextView>(R.id.edit_contNo)
        var work_year=itemView.findViewById<TextInputEditText>(R.id.edit_work_year)
        var customer_poNo=itemView.findViewById<TextInputEditText>(R.id.edit_customer_poNo)
        var cont_type_code=itemView.findViewById<AutoCompleteTextView>(R.id.edit_cont_type_code)
        var sws=itemView.findViewById<TextInputEditText>(R.id.edit_sws)
        var swe=itemView.findViewById<TextInputEditText>(R.id.edit_swe)
        var shipping_order_No=itemView.findViewById<TextInputEditText>(R.id.edit_shipping_order_No)
        var last_swe=itemView.findViewById<TextInputEditText>(R.id.edit_last_swe)
        var sailing_date=itemView.findViewById<TextInputEditText>(R.id.edit_sailing_date)
        var port_id=itemView.findViewById<AutoCompleteTextView>(R.id.edit_port_id)
        var car_id=itemView.findViewById<TextInputEditText>(R.id.edit_car_id)
        var date_provided=itemView.findViewById<TextInputEditText>(R.id.edit_date_provided)
        var est_start_stacking_date=itemView.findViewById<TextInputEditText>(R.id.edit_est_start_stacking_date)
        var est_stop_stacking_date=itemView.findViewById<TextInputEditText>(R.id.edit_est_stop_stacking_date)
        var est_work_hours=itemView.findViewById<TextInputEditText>(R.id.edit_est_work_hours)
        var unit_of_timer=itemView.findViewById<TextInputEditText>(R.id.edit_unit_of_timer)
        var worker=itemView.findViewById<TextInputEditText>(R.id.edit_worker)
        var start_stacking_date=itemView.findViewById<TextInputEditText>(R.id.edit_start_stacking_date)
        var stop_stacking_date=itemView.findViewById<TextInputEditText>(R.id.edit_stop_stacking_date)
        var is_urgent=itemView.findViewById<AutoCompleteTextView>(R.id.edit_is_urgent)
        var urgent_deadline=itemView.findViewById<TextInputEditText>(R.id.edit_urgent_deadline)
        var is_finished=itemView.findViewById<AutoCompleteTextView>(R.id.edit_is_finished)
        var finished_date=itemView.findViewById<TextInputEditText>(R.id.edit_finished_date)

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
        lateinit var oldData:StackingControlListHeader
        lateinit var newData:StackingControlListHeader
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
                        //code.inputType=InputType.TYPE_CLASS_TEXT
                        contNo.setAdapter(arrayAdapter01)
                        work_year.inputType=InputType.TYPE_CLASS_TEXT
                        customer_poNo.inputType=InputType.TYPE_CLASS_TEXT
                        cont_type_code.setAdapter(arrayAdapter02)
                        sws.inputType=InputType.TYPE_DATETIME_VARIATION_DATE
                        swe.inputType=InputType.TYPE_DATETIME_VARIATION_DATE
                        shipping_order_No.inputType=InputType.TYPE_CLASS_TEXT
                        last_swe.inputType=InputType.TYPE_DATETIME_VARIATION_DATE
                        sailing_date.inputType=InputType.TYPE_DATETIME_VARIATION_DATE
                        port_id.setAdapter(arrayAdapter03)
                        car_id.inputType=InputType.TYPE_CLASS_TEXT
                        date_provided.inputType=InputType.TYPE_DATETIME_VARIATION_DATE
                        est_start_stacking_date.inputType=InputType.TYPE_CLASS_DATETIME
                        est_stop_stacking_date.inputType=InputType.TYPE_CLASS_DATETIME
                        est_work_hours.inputType=InputType.TYPE_CLASS_NUMBER
                        unit_of_timer.inputType=InputType.TYPE_CLASS_TEXT
                        worker.inputType=InputType.TYPE_CLASS_NUMBER
                        start_stacking_date.inputType=InputType.TYPE_CLASS_DATETIME
                        stop_stacking_date.inputType=InputType.TYPE_CLASS_DATETIME
                        is_urgent.setAdapter(arrayAdapter)
                        is_urgent.isClickable=true
                        urgent_deadline.inputType=InputType.TYPE_CLASS_DATETIME
                        is_finished.setAdapter(arrayAdapter)
                        is_finished.isClickable=true
                        finished_date.inputType=InputType.TYPE_CLASS_DATETIME



                        remark.isClickable=true
                        remark.isFocusable=true
                        remark.isFocusableInTouchMode=true
                        remark.isTextInputLayoutFocusedRectEnabled=true
                        contNo.requestFocus()
                        edit_btn.text = "完成"
                    }
                    "完成"->{
                        newData= StackingControlListHeader()
                        newData.code=code.text.toString()
                        code.inputType=InputType.TYPE_NULL
                        newData.contNo=contNo.text.toString()
                        contNo.setAdapter(null)
                        newData.work_year=work_year.text.toString()
                        work_year.inputType=InputType.TYPE_NULL
                        newData.customer_poNo=customer_poNo.text.toString()
                        customer_poNo.inputType=InputType.TYPE_NULL
                        newData.cont_type_code=cont_type_code.text.toString()
                        cont_type_code.setAdapter(null)
                        if(sws.text.toString()==""){
                            newData.sws=null
                        }
                        else{
                            newData.sws=sws.text.toString()
                        }
                        sws.inputType=InputType.TYPE_NULL
                        if(swe.text.toString()==""){
                            newData.swe=null
                        }
                        else{
                            newData.swe=swe.text.toString()
                        }
                        swe.inputType=InputType.TYPE_NULL
                        newData.shipping_order_No=shipping_order_No.text.toString()
                        shipping_order_No.inputType=InputType.TYPE_NULL
                        if(last_swe.text.toString()==""){
                            newData.last_swe=null
                        }
                        else{
                            newData.last_swe=last_swe.text.toString()
                        }
                        last_swe.inputType=InputType.TYPE_NULL
                        if(sailing_date.text.toString()==""){
                            newData.sailing_date=null
                        }
                        else{
                            newData.sailing_date=sailing_date.text.toString()
                        }
                        sailing_date.inputType=InputType.TYPE_NULL
                        newData.port_id=port_id.text.toString()
                        port_id.setAdapter(null)
                        newData.car_id=car_id.text.toString()
                        car_id.inputType=InputType.TYPE_NULL
                        if(date_provided.text.toString()==""){
                            newData.date_provided=null
                        }
                        else{
                            newData.date_provided=date_provided.text.toString()
                        }
                        date_provided.inputType=InputType.TYPE_NULL
                        if(est_start_stacking_date.text.toString()==""){
                            newData.est_start_stacking_date=null
                        }
                        else{
                            newData.est_start_stacking_date=est_start_stacking_date.text.toString()
                        }
                        est_start_stacking_date.inputType=InputType.TYPE_NULL
                        if(est_stop_stacking_date.text.toString()==""){
                            newData.est_stop_stacking_date=null
                        }
                        else{
                            newData.est_stop_stacking_date=est_stop_stacking_date.text.toString()
                        }
                        est_stop_stacking_date.inputType=InputType.TYPE_NULL
                        newData.est_work_hours=est_work_hours.text.toString().toDouble()
                        est_work_hours.inputType=InputType.TYPE_NULL
                        newData.unit_of_timer=unit_of_timer.text.toString()
                        unit_of_timer.inputType=InputType.TYPE_NULL
                        newData.worker=worker.text.toString().toInt()
                        worker.inputType=InputType.TYPE_NULL
                        if(start_stacking_date.text.toString()==""){
                            newData.start_stacking_date=null
                        }
                        else{
                            newData.start_stacking_date=start_stacking_date.text.toString()
                        }
                        start_stacking_date.inputType=InputType.TYPE_NULL
                        if(stop_stacking_date.text.toString()==""){
                            newData.stop_stacking_date=null
                        }
                        else{
                            newData.stop_stacking_date=stop_stacking_date.text.toString()
                        }
                        stop_stacking_date.inputType=InputType.TYPE_NULL
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
                        newData.is_finished=is_finished.text.toString().toBoolean()
                        is_finished.isClickable=false
                        is_finished.setAdapter(null)
                        if(finished_date.text.toString()==""){
                            newData.finished_date=null
                        }
                        else{
                            newData.finished_date=finished_date.text.toString()
                        }
                        finished_date.inputType=InputType.TYPE_NULL

                        newData.remark=remark.text.toString()
                        remark.isClickable=false
                        remark.isFocusable=false
                        remark.isFocusableInTouchMode=false
                        remark.isTextInputLayoutFocusedRectEnabled=false
                        //Log.d("GSON", "msg: ${oldData}\n")
                        //Log.d("GSON", "msg: ${newData.remark}\n")
                        edit_Stacking("StackingControlListHeader",oldData,newData)//更改資料庫資料
                        when(cookie_data.status){
                            0-> {//成功
                                itemData.data[adapterPosition] = newData//更改渲染資料
                                editor.setText(cookie_data.username)
                                Toast.makeText(itemView.context, cookie_data.msg, Toast.LENGTH_SHORT).show()
                            }
                            1->{//失敗
                                Toast.makeText(itemView.context, cookie_data.msg, Toast.LENGTH_SHORT).show()
                                code.setText(oldData.code)
                                contNo.setText(oldData.contNo)
                                work_year.setText(oldData.work_year)
                                customer_poNo.setText(oldData.customer_poNo)
                                cont_type_code.setText(oldData.cont_type_code)
                                sws.setText(oldData.sws)
                                swe.setText(oldData.swe)
                                shipping_order_No.setText(oldData.shipping_order_No)
                                last_swe.setText(oldData.last_swe)
                                sailing_date.setText(oldData.sailing_date)
                                port_id.setText(oldData.port_id)
                                car_id.setText(oldData.car_id)
                                date_provided.setText(oldData.date_provided)
                                est_start_stacking_date.setText(oldData.est_start_stacking_date)
                                est_stop_stacking_date.setText(oldData.est_stop_stacking_date)
                                est_work_hours.setText(oldData.est_work_hours.toString())
                                unit_of_timer.setText(oldData.unit_of_timer)
                                worker.setText(oldData.worker.toString())
                                start_stacking_date.setText(oldData.start_stacking_date)
                                stop_stacking_date.setText(oldData.stop_stacking_date)
                                is_urgent.setText(oldData.is_urgent.toString())
                                urgent_deadline.setText(oldData.urgent_deadline)
                                is_finished.setText(oldData.is_finished.toString())
                                finished_date.setText(oldData.finished_date)


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
                    delete_Stacking("StackingControlListHeader",data[adapterPosition])
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
                    lock_Stacking("StackingControlListHeader",data[adapterPosition])
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
                    over_Stacking("StackingControlListHeader",data[adapterPosition])
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
        private fun edit_Stacking(operation:String,oldData:StackingControlListHeader,newData:StackingControlListHeader) {
            val old =JSONObject()
            old.put("code",oldData.code)
            old.put("contNo",oldData.contNo)
            old.put("work_year",oldData.work_year)
            old.put("customer_poNo",oldData.customer_poNo)
            old.put("cont_type_code",oldData.cont_type_code)
            old.put("sws",oldData.sws)
            old.put("swe",oldData.swe)
            old.put("shipping_order_No",oldData.shipping_order_No)
            old.put("last_swe",oldData.last_swe)
            old.put("sailing_date",oldData.sailing_date)
            old.put("port_id",oldData.port_id)
            old.put("car_id",oldData.car_id)
            old.put("date_provided",oldData.date_provided)
            old.put("est_start_stacking_date",oldData.est_start_stacking_date)
            old.put("est_stop_stacking_date",oldData.est_stop_stacking_date)
            old.put("est_work_hours",oldData.est_work_hours)
            old.put("unit_of_timer",oldData.unit_of_timer)
            old.put("worker",oldData.worker)
            old.put("start_stacking_date",oldData.start_stacking_date)
            old.put("stop_stacking_date",oldData.stop_stacking_date)
            old.put("is_urgent",oldData.is_urgent)
            old.put("urgent_deadline",oldData.urgent_deadline)
            old.put("is_finished",oldData.is_finished)
            old.put("finished_date",oldData.finished_date)

            old.put("remark",oldData.remark)
            val new =JSONObject()
            new.put("code",newData.code)
            new.put("contNo",newData.contNo)
            new.put("work_year",newData.work_year)
            new.put("customer_poNo",newData.customer_poNo)
            new.put("cont_type_code",newData.cont_type_code)
            new.put("sws",newData.sws)
            new.put("swe",newData.swe)
            new.put("shipping_order_No",newData.shipping_order_No)
            new.put("last_swe",newData.last_swe)
            new.put("sailing_date",newData.sailing_date)
            new.put("port_id",newData.port_id)
            new.put("car_id",newData.car_id)
            new.put("date_provided",newData.date_provided)
            new.put("est_start_stacking_date",newData.est_start_stacking_date)
            new.put("est_stop_stacking_date",newData.est_stop_stacking_date)
            new.put("est_work_hours",newData.est_work_hours)
            new.put("unit_of_timer",newData.unit_of_timer)
            new.put("worker",newData.worker)
            new.put("start_stacking_date",newData.start_stacking_date)
            new.put("stop_stacking_date",newData.stop_stacking_date)
            new.put("is_urgent",newData.is_urgent)
            new.put("urgent_deadline",newData.urgent_deadline)
            new.put("is_finished",newData.is_finished)
            new.put("finished_date",newData.finished_date)

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
                .url("http://140.125.46.125:8000/stacking_control_management")
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
        private fun delete_Stacking(operation:String,deleteData:StackingControlListHeader) {
            val delete = JSONObject()
            delete.put("code",deleteData.code)
            delete.put("contNo",deleteData.contNo)
            delete.put("work_year",deleteData.work_year)
            delete.put("customer_poNo",deleteData.customer_poNo)
            delete.put("cont_type_code",deleteData.cont_type_code)
            delete.put("sws",deleteData.sws)
            delete.put("swe",deleteData.swe)
            delete.put("shipping_order_No",deleteData.shipping_order_No)
            delete.put("last_swe",deleteData.last_swe)
            delete.put("sailing_date",deleteData.sailing_date)
            delete.put("port_id",deleteData.port_id)
            delete.put("car_id",deleteData.car_id)
            delete.put("date_provided",deleteData.date_provided)
            delete.put("est_start_stacking_date",deleteData.est_start_stacking_date)
            delete.put("est_stop_stacking_date",deleteData.est_stop_stacking_date)
            delete.put("est_work_hours",deleteData.est_work_hours)
            delete.put("unit_of_timer",deleteData.unit_of_timer)
            delete.put("worker",deleteData.worker)
            delete.put("start_stacking_date",deleteData.start_stacking_date)
            delete.put("stop_stacking_date",deleteData.stop_stacking_date)
            delete.put("is_urgent",deleteData.is_urgent)
            delete.put("urgent_deadline",deleteData.urgent_deadline)
            delete.put("is_finished",deleteData.is_finished)
            delete.put("finished_date",deleteData.finished_date)

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
                .url("http://140.125.46.125:8000/stacking_control_management")
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
        private fun lock_Stacking(operation:String,lockData:StackingControlListHeader) {
            val lock = JSONObject()
            lock.put("code",lockData.code)
            lock.put("contNo",lockData.contNo)
            lock.put("work_year",lockData.work_year)
            lock.put("customer_poNo",lockData.customer_poNo)
            lock.put("cont_type_code",lockData.cont_type_code)
            lock.put("sws",lockData.sws)
            lock.put("swe",lockData.swe)
            lock.put("shipping_order_No",lockData.shipping_order_No)
            lock.put("last_swe",lockData.last_swe)
            lock.put("sailing_date",lockData.sailing_date)
            lock.put("port_id",lockData.port_id)
            lock.put("car_id",lockData.car_id)
            lock.put("date_provided",lockData.date_provided)
            lock.put("est_start_stacking_date",lockData.est_start_stacking_date)
            lock.put("est_stop_stacking_date",lockData.est_stop_stacking_date)
            lock.put("est_work_hours",lockData.est_work_hours)
            lock.put("unit_of_timer",lockData.unit_of_timer)
            lock.put("worker",lockData.worker)
            lock.put("start_stacking_date",lockData.start_stacking_date)
            lock.put("stop_stacking_date",lockData.stop_stacking_date)
            lock.put("is_urgent",lockData.is_urgent)
            lock.put("urgent_deadline",lockData.urgent_deadline)
            lock.put("is_finished",lockData.is_finished)
            lock.put("finished_date",lockData.finished_date)

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
                .url("http://140.125.46.125:8000/stacking_control_management")
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
        private fun over_Stacking(operation:String,overData:StackingControlListHeader) {
            val over = JSONObject()
            over.put("code",overData.code)
            over.put("contNo",overData.contNo)
            over.put("work_year",overData.work_year)
            over.put("customer_poNo",overData.customer_poNo)
            over.put("cont_type_code",overData.cont_type_code)
            over.put("sws",overData.sws)
            over.put("swe",overData.swe)
            over.put("shipping_order_No",overData.shipping_order_No)
            over.put("last_swe",overData.last_swe)
            over.put("sailing_date",overData.sailing_date)
            over.put("port_id",overData.port_id)
            over.put("car_id",overData.car_id)
            over.put("date_provided",overData.date_provided)
            over.put("est_start_stacking_date",overData.est_start_stacking_date)
            over.put("est_stop_stacking_date",overData.est_stop_stacking_date)
            over.put("est_work_hours",overData.est_work_hours)
            over.put("unit_of_timer",overData.unit_of_timer)
            over.put("worker",overData.worker)
            over.put("start_stacking_date",overData.start_stacking_date)
            over.put("stop_stacking_date",overData.stop_stacking_date)
            over.put("is_urgent",overData.is_urgent)
            over.put("urgent_deadline",overData.urgent_deadline)
            over.put("is_finished",overData.is_finished)
            over.put("finished_date",overData.finished_date)

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
                .url("http://140.125.46.125:8000/stacking_control_management")
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
    fun addItem(addData:StackingControlListHeader){
        data.add(itemData.count,addData)
        notifyItemInserted(itemData.count)
        itemData.count+=1//cookie_data.itemCount+=1
    }

}


