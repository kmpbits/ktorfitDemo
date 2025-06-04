package com.joel.ktorfitdemo.core.di.modules

import com.joel.ktorfitdemo.core.utils.Constants
import com.joel.ktorfitdemo.data.network.service.ApiService
import com.joel.ktorfitdemo.data.network.service.createApiService
import de.jensklingenberg.ktorfit.Ktorfit
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
class NetworkModule {

    @Single
    fun provideKtorfit(): Ktorfit {
        return Ktorfit.Builder()
            .baseUrl(Constants.BASE_URL)
            .build()
    }

    @Single
    fun provideApiService(ktorfit: Ktorfit): ApiService {
        return ktorfit.createApiService()
    }
}