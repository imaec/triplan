package com.imaec.triplan.ui.select.place

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.SimpleItemAnimator
import com.imaec.domain.model.PlaceDto
import com.imaec.triplan.BR
import com.imaec.triplan.R
import com.imaec.triplan.base.BaseActivity
import com.imaec.triplan.base.BaseListAdapter
import com.imaec.triplan.databinding.ActivitySelectPlaceBinding
import com.imaec.triplan.ext.startActivity
import com.imaec.triplan.ui.writeplace.WritePlaceActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectPlaceActivity :
    BaseActivity<ActivitySelectPlaceBinding>(R.layout.activity_select_place) {

    private val viewModel by viewModels<SelectPlaceViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBinding()
        setupRecyclerView()
        setupData()
        setupListener()
        setupObserver()
    }

    private fun setupBinding() {
        binding.vm = viewModel
    }

    private fun setupRecyclerView() {
        with(binding.rvSelectPlace) {
            val diffUtil = object : DiffUtil.ItemCallback<PlaceDto>() {
                override fun areItemsTheSame(oldItem: PlaceDto, newItem: PlaceDto): Boolean =
                    oldItem.placeId == newItem.placeId

                override fun areContentsTheSame(oldItem: PlaceDto, newItem: PlaceDto): Boolean =
                    oldItem == newItem
            }

            val animator = itemAnimator
            if (animator is SimpleItemAnimator) {
                animator.supportsChangeAnimations = false
            }

            adapter = BaseListAdapter(
                layoutResId = R.layout.item_select_place,
                bindingItemId = BR.item,
                viewModel = mapOf(BR.vm to viewModel),
                diffUtil = diffUtil
            )
        }
    }

    private fun setupData() {
        viewModel.fetchData()
    }

    private fun setupListener() {
        with(binding.mtbSelectPlace) {
            setNavigationOnClickListener {
                finish()
            }
        }
    }

    private fun setupObserver() {
        with(viewModel.state) {
            observe(this@SelectPlaceActivity) {
                when (it) {
                    is SelectPlaceState.OnClickPlace -> {
                        setResult(
                            RESULT_OK,
                            Intent().apply {
                                putExtra(PLACE, it.place)
                            }
                        )
                        finish()
                    }
                    SelectPlaceState.OnClickAddPlace -> startActivity<WritePlaceActivity>()
                }
            }
        }
    }

    companion object {
        const val SELECT_PLACE = "select_place"
        const val PLACE = "place"
    }
}
