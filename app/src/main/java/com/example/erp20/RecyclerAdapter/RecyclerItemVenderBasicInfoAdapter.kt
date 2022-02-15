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


class RecyclerItemVenderBasicInfoAdapter() :
    RecyclerView.Adapter<RecyclerItemVenderBasicInfoAdapter.ViewHolder>() {
    private val itemData: ShowVenderBasicInfo =Gson().fromJson(cookie_data.response_data, ShowVenderBasicInfo::class.java)
    private var data: ArrayList<VenderBasicInfo> =itemData.data

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemVenderBasicInfoAdapter.ViewHolder {
        val v= LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_vender_basic_info,parent,false)
        return ViewHolder(v)

    }

    override fun onBindViewHolder(holder: RecyclerItemVenderBasicInfoAdapter.ViewHolder, position: Int) {

        //Log.d("GSON", "msg: ${itemData.data[position]}\n")
        holder._id.setText(data[position]._id)
        holder._id.inputType=InputType.TYPE_NULL
        holder.abbreviation.setText(data[position].abbreviation)
        holder.abbreviation.inputType=InputType.TYPE_NULL
        holder.full_name.setText(data[position].full_name)
        holder.full_name.inputType=InputType.TYPE_NULL
        holder.card_number.setText(data[position].card_number)
        holder.card_number.inputType=InputType.TYPE_NULL
        holder.is_supplier.setText(data[position].is_supplier.toString())
        holder.is_supplier.isClickable=false
        holder.is_supplier.inputType=InputType.TYPE_NULL
        holder.is_processor.setText(data[position].is_processor.toString())
        holder.is_processor.isClickable=false
        holder.is_processor.inputType=InputType.TYPE_NULL
        holder.is_other.setText(data[position].is_other.toString())
        holder.is_other.isClickable=false
        holder.is_other.inputType=InputType.TYPE_NULL
        holder.rank.setText(data[position].rank)
        holder.rank.inputType=InputType.TYPE_NULL
        holder.evaluation_date.setText(data[position].evaluation_date)
        holder.evaluation_date.inputType=InputType.TYPE_NULL
        holder.days_before_delivery.setText(data[position].days_before_delivery.toString())
        holder.days_before_delivery.inputType=InputType.TYPE_NULL
        holder.days_before_delivery_time.setText(data[position].days_before_delivery_time)
        holder.days_before_delivery_time.inputType=InputType.TYPE_NULL




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
        var _id=itemView.findViewById<TextInputEditText>(R.id.edit_id)
        var abbreviation=itemView.findViewById<TextInputEditText>(R.id.edit_abbreviation)
        var full_name=itemView.findViewById<TextInputEditText>(R.id.edit_full_name)
        var card_number=itemView.findViewById<TextInputEditText>(R.id.edit_card_number)
        var is_supplier=itemView.findViewById<AutoCompleteTextView>(R.id.edit_is_supplier)
        var is_processor=itemView.findViewById<AutoCompleteTextView>(R.id.edit_is_processor)
        var is_other=itemView.findViewById<AutoCompleteTextView>(R.id.edit_is_other)
        var rank=itemView.findViewById<TextInputEditText>(R.id.edit_rank)
        var evaluation_date=itemView.findViewById<TextInputEditText>(R.id.edit_evaluation_date)
        var days_before_delivery=itemView.findViewById<TextInputEditText>(R.id.edit_days_before_delivery)
        var days_before_delivery_time=itemView.findViewById<TextInputEditText>(R.id.edit_days_before_delivery_time)


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
        lateinit var oldData:VenderBasicInfo
        lateinit var newData:VenderBasicInfo
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
                        _id.inputType=InputType.TYPE_CLASS_TEXT
                        abbreviation.inputType=InputType.TYPE_CLASS_TEXT
                        full_name.inputType=InputType.TYPE_CLASS_TEXT
                        card_number.inputType=InputType.TYPE_CLASS_TEXT
                        is_supplier.setAdapter(arrayAdapter)
                        is_supplier.isClickable=true
                        is_processor.setAdapter(arrayAdapter)
                        is_processor.isClickable=true
                        is_processor.setAdapter(arrayAdapter)
                        is_processor.isClickable=true
                        rank.inputType=InputType.TYPE_CLASS_TEXT
                        evaluation_date.inputType=InputType.TYPE_CLASS_DATETIME
                        days_before_delivery.inputType=InputType.TYPE_CLASS_NUMBER
                        days_before_delivery_time.inputType=InputType.TYPE_CLASS_DATETIME

                        remark.isClickable=true
                        remark.isFocusable=true
                        remark.isFocusableInTouchMode=true
                        remark.isTextInputLayoutFocusedRectEnabled=true
                        _id.requestFocus()
                        edit_btn.text = "完成"
                    }
                    "完成"->{
                        newData= VenderBasicInfo()
                        newData._id=_id.text.toString()
                        _id.inputType=InputType.TYPE_NULL
                        newData.abbreviation=abbreviation.text.toString()
                        abbreviation.inputType=InputType.TYPE_NULL
                        newData.full_name=full_name.text.toString()
                        full_name.inputType=InputType.TYPE_NULL
                        newData.card_number=card_number.text.toString()
                        card_number.inputType=InputType.TYPE_NULL
                        newData.is_supplier=is_supplier.text.toString().toBoolean()
                        is_supplier.isClickable=false
                        is_supplier.setAdapter(null)
                        newData.is_processor=is_processor.text.toString().toBoolean()
                        is_processor.isClickable=false
                        is_processor.setAdapter(null)
                        newData.is_other=is_other.text.toString().toBoolean()
                        is_other.isClickable=false
                        is_other.setAdapter(null)
                        newData.rank=rank.text.toString()
                        rank.inputType=InputType.TYPE_NULL
                        newData.evaluation_date=evaluation_date.text.toString()
                        evaluation_date.inputType=InputType.TYPE_NULL
                        newData.days_before_delivery=days_before_delivery.text.toString().toInt()
                        days_before_delivery.inputType=InputType.TYPE_NULL
                        newData.days_before_delivery_time=days_before_delivery_time.text.toString()
                        days_before_delivery_time.inputType=InputType.TYPE_NULL


                        newData.remark=remark.text.toString()
                        remark.isClickable=false
                        remark.isFocusable=false
                        remark.isFocusableInTouchMode=false
                        remark.isTextInputLayoutFocusedRectEnabled=false
                        //Log.d("GSON", "msg: ${oldData}\n")
                        //Log.d("GSON", "msg: ${newData.remark}\n")
                        editBasic("VenderBasicInfo",oldData,newData)//更改資料庫資料
                        when(cookie_data.status){
                            0-> {//成功
                                itemData.data[adapterPosition] = newData//更改渲染資料
                                editor.setText(cookie_data.username)
                                Toast.makeText(itemView.context, cookie_data.msg, Toast.LENGTH_SHORT).show()
                            }
                            1->{//失敗
                                Toast.makeText(itemView.context, cookie_data.msg, Toast.LENGTH_SHORT).show()
                                _id.setText(oldData._id)
                                abbreviation.setText(oldData.abbreviation)
                                full_name.setText(oldData.full_name)
                                card_number.setText(oldData.card_number)
                                is_supplier.setText(oldData.is_supplier.toString())
                                is_processor.setText(oldData.is_processor.toString())
                                is_other.setText(oldData.is_other.toString())
                                rank.setText(oldData.rank)
                                evaluation_date.setText(oldData.evaluation_date)
                                days_before_delivery.setText(oldData.days_before_delivery.toString())
                                days_before_delivery_time.setText(oldData.days_before_delivery_time)


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
                    deleteBasic("VenderBasicInfo",data[adapterPosition])
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
                    lockBasic("VenderBasicInfo",data[adapterPosition])
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
        private fun editBasic(operation:String,oldData:VenderBasicInfo,newData:VenderBasicInfo) {
            val old =JSONObject()
            old.put("_id",oldData._id)
            old.put("abbreviation",oldData.abbreviation)
            old.put("full_name",oldData.full_name)
            old.put("card_number",oldData.card_number)
            old.put("is_supplier",oldData.is_supplier)
            old.put("is_processor",oldData.is_processor)
            old.put("is_other",oldData.is_other)
            old.put("rank",oldData.rank)
            old.put("evaluation_date",oldData.evaluation_date)
            old.put("days_before_delivery",oldData.days_before_delivery)
            old.put("days_before_delivery_time",oldData.days_before_delivery_time)

            old.put("remark",oldData.remark)
            val new =JSONObject()
            new.put("_id",newData._id)
            new.put("abbreviation",newData.abbreviation)
            new.put("full_name",newData.full_name)
            new.put("card_number",newData.card_number)
            new.put("is_supplier",newData.is_supplier)
            new.put("is_processor",newData.is_processor)
            new.put("is_other",newData.is_other)
            new.put("rank",newData.rank)
            new.put("evaluation_date",newData.evaluation_date)
            new.put("days_before_delivery",newData.days_before_delivery)
            new.put("days_before_delivery_time",newData.days_before_delivery_time)


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
                .add("action",cookie_data.Actions.CHANGE)
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
        private fun deleteBasic(operation:String,deleteData:VenderBasicInfo) {
            val delete = JSONObject()
            delete.put("_id",deleteData._id)
            delete.put("abbreviation",deleteData.abbreviation)
            delete.put("full_name",deleteData.full_name)
            delete.put("card_number",deleteData.card_number)
            delete.put("is_supplier",deleteData.is_supplier)
            delete.put("is_processor",deleteData.is_processor)
            delete.put("is_other",deleteData.is_other)
            delete.put("rank",deleteData.rank)
            delete.put("evaluation_date",deleteData.evaluation_date)
            delete.put("days_before_delivery",deleteData.days_before_delivery)
            delete.put("days_before_delivery_time",deleteData.days_before_delivery_time)


            delete.put("remark",deleteData.remark)
            //Log.d("GSON", "msg:${delete}")
            val body = FormBody.Builder()
                .add("card_number",cookie_data.card_number)
                .add("username", cookie_data.username)
                .add("operation", operation)
                .add("action",cookie_data.Actions.DELETE)
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
        private fun lockBasic(operation:String,lockData:VenderBasicInfo) {
            val lock = JSONObject()
            lock.put("_id",lockData._id)
            lock.put("abbreviation",lockData.abbreviation)
            lock.put("full_name",lockData.full_name)
            lock.put("card_number",lockData.card_number)
            lock.put("is_supplier",lockData.is_supplier)
            lock.put("is_processor",lockData.is_processor)
            lock.put("is_other",lockData.is_other)
            lock.put("rank",lockData.rank)
            lock.put("evaluation_date",lockData.evaluation_date)
            lock.put("days_before_delivery",lockData.days_before_delivery)
            lock.put("days_before_delivery_time",lockData.days_before_delivery_time)


            lock.put("remark",lockData.remark)
            //Log.d("GSON", "msg:${delete}")
            val body = FormBody.Builder()
                .add("card_number",cookie_data.card_number)
                .add("username", cookie_data.username)
                .add("operation", operation)
                .add("action",cookie_data.Actions.LOCK)
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
    fun addItem(addData:VenderBasicInfo){
        data.add(itemData.count,addData)
        notifyItemInserted(itemData.count)
        itemData.count+=1
    }

}


