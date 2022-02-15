package com.example.erp20.app13
import android.app.DatePickerDialog
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


class RecyclerItemOAReferenceAdapter(filterOA1:String) :
    RecyclerView.Adapter<RecyclerItemOAReferenceAdapter.ViewHolder>() {
    private var itemData: ShowOAReference =Gson().fromJson(cookie_data.response_data, ShowOAReference::class.java)
    private var data: ArrayList<OAReference> =itemData.data
    var relativeCombobox01=cookie_data.shipping_number_ComboboxData
    var relativeCombobox02=cookie_data.CustomerOrderHeader_poNo_ComboboxData
    val dateF = SimpleDateFormat("yyyy-MM-dd(EEEE)", Locale.TAIWAN)
    val timeF = SimpleDateFormat("HH:mm:ss", Locale.TAIWAN)
    val dateF2 = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    var filterOA1=filterOA1
    init {
        // println(SelectFilter)
        data=filter(data,filterOA1)
        data=sort(data)

    }
    fun filter(data: java.util.ArrayList<OAReference>, FilterOA1:String): java.util.ArrayList<OAReference> {
        var ArrayList: java.util.ArrayList<OAReference> =
            java.util.ArrayList<OAReference>()
        for(i in 0 until data.size){
            if(data[i].oa_referenceNO2==FilterOA1){
                ArrayList.add(data[i])
            }
        }

        return ArrayList
    }
    fun sort(data: java.util.ArrayList<OAReference>): java.util.ArrayList<OAReference> {
        var sortedList:List<OAReference> = data

        sortedList  = data.sortedWith(
            compareBy(
                { it.oa_referenceNO2 },
            )
        )

        return sortedList.toCollection(java.util.ArrayList())
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v= LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_oa_reference_no,parent,false)
        return ViewHolder(v)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        holder.oa_referenceNO1.setText(data[position].oa_referenceNO2)
        holder.oa_referenceNO1.inputType=InputType.TYPE_NULL
        holder.oa_referenceNO2.setText(data[position].oa_referenceNO3)
        holder.oa_referenceNO2.inputType=InputType.TYPE_NULL
        holder.oa_referenceNO3.setText(data[position].oa_referenceNO4)
        holder.oa_referenceNO3.inputType=InputType.TYPE_NULL
        holder.invalid.setText(data[position].invalid.toString())
        holder.invalid.inputType=InputType.TYPE_NULL



        holder.remark.setText(data[position].remark)
        holder.remark.isClickable=false
        holder.remark.isFocusable=false
        holder.remark.isFocusableInTouchMode=false
        holder.remark.isTextInputLayoutFocusedRectEnabled=false
        holder.deletebtn.isVisible=true
        holder.edit_btn.isVisible=true
        holder.next_btn.isVisible=true
        holder.book_detail_btn.isVisible=true
    }

    override fun getItemCount(): Int {
        return data.size//cookie_data.itemCount
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        var oa_referenceNO1=itemView.findViewById<TextInputEditText>(R.id.edit_oa_referenceNO1)
        var oa_referenceNO2=itemView.findViewById<TextInputEditText>(R.id.edit_oa_referenceNO2)
        var oa_referenceNO3=itemView.findViewById<TextInputEditText>(R.id.edit_oa_referenceNO3)

        var invalid=itemView.findViewById<TextInputEditText>(R.id.edit_invalid)

        var edit_btn=itemView.findViewById<Button>(R.id.edit_btn)
        var deletebtn=itemView.findViewById<Button>(R.id.delete_btn)
        var next_btn=itemView.findViewById<Button>(R.id.next_btn)
        var book_detail_btn=itemView.findViewById<Button>(R.id.book_detail_btn)

        var remark=itemView.findViewById<TextInputEditText>(R.id.edit_remark)
        lateinit var oldData:OAReference
        lateinit var newData:OAReference
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
                        oldData=data[adapterPosition]

                        oa_referenceNO1.inputType=InputType.TYPE_CLASS_TEXT
                        oa_referenceNO2.inputType=InputType.TYPE_CLASS_TEXT
                        oa_referenceNO3.inputType=InputType.TYPE_CLASS_TEXT

                        remark.isClickable=true
                        remark.isFocusable=true
                        remark.isFocusableInTouchMode=true
                        remark.isTextInputLayoutFocusedRectEnabled=true
                        oa_referenceNO1.requestFocus()
                        edit_btn.text = "完成"
                    }
                    "完成"->{

                        newData= oldData.copy()//BookingNoticeHeader()

                        //newData.oa_referenceNO1=oa_referenceNO1.text.toString()+"-"+oa_referenceNO2.text.toString()+"-"+oa_referenceNO3.text.toString()


                        newData.oa_referenceNO2=oa_referenceNO1.text.toString()
                        oa_referenceNO1.inputType=InputType.TYPE_NULL

                        newData.oa_referenceNO3=oa_referenceNO2.text.toString()
                        oa_referenceNO2.inputType=InputType.TYPE_NULL

                        newData.oa_referenceNO4=oa_referenceNO3.text.toString()
                        oa_referenceNO3.inputType=InputType.TYPE_NULL


                        newData.remark=remark.text.toString()
                        remark.isClickable=false
                        remark.isFocusable=false
                        remark.isFocusableInTouchMode=false
                        remark.isTextInputLayoutFocusedRectEnabled=false
                        //Log.d("GSON", "msg: ${oldData}\n")
                        //Log.d("GSON", "msg: ${newData.remark}\n")
                        edit_OAReference("OAReference",oldData,newData)//更改資料庫資料
                        when(cookie_data.status){
                            0-> {//成功
                                data[adapterPosition] = newData//更改渲染資料
                                Toast.makeText(itemView.context, cookie_data.msg, Toast.LENGTH_SHORT).show()
                            }
                            1->{//失敗
                                Toast.makeText(itemView.context, cookie_data.msg, Toast.LENGTH_SHORT).show()
                                oa_referenceNO1.setText(oldData.oa_referenceNO2)
                                oa_referenceNO2.setText(oldData.oa_referenceNO3)
                                oa_referenceNO3.setText(oldData.oa_referenceNO4)
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
                    delete_OAReference("OAReference",data[adapterPosition])
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

            //訂艙單頭
            book_detail_btn.setOnClickListener {
                cookie_data.first_recyclerView=cookie_data.recyclerView
                var dialogF= PopUpBookDetailFragment(oa_referenceNO1.text.toString())
                dialogF.show( ( itemView.context as AppCompatActivity).supportFragmentManager ,"訂艙明細")
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
                     lock_BookingNotice("OAReference",data[adapterPosition])
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
                     over_BookingNotice("OAReference",data[adapterPosition])
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
        private fun edit_OAReference(operation:String,oldData:OAReference,newData:OAReference) {
            val old =JSONObject()
            //old.put("oa_referenceNO1",oldData.oa_referenceNO1)
            old.put("oa_referenceNO2",oldData.oa_referenceNO2)
            old.put("oa_referenceNO3",oldData.oa_referenceNO3)
            old.put("oa_referenceNO4",oldData.oa_referenceNO4)

            old.put("remark",oldData.remark)
            val new =JSONObject()
            //new.put("oa_referenceNO1",newData.oa_referenceNO1)
            new.put("oa_referenceNO2",newData.oa_referenceNO2)
            new.put("oa_referenceNO3",newData.oa_referenceNO3)
            new.put("oa_referenceNO4",newData.oa_referenceNO4)

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
        private fun delete_OAReference(operation:String,deleteData:OAReference) {
            val delete = JSONObject()
            delete.put("oa_referenceNO1",deleteData.oa_referenceNO1)
            delete.put("oa_referenceNO2",deleteData.oa_referenceNO2)
            delete.put("oa_referenceNO3",deleteData.oa_referenceNO3)
            delete.put("oa_referenceNO4",deleteData.oa_referenceNO4)

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
        private fun lock_OAReference(operation:String,lockData:OAReference) {
            val lock = JSONObject()
            lock.put("oa_referenceNO1",lockData.oa_referenceNO1)
            lock.put("oa_referenceNO2",lockData.oa_referenceNO2)
            lock.put("oa_referenceNO3",lockData.oa_referenceNO3)
            lock.put("oa_referenceNO4",lockData.oa_referenceNO4)

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
        private fun over_OAReference(operation:String,overData:OAReference) {
            val over = JSONObject()
            over.put("oa_referenceNO1",overData.oa_referenceNO1)
            over.put("oa_referenceNO2",overData.oa_referenceNO2)
            over.put("oa_referenceNO3",overData.oa_referenceNO3)
            over.put("oa_referenceNO4",overData.oa_referenceNO4)

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


    }
    fun addItem(addData:OAReference){
        data.add(data.size,addData)
        notifyItemInserted(data.size)
        cookie_data.recyclerView.smoothScrollToPosition(data.size)
        // itemData.count+=1//cookie_data.itemCount+=1
    }

}


