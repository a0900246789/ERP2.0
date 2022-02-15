package com.example.erp20.Model
import android.media.VolumeShaper
import android.text.Editable
import com.google.gson.annotations.SerializedName
import org.jetbrains.annotations.NotNull
import java.text.DateFormat

open class Base{
    var id:Int=0
    var remark:String= ""
    var creator:String= ""
    var editor:String = ""
    var Lock:Boolean=false
    var lock_time:String=""
    var invalid:Boolean=false
    var invalid_time:String=""
    var create_time:String=""
    var edit_time:String=""

//CustomerOrder
    var is_closed:Boolean=false
    var close_time:String=""
}

data class login (
    val status: Int,
    val msg: String,
    val login_flag: String,
    val card_number:String,
    val dept:String,
)

data class Response(
    val status: Int,
    val msg: String
)

data class ShowSysArgPur(
    val data: ArrayList<SysArgPur>,
    var count: Int,
    val status: Int
)
data class SysArgPur(
    var system_code: String="",
    var limited_days: Int=0,
):Base()


data class ShowProdType(
    val data: ArrayList<ProdType>,
    var count: Int,
    val status: Int
)
data class ProdType(
    var product_type_name: String="",
    var product_type_id: String="",
):Base()

data class ShowContNo(
    val data: ArrayList<ContNo>,
    var count: Int,
    val status: Int
)
data class ContNo(
    var cont_code: String="",
    var customer_code: String="",
    var age: String="",
    var serial_number: Int=0,
    var cont_code_name: String="",
):Base()

data class ShowContType(
    val data: ArrayList<ContType>,
    var count: Int,
    val status: Int
)
data class ContType(
    var cont_type_code: String="",
    var cont_type: String="",
    var order: String="",
):Base()


data class ShowPort(
    val data: ArrayList<Port>,
    var count: Int,
    val status: Int
)
data class Port(
    var port_id: String="",
    var port_name: String="",
):Base()

data class ShowStoreArea(
    val data: ArrayList<StoreArea>,
    var count: Int,
    val status: Int
)
data class StoreArea(
    var store_area: String="",
    var store_area_name: String="",
    var number_of_standard_measurement: Int=0,
):Base()

data class ShowStoreLocal(
    val data: ArrayList<StoreLocal>,
    var count: Int,
    val status: Int
)
data class StoreLocal(
    var store_local: String="",
):Base()

data class ShowDepartment(
    val data: ArrayList<Department>,
    var count: Int,
    val status: Int
)
data class Department(
    var dept_name: String="",
    var dept_code: String="",
    var is_factory_manager:Boolean=false,//廠務
    var is_wareehouse:Boolean=false, //倉儲
    var is_pp:Boolean=false,         //生計
    var is_production:Boolean=false, //生產
    var is_procurement:Boolean=false,//採購
    var is_pm:Boolean=false,         //生管
    var is_qc:Boolean=false,         //品管
    var is_qa:Boolean=false,         //品保
    var is_rd:Boolean=false,         //開發/研發
    var is_business:Boolean=false,   //營業
    var is_shipping:Boolean=false,   //船務
    var is_fa:Boolean=false,         //管理
    var is_finance:Boolean=false,    //財會
    var is_hr:Boolean=false,         //人資
    var is_ga:Boolean=false,         //總務
):Base()

data class ShowInvChangeTypeM(
    val data: ArrayList<InvChangeTypeM>,
    var count: Int,
    val status: Int
)
data class InvChangeTypeM(
    var inv_code_m: String="",
    var inv_name_m: String="",
):Base()

data class ShowInvChangeTypeS(
    val data: ArrayList<InvChangeTypeS>,
    var count: Int,
    val status: Int
)
data class InvChangeTypeS(
    var inv_code_s: String="",
    var inv_code_m: String="",
    var inv_name_s: String="",
    var is_inventory_plus: Boolean=false,
    var is_inventory_reduce: Boolean=false,
    var is_not_affect: Boolean=false,
    var is_ok_product_warehouse: Boolean=false,
    var is_ng_product_warehouse: Boolean=false,
    var is_scrapped: Boolean=false,
    var auto_push_code: String="",
):Base()

