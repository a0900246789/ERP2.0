package com.example.erp20.app02
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


class RecyclerItemMealOrderListHeaderAdapter() :
    RecyclerView.Adapter<RecyclerItemMealOrderListHeaderAdapter.ViewHolder>() {
    private var itemData: ShowMealOrderListHeader =Gson().fromJson(cookie_data.response_data, ShowMealOrderListHeader::class.java)
    private var data: ArrayList<MealOrderListHeader> =itemData.data
    var relativeCombobox01=cookie_data.dept_name_ComboboxData

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemMealOrderListHeaderAdapter.ViewHolder {
        val v= LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_meal_order_list_header,parent,false)
        return ViewHolder(v)

    }

    override fun onBindViewHolder(holder: RecyclerItemMealOrderListHeaderAdapter.ViewHolder, position: Int) {

        //Log.d("GSON", "msg: ${itemData.data[position]}\n")
        holder._id.setText(data[position]._id)
        holder._id.inputType=InputType.TYPE_NULL
        holder.date.setText(data[position].date)
        holder.date.inputType=InputType.TYPE_NULL
        holder.dept.setText(data[position].dept)
        holder.dept.inputType=InputType.TYPE_NULL
        holder.l_meat_meals.setText(data[position].l_meat_meals.toString())
        holder.l_meat_meals.inputType=InputType.TYPE_NULL
        holder.l_vegetarian_meals.setText(data[position].l_vegetarian_meals.toString())
        holder.l_vegetarian_meals.inputType=InputType.TYPE_NULL
        holder.l_indonesia_meals.setText(data[position].l_indonesia_meals.toString())
        holder.l_indonesia_meals.inputType=InputType.TYPE_NULL
        holder.l_num_of_selfcare.setText(data[position].l_num_of_selfcare.toString())
        holder.l_num_of_selfcare.inputType=InputType.TYPE_NULL
        holder.num_of_leave.setText(data[position].num_of_leave.toString())
        holder.num_of_leave.inputType=InputType.TYPE_NULL
        holder.total_leave_hours.setText(data[position].total_leave_hours.toString())
        holder.total_leave_hours.inputType=InputType.TYPE_NULL
        holder.attendance.setText(data[position].attendance.toString())
        holder.attendance.inputType=InputType.TYPE_NULL
        holder.number_of_support.setText(data[position].number_of_support.toString())
        holder.number_of_support.inputType=InputType.TYPE_NULL
        holder.total_support_hours.setText(data[position].total_support_hours.toString())
        holder.total_support_hours.inputType=InputType.TYPE_NULL
        holder.d_meat_meals.setText(data[position].d_meat_meals.toString())
        holder.d_meat_meals.inputType=InputType.TYPE_NULL
        holder.d_vegetarian_meals.setText(data[position].d_vegetarian_meals.toString())
        holder.d_vegetarian_meals.inputType=InputType.TYPE_NULL
        holder.d_indonesia_meals.setText(data[position].d_indonesia_meals.toString())
        holder.d_indonesia_meals.inputType=InputType.TYPE_NULL
        holder.d_num_of_selfcare.setText(data[position].d_num_of_selfcare.toString())
        holder.d_num_of_selfcare.inputType=InputType.TYPE_NULL
        holder.number_of_overtime.setText(data[position].number_of_overtime.toString())
        holder.number_of_overtime.inputType=InputType.TYPE_NULL
        holder.number_of_overtime_support.setText(data[position].number_of_overtime_support.toString())
        holder.number_of_overtime_support.inputType=InputType.TYPE_NULL
        holder.is_check.setText(data[position].is_check.toString())
        holder.is_check.isClickable=false
        holder.is_check.inputType=InputType.TYPE_NULL
        holder.check_time.setText(data[position].check_time)
        holder.check_time.inputType=InputType.TYPE_NULL



        holder.creator.setText(data[position].creator)
        holder.creator_time.setText(data[position].create_time)
        holder.editor.setText(data[position].editor)
        holder.editor_time.setText(data[position].edit_time)
        holder.lock.setText(data[position].Lock.toString())
        holder.lock_time.setText(data[position].lock_time)
        holder.invalid.setText(data[position].invalid.toString())
        holder.invalid_time.setText(data[position].invalid_time)
        holder.is_closed.setText(data[position].is_closed.toString())
        holder.close_time.setText(data[position].close_time)
        holder.remark.setText(data[position].remark)
        holder.remark.isClickable=false
        holder.remark.isFocusable=false
        holder.remark.isFocusableInTouchMode=false
        holder.remark.isTextInputLayoutFocusedRectEnabled=false
        holder.deletebtn.isVisible=true
        holder.edit_btn.isVisible=true
        holder.lockbtn.isVisible=true
        holder.overbtn.isVisible=true
    }

    override fun getItemCount(): Int {
        return itemData.count//cookie_data.itemCount
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var _id=itemView.findViewById<TextInputEditText>(R.id.edit_id)
        var date=itemView.findViewById<TextInputEditText>(R.id.edit_date)
        var dept=itemView.findViewById<AutoCompleteTextView>(R.id.edit_dept)
        var l_meat_meals=itemView.findViewById<TextInputEditText>(R.id.edit_l_meat_meals)
        var l_vegetarian_meals=itemView.findViewById<TextInputEditText>(R.id.edit_l_vegetarian_meals)
        var l_indonesia_meals =itemView.findViewById<TextInputEditText>(R.id.edit_l_indonesia_meals)
        var l_num_of_selfcare=itemView.findViewById<TextInputEditText>(R.id.edit_l_num_of_selfcare)
        var num_of_leave=itemView.findViewById<TextInputEditText>(R.id.edit_num_of_leave)
        var total_leave_hours=itemView.findViewById<TextInputEditText>(R.id.edit_total_leave_hours)
        var attendance=itemView.findViewById<TextInputEditText>(R.id.edit_attendance)
        var number_of_support=itemView.findViewById<TextInputEditText>(R.id.edit_number_of_support)
        var total_support_hours=itemView.findViewById<TextInputEditText>(R.id.edit_total_support_hours)
        var d_meat_meals=itemView.findViewById<TextInputEditText>(R.id.edit_d_meat_meals)
        var d_vegetarian_meals=itemView.findViewById<TextInputEditText>(R.id.edit_d_vegetarian_meals)
        var d_indonesia_meals=itemView.findViewById<TextInputEditText>(R.id.edit_d_indonesia_meals)
        var d_num_of_selfcare=itemView.findViewById<TextInputEditText>(R.id.edit_d_num_of_selfcare)
        var number_of_overtime=itemView.findViewById<TextInputEditText>(R.id.edit_number_of_overtime)
        var number_of_overtime_support=itemView.findViewById<TextInputEditText>(R.id.edit_number_of_overtime_support)
        var is_check=itemView.findViewById<AutoCompleteTextView>(R.id.edit_is_check)
        var check_time=itemView.findViewById<TextInputEditText>(R.id.edit_check_time)

        var edit_btn=itemView.findViewById<Button>(R.id.edit_btn)
        var deletebtn=itemView.findViewById<Button>(R.id.delete_btn)
        var lockbtn=itemView.findViewById<Button>(R.id.lock_btn)
        var overbtn=itemView.findViewById<Button>(R.id.over_btn)
        var creator=itemView.findViewById<AutoCompleteTextView>(R.id.edit_creator)
        var creator_time=itemView.findViewById<AutoCompleteTextView>(R.id.edit_create_time)
        var editor=itemView.findViewById<AutoCompleteTextView>(R.id.edit_editor)
        var editor_time=itemView.findViewById<AutoCompleteTextView>(R.id.edit_edit_time)
        var lock=itemView.findViewById<AutoCompleteTextView>(R.id.edit_lock)
        var lock_time=itemView.findViewById<AutoCompleteTextView>(R.id.edit_lock_time)
        var invalid=itemView.findViewById<AutoCompleteTextView>(R.id.edit_invalid)
        var invalid_time=itemView.findViewById<AutoCompleteTextView>(R.id.edit_invalid_time)
        var is_closed=itemView.findViewById<AutoCompleteTextView>(R.id.edit_is_closed)
        var close_time=itemView.findViewById<AutoCompleteTextView>(R.id.edit_close_time)
        var remark=itemView.findViewById<TextInputEditText>(R.id.edit_remark)
        lateinit var oldData:MealOrderListHeader
        lateinit var newData:MealOrderListHeader
        init {
            itemView.setOnClickListener{
                val position:Int=adapterPosition
                Log.d("GSON", "msg: ${position}\n")

            }

            val combobox=itemView.resources.getStringArray(R.array.combobox_yes_no)
            val arrayAdapter= ArrayAdapter(itemView.context,R.layout.combobox_item,combobox)
            val arrayAdapter01= ArrayAdapter(itemView.context,R.layout.combobox_item,relativeCombobox01)

            //編輯按鈕
            edit_btn.setOnClickListener {
                when(edit_btn.text){
                    "編輯"->{
                        oldData=itemData.data[adapterPosition]
                        //Log.d("GSON", "msg: ${oldData.id}\n")
                        //_id.inputType=InputType.TYPE_CLASS_TEXT
                        date.inputType=InputType.TYPE_DATETIME_VARIATION_DATE
                        dept.setAdapter(arrayAdapter01)
                        l_meat_meals.inputType=InputType.TYPE_CLASS_NUMBER
                        l_vegetarian_meals.inputType=InputType.TYPE_CLASS_NUMBER
                        l_indonesia_meals.inputType=InputType.TYPE_CLASS_NUMBER
                        l_num_of_selfcare.inputType=InputType.TYPE_CLASS_NUMBER
                        num_of_leave.inputType=InputType.TYPE_CLASS_NUMBER
                        total_leave_hours.inputType=InputType.TYPE_CLASS_NUMBER
                        attendance.inputType=InputType.TYPE_CLASS_NUMBER
                        number_of_support.inputType=InputType.TYPE_CLASS_NUMBER
                        total_support_hours.inputType=InputType.TYPE_CLASS_NUMBER
                        d_meat_meals.inputType=InputType.TYPE_CLASS_NUMBER
                        d_vegetarian_meals.inputType=InputType.TYPE_CLASS_NUMBER
                        d_indonesia_meals.inputType=InputType.TYPE_CLASS_NUMBER
                        d_num_of_selfcare.inputType=InputType.TYPE_CLASS_NUMBER
                        number_of_overtime.inputType=InputType.TYPE_CLASS_NUMBER
                        number_of_overtime_support.inputType=InputType.TYPE_CLASS_NUMBER
                        is_check.setAdapter(arrayAdapter)
                        is_check.isClickable=true
                        check_time.inputType=InputType.TYPE_CLASS_DATETIME



                        remark.isClickable=true
                        remark.isFocusable=true
                        remark.isFocusableInTouchMode=true
                        remark.isTextInputLayoutFocusedRectEnabled=true
                        _id.requestFocus()
                        edit_btn.text = "完成"
                    }
                    "完成"->{
                        newData= MealOrderListHeader()
                        newData._id=_id.text.toString()
                        _id.inputType=InputType.TYPE_NULL
                        if(date.text.toString()==""){
                            newData.date=null
                        }
                        else{
                            newData.date=date.text.toString()
                        }
                        date.inputType=InputType.TYPE_NULL
                        newData.dept=dept.text.toString()
                        dept.setAdapter(null)
                        newData.l_meat_meals=l_meat_meals.text.toString().toInt()
                        l_meat_meals.inputType=InputType.TYPE_NULL
                        newData.l_vegetarian_meals=l_vegetarian_meals.text.toString().toInt()
                        l_vegetarian_meals.inputType=InputType.TYPE_NULL
                        newData.l_indonesia_meals=l_indonesia_meals.text.toString().toInt()
                        l_indonesia_meals.inputType=InputType.TYPE_NULL
                        newData.l_num_of_selfcare=l_num_of_selfcare.text.toString().toInt()
                        l_num_of_selfcare.inputType=InputType.TYPE_NULL
                        newData.num_of_leave=num_of_leave.text.toString().toInt()
                        num_of_leave.inputType=InputType.TYPE_NULL
                        newData.total_leave_hours=total_leave_hours.text.toString().toDouble()
                        total_leave_hours.inputType=InputType.TYPE_NULL
                        newData.attendance=attendance.text.toString().toInt()
                        attendance.inputType=InputType.TYPE_NULL
                        newData.number_of_support=number_of_support.text.toString().toInt()
                        number_of_support.inputType=InputType.TYPE_NULL
                        newData.total_support_hours=total_support_hours.text.toString().toDouble()
                        total_support_hours.inputType=InputType.TYPE_NULL
                        newData.d_meat_meals=d_meat_meals.text.toString().toInt()
                        d_meat_meals.inputType=InputType.TYPE_NULL
                        newData.d_vegetarian_meals=d_vegetarian_meals.text.toString().toInt()
                        d_vegetarian_meals.inputType=InputType.TYPE_NULL
                        newData.d_indonesia_meals=d_indonesia_meals.text.toString().toInt()
                        d_indonesia_meals.inputType=InputType.TYPE_NULL
                        newData.d_num_of_selfcare=d_num_of_selfcare.text.toString().toInt()
                        d_num_of_selfcare.inputType=InputType.TYPE_NULL
                        newData.number_of_overtime=number_of_overtime.text.toString().toInt()
                        number_of_overtime.inputType=InputType.TYPE_NULL
                        newData.number_of_overtime_support=number_of_overtime_support.text.toString().toDouble()
                        number_of_overtime_support.inputType=InputType.TYPE_NULL
                        newData.is_check=is_check.text.toString().toBoolean()
                        is_check.isClickable=false
                        is_check.setAdapter(null)
                        if(check_time.text.toString()==""){
                            newData.check_time=null
                        }
                        else{
                            newData.check_time=check_time.text.toString()
                        }
                        check_time.inputType=InputType.TYPE_NULL

                        newData.remark=remark.text.toString()
                        remark.isClickable=false
                        remark.isFocusable=false
                        remark.isFocusableInTouchMode=false
                        remark.isTextInputLayoutFocusedRectEnabled=false
                        //Log.d("GSON", "msg: ${oldData}\n")
                        //Log.d("GSON", "msg: ${newData.remark}\n")
                        edit_ProductControlOrder("MealOrderListHeader",oldData,newData)//更改資料庫資料
                        when(cookie_data.status){
                            0-> {//成功
                                itemData.data[adapterPosition] = newData//更改渲染資料
                                editor.setText(cookie_data.username)
                                Toast.makeText(itemView.context, cookie_data.msg, Toast.LENGTH_SHORT).show()
                            }
                            1->{//失敗
                                Toast.makeText(itemView.context, cookie_data.msg, Toast.LENGTH_SHORT).show()
                                _id.setText(oldData._id)
                                date.setText(oldData.date)
                                dept.setText(oldData.dept)
                                l_meat_meals.setText(oldData.l_meat_meals.toString())
                                l_vegetarian_meals.setText(oldData.l_vegetarian_meals.toString())
                                l_indonesia_meals.setText(oldData.l_indonesia_meals.toString())
                                l_num_of_selfcare.setText(oldData.l_num_of_selfcare.toString())
                                num_of_leave.setText(oldData.num_of_leave.toString())
                                total_leave_hours.setText(oldData.total_leave_hours.toString())
                                attendance.setText(oldData.attendance.toString())
                                number_of_support.setText(oldData.number_of_support.toString())
                                total_support_hours.setText(oldData.total_support_hours.toString())
                                d_meat_meals.setText(oldData.d_meat_meals.toString())
                                d_vegetarian_meals.setText(oldData.d_vegetarian_meals.toString())
                                d_indonesia_meals.setText(oldData.d_indonesia_meals.toString())
                                d_num_of_selfcare.setText(oldData.d_num_of_selfcare.toString())
                                number_of_overtime.setText(oldData.number_of_overtime.toString())
                                number_of_overtime_support.setText(oldData.number_of_overtime_support.toString())
                                number_of_support.setText(oldData.number_of_support.toString())
                                is_check.setText(oldData.is_check.toString())
                                check_time.setText(oldData.check_time)


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
                    delete_ProductControlOrder("MealOrderListHeader",data[adapterPosition])
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
                    lock_ProductControlOrder("MealOrderListHeader",data[adapterPosition])
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
                    over_ProductControlOrder("MealOrderListHeader",data[adapterPosition])
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

            }
        }
        private fun edit_ProductControlOrder(operation:String,oldData:MealOrderListHeader,newData:MealOrderListHeader) {
            val old =JSONObject()
            old.put("_id",oldData._id)
            old.put("date",oldData.date)
            old.put("dept",oldData.dept)
            old.put("l_meat_meals",oldData.l_meat_meals)
            old.put("l_vegetarian_meals",oldData.l_vegetarian_meals)
            old.put("l_indonesia_meals",oldData.l_indonesia_meals)
            old.put("l_num_of_selfcare",oldData.l_num_of_selfcare)
            old.put("num_of_leave",oldData.num_of_leave)
            old.put("total_leave_hours",oldData.total_leave_hours)
            old.put("attendance",oldData.attendance)
            old.put("number_of_support",oldData.number_of_support)
            old.put("total_support_hours",oldData.total_support_hours)
            old.put("d_meat_meals",oldData.d_meat_meals)
            old.put("d_vegetarian_meals",oldData.d_vegetarian_meals)
            old.put("d_indonesia_meals",oldData.d_indonesia_meals)
            old.put("d_num_of_selfcare",oldData.d_num_of_selfcare)
            old.put("number_of_overtime",oldData.number_of_overtime)
            old.put("number_of_overtime_support",oldData.number_of_overtime_support)
            old.put("is_check",oldData.is_check)
            old.put("check_time",oldData.check_time)

            old.put("remark",oldData.remark)
            val new =JSONObject()
            new.put("_id",newData._id)
            new.put("date",newData.date)
            new.put("dept",newData.dept)
            new.put("l_meat_meals",oldData.l_meat_meals)
            new.put("l_vegetarian_meals",newData.l_vegetarian_meals)
            new.put("l_indonesia_meals",newData.l_indonesia_meals)
            new.put("l_num_of_selfcare",newData.l_num_of_selfcare)
            new.put("num_of_leave",newData.num_of_leave)
            new.put("total_leave_hours",newData.total_leave_hours)
            new.put("attendance",newData.attendance)
            new.put("number_of_support",newData.number_of_support)
            new.put("total_support_hours",newData.total_support_hours)
            new.put("d_meat_meals",newData.d_meat_meals)
            new.put("d_vegetarian_meals",newData.d_vegetarian_meals)
            new.put("d_indonesia_meals",newData.d_indonesia_meals)
            new.put("d_num_of_selfcare",newData.d_num_of_selfcare)
            new.put("number_of_overtime",newData.number_of_overtime)
            new.put("number_of_overtime_support",newData.number_of_overtime_support)
            new.put("is_check",newData.is_check)
            new.put("check_time",newData.check_time)

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
                .url("http://140.125.46.125:8000/production_control_sheet_management")
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
        private fun delete_ProductControlOrder(operation:String,deleteData:MealOrderListHeader) {
            val delete = JSONObject()
            delete.put("_id",deleteData._id)
            delete.put("date",deleteData.date)
            delete.put("dept",deleteData.dept)
            delete.put("l_meat_meals",deleteData.l_meat_meals)
            delete.put("l_vegetarian_meals",deleteData.l_vegetarian_meals)
            delete.put("l_indonesia_meals",deleteData.l_indonesia_meals)
            delete.put("l_num_of_selfcare",deleteData.l_num_of_selfcare)
            delete.put("num_of_leave",deleteData.num_of_leave)
            delete.put("total_leave_hours",deleteData.total_leave_hours)
            delete.put("attendance",deleteData.attendance)
            delete.put("number_of_support",deleteData.number_of_support)
            delete.put("total_support_hours",deleteData.total_support_hours)
            delete.put("d_meat_meals",deleteData.d_meat_meals)
            delete.put("d_vegetarian_meals",deleteData.d_vegetarian_meals)
            delete.put("d_indonesia_meals",deleteData.d_indonesia_meals)
            delete.put("d_num_of_selfcare",deleteData.d_num_of_selfcare)
            delete.put("number_of_overtime",deleteData.number_of_overtime)
            delete.put("number_of_overtime_support",deleteData.number_of_overtime_support)
            delete.put("is_check",deleteData.is_check)
            delete.put("check_time",deleteData.check_time)

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
                .url("http://140.125.46.125:8000/production_control_sheet_management")
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
        private fun lock_ProductControlOrder(operation:String,lockData:MealOrderListHeader) {
            val lock = JSONObject()
            lock.put("_id",lockData._id)
            lock.put("date",lockData.date)
            lock.put("dept",lockData.dept)
            lock.put("l_meat_meals",lockData.l_meat_meals)
            lock.put("l_vegetarian_meals",lockData.l_vegetarian_meals)
            lock.put("l_indonesia_meals",lockData.l_indonesia_meals)
            lock.put("l_num_of_selfcare",lockData.l_num_of_selfcare)
            lock.put("num_of_leave",lockData.num_of_leave)
            lock.put("total_leave_hours",lockData.total_leave_hours)
            lock.put("attendance",lockData.attendance)
            lock.put("number_of_support",lockData.number_of_support)
            lock.put("total_support_hours",lockData.total_support_hours)
            lock.put("d_meat_meals",lockData.d_meat_meals)
            lock.put("d_vegetarian_meals",lockData.d_vegetarian_meals)
            lock.put("d_indonesia_meals",lockData.d_indonesia_meals)
            lock.put("d_num_of_selfcare",lockData.d_num_of_selfcare)
            lock.put("number_of_overtime",lockData.number_of_overtime)
            lock.put("number_of_overtime_support",lockData.number_of_overtime_support)
            lock.put("is_check",lockData.is_check)
            lock.put("check_time",lockData.check_time)

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
                .url("http://140.125.46.125:8000/production_control_sheet_management")
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
        private fun over_ProductControlOrder(operation:String,overData:MealOrderListHeader) {
            val over = JSONObject()
            over.put("_id",overData._id)
            over.put("date",overData.date)
            over.put("dept",overData.dept)
            over.put("l_meat_meals",overData.l_meat_meals)
            over.put("l_vegetarian_meals",overData.l_vegetarian_meals)
            over.put("l_indonesia_meals",overData.l_indonesia_meals)
            over.put("l_num_of_selfcare",overData.l_num_of_selfcare)
            over.put("num_of_leave",overData.num_of_leave)
            over.put("total_leave_hours",overData.total_leave_hours)
            over.put("attendance",overData.attendance)
            over.put("number_of_support",overData.number_of_support)
            over.put("total_support_hours",overData.total_support_hours)
            over.put("d_meat_meals",overData.d_meat_meals)
            over.put("d_vegetarian_meals",overData.d_vegetarian_meals)
            over.put("d_indonesia_meals",overData.d_indonesia_meals)
            over.put("d_num_of_selfcare",overData.d_num_of_selfcare)
            over.put("number_of_overtime",overData.number_of_overtime)
            over.put("number_of_overtime_support",overData.number_of_overtime_support)
            over.put("is_check",overData.is_check)
            over.put("check_time",overData.check_time)

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
                .url("http://140.125.46.125:8000/production_control_sheet_management")
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
    fun addItem(addData:MealOrderListHeader){
        data.add(itemData.count,addData)
        notifyItemInserted(itemData.count)
        itemData.count+=1//cookie_data.itemCount+=1
    }

}


