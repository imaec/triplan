package com.imaec.triplan.ui.writeplace.searchaddress

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.SimpleItemAnimator
import com.imaec.triplan.BR
import com.imaec.triplan.R
import com.imaec.triplan.base.BaseActivity
import com.imaec.triplan.base.BaseMultiListAdapter
import com.imaec.triplan.base.ViewHolderType
import com.imaec.triplan.databinding.ActivitySearchAddressBinding
import com.imaec.triplan.ext.toast
import com.imaec.triplan.ui.common.RecyclerViewDividerDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchAddressActivity :
    BaseActivity<ActivitySearchAddressBinding>(R.layout.activity_search_address) {

    private val viewModel by viewModels<SearchAddressViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBinding()
        setupRecyclerView()
        setupListener()
        setupObserver()
    }

    private fun setupBinding() {
        binding.vm = viewModel
    }

    private fun setupRecyclerView() {
        with(binding.rvSearchAddress) {
            val viewHolderMapper: (SearchAddressItem) -> ViewHolderType = {
                when (it) {
                    SearchAddressItem.SearchInput -> {
                        SearchAddressHolderType.InputHolder
                    }
                    is SearchAddressItem.Address -> {
                        SearchAddressHolderType.AddressHolder
                    }
                }
            }

            val diffUtil = object : DiffUtil.ItemCallback<SearchAddressItem>() {
                override fun areItemsTheSame(
                    oldItem: SearchAddressItem,
                    newItem: SearchAddressItem
                ): Boolean =
                    if (oldItem is SearchAddressItem.Address &&
                        newItem is SearchAddressItem.Address
                    ) {
                        oldItem.address == newItem.address
                    } else {
                        oldItem == newItem
                    }

                override fun areContentsTheSame(
                    oldItem: SearchAddressItem,
                    newItem: SearchAddressItem
                ): Boolean =
                    oldItem == newItem
            }

            val animator = itemAnimator
            if (animator is SimpleItemAnimator) {
                animator.supportsChangeAnimations = false
            }

            addItemDecoration(RecyclerViewDividerDecoration(startPosition = 1))

            adapter = BaseMultiListAdapter(
                viewHolderMapper = viewHolderMapper,
                viewHolderType = SearchAddressHolderType::class,
                viewModel = mapOf(BR.vm to viewModel),
                diffUtil = diffUtil
            )
        }
    }

    private fun setupListener() {
        with(binding.mtbSearchAddress) {
            setNavigationOnClickListener {
                finish()
            }
        }
    }

    private fun setupObserver() {
        with(viewModel.state) {
            observe(this@SearchAddressActivity) {
                when (it) {
                    is SearchAddressState.OnClickAddress -> {
                        setResult(
                            RESULT_OK,
                            Intent().apply {
                                putExtra(ADDRESS, it.address)
                            }
                        )
                        finish()
                    }
                    is SearchAddressState.OnError -> toast(it.message)
                }
            }
        }
    }

    enum class SearchAddressHolderType(
        override val layoutResId: Int,
        override val bindingItemId: Int
    ) : ViewHolderType {
        InputHolder(
            layoutResId = R.layout.item_search_address_input,
            bindingItemId = BR._all
        ),
        AddressHolder(
            layoutResId = R.layout.item_search_address,
            bindingItemId = BR.item
        )
    }

    companion object {
        const val SEARCH_ADDRESS = "search_address"
        const val ADDRESS = "address"
    }
}
