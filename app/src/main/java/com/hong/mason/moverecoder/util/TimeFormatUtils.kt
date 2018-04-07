package com.hong.mason.moverecoder.util

class TimeFormatUtils {
    companion object {
        private const val ONE_SECOND = 1000L
        private const val ONE_MINUTE = ONE_SECOND * 60
        private const val ONE_HOUR = ONE_MINUTE * 60
        fun getDurationString(duration: Long): String {
            var value = duration
            val hour = value / ONE_HOUR
            value %= ONE_HOUR
            val minute = value / ONE_MINUTE
            value %= ONE_MINUTE
            val second = value / ONE_SECOND

            val stringBuilder = StringBuilder()
            if (hour > 0) {
                stringBuilder.append("$hour 시간")
            }
            if (minute > 0) {
                if (stringBuilder.isNotEmpty()) {
                    stringBuilder.append(" ")
                }
                stringBuilder.append("$minute 분")
            }
            if (second > 0) {
                if (stringBuilder.isNotEmpty()) {
                    stringBuilder.append(" ")
                }
                stringBuilder.append("$second 초")
            }
            return stringBuilder.toString()
        }
    }
}