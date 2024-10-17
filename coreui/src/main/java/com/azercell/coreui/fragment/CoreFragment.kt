package com.azercell.coreui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import timber.log.Timber

typealias InflateFragment<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

/**
 * Abstract base class for fragments that use ViewBinding.
 *
 * This class provides a convenient way to work with ViewBinding in Fragments by handling the inflation
 * of the binding. Subclasses should provide a lambda function for inflating the ViewBinding instance.
 *
 * @param VB The type of ViewBinding associated with the fragment.
 * @property inflate A lambda function used to inflate the ViewBinding instance.
 */
abstract class CoreFragment<VB : ViewBinding>(
    private val inflate: InflateFragment<VB>
) : Fragment() {

    /**
     * The ViewBinding instance associated with the fragment.
     */
    private var _binding: VB? = null
    val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.v("Create fragment %s", this.javaClass.name)
    }

    /**
     * Called to have the fragment instantiate its user interface view.
     *
     * This method inflates the ViewBinding instance using the provided lambda function and returns
     * its root view.
     *
     * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state.
     * @return Return the View for the fragment's UI, or null.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater, container, false)
        return binding.root
    }

    /**
     * Called when the view previously created by onCreateView(LayoutInflater, ViewGroup, Bundle)
     * has been detached from the fragment.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
