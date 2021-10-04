package com.example.erp20.Model
import okhttp3.*
import android.app.Application
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
}
data class login (
    val status: Int,
    val msg: String,
    val login_flag: String,
    val card_number:String
)
data class response(
    val status: Int,
    val msg: String
)
data class ShowProdType(
    val data: ArrayList<ProdType>,
    val count: Int,
    val status: Int
)

data class ProdType(
    var product_type_name: String,
    var product_type_id: String,
):Base()
data class ShowContNo(
    val data: ArrayList<ContNo>,
    val count: Int,
    val status: Int
)

data class ContNo(
    var cont_code: String,
    var customer_code: String,
    var age: String,
    var serial_number: Int,
    var cont_code_name: String,
):Base()

data class ShowContType(
    val data: ArrayList<ContType>,
    val count: Int,
    val status: Int
)

data class ContType(
    var cont_type_code: String,
    var cont_type: String,
    var order: String,
):Base()


data class ShowPort(
    val data: ArrayList<Port>,
    val count: Int,
    val status: Int
)

data class Port(
    var port_id: String,
    var port_name: String,
):Base()

data class ShowProductBasicInfo(
    val data: ArrayList<ProductBasicInfo>,
    val count: Int,
    val status: Int
)

data class ProductBasicInfo(
    var _id: String,
    var product_name: String,
    var product_type: String,
    var is_new : Boolean,
    var release_date : String,
    var is_discontinue : Boolean,
    var discontinued_date : String,
    var is_inventory : Boolean,
    var inventory_date  : String,
):Base()