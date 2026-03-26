package com.carlosdmg.ecoclinic.app.presentation

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.net.toUri
import com.carlosdmg.ecoclinic.R

class NavIntent (private val context: Context) {

    fun openPhoneDial(phoneNumber: Int) {
        try {
            val parsedPhoneNumber = "tel:${context.getString(phoneNumber)}".toUri()
            val intent = Intent(Intent.ACTION_DIAL, parsedPhoneNumber).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            Log.d("@dev", context.getString(R.string.app_intent_error) + e)
        }
    }

}