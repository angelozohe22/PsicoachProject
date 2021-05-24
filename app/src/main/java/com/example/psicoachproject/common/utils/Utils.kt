package com.example.psicoachproject.common.utils

import android.app.AlertDialog
import android.content.Context
import android.os.Build
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Patterns
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import com.example.psicoachproject.R

/**
 * Created by Angelo on 5/11/2021
 */

fun afterTextChanged(function: (s: Editable) -> Unit): TextWatcher {
    return object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable) {
            function(s)
        }
    }
}

fun customDialog(ctx: Context, layout: Int, function: (View, AlertDialog) -> Unit) {
    //Create dialog
    val dialog =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            AlertDialog.Builder(ctx, R.style.CustomDialogBackground)
        else AlertDialog.Builder(ctx)
    val customDialog = dialog.create()

    //Create view
    val layoutInflater =
        ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as android.view.LayoutInflater
    val view = layoutInflater.inflate(layout, null)

    customDialog.apply {
        window?.setBackgroundDrawableResource(android.R.color.transparent)
        window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        window?.setGravity(Gravity.CENTER)
        function(view, this)
        setView(view)
        show()
    }
}

fun calcularPxToDps(context: Context, pixels: Int): Int {
    val scale = context.resources.displayMetrics.density
    return (pixels * scale + 0.5f).toInt()
}

fun isNullOrEmpty(text: Any): Boolean {
    return when (text) {
        is String -> text.trim().isEmpty()
        is EditText -> text.text.trim().isEmpty()
        else -> false
    }
}

fun isEmailValid(email: String): Boolean {
    val pattern = Patterns.EMAIL_ADDRESS
    return pattern.matcher(email).matches()
}