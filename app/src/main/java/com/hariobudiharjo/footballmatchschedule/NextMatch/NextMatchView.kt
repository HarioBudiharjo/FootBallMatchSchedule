package com.hariobudiharjo.footballmatchschedule.NextMatch

import com.hariobudiharjo.footballmatchschedule.Model.matchModel

interface NextMatchView {
    fun showLoading()
    fun hideLoading()
    fun showDetail(data: MutableList<matchModel>)
}