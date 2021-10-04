package com.example.erp20.RecyclerAdapter
import android.app.Activity
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.Toast
import android.widget.*
import android.content.*
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


class RecyclerItemProductBasicInfoAdapter() :
    RecyclerView.Adapter<RecyclerItemProductBasicInfoAdapter.ViewHolder>() {
    private val itemData: ShowProductBasicInfo =Gson().fromJson(cookie_data.response_data, ShowProductBasicInfo::class.java)
    private var data: ArrayList<ProductBasicInfo> =itemData.data

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemProductBasicInfoAdapter.ViewHolder {
        val v= LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_product_basic_info,parent,false)
        return ViewHolder(v)

    }

    override fun onBindViewHolder(holder: RecyclerItemProductBasicInfoAdapter.ViewHolder, position: Int) {

        //Log.d("GSON", "msg: ${itemData.data[position]}\n")
        holder._id.setText(data[position]._id)
        holder._id.inputType=InputType.TYPE_NULL
        holder.product_name.setText(data[position].product_name)
        holder.product_name.inputType=InputType.TYPE_NULL
        holder.product_type.setText(data[position].product_type)
        holder.product_type.inputType=InputType.TYPE_NULL
        holder.is_new.setText(data[position].is_new.toString())
        holder.is_new.isClickable=false
        holder.is_new.inputType=InputType.TYPE_NULL
        holder.release_date.setText(data[position].release_date)
        holder.release_date.inputType=InputType.TYPE_NULL
        holder.is_discontinue.setText(data[position].is_discontinue.toString())
        holder.is_discontinue.isClickable=false
        holder.is_discontinue.inputType=InputType.TYPE_NULL
        holder.discontinued_date.setText(data[position].discontinued_date)
        holder.discontinued_date.inputType=InputType.TYPE_NULL
        holder.is_inventory.setText(data[position].is_inventory.toString())
        holder.is_inventory.isClickable=false
        holder.is_inventory.inputType=InputType.TYPE_NULL
        holder.inventory_date.setText(data[position].inventory_date)
        holder.inventory_date.inputType=InputType.TYPE_NULL




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
        return cookie_data.itemCount
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var _id=itemView.findViewById<TextInputEditText>(R.id.edit_id)
        var product_name=itemView.findViewById<TextInputEditText>(R.id.edit_product_name)
        var product_type=itemView.findViewById<TextInputEditText>(R.id.edit_product_type)
        var is_new=itemView.findViewById<AutoCompleteTextView>(R.id.edit_is_new)
        var release_date=itemView.findViewById<TextInputEditText>(R.id.edit_release_date)
        var is_discontinue=itemView.findViewById<AutoCompleteTextView>(R.id.edit_is_discontinue)
        var discontinued_date=itemView.findViewById<TextInputEditText>(R.id.edit_discontinued_date)
        var is_inventory=itemView.findViewById<AutoCompleteTextView>(R.id.edit_is_inventory)
        var inventory_date=itemView.findViewById<TextInputEditText>(R.id.edit_inventory_date)

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
        lateinit var oldData:ProductBasicInfo
        lateinit var newData:ProductBasicInfo
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
                        product_name.inputType=InputType.TYPE_CLASS_TEXT
                        product_type.inputType=InputType.TYPE_CLASS_TEXT
                        is_new.setAdapter(arrayAdapter)
                        println(is_new.dropDownHeight)
                        is_new.isClickable=true
                        release_date.inputType=InputType.TYPE_DATETIME_VARIATION_DATE
                        is_discontinue.setAdapter(arrayAdapter)
                        is_discontinue.isClickable=true
                        discontinued_date.inputType=InputType.TYPE_DATETIME_VARIATION_DATE
                        is_inventory.setAdapter(arrayAdapter)
                        is_inventory.isClickable=true
                        inventory_date.inputType=InputType.TYPE_CLASS_DATETIME

                        remark.isClickable=true
                        remark.isFocusable=true
                        remark.isFocusableInTouchMode=true
                        remark.isTextInputLayoutFocusedRectEnabled=true
                        _id.requestFocus()
                        edit_btn.text = "完成"
                    }
                    "完成"->{
                        newData= ProductBasicInfo("","","",false,"",false,"",false,"")
                        newData._id=_id.text.toString()
                        newData.product_name=product_name.text.toString()
                        newData.product_type=product_type.text.toString()
                        newData.is_new=is_new.text.toString().toBoolean()
                        newData.release_date=release_date.text.toString()
                        newData.is_discontinue=is_discontinue.text.toString().toBoolean()
                        newData.discontinued_date=discontinued_date.text.toString()
                        newData.is_inventory=is_inventory.text.toString().toBoolean()
                        newData.inventory_date=inventory_date.text.toString()

                        newData.remark=remark.text.toString()
                        //Log.d("GSON", "msg: ${oldData}\n")
                        //Log.d("GSON", "msg: ${newData.remark}\n")
                        _id.inputType=InputType.TYPE_NULL
                        product_name.inputType=InputType.TYPE_NULL
                        product_type.inputType=InputType.TYPE_NULL
                        is_new.isClickable=false
                        is_new.setAdapter(null)
                        release_date.inputType=InputType.TYPE_NULL
                        is_discontinue.isClickable=false
                        is_discontinue.setAdapter(null)
                        discontinued_date.inputType=InputType.TYPE_NULL
                        is_inventory.isClickable=false
                        is_inventory.setAdapter(null)
                        inventory_date.inputType=InputType.TYPE_NULL

                        remark.isClickable=false
                        remark.isFocusable=false
                        remark.isFocusableInTouchMode=false
                        remark.isTextInputLayoutFocusedRectEnabled=false
                        editBasic("ProductBasicInfo",oldData,newData)//更改資料庫資料
                        when(cookie_data.status){
                            0-> {//成功
                                itemData.data[adapterPosition] = newData//更改渲染資料
                                editor.setText(cookie_data.username)
                                Toast.makeText(itemView.context, cookie_data.msg, Toast.LENGTH_SHORT).show()
                            }
                            1->{//失敗
                                Toast.makeText(itemView.context, cookie_data.msg, Toast.LENGTH_SHORT).show()
                                _id.setText(oldData._id)
                                product_name.setText(oldData.product_name)
                                product_type.setText(oldData.product_type)
                                is_new.setText(oldData.is_new.toString())
                                release_date.setText(oldData.release_date)
                                is_discontinue.setText(oldData.is_discontinue.toString())
                                discontinued_date.setText(oldData.discontinued_date)
                                is_inventory.setText(oldData.is_inventory.toString())
                                inventory_date.setText(oldData.inventory_date)

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
                    deleteBasic("ProductBasicInfo",data[adapterPosition])
                    when(cookie_data.status){
                        0-> {//成功
                            data.removeAt(adapterPosition)
                            notifyItemRemoved(adapterPosition)
                            cookie_data.itemCount-=1
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
                    lockBasic("ProductBasicInfo",data[adapterPosition])
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
        private fun editBasic(operation:String,oldData:ProductBasicInfo,newData:ProductBasicInfo) {
            val old =JSONObject()
            old.put("_id",oldData._id)
            old.put("product_name",oldData.product_name)
            old.put("product_type",oldData.product_type)
            old.put("is_new",oldData.is_new)
            old.put("release_date",oldData.release_date)
            old.put("is_discontinue",oldData.is_discontinue)
            old.put("discontinued_date",oldData.discontinued_date)
            old.put("is_inventory",oldData.is_inventory)
            old.put("inventory_date",oldData.inventory_date)
            old.put("remark",oldData.remark)
            val new =JSONObject()
            new.put("_id",newData._id)
            new.put("product_name",newData.product_name)
            new.put("product_type",newData.product_type)
            new.put("is_new",newData.is_new)
            new.put("release_date",newData.release_date)
            new.put("is_discontinue",newData.is_discontinue)
            new.put("discontinued_date",newData.discontinued_date)
            new.put("is_inventory",newData.is_inventory)
            new.put("inventory_date",newData.inventory_date)
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
            val responseInfo = Gson().fromJson(cookie_data.response_data, response::class.java)
            cookie_data.status=responseInfo.status
            cookie_data.msg=responseInfo.msg


        }
        private fun deleteBasic(operation:String,deleteData:ProductBasicInfo) {
            val delete = JSONObject()
            delete.put("_id",deleteData._id)
            delete.put("product_name",deleteData.product_name)
            delete.put("product_type",deleteData.product_type)
            delete.put("is_new",deleteData.is_new)
            delete.put("release_date",deleteData.release_date)
            delete.put("is_discontinue",deleteData.is_discontinue)
            delete.put("discontinued_date",deleteData.discontinued_date)
            delete.put("is_inventory",deleteData.is_inventory)
            delete.put("inventory_date",deleteData.inventory_date)
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
                val responseInfo = Gson().fromJson(cookie_data.response_data, response::class.java)
                cookie_data.status=responseInfo.status
                cookie_data.msg=responseInfo.msg
            }

        }
        private fun lockBasic(operation:String,lockData:ProductBasicInfo) {
            val lock = JSONObject()
            lock.put("_id",lockData._id)
            lock.put("product_name",lockData.product_name)
            lock.put("product_type",lockData.product_type)
            lock.put("is_new",lockData.is_new)
            lock.put("release_date",lockData.release_date)
            lock.put("is_discontinue",lockData.is_discontinue)
            lock.put("discontinued_date",lockData.discontinued_date)
            lock.put("is_inventory",lockData.is_inventory)
            lock.put("inventory_date",lockData.inventory_date)
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
                val responseInfo = Gson().fromJson(cookie_data.response_data, response::class.java)
                cookie_data.status=responseInfo.status
                cookie_data.msg=responseInfo.msg
            }

        }


    }
    fun addItem(addData:ProductBasicInfo){
        data.add(cookie_data.itemCount,addData)
        notifyItemInserted(cookie_data.itemCount)
        cookie_data.itemCount+=1
    }

}


