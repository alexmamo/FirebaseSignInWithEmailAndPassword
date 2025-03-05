package ro.alexmamo.firebasesigninwithemailandpassword.core

import android.content.Context
import android.util.Log
import android.widget.Toast

const val TAG = "AppTag"
const val EMPTY_STRING = ""

fun logMessage(
    message: String
) = Log.e(TAG, message)

fun showToastMessage(
    context: Context,
    message: String
) = Toast.makeText(context, message, Toast.LENGTH_LONG).show()