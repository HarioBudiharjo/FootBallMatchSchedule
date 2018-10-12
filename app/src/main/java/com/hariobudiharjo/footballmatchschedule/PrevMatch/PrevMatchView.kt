package com.hariobudiharjo.footballmatchschedule.PrevMatch

import com.hariobudiharjo.footballmatchschedule.Model.matchModel

interface PrevMatchView {
    fun showLoading()
    fun hideLoading()
    fun showDetail(data: MutableList<matchModel>)
}