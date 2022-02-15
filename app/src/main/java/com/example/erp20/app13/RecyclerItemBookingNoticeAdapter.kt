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


class RecyclerItemBookingNoticeAdapter(FilterTopic:String,FilterContent:String) :
    RecyclerView.Adapter<RecyclerItemBookingNoticeAdapter.ViewHolder>() {
    private var itemData: ShowBookingNoticeHeader =Gson().fromJson(cookie_data.response_data, ShowBookingNoticeHeader::class.java)
    private var data: ArrayList<BookingNoticeHeader> =itemData.data
    var relativeCombobox01=cookie_data.shipping_number_ComboboxData
    var relativeCombobox02=cookie_data.CustomerOrderHeader_poNo_ComboboxData
    val dateF = SimpleDateFormat("yyyy-MM-dd(EEEE)", Locale.TAIWAN)
    val timeF = SimpleDateFormat("HH:mm:ss", Locale.TAIWAN)
    val dateF2 = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    init {
        // println(SelectFilter)
        data=filter(data,FilterTopic,FilterContent)
        data=sort(data)

    }
    fun filter(data: java.util.ArrayList<BookingNoticeHeader>, filterTopic:String, filterContent:String): java.util.ArrayList<BookingNoticeHeader> {
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
    }
    fun sort(data: java.util.ArrayList<BookingNoticeHeader>): java.util.ArrayList<BookingNoticeHeader> {
        var sortedList:List<BookingNoticeHeader> = data

        sortedList  = data.sortedWith(
            compareBy(
                { it._id },
            )
        )

        return sortedList.toCollection(java.util.ArrayList())
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v= LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_booking_notice,parent,false)
        return ViewHolder(v)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        //Log.d("GSON", "msg: ${itemData.data[position]}\n")
        holder._id.setText(data[position]._id)
        holder._id.inputType=InputType.TYPE_NULL
        holder.invalid.setText(data[position].invalid.toString())
        holder.invalid.inputType=InputType.TYPE_NULL
        holder.notice_number.setText(data[position].notice_number)
        holder.notice_number.inputType=InputType.TYPE_NULL
        holder.customer_poNo.setText(data[position].customer_poNo)
        holder.customer_poNo.inputType=InputType.TYPE_NULL

        if(data[position].customer_poNo!=null && cookie_data.CustomerOrderHeader_poNo_ComboboxData.indexOf(data[position].customer_poNo)!=-1 && cookie_data.CustomerOrderHeader_swe_ComboboxData[cookie_data.CustomerOrderHeader_poNo_ComboboxData.indexOf(data[position].customer_poNo)]!=null){
            //date
            var readDateString=cookie_data.CustomerOrderHeader_swe_ComboboxData[cookie_data.CustomerOrderHeader_poNo_ComboboxData.indexOf(data[position].customer_poNo)]
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

        holder.shipping_order_number.setText(data[position].shipping_order_number)
        holder.shipping_order_number.inputType=InputType.TYPE_NULL

        if(data[position].act_clearance_date!=null){
            //date
            var readDateString=data[position].act_clearance_date
            //println(readDateString)
            val readDate=Calendar.getInstance()
            //println(readDateString.subSequence(0,4))
            readDate.set(Calendar.YEAR,readDateString!!.subSequence(0,4).toString().toInt())//yyyy
            // println(readDateString.subSequence(5,7))
            readDate.set(Calendar.MONTH,readDateString!!.subSequence(5,7).toString().toInt()-1)//MM
            // println(readDateString.subSequence(8,10))
            readDate.set(Calendar.DAY_OF_MONTH,readDateString!!.subSequence(8,10).toString().toInt())//dd
            var date=dateF.format(readDate.time)
            holder.act_clearance_date.setText(date)
            readDate.add(Calendar.DAY_OF_YEAR,7)
            date=dateF.format(readDate.time)
            holder.pre_clearance_date.setText(date)
        }
        else{
            holder.act_clearance_date.setText(null)
            holder.pre_clearance_date.setText(null)
        }
        holder.act_clearance_date.inputType=InputType.TYPE_NULL
        holder.pre_clearance_date.inputType=InputType.TYPE_NULL


        if(data[position].act_shipping_date!=null){
            //date
            var readDateString=data[position].act_shipping_date
            //println(readDateString)
            val readDate=Calendar.getInstance()
            //println(readDateString.subSequence(0,4))
            readDate.set(Calendar.YEAR,readDateString!!.subSequence(0,4).toString().toInt())//yyyy
            // println(readDateString.subSequence(5,7))
            readDate.set(Calendar.MONTH,readDateString!!.subSequence(5,7).toString().toInt()-1)//MM
            // println(readDateString.subSequence(8,10))
            readDate.set(Calendar.DAY_OF_MONTH,readDateString!!.subSequence(8,10).toString().toInt())//dd
            var date=dateF.format(readDate.time)
            holder.act_shipping_date.setText(date)
        }
        else{
            holder.act_shipping_date.setText(null)
        }
        holder.act_shipping_date.inputType=InputType.TYPE_NULL

        holder.is_last.setText(data[position].is_last.toString())
        holder.is_last.inputType=InputType.TYPE_NULL


        holder.oa_referenceNO1.setText(data[position].oa_referenceNO1)
        holder.oa_referenceNO1.inputType=InputType.TYPE_NULL

        if(cookie_data.OAReference_oa_referenceNO1_ComboboxData.indexOf(data[position].oa_referenceNO1)!=-1){
            holder.oa_referenceNO2.setText(
                cookie_data.OAReference_oa_referenceNO2_ComboboxData[
                        cookie_data.OAReference_oa_referenceNO1_ComboboxData.indexOf(
                            data[position].oa_referenceNO1)])
            holder.oa_referenceNO3.setText(
                cookie_data.OAReference_oa_referenceNO3_ComboboxData[
                        cookie_data.OAReference_oa_referenceNO1_ComboboxData.indexOf(
                            data[position].oa_referenceNO1)])
        }
        else{
            holder.oa_referenceNO2.setText("")
            holder.oa_referenceNO3.setText("")
        }
        holder.oa_referenceNO2.inputType=InputType.TYPE_NULL
        holder.oa_referenceNO3.inputType=InputType.TYPE_NULL




        holder.shipping_number.setText(data[position].shipping_number+" "+
                cookie_data.shipping_name_ComboboxData[
                        cookie_data.shipping_number_ComboboxData.indexOf(
                            data[position].shipping_number)]
        )
        holder.shipping_number.inputType=InputType.TYPE_NULL





        if(data[position].date!=null){
            //date
            var readDateString=data[position].date
            //println(readDateString)
            val readDate=Calendar.getInstance()
            //println(readDateString.subSequence(0,4))
            readDate.set(Calendar.YEAR,readDateString!!.subSequence(0,4).toString().toInt())//yyyy
            // println(readDateString.subSequence(5,7))
            readDate.set(Calendar.MONTH,readDateString!!.subSequence(5,7).toString().toInt()-1)//MM
            // println(readDateString.subSequence(8,10))
            readDate.set(Calendar.DAY_OF_MONTH,readDateString!!.subSequence(8,10).toString().toInt())//dd
            var date=dateF.format(readDate.time)
            holder.date.setText(date)
        }
        else{
            holder.date.setText(null)
        }
        holder.date.inputType=InputType.TYPE_NULL



        holder.remark.setText(data[position].remark)
        holder.remark.isClickable=false
        holder.remark.isFocusable=false
        holder.remark.isFocusableInTouchMode=false
        holder.remark.isTextInputLayoutFocusedRectEnabled=false
        holder.deletebtn.isVisible=true
        holder.edit_btn.isVisible=true
        holder.next_btn.isVisible=true
        holder.cont_list_btn.isVisible=true
    }

    override fun getItemCount(): Int {
        return data.size//cookie_data.itemCount
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var _id=itemView.findViewById<TextInputEditText>(R.id.edit_id)
        var invalid=itemView.findViewById<TextInputEditText>(R.id.edit_invalid)
        var notice_number=itemView.findViewById<AutoCompleteTextView>(R.id.edit_notice_number)
        var customer_poNo=itemView.findViewById<AutoCompleteTextView>(R.id.edit_customer_poNo)
        var swe=itemView.findViewById<TextInputEditText>(R.id.edit_swe)
        var shipping_order_number=itemView.findViewById<TextInputEditText>(R.id.edit_shipping_order_number)
        var act_clearance_date=itemView.findViewById<TextInputEditText>(R.id.edit_act_clearance_date)
        var pre_clearance_date=itemView.findViewById<TextInputEditText>(R.id.edit_pre_clearance_date)
        var act_shipping_date=itemView.findViewById<TextInputEditText>(R.id.edit_act_shipping_date)
        var is_last=itemView.findViewById<AutoCompleteTextView>(R.id.edit_is_last)
        var oa_referenceNO1=itemView.findViewById<AutoCompleteTextView>(R.id.edit_oa_referenceNO1)
        var oa_referenceNO2=itemView.findViewById<TextInputEditText>(R.id.edit_oa_referenceNO2)
        var oa_referenceNO3=itemView.findViewById<TextInputEditText>(R.id.edit_oa_referenceNO3)
        var shipping_number=itemView.findViewById<AutoCompleteTextView>(R.id.edit_shipping_number)
        var date=itemView.findViewById<TextInputEditText>(R.id.edit_date)





        var edit_btn=itemView.findViewById<Button>(R.id.edit_btn)
        var deletebtn=itemView.findViewById<Button>(R.id.delete_btn)
        var next_btn=itemView.findViewById<Button>(R.id.next_btn)
        var cont_list_btn=itemView.findViewById<Button>(R.id.cont_list_btn)



        var remark=itemView.findViewById<TextInputEditText>(R.id.edit_remark)
        lateinit var oldData:BookingNoticeHeader
        lateinit var newData:BookingNoticeHeader
        var edit=false
        var datetemp1=""
        var datetemp2=""
        var datetemp3=""
        var datetemp4=""
        var datetemp5=""
        var oatemp2=""
        var oatemp3=""
        var tempship=""
        init {
            itemView.setOnClickListener{
                val position:Int=adapterPosition
                Log.d("GSON", "msg: ${position}\n")

            }

            val combobox=itemView.resources.getStringArray(R.array.combobox_yes_no)
            val arrayAdapter= ArrayAdapter(itemView.context,R.layout.combobox_item,combobox)
            val arrayAdapter01= ArrayAdapter(itemView.context,R.layout.combobox_item,cookie_data.customer_booking_prefix_ComboboxData)
            val arrayAdapter02= ArrayAdapter(itemView.context,R.layout.combobox_item,cookie_data.CustomerOrderHeader_poNo_ComboboxData)
            val arrayAdapter03= ArrayAdapter(itemView.context,R.layout.combobox_item,cookie_data.OAReference_oa_referenceNO1_ComboboxData)
            val arrayAdapter04= ArrayAdapter(itemView.context,R.layout.combobox_item,cookie_data.shipping_number_name_ComboboxData)

            //po#
            customer_poNo.setOnItemClickListener { parent, view, position, id ->
                if(cookie_data.CustomerOrderHeader_swe_ComboboxData[position]!=null){
                    var readDateString=cookie_data.CustomerOrderHeader_swe_ComboboxData[position]
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
                    swe.setText("")
                }

            }

            //結關日期
            act_clearance_date.setOnClickListener {
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
                            var date= dateF.format(SelectedDate.time)
                            act_clearance_date.setText(date)
                            SelectedDate.add(Calendar.DAY_OF_YEAR,7)
                            date=dateF.format(SelectedDate.time)
                            pre_clearance_date.setText(date)
                        },year,month,day).show()

                }
            }
           /* pre_clearance_date.setOnClickListener {
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
                            val date= dateF.format(SelectedDate.time)
                            pre_clearance_date.setText(date)
                        },year,month,day).show()

                }
            }*/
            //實際開船日
            act_shipping_date.setOnClickListener {
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
                            val date= dateF.format(SelectedDate.time)
                            act_shipping_date.setText(date)
                        },year,month,day).show()

                }
            }

            //開單日期
            date.setOnClickListener {
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
                            date.setText(Date)
                        },year,month,day).show()

                }
            }

            oa_referenceNO1.setOnItemClickListener { parent, view, position, id ->
                oa_referenceNO2.setText(cookie_data.OAReference_oa_referenceNO2_ComboboxData[position])
                oa_referenceNO3.setText(cookie_data.OAReference_oa_referenceNO3_ComboboxData[position])
            }


            //編輯按鈕
            edit_btn.setOnClickListener {
                when(edit_btn.text){
                    "編輯"->{
                        edit=true
                        oldData=data[adapterPosition]
                        //Log.d("GSON", "msg: ${oldData.id}\n")
                        //_id.inputType=InputType.TYPE_CLASS_TEXT
                        datetemp1=swe.text.toString()
                        datetemp2=act_clearance_date.text.toString()
                        datetemp3=pre_clearance_date.text.toString()
                        datetemp4=act_shipping_date.text.toString()
                        datetemp5=date.text.toString()
                        oatemp2=oa_referenceNO2.text.toString()
                        oatemp3=oa_referenceNO3.text.toString()
                        notice_number.setAdapter(arrayAdapter01)
                        notice_number.inputType=InputType.TYPE_CLASS_TEXT
                        customer_poNo.setAdapter(arrayAdapter02)
                        customer_poNo.inputType=InputType.TYPE_CLASS_TEXT
                        shipping_order_number.inputType=InputType.TYPE_CLASS_TEXT
                        is_last.setAdapter(arrayAdapter)
                        oa_referenceNO1.setAdapter(arrayAdapter03)
                        oa_referenceNO1.inputType=InputType.TYPE_CLASS_TEXT

                        shipping_number.setAdapter(arrayAdapter04)
                        shipping_number.inputType=InputType.TYPE_CLASS_TEXT
                        tempship=shipping_number.text.toString()

                        //date.inputType=InputType.TYPE_DATETIME_VARIATION_DATE

                        remark.isClickable=true
                        remark.isFocusable=true
                        remark.isFocusableInTouchMode=true
                        remark.isTextInputLayoutFocusedRectEnabled=true
                        //shipping_number.requestFocus()
                        edit_btn.text = "完成"
                    }
                    "完成"->{
                        edit=false
                        newData= oldData.copy()//BookingNoticeHeader()
                        //newData._id=_id.text.toString()
                        //_id.inputType=InputType.TYPE_NULL
                        newData.notice_number=notice_number.text.toString()
                        notice_number.setAdapter(null)
                        notice_number.inputType=InputType.TYPE_NULL
                        newData.customer_poNo=customer_poNo.text.toString()
                        customer_poNo.setAdapter(null)
                        customer_poNo.inputType=InputType.TYPE_NULL
                        newData.shipping_order_number=shipping_order_number.text.toString()
                        shipping_order_number.inputType=InputType.TYPE_NULL

                        if(act_clearance_date.text.toString()==""){
                            newData.act_clearance_date=null
                        }
                        else{
                            newData.act_clearance_date=act_clearance_date.text.toString().substring(0,act_clearance_date.text.toString().indexOf("("))
                        }
                        act_clearance_date.inputType=InputType.TYPE_NULL

                        if(act_shipping_date.text.toString()==""){
                            newData.act_shipping_date=null
                        }
                        else{
                            newData.act_shipping_date=act_shipping_date.text.toString().substring(0,act_shipping_date.text.toString().indexOf("("))
                        }
                        act_shipping_date.inputType=InputType.TYPE_NULL

                        newData.is_last=is_last.text.toString().toBoolean()
                        is_last.setAdapter(null)
                        newData.oa_referenceNO1=oa_referenceNO1.text.toString()
                        oa_referenceNO1.setAdapter(null)
                        oa_referenceNO1.inputType=InputType.TYPE_NULL
                        if(shipping_number.text.toString()==""){
                            newData.shipping_number=""
                        }
                        else{
                            newData.shipping_number=shipping_number.text.toString().substring(0,shipping_number.text.toString().indexOf(" "))
                        }
                        shipping_number.setAdapter(null)
                        shipping_number.inputType=InputType.TYPE_NULL
                        if(date.text.toString()==""){
                            newData.date=null
                        }
                        else{
                            newData.date=date.text.toString().substring(0,date.text.toString().indexOf("("))
                        }
                        date.inputType=InputType.TYPE_NULL




                        newData.remark=remark.text.toString()
                        remark.isClickable=false
                        remark.isFocusable=false
                        remark.isFocusableInTouchMode=false
                        remark.isTextInputLayoutFocusedRectEnabled=false
                        //Log.d("GSON", "msg: ${oldData}\n")
                        //Log.d("GSON", "msg: ${newData.remark}\n")
                        edit_BookingNotice("BookingNoticeHeader",oldData,newData)//更改資料庫資料
                        when(cookie_data.status){
                            0-> {//成功
                                data[adapterPosition] = newData//更改渲染資料
                                Toast.makeText(itemView.context, cookie_data.msg, Toast.LENGTH_SHORT).show()
                            }
                            1->{//失敗
                                Toast.makeText(itemView.context, cookie_data.msg, Toast.LENGTH_SHORT).show()
                                _id.setText(oldData._id)
                                notice_number.setText(oldData.notice_number)
                                swe.setText(datetemp1)
                                act_clearance_date.setText(datetemp2)
                                pre_clearance_date.setText(datetemp3)
                                act_shipping_date.setText(datetemp4)
                                is_last.setText(oldData.is_last.toString())
                                date.setText(datetemp5)
                                shipping_number.setText(tempship)
                                shipping_order_number.setText(oldData.shipping_order_number)
                                customer_poNo.setText(oldData.customer_poNo)
                                oa_referenceNO1.setText(oldData.oa_referenceNO1)
                                oa_referenceNO2.setText(oatemp2)
                                oa_referenceNO3.setText(oatemp3)
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
                    delete_BookingNotice("BookingNoticeHeader",data[adapterPosition])
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
            cont_list_btn.setOnClickListener {
                cookie_data.first_recyclerView=cookie_data.recyclerView
                var dialogF= PopUpContListFragment(data[adapterPosition]._id,data[adapterPosition].notice_number,data[adapterPosition].customer_poNo,swe.text.toString())
                dialogF.show( ( itemView.context as AppCompatActivity).supportFragmentManager ,"櫃編清單")
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
                    lock_BookingNotice("BookingNoticeHeader",data[adapterPosition])
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
                    over_BookingNotice("BookingNoticeHeader",data[adapterPosition])
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
        private fun edit_BookingNotice(operation:String,oldData:BookingNoticeHeader,newData:BookingNoticeHeader) {
            val old =JSONObject()
            old.put("_id",oldData._id)
            old.put("shipping_number",oldData.shipping_number)
            old.put("shipping_order_number",oldData.shipping_order_number)
            old.put("date",oldData.date)
            old.put("customer_poNo",oldData.customer_poNo)
            old.put("notice_number",oldData.notice_number)
            old.put("act_clearance_date",oldData.act_clearance_date)
            old.put("act_shipping_date",oldData.act_shipping_date)
            old.put("oa_referenceNO1",oldData.oa_referenceNO1)
            old.put("is_last",oldData.is_last)


            old.put("remark",oldData.remark)
            val new =JSONObject()
            new.put("_id",newData._id)
            new.put("shipping_number",newData.shipping_number)
            new.put("shipping_order_number",newData.shipping_order_number)
            new.put("date",newData.date)
            new.put("customer_poNo",newData.customer_poNo)
            new.put("notice_number",newData.notice_number)
            new.put("act_clearance_date",newData.act_clearance_date)
            new.put("act_shipping_date",newData.act_shipping_date)
            new.put("oa_referenceNO1",newData.oa_referenceNO1)
            new.put("is_last",newData.is_last)

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
        private fun delete_BookingNotice(operation:String,deleteData:BookingNoticeHeader) {
            val delete = JSONObject()
            delete.put("_id",deleteData._id)
            delete.put("shipping_number",deleteData.shipping_number)
            delete.put("shipping_order_number",deleteData.shipping_order_number)
            delete.put("date",deleteData.date)
            delete.put("customer_poNo",deleteData.customer_poNo)
            delete.put("notice_number",deleteData.notice_number)
            delete.put("act_clearance_date",deleteData.act_clearance_date)
            delete.put("act_shipping_date",deleteData.act_shipping_date)
            delete.put("oa_referenceNO1",deleteData.oa_referenceNO1)
            delete.put("is_last",deleteData.is_last)

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
        private fun lock_BookingNotice(operation:String,lockData:BookingNoticeHeader) {
            val lock = JSONObject()
            lock.put("_id",lockData._id)
            lock.put("shipping_number",lockData.shipping_number)
            lock.put("shipping_order_number",lockData.shipping_order_number)
            lock.put("date",lockData.date)
            lock.put("customer_poNo",lockData.customer_poNo)
            lock.put("notice_number",lockData.notice_number)
            lock.put("act_clearance_date",lockData.act_clearance_date)
            lock.put("act_shipping_date",lockData.act_shipping_date)
            lock.put("oa_referenceNO1",lockData.oa_referenceNO1)
            lock.put("is_last",lockData.is_last)

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
        private fun over_BookingNotice(operation:String,overData:BookingNoticeHeader) {
            val over = JSONObject()
            over.put("_id",overData._id)
            over.put("shipping_number",overData.shipping_number)
            over.put("shipping_order_number",overData.shipping_order_number)
            over.put("date",overData.date)
            over.put("customer_poNo",overData.customer_poNo)
            over.put("notice_number",overData.notice_number)
            over.put("act_clearance_date",overData.act_clearance_date)
            over.put("act_shipping_date",overData.act_shipping_date)
            over.put("oa_referenceNO1",overData.oa_referenceNO1)
            over.put("is_last",overData.is_last)

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
    fun addItem(addData:BookingNoticeHeader){
        data.add(data.size,addData)
        notifyItemInserted(data.size)
        cookie_data.recyclerView.smoothScrollToPosition(data.size)
       // itemData.count+=1//cookie_data.itemCount+=1
    }

}


