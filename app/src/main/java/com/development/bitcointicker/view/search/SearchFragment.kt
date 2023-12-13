package com.development.bitcointicker.view.search


import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import com.development.bitcointicker.databinding.FragmentSearchBinding
import com.development.bitcointicker.utils.resource.Resource
import com.development.bitcointicker.view.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {

    private val searchVm: SearchVM by viewModels()
    private val searchListAdapter by lazy { SearchListAdapter() }
    override fun mViewModel(): ViewModel = searchVm

    override fun setupView() {

        with(binding) {
            rvSearchCoin.adapter = searchListAdapter
            searchVm.invokeAction(SearchContracts.Action.GetSearchBitcoinList)
            observeSearchCoinList()

            searchCoin.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    newText?.let {
                        searchListAdapter.filter.filter(newText)
                    }?:run {
                        searchListAdapter.filter.filter("")
                    }
                    return false
                }
            })
        }
    }
    private fun observeSearchCoinList() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {

            searchVm.uiStateData.collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        showProgress()
                    }

                    is Resource.Success -> {
                        hideProgress()
                        searchListAdapter.updateSearchList(resource.data)
                    }

                    is Resource.Error -> {
                        showError(resource.throwable)
                    }
                }

            }
        }
    }

}