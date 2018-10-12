package com.hariobudiharjo.footballmatchschedule.api

import com.hariobudiharjo.footballmatchschedule.Network.ApiMatch
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class ApiMatchTest {
    @Test
    fun testDoRequest() {
        val apiRepository = mock(ApiMatch::class.java)
        val url = "https://www.thesportsdb.com/api/v1/json/1/eventspastleague.php?id=4328"
        apiRepository.doRequest(url)
        verify(apiRepository).doRequest(url)
    }
}