package com.carlosdmg.ecoclinic.app.presentation

import android.text.InputType
import android.widget.EditText


fun EditText.setNumeric() {
    this.inputType = InputType.TYPE_CLASS_NUMBER
}