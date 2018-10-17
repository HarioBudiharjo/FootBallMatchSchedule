package com.hariobudiharjo.footballmatchschedule.Team


import android.app.ProgressDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.google.gson.Gson
import com.hariobudiharjo.footballmatchschedule.Model.teamModel
import com.hariobudiharjo.footballmatchschedule.Network.ApiMatch

import com.hariobudiharjo.footballmatchschedule.R


class TeamFragment : Fragment(), TeamView {
    private var matchs: MutableList<teamModel> = mutableListOf()
    lateinit var adapter: RVTeamAdapter
    lateinit var progress: ProgressDialog
    lateinit var presenter: TeamPresenter
    override fun showLoading() {
        progress.show()
    }

    override fun hideLoading() {
        progress.dismiss()
    }

    override fun showDetail(data: MutableList<teamModel>) {
        matchs.addAll(data)
        adapter.notifyDataSetChanged()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_team, container, false)
        val _recyclerView: RecyclerView = view.findViewById(R.id.rv_team)
        _recyclerView.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)

        val request = ApiMatch()
        val gson = Gson()

        progress = ProgressDialog(context)
        progress.setTitle("Loading")
        progress.setMessage("Wait while loading...")
        progress.setCancelable(false)

        presenter = TeamPresenter(this, request, gson)
        presenter.getNextMatchDetail()

        adapter = RVTeamAdapter(context!!, matchs)
        _recyclerView.adapter = adapter

        return view
    }


}
