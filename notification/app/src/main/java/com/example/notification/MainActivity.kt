package com.example.notification

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.notification.ui.theme.NotificationTheme

class MainActivity : ComponentActivity() {

    // Notification Channel ID
    private val CHANNEL_ID = "example_notification_channel"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Create notification channel
        createNotificationChannel()

        // Request permission for Android 13+ if needed
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(
                    this@MainActivity,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }

        setContent {
            NotificationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        Greeting(name = "Ajay")
                        NotifyButton(onNotifyButtonClicked = {
                            showRegularNotification()
                        })
                        HeadsUpNotifyButton(onHeadsUpNotifyButtonClicked = {
                            showHeadsUpNotification()
                        })
                        LockScreenButton(onLockScreenButtonClicked = {
                            showLockScreenNotification()
                        })
                    }
                }
            }
        }
    }

    // Request permission launcher for notifications (Android 13+)
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (!isGranted) {
                // Handle if the user denies notification permission
            }
        }

    // Method to show regular notification
    private fun showRegularNotification() {
        // Intent to open MainActivity when notification is clicked
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        // PendingIntent for the notification click
        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        // Build the notification
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("Hi Ajay")
            .setContentText("How are you??")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)  // Regular priority
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        // Show the notification using NotificationManagerCompat
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        NotificationManagerCompat.from(this).notify(1, builder.build())
    }

    // Method to show heads-up (interruptive) notification like WhatsApp/Instagram
    private fun showHeadsUpNotification() {
        // Intent to open MainActivity when notification is clicked
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        // PendingIntent for the notification click
        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        // Build the heads-up notification
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_alert) // Icon for heads-up
            .setContentTitle("Heads-up Notification")
            .setContentText("This is a Heads-up Notification similar to WhatsApp!")
            .setPriority(NotificationCompat.PRIORITY_HIGH)  // Heads-up priority
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)  // Message category to make it similar to chat apps
            .setStyle(NotificationCompat.BigTextStyle().bigText("Hi Ajay! You got a new message"))
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        // Show the notification using NotificationManagerCompat
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        NotificationManagerCompat.from(this).notify(2, builder.build())
    }

    // Method to show a lock screen notification
    private fun showLockScreenNotification() {
        // Intent to open MainActivity when notification is clicked
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        // PendingIntent for the notification click
        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        // Build the lock screen notification
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_lock_idle_lock) // Icon for lock screen notification
            .setContentTitle("Locked Message")
            .setContentText("Can't show you locked message.")
            .setPriority(NotificationCompat.PRIORITY_HIGH)  // High priority
            .setVisibility(NotificationCompat.VISIBILITY_PRIVATE) // Visibility set to private
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        // Show the notification using NotificationManagerCompat
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        NotificationManagerCompat.from(this).notify(3, builder.build())
    }

    // Create a notification channel (required for Android 8.0 and above)
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "High Priority Notification Channel"
            val descriptionText = "This channel is for high-priority notifications"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
                lockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC // Ensuring visibility on the lock screen
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(text = "Hello $name!", modifier = modifier)
}

@Composable
fun NotifyButton(onNotifyButtonClicked: () -> Unit) {
    Button(onClick = onNotifyButtonClicked, modifier = Modifier.padding(top = 16.dp)) {
        Text("Notify")
    }
}

@Composable
fun HeadsUpNotifyButton(onHeadsUpNotifyButtonClicked: () -> Unit) {
    Button(onClick = onHeadsUpNotifyButtonClicked, modifier = Modifier.padding(top = 16.dp)) {
        Text("Heads-up Notify")
    }
}

@Composable
fun LockScreenButton(onLockScreenButtonClicked: () -> Unit) {
    Button(onClick = onLockScreenButtonClicked, modifier = Modifier.padding(top = 16.dp)) {
        Text("Lock Screen Notify")
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NotificationTheme {
        Column {
            Greeting("Ajay")
            NotifyButton(onNotifyButtonClicked = {})
            HeadsUpNotifyButton(onHeadsUpNotifyButtonClicked = {})
            LockScreenButton(onLockScreenButtonClicked = {})
        }
    }
}
