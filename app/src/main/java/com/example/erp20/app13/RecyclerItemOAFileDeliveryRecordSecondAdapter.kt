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
import com.example.erp20.app06.PopUpItemDetailFragment3
import java.text.SimpleDateFormat


class RecyclerItemOAFileDeliveryRecordSecondAdapter() :
    RecyclerView.Adapter<RecyclerItemOAFileDeliveryRecordSecondAdapter.ViewHolder>() {
    private var itemData: ShowOAFileDeliveryRecordBody =Gson().fromJson(cookie_data.response_data, ShowOAFileDeliveryRecordBody::class.java)
    private var data: ArrayList<OAFileDeliveryRecordBody> =itemData.data
    var relativeCombobox01=cookie_data.shipping_number_ComboboxData
    var relativeCombobox02=cookie_data.CustomerOrderHeader_poNo_ComboboxData
    val dateF = SimpleDateFormat("yyyy-MM-dd(EEEE)", Locale.TAIWAN)
    val timeF = SimpleDateFormat("HH:mm:ss", Locale.TAIWAN)
    val dateF2 = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    //var FilterOA1=FilterOA1
    init {
        // println(SelectFilter)
        //data=filter(data,FilterTopic,FilterContent)
        data=sort(data)

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
    fun sort(data: java.util.ArrayList<OAFileDeliveryRecordBody>): java.util.ArrayList<OAFileDeliveryRecordBody> {
        var sortedList:List<OAFileDeliveryRecordBody> = data

        sortedList  = data.sortedWith(
            compareBy(
                { it.booking_noticeNo },
            )
        )

        return sortedList.toCollection(java.util.ArrayList())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v= LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_oa_file_delivery_record_second,parent,false)
        return ViewHolder(v)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        //Log.d("GSON", "msg: ${itemData.data[position]}\n")
        holder.booking_noticeNo.setText(data[position].booking_noticeNo)
        holder.booking_noticeNo.inputType=InputType.TYPE_NULL

        holder.header_id.setText(cookie_data.BookingNoticeHeader_id_ComboboxData[
                cookie_data.BookingNoticeHeader_notice_numbe_ComboboxData.indexOf(
                    data[position].booking_noticeNo)])
        holder.header_id.inputType=InputType.TYPE_NULL

        holder.oa_referenceNO1.setText(cookie_data.BookingNoticeHeader_oa_referenceNO1_ComboboxData[
                cookie_data.BookingNoticeHeader_notice_numbe_ComboboxData.indexOf(
                    data[position].booking_noticeNo)])
        holder.oa_referenceNO1.inputType=InputType.TYPE_NULL

        if(cookie_data.OAReference_oa_referenceNO1_ComboboxData.indexOf(holder.oa_referenceNO1.text.toString())!=-1){
            holder.oa_referenceNO2.setText(
                cookie_data.OAReference_oa_referenceNO2_ComboboxData[
                        cookie_data.OAReference_oa_referenceNO1_ComboboxData.indexOf(
                            holder.oa_referenceNO1.text.toString())])
            holder.oa_referenceNO3.setText(
                cookie_data.OAReference_oa_referenceNO3_ComboboxData[
                        cookie_data.OAReference_oa_referenceNO1_ComboboxData.indexOf(
                            holder.oa_referenceNO1.text.toString())])
        }
        else{
            holder.oa_referenceNO2.setText("")
            holder.oa_referenceNO3.setText("")
        }
        holder.oa_referenceNO2.inputType=InputType.TYPE_NULL
        holder.oa_referenceNO3.inputType=InputType.TYPE_NULL


        holder.customer_poNo.setText(cookie_data.BookingNoticeHeader_customer_poNo_ComboboxData[
                cookie_data.BookingNoticeHeader_notice_numbe_ComboboxData.indexOf(
                    data[position].booking_noticeNo)])
        holder.customer_poNo.inputType=InputType.TYPE_NULL

        if(holder.customer_poNo.text.toString()!="" && cookie_data.CustomerOrderHeader_poNo_ComboboxData.indexOf(holder.customer_poNo.text.toString())!=-1 && cookie_data.CustomerOrderHeader_swe_ComboboxData[cookie_data.CustomerOrderHeader_poNo_ComboboxData.indexOf(holder.customer_poNo.text.toString())]!=null){
            //date
            var readDateString=cookie_data.CustomerOrderHeader_swe_ComboboxData[cookie_data.CustomerOrderHeader_poNo_ComboboxData.indexOf(holder.customer_poNo.text.toString())]
            //println(readDateString)
            val readDate=Calendar.getInstance()
            //println(readDateString.subSequence(0,4))
            readDate.set(Calendar.YEAR,readDateString!!.subSequence(0,4).toString().toInt())//yyyy
            // println(readDateString.subSequence(5,7))
            readDate.set(Calendar.MONTH,readDateString!!.subSequence(5,7).toString().toInt()-1)//MM
            // println(readDateString.subSequence(8,10))
            readDate.set(Calendar.DAY_OF_MONTH,readDateString!!.subSequence(8,10).toString().toInt())//dd
            var date=dateF.format(readDate.time)
            holder.swe.setText(date)
        }
        else{
            holder.swe.setText(null)
        }
        holder.swe.inputType=InputType.TYPE_NULL

        holder.trackingNo.setText(data[position].trackingNo)
        holder.trackingNo.inputType=InputType.TYPE_NULL



        holder.remark.setText(data[position].remark)
        holder.remark.isClickable=false
        holder.remark.isFocusable=false
        holder.remark.isFocusableInTouchMode=false
        holder.remark.isTextInputLayoutFocusedRectEnabled=false
        holder.deletebtn.isVisible=true
        holder.edit_btn.isVisible=true
        holder.next_btn.isVisible=true
        holder.book_info_btn.isVisible=true
    }

    override fun getItemCount(): Int {
        return data.size//cookie_data.itemCount
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var booking_noticeNo=itemView.findViewById<AutoCompleteTextView>(R.id.edit_booking_noticeNo)
        var header_id=itemView.findViewById<TextInputEditText>(R.id.edit_header_id)
        var oa_referenceNO1=itemView.findViewById<TextInputEditText>(R.id.edit_oa_referenceNO1)
        var oa_referenceNO2=itemView.findViewById<TextInputEditText>(R.id.edit_oa_referenceNO2)
        var oa_referenceNO3=itemView.findViewById<TextInputEditText>(R.id.edit_oa_referenceNO3)
        var customer_poNo=itemView.findViewById<TextInputEditText>(R.id.edit_customer_poNo)
        var swe=itemView.findViewById<TextInputEditText>(R.id.edit_swe)
        var trackingNo=itemView.findViewById<TextInputEditText>(R.id.edit_trackingNo)


        var edit_btn=itemView.findViewById<Button>(R.id.edit_btn)
        var deletebtn=itemView.findViewById<Button>(R.id.delete_btn)
        var next_btn=itemView.findViewById<Button>(R.id.next_btn)
        var book_info_btn=itemView.findViewById<Button>(R.id.book_info_btn)
        var remark=itemView.findViewById<TextInputEditText>(R.id.edit_remark)
        lateinit var oldData:OAFileDeliveryRecordBody
        lateinit var newData:OAFileDeliveryRecordBody
        init {
            itemView.setOnClickListener{
                val position:Int=adapterPosition
                Log.d("GSON", "msg: ${position}\n")
            }

            val combobox=itemView.resources.getStringArray(R.array.combobox_yes_no)
            val arrayAdapter= ArrayAdapter(itemView.context,R.layout.combobox_item,combobox)
            val arrayAdapter01= ArrayAdapter(itemView.context,R.layout.combobox_item,cookie_data.BookingNoticeHeader_notice_numbe_ComboboxData)
            booking_noticeNo.setOnItemClickListener { parent, view, position, id ->

                header_id.setText(cookie_data.BookingNoticeHeader_id_ComboboxData[position])

                oa_referenceNO1.setText(cookie_data.BookingNoticeHeader_oa_referenceNO1_ComboboxData[position])
                oa_referenceNO1.inputType=InputType.TYPE_NULL

                if(cookie_data.OAReference_oa_referenceNO1_ComboboxData.indexOf(oa_referenceNO1.text.toString())!=-1){
                    oa_referenceNO2.setText(
                        cookie_data.OAReference_oa_referenceNO2_ComboboxData[
                                cookie_data.OAReference_oa_referenceNO1_ComboboxData.indexOf(
                                    oa_referenceNO1.text.toString())])
                   oa_referenceNO3.setText(
                        cookie_data.OAReference_oa_referenceNO3_ComboboxData[
                                cookie_data.OAReference_oa_referenceNO1_ComboboxData.indexOf(
                                   oa_referenceNO1.text.toString())])
                }
                else{
                   oa_referenceNO2.setText("")
                   oa_referenceNO3.setText("")
                }
                oa_referenceNO2.inputType=InputType.TYPE_NULL
                oa_referenceNO3.inputType=InputType.TYPE_NULL


                customer_poNo.setText(cookie_data.BookingNoticeHeader_customer_poNo_ComboboxData[position])
                customer_poNo.inputType=InputType.TYPE_NULL

                if(customer_poNo.text.toString()!="" && cookie_data.CustomerOrderHeader_poNo_ComboboxData.indexOf(customer_poNo.text.toString())!=-1 && cookie_data.CustomerOrderHeader_swe_ComboboxData[cookie_data.CustomerOrderHeader_poNo_ComboboxData.indexOf(customer_poNo.text.toString())]!=null){
                    //date
                    var readDateString=cookie_data.CustomerOrderHeader_swe_ComboboxData[cookie_data.CustomerOrderHeader_poNo_ComboboxData.indexOf(customer_poNo.text.toString())]
                    //println(readDateString)
                    val readDate=Calendar.getInstance()
                    //println(readDateString.subSequence(0,4))
                    readDate.set(Calendar.YEAR,readDateString!!.subSequence(0,4).toString().toInt())//yyyy
                    // println(readDateString.subSequence(5,7))
                    readDate.set(Calendar.MONTH,readDateString!!.subSequence(5,7).toString().toInt()-1)//MM
                    // println(readDateString.subSequence(8,10))
                    readDate.set(Calendar.DAY_OF_MONTH,readDateString!!.subSequence(8,10).toString().toInt())//dd
                    var date=dateF.format(readDate.time)
                    swe.setText(date)
                }
                else{
                    swe.setText(null)
                }
                swe.inputType=InputType.TYPE_NULL


            }

            //編輯按鈕
            edit_btn.setOnClickListener {
                  when(edit_btn.text){
                      "編輯"->{
                          oldData=data[adapterPosition]
                          //Log.d("GSON", "msg: ${oldData.id}\n")
                          booking_noticeNo.inputType=InputType.TYPE_CLASS_TEXT
                          booking_noticeNo.setAdapter(arrayAdapter01)


                          remark.isClickable=true
                          remark.isFocusable=true
                          remark.isFocusableInTouchMode=true
                          remark.isTextInputLayoutFocusedRectEnabled=true
                          booking_noticeNo.requestFocus()
                          edit_btn.text = "完成"
                      }
                      "完成"->{

                          newData= oldData.copy()//BookingNoticeHeader()

                          newData.booking_noticeNo=booking_noticeNo.text.toString()
                          booking_noticeNo.setAdapter(null)
                          booking_noticeNo.inputType=InputType.TYPE_NULL

                          newData.remark=remark.text.toString()
                          remark.isClickable=false
                          remark.isFocusable=false
                          remark.isFocusableInTouchMode=false
                          remark.isTextInputLayoutFocusedRectEnabled=false
                          //Log.d("GSON", "msg: ${oldData}\n")
                          //Log.d("GSON", "msg: ${newData.remark}\n")
                          edit_OAFileDeliveryRecord("OAFileDeliveryRecordBody",oldData,newData)//更改資料庫資料
                          when(cookie_data.status){
                              0-> {//成功
                                  data[adapterPosition] = newData//更改渲染資料
                                  Toast.makeText(itemView.context, cookie_data.msg, Toast.LENGTH_SHORT).show()
                              }
                              1->{//失敗
                                  Toast.makeText(itemView.context, cookie_data.msg, Toast.LENGTH_SHORT).show()
                                  booking_noticeNo.setText(oldData.booking_noticeNo)
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
                      delete_OAFileDeliveryRecord("OAFileDeliveryRecordBody",data[adapterPosition])
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

            //下一筆
            next_btn.setOnClickListener {
                if(adapterPosition+1<=data.size){
                    cookie_data.recyclerView.smoothScrollToPosition(adapterPosition+1)
                }

            }

            //單身
            book_info_btn.setOnClickListener {
                cookie_data.second_recyclerView=cookie_data.recyclerView
                var dialogF= PopUpBookInfoFragment(oa_referenceNO1.text.toString())
                dialogF.show( ( getActivity() as AppCompatActivity).supportFragmentManager ,"訂艙資訊")
            }

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
                     lock_OAFileDeliveryRecord("OAFileDeliveryRecordBody",data[adapterPosition])
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
                     over_OAFileDeliveryRecord("OAFileDeliveryRecordBody",data[adapterPosition])
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
        private fun edit_OAFileDeliveryRecord(operation:String,oldData:OAFileDeliveryRecordBody,newData:OAFileDeliveryRecordBody) {
            val old =JSONObject()
            old.put("_id",oldData._id)
            old.put("trackingNo",oldData.trackingNo)
            old.put("booking_noticeNo",oldData.booking_noticeNo)


            old.put("remark",oldData.remark)
            val new =JSONObject()
            new.put("_id",newData._id)
            new.put("trackingNo",newData.trackingNo)
            new.put("booking_noticeNo",newData.booking_noticeNo)

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
        private fun delete_OAFileDeliveryRecord(operation:String,deleteData:OAFileDeliveryRecordBody) {
            val delete = JSONObject()
            delete.put("_id",deleteData._id)
            delete.put("trackingNo",deleteData.trackingNo)
            delete.put("booking_noticeNo",deleteData.booking_noticeNo)

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
        private fun lock_OAFileDeliveryRecord(operation:String,lockData:OAFileDeliveryRecordBody) {
            val lock = JSONObject()
            lock.put("_id",lockData._id)
            lock.put("trackingNo",lockData.trackingNo)
            lock.put("booking_noticeNo",lockData.booking_noticeNo)

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
        private fun over_OAFileDeliveryRecord(operation:String,overData:OAFileDeliveryRecordBody) {
            val over = JSONObject()
            over.put("_id",overData._id)
            over.put("trackingNo",overData.trackingNo)
            over.put("booking_noticeNo",overData.booking_noticeNo)

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
    fun addItem(addData:OAFileDeliveryRecordBody){
        data.add(data.size,addData)
        notifyItemInserted(data.size)
        cookie_data.recyclerView.smoothScrollToPosition(data.size)
        // itemData.count+=1//cookie_data.itemCount+=1
    }

}


