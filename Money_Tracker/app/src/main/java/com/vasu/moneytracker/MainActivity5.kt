package com.vasu.moneytracker
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
lateinit var l: ListView
lateinit var add:ImageButton
class MainActivity5 : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main5)
        sharedPreferences=getSharedPreferences(getString(R.string.Data), Context.MODE_PRIVATE)
        var mode= sharedPreferences.getString("mode","assets")
        setTitle(mode?.capitalize())
        var actionBar = getSupportActionBar()
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setDisplayShowHomeEnabled(true)
        }
        var titlearray=getArray(sharedPreferences,mode+"-2")
        var valuearray=getArray(sharedPreferences,mode+"-3")
        var array3=getArray(sharedPreferences,mode+"-4")
        var a=ArrayList<newClass>()
        for(x in 0..titlearray.size-1){
            var b=newClass(titlearray[x],valuearray[x],array3[x])
            a.add(b)
        }
        var sum:Int=0
        for(x in 0..valuearray.size-1){
            sum=sum+valuearray[x].toInt()
        }
        a.add(newClass("Total",sum.toString(),""))
        l = findViewById(R.id.list)
        val customAdapter=CustomAdapter(this,R.layout.listview_activity,a)
        l.setAdapter(customAdapter)
        l.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val clickedItem = parent.getItemAtPosition(position).toString()
            // show an alert dialog to confirm
            if(position != (a.size - 1)) {
                MaterialAlertDialogBuilder(this).apply {
                    setTitle("${a[position].gettitle()}")
                    setMessage("Do you want to delete It from list?")
                    setPositiveButton("Delete") { _, _ ->
                        // remove clicked item from second list view
                        a.removeAt(position)
                        var b = titlearray.toCollection(ArrayList())
                        b.removeAt(position)
                        titlearray = b.toTypedArray()
                        saveArray(titlearray, titlearray.size, sharedPreferences, mode + "-2")
                        customAdapter.notifyDataSetChanged()
                        var c = valuearray.toCollection(ArrayList())
                        c.removeAt(position)
                        valuearray = c.toTypedArray()
                        saveArray(valuearray, valuearray.size, sharedPreferences, mode + "-3")
                        var d = array3.toCollection(ArrayList())
                        d.removeAt(position)
                        array3 = d.toTypedArray()
                        saveArray(array3, array3.size, sharedPreferences, mode + "-4")
                        customAdapter.notifyDataSetChanged()
                    }
                setNeutralButton("Cancel") { _, _ -> }
            }.create().show()
        }
        }
        add=findViewById(R.id.Add)
        add.setOnClickListener{ view->
            intent = Intent(this@MainActivity5, MainActivity6::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            finish()
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
            intent = Intent(this@MainActivity5, MainActivity4::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            finish()
        return true
    }

}