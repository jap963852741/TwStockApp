package com.jap.twstockapp.util.dialog

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import com.jap.twstockapp.R

class MyAutoCompleteTextView: androidx.appcompat.widget.AppCompatAutoCompleteTextView {
    private val TAG = "MyAutoCompleteTextView"

    constructor(context: Context) : this(context, null)

    constructor(context: Context,attrs: AttributeSet?) : this(context, attrs, R.attr.autoCompleteTextViewStyle)

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr)



    override fun setText(text: CharSequence?, filter: Boolean) {
        Log.e(TAG,"setText")
        super.setText(text, filter)
    }

    override fun replaceText(text: CharSequence?) {
        Log.e(TAG,"replaceText")
        var number = text!!.split(" ")[0]
        super.replaceText(number)
    }
}