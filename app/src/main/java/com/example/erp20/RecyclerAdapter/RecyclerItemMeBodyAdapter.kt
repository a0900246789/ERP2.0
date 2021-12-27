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


class RecyclerItemMeBodyAdapter() :
    RecyclerView.Adapter<RecyclerItemMeBodyAdapter.ViewHolder>() {
    private val itemData: ShowMeBody =Gson().fromJson(cookie_data.response_data, ShowMeBody::class.java)
    private var data: ArrayList<MeBody> =itemData.data

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemMeBodyAdapter.ViewHolder {
        val v= LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_me_body,parent,false)
        return ViewHolder(v)

    }

    override fun onBindViewHolder(holder: RecyclerItemMeBodyAdapter.ViewHolder, position: Int) {

        //Log.d("GSON", "msg: ${itemData.data[position]}\n")
        holder._id.setText(data[position]._id)
        holder._id.inputType=InputType.TYPE_NULL
        holder.process_number.setText(data[position].process_number)
        holder.process_number.inputType=InputType.TYPE_NULL
        holder.processing_sequence.setText(data[position].processing_sequence)
        holder.processing_sequence.inputType=InputType.TYPE_NULL
        holder.process_code.setText(data[position].process_code)
        holder.process_code.inputType=InputType.TYPE_NULL
        holder.item_id.setText(data[position].item_id)
        holder.item_id.inputType=InputType.TYPE_NULL
        holder.pline_id.setText(data[position].pline_id)
        holder.pline_id.inputType=InputType.TYPE_NULL
        holder.standard_working_hours_optimal.setText(data[position].standard_working_hours_optimal.toString())
        holder.standard_working_hours_optimal.inputType=InputType.TYPE_NULL
        holder.unit_of_timer_optimal.setText(data[position].unit_of_timer_optimal)
        holder.unit_of_timer_optimal.inputType=InputType.TYPE_NULL
        holder.number_of_staff_optimal.setText(data[position].number_of_staff_optimal)
        holder.number_of_staff_optimal.inputType=InputType.TYPE_NULL
        holder.theoretical_output_optimal.setText(data[position].theoretical_output_optimal.toString())
        holder.theoretical_output_optimal.inputType=InputType.TYPE_NULL
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
        var _id=itemView.findViewById<TextInputEditText>(R.id.edit_id)
        var process_number=itemView.findViewById<TextInputEditText>(R.id.edit_process_number)
        var processing_sequence=itemView.findViewById<TextInputEditText>(R.id.edit_processing_sequence)
        var process_code=itemView.findViewById<TextInputEditText>(R.id.edit_process_code)
        var item_id=itemView.findViewById<TextInputEditText>(R.id.edit_item_id)
        var pline_id=itemView.findViewById<TextInputEditText>(R.id.edit_pline_id)
        var standard_working_hours_optimal=itemView.findViewById<TextInputEditText>(R.id.edit_standard_working_hours_optimal)
        var unit_of_timer_optimal=itemView.findViewById<TextInputEditText>(R.id.edit_unit_of_timer_optimal)
        var number_of_staff_optimal=itemView.findViewById<TextInputEditText>(R.id.edit_number_of_staff_optimal)
        var theoretical_output_optimal=itemView.findViewById<TextInputEditText>(R.id.edit_theoretical_output_optimal)
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
        lateinit var oldData:MeBody
        lateinit var newData:MeBody
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
                        _id.inputType=InputType.TYPE_NULL
                        process_number.inputType=InputType.TYPE_CLASS_TEXT
                        processing_sequence.inputType=InputType.TYPE_CLASS_TEXT
                        process_code.inputType=InputType.TYPE_CLASS_TEXT
                        item_id.inputType=InputType.TYPE_CLASS_TEXT
                        pline_id.inputType=InputType.TYPE_CLASS_TEXT
                        standard_working_hours_optimal.inputType=InputType.TYPE_CLASS_NUMBER
                        unit_of_timer_optimal.inputType=InputType.TYPE_CLASS_TEXT
                        number_of_staff_optimal.inputType=InputType.TYPE_CLASS_TEXT
                        theoretical_output_optimal.inputType=InputType.TYPE_CLASS_NUMBER
                        standard_working_hours_less.inputType=InputType.TYPE_CLASS_NUMBER
                        unit_of_timer_less.inputType=InputType.TYPE_CLASS_TEXT
                        number_of_staff_less.inputType=InputType.TYPE_CLASS_TEXT
                        theoretical_output_less.inputType=InputType.TYPE_CLASS_NUMBER

                        remark.isClickable=true
                        remark.isFocusable=true
                        remark.isFocusableInTouchMode=true
                        remark.isTextInputLayoutFocusedRectEnabled=true
                        process_number.requestFocus()
                        edit_btn.text = "完成"
                    }
                    "完成"->{
                        newData= MeBody()
                        newData._id=_id.text.toString()
                        _id.inputType=InputType.TYPE_NULL
                        newData.process_number=process_number.text.toString()
                        process_number.inputType=InputType.TYPE_NULL
                        newData.processing_sequence=processing_sequence.text.toString()
                        processing_sequence.inputType=InputType.TYPE_NULL
                        newData.process_code=process_code.text.toString()
                        process_code.inputType=InputType.TYPE_NULL
                        newData.item_id=item_id.text.toString()
                        item_id.inputType=InputType.TYPE_NULL
                        newData.pline_id=pline_id.text.toString()
                        pline_id.inputType=InputType.TYPE_NULL
                        newData.standard_working_hours_optimal=standard_working_hours_optimal.text.toString().toInt()
                        standard_working_hours_optimal.inputType=InputType.TYPE_NULL
                        newData.unit_of_timer_optimal=unit_of_timer_optimal.text.toString()
                        unit_of_timer_optimal.inputType=InputType.TYPE_NULL
                        newData.number_of_staff_optimal=number_of_staff_optimal.text.toString()
                        number_of_staff_optimal.inputType=InputType.TYPE_NULL
                        newData.theoretical_output_optimal=theoretical_output_optimal.text.toString().toInt()
                        theoretical_output_optimal.inputType=InputType.TYPE_NULL
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
                        edit_S_Basic("Me","body",oldData,newData)//更改資料庫資料
                        when(cookie_data.status){
                            0-> {//成功
                                itemData.data[adapterPosition] = newData//更改渲染資料
                                editor.setText(cookie_data.username)
                                Toast.makeText(itemView.context, cookie_data.msg, Toast.LENGTH_SHORT).show()
                            }
                            1->{//失敗
                                Toast.makeText(itemView.context, cookie_data.msg, Toast.LENGTH_SHORT).show()
                                _id.setText(oldData._id)
                                process_number.setText(oldData.process_number)
                                processing_sequence.setText(oldData.processing_sequence)
                                process_code.setText(oldData.process_code)
                                item_id.setText(oldData.item_id)
                                pline_id.setText(oldData.pline_id)
                                standard_working_hours_optimal.setText(oldData.standard_working_hours_optimal.toString())
                                unit_of_timer_optimal.setText(oldData.unit_of_timer_optimal)
                                number_of_staff_optimal.setText(oldData.number_of_staff_optimal)
                                theoretical_output_optimal.setText(oldData.theoretical_output_optimal.toString())
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
                    delete_S_Basic("Me","body",data[adapterPosition])
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
                    lock_S_Basic("Me","body",data[adapterPosition])
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
        private fun edit_S_Basic(operation:String,target:String,oldData:MeBody,newData:MeBody) {
            val old =JSONObject()
            old.put("_id",oldData._id)
            old.put("process_number",oldData.process_number)
            old.put("processing_sequence",oldData.processing_sequence)
            old.put("process_code",oldData.process_code)
            old.put("item_id",oldData.item_id)
            old.put("pline_id",oldData.pline_id)
            old.put("standard_working_hours_optimal",oldData.standard_working_hours_optimal)
            old.put("unit_of_timer_optimal",oldData.unit_of_timer_optimal)
            old.put("number_of_staff_optimal",oldData.number_of_staff_optimal)
            old.put("theoretical_output_optimal",oldData.theoretical_output_optimal)
            old.put("standard_working_hours_less",oldData.standard_working_hours_less)
            old.put("unit_of_timer_less",oldData.unit_of_timer_less)
            old.put("number_of_staff_less",oldData.number_of_staff_less)
            old.put("theoretical_output_less",oldData.theoretical_output_less)

            old.put("remark",oldData.remark)
            val new =JSONObject()
            new.put("_id",newData._id)
            new.put("process_number",newData.process_number)
            new.put("processing_sequence",newData.processing_sequence)
            new.put("process_code",newData.process_code)
            new.put("item_id",newData.item_id)
            new.put("pline_id",newData.pline_id)
            new.put("standard_working_hours_optimal",newData.standard_working_hours_optimal)
            new.put("unit_of_timer_optimal",newData.unit_of_timer_optimal)
            new.put("number_of_staff_optimal",newData.number_of_staff_optimal)
            new.put("theoretical_output_optimal",newData.theoretical_output_optimal)
            new.put("standard_working_hours_less",newData.standard_working_hours_less)
            new.put("unit_of_timer_less",newData.unit_of_timer_less)
            new.put("number_of_staff_less",newData.number_of_staff_less)
            new.put("theoretical_output_less",newData.theoretical_output_less)

            new.put("remark",newData.remark)
            new.put("editor",cookie_data.username)
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
                .url("http://140.125.46.125:8000/special_basic_management")
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
        private fun delete_S_Basic(operation:String,target:String,deleteData:MeBody) {
            val delete = JSONObject()
            delete.put("_id",deleteData._id)
            delete.put("process_number",deleteData.process_number)
            delete.put("processing_sequence",deleteData.processing_sequence)
            delete.put("process_code",deleteData.process_code)
            delete.put("item_id",deleteData.item_id)
            delete.put("pline_id",deleteData.pline_id)
            delete.put("standard_working_hours_optimal",deleteData.standard_working_hours_optimal)
            delete.put("unit_of_timer_optimal",deleteData.unit_of_timer_optimal)
            delete.put("number_of_staff_optimal",deleteData.number_of_staff_optimal)
            delete.put("theoretical_output_optimal",deleteData.theoretical_output_optimal)
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
                .add("target", target)
                .add("action","3")
                .add("data",delete.toString())
                .add("csrfmiddlewaretoken", cookie_data.tokenValue)
                .add("login_flag", cookie_data.loginflag)
                .build()
            val request = Request.Builder()
                .url("http://140.125.46.125:8000/special_basic_management")
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
        private fun lock_S_Basic(operation:String,target:String,lockData:MeBody) {
            val lock = JSONObject()
            lock.put("_id",lockData._id)
            lock.put("process_number",lockData.process_number)
            lock.put("processing_sequence",lockData.processing_sequence)
            lock.put("process_code",lockData.process_code)
            lock.put("item_id",lockData.item_id)
            lock.put("pline_id",lockData.pline_id)
            lock.put("standard_working_hours_optimal",lockData.standard_working_hours_optimal)
            lock.put("unit_of_timer_optimal",lockData.unit_of_timer_optimal)
            lock.put("number_of_staff_optimal",lockData.number_of_staff_optimal)
            lock.put("theoretical_output_optimal",lockData.theoretical_output_optimal)
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
                .add("target", target)
                .add("action","4")
                .add("data",lock.toString())
                .add("csrfmiddlewaretoken", cookie_data.tokenValue)
                .add("login_flag", cookie_data.loginflag)
                .build()
            val request = Request.Builder()
                .url("http://140.125.46.125:8000/special_basic_management")
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
    fun addItem(addData:MeBody){
        data.add(itemData.count,addData)
        notifyItemInserted(itemData.count)
        itemData.count+=1
    }

}


