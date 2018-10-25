package com.hariobudiharjo.footballmatchschedule.DetailTeam

import android.util.Log
import com.google.gson.Gson
import com.hariobudiharjo.footballmatchschedule.Model.teamModel
import com.hariobudiharjo.footballmatchschedule.Model.teamResponse
import com.hariobudiharjo.footballmatchschedule.Network.ApiMatch
import com.hariobudiharjo.footballmatchschedule.Util.CoroutineContextProvider
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class DetailTeamPresenter(private val view: DetailTeamView,
                          private val apiRepository: ApiMatch,
                          private val gson: Gson,
                          private val contextPool: CoroutineContextProvider = CoroutineContextProvider()) {
    private var teams: MutableList<teamModel> = mutableListOf()

    fun getTeamDetail(id:String) {
        view.showLoading()
        doAsync {
            val data = gson.fromJson(apiRepository
                    .doRequest("https://www.thesportsdb.com/api/v1/json/1/lookupteam.php?id=$id"),
                    teamResponse::class.java)
            teams.clear()
            teams.addAll(data.teams)
            Log.d("DEBUG", teams.toString())

            uiThread {
                view.showDetail(teams)
                view.hideLoading()
            }
        }
    }

}