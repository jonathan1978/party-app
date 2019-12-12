package com.creativeapps.partyapp.ui.home.events

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.creativeapps.partyapp.R
import com.creativeapps.partyapp.data.db.entities.Event
import com.creativeapps.partyapp.util.Coroutines
import com.creativeapps.partyapp.util.hide
import com.creativeapps.partyapp.util.show
import com.creativeapps.partyapp.util.toast
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.events_fragment.*
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
        bindUI()
    }

    private fun bindUI() = Coroutines.main {
        progress_bar.show()
        viewModel.events.await().observe(this, Observer {
            progress_bar.hide()
            initRecyclerView(it.toEventItem())
        })

    }

    private fun initRecyclerView(eventItem: List<EventItem>) {
        val mAdapter = GroupAdapter<ViewHolder>().apply {
            addAll(eventItem)
        }

        recyclerview.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = mAdapter
        }
    }

    private fun List<Event>.toEventItem() : List<EventItem>{
        return this.map {
            EventItem(it)
        }
    }
}