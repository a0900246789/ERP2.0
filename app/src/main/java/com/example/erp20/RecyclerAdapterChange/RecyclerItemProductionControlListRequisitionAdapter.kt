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


class RecyclerItemProductionControlListRequisitionAdapter() :
    RecyclerView.Adapter<RecyclerItemProductionControlListRequisitionAdapter.ViewHolder>() {
    private var itemData: ShowProductionControlListRequisition =Gson().fromJson(cookie_data.response_data, ShowProductionControlListRequisition::class.java)
    private var data: ArrayList<ProductionControlListRequisition> =itemData.data
    var relativeCombobox01=cookie_data.item_id_ComboboxData
    var relativeCombobox02=cookie_data.card_number_ComboboxData

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemProductionControlListRequisitionAdapter.ViewHolder {
        val v= LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_production_control_list_requisition,parent,false)
        return ViewHolder(v)

    }

    override fun onBindViewHolder(holder: RecyclerItemProductionControlListRequisitionAdapter.ViewHolder, position: Int) {

        //Log.d("GSON", "msg: ${itemData.data[position]}\n")
        holder.requisition_number.setText(data[position].requisition_number)
        holder.requisition_number.inputType=InputType.TYPE_NULL
        holder.prod_ctrl_order_number.setText(data[position].prod_ctrl_order_number)
        holder.prod_ctrl_order_number.inputType=InputType.TYPE_NULL
        holder.header_id.setText(data[position].header_id)
        holder.header_id.inputType=InputType.TYPE_NULL
        holder.section.setText(data[position].section)
        holder.section.inputType=InputType.TYPE_NULL
        holder.item_id.setText(data[position].item_id)
        holder.item_id.inputType=InputType.TYPE_NULL
        holder.unit_of_measurement.setText(data[position].unit_of_measurement)
        holder.unit_of_measurement.inputType=InputType.TYPE_NULL
        holder.estimated_picking_date.setText(data[position].estimated_picking_date)
        holder.estimated_picking_date.inputType=InputType.TYPE_NULL
        holder.estimated_picking_amount.setText(data[position].estimated_picking_amount.toString())
        holder.estimated_picking_amount.inputType=InputType.TYPE_NULL
        holder.is_inventory_lock.setText(data[position].is_inventory_lock.toString())
        holder.is_inventory_lock.isClickable=false
        holder.is_inventory_lock.inputType=InputType.TYPE_NULL
        holder.inventory_lock_time.setText(data[position].inventory_lock_time)
        holder.inventory_lock_time.inputType=InputType.TYPE_NULL
        holder.is_existing_stocks.setText(data[position].is_existing_stocks.toString())
        holder.is_existing_stocks.isClickable=false
        holder.is_existing_stocks.inputType=InputType.TYPE_NULL
        holder.existing_stocks_amount.setText(data[position].existing_stocks_amount.toString())
        holder.existing_stocks_amount.inputType=InputType.TYPE_NULL
        holder.existing_stocks_edit_time.setText(data[position].existing_stocks_edit_time)
        holder.existing_stocks_edit_time.inputType=InputType.TYPE_NULL
        holder.pre_delivery_date.setText(data[position].pre_delivery_date)
        holder.pre_delivery_date.inputType=InputType.TYPE_NULL
        holder.pre_delivery_amount.setText(data[position].pre_delivery_amount.toString())
        holder.pre_delivery_amount.inputType=InputType.TYPE_NULL
        holder.pre_delivery_edit_time.setText(data[position].pre_delivery_edit_time)
        holder.pre_delivery_edit_time.inputType=InputType.TYPE_NULL
        holder.is_vender_check.setText(data[position].is_vender_check.toString())
        holder.is_vender_check.isClickable=false
        holder.is_vender_check.inputType=InputType.TYPE_NULL
        holder.vender_pre_delivery_date.setText(data[position].vender_pre_delivery_date)
        holder.vender_pre_delivery_date.inputType=InputType.TYPE_NULL
        holder.vender_pre_delivery_amount.setText(data[position].vender_pre_delivery_amount.toString())
        holder.vender_pre_delivery_amount.inputType=InputType.TYPE_NULL
        holder.vender_edit_time.setText(data[position].vender_edit_time)
        holder.vender_edit_time.inputType=InputType.TYPE_NULL
        holder.procurement_approval.setText(data[position].procurement_approval)
        holder.procurement_approval.inputType=InputType.TYPE_NULL
        holder.approval_instructions.setText(data[position].approval_instructions)
        holder.approval_instructions.inputType=InputType.TYPE_NULL
        holder.is_instock.setText(data[position].is_instock.toString())
        holder.is_instock.isClickable=false
        holder.is_instock.inputType=InputType.TYPE_NULL
        holder.vender_instock_date.setText(data[position].vender_instock_date)
        holder.vender_instock_date.inputType=InputType.TYPE_NULL
        holder.is_materials_sent.setText(data[position].is_materials_sent.toString())
        holder.is_materials_sent.isClickable=false
        holder.is_materials_sent.inputType=InputType.TYPE_NULL
        holder.materials_sent_date.setText(data[position].materials_sent_date)
        holder.materials_sent_date.inputType=InputType.TYPE_NULL
        holder.materials_sent_amount.setText(data[position].materials_sent_amount.toString())
        holder.materials_sent_amount.inputType=InputType.TYPE_NULL
        holder.ng_amount.setText(data[position].ng_amount.toString())
        holder.ng_amount.inputType=InputType.TYPE_NULL
        holder.card_number.setText(data[position].card_number)
        holder.card_number.inputType=InputType.TYPE_NULL
        holder.over_received_order.setText(data[position].over_received_order.toString())
        holder.over_received_order.isClickable=false
        holder.over_received_order.inputType=InputType.TYPE_NULL
        holder.over_received_amount.setText(data[position].over_received_amount.toString())
        holder.over_received_amount.inputType=InputType.TYPE_NULL
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
        var requisition_number=itemView.findViewById<TextInputEditText>(R.id.edit_requisition_number)
        var prod_ctrl_order_number=itemView.findViewById<TextInputEditText>(R.id.edit_prod_ctrl_order_number)
        var header_id=itemView.findViewById<TextInputEditText>(R.id.edit_header_id)
        var section=itemView.findViewById<TextInputEditText>(R.id.edit_section)
        var item_id=itemView.findViewById<AutoCompleteTextView>(R.id.edit_item_id)
        var unit_of_measurement =itemView.findViewById<TextInputEditText>(R.id.edit_unit_of_measurement)
        var estimated_picking_date=itemView.findViewById<TextInputEditText>(R.id.edit_estimated_picking_date)
        var estimated_picking_amount=itemView.findViewById<TextInputEditText>(R.id.edit_estimated_picking_amount)
        var is_inventory_lock=itemView.findViewById<AutoCompleteTextView>(R.id.edit_is_inventory_lock)
        var inventory_lock_time=itemView.findViewById<TextInputEditText>(R.id.edit_inventory_lock_time)
        var is_existing_stocks=itemView.findViewById<AutoCompleteTextView>(R.id.edit_is_existing_stocks)
        var existing_stocks_amount=itemView.findViewById<TextInputEditText>(R.id.edit_existing_stocks_amount)
        var existing_stocks_edit_time=itemView.findViewById<TextInputEditText>(R.id.edit_existing_stocks_edit_time)
        var pre_delivery_date=itemView.findViewById<TextInputEditText>(R.id.edit_pre_delivery_date)
        var pre_delivery_amount=itemView.findViewById<TextInputEditText>(R.id.edit_pre_delivery_amount)
        var pre_delivery_edit_time=itemView.findViewById<TextInputEditText>(R.id.edit_pre_delivery_edit_time)
        var is_vender_check=itemView.findViewById<AutoCompleteTextView>(R.id.edit_is_vender_check)
        var vender_pre_delivery_date=itemView.findViewById<TextInputEditText>(R.id.edit_vender_pre_delivery_date)
        var vender_pre_delivery_amount=itemView.findViewById<TextInputEditText>(R.id.edit_vender_pre_delivery_amount)
        var vender_edit_time=itemView.findViewById<TextInputEditText>(R.id.edit_vender_edit_time)
        var procurement_approval=itemView.findViewById<TextInputEditText>(R.id.edit_procurement_approval)
        var approval_instructions=itemView.findViewById<TextInputEditText>(R.id.edit_approval_instructions)
        var is_instock=itemView.findViewById<AutoCompleteTextView>(R.id.edit_is_instock)
        var vender_instock_date=itemView.findViewById<TextInputEditText>(R.id.edit_vender_instock_date)
        var is_materials_sent=itemView.findViewById<AutoCompleteTextView>(R.id.edit_is_materials_sent)
        var materials_sent_date=itemView.findViewById<TextInputEditText>(R.id.edit_materials_sent_date)
        var materials_sent_amount=itemView.findViewById<TextInputEditText>(R.id.edit_materials_sent_amount)
        var ng_amount=itemView.findViewById<TextInputEditText>(R.id.edit_ng_amount)
        var card_number=itemView.findViewById<AutoCompleteTextView>(R.id.edit_card_number)
        var over_received_order=itemView.findViewById<AutoCompleteTextView>(R.id.edit_over_received_order)
        var over_received_amount=itemView.findViewById<TextInputEditText>(R.id.edit_over_received_amount)
        var is_urgent=itemView.findViewById<AutoCompleteTextView>(R.id.edit_is_urgent)
        var urgent_deadline=itemView.findViewById<TextInputEditText>(R.id.edit_urgent_deadline)


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

        var layout=itemView.findViewById<ConstraintLayout>(R.id.constraint_layout)

        lateinit var oldData:ProductionControlListRequisition
        lateinit var newData:ProductionControlListRequisition
        init {
            itemView.setOnClickListener{
                val position:Int=adapterPosition
                Log.d("GSON", "msg: ${position}\n")

            }

            val combobox=itemView.resources.getStringArray(R.array.combobox_yes_no)
            val arrayAdapter= ArrayAdapter(itemView.context,R.layout.combobox_item,combobox)
            val arrayAdapter01= ArrayAdapter(itemView.context,R.layout.combobox_item,relativeCombobox01)
            val arrayAdapter02= ArrayAdapter(itemView.context,R.layout.combobox_item,relativeCombobox02)

            //編輯按鈕
            edit_btn.setOnClickListener {
                when(edit_btn.text){
                    "編輯"->{
                        oldData=itemData.data[adapterPosition]
                        //Log.d("GSON", "msg: ${oldData.id}\n")
                        //requisition_number.inputType=InputType.TYPE_CLASS_TEXT
                        //prod_ctrl_order_number.inputType=InputType.TYPE_CLASS_TEXT
                        //header_id.inputType=InputType.TYPE_CLASS_TEXT
                        //section.inputType=InputType.TYPE_CLASS_NUMBER
                        item_id.setAdapter(arrayAdapter01)
                        unit_of_measurement.inputType=InputType.TYPE_CLASS_TEXT
                        estimated_picking_date.inputType=InputType.TYPE_DATETIME_VARIATION_DATE
                        estimated_picking_amount.inputType=InputType.TYPE_CLASS_NUMBER
                        is_inventory_lock.setAdapter(arrayAdapter)
                        is_inventory_lock.isClickable=true
                        inventory_lock_time.inputType=InputType.TYPE_CLASS_DATETIME
                        is_existing_stocks.setAdapter(arrayAdapter)
                        is_existing_stocks.isClickable=true
                        existing_stocks_amount.inputType=InputType.TYPE_CLASS_NUMBER
                        existing_stocks_edit_time.inputType=InputType.TYPE_CLASS_DATETIME
                        pre_delivery_date.inputType=InputType.TYPE_DATETIME_VARIATION_DATE
                        pre_delivery_amount.inputType=InputType.TYPE_CLASS_NUMBER
                        pre_delivery_edit_time.inputType=InputType.TYPE_CLASS_DATETIME
                        is_vender_check.setAdapter(arrayAdapter)
                        is_vender_check.isClickable=true
                        vender_pre_delivery_date.inputType=InputType.TYPE_DATETIME_VARIATION_DATE
                        vender_pre_delivery_amount.inputType=InputType.TYPE_CLASS_NUMBER
                        vender_edit_time.inputType=InputType.TYPE_CLASS_DATETIME
                        procurement_approval.inputType=InputType.TYPE_CLASS_TEXT
                        approval_instructions.inputType=InputType.TYPE_CLASS_TEXT
                        is_instock.setAdapter(arrayAdapter)
                        is_instock.isClickable=true
                        vender_instock_date.inputType=InputType.TYPE_CLASS_DATETIME
                        is_materials_sent.setAdapter(arrayAdapter)
                        is_materials_sent.isClickable=true
                        materials_sent_date.inputType=InputType.TYPE_CLASS_DATETIME
                        materials_sent_amount.inputType=InputType.TYPE_CLASS_NUMBER
                        ng_amount.inputType=InputType.TYPE_CLASS_NUMBER
                        card_number.setAdapter(arrayAdapter02)
                        over_received_order.setAdapter(arrayAdapter)
                        over_received_order.isClickable=true
                        over_received_amount.inputType=InputType.TYPE_CLASS_NUMBER
                        is_urgent.setAdapter(arrayAdapter)
                        is_urgent.isClickable=true
                        urgent_deadline.inputType=InputType.TYPE_CLASS_DATETIME



                        remark.isClickable=true
                        remark.isFocusable=true
                        remark.isFocusableInTouchMode=true
                        remark.isTextInputLayoutFocusedRectEnabled=true
                        item_id.requestFocus()
                        edit_btn.text = "完成"
                        layout.setBackgroundColor(Color.parseColor("#6E5454"))
                        edit_btn.setBackgroundColor(Color.parseColor("#FF3700B3"))
                    }
                    "完成"->{
                        newData= ProductionControlListRequisition()
                        newData.requisition_number=requisition_number.text.toString()
                        requisition_number.inputType=InputType.TYPE_NULL
                        newData.prod_ctrl_order_number=prod_ctrl_order_number.text.toString()
                        prod_ctrl_order_number.inputType=InputType.TYPE_NULL
                        newData.header_id=header_id.text.toString()
                        header_id.inputType=InputType.TYPE_NULL
                        newData.section=section.text.toString()
                        section.inputType=InputType.TYPE_NULL
                        newData.item_id=item_id.text.toString()
                        item_id.setAdapter(null)
                        newData.unit_of_measurement=unit_of_measurement.text.toString()
                        unit_of_measurement.inputType=InputType.TYPE_NULL
                        if(estimated_picking_date.text.toString()==""){
                            newData.estimated_picking_date=null
                        }
                        else{
                            newData.estimated_picking_date=estimated_picking_date.text.toString()
                        }
                        estimated_picking_date.inputType=InputType.TYPE_NULL
                        newData.estimated_picking_amount=estimated_picking_amount.text.toString().toInt()
                        estimated_picking_amount.inputType=InputType.TYPE_NULL
                        newData.is_inventory_lock=is_inventory_lock.text.toString().toBoolean()
                        is_inventory_lock.isClickable=false
                        is_inventory_lock.setAdapter(null)
                        if(inventory_lock_time.text.toString()==""){
                            newData.inventory_lock_time=null
                        }
                        else{
                            newData.inventory_lock_time=inventory_lock_time.text.toString()
                        }
                        inventory_lock_time.inputType=InputType.TYPE_NULL

                        newData.is_existing_stocks=is_existing_stocks.text.toString().toBoolean()
                        is_existing_stocks.isClickable=false
                        is_existing_stocks.setAdapter(null)
                        newData.existing_stocks_amount=existing_stocks_amount.text.toString().toInt()
                        existing_stocks_amount.inputType=InputType.TYPE_NULL
                        if(existing_stocks_edit_time.text.toString()==""){
                            newData.existing_stocks_edit_time=null
                        }
                        else{
                            newData.existing_stocks_edit_time=existing_stocks_edit_time.text.toString()
                        }
                        existing_stocks_edit_time.inputType=InputType.TYPE_NULL
                        if(pre_delivery_date.text.toString()==""){
                            newData.pre_delivery_date=null
                        }
                        else{
                            newData.pre_delivery_date=pre_delivery_date.text.toString()
                        }
                        pre_delivery_date.inputType=InputType.TYPE_NULL
                        newData.pre_delivery_amount=pre_delivery_amount.text.toString().toInt()
                        pre_delivery_amount.inputType=InputType.TYPE_NULL
                        if(pre_delivery_edit_time.text.toString()==""){
                            newData.pre_delivery_edit_time=null
                        }
                        else{
                            newData.pre_delivery_edit_time=pre_delivery_edit_time.text.toString()
                        }
                        pre_delivery_edit_time.inputType=InputType.TYPE_NULL
                        newData.is_vender_check=is_vender_check.text.toString().toBoolean()
                        is_vender_check.isClickable=false
                        is_vender_check.setAdapter(null)
                        if(vender_pre_delivery_date.text.toString()==""){
                            newData.vender_pre_delivery_date=null
                        }
                        else{
                            newData.vender_pre_delivery_date=vender_pre_delivery_date.text.toString()
                        }
                        vender_pre_delivery_date.inputType=InputType.TYPE_NULL
                        newData.vender_pre_delivery_amount=vender_pre_delivery_amount.text.toString().toInt()
                        vender_pre_delivery_amount.inputType=InputType.TYPE_NULL
                        if(vender_edit_time.text.toString()==""){
                            newData.vender_edit_time=null
                        }
                        else{
                            newData.vender_edit_time=vender_edit_time.text.toString()
                        }
                        vender_edit_time.inputType=InputType.TYPE_NULL
                        newData.procurement_approval=procurement_approval.text.toString()
                        procurement_approval.inputType=InputType.TYPE_NULL
                        newData.approval_instructions=approval_instructions.text.toString()
                        approval_instructions.inputType=InputType.TYPE_NULL
                        newData.is_instock=is_instock.text.toString().toBoolean()
                        is_instock.isClickable=false
                        is_instock.setAdapter(null)
                        if(vender_instock_date.text.toString()==""){
                            newData.vender_instock_date=null
                        }
                        else{
                            newData.vender_instock_date=vender_instock_date.text.toString()
                        }
                        vender_instock_date.inputType=InputType.TYPE_NULL
                        newData.is_materials_sent=is_materials_sent.text.toString().toBoolean()
                        is_materials_sent.isClickable=false
                        is_materials_sent.setAdapter(null)
                        if(materials_sent_date.text.toString()==""){
                            newData.materials_sent_date=null
                        }
                        else{
                            newData.materials_sent_date=materials_sent_date.text.toString()
                        }
                        materials_sent_date.inputType=InputType.TYPE_NULL
                        newData.materials_sent_amount=materials_sent_amount.text.toString().toInt()
                        materials_sent_amount.inputType=InputType.TYPE_NULL
                        newData.ng_amount=ng_amount.text.toString().toInt()
                        ng_amount.inputType=InputType.TYPE_NULL
                        newData.card_number=card_number.text.toString()
                        card_number.setAdapter(null)
                        newData.over_received_order=over_received_order.text.toString().toBoolean()
                        over_received_order.isClickable=false
                        over_received_order.setAdapter(null)
                        newData.over_received_amount=over_received_amount.text.toString().toInt()
                        over_received_amount.inputType=InputType.TYPE_NULL
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
                        edit_ProductControlOrder("ProductionControlListRequisition",oldData,newData)//更改資料庫資料
                        when(cookie_data.status){
                            0-> {//成功
                                itemData.data[adapterPosition] = newData//更改渲染資料
                                editor.setText(cookie_data.username)
                                Toast.makeText(itemView.context, cookie_data.msg, Toast.LENGTH_SHORT).show()
                            }
                            1->{//失敗
                                Toast.makeText(itemView.context, cookie_data.msg, Toast.LENGTH_SHORT).show()
                                requisition_number.setText(oldData.requisition_number)
                                prod_ctrl_order_number.setText(oldData.prod_ctrl_order_number)
                                header_id.setText(oldData.header_id)
                                section.setText(oldData.section)
                                item_id.setText(oldData.item_id)
                                unit_of_measurement.setText(oldData.unit_of_measurement)
                                estimated_picking_date.setText(oldData.estimated_picking_date)
                                estimated_picking_amount.setText(oldData.estimated_picking_amount.toString())
                                is_inventory_lock.setText(oldData.is_inventory_lock.toString())
                                inventory_lock_time.setText(oldData.inventory_lock_time)
                                is_existing_stocks.setText(oldData.is_existing_stocks.toString())
                                existing_stocks_amount.setText(oldData.existing_stocks_amount.toString())
                                existing_stocks_edit_time.setText(oldData.existing_stocks_edit_time)
                                pre_delivery_date.setText(oldData.pre_delivery_date)
                                pre_delivery_amount.setText(oldData.pre_delivery_amount.toString())
                                pre_delivery_edit_time.setText(oldData.pre_delivery_edit_time)
                                is_vender_check.setText(oldData.is_vender_check.toString())
                                vender_pre_delivery_date.setText(oldData.vender_pre_delivery_date)
                                vender_pre_delivery_amount.setText(oldData.vender_pre_delivery_amount.toString())
                                vender_edit_time.setText(oldData.vender_edit_time)
                                procurement_approval.setText(oldData.procurement_approval)
                                approval_instructions.setText(oldData.approval_instructions)
                                is_instock.setText(oldData.is_instock.toString())
                                vender_instock_date.setText(oldData.vender_instock_date)
                                is_materials_sent.setText(oldData.is_materials_sent.toString())
                                materials_sent_date.setText(oldData.materials_sent_date)
                                materials_sent_amount.setText(oldData.materials_sent_amount.toString())
                                ng_amount.setText(oldData.ng_amount.toString())
                                card_number.setText(oldData.card_number)
                                over_received_order.setText(oldData.over_received_order.toString())
                                over_received_amount.setText(oldData.over_received_amount.toString())
                                is_urgent.setText(oldData.is_urgent.toString())
                                urgent_deadline.setText(oldData.urgent_deadline)

                                remark.setText(oldData.remark)
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
                    delete_ProductControlOrder("ProductionControlListRequisition",data[adapterPosition])
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
                    lock_ProductControlOrder("ProductionControlListRequisition",data[adapterPosition])
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
                    over_ProductControlOrder("ProductionControlListRequisition",data[adapterPosition])
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
        private fun edit_ProductControlOrder(operation:String,oldData:ProductionControlListRequisition,newData:ProductionControlListRequisition) {
            val old =JSONObject()
            old.put("prod_ctrl_order_number",oldData.prod_ctrl_order_number)
            old.put("requisition_number",oldData.requisition_number)
            old.put("header_id",oldData.header_id)
            old.put("section",oldData.section)
            old.put("item_id",oldData.item_id)
            old.put("unit_of_measurement",oldData.unit_of_measurement)
            old.put("estimated_picking_date",oldData.estimated_picking_date)
            old.put("estimated_picking_amount",oldData.estimated_picking_amount)
            old.put("is_inventory_lock",oldData.is_inventory_lock)
            old.put("inventory_lock_time",oldData.inventory_lock_time)
            old.put("is_existing_stocks",oldData.is_existing_stocks)
            old.put("existing_stocks_amount",oldData.existing_stocks_amount)
            old.put("existing_stocks_edit_time",oldData.existing_stocks_edit_time)
            old.put("pre_delivery_date",oldData.pre_delivery_date)
            old.put("pre_delivery_amount",oldData.pre_delivery_amount)
            old.put("pre_delivery_edit_time",oldData.pre_delivery_edit_time)
            old.put("is_vender_check",oldData.is_vender_check)
            old.put("vender_pre_delivery_date",oldData.vender_pre_delivery_date)
            old.put("vender_pre_delivery_amount",oldData.vender_pre_delivery_amount)
            old.put("vender_edit_time",oldData.vender_edit_time)
            old.put("procurement_approval",oldData.procurement_approval)
            old.put("approval_instructions",oldData.approval_instructions)
            old.put("is_instock",oldData.is_instock)
            old.put("vender_instock_date",oldData.vender_instock_date)
            old.put("is_materials_sent",oldData.is_materials_sent)
            old.put("materials_sent_date",oldData.materials_sent_date)
            old.put("materials_sent_amount",oldData.materials_sent_amount)
            old.put("ng_amount",oldData.ng_amount)
            old.put("card_number",oldData.card_number)
            old.put("over_received_order",oldData.over_received_order)
            old.put("over_received_amount",oldData.over_received_amount)
            old.put("is_urgent",oldData.is_urgent)
            old.put("urgent_deadline",oldData.urgent_deadline)



            old.put("remark",oldData.remark)
            val new =JSONObject()
            new.put("prod_ctrl_order_number",newData.prod_ctrl_order_number)
            new.put("requisition_number",newData.requisition_number)
            new.put("header_id",newData.header_id)
            new.put("section",newData.section)
            new.put("item_id",newData.item_id)
            new.put("unit_of_measurement",newData.unit_of_measurement)
            new.put("estimated_picking_date",newData.estimated_picking_date)
            new.put("estimated_picking_amount",newData.estimated_picking_amount)
            new.put("is_inventory_lock",newData.is_inventory_lock)
            new.put("inventory_lock_time",newData.inventory_lock_time)
            new.put("is_existing_stocks",newData.is_existing_stocks)
            new.put("existing_stocks_amount",newData.existing_stocks_amount)
            new.put("existing_stocks_edit_time",newData.existing_stocks_edit_time)
            new.put("pre_delivery_date",newData.pre_delivery_date)
            new.put("pre_delivery_amount",newData.pre_delivery_amount)
            new.put("pre_delivery_edit_time",newData.pre_delivery_edit_time)
            new.put("is_vender_check",newData.is_vender_check)
            new.put("vender_pre_delivery_date",newData.vender_pre_delivery_date)
            new.put("vender_pre_delivery_amount",newData.vender_pre_delivery_amount)
            new.put("vender_edit_time",newData.vender_edit_time)
            new.put("procurement_approval",newData.procurement_approval)
            new.put("approval_instructions",newData.approval_instructions)
            new.put("is_instock",newData.is_instock)
            new.put("vender_instock_date",newData.vender_instock_date)
            new.put("is_materials_sent",newData.is_materials_sent)
            new.put("materials_sent_date",newData.materials_sent_date)
            new.put("materials_sent_amount",newData.materials_sent_amount)
            new.put("ng_amount",newData.ng_amount)
            new.put("card_number",newData.card_number)
            new.put("over_received_order",newData.over_received_order)
            new.put("over_received_amount",newData.over_received_amount)
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
        private fun delete_ProductControlOrder(operation:String,deleteData:ProductionControlListRequisition) {
            val delete = JSONObject()
            delete.put("prod_ctrl_order_number",deleteData.prod_ctrl_order_number)
            delete.put("requisition_number",deleteData.requisition_number)
            delete.put("header_id",deleteData.header_id)
            delete.put("section",deleteData.section)
            delete.put("item_id",deleteData.item_id)
            delete.put("unit_of_measurement",deleteData.unit_of_measurement)
            delete.put("estimated_picking_date",deleteData.estimated_picking_date)
            delete.put("estimated_picking_amount",deleteData.estimated_picking_amount)
            delete.put("is_inventory_lock",deleteData.is_inventory_lock)
            delete.put("inventory_lock_time",deleteData.inventory_lock_time)
            delete.put("is_existing_stocks",deleteData.is_existing_stocks)
            delete.put("existing_stocks_amount",deleteData.existing_stocks_amount)
            delete.put("existing_stocks_edit_time",deleteData.existing_stocks_edit_time)
            delete.put("pre_delivery_date",deleteData.pre_delivery_date)
            delete.put("pre_delivery_amount",deleteData.pre_delivery_amount)
            delete.put("pre_delivery_edit_time",deleteData.pre_delivery_edit_time)
            delete.put("is_vender_check",deleteData.is_vender_check)
            delete.put("vender_pre_delivery_date",deleteData.vender_pre_delivery_date)
            delete.put("vender_pre_delivery_amount",deleteData.vender_pre_delivery_amount)
            delete.put("vender_edit_time",deleteData.vender_edit_time)
            delete.put("procurement_approval",deleteData.procurement_approval)
            delete.put("approval_instructions",deleteData.approval_instructions)
            delete.put("is_instock",deleteData.is_instock)
            delete.put("vender_instock_date",deleteData.vender_instock_date)
            delete.put("is_materials_sent",deleteData.is_materials_sent)
            delete.put("materials_sent_date",deleteData.materials_sent_date)
            delete.put("materials_sent_amount",deleteData.materials_sent_amount)
            delete.put("ng_amount",deleteData.ng_amount)
            delete.put("card_number",deleteData.card_number)
            delete.put("over_received_order",deleteData.over_received_order)
            delete.put("over_received_amount",deleteData.over_received_amount)
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
        private fun lock_ProductControlOrder(operation:String,lockData:ProductionControlListRequisition) {
            val lock = JSONObject()
            lock.put("prod_ctrl_order_number",lockData.prod_ctrl_order_number)
            lock.put("requisition_number",lockData.requisition_number)
            lock.put("header_id",lockData.header_id)
            lock.put("section",lockData.section)
            lock.put("item_id",lockData.item_id)
            lock.put("unit_of_measurement",lockData.unit_of_measurement)
            lock.put("estimated_picking_date",lockData.estimated_picking_date)
            lock.put("estimated_picking_amount",lockData.estimated_picking_amount)
            lock.put("is_inventory_lock",lockData.is_inventory_lock)
            lock.put("inventory_lock_time",lockData.inventory_lock_time)
            lock.put("is_existing_stocks",lockData.is_existing_stocks)
            lock.put("existing_stocks_amount",lockData.existing_stocks_amount)
            lock.put("existing_stocks_edit_time",lockData.existing_stocks_edit_time)
            lock.put("pre_delivery_date",lockData.pre_delivery_date)
            lock.put("pre_delivery_amount",lockData.pre_delivery_amount)
            lock.put("pre_delivery_edit_time",lockData.pre_delivery_edit_time)
            lock.put("is_vender_check",lockData.is_vender_check)
            lock.put("vender_pre_delivery_date",lockData.vender_pre_delivery_date)
            lock.put("vender_pre_delivery_amount",lockData.vender_pre_delivery_amount)
            lock.put("vender_edit_time",lockData.vender_edit_time)
            lock.put("procurement_approval",lockData.procurement_approval)
            lock.put("approval_instructions",lockData.approval_instructions)
            lock.put("is_instock",lockData.is_instock)
            lock.put("vender_instock_date",lockData.vender_instock_date)
            lock.put("is_materials_sent",lockData.is_materials_sent)
            lock.put("materials_sent_date",lockData.materials_sent_date)
            lock.put("materials_sent_amount",lockData.materials_sent_amount)
            lock.put("ng_amount",lockData.ng_amount)
            lock.put("card_number",lockData.card_number)
            lock.put("over_received_order",lockData.over_received_order)
            lock.put("over_received_amount",lockData.over_received_amount)
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
        private fun over_ProductControlOrder(operation:String,overData:ProductionControlListRequisition) {
            val over = JSONObject()
            over.put("prod_ctrl_order_number",overData.prod_ctrl_order_number)
            over.put("requisition_number",overData.requisition_number)
            over.put("header_id",overData.header_id)
            over.put("section",overData.section)
            over.put("item_id",overData.item_id)
            over.put("unit_of_measurement",overData.unit_of_measurement)
            over.put("estimated_picking_date",overData.estimated_picking_date)
            over.put("estimated_picking_amount",overData.estimated_picking_amount)
            over.put("is_inventory_lock",overData.is_inventory_lock)
            over.put("inventory_lock_time",overData.inventory_lock_time)
            over.put("is_existing_stocks",overData.is_existing_stocks)
            over.put("existing_stocks_amount",overData.existing_stocks_amount)
            over.put("existing_stocks_edit_time",overData.existing_stocks_edit_time)
            over.put("pre_delivery_date",overData.pre_delivery_date)
            over.put("pre_delivery_amount",overData.pre_delivery_amount)
            over.put("pre_delivery_edit_time",overData.pre_delivery_edit_time)
            over.put("is_vender_check",overData.is_vender_check)
            over.put("vender_pre_delivery_date",overData.vender_pre_delivery_date)
            over.put("vender_pre_delivery_amount",overData.vender_pre_delivery_amount)
            over.put("vender_edit_time",overData.vender_edit_time)
            over.put("procurement_approval",overData.procurement_approval)
            over.put("approval_instructions",overData.approval_instructions)
            over.put("is_instock",overData.is_instock)
            over.put("vender_instock_date",overData.vender_instock_date)
            over.put("is_materials_sent",overData.is_materials_sent)
            over.put("materials_sent_date",overData.materials_sent_date)
            over.put("materials_sent_amount",overData.materials_sent_amount)
            over.put("ng_amount",overData.ng_amount)
            over.put("card_number",overData.card_number)
            over.put("over_received_order",overData.over_received_order)
            over.put("over_received_amount",overData.over_received_amount)
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

    }
    fun addItem(addData:ProductionControlListRequisition){
        data.add(itemData.count,addData)
        notifyItemInserted(itemData.count)
        itemData.count+=1//cookie_data.itemCount+=1
    }

}


