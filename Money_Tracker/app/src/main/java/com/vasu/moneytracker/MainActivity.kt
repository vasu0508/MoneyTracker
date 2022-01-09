package com.vasu.moneytracker

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var usernameedit:EditText
    lateinit var moneyinhandedit:EditText
    lateinit var submitbutton:Button
    lateinit var sharedPreferences:SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setTitle("Login")
        sharedPreferences=getSharedPreferences(getString(R.string.Data), Context.MODE_PRIVATE)
        var isLoggedin=sharedPreferences.getBoolean("isLoggedin",false)
        var username=sharedPreferences.getString("username","Guest")
        var monthlyincome=sharedPreferences.getFloat("monthlyincome",0F)
        var moneyinhand=sharedPreferences.getFloat("moneyinhand",0F)
        usernameedit = findViewById(R.id.usernameedit)
        moneyinhandedit = findViewById(R.id.moneyinhandedit)
        submitbutton = findViewById(R.id.submitbutton)
        if(isLoggedin==false) {
            submitbutton.setOnClickListener {
                if(usernameedit?.text.toString()!="" && moneyinhandedit?.text.toString()!="") {
                    storedata()
                    Toast.makeText(
                        this@MainActivity,
                        "Welcome " + usernameedit.getText(),
                        Toast.LENGTH_SHORT
                    ).show()
                    var intent = Intent(this@MainActivity, MainActivity2::class.java)
                    startActivity(intent)
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                    finish()
                }
                else{
                    Toast.makeText(
                        this@MainActivity,
                        "All Fields are Required",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        else{
            Toast.makeText(this@MainActivity, "Welcome Back "+username, Toast.LENGTH_SHORT).show()
            var intent= Intent(this@MainActivity,MainActivity2::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            finish()
        }
    }
    public fun storedata(){
        sharedPreferences.edit().putBoolean("isLoggedin",true).apply()
        sharedPreferences.edit().putString("username",usernameedit.text.toString()).apply()
        sharedPreferences.edit().putFloat("moneyinhand",moneyinhandedit.text.toString().toFloat()).apply()
        var a:Array<String> = emptyArray()
        var b:Array<String> = emptyArray()
        var c:Array<String> = emptyArray()
        b=b+"Cash in hand"
        a=a+moneyinhandedit.getText().toString()
        val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy 'at' HH:mm:ss ")
        val currentDateAndTime: String = simpleDateFormat.format(Date())
        c=c+currentDateAndTime
        saveArray(a,a.size,sharedPreferences,"assets"+"-3")
        saveArray(b,b.size,sharedPreferences,"assets"+"-2")
        saveArray(c,c.size,sharedPreferences,"assets"+"-4")
    }
    fun saveArray(Array:Array<String>,ArrayLength:Int,SharedPreferences:SharedPreferences,Key:String){
        SharedPreferences.edit().putInt(Key+(-1).toString(),ArrayLength).apply()
        for(i in 0..(ArrayLength-1)){
            SharedPreferences.edit().putString(Key+i.toString(),Array[i]).apply()
        }
    }
}