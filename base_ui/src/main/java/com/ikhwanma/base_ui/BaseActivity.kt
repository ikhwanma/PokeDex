package com.ikhwanma.base_ui

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VM: ViewModel, VB: ViewBinding>: AppCompatActivity() {
    protected lateinit var binding: VB
    protected abstract val viewModel: VM?

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        binding = getViewBinding()
        setContentView(binding.root)
    }

    override fun onPostCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onPostCreate(savedInstanceState, persistentState)
        setUpView()
        setUpObserver()
    }

    protected open fun setUpObserver(){}
    protected open fun setUpView(){}
    abstract fun getViewBinding():VB
}