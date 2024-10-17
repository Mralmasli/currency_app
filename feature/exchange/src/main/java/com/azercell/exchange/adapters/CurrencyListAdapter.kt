package com.azercell.exchange.adapters

import androidx.recyclerview.widget.DiffUtil
import com.azercell.coreui.ui.BaseAdapter
import com.azercell.coreui.ui.Binder
import com.azercell.coreui.ui.Inflater
import com.azercell.exchange.databinding.ViewCurrencyItemBinding

class CurrencyListAdapter(
    val callback: (String) -> Unit
) : BaseAdapter<String, ViewCurrencyItemBinding>(
    diffCallBack = object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }
) {
    override val bindingInflater: Inflater<ViewCurrencyItemBinding>
        get() = ViewCurrencyItemBinding::inflate

    override val itemBinder: Binder<String, ViewCurrencyItemBinding> = { item, _ ->
        currencyText.text = item
        currencyContainer.setOnClickListener {
            callback(item)
        }
    }
}