data class ShowEquipmentMaintenanceType(
    val data: ArrayList<EquipmentMaintenanceType>,
    var count: Int,
    val status: Int
)
data class EquipmentMaintenanceType(
    var maintain_type: String="",
):Base()

data class ShowProductBasicInfo(
    val data: ArrayList<ProductBasicInfo>,
    var count: Int,
    val status: Int
)
data class ProductBasicInfo(
    var _id: String="",
    var product_name: String="",
    var product_type: String="",
    var is_new : Boolean=false,
    var release_date : String="",
    var is_discontinue : Boolean=false,
    var discontinued_date :String="",
    var is_inventory : Boolean=false,
    var inventory_date  :String="",
):Base()

data class ShowCustomBasicInfo(
    val data: ArrayList<CustomBasicInfo>,
    var count: Int,
    val status: Int
)
data class CustomBasicInfo(
    var _id: String="",
    var abbreviation: String="",
    var full_name: String="",
    var code : String="",
    var booking_prefix: String="",
    var SWE_interval_days: Int=0,
    var act_shipping_interval_days: Int=0,
    var arrival_date_interval_days : Int=0,
):Base()

data class ShowCarCBasicInfo(
    val data: ArrayList<CarCBasicInfo>,
    var count: Int,
    val status: Int
)
data class CarCBasicInfo(
    var _id: String,
    var abbreviation: String,
):Base()

data class ShowShippingCompanyBasicInfo(
    val data: ArrayList<ShippingCompanyBasicInfo>,
    var count: Int,
    val status: Int
)
data class ShippingCompanyBasicInfo(
    var shipping_number: String="",
    var shipping_name: String="",
):Base()

data class ShowItemBasicInfo(
    val data: ArrayList<ItemBasicInfo>,
    var count: Int,
    val status: Int
)
data class ItemBasicInfo(
    var _id: String="",
    var name: String="",
    var specification: String="",
    var size: String="",
    var unit_of_measurement: String="",
    var item_type: String="",
    var semi_finished_product_number: String="",
    var is_purchased_parts: Boolean=false,
    var MOQ: Double =0.0,
    var MOQ_time: String="",
    var batch: Double =0.0,
    var batch_time: String="",
    var vender_id: String="",
    var is_machining_parts: Boolean=false,
    var pline_id: String="",
    var change_mold: Double =0.0,
    var mold_unit_of_timer: String="",
    var change_powder: Double =0.0,
    var powder_unit_of_timer: String="",
    var LT: String="",
    var LT_unit_of_timer: String="",
    var feed_in_advance_day: Int=0,
    var feed_in_advance_time: String="",
    var release_date: String="",
    var is_schedule_adjustment_materials: Boolean=false,
    var schedule_adjustment_materials_time: String="",
    var is_long_delivery: Boolean=false,
    var long_delivery_time: String="",
    var is_low_yield: Boolean=false,
    var low_yield_time: String="",
    var is_min_manpower: Boolean=false,
    var min_manpower_reduce_ratio: Double =0.0,
    var is_standard_manpower: Boolean=false,
    var standard_manpower: Int=0,
    var unit_of_timer: String="",
    var open_line_time: String="",
    var card_number: String="",
    var number_of_accounts: Double =0.0,
    var settlement_date: String="",
    var is_production_materials: Boolean=false,
    var exemption_time: String?=null,
    var is_exemption : Boolean=false,

):Base()

data class ShowPLineBasicInfo(
    val data: ArrayList<PLineBasicInfo>,
    var count: Int,
    val status: Int
)
data class PLineBasicInfo(
    var _id: String="",
    var name: String="",
    var is_selfmade: Boolean=false,
    var is_outsourcing : Boolean=false,
    var vender_id : String="",
):Base()

data class ShowProductionEquipmentBasicInfo(
    val data: ArrayList<ProductionEquipmentBasicInfo>,
    var count: Int,
    val status: Int
)
data class ProductionEquipmentBasicInfo(
    var production_equipment_number: String="",
    var full_name: String="",
    var abbreviation: String="",
    var pline_id: String="",
    var asset_number: String="",
    var equipment_type: String="",
    var is_production_equipment: Boolean=false,
    var is_mould: Boolean=false,
    var is_fixture: Boolean=false,
    var is_checking_fixture : Boolean=false,
):Base()

