package com.hariobudiharjo.footballmatchschedule.DetailTeam

import com.hariobudiharjo.footballmatchschedule.Model.teamModel

interface DetailTeamView {
    fun showLoading()
    fun hideLoading()
    fun showDetail(data: MutableList<teamModel>)
}