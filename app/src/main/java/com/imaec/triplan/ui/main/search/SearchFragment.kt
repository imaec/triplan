package com.imaec.triplan.ui.main.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.SimpleItemAnimator
import com.imaec.triplan.BR
import com.imaec.triplan.R
import com.imaec.triplan.base.BaseFragment
import com.imaec.triplan.base.BaseMultiListAdapter
import com.imaec.triplan.base.ViewHolderType
import com.imaec.triplan.databinding.FragmentSearchBinding
import com.imaec.triplan.ext.hideKeyboard
import com.imaec.triplan.ext.startActivity
import com.imaec.triplan.ui.main.MainActivity
import com.imaec.triplan.ui.place.PlaceMoreActivity
import com.imaec.triplan.ui.plan.PlanDetailActivity
import com.imaec.triplan.ui.plan.more.PlanMoreActivity
import com.imaec.triplan.ui.writeplace.WritePlaceActivity
import com.imaec.triplan.ui.writeplace.WritePlaceType
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment :
    BaseFragment<FragmentSearchBinding>(R.layout.fragment_search),
    MainActivity.OnBackPressedListener {

    private val viewModel by viewModels<SearchViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBinding()
        setupRecyclerView()
        setupData()
        setupListener()
        setupObserver()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity() as MainActivity).setBackPressedListener(this)
    }

    private fun setupBinding() {
        binding.vm = viewModel
    }

    private fun setupRecyclerView() {
        with(binding.rvSearchResult) {
            val viewHolderMapper: (SearchItem) -> ViewHolderType = {
                when (it) {
                    is SearchItem.SearchResultPlan -> {
                        SearchHolderType.PlanHolder
                    }
                    is SearchItem.SearchResultPlace -> {
                        SearchHolderType.PlaceHolder
                    }
                }
            }

            val diffUtil = object : DiffUtil.ItemCallback<SearchItem>() {
                override fun areItemsTheSame(
                    oldItem: SearchItem,
                    newItem: SearchItem
                ): Boolean = oldItem == newItem

                override fun areContentsTheSame(
                    oldItem: SearchItem,
                    newItem: SearchItem
                ): Boolean =
                    oldItem == newItem
            }

            val animator = itemAnimator
            if (animator is SimpleItemAnimator) {
                animator.supportsChangeAnimations = false
            }

            adapter = BaseMultiListAdapter(
                viewHolderMapper = viewHolderMapper,
                viewHolderType = SearchHolderType::class,
                viewModel = mapOf(BR.vm to viewModel),
                diffUtil = diffUtil
            )
        }
    }

    private fun setupData() {
        viewModel.fetchData()
    }

    private fun setupListener() {
        with(binding) {
            tieSearch.setOnEditorActionListener { v, actionId, _ ->
                var handled = false
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    viewModel.search(viewModel.search.get())
                    hideKeyboard(v)
                    handled = true
                }
                handled
            }
        }
    }

    private fun setupObserver() {
        with(viewModel.state) {
            observe(viewLifecycleOwner) {
                when (it) {
                    SearchState.OnClickSearch -> hideKeyboard(binding.tieSearch)
                    is SearchState.OnClickPlan -> {
                        startActivity<PlanDetailActivity>(
                            PlanDetailActivity.createBundle(it.plan)
                        )
                    }
                    is SearchState.OnClickPlanMore -> {
                        startActivity<PlanMoreActivity>(
                            PlanMoreActivity.createBundle(
                                title = "일정 검색 결과",
                                keyword = it.keyword
                            )
                        )
                    }
                    is SearchState.OnClickPlace -> {
                        startActivity<WritePlaceActivity>(
                            WritePlaceActivity.createBundle(it.place, WritePlaceType.EDIT)
                        )
                    }
                    is SearchState.OnClickPlaceMore -> {
                        requireActivity().activityResultRegistry.register(
                            PlaceMoreActivity.PLACE_MORE,
                            ActivityResultContracts.StartActivityForResult()
                        ) {
                            if (it.resultCode == AppCompatActivity.RESULT_OK) {
                                viewModel.search(viewModel.search.get())
                            }
                        }.launch(
                            Intent(requireContext(), PlaceMoreActivity::class.java).apply {
                                putExtras(PlaceMoreActivity.createBundle(keyword = it.keyword))
                            }
                        )
                    }
                }
            }
        }
    }

    enum class SearchHolderType(
        override val layoutResId: Int,
        override val bindingItemId: Int
    ) : ViewHolderType {
        PlanHolder(
            layoutResId = R.layout.item_search_result_plan_container,
            bindingItemId = BR.item
        ),
        PlaceHolder(
            layoutResId = R.layout.item_search_result_place_container,
            bindingItemId = BR.item
        )
    }

    override fun onBackPressed(): Boolean {
        return viewModel.existSearchResult()
    }
}
