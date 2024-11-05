package com.example.a039_broadcastreciever

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class TestReciever : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        val toast = Toast.makeText(context, "testReciever가 동작",Toast.LENGTH_SHORT)
        toast.show()
    }
}