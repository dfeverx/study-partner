package app.dfeverx.learningpartner.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class OnBootCompletedReceiver : BroadcastReceiver() {
    private val TAG = "BootCompletedReceiver"
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            // Reschedule alarms here if necessary
            Log.d(TAG, "onReceive: Boot completed")
        }
    }
}