package com.vasu.moneytracker

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MainActivity3 : AppCompatActivity() {
    lateinit var totalassets:TextView
    lateinit var totalliabilities:TextView
    lateinit var totalincome:TextView
    lateinit var totalexpenses:TextView
    lateinit var username:TextView
    lateinit var position:TextView
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
        setTitle("Profile")
        sharedPreferences=getSharedPreferences(getString(R.string.Data), Context.MODE_PRIVATE)
        totalassets=findViewById(R.id.totalassetsedit)
        totalliabilities=findViewById(R.id.totalliabilitiesedit)
        totalexpenses=findViewById(R.id.totalexpensesedit)
        totalincome=findViewById(R.id.totalincomeedit)
        username=findViewById(R.id.user)
        position=findViewById(R.id.positionedit)
        username.setText(sharedPreferences.getString("username","UserName"))
        var assets=getArray(sharedPreferences,"assets-3")
        var liabilities=getArray(sharedPreferences,"liabilities-3")
        var incomes=getArray(sharedPreferences,"incomes-3")
        var expenses=getArray(sharedPreferences,"expenses-3")
        var sum1=0
        for(x in 0..assets.size-1){
            sum1=sum1+assets[x].toInt()
        }
        var sum2=0
        for(x in 0..liabilities.size-1){
            sum2=sum2+liabilities[x].toInt()
        }
        var sum3=0
        for(x in 0..incomes.size-1){
            sum3=sum3+incomes[x].toInt()
        }
        var sum4=0
        for(x in 0..expenses.size-1){
            sum4=sum4+expenses[x].toInt()
        }
        totalassets.setText(sum1.toString())
        totalliabilities.setText(sum2.toString())
        totalexpenses.setText(sum4.toString())
        totalincome.setText(sum3.toString())
        position.setText((sum1-sum2).toString())
        Navigation=findViewById(R.id.bottom_nav)
        Navigation.setSelectedItemId(R.id.nav_profile)
        Navigation.setOnNavigationItemSelectedListener { item ->  // using lamda
            myClickItem(item) //call here
            true
        }
    }
    fun myClickItem(item: MenuItem){
        when(item.getItemId()){
            R.id.nav_dashboard->{
                intent= Intent(this@MainActivity3,MainActivity2::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                finish()
            }
            R.id.nav_logout->{
                MaterialAlertDialogBuilder(this).apply {
                    setTitle("Log Out")
                    setMessage("Are you Sure you want to Log Out?")
                    setPositiveButton("Yes") { _, _ ->
                        intent=Intent(this@MainActivity3,MainActivity::class.java)
                        sharedPreferences.edit().putBoolean("isLoggedin",false).apply()
                        sharedPreferences.edit().clear().commit()
                        Toast.makeText(this@MainActivity3,"Successfully Logged Out",Toast.LENGTH_SHORT).show()
                        startActivity(intent)
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                        finish()
                    }
                    setNeutralButton("No") { _, _ ->
                        Navigation.setSelectedItemId(R.id.nav_profile)
                    }
                }.create().show()
            }
            R.id.nav_settings->{
                intent= Intent(this@MainActivity3,MainActivity4::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                finish()
            }
        }
    }
    fun getArray(SharedPreferences: SharedPreferences, Key:String):Array<String>{
        var ArrayLength=SharedPreferences.getInt(Key+(-1).toString(),0)
        var item:String
        var array= emptyArray<String>()
        for(i in 0..(ArrayLength-1)){
            item= SharedPreferences.getString((Key.toString()+i.toString()).toString(),"").toString()
            array=array+item
        }
        return array
    }
}