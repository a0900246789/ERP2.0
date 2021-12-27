package com.example.erp20.RecyclerAdapter
import android.graphics.Color
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
import androidx.constraintlayout.widget.ConstraintLayout
import java.text.SimpleDateFormat


class RecyclerItemMealOrderListBodyDetailAdapter() :
    RecyclerView.Adapter<RecyclerItemMealOrderListBodyDetailAdapter.ViewHolder>() {
    //private var itemData: ShowMealOrderListBody =Gson().fromJson(cookie_data.response_data, ShowMealOrderListBody::class.java)
    private var data: ArrayList<MealOrderListBody> =cookie_data.MealOrderListBody_ArrayList_data

    var relativeCombobox01=cookie_data.card_number_ComboboxData
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemMealOrderListBodyDetailAdapter.ViewHolder {
        val v= LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_meal_order_list_body_detail,parent,false)
        return ViewHolder(v)

    }

    override fun onBindViewHolder(holder: RecyclerItemMealOrderListBodyDetailAdapter.ViewHolder, position: Int) {

        //Log.d("GSON", "msg: ${itemData.data[position]}\n")

        holder._date.setText(data[position].create_time)
        holder._date.inputType=InputType.TYPE_NULL
        holder._name.setText(   cookie_data.name_ComboboxData[ cookie_data.card_number_ComboboxData.indexOf(data[position].card_number) ] )
        holder._name.inputType=InputType.TYPE_NULL
        holder.card_number.setText(data[position].card_number)
        holder.card_number.inputType=InputType.TYPE_NULL




        if(data[position].is_morning_leave && !data[position].is_lunch_leave && !data[position].is_all_day_leave){
            holder.is_absent.setText("早假")
        }
        else if(!data[position].is_morning_leave && data[position].is_lunch_leave && !data[position].is_all_day_leave){
            holder.is_absent.setText("午假")
        }
        else if(!data[position].is_morning_leave && !data[position].is_lunch_leave && data[position].is_all_day_leave){
            holder.is_absent.setText("全假")
        }
        else{
            holder.is_absent.setText("無")
        }
        holder.is_absent.inputType=InputType.TYPE_NULL


        holder.leave_hours.setText(data[position].leave_hours.toString())
        holder.leave_hours.inputType=InputType.TYPE_NULL

        holder.overtime_hours.setText(data[position].overtime_hours.toString())
        holder.overtime_hours.inputType=InputType.TYPE_NULL

        holder.support_hours.setText(data[position].support_hours.toString())
        holder.support_hours.inputType=InputType.TYPE_NULL


        if(data[position].is_l_meat_meals && !data[position].is_l_vegetarian_meals && !data[position].is_l_indonesia_meals && !data[position].is_l_num_of_selfcare){
            holder.is_l_meals.setText("葷食")
        }
        else if(!data[position].is_l_meat_meals && data[position].is_l_vegetarian_meals && !data[position].is_l_indonesia_meals && !data[position].is_l_num_of_selfcare){
            holder.is_l_meals.setText("素食")
        }
        else if(!data[position].is_l_meat_meals && !data[position].is_l_vegetarian_meals && data[position].is_l_indonesia_meals && !data[position].is_l_num_of_selfcare){
            holder.is_l_meals.setText("印尼餐")
        }
        else{
            holder.is_l_meals.setText("自理")
        }
        holder.is_l_meals.inputType=InputType.TYPE_NULL

        if(data[position].is_d_meat_meals && !data[position].is_d_vegetarian_meals && !data[position].is_d_indonesia_meals && !data[position].is_d_num_of_selfcare){
            holder.is_d_meals.setText("葷食")
        }
        else if(!data[position].is_d_meat_meals && data[position].is_d_vegetarian_meals && !data[position].is_d_indonesia_meals && !data[position].is_d_num_of_selfcare){
            holder.is_d_meals.setText("素食")
        }
        else if(!data[position].is_d_meat_meals && !data[position].is_d_vegetarian_meals && data[position].is_d_indonesia_meals && !data[position].is_d_num_of_selfcare){
            holder.is_d_meals.setText("印尼餐")
        }
        else{
            holder.is_d_meals.setText("自理")
        }
        holder.is_d_meals.inputType=InputType.TYPE_NULL






        //holder.deletebtn.isVisible=true
        //holder.edit_btn.isVisible=true
        //holder.lockbtn.isVisible=true
        //holder.overbtn.isVisible=true
    }

    override fun getItemCount(): Int {
        return data.size//cookie_data.itemCount
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        //var header_id=itemView.findViewById<TextInputEditText>(R.id.edit_header_id)
        var _date=itemView.findViewById<TextInputEditText>(R.id.edit_date)
        //var order_number=itemView.findViewById<TextInputEditText>(R.id.edit_order_number)
        var _name=itemView.findViewById<TextInputEditText>(R.id.edit_name)

        var card_number=itemView.findViewById<AutoCompleteTextView>(R.id.edit_card_number)
        //var is_support=itemView.findViewById<AutoCompleteTextView>(R.id.edit_is_support)
        var is_support=false
        var support_hours=itemView.findViewById<TextInputEditText>(R.id.edit_support_hours)

        var is_absent =itemView.findViewById<AutoCompleteTextView>(R.id.edit_is_absent)
        //var is_morning_leave =itemView.findViewById<AutoCompleteTextView>(R.id.edit_is_morning_leave)
        var is_morning_leave=false
        //var is_lunch_leave=itemView.findViewById<AutoCompleteTextView>(R.id.edit_is_lunch_leave)
        var is_lunch_leave=false
        //var is_all_day_leave=itemView.findViewById<AutoCompleteTextView>(R.id.edit_is_all_day_leave)
        var is_all_day_leave=false
        var leave_hours=itemView.findViewById<TextInputEditText>(R.id.edit_leave_hours)

        var is_l_meals=itemView.findViewById<AutoCompleteTextView>(R.id.edit_is_l_meals)
        // var is_l_meat_meals=itemView.findViewById<AutoCompleteTextView>(R.id.edit_is_l_meat_meals)
        var is_l_meat_meals=false
        // var is_l_vegetarian_meals=itemView.findViewById<AutoCompleteTextView>(R.id.edit_is_l_vegetarian_meals)
        var is_l_vegetarian_meals=false
        // var is_l_indonesia_meals=itemView.findViewById<AutoCompleteTextView>(R.id.edit_is_l_indonesia_meals)
        var is_l_indonesia_meals=false
        // var is_l_num_of_selfcare=itemView.findViewById<AutoCompleteTextView>(R.id.edit_is_l_num_of_selfcare)
        var is_l_num_of_selfcare=false
        // var is_overtime=itemView.findViewById<AutoCompleteTextView>(R.id.edit_is_overtime)
        var is_overtime=false
        var overtime_hours=itemView.findViewById<TextInputEditText>(R.id.edit_overtime_hours)

        var is_d_meals=itemView.findViewById<AutoCompleteTextView>(R.id.edit_is_d_meals)
        // var is_d_meat_meals=itemView.findViewById<AutoCompleteTextView>(R.id.edit_is_d_meat_meals)
        var is_d_meat_meals=false
        // var is_d_vegetarian_meals=itemView.findViewById<AutoCompleteTextView>(R.id.edit_is_d_vegetarian_meals)
        var is_d_vegetarian_meals=false
        // var is_d_indonesia_meals=itemView.findViewById<AutoCompleteTextView>(R.id.edit_is_d_indonesia_meals)
        var is_d_indonesia_meals=false
        //var is_d_num_of_selfcare=itemView.findViewById<AutoCompleteTextView>(R.id.edit_is_d_num_of_selfcare)
        var is_d_num_of_selfcare=false

        var edit_btn=itemView.findViewById<Button>(R.id.edit_btn)
        var deletebtn=itemView.findViewById<Button>(R.id.delete_btn)
        var lockbtn=itemView.findViewById<Button>(R.id.lock_btn)
        var overbtn=itemView.findViewById<Button>(R.id.over_btn)
        // var creator=itemView.findViewById<AutoCompleteTextView>(R.id.edit_creator)
        //var creator_time=itemView.findViewById<AutoCompleteTextView>(R.id.edit_create_time)
        //var editor=itemView.findViewById<AutoCompleteTextView>(R.id.edit_editor)
        // var editor_time=itemView.findViewById<AutoCompleteTextView>(R.id.edit_edit_time)
        // var lock=itemView.findViewById<AutoCompleteTextView>(R.id.edit_lock)
        // var lock_time=itemView.findViewById<AutoCompleteTextView>(R.id.edit_lock_time)
        // var invalid=itemView.findViewById<AutoCompleteTextView>(R.id.edit_invalid)
        // var invalid_time=itemView.findViewById<AutoCompleteTextView>(R.id.edit_invalid_time)
        // var is_closed=itemView.findViewById<AutoCompleteTextView>(R.id.edit_is_closed)
        // var close_time=itemView.findViewById<AutoCompleteTextView>(R.id.edit_close_time)
        // var remark=itemView.findViewById<TextInputEditText>(R.id.edit_remark)

        var layout=itemView.findViewById<ConstraintLayout>(R.id.constraint_layout)
        lateinit var oldData:MealOrderListBody
        lateinit var newData:MealOrderListBody
        init {
            itemView.setOnClickListener{
                val position:Int=adapterPosition
                Log.d("GSON", "msg: ${position}\n")

            }

            val combobox=itemView.resources.getStringArray(R.array.combobox_yes_no)
            val arrayAdapter= ArrayAdapter(itemView.context,R.layout.combobox_item,combobox)
            val combobox_absent=itemView.resources.getStringArray(R.array.absent)
            val arrayAdapter_absent= ArrayAdapter(itemView.context,R.layout.combobox_item,combobox_absent)
            val combobox_meal=itemView.resources.getStringArray(R.array.meal)
            val arrayAdapter_meal= ArrayAdapter(itemView.context,R.layout.combobox_item,combobox_meal)
            val arrayAdapter01= ArrayAdapter(itemView.context,R.layout.combobox_item,relativeCombobox01)

         /*   //編輯按鈕
            edit_btn.setOnClickListener {
                when(edit_btn.text){
                    "編輯"->{
                        oldData=data[adapterPosition]
                        //Log.d("GSON", "msg: ${oldData.id}\n")
                        //header_id.inputType=InputType.TYPE_CLASS_TEXT
                        //order_number.inputType=InputType.TYPE_DATETIME_VARIATION_DATE
                        //card_number.setAdapter(arrayAdapter01)
                        is_absent.setAdapter(arrayAdapter_absent)
                        leave_hours.inputType=(InputType.TYPE_NUMBER_FLAG_DECIMAL or  InputType.TYPE_CLASS_NUMBER )
                        support_hours.inputType=(InputType.TYPE_NUMBER_FLAG_DECIMAL or  InputType.TYPE_CLASS_NUMBER )
                        overtime_hours.inputType=(InputType.TYPE_NUMBER_FLAG_DECIMAL or  InputType.TYPE_CLASS_NUMBER )
                        is_l_meals.setAdapter(arrayAdapter_meal)
                        is_d_meals.setAdapter(arrayAdapter_meal)


                        is_absent.requestFocus()
                        layout.setBackgroundColor(Color.parseColor("#6E5454"))
                        edit_btn.setBackgroundColor(Color.parseColor("#FF3700B3"))
                        edit_btn.text = "完成"
                    }
                    "完成"->{
                        newData= MealOrderListBody()
                        newData.header_id=oldData.header_id
                        newData.order_number=oldData.order_number
                        newData.card_number=card_number.text.toString()
                        card_number.setAdapter(null)
                        newData.is_support = support_hours.text.toString().toDouble() != 0.0
                        newData.support_hours=support_hours.text.toString().toDouble()
                        support_hours.inputType=InputType.TYPE_NULL

                        newData.is_overtime=overtime_hours.text.toString().toDouble() != 0.0
                        newData.overtime_hours=overtime_hours.text.toString().toDouble()
                        overtime_hours.inputType=InputType.TYPE_NULL

                        when(is_absent.text.toString()){
                            "早假"->{
                                newData.is_morning_leave=true
                                newData.is_lunch_leave=false
                                newData.is_all_day_leave=false
                            }
                            "午假"->{
                                newData.is_morning_leave=false
                                newData.is_lunch_leave=true
                                newData.is_all_day_leave=false
                            }
                            "全假"->{
                                newData.is_morning_leave=false
                                newData.is_lunch_leave=false
                                newData.is_all_day_leave=true
                            }
                            "無"->{
                                newData.is_morning_leave=false
                                newData.is_lunch_leave=false
                                newData.is_all_day_leave=false
                            }
                        }
                        is_absent.setAdapter(null)

                        newData.leave_hours=leave_hours.text.toString().toDouble()
                        leave_hours.inputType=InputType.TYPE_NULL

                        when(is_l_meals.text.toString()){
                            "葷食"->{
                                newData.is_l_meat_meals=true
                                newData.is_l_vegetarian_meals=false
                                newData.is_l_indonesia_meals=false
                                newData.is_l_num_of_selfcare=false
                            }
                            "素食"->{
                                newData.is_l_meat_meals=false
                                newData.is_l_vegetarian_meals=true
                                newData.is_l_indonesia_meals=false
                                newData.is_l_num_of_selfcare=false
                            }
                            "印尼餐"->{
                                newData.is_l_meat_meals=false
                                newData.is_l_vegetarian_meals=false
                                newData.is_l_indonesia_meals=true
                                newData.is_l_num_of_selfcare=false
                            }
                            "自理"->{
                                newData.is_l_meat_meals=false
                                newData.is_l_vegetarian_meals=false
                                newData.is_l_indonesia_meals=false
                                newData.is_l_num_of_selfcare=true
                            }
                        }
                        is_l_meals.setAdapter(null)


                        when(is_d_meals.text.toString()){
                            "葷食"->{
                                newData.is_d_meat_meals=true
                                newData.is_d_vegetarian_meals=false
                                newData.is_d_indonesia_meals=false
                                newData.is_d_num_of_selfcare=false
                            }
                            "素食"->{
                                newData.is_d_meat_meals=false
                                newData.is_d_vegetarian_meals=true
                                newData.is_d_indonesia_meals=false
                                newData.is_d_num_of_selfcare=false
                            }
                            "印尼餐"->{
                                newData.is_d_meat_meals=false
                                newData.is_d_vegetarian_meals=false
                                newData.is_d_indonesia_meals=true
                                newData.is_d_num_of_selfcare=false
                            }
                            "自理"->{
                                newData.is_d_meat_meals=false
                                newData.is_d_vegetarian_meals=false
                                newData.is_d_indonesia_meals=false
                                newData.is_d_num_of_selfcare=true
                            }
                        }
                        is_d_meals.setAdapter(null)



                        newData.remark=oldData.remark

                        //Log.d("GSON", "msg: ${oldData}\n")
                        //Log.d("GSON", "msg: ${newData.remark}\n")
                        edit_ProductControlOrder("MealOrderListBody",oldData,newData)//更改資料庫資料
                        when(cookie_data.status){
                            0-> {//成功
                                data[adapterPosition] = newData//更改渲染資料
                                Toast.makeText(itemView.context, cookie_data.msg, Toast.LENGTH_SHORT).show()
                            }
                            1->{//失敗
                                Toast.makeText(itemView.context, cookie_data.msg, Toast.LENGTH_SHORT).show()

                                card_number.setText(oldData.card_number)


                                if(oldData.is_morning_leave && !oldData.is_lunch_leave && !oldData.is_all_day_leave){
                                    is_absent.setText("早假")
                                }
                                else if(!oldData.is_morning_leave && oldData.is_lunch_leave && !oldData.is_all_day_leave){
                                    is_absent.setText("午假")
                                }
                                else if(!oldData.is_morning_leave && !oldData.is_lunch_leave && oldData.is_all_day_leave){
                                    is_absent.setText("全假")
                                }
                                else{
                                    is_absent.setText("無")
                                }


                                leave_hours.setText(oldData.leave_hours.toString())
                                overtime_hours.setText(oldData.overtime_hours.toString())
                                support_hours.setText(oldData.support_hours.toString())

                                if(oldData.is_l_meat_meals && !oldData.is_l_vegetarian_meals && !oldData.is_l_indonesia_meals && !oldData.is_l_num_of_selfcare){
                                    is_l_meals.setText("葷食")
                                }
                                else if(!oldData.is_l_meat_meals && oldData.is_l_vegetarian_meals && !oldData.is_l_indonesia_meals && !oldData.is_l_num_of_selfcare){
                                    is_l_meals.setText("素食")
                                }
                                else if(!oldData.is_l_meat_meals && !oldData.is_l_vegetarian_meals && oldData.is_l_indonesia_meals && !oldData.is_l_num_of_selfcare){
                                    is_l_meals.setText("印尼餐")
                                }
                                else{
                                    is_l_meals.setText("自理")
                                }

                                if(oldData.is_d_meat_meals && !oldData.is_d_vegetarian_meals && !oldData.is_d_indonesia_meals && !oldData.is_d_num_of_selfcare){
                                    is_d_meals.setText("葷食")
                                }
                                else if(!oldData.is_d_meat_meals && oldData.is_d_vegetarian_meals && !oldData.is_d_indonesia_meals && !oldData.is_d_num_of_selfcare){
                                    is_d_meals.setText("素食")
                                }
                                else if(!oldData.is_d_meat_meals && !oldData.is_d_vegetarian_meals && oldData.is_d_indonesia_meals && !oldData.is_d_num_of_selfcare){
                                    is_d_meals.setText("印尼餐")
                                }
                                else{
                                    is_d_meals.setText("自理")
                                }





                            }
                        }
                        //Log.d("GSON", "msg: ${itemData.data}\n")
                        edit_btn.text = "編輯"
                        layout.setBackgroundColor(Color.parseColor("#977C7C"))
                        edit_btn.setBackgroundColor(Color.parseColor("#FF6200EE"))
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
                    delete_ProductControlOrder("MealOrderListBody",data[adapterPosition])
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
                    lock_ProductControlOrder("MealOrderListBody",data[adapterPosition])
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
                    over_ProductControlOrder("MealOrderListBody",data[adapterPosition])
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
        private fun edit_ProductControlOrder(operation:String,oldData:MealOrderListBody,newData:MealOrderListBody) {
            val old =JSONObject()
            old.put("header_id",oldData.header_id)
            old.put("order_number",oldData.order_number)
            old.put("card_number",oldData.card_number)
            old.put("is_support",oldData.is_support)
            old.put("support_hours",oldData.support_hours)
            old.put("is_morning_leave",oldData.is_morning_leave)
            old.put("is_lunch_leave",oldData.is_lunch_leave)
            old.put("is_all_day_leave",oldData.is_all_day_leave)
            old.put("leave_hours",oldData.leave_hours)
            old.put("is_l_meat_meals",oldData.is_l_meat_meals)
            old.put("is_l_vegetarian_meals",oldData.is_l_vegetarian_meals)
            old.put("is_l_indonesia_meals",oldData.is_l_indonesia_meals)
            old.put("is_l_num_of_selfcare",oldData.is_l_num_of_selfcare)
            old.put("is_overtime",oldData.is_overtime)
            old.put("overtime_hours",oldData.overtime_hours)
            old.put("is_d_meat_meals",oldData.is_d_meat_meals)
            old.put("is_d_vegetarian_meals",oldData.is_d_vegetarian_meals)
            old.put("is_d_indonesia_meals",oldData.is_d_indonesia_meals)
            old.put("is_d_num_of_selfcare",oldData.is_d_num_of_selfcare)


            old.put("remark",oldData.remark)
            val new =JSONObject()
            new.put("header_id",newData.header_id)
            new.put("order_number",newData.order_number)
            new.put("card_number",newData.card_number)
            new.put("is_support",newData.is_support)
            new.put("support_hours",newData.support_hours)
            new.put("is_morning_leave",newData.is_morning_leave)
            new.put("is_lunch_leave",newData.is_lunch_leave)
            new.put("is_all_day_leave",newData.is_all_day_leave)
            new.put("leave_hours",newData.leave_hours)
            new.put("is_l_meat_meals",newData.is_l_meat_meals)
            new.put("is_l_vegetarian_meals",newData.is_l_vegetarian_meals)
            new.put("is_l_indonesia_meals",newData.is_l_indonesia_meals)
            new.put("is_l_num_of_selfcare",newData.is_l_num_of_selfcare)
            new.put("is_overtime",newData.is_overtime)
            new.put("overtime_hours",newData.overtime_hours)
            new.put("is_d_meat_meals",newData.is_d_meat_meals)
            new.put("is_d_vegetarian_meals",newData.is_d_vegetarian_meals)
            new.put("is_d_indonesia_meals",newData.is_d_indonesia_meals)
            new.put("is_d_num_of_selfcare",newData.is_d_num_of_selfcare)

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
        private fun delete_ProductControlOrder(operation:String,deleteData:MealOrderListBody) {
            val delete = JSONObject()
            delete.put("header_id",deleteData.header_id)
            delete.put("order_number",deleteData.order_number)
            delete.put("card_number",deleteData.card_number)
            delete.put("is_support",deleteData.is_support)
            delete.put("support_hours",deleteData.support_hours)
            delete.put("is_morning_leave",deleteData.is_morning_leave)
            delete.put("is_lunch_leave",deleteData.is_lunch_leave)
            delete.put("is_all_day_leave",deleteData.is_all_day_leave)
            delete.put("leave_hours",deleteData.leave_hours)
            delete.put("is_l_meat_meals",deleteData.is_l_meat_meals)
            delete.put("is_l_vegetarian_meals",deleteData.is_l_vegetarian_meals)
            delete.put("is_l_indonesia_meals",deleteData.is_l_indonesia_meals)
            delete.put("is_l_num_of_selfcare",deleteData.is_l_num_of_selfcare)
            delete.put("is_overtime",deleteData.is_overtime)
            delete.put("overtime_hours",deleteData.overtime_hours)
            delete.put("is_d_meat_meals",deleteData.is_d_meat_meals)
            delete.put("is_d_vegetarian_meals",deleteData.is_d_vegetarian_meals)
            delete.put("is_d_indonesia_meals",deleteData.is_d_indonesia_meals)
            delete.put("is_d_num_of_selfcare",deleteData.is_d_num_of_selfcare)

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
        private fun lock_ProductControlOrder(operation:String,lockData:MealOrderListBody) {
            val lock = JSONObject()
            lock.put("header_id",lockData.header_id)
            lock.put("order_number",lockData.order_number)
            lock.put("card_number",lockData.card_number)
            lock.put("is_support",lockData.is_support)
            lock.put("support_hours",lockData.support_hours)
            lock.put("is_morning_leave",lockData.is_morning_leave)
            lock.put("is_lunch_leave",lockData.is_lunch_leave)
            lock.put("is_all_day_leave",lockData.is_all_day_leave)
            lock.put("leave_hours",lockData.leave_hours)
            lock.put("is_l_meat_meals",lockData.is_l_meat_meals)
            lock.put("is_l_vegetarian_meals",lockData.is_l_vegetarian_meals)
            lock.put("is_l_indonesia_meals",lockData.is_l_indonesia_meals)
            lock.put("is_l_num_of_selfcare",lockData.is_l_num_of_selfcare)
            lock.put("is_overtime",lockData.is_overtime)
            lock.put("overtime_hours",lockData.overtime_hours)
            lock.put("is_d_meat_meals",lockData.is_d_meat_meals)
            lock.put("is_d_vegetarian_meals",lockData.is_d_vegetarian_meals)
            lock.put("is_d_indonesia_meals",lockData.is_d_indonesia_meals)
            lock.put("is_d_num_of_selfcare",lockData.is_d_num_of_selfcare)

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
        private fun over_ProductControlOrder(operation:String,overData:MealOrderListBody) {
            val over = JSONObject()
            over.put("header_id",overData.header_id)
            over.put("order_number",overData.order_number)
            over.put("card_number",overData.card_number)
            over.put("is_support",overData.is_support)
            over.put("support_hours",overData.support_hours)
            over.put("is_morning_leave",overData.is_morning_leave)
            over.put("is_lunch_leave",overData.is_lunch_leave)
            over.put("is_all_day_leave",overData.is_all_day_leave)
            over.put("leave_hours",overData.leave_hours)
            over.put("is_l_meat_meals",overData.is_l_meat_meals)
            over.put("is_l_vegetarian_meals",overData.is_l_vegetarian_meals)
            over.put("is_l_indonesia_meals",overData.is_l_indonesia_meals)
            over.put("is_l_num_of_selfcare",overData.is_l_num_of_selfcare)
            over.put("is_overtime",overData.is_overtime)
            over.put("overtime_hours",overData.overtime_hours)
            over.put("is_d_meat_meals",overData.is_d_meat_meals)
            over.put("is_d_vegetarian_meals",overData.is_d_vegetarian_meals)
            over.put("is_d_indonesia_meals",overData.is_d_indonesia_meals)
            over.put("is_d_num_of_selfcare",overData.is_d_num_of_selfcare)

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
    fun addItem(addData:MealOrderListBody){
        data.add(data.size,addData)
        notifyItemInserted(data.size)
        //itemData.count+=1//cookie_data.itemCount+=1
    }

}



