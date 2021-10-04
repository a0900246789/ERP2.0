package com.example.erp20
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.erp20.Model.login
import com.google.gson.Gson
import kotlinx.coroutines.*
import okhttp3.*
import java.io.IOException
import kotlin.properties.Delegates

class cookie_data : Application() {
    companion object {
        lateinit var tokenValue :String
        lateinit var loginflag:String
        lateinit var response_data:String
        lateinit var msg:String
        val username:String="System"
        val password:String="cvFm9Mq6"
        lateinit var card_number:String
        var status by Delegates.notNull<Int>()
        var itemCount:Int = 0
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
        password = findViewById<EditText>(R.id.Edit_Password)

    }
    private fun gettoken() {
        val request= Request.Builder()
            .url("http://140.125.46.125:8000/login")
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
            login();
        }

    }
    private fun login() {
        val body = FormBody.Builder()
            .add("username", cookie_data.username)
            .add("password", cookie_data.password)
            .add("csrfmiddlewaretoken", cookie_data.tokenValue)
            .build()
        val request = Request.Builder()
            .url("http://140.125.46.125:8000/login")
            .header("User-Agent", "ERP_MOBILE")
            .post(body)
            .build()

        runBlocking {
            var job = CoroutineScope(Dispatchers.IO).launch {
                var response = cookie_data.okHttpClient.newCall(request).execute()
                response.body?.run {
                    login_Info = Gson().fromJson(string(), login::class.java)
                    Log.d("GSON", "msg: ${login_Info.msg}")
                    cookie_data.loginflag=login_Info.login_flag
                    cookie_data.card_number=login_Info.card_number
                }
            }
            job.join()
            Toast.makeText(applicationContext,login_Info.msg,Toast.LENGTH_SHORT).show()
            val intent=Intent(applicationContext,DisplayActivity::class.java)
            /*val bundle=Bundle().apply {
                putString("token",cookie_data.tokenValue)
                putString("login_flag",cookie_data.loginflag)

            }
            intent.putExtra("Bundle",bundle)*/
            startActivity(intent)
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
}