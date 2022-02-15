package com.example.erp20.app13
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
import java.text.SimpleDateFormat


class RecyclerItemCustomerOrderHeaderAdapter(Filter_poNo:String) :
    RecyclerView.Adapter<RecyclerItemCustomerOrderHeaderAdapter.ViewHolder>() {
    private var itemData: ShowCustomerOrderHeader =Gson().fromJson(cookie_data.response_data, ShowCustomerOrderHeader::class.java)
    private var data: ArrayList<CustomerOrderHeader> =itemData.data
    var relativeCombobox01=cookie_data.customer_id_ComboboxData
    var relativeCombobox02=cookie_data.cont_id_ComboboxData
    val dateF = SimpleDateFormat("yyyy-MM-dd(EEEE)", Locale.TAIWAN)


    init {
        // println(SelectFilter)
        data=filter(data,Filter_poNo)
        data=sort(data)

    }
    fun filter(data: java.util.ArrayList<CustomerOrderHeader>, filter_poNo:String): java.util.ArrayList<CustomerOrderHeader> {
        var ArrayList: java.util.ArrayList<CustomerOrderHeader> =
            java.util.ArrayList<CustomerOrderHeader>()
        for(i in 0 until data.size){
            if(data[i].poNo==filter_poNo){
                ArrayList.add(data[i])
            }
        }
        return ArrayList
    }
    fun sort(data: java.util.ArrayList<CustomerOrderHeader>): java.util.ArrayList<CustomerOrderHeader> {
        var sortedList:List<CustomerOrderHeader> = data

        sortedList  = data.sortedWith(
            compareBy(
                { it.poNo },
            )
        )

        return sortedList.toCollection(java.util.ArrayList())
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v= LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_customer_order_header_simplify,parent,false)
        return ViewHolder(v)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        //Log.d("GSON", "msg: ${itemData.data[position]}\n")
        holder.poNo.setText(data[position].poNo)
        holder.poNo.inputType=InputType.TYPE_NULL
        if(data[position].sws!=null){
            //date
            var readDateString=data[position].sws
            //println(readDateString)
            val readDate=Calendar.getInstance()
            //println(readDateString.subSequence(0,4))
            readDate.set(Calendar.YEAR,readDateString!!.subSequence(0,4).toString().toInt())//yyyy
            // println(readDateString.subSequence(5,7))
            readDate.set(Calendar.MONTH,readDateString!!.subSequence(5,7).toString().toInt()-1)//MM
            // println(readDateString.subSequence(8,10))
            readDate.set(Calendar.DAY_OF_MONTH,readDateString!!.subSequence(8,10).toString().toInt())//dd
            var date=dateF.format(readDate.time)
            holder.sws.setText(date)
        }
        else{
            holder.sws.setText(null)
        }
        holder.sws.inputType=InputType.TYPE_NULL

        if(data[position].swe!=null){
            //date
            var readDateString=data[position].swe
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
        holder.cont_count.setText(data[position].cont_count.toString())
        holder.cont_count.inputType=InputType.TYPE_NULL
        holder.is_closed.setText(data[position].is_closed.toString())
        holder.is_closed.inputType=InputType.TYPE_NULL
        holder.invalid.setText(data[position].invalid.toString())
        holder.invalid.inputType=InputType.TYPE_NULL

        if(data[position].order_date!=null){
            //date
            var readDateString=data[position].order_date
            //println(readDateString)
            val readDate=Calendar.getInstance()
            //println(readDateString.subSequence(0,4))
            readDate.set(Calendar.YEAR,readDateString!!.subSequence(0,4).toString().toInt())//yyyy
            // println(readDateString.subSequence(5,7))
            readDate.set(Calendar.MONTH,readDateString!!.subSequence(5,7).toString().toInt()-1)//MM
            // println(readDateString.subSequence(8,10))
            readDate.set(Calendar.DAY_OF_MONTH,readDateString!!.subSequence(8,10).toString().toInt())//dd
            var date=dateF.format(readDate.time)
            holder.order_date.setText(date)
        }
        else{
            holder.order_date.setText(null)
        }
        holder.order_date.inputType=InputType.TYPE_NULL

        holder.customer_id.setText(data[position].customer_id)
        holder.customer_id.inputType=InputType.TYPE_NULL


        /*holder.start_cont_id.setText(data[position].start_cont_id)
        holder.start_cont_id.inputType=InputType.TYPE_NULL
        holder.end_cont_id .setText(data[position].end_cont_id )
        holder.end_cont_id .inputType=InputType.TYPE_NULL
        holder.is_urgent.setText(data[position].is_urgent.toString())
        holder.is_urgent.isClickable=false
        holder.is_urgent.inputType=InputType.TYPE_NULL
        holder.urgent_deadline.setText(data[position].urgent_deadline)
        holder.urgent_deadline.inputType=InputType.TYPE_NULL



        holder.creator.setText(data[position].creator)
        holder.creator_time.setText(data[position].create_time)
        holder.editor.setText(data[position].editor)
        holder.editor_time.setText(data[position].edit_time)
        holder.lock.setText(data[position].Lock.toString())
        holder.lock_time.setText(data[position].lock_time)*/

        //holder.invalid_time.setText(data[position].invalid_time)

        //holder.close_time.setText(data[position].close_time)

        holder.remark.setText(data[position].remark)
        holder.remark.isClickable=false
        holder.remark.isFocusable=false
        holder.remark.isFocusableInTouchMode=false
        holder.remark.isTextInputLayoutFocusedRectEnabled=false


        holder.next_btn.isVisible=true
        if(!data[position].invalid){
            holder.book_detail_btn.isVisible=true
        }

        /*holder.deletebtn.isVisible=true
        holder.edit_btn.isVisible=true
        holder.lockbtn.isVisible=true
        holder.overbtn.isVisible=true*/

    }

    override fun getItemCount(): Int {
        return data.size//cookie_data.itemCount
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var poNo=itemView.findViewById<TextInputEditText>(R.id.edit_poNo)
        var sws=itemView.findViewById<TextInputEditText>(R.id.edit_sws)
        var swe=itemView.findViewById<TextInputEditText>(R.id.edit_swe)
        var cont_count=itemView.findViewById<TextInputEditText>(R.id.edit_cont_count)
        var is_closed=itemView.findViewById<TextInputEditText>(R.id.edit_is_closed)
        var invalid=itemView.findViewById<TextInputEditText>(R.id.edit_invalid)
        var order_date=itemView.findViewById<TextInputEditText>(R.id.edit_order_date)
        var customer_id=itemView.findViewById<TextInputEditText>(R.id.edit_customer_id)

        /*var start_cont_id=itemView.findViewById<AutoCompleteTextView>(R.id.edit_start_cont_id)
        var end_cont_id=itemView.findViewById<AutoCompleteTextView>(R.id.edit_end_cont_id)
        var is_urgent=itemView.findViewById<AutoCompleteTextView>(R.id.edit_is_urgent)
        var urgent_deadline=itemView.findViewById<TextInputEditText>(R.id.edit_urgent_deadline)*/

        var next_btn=itemView.findViewById<Button>(R.id.next_btn)
        var book_detail_btn=itemView.findViewById<Button>(R.id.book_detail_btn)
    /*
        //var edit_btn=itemView.findViewById<Button>(R.id.edit_btn)
        //var deletebtn=itemView.findViewById<Button>(R.id.delete_btn)
        //var lockbtn=itemView.findViewById<Button>(R.id.lock_btn)
        //var overbtn=itemView.findViewById<Button>(R.id.over_btn)
        //var creator=itemView.findViewById<AutoCompleteTextView>(R.id.edit_creator)
        //var creator_time=itemView.findViewById<AutoCompleteTextView>(R.id.edit_create_time)
        //var editor=itemView.findViewById<AutoCompleteTextView>(R.id.edit_editor)
        //var editor_time=itemView.findViewById<AutoCompleteTextView>(R.id.edit_edit_time)
        //var lock=itemView.findViewById<AutoCompleteTextView>(R.id.edit_lock)
       // var lock_time=itemView.findViewById<AutoCompleteTextView>(R.id.edit_lock_time)*/


       // var invalid_time=itemView.findViewById<AutoCompleteTextView>(R.id.edit_invalid_time)

        //var close_time=itemView.findViewById<AutoCompleteTextView>(R.id.edit_close_time)
        var remark=itemView.findViewById<TextInputEditText>(R.id.edit_remark)
        lateinit var oldData:CustomerOrderHeader
        lateinit var newData:CustomerOrderHeader
        init {
            itemView.setOnClickListener{
                val position:Int=adapterPosition
                Log.d("GSON", "msg: ${position}\n")

            }

            val combobox=itemView.resources.getStringArray(R.array.combobox_yes_no)
            val arrayAdapter= ArrayAdapter(itemView.context,R.layout.combobox_item,combobox)
            val arrayAdapter01=ArrayAdapter(itemView.context,R.layout.combobox_item,relativeCombobox01)
            val arrayAdapter02=ArrayAdapter(itemView.context,R.layout.combobox_item,relativeCombobox02)

            //下一筆
            next_btn.setOnClickListener {
                if(adapterPosition+1<=data.size){
                    cookie_data.recyclerView.smoothScrollToPosition(adapterPosition+1)
                }

            }

            //單身
            book_detail_btn.setOnClickListener {
                cookie_data.first_recyclerView=cookie_data.recyclerView
                println(data[adapterPosition].poNo)
                println( cookie_data.BookingNoticeHeader_customer_poNo_ComboboxData)


                if(cookie_data.BookingNoticeHeader_customer_poNo_ComboboxData.indexOf(data[adapterPosition].poNo)!=-1){
                    var dialogF= PopUpCustomerOrderHeaderBookDeatailFragment(
                        cookie_data.OAFileDeliveryRecordBody_trackingNo_ComboboxData[
                                cookie_data.OAFileDeliveryRecordBody_booking_noticeNo_ComboboxData.indexOf(
                                    cookie_data.BookingNoticeHeader_notice_numbe_ComboboxData[
                                            cookie_data.BookingNoticeHeader_customer_poNo_ComboboxData.indexOf(data[adapterPosition].poNo)
                                    ])],data[adapterPosition].poNo)
                    dialogF.show( ( itemView.context as AppCompatActivity).supportFragmentManager ,"訂艙明細")
                }
                else{
                    var dialogF= PopUpCustomerOrderHeaderBookDeatailFragment("",data[adapterPosition].poNo)
                    dialogF.show( ( itemView.context as AppCompatActivity).supportFragmentManager ,"訂艙明細")
                }
               /* */
            }


            /*  //編輯按鈕
              edit_btn.setOnClickListener {
                  when(edit_btn.text){
                      "編輯"->{
                          oldData=itemData.data[adapterPosition]
                          //Log.d("GSON", "msg: ${oldData.id}\n")
                          poNo.inputType=InputType.TYPE_CLASS_TEXT
                          order_date.inputType=InputType.TYPE_DATETIME_VARIATION_DATE
                          customer_id.setAdapter(arrayAdapter01)
                          cont_count.inputType=InputType.TYPE_CLASS_NUMBER
                          start_cont_id.setAdapter(arrayAdapter02)
                          end_cont_id.setAdapter(arrayAdapter02)
                          is_urgent.setAdapter(arrayAdapter)
                          is_urgent.isClickable=true
                          urgent_deadline.inputType=InputType.TYPE_CLASS_DATETIME


                          remark.isClickable=true
                          remark.isFocusable=true
                          remark.isFocusableInTouchMode=true
                          remark.isTextInputLayoutFocusedRectEnabled=true
                          poNo.requestFocus()
                          edit_btn.text = "完成"
                      }
                      "完成"->{
                          newData= CustomerOrderHeader()
                          newData.poNo=poNo.text.toString()
                          poNo.inputType=InputType.TYPE_NULL
                          newData.order_date=order_date.text.toString()
                          order_date.inputType=InputType.TYPE_NULL
                          newData.customer_id=customer_id.text.toString()
                          customer_id.setAdapter(null)
                          newData.cont_count=cont_count.text.toString().toInt()
                          cont_count.inputType=InputType.TYPE_NULL
                          newData.start_cont_id=start_cont_id.text.toString()
                          start_cont_id.setAdapter(null)
                          newData.end_cont_id=end_cont_id.text.toString()
                          end_cont_id.setAdapter(null)
                          newData.is_urgent=is_urgent.text.toString().toBoolean()
                          is_urgent.isClickable=false
                          is_urgent.setAdapter(null)
                          if(urgent_deadline.text.toString()==""){
                              newData.urgent_deadline=null
                          }
                          else{
                              newData.urgent_deadline=urgent_deadline.text.toString()
                          }
                          urgent_deadline.inputType=InputType.TYPE_NULL



                          newData.remark=remark.text.toString()
                          remark.isClickable=false
                          remark.isFocusable=false
                          remark.isFocusableInTouchMode=false
                          remark.isTextInputLayoutFocusedRectEnabled=false
                          //Log.d("GSON", "msg: ${oldData}\n")
                          //Log.d("GSON", "msg: ${newData.remark}\n")
                          edit_CustomerOrder("CustomerOrder","header",oldData,newData)//更改資料庫資料
                          when(cookie_data.status){
                              0-> {//成功
                                  itemData.data[adapterPosition] = newData//更改渲染資料
                                  editor.setText(cookie_data.username)
                                  Toast.makeText(itemView.context, cookie_data.msg, Toast.LENGTH_SHORT).show()
                              }
                              1->{//失敗
                                  Toast.makeText(itemView.context, cookie_data.msg, Toast.LENGTH_SHORT).show()
                                  poNo.setText(oldData.poNo)
                                  order_date.setText(oldData.order_date)
                                  customer_id.setText(oldData.customer_id)
                                  cont_count.setText(oldData.cont_count.toString())
                                  start_cont_id.setText(oldData.start_cont_id)
                                  end_cont_id.setText(oldData.end_cont_id)
                                  is_urgent.setText(oldData.is_urgent.toString())
                                  urgent_deadline.setText(oldData.urgent_deadline)



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
                      delete_CustomerOrder("CustomerOrder","header",data[adapterPosition])
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
                      lock_CustomerOrder("CustomerOrder","header",data[adapterPosition])
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
                      over_CustomerOrder("CustomerOrder","header",data[adapterPosition])
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
        private fun edit_CustomerOrder(operation:String,target:String,oldData:CustomerOrderHeader,newData:CustomerOrderHeader) {
            val old =JSONObject()
            old.put("poNo",oldData.poNo)
            old.put("order_date",oldData.order_date)
            old.put("customer_id",oldData.customer_id)
            old.put("cont_count",oldData.cont_count)
            old.put("start_cont_id",oldData.start_cont_id)
            old.put("end_cont_id",oldData.end_cont_id)
            old.put("is_urgent",oldData.is_urgent)
            old.put("urgent_deadline",oldData.urgent_deadline)

            old.put("remark",oldData.remark)
            val new =JSONObject()
            new.put("poNo",newData.poNo)
            new.put("order_date",newData.order_date)
            new.put("customer_id",newData.customer_id)
            new.put("cont_count",newData.cont_count)
            new.put("start_cont_id",newData.start_cont_id)
            new.put("end_cont_id",newData.end_cont_id)
            new.put("is_urgent",newData.is_urgent)
            new.put("urgent_deadline",newData.urgent_deadline)

            new.put("editor",cookie_data.username)
            new.put("remark",newData.remark)
            val data = JSONArray()
            data.put(old)
            data.put((new))
            val body = FormBody.Builder()
                .add("data",data.toString())
                .add("username", cookie_data.username)
                .add("operation", operation)
                .add("target",target)
                .add("action",cookie_data.Actions.CHANGE)
                .add("csrfmiddlewaretoken", cookie_data.tokenValue)
                .add("login_flag", cookie_data.loginflag)
                .build()
            val request = Request.Builder()
                .url(cookie_data.URL+"/custom_order_management")
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
        private fun delete_CustomerOrder(operation:String,target:String,deleteData:CustomerOrderHeader) {
            val delete = JSONObject()
            delete.put("poNo",deleteData.poNo)
            delete.put("order_date",deleteData.order_date)
            delete.put("customer_id",deleteData.customer_id)
            delete.put("cont_count",deleteData.cont_count)
            delete.put("start_cont_id",deleteData.start_cont_id)
            delete.put("end_cont_id",deleteData.end_cont_id)
            delete.put("is_urgent",deleteData.is_urgent)
            delete.put("urgent_deadline",deleteData.urgent_deadline)

            delete.put("remark",deleteData.remark)
            //Log.d("GSON", "msg:${delete}")
            val body = FormBody.Builder()
                .add("username", cookie_data.username)
                .add("operation", operation)
                .add("target", target)
                .add("action",cookie_data.Actions.DELETE)
                .add("data",delete.toString())
                .add("csrfmiddlewaretoken", cookie_data.tokenValue)
                .add("login_flag", cookie_data.loginflag)
                .build()
            val request = Request.Builder()
                .url(cookie_data.URL+"/custom_order_management")
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
        private fun lock_CustomerOrder(operation:String,target:String,lockData:CustomerOrderHeader) {
            val lock = JSONObject()
            lock.put("poNo",lockData.poNo)
            lock.put("order_date",lockData.order_date)
            lock.put("customer_id",lockData.customer_id)
            lock.put("cont_count",lockData.cont_count)
            lock.put("start_cont_id",lockData.start_cont_id)
            lock.put("end_cont_id",lockData.end_cont_id)
            lock.put("is_urgent",lockData.is_urgent)
            lock.put("urgent_deadline",lockData.urgent_deadline)

            lock.put("remark",lockData.remark)
            //Log.d("GSON", "msg:${delete}")
            val body = FormBody.Builder()
                .add("username", cookie_data.username)
                .add("operation", operation)
                .add("target", target)
                .add("action",cookie_data.Actions.LOCK)
                .add("data",lock.toString())
                .add("csrfmiddlewaretoken", cookie_data.tokenValue)
                .add("login_flag", cookie_data.loginflag)
                .build()
            val request = Request.Builder()
                .url(cookie_data.URL+"/custom_order_management")
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
        private fun over_CustomerOrder(operation:String,target:String,overData:CustomerOrderHeader) {
            val over = JSONObject()
            over.put("poNo",overData.poNo)
            over.put("order_date",overData.order_date)
            over.put("customer_id",overData.customer_id)
            over.put("cont_count",overData.cont_count)
            over.put("start_cont_id",overData.start_cont_id)
            over.put("end_cont_id",overData.end_cont_id)
            over.put("is_urgent",overData.is_urgent)
            over.put("urgent_deadline",overData.urgent_deadline)

            over.put("remark",overData.remark)
            //Log.d("GSON", "msg:${delete}")
            val body = FormBody.Builder()
                .add("username", cookie_data.username)
                .add("operation", operation)
                .add("target", target)
                .add("action",cookie_data.Actions.CLOSE)
                .add("data",over.toString())
                .add("csrfmiddlewaretoken", cookie_data.tokenValue)
                .add("login_flag", cookie_data.loginflag)
                .build()
            val request = Request.Builder()
                .url(cookie_data.URL+"/custom_order_management")
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
    fun addItem(addData:CustomerOrderHeader){
        data.add(itemData.count,addData)
        notifyItemInserted(itemData.count)
        itemData.count+=1//cookie_data.itemCount+=1
    }

}


