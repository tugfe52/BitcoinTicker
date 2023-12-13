package com.development.bitcointicker.view.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.development.bitcointicker.databinding.ItemCoinsListBinding
import com.development.bitcointicker.model.response.bitcoinlist.BitcoinListResponse
import com.development.bitcointicker.utils.extensions.setRateChangeColor
import com.development.bitcointicker.utils.extensions.setRateImageDrawable

class BitcoinListAdapter : RecyclerView.Adapter<BitcoinListAdapter.BitcoinListViewHolder>() {

    private var bitcoinItemList = arrayListOf<BitcoinListResponse>()

    inner class BitcoinListViewHolder(private val binding: ItemCoinsListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: BitcoinListResponse) = with(binding) {

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
    ): BitcoinListAdapter.BitcoinListViewHolder = BitcoinListViewHolder(
        ItemCoinsListBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: BitcoinListAdapter.BitcoinListViewHolder, position: Int) {
        holder.bind(bitcoinItemList[position])
    }

    override fun getItemCount(): Int = bitcoinItemList.size

    fun updateBitcoinList(newBitcoinItemList: ArrayList<BitcoinListResponse>) {

        val diffUtil = object : DiffUtil.Callback() {

            override fun getOldListSize(): Int {
                return bitcoinItemList.size
            }

            override fun getNewListSize(): Int {
                return newBitcoinItemList.size
            }

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return bitcoinItemList[oldItemPosition] == newBitcoinItemList[newItemPosition]
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return bitcoinItemList[oldItemPosition] == newBitcoinItemList[newItemPosition]
            }
        }

        val result = DiffUtil.calculateDiff(diffUtil)
        result.dispatchUpdatesTo(this)
        bitcoinItemList = newBitcoinItemList
    }
}