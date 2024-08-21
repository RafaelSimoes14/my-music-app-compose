package com.example.mymusicappcompose.log

import android.util.Log

const val TAG = "APP"
fun logInfo(message: String) = Log.i(TAG, message)
fun logDebug(message: String) = Log.d(TAG, message)
fun logError(message: String) = Log.e(TAG, message)
