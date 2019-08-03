package com.example.alarmmanager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class alarmreciver:BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {

        var getresult:String=p1!!.getStringExtra("extra")

        var service_Intent:Intent= Intent(p0,ringtoneservice::class.java)
        service_Intent.putExtra("extra",getresult)
        p0!!.startService(service_Intent)
    }

}