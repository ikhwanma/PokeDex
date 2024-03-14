package com.ikhwanma.base_ui

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseRecyclerViewAdapter<ITEM, VB: ViewBinding>: RecyclerView.Adapter<BaseRecyclerViewAdapter<ITEM, VB>.ViewHolder>() {
    protected val listItems = arrayListOf<ITEM>()

    abstract fun getViewBinding(parent: ViewGroup): VB

    abstract fun bindItem(binding: VB, data: ITEM, position: Int)

    fun getListItems(): List<ITEM> = listItems

    @SuppressLint("NotifyDataSetChanged")
    open fun addListItems(listItems: List<ITEM>){
        this.listItems.addAll(listItems)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    open fun updateListItems(listItems: List<ITEM>){
        this.listItems.apply {
            clear()
            addAll(listItems)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseRecyclerViewAdapter<ITEM, VB>.ViewHolder {
        return ViewHolder(getViewBinding(parent))
    }

    override fun onBindViewHolder(
        holder: BaseRecyclerViewAdapter<ITEM, VB>.ViewHolder,
        position: Int
    ) {
        bindItem(holder.binding, listItems[position], position)
    }

    override fun getItemCount(): Int = listItems.size

    inner class ViewHolder(val binding: VB): RecyclerView.ViewHolder(binding.root)
}