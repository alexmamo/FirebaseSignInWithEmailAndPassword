package ro.alexmamo.firebasesigninwithemailandpassword.core

import android.content.Context
import android.util.Log
import android.widget.Toast

const val TAG = "AppTag"
const val EMPTY_STRING = ""

fun logErrorMessage(
    errorMessage: String
) = Log.e(TAG, errorMessage)

fun showToastMessage(
    context: Context,
    message: String
) = Toast.makeText(context, message, Toast.LENGTH_LONG).show()