package com.example.psicoachproject.domain.repository.home

import com.example.psicoachproject.data.remote.source.dto.CommentResponse
import com.example.psicoachproject.data.remote.source.dto.PendingResponse
import com.example.psicoachproject.data.remote.source.home.HomeRemoteDataSource
import com.example.psicoachproject.domain.model.Comment
import com.example.psicoachproject.domain.model.MeetingCalendar
import com.example.psicoachproject.domain.model.MeetingEvent
import com.example.psicoachproject.domain.model.PendingMeetingEvent
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class HomeRepositoryImplTest{

    @RelaxedMockK
    private lateinit var remoteDataSource: HomeRemoteDataSource

    lateinit var homeRepository: HomeRepositoryImpl

    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
        homeRepository = HomeRepositoryImpl(remoteDataSource)
    }

    @Test
    fun `when the api returns a list of appointments to client`() {
        runBlocking {
            //Given
            val pendingList = emptyList<PendingMeetingEvent>()
            val appointmentList = listOf(
                MeetingEvent(
                    idAppointment = 1384,
                    packageName = "Paquete: SESION - Tema: Depresión",
                    issue = "Depresión",
                    date = "2022-07-09T00:00:00.000Z",
                    dateFormat = "Sábado 9 de Julio de 2022",
                    startTime = "10:00",
                    endTime = "10:45",
                    description = "Mensaje descripcion",
                    link = "https://meet.google.com/uyx-stdr-azn",
                    comments = emptyList(),
                    state = ""
                )
            )
            coEvery { remoteDataSource.getMeetingsCalendar("2022","07") } returns MeetingCalendar(appointmentList, pendingList)

            //When
            homeRepository.getMeetingsCalendar("2022","07")

        }
    }

    @Test
    fun `when the api returns a list of appointments to secretary`() {
        runBlocking {
            //Given
            val pendingList = emptyList<PendingMeetingEvent>()
            val appointmentList = listOf(
                MeetingEvent(
                    idAppointment = 1384,
                            packageName = "Paquete: SESION - Tema: Depresión",
                            issue = "Depresión",
                            date = "2022-07-09T00:00:00.000Z",
                            dateFormat = "Sábado 9 de Julio de 2022",
                            startTime = "10:00",
                            endTime = "10:45",
                            description = "Mensaje descripcion",
                            link = "https://meet.google.com/uyx-stdr-azn",
                            comments = emptyList(),
                            state = ""
                )
            )
            coEvery { remoteDataSource.getMeetingsCalendar("2022","07") } returns MeetingCalendar(appointmentList, pendingList)

            //When
            homeRepository.getMeetingsCalendar("2022","07")

        }
    }

    @Test
    fun `when the api returns a list of appointments for a day to client`() {
        runBlocking {
            //Given
            val pendingList = emptyList<PendingMeetingEvent>()
            val appointmentList = listOf(
                MeetingEvent(
                    idAppointment = 1384,
                    packageName = "Paquete: SESION - Tema: Depresión",
                    issue = "Depresión",
                    date = "2022-07-09T00:00:00.000Z",
                    dateFormat = "Sábado 9 de Julio de 2022",
                    startTime = "10:00",
                    endTime = "10:45",
                    description = "Mensaje descripcion",
                    link = "https://meet.google.com/uyx-stdr-azn",
                    comments = emptyList(),
                    state = ""
                ),
                MeetingEvent(
                    idAppointment = 1384,
                    packageName = "Paquete: SESION - Tema: Depresión",
                    issue = "Depresión",
                    date = "2022-06-30T00:00:00.000Z",
                    dateFormat = "Jueves 30 de Junio de 2022",
                    startTime = "10:00",
                    endTime = "10:45",
                    description = "Mensaje descripcion",
                    link = "https://meet.google.com/uyx-stdr-azn",
                    comments = emptyList(),
                    state = ""
                ),
                MeetingEvent(
                    idAppointment = 1384,
                    packageName = "Paquete: SESION - Tema: Depresión",
                    issue = "Depresión",
                    date = "2022-06-29T00:00:00.000Z",
                    dateFormat = "Miercoles 29 de Junio de 2022",
                    startTime = "10:00",
                    endTime = "10:45",
                    description = "Mensaje descripcion",
                    link = "https://meet.google.com/uyx-stdr-azn",
                    comments = emptyList(),
                    state = ""
                )
            )
            coEvery { remoteDataSource.getMeetingsCalendar("2022","07") } returns MeetingCalendar(appointmentList, pendingList)

            //When
            homeRepository.getMeetingsCalendar("2022","07")

        }
    }

    @Test
    fun `when pending appointment will be approved`() {
        runBlocking {
            //Given
            coEvery { remoteDataSource.changeStateAppointment("12","1") } returns "Se aprobo la cita"

            //When
            homeRepository.changeStateAppointment("12", "1")

            //Then
            coVerify { remoteDataSource.getPendingList() }
        }
    }

}