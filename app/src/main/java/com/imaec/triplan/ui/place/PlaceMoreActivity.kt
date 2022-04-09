package com.imaec.triplan.ui.place

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.SimpleItemAnimator
import com.imaec.domain.model.PlaceDto
import com.imaec.triplan.BR
import com.imaec.triplan.R
import com.imaec.triplan.base.BaseActivity
import com.imaec.triplan.base.BaseListAdapter
import com.imaec.triplan.databinding.ActivityPlaceMoreBinding
import com.imaec.triplan.ext.startActivity
import com.imaec.triplan.ext.toast
import com.imaec.triplan.ui.common.CommonDialog
import com.imaec.triplan.ui.writeplace.WritePlaceActivity
import com.imaec.triplan.ui.writeplace.WritePlaceType
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlaceMoreActivity : BaseActivity<ActivityPlaceMoreBinding>(R.layout.activity_place_more) {

    private val viewModel by viewModels<PlaceMoreViewModel>()

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
        with(binding.rvPlaceMore) {
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
                layoutResId = R.layout.item_place_more,
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
        with(binding.mtbPlaceMore) {
            setNavigationOnClickListener {
                finish()
            }
        }
    }

    private fun setupObserver() {
        with(viewModel.state) {
            observe(this@PlaceMoreActivity) {
                when (it) {
                    is PlaceMoreState.OnClickPlace -> {
                        startActivity<WritePlaceActivity>(
                            WritePlaceActivity.createBundle(it.place, WritePlaceType.EDIT)
                        )
                    }
                    is PlaceMoreState.OnLongClickPlace -> {
                        CommonDialog(
                            context = this@PlaceMoreActivity,
                            title = "내 장소 삭제",
                            text = "\"${it.place.placeName}\"을(를) 삭제하시겠습니까?",
                            okCallback = {
                                viewModel.deletePlace(it.place)
                            }
                        ).show()
                    }
                    is PlaceMoreState.DeletedPlace -> {
                        setResult(RESULT_OK)
                        toast(it.message)
                    }
                }
            }
        }
    }

    companion object {
        const val PLACE_MORE = "place_more"
        const val KEYWORD = "keyword"

        fun createBundle(
            keyword: String?
        ): Bundle = bundleOf(
            KEYWORD to keyword
        )
    }
}
