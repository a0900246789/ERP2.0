package com.example.erp20.MainFragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import com.example.erp20.*
import com.example.erp20.app01.Activity01
import com.example.erp20.app02.Activity02
import com.example.erp20.app04.Activity04
import com.example.erp20.app05.Activity05
import com.example.erp20.app06.Activity06
import com.example.erp20.app07.Activity07
import com.example.erp20.app08.Activity08
import com.example.erp20.app09.Activity09
import com.example.erp20.app10.Activity10
import com.example.erp20.app13.Activity13

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

        val Btn01=getView()?.findViewById<ImageButton>(R.id.btn_01)
        val Btn02=getView()?.findViewById<ImageButton>(R.id.btn_02)
        val Btn03=getView()?.findViewById<ImageButton>(R.id.btn_03)
        val Btn04=getView()?.findViewById<ImageButton>(R.id.btn_04)
        val Btn05=getView()?.findViewById<ImageButton>(R.id.btn_05)
        val Btn06=getView()?.findViewById<ImageButton>(R.id.btn_06)
        val Btn07=getView()?.findViewById<ImageButton>(R.id.btn_07)
        val Btn08=getView()?.findViewById<ImageButton>(R.id.btn_08)
        val Btn09=getView()?.findViewById<ImageButton>(R.id.btn_09)
        val Btn10=getView()?.findViewById<ImageButton>(R.id.btn_10)
        val Btn13=getView()?.findViewById<ImageButton>(R.id.btn_13)

        //01
        Btn01?.setOnClickListener{
            //Toast.makeText(activity,"1233211212",Toast.LENGTH_SHORT).show()
            val intent=Intent(activity, Activity01::class.java)
            startActivity(intent)
        }
        //02
        Btn02?.setOnClickListener{
            //Toast.makeText(activity,"1233211212",Toast.LENGTH_SHORT).show()
            val intent=Intent(activity, Activity02::class.java)
            startActivity(intent)
        }
        //03
        Btn03?.setOnClickListener{
            //Toast.makeText(activity,"1233211212",Toast.LENGTH_SHORT).show()
            val intent=Intent(activity, STK_Activity::class.java)
            startActivity(intent)
        }
        //04
        Btn04?.setOnClickListener{
            //Toast.makeText(activity,"1233211212",Toast.LENGTH_SHORT).show()
            val intent=Intent(activity, Activity04::class.java)
            startActivity(intent)
        }
        //05
        Btn05?.setOnClickListener{
            //Toast.makeText(activity,"1233211212",Toast.LENGTH_SHORT).show()
            val intent=Intent(activity, Activity05::class.java)
            startActivity(intent)
        }
        //06
        Btn06?.setOnClickListener{
            //Toast.makeText(activity,"1233211212",Toast.LENGTH_SHORT).show()
            val intent=Intent(activity, Activity06::class.java)
            startActivity(intent)
        }
        //07
        Btn07?.setOnClickListener{
            //Toast.makeText(activity,"1233211212",Toast.LENGTH_SHORT).show()
            val intent=Intent(activity, Activity07::class.java)
            startActivity(intent)
        }
        //08
        Btn08?.setOnClickListener{
            //Toast.makeText(activity,"1233211212",Toast.LENGTH_SHORT).show()
            val intent=Intent(activity, Activity08::class.java)
            startActivity(intent)
        }
        //09
        Btn09?.setOnClickListener{
            //Toast.makeText(activity,"1233211212",Toast.LENGTH_SHORT).show()
            val intent=Intent(activity, Activity09::class.java)
            startActivity(intent)
        }
        //10
        Btn10?.setOnClickListener{
            //Toast.makeText(activity,"1233211212",Toast.LENGTH_SHORT).show()
            val intent=Intent(activity, Activity10::class.java)
            startActivity(intent)
        }


        //13
        Btn13?.setOnClickListener{
            //Toast.makeText(activity,"1233211212",Toast.LENGTH_SHORT).show()
            val intent=Intent(activity, Activity13::class.java)
            startActivity(intent)
        }

    }
}