package com.creativeapps.partyapp.ui.home.events

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.creativeapps.partyapp.R
import com.creativeapps.partyapp.util.Coroutines
import com.creativeapps.partyapp.util.toast
import org.kodein.di.android.x.kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance

class EventsFragment : Fragment(), KodeinAware {
    override val kodein by kodein()

    private val factory: EventsViewModelFactory by instance()

    private lateinit var viewModel: EventsViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.events_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, factory).get(EventsViewModel::class.java)

        Coroutines.main {
            val events = viewModel.events.await()
            events.observe(this, Observer {
                context?.toast(it.size.toString())
            })
        }
    }
}