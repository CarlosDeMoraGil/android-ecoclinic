package com.carlosdmg.ecoclinic.feature.user.presentation.views

import android.content.Context
import android.text.InputType
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.view.updateLayoutParams
import com.carlosdmg.ecoclinic.app.presentation.setNumeric
import com.carlosdmg.ecoclinic.databinding.ViewFormFieldBinding

class SignUpFormField @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding = ViewFormFieldBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        orientation = VERTICAL
    }

    fun setView(
        text: Int,
        hint: Int? = null,
        widthF: Int? = null,
        numeric: Boolean? = null,
        passwordType: Boolean? = null
    ) {
        binding.apply {
            formVwTitle.text = context.getString(text)

            hint?.let {
                formVwEdittext.hint = context.getString(it)
            }
            widthF?.let { resId ->
                formVwEdittext.updateLayoutParams {
                    width = context.resources.getDimensionPixelSize(resId)
                }
            }
            numeric?.let {
                formVwEdittext.setNumeric()
            }
            passwordType?.let {
                setPasswordVisibility(passwordType)
            }
        }
    }

    fun setColor(color: Int) = binding.formVwTitle.setTextColor(context.getColor(color))

    fun getText() = binding.formVwEdittext.text.toString()

    fun isEmpty(): Boolean {
        return binding.formVwEdittext.text.isNullOrBlank()
    }

    fun setPasswordVisibility(showHide: Boolean) {
        binding.formVwEdittext.apply {
            inputType = if (showHide) {
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            } else {
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD

            }
        }
    }

}