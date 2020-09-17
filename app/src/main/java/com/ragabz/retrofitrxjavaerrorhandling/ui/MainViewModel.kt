package com.ragabz.retrofitrxjavaerrorhandling.ui

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.ragabz.retrofitrxjavaerrorhandling.network.ApiError
import com.ragabz.retrofitrxjavaerrorhandling.network.ServerUnreachableException
import com.ragabz.retrofitrxjavaerrorhandling.network.mapNetworkErrors
import com.ragabz.retrofitrxjavaerrorhandling.data.local.repositories.ChallengesRepository
import com.ragabz.retrofitrxjavaerrorhandling.data.local.repositories.LoginRepository
import timber.log.Timber

class MainViewModel @ViewModelInject constructor(
    private val loginRepository: LoginRepository,
    private val challengesRepository: ChallengesRepository,
    @Assisted private val savedState: SavedStateHandle
) : ViewModel() {

    val errorState: MutableLiveData<String> by lazy {
        MutableLiveData()
    }


    fun login(mobileNumber: String) {

        loginRepository.requestOtp(mobileNumber)
            .subscribe({
                Timber.i("onSuccess() --> otpResponse is : ${it}")
            }, this::handleError)
    }

    fun loadChallengeList() {
        challengesRepository.requestChallengesList()
            .mapNetworkErrors()
            .subscribe(
                {
                    Timber.i("onSuccess() --> response is : $it")
                }, this::handleError
            )
    }

    private fun handleError(throwable: Throwable) {
        when (throwable) {
            is ApiError.BadRequest -> {
                errorState.postValue(throwable.errorResponse.toString())
                Timber.e("onError() --> error: ${throwable.errorResponse.name}")
                Timber.e("onError() --> error: ${throwable.errorResponse.message}")
                Timber.e("onError() --> error: ${throwable.errorResponse.className}")
                Timber.e("onError() --> error: ${throwable.errorResponse.errors}")
                Timber.e("onError() --> error: ${throwable.errorResponse.code}")
            }

            is ApiError.TooManyRequest -> {
                errorState.postValue(throwable.errorResponse.toString())
                Timber.e("onError() --> error: ${throwable.errorResponse.code}")
                Timber.e("onError() --> error: ${throwable.errorResponse.message}")
            }
            is ApiError.Unauthorized -> {
                errorState.postValue(throwable.errorResponse.toString())
                Timber.e("onError() --> error: ${throwable.errorResponse.name}")
                Timber.e("onError() --> error: ${throwable.errorResponse.message}")
                Timber.e("onError() --> error: ${throwable.errorResponse.className}")
                Timber.e("onError() --> error: ${throwable.errorResponse.errors}")
                Timber.e("onError() --> error: ${throwable.errorResponse.code}")
            }
            is ServerUnreachableException -> {
                errorState.postValue(throwable.toString())
                Timber.e("onError() --> error: ServerUnreachableException")
            }
        }
    }
}