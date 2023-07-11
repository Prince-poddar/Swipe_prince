package com.example.swipe.di

import com.example.swipe.networking.UserServices
import retrofit2.Retrofit
import org.koin.dsl.module

val apiModule = module {
    single(createdAtStart = false) { get<Retrofit>().create(UserServices::class.java) }
}