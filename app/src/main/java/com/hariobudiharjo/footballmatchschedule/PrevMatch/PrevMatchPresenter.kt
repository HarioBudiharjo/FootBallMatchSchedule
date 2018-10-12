package com.hariobudiharjo.footballmatchschedule.PrevMatch

import android.util.Log
import com.google.gson.Gson
import com.hariobudiharjo.footballmatchschedule.Model.matchModel
import com.hariobudiharjo.footballmatchschedule.Model.matchResponse
import com.hariobudiharjo.footballmatchschedule.Network.ApiMatch
import com.hariobudiharjo.footballmatchschedule.Util.CoroutineContextProvider
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class PrevMatchPresenter(private val view: PrevMatchView,
                         private val apiRepository: ApiMatch,
                         private val gson: Gson,
                         private val contextPool: CoroutineContextProvider = CoroutineContextProvider()) {

    private var matchs: MutableList<matchModel> = mutableListOf()

    fun getPrevMatchDetail() {
        view.showLoading()
        doAsync {
            val data = gson.fromJson(apiRepository
                    .doRequest("https://www.thesportsdb.com/api/v1/json/1/eventspastleague.php?id=4328"),
                    matchResponse::class.java)
            matchs.clear()
            matchs.addAll(data.events)
            Log.d("DEBUG", matchs.toString())

            uiThread {
                view.showDetail(matchs)
                view.hideLoading()
            }
        }
    }
}