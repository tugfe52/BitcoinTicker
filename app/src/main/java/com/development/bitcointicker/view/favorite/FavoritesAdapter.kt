package com.development.bitcointicker.view.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.development.bitcointicker.databinding.ItemCoinsListBinding
import com.development.bitcointicker.model.response.favorite.FavoriteModel
import com.development.bitcointicker.utils.extensions.setRateChangeColor
import com.development.bitcointicker.utils.extensions.setRateImageDrawable

class FavoritesAdapter : RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder>() {

    private var favoritesList = arrayListOf<FavoriteModel>()
    var coinClick: ((String) -> Unit) = { _ -> }

    inner class FavoritesViewHolder(private val binding: ItemCoinsListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FavoriteModel) = with(binding) {

            Glide.with(itemView.context).load(item.image.orEmpty()).into(ivBitcoin)
            tvBitcoinName.text = "${item.coinName}"
            tvCurrentPrice.text = "$ " + String.format("%.3f", item.price)
            tvBitcoinRate.text = String.format("%.2f", item.percentage) + "%"
            tvBitcoinRate.setRateChangeColor(item.percentage.toString())
            imageCoinStatus.setRateImageDrawable(item.percentage.toString(), itemView.context)

            itemView.setOnClickListener {
                coinClick(item.coinId)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoritesAdapter.FavoritesViewHolder = FavoritesViewHolder(
        ItemCoinsListBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: FavoritesAdapter.FavoritesViewHolder, position: Int) {
        holder.bind(favoritesList[position])
    }

    override fun getItemCount(): Int = favoritesList.size

    fun updateFavorites(newFavoritesList: ArrayList<FavoriteModel>) {

        val diffUtil = object : DiffUtil.Callback() {

            override fun getOldListSize(): Int {
                return favoritesList.size
            }

            override fun getNewListSize(): Int {
                return newFavoritesList.size
            }

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return favoritesList[oldItemPosition] == newFavoritesList[newItemPosition]
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return favoritesList[oldItemPosition] == newFavoritesList[newItemPosition]
            }
        }

        val result = DiffUtil.calculateDiff(diffUtil)
        result.dispatchUpdatesTo(this)
        favoritesList = newFavoritesList
    }
}