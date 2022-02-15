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


class RecyclerItemDMCILBodyAdapter() :
    RecyclerView.Adapter<RecyclerItemDMCILBodyAdapter.ViewHolder>() {
    private var itemData: ShowDMCILBody =Gson().fromJson(cookie_data.response_data, ShowDMCILBody::class.java)
    private var data: ArrayList<DMCILBody> =itemData.data

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemDMCILBodyAdapter.ViewHolder {
        val v= LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_dmcil_body,parent,false)
        return ViewHolder(v)

    }

    override fun onBindViewHolder(holder: RecyclerItemDMCILBodyAdapter.ViewHolder, position: Int) {

        //Log.d("GSON", "msg: ${itemData.data[position]}\n")
        holder.header_id.setText(data[position].header_id)
        holder.header_id.inputType=InputType.TYPE_NULL
        holder.code.setText(data[position].code)
        holder.code.inputType=InputType.TYPE_NULL
        holder.section.setText(data[position].section)
        holder.section.inputType=InputType.TYPE_NULL
        holder.outline.setText(data[position].outline)
        holder.outline.inputType=InputType.TYPE_NULL
        holder.dept.setText(data[position].dept)
        holder.dept.inputType=InputType.TYPE_NULL
        holder.context.setText(data[position].context)
        holder.context.inputType=InputType.TYPE_NULL
        holder.info_type.setText(data[position].info_type)
        holder.info_type.inputType=InputType.TYPE_NULL
        holder.item_id.setText(data[position].item_id)
        holder.item_id.inputType=InputType.TYPE_NULL




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
        var code=itemView.findViewById<TextInputEditText>(R.id.edit_code)
        var section=itemView.findViewById<TextInputEditText>(R.id.edit_section)
        var outline=itemView.findViewById<TextInputEditText>(R.id.edit_outline)
        var context=itemView.findViewById<TextInputEditText>(R.id.edit_context)
        var dept=itemView.findViewById<TextInputEditText>(R.id.edit_dept)
        var info_type=itemView.findViewById<TextInputEditText>(R.id.edit_info_type)
        var item_id=itemView.findViewById<TextInputEditText>(R.id.edit_item_id)


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
        lateinit var oldData:DMCILBody
        lateinit var newData:DMCILBody
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
                        //header_id.inputType=InputType.TYPE_CLASS_TEXT
                        //code.inputType=InputType.TYPE_CLASS_TEXT
                        section.inputType=InputType.TYPE_CLASS_TEXT
                        outline.inputType=InputType.TYPE_CLASS_TEXT
                        context.inputType=InputType.TYPE_CLASS_TEXT
                        dept.inputType=InputType.TYPE_CLASS_TEXT
                        info_type.inputType=InputType.TYPE_CLASS_TEXT
                        item_id.inputType=InputType.TYPE_CLASS_TEXT

                        remark.isClickable=true
                        remark.isFocusable=true
                        remark.isFocusableInTouchMode=true
                        remark.isTextInputLayoutFocusedRectEnabled=true
                        section.requestFocus()
                        edit_btn.text = "完成"
                    }
                    "完成"->{
                        newData= DMCILBody()
                        newData.header_id=header_id.text.toString()
                        header_id.inputType=InputType.TYPE_NULL
                        newData.code=code.text.toString()
                        code.inputType=InputType.TYPE_NULL
                        newData.section=section.text.toString()
                        section.inputType=InputType.TYPE_NULL
                        newData.outline=outline.text.toString()
                        outline.inputType=InputType.TYPE_NULL
                        newData.dept=dept.text.toString()
                        dept.inputType=InputType.TYPE_NULL
                        newData.context=context.text.toString()
                        context.inputType=InputType.TYPE_NULL
                        newData.info_type=info_type.text.toString()
                        info_type.inputType=InputType.TYPE_NULL
                        newData.item_id=item_id.text.toString()
                        item_id.inputType=InputType.TYPE_NULL


                        newData.remark=remark.text.toString()
                        remark.isClickable=false
                        remark.isFocusable=false
                        remark.isFocusableInTouchMode=false
                        remark.isTextInputLayoutFocusedRectEnabled=false
                        //Log.d("GSON", "msg: ${oldData}\n")
                        //Log.d("GSON", "msg: ${newData.remark}\n")
                        edit_DMCIL("DMCILBody",oldData,newData)//更改資料庫資料
                        when(cookie_data.status){
                            0-> {//成功
                                itemData.data[adapterPosition] = newData//更改渲染資料
                                editor.setText(cookie_data.username)
                                Toast.makeText(itemView.context, cookie_data.msg, Toast.LENGTH_SHORT).show()
                            }
                            1->{//失敗
                                Toast.makeText(itemView.context, cookie_data.msg, Toast.LENGTH_SHORT).show()
                                header_id.setText(oldData.header_id)
                                code.setText(oldData.code)
                                section.setText(oldData.section)
                                outline.setText(oldData.outline)
                                dept.setText(oldData.dept)
                                context.setText(oldData.context)
                                info_type.setText(oldData.info_type)
                                item_id.setText(oldData.item_id)


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
                    delete_DMCIL("DMCILBody",data[adapterPosition])
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
                    lock_DMCIL("DMCILBody",data[adapterPosition])
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
                    over_DMCIL("DMCILBody",data[adapterPosition])
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
        private fun edit_DMCIL(operation:String,oldData:DMCILBody,newData:DMCILBody) {
            val old =JSONObject()
            old.put("header_id",oldData.header_id)
            old.put("code",oldData.code)
            old.put("section",oldData.section)
            old.put("outline",oldData.outline)
            old.put("dept",oldData.dept)
            old.put("context",oldData.context)
            old.put("info_type",oldData.info_type)
            old.put("item_id",oldData.item_id)


            old.put("remark",oldData.remark)
            val new =JSONObject()
            new.put("header_id",newData.header_id)
            new.put("code",newData.code)
            new.put("section",newData.section)
            new.put("outline",newData.outline)
            new.put("dept",newData.dept)
            new.put("context",newData.context)
            new.put("info_type",newData.info_type)
            new.put("item_id",newData.item_id)


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
                .url(cookie_data.URL+"/dmcil_management")
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
        private fun delete_DMCIL(operation:String,deleteData:DMCILBody) {
            val delete = JSONObject()
            delete.put("header_id",deleteData.header_id)
            delete.put("code",deleteData.code)
            delete.put("section",deleteData.section)
            delete.put("outline",deleteData.outline)
            delete.put("dept",deleteData.dept)
            delete.put("context",deleteData.context)
            delete.put("info_type",deleteData.info_type)
            delete.put("item_id",deleteData.item_id)


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
                .url(cookie_data.URL+"/dmcil_management")
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
        private fun lock_DMCIL(operation:String,lockData:DMCILBody) {
            val lock = JSONObject()
            lock.put("header_id",lockData.header_id)
            lock.put("code",lockData.code)
            lock.put("section",lockData.section)
            lock.put("outline",lockData.outline)
            lock.put("dept",lockData.dept)
            lock.put("context",lockData.context)
            lock.put("info_type",lockData.info_type)
            lock.put("item_id",lockData.item_id)

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
                .url(cookie_data.URL+"/dmcil_management")
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
        private fun over_DMCIL(operation:String,overData:DMCILBody) {
            val over = JSONObject()
            over.put("header_id",overData.header_id)
            over.put("code",overData.code)
            over.put("section",overData.section)
            over.put("outline",overData.outline)
            over.put("dept",overData.dept)
            over.put("context",overData.context)
            over.put("info_type",overData.info_type)
            over.put("item_id",overData.item_id)

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
                .url(cookie_data.URL+"/dmcil_management")
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
    fun addItem(addData:DMCILBody){
        data.add(itemData.count,addData)
        notifyItemInserted(itemData.count)
        itemData.count+=1//cookie_data.itemCount+=1
    }

}


