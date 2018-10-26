package com.hariobudiharjo.footballmatchschedule.DetailPlayer

import com.hariobudiharjo.footballmatchschedule.Model.playerModel

interface DetailPlayerView {
    fun showLoading()
    fun hideLoading()
    fun showDetail(data: MutableList<playerModel>)
}