package app.dfeverx.learningpartner.utils

import android.app.AlarmManager
import android.os.Build

fun AlarmManager.hasPermission(): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        this.canScheduleExactAlarms()
    } else {
        true
    }

}