data class ShowMeWorkstationBasicInfo(
    val data: ArrayList<MeWorkstationBasicInfo>,
    var count: Int,
    val status: Int
)
data class MeWorkstationBasicInfo(
    var workstation_number: String="",
    var product_id: String="",
    var workstation_code: String="",
    var is_body_condition_ok: Boolean=true,
    var is_vision_condition_ok: Boolean=true,
    var is_technology_ok: Boolean=true,
    var is_normal : Boolean=true,
    var job_description: String="",
    var standard_working_hours_optimal: Int=8,
    var unit_of_timer_optimal: String="",
    var number_of_staff_optimal: String="",
    var theoretical_output: Int=1,
    var standard_working_hours_less: Int=8,
    var unit_of_timer_less: String="",
    var number_of_staff_less: String="",
    var theoretical_output_less: Int=1,
):Base()

data class ShowMeWorkstationEquipmentBasicInfo(
    val data: ArrayList<MeWorkstationEquipmentBasicInfo>,
    var count: Int,
    val status: Int
)
data class MeWorkstationEquipmentBasicInfo(
    var equipment_number: String="",
    var workstation_number: String="",
    var device_id: String="",

):Base()

data class ShowMeHeader(
    val data: ArrayList<MeHeader>,
    var count: Int,
    val status: Int
)
data class MeHeader(
    var _id: String="",
    var product_id: String="",
    var theoretical_output: Int=1,
    var unit_of_timer: String="",
    var work_option: String="",
    var issue_date: String="",
    var item_id: String="",
):Base()

data class ShowMeBody(
    val data: ArrayList<MeBody>,
    var count: Int,
    val status: Int
)
data class MeBody(
    var _id: String="",
    var process_number: String="",
    var processing_sequence: String="",
    var process_code: String="",
    var work_option: String="",
    var item_id: String="",
    var pline_id: String="",
    var standard_working_hours_optimal: Int=8,
    var unit_of_timer_optimal: String="",
    var number_of_staff_optimal: String="",
    var theoretical_output_optimal: Int=1,
    var standard_working_hours_less: Int=8,
    var unit_of_timer_less: String="",
    var number_of_staff_less: String="",
    var theoretical_output_less: Int=1,
):Base()

data class ShowstaffBasicInfo(
    val data: ArrayList<staffBasicInfo>,
    var count: Int,
    val status: Int
)
data class staffBasicInfo(
    var card_number: String="",
    var password: String="",
    var name: String="",
    var dept: String="",
    var position: String="",
    var is_work: Boolean=true,
    var date_of_employment: String="",
    var date_of_resignation: String="",
    var skill_code: String="",
    var skill_rank: String="",
    var qr_code: String="",
):Base()

data class ShowVenderBasicInfo(
    val data: ArrayList<VenderBasicInfo>,
    var count: Int,
    val status: Int
)
data class VenderBasicInfo(
    var _id: String="",
    var abbreviation: String="",
    var full_name: String="",
    var card_number: String="",
    var is_supplier: Boolean=false,
    var is_processor: Boolean=false,
    var is_other: Boolean=false,
    var rank: String="",
    var evaluation_date: String="",
    var days_before_delivery: Int=0,
    var days_before_delivery_time: String="",
):Base()

data class ShowItemOfManufacturerCapacity(
    val data: ArrayList<ItemOfManufacturerCapacity>,
    var count: Int,
    val status: Int
)
data class ItemOfManufacturerCapacity(
    var item_id: String="",
    var vender_id: String="",
    var container_id: String="",
    var unit_of_timer: String="",
    var dmcil_id: String="",
):Base()

data class ShowContainerBasicInfo(
    val data: ArrayList<ContainerBasicInfo>,
    var count: Int,
    val status: Int
)
data class ContainerBasicInfo(
    var container_id: String="",
    var container_name: String="",
    var spec: String="",
    var dmcil_id: String="",
):Base()

