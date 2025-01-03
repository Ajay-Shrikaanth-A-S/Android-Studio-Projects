package com.example.notification

import android.app.Service
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.TextView

class OverlayService : Service() {

    private lateinit var windowManager: WindowManager
    private lateinit var overlayView: View

    override fun onCreate() {
        super.onCreate()

        // Inflate the overlay layout
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        overlayView = LayoutInflater.from(this).inflate(R.layout.overlay_layout, null)

        // Set the layout parameters for the overlay
        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            else WindowManager.LayoutParams.TYPE_PHONE,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        )

        // Set the gravity and position of the overlay
        params.gravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL
        params.x = 0
        params.y = 100

        // Add the view to the window
        windowManager.addView(overlayView, params)

        // Set a click listener to remove the overlay when clicked
        overlayView.setOnClickListener {
            stopSelf() // Stop the service
        }

        // Set the message to show (e.g., OTP or alert message)
        val messageTextView: TextView = overlayView.findViewById(R.id.overlay_message)
        messageTextView.text = "Heads Up! This is an overlay message." // Change this message as needed
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::windowManager.isInitialized) {
            windowManager.removeView(overlayView) // Remove the overlay view when service is destroyed
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null // We don't provide binding
    }
}
