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


class RecyclerItemProductionEquipmentBasicInfoAdapter() :
    RecyclerView.Adapter<RecyclerItemProductionEquipmentBasicInfoAdapter.ViewHolder>() {
    private val itemData: ShowProductionEquipmentBasicInfo =Gson().fromJson(cookie_data.response_data, ShowProductionEquipmentBasicInfo::class.java)
    private var data: ArrayList<ProductionEquipmentBasicInfo> =itemData.data

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemProductionEquipmentBasicInfoAdapter.ViewHolder {
        val v= LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_production_equipment_basic_info,parent,false)
        return ViewHolder(v)

    }

    override fun onBindViewHolder(holder: RecyclerItemProductionEquipmentBasicInfoAdapter.ViewHolder, position: Int) {

        //Log.d("GSON", "msg: ${itemData.data[position]}\n")
        holder.production_equipment_number.setText(data[position].production_equipment_number)
        holder.production_equipment_number.inputType=InputType.TYPE_NULL
        holder.full_name.setText(data[position].full_name)
        holder.full_name.inputType=InputType.TYPE_NULL
        holder.abbreviation.setText(data[position].abbreviation)
        holder.abbreviation.inputType=InputType.TYPE_NULL
        holder.pline_id.setText(data[position].pline_id)
        holder.pline_id.inputType=InputType.TYPE_NULL
        holder.asset_number.setText(data[position].asset_number)
        holder.asset_number.inputType=InputType.TYPE_NULL
        holder.equipment_type.setText(data[position].equipment_type)
        holder.equipment_type.inputType=InputType.TYPE_NULL
        holder.is_production_equipment.setText(data[position].is_production_equipment.toString())
        holder.is_production_equipment.isClickable=false
        holder.is_production_equipment.inputType=InputType.TYPE_NULL
        holder.is_mould.setText(data[position].is_mould.toString())
        holder.is_mould.isClickable=false
        holder.is_mould.inputType=InputType.TYPE_NULL
        holder.is_fixture.setText(data[position].is_fixture.toString())
        holder.is_fixture.isClickable=false
        holder.is_fixture.inputType=InputType.TYPE_NULL
        holder.is_checking_fixture.setText(data[position].is_checking_fixture.toString())
        holder.is_checking_fixture.isClickable=false
        holder.is_checking_fixture.inputType=InputType.TYPE_NULL




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
        holder.deletebtn.isVisible=true
        holder.edit_btn.isVisible=true
        holder.lockbtn.isVisible=true

    }

    override fun getItemCount(): Int {
        return itemData.count
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var production_equipment_number=itemView.findViewById<TextInputEditText>(R.id.edit_production_equipment_number)
        var full_name=itemView.findViewById<TextInputEditText>(R.id.edit_full_name)
        var abbreviation=itemView.findViewById<TextInputEditText>(R.id.edit_abbreviation)
        var pline_id=itemView.findViewById<TextInputEditText>(R.id.edit_pline_id)
        var asset_number=itemView.findViewById<TextInputEditText>(R.id.edit_asset_number)
        var equipment_type=itemView.findViewById<TextInputEditText>(R.id.edit_equipment_type)
        var is_production_equipment=itemView.findViewById<AutoCompleteTextView>(R.id.edit_is_production_equipment)
        var is_mould=itemView.findViewById<AutoCompleteTextView>(R.id.edit_is_mould)
        var is_fixture=itemView.findViewById<AutoCompleteTextView>(R.id.edit_is_fixture)
        var is_checking_fixture=itemView.findViewById<AutoCompleteTextView>(R.id.edit_is_checking_fixture)

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
        lateinit var oldData:ProductionEquipmentBasicInfo
        lateinit var newData:ProductionEquipmentBasicInfo
        init {
            itemView.setOnClickListener{
                val position:Int=adapterPosition
                Log.d("GSON", "msg: ${position}\n")

            }

            val combobox=itemView.resources.getStringArray(R.array.combobox_yes_no)
            val arrayAdapter= ArrayAdapter(itemView.context,R.layout.combobox_item,combobox)



            //編輯按鈕
            edit_btn.setOnClickListener {
                when(edit_btn.text){
                    "編輯"->{
                        oldData=itemData.data[adapterPosition]
                        //Log.d("GSON", "msg: ${oldData.id}\n")
                        production_equipment_number.inputType=InputType.TYPE_CLASS_TEXT
                        full_name.inputType=InputType.TYPE_CLASS_TEXT
                        abbreviation.inputType=InputType.TYPE_CLASS_TEXT
                        pline_id.inputType=InputType.TYPE_CLASS_TEXT
                        asset_number.inputType=InputType.TYPE_CLASS_TEXT
                        equipment_type.inputType=InputType.TYPE_CLASS_TEXT
                        is_production_equipment.setAdapter(arrayAdapter)
                        is_production_equipment.isClickable=true
                        is_mould.setAdapter(arrayAdapter)
                        is_mould.isClickable=true
                        is_fixture.setAdapter(arrayAdapter)
                        is_fixture.isClickable=true
                        is_checking_fixture.setAdapter(arrayAdapter)
                        is_checking_fixture.isClickable=true

                        remark.isClickable=true
                        remark.isFocusable=true
                        remark.isFocusableInTouchMode=true
                        remark.isTextInputLayoutFocusedRectEnabled=true
                        production_equipment_number.requestFocus()
                        edit_btn.text = "完成"
                    }
                    "完成"->{
                        newData= ProductionEquipmentBasicInfo()
                        newData.production_equipment_number=production_equipment_number.text.toString()
                        production_equipment_number.inputType=InputType.TYPE_NULL
                        newData.full_name=full_name.text.toString()
                        full_name.inputType=InputType.TYPE_NULL
                        newData.abbreviation=abbreviation.text.toString()
                        abbreviation.inputType=InputType.TYPE_NULL
                        newData.pline_id=pline_id.text.toString()
                        pline_id.inputType=InputType.TYPE_NULL
                        newData.asset_number=asset_number.text.toString()
                        asset_number.inputType=InputType.TYPE_NULL
                        newData.equipment_type=equipment_type.text.toString()
                        equipment_type.inputType=InputType.TYPE_NULL
                        newData.is_production_equipment=is_production_equipment.text.toString().toBoolean()
                        is_production_equipment.isClickable=false
                        is_production_equipment.setAdapter(null)
                        newData.is_mould=is_mould.text.toString().toBoolean()
                        is_mould.isClickable=false
                        is_mould.setAdapter(null)
                        newData.is_fixture=is_fixture.text.toString().toBoolean()
                        is_fixture.isClickable=false
                        is_fixture.setAdapter(null)
                        newData.is_checking_fixture=is_checking_fixture.text.toString().toBoolean()
                        is_checking_fixture.isClickable=false
                        is_checking_fixture.setAdapter(null)


                        newData.remark=remark.text.toString()
                        remark.isClickable=false
                        remark.isFocusable=false
                        remark.isFocusableInTouchMode=false
                        remark.isTextInputLayoutFocusedRectEnabled=false
                        //Log.d("GSON", "msg: ${oldData}\n")
                        //Log.d("GSON", "msg: ${newData.remark}\n")
                        editBasic("ProductionEquipmentBasicInfo",oldData,newData)//更改資料庫資料
                        when(cookie_data.status){
                            0-> {//成功
                                itemData.data[adapterPosition] = newData//更改渲染資料
                                editor.setText(cookie_data.username)
                                Toast.makeText(itemView.context, cookie_data.msg, Toast.LENGTH_SHORT).show()
                            }
                            1->{//失敗
                                Toast.makeText(itemView.context, cookie_data.msg, Toast.LENGTH_SHORT).show()
                                production_equipment_number.setText(oldData.production_equipment_number)
                                full_name.setText(oldData.full_name)
                                abbreviation.setText(oldData.abbreviation)
                                pline_id.setText(oldData.pline_id)
                                asset_number.setText(oldData.asset_number)
                                equipment_type.setText(oldData.equipment_type)
                                is_production_equipment.setText(oldData.is_production_equipment.toString())
                                is_mould.setText(oldData.is_mould.toString())
                                is_fixture.setText(oldData.is_fixture.toString())
                                is_checking_fixture.setText(oldData.is_checking_fixture.toString())


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
                    deleteBasic("ProductionEquipmentBasicInfo",data[adapterPosition])
                    when(cookie_data.status){
                        0-> {//成功
                            data.removeAt(adapterPosition)
                            notifyItemRemoved(adapterPosition)
                            itemData.count-=1
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
                    lockBasic("ProductionEquipmentBasicInfo",data[adapterPosition])
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
        }
        private fun editBasic(operation:String,oldData:ProductionEquipmentBasicInfo,newData:ProductionEquipmentBasicInfo) {
            val old =JSONObject()
            old.put("production_equipment_number",oldData.production_equipment_number)
            old.put("full_name",oldData.full_name)
            old.put("abbreviation",oldData.abbreviation)
            old.put("pline_id",oldData.pline_id)
            old.put("asset_number",oldData.asset_number)
            old.put("equipment_type",oldData.equipment_type)
            old.put("is_production_equipment",oldData.is_production_equipment)
            old.put("is_mould",oldData.is_mould)
            old.put("is_fixture",oldData.is_fixture)
            old.put("is_checking_fixture",oldData.is_checking_fixture)

            old.put("remark",oldData.remark)
            val new =JSONObject()
            new.put("production_equipment_number",newData.production_equipment_number)
            new.put("full_name",newData.full_name)
            new.put("abbreviation",newData.abbreviation)
            new.put("pline_id",newData.pline_id)
            new.put("asset_number",newData.asset_number)
            new.put("equipment_type",newData.equipment_type)
            new.put("is_production_equipment",newData.is_production_equipment)
            new.put("is_mould",newData.is_mould)
            new.put("is_fixture",newData.is_fixture)
            new.put("is_checking_fixture",newData.is_checking_fixture)

            new.put("editor",cookie_data.username)
            new.put("remark",newData.remark)
            val data = JSONArray()
            data.put(old)
            data.put((new))
            val body = FormBody.Builder()
                .add("card_number",cookie_data.card_number)
                .add("data",data.toString())
                .add("username", cookie_data.username)
                .add("operation", operation)
                .add("action","2")
                .add("csrfmiddlewaretoken", cookie_data.tokenValue)
                .add("login_flag", cookie_data.loginflag)
                .build()
            val request = Request.Builder()
                .url(cookie_data.URL+"/basic_management")
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
        private fun deleteBasic(operation:String,deleteData:ProductionEquipmentBasicInfo) {
            val delete = JSONObject()
            delete.put("production_equipment_number",deleteData.production_equipment_number)
            delete.put("full_name",deleteData.full_name)
            delete.put("abbreviation",deleteData.abbreviation)
            delete.put("pline_id",deleteData.pline_id)
            delete.put("asset_number",deleteData.asset_number)
            delete.put("equipment_type",deleteData.equipment_type)
            delete.put("is_production_equipment",deleteData.is_production_equipment)
            delete.put("is_mould",deleteData.is_mould)
            delete.put("is_fixture",deleteData.is_fixture)
            delete.put("is_checking_fixture",deleteData.is_checking_fixture)

            delete.put("remark",deleteData.remark)
            //Log.d("GSON", "msg:${delete}")
            val body = FormBody.Builder()
                .add("card_number",cookie_data.card_number)
                .add("username", cookie_data.username)
                .add("operation", operation)
                .add("action","3")
                .add("data",delete.toString())
                .add("csrfmiddlewaretoken", cookie_data.tokenValue)
                .add("login_flag", cookie_data.loginflag)
                .build()
            val request = Request.Builder()
                .url(cookie_data.URL+"/basic_management")
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
        private fun lockBasic(operation:String,lockData:ProductionEquipmentBasicInfo) {
            val lock = JSONObject()
            lock.put("production_equipment_number",lockData.production_equipment_number)
            lock.put("full_name",lockData.full_name)
            lock.put("abbreviation",lockData.abbreviation)
            lock.put("pline_id",lockData.pline_id)
            lock.put("asset_number",lockData.asset_number)
            lock.put("equipment_type",lockData.equipment_type)
            lock.put("is_production_equipment",lockData.is_production_equipment)
            lock.put("is_mould",lockData.is_mould)
            lock.put("is_fixture",lockData.is_fixture)
            lock.put("is_checking_fixture",lockData.is_checking_fixture)

            lock.put("remark",lockData.remark)
            //Log.d("GSON", "msg:${delete}")
            val body = FormBody.Builder()
                .add("card_number",cookie_data.card_number)
                .add("username", cookie_data.username)
                .add("operation", operation)
                .add("action","4")
                .add("data",lock.toString())
                .add("csrfmiddlewaretoken", cookie_data.tokenValue)
                .add("login_flag", cookie_data.loginflag)
                .build()
            val request = Request.Builder()
                .url(cookie_data.URL+"/basic_management")
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
    fun addItem(addData:ProductionEquipmentBasicInfo){
        data.add(itemData.count,addData)
        notifyItemInserted(itemData.count)
        itemData.count+=1
    }

}


