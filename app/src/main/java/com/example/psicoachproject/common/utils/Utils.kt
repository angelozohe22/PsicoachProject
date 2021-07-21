package com.example.psicoachproject.common.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import androidx.core.content.ContextCompat
import com.example.psicoachproject.R
import com.example.psicoachproject.core.Constants
import com.example.psicoachproject.core.aplication.preferences
import com.example.psicoachproject.data.remote.source.dto.Day
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*

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

fun setStatusBarColor(
    activity: Activity, color: Int = ContextCompat.getColor(
        activity.applicationContext,
        R.color.primary_dark
    )
) {
    val window = activity.window
    val hsv = FloatArray(3)
    var customColor: Int = color

    Color.colorToHSV(customColor, hsv)
    customColor = Color.HSVToColor(hsv)

    if (Build.VERSION.SDK_INT >= 21) {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = customColor //Define color
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
//        window?.setBackgroundDrawableResource(android.R.color.transparent)
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

fun setColorTintBottomNavigation(): ColorStateList {
    val gris   = Color.parseColor("#9E9E9E")
    val states = arrayOf(
        intArrayOf(android.R.attr.state_checked),
        intArrayOf(android.R.attr.state_selected),
        intArrayOf(-android.R.attr.state_selected)
    )
    val colors = intArrayOf(Color.parseColor("#ffffff"), Color.parseColor("#808080"), gris)
    return ColorStateList(states, colors)
}

fun getSchedulesPerDay(stringDate: String, complete: (List<String>)  -> Unit){
    val date = stringDate.toParseDate("dd/MM/yyyy")
    val c: Calendar = Calendar.getInstance()
    c.time = date
    val dayOfWeek = c.get(Calendar.DAY_OF_WEEK)

    val schedule = Gson().fromJson(preferences.schedule, Day::class.java)
    when(dayOfWeek){
        1 -> { //Domingo
            complete(emptyList())
        }
        2 -> { complete(schedule.monday ?: emptyList()) }
        3 -> { complete(schedule.tuesday ?: emptyList()) }
        4 -> { complete(schedule.wednesday ?: emptyList()) }
        5 -> { complete(schedule.thursday ?: emptyList()) }
        6 -> { complete(schedule.friday ?: emptyList()) }
        7 -> { //Sabado
            complete(schedule.saturday ?: emptyList())
        }
    }
}

fun Any.toJson(): String = Gson().toJson(this)

fun getColorWithAlpha(color: Int, ratio: Float): Int {
    return Color.argb(Math.round(Color.alpha(color) * ratio), Color.red(color), Color.green(color), Color.blue(color))
}

fun String.toDateStringDate(
    format: String = "yyyy-MM-dd",
    locale: Locale = Locale.getDefault()
): String{
    return (this.toParseDate(format))?.toParseString() ?: ""
}

fun String.toParseDate(format: String = "yyyy-MM-dd",
                       locale: Locale = Locale.getDefault()
): Date? = SimpleDateFormat(format, locale).parse(this)

fun Date.toParseString(format: String = "yyyy-MM-dd", locale: Locale = Locale.getDefault()): String {
    val formatter = SimpleDateFormat(format, locale)
    return formatter.format(this)
}

fun getFormatDateString(day: Int, month: Int, year: Int): String{
    val customDay   = (if(day<10) "0" else "").plus(day)
    val customMonth = (if(month<10) "0" else "").plus(month)
    return "$customDay/${customMonth}/$year"
}

fun getFormatDateString2(date: String): String{
    val dateArray = date.split("/")
    val day  = dateArray[0]
    val month= dateArray[1]
    val year = dateArray[2]
    return "$year-$month-$day"
}

fun View.showSnackBar(message: String, duration: Int = Snackbar.LENGTH_LONG){
    Snackbar.make(this,
            message,
            duration
    ).let { snackBar ->
        snackBar.setAction("Cerrar"){
            snackBar.dismiss()
        }
    }.show()
}

fun String.capitalizeFully(): String? {
    var s = this
    if (s.trim { it <= ' ' }.isEmpty()) {
        return s
    }
    s = s.toLowerCase()
    val cArr = s.trim { it <= ' ' }.toCharArray()
    cArr[0] = Character.toUpperCase(cArr[0])
    for (i in cArr.indices) {
        if (cArr[i] == ' ' && i + 1 < cArr.size)  cArr[i + 1] = Character.toUpperCase(cArr[i + 1])
        if (cArr[i] == '-' && i + 1 < cArr.size)  cArr[i + 1] = Character.toUpperCase(cArr[i + 1])
        if (cArr[i] == '\'' && i + 1 < cArr.size) cArr[i + 1] = Character.toUpperCase(cArr[i + 1])
    }
    return String(cArr)
}

fun dateStringToTimeMilli(date: String, formmatter: String = "yyyy-MM-dd"): Long? {
    val date = date
    val format = SimpleDateFormat(formmatter)
    val time: Long?
    time = try {
        val dateFormatted = format.parse(date)
        dateFormatted.time
    } catch (e: Exception) {
        null
    }
    return time
}

fun getColorPackage(packageName: String): Int{
    return when(packageName){
        Constants.PACKAGE_BASIC -> R.color.color_package_basic
        Constants.PACKAGE_PREMIUM -> R.color.color_package_premium
        Constants.PACKAGE_MEDIUM -> R.color.color_package_medium
        Constants.PACKAGE_REGULAR -> R.color.color_package_regular
        Constants.PACKAGE_LUXURY -> R.color.color_package_luxury
        else -> R.color.color_package_basic
    }
}

@SuppressLint("ClickableViewAccessibility")
fun EditText.onRightDrawableClicked(onClicked: (view: EditText) -> Unit) {
    this.setOnTouchListener { v, event ->
        var hasConsumed = false
        if (v is EditText) {
            if (event.x >= v.width - v.totalPaddingRight) {
                if (event.action == MotionEvent.ACTION_UP) {
                    onClicked(this)
                }
                hasConsumed = true
            }
        }
        hasConsumed
    }
}