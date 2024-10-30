package ro.alexmamo.firebasesigninwithemailandpassword.core

import android.content.Context
import android.util.Log
import android.widget.Toast.LENGTH_LONG
import android.widget.Toast.makeText
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ro.alexmamo.firebasesigninwithemailandpassword.core.Constants.REVOKE_ACCESS_MESSAGE
import ro.alexmamo.firebasesigninwithemailandpassword.core.Constants.SIGN_OUT_ITEM
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