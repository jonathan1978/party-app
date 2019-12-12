package com.creativeapps.partyapp.ui.home.events

import com.creativeapps.partyapp.R
import com.creativeapps.partyapp.data.db.entities.Event
import com.creativeapps.partyapp.databinding.ItemEventBinding
import com.xwray.groupie.databinding.BindableItem

class EventItem(
        private val event: Event
) : BindableItem<ItemEventBinding>(){
    override fun getLayout() = R.layout.item_event

    override fun bind(viewBinding: ItemEventBinding, position: Int) {
        viewBinding.setEvent(event)
    }
}