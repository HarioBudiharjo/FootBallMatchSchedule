package com.hariobudiharjo.footballmatchschedule.Team

import android.util.Log
import com.google.gson.Gson
import com.hariobudiharjo.footballmatchschedule.Model.teamModel
import com.hariobudiharjo.footballmatchschedule.Model.teamResponse
import com.hariobudiharjo.footballmatchschedule.Network.ApiMatch
import com.hariobudiharjo.footballmatchschedule.Util.CoroutineContextProvider
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class TeamPresenter(private val view: TeamView,
                    private val apiRepository: ApiMatch,
                    private val gson: Gson,
                    private val contextPool: CoroutineContextProvider = CoroutineContextProvider()) {
    private var matchs: MutableList<teamModel> = mutableListOf()

    fun getNextMatchDetail() {
        view.showLoading()
        doAsync {
            val data = gson.fromJson(apiRepository
                    .doRequest("https://www.thesportsdb.com/api/v1/json/1/lookup_all_teams.php?id=4328"),
                    teamResponse::class.java)
            matchs.clear()
            matchs.addAll(data.teams)
            Log.d("DEBUG", matchs.toString())

            uiThread {
                view.showDetail(matchs)
                view.hideLoading()
            }
        }
    }
}