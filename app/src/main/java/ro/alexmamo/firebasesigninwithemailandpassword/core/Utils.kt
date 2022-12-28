package ro.alexmamo.firebasesigninwithemailandpassword.core

import android.util.Log
import ro.alexmamo.firebasesigninwithemailandpassword.core.Constants.TAG

class Utils {
    companion object {
        fun print(e: Exception) {
            Log.e(TAG, e.stackTraceToString())
        }
    }
}