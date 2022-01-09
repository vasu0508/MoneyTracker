package com.vasu.moneytracker

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MainActivity4 : AppCompatActivity() {
    lateinit var assetsbutton:Button
    lateinit var liabilitiesbutton:Button
    lateinit var incomebutton:Button
    lateinit var expensesbutton:Button
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main4)
        setTitle("Settings")
        Navigation=findViewById(R.id.bottom_nav)
        Navigation.setSelectedItemId(R.id.nav_settings)
        Navigation.setOnNavigationItemSelectedListener { item ->  // using lamda
            myClickItem(item) //call here
            true
        }
        assetsbutton=findViewById(R.id.AssetsButton)
        liabilitiesbutton=findViewById(R.id.LiabilitiesButton)
        incomebutton=findViewById(R.id.IncomeButton)
        expensesbutton=findViewById(R.id.ExpensesButton)
        sharedPreferences=getSharedPreferences(getString(R.string.Data), Context.MODE_PRIVATE)
        assetsbutton.setOnClickListener {
            sharedPreferences.edit().putString("mode","assets").apply()
            var intent=Intent(this@MainActivity4,MainActivity5::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            finish()
        }
        liabilitiesbutton.setOnClickListener {
            sharedPreferences.edit().putString("mode","liabilities").apply()
            var intent=Intent(this@MainActivity4,MainActivity5::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            finish()
        }
        incomebutton.setOnClickListener {
            sharedPreferences.edit().putString("mode","incomes").apply()
            var intent=Intent(this@MainActivity4,MainActivity5::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            finish()
        }
        expensesbutton.setOnClickListener {
            sharedPreferences.edit().putString("mode","expenses").apply()
            var intent=Intent(this@MainActivity4,MainActivity5::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            finish()
        }
    }
    fun myClickItem(item: MenuItem){
        when(item.getItemId()){
            R.id.nav_dashboard -> {
                intent = Intent(this@MainActivity4, MainActivity2::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                finish()
            }
            R.id.nav_logout -> {
                MaterialAlertDialogBuilder(this).apply {
                    setTitle("Log Out")
                    setMessage("Are you Sure you want to Log Out?")
                    setPositiveButton("Yes") { _, _ ->
                        intent=Intent(this@MainActivity4,MainActivity::class.java)
                        sharedPreferences.edit().putBoolean("isLoggedin",false).apply()
                        sharedPreferences.edit().clear().commit()
                        Toast.makeText(this@MainActivity4,"Successfully Logged Out",Toast.LENGTH_SHORT).show()
                        startActivity(intent)
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                        finish()
                    }
                    setNeutralButton("No") { _, _ ->
                        Navigation.setSelectedItemId(R.id.nav_settings)
                    }
                }.create().show()
            }
            R.id.nav_profile -> {
                intent = Intent(this@MainActivity4, MainActivity3::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                finish()
            }
        }
    }
}