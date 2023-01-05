package ru.netology.linked.data.repository.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.netology.linked.data.repository.AuthRepositoryImpl
import ru.netology.linked.data.repository.RepositoryImpl
import ru.netology.linked.domain.AuthRepository
import ru.netology.linked.domain.Repository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Singleton
    @Binds
    fun bindsRepository(impl: RepositoryImpl): Repository

    @Singleton
    @Binds
    fun bindsAuthRepository(impl: AuthRepositoryImpl): AuthRepository

}