package com.imaec.triplan.base

import android.view.View
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.imaec.domain.Result
import java.util.concurrent.atomic.AtomicBoolean

@BindingAdapter("bindVisible")
fun View.bindVisible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

@BindingAdapter("bindSingleClick")
fun View.bindSingleClick(clickListener: View.OnClickListener?) {
    clickListener?.also {
        setOnClickListener(OnSingleClickListener(it))
    } ?: setOnClickListener(null)
}

class OnSingleClickListener(
    private val clickListener: View.OnClickListener,
    private val intervalMs: Long = 1000
) : View.OnClickListener {
    private var canClick = AtomicBoolean(true)

    override fun onClick(v: View?) {
        if (canClick.getAndSet(false)) {
            v?.run {
                postDelayed(
                    {
                        canClick.set(true)
                    },
                    intervalMs
                )
                clickListener.onClick(v)
            }
        }
    }
}

@BindingAdapter("bindHtmlText")
fun TextView.bindHtmlText(str: String) {
    text = HtmlCompat.fromHtml(str, HtmlCompat.FROM_HTML_MODE_LEGACY)
}

@Suppress("UNCHECKED_CAST")
@BindingAdapter("bindItemList")
fun RecyclerView.bindItemList(itemList: List<Any>?) {
    itemList ?: return

    (adapter as? BaseListAdapter<Any>)?.run {
        submitList(itemList)
    }

    (adapter as? BaseMultiListAdapter<Any>)?.run {
        submitList(itemList)
    }
}

@Suppress("UNCHECKED_CAST")
@BindingAdapter("bindItemList")
fun RecyclerView.bindItemList(itemResult: Result<*>) {
    if (itemResult is Result.Success) {
        (adapter as? BaseListAdapter<Any>)?.run {
            submitList(itemResult.data as List<Any>)
        }
    }
}
