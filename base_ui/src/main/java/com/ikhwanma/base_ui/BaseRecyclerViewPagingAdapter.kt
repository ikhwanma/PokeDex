package com.ikhwanma.base_ui

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseRecyclerViewPagingAdapter<ITEM, VB: ViewBinding>: PagingDataAdapter<ITEM & Any, BaseRecyclerViewPagingAdapter<ITEM, VB>.ViewHolder>(
    object : DiffUtil.ItemCallback<ITEM & Any>() {
        override fun areItemsTheSame(oldItem: ITEM & Any, newItem: ITEM & Any): Boolean =
            oldItem == newItem

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: ITEM & Any, newItem: ITEM & Any): Boolean =
            oldItem == newItem
    }
) {
    abstract fun getViewBinding(parent: ViewGroup): VB
    abstract fun bindItem(binding: VB, data: ITEM, position: Int)

    override fun onBindViewHolder(
        holder: BaseRecyclerViewPagingAdapter<ITEM, VB>.ViewHolder,
        position: Int
    ) {
        val data = getItem(position)!!
        bindItem(holder.binding, data, position)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseRecyclerViewPagingAdapter<ITEM, VB>.ViewHolder {
        return ViewHolder(getViewBinding(parent))
    }

    inner class ViewHolder(val binding: VB): RecyclerView.ViewHolder(binding.root)
}