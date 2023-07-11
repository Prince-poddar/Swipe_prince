package com.example.swipe

import android.app.Application
import com.example.swipe.di.RetrofitModule
import com.example.swipe.di.apiModule
import com.example.swipe.di.repositoryModule
import com.example.swipe.di.viewModelModule
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import timber.log.Timber
import org.koin.core.logger.Level
import org.koin.android.ext.koin.androidLogger




class SwipeApplication : Application() {
    companion object {
        lateinit var instance: SwipeApplication
            private set
    }
    override fun onCreate() {
        super.onCreate()
        if(BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@SwipeApplication)
            modules(listOf(
                viewModelModule,
                apiModule,
                repositoryModule,
                RetrofitModule
            ))

        }
        instance = this
    }
    private fun provideModules() = listOf(RetrofitModule, apiModule, repositoryModule, viewModelModule)

}