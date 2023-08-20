package com.example.boxgroceries.di

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.example.boxgroceries.data.AppDatabase
import com.example.boxgroceries.data.Item
import com.example.boxgroceries.data.ItemDao
import com.example.boxgroceries.data.ItemsRepository
import com.example.boxgroceries.data.OfflineItemsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
//    appContext: Application
    fun providesDatabase(@ApplicationContext appContext: Context): AppDatabase =
        Room.databaseBuilder(
            appContext,
            AppDatabase::class.java, "BoxGroceries"
        ).build()


    @Provides
    fun providesItemDao(appDatabase: AppDatabase): ItemDao = appDatabase.itemDao()


    @Singleton
    @Provides
    fun providesItemsRepository(itemDao: ItemDao): ItemsRepository {
        println("Called providesItemsRepository")
        return OfflineItemsRepository(itemDao)
    }
}