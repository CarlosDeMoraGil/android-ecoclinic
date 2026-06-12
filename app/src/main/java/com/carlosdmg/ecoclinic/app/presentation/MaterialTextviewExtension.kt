package com.carlosdmg.ecoclinic.app.presentation

import com.google.android.material.textview.MaterialTextView

fun MaterialTextView.setColor(colorRes: Int) {
    this.setTextColor(context.getColor(colorRes))
}