package com.ikhwanma.network.di

import com.ikhwanma.domain.repository.IPokemonRepository
import com.ikhwanma.network.repository.PokemonRepository
import com.ikhwanma.network.utils.PokemonDataSource
import com.ikhwanma.network.service.PokemonService
import com.ikhwanma.common.utils.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(PokemonService::class.java)
    }
}

val repositoryModule = module {
    single { PokemonDataSource(get()) }
    single<IPokemonRepository> {
        PokemonRepository(
            get(),
            get()
        )
    }
}