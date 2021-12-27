package com.example.erp20

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.erp20.ClassFragment.OPS.OPS05changeFragment
import com.example.erp20.ClassFragment.OPS.OPS05maintainFragment
import com.example.erp20.ClassFragment.PUR.PUR04maintainFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class OPS_Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ops)
        val OPS05maintainFragment= OPS05maintainFragment()
        val OPS05changeFragment= OPS05changeFragment()
        currentFragment(OPS05maintainFragment)
        //bottom nav
        val navView: BottomNavigationView =findViewById(R.id.bottom_nav_view)
        navView.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.nav_prework -> {
                    //currentFragment(Produce02preworkFragment)
                    Toast.makeText(applicationContext, "1", Toast.LENGTH_SHORT).show()
                }
                R.id.nav_maintain -> {
                    currentFragment(OPS05maintainFragment)
                    Toast.makeText(applicationContext, "2", Toast.LENGTH_SHORT).show()
                }
                R.id.nav_change_data_maintain -> {
                    currentFragment(OPS05changeFragment)
                    Toast.makeText(applicationContext, "3", Toast.LENGTH_SHORT).show()
                }
                R.id.nav_batch_processing -> {
                    Toast.makeText(applicationContext, "4", Toast.LENGTH_SHORT).show()
                }
                R.id.nav_dashboard -> {
                    Toast.makeText(applicationContext, "5", Toast.LENGTH_SHORT).show()
                }
            }

            true
        }




    }

    //切換分頁
    private fun currentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.current_fragment,fragment)
            //addToBackStack(null)//啟用返回建
            commit()
        }

    }

    //點擊旁邊自動收起鍵盤
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }
}