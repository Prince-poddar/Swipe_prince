package com.example.swipe.di

import com.example.swipe.repository.UsersRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { UsersRepository(get()) }
}