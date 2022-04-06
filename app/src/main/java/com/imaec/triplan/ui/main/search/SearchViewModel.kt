package com.imaec.triplan.ui.main.search

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor() : ViewModel() {

//    private val _state = MutableLiveData<SearchState>()
//    val state: LiveData<SearchState> get() = _state

    private val _recentList = MutableLiveData<List<String>>(emptyList())
    val recentList: LiveData<List<String>> get() = _recentList

//    private val _searchResultList = MutableLiveData<List<SearchItem>>()
//    val searchResultList: LiveData<List<SearchItem>> get() = _searchResultList

    val search = ObservableField("")

    fun onClickSearch() {
    }

    fun onClickKeyword(keyword: String) {
    }
}
