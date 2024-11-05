package com.example.a040_systembr

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsMessage
import android.util.Log
import android.widget.Toast
import java.util.Objects

class TestReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // BR 를 동작시키기 위해서 사용한 이름으로 분기한다.
        when (intent.action) {
            // 부팅이 완료되었을 때
            // AndroidManifest.xml
            //             <intent-filter>
            //                <action android:name="android.intent.action.BOOT_COMPLETED"/>
            //            </intent-filter>
            "android.intent.action.BOOT_COMPLETED" -> {
               Log.d("test100","Boot Completed")
            }
            // 문자 메시지가 수신되었을 때
            // AndroidManifest.xml
            //             <intent-filter>
            //                <action android:name="android.provider.Telephony.SMS_RECEIVED"/>
            //            </intent-filter>
            "android.provider.Telephony.SMS_RECEIVED" -> {
                // 문자 정보를 가지고 있는 Bundle 객체가 있는지 확인한다.
                if(intent.extras != null){
                    // 문자 메시지 정보를 추출힌다.
                    val obj = intent.extras?.get("pdus") as Array<*>
                    // 문자 메시지 양식 객체를 추출한다.
                    val format = intent.extras?.getString("format")

                    // 문자 메시지 수 만큼 반복한다.
                    obj.forEach {
                        // 문자 메시지 객체로 변환한다.
                        val currentSms = SmsMessage.createFromPdu(it as ByteArray, format)
                        Log.d("test100", "전화번호 : ${currentSms.displayOriginatingAddress}")
                        Log.d("test100", "내용 : ${currentSms.displayMessageBody}")
                    }
                }
            }
        }
    }
}
