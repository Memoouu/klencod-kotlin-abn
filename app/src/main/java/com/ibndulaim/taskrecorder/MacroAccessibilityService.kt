
package com.ibndulaim.taskrecorder

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.graphics.Path
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo

class MacroAccessibilityService : AccessibilityService() {
    companion object {
        var isRecording = false
        var recordedSteps = mutableListOf<MacroStep>()
        var instance: MacroAccessibilityService? = null
    }
    data class MacroStep(val type: String, val text: String, val x: Float, val y: Float)
    
    override fun onServiceConnected() { instance = this }
    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if(!isRecording) return
        // تسجيل ذكي: يحفظ النص مو بس الاحداثيات
        val node = event?.source ?: return
        val text = node.text?.toString() ?: node.contentDescription?.toString() ?: ""
        if(text.isNotEmpty()) {
            recordedSteps.add(MacroStep("SMART_CLICK_TEXT", text, 0f, 0f))
        }
    }
    override fun onInterrupt() {}
    
    fun smartClickOnText(targetText: String): Boolean {
        val root = rootInActiveWindow ?: return false
        val nodes = root.findAccessibilityNodeInfosByText(targetText)
        for(node in nodes) {
            if(node.isClickable) { node.performAction(AccessibilityNodeInfo.ACTION_CLICK); return true }
            // Smart Wait - ينتظر حتى يلقى النص
        }
        return false
    }
    fun performClick(x: Float, y: Float) {
        val path = Path(); path.moveTo(x,y)
        val gesture = GestureDescription.Builder().addStroke(GestureDescription.StrokeDescription(path,0,100)).build()
        dispatchGesture(gesture,null,null)
    }
}
