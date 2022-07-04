package com.example.psicoachproject.domain.repository.home

import com.example.psicoachproject.data.remote.source.dto.PendingResponse
import com.example.psicoachproject.data.remote.source.home.HomeRemoteDataSource
import com.example.psicoachproject.data.remote.source.home.HomeRemoteDataSourceImpl
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
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
    fun `when api returns pending list data`() {
        runBlocking {
            val pendingTestList = listOf<PendingResponse>(PendingResponse(0, "Basico", "Malestar"))
            coEvery { remoteDataSource.getPendingList() } returns pendingTestList

            homeRepository.getPendingList()

            coVerify { }
        }
    }

}