data class ShowCustomerOrderHeader(
    val data: ArrayList<CustomerOrderHeader>,
    var count: Int,
    val status: Int
)
data class CustomerOrderHeader(
    var poNo: String="",
    var order_date: String="",
    var customer_id: String="",
    var cont_count: Int=0,
    var start_cont_id: String="",
    var end_cont_id: String="",
    var sws:String="",
    var swe:String="",
    var is_urgent: Boolean=false,
    var urgent_deadline: String?=null,
):Base()

data class ShowCustomerOrderBody(
    val data: ArrayList<CustomerOrderBody>,
    var count: Int,
    val status: Int
)
data class CustomerOrderBody(
    var poNo: String="",
    var product_id: String="",
    var quantity_of_order: Int=0,
    var quantity_delivered: Int=0,
    var unit_of_measurement: String="",
    var body_id: String="",
    var section: String="",
):Base()

data class ShowCustomerForecastListHeader(
    val data: ArrayList<CustomerForecastListHeader>,
    var count: Int,
    val status: Int
)
data class CustomerForecastListHeader(
    var _id: String="",
    var date: String?=null,
    var custom_id: String="",
    var forecast_basis: String="",
):Base()

data class ShowCustomerForecastListBody(
    val data: ArrayList<CustomerForecastListBody>,
    var count: Int,
    val status: Int
)
data class CustomerForecastListBody(
    var header_id: String="",
    var section: String="",
    var age_mounth: String="",
    var product_id: String="",
    var product_type_id: String="",
    var count: Int=0,
    var unit_of_measurement: String="",
):Base()

data class ShowMasterScheduledOrderHeader(
    val data: ArrayList<MasterScheduledOrderHeader>,
    var count: Int,
    val status: Int
)
data class MasterScheduledOrderHeader(
    var _id: String="",
    var product_type_id: String="",
    var est_output:  Int=0,
    var customer_poNo: String="",
):Base()

data class ShowMasterScheduledOrderBody(
    val data: ArrayList<MasterScheduledOrderBody>,
    var count: Int,
    val status: Int
)
data class MasterScheduledOrderBody(
    var code: String="",
    var header_id: String="",
    var section: String="",
    var product_type_id: String="",
    var pre_delivery_date: String?=null,
    var est_output: Int=0,
    var customer_poNo: String="",
):Base()


data class ShowStackingControlListHeader(
    val data: ArrayList<StackingControlListHeader>,
    var count: Int,
    val status: Int
)
data class StackingControlListHeader(
    var code: String="",
    var contNo: String="",
    var work_year: String="",
    var customer_poNo: String="",
    var cont_type_code: String="",
    var sws: String?=null,
    var swe: String?=null,
    var shipping_order_No: String="",
    var last_swe: String?=null,
    var sailing_date: String?=null,
    var port_id: String="",
    var car_id: String="",
    var date_provided: String?=null,
    var est_start_stacking_date: String?=null,
    var est_stop_stacking_date: String?=null,
    var est_work_hours: Double=0.0,
    var unit_of_timer: String="",
    var worker: Int=0,
    var start_stacking_date: String?=null,
    var stop_stacking_date: String?=null,
    var is_urgent: Boolean=false,
    var urgent_deadline: String?=null,
    var is_finished: Boolean=false,
    var finished_date: String?=null,
):Base()

data class ShowStackingControlListBody(
    val data: ArrayList<StackingControlListBody>,
    var count: Int,
    val status: Int
)
data class StackingControlListBody(
    var code: String="",
    var header_id: String="",
    var product_id: String="",
    var master_order_number: String="",
    var count: Int=0,
    var store_area: String="",
    var store_local: String=""
):Base()

data class ShowBookingNoticeHeader(
    val data: ArrayList<BookingNoticeHeader>,
    var count: Int,
    val status: Int
)
data class BookingNoticeHeader(
    var _id: String="",
    var shipping_number: String="",
    var shipping_order_number: String="",
    var date: String?=null,
    var customer_poNo: String="",
    var notice_number: String="",
    var act_clearance_date: String?=null,
    var act_shipping_date: String?=null,
    var oa_referenceNO1: String="",
    var is_last: Boolean=false,

    ):Base()

