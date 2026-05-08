package com.carlosdmg.ecoclinic.feature.user.presentation.views

import android.content.Context
import android.graphics.Typeface
import android.text.InputType
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.carlosdmg.ecoclinic.R
import com.carlosdmg.ecoclinic.databinding.ViewFormFieldBinding

class SingUpFormField @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding = ViewFormFieldBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        orientation = VERTICAL
    }

    fun setView(text: Int, hint: Int? = null) {
        binding.apply {
            formVwTitle.text = context.getString(text)
            hint?.let {
                formVwEdittext.hint = context.getString(it)
            }
        }
    }

    fun getText() = binding.formVwEdittext.text.toString()

}