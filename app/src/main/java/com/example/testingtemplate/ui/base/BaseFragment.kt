package com.example.testingtemplate.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.MainThread
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<ViewModel : BaseViewModel, Binding : ViewDataBinding> : Fragment() {

    protected abstract val viewModel: ViewModel

    protected var binding: Binding? = null
        private set

    abstract fun inflateLayout(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): Binding

    @MainThread
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = inflateLayout(
            inflater = inflater, container = container, savedInstanceState = savedInstanceState
        )
        binding!!.lifecycleOwner = viewLifecycleOwner
        return binding!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}