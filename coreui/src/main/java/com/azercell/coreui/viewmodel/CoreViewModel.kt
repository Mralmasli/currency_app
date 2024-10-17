package com.azercell.coreui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import az.unibank.coreui.state.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class CoreViewModel<S:State>(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    /**
     * Internal mutable state flow representing the UI state.
     * This flow is initialized with the value retrieved from the saved state handle, if available.
     */
    private val _uiState: MutableStateFlow<S?> = MutableStateFlow(
        savedStateHandle[UI_STATE]
    )

    /**
     * Exposed immutable state flow representing the UI state.
     * Consumers of this ViewModel should observe this flow to receive updates about UI state changes.
     */
    val uiSavedState = _uiState.asStateFlow()

    /**
     * Saves the provided UI state to the saved state handle.
     * This method should be called before accessing the UI state to ensure it's properly persisted.
     *
     * @param state The UI state to be saved.
     */
    open fun saveState(state: State) {
        savedStateHandle[UI_STATE] = state
    }

    companion object {
        // Key for storing and retrieving UI state from the saved state handle.
        private const val UI_STATE = "ui_state"
    }

}

