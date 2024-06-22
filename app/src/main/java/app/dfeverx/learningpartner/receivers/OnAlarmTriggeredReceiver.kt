package app.dfeverx.learningpartner.receivers

import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getString
import androidx.core.net.toUri
import app.dfeverx.learningpartner.ui.main.MainActivity
import app.dfeverx.learningpartner.R

class OnAlarmTriggeredReceiver : BroadcastReceiver() {
    private val TAG = "OnNotificationReceiver"

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d(TAG, "onReceive: alarm triggered $context")
        context?.let {
            val noteId = intent?.getStringExtra("noteId")
            val taskDetailIntent = Intent(
                Intent.ACTION_VIEW,
                "ninaiva://app/noteId=${noteId}".toUri(),
                context,
                MainActivity::class.java
            )

            val pending: PendingIntent = TaskStackBuilder.create(context).run {
                addNextIntentWithParentStack(taskDetailIntent)
                getPendingIntent(1, PendingIntent.FLAG_UPDATE_CURRENT)
            }

            val builder = NotificationCompat.Builder(
                it,
                getString(context, R.string.scheduled_notification_channel_id)
            )
                .setContentIntent(pending)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Alarm")

                .setContentText("Your alarm is ringing!")
                .setPriority(NotificationCompat.PRIORITY_HIGH)

            with(NotificationManagerCompat.from(it)) {
                notify(123, builder.build())
            }
        }
    }


}