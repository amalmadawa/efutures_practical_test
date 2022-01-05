package com.amal.efuturespracticaltest.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amal.efuturespracticaltest.data.model.Scenarios
import com.amal.efuturespracticaltest.data.repo.MainRepository
import kotlinx.coroutines.*


class ScenariosViewModel(private val repository: MainRepository) : ViewModel() {

    val scenariosList = MutableLiveData<List<Scenarios>>()
    val errorMessage = MutableLiveData<String>()

    //
    var job: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }
    val loading = MutableLiveData<Boolean>()

    fun getAllScenarios() {

        loading.value = true;

        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {

            val response = repository.getScenarios()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    scenariosList.postValue(response.body())
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