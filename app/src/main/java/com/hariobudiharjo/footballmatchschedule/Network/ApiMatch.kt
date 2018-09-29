package com.hariobudiharjo.footballmatchschedule.Network

import java.net.URL

class ApiMatch {
    fun doRequest(url: String): String {
        return URL(url).readText()
    }
}