package com.joel.ktorfitdemo.data.utils

import com.joel.ktorfitdemo.data.network.service.ApiService
import de.jensklingenberg.ktorfit.Ktorfit

object KtorfitUtils {

    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

    // This is only for demonstration purposes.
    // For production code, consider using a dependency injection framework like Koin.
    fun getService(): ApiService {
        return Ktorfit.Builder()
            .baseUrl(BASE_URL)
            .build()
            .create()
    }
}