package com.azercell.exchange.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.azercell.common.extensions.format
import com.azercell.common.extensions.setOnChangeListener
import com.azercell.common.extensions.toBigDecimalOrZero
import com.azercell.coreui.fragment.CoreFragment
import com.azercell.coreui.uistate.collectEvent
import com.azercell.coreui.uistate.render
import com.azercell.coreui.uistate.uiStateDiffRender
import com.azercell.exchange.CurrencyResult
import com.azercell.exchange.SelectCurrencyFragment
import com.azercell.exchange.databinding.FragmentExchangeBinding
import com.azercell.exchange.model.ExchangeEvent
import com.azercell.exchange.model.ExchangeState
import com.azercell.exchange.viewmodel.ExchangeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExchangeFragment : CoreFragment<FragmentExchangeBinding>(FragmentExchangeBinding::inflate) {


    private val viewModel: ExchangeViewModel by viewModels()
    private var isFocusChanged = false

    private val exchangeDiffer = uiStateDiffRender {
        ExchangeState::primaryCurrency{ text ->
            binding.primaryCurrency.text = text
        }
        ExchangeState::secondaryCurrency { text ->
            binding.secondaryCurrency.text = text
        }
        ExchangeState::primaryAmount {amount->
            binding.primaryField.setText(amount.format())
            binding.primaryField.setSelection(amount.format().length)
        }
        ExchangeState::secondaryAmount {amount->
            binding.secondaryField.setText(amount.format())
            binding.secondaryField.setSelection(amount.format().length)
        }
        ExchangeState::isLoading {isLoading->
            binding.loader.visibility = if(isLoading) View.VISIBLE else View.GONE
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewModel) {
            render(lifecycleOwner = viewLifecycleOwner, render = exchangeDiffer)
            collectEvent(lifecycle = lifecycle) { event ->
                when (event) {
                    is ExchangeEvent.SetAmount -> {

                    }
                }

            }
        }

        initListener()
        initOnClickListener()
        initObserver()
    }

    private fun initListener(){
        binding.primaryField.setOnFocusChangeListener { _, hasFocus ->
            isFocusChanged = hasFocus == true
        }


        binding.convert.setOnClickListener {
            if(isFocusChanged){
                viewModel.updatePrimaryAmount(binding.primaryField.text.toString().toBigDecimalOrZero())
            }else{
                viewModel.updateSecondAmount(binding.secondaryField.text.toString().toBigDecimalOrZero())
            }
        }
    }

    private fun initOnClickListener(){
        binding.primaryCurrency.setOnClickListener {
            val action = ExchangeFragmentDirections.actionExchangeFragmentToSelectCurrencyFragment(
                currencyList = viewModel.uiStateFlow.value.currencyList?.toTypedArray() ?: emptyArray(),
                isPrimary = true
            )
            findNavController().navigate(action)
        }

        binding.secondaryCurrency.setOnClickListener {
            val action = ExchangeFragmentDirections.actionExchangeFragmentToSelectCurrencyFragment(
                currencyList = viewModel.uiStateFlow.value.currencyList?.toTypedArray() ?: emptyArray(),
                isPrimary = false
            )
            findNavController().navigate(action)
        }
    }

    private fun initObserver(){
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<CurrencyResult>(
            SelectCurrencyFragment.CURRENCY_KEY
        )?.observe(viewLifecycleOwner) { result ->
            val isPrimary = result.first
            val currency = result.second
            if(isPrimary) {
                viewModel.setPrimaryCurrency(currency)
            }
            else {
                viewModel.setSecondaryCurrency(currency)
            }
        }
    }

}