package com.vasu.moneytracker

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.text.SimpleDateFormat
import java.util.*

lateinit var submit: Button
lateinit var Assettitleedit:EditText
lateinit var Assetvalueedit:EditText
lateinit var Assettitle:TextView
lateinit var Assetvalue:TextView
lateinit var pagetitle:TextView
lateinit var sharedpreferences: SharedPreferences
class MainActivity6 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var actionBar = getSupportActionBar()
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setDisplayShowHomeEnabled(true)
        }
        setContentView(R.layout.activity_main6)
        submit=findViewById(R.id.Submitbutton)
        Assettitle=findViewById(R.id.assettitle)
        Assettitleedit=findViewById(R.id.assettitleedit)
        Assetvalue=findViewById(R.id.assetvalue)
        Assetvalueedit=findViewById(R.id.assetvalueedit)
        pagetitle=findViewById(R.id.AddAsset)
        sharedpreferences=getSharedPreferences(getString(R.string.Data), Context.MODE_PRIVATE)
        var mode= sharedpreferences.getString("mode","assets")
        setTitle("Add ${mode?.capitalize()}")
        pagetitle.setText("")
        if(mode=="incomes"){
            Assettitle.setText("Income Title")
            Assetvalue.setText("Income(+)")
        }
        else if(mode=="expenses"){
            Assettitle.setText("Expense Title")
            Assetvalue.setText("Expense(-)")
        }
        else if(mode=="liabilities"){
            Assettitle.setText("Liability Title")
            Assetvalue.setText("Liability Value(-)")
        }
        submit.setOnClickListener {
            if(Assetvalueedit.getText().toString()=="" || Assettitleedit.getText().toString()==""){
                Toast.makeText(
                        this@MainActivity6,
                        "All Fields are Required",
                        Toast.LENGTH_SHORT
                ).show()
            }
            else {
                var array1 = getArray(sharedpreferences, mode + "-2")
                var array2 = getArray(sharedpreferences, mode + "-3")
                var array3 = getArray(sharedPreferences,mode+"-4")
                array1 = arrayOf(Assettitleedit.getText().toString())+array1
                array2 = arrayOf(Assetvalueedit.getText().toString())+array2
                val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy 'at' HH:mm:ss ")
                val currentDateAndTime: String = simpleDateFormat.format(Date())
                array3=arrayOf(currentDateAndTime)+array3
                saveArray(array1, array1.size, sharedpreferences, mode + "-2")
                saveArray(array2, array2.size, sharedpreferences, mode + "-3")
                saveArray(array3, array3.size, sharedpreferences, mode + "-4")
                intent = Intent(this@MainActivity6, MainActivity5::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                finish()
            }
        }
        Navigation=findViewById(R.id.bottom_nav)
        Navigation.setSelectedItemId(R.id.nav_settings)
        Navigation.setOnNavigationItemSelectedListener { item ->  // using lamda
            myClickItem(item) //call here
            true
        }
    }
    fun myClickItem(item: MenuItem){
        when(item.getItemId()){
            R.id.nav_dashboard -> {
                intent = Intent(this@MainActivity6, MainActivity2::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                finish()
            }
            R.id.nav_logout -> {
                MaterialAlertDialogBuilder(this).apply {
                    setTitle("Log Out")
                    setMessage("Are you Sure you want to Log Out?")
                    setPositiveButton("Yes") { _, _ ->
                        intent=Intent(this@MainActivity6,MainActivity::class.java)
                        sharedPreferences.edit().putBoolean("isLoggedin",false).apply()
                        sharedPreferences.edit().clear().commit()
                        Toast.makeText(this@MainActivity6,"Successfully Logged Out",Toast.LENGTH_SHORT).show()
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
                intent = Intent(this@MainActivity6, MainActivity3::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                finish()
            }
        }
    }
    fun saveArray(Array:Array<String>,ArrayLength:Int,SharedPreferences:SharedPreferences,Key:String){
        SharedPreferences.edit().putInt(Key+(-1).toString(),ArrayLength).apply()
        for(i in 0..(ArrayLength-1)){
            SharedPreferences.edit().putString(Key+i.toString(),Array[i]).apply()
        }
    }
    fun getArray(SharedPreferences:SharedPreferences,Key:String):Array<String>{
        var ArrayLength=SharedPreferences.getInt(Key+(-1).toString(),0)
        var item:String
        var array= emptyArray<String>()
        for(i in 0..(ArrayLength-1)){
            item= SharedPreferences.getString((Key.toString()+i.toString()).toString(),"").toString()
            array=array+item
        }
        return array
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        intent = Intent(this@MainActivity6, MainActivity5::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        finish()
        return true
    }
}