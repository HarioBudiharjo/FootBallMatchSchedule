package com.hariobudiharjo.footballmatchschedule.DetailTeam.PlayerTeam


import android.app.ProgressDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.google.gson.Gson
import com.hariobudiharjo.footballmatchschedule.Model.playerModel
import com.hariobudiharjo.footballmatchschedule.Network.ApiMatch

import com.hariobudiharjo.footballmatchschedule.R

class PlayerTeamFragment : Fragment(), PlayerTeamView {

    private var matchs: MutableList<playerModel> = mutableListOf()
    lateinit var adapter: RVPlayerAdapter
    lateinit var progress: ProgressDialog
    lateinit var presenter: PlayerTeamPresenter
    override fun showLoading() {
        progress.show()
    }

    override fun hideLoading() {
        progress.dismiss()
    }

    override fun showDetail(data: MutableList<playerModel>) {
        matchs.addAll(data)
        adapter.notifyDataSetChanged()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

//        https://www.thesportsdb.com/api/v1/json/1/lookup_all_players.php?id=133604
        val viewku = inflater.inflate(R.layout.fragment_player_team, container, false)


        val _recyclerView: RecyclerView = viewku.findViewById(R.id.rv_player)
        _recyclerView.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)

        val idteam = arguments!!.getString("idteam")
        Log.d("IDTEAM", idteam)

        val request = ApiMatch()
        val gson = Gson()

        progress = ProgressDialog(context)
        progress.setTitle("Loading")
        progress.setMessage("Wait while loading...")
        progress.setCancelable(false)

        presenter = PlayerTeamPresenter(this, request, gson)
        presenter.getPlayerList(idteam)


        adapter = RVPlayerAdapter(context!!, matchs)
        _recyclerView.adapter = adapter

        return viewku
    }


}
