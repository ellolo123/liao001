package com.example.myapplication000

import android.app.Activity
import android.app.ActivityManager
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URI
import java.net.URL
import kotlin.concurrent.thread


class MainActivity2 : AppCompatActivity() {

    var button4:Button=findViewById(R.id.button4)
    var textView2: TextView ?= null
    var str:String?=null
    var handler=object :Handler(Looper.getMainLooper()){

            override fun handleMessage(msg:Message){
                if(msg.what==0)
                {
                    str=msg.obj.toString()

                }
            }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        textView2= findViewById(R.id.textView2)
        button4.setOnClickListener(View.OnClickListener{
//            val intent: Intent = Intent(this, MainActivity::class.java)
            val intent:Intent= Intent()
            intent.putExtra("st", str)
            setResult(RESULT_OK,intent)
//            startActivityForResult(intent,1)
        })
        val intent: Intent = getIntent()
//        if(intent!=null)
        val sturl: String? = intent.getStringExtra("internetAddr")
        val uri: URI = URI.create(sturl)
        val url: URL = uri.toURL()

        thread{
            try {
                try {
//            定位资源字节流
                    val is1: InputStream = url.openStream()
//            转换成字符流
                    val isr: InputStreamReader = InputStreamReader(is1, "utf-8")
//            字符流进入缓冲区
                    val br: BufferedReader = BufferedReader(isr)
                    var str = br.readLine()
                    val m=Message()
                    m.what=0
                    m.obj=str
                    handler.sendMessage(m)

//
//                    while (str != null) {
//                        print(str)
//                        str = br.readLine()
//                    }

                    br.close()
                    is1.close()
                    isr.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            } catch (e: MalformedURLException) {
                e.printStackTrace()
            }
        }
        displayText(textView2,str)

    }

    @Override
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                setResult(0, Intent().putExtra())
                this.finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)

    }

    fun displayText(textv:TextView,st:String):Unit{
        textv.text=st
    }

    fun onActivityResult(requestCode:Int,resultCode:Int,data:Intent){
        if(requestCode==1){
            //刷新


        }
    }


}