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


class RecyclerItemCustomerForecastListBodyAdapter() :
    RecyclerView.Adapter<RecyclerItemCustomerForecastListBodyAdapter.ViewHolder>() {
    private var itemData: ShowCustomerForecastListBody =Gson().fromJson(cookie_data.response_data, ShowCustomerForecastListBody::class.java)
    private var data: ArrayList<CustomerForecastListBody> =itemData.data
    var relativeCombobox01=cookie_data.product_id_ComboboxData
    var relativeCombobox02=cookie_data.product_type_id_ComboboxData

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemCustomerForecastListBodyAdapter.ViewHolder {
        val v= LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_customer_forecast_list_body,parent,false)
        return ViewHolder(v)

    }

    override fun onBindViewHolder(holder: RecyclerItemCustomerForecastListBodyAdapter.ViewHolder, position: Int) {

        //Log.d("GSON", "msg: ${itemData.data[position]}\n")
        holder.header_id.setText(data[position].header_id)
        holder.header_id.inputType=InputType.TYPE_NULL
        holder.section.setText(data[position].section)
        holder.section.inputType=InputType.TYPE_NULL
        holder.age_mounth.setText(data[position].age_mounth)
        holder.age_mounth.inputType=InputType.TYPE_NULL
        holder.product_id.setText(data[position].product_id)
        holder.product_id.inputType=InputType.TYPE_NULL
        holder.product_type_id.setText(data[position].product_type_id)
        holder.product_type_id.inputType=InputType.TYPE_NULL
        holder.count.setText(data[position].count.toString())
        holder.count.inputType=InputType.TYPE_NULL
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
        var header_id=itemView.findViewById<TextInputEditText>(R.id.edit_header_id)
        var section=itemView.findViewById<TextInputEditText>(R.id.edit_section)
        var age_mounth=itemView.findViewById<TextInputEditText>(R.id.edit_age_mounth)
        var product_id=itemView.findViewById<AutoCompleteTextView>(R.id.edit_product_id)
        var product_type_id=itemView.findViewById<AutoCompleteTextView>(R.id.edit_product_type_id)
        var count=itemView.findViewById<TextInputEditText>(R.id.edit_count)
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
        lateinit var oldData:CustomerForecastListBody
        lateinit var newData:CustomerForecastListBody
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
                        //header_id.inputType=InputType.TYPE_CLASS_TEXT
                        section.inputType=InputType.TYPE_CLASS_TEXT
                        //age_mounth.inputType=InputType.TYPE_CLASS_TEXT
                        product_id.setAdapter(arrayAdapter01)
                        product_type_id.setAdapter(arrayAdapter02)
                        count.inputType=InputType.TYPE_CLASS_NUMBER
                        unit_of_measurement.inputType=InputType.TYPE_CLASS_TEXT


                        remark.isClickable=true
                        remark.isFocusable=true
                        remark.isFocusableInTouchMode=true
                        remark.isTextInputLayoutFocusedRectEnabled=true
                        section.requestFocus()
                        edit_btn.text = "完成"
                    }
                    "完成"->{
                        newData= CustomerForecastListBody()
                        newData.header_id=header_id.text.toString()
                        header_id.inputType=InputType.TYPE_NULL
                        newData.section=section.text.toString()
                        section.inputType=InputType.TYPE_NULL
                        newData.age_mounth=age_mounth.text.toString()
                        age_mounth.inputType=InputType.TYPE_NULL
                        newData.product_id=product_id.text.toString()
                        product_id.setAdapter(null)
                        newData.product_type_id=product_type_id.text.toString()
                        product_type_id.setAdapter(null)
                        newData.count=count.text.toString().toInt()
                        count.inputType=InputType.TYPE_NULL
                        newData.unit_of_measurement=unit_of_measurement.text.toString()
                        unit_of_measurement.inputType=InputType.TYPE_NULL



                        newData.remark=remark.text.toString()
                        remark.isClickable=false
                        remark.isFocusable=false
                        remark.isFocusableInTouchMode=false
                        remark.isTextInputLayoutFocusedRectEnabled=false
                        //Log.d("GSON", "msg: ${oldData}\n")
                        //Log.d("GSON", "msg: ${newData.remark}\n")
                        edit_CustomerForecastList("CustomerForecastList","body",oldData,newData)//更改資料庫資料
                        when(cookie_data.status){
                            0-> {//成功
                                itemData.data[adapterPosition] = newData//更改渲染資料
                                editor.setText(cookie_data.username)
                                Toast.makeText(itemView.context, cookie_data.msg, Toast.LENGTH_SHORT).show()
                            }
                            1->{//失敗
                                Toast.makeText(itemView.context, cookie_data.msg, Toast.LENGTH_SHORT).show()
                                header_id.setText(oldData.header_id)
                                section.setText(oldData.section)
                                age_mounth.setText(oldData.age_mounth)
                                product_id.setText(oldData.product_id)
                                product_type_id.setText(oldData.product_type_id)
                                count.setText(oldData.count.toString())
                                unit_of_measurement.setText(oldData.unit_of_measurement)

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
                    delete_CustomerForecastList("CustomerForecastList","body",data[adapterPosition])
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
                    lock_CustomerForecastList("CustomerForecastList","body",data[adapterPosition])
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
                    over_CustomerForecastList("CustomerForecastList","body",data[adapterPosition])
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
        private fun edit_CustomerForecastList(operation:String,target:String,oldData: CustomerForecastListBody,newData: CustomerForecastListBody) {
            val old =JSONObject()
            old.put("header_id",oldData.header_id)
            old.put("section",oldData.section)
            old.put("age_mounth",oldData.age_mounth)
            old.put("product_id",oldData.product_id)
            old.put("product_type_id",oldData.product_type_id)
            old.put("count",oldData.count)
            old.put("unit_of_measurement",oldData.unit_of_measurement)

            old.put("remark",oldData.remark)
            val new =JSONObject()
            new.put("header_id",newData.header_id)
            new.put("section",newData.section)
            new.put("age_mounth",newData.age_mounth)
            new.put("product_id",newData.product_id)
            new.put("product_type_id",newData.product_type_id)
            new.put("count",newData.count)
            new.put("unit_of_measurement",newData.unit_of_measurement)


            new.put("editor",cookie_data.username)
            new.put("remark",newData.remark)
            val data = JSONArray()
            data.put(old)
            data.put((new))
            val body = FormBody.Builder()
                .add("operation",operation)
                .add("target",target)
                .add("data",data.toString())
                .add("username", cookie_data.username)
                .add("action",cookie_data.Actions.CHANGE)
                .add("csrfmiddlewaretoken", cookie_data.tokenValue)
                .add("login_flag", cookie_data.loginflag)
                .build()
            val request = Request.Builder()
                .url(cookie_data.URL+"/custom_order_management")
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
        private fun delete_CustomerForecastList(operation:String,target:String,deleteData: CustomerForecastListBody) {
            val delete = JSONObject()
            delete.put("header_id",deleteData.header_id)
            delete.put("section",deleteData.section)
            delete.put("age_mounth",deleteData.age_mounth)
            delete.put("product_id",deleteData.product_id)
            delete.put("product_type_id",deleteData.product_type_id)
            delete.put("count",deleteData.count)
            delete.put("unit_of_measurement",deleteData.unit_of_measurement)

            delete.put("remark",deleteData.remark)
            //Log.d("GSON", "msg:${delete}")
            val body = FormBody.Builder()
                .add("operation", operation)
                .add("target",target)
                .add("username", cookie_data.username)
                .add("action",cookie_data.Actions.DELETE)
                .add("data",delete.toString())
                .add("csrfmiddlewaretoken", cookie_data.tokenValue)
                .add("login_flag", cookie_data.loginflag)
                .build()
            val request = Request.Builder()
                .url(cookie_data.URL+"/custom_order_management")
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
        private fun lock_CustomerForecastList(operation:String,target:String,lockData: CustomerForecastListBody) {
            val lock = JSONObject()
            lock.put("header_id",lockData.header_id)
            lock.put("section",lockData.section)
            lock.put("age_mounth",lockData.age_mounth)
            lock.put("product_id",lockData.product_id)
            lock.put("product_type_id",lockData.product_type_id)
            lock.put("count",lockData.count)
            lock.put("unit_of_measurement",lockData.unit_of_measurement)

            lock.put("remark",lockData.remark)
            //Log.d("GSON", "msg:${lock}")
            val body = FormBody.Builder()
                .add("operation", operation)
                .add("target",target)
                .add("username", cookie_data.username)
                .add("action",cookie_data.Actions.LOCK)
                .add("data",lock.toString())
                .add("csrfmiddlewaretoken", cookie_data.tokenValue)
                .add("login_flag", cookie_data.loginflag)
                .build()
            val request = Request.Builder()
                .url(cookie_data.URL+"/custom_order_management")
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
        private fun over_CustomerForecastList(operation:String,target:String,overData:CustomerForecastListBody) {
            val over = JSONObject()
            over.put("header_id",overData.header_id)
            over.put("section",overData.section)
            over.put("age_mounth",overData.age_mounth)
            over.put("product_id",overData.product_id)
            over.put("product_type_id",overData.product_type_id)
            over.put("count",overData.count)
            over.put("unit_of_measurement",overData.unit_of_measurement)

            over.put("remark",overData.remark)
            //Log.d("GSON", "msg:${delete}")
            val body = FormBody.Builder()
                .add("username", cookie_data.username)
                .add("operation", operation)
                .add("target", target)
                .add("action",cookie_data.Actions.CLOSE)
                .add("data",over.toString())
                .add("csrfmiddlewaretoken", cookie_data.tokenValue)
                .add("login_flag", cookie_data.loginflag)
                .build()
            val request = Request.Builder()
                .url(cookie_data.URL+"/custom_order_management")
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
    fun addItem(addData: CustomerForecastListBody){
        data.add(itemData.count,addData)
        notifyItemInserted(itemData.count)
        itemData.count+=1//cookie_data.itemCount+=1
    }

}


