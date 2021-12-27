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
import androidx.appcompat.app.AppCompatActivity
import com.example.erp20.PopUpWindow.QrCodeFragment


class RecyclerItemstaffBasicInfoAdapter() :
    RecyclerView.Adapter<RecyclerItemstaffBasicInfoAdapter.ViewHolder>() {
    private var itemData: ShowstaffBasicInfo =Gson().fromJson(cookie_data.response_data, ShowstaffBasicInfo::class.java)
    private var data: ArrayList<staffBasicInfo> =itemData.data

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemstaffBasicInfoAdapter.ViewHolder {
        val v= LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_staff_basic_info,parent,false)
        return ViewHolder(v)

    }

    override fun onBindViewHolder(holder: RecyclerItemstaffBasicInfoAdapter.ViewHolder, position: Int) {

        //Log.d("GSON", "msg: ${itemData.data[position]}\n")
        holder.card_number.setText(data[position].card_number)
        holder.card_number.inputType=InputType.TYPE_NULL
        holder.password.setText(data[position].password)
        holder.password.inputType=InputType.TYPE_NULL
        holder.name.setText(data[position].name)
        holder.name.inputType=InputType.TYPE_NULL
        holder.dept.setText(data[position].dept)
        holder.dept.inputType=InputType.TYPE_NULL
        holder.position.setText(data[position].position)
        holder.position.inputType=InputType.TYPE_NULL
        holder.is_work.setText(data[position].is_work.toString())
        holder.is_work.isClickable=false
        holder.is_work.inputType=InputType.TYPE_NULL
        holder.date_of_employment.setText(data[position].date_of_employment)
        holder.date_of_employment.inputType=InputType.TYPE_NULL
        holder.date_of_resignation.setText(data[position].date_of_resignation)
        holder.date_of_resignation.inputType=InputType.TYPE_NULL
        holder.skill_code.setText(data[position].skill_code)
        holder.skill_code.inputType=InputType.TYPE_NULL
        holder.skill_rank.setText(data[position].skill_rank)
        holder.skill_rank.inputType=InputType.TYPE_NULL
        holder.qr_code.setText(data[position].qr_code)
        holder.qr_code.inputType=InputType.TYPE_NULL




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
        return itemData.count//cookie_data.itemCount
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var card_number=itemView.findViewById<TextInputEditText>(R.id.edit_card_number)
        var password=itemView.findViewById<TextInputEditText>(R.id.edit_password)
        var name=itemView.findViewById<TextInputEditText>(R.id.edit_name)
        var dept=itemView.findViewById<TextInputEditText>(R.id.edit_dept)
        var position=itemView.findViewById<TextInputEditText>(R.id.edit_position)
        var is_work=itemView.findViewById<AutoCompleteTextView>(R.id.edit_is_work)
        var date_of_employment=itemView.findViewById<TextInputEditText>(R.id.edit_date_of_employment)
        var date_of_resignation=itemView.findViewById<TextInputEditText>(R.id.edit_date_of_resignation)
        var skill_code=itemView.findViewById<TextInputEditText>(R.id.edit_skill_code)
        var skill_rank=itemView.findViewById<TextInputEditText>(R.id.edit_skill_rank)
        var qr_code=itemView.findViewById<TextInputEditText>(R.id.edit_qr_code)

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
        lateinit var oldData:staffBasicInfo
        lateinit var newData:staffBasicInfo
        init {
            itemView.setOnClickListener{
                val position:Int=adapterPosition
                Log.d("GSON", "msg: ${position}\n")

            }

            val combobox=itemView.resources.getStringArray(R.array.combobox_yes_no)
            val arrayAdapter= ArrayAdapter(itemView.context,R.layout.combobox_item,combobox)
            var qrcodeFlag=false

            //QRCode
            qr_code.setOnClickListener {
                when(qrcodeFlag){
                    true->{
                        var dialogF= QrCodeFragment()
                        dialogF.show( ( itemView.context as AppCompatActivity).supportFragmentManager ,"Qrcode")
                        cookie_data.currentAdapter="edit"
                        //Log.d("GSON", "msg:${dialogF.data}")
                    }
                    false->{

                    }
                }
            }


            //編輯按鈕
            edit_btn.setOnClickListener {
                when(edit_btn.text){
                    "編輯"->{
                        cookie_data.itemposition=adapterPosition
                        oldData=itemData.data[adapterPosition]
                        //Log.d("GSON", "msg: ${oldData.id}\n")
                        card_number.inputType=InputType.TYPE_CLASS_TEXT
                        password.inputType=InputType.TYPE_CLASS_TEXT
                        name.inputType=InputType.TYPE_CLASS_TEXT
                        dept.inputType=InputType.TYPE_CLASS_TEXT
                        position.inputType=InputType.TYPE_CLASS_TEXT
                        is_work.setAdapter(arrayAdapter)
                        is_work.isClickable=true
                        date_of_employment.inputType=InputType.TYPE_DATETIME_VARIATION_DATE
                        date_of_resignation.inputType=InputType.TYPE_DATETIME_VARIATION_DATE
                        skill_code.inputType=InputType.TYPE_CLASS_TEXT
                        skill_rank.inputType=InputType.TYPE_CLASS_TEXT
                        //qr_code.inputType=InputType.TYPE_CLASS_TEXT
                        qrcodeFlag=true

                        remark.isClickable=true
                        remark.isFocusable=true
                        remark.isFocusableInTouchMode=true
                        remark.isTextInputLayoutFocusedRectEnabled=true
                        card_number.requestFocus()
                        edit_btn.text = "完成"
                    }
                    "完成"->{
                        newData= staffBasicInfo()
                        newData.card_number=card_number.text.toString()
                        card_number.inputType=InputType.TYPE_NULL
                        newData.password=password.text.toString()
                        password.inputType=InputType.TYPE_NULL
                        newData.name=name.text.toString()
                        name.inputType=InputType.TYPE_NULL
                        newData.dept=dept.text.toString()
                        dept.inputType=InputType.TYPE_NULL
                        newData.position=position.text.toString()
                        position.inputType=InputType.TYPE_NULL
                        newData.is_work=is_work.text.toString().toBoolean()
                        is_work.isClickable=false
                        is_work.setAdapter(null)
                        newData.date_of_employment=date_of_employment.text.toString()
                        date_of_employment.inputType=InputType.TYPE_NULL
                        newData.date_of_resignation=date_of_resignation.text.toString()
                        date_of_resignation.inputType=InputType.TYPE_NULL
                        newData.skill_code=skill_code.text.toString()
                        skill_code.inputType=InputType.TYPE_NULL
                        newData.skill_rank=skill_rank.text.toString()
                        skill_rank.inputType=InputType.TYPE_NULL
                        newData.qr_code=qr_code.text.toString()
                        qr_code.inputType=InputType.TYPE_NULL
                        qrcodeFlag=false

                        newData.remark=remark.text.toString()
                        remark.isClickable=false
                        remark.isFocusable=false
                        remark.isFocusableInTouchMode=false
                        remark.isTextInputLayoutFocusedRectEnabled=false
                        //Log.d("GSON", "msg: ${oldData}\n")
                        //Log.d("GSON", "msg: ${newData.remark}\n")
                        edit_Staff_Basic(oldData,newData)//更改資料庫資料
                        when(cookie_data.status){
                            0-> {//成功
                                itemData.data[adapterPosition] = newData//更改渲染資料
                                editor.setText(cookie_data.username)
                                Toast.makeText(itemView.context, cookie_data.msg, Toast.LENGTH_SHORT).show()
                            }
                            1->{//失敗
                                Toast.makeText(itemView.context, cookie_data.msg, Toast.LENGTH_SHORT).show()
                                card_number.setText(oldData.card_number)
                                password.setText(oldData.password)
                                name.setText(oldData.name)
                                dept.setText(oldData.dept)
                                position.setText(oldData.position)
                                is_work.setText(oldData.is_work.toString())
                                date_of_employment.setText(oldData.date_of_employment)
                                date_of_resignation.setText(oldData.date_of_resignation)
                                skill_code.setText(oldData.skill_code)
                                skill_rank.setText(oldData.skill_rank)
                                qr_code.setText(oldData.qr_code)


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
                    delete_Staff_Basic(data[adapterPosition])
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
                    lock_Staff_Basic(data[adapterPosition])
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
        private fun edit_Staff_Basic(oldData:staffBasicInfo,newData:staffBasicInfo) {
            val old =JSONObject()
            old.put("card_number",oldData.card_number)
            old.put("password",oldData.password)
            old.put("name",oldData.name)
            old.put("dept",oldData.dept)
            old.put("position",oldData.position)
            old.put("is_work",oldData.is_work)
            old.put("date_of_employment",oldData.date_of_employment)
            old.put("date_of_resignation",oldData.date_of_resignation)
            old.put("skill_code",oldData.skill_code)
            old.put("skill_rank",oldData.skill_rank)
            old.put("qr_code",oldData.qr_code)

            old.put("remark",oldData.remark)
            val new =JSONObject()
            new.put("card_number",newData.card_number)
            new.put("password",newData.password)
            new.put("name",newData.name)
            new.put("dept",newData.dept)
            new.put("position",newData.position)
            new.put("is_work",newData.is_work)
            new.put("date_of_employment",newData.date_of_employment)
            new.put("date_of_resignation",newData.date_of_resignation)
            new.put("skill_code",newData.skill_code)
            new.put("skill_rank",newData.skill_rank)
            new.put("qr_code",newData.qr_code)

            new.put("editor",cookie_data.username)
            new.put("remark",newData.remark)
            val data = JSONArray()
            data.put(old)
            data.put((new))
            val body = FormBody.Builder()
                .add("data",data.toString())
                .add("username", cookie_data.username)
                .add("action","2")
                .add("csrfmiddlewaretoken", cookie_data.tokenValue)
                .add("login_flag", cookie_data.loginflag)
                .build()
            val request = Request.Builder()
                .url("http://140.125.46.125:8000/staff_management")
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
        private fun delete_Staff_Basic(deleteData:staffBasicInfo) {
            val delete = JSONObject()
            delete.put("card_number",deleteData.card_number)
            delete.put("password",deleteData.password)
            delete.put("name",deleteData.name)
            delete.put("dept",deleteData.dept)
            delete.put("position",deleteData.position)
            delete.put("is_work",deleteData.is_work)
            delete.put("date_of_employment",deleteData.date_of_employment)
            delete.put("date_of_resignation",deleteData.date_of_resignation)
            delete.put("skill_code",deleteData.skill_code)
            delete.put("skill_rank",deleteData.skill_rank)
            delete.put("qr_code",deleteData.qr_code)

            delete.put("remark",deleteData.remark)
            //Log.d("GSON", "msg:${delete}")
            val body = FormBody.Builder()
                .add("username", cookie_data.username)
                .add("action","3")
                .add("data",delete.toString())
                .add("csrfmiddlewaretoken", cookie_data.tokenValue)
                .add("login_flag", cookie_data.loginflag)
                .build()
            val request = Request.Builder()
                .url("http://140.125.46.125:8000/staff_management")
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
        private fun lock_Staff_Basic(lockData:staffBasicInfo) {
            val lock = JSONObject()
            lock.put("card_number",lockData.card_number)
            lock.put("password",lockData.password)
            lock.put("name",lockData.name)
            lock.put("dept",lockData.dept)
            lock.put("position",lockData.position)
            lock.put("is_work",lockData.is_work)
            lock.put("date_of_employment",lockData.date_of_employment)
            lock.put("date_of_resignation",lockData.date_of_resignation)
            lock.put("skill_code",lockData.skill_code)
            lock.put("skill_rank",lockData.skill_rank)
            lock.put("qr_code",lockData.qr_code)

            lock.put("remark",lockData.remark)
            //Log.d("GSON", "msg:${delete}")
            val body = FormBody.Builder()
                .add("username", cookie_data.username)
                .add("action","4")
                .add("data",lock.toString())
                .add("csrfmiddlewaretoken", cookie_data.tokenValue)
                .add("login_flag", cookie_data.loginflag)
                .build()
            val request = Request.Builder()
                .url("http://140.125.46.125:8000/staff_management")
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
    fun addItem(addData:staffBasicInfo){
        data.add(itemData.count,addData)
        notifyItemInserted(itemData.count)
        itemData.count+=1//cookie_data.itemCount+=1
    }

}


