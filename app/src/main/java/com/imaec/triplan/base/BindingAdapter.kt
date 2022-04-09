package com.imaec.triplan.base

import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.imaec.domain.Result
import com.imaec.triplan.R
import com.imaec.triplan.ui.main.search.SearchViewModel
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

@BindingAdapter("bindLongClick")
fun View.bindLongClick(clickListener: View.OnLongClickListener?) {
    clickListener?.also {
        setOnLongClickListener(OnLongClickListener(it))
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

class OnLongClickListener(
    private val clickListener: View.OnLongClickListener,
    private val intervalMs: Long = 1000
) : View.OnLongClickListener {
    private var canClick = AtomicBoolean(true)

    override fun onLongClick(v: View?): Boolean {
        if (canClick.getAndSet(false)) {
            v?.run {
                postDelayed(
                    {
                        canClick.set(true)
                    },
                    intervalMs
                )
                return clickListener.onLongClick(v)
            }
        }
        return false
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

@BindingAdapter(
    value = [
        "bindSearchRecentKeywordList",
        "bindSearchViewModel"
    ]
)
fun ChipGroup.bindSearchRecent(recentList: List<String>, vm: SearchViewModel) {
    removeAllViews()

    recentList.forEach {
        val chip = LayoutInflater.from(context)
            .inflate(R.layout.chip_recent_keyword, this, false) as Chip
        chip.text = it
        chip.setOnClickListener { _ ->
            vm.onClickKeyword(it)
        }
        chip.setOnCloseIconClickListener { _ ->
            vm.onClickDeleteKeyword(it)
        }

        addView(chip)
    }
}
