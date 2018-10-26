package com.hariobudiharjo.footballmatchschedule.DetailTeam.PlayerTeam

import android.util.Log
import com.google.gson.Gson
import com.hariobudiharjo.footballmatchschedule.Model.playerModel
import com.hariobudiharjo.footballmatchschedule.Model.playerResponse
import com.hariobudiharjo.footballmatchschedule.Network.ApiMatch
import com.hariobudiharjo.footballmatchschedule.Util.CoroutineContextProvider
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class PlayerTeamPresenter(private val view: PlayerTeamView,
                          private val apiRepository: ApiMatch,
                          private val gson: Gson,
                          private val contextPool: CoroutineContextProvider = CoroutineContextProvider()) {
    private var matchs: MutableList<playerModel> = mutableListOf()

    fun getPlayerList(idTeam: String) {
        view.showLoading()
        doAsync {
            val data = gson.fromJson(apiRepository
                    .doRequest("https://www.thesportsdb.com/api/v1/json/1/lookup_all_players.php?id=$idTeam"),
                    playerResponse::class.java)
            matchs.clear()
            matchs.addAll(data.player)
            Log.d("DEBUG", matchs.toString())

            uiThread {
                view.showDetail(matchs)
                view.hideLoading()
            }
        }
    }

}