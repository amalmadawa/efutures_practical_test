package com.amal.efuturespracticaltest.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amal.efuturespracticaltest.data.model.Cases
import com.amal.efuturespracticaltest.data.repo.MainRepository
import kotlinx.coroutines.*


class CasesViewModel(private val repository: MainRepository) : ViewModel() {

    val cases = MutableLiveData<List<Cases>>()
    val errorMessage = MutableLiveData<String>()

    var job: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage} ${throwable.message}")
    }
    val loading = MutableLiveData<Boolean>()

    fun getCase(id: Int) {

        loading.value = true;

        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {

            val response = repository.getCases(id)

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    cases.postValue(response.body())
                    loading.value = false
                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }
    }

    private fun onError(message: String) {
        errorMessage.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}