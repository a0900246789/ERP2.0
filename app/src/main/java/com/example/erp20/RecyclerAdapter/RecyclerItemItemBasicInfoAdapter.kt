package com.example.erp20.RecyclerAdapter
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


class RecyclerItemItemBasicInfoAdapter() :
    RecyclerView.Adapter<RecyclerItemItemBasicInfoAdapter.ViewHolder>() {
    private val itemData: ShowItemBasicInfo =Gson().fromJson(cookie_data.response_data, ShowItemBasicInfo::class.java)
    private var data: ArrayList<ItemBasicInfo> =itemData.data

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemItemBasicInfoAdapter.ViewHolder {
        val v= LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_item_basic_info,parent,false)
        return ViewHolder(v)

    }

    override fun onBindViewHolder(holder: RecyclerItemItemBasicInfoAdapter.ViewHolder, position: Int) {

        //Log.d("GSON", "msg: ${itemData.data[position]}\n")
        holder._id.setText(data[position]._id)
        holder._id.inputType=InputType.TYPE_NULL
        holder.name.setText(data[position].name)
        holder.name.inputType=InputType.TYPE_NULL
        holder.specification.setText(data[position].specification)
        holder.specification.inputType=InputType.TYPE_NULL
        holder.size.setText(data[position].size)
        holder.size.inputType=InputType.TYPE_NULL
        holder.unit_of_measurement.setText(data[position].unit_of_measurement)
        holder.unit_of_measurement.inputType=InputType.TYPE_NULL
        holder.item_type.setText(data[position].item_type)
        holder.item_type.inputType=InputType.TYPE_NULL
        holder.semi_finished_product_number.setText(data[position].semi_finished_product_number)
        holder.semi_finished_product_number.inputType=InputType.TYPE_NULL
        holder.is_purchased_parts.setText(data[position].is_purchased_parts.toString())
        holder.is_purchased_parts.isClickable=false
        holder.is_purchased_parts.inputType=InputType.TYPE_NULL
        holder.MOQ.setText(data[position].MOQ.toString())
        holder.MOQ.inputType=InputType.TYPE_NULL
        holder.MOQ_time.setText(data[position].MOQ_time)
        holder.MOQ_time.inputType=InputType.TYPE_NULL
        holder.batch.setText(data[position].batch.toString())
        holder.batch.inputType=InputType.TYPE_NULL
        holder.batch_time.setText(data[position].batch_time)
        holder.batch_time.inputType=InputType.TYPE_NULL
        holder.vender_id.setText(data[position].vender_id)
        holder.vender_id.inputType=InputType.TYPE_NULL
        holder.is_machining_parts.setText(data[position].is_machining_parts.toString())
        holder.is_machining_parts.isClickable=false
        holder.is_machining_parts.inputType=InputType.TYPE_NULL
        holder.pline_id.setText(data[position].pline_id)
        holder.pline_id.inputType=InputType.TYPE_NULL
        holder.change_mold.setText(data[position].change_mold.toString())
        holder.change_mold.inputType=InputType.TYPE_NULL
        holder.mold_unit_of_timer.setText(data[position].mold_unit_of_timer)
        holder.mold_unit_of_timer.inputType=InputType.TYPE_NULL
        holder.change_powder.setText(data[position].change_powder.toString())
        holder.change_powder.inputType=InputType.TYPE_NULL
        holder.powder_unit_of_timer.setText(data[position].powder_unit_of_timer)
        holder.powder_unit_of_timer.inputType=InputType.TYPE_NULL
        holder.LT.setText(data[position].LT)
        holder.LT.inputType=InputType.TYPE_NULL
        holder.LT_unit_of_timer.setText(data[position].LT_unit_of_timer)
        holder.LT_unit_of_timer.inputType=InputType.TYPE_NULL
        holder.feed_in_advance_day.setText(data[position].feed_in_advance_day.toString())
        holder.feed_in_advance_day.inputType=InputType.TYPE_NULL
        holder.feed_in_advance_time.setText(data[position].feed_in_advance_time)
        holder.feed_in_advance_time.inputType=InputType.TYPE_NULL
        holder.release_date.setText(data[position].release_date)
        holder.release_date.inputType=InputType.TYPE_NULL
        holder.is_schedule_adjustment_materials.setText(data[position].is_schedule_adjustment_materials.toString())
        holder.is_schedule_adjustment_materials.isClickable=false
        holder.is_schedule_adjustment_materials.inputType=InputType.TYPE_NULL
        holder.schedule_adjustment_materials_time.setText(data[position].schedule_adjustment_materials_time)
        holder.schedule_adjustment_materials_time.inputType=InputType.TYPE_NULL
        holder.is_long_delivery.setText(data[position].is_long_delivery.toString())
        holder.is_long_delivery.isClickable=false
        holder.is_long_delivery.inputType=InputType.TYPE_NULL
        holder.long_delivery_time.setText(data[position].long_delivery_time)
        holder.long_delivery_time.inputType=InputType.TYPE_NULL
        holder.is_low_yield.setText(data[position].is_low_yield.toString())
        holder.is_low_yield.isClickable=false
        holder.is_low_yield.inputType=InputType.TYPE_NULL
        holder.low_yield_time.setText(data[position].low_yield_time)
        holder.low_yield_time.inputType=InputType.TYPE_NULL
        holder.is_min_manpower.setText(data[position].is_min_manpower.toString())
        holder.is_min_manpower.isClickable=false
        holder.is_min_manpower.inputType=InputType.TYPE_NULL
        holder.min_manpower_reduce_ratio.setText(data[position].min_manpower_reduce_ratio.toString())
        holder.min_manpower_reduce_ratio.inputType=InputType.TYPE_NULL
        holder.is_standard_manpower.setText(data[position].is_standard_manpower.toString())
        holder.is_standard_manpower.isClickable=false
        holder.is_standard_manpower.inputType=InputType.TYPE_NULL
        holder.standard_manpower.setText(data[position].standard_manpower.toString())
        holder.standard_manpower.inputType=InputType.TYPE_NULL
        holder.unit_of_timer.setText(data[position].unit_of_timer)
        holder.unit_of_timer.inputType=InputType.TYPE_NULL
        holder.open_line_time.setText(data[position].open_line_time)
        holder.open_line_time.inputType=InputType.TYPE_NULL
        holder.card_number.setText(data[position].card_number)
        holder.card_number.inputType=InputType.TYPE_NULL
        holder.number_of_accounts.setText(data[position].number_of_accounts.toString())
        holder.number_of_accounts.inputType=InputType.TYPE_NULL
        holder.settlement_date.setText(data[position].settlement_date)
        holder.settlement_date.inputType=InputType.TYPE_NULL
        holder.is_production_materials.setText(data[position].is_production_materials.toString())
        holder.is_production_materials.isClickable=false
        holder.is_production_materials.inputType=InputType.TYPE_NULL



        holder.creator.setText(data[position].creator)
        holder.creator_time.setText(data[position].create_time)
        holder.editor.setText(data[position].editor)
        holder.editor_time.setText(data[position].edit_time)
        holder.lock.setText(data[position].Lock.toString())
        holder.lock_time.setText(data[position].lock_time)
        holder.invalid.setText(data[position].invalid.toString())
        holder.invalid_time.setText(data[position].invalid_time)
        holder.remark.setText(data[position].remark)
        holder.remark.isClickable=false
        holder.remark.isFocusable=false
        holder.remark.isFocusableInTouchMode=false
        holder.remark.isTextInputLayoutFocusedRectEnabled=false
        holder.deletebtn.isVisible=true
        holder.edit_btn.isVisible=true
        holder.lockbtn.isVisible=true

    }

    override fun getItemCount(): Int {
        return itemData.count
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var _id=itemView.findViewById<TextInputEditText>(R.id.edit_id)
        var name=itemView.findViewById<TextInputEditText>(R.id.edit_name)
        var specification=itemView.findViewById<TextInputEditText>(R.id.edit_specification)
        var size=itemView.findViewById<TextInputEditText>(R.id.edit_size)
        var unit_of_measurement=itemView.findViewById<TextInputEditText>(R.id.edit_unit_of_measurement)
        var item_type=itemView.findViewById<TextInputEditText>(R.id.edit_item_type)
        var semi_finished_product_number=itemView.findViewById<TextInputEditText>(R.id.edit_semi_finished_product_number)
        var is_purchased_parts=itemView.findViewById<AutoCompleteTextView>(R.id.edit_is_purchased_parts)
        var MOQ=itemView.findViewById<TextInputEditText>(R.id.edit_MOQ)
        var MOQ_time=itemView.findViewById<TextInputEditText>(R.id.edit_MOQ_time)
        var batch=itemView.findViewById<TextInputEditText>(R.id.edit_batch)
        var batch_time=itemView.findViewById<TextInputEditText>(R.id.edit_batch_time)
        var vender_id=itemView.findViewById<TextInputEditText>(R.id.edit_vender_id)
        var is_machining_parts=itemView.findViewById<AutoCompleteTextView>(R.id.edit_is_machining_parts)
        var pline_id=itemView.findViewById<TextInputEditText>(R.id.edit_pline_id)
        var change_mold=itemView.findViewById<TextInputEditText>(R.id.edit_change_mold)
        var mold_unit_of_timer=itemView.findViewById<TextInputEditText>(R.id.edit_mold_unit_of_timer)
        var change_powder=itemView.findViewById<TextInputEditText>(R.id.edit_change_powder)
        var powder_unit_of_timer=itemView.findViewById<TextInputEditText>(R.id.edit_powder_unit_of_timer)
        var LT=itemView.findViewById<TextInputEditText>(R.id.edit_LT)
        var LT_unit_of_timer=itemView.findViewById<TextInputEditText>(R.id.edit_LT_unit_of_timer)
        var feed_in_advance_day=itemView.findViewById<TextInputEditText>(R.id.edit_feed_in_advance_day)
        var feed_in_advance_time=itemView.findViewById<TextInputEditText>(R.id.edit_feed_in_advance_time)
        var release_date=itemView.findViewById<TextInputEditText>(R.id.edit_release_date)
        var is_schedule_adjustment_materials=itemView.findViewById<AutoCompleteTextView>(R.id.edit_is_schedule_adjustment_materials)
        var schedule_adjustment_materials_time=itemView.findViewById<TextInputEditText>(R.id.edit_schedule_adjustment_materials_time)
        var is_long_delivery=itemView.findViewById<AutoCompleteTextView>(R.id.edit_is_long_delivery)
        var long_delivery_time=itemView.findViewById<TextInputEditText>(R.id.edit_long_delivery_time)
        var is_low_yield=itemView.findViewById<AutoCompleteTextView>(R.id.edit_is_low_yield)
        var low_yield_time=itemView.findViewById<TextInputEditText>(R.id.edit_low_yield_time)
        var is_min_manpower=itemView.findViewById<AutoCompleteTextView>(R.id.edit_is_min_manpower)
        var min_manpower_reduce_ratio=itemView.findViewById<TextInputEditText>(R.id.edit_min_manpower_reduce_ratio)
        var is_standard_manpower=itemView.findViewById<AutoCompleteTextView>(R.id.edit_is_standard_manpower)
        var standard_manpower=itemView.findViewById<TextInputEditText>(R.id.edit_standard_manpower)
        var unit_of_timer=itemView.findViewById<TextInputEditText>(R.id.edit_unit_of_timer)
        var open_line_time=itemView.findViewById<TextInputEditText>(R.id.edit_open_line_time)
        var card_number=itemView.findViewById<TextInputEditText>(R.id.edit_card_number)
        var number_of_accounts=itemView.findViewById<TextInputEditText>(R.id.edit_number_of_accounts)
        var settlement_date=itemView.findViewById<TextInputEditText>(R.id.edit_settlement_date)
        var is_production_materials=itemView.findViewById<AutoCompleteTextView>(R.id.edit_is_production_materials)



        var edit_btn=itemView.findViewById<Button>(R.id.edit_btn)
        var deletebtn=itemView.findViewById<Button>(R.id.delete_btn)
        var lockbtn=itemView.findViewById<Button>(R.id.lock_btn)
        var creator=itemView.findViewById<AutoCompleteTextView>(R.id.edit_creator)
        var creator_time=itemView.findViewById<AutoCompleteTextView>(R.id.edit_create_time)
        var editor=itemView.findViewById<AutoCompleteTextView>(R.id.edit_editor)
        var editor_time=itemView.findViewById<AutoCompleteTextView>(R.id.edit_edit_time)
        var lock=itemView.findViewById<AutoCompleteTextView>(R.id.edit_lock)
        var lock_time=itemView.findViewById<AutoCompleteTextView>(R.id.edit_lock_time)
        var invalid=itemView.findViewById<AutoCompleteTextView>(R.id.edit_invalid)
        var invalid_time=itemView.findViewById<AutoCompleteTextView>(R.id.edit_invalid_time)
        var remark=itemView.findViewById<TextInputEditText>(R.id.edit_remark)
        lateinit var oldData:ItemBasicInfo
        lateinit var newData:ItemBasicInfo
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
                        oldData=itemData.data[adapterPosition]
                        //Log.d("GSON", "msg: ${oldData.id}\n")
                        _id.inputType=InputType.TYPE_CLASS_TEXT
                        name.inputType=InputType.TYPE_CLASS_TEXT
                        specification.inputType=InputType.TYPE_CLASS_TEXT
                        size.inputType=InputType.TYPE_CLASS_TEXT
                        unit_of_measurement.inputType=InputType.TYPE_CLASS_TEXT
                        item_type.inputType=InputType.TYPE_CLASS_TEXT
                        semi_finished_product_number.inputType=InputType.TYPE_CLASS_TEXT
                        is_purchased_parts.setAdapter(arrayAdapter)
                        is_purchased_parts.isClickable=true
                        MOQ.inputType=InputType.TYPE_CLASS_NUMBER
                        MOQ_time.inputType=InputType.TYPE_CLASS_DATETIME
                        batch.inputType=InputType.TYPE_CLASS_NUMBER
                        batch_time.inputType=InputType.TYPE_CLASS_DATETIME
                        vender_id.inputType=InputType.TYPE_CLASS_TEXT
                        is_machining_parts.setAdapter(arrayAdapter)
                        is_machining_parts.isClickable=true
                        pline_id.inputType=InputType.TYPE_CLASS_TEXT
                        change_mold.inputType=InputType.TYPE_CLASS_NUMBER
                        mold_unit_of_timer.inputType=InputType.TYPE_CLASS_TEXT
                        change_powder.inputType=InputType.TYPE_CLASS_NUMBER
                        powder_unit_of_timer.inputType=InputType.TYPE_CLASS_TEXT
                        LT.inputType=InputType.TYPE_CLASS_DATETIME
                        LT_unit_of_timer.inputType=InputType.TYPE_CLASS_TEXT
                        feed_in_advance_day.inputType=InputType.TYPE_CLASS_NUMBER
                        feed_in_advance_time.inputType=InputType.TYPE_CLASS_DATETIME
                        release_date.inputType=InputType.TYPE_DATETIME_VARIATION_DATE
                        is_schedule_adjustment_materials.setAdapter(arrayAdapter)
                        is_schedule_adjustment_materials.isClickable=true
                        schedule_adjustment_materials_time.inputType=InputType.TYPE_CLASS_DATETIME
                        is_long_delivery.setAdapter(arrayAdapter)
                        is_long_delivery.isClickable=true
                        long_delivery_time.inputType=InputType.TYPE_CLASS_DATETIME
                        is_low_yield.setAdapter(arrayAdapter)
                        is_low_yield.isClickable=true
                        low_yield_time.inputType=InputType.TYPE_CLASS_DATETIME
                        is_min_manpower.setAdapter(arrayAdapter)
                        is_min_manpower.isClickable=true
                        min_manpower_reduce_ratio.inputType=InputType.TYPE_CLASS_NUMBER
                        is_standard_manpower.setAdapter(arrayAdapter)
                        is_standard_manpower.isClickable=true
                        standard_manpower.inputType=InputType.TYPE_CLASS_NUMBER
                        unit_of_timer.inputType=InputType.TYPE_CLASS_TEXT
                        open_line_time.inputType=InputType.TYPE_CLASS_DATETIME
                        card_number.inputType=InputType.TYPE_CLASS_TEXT
                        number_of_accounts.inputType=InputType.TYPE_CLASS_NUMBER
                        settlement_date.inputType=InputType.TYPE_CLASS_DATETIME
                        is_production_materials.setAdapter(arrayAdapter)
                        is_production_materials.isClickable=true





                        remark.isClickable=true
                        remark.isFocusable=true
                        remark.isFocusableInTouchMode=true
                        remark.isTextInputLayoutFocusedRectEnabled=true
                        _id.requestFocus()
                        edit_btn.text = "完成"
                    }
                    "完成"->{
                        newData= ItemBasicInfo()

                        newData._id=_id.text.toString()
                        newData.name=name.text.toString()
                        newData.specification=specification.text.toString()
                        newData.size=size.text.toString()
                        newData.unit_of_measurement=unit_of_measurement.text.toString()
                        newData.item_type=item_type.text.toString()
                        newData.semi_finished_product_number=semi_finished_product_number.text.toString()
                        newData.is_purchased_parts=is_purchased_parts.text.toString().toBoolean()
                        newData.MOQ=MOQ.text.toString().toDouble()
                        newData.MOQ_time=MOQ_time.text.toString()

                        newData.batch=batch.text.toString().toDouble()
                        newData.batch_time=batch_time.text.toString()
                        newData.vender_id=vender_id.text.toString()
                        newData.is_machining_parts=is_machining_parts.text.toString().toBoolean()
                        newData.pline_id=pline_id.text.toString()
                        newData.change_mold=change_mold.text.toString().toDouble()
                        newData.mold_unit_of_timer=mold_unit_of_timer.text.toString()
                        newData.change_powder=change_powder.text.toString().toDouble()
                        newData.powder_unit_of_timer=powder_unit_of_timer.text.toString()
                        newData.LT=LT.text.toString()

                        newData.LT_unit_of_timer=LT_unit_of_timer.text.toString()
                        newData.feed_in_advance_day=feed_in_advance_day.text.toString().toInt()
                        newData.feed_in_advance_time=feed_in_advance_time.text.toString()
                        newData.release_date=release_date.text.toString()
                        newData.is_schedule_adjustment_materials=is_schedule_adjustment_materials.text.toString().toBoolean()
                        newData.schedule_adjustment_materials_time=schedule_adjustment_materials_time.text.toString()
                        newData.is_long_delivery=is_long_delivery.text.toString().toBoolean()
                        newData.long_delivery_time=long_delivery_time.text.toString()
                        newData.is_low_yield=is_low_yield.text.toString().toBoolean()
                        newData.low_yield_time=low_yield_time.text.toString()

                        newData.is_min_manpower=is_min_manpower.text.toString().toBoolean()
                        newData.min_manpower_reduce_ratio=min_manpower_reduce_ratio.text.toString().toDouble()
                        newData.is_standard_manpower=is_standard_manpower.text.toString().toBoolean()
                        newData.standard_manpower=standard_manpower.text.toString().toInt()
                        newData.unit_of_timer=unit_of_timer.text.toString()
                        newData.open_line_time=open_line_time.text.toString()
                        newData.card_number=card_number.text.toString()
                        newData.number_of_accounts=number_of_accounts.text.toString().toDouble()
                        newData.settlement_date=settlement_date.text.toString()
                        newData.is_production_materials=is_production_materials.text.toString().toBoolean()



                        newData.remark=remark.text.toString()
                        //Log.d("GSON", "msg: ${oldData}\n")
                        //Log.d("GSON", "msg: ${newData.remark}\n")
                        _id.inputType=InputType.TYPE_NULL
                        name.inputType=InputType.TYPE_NULL
                        specification.inputType=InputType.TYPE_NULL
                        size.inputType=InputType.TYPE_NULL
                        unit_of_measurement.inputType=InputType.TYPE_NULL
                        item_type.inputType=InputType.TYPE_NULL
                        semi_finished_product_number.inputType=InputType.TYPE_NULL
                        is_purchased_parts.setAdapter(null)
                        is_purchased_parts.isClickable=false
                        MOQ.inputType=InputType.TYPE_NULL
                        MOQ_time.inputType=InputType.TYPE_NULL
                        batch.inputType=InputType.TYPE_NULL
                        batch_time.inputType=InputType.TYPE_NULL
                        vender_id.inputType=InputType.TYPE_NULL
                        is_machining_parts.setAdapter(null)
                        is_machining_parts.isClickable=false
                        pline_id.inputType=InputType.TYPE_NULL
                        change_mold.inputType=InputType.TYPE_NULL
                        mold_unit_of_timer.inputType=InputType.TYPE_NULL
                        change_powder.inputType=InputType.TYPE_NULL
                        powder_unit_of_timer.inputType=InputType.TYPE_NULL
                        LT.inputType=InputType.TYPE_NULL
                        LT_unit_of_timer.inputType=InputType.TYPE_NULL
                        feed_in_advance_day.inputType=InputType.TYPE_NULL
                        feed_in_advance_time.inputType=InputType.TYPE_NULL
                        release_date.inputType=InputType.TYPE_NULL
                        is_schedule_adjustment_materials.setAdapter(null)
                        is_schedule_adjustment_materials.isClickable=false
                        schedule_adjustment_materials_time.inputType=InputType.TYPE_NULL
                        is_long_delivery.setAdapter(null)
                        is_long_delivery.isClickable=false
                        long_delivery_time.inputType=InputType.TYPE_NULL
                        is_low_yield.setAdapter(null)
                        is_low_yield.isClickable=false
                        low_yield_time.inputType=InputType.TYPE_NULL
                        is_min_manpower.setAdapter(null)
                        is_min_manpower.isClickable=false
                        min_manpower_reduce_ratio.inputType=InputType.TYPE_NULL
                        is_standard_manpower.setAdapter(null)
                        is_standard_manpower.isClickable=false
                        standard_manpower.inputType=InputType.TYPE_NULL
                        unit_of_timer.inputType=InputType.TYPE_NULL
                        open_line_time.inputType=InputType.TYPE_NULL
                        card_number.inputType=InputType.TYPE_NULL
                        number_of_accounts.inputType=InputType.TYPE_NULL
                        settlement_date.inputType=InputType.TYPE_NULL
                        is_production_materials.setAdapter(null)
                        is_production_materials.isClickable=false


                        remark.isClickable=false
                        remark.isFocusable=false
                        remark.isFocusableInTouchMode=false
                        remark.isTextInputLayoutFocusedRectEnabled=false
                        editBasic("ItemBasicInfo",oldData,newData)//更改資料庫資料
                        when(cookie_data.status){
                            0-> {//成功
                                itemData.data[adapterPosition] = newData//更改渲染資料
                                editor.setText(cookie_data.username)
                                Toast.makeText(itemView.context, cookie_data.msg, Toast.LENGTH_SHORT).show()
                            }
                            1->{//失敗
                                Toast.makeText(itemView.context, cookie_data.msg, Toast.LENGTH_SHORT).show()

                                _id.setText(oldData._id)
                                name.setText(oldData.name)
                                specification.setText(oldData.specification)
                                size.setText(oldData.size)
                                unit_of_measurement.setText(oldData.unit_of_measurement)
                                item_type.setText(oldData.item_type)
                                semi_finished_product_number.setText(oldData.semi_finished_product_number)
                                is_purchased_parts.setText(oldData.is_purchased_parts.toString())
                                MOQ.setText(oldData.MOQ.toString())
                                MOQ_time.setText(oldData.MOQ_time)

                                batch.setText(oldData.batch.toString())
                                batch_time.setText(oldData.batch_time)
                                vender_id.setText(oldData.vender_id)
                                is_machining_parts.setText(oldData.is_machining_parts.toString())
                                pline_id.setText(oldData.pline_id)
                                change_mold.setText(oldData.change_mold.toString())
                                mold_unit_of_timer.setText(oldData.mold_unit_of_timer)
                                change_powder.setText(oldData.change_powder.toString())
                                powder_unit_of_timer.setText(oldData.powder_unit_of_timer)
                                LT.setText(oldData.LT)

                                LT_unit_of_timer.setText(oldData.LT_unit_of_timer)
                                feed_in_advance_day.setText(oldData.feed_in_advance_day.toString())
                                feed_in_advance_time.setText(oldData.feed_in_advance_time)
                                release_date.setText(oldData.release_date)
                                is_schedule_adjustment_materials.setText(oldData.is_schedule_adjustment_materials.toString())
                                schedule_adjustment_materials_time.setText(oldData.schedule_adjustment_materials_time)
                                is_long_delivery.setText(oldData.is_long_delivery.toString())
                                long_delivery_time.setText(oldData.long_delivery_time)
                                is_low_yield.setText(oldData.is_low_yield.toString())
                                low_yield_time.setText(oldData.low_yield_time)

                                is_min_manpower.setText(oldData.is_min_manpower.toString())
                                min_manpower_reduce_ratio.setText(oldData.min_manpower_reduce_ratio.toString())
                                is_standard_manpower.setText(oldData.is_standard_manpower.toString())
                                standard_manpower.setText(oldData.standard_manpower.toString())
                                unit_of_timer.setText(oldData.unit_of_timer)
                                open_line_time.setText(oldData.open_line_time)
                                card_number.setText(oldData.card_number)
                                number_of_accounts.setText(oldData.number_of_accounts.toString())
                                settlement_date.setText(oldData.settlement_date)
                                is_production_materials.setText(oldData.is_production_materials.toString())


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
                    deleteBasic("ItemBasicInfo",data[adapterPosition])
                    when(cookie_data.status){
                        0-> {//成功
                            data.removeAt(adapterPosition)
                            notifyItemRemoved(adapterPosition)
                            itemData.count-=1
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
                    lockBasic("ItemBasicInfo",data[adapterPosition])
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
        }
        private fun editBasic(operation:String,oldData:ItemBasicInfo,newData:ItemBasicInfo) {
            val old =JSONObject()
            old.put("_id",oldData._id)
            old.put("name",oldData.name)
            old.put("specification",oldData.specification)
            old.put("size",oldData.size)
            old.put("unit_of_measurement",oldData.unit_of_measurement)
            old.put("item_type",oldData.item_type)
            old.put("semi_finished_product_number",oldData.semi_finished_product_number)
            old.put("is_purchased_parts",oldData.is_purchased_parts)
            old.put("MOQ",oldData.MOQ)
            old.put("MOQ_time",oldData.MOQ_time)

            old.put("batch",oldData.batch)
            old.put("batch_time",oldData.batch_time)
            old.put("vender_id",oldData.vender_id)
            old.put("is_machining_parts",oldData.is_machining_parts)
            old.put("pline_id",oldData.pline_id)
            old.put("change_mold",oldData.change_mold)
            old.put("mold_unit_of_timer",oldData.mold_unit_of_timer)
            old.put("change_powder",oldData.change_powder)
            old.put("powder_unit_of_timer",oldData.powder_unit_of_timer)
            old.put("LT",oldData.LT)

            old.put("LT_unit_of_timer",oldData.LT_unit_of_timer)
            old.put("feed_in_advance_day",oldData.feed_in_advance_day)
            old.put("feed_in_advance_time",oldData.feed_in_advance_time)
            old.put("release_date",oldData.release_date)
            old.put("is_schedule_adjustment_materials",oldData.is_schedule_adjustment_materials)
            old.put("schedule_adjustment_materials_time",oldData.schedule_adjustment_materials_time)
            old.put("is_long_delivery",oldData.is_long_delivery)
            old.put("long_delivery_time",oldData.long_delivery_time)
            old.put("is_low_yield",oldData.is_low_yield)
            old.put("low_yield_time",oldData.low_yield_time)

            old.put("is_min_manpower",oldData.is_min_manpower)
            old.put("min_manpower_reduce_ratio",oldData.min_manpower_reduce_ratio)
            old.put("is_standard_manpower",oldData.is_standard_manpower)
            old.put("standard_manpower",oldData.standard_manpower)
            old.put("unit_of_timer",oldData.unit_of_timer)
            old.put("open_line_time",oldData.open_line_time)
            old.put("card_number",oldData.card_number)
            old.put("number_of_accounts",oldData.number_of_accounts)
            old.put("settlement_date",oldData.settlement_date)
            old.put("is_production_materials",oldData.is_production_materials)

            old.put("remark",oldData.remark)

            val new =JSONObject()
            new.put("_id",newData._id)
            new.put("name",newData.name)
            new.put("specification",newData.specification)
            new.put("size",newData.size)
            new.put("unit_of_measurement",newData.unit_of_measurement)
            new.put("item_type",newData.item_type)
            new.put("semi_finished_product_number",newData.semi_finished_product_number)
            new.put("is_purchased_parts",newData.is_purchased_parts)
            new.put("MOQ",newData.MOQ)
            new.put("MOQ_time",newData.MOQ_time)

            new.put("batch",newData.batch)
            new.put("batch_time",newData.batch_time)
            new.put("vender_id",newData.vender_id)
            new.put("is_machining_parts",newData.is_machining_parts)
            new.put("pline_id",newData.pline_id)
            new.put("change_mold",newData.change_mold)
            new.put("mold_unit_of_timer",newData.mold_unit_of_timer)
            new.put("change_powder",newData.change_powder)
            new.put("powder_unit_of_timer",newData.powder_unit_of_timer)
            new.put("LT",newData.LT)

            new.put("LT_unit_of_timer",newData.LT_unit_of_timer)
            new.put("feed_in_advance_day",newData.feed_in_advance_day)
            new.put("feed_in_advance_time",newData.feed_in_advance_time)
            new.put("release_date",newData.release_date)
            new.put("is_schedule_adjustment_materials",newData.is_schedule_adjustment_materials)
            new.put("schedule_adjustment_materials_time",newData.schedule_adjustment_materials_time)
            new.put("is_long_delivery",newData.is_long_delivery)
            new.put("long_delivery_time",newData.long_delivery_time)
            new.put("is_low_yield",newData.is_low_yield)
            new.put("low_yield_time",newData.low_yield_time)

            new.put("is_min_manpower",newData.is_min_manpower)
            new.put("min_manpower_reduce_ratio",newData.min_manpower_reduce_ratio)
            new.put("is_standard_manpower",newData.is_standard_manpower)
            new.put("standard_manpower",newData.standard_manpower)
            new.put("unit_of_timer",newData.unit_of_timer)
            new.put("open_line_time",newData.open_line_time)
            new.put("card_number",newData.card_number)
            new.put("number_of_accounts",newData.number_of_accounts)
            new.put("settlement_date",newData.settlement_date)
            new.put("is_production_materials",newData.is_production_materials)

            new.put("editor",cookie_data.username)
            new.put("remark",newData.remark)
            val data = JSONArray()
            data.put(old)
            data.put((new))
            val body = FormBody.Builder()
                .add("card_number",cookie_data.card_number)
                .add("data",data.toString())
                .add("username", cookie_data.username)
                .add("operation", operation)
                .add("action","2")
                .add("csrfmiddlewaretoken", cookie_data.tokenValue)
                .add("login_flag", cookie_data.loginflag)
                .build()
            val request = Request.Builder()
                .url("http://140.125.46.125:8000/basic_management")
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
        private fun deleteBasic(operation:String,deleteData:ItemBasicInfo) {
            val delete = JSONObject()
            delete.put("_id",deleteData._id)
            delete.put("name",deleteData.name)
            delete.put("specification",deleteData.specification)
            delete.put("size",deleteData.size)
            delete.put("unit_of_measurement",deleteData.unit_of_measurement)
            delete.put("item_type",deleteData.item_type)
            delete.put("semi_finished_product_number",deleteData.semi_finished_product_number)
            delete.put("is_purchased_parts",deleteData.is_purchased_parts)
            delete.put("MOQ",deleteData.MOQ)
            delete.put("MOQ_time",deleteData.MOQ_time)

            delete.put("batch",deleteData.batch)
            delete.put("batch_time",deleteData.batch_time)
            delete.put("vender_id",deleteData.vender_id)
            delete.put("is_machining_parts",deleteData.is_machining_parts)
            delete.put("pline_id",deleteData.pline_id)
            delete.put("change_mold",deleteData.change_mold)
            delete.put("mold_unit_of_timer",deleteData.mold_unit_of_timer)
            delete.put("change_powder",deleteData.change_powder)
            delete.put("powder_unit_of_timer",deleteData.powder_unit_of_timer)
            delete.put("LT",deleteData.LT)

            delete.put("LT_unit_of_timer",deleteData.LT_unit_of_timer)
            delete.put("feed_in_advance_day",deleteData.feed_in_advance_day)
            delete.put("feed_in_advance_time",deleteData.feed_in_advance_time)
            delete.put("release_date",deleteData.release_date)
            delete.put("is_schedule_adjustment_materials",deleteData.is_schedule_adjustment_materials)
            delete.put("schedule_adjustment_materials_time",deleteData.schedule_adjustment_materials_time)
            delete.put("is_long_delivery",deleteData.is_long_delivery)
            delete.put("long_delivery_time",deleteData.long_delivery_time)
            delete.put("is_low_yield",deleteData.is_low_yield)
            delete.put("low_yield_time",deleteData.low_yield_time)

            delete.put("is_min_manpower",deleteData.is_min_manpower)
            delete.put("min_manpower_reduce_ratio",deleteData.min_manpower_reduce_ratio)
            delete.put("is_standard_manpower",deleteData.is_standard_manpower)
            delete.put("standard_manpower",deleteData.standard_manpower)
            delete.put("unit_of_timer",deleteData.unit_of_timer)
            delete.put("open_line_time",deleteData.open_line_time)
            delete.put("card_number",deleteData.card_number)
            delete.put("number_of_accounts",deleteData.number_of_accounts)
            delete.put("settlement_date",deleteData.settlement_date)
            delete.put("is_production_materials",deleteData.is_production_materials)

            delete.put("remark",deleteData.remark)
            //Log.d("GSON", "msg:${delete}")
            val body = FormBody.Builder()
                .add("card_number",cookie_data.card_number)
                .add("username", cookie_data.username)
                .add("operation", operation)
                .add("action","3")
                .add("data",delete.toString())
                .add("csrfmiddlewaretoken", cookie_data.tokenValue)
                .add("login_flag", cookie_data.loginflag)
                .build()
            val request = Request.Builder()
                .url("http://140.125.46.125:8000/basic_management")
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
        private fun lockBasic(operation:String,lockData:ItemBasicInfo) {
            val lock = JSONObject()
            lock.put("_id",lockData._id)
            lock.put("name",lockData.name)
            lock.put("specification",lockData.specification)
            lock.put("size",lockData.size)
            lock.put("unit_of_measurement",lockData.unit_of_measurement)
            lock.put("item_type",lockData.item_type)
            lock.put("semi_finished_product_number",lockData.semi_finished_product_number)
            lock.put("is_purchased_parts",lockData.is_purchased_parts)
            lock.put("MOQ",lockData.MOQ)
            lock.put("MOQ_time",lockData.MOQ_time)

            lock.put("batch",lockData.batch)
            lock.put("batch_time",lockData.batch_time)
            lock.put("vender_id",lockData.vender_id)
            lock.put("is_machining_parts",lockData.is_machining_parts)
            lock.put("pline_id",lockData.pline_id)
            lock.put("change_mold",lockData.change_mold)
            lock.put("mold_unit_of_timer",lockData.mold_unit_of_timer)
            lock.put("change_powder",lockData.change_powder)
            lock.put("powder_unit_of_timer",lockData.powder_unit_of_timer)
            lock.put("LT",lockData.LT)

            lock.put("LT_unit_of_timer",lockData.LT_unit_of_timer)
            lock.put("feed_in_advance_day",lockData.feed_in_advance_day)
            lock.put("feed_in_advance_time",lockData.feed_in_advance_time)
            lock.put("release_date",lockData.release_date)
            lock.put("is_schedule_adjustment_materials",lockData.is_schedule_adjustment_materials)
            lock.put("schedule_adjustment_materials_time",lockData.schedule_adjustment_materials_time)
            lock.put("is_long_delivery",lockData.is_long_delivery)
            lock.put("long_delivery_time",lockData.long_delivery_time)
            lock.put("is_low_yield",lockData.is_low_yield)
            lock.put("low_yield_time",lockData.low_yield_time)

            lock.put("is_min_manpower",lockData.is_min_manpower)
            lock.put("min_manpower_reduce_ratio",lockData.min_manpower_reduce_ratio)
            lock.put("is_standard_manpower",lockData.is_standard_manpower)
            lock.put("standard_manpower",lockData.standard_manpower)
            lock.put("unit_of_timer",lockData.unit_of_timer)
            lock.put("open_line_time",lockData.open_line_time)
            lock.put("card_number",lockData.card_number)
            lock.put("number_of_accounts",lockData.number_of_accounts)
            lock.put("settlement_date",lockData.settlement_date)
            lock.put("is_production_materials",lockData.is_production_materials)

            lock.put("remark",lockData.remark)
            //Log.d("GSON", "msg:${delete}")
            val body = FormBody.Builder()
                .add("card_number",cookie_data.card_number)
                .add("username", cookie_data.username)
                .add("operation", operation)
                .add("action","4")
                .add("data",lock.toString())
                .add("csrfmiddlewaretoken", cookie_data.tokenValue)
                .add("login_flag", cookie_data.loginflag)
                .build()
            val request = Request.Builder()
                .url("http://140.125.46.125:8000/basic_management")
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
    fun addItem(addData:ItemBasicInfo){
        data.add(itemData.count,addData)
        notifyItemInserted(itemData.count)
        itemData.count+=1
    }

}


