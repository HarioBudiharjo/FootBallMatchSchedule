package com.hariobudiharjo.footballmatchschedule.Detail

import com.google.gson.Gson
import com.hariobudiharjo.footballmatchschedule.Model.matchResponse
import com.hariobudiharjo.footballmatchschedule.Model.teamResponse
import com.hariobudiharjo.footballmatchschedule.Network.ApiMatch
import com.hariobudiharjo.footballmatchschedule.Util.CoroutineContextProvider
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class DetailPresenter(private val view: DetailView,
                      private val apiRepository: ApiMatch,
                      private val gson: Gson,
                      private val contextPool: CoroutineContextProvider = CoroutineContextProvider()) {
    fun getTeamDetail(idevent: String) {
        view.showLoading()
        doAsync {
            val data = gson.fromJson(apiRepository
                    .doRequest("https://www.thesportsdb.com/api/v1/json/1/lookupevent.php?id=${idevent}"),
                    matchResponse::class.java)


            uiThread {
                doAsync {
                    val team = gson.fromJson(apiRepository
                            .doRequest("https://www.thesportsdb.com/api/v1/json/1/lookupteam.php?id=${data.events[0].idHomeTeam}"),
                            teamResponse::class.java)

                    val team2 = gson.fromJson(apiRepository
                            .doRequest("https://www.thesportsdb.com/api/v1/json/1/lookupteam.php?id=${data.events[0].idAwayTeam}"),
                            teamResponse::class.java)
                    uiThread {
                        view.showDetail(data, team.teams[0].strTeamBadge.toString(), team2.teams[0].strTeamBadge.toString())
                        view.hideLoading()
                    }
                }
            }
        }
    }
}