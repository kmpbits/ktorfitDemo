package com.joel.ktorfitdemo.core.di.modules

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module
@ComponentScan("com.joel.ktorfitdemo.data.repository")
class RepositoryModule