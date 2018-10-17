package com.hariobudiharjo.footballmatchschedule.Team

import com.hariobudiharjo.footballmatchschedule.Model.teamModel

interface TeamView {
    fun showLoading()
    fun hideLoading()
    fun showDetail(data: MutableList<teamModel>)
}