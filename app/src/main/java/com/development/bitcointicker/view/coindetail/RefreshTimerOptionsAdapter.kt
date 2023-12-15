package com.development.bitcointicker.view.coindetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.development.bitcointicker.databinding.ItemTimerOptionsBinding

class RefreshTimerOptionsAdapter  : RecyclerView.Adapter<RefreshTimerOptionsAdapter.RefreshTimerViewHolder>() {

    private var optionList = arrayListOf<Int>()
    var optionsClick: ((Int) -> Unit) = { _ -> }

    inner class RefreshTimerViewHolder(private val binding: ItemTimerOptionsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Int) = with(binding) {

            tvTimerOptionTitle.text = "${item} Saniye"

            itemView.setOnClickListener {
                optionsClick(item)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RefreshTimerOptionsAdapter.RefreshTimerViewHolder = RefreshTimerViewHolder(
        ItemTimerOptionsBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: RefreshTimerOptionsAdapter.RefreshTimerViewHolder, position: Int) {
        holder.bind(optionList[position])
    }

    override fun getItemCount(): Int = optionList.size

    fun updateOptionsList(newOptionList: ArrayList<Int>) {

        val diffUtil = object : DiffUtil.Callback() {

            override fun getOldListSize(): Int {
                return optionList.size
            }

            override fun getNewListSize(): Int {
                return newOptionList.size
            }

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return optionList[oldItemPosition] == newOptionList[newItemPosition]
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return optionList[oldItemPosition] == newOptionList[newItemPosition]
            }
        }

        val result = DiffUtil.calculateDiff(diffUtil)
        result.dispatchUpdatesTo(this)
        optionList = newOptionList
    }
}