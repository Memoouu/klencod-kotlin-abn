
package com.ibndulaim.taskrecorder

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private var counter = 0
    private var isLooping = false
    private val quotes = listOf(
        "القوة الحقيقية في أن تعمل مرة وتستفيد ألف مرة - ابن دليم @ss66s1",
        "لا تتبع القطيع، اصنع طريقك والآلة تتبعك - ابن دليم",
        "البساطة هي قمة الاحتراف - ابن دليم",
        "من يتقن التكرار، يتقن النجاح - ابن دليم",
        "دع أفعالك تتكلم، ودع الآلة تكررها - ابن دليم"
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        findViewById<TextView>(R.id.quoteText).text = quotes.random()
        findViewById<Button>(R.id.btnEnableAccess).setOnClickListener {
            startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
        }
        findViewById<Button>(R.id.btnRecord).setOnClickListener {
            MacroAccessibilityService.isRecording = true
        }
        findViewById<Button>(R.id.btnStopRecord).setOnClickListener {
            MacroAccessibilityService.isRecording = false
            // حفظ الماكرو
        }
        findViewById<Button>(R.id.btnPlayLoop).setOnClickListener {
            isLooping = true
            startService(Intent(this, FloatingStopService::class.java))
            startLoop()
        }
        findViewById<Button>(R.id.btnStopLoop).setOnClickListener {
            stopLoop()
        }
    }
    private fun startLoop() {
        Thread {
            while(isLooping) {
                counter++
                runOnUiThread {
                    findViewById<TextView>(R.id.counterText).text = "$counter"
                    findViewById<TextView>(R.id.counterLabel).text = "مرة دزلك حلو - @ss66s1"
                }
                // هنا ينفذ الماكرو المسجل مع Smart Wait
                // waitForText("متابعة") بدل sleep
                Thread.sleep(1500)
            }
        }.start()
    }
    private fun stopLoop() {
        isLooping = false
        stopService(Intent(this, FloatingStopService::class.java))
    }
}