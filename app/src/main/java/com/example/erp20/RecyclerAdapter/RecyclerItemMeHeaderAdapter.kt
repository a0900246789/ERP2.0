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


class RecyclerItemMeHeaderAdapter() :
    RecyclerView.Adapter<RecyclerItemMeHeaderAdapter.ViewHolder>() {
    private val itemData: ShowMeHeader =Gson().fromJson(cookie_data.response_data, ShowMeHeader::class.java)
    private var data: ArrayList<MeHeader> =itemData.data

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemMeHeaderAdapter.ViewHolder {
        val v= LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_me_header,parent,false)
        return ViewHolder(v)

    }

    override fun onBindViewHolder(holder: RecyclerItemMeHeaderAdapter.ViewHolder, position: Int) {

        //Log.d("GSON", "msg: ${itemData.data[position]}\n")
        holder._id.setText(data[position]._id)
        holder._id.inputType=InputType.TYPE_NULL
        holder.product_id.setText(data[position].product_id)
        holder.product_id.inputType=InputType.TYPE_NULL
        holder.theoretical_output.setText(data[position].theoretical_output.toString())
        holder.theoretical_output.inputType=InputType.TYPE_NULL
        holder.unit_of_timer.setText(data[position].unit_of_timer)
        holder.unit_of_timer.inputType=InputType.TYPE_NULL
        holder.issue_date.setText(data[position].issue_date)
        holder.issue_date.inputType=InputType.TYPE_NULL
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
        var product_id=itemView.findViewById<TextInputEditText>(R.id.edit_product_id)
        var theoretical_output=itemView.findViewById<TextInputEditText>(R.id.edit_theoretical_output)
        var unit_of_timer=itemView.findViewById<TextInputEditText>(R.id.edit_unit_of_timer)
        var issue_date=itemView.findViewById<TextInputEditText>(R.id.edit_issue_date)
        var item_id=itemView.findViewById<TextInputEditText>(R.id.edit_item_id)


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
        lateinit var oldData:MeHeader
        lateinit var newData:MeHeader
        init {
            itemView.setOnClickListener{
                val position:Int=adapterPosition
                Log.d("GSON", "msg: ${position}\n")

            }


            //編輯按鈕
            edit_btn.setOnClickListener {
                when(edit_btn.text){
                    "編輯"->{
                        oldData=itemData.data[adapterPosition]
                        //Log.d("GSON", "msg: ${oldData.id}\n")
                        _id.inputType=InputType.TYPE_CLASS_TEXT
                        product_id.inputType=InputType.TYPE_CLASS_TEXT
                        theoretical_output.inputType=InputType.TYPE_CLASS_NUMBER
                        unit_of_timer.inputType=InputType.TYPE_CLASS_TEXT
                        issue_date.inputType=InputType.TYPE_DATETIME_VARIATION_DATE
                        item_id.inputType=InputType.TYPE_CLASS_TEXT

                        remark.isClickable=true
                        remark.isFocusable=true
                        remark.isFocusableInTouchMode=true
                        remark.isTextInputLayoutFocusedRectEnabled=true
                        _id.requestFocus()
                        edit_btn.text = "完成"
                    }
                    "完成"->{
                        newData= MeHeader()
                        newData._id=_id.text.toString()
                        _id.inputType=InputType.TYPE_NULL
                        newData.product_id=product_id.text.toString()
                        product_id.inputType=InputType.TYPE_NULL
                        newData.theoretical_output=theoretical_output.text.toString().toInt()
                        theoretical_output.inputType=InputType.TYPE_NULL
                        newData.unit_of_timer=unit_of_timer.text.toString()
                        unit_of_timer.inputType=InputType.TYPE_NULL
                        newData.issue_date=issue_date.text.toString()
                        issue_date.inputType=InputType.TYPE_NULL
                        newData.item_id=item_id.text.toString()
                        item_id.inputType=InputType.TYPE_NULL


                        newData.remark=remark.text.toString()
                        remark.isClickable=false
                        remark.isFocusable=false
                        remark.isFocusableInTouchMode=false
                        remark.isTextInputLayoutFocusedRectEnabled=false
                        //Log.d("GSON", "msg: ${oldData}\n")
                        //Log.d("GSON", "msg: ${newData.remark}\n")
                        edit_S_Basic("Me","header",oldData,newData)//更改資料庫資料
                        when(cookie_data.status){
                            0-> {//成功
                                itemData.data[adapterPosition] = newData//更改渲染資料
                                editor.setText(cookie_data.username)
                                Toast.makeText(itemView.context, cookie_data.msg, Toast.LENGTH_SHORT).show()
                            }
                            1->{//失敗
                                Toast.makeText(itemView.context, cookie_data.msg, Toast.LENGTH_SHORT).show()
                                _id.setText(oldData._id)
                                product_id.setText(oldData.product_id)
                                theoretical_output.setText(oldData.theoretical_output.toString())
                                unit_of_timer.setText(oldData.unit_of_timer)
                                issue_date.setText(oldData.issue_date)
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
                    delete_S_Basic("Me","header",data[adapterPosition])
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
                    lock_S_Basic("Me","header",data[adapterPosition])
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
        private fun edit_S_Basic(operation:String,target:String,oldData:MeHeader,newData:MeHeader) {
            val old =JSONObject()
            old.put("_id",oldData._id)
            old.put("product_id",oldData.product_id)
            old.put("theoretical_output",oldData.theoretical_output)
            old.put("unit_of_timer",oldData.unit_of_timer)
            old.put("issue_date",oldData.issue_date)
            old.put("item_id",oldData.item_id)

            old.put("remark",oldData.remark)
            val new =JSONObject()
            new.put("_id",newData._id)
            new.put("product_id",newData.product_id)
            new.put("theoretical_output",newData.theoretical_output)
            new.put("unit_of_timer",newData.unit_of_timer)
            new.put("issue_date",newData.issue_date)
            new.put("item_id",newData.item_id)

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
                .add("target",target)
                .add("action","2")
                .add("csrfmiddlewaretoken", cookie_data.tokenValue)
                .add("login_flag", cookie_data.loginflag)
                .build()
            val request = Request.Builder()
                .url(cookie_data.URL+"/special_basic_management")
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
        private fun delete_S_Basic(operation:String,target:String,deleteData:MeHeader) {
            val delete = JSONObject()
            delete.put("_id",deleteData._id)
            delete.put("product_id",deleteData.product_id)
            delete.put("theoretical_output",deleteData.theoretical_output)
            delete.put("unit_of_timer",deleteData.unit_of_timer)
            delete.put("issue_date",deleteData.issue_date)
            delete.put("item_id",deleteData.item_id)

            delete.put("remark",deleteData.remark)
            //Log.d("GSON", "msg:${delete}")
            val body = FormBody.Builder()
                .add("card_number",cookie_data.card_number)
                .add("username", cookie_data.username)
                .add("operation", operation)
                .add("target", target)
                .add("action","3")
                .add("data",delete.toString())
                .add("csrfmiddlewaretoken", cookie_data.tokenValue)
                .add("login_flag", cookie_data.loginflag)
                .build()
            val request = Request.Builder()
                .url(cookie_data.URL+"/special_basic_management")
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
        private fun lock_S_Basic(operation:String,target:String,lockData:MeHeader) {
            val lock = JSONObject()
            lock.put("_id",lockData._id)
            lock.put("product_id",lockData.product_id)
            lock.put("theoretical_output",lockData.theoretical_output)
            lock.put("unit_of_timer",lockData.unit_of_timer)
            lock.put("issue_date",lockData.issue_date)
            lock.put("item_id",lockData.item_id)

            lock.put("remark",lockData.remark)
            //Log.d("GSON", "msg:${delete}")
            val body = FormBody.Builder()
                .add("card_number",cookie_data.card_number)
                .add("username", cookie_data.username)
                .add("operation", operation)
                .add("target", target)
                .add("action","4")
                .add("data",lock.toString())
                .add("csrfmiddlewaretoken", cookie_data.tokenValue)
                .add("login_flag", cookie_data.loginflag)
                .build()
            val request = Request.Builder()
                .url(cookie_data.URL+"/special_basic_management")
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
    fun addItem(addData:MeHeader){
        data.add(itemData.count,addData)
        notifyItemInserted(itemData.count)
        itemData.count+=1
    }

}


