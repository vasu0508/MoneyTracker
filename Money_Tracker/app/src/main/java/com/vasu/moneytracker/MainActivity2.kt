package com.vasu.moneytracker

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.lang.String.format
import java.text.SimpleDateFormat
import java.util.*

lateinit var Navigation:BottomNavigationView
lateinit var sharedPreferences:SharedPreferences
lateinit var averageexpense:TextView
lateinit var averageincome:TextView
lateinit var averageposition:TextView
lateinit var monthposition:TextView
lateinit var status:TextView
class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle("Dashboard")
        setContentView(R.layout.activity_main2)
        sharedPreferences=getSharedPreferences(getString(R.string.Data), Context.MODE_PRIVATE)
        averageexpense=findViewById(R.id.averageexpenseedit)
        averageincome=findViewById(R.id.averageincomeedit)
        averageposition=findViewById(R.id.averagepositionedit)
        monthposition=findViewById(R.id.monthlypositionedit)
        status=findViewById(R.id.status)
        val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy 'at' HH:mm:ss ")
        val currentDateAndTime: Date=Date()
        var incomearray=getArray(sharedPreferences,"incomes-3")
        var expensearray=getArray(sharedPreferences,"expenses-3")
        var incomedatearray=getArray(sharedPreferences,"incomes-4")
        var expensedatearray=getArray(sharedPreferences,"expenses-4")
        var average=0
        for(x in 0..expensedatearray.size-1) {
            var date: Date = simpleDateFormat.parse(expensedatearray[x])
            var month=date.getMonth()
            if(month==currentDateAndTime.getMonth()){
                average=average+expensearray[x].toInt()
            }
        }
        var average2=0
        for(x in 0..incomedatearray.size-1) {
            var date: Date = simpleDateFormat.parse(incomedatearray[x])
            var month=date.getMonth()
            if(month==currentDateAndTime.getMonth()){
                average2=average2+incomearray[x].toInt()
            }
        }
        monthposition.setText((average2-average).toString())
        var a:String
        if((average2-average)>0){
            a="Gain"
        }
        else if((average2-average)<0){
            a="Loss"
        }
        else{
            a="Neutral"
        }
        status.setText(a)
        var avexpense:Double=0.toDouble()
        var avincome:Double=0.toDouble()
        if(incomedatearray.size!=0) {
            var months = currentDateAndTime.getMonth() - simpleDateFormat.parse(incomedatearray[incomedatearray.size - 1]).getMonth()
            var years = currentDateAndTime.getMonth() - simpleDateFormat.parse(incomedatearray[incomedatearray.size - 1]).getMonth()
            var days=currentDateAndTime.getDay() - simpleDateFormat.parse(incomedatearray[incomedatearray.size - 1]).getDay()
            var totalDays=days+months*30+years*12
            var totalmonths=totalDays/30+1
            var sum1=0
            for(x in 0..incomearray.size-1){
                sum1=sum1+incomearray[x].toInt()
            }
            var average3:Double=sum1.toDouble()/totalmonths.toDouble()
            avincome=average3
            averageincome.setText(average3.toString())
        }
        if(expensedatearray.size!=0) {
            var months = currentDateAndTime.getMonth() - simpleDateFormat.parse(expensedatearray[expensedatearray.size - 1]).getMonth()
            var years = currentDateAndTime.getMonth() - simpleDateFormat.parse(expensedatearray[expensedatearray.size - 1]).getMonth()
            var days=currentDateAndTime.getDay() - simpleDateFormat.parse(expensedatearray[expensedatearray.size - 1]).getDay()
            var totalDays=days+months*30+years*12
            var totalmonths:Int=totalDays/30+1
            var sum1=0
            for(x in 0..expensearray.size-1){
                sum1=sum1+expensearray[x].toInt()
            }
            var average3:Double=sum1.toDouble()/totalmonths.toDouble()
            avexpense=average3
            averageexpense.setText(average3.toString())
        }
        var avposition=avincome-avexpense
        averageposition.setText(avposition.toString())
        Navigation=findViewById(R.id.bottom_nav)
        Navigation.setSelectedItemId(R.id.nav_dashboard)
        Navigation.setOnNavigationItemSelectedListener { item ->  // using lamda
            myClickItem(item) //call here
            true
        }
    }
    fun myClickItem(item: MenuItem){
        when(item.getItemId()){
            R.id.nav_logout->{
                MaterialAlertDialogBuilder(this).apply {
                    setTitle("Log Out")
                    setMessage("Are you Sure you want to Log Out?")
                    setPositiveButton("Yes") { _, _ ->
                        intent=Intent(this@MainActivity2,MainActivity::class.java)
                        sharedPreferences.edit().putBoolean("isLoggedin",false).apply()
                        sharedPreferences.edit().clear().commit()
                        Toast.makeText(this@MainActivity2,"Successfully Logged Out",Toast.LENGTH_SHORT).show()
                        startActivity(intent)
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                        finish()
                    }
                    setNeutralButton("No") { _, _ ->
                        Navigation.setSelectedItemId(R.id.nav_dashboard)
                    }
                }.create().show()

            }
            R.id.nav_profile->{
                intent=Intent(this@MainActivity2,MainActivity3::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                finish()
            }
            R.id.nav_settings->{
                intent=Intent(this@MainActivity2,MainActivity4::class.java)
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