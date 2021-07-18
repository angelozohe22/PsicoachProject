package com.example.psicoachproject.ui.modules.home.client.fragments.adapter

import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.psicoachproject.R
import com.example.psicoachproject.databinding.ItemEventBinding
import com.example.psicoachproject.domain.model.MeetingEvent

/**
 * Created by Angelo on 7/5/2021
 */
class EventAdapter: RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    private var _listEvents = emptyList<MeetingEvent>()

    fun setData(data: List<MeetingEvent>){
        this._listEvents = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EventAdapter.EventViewHolder {
        return EventViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false))
    }

    override fun onBindViewHolder(holder: EventAdapter.EventViewHolder, position: Int) {
        val event = _listEvents[position]
        holder.bindView(event)
    }

    override fun getItemCount(): Int = _listEvents.size

    inner class EventViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val eventBinding = ItemEventBinding.bind(itemView)
        @RequiresApi(Build.VERSION_CODES.N)
        fun bindView(event: MeetingEvent){
            eventBinding.apply {
                lblPackage.text = event.packageName
                lblIssue.text   = event.issue
                lblDate.text    = "Fecha: ${event.date}"
                lblEndTime.text = "Termina a las: ${event.endTime} horas"
                val linkEvent = "<a href='https://meet.google.com/odh-ehoh-ubi'> https://meet.google.com/fjm-gyod-mvg </a>"
                lblLink.text    = "Link de evento: ${Html.fromHtml(linkEvent, Html.FROM_HTML_MODE_COMPACT)}"
            }
        }
    }

}