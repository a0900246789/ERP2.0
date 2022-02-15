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


class RecyclerItemPLineBasicInfoAdapter() :
    RecyclerView.Adapter<RecyclerItemPLineBasicInfoAdapter.ViewHolder>() {
    private val itemData: ShowPLineBasicInfo =Gson().fromJson(cookie_data.response_data, ShowPLineBasicInfo::class.java)
    private var data: ArrayList<PLineBasicInfo> =itemData.data

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemPLineBasicInfoAdapter.ViewHolder {
        val v= LayoutInflater.from(parent.context).inflate(R.layout.recycler_item__p_line_basic_info,parent,false)
        return ViewHolder(v)

    }

    override fun onBindViewHolder(holder: RecyclerItemPLineBasicInfoAdapter.ViewHolder, position: Int) {

        //Log.d("GSON", "msg: ${itemData.data[position]}\n")
        holder._id.setText(data[position]._id)
        holder._id.inputType=InputType.TYPE_NULL
        holder.name.setText(data[position].name)
        holder.name.inputType=InputType.TYPE_NULL
        holder.is_selfmade.setText(data[position].is_selfmade.toString())
        holder.is_selfmade.isClickable=false
        holder.is_selfmade.inputType=InputType.TYPE_NULL
        holder.is_outsourcing.setText(data[position].is_outsourcing.toString())
        holder.is_outsourcing.isClickable=false
        holder.is_outsourcing.inputType=InputType.TYPE_NULL
        holder.vender_id.setText(data[position].vender_id)
        holder.vender_id.inputType=InputType.TYPE_NULL




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
        var name=itemView.findViewById<TextInputEditText>(R.id.edit_name)
        var is_selfmade=itemView.findViewById<AutoCompleteTextView>(R.id.edit_is_selfmade)
        var is_outsourcing=itemView.findViewById<AutoCompleteTextView>(R.id.edit_is_outsourcing)
        var vender_id=itemView.findViewById<TextInputEditText>(R.id.edit_vender_id)

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
        lateinit var oldData:PLineBasicInfo
        lateinit var newData:PLineBasicInfo
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
                        name.inputType=InputType.TYPE_CLASS_TEXT
                        is_selfmade.setAdapter(arrayAdapter)
                        is_selfmade.isClickable=true
                        is_outsourcing.setAdapter(arrayAdapter)
                        is_outsourcing.isClickable=true
                        vender_id.inputType=InputType.TYPE_CLASS_TEXT

                        remark.isClickable=true
                        remark.isFocusable=true
                        remark.isFocusableInTouchMode=true
                        remark.isTextInputLayoutFocusedRectEnabled=true
                        _id.requestFocus()
                        edit_btn.text = "完成"
                    }
                    "完成"->{
                        newData= PLineBasicInfo()
                        newData._id=_id.text.toString()
                        _id.inputType=InputType.TYPE_NULL
                        newData.name=name.text.toString()
                        name.inputType=InputType.TYPE_NULL
                        newData.is_selfmade=is_selfmade.text.toString().toBoolean()
                        is_selfmade.isClickable=false
                        is_selfmade.setAdapter(null)
                        newData.is_outsourcing=is_outsourcing.text.toString().toBoolean()
                        is_outsourcing.isClickable=false
                        is_outsourcing.setAdapter(null)
                        newData.vender_id=vender_id.text.toString()
                        vender_id.inputType=InputType.TYPE_NULL


                        newData.remark=remark.text.toString()
                        remark.isClickable=false
                        remark.isFocusable=false
                        remark.isFocusableInTouchMode=false
                        remark.isTextInputLayoutFocusedRectEnabled=false
                        //Log.d("GSON", "msg: ${oldData}\n")
                        //Log.d("GSON", "msg: ${newData.remark}\n")
                        editBasic("PLineBasicInfo",oldData,newData)//更改資料庫資料
                        when(cookie_data.status){
                            0-> {//成功
                                itemData.data[adapterPosition] = newData//更改渲染資料
                                editor.setText(cookie_data.username)
                                Toast.makeText(itemView.context, cookie_data.msg, Toast.LENGTH_SHORT).show()
                            }
                            1->{//失敗
                                Toast.makeText(itemView.context, cookie_data.msg, Toast.LENGTH_SHORT).show()
                                _id.setText(oldData._id)
                                name.setText(oldData.name)
                                is_selfmade.setText(oldData.is_selfmade.toString())
                                is_outsourcing.setText(oldData.is_outsourcing.toString())
                                vender_id.setText(oldData.vender_id)


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
                    deleteBasic("PLineBasicInfo",data[adapterPosition])
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
                    lockBasic("PLineBasicInfo",data[adapterPosition])
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
        private fun editBasic(operation:String,oldData:PLineBasicInfo,newData:PLineBasicInfo) {
            val old =JSONObject()
            old.put("_id",oldData._id)
            old.put("name",oldData.name)
            old.put("is_selfmade",oldData.is_selfmade)
            old.put("is_outsourcing",oldData.is_outsourcing)
            old.put("vender_id",oldData.vender_id)
            old.put("remark",oldData.remark)
            val new =JSONObject()
            new.put("_id",newData._id)
            new.put("name",newData.name)
            new.put("is_selfmade",newData.is_selfmade)
            new.put("is_outsourcing",newData.is_outsourcing)
            new.put("vender_id",newData.vender_id)
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
        private fun deleteBasic(operation:String,deleteData:PLineBasicInfo) {
            val delete = JSONObject()
            delete.put("_id",deleteData._id)
            delete.put("name",deleteData.name)
            delete.put("is_selfmade",deleteData.is_selfmade)
            delete.put("is_outsourcing",deleteData.is_outsourcing)
            delete.put("vender_id",deleteData.vender_id)
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
        private fun lockBasic(operation:String,lockData:PLineBasicInfo) {
            val lock = JSONObject()
            lock.put("_id",lockData._id)
            lock.put("name",lockData.name)
            lock.put("is_selfmade",lockData.is_selfmade)
            lock.put("is_outsourcing",lockData.is_outsourcing)
            lock.put("vender_id",lockData.vender_id)
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
    fun addItem(addData:PLineBasicInfo){
        data.add(itemData.count,addData)
        notifyItemInserted(itemData.count)
        itemData.count+=1
    }

}


