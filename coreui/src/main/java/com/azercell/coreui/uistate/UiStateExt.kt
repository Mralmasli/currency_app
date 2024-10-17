package com.azercell.coreui.uistate

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.azercell.coreui.state.UiStateDelegate
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Helper function for rendering UI states with lifecycle-aware management in Fragments.
 * This function sets up a [UiStateDiffRender] for observing changes in UI states and automatically clears
 * the render when the Fragment's view is destroyed, preventing potential memory leaks.
 *
 *
 * @param init Lambda function for configuring the [UiStateDiffRender.Builder] instance.
 * @return Instance of [UiStateDiffRender] for observing UI state changes.
 */
inline fun <T> Fragment.uiStateDiffRender(
    init: UiStateDiffRender.Builder<T>.() -> Unit
): UiStateDiffRender<T> {

    var render: UiStateDiffRender<T>? = null

    lifecycle.addObserver(object : DefaultLifecycleObserver {
        val viewLifecycleOwnerLiveDataObserver = Observer<LifecycleOwner?> {
            val viewLifecycleOwner = it ?: return@Observer

            viewLifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
                override fun onDestroy(owner: LifecycleOwner) {
                    render?.clear()
                }
            })
        }

        override fun onCreate(owner: LifecycleOwner) {
            viewLifecycleOwnerLiveData.observeForever(viewLifecycleOwnerLiveDataObserver)
        }

        override fun onDestroy(owner: LifecycleOwner) {
            viewLifecycleOwnerLiveData.removeObserver(viewLifecycleOwnerLiveDataObserver)
            render = null
        }
    })

    return UiStateDiffRender.Builder<T>()
        .apply(init)
        .build().apply {
            render = this
        }
}

/**
 * Helper function for rendering UI states with lifecycle-aware management in Activity.
 * This function sets up a [UiStateDiffRender] for observing changes in UI states and automatically clears
 * the render when the Fragment's view is destroyed, preventing potential memory leaks.
 *
 * @param init Lambda function for configuring the [UiStateDiffRender.Builder] instance.
 * @return Instance of [UiStateDiffRender] for observing UI state changes.
 */
inline fun <T> AppCompatActivity.uiStateDiffRender(
    init: UiStateDiffRender.Builder<T>.() -> Unit
): UiStateDiffRender<T> {

    var render: UiStateDiffRender<T>? = null

    lifecycle.addObserver(object : DefaultLifecycleObserver {

        override fun onDestroy(owner: LifecycleOwner) {
            render?.clear()
            render = null
        }
    })

    return UiStateDiffRender.Builder<T>()
        .apply(init)
        .build().apply {
            render = this
        }
}

/**
 * Function for rendering UI states using a provided [UiStateDiffRender] instance and a [LifecycleOwner]'s lifecycle.
 * This function collects UI states from the [uiStateFlow] of a [UiStateDelegate] and passes them to the provided
 * [UiStateDiffRender] for rendering. It operates within the specified lifecycle state of the lifecycleOwner to ensure
 * proper management and handling of UI state updates.
 *
 * @param lifecycleOwner The LifecycleOwner whose lifecycle will be observed for rendering UI states.
 * @param lifecycleState The minimum lifecycle state at which the rendering will be active. Defaults to [Lifecycle.State.STARTED].
 * @param render The [UiStateDiffRender] instance responsible for rendering UI states.
 * @return A [Job] representing the rendering process.
 */
fun <State: az.unibank.coreui.state.State, Event> UiStateDelegate<State, Event>.render(
    lifecycleOwner: LifecycleOwner,
    lifecycleState: Lifecycle.State = Lifecycle.State.STARTED,
    render: UiStateDiffRender<State>
): Job = lifecycleOwner.lifecycleScope.launch {
    uiStateFlow.flowWithLifecycle(
        lifecycle = lifecycleOwner.lifecycle,
        minActiveState = lifecycleState,
    ).collectLatest(render::render)
}

/**
 * render [State] with [AppCompatActivity]
 * The UI re-renders based on the new state
 **/
fun <State: az.unibank.coreui.state.State, Event> UiStateDelegate<State, Event>.render(
    lifecycle: Lifecycle,
    lifecycleState: Lifecycle.State = Lifecycle.State.STARTED,
    render: UiStateDiffRender<State>
): Job = lifecycle.coroutineScope.launch {
    uiStateFlow.flowWithLifecycle(
        lifecycle = lifecycle,
        minActiveState = lifecycleState,
    ).collectLatest(render::render)
}

/**
 * Function for collecting single events emitted by a [UiStateDelegate] within a specified lifecycle state of a [LifecycleOwner].
 * This function observes the [singleEvents] flow of a [UiStateDelegate], collecting and executing the provided block for each emitted event.
 * It operates within the specified lifecycle state of the lifecycleOwner to ensure proper event handling.
 *
 * @param lifecycleOwner The LifecycleOwner whose lifecycle will be observed for collecting single events.
 * @param lifecycleState The minimum lifecycle state at which event collection will be active. Defaults to [Lifecycle.State.RESUMED].
 * @param block The block of code to be executed for each emitted event.
 * @return A [Job] representing the event collection process.
 */
fun <State: az.unibank.coreui.state.State, Event> UiStateDelegate<State, Event>.collectEvent(
    lifecycleOwner: LifecycleOwner,
    lifecycleState: Lifecycle.State = Lifecycle.State.RESUMED,
    block: (event: Event) -> Unit
): Job = lifecycleOwner.lifecycleScope.launch {
    singleEvents.flowWithLifecycle(
        lifecycle = lifecycleOwner.lifecycle,
        minActiveState = lifecycleState,
    ).collect { event ->
        block.invoke(event)
    }
}

/**
 * send [Event] with [AppCompatActivity]
 * The UI re-renders based on the new event
 **/
fun <State: az.unibank.coreui.state.State, Event> UiStateDelegate<State, Event>.collectEvent(
    lifecycle: Lifecycle,
    lifecycleState: Lifecycle.State = Lifecycle.State.RESUMED,
    block: (event: Event) -> Unit
): Job = lifecycle.coroutineScope.launch {
    singleEvents.flowWithLifecycle(
        lifecycle = lifecycle,
        minActiveState = lifecycleState,
    ).collect {
        block(it)
    }
}

