package com.hariobudiharjo.footballmatchschedule.DetailTeam.PlayerTeam

import com.hariobudiharjo.footballmatchschedule.Model.playerModel
import com.hariobudiharjo.footballmatchschedule.Model.teamModel

interface PlayerTeamView {
    fun showLoading()
    fun hideLoading()
    fun showDetail(data: MutableList<playerModel>)
}