data class ShowBookingNoticeBody(
    val data: ArrayList<BookingNoticeBody>,
    var count: Int,
    val status: Int
)
data class BookingNoticeBody(
    var header_id: String="",
    var body_id: String="",
    var cont_code: String="",
):Base()

data class ShowBookingNoticeLog(
    val data: ArrayList<BookingNoticeLog>,
    var count: Int,
    val status: Int
)
data class BookingNoticeLog(
    var code: String="",
    var date_time: String="",
    var shipping_number: String="",
    var shipping_order_number_old: String="",
    var shipping_order_number_new: String="",
    var header_id_old: String="",
    var header_id_new: String="",
):Base()

data class ShowOAReference(
    val data: ArrayList<OAReference>,
    var count: Int,
    val status: Int
)
data class OAReference(
    var oa_referenceNO1: String="",
    var oa_referenceNO2: String="",
    var oa_referenceNO3: String="",
    var oa_referenceNO4: String="",
):Base()


data class ShowOAFileDeliveryRecordHeader(
    val data: ArrayList<OAFileDeliveryRecordHeader>,
    var count: Int,
    val status: Int
)
data class OAFileDeliveryRecordHeader(
    var trackingNo: String="",
    var courier_company: String="",
    var delivery_date: String?=null,
    var arrival_date: String?=null,
    var receiver: String="",
    var shippin_billing_month: String="",
    var billing_date: String?=null,
):Base()

data class ShowOAFileDeliveryRecordBody(
    val data: ArrayList<OAFileDeliveryRecordBody>,
    var count: Int,
    val status: Int
)
data class OAFileDeliveryRecordBody(
    var _id: String="",
    var trackingNo: String="",
    var booking_noticeNo: String="",
):Base()

data class ShowShippingLog(
    val data: ArrayList<ShippingLog>,
    var count: Int,
    val status: Int
)
data class ShippingLog(
    var _id: String="",
    var bookingNo: String="",
    var booking_noticeNo: String="",
    var shipping_date_old: String="",
    var shipping_date_new: String="",
    var is_old: Boolean=false,
    var is_new: Boolean=false,
):Base()

data class ShowDMCILHeader(
    val data: ArrayList<DMCILHeader>,
    var count: Int,
    val status: Int
)
data class DMCILHeader(
    var _id: String="",
    var dept: String="",
    var topic: String="",
    var info_type: String="",
    var item_id: String="",
):Base()

data class ShowDMCILBody(
    val data: ArrayList<DMCILBody>,
    var count: Int,
    val status: Int
)
data class DMCILBody(
    var header_id: String="",
    var code: String="",
    var section: String="",
    var outline: String="",
    var context: String="",
    var dept: String="",
    var info_type: String="",
    var item_id: String="",
):Base()

data class ShowProductControlOrderHeader(
    val data: ArrayList<ProductControlOrderHeader>,
    var count: Int,
    val status: Int
)
data class ProductControlOrderHeader(
    var _id: String="",
    var is_non_production: Boolean=false,
    var item_id: String="",
    var latest_inspection_day: String?=null,
    var start_stock_up: String?=null,
    var end_stock_up: String?=null,
    var customer_poNo: String="",
    var customer_code: String="",
    var is_re_make: Boolean=false,
    var qc_date: String?=null,
    var qc_number: String?=null,
    var unit_of_measurement: String="",
    var est_start_date: String?=null,
    var est_complete_date: String?=null,
    var unit_of_timer: String="",
    var start_date: String?=null,
    var complete_date: String?=null,
    var actual_output: Int=0,
    var is_inspected: Boolean=false,
    var inspected_date: String?=null,
    var is_passed: Boolean=false,
    var passed_time: String?=null,
    var is_urgent: Boolean=false,
    var urgent_deadline: String?=null,
):Base()

