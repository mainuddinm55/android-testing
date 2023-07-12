package com.example.testingtemplate.ui.base

import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T, VH : ViewDataBinding>(
    private val listener: (item: T, view: View, position: Int) -> Unit
) : RecyclerView.Adapter<BaseAdapter.BaseViewHolder<VH>>() {

    private val differ by lazy {
        AsyncListDiffer(this, itemDiffUtilCallback())
    }

    protected abstract fun inflateBinding(parent: ViewGroup, viewType: Int): VH

    protected abstract fun itemDiffUtilCallback(): DiffUtil.ItemCallback<T>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<VH> {
        val viewHolder = BaseViewHolder(inflateBinding(parent, viewType))
        viewHolder.itemView.setOnClickListener {
            val item = getItem(viewHolder.adapterPosition)
            listener.invoke(item, it, viewHolder.adapterPosition)
        }
        return viewHolder
    }

    fun addItems(items: List<T>) {
        differ.submitList(items)
        notifyDataSetChanged()
    }

    fun addItem(item: T) {
        val list = differ.currentList
        list.add(item)
        differ.submitList(list)
        notifyDataSetChanged()
    }

    fun getItem(position: Int): T {
        return differ.currentList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    class BaseViewHolder<VH : ViewDataBinding>(val binding: VH) :
        RecyclerView.ViewHolder(binding.root)
}