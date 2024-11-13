package ro.alexmamo.firebasesigninwithemailandpassword.core

import android.content.Context
import android.util.Log
import android.widget.Toast.LENGTH_LONG
import android.widget.Toast.makeText
import ro.alexmamo.firebasesigninwithemailandpassword.domain.model.Response

const val TAG = "AppTag"
const val EMPTY_STRING = ""

fun printError(e: Exception) = Log.e(TAG, "${e.message}")

fun showToastError(
    context: Context,
    e: Exception
) = makeText(context, "${e.message}", LENGTH_LONG).show()

fun showToastMessage(
    context: Context,
    resourceId: Int
) = makeText(context, context.resources.getString(resourceId), LENGTH_LONG).show()

suspend fun <T> launchCatching(block: suspend () -> T) = try {
    Response.Success(block())
} catch (e: Exception) {
    Response.Failure(e)
}