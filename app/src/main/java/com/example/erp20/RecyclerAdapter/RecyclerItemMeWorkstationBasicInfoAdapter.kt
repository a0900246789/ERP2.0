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


class RecyclerItemMeWorkstationBasicInfoAdapter() :
    RecyclerView.Adapter<RecyclerItemMeWorkstationBasicInfoAdapter.ViewHolder>() {
    private val itemData: ShowMeWorkstationBasicInfo =Gson().fromJson(cookie_data.response_data, ShowMeWorkstationBasicInfo::class.java)
    private var data: ArrayList<MeWorkstationBasicInfo> =itemData.data

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemMeWorkstationBasicInfoAdapter.ViewHolder {
        val v= LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_me_workstation_basic_info,parent,false)
        return ViewHolder(v)

    }

    override fun onBindViewHolder(holder: RecyclerItemMeWorkstationBasicInfoAdapter.ViewHolder, position: Int) {

        //Log.d("GSON", "msg: ${itemData.data[position]}\n")
        holder.workstation_number.setText(data[position].workstation_number)
        holder.workstation_number.inputType=InputType.TYPE_NULL
        holder.product_id.setText(data[position].product_id)
        holder.product_id.inputType=InputType.TYPE_NULL
        holder.workstation_code.setText(data[position].workstation_code)
        holder.workstation_code.inputType=InputType.TYPE_NULL
        holder.is_body_condition_ok.setText(data[position].is_body_condition_ok.toString())
        holder.is_body_condition_ok.isClickable=false
        holder.is_body_condition_ok.inputType=InputType.TYPE_NULL
        holder.is_vision_condition_ok.setText(data[position].is_vision_condition_ok.toString())
        holder.is_vision_condition_ok.isClickable=false
        holder.is_vision_condition_ok.inputType=InputType.TYPE_NULL
        holder.is_technology_ok.setText(data[position].is_technology_ok.toString())
        holder.is_technology_ok.isClickable=false
        holder.is_technology_ok.inputType=InputType.TYPE_NULL
        holder.is_normal.setText(data[position].is_normal.toString())
        holder.is_normal.isClickable=false
        holder.is_normal.inputType=InputType.TYPE_NULL
        holder.job_description.setText(data[position].job_description)//工作描述
        holder.job_description.isClickable=false
        holder.job_description.isFocusable=false
        holder.job_description.isFocusableInTouchMode=false
        holder.job_description.isTextInputLayoutFocusedRectEnabled=false
        holder.standard_working_hours_optimal.setText(data[position].standard_working_hours_optimal.toString())
        holder.standard_working_hours_optimal.inputType=InputType.TYPE_NULL
        holder.unit_of_timer_optimal.setText(data[position].unit_of_timer_optimal)
        holder.unit_of_timer_optimal.inputType=InputType.TYPE_NULL
        holder.number_of_staff_optimal.setText(data[position].number_of_staff_optimal)
        holder.number_of_staff_optimal.inputType=InputType.TYPE_NULL
        holder.theoretical_output.setText(data[position].theoretical_output.toString())
        holder.theoretical_output.inputType=InputType.TYPE_NULL
        holder.standard_working_hours_less.setText(data[position].standard_working_hours_less.toString())
        holder.standard_working_hours_less.inputType=InputType.TYPE_NULL
        holder.unit_of_timer_less.setText(data[position].unit_of_timer_less)
        holder.unit_of_timer_less.inputType=InputType.TYPE_NULL
        holder.number_of_staff_less.setText(data[position].number_of_staff_less)
        holder.number_of_staff_less.inputType=InputType.TYPE_NULL
        holder.theoretical_output_less.setText(data[position].theoretical_output_less.toString())
        holder.theoretical_output_less.inputType=InputType.TYPE_NULL


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
        var workstation_number=itemView.findViewById<TextInputEditText>(R.id.edit_workstation_number)
        var product_id=itemView.findViewById<TextInputEditText>(R.id.edit_product_id)
        var workstation_code=itemView.findViewById<TextInputEditText>(R.id.edit_workstation_code)
        var is_body_condition_ok=itemView.findViewById<AutoCompleteTextView>(R.id.edit_is_body_condition_ok)
        var is_vision_condition_ok=itemView.findViewById<AutoCompleteTextView>(R.id.edit_is_vision_condition_ok)
        var is_technology_ok=itemView.findViewById<AutoCompleteTextView>(R.id.edit_is_technology_ok)
        var is_normal=itemView.findViewById<AutoCompleteTextView>(R.id.edit_is_normal)
        var job_description=itemView.findViewById<TextInputEditText>(R.id.edit_job_description)
        var standard_working_hours_optimal=itemView.findViewById<TextInputEditText>(R.id.edit_standard_working_hours_optimal)
        var unit_of_timer_optimal=itemView.findViewById<TextInputEditText>(R.id.edit_unit_of_timer_optimal)
        var number_of_staff_optimal=itemView.findViewById<TextInputEditText>(R.id.edit_number_of_staff_optimal)
        var theoretical_output=itemView.findViewById<TextInputEditText>(R.id.edit_theoretical_output)
        var standard_working_hours_less=itemView.findViewById<TextInputEditText>(R.id.edit_standard_working_hours_less)
        var unit_of_timer_less=itemView.findViewById<TextInputEditText>(R.id.edit_unit_of_timer_less)
        var number_of_staff_less=itemView.findViewById<TextInputEditText>(R.id.edit_number_of_staff_less)
        var theoretical_output_less=itemView.findViewById<TextInputEditText>(R.id.edit_theoretical_output_less)

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
        lateinit var oldData:MeWorkstationBasicInfo
        lateinit var newData:MeWorkstationBasicInfo
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
                        workstation_number.inputType=InputType.TYPE_CLASS_TEXT
                        product_id.inputType=InputType.TYPE_CLASS_TEXT
                        workstation_code.inputType=InputType.TYPE_CLASS_TEXT
                        is_body_condition_ok.setAdapter(arrayAdapter)
                        is_body_condition_ok.isClickable=true
                        is_vision_condition_ok.setAdapter(arrayAdapter)
                        is_vision_condition_ok.isClickable=true
                        is_technology_ok.setAdapter(arrayAdapter)
                        is_technology_ok.isClickable=true
                        is_normal.setAdapter(arrayAdapter)
                        is_normal.isClickable=true
                        job_description.isClickable=true//工作描述
                        job_description.isFocusable=true
                        job_description.isFocusableInTouchMode=true
                        job_description.isTextInputLayoutFocusedRectEnabled=true
                        standard_working_hours_optimal.inputType=InputType.TYPE_CLASS_NUMBER
                        unit_of_timer_optimal.inputType=InputType.TYPE_CLASS_TEXT
                        number_of_staff_optimal.inputType=InputType.TYPE_CLASS_TEXT
                        theoretical_output.inputType=InputType.TYPE_CLASS_NUMBER
                        standard_working_hours_less.inputType=InputType.TYPE_CLASS_NUMBER
                        unit_of_timer_less.inputType=InputType.TYPE_CLASS_TEXT
                        number_of_staff_less.inputType=InputType.TYPE_CLASS_TEXT
                        theoretical_output_less.inputType=InputType.TYPE_CLASS_NUMBER

                        remark.isClickable=true
                        remark.isFocusable=true
                        remark.isFocusableInTouchMode=true
                        remark.isTextInputLayoutFocusedRectEnabled=true
                        workstation_number.requestFocus()
                        edit_btn.text = "完成"
                    }
                    "完成"->{
                        newData= MeWorkstationBasicInfo()
                        newData.workstation_number=workstation_number.text.toString()
                        workstation_number.inputType=InputType.TYPE_NULL
                        newData.product_id=product_id.text.toString()
                        product_id.inputType=InputType.TYPE_NULL
                        newData.workstation_code=workstation_code.text.toString()
                        workstation_code.inputType=InputType.TYPE_NULL
                        newData.is_body_condition_ok=is_body_condition_ok.text.toString().toBoolean()
                        is_body_condition_ok.isClickable=false
                        is_body_condition_ok.setAdapter(null)
                        newData.is_vision_condition_ok=is_vision_condition_ok.text.toString().toBoolean()
                        is_vision_condition_ok.isClickable=false
                        is_vision_condition_ok.setAdapter(null)
                        newData.is_technology_ok=is_technology_ok.text.toString().toBoolean()
                        is_technology_ok.isClickable=false
                        is_technology_ok.setAdapter(null)
                        newData.is_normal=is_normal.text.toString().toBoolean()
                        is_normal.isClickable=false
                        is_normal.setAdapter(null)
                        newData.job_description=job_description.text.toString()//工作描述
                        job_description.isClickable=false
                        job_description.isFocusable=false
                        job_description.isFocusableInTouchMode=false
                        job_description.isTextInputLayoutFocusedRectEnabled=false
                        newData.standard_working_hours_optimal=standard_working_hours_optimal.text.toString().toInt()
                        standard_working_hours_optimal.inputType=InputType.TYPE_NULL
                        newData.unit_of_timer_optimal=unit_of_timer_optimal.text.toString()
                        unit_of_timer_optimal.inputType=InputType.TYPE_NULL
                        newData.number_of_staff_optimal=number_of_staff_optimal.text.toString()
                        number_of_staff_optimal.inputType=InputType.TYPE_NULL
                        newData.theoretical_output=theoretical_output.text.toString().toInt()
                        theoretical_output.inputType=InputType.TYPE_NULL
                        newData.standard_working_hours_less=standard_working_hours_less.text.toString().toInt()
                        standard_working_hours_less.inputType=InputType.TYPE_NULL
                        newData.unit_of_timer_less=unit_of_timer_less.text.toString()
                        unit_of_timer_less.inputType=InputType.TYPE_NULL
                        newData.number_of_staff_less=number_of_staff_less.text.toString()
                        number_of_staff_less.inputType=InputType.TYPE_NULL
                        newData.theoretical_output_less=theoretical_output_less.text.toString().toInt()
                        theoretical_output_less.inputType=InputType.TYPE_NULL


                        newData.remark=remark.text.toString()
                        remark.isClickable=false
                        remark.isFocusable=false
                        remark.isFocusableInTouchMode=false
                        remark.isTextInputLayoutFocusedRectEnabled=false
                        //Log.d("GSON", "msg: ${oldData}\n")
                        //Log.d("GSON", "msg: ${newData.remark}\n")
                        editBasic("MeWorkstationBasicInfo",oldData,newData)//更改資料庫資料
                        when(cookie_data.status){
                            0-> {//成功
                                itemData.data[adapterPosition] = newData//更改渲染資料
                                editor.setText(cookie_data.username)
                                Toast.makeText(itemView.context, cookie_data.msg, Toast.LENGTH_SHORT).show()
                            }
                            1->{//失敗
                                Toast.makeText(itemView.context, cookie_data.msg, Toast.LENGTH_SHORT).show()
                                workstation_number.setText(oldData.workstation_number)
                                product_id.setText(oldData.product_id)
                                workstation_code.setText(oldData.workstation_code)
                                is_body_condition_ok.setText(oldData.is_body_condition_ok.toString())
                                is_vision_condition_ok.setText(oldData.is_vision_condition_ok.toString())
                                is_technology_ok.setText(oldData.is_technology_ok.toString())
                                is_normal.setText(oldData.is_normal.toString())
                                job_description.setText(oldData.job_description)
                                standard_working_hours_optimal.setText(oldData.standard_working_hours_optimal.toString())
                                unit_of_timer_optimal.setText(oldData.unit_of_timer_optimal)
                                number_of_staff_optimal.setText(oldData.number_of_staff_optimal)
                                theoretical_output.setText(oldData.theoretical_output.toString())
                                standard_working_hours_less.setText(oldData.standard_working_hours_less.toString())
                                unit_of_timer_less.setText(oldData.unit_of_timer_less)
                                number_of_staff_less.setText(oldData.number_of_staff_less)
                                theoretical_output_less.setText(oldData.theoretical_output_less.toString())


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
                    deleteBasic("MeWorkstationBasicInfo",data[adapterPosition])
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
                    lockBasic("MeWorkstationBasicInfo",data[adapterPosition])
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
        private fun editBasic(operation:String,oldData:MeWorkstationBasicInfo,newData:MeWorkstationBasicInfo) {
            val old =JSONObject()
            old.put("workstation_number",oldData.workstation_number)
            old.put("product_id",oldData.product_id)
            old.put("workstation_code",oldData.workstation_code)
            old.put("is_body_condition_ok",oldData.is_body_condition_ok)
            old.put("is_vision_condition_ok",oldData.is_vision_condition_ok)
            old.put("is_technology_ok",oldData.is_technology_ok)
            old.put("is_normal",oldData.is_normal)
            old.put("job_description",oldData.job_description)
            old.put("standard_working_hours_optimal",oldData.standard_working_hours_optimal)
            old.put("unit_of_timer_optimal",oldData.unit_of_timer_optimal)
            old.put("number_of_staff_optimal",oldData.number_of_staff_optimal)
            old.put("theoretical_output",oldData.theoretical_output)
            old.put("standard_working_hours_less",oldData.standard_working_hours_less)
            old.put("unit_of_timer_less",oldData.unit_of_timer_less)
            old.put("number_of_staff_less",oldData.number_of_staff_less)
            old.put("theoretical_output_less",oldData.theoretical_output_less)

            old.put("remark",oldData.remark)
            val new =JSONObject()
            new.put("workstation_number",newData.workstation_number)
            new.put("product_id",newData.product_id)
            new.put("workstation_code",newData.workstation_code)
            new.put("is_body_condition_ok",newData.is_body_condition_ok)
            new.put("is_vision_condition_ok",newData.is_vision_condition_ok)
            new.put("is_technology_ok",newData.is_technology_ok)
            new.put("is_normal",newData.is_normal)
            new.put("job_description",newData.job_description)
            new.put("standard_working_hours_optimal",newData.standard_working_hours_optimal)
            new.put("unit_of_timer_optimal",newData.unit_of_timer_optimal)
            new.put("number_of_staff_optimal",newData.number_of_staff_optimal)
            new.put("theoretical_output",newData.theoretical_output)
            new.put("standard_working_hours_less",newData.standard_working_hours_less)
            new.put("unit_of_timer_less",newData.unit_of_timer_less)
            new.put("number_of_staff_less",newData.number_of_staff_less)
            new.put("theoretical_output_less",newData.theoretical_output_less)

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
                .url("http://140.125.46.125:8000/basic_management")
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
        private fun deleteBasic(operation:String,deleteData:MeWorkstationBasicInfo) {
            val delete = JSONObject()
            delete.put("workstation_number",deleteData.workstation_number)
            delete.put("product_id",deleteData.product_id)
            delete.put("workstation_code",deleteData.workstation_code)
            delete.put("is_body_condition_ok",deleteData.is_body_condition_ok)
            delete.put("is_vision_condition_ok",deleteData.is_vision_condition_ok)
            delete.put("is_technology_ok",deleteData.is_technology_ok)
            delete.put("is_normal",deleteData.is_normal)
            delete.put("job_description",deleteData.job_description)
            delete.put("standard_working_hours_optimal",deleteData.standard_working_hours_optimal)
            delete.put("unit_of_timer_optimal",deleteData.unit_of_timer_optimal)
            delete.put("number_of_staff_optimal",deleteData.number_of_staff_optimal)
            delete.put("theoretical_output",deleteData.theoretical_output)
            delete.put("standard_working_hours_less",deleteData.standard_working_hours_less)
            delete.put("unit_of_timer_less",deleteData.unit_of_timer_less)
            delete.put("number_of_staff_less",deleteData.number_of_staff_less)
            delete.put("theoretical_output_less",deleteData.theoretical_output_less)

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
                .url("http://140.125.46.125:8000/basic_management")
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
        private fun lockBasic(operation:String,lockData:MeWorkstationBasicInfo) {
            val lock = JSONObject()
            lock.put("workstation_number",lockData.workstation_number)
            lock.put("product_id",lockData.product_id)
            lock.put("workstation_code",lockData.workstation_code)
            lock.put("is_body_condition_ok",lockData.is_body_condition_ok)
            lock.put("is_vision_condition_ok",lockData.is_vision_condition_ok)
            lock.put("is_technology_ok",lockData.is_technology_ok)
            lock.put("is_normal",lockData.is_normal)
            lock.put("job_description",lockData.job_description)
            lock.put("standard_working_hours_optimal",lockData.standard_working_hours_optimal)
            lock.put("unit_of_timer_optimal",lockData.unit_of_timer_optimal)
            lock.put("number_of_staff_optimal",lockData.number_of_staff_optimal)
            lock.put("theoretical_output",lockData.theoretical_output)
            lock.put("standard_working_hours_less",lockData.standard_working_hours_less)
            lock.put("unit_of_timer_less",lockData.unit_of_timer_less)
            lock.put("number_of_staff_less",lockData.number_of_staff_less)
            lock.put("theoretical_output_less",lockData.theoretical_output_less)

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
                .url("http://140.125.46.125:8000/basic_management")
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
    fun addItem(addData:MeWorkstationBasicInfo){
        data.add(itemData.count,addData)
        notifyItemInserted(itemData.count)
        itemData.count+=1
    }

}


