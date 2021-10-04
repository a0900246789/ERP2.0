package com.example.erp20

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedDispatcher
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.erp20.MainFragment.HomeFragment
import com.example.erp20.MainFragment.SettingFragment
import com.google.android.material.navigation.NavigationView
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.*



class DisplayActivity : AppCompatActivity() {

    private lateinit var toggle:ActionBarDrawerToggle
    private lateinit var loginflag:String
    private lateinit var msg:String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display)
        //get data
        /*val passedBundle =intent.getBundleExtra("Bundle")
        tokenValue=passedBundle?.getString("token").toString()
        loginflag=passedBundle?.getString("login_flag").toString()*/

        //fragment
        val homeFragment=HomeFragment()
        val settingFragment=SettingFragment()
        currentFragment(homeFragment)
        /*theTextView.text = "token:${passedBundle?.getString("token")}\n" +
                "login_flag:${passedBundle?.getString("login_flag")}\n"*/

        //drawer
        val drawerLayout:DrawerLayout=findViewById(R.id.drawerLayout)
        //navTop
        val navView:NavigationView=findViewById(R.id.nav_view)
        toggle= ActionBarDrawerToggle(this,drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)//返回建

        navView.setNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.nav_home -> {
                    currentFragment(homeFragment)
                    Toast.makeText(applicationContext, "home", Toast.LENGTH_SHORT).show()
                }
                R.id.nav_settings -> {
                    currentFragment(settingFragment)
                    Toast.makeText(applicationContext, "setting", Toast.LENGTH_SHORT).show()
                }
                R.id.nav_logout -> {
                    val mAlertDialog = AlertDialog.Builder(this)
                    mAlertDialog.setIcon(R.mipmap.ic_launcher_round) //set alertdialog icon
                    mAlertDialog.setTitle("登出") //set alertdialog title
                    mAlertDialog.setMessage("確定要登出?") //set alertdialog message
                    mAlertDialog.setPositiveButton("Yes") { dialog, id ->
                        logout()
                    }
                    mAlertDialog.setNegativeButton("No") { dialog, id ->
                        dialog.dismiss()
                    }
                    mAlertDialog.show()
                }
            }
            drawerLayout.closeDrawers()//點擊完彈回
            true
        }

    }
    //切換分頁
    private fun currentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.current_fragment,fragment)
            commit()
        }

    }

    private fun logout() {
        val body = FormBody.Builder()
            .add("username", "System")
            .add("password", "cvFm9Mq6")
            .add("csrfmiddlewaretoken", cookie_data.tokenValue)
            .add("login_flag",cookie_data.loginflag)
            .build()
        val request = Request.Builder()
            .url("http://140.125.46.125:8000/logout")
            .header("User-Agent", "ERP_MOBILE")
            .post(body)
            .build()
        runBlocking {
            var job = CoroutineScope(Dispatchers.IO).launch {
                var response = cookie_data.okHttpClient.newCall(request).execute()

                val login_Info = Gson().fromJson(response.body?.string(), com.example.erp20.Model.login::class.java)
                response.body?.run {
                    Log.d("GSON", "msg: ${login_Info.msg}")
                    loginflag=login_Info.login_flag
                    msg=login_Info.msg
                }
            }
            job.join()
            Toast.makeText(applicationContext,msg,Toast.LENGTH_SHORT).show()
            val logoutIntent= Intent(applicationContext,MainActivity::class.java)
            logoutIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)//清空上一頁
            startActivity(logoutIntent)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return  true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        val mAlertDialog = AlertDialog.Builder(this)
        mAlertDialog.setIcon(R.mipmap.ic_launcher_round) //set alertdialog icon
        mAlertDialog.setTitle("登出") //set alertdialog title
        mAlertDialog.setMessage("確定要登出?") //set alertdialog message
        mAlertDialog.setPositiveButton("Yes") { dialog, id ->
            logout()
        }
        mAlertDialog.setNegativeButton("No") { dialog, id ->
            dialog.dismiss()
        }
        mAlertDialog.show()
    }//返回建


}


