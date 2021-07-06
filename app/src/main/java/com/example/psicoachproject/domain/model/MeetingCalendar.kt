package com.example.psicoachproject.domain.model

/**
 * Created by Angelo on 7/5/2021
 */
data class MeetingCalendar(
    val eventList: List<MeetingEvent>,
    val pendingEventList: List<PendingMeetingEvent>
)
