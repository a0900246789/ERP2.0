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
import java.util.*

class RecyclerItemOnlineListAdapter() :
    RecyclerView.Adapter<RecyclerItemOnlineListAdapter.ViewHolder>() {
    private var itemData: ShowProductControlOrderBody_B =Gson().fromJson(cookie_data.response_data, ShowProductControlOrderBody_B::class.java)
    private var data: ArrayList<ProductControlOrderBody_B> =itemData.data
    init {
        // println(SelectFilter)
        //data=filter(data,FilterTopic,FilterContent)
        data=sort(data)

    }

    fun sort(data: java.util.ArrayList<ProductControlOrderBody_B>): java.util.ArrayList<ProductControlOrderBody_B> {
      var sortedList:List<ProductControlOrderBody_B> = data

      sortedList  = data.sortedWith(
          compareBy(
              { it.qr_code },
          )
      )

      return sortedList.toCollection(java.util.ArrayList())
  }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemOnlineListAdapter.ViewHolder {
        val v= LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_online_list,parent,false)
        return ViewHolder(v)

    }

    override fun onBindViewHolder(holder: RecyclerItemOnlineListAdapter.ViewHolder, position: Int) {

        holder.staff_name.setText(data[position].staff_name+"-"+data[position].qr_code+"\n(上)"+data[position].online_time+"\n(下)"+data[position].offline_time)
        holder.staff_name.isClickable=false
        holder.staff_name.isFocusable=false
        holder.staff_name.isTextInputLayoutFocusedRectEnabled=false

    }

    override fun getItemCount(): Int {
        return data.size//cookie_data.itemCount
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var staff_name=itemView.findViewById<TextInputEditText>(R.id.edit_staff_name)




        lateinit var oldData:ProductControlOrderBody_B
        lateinit var newData:ProductControlOrderBody_B
        init {
            itemView.setOnClickListener{
                val position:Int=adapterPosition
                Log.d("GSON", "msg: ${position}\n")

            }


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

    }


}


