package com.hariobudiharjo.footballmatchschedule.DetailPlayer

import android.util.Log
import com.google.gson.Gson
import com.hariobudiharjo.footballmatchschedule.Model.playerDetailResponse
import com.hariobudiharjo.footballmatchschedule.Model.playerModel
import com.hariobudiharjo.footballmatchschedule.Model.playerResponse
import com.hariobudiharjo.footballmatchschedule.Network.ApiMatch
import com.hariobudiharjo.footballmatchschedule.Util.CoroutineContextProvider
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class DetailPlayerPresenter(private val view: DetailPlayerView,
                            private val apiRepository: ApiMatch,
                            private val gson: Gson,
                            private val contextPool: CoroutineContextProvider = CoroutineContextProvider()) {
    private var matchs: MutableList<playerModel> = mutableListOf()

    fun getPlayerList(idPlayer: String) {

        Log.d("DEBUGDETAIL", "MASUK PAK EKO")
        view.showLoading()
        doAsync {
            val data = gson.fromJson(apiRepository
                    .doRequest("https://www.thesportsdb.com/api/v1/json/1/lookupplayer.php?id=$idPlayer"),
                    playerDetailResponse::class.java)
            matchs.clear()
            matchs.addAll(data.players)
            Log.d("DEBUG", matchs.toString())

            uiThread {
                view.showDetail(matchs)
                view.hideLoading()
            }
        }
    }
}