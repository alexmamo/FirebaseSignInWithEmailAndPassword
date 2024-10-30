package ro.alexmamo.firebasesigninwithemailandpassword.core

import android.content.Context
import android.util.Log
import android.widget.Toast.LENGTH_LONG
import android.widget.Toast.makeText
import ro.alexmamo.firebasesigninwithemailandpassword.core.Constants.TAG

fun printError(
    e: Exception
) = Log.e(TAG, "${e.message}")

fun toastError(
    context: Context,
    e: Exception
) = makeText(context, "${e.message}", LENGTH_LONG).show()

fun toastMessage(
    context: Context,
    message: String?
) = makeText(context, "$message", LENGTH_LONG).show()