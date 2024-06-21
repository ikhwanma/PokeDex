package com.ikhwanma.pokedex

import android.app.Application
import com.ikhwanma.database.di.databaseModule
import com.ikhwanma.database.di.localDataSourceModule
import com.ikhwanma.domain.di.useCaseModule
import com.ikhwanma.network.di.networkModule
import com.ikhwanma.network.di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class PokeDexApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@PokeDexApplication)
            modules(
                listOf(
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    databaseModule,
                    localDataSourceModule
                )
            )
        }
    }
}