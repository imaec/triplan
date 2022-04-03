package com.imaec.triplan.base

import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import kotlin.reflect.KClass

class BaseMultiListAdapter<ITEM : Any>(
    private val viewHolderMapper: (ITEM) -> ViewHolderType,
    private val viewHolderType: KClass<out ViewHolderType>,
    private val viewModel: Map<Int, ViewModel>,
    diffUtil: DiffUtil.ItemCallback<ITEM>,
    private val isHasStableIds: Boolean = false
) : ListAdapter<ITEM, BaseViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val viewHolderType = viewHolderType.java.enumConstants[viewType]
        return BaseViewHolder(
            parent = parent,
            layoutResourceId = viewHolderType.layoutResId,
            bindingItemId = viewHolderType.bindingItemId,
            viewModel = viewModel
        )
    }

    override fun getItemViewType(position: Int): Int =
        (viewHolderMapper(getItem(position)) as Enum<*>).ordinal

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) =
        holder.bind(getItem(position))

    override fun getItemId(position: Int): Long = if (isHasStableIds) {
        position.toLong()
    } else {
        super.getItemId(position)
    }
}

interface ViewHolderType {
    val layoutResId: Int
    val bindingItemId: Int
}
