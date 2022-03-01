package com.example.erp20.app13
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
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
import java.security.AccessController.getContext
import java.text.SimpleDateFormat
import java.util.*


class RecyclerItemOAFileDeliveryRecord2Adapter() :
    RecyclerView.Adapter<RecyclerItemOAFileDeliveryRecord2Adapter.ViewHolder>() {
    private var itemData: ShowOAFileDeliveryRecordHeader =Gson().fromJson(cookie_data.response_data, ShowOAFileDeliveryRecordHeader::class.java)
    private var data: ArrayList<OAFileDeliveryRecordHeader> =itemData.data

    val dateF = SimpleDateFormat("yyyy-MM-dd(EEEE)", Locale.TAIWAN)

    init {
        // println(SelectFilter)
        //data=filter(data,FilterTopic,FilterContent)
        //data=sort(data)

    }
    /*fun filter(data: java.util.ArrayList<BookingNoticeHeader>, filterTopic:String, filterContent:String): java.util.ArrayList<BookingNoticeHeader> {
        var ArrayList: java.util.ArrayList<BookingNoticeHeader> =
            java.util.ArrayList<BookingNoticeHeader>()
        if(filterTopic=="訂艙管制單號"){
            for(i in 0 until data.size){
                if(data[i]._id==filterContent){
                    ArrayList.add(data[i])
                }
            }
        }
        else if(filterTopic=="訂艙通知號碼"){
            for(i in 0 until data.size){
                if(data[i].notice_number==filterContent){
                    ArrayList.add(data[i])
                }
            }
        }
        else if(filterTopic=="PO#"){
            for(i in 0 until data.size){
                if(data[i].customer_poNo==filterContent){
                    ArrayList.add(data[i])
                }
            }
        }
        return ArrayList
    }*/
    /*fun sort(data: java.util.ArrayList<OAFileDeliveryRecordHeader>): java.util.ArrayList<OAFileDeliveryRecordHeader> {
        var sortedList:List<OAFileDeliveryRecordHeader> = data

        sortedList  = data.sortedWith(
            compareBy(
                { it.cont_code },
            )
        )

        return sortedList.toCollection(java.util.ArrayList())
    }*/
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v= LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_oa_file_delivery_record2,parent,false)
        return ViewHolder(v)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.trackingNo.setText(data[position].trackingNo)
        holder.trackingNo.inputType=InputType.TYPE_NULL
        holder.courier_company.setText(data[position].courier_company)
        holder.courier_company.inputType=InputType.TYPE_NULL

        if(data[position].delivery_date!=null){
            //date
            var readDateString=data[position].delivery_date
            //println(readDateString)
            val readDate=Calendar.getInstance()
            //println(readDateString.subSequence(0,4))
            readDate.set(Calendar.YEAR,readDateString!!.subSequence(0,4).toString().toInt())//yyyy
            // println(readDateString.subSequence(5,7))
            readDate.set(Calendar.MONTH,readDateString!!.subSequence(5,7).toString().toInt()-1)//MM
            // println(readDateString.subSequence(8,10))
            readDate.set(Calendar.DAY_OF_MONTH,readDateString!!.subSequence(8,10).toString().toInt())//dd
            var date=dateF.format(readDate.time)
            holder.delivery_date.setText(date)
        }
        else{
            holder.delivery_date.setText(null)
        }
        holder.delivery_date.inputType=InputType.TYPE_NULL

        if(data[position].arrival_date!=null){
            //date
            var readDateString=data[position].arrival_date
            //println(readDateString)
            val readDate=Calendar.getInstance()
            //println(readDateString.subSequence(0,4))
            readDate.set(Calendar.YEAR,readDateString!!.subSequence(0,4).toString().toInt())//yyyy
            // println(readDateString.subSequence(5,7))
            readDate.set(Calendar.MONTH,readDateString!!.subSequence(5,7).toString().toInt()-1)//MM
            // println(readDateString.subSequence(8,10))
            readDate.set(Calendar.DAY_OF_MONTH,readDateString!!.subSequence(8,10).toString().toInt())//dd
            var date=dateF.format(readDate.time)
            holder.arrival_date.setText(date)
        }
        else{
            holder.arrival_date.setText(null)
        }
        holder.arrival_date.inputType=InputType.TYPE_NULL

        holder.receiver.setText(data[position].receiver)
        holder.receiver.inputType=InputType.TYPE_NULL
        holder.shippin_billing_month.setText(data[position].shippin_billing_month)
        holder.shippin_billing_month.inputType=InputType.TYPE_NULL

        if(data[position].billing_date!=null){
            //date
            var readDateString=data[position].billing_date
            //println(readDateString)
            val readDate=Calendar.getInstance()
            //println(readDateString.subSequence(0,4))
            readDate.set(Calendar.YEAR,readDateString!!.subSequence(0,4).toString().toInt())//yyyy
            // println(readDateString.subSequence(5,7))
            readDate.set(Calendar.MONTH,readDateString!!.subSequence(5,7).toString().toInt()-1)//MM
            // println(readDateString.subSequence(8,10))
            readDate.set(Calendar.DAY_OF_MONTH,readDateString!!.subSequence(8,10).toString().toInt())//dd
            var date=dateF.format(readDate.time)
            holder.billing_date.setText(date)
        }
        else{
            holder.billing_date.setText(null)
        }
        holder.billing_date.inputType=InputType.TYPE_NULL
        holder.is_closed.setText(data[position].is_closed.toString())
        holder.is_closed.inputType=InputType.TYPE_NULL


        holder.remark.setText(data[position].remark)
        holder.remark.isClickable=false
        holder.remark.isFocusable=false
        holder.remark.isFocusableInTouchMode=false
        holder.remark.isTextInputLayoutFocusedRectEnabled=false
        /*holder.deletebtn.isVisible=true
        holder.edit_btn.isVisible=true
        holder.next_btn.isVisible=true
        holder.stack_detail_btn.isVisible=true
        holder.send_record_btn.isVisible=true*/
    }

    override fun getItemCount(): Int {
        return data.size//cookie_data.itemCount
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var trackingNo=itemView.findViewById<TextInputEditText>(R.id.edit_trackingNo)
        var courier_company=itemView.findViewById<TextInputEditText>(R.id.edit_courier_company)
        var delivery_date=itemView.findViewById<TextInputEditText>(R.id.edit_delivery_date)
        var arrival_date=itemView.findViewById<TextInputEditText>(R.id.edit_arrival_date)
        var receiver=itemView.findViewById<TextInputEditText>(R.id.edit_receiver)
        var shippin_billing_month=itemView.findViewById<TextInputEditText>(R.id.edit_shippin_billing_month)
        var billing_date=itemView.findViewById<TextInputEditText>(R.id.edit_billing_date)
        var is_closed=itemView.findViewById<TextInputEditText>(R.id.edit_is_closed)


        var edit_btn=itemView.findViewById<Button>(R.id.edit_btn)
        var deletebtn=itemView.findViewById<Button>(R.id.delete_btn)
        var next_btn=itemView.findViewById<Button>(R.id.next_btn)
        var stack_detail_btn=itemView.findViewById<Button>(R.id.stack_detail_btn)
        var send_record_btn=itemView.findViewById<Button>(R.id.send_record_btn)

        var remark=itemView.findViewById<TextInputEditText>(R.id.edit_remark)
        lateinit var oldData:OAFileDeliveryRecordHeader
        lateinit var newData:OAFileDeliveryRecordHeader
        var tempContType=""
        init {
            itemView.setOnClickListener{
                val position:Int=adapterPosition
                Log.d("GSON", "msg: ${position}\n")

            }
/*
            val combobox=itemView.resources.getStringArray(R.array.combobox_yes_no)
            val arrayAdapter= ArrayAdapter(itemView.context,R.layout.combobox_item,combobox)
            val arrayAdapter01= ArrayAdapter(itemView.context,R.layout.combobox_item,cookie_data.cont_code_ComboboxData)

            cont_code.setOnItemClickListener { parent, view, position, id ->
                if(cookie_data.StackingControlListHeader_contNo_ComboboxData.indexOf(cont_code.text.toString())!=-1){
                    cont_type.setText(cookie_data.StackingControlListHeader_cont_type_code_ComboboxData[
                            cookie_data.StackingControlListHeader_contNo_ComboboxData.indexOf(
                                cont_code.text.toString())])
                }
                else{
                    cont_type.setText("")
                }

            }

            //編輯按鈕
            edit_btn.setOnClickListener {
                when(edit_btn.text){
                    "編輯"->{
                        oldData=data[adapterPosition]
                        //Log.d("GSON", "msg: ${oldData.id}\n")
                        //header_id.inputType=InputType.TYPE_CLASS_TEXT
                        //body_id.inputType=InputType.TYPE_CLASS_TEXT
                        cont_code.setAdapter(arrayAdapter01)
                        cont_code.inputType=InputType.TYPE_CLASS_TEXT
                        tempContType=cont_type.text.toString()
                        remark.isClickable=true
                        remark.isFocusable=true
                        remark.isFocusableInTouchMode=true
                        remark.isTextInputLayoutFocusedRectEnabled=true
                        cont_code.requestFocus()
                        edit_btn.text = "完成"
                    }
                    "完成"->{
                        newData= oldData.copy()//BookingNoticeBody()
                        newData.cont_code=cont_code.text.toString()
                        cont_code.setAdapter(null)
                        cont_code.inputType=InputType.TYPE_NULL

                        newData.remark=remark.text.toString()
                        remark.isClickable=false
                        remark.isFocusable=false
                        remark.isFocusableInTouchMode=false
                        remark.isTextInputLayoutFocusedRectEnabled=false
                        //Log.d("GSON", "msg: ${oldData}\n")
                        //Log.d("GSON", "msg: ${newData.remark}\n")
                        edit_BookingNotice("BookingNoticeBody",oldData,newData)//更改資料庫資料
                        when(cookie_data.status){
                            0-> {//成功
                                data[adapterPosition] = newData//更改渲染資料
                                Toast.makeText(itemView.context, cookie_data.msg, Toast.LENGTH_SHORT).show()
                            }
                            1->{//失敗
                                Toast.makeText(itemView.context, cookie_data.msg, Toast.LENGTH_SHORT).show()
                                //header_id.setText(oldData.header_id)
                                //body_id.setText(oldData.body_id)
                                cont_code.setText(oldData.cont_code)
                                cont_type.setText(tempContType)

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
                    delete_BookingNotice("BookingNoticeBody",data[adapterPosition])
                    when(cookie_data.status){
                        0-> {//成功
                            data.removeAt(adapterPosition)
                            notifyItemRemoved(adapterPosition)
                            //itemData.count-=1//cookie_data.itemCount-=1
                            Toast.makeText(itemView.context, cookie_data.msg, Toast.LENGTH_SHORT).show()

                        }
                        1->{//失敗
                            Toast.makeText(itemView.context, cookie_data.msg, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                mAlertDialog.show()

            }

            //下一筆
            next_btn.setOnClickListener {
                if(adapterPosition+1<=data.size){
                    cookie_data.recyclerView.smoothScrollToPosition(adapterPosition+1)
                }

            }

           */


            /* //鎖定按鈕
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
                     lock_BookingNotice("BookingNoticeBody",data[adapterPosition])
                     when(cookie_data.status){
                         0-> {//成功

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
                     over_BookingNotice("BookingNoticeBody",data[adapterPosition])
                     when(cookie_data.status){
                         0-> {//成功
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
        private fun edit_BookingNotice(operation:String,oldData:BookingNoticeBody,newData:BookingNoticeBody) {
            val old =JSONObject()
            old.put("header_id",oldData.header_id)
            old.put("body_id",oldData.body_id)
            old.put("cont_code",oldData.cont_code)


            old.put("remark",oldData.remark)
            val new =JSONObject()
            new.put("header_id",newData.header_id)
            new.put("body_id",newData.body_id)
            new.put("cont_code",newData.cont_code)



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
                .url(cookie_data.URL+"/shipping_order_management")
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
        private fun delete_BookingNotice(operation:String,deleteData:BookingNoticeBody) {
            val delete = JSONObject()
            delete.put("header_id",deleteData.header_id)
            delete.put("body_id",deleteData.body_id)
            delete.put("cont_code",deleteData.cont_code)

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
                .url(cookie_data.URL+"/shipping_order_management")
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
        private fun lock_BookingNotice(operation:String,lockData:BookingNoticeBody) {
            val lock = JSONObject()
            lock.put("header_id",lockData.header_id)
            lock.put("body_id",lockData.body_id)
            lock.put("cont_code",lockData.cont_code)

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
                .url(cookie_data.URL+"/shipping_order_management")
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
        private fun over_BookingNotice(operation:String,overData:BookingNoticeBody) {
            val over = JSONObject()
            over.put("header_id",overData.header_id)
            over.put("body_id",overData.body_id)
            over.put("cont_code",overData.cont_code)

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
                .url(cookie_data.URL+"/shipping_order_management")
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
        private fun getActivity(): Activity? {
            var context: Context = itemView.context
            while (context is ContextWrapper) {
                if (context is Activity) {
                    return context
                }
                context = (context as ContextWrapper).baseContext
            }
            return null
        }
    }
    fun addItem(addData:OAFileDeliveryRecordHeader){
        data.add(data.size,addData)
        notifyItemInserted(data.size)
        cookie_data.recyclerView.smoothScrollToPosition(data.size)
    }

}


