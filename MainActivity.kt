package com.blankkeys.keyboard
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Gravity
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.graphics.Color

class MainActivity : Activity() {
 override fun onCreate(savedInstanceState: Bundle?) {
  super.onCreate(savedInstanceState)
  val root = LinearLayout(this).apply { orientation=1; gravity=Gravity.CENTER; setPadding(32,32,32,32); setBackgroundColor(Color.rgb(12,12,18)) }
  root.addView(TextView(this).apply { text="BLANK KEYS"; textSize=28f; gravity=Gravity.CENTER; setTextColor(Color.rgb(90,245,220)) })
  root.addView(TextView(this).apply { text="Blank keycaps. Normal typing."; textSize=16f; gravity=Gravity.CENTER; setTextColor(Color.WHITE); setPadding(0,24,0,24) })
  root.addView(Button(this).apply { text="1. Enable Blank Keys"; setOnClickListener { startActivity(Intent(Settings.ACTION_INPUT_METHOD_SETTINGS)) } })
  root.addView(Button(this).apply { text="2. Choose Blank Keys"; setOnClickListener { (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).showInputMethodPicker() } })
  setContentView(root)
 }
}
