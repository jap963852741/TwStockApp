package com.jap.twStockApp.util.dialog

import android.content.Context
import android.util.AttributeSet
import com.jap.twStockApp.R

class MyAutoCompleteTextView : androidx.appcompat.widget.AppCompatAutoCompleteTextView {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, R.attr.autoCompleteTextViewStyle)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun replaceText(text: CharSequence?) = super.replaceText(text!!.split(" ")[0])
}
