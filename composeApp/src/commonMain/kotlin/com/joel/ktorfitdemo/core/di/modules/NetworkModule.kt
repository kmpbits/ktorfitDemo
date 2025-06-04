package com.joel.ktorfitdemo.core.di.modules

import com.joel.ktorfitdemo.core.utils.Constants
import com.joel.ktorfitdemo.data.network.service.ApiService
import com.joel.ktorfitdemo.data.network.service.createApiService
import de.jensklingenberg.ktorfit.Ktorfit
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
class NetworkModule {

    @Single
    fun provideKtorfit(client: HttpClient): Ktorfit {
        return Ktorfit.Builder()
            .baseUrl(Constants.BASE_URL)
            .httpClient(client)
            .build()
    }

    @Single
    fun provideApiService(ktorfit: Ktorfit): ApiService {
        return ktorfit.createApiService()
    }

    @Single
    fun provideHttpClient(): HttpClient {
        return HttpClient {
            install(ContentNegotiation) {
                json()
            }
        }
    }
}