package com.example.erp20.app13
import android.app.Activity
import android.app.DatePickerDialog
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


class RecyclerItemOAFileDeliveryRecordBillAdapter(filter_courier_company:String,filter_shippin_billing_month:String) :
    RecyclerView.Adapter<RecyclerItemOAFileDeliveryRecordBillAdapter.ViewHolder>() {
    private var itemData: ShowOAFileDeliveryRecordHeader =Gson().fromJson(cookie_data.response_data, ShowOAFileDeliveryRecordHeader::class.java)
    private var data: ArrayList<OAFileDeliveryRecordHeader> =itemData.data

    val dateF = SimpleDateFormat("yyyy-MM-dd(EEEE)", Locale.TAIWAN)
    val dateF2 = SimpleDateFormat("yyyy-MM", Locale.TAIWAN)

    init {
        // println(SelectFilter)
        data=filter(data,filter_courier_company,filter_shippin_billing_month)
        data=sort(data)

    }

    fun filter(data: java.util.ArrayList<OAFileDeliveryRecordHeader>, filter_courier_company:String,filter_shippin_billing_month:String): java.util.ArrayList<OAFileDeliveryRecordHeader> {
        var ArrayList: java.util.ArrayList<OAFileDeliveryRecordHeader> =
            java.util.ArrayList<OAFileDeliveryRecordHeader>()
        if(filter_courier_company!=""){
            for(i in 0 until data.size){
                if(data[i].courier_company==filter_courier_company){
                    ArrayList.add(data[i])
                }
            }
        }
        else{
            ArrayList=data
        }
        var ArrayList2: java.util.ArrayList<OAFileDeliveryRecordHeader> =
            java.util.ArrayList<OAFileDeliveryRecordHeader>()
        if(filter_shippin_billing_month!=""){
            for(i in 0 until ArrayList.size){
                if(ArrayList[i].shippin_billing_month==filter_shippin_billing_month){
                    ArrayList2.add(ArrayList[i])
                }
            }
        }
        else{
            ArrayList2=ArrayList
        }


        return ArrayList2
    }
    fun sort(data: java.util.ArrayList<OAFileDeliveryRecordHeader>): java.util.ArrayList<OAFileDeliveryRecordHeader> {
        var sortedList:List<OAFileDeliveryRecordHeader> = data

        sortedList  = data.sortedWith(
            compareBy(
                { it.trackingNo },
            )
        )

        return sortedList.toCollection(java.util.ArrayList())
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v= LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_oa_file_delivery_record_bill,parent,false)
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




        if(!data[position].invalid){
            holder.edit_btn.isVisible=true
            holder.send_record_btn.isVisible=true
        }

        holder.next_btn.isVisible=true


    }

    override fun getItemCount(): Int {
        return data.size//cookie_data.itemCount
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var trackingNo=itemView.findViewById<TextInputEditText>(R.id.edit_trackingNo)
        var courier_company=itemView.findViewById<TextInputEditText>(R.id.edit_courier_company)
        var delivery_date=itemView.findViewById<TextInputEditText>(R.id.edit_delivery_date)


        var shippin_billing_month=itemView.findViewById<TextInputEditText>(R.id.edit_shippin_billing_month)
        var billing_date=itemView.findViewById<TextInputEditText>(R.id.edit_billing_date)
        var is_closed=itemView.findViewById<TextInputEditText>(R.id.edit_is_closed)


        var edit_btn=itemView.findViewById<Button>(R.id.edit_btn)

        var next_btn=itemView.findViewById<Button>(R.id.next_btn)
        var send_record_btn=itemView.findViewById<Button>(R.id.send_record_btn)


        lateinit var oldData:OAFileDeliveryRecordHeader
        lateinit var newData:OAFileDeliveryRecordHeader
        var edit=false
        var tempDate1=""
        var tempDate2=""
        var tempDate3=""
        init {
            itemView.setOnClickListener{
                val position:Int=adapterPosition
                Log.d("GSON", "msg: ${position}\n")

            }

            val combobox=itemView.resources.getStringArray(R.array.combobox_yes_no)
            val arrayAdapter= ArrayAdapter(itemView.context,R.layout.combobox_item,combobox)
            val arrayAdapter01= ArrayAdapter(itemView.context,R.layout.combobox_item,cookie_data.cont_code_ComboboxData)

         /*   //寄送日期
            delivery_date.setOnClickListener {
                if(edit==true){
                    var c= Calendar.getInstance()
                    val year= c.get(Calendar.YEAR)
                    val month = c.get(Calendar.MONTH)
                    val day = c.get(Calendar.DAY_OF_MONTH)
                    var datePicker = DatePickerDialog(itemView.context,
                        DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                            val SelectedDate=Calendar.getInstance()
                            SelectedDate.set(Calendar.YEAR,mYear)
                            SelectedDate.set(Calendar.MONTH,mMonth)
                            SelectedDate.set(Calendar.DAY_OF_MONTH,mDay)
                            val Date= dateF.format(SelectedDate.time)
                            delivery_date.setText(Date)
                        },year,month,day).show()

                }
            }*/

            //結帳日其
            billing_date.setOnClickListener {
                if(edit==true){
                    var c= Calendar.getInstance()
                    val year= c.get(Calendar.YEAR)
                    val month = c.get(Calendar.MONTH)
                    val day = c.get(Calendar.DAY_OF_MONTH)
                    var datePicker = DatePickerDialog(itemView.context,
                        DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                            val SelectedDate=Calendar.getInstance()
                            SelectedDate.set(Calendar.YEAR,mYear)
                            SelectedDate.set(Calendar.MONTH,mMonth)
                            SelectedDate.set(Calendar.DAY_OF_MONTH,mDay)
                            val Date= dateF.format(SelectedDate.time)
                            billing_date.setText(Date)
                        },year,month,day).show()

                }
            }
            //結帳月份
            shippin_billing_month.setOnClickListener {
                if(edit==true){
                    var c= Calendar.getInstance()
                    val year= c.get(Calendar.YEAR)
                    val month = c.get(Calendar.MONTH)
                    val day = c.get(Calendar.DAY_OF_MONTH)
                    var datePicker = DatePickerDialog(itemView.context,
                        DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                            val SelectedDate=Calendar.getInstance()
                            SelectedDate.set(Calendar.YEAR,mYear)
                            SelectedDate.set(Calendar.MONTH,mMonth)
                            SelectedDate.set(Calendar.DAY_OF_MONTH,mDay)
                            val Date= dateF2.format(SelectedDate.time)
                            shippin_billing_month.setText(Date)
                        },year,month,day).show()

                }
            }


            //編輯按鈕
            edit_btn.setOnClickListener {
                when(edit_btn.text){
                    "編輯"->{

                        edit=true
                        oldData=data[adapterPosition]
                        //Log.d("GSON", "msg: ${oldData.id}\n")
                       // trackingNo.inputType=InputType.TYPE_CLASS_TEXT
                        //courier_company.inputType=InputType.TYPE_CLASS_TEXT



                       // shippin_billing_month.inputType=(InputType.TYPE_CLASS_TEXT or InputType.TYPE_CLASS_NUMBER)
                        //shippin_billing_month.setSelectAllOnFocus(true)
                        tempDate1=billing_date.text.toString()


                        shippin_billing_month.requestFocus()
                        edit_btn.text = "完成"
                    }
                    "完成"->{

                        edit=false
                        newData= oldData.copy()//OAFileDeliveryRecordHeader()
                        newData.is_closed=oldData.is_closed
                        newData.shippin_billing_month=shippin_billing_month.text.toString()
                        shippin_billing_month.inputType=InputType.TYPE_NULL

                        if(billing_date.text.toString()==""){
                            newData.billing_date=null
                        }
                        else{
                            newData.billing_date=billing_date.text.toString().substring(0,billing_date.text.toString().indexOf("("))
                        }
                        billing_date.inputType=InputType.TYPE_NULL


                        //Log.d("GSON", "msg: ${oldData}\n")
                        //Log.d("GSON", "msg: ${newData.remark}\n")
                        edit_OAFileDeliveryRecord("OAFileDeliveryRecordHeader",oldData,newData)//更改資料庫資料

                        when(cookie_data.status){
                            0-> {//成功
                                data[adapterPosition] = newData//更改渲染資料
                                Toast.makeText(itemView.context, cookie_data.msg, Toast.LENGTH_SHORT).show()
                                if(data[adapterPosition].billing_date!=null && !data[adapterPosition].is_closed){
                                    over_OAFileDeliveryRecord("OAFileDeliveryRecordHeader",data[adapterPosition])
                                    Toast.makeText(itemView.context, cookie_data.msg, Toast.LENGTH_SHORT).show()
                                    when(cookie_data.status){
                                        0->{
                                            is_closed.setText("true")
                                        }
                                    }
                                }
                            }
                            1->{//失敗
                                Toast.makeText(itemView.context, cookie_data.msg, Toast.LENGTH_SHORT).show()

                                shippin_billing_month.setText(oldData.shippin_billing_month)
                                billing_date.setText(tempDate1)


                            }
                        }
                        //Log.d("GSON", "msg: ${itemData.data}\n")
                        edit_btn.text = "編輯"
                    }

                }

            }
           /* //刪除按鈕
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
                    delete_OAFileDeliveryRecord("OAFileDeliveryRecordHeader",data[adapterPosition])
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

            }*/

            //下一筆
            next_btn.setOnClickListener {
                if(adapterPosition+1<=data.size){
                    cookie_data.recyclerView.smoothScrollToPosition(adapterPosition+1)
                }

            }

            //單身
            send_record_btn.setOnClickListener {
                cookie_data.first_recyclerView=cookie_data.recyclerView
                var dialogF= PopUpSendRecordFragment2(data[adapterPosition].trackingNo)
                dialogF.show( ( itemView.context as AppCompatActivity).supportFragmentManager ,"寄送紀錄")
            }


            /*//鎖定按鈕
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
                     lock_OAFileDeliveryRecord("OAFileDeliveryRecordHeader",data[adapterPosition])
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

            /* //結案按鈕
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
                     over_OAFileDeliveryRecord("OAFileDeliveryRecordHeader",data[adapterPosition])
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
        private fun edit_OAFileDeliveryRecord(operation:String,oldData:OAFileDeliveryRecordHeader,newData:OAFileDeliveryRecordHeader) {
            val old =JSONObject()
            old.put("trackingNo",oldData.trackingNo)
            old.put("courier_company",oldData.courier_company)
            old.put("delivery_date",oldData.delivery_date)
            old.put("arrival_date",oldData.arrival_date)
            old.put("receiver",oldData.receiver)
            old.put("shippin_billing_month",oldData.shippin_billing_month)
            old.put("billing_date",oldData.billing_date)

            old.put("remark",oldData.remark)
            val new =JSONObject()
            new.put("trackingNo",newData.trackingNo)
            new.put("courier_company",newData.courier_company)
            new.put("delivery_date",newData.delivery_date)
            new.put("arrival_date",newData.arrival_date)
            new.put("receiver",newData.receiver)
            new.put("shippin_billing_month",newData.shippin_billing_month)
            new.put("billing_date",newData.billing_date)


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
        private fun delete_OAFileDeliveryRecord(operation:String,deleteData:OAFileDeliveryRecordHeader) {
            val delete = JSONObject()
            delete.put("trackingNo",deleteData.trackingNo)
            delete.put("courier_company",deleteData.courier_company)
            delete.put("delivery_date",deleteData.delivery_date)
            delete.put("arrival_date",deleteData.arrival_date)
            delete.put("receiver",deleteData.receiver)
            delete.put("shippin_billing_month",deleteData.shippin_billing_month)
            delete.put("billing_date",deleteData.billing_date)

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
        private fun lock_OAFileDeliveryRecord(operation:String,lockData:OAFileDeliveryRecordHeader) {
            val lock = JSONObject()
            lock.put("trackingNo",lockData.trackingNo)
            lock.put("courier_company",lockData.courier_company)
            lock.put("delivery_date",lockData.delivery_date)
            lock.put("arrival_date",lockData.arrival_date)
            lock.put("receiver",lockData.receiver)
            lock.put("shippin_billing_month",lockData.shippin_billing_month)
            lock.put("billing_date",lockData.billing_date)

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
        private fun over_OAFileDeliveryRecord(operation:String,overData:OAFileDeliveryRecordHeader) {
            val over = JSONObject()
            over.put("trackingNo",overData.trackingNo)
            over.put("courier_company",overData.courier_company)
            over.put("delivery_date",overData.delivery_date)
            over.put("arrival_date",overData.arrival_date)
            over.put("receiver",overData.receiver)
            over.put("shippin_billing_month",overData.shippin_billing_month)
            over.put("billing_date",overData.billing_date)

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


