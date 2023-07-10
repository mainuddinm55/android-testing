package com.example.testingtemplate.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.example.testingtemplate.data.models.Quote
import com.example.testingtemplate.databinding.RowItemQuoteBinding
import com.example.testingtemplate.ui.base.BaseAdapter

class QuoteAdapter(
    listener: (
        item: Quote, view: View, position: Int
    ) -> Unit
) : BaseAdapter<Quote, RowItemQuoteBinding>(listener) {

    override fun itemDiffUtilCallback(): DiffUtil.ItemCallback<Quote> {
        return object : DiffUtil.ItemCallback<Quote>() {
            override fun areItemsTheSame(oldItem: Quote, newItem: Quote): Boolean {
                return oldItem._id == newItem._id
            }

            override fun areContentsTheSame(oldItem: Quote, newItem: Quote): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun inflateBinding(parent: ViewGroup, viewType: Int): RowItemQuoteBinding {
        return RowItemQuoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<RowItemQuoteBinding>, position: Int) {
        val item = getItem(position)
        holder.binding.quote = item
    }

}