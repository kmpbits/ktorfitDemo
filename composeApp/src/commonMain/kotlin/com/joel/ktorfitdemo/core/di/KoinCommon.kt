package com.joel.ktorfitdemo.core.di

import com.joel.ktorfitdemo.core.di.modules.NetworkModule
import com.joel.ktorfitdemo.core.di.modules.RepositoryModule
import org.koin.core.KoinApplication
import org.koin.core.annotation.Module
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.ksp.generated.module

fun initApplication(appDeclaration: KoinAppDeclaration = {}): KoinApplication {
    return startKoin {
        appDeclaration()
        modules(
            AppModule().module,
        )
    }
}

@Module(
    includes = [
        RepositoryModule::class,
        NetworkModule::class,
    ]
)
class AppModule

@Suppress("unused") //using in iOS
fun initKoin() = initApplication {}