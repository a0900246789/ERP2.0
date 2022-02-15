package com.example.erp20.RecyclerAdapter

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
import androidx.constraintlayout.widget.ConstraintLayout
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


class RecyclerItemInvChangeTypeSAdapter() :
    RecyclerView.Adapter<RecyclerItemInvChangeTypeSAdapter.ViewHolder>() {
    private val itemData: ShowInvChangeTypeS =Gson().fromJson(cookie_data.response_data, ShowInvChangeTypeS::class.java)
    private var data: ArrayList<InvChangeTypeS> =itemData.data

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemInvChangeTypeSAdapter.ViewHolder {
        val v= LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_inv_change_type_s,parent,false)
        return ViewHolder(v)

    }

    override fun onBindViewHolder(holder: RecyclerItemInvChangeTypeSAdapter.ViewHolder, position: Int) {

        //Log.d("GSON", "msg: ${itemData.data[position]}\n")
        holder.inv_code_s.setText(data[position].inv_code_s)
        holder.inv_code_s.inputType=InputType.TYPE_NULL
        holder.inv_code_m.setText(data[position].inv_code_m)
        holder.inv_code_m.inputType=InputType.TYPE_NULL
        holder.inv_name_s.setText(data[position].inv_name_s)
        holder.inv_name_s.inputType=InputType.TYPE_NULL
        holder.is_inventory_plus.setText(data[position].is_inventory_plus.toString())
        holder.is_inventory_plus.inputType=InputType.TYPE_NULL
        holder.is_inventory_reduce.setText(data[position].is_inventory_reduce.toString())
        holder.is_inventory_reduce.inputType=InputType.TYPE_NULL
        holder.is_not_affect.setText(data[position].is_not_affect.toString())
        holder.is_not_affect.inputType=InputType.TYPE_NULL
        holder.is_ok_product_warehouse.setText(data[position].is_ok_product_warehouse.toString())
        holder.is_ok_product_warehouse.inputType=InputType.TYPE_NULL
        holder.is_ng_product_warehouse.setText(data[position].is_ng_product_warehouse.toString())
        holder.is_ng_product_warehouse.inputType=InputType.TYPE_NULL
        holder.is_scrapped.setText(data[position].is_scrapped.toString())
        holder.is_scrapped.inputType=InputType.TYPE_NULL
        holder.auto_push_code.setText(data[position].auto_push_code)
        holder.auto_push_code.inputType=InputType.TYPE_NULL


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
        var inv_code_s=itemView.findViewById<TextInputEditText>(R.id.edit_inv_code_s)
        var inv_code_m=itemView.findViewById<TextInputEditText>(R.id.edit_inv_code_m)
        var inv_name_s=itemView.findViewById<TextInputEditText>(R.id.edit_inv_name_s)
        var is_inventory_plus=itemView.findViewById<AutoCompleteTextView>(R.id.edit_is_inventory_plus)
        var is_inventory_reduce=itemView.findViewById<AutoCompleteTextView>(R.id.edit_is_inventory_reduce)
        var is_not_affect=itemView.findViewById<AutoCompleteTextView>(R.id.edit_is_not_affect)
        var is_ok_product_warehouse=itemView.findViewById<AutoCompleteTextView>(R.id.edit_is_ok_product_warehouse)
        var is_ng_product_warehouse=itemView.findViewById<AutoCompleteTextView>(R.id.edit_is_ng_product_warehouse)
        var is_scrapped=itemView.findViewById<AutoCompleteTextView>(R.id.edit_is_scrapped)
        var auto_push_code=itemView.findViewById<TextInputEditText>(R.id.edit_auto_push_code)

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
        var layout=itemView.findViewById<ConstraintLayout>(R.id.constraint_layout)
        lateinit var oldData:InvChangeTypeS
        lateinit var newData:InvChangeTypeS
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
                        inv_code_s.inputType=InputType.TYPE_CLASS_TEXT
                        //inv_code_m.inputType=InputType.TYPE_CLASS_TEXT
                        inv_name_s.inputType=InputType.TYPE_CLASS_TEXT
                        is_inventory_plus.setAdapter(arrayAdapter)
                        is_inventory_reduce.setAdapter(arrayAdapter)
                        is_not_affect.setAdapter(arrayAdapter)
                        is_ok_product_warehouse.setAdapter(arrayAdapter)
                        is_ng_product_warehouse.setAdapter(arrayAdapter)
                        is_scrapped.setAdapter(arrayAdapter)
                        auto_push_code.inputType=InputType.TYPE_CLASS_TEXT


                        remark.isClickable=true
                        remark.isFocusable=true
                        remark.isFocusableInTouchMode=true
                        remark.isTextInputLayoutFocusedRectEnabled=true
                        inv_code_s.requestFocus()
                        edit_btn.text = "完成"
                        layout.setBackgroundColor(Color.parseColor("#6E5454"))
                        edit_btn.setBackgroundColor(Color.parseColor("#FF3700B3"))
                    }
                    "完成"->{
                        newData=InvChangeTypeS()
                        newData.inv_code_s=inv_code_s.text.toString()
                        inv_code_s.inputType=InputType.TYPE_NULL
                        newData.inv_code_m=inv_code_m.text.toString()
                        inv_code_m.inputType=InputType.TYPE_NULL
                        newData.inv_name_s=inv_name_s.text.toString()
                        inv_name_s.inputType=InputType.TYPE_NULL
                        newData.is_inventory_plus=is_inventory_plus.text.toString().toBoolean()
                        is_inventory_plus.setAdapter(null)
                        newData.is_inventory_reduce=is_inventory_reduce.text.toString().toBoolean()
                        is_inventory_reduce.setAdapter(null)
                        newData.is_not_affect=is_not_affect.text.toString().toBoolean()
                        is_not_affect.setAdapter(null)
                        newData.is_ok_product_warehouse=is_ok_product_warehouse.text.toString().toBoolean()
                        is_ok_product_warehouse.setAdapter(null)
                        newData.is_ng_product_warehouse=is_ng_product_warehouse.text.toString().toBoolean()
                        is_ng_product_warehouse.setAdapter(null)
                        newData.is_scrapped=is_scrapped.text.toString().toBoolean()
                        is_scrapped.setAdapter(null)
                        newData.auto_push_code=auto_push_code.text.toString()
                        auto_push_code.inputType=InputType.TYPE_NULL

                        newData.remark=remark.text.toString()
                        //Log.d("GSON", "msg: ${oldData}\n")
                        //Log.d("GSON", "msg: ${newData.remark}\n")
                        remark.isClickable=false
                        remark.isFocusable=false
                        remark.isFocusableInTouchMode=false
                        remark.isTextInputLayoutFocusedRectEnabled=false
                        editDef("InvChangeTypeS",oldData,newData)//更改資料庫資料
                        when(cookie_data.status){
                            0-> {//成功
                                itemData.data[adapterPosition] = newData//更改渲染資料
                                editor.setText(cookie_data.username)
                                Toast.makeText(itemView.context, cookie_data.msg, Toast.LENGTH_SHORT).show()
                            }
                            1->{//失敗
                                Toast.makeText(itemView.context, cookie_data.msg, Toast.LENGTH_SHORT).show()
                                inv_code_s.setText(oldData.inv_code_s)
                                inv_code_m.setText(oldData.inv_code_m)
                                inv_name_s.setText(oldData.inv_name_s)
                                is_inventory_plus.setText(oldData.is_inventory_plus.toString())
                                is_inventory_reduce.setText(oldData.is_inventory_reduce.toString())
                                is_not_affect.setText(oldData.is_not_affect.toString())
                                is_ok_product_warehouse.setText(oldData.is_ok_product_warehouse.toString())
                                is_ng_product_warehouse.setText(oldData.is_ng_product_warehouse.toString())
                                is_scrapped.setText(oldData.is_scrapped.toString())
                                auto_push_code.setText(oldData.auto_push_code)

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
                    deleteDef("InvChangeTypeS",data[adapterPosition])
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
                    lockDef("InvChangeTypeS",data[adapterPosition])
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
        private fun editDef(operation:String,oldData:InvChangeTypeS,newData:InvChangeTypeS) {
            val old =JSONObject()
            old.put("inv_code_s",oldData.inv_code_s)
            old.put("inv_code_m",oldData.inv_code_m)
            old.put("inv_name_s",oldData.inv_name_s)
            old.put("is_inventory_plus",oldData.is_inventory_plus)
            old.put("is_inventory_reduce",oldData.is_inventory_reduce)
            old.put("is_not_affect",oldData.is_not_affect)
            old.put("is_ok_product_warehouse",oldData.is_ok_product_warehouse)
            old.put("is_ng_product_warehouse",oldData.is_ng_product_warehouse)
            old.put("is_scrapped",oldData.is_scrapped)
            old.put("auto_push_code",oldData.auto_push_code)


            old.put("remark",oldData.remark)
            val new =JSONObject()
            new.put("inv_code_s",newData.inv_code_s)
            new.put("inv_code_m",newData.inv_code_m)
            new.put("inv_name_s",newData.inv_name_s)
            new.put("is_inventory_plus",newData.is_inventory_plus)
            new.put("is_inventory_reduce",newData.is_inventory_reduce)
            new.put("is_not_affect",newData.is_not_affect)
            new.put("is_ok_product_warehouse",newData.is_ok_product_warehouse)
            new.put("is_ng_product_warehouse",newData.is_ng_product_warehouse)
            new.put("is_scrapped",newData.is_scrapped)
            new.put("auto_push_code",newData.auto_push_code)


            new.put("editor",cookie_data.username)
            new.put("remark",newData.remark)
            val data = JSONArray()
            data.put(old)
            data.put((new))
            val body = FormBody.Builder()
                .add("data",data.toString())
                .add("username", cookie_data.username)
                .add("operation", operation)
                .add("action",cookie_data.Actions.CHANGE)
                .add("csrfmiddlewaretoken", cookie_data.tokenValue)
                .add("login_flag", cookie_data.loginflag)
                .build()
            val request = Request.Builder()
                .url(cookie_data.URL+"/def_management")
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
        private fun deleteDef(operation:String,deleteData:InvChangeTypeS) {
            val delete = JSONObject()
            delete.put("inv_code_s",deleteData.inv_code_s)
            delete.put("inv_code_m",deleteData.inv_code_m)
            delete.put("inv_name_s",deleteData.inv_name_s)
            delete.put("is_inventory_plus",deleteData.is_inventory_plus)
            delete.put("is_inventory_reduce",deleteData.is_inventory_reduce)
            delete.put("is_not_affect",deleteData.is_not_affect)
            delete.put("is_ok_product_warehouse",deleteData.is_ok_product_warehouse)
            delete.put("is_ng_product_warehouse",deleteData.is_ng_product_warehouse)
            delete.put("is_scrapped",deleteData.is_scrapped)
            delete.put("auto_push_code",deleteData.auto_push_code)


            delete.put("remark",deleteData.remark)
            //Log.d("GSON", "msg:${delete}")
            val body = FormBody.Builder()
                .add("username", cookie_data.username)
                .add("operation", operation)
                .add("action",cookie_data.Actions.DELETE)
                .add("data",delete.toString())
                .add("csrfmiddlewaretoken", cookie_data.tokenValue)
                .add("login_flag", cookie_data.loginflag)
                .build()
            val request = Request.Builder()
                .url(cookie_data.URL+"/def_management")
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
        private fun lockDef(operation:String,lockData:InvChangeTypeS) {
            val lock = JSONObject()
            lock.put("inv_code_s",lockData.inv_code_s)
            lock.put("inv_code_m",lockData.inv_code_m)
            lock.put("inv_name_s",lockData.inv_name_s)
            lock.put("is_inventory_plus",lockData.is_inventory_plus)
            lock.put("is_inventory_reduce",lockData.is_inventory_reduce)
            lock.put("is_not_affect",lockData.is_not_affect)
            lock.put("is_ok_product_warehouse",lockData.is_ok_product_warehouse)
            lock.put("is_ng_product_warehouse",lockData.is_ng_product_warehouse)
            lock.put("is_scrapped",lockData.is_scrapped)
            lock.put("auto_push_code",lockData.auto_push_code)


            lock.put("remark",lockData.remark)
            //Log.d("GSON", "msg:${delete}")
            val body = FormBody.Builder()
                .add("username", cookie_data.username)
                .add("operation", operation)
                .add("action",cookie_data.Actions.LOCK)
                .add("data",lock.toString())
                .add("csrfmiddlewaretoken", cookie_data.tokenValue)
                .add("login_flag", cookie_data.loginflag)
                .build()
            val request = Request.Builder()
                .url(cookie_data.URL+"/def_management")
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
    fun addItem(addData:InvChangeTypeS){
        data.add(itemData.count,addData)
        notifyItemInserted(itemData.count)
        itemData.count+=1
    }

}


