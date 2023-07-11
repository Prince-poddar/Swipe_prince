package com.example.swipe.di

import com.example.swipe.ui.viewModels.AddProductViewModel
import com.example.swipe.ui.viewModels.ProductViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { ProductViewModel((get())) }
    viewModel { AddProductViewModel((get())) }

}


