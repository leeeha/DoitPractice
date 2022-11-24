package com.tutorial.ch10_notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.RemoteInput
import com.tutorial.ch10_notification.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.notificationButton.setOnClickListener {
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            val builder = createNotificationBuilder(notificationManager)
            setNotification(builder)

            val replyPendingIntent = createReplyPendingIntent()
            val remoteInput = createRemoteInput()
            val action = createNotificationAction(replyPendingIntent, remoteInput)
            builder.addAction(action)

            notificationManager.notify(NOTIFICATION_ID, builder.build())
        }
    }

    private fun createNotificationBuilder(notificationManager: NotificationManager): NotificationCompat.Builder {
        val builder: NotificationCompat.Builder

        // API Level 26 이상인 경우 채널 사용
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                getString(R.string.channel_name),
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = getString(R.string.channel_desc)
                setShowBadge(true)
                val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                val audioAttributes = AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_ALARM)
                    .build()
                setSound(uri, audioAttributes)
                enableVibration(true)
            }
            notificationManager.createNotificationChannel(channel)
            builder = NotificationCompat.Builder(this, CHANNEL_ID)
        } else {
            builder = NotificationCompat.Builder(this)
        }

        return builder
    }

    private fun setNotification(builder: NotificationCompat.Builder) {
        builder.run {
            setSmallIcon(R.drawable.small)
            setWhen(System.currentTimeMillis())
            setContentTitle(getString(R.string.noti_content_title))
            setContentText(getString(R.string.noti_content_text))
            setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.gdsc_mark))
        }
    }

    private fun createReplyPendingIntent(): PendingIntent {
        val replyIntent = Intent(this, ReplyReceiver::class.java)
        return PendingIntent.getBroadcast(
            this,
            30,
            replyIntent,
            PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    private fun createRemoteInput(): RemoteInput {
        val remoteInput = RemoteInput.Builder(KEY_TEXT_REPLY).run {
            setLabel(getString(R.string.noti_reply_label))
            build()
        }
        return remoteInput
    }

    private fun createNotificationAction(
        replyPendingIntent: PendingIntent,
        remoteInput: RemoteInput
    ): NotificationCompat.Action {
        return NotificationCompat.Action.Builder(
            R.drawable.send,
            getString(R.string.noti_action_title),
            replyPendingIntent
        ).addRemoteInput(remoteInput).build()
    }
}