data class ShowProductControlOrderBody_A(
    val data: ArrayList<ProductControlOrderBody_A>,
    var count: Int,
    val status: Int
)
data class ProductControlOrderBody_A(
    var header_id: String="",
    var prod_ctrl_order_number: String="",
    var me_code: String="",
    var semi_finished_prod_number: String="",
    var pline_id:  String="",
    var latest_inspection_day: String?=null,
    var is_re_make: Boolean=false,
    var qc_date: String?=null,
    var qc_number: String?=null,
    var unit_of_measurement: String="",
    var est_start_date: String?=null,
    var est_complete_date: String?=null,
    var est_output: Int=0,
    var unit_of_timer: String="",
    var start_date: String?=null,
    var complete_date: String?=null,
    var actual_output: Int=0,
    var is_request_support: Boolean=false,
    var number_of_support: Int=0,
    var number_of_supported: Int=0,
    var request_support_time: String?=null,
    var is_urgent: Boolean=false,
    var urgent_deadline: String?=null,
):Base()

data class ShowMealOrderListHeader(
    val data: ArrayList<MealOrderListHeader>,
    var count: Int,
    val status: Int
)
data class MealOrderListHeader(
    var _id: String="",
    var date: String?=null,
    var dept: String?=null,
    var l_meat_meals: Int=0,
    var l_vegetarian_meals:  Int=0,
    var l_indonesia_meals: Int=0,
    var l_num_of_selfcare: Int=0,
    var total_leave_hours: Double=0.0,
    var num_of_leave: Int=0,
    var attendance: Int=0,
    var number_of_support: Int=0,
    var total_support_hours: Double=0.0,
    var d_meat_meals: Int=0,
    var d_vegetarian_meals: Int=0,
    var d_indonesia_meals: Int=0,
    var d_num_of_selfcare: Int=0,
    var number_of_overtime: Int=0,
    var number_of_overtime_support:  Double=0.0,
    var is_check: Boolean=false,
    var check_time: String?=null,
):Base()

data class ShowMealOrderListBody(
    val data: ArrayList<MealOrderListBody>,
    var count: Int,
    val status: Int
)
data class MealOrderListBody(
    var header_id: String?=null,
    var order_number: String?=null,
    var card_number: String="",
    var is_support: Boolean=false,
    var support_hours:  Double=0.0,
    var is_morning_leave: Boolean=false,
    var is_lunch_leave: Boolean=false,
    var is_all_day_leave: Boolean=false,
    var leave_hours: Double=0.0,
    var is_l_meat_meals: Boolean=false,
    var is_l_vegetarian_meals: Boolean=false,
    var is_l_indonesia_meals: Boolean=false,
    var is_l_num_of_selfcare: Boolean=false,
    var is_overtime: Boolean=false,
    var overtime_hours: Double=0.0,
    var is_d_meat_meals: Boolean=false,
    var is_d_vegetarian_meals: Boolean=false,
    var is_d_indonesia_meals:  Boolean=false,
    var is_d_num_of_selfcare: Boolean=false,
):Base()

data class ShowProductControlOrderBody_B(
    val data: ArrayList<ProductControlOrderBody_B>,
    var count: Int,
    val status: Int
)
data class ProductControlOrderBody_B(
    var code: String="",
    var prod_ctrl_order_number: String="",
    var pline: String="",
    var workstation_number:  String="",
    var qr_code:  String="",
    var card_number:String="",
    var staff_name: String="",
    var online_time: String?=null,
    var offline_time: String?=null,
    var is_personal_report: Boolean=false,
    var actual_output: Int=0,
):Base()

data class ShowProductControlOrderBody_C(
    val data: ArrayList<ProductControlOrderBody_C>,
    var count: Int,
    val status: Int
)
data class ProductControlOrderBody_C(
    var code: String="",
    var prod_ctrl_order_number: String="",
    var pline: String="",
    var workstation_number:  String="",
    var qr_code:  String="",
    var card_number:String="",
    var staff_name: String="",
    var online_time: String?=null,
    var offline_time: String?=null,
    var is_personal_report: Boolean=false,
    var actual_output: Int=0,
):Base()

