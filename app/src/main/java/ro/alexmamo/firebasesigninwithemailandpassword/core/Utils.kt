package ro.alexmamo.firebasesigninwithemailandpassword.core

import android.content.Context
import android.util.Log
import android.widget.Toast.LENGTH_LONG
import android.widget.Toast.makeText

const val TAG = "AppTag"
const val EMPTY_STRING = ""

fun logMessage(
    message: String
) = Log.e(TAG, message)

fun showToastMessage(
    context: Context,
    message: String
) = makeText(context, message, LENGTH_LONG).show()