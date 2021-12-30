package com.uyghar.firebasedemo.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.uyghar.firebasedemo.R
import java.io.IOException
import java.net.URL
//isim:String, token: String
class MyFirebaseMessagingService: FirebaseMessagingService() {
    private val CHANNEL_ID = "TEST"
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        //token bilen isim ni servergha yollaymiz
        val prefrences = applicationContext.getSharedPreferences("firebase", Context.MODE_PRIVATE)
        val editor = prefrences.edit()
        editor.putString("token",token)
        editor.apply()
        editor.commit()
        Log.i("firebase_token", token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.i("firebase_message", message.notification?.body.toString())
        showNotification(message.notification?.title, message.notification?.body, 0)
    }


    fun showNotification(title: String?, body: String?, id: Int) {
        createNotificationChannel()
        //val intent = Intent(this, NotificationDetail::class.java)
        //intent.putExtra("id", user.id)
        //val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_ONE_SHOT)
        var builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_menu_camera)
            .setContentTitle(title)
            .setContentText(body)
//            .setStyle(NotificationCompat.BigTextStyle()
//                .bigText("Bu sinaq tekst"))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            //.setContentIntent(pendingIntent)
        with(NotificationManagerCompat.from(this)) {
            // notificationId is a unique int for each notification that you must define
            notify(1, builder.build())
        }
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Qanal1"
            val descriptionText = "Izahat1"
            val importance = NotificationManager.IMPORTANCE_DEFAULT

            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }


}