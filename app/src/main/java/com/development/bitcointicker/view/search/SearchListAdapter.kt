package com.development.bitcointicker.view.search

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.development.bitcointicker.databinding.ItemCoinsListBinding
import com.development.bitcointicker.model.response.search.SearchCoinListResponse
import com.development.bitcointicker.utils.extensions.setRateChangeColor
import com.development.bitcointicker.utils.extensions.setRateImageDrawable
import java.util.Locale

class SearchListAdapter : RecyclerView.Adapter<SearchListAdapter.SearchListViewHolder>(), Filterable {

    private var searchItemList = arrayListOf<SearchCoinListResponse>()
    private var searchItemListFiltered = arrayListOf<SearchCoinListResponse>()

    inner class SearchListViewHolder(private val binding: ItemCoinsListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SearchCoinListResponse) = with(binding) {

            Glide.with(itemView.context).load(item.image.orEmpty()).into(ivBitcoin)
            tvBitcoinName.text = item.name
            tvCurrentPrice.text = "$ " + String.format("%.3f", item.currentPrice)
            tvBitcoinRate.text = String.format("%.2f", item.priceChangePercentage24h) + "%"
            tvBitcoinRate.setRateChangeColor(item.priceChangePercentage24h.toString())
            imageCoinStatus.setRateImageDrawable(item.priceChangePercentage24h.toString(), itemView.context)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchListAdapter.SearchListViewHolder = SearchListViewHolder(
        ItemCoinsListBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: SearchListAdapter.SearchListViewHolder, position: Int) {
        holder.bind(searchItemListFiltered[position])
    }

    override fun getItemCount(): Int = searchItemListFiltered.size

    fun updateSearchList(newSearchItemList: ArrayList<SearchCoinListResponse>) {

        val diffUtil = object : DiffUtil.Callback() {

            override fun getOldListSize(): Int {
                return searchItemList.size
            }

            override fun getNewListSize(): Int {
                return newSearchItemList.size
            }

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return searchItemList[oldItemPosition] == newSearchItemList[newItemPosition]
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return searchItemList[oldItemPosition] == newSearchItemList[newItemPosition]
            }
        }

        val result = DiffUtil.calculateDiff(diffUtil)
        result.dispatchUpdatesTo(this)
        searchItemList = newSearchItemList
        searchItemListFiltered = newSearchItemList
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                val charString = charSequence.toString()
                searchItemListFiltered = if (charString.isEmpty()) {
                    searchItemList
                } else {
                    val filteredList = ArrayList<SearchCoinListResponse>()
                    for (row in searchItemList) {
                        if (row.name?.lowercase().orEmpty().contains(charString.lowercase())) {
                            filteredList.add(row)
                        }
                    }
                    filteredList
                }

                val filterResults = FilterResults()
                filterResults.values = searchItemListFiltered
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence?, filterResults: FilterResults?) {
                searchItemListFiltered = filterResults?.values as ArrayList<SearchCoinListResponse>
                notifyDataSetChanged()
            }
        }
    }
}