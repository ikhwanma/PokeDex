package com.ikhwanma.base_ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding

abstract class BaseDialogFragment<VM: ViewModel, VB: ViewBinding>: DialogFragment() {
    private var _binding: VB? = null
    protected val binding get() = _binding!!
    abstract val viewModel: VM?

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = getViewBinding()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()
        setUpObserver()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    protected open fun setUpObserver(){}
    protected open fun setUpView(){}
    abstract fun getViewBinding():VB
}