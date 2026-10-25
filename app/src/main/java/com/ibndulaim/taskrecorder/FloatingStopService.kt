
package com.ibndulaim.taskrecorder

import android.app.*
import android.content.Intent
import android.os.IBinder
import android.view.*
import android.widget.Button

class FloatingStopService : Service() {
    private var windowManager: WindowManager? = null
    private var floatingView: View? = null
    override fun onBind(intent: Intent?): IBinder? = null
    override fun onCreate() {
        super.onCreate()
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        floatingView = Button(this).apply {
            text = "إيقاف - @ss66s1 \n 0 مرة"
            setBackgroundColor(0xFFFF0000.toInt())
            setOnClickListener {
                // إيقاف الحلقة
                stopSelf()
            }
        }
        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, android.graphics.PixelFormat.TRANSLUCENT
        )
        params.gravity = Gravity.TOP or Gravity.CENTER
        windowManager?.addView(floatingView, params)
    }
    override fun onDestroy() {
        super.onDestroy()
        floatingView?.let { windowManager?.removeView(it) }
    }
}
