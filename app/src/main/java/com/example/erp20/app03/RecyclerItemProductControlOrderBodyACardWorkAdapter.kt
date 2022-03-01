package com.example.erp20.RecyclerAdapter
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
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
import com.example.erp20.app03.PopUpCardWorkFragment
import com.example.erp20.app03.PopUpOnlineListFragment
import com.example.erp20.app03.QrCodeCardWorkFragment
import com.example.erp20.app04.PopUpOutSourceFragment
import com.google.android.material.internal.ContextUtils.getActivity
import java.text.SimpleDateFormat


class RecyclerItemProductControlOrderBodyACardWorkAdapter() :
    RecyclerView.Adapter<RecyclerItemProductControlOrderBodyACardWorkAdapter.ViewHolder>() {
    private var itemData: ShowProductControlOrderBody_A =Gson().fromJson(cookie_data.response_data, ShowProductControlOrderBody_A::class.java)
    private var data: ArrayList<ProductControlOrderBody_A> =itemData.data

    val dateF = SimpleDateFormat("yyyy-MM-dd(EEEE)", Locale.TAIWAN)
    val timeF = SimpleDateFormat("HH:mm:ss", Locale.TAIWAN)
    val df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.TAIWAN)
    init {
        // println(SelectFilter)
        //data=sort(data,SelectFilter)

    }
   /* fun sort(data:ArrayList<ProductControlOrderBody_A>,filter:String): ArrayList<ProductControlOrderBody_A>{
        var sortedList:List<ProductControlOrderBody_A> = data
        sortedList  = data.sortedWith(
            compareBy(
                { it.latest_inspection_day},
                { it.header_id},
                { it.est_start_date}
            )
        )
        return sortedList.toCollection(java.util.ArrayList())
    }*/

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemProductControlOrderBodyACardWorkAdapter.ViewHolder {
        val v= LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_product_control_order_body_a_card_work,parent,false)
        return ViewHolder(v)

    }

    override fun onBindViewHolder(holder: RecyclerItemProductControlOrderBodyACardWorkAdapter.ViewHolder, position: Int) {



        holder.prod_ctrl_order_number.setText(data[position].prod_ctrl_order_number)
        holder.prod_ctrl_order_number.inputType=InputType.TYPE_NULL

        if(cookie_data.item_id_ComboboxData.indexOf(data[position].semi_finished_prod_number)!=-1){
            holder.semi_finished_prod_number.setText(data[position].semi_finished_prod_number+" "+
                    cookie_data.item_name_ComboboxData[
                            cookie_data.item_id_ComboboxData.indexOf(
                                data[position].semi_finished_prod_number)])
        }
        else{
            holder.semi_finished_prod_number.setText(data[position].semi_finished_prod_number)
        }
        holder.semi_finished_prod_number.inputType=InputType.TYPE_NULL
        holder.pline_id.setText(data[position].pline_id+" "+cookie_data.pline_name_ComboboxData[cookie_data.pline_id_ComboboxData.indexOf(data[position].pline_id)])
        holder.pline_id.inputType=InputType.TYPE_NULL

        if(data[position].est_start_date!=null){
            //date
            var readDateString=data[position].est_start_date
            //println(readDateString)
            val readDate=Calendar.getInstance()
            //println(readDateString.subSequence(0,4))
            readDate.set(Calendar.YEAR,readDateString!!.subSequence(0,4).toString().toInt())//yyyy
            // println(readDateString.subSequence(5,7))
            readDate.set(Calendar.MONTH,readDateString!!.subSequence(5,7).toString().toInt()-1)//MM
            // println(readDateString.subSequence(8,10))
            readDate.set(Calendar.DAY_OF_MONTH,readDateString!!.subSequence(8,10).toString().toInt())//dd
            var date=dateF.format(readDate.time)
            holder.startDate=date
            holder.est_start_date.setText(date)
        }
        else{
            holder.est_start_date.setText(null)
        }
        holder.est_start_date.inputType=InputType.TYPE_NULL
        if(data[position].est_complete_date!=null){
            //date
            var readDateString=data[position].est_complete_date
            //println(readDateString)
            val readDate=Calendar.getInstance()
            //println(readDateString.subSequence(0,4))
            readDate.set(Calendar.YEAR,readDateString!!.subSequence(0,4).toString().toInt())//yyyy
            // println(readDateString.subSequence(5,7))
            readDate.set(Calendar.MONTH,readDateString!!.subSequence(5,7).toString().toInt()-1)//MM
            // println(readDateString.subSequence(8,10))
            readDate.set(Calendar.DAY_OF_MONTH,readDateString!!.subSequence(8,10).toString().toInt())//dd
            var date=dateF.format(readDate.time)
            holder.EndDate=date
            holder.est_complete_date.setText(date)

        }
        else{
            holder.est_complete_date.setText(null)
        }

        holder.est_complete_date.inputType=InputType.TYPE_NULL

        if(cookie_data.MeBody_process_number_ComboboxData.indexOf(data[position].me_code)<0){
            holder.work_option.setText(null)
        }
        else{
            holder.work_option.setText(cookie_data.MeBody_work_option_ComboboxData[cookie_data.MeBody_process_number_ComboboxData.indexOf(data[position].me_code)])
        }
        holder.work_option.inputType=InputType.TYPE_NULL

        holder.est_output.setText(data[position].est_output.toString())
        holder.est_output.inputType=InputType.TYPE_NULL




        holder.QR_btn.isVisible=true
        holder.type_qr_btn.isVisible=true
        holder.all_offline_btn.isVisible=true
        holder.online_peaple_btn.isVisible=true

    }

    override fun getItemCount(): Int {
        return data.size//cookie_data.itemCount
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var prod_ctrl_order_number=itemView.findViewById<TextInputEditText>(R.id.edit_prod_ctrl_order_number)
        var semi_finished_prod_number=itemView.findViewById<TextInputEditText>(R.id.edit_semi_finished_prod_number)
        var est_output=itemView.findViewById<TextInputEditText>(R.id.edit_est_output)
        var work_option=itemView.findViewById<TextInputEditText>(R.id.edit_work_option)
        var pline_id=itemView.findViewById<TextInputEditText>(R.id.edit_pline_id)
        var est_start_date=itemView.findViewById<TextInputEditText>(R.id.edit_est_start_date)
        var est_complete_date=itemView.findViewById<TextInputEditText>(R.id.edit_est_complete_date)

        var QR_btn=itemView.findViewById<Button>(R.id.QR_btn)
        var type_qr_btn=itemView.findViewById<Button>(R.id.type_qr_btn)
        var all_offline_btn=itemView.findViewById<Button>(R.id.all_offline_btn)
        var online_peaple_btn=itemView.findViewById<Button>(R.id.online_peaple_btn)

        var editTime=false
        var startDate=""
        var EndDate=""
        lateinit var oldData:ProductControlOrderBody_A
        lateinit var newData:ProductControlOrderBody_A
        init {
            itemView.setOnClickListener{
                val position:Int=adapterPosition
                Log.d("GSON", "msg: ${position}\n")

            }

            val combobox=itemView.resources.getStringArray(R.array.combobox_yes_no)
            val arrayAdapter= ArrayAdapter(itemView.context,R.layout.combobox_item,combobox)
            QR_btn.setOnClickListener {
                var dialogF= QrCodeCardWorkFragment()
                dialogF.show( ( itemView.context as AppCompatActivity).supportFragmentManager ,"QRCode")
            }
            //手動QRcode
            type_qr_btn.setOnClickListener {
                //combobox show
                val item = LayoutInflater.from(itemView.context).inflate(R.layout.filter_combobox_type, null)
                val mAlertDialog = AlertDialog.Builder(itemView.context)
                mAlertDialog.setTitle("QRCode") //set alertdialog title
                mAlertDialog.setView(item)
                //filter_combobox選單內容
                val comboboxView=item.findViewById<TextInputEditText>(R.id.autoCompleteText)

                mAlertDialog.setPositiveButton("取消") { dialog, id ->
                    dialog.dismiss()
                }
                mAlertDialog.setNegativeButton("確定") { dialog, id ->
                    //println(comboboxView.text)
                    comboboxView.text.toString()
                    cookie_data.first_recyclerView=cookie_data.recyclerView

                    var P=pline_id.text.toString().substring(0,pline_id.text.toString().indexOf(" "))
                    var dialogF= PopUpCardWorkFragment(prod_ctrl_order_number.text.toString(),work_option.text.toString(),P, comboboxView.text.toString())
                    dialogF.show( ( getActivity() as AppCompatActivity).supportFragmentManager ,"上線刷卡紀錄")

                }
                mAlertDialog.show()
            }
            //全組下線
            all_offline_btn.setOnClickListener {
                val mAlertDialog = AlertDialog.Builder(itemView.context)
                mAlertDialog.setIcon(R.mipmap.ic_launcher_round) //set alertdialog icon
                mAlertDialog.setTitle("全組下線") //set alertdialog title
                mAlertDialog.setMessage("確定要全組下線?！") //set alertdialog message
                mAlertDialog.setPositiveButton("取消") { dialog, id ->
                    dialog.dismiss()
                }
                mAlertDialog.setNegativeButton("確定") { dialog, id ->
                    bundle_edit_ProductControlOrder("ProductControlOrderBody_B",prod_ctrl_order_number.text.toString())
                    when(cookie_data.status){
                        0-> {//成功
                            Toast.makeText(itemView.context, "全員下線成功", Toast.LENGTH_SHORT).show()

                        }
                        1->{//失敗
                            Toast.makeText(itemView.context, cookie_data.msg, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                mAlertDialog.show()
            }

            //在線人員名單
            online_peaple_btn.setOnClickListener {
                cookie_data.first_recyclerView=cookie_data.recyclerView
                var dialogF= PopUpOnlineListFragment(prod_ctrl_order_number.text.toString())
                dialogF.show( ( getActivity() as AppCompatActivity).supportFragmentManager ,"在線人員名單(查詢)")
            }

         /*
            est_start_date.setOnClickListener {
                if(editTime==true){
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
                            val date= dateF.format(SelectedDate.time)
                            est_start_date.setText(date)
                        },year,month,day).show()

                }
            }
            est_complete_date.setOnClickListener {
                if(editTime==true){
                    var c= Calendar.getInstance()
                    val year= c.get(Calendar.YEAR)
                    val month = c.get(Calendar.MONTH)
                    val day = c.get(Calendar.DAY_OF_MONTH)
                    var datePicker = DatePickerDialog(itemView.context,DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                        val SelectedDate=Calendar.getInstance()
                        SelectedDate.set(Calendar.YEAR,mYear)
                        SelectedDate.set(Calendar.MONTH,mMonth)
                        SelectedDate.set(Calendar.DAY_OF_MONTH,mDay)
                        val date= dateF.format(SelectedDate.time)
                        est_complete_date.setText(date)
                    },year,month,day).show()

                }
            }*/

         /*   //編輯按鈕
            edit_btn.setOnClickListener {
                when(edit_btn.text){
                    "編輯"->{
                        oldData=data[adapterPosition]

                        editTime=true
                        est_start_date.requestFocus()
                        edit_btn.text = "完成"
                    }
                    "完成"->{
                        editTime=false
                        newData=oldData.copy()

                        if(est_start_date.text.toString()==""){
                            newData.est_start_date=null
                        }
                        else{
                            newData.est_start_date=est_start_date.text.toString().substring(0,est_start_date.text.toString().indexOf("("))
                        }
                        est_start_date.inputType=InputType.TYPE_NULL
                        if(est_complete_date.text.toString()==""){
                            newData.est_complete_date=null
                        }
                        else{
                            newData.est_complete_date=est_complete_date.text.toString().substring(0,est_start_date.text.toString().indexOf("("))
                        }
                        est_complete_date.inputType=InputType.TYPE_NULL


                        //Log.d("GSON", "msg: ${oldData}\n")
                        //Log.d("GSON", "msg: ${newData.remark}\n")
                        edit_ProductControlOrder("ProductControlOrderBody_A",oldData,newData)//更改資料庫資料
                        when(cookie_data.status){
                            0-> {//成功
                                data[adapterPosition] = newData//更改渲染資料
                                //editor.setText(cookie_data.username)
                                Toast.makeText(itemView.context, cookie_data.msg, Toast.LENGTH_SHORT).show()
                            }
                            1->{//失敗
                                Toast.makeText(itemView.context, cookie_data.msg, Toast.LENGTH_SHORT).show()

                                est_start_date.setText(startDate)
                                est_complete_date.setText(EndDate)

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
                    delete_ProductControlOrder("ProductControlOrderBody_A",data[adapterPosition])
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
                      lock_ProductControlOrder("ProductControlOrderBody_A",data[adapterPosition])
                      when(cookie_data.status){
                          0-> {//成功
                             // lock.setText("true")
                             // lock_time.setText(Calendar.getInstance().getTime().toString())
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
                     over_ProductControlOrder("ProductControlOrderBody_A",data[adapterPosition])
                     when(cookie_data.status){
                         0-> {//成功
                             //is_closed.setText("true")
                             //close_time.setText(Calendar.getInstance().getTime().toString())
                             Toast.makeText(itemView.context, cookie_data.msg, Toast.LENGTH_SHORT).show()

                         }
                         1->{//失敗
                             Toast.makeText(itemView.context, cookie_data.msg, Toast.LENGTH_SHORT).show()
                         }
                     }
                 }
                 mAlertDialog.show()

             }*/

            out_source_btn.setOnClickListener {
                var dialogF= PopUpOutSourceFragment(data[adapterPosition].prod_ctrl_order_number)
                dialogF.show( ( itemView.context as AppCompatActivity).supportFragmentManager ,"批次檢視")
            }*/
        }
        private fun edit_ProductControlOrder(operation:String,oldData:ProductControlOrderBody_A,newData:ProductControlOrderBody_A) {
            val old =JSONObject()
            old.put("header_id",oldData.header_id)
            old.put("prod_ctrl_order_number",oldData.prod_ctrl_order_number)
            old.put("me_code",oldData.me_code)
            old.put("semi_finished_prod_number",oldData.semi_finished_prod_number)
            old.put("pline_id",oldData.pline_id)
            old.put("latest_inspection_day",oldData.latest_inspection_day)
            old.put("is_re_make",oldData.is_re_make)
            old.put("qc_date",oldData.qc_date)
            old.put("qc_number",oldData.qc_number)
            old.put("unit_of_measurement",oldData.unit_of_measurement)
            old.put("est_start_date",oldData.est_start_date)
            old.put("est_complete_date",oldData.est_complete_date)
            old.put("est_output",oldData.est_output)
            old.put("unit_of_timer",oldData.unit_of_timer)
            old.put("start_date",oldData.start_date)
            old.put("complete_date",oldData.complete_date)
            old.put("actual_output",oldData.actual_output)
            old.put("is_request_support",oldData.is_request_support)
            old.put("number_of_support",oldData.number_of_support)
            old.put("number_of_supported",oldData.number_of_supported)
            old.put("request_support_time",oldData.request_support_time)
            old.put("is_urgent",oldData.is_urgent)
            old.put("urgent_deadline",oldData.urgent_deadline)

            old.put("remark",oldData.remark)
            val new =JSONObject()
            new.put("header_id",newData.header_id)
            new.put("prod_ctrl_order_number",newData.prod_ctrl_order_number)
            new.put("me_code",newData.me_code)
            new.put("semi_finished_prod_number",newData.semi_finished_prod_number)
            new.put("pline_id",newData.pline_id)
            new.put("latest_inspection_day",newData.latest_inspection_day)
            new.put("is_re_make",newData.is_re_make)
            new.put("qc_date",newData.qc_date)
            new.put("qc_number",newData.qc_number)
            new.put("unit_of_measurement",newData.unit_of_measurement)
            new.put("est_start_date",newData.est_start_date)
            new.put("est_complete_date",newData.est_complete_date)
            new.put("est_output",newData.est_output)
            new.put("unit_of_timer",newData.unit_of_timer)
            new.put("start_date",newData.start_date)
            new.put("complete_date",newData.complete_date)
            new.put("actual_output",newData.actual_output)
            new.put("is_request_support",newData.is_request_support)
            new.put("number_of_support",newData.number_of_support)
            new.put("number_of_supported",newData.number_of_supported)
            new.put("request_support_time",newData.request_support_time)
            new.put("is_urgent",newData.is_urgent)
            new.put("urgent_deadline",newData.urgent_deadline)

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
        private fun delete_ProductControlOrder(operation:String,deleteData:ProductControlOrderBody_A) {
            val delete = JSONObject()
            delete.put("header_id",deleteData.header_id)
            delete.put("prod_ctrl_order_number",deleteData.prod_ctrl_order_number)
            delete.put("me_code",deleteData.me_code)
            delete.put("semi_finished_prod_number",deleteData.semi_finished_prod_number)
            delete.put("pline_id",deleteData.pline_id)
            delete.put("latest_inspection_day",deleteData.latest_inspection_day)
            delete.put("is_re_make",deleteData.is_re_make)
            delete.put("qc_date",deleteData.qc_date)
            delete.put("qc_number",deleteData.qc_number)
            delete.put("unit_of_measurement",deleteData.unit_of_measurement)
            delete.put("est_start_date",deleteData.est_start_date)
            delete.put("est_complete_date",deleteData.est_complete_date)
            delete.put("est_output",deleteData.est_output)
            delete.put("unit_of_timer",deleteData.unit_of_timer)
            delete.put("start_date",deleteData.start_date)
            delete.put("complete_date",deleteData.complete_date)
            delete.put("actual_output",deleteData.actual_output)
            delete.put("is_request_support",deleteData.is_request_support)
            delete.put("number_of_support",deleteData.number_of_support)
            delete.put("number_of_supported",deleteData.number_of_supported)
            delete.put("request_support_time",deleteData.request_support_time)
            delete.put("is_urgent",deleteData.is_urgent)
            delete.put("urgent_deadline",deleteData.urgent_deadline)

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
        private fun lock_ProductControlOrder(operation:String,lockData:ProductControlOrderBody_A) {
            val lock = JSONObject()
            lock.put("header_id",lockData.header_id)
            lock.put("prod_ctrl_order_number",lockData.prod_ctrl_order_number)
            lock.put("me_code",lockData.me_code)
            lock.put("semi_finished_prod_number",lockData.semi_finished_prod_number)
            lock.put("pline_id",lockData.pline_id)
            lock.put("latest_inspection_day",lockData.latest_inspection_day)
            lock.put("is_re_make",lockData.is_re_make)
            lock.put("qc_date",lockData.qc_date)
            lock.put("qc_number",lockData.qc_number)
            lock.put("unit_of_measurement",lockData.unit_of_measurement)
            lock.put("est_start_date",lockData.est_start_date)
            lock.put("est_complete_date",lockData.est_complete_date)
            lock.put("est_output",lockData.est_output)
            lock.put("unit_of_timer",lockData.unit_of_timer)
            lock.put("start_date",lockData.start_date)
            lock.put("complete_date",lockData.complete_date)
            lock.put("actual_output",lockData.actual_output)
            lock.put("is_request_support",lockData.is_request_support)
            lock.put("number_of_support",lockData.number_of_support)
            lock.put("number_of_supported",lockData.number_of_supported)
            lock.put("request_support_time",lockData.request_support_time)
            lock.put("is_urgent",lockData.is_urgent)
            lock.put("urgent_deadline",lockData.urgent_deadline)

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
        private fun over_ProductControlOrder(operation:String,overData:ProductControlOrderBody_A) {
            val over = JSONObject()
            over.put("header_id",overData.header_id)
            over.put("prod_ctrl_order_number",overData.prod_ctrl_order_number)
            over.put("me_code",overData.me_code)
            over.put("semi_finished_prod_number",overData.semi_finished_prod_number)
            over.put("pline_id",overData.pline_id)
            over.put("latest_inspection_day",overData.latest_inspection_day)
            over.put("is_re_make",overData.is_re_make)
            over.put("qc_date",overData.qc_date)
            over.put("qc_number",overData.qc_number)
            over.put("unit_of_measurement",overData.unit_of_measurement)
            over.put("est_start_date",overData.est_start_date)
            over.put("est_complete_date",overData.est_complete_date)
            over.put("est_output",overData.est_output)
            over.put("unit_of_timer",overData.unit_of_timer)
            over.put("start_date",overData.start_date)
            over.put("complete_date",overData.complete_date)
            over.put("actual_output",overData.actual_output)
            over.put("is_request_support",overData.is_request_support)
            over.put("number_of_support",overData.number_of_support)
            over.put("number_of_supported",overData.number_of_supported)
            over.put("request_support_time",overData.request_support_time)
            over.put("is_urgent",overData.is_urgent)
            over.put("urgent_deadline",overData.urgent_deadline)

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
        private fun bundle_edit_ProductControlOrder(operation:String,same_Title:String) {
            val sameTitle =JSONObject()
            sameTitle.put("prod_ctrl_order_number",same_Title)
            val changeContext =JSONObject()
            changeContext.put("offline_time",cookie_data.currentDateTime())
            val data = JSONArray()
            data.put(sameTitle)
            data.put(changeContext)
            val body = FormBody.Builder()
                .add("operation",operation)
                .add("data",data.toString())
                .add("username", cookie_data.username)
                .add("action",cookie_data.Actions.BUNDLE_CHANGE)
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
    }
    fun addItem(addData:ProductControlOrderBody_A){
        data.add(itemData.count,addData)
        notifyItemInserted(itemData.count)
        itemData.count+=1//cookie_data.itemCount+=1
    }

}


