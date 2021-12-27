package com.example.erp20.RecyclerAdapter
import android.content.Context
import android.content.DialogInterface
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
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.erp20.Model.*
import com.example.erp20.PopUpWindow.QrCodeFragment
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

class RecyclerItemEquipmentMaintenanceRecordAdapter() :
    RecyclerView.Adapter<RecyclerItemEquipmentMaintenanceRecordAdapter.ViewHolder>() {
    private var itemData: ShowEquipmentMaintenanceRecord =Gson().fromJson(cookie_data.response_data, ShowEquipmentMaintenanceRecord::class.java)
    private var data: ArrayList<EquipmentMaintenanceRecord> =itemData.data
    var relativeCombobox01=cookie_data.device_id_ComboboxData
    var relativeCombobox02=cookie_data.vender_id_ComboboxData
    var relativeCombobox03=cookie_data.maintain_type_ComboboxData

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemEquipmentMaintenanceRecordAdapter.ViewHolder {
        val v= LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_equipment_maintenance_record,parent,false)
        return ViewHolder(v)

    }

    override fun onBindViewHolder(holder: RecyclerItemEquipmentMaintenanceRecordAdapter.ViewHolder, position: Int) {
        //Log.d("GSON", "msg: ${itemData.data[position]}\n")
        holder.maintenance_id.setText(data[position].maintenance_id)
        holder.maintenance_id.inputType=InputType.TYPE_NULL
        holder.equipment_id.setText(data[position].equipment_id)
        holder.equipment_id.inputType=InputType.TYPE_NULL
        holder.date.setText(data[position].date)
        holder.date.inputType=InputType.TYPE_NULL
        holder.start_time.setText(data[position].start_time)
        holder.start_time.inputType=InputType.TYPE_NULL
        holder.end_time.setText(data[position].end_time)
        holder.end_time.inputType=InputType.TYPE_NULL
        holder.vender_id.setText(data[position].vender_id)
        holder.vender_id.inputType=InputType.TYPE_NULL
        holder.maintenance_type.setText(data[position].maintenance_type)
        holder.maintenance_type.inputType=InputType.TYPE_NULL
        holder.is_ok.setText(data[position].is_ok.toString())
        holder.is_ok.isClickable=false
        holder.is_ok.inputType=InputType.TYPE_NULL
        holder.is_not_available.setText(data[position].is_not_available.toString())
        holder.is_not_available.isClickable=false
        holder.is_not_available.inputType=InputType.TYPE_NULL



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
        var maintenance_id=itemView.findViewById<TextInputEditText>(R.id.edit_maintenance_id)
        var equipment_id=itemView.findViewById<AutoCompleteTextView>(R.id.edit_equipment_id)
        var date=itemView.findViewById<TextInputEditText>(R.id.edit_date)
        var start_time=itemView.findViewById<TextInputEditText>(R.id.edit_start_time)
        var end_time=itemView.findViewById<TextInputEditText>(R.id.edit_end_time)
        var vender_id =itemView.findViewById<AutoCompleteTextView>(R.id.edit_vender_id)
        var maintenance_type=itemView.findViewById<AutoCompleteTextView>(R.id.edit_maintenance_type)
        var is_ok=itemView.findViewById<AutoCompleteTextView>(R.id.edit_is_ok)
        var is_not_available=itemView.findViewById<AutoCompleteTextView>(R.id.edit_is_not_available)

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
        lateinit var oldData:EquipmentMaintenanceRecord
        lateinit var newData:EquipmentMaintenanceRecord
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
                        cookie_data.itemposition=adapterPosition
                        oldData=itemData.data[adapterPosition]
                        //Log.d("GSON", "msg: ${oldData.id}\n")
                        //maintenance_id.inputType=InputType.TYPE_CLASS_TEXT
                        equipment_id.setAdapter(arrayAdapter01)
                        date.inputType=InputType.TYPE_DATETIME_VARIATION_DATE
                        start_time.inputType=InputType.TYPE_CLASS_DATETIME
                        end_time.inputType=InputType.TYPE_CLASS_DATETIME
                        vender_id.setAdapter(arrayAdapter02)
                        maintenance_type.setAdapter(arrayAdapter03)
                        is_ok.setAdapter(arrayAdapter)
                        is_ok.isClickable=true
                        is_not_available.setAdapter(arrayAdapter)
                        is_not_available.isClickable=true


                        remark.isClickable=true
                        remark.isFocusable=true
                        remark.isFocusableInTouchMode=true
                        remark.isTextInputLayoutFocusedRectEnabled=true
                        equipment_id.requestFocus()
                        edit_btn.text = "完成"
                        layout.setBackgroundColor(Color.parseColor("#6E5454"))
                        edit_btn.setBackgroundColor(Color.parseColor("#FF3700B3"))
                    }
                    "完成"->{
                        newData= EquipmentMaintenanceRecord()
                        newData.maintenance_id=maintenance_id.text.toString()
                        maintenance_id.inputType=InputType.TYPE_NULL
                        newData.equipment_id=equipment_id.text.toString()
                        equipment_id.setAdapter(null)
                        if(date.text.toString()==""){
                            newData.date=null
                        }
                        else{
                            newData.date=date.text.toString()
                        }
                        date.inputType=InputType.TYPE_NULL
                        if(start_time.text.toString()==""){
                            newData.start_time=null
                        }
                        else{
                            newData.start_time=start_time.text.toString()
                        }
                        start_time.inputType=InputType.TYPE_NULL
                        if(end_time.text.toString()==""){
                            newData.end_time=null
                        }
                        else{
                            newData.end_time=end_time.text.toString()
                        }
                        end_time.inputType=InputType.TYPE_NULL
                        newData.vender_id=vender_id.text.toString()
                        vender_id.setAdapter(null)
                        newData.maintenance_type=maintenance_type.text.toString()
                        maintenance_type.setAdapter(null)
                        newData.is_ok=is_ok.text.toString().toBoolean()
                        is_ok.isClickable=false
                        is_ok.setAdapter(null)
                        newData.is_not_available=is_not_available.text.toString().toBoolean()
                        is_not_available.isClickable=false
                        is_not_available.setAdapter(null)


                        newData.remark=remark.text.toString()
                        remark.isClickable=false
                        remark.isFocusable=false
                        remark.isFocusableInTouchMode=false
                        remark.isTextInputLayoutFocusedRectEnabled=false
                        //Log.d("GSON", "msg: ${oldData}\n")
                        //Log.d("GSON", "msg: ${newData.remark}\n")
                        edit_ProductControlOrder("EquipmentMaintenanceRecord",oldData,newData)//更改資料庫資料
                        when(cookie_data.status){
                            0-> {//成功
                                itemData.data[adapterPosition] = newData//更改渲染資料
                                editor.setText(cookie_data.username)
                                Toast.makeText(itemView.context, cookie_data.msg, Toast.LENGTH_SHORT).show()
                            }
                            1->{//失敗
                                Toast.makeText(itemView.context, cookie_data.msg, Toast.LENGTH_SHORT).show()
                                maintenance_id.setText(oldData.maintenance_id)
                                equipment_id.setText(oldData.equipment_id)
                                date.setText(oldData.date)
                                start_time.setText(oldData.start_time)
                                end_time.setText(oldData.end_time)
                                vender_id.setText(oldData.vender_id)
                                maintenance_type.setText(oldData.maintenance_type)
                                is_ok.setText(oldData.is_ok.toString())
                                is_not_available.setText(oldData.is_not_available.toString())

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
                    delete_ProductControlOrder("EquipmentMaintenanceRecord",data[adapterPosition])
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
                    lock_ProductControlOrder("EquipmentMaintenanceRecord",data[adapterPosition])
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
                    over_ProductControlOrder("EquipmentMaintenanceRecord",data[adapterPosition])
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
        private fun edit_ProductControlOrder(operation:String,oldData:EquipmentMaintenanceRecord,newData:EquipmentMaintenanceRecord) {
            val old =JSONObject()
            old.put("maintenance_id",oldData.maintenance_id)
            old.put("equipment_id",oldData.equipment_id)
            old.put("date",oldData.date)
            old.put("start_time",oldData.start_time)
            old.put("end_time",oldData.end_time)
            old.put("vender_id",oldData.vender_id)
            old.put("maintenance_type",oldData.maintenance_type)
            old.put("is_ok",oldData.is_ok)
            old.put("is_not_available",oldData.is_not_available)



            old.put("remark",oldData.remark)
            val new =JSONObject()
            new.put("maintenance_id",newData.maintenance_id)
            new.put("equipment_id",newData.equipment_id)
            new.put("date",newData.date)
            new.put("start_time",newData.start_time)
            new.put("end_time",newData.end_time)
            new.put("vender_id",newData.vender_id)
            new.put("maintenance_type",newData.maintenance_type)
            new.put("is_ok",newData.is_ok)
            new.put("is_not_available",newData.is_not_available)

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
                .url("http://140.125.46.125:8000/vender_log_management")
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
        private fun delete_ProductControlOrder(operation:String,deleteData:EquipmentMaintenanceRecord) {
            val delete = JSONObject()
            delete.put("maintenance_id",deleteData.maintenance_id)
            delete.put("equipment_id",deleteData.equipment_id)
            delete.put("date",deleteData.date)
            delete.put("start_time",deleteData.start_time)
            delete.put("end_time",deleteData.end_time)
            delete.put("vender_id",deleteData.vender_id)
            delete.put("maintenance_type",deleteData.maintenance_type)
            delete.put("is_ok",deleteData.is_ok)
            delete.put("is_not_available",deleteData.is_not_available)

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
                .url("http://140.125.46.125:8000/vender_log_management")
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
        private fun lock_ProductControlOrder(operation:String,lockData:EquipmentMaintenanceRecord) {
            val lock = JSONObject()
            lock.put("maintenance_id",lockData.maintenance_id)
            lock.put("equipment_id",lockData.equipment_id)
            lock.put("date",lockData.date)
            lock.put("start_time",lockData.start_time)
            lock.put("end_time",lockData.end_time)
            lock.put("vender_id",lockData.vender_id)
            lock.put("maintenance_type",lockData.maintenance_type)
            lock.put("is_ok",lockData.is_ok)
            lock.put("is_not_available",lockData.is_not_available)

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
                .url("http://140.125.46.125:8000/vender_log_management")
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
        private fun over_ProductControlOrder(operation:String,overData:EquipmentMaintenanceRecord) {
            val over = JSONObject()
            over.put("maintenance_id",overData.maintenance_id)
            over.put("equipment_id",overData.equipment_id)
            over.put("date",overData.date)
            over.put("start_time",overData.start_time)
            over.put("end_time",overData.end_time)
            over.put("vender_id",overData.vender_id)
            over.put("maintenance_type",overData.maintenance_type)
            over.put("is_ok",overData.is_ok)
            over.put("is_not_available",overData.is_not_available)

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
                .url("http://140.125.46.125:8000/vender_log_management")
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
    fun addItem(addData:EquipmentMaintenanceRecord){
        data.add(itemData.count,addData)
        notifyItemInserted(itemData.count)
        itemData.count+=1//cookie_data.itemCount+=1
    }


}


