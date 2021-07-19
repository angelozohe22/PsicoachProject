package com.example.psicoachproject.ui.modules.home.client.fragments.adapter

import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.psicoachproject.R
import com.example.psicoachproject.core.Constants
import com.example.psicoachproject.core.aplication.preferences
import com.example.psicoachproject.databinding.ItemEventBinding
import com.example.psicoachproject.domain.model.MeetingEvent

/**
 * Created by Angelo on 7/5/2021
 */
class EventAdapter(
): RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

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

        fun bindView(event: MeetingEvent){
            eventBinding.apply {
                lblPackage.text = "Paquete: ${event.packageName}"
                lblIssue.text   = "Tema: ${event.issue}"
                lblTime.text = "Hora: ${event.startTime} / ${event.endTime}"
                val linkEvent = "<a href='${event.link}'> ${event.link} </a>"
                lblLink.text    = "Link de evento: ${Html.fromHtml(linkEvent, Html.FROM_HTML_MODE_COMPACT)}"
                if (event.state == Constants.STATE_PENDING){
                    lblStatus.text =  "Subir voucher"
                    imgIconPending.setBackgroundResource(R.drawable.ic_pencil)
                }else{
                    lblStatus.text =  "Pagado"
                    imgIconPending.setBackgroundResource(R.drawable.ic_pencil)
                    imgIconPending.isClickable = false
                }
            }
        }
    }

    interface EventListener{
        fun goToDetails(event: MeetingEvent)
    }

}