data class ShowProductControlOrderBody_D(
    val data: ArrayList<ProductControlOrderBody_D>,
    var count: Int,
    val status: Int
)
data class ProductControlOrderBody_D(
    var prod_ctrl_order_number: String="",
    var prod_batch_code: String="",
    var section: Int=0,
    var est_complete_date: String?=null,
    var est_complete_date_vender:  String?=null,
    var est_output: Double=0.0,
    var actual_output_vender: Double=0.0,
    var quantity_delivered: Double=0.0,
    var is_urgent: Boolean=false,
    var urgent_deadline: String?=null,
    var notice_matter: String="",
    var v_notice_matter: String="",
    var is_request_reply: Boolean=false,
    var notice_reply_time: String?=null,
    var is_vender_reply: Boolean=false,
    var vender_reply_time: String?=null,
    var is_argee: Boolean=false,
    var argee_time:String?=null,
    var is_v_argee: Boolean=false,
    var v_argee_time: String?=null,
):Base()

data class ShowProductionControlListRequisition(
    val data: ArrayList<ProductionControlListRequisition>,
    var count: Int,
    val status: Int
)
data class ProductionControlListRequisition(
    var requisition_number: String="",
    var prod_ctrl_order_number: String="",
    var header_id: String="",
    var section: String="",
    var item_id: String="",
    var unit_of_measurement: String="",
    var estimated_picking_date: String?=null,
    var estimated_picking_amount: Int=0,
    var is_inventory_lock: Boolean=false,
    var inventory_lock_time: String?=null,
    var is_existing_stocks: Boolean=false,
    var existing_stocks_amount:  Int=0,
    var existing_stocks_edit_time: String?=null,
    var pre_delivery_date: String?=null,
    var pre_delivery_amount: Int=0,
    var pre_delivery_edit_time: String?=null,
    var is_vender_check: Boolean=false,
    var vender_pre_delivery_date:  String?=null,
    var vender_pre_delivery_amount: Int=0,
    var vender_edit_time: String?=null,
    var procurement_approval: String="",
    var approval_instructions: String="",
    var is_instock: Boolean=false,
    var vender_instock_date: String?=null,
    var is_materials_sent:Boolean=false,
    var materials_sent_date: String?=null,
    var materials_sent_amount:  Int=0,
    var ng_amount: Int=0,
    var card_number: String="",
    var over_received_order: Boolean=false,
    var over_received_amount: Int=0,
    var is_urgent: Boolean=false,
    var urgent_deadline: String?=null,
):Base()

data class ShowStockTransOrderHeader(
    val data: ArrayList<StockTransOrderHeader>,
    var count: Int,
    val status: Int
)
data class StockTransOrderHeader(
    var _id: String="",
    var date: String?=null,
    var dept: String="",
    var main_trans_code: String="",
    var sec_trans_code: String="",
    var purchase_order_id: String="",
    var prod_ctrl_order_number: String="",
    var illustrate: String="",
):Base()

data class ShowStockTransOrderBody(
    val data: ArrayList<StockTransOrderBody>,
    var count: Int,
    val status: Int
)
data class StockTransOrderBody(
    var header_id: String="",
    var body_id: String="",
    var item_id: String="",
    var modify_count: Double=0.0,
    var main_trans_code: String="",
    var sec_trans_code: String="",
    var store_area: String="",
    var store_local: String="",
    var qc_insp_number:String="",
    var qc_time: String?=null,
    var ok_count: Double=0.0,
    var ng_count: Double=0.0,
    var scrapped_count: Double=0.0,
    var is_rework: Boolean=false,
):Base()


data class ShowEquipmentMaintenanceRecord(
    val data: ArrayList<EquipmentMaintenanceRecord>,
    var count: Int,
    val status: Int
)
data class EquipmentMaintenanceRecord(
    var maintenance_id: String="",
    var equipment_id: String="",
    var date: String?=null,
    var start_time: String?=null,
    var end_time: String?=null,
    var vender_id: String="",
    var maintenance_type: String="",
    var is_ok:Boolean=false,
    var is_not_available: Boolean=false,
):Base()

data class ShowPurchaseOrderHeader(
    val data: ArrayList<PurchaseOrderHeader>,
    var count: Int,
    val status: Int
)
data class PurchaseOrderHeader(
    var poNo: String="",
    var purchase_date: String?=null,
    var vender_id: String="",
    var vender_name: String="",
    var order: String="",
):Base()

