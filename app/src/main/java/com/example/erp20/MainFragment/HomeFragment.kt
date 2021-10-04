package com.example.erp20.MainFragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.example.erp20.DisplayActivity
import com.example.erp20.R
import com.example.erp20.SaleActivity
import com.example.erp20.cookie_data

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val theTextView = getView()?.findViewById<TextView>(R.id.home_fragment_text)
        val saleBtn=getView()?.findViewById<ImageButton>(R.id.btn_01)
        theTextView?.text = "home\n"+"${cookie_data.tokenValue}\n"+"${cookie_data.loginflag}"


        saleBtn?.setOnClickListener{
            //Toast.makeText(activity,"1233211212",Toast.LENGTH_SHORT).show()
            val intent=Intent(activity, SaleActivity::class.java)
            startActivity(intent)

        }

    }
}