package com.jap.twstockapp.util.dialog

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.res.Resources.Theme
import android.content.res.TypedArray
import android.database.DataSetObserver
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import android.widget.SpinnerAdapter
import android.widget.ThemedSpinnerAdapter
import androidx.appcompat.R
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.view.ContextThemeWrapper
import androidx.appcompat.widget.AppCompatSpinner
import androidx.appcompat.widget.ThemeUtils
import androidx.appcompat.widget.TintTypedArray

class MySpinner : AppCompatSpinner {
    private var mPopup: SpinnerPopup? = null
    private val TAG = "MySpinner"
    private val mPopupContext: Context
    private var MODE_THEME = -1
    private val ATTRS_ANDROID_SPINNERMODE = intArrayOf(android.R.attr.spinnerMode)
    private val MODE_DIALOG = 0
    private var mPopupSet = false
    private var mTempAdapter: SpinnerAdapter? = null
    private var next_mode: Int = 0

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, R.attr.spinnerStyle)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs, defStyleAttr, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, mode: Int) : this(context, attrs, defStyleAttr, mode, null)

    @SuppressLint("RestrictedApi")
    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        mode: Int,
        popupTheme: Theme?
    ) : super(context, attrs, defStyleAttr, mode, popupTheme) {

        ThemeUtils.checkAppCompatTheme(this, getContext())

        val a = TintTypedArray.obtainStyledAttributes(
            context, attrs,
            R.styleable.Spinner, defStyleAttr, 0
        )

        mPopupContext = if (popupTheme != null) {
            ContextThemeWrapper(context, popupTheme)
        } else {
            val popupThemeResId = a.getResourceId(R.styleable.Spinner_popupTheme, 0)
            if (popupThemeResId != 0) {
                ContextThemeWrapper(context, popupThemeResId)
            } else {
                context
            }
        }

        if (mode == MODE_THEME) {
            var aa: TypedArray? = null
            try {
                aa = context.obtainStyledAttributes(
                    attrs, ATTRS_ANDROID_SPINNERMODE,
                    defStyleAttr, 0
                )
                if (aa.hasValue(0)) {
                    next_mode = aa.getInt(0, MODE_DIALOG)
                }
            } catch (e: Exception) {
                Log.i(TAG, "Could not read android:spinnerMode", e)
            } finally {
                aa?.recycle()
            }
        }
        when (next_mode) {
            MODE_DIALOG -> {
                mPopup = this.DialogPopup()
                (mPopup as DialogPopup).setPromptText(a.getString(R.styleable.Spinner_android_prompt))
            }
        }

        val entries =
            a.getTextArray(R.styleable.Spinner_android_entries)
        if (entries != null) {
            val adapter = ArrayAdapter(
                context, android.R.layout.simple_spinner_item, entries
            )
            adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
            setAdapter(adapter)
        }

        a.recycle()

        mPopupSet = true

        // Base constructors can call setAdapter before we initialize mPopup.
        // Finish setting things up if this happened.

        // Base constructors can call setAdapter before we initialize mPopup.
        // Finish setting things up if this happened.
        if (mTempAdapter != null) {
            adapter = mTempAdapter
            mTempAdapter = null
        }
//        mPopup = this.DialogPopup()
//        (mPopup as DialogPopup).setPromptText(a.getString(R.styleable.Spinner_android_prompt))
    }

    override fun setAdapter(adapter: SpinnerAdapter?) {
        // The super constructor may call setAdapter before we're prepared.
        // Postpone doing anything until we've finished construction.

        // The super constructor may call setAdapter before we're prepared.
        // Postpone doing anything until we've finished construction.
        if (!mPopupSet) {
            mTempAdapter = adapter
            return
        }

        super.setAdapter(adapter)

        if (mPopup != null) {
            val popupContext =
                mPopupContext ?: context
            mPopup!!.setAdapter(this.DropDownAdapter(adapter, popupContext.theme))
        }
    }

    interface SpinnerPopup {
        fun setAdapter(adapter: ListAdapter?)

        /**
         * Show the popup
         */
        fun show(textDirection: Int, textAlignment: Int)

        /**
         * Dismiss the popup
         */
        fun dismiss()

        /**
         * @return true if the popup is showing, false otherwise.
         */
        val isShowing: Boolean

        /**
         * Set hint text to be displayed to the user. This should provide
         * a description of the choice being made.
         * @param hintText Hint text to set.
         */
        fun setPromptText(hintText: CharSequence?)
        val hintText: CharSequence?

        fun setBackgroundDrawable(bg: Drawable?)

        var horizontalOriginalOffset: Int
        val background: Drawable?
        var verticalOffset: Int
        var horizontalOffset: Int
    }

    inner class DialogPopup : SpinnerPopup, DialogInterface.OnClickListener {
        var mPopup: AlertDialog? = null
        private var mListAdapter: ListAdapter? = null
        override var hintText: CharSequence? = null

        override fun dismiss() {
            if (mPopup != null) {
                mPopup!!.dismiss()
                mPopup = null
            }
        }

        override val isShowing: Boolean
            get() = if (mPopup != null) mPopup!!.isShowing else false

        override fun setAdapter(adapter: ListAdapter?) {
            mListAdapter = adapter
        }

        override fun setPromptText(hintText: CharSequence?) {
            this.hintText = hintText
        }

        override fun show(textDirection: Int, textAlignment: Int) {

            if (mListAdapter == null) {
                return
            }
            val builder = AlertDialog.Builder(mPopupContext)

            if (hintText != null) {
                builder.setTitle(hintText)
            }

            mPopup = builder.setSingleChoiceItems(
                mListAdapter,
                selectedItemPosition, this
            ).create()

            // 改寫 原 spinner alertdialog 可關閉
            mPopup!!.setCancelable(false)
            mPopup!!.setCanceledOnTouchOutside(false)

            mPopup!!.show()
        }

        override fun onClick(dialog: DialogInterface, which: Int) {
            setSelection(which)
            if (onItemClickListener != null) {
                performItemClick(null, which, mListAdapter!!.getItemId(which))
            }
            dismiss()
        }
        override fun setBackgroundDrawable(bg: Drawable?) {
            Log.e(
                TAG,
                "Cannot set popup background for MODE_DIALOG, ignoring"
            )
        }
        override val background: Drawable? = null
        override var verticalOffset: Int
            get() = 0
            set(px) {
                Log.e(
                    TAG,
                    "Cannot set vertical offset for MODE_DIALOG, ignoring"
                )
            }
        override var horizontalOffset: Int
            get() = 0
            set(px) {
                Log.e(
                    TAG,
                    "Cannot set horizontal offset for MODE_DIALOG, ignoring"
                )
            }
        override var horizontalOriginalOffset: Int
            get() = 0
            set(px) {
                Log.e(
                    TAG,
                    "Cannot set horizontal (original) offset for MODE_DIALOG, ignoring"
                )
            }
    }

    override fun performClick(): Boolean {
        if (mPopup != null) {
            // If we have a popup, show it if needed, or just consume the click...
            if (!mPopup!!.isShowing) {
                this.showPopup()
            }
            return true
        }
        // Else let the platform handle the click
        return super.performClick()
    }

    private fun showPopup() {
        mPopup!!.show(-1, -1)
    }

    /**
     * <p>Wrapper
     class for an Adapter. Transforms the embedded Adapter instance
     * into a ListAdapter.</p>
     */
    inner class DropDownAdapter : ListAdapter, SpinnerAdapter {

        var mAdapter: SpinnerAdapter? = null

        var mListAdapter: ListAdapter? = null

        /**
         * Creates a new ListAdapter wrapper for the specified adapter.
         *
         * @param adapter       the SpinnerAdapter to transform into a ListAdapter
         * @param dropDownTheme the theme against which to inflate drop-down
         * views, may be {@null} to use default theme
         */
        constructor(
            adapter: SpinnerAdapter?,
            dropDownTheme: Theme?
        ) {
            mAdapter = adapter
            if (adapter is ListAdapter) {
                mListAdapter = adapter
            }
            if (dropDownTheme != null) {
                if (adapter is ThemedSpinnerAdapter) {
                    val themedAdapter = adapter
                    if (themedAdapter.dropDownViewTheme != dropDownTheme) {
                        themedAdapter.dropDownViewTheme = dropDownTheme
                    }
                } else if (adapter is androidx.appcompat.widget.ThemedSpinnerAdapter) {
                    val themedAdapter = adapter
                    if (themedAdapter.dropDownViewTheme == null) {
                        themedAdapter.dropDownViewTheme = dropDownTheme
                    }
                }
            }
        }

        override fun getCount(): Int {
            return if (mAdapter == null) 0 else mAdapter!!.count
        }

        override fun getItem(position: Int): Any? {
            return if (mAdapter == null) null else mAdapter!!.getItem(position)
        }

        override fun getItemId(position: Int): Long {
            return if (mAdapter == null) -1 else mAdapter!!.getItemId(position)
        }

        override fun getView(
            position: Int,
            convertView: View?,
            parent: ViewGroup?
        ): View? {
            return getDropDownView(position, convertView, parent)
        }

        override fun getDropDownView(
            position: Int,
            convertView: View?,
            parent: ViewGroup?
        ): View? {
            return if (mAdapter == null) null else mAdapter!!.getDropDownView(
                position,
                convertView,
                parent
            )
        }

        override fun hasStableIds(): Boolean {
            return mAdapter != null && mAdapter!!.hasStableIds()
        }

        override fun registerDataSetObserver(observer: DataSetObserver?) {
            if (mAdapter != null) {
                mAdapter!!.registerDataSetObserver(observer)
            }
        }

        override fun unregisterDataSetObserver(observer: DataSetObserver?) {
            if (mAdapter != null) {
                mAdapter!!.unregisterDataSetObserver(observer)
            }
        }

        /**
         * If the wrapped SpinnerAdapter is also a ListAdapter, delegate this call.
         * Otherwise, return true.
         */
        override fun areAllItemsEnabled(): Boolean {
            val adapter = mListAdapter
            return adapter?.areAllItemsEnabled() ?: true
        }

        /**
         * If the wrapped SpinnerAdapter is also a ListAdapter, delegate this call.
         * Otherwise, return true.
         */
        override fun isEnabled(position: Int): Boolean {
            val adapter = mListAdapter
            return adapter?.isEnabled(position) ?: true
        }

        override fun getItemViewType(position: Int): Int {
            return 0
        }

        override fun getViewTypeCount(): Int {
            return 1
        }

        override fun isEmpty(): Boolean {
            return count == 0
        }
    }
}
