package com.example.erp20
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {

    lateinit var username:EditText
    lateinit var password:EditText
    lateinit var tokenValue :String
    var cookieStore= HashMap<String, List<Cookie>?>()
    val okHttpClient = OkHttpClient.Builder().cookieJar(object :CookieJar{
        override fun saveFromResponse(httpUrl: HttpUrl, list: List<Cookie>)
        {
            Log.d("Save", "response: ${httpUrl.host}")
            cookieStore[httpUrl.host] = list
        }
        override fun loadForRequest(httpUrl: HttpUrl): List<Cookie>
        {
            Log.d("Load", "response: ${httpUrl.host}")
            val cookies = cookieStore[httpUrl.host]
            if(cookies==null){
                Log.d("沒加載到cookie", "response: 哭阿")
            }
            return cookies ?: ArrayList()
        }
    }).build()

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

        okHttpClient.newCall(request).enqueue(object : Callback {
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
                    tokenValue=cookie.value
                }
                val result = response.body?.string();
                Log.d("Token", "response:${tokenValue}")
            }
        })
    }

    fun btn_login(view: View) {

        if(username.text.trim().isEmpty() || password.text.trim().isEmpty()){
            Toast.makeText(this,"Input required",Toast.LENGTH_LONG).show()
        }
        else{
            login();
            Toast.makeText(this,"Login Success",Toast.LENGTH_LONG).show()
            val intent=Intent(this,DisplayActivity::class.java)
            intent.putExtra("data","test data")
            startActivity(intent)

        }

    }

    private fun login() {
        val body= FormBody.Builder()
            .add("username", "System")
            .add("password", "cvFm9Mq6")
            .add("csrfmiddlewaretoken",tokenValue)
            .build()
        val request= Request.Builder()
            .url("http://140.125.46.125:8000/login")
            .header("User-Agent","ERP_MOBILE")
            .post(body)
            .build()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("登入失敗", e.toString())
            }
            override fun onResponse(call: Call, response: Response) {
                //Log.d("登入成功", "response:${response.body?.string()}")
                var s= response.body?.string()
                val login_Info = Gson().fromJson(s, com.example.erp20.Model.login::class.java)
                Log.d("GSON", "msg: ${login_Info.msg}")


            }
        })
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

}