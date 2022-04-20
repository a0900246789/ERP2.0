package com.example.erp20

import android.Manifest
import android.app.AlertDialog
import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.erp20.Model.*
import com.google.gson.Gson
import kotlinx.coroutines.*
import okhttp3.*
import okhttp3.Response
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.properties.Delegates


class cookie_data : Application() {
    companion object {
        lateinit var tokenValue :String
        lateinit var loginflag:String
        lateinit var response_data:String
        lateinit var msg:String
        lateinit var card_number:String
        lateinit var scan_qrcode:String
        lateinit var scan_card_number:String
        lateinit var dept:String
        var username:String=""//"System"//"One"
        var password:String=""//"cvFm9Mq6"//"eb2014326"
        const val URL:String="http://35.201.203.196"//"http://sunwhiteerptest.ddns.net:8000"//"http://118.168.43.235:8000"//"http://140.125.46.125:8000"
        fun currentDateTime():String{
            var datetime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            var readDate=Calendar.getInstance()
            var currentDatetime = datetime.format(readDate.time)
            return currentDatetime
        }
        var date = SimpleDateFormat("yyyy-MM-dd")
        var currentDate = date.format(Date())
        lateinit var selectedFilter:String
        var MealOrderListBody_ArrayList_data: ArrayList<MealOrderListBody> = ArrayList<MealOrderListBody>()
        var cardwork:String="上線"
        val Routes=routes()
        class routes(
            val special_basic_management:String="/special_basic_management",
            val basic_management:String="/basic_management",
            val def_management:String="/def_management",
            val logout:String="/logout",
            val login:String="/login",
            val vender_log_management:String="/vender_log_management",
            val dmcil_management:String="/dmcil_management",
            val shipping_order_management:String="/shipping_order_management",
            val inventory_management:String="/inventory_management",
            val work_management:String="/work_management",
            val master_scheduled_order_management:String="/master_scheduled_order_management",
            val stacking_control_management:String="/stacking_control_management",
            val production_control_sheet_management:String="/production_control_sheet_management",
            val purchase_order_management:String="/purchase_order_management",
            val custom_order_management:String="/custom_order_management",
            val staff_management:String="/staff_management",
        )

        var status by Delegates.notNull<Int>()
        var cookieStore = HashMap<String, List<Cookie>?>()
        fun setcookie(httpUrl: String, list: List<Cookie>){
            cookieStore[httpUrl] = list
        }
        fun getcookie(httpUrl: String):List<Cookie>{
            return cookieStore[httpUrl]?: ArrayList()
        }
        val okHttpClient = OkHttpClient.Builder().cookieJar(object :CookieJar{
            override fun saveFromResponse(httpUrl: HttpUrl, list: List<Cookie>)
            {
                Log.d("Save", "response: ${httpUrl.host}")
                //cookieStore[httpUrl.host] = list
                var Save=setcookie(httpUrl.host,list)
            }
            override fun loadForRequest(httpUrl: HttpUrl): List<Cookie>
            {
                Log.d("Load", "response: ${httpUrl.host}")
                //val cookies = cookieStore[httpUrl.host]
                val cookies = getcookie(httpUrl.host)
                if(cookies==null){
                    Log.d("沒加載到cookie", "response: 哭阿")
                }
                return cookies ?: ArrayList()
            }
        }).build()
        val Actions=actions()
        class actions(
             val VIEW:String="0",
             val ADD:String="1",
             val CHANGE:String="2",
             val DELETE:String="3",
             val LOCK:String="4",
             val PERMISSION_ADD:String="5",
             val PERMISSION_DELETE:String="6",
             val CLOSE:String="7",
             val BUNDLE_CHANGE:String="8",
        )

        var currentAdapter:String=""
        var itemposition:Int=0
        lateinit var recyclerView: RecyclerView
        lateinit var first_recyclerView: RecyclerView
        lateinit var second_recyclerView: RecyclerView
        lateinit var third_recyclerView: RecyclerView

        lateinit var Add_View: View

        var limited_days_ComboboxData: MutableList<Int> = mutableListOf<Int>()//採購交期協調限制天數
        var customer_id_ComboboxData: MutableList<String> = mutableListOf<String>()//客戶編號
        var customer_abbreviation_ComboboxData: MutableList<String> = mutableListOf<String>()//客戶簡稱
        var customer_booking_prefix_ComboboxData: MutableList<String> = mutableListOf<String>()//客戶訂艙通知單前置碼
        var cont_id_ComboboxData: MutableList<String> = mutableListOf<String>()//櫃編(去除年度)
        var cont_code_ComboboxData: MutableList<String> = mutableListOf<String>()//櫃編
        var cont_age_ComboboxData: MutableList<String> = mutableListOf<String>()//櫃編年度
        var product_id_ComboboxData: MutableList<String> = mutableListOf<String>()//產品編號
        var product_name_ComboboxData: MutableList<String> = mutableListOf<String>()//產品名稱
        var product_type_id_ComboboxData: MutableList<String> = mutableListOf<String>()//產品型號
        var cont_type_code_ComboboxData: MutableList<String> = mutableListOf<String>()//櫃型代號
        var port_id_ComboboxData: MutableList<String> = mutableListOf<String>()//港口編號
        var store_area_ComboboxData: MutableList<String> = mutableListOf<String>()//儲區
        var store_area_name_ComboboxData: MutableList<String> = mutableListOf<String>()//儲區名稱
        var store_area_id_name_ComboboxData: MutableList<String> = mutableListOf<String>()//儲區id+名稱
        var store_local_ComboboxData: MutableList<String> = mutableListOf<String>()//儲位
        var shipping_number_ComboboxData: MutableList<String> = mutableListOf<String>()//船公司代號
        var shipping_name_ComboboxData: MutableList<String> = mutableListOf<String>()//船公司簡稱
        var shipping_number_name_ComboboxData: MutableList<String> = mutableListOf<String>()//船公司-代號-簡稱
        var item_id_ComboboxData: MutableList<String> = mutableListOf<String>()//料件編號
        var Filter_item_id_ComboboxData: MutableList<String> = mutableListOf<String>()//料件編號(篩選過)
        var item_name_ComboboxData: MutableList<String> = mutableListOf<String>()//料件名稱
        var item_id_name_ComboboxData: MutableList<String> = mutableListOf<String>()//料件編號+名稱
        var semi_finished_product_number_ComboboxData: MutableList<String> = mutableListOf<String>()//半成品編號
        var is_exemption_ComboboxData: MutableList<Boolean> = mutableListOf<Boolean>()//免驗
        var pline_name_ComboboxData: MutableList<String> = mutableListOf<String>()//生產線名稱
        var pline_id_ComboboxData: MutableList<String> = mutableListOf<String>()//生產線編號
        var pline_id_name_ComboboxData: MutableList<String> = mutableListOf<String>()//生產線編號名稱
        var out_sourceing_ComboboxData: MutableList<Boolean> = mutableListOf<Boolean>()//生產線托外
        var pline_vender_id_ComboboxData: MutableList<String> = mutableListOf<String>()//生產線廠商編號
        var dept_name_ComboboxData: MutableList<String> = mutableListOf<String>()//部門

        var card_number_ComboboxData: MutableList<String> = mutableListOf<String>()//員工卡號
        var name_ComboboxData: MutableList<String> = mutableListOf<String>()//員工姓名
        var qrcode_ComboboxData: MutableList<String> = mutableListOf<String>()//員工Qrcode
        var staff_dept_ComboboxData: MutableList<String> = mutableListOf<String>()//員工部門
        var is_work_ComboboxData: MutableList<Boolean> = mutableListOf<Boolean>()//員工在職

        var inv_code_m_ComboboxData: MutableList<String> = mutableListOf<String>()//主異動別代號
        var inv_code_name_m_ComboboxData: MutableList<String> = mutableListOf<String>()//主異動別代號名稱
        var inv_code_s_ComboboxData: MutableList<String> = mutableListOf<String>()//次異動別代號
        var inv_code_name_s_ComboboxData: MutableList<String> = mutableListOf<String>()//次異動別代號名稱
        var inv_code_s_inv_code_m_ComboboxData: MutableList<String> = mutableListOf<String>()//次異動別代號-主異動別代號

        var workstation_number_ComboboxData: MutableList<String> = mutableListOf<String>()//工作站編號
        var device_id_ComboboxData: MutableList<String> = mutableListOf<String>()//設備編號
        var vender_id_ComboboxData: MutableList<String> = mutableListOf<String>()//廠商編號
        var vender_abbreviation_ComboboxData: MutableList<String> = mutableListOf<String>()//廠商簡稱
        var vender_id_halfname_ComboboxData: MutableList<String> = mutableListOf<String>()//廠商編號簡稱
        var vender_card_number_ComboboxData: MutableList<String> = mutableListOf<String>()//聯絡廠商的員工id
        var maintain_type_ComboboxData: MutableList<String> = mutableListOf<String>()//維護類別

        var ProductControlOrderHeader_id_ComboboxData: MutableList<String> = mutableListOf<String>()//生產管制單號
        var ProductControlOrderHeader_poNo_ComboboxData: MutableList<String> = mutableListOf<String>()//生產管制單頭-poNo
        var ProductControlOrderBody_A_prod_ctrl_order_number_ComboboxData: MutableList<String> = mutableListOf<String>()//生產管制單身-編號
        var ProductControlOrderBody_A_me_code_ComboboxData: MutableList<String> = mutableListOf<String>()//生產管制單身-工程編制單編號
        var ProductControlOrderBody_A_semi_finished_prod_number_ComboboxData: MutableList<String> = mutableListOf<String>()//生產管制單身-半成品編號
        var ProductControlOrderBody_A_pline_id_ComboboxData: MutableList<String> = mutableListOf<String>()//生產管制單身-生產線
        var ProductControlOrderBody_A_complete_date_ComboboxData: MutableList<String?> = mutableListOf<String?>()//生產管制單身-實際完工日期
        var ProductControlOrderBody_A_est_complete_date_ComboboxData: MutableList<String?> = mutableListOf<String?>()//生產管制單身-預際完工日期
        var ProductControlOrderBody_A_is_closed_ComboboxData: MutableList<Boolean> = mutableListOf<Boolean>()//生產管制單身-是否結案
        var ProductControlOrderBody_A_is_re_make_ComboboxData: MutableList<Boolean> = mutableListOf<Boolean>()//生產管制單身-重工
        var ProductControlOrderBody_A_est_output_ComboboxData: MutableList<Int> = mutableListOf<Int>()//生產管制單身-預計產量
        var ProductControlOrderBody_A_est_start_date_ComboboxData: MutableList<String?> = mutableListOf<String?>()//生產管制單身-預計開工日期
        var ProductControlOrderBody_B_filter_offline_time_ComboboxData: MutableList<String?> = mutableListOf<String?>()//報工-下線時間
        var ProductControlOrderBody_D_prod_batch_code_ComboboxData: MutableList<String> = mutableListOf<String>()//生產管制單批號
        var MasterScheduledOrderHeader_id_ComboboxData: MutableList<String> = mutableListOf<String>()//主排單號
        var CustomerOrderHeader_poNo_ComboboxData: MutableList<String> = mutableListOf<String>()//客戶訂單單號
        var CustomerOrderHeader_swe_ComboboxData: MutableList<String?> = mutableListOf<String?>()//客戶訂單單頭-swe
        var MeHeader_id_ComboboxData: MutableList<String> = mutableListOf<String>()//工程編制單號
        var MeBody_process_number_ComboboxData: MutableList<String> = mutableListOf<String>()//工程編制單身-編號
        var MeBody_work_option_ComboboxData: MutableList<String> = mutableListOf<String>()//工程編制單身-作業項目
        var PurchasePreparationListBody_id_ComboboxData: MutableList<String> = mutableListOf<String>()//採購備料單編號
        var PurchaseBatchOrder_batch_id_ComboboxData: MutableList<String> = mutableListOf<String>()//採購單批號
        var PurchaseBatchOrder_purchase_order_id_ComboboxData: MutableList<String> = mutableListOf<String>()//採購單批號-採購單編號
        var PurchaseBatchOrder_is_closed_ComboboxData: MutableList<Boolean> = mutableListOf<Boolean>()//採購單批號-結案
        var PurchaseOrderHeader_poNo_ComboboxData: MutableList<String> = mutableListOf<String>()//採購單頭-單號
        var PurchaseOrderHeader_vender_name_ComboboxData: MutableList<String> = mutableListOf<String>()//採購單頭-廠商簡稱

        var PurchaseOrderBody_poNo_ComboboxData: MutableList<String> = mutableListOf<String>()//採購單身-單號
        var PurchaseOrderBody_body_id_ComboboxData: MutableList<String> = mutableListOf<String>()//採購單身-編號
        var PurchaseOrderBody_item_id_ComboboxData: MutableList<String> = mutableListOf<String>()//採購單身-料件編號
        var PurchaseInlineOrderBody_id_ComboboxData: MutableList<String> = mutableListOf<String>()//內聯單編號
        var VenderShipmentHeader_poNo_ComboboxData: MutableList<String?> = mutableListOf<String?>()//廠商進貨單(單頭)-進貨單號
        var VenderShipmentHeader_purchase_date_ComboboxData: MutableList<String?> = mutableListOf<String?>()//廠商進貨單(單頭)-進貨日期
        var VenderShipmentHeader_vender_id_ComboboxData: MutableList<String> = mutableListOf<String>()//廠商進貨單(單頭)-廠商編號
        var VenderShipmentHeader_vender_shipment_id_ComboboxData: MutableList<String> = mutableListOf<String>()//廠商進貨單(單頭)-廠商出貨單號
        var StockTransOrderHeader_id_ComboboxData: MutableList<String> = mutableListOf<String>()//庫存異動單(單頭)-單號
        var StockTransOrderHeader_date_ComboboxData: MutableList<String?> = mutableListOf<String?>()//庫存異動單(單頭)-異動日期
        var StockTransOrderHeader_dept_ComboboxData: MutableList<String> = mutableListOf<String>()//庫存異動單(單頭)-異動單位
        var StockTransOrderHeader_purchase_order_id_ComboboxData: MutableList<String> = mutableListOf<String>()//庫存異動單(單頭)-採購單編號
        var StockTransOrderHeader_prod_ctrl_order_number_ComboboxData: MutableList<String> = mutableListOf<String>()//庫存異動單(單頭)-生產管制單編號
        var StockTransOrderHeader_main_trans_code_ComboboxData: MutableList<String> = mutableListOf<String>()//庫存異動單(單頭)-主異動別代號
        var StockTransOrderHeader_sec_trans_code_ComboboxData: MutableList<String> = mutableListOf<String>()//庫存異動單(單頭)-次異動別代號
        var OAReference_oa_referenceNO_ComboboxData: MutableList<String> = mutableListOf<String>()//OAReference01
        var OAReference_oa_referenceNO1_ComboboxData: MutableList<String> = mutableListOf<String>()//OAReference02
        var OAReference_oa_referenceNO2_ComboboxData: MutableList<String> = mutableListOf<String>()//OAReference03
        var OAReference_oa_referenceNO3_ComboboxData: MutableList<String> = mutableListOf<String>()//OAReference04
        var BookingNoticeHeader_id_ComboboxData: MutableList<String> = mutableListOf<String>()//訂艙通知單-單頭-訂艙管制單號
        var BookingNoticeHeader_notice_numbe_ComboboxData: MutableList<String> = mutableListOf<String>()//訂艙通知單-單頭-訂艙通知號碼
        var BookingNoticeHeader_customer_poNo_ComboboxData: MutableList<String> = mutableListOf<String>()//訂艙通知單-單頭-客戶訂單單號
        var BookingNoticeHeader_oa_referenceNO1_ComboboxData: MutableList<String> = mutableListOf<String>()//訂艙通知單-單頭-OA1
        var OAFileDeliveryRecordBody_trackingNo_ComboboxData: MutableList<String> = mutableListOf<String>()//OA文件寄送記錄(單身)-寄件單據號碼
        var OAFileDeliveryRecordBody_booking_noticeNo_ComboboxData: MutableList<String> = mutableListOf<String>()//OA文件寄送記錄(單身)-訂艙通知號碼
        var OAFileDeliveryRecordHeader_courier_company_ComboboxData: MutableList<String> = mutableListOf<String>()//OA文件寄送記錄(單頭)-快遞公司
        var OAFileDeliveryRecordHeader_shippin_billing_month_ComboboxData: MutableList<String> = mutableListOf<String>()//OA文件寄送記錄(單頭)-船務結帳月份
        var StackingControlListHeader_code_ComboboxData: MutableList<String> = mutableListOf<String>()//疊櫃管制單(單頭)-單號
        var StackingControlListHeader_contNo_ComboboxData: MutableList<String> = mutableListOf<String>()//疊櫃管制單(單頭)-櫃編
        var StackingControlListHeader_cont_type_code_ComboboxData: MutableList<String> = mutableListOf<String>()//疊櫃管制單(單頭)-櫃型代號

    }
}
class MainActivity : AppCompatActivity() {
    lateinit var username:EditText
    lateinit var password:EditText
    lateinit var login_Info:login

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        gettoken()
        username = findViewById<EditText>(R.id.Edit_Username)
        val userid = getSharedPreferences("record", MODE_PRIVATE)
            .getString("USER", "")
        username.setText(userid)
        password = findViewById<EditText>(R.id.Edit_Password)

    }
    override fun onStart() {
        super.onStart()
        // 畫面開始時檢查權限
        onClickRequestPermission()
    }
    private fun gettoken() {
        val request= Request.Builder()
            .url(cookie_data.URL+"/login")
            .header("User-Agent","ERP_MOBILE")
            .build()
        cookie_data.okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("HKT123", e.toString())
            }
            override fun onResponse(call: Call, response: Response) {
                val headers = response.headers
                val loginUrl = request.url
                val cookies = Cookie.parseAll(loginUrl, headers)
                val cookieStr = StringBuilder()
                for (cookie in cookies) {
                    cookieStr.append(cookie.name).append("=").append(cookie.value + ";")
                    cookie_data.tokenValue=cookie.value
                }
                val result = response.body?.string();
                Log.d("Token", "response:${cookie_data.tokenValue}")
            }
        })
    }
    fun btn_login(view: View) {

        if(username.text.trim().isEmpty() || password.text.trim().isEmpty()){
            Toast.makeText(this,"Input required",Toast.LENGTH_LONG).show()
        }
        else{
            cookie_data.username=username.text.toString()
            cookie_data.password=password.text.toString()
            login()
        }

    }
    private fun login() {
        val body = FormBody.Builder()
            .add("username", cookie_data.username)
            .add("password", cookie_data.password)
            .add("csrfmiddlewaretoken", cookie_data.tokenValue)
            .build()
        val request = Request.Builder()
            .url(cookie_data.URL+"/login")
            .header("User-Agent", "ERP_MOBILE")
            .post(body)
            .build()

        runBlocking {
            var job = CoroutineScope(Dispatchers.IO).launch {
                var response = cookie_data.okHttpClient.newCall(request).execute()
                var response_info=response.body?.string().toString()
                Log.d("GSON", "msg: ${response_info}")
                login_Info = Gson().fromJson(response_info, login::class.java)


            }
            job.join()
            when(login_Info.status)
            {
                0->{
                    val pref = getSharedPreferences("record", MODE_PRIVATE)
                    pref.edit()
                        .putString("USER", cookie_data.username)
                        .commit()
                    cookie_data.loginflag=login_Info.login_flag
                    cookie_data.card_number=login_Info.card_number
                    cookie_data.dept=login_Info.dept
                    Toast.makeText(applicationContext,login_Info.msg,Toast.LENGTH_SHORT).show()
                    val intent=Intent(applicationContext,DisplayActivity::class.java)
                    startActivity(intent)
                }
                1->{
                    Toast.makeText(applicationContext,login_Info.msg,Toast.LENGTH_SHORT).show()
                }
            }

            /*val bundle=Bundle().apply {
                putString("token",cookie_data.tokenValue)
                putString("login_flag",cookie_data.loginflag)
            }
            intent.putExtra("Bundle",bundle)*/

        }


    }
    fun unicodeDecode(unicode: String): String {
        val stringBuffer = StringBuilder()
        var i = 0
        while (i < unicode.length) {
            if (i + 1 < unicode.length)
                if (unicode[i].toString() + unicode[i + 1].toString() == "\\u") {
                    val symbol = unicode.substring(i + 2, i + 6)
                    val c = Integer.parseInt(symbol, 16)
                    stringBuffer.append(c.toChar())
                    i += 5
                } else stringBuffer.append(unicode[i])
            i++
        }
        return stringBuffer.toString()
    }

    //點擊旁邊自動收起鍵盤
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }


    private fun onAgree() {
        Toast.makeText(this, "已取得相機權限", Toast.LENGTH_SHORT).show()
        // 取得權限後要做的事情...
    }

    private fun onDisagree() {
        Toast.makeText(this, "未取得相機權限", Toast.LENGTH_SHORT).show()
        // 沒有取得權限的替代方案...
    }

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission())
    { isGranted: Boolean ->
        // 判斷使用者是否給予權限
        if (isGranted) {
            onAgree()
        } else {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                // 被拒絕太多次，無法開啟請求權限視窗
                AlertDialog.Builder(this)
                    .setTitle("需要相機權限")
                    .setMessage("這個APP需要相機權限，因為被拒絕太多次，無法自動給予權限，請至設定手動開啟")
                    .setPositiveButton("Ok") { _, _ ->
                        // 開啟本App在設定中的權限視窗，在內心祈禱使用者願意給予權限
                        openPermissionSettings()
                    }
                    .setNeutralButton("No") { _, _ -> onDisagree() }
                    .show()
            }
        }
    }

    //取得權限
    private fun onClickRequestPermission() {
        when {
            ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED -> {
                // 情況一：已經同意
                Toast.makeText(this, "已取得相機權限", Toast.LENGTH_SHORT).show()
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.CAMERA
            ) -> {
                // 情況二：被拒絕過，彈出視窗告知本App需要權限的原因
                AlertDialog.Builder(this)
                    .setTitle("需要相機權限")
                    .setMessage("這個APP需要相機權限，請給予權限")
                    .setPositiveButton("Ok") { _, _ -> requestPermissionLauncher.launch(Manifest.permission.CAMERA) }
                    .setNeutralButton("No") { _, _ -> onDisagree() }
                    .show()
            }
            else -> {
                // 情況三：第一次請求權限，直接請求權限
                requestPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }

    // 開啟設定頁面
    private fun openPermissionSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        startActivity(intent)
    }

}