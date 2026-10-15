package com.blankkeys.keyboard
import android.inputmethodservice.InputMethodService
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.LinearLayout

class BlankKeyboardService : InputMethodService() {
 private var shift=false
 private var symbols=false
 private val letters=arrayOf(arrayOf("q","w","e","r","t","y","u","i","o","p"),arrayOf("a","s","d","f","g","h","j","k","l"),arrayOf("SHIFT","z","x","c","v","b","n","m","BACK"),arrayOf("?123",",","SPACE",".","ENTER"))
 private val nums=arrayOf(arrayOf("1","2","3","4","5","6","7","8","9","0"),arrayOf("@","#","$","%","&","-","+","(",")"),arrayOf("=/<","*",""",":",";","!","?","BACK"),arrayOf("ABC",",","SPACE",".","ENTER"))
 override fun onCreateInputView()=makeKeyboard()
 private fun dp(n:Int)=(n*resources.displayMetrics.density).toInt()
 private fun makeKeyboard():LinearLayout {
  val root=LinearLayout(this).apply { orientation=1; setPadding(dp(4),dp(4),dp(4),dp(6)); setBackgroundColor(Color.rgb(14,14,21)) }
  (if(symbols) nums else letters).forEach { row ->
   val line=LinearLayout(this).apply { orientation=0; gravity=Gravity.CENTER }
   row.forEach { k ->
    val b=Button(this).apply {
     text=""; contentDescription=when(k){"SPACE"->"Space";"BACK"->"Backspace";"ENTER"->"Enter";"SHIFT"->"Shift";else->"Blank key"}
     setTextColor(Color.TRANSPARENT); textSize=1f; isAllCaps=false; minWidth=0; minimumWidth=0; minHeight=dp(42); setPadding(0,0,0,0)
     background=GradientDrawable().apply { cornerRadius=dp(7).toFloat(); setColor(if(k in listOf("SHIFT","BACK","ENTER","?123","ABC","=/<","SPACE")) Color.rgb(44,47,62) else Color.rgb(35,36,46)); setStroke(dp(1),Color.rgb(54,58,74)) }
     setOnClickListener { key(k) }
    }
    val w=if(k=="SPACE") 5f else if(k in listOf("SHIFT","BACK","ENTER","?123","ABC","=/<")) 1.5f else 1f
    line.addView(b,LinearLayout.LayoutParams(0,dp(47),w).apply { setMargins(dp(2),dp(3),dp(2),dp(3)) })
   }
   root.addView(line,LinearLayout.LayoutParams(-1,-2))
  }
  return root
 }
 private fun key(k:String) {
  val ic=currentInputConnection ?: return
  when(k) {
   "SPACE"->ic.commitText(" ",1)
   "BACK"->{ ic.deleteSurroundingText(1,0) }
   "ENTER"->{ val a=currentInputEditorInfo?.imeOptions?.and(EditorInfo.IME_MASK_ACTION); if(a!=null && a!=EditorInfo.IME_ACTION_NONE && a!=EditorInfo.IME_ACTION_UNSPECIFIED) ic.performEditorAction(a) else { ic.sendKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN,KeyEvent.KEYCODE_ENTER)); ic.sendKeyEvent(KeyEvent(KeyEvent.ACTION_UP,KeyEvent.KEYCODE_ENTER)) } }
   "SHIFT"->{shift=!shift; setInputView(makeKeyboard())}
   "?123","=/<"->{symbols=true; setInputView(makeKeyboard())}
   "ABC"->{symbols=false; setInputView(makeKeyboard())}
   ","->ic.commitText(",",1)
   "."->ic.commitText(".",1)
   else->ic.commitText(if(shift) k.uppercase() else k,1)
  }
 }
}
