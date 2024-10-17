package com.azercell.exchange

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.azercell.coreui.fragment.CoreFragment
import com.azercell.exchange.adapters.CurrencyListAdapter
import com.azercell.exchange.databinding.FragmentSelectCurrencyBinding
import dagger.hilt.android.AndroidEntryPoint


typealias CurrencyResult = Pair<Boolean, String>

@AndroidEntryPoint
class SelectCurrencyFragment : CoreFragment<FragmentSelectCurrencyBinding>(FragmentSelectCurrencyBinding::inflate) {

    private val args : SelectCurrencyFragmentArgs by navArgs()

    private val navController by lazy { findNavController() }

    private lateinit var currencyListAdapter: CurrencyListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currencyListAdapter = CurrencyListAdapter(
            callback = { currency ->

                navController.previousBackStackEntry?.savedStateHandle?.set(
                    CURRENCY_KEY, CurrencyResult(args.isPrimary, currency)
                )
                navController.popBackStack()
            }
        )

        currencyListAdapter.submitList(args.currencyList.toList())

        binding?.apply {
            currencyList.adapter = currencyListAdapter
        }
    }

    companion object {
        const val CURRENCY_KEY = "CURRENCY_KEY"
    }
}