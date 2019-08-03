package com.example.alarmmanager

import android.annotation.TargetApi
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.TimePicker
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    lateinit var am:AlarmManager
    lateinit var tp:TimePicker
    lateinit var update_text:TextView
    lateinit var con:Context
    lateinit var btnstart:Button
    lateinit var btnstop:Button
    var hour:Int=0
    var min:Int=0
    lateinit var pi:PendingIntent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.con=this
        am=getSystemService(Context.ALARM_SERVICE) as AlarmManager
        tp=findViewById(R.id.tp) as TimePicker
        update_text=findViewById(R.id.update_text) as TextView
        btnstart=findViewById(R.id.setalarm) as Button
        btnstop=findViewById(R.id.stopalram) as Button
        var calander:Calendar= Calendar.getInstance()
        var myintent:Intent=Intent(this,alarmreciver::class.java)
        btnstart.setOnClickListener(object:View.OnClickListener{
            @TargetApi(Build.VERSION_CODES.KITKAT)
            override fun onClick(p0: View?) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    calander.set(Calendar.HOUR_OF_DAY,tp.hour)
                    calander.set(Calendar.MINUTE,tp.minute)
                    calander.set(Calendar.SECOND,0)
                    calander.set(Calendar.MILLISECOND,0)
                    hour= tp.hour
                    min=tp.minute


                }
                else{
                    calander.set(Calendar.HOUR_OF_DAY,tp.currentHour)
                    calander.set(Calendar.MINUTE,tp.currentMinute)
                    calander.set(Calendar.SECOND,0)
                    calander.set(Calendar.MILLISECOND,0)
                    hour= tp.currentHour
                    min=tp.currentMinute
                }
                var hr_str:String=hour.toString()
                var m_str:String=hour.toString()
                if(hour>12){
                    hr_str=(hour-12).toString()
                }
                if(min<10){
                    m_str="0$min"
                }
                set_alarm_text("Alarm set to :$hr_str:$m_str")
                myintent.putExtra("extra","on")
                pi= PendingIntent.getBroadcast(this@MainActivity,0,myintent,PendingIntent.FLAG_CANCEL_CURRENT)
                am.setExact(AlarmManager.RTC_WAKEUP,calander.timeInMillis,pi)

            }
        })
        btnstop.setOnClickListener(object :View.OnClickListener{
            override fun onClick(p0: View?) {
                set_alarm_text("Alram off")
                pi= PendingIntent.getBroadcast(this@MainActivity,0,myintent,PendingIntent.FLAG_CANCEL_CURRENT)
                am.cancel(pi)
                myintent.putExtra("extra","off")
                sendBroadcast(myintent)
            }

        })

    }

    private fun set_alarm_text(s: String) {
        update_text.setText(s)

    }



}
