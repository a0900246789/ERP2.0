package com.example.erp20.app04

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.erp20.Model.Response
import com.example.erp20.R
import com.example.erp20.RecyclerAdapter.RecyclerItemProductControlOrderBodyADetailAdapter
import com.example.erp20.RecyclerAdapter.RecyclerItemProductControlOrderBodyDAdapter
import com.example.erp20.RecyclerAdapter.RecyclerItemProductControlOrderBodyDSimplifyAdapter
import com.example.erp20.cookie_data
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.FormBody
import okhttp3.Request
import org.json.JSONObject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PopUpOutSourceFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PopUpOutSourceFragment(filter:String) : DialogFragment() {
    private lateinit var ProductControlOrderBody_D_Simplify_adapter: RecyclerItemProductControlOrderBodyDSimplifyAdapter
    lateinit var Filter: String
    init {
        Filter=filter
    }
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setWidthPercent(90)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pop_up_out_source, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        show_header_body_filter("ProductControlOrderBody_D","condition","False",Filter)
        var popUpName=view.findViewById<TextView>(R.id.popUpName)
        popUpName.text="批量檢視"
        var dismiss=view.findViewById<Button>(R.id.dismiss_btn)
        dismiss?.setOnClickListener {
            dismiss()
        }
        val recyclerView=view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager= LinearLayoutManager(view.context)//設定Linear格式
        ProductControlOrderBody_D_Simplify_adapter= RecyclerItemProductControlOrderBodyDSimplifyAdapter()
        recyclerView.adapter=ProductControlOrderBody_D_Simplify_adapter//找對應itemAdapter

    }

    //異動資料單頭單身過濾
    private fun show_header_body_filter(operation:String,view_type:String,view_hide:String,selectedfilter:String) {
        when(operation){
            "ProductControlOrderBody_D"->{
                val filter = JSONObject()
                filter.put("is_closed",false)
                filter.put("prod_ctrl_order_number",selectedfilter)
                val body = FormBody.Builder()
                    .add("username", cookie_data.username)
                    .add("operation", operation)
                    .add("action", cookie_data.Actions.VIEW)
                    .add("view_type",view_type)
                    .add("view_hide",view_hide)
                    .add("data",filter.toString())
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
                    if(responseInfo.msg !=null){
                        cookie_data.msg=responseInfo.msg
                    }
                    //Log.d("GSON", "msg:${responseInfo}")
                }
            }
        }
    }

    fun DialogFragment.setWidthPercent(percentage: Int) {
        val percent = percentage.toFloat() / 100
        val dm = Resources.getSystem().displayMetrics
        val rect = dm.run { Rect(0, 0, widthPixels, heightPixels) }
        val percentWidth = rect.width() * percent
        dialog?.window?.setLayout(percentWidth.toInt(), ViewGroup.LayoutParams.WRAP_CONTENT)
    }


    /*companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PopUpOutSourceFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PopUpOutSourceFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }*/
}