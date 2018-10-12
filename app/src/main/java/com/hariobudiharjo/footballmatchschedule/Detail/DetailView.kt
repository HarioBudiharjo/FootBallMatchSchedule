package com.hariobudiharjo.footballmatchschedule.Detail

import com.hariobudiharjo.footballmatchschedule.Model.matchResponse

interface DetailView {
    fun showLoading()
    fun hideLoading()
    fun showDetail(data: matchResponse, badgeHome: String, badgeAway: String)
}