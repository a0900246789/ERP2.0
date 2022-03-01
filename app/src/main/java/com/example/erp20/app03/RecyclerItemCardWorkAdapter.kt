package com.example.erp20.RecyclerAdapter
import android.content.Context
import android.content.DialogInterface
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
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.erp20.Model.*
import com.example.erp20.PopUpWindow.QrCodeFragment
import com.example.erp20.R
import com.example.erp20.cookie_data
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import kotlinx.coroutines.*
import okhttp3.FormBody
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class RecyclerItemCardWorkAdapterr(add_work_option:String) :
    RecyclerView.Adapter<RecyclerItemCardWorkAdapterr.ViewHolder>() {
    private var itemData: ShowProductControlOrderBody_B =Gson().fromJson(cookie_data.response_data, ShowProductControlOrderBody_B::class.java)
    private var data: ArrayList<ProductControlOrderBody_B> =itemData.data
    val dateF = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.TAIWAN)

    var add_work_option=add_work_option

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemCardWorkAdapterr.ViewHolder {
        val v= LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_card_work,parent,false)
        return ViewHolder(v)

    }

    override fun onBindViewHolder(holder: RecyclerItemCardWorkAdapterr.ViewHolder, position: Int) {
        //Log.d("GSON", "msg: ${itemData.data[position]}\n")

        holder.prod_ctrl_order_number.setText(data[position].prod_ctrl_order_number)
        holder.prod_ctrl_order_number.inputType=InputType.TYPE_NULL
        holder.pline.setText(data[position].pline)
        holder.pline.inputType=InputType.TYPE_NULL
        holder.workstation_number.setText(data[position].workstation_number)
        holder.workstation_number.inputType=InputType.TYPE_NULL
        holder.qr_code.setText(data[position].qr_code)
        holder.qr_code.inputType=InputType.TYPE_NULL
        holder.qr_code.isClickable=false
        holder.card_number.setText(data[position].card_number)
        holder.card_number.inputType=InputType.TYPE_NULL
        holder.staff_name.setText(data[position].staff_name)
        holder.staff_name.inputType=InputType.TYPE_NULL

        if(data[position].online_time!=null){
            //date
            var readDateString=data[position].online_time
            //println(readDateString)
            val readDate=Calendar.getInstance()
            //println(readDateString.subSequence(0,4))
            readDate.set(Calendar.YEAR,readDateString!!.subSequence(0,4).toString().toInt())//yyyy
            // println(readDateString.subSequence(5,7))
            readDate.set(Calendar.MONTH,readDateString!!.subSequence(5,7).toString().toInt()-1)//MM
            // println(readDateString.subSequence(8,10))
            readDate.set(Calendar.DAY_OF_MONTH,readDateString!!.subSequence(8,10).toString().toInt())//dd
            readDate.set(Calendar.HOUR_OF_DAY,readDateString!!.subSequence(11,13).toString().toInt())//hh
            readDate.set(Calendar.MINUTE,readDateString!!.subSequence(14,16).toString().toInt())//mm
            readDate.set(Calendar.SECOND,readDateString!!.subSequence(17,19).toString().toInt())//ss
            var date=dateF.format(readDate.time)
            holder.online_time.setText(date)
        }
        else{
            holder.online_time.setText(null)
        }
        holder.online_time.inputType=InputType.TYPE_NULL

        holder.work_option.setText(add_work_option)
        holder.work_option.inputType=InputType.TYPE_NULL

        holder.deletebtn.isVisible=true

    }

    override fun getItemCount(): Int {
        return data.size//cookie_data.itemCount
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        var prod_ctrl_order_number=itemView.findViewById<TextInputEditText>(R.id.edit_prod_ctrl_order_number)
        var work_option=itemView.findViewById<TextInputEditText>(R.id.edit_work_option)
        var pline=itemView.findViewById<TextInputEditText>(R.id.edit_pline)
        var staff_name=itemView.findViewById<TextInputEditText>(R.id.edit_staff_name)
        var online_time=itemView.findViewById<TextInputEditText>(R.id.edit_online_time)
        var card_number =itemView.findViewById<TextInputEditText>(R.id.edit_card_number)
        var qr_code=itemView.findViewById<TextInputEditText>(R.id.edit_qr_code)
        var workstation_number=itemView.findViewById<TextInputEditText>(R.id.edit_workstation_number)


        var deletebtn=itemView.findViewById<Button>(R.id.delete_btn)


        var layout=itemView.findViewById<ConstraintLayout>(R.id.constraint_layout)
        lateinit var oldData:ProductControlOrderBody_B
        lateinit var newData:ProductControlOrderBody_B
        init {
            itemView.setOnClickListener{
                val position:Int=adapterPosition
                Log.d("GSON", "msg: ${position}\n")

            }

            val combobox=itemView.resources.getStringArray(R.array.combobox_yes_no)
            val arrayAdapter= ArrayAdapter(itemView.context,R.layout.combobox_item,combobox)


            var qrcodeFlag:Boolean=false


         /*
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
*/


         /*   //編輯按鈕
            edit_btn.setOnClickListener {
                when(edit_btn.text){
                    "編輯"->{
                        cookie_data.itemposition=adapterPosition
                        oldData=data[adapterPosition]
                        //Log.d("GSON", "msg: ${oldData.id}\n")
                        //code.inputType=InputType.TYPE_CLASS_TEXT
                        //prod_ctrl_order_number.inputType=InputType.TYPE_CLASS_TEXT
                        pline.setAdapter(arrayAdapter01)
                        workstation_number.setAdapter(arrayAdapter02)
                        //qr_code.inputType=InputType.TYPE_CLASS_TEXT
                        qrcodeFlag=true
                        card_number.setAdapter(arrayAdapter03)
                        staff_name.setAdapter(arrayAdapter04)
                        online_time.inputType=InputType.TYPE_CLASS_DATETIME
                        offline_time.inputType=InputType.TYPE_CLASS_DATETIME
                        is_personal_report.setAdapter(arrayAdapter)
                        is_personal_report.isClickable=true
                        actual_output.inputType=InputType.TYPE_CLASS_NUMBER



                        remark.isClickable=true
                        remark.isFocusable=true
                        remark.isFocusableInTouchMode=true
                        remark.isTextInputLayoutFocusedRectEnabled=true
                        pline.requestFocus()
                        edit_btn.text = "完成"
                        layout.setBackgroundColor(Color.parseColor("#6E5454"))
                        edit_btn.setBackgroundColor(Color.parseColor("#FF3700B3"))
                    }
                    "完成"->{
                        newData= ProductControlOrderBody_B()
                        newData.code=code.text.toString()
                        code.inputType=InputType.TYPE_NULL
                        newData.prod_ctrl_order_number=prod_ctrl_order_number.text.toString()
                        prod_ctrl_order_number.inputType=InputType.TYPE_NULL
                        newData.pline=pline.text.toString()
                        pline.setAdapter(null)
                        newData.workstation_number=workstation_number.text.toString()
                        workstation_number.setAdapter(null)
                        newData.qr_code=qr_code.text.toString()
                        qrcodeFlag=false
                        qr_code.inputType=InputType.TYPE_NULL
                        newData.card_number=card_number.text.toString()
                        card_number.setAdapter(null)
                        newData.staff_name=staff_name.text.toString()
                        staff_name.setAdapter(null)
                        if(online_time.text.toString()==""){
                            newData.online_time=null
                        }
                        else{
                            newData.online_time=online_time.text.toString()
                        }
                        online_time.inputType=InputType.TYPE_NULL
                        if(offline_time.text.toString()==""){
                            newData.offline_time=null
                        }
                        else{
                            newData.offline_time=offline_time.text.toString()
                        }
                        offline_time.inputType=InputType.TYPE_NULL
                        newData.is_personal_report=is_personal_report.text.toString().toBoolean()
                        is_personal_report.isClickable=false
                        is_personal_report.setAdapter(null)
                        newData.actual_output=actual_output.text.toString().toInt()
                        actual_output.inputType=InputType.TYPE_NULL


                        newData.remark=remark.text.toString()
                        remark.isClickable=false
                        remark.isFocusable=false
                        remark.isFocusableInTouchMode=false
                        remark.isTextInputLayoutFocusedRectEnabled=false
                        //Log.d("GSON", "msg: ${oldData}\n")
                        //Log.d("GSON", "msg: ${newData.remark}\n")
                        edit_ProductControlOrder("ProductControlOrderBody_B",oldData,newData)//更改資料庫資料
                        when(cookie_data.status){
                            0-> {//成功
                                itemData.data[adapterPosition] = newData//更改渲染資料
                                editor.setText(cookie_data.username)
                                Toast.makeText(itemView.context, cookie_data.msg, Toast.LENGTH_SHORT).show()
                            }
                            1->{//失敗
                                Toast.makeText(itemView.context, cookie_data.msg, Toast.LENGTH_SHORT).show()
                                code.setText(oldData.code)
                                prod_ctrl_order_number.setText(oldData.prod_ctrl_order_number)
                                pline.setText(oldData.pline)
                                workstation_number.setText(oldData.workstation_number)
                                qr_code.setText(oldData.qr_code)
                                card_number.setText(oldData.card_number)
                                staff_name.setText(oldData.staff_name)
                                online_time.setText(oldData.online_time)
                                offline_time.setText(oldData.offline_time)
                                is_personal_report.setText(oldData.is_personal_report.toString())
                                actual_output.setText(oldData.actual_output.toString())


                                remark.setText(oldData.remark)
                            }
                        }
                        //Log.d("GSON", "msg: ${itemData.data}\n")
                        edit_btn.text = "編輯"
                        layout.setBackgroundColor(Color.parseColor("#977C7C"))
                        edit_btn.setBackgroundColor(Color.parseColor("#FF6200EE"))
                    }

                }

            }*/
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
                    delete_ProductControlOrder("ProductControlOrderBody_B",data[adapterPosition])
                    when(cookie_data.status){
                        0-> {//成功
                            data.removeAt(adapterPosition)
                            notifyItemRemoved(adapterPosition)
                            Toast.makeText(itemView.context, cookie_data.msg, Toast.LENGTH_SHORT).show()

                        }
                        1->{//失敗
                            Toast.makeText(itemView.context, cookie_data.msg, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                mAlertDialog.show()

            }

          /*  //鎖定按鈕
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
                    lock_ProductControlOrder("ProductControlOrderBody_B",data[adapterPosition])
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
                    over_ProductControlOrder("ProductControlOrderBody_B",data[adapterPosition])
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

            }*/
        }
        private fun edit_ProductControlOrder(operation:String,oldData:ProductControlOrderBody_B,newData:ProductControlOrderBody_B) {
            val old =JSONObject()
            old.put("code",oldData.code)
            old.put("prod_ctrl_order_number",oldData.prod_ctrl_order_number)
            old.put("pline",oldData.pline)
            old.put("workstation_number",oldData.workstation_number)
            old.put("qr_code",oldData.qr_code)
            old.put("card_number",oldData.card_number)
            old.put("staff_name",oldData.staff_name)
            old.put("online_time",oldData.online_time)
            old.put("offline_time",oldData.offline_time)
            old.put("is_personal_report",oldData.is_personal_report)
            old.put("actual_output",oldData.actual_output)

            old.put("remark",oldData.remark)
            val new =JSONObject()
            new.put("code",newData.code)
            new.put("prod_ctrl_order_number",newData.prod_ctrl_order_number)
            new.put("pline",newData.pline)
            new.put("workstation_number",newData.workstation_number)
            new.put("qr_code",newData.qr_code)
            new.put("card_number",newData.card_number)
            new.put("staff_name",newData.staff_name)
            new.put("online_time",newData.online_time)
            new.put("offline_time",newData.offline_time)
            new.put("is_personal_report",newData.is_personal_report)
            new.put("actual_output",newData.actual_output)

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
                .url(cookie_data.URL+"/production_control_sheet_management")
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
        private fun delete_ProductControlOrder(operation:String,deleteData:ProductControlOrderBody_B) {
            val delete = JSONObject()
            delete.put("code",deleteData.code)
            delete.put("prod_ctrl_order_number",deleteData.prod_ctrl_order_number)
            delete.put("pline",deleteData.pline)
            delete.put("workstation_number",deleteData.workstation_number)
            delete.put("qr_code",deleteData.qr_code)
            delete.put("card_number",deleteData.card_number)
            delete.put("staff_name",deleteData.staff_name)
            delete.put("online_time",deleteData.online_time)
            delete.put("offline_time",deleteData.offline_time)
            delete.put("is_personal_report",deleteData.is_personal_report)
            delete.put("actual_output",deleteData.actual_output)

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
                .url(cookie_data.URL+"/production_control_sheet_management")
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
        private fun lock_ProductControlOrder(operation:String,lockData:ProductControlOrderBody_B) {
            val lock = JSONObject()
            lock.put("code",lockData.code)
            lock.put("prod_ctrl_order_number",lockData.prod_ctrl_order_number)
            lock.put("pline",lockData.pline)
            lock.put("workstation_number",lockData.workstation_number)
            lock.put("qr_code",lockData.qr_code)
            lock.put("card_number",lockData.card_number)
            lock.put("staff_name",lockData.staff_name)
            lock.put("online_time",lockData.online_time)
            lock.put("offline_time",lockData.offline_time)
            lock.put("is_personal_report",lockData.is_personal_report)
            lock.put("actual_output",lockData.actual_output)

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
                .url(cookie_data.URL+"/production_control_sheet_management")
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
        private fun over_ProductControlOrder(operation:String,overData:ProductControlOrderBody_B) {
            val over = JSONObject()
            over.put("code",overData.code)
            over.put("prod_ctrl_order_number",overData.prod_ctrl_order_number)
            over.put("pline",overData.pline)
            over.put("workstation_number",overData.workstation_number)
            over.put("qr_code",overData.qr_code)
            over.put("card_number",overData.card_number)
            over.put("staff_name",overData.staff_name)
            over.put("online_time",overData.online_time)
            over.put("offline_time",overData.offline_time)
            over.put("is_personal_report",overData.is_personal_report)
            over.put("actual_output",overData.actual_output)

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
                .url(cookie_data.URL+"/production_control_sheet_management")
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
    fun addItem(addData:ProductControlOrderBody_B){
        data.add(data.size,addData)
        notifyItemInserted(data.size)
        cookie_data.recyclerView.smoothScrollToPosition(data.size)
    }


}


