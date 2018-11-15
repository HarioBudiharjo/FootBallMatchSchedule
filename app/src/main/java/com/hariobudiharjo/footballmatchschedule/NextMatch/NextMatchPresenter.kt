package com.hariobudiharjo.footballmatchschedule.NextMatch

import android.util.Log
import com.google.gson.Gson
import com.hariobudiharjo.footballmatchschedule.Model.eventResponse
import com.hariobudiharjo.footballmatchschedule.Model.matchModel
import com.hariobudiharjo.footballmatchschedule.Model.matchResponse
import com.hariobudiharjo.footballmatchschedule.Network.ApiMatch
import com.hariobudiharjo.footballmatchschedule.Util.CoroutineContextProvider
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class NextMatchPresenter(private val view: NextMatchView,
                         private val apiRepository: ApiMatch,
                         private val gson: Gson,
                         private val contextPool: CoroutineContextProvider = CoroutineContextProvider()) {

    private var matchs: MutableList<matchModel> = mutableListOf()

    fun getNextMatchDetail() {
        view.showLoading()
        doAsync {
            val data = gson.fromJson(apiRepository
                    .doRequest("https://www.thesportsdb.com/api/v1/json/1/eventsnextleague.php?id=4328"),
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

    fun getSearchEvent(match: String?) {
        view.showLoading()
        doAsync {
            val data = gson.fromJson(apiRepository
                    .doRequest("https://www.thesportsdb.com/api/v1/json/1/searchevents.php?e=$match"),
                    eventResponse::class.java)
            matchs.clear()
            matchs.addAll(data.event)
            Log.d("DEBUG", matchs.toString())

            uiThread {
                view.showDetail(matchs)
                view.hideLoading()
            }
        }
    }

    fun getLeague(league: String?) {
        view.showLoading()
        doAsync {
            val data = gson.fromJson(apiRepository
                    .doRequest("https://www.thesportsdb.com/api/v1/json/1/searchfilename.php?e=$league"),
                    eventResponse::class.java)
            matchs.clear()
            matchs.addAll(data.event)
            Log.d("DEBUG", matchs.toString())

            uiThread {
                view.showDetail(matchs)
                view.hideLoading()
            }
        }
    }


}