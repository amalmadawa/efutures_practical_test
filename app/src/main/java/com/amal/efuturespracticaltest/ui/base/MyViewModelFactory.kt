package com.amal.efuturespracticaltest.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.amal.efuturespracticaltest.data.repo.MainRepository
import com.amal.efuturespracticaltest.ui.viewmodel.CasesViewModel
import com.amal.efuturespracticaltest.ui.viewmodel.ScenariosViewModel

class MyViewModelFactory (private val repository: MainRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ScenariosViewModel::class.java)) {
            ScenariosViewModel(this.repository) as T
        }else if(modelClass.isAssignableFrom(CasesViewModel::class.java)){
            CasesViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}

