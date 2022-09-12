package com.example.valorantwiki.ui.activities.extensions

import android.content.Context
import android.content.Intent

fun Context.goTo(
    destinationClass: Class<*>,
    intent: Intent.() -> Unit = {}
){
    Intent(
        this,
        destinationClass
    ).apply {
        intent()
        startActivity(this)
    }
}