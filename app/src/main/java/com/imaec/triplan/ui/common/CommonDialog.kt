package com.imaec.triplan.ui.common

import android.content.Context
import android.os.Bundle
import com.imaec.triplan.R
import com.imaec.triplan.base.BaseDialog
import com.imaec.triplan.databinding.DialogCommonBinding

class CommonDialog(
    context: Context,
    private val title: String,
    private val text: String = "",
    private val cancelCallback: (() -> Unit)? = null,
    private val okCallback: (() -> Unit)? = null
) : BaseDialog<DialogCommonBinding>(context, R.layout.dialog_common) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBinding()
    }

    private fun setupBinding() {
        with(binding) {
            tvTitle.text = title
            tvMessage.text = text
            tvCancel.setOnClickListener {
                dismiss()
                cancelCallback?.invoke()
            }
            tvOk.setOnClickListener {
                dismiss()
                okCallback?.invoke()
            }
        }
    }
}