data class ShowPurchaseOrderBody(
    val data: ArrayList<PurchaseOrderBody>,
    var count: Int,
    val status: Int
)
data class PurchaseOrderBody(
    var body_id: String="",
    var poNo: String="",
    var section: String="",
    var item_id: String="",
    var purchase_count: Double=0.0,
    var purchase_in_count: Double=0.0,
    var purchase_undelivered_count: Double=0.0,
    var unit_of_measurement:String="",
    var pre_delivery_date: String?=null,
    var total_batch: Int=0,
    var purchase_date: String?=null,
    var master_order_numer: String="",
    var material_preparation_number: String="",
    var inline_number: String="",
    var is_done: Boolean=false,
    var done_reason: String="",
    var done_time:String?=null,
):Base()

data class ShowPurchaseBatchOrder(
    val data: ArrayList<PurchaseBatchOrder>,
    var count: Int,
    val status: Int
)
data class PurchaseBatchOrder(
    var batch_id: String="",
    var purchase_order_id: String="",
    var section: String="",
    var count: Double=0.0,
    var pre_delivery_date:  String?=null,
    var v_count:Double=0.0,
    var v_pre_delivery_date: String?=null,
    var quantity_delivered: Double=0.0,
    var prod_ctrl_order_number: String="",
    var is_warning: Boolean=false,
    var is_urgent: Boolean=false,
    var urgent_deadline: String?=null,
    var notice_matter: String="",
    var v_notice_matter: String="",
    var is_request_reply: Boolean=false,
    var notice_reply_time: String?=null,
    var is_vender_reply: Boolean=false,
    var vender_reply_time: String?=null,
    var is_argee: Boolean=false,
    var argee_time:String?=null,
    var is_v_argee: Boolean=false,
    var v_argee_time: String?=null,
    var number_of_standard_measurements: Double=0.0,
):Base()

data class ShowPurchasePreparationListHeader(
    val data: ArrayList<PurchasePreparationListHeader>,
    var count: Int,
    val status: Int
)
data class PurchasePreparationListHeader(
    var poNo: String="",
    var vender_id: String="",
):Base()

data class ShowPurchasePreparationListBody(
    val data: ArrayList<PurchasePreparationListBody>,
    var count: Int,
    val status: Int
)
data class PurchasePreparationListBody(
    var body_id: String="",
    var poNo: String="",
    var section: String="",
    var item_id: String="",
    var name: String="",
    var purchase_date: String?=null,
    var stock_quantity: Double=0.0,
    var accumulated_deduction: Double=0.0,
    var unit_of_measurement: String="",
):Base()

data class ShowVenderShipmentHeader(
    val data: ArrayList<VenderShipmentHeader>,
    var count: Int,
    val status: Int
)
data class VenderShipmentHeader(
    var poNo: String="",
    var purchase_date: String?=null,
    var vender_id: String="",
    var vender_shipment_id: String="",
):Base()

data class ShowVenderShipmentBody(
    val data: ArrayList<VenderShipmentBody>,
    var count: Int,
    val status: Int
)
data class VenderShipmentBody(
    var body_id: String="",
    var poNo: String="",
    var section: String="",
    var item_id: String="",
    var purchase_count: Double=0.0,
    var batch_id: String="",
    var prod_batch_code: String="",
    var qc_date:String?=null,
    var qc_number: String="",
    var is_tobe_determined: Boolean=false,
    var is_acceptance: Boolean=false,
    var is_reject: Boolean=false,
    var is_special_case: Boolean=false,
):Base()

data class ShowPurchaseInlineOrderHeader(
    val data: ArrayList<PurchaseInlineOrderHeader>,
    var count: Int,
    val status: Int
)
data class PurchaseInlineOrderHeader(
    var _id: String="",
    var vender_id: String="",
    var date: String?=null,
):Base()

data class ShowPurchaseInlineOrderBody(
    val data: ArrayList<PurchaseInlineOrderBody>,
    var count: Int,
    val status: Int
)
data class PurchaseInlineOrderBody(
    var header_id: String="",
    var body_id: String="",
    var section: String="",
    var item_id: String="",
    var item_name: String="",
    var pre_delivery_date: String?=null,
    var purchase_quantity: Double=0.0,
    var accumulated_deduction: Double=0.0,
    var unit_of_measurement: String="",
):Base()