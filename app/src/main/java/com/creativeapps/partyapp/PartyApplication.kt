package com.creativeapps.partyapp

import android.app.Application
import com.creativeapps.partyapp.data.db.AppDatabase
import com.creativeapps.partyapp.data.network.MyApi
import com.creativeapps.partyapp.data.network.NetworkConnectionInterceptor
import com.creativeapps.partyapp.data.repositories.UserRepository
import com.creativeapps.partyapp.ui.auth.AuthViewModelFactory
import com.creativeapps.partyapp.ui.home.profile.ProfileViewModel
import com.creativeapps.partyapp.ui.home.profile.ProfileViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class PartyApplication : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@PartyApplication))

        bind() from singleton { NetworkConnectionInterceptor(instance()) }
        bind() from singleton { MyApi(instance()) }
        bind() from singleton { AppDatabase(instance()) }
        bind() from singleton { UserRepository(instance(), instance()) }
        bind() from provider { AuthViewModelFactory(instance()) }
        bind() from provider { ProfileViewModelFactory(instance()) }
    }
}