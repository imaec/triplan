package com.imaec.triplan.ui.common.input

import android.content.Context
import android.os.Bundle
import com.imaec.triplan.R
import com.imaec.triplan.base.BaseDialog
import com.imaec.triplan.databinding.DialogInputBinding

class InputDialog(
    context: Context,
    private val title: String,
    private val hint: String,
    private val text: String = "",
    private val cancelCallback: (() -> Unit)? = null,
    private val okCallback: ((String) -> Unit)? = null
) : BaseDialog<DialogInputBinding>(context, R.layout.dialog_input) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBinding()
    }

    private fun setupBinding() {
        with(binding) {
            tvTitle.text = title
            etInput.setText(text)
            etInput.hint = hint
            tvCancel.setOnClickListener {
                dismiss()
                cancelCallback?.invoke()
            }
            tvOk.setOnClickListener {
                dismiss()
                okCallback?.invoke(etInput.text.toString())
            }
        }
    }
}
