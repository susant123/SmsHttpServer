package com.example.smshttpserver

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.telephony.SmsManager
import androidx.core.content.ContextCompat
import fi.iki.elonen.NanoHTTPD
import org.json.JSONObject

class SmsHttpServer(private val context: Context, port: Int) : NanoHTTPD(port) {
    override fun serve(session: IHTTPSession): Response {
        if (session.method == Method.POST) {
            val map = HashMap<String, String>()
            session.parseBody(map)
            val postData = map["postData"]
            val json = JSONObject(postData)
            val phoneNumber = json.getString("to")
            val message = json.getString("message")

            sendSMS(phoneNumber, message)
            makeCall(phoneNumber)

            return newFixedLengthResponse("SMS Sent and Call Initiated")
        }
        return newFixedLengthResponse("Only POST allowed")
    }

    private fun sendSMS(phoneNumber: String, message: String) {
        val smsManager = SmsManager.getDefault()
        smsManager.sendTextMessage(phoneNumber, null, message, null, null)
    }

    private fun makeCall(phoneNumber: String) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            val callIntent = Intent(Intent.ACTION_CALL).apply {
                data = Uri.parse("tel:$phoneNumber")
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(callIntent)
        }
    }
}
