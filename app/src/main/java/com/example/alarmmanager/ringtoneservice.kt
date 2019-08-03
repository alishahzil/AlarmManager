package com.example.alarmmanager

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.os.IBinder
import androidx.core.app.NotificationCompat

class ringtoneservice:Service() {
    companion object{
        lateinit var r:Ringtone
    }
    var id:Int=0
    var isruning:Boolean=false
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        var state:String=intent!!.getStringExtra("extra")
        assert(state!=null)
        when (state){
            "on"->id=1
            "off"->id=0
        }
        if (!this.isruning&&id==1){
            playalarm()
            this.isruning=true
            this.id=0
            firenotification()
        }else if(this.isruning&&id==0){
            r.stop()
            this.isruning=false
            this.id=1

        }else if (!this.isruning&&id==0){
            this.isruning=false
            this.id=0

        }else if (this.isruning&&id==1){
            this.isruning=true
            this.id=1

        }else{

        }
        return START_NOT_STICKY
    }

    private fun firenotification(){
        var main_activity_intent:Intent=Intent(this,MainActivity::class.java)
        var pi:PendingIntent= PendingIntent.getActivity(this,0,main_activity_intent,0)
        val defaultsoundUri=RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        var notify_manager:NotificationManager=getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        var notificationManager:NotificationManager=getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        var notification:Notification=NotificationCompat.Builder(this)
            .setContentTitle("Alarm is going off")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setSound(defaultsoundUri)
            .setContentText("CLick me")
            .setContentIntent(pi)
            .setAutoCancel(true)
            .build()
        notify_manager.notify(0,notification)


    }

    private fun playalarm() {
        var alarmUri:Uri=RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        if(alarmUri==null){
            alarmUri=RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        }
        r=RingtoneManager.getRingtone(baseContext,alarmUri)
        r.play()
    }

    override fun onDestroy() {
        super.onDestroy()
        this.isruning=false
    }
}