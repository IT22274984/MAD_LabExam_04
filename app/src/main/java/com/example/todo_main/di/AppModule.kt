package com.example.todo_main.di

import android.content.Context
import android.content.SharedPreferences
import com.example.todo_main.util.SharedPrefConstants
import com.google.android.datatransport.runtime.dagger.Module
import com.google.gson.Gson
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    class SingletonComponent {

    }

    @Provides
    @Singleton
    fun provideSharedPref(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(SharedPrefConstants.LOCAL_SHARED_PREF, Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }

}

class Gson {

}

annotation class ApplicationContext

annotation class InstallIn(val value: Any)
