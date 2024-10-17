package com.azercell.exchange.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.azercell.coreui.state.UiStateDelegate
import com.azercell.coreui.state.UiStateDelegateImpl
import com.azercell.coreui.viewmodel.CoreViewModel
import com.azercell.domain.model.ResultWrapper
import com.azercell.domain.usecases.ExchangeUseCase
import com.azercell.domain.usecases.GetCurrencyListUseCase
import com.azercell.exchange.model.ExchangeEvent
import com.azercell.exchange.model.ExchangeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject

@HiltViewModel
class ExchangeViewModel @Inject constructor(
    private val getCurrencyListUseCase: GetCurrencyListUseCase,
    private val exchangeUseCase: ExchangeUseCase,
    savedStateHandle: SavedStateHandle
) : CoreViewModel<ExchangeState>(savedStateHandle),
    UiStateDelegate<ExchangeState, ExchangeEvent> by UiStateDelegateImpl(ExchangeState()) {

    init {
        getCurrencyList()
    }

    private fun getCurrencyList() {
        viewModelScope.launch {
            when (val result = getCurrencyListUseCase.invoke()) {
                is ResultWrapper.Error -> {}
                is ResultWrapper.Success -> {
                    updateUiState { s ->
                        s.copy(currencyList = result.success, isLoading = false)
                    }
                }
            }

        }
    }

    fun updatePrimaryAmount(amount: BigDecimal) {
        viewModelScope.launch {
            updateUiState { s ->
                s.copy(primaryAmount = amount, secondaryAmount = amount.multiply(s.exchangeValue))
            }
        }
    }

    fun updateSecondAmount(amount: BigDecimal) {
        viewModelScope.launch {
            updateUiState { s ->
                s.copy(
                    secondaryAmount = amount,
                    primaryAmount = amount.divide(s.exchangeValue, 4, RoundingMode.HALF_UP)
                )
            }
        }
    }

    fun setPrimaryCurrency(value:String){
        viewModelScope.launch {
            updateUiState { s->
                s.copy(primaryCurrency = value)
            }
            exchange(true)
        }
    }

    fun setSecondaryCurrency(value:String){
        viewModelScope.launch {
            updateUiState { s->
                s.copy(secondaryCurrency = value)
            }
            exchange(false)
        }
    }

    fun exchange(isPrimary: Boolean) {
        viewModelScope.launch {
            updateUiState { s -> s.copy(isLoading = true) }
            val result = exchangeUseCase.invoke(
                ExchangeUseCase.Param(
                    from = uiState.primaryCurrency,
                    to = uiState.secondaryCurrency
                )
            )
            when(result){
                is ResultWrapper.Error -> {
                    updateUiState { s -> s.copy(isLoading = false) }
                }
                is ResultWrapper.Success -> {
                    updateUiState { s -> s.copy(isLoading = false, exchangeValue = result.success) }
                }
            }
        }
    }
}