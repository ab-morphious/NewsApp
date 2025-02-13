package com.appsolutely.newsapp.di

import com.appsolutely.newsapp.data.api.NewsApiService
import com.appsolutely.newsapp.data.datasource.remote.NewsRemoteDataSource
import com.appsolutely.newsapp.data.repository.NewsRepositoryImpl
import com.appsolutely.newsapp.domain.repository.NewsRepository
import com.appsolutely.newsapp.domain.usecase.GetNewsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideNewsApiService(retrofit: Retrofit): NewsApiService {
        return retrofit.create(NewsApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideNewsRemoteDataSource(apiService: NewsApiService): NewsRemoteDataSource {
        return NewsRemoteDataSource(apiService)
    }

    @Provides
    @Singleton
    fun provideNewsRepository(remoteDataSource: NewsRemoteDataSource): NewsRepository {
        return NewsRepositoryImpl(remoteDataSource)
    }

    @Provides
    @Singleton
    fun provideGetNewsUseCase(repository: NewsRepository): GetNewsUseCase {
        return GetNewsUseCase(repository)
    }
}
