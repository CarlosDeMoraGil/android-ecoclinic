package com.carlosdmg.ecoclinic.app.presentation

import android.view.View

fun View.hide(){
    this.visibility = View.GONE
}

fun View.show(){
    this.visibility = View.VISIBLE
}

fun View.setVisibility(visible: Boolean) {
    if (visible) {
        this.hide()
    } else {
        this.show()
    }
}