package com.hariobudiharjo.footballmatchschedule.NextMatch


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
import com.hariobudiharjo.footballmatchschedule.Model.matchModel
import com.hariobudiharjo.footballmatchschedule.Model.matchResponse
import com.hariobudiharjo.footballmatchschedule.Network.ApiMatch

import com.hariobudiharjo.footballmatchschedule.R

class NextMatchFragment : Fragment(), NextMatchView {
    override fun showLoading() {
        progress.show()
    }

    override fun hideLoading() {
        progress.dismiss()
    }

    override fun showDetail(data: MutableList<matchModel>) {
        matchs.addAll(data)
        adapter.notifyDataSetChanged()
    }

    private var matchs: MutableList<matchModel> = mutableListOf()
    lateinit var adapter: RVNextAdapter
    lateinit var progress: ProgressDialog
    lateinit var presenter: NextMatchPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_next_match, container, false)

        val _recyclerView: RecyclerView = view.findViewById(R.id.rv_next_match)
        _recyclerView.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)

        val request = ApiMatch()
        val gson = Gson()

        progress = ProgressDialog(context)
        progress.setTitle("Loading")
        progress.setMessage("Wait while loading...")
        progress.setCancelable(false)

        presenter = NextMatchPresenter(this, request, gson)
        presenter.getNextMatchDetail()

        adapter = RVNextAdapter(context!!, matchs)
        _recyclerView.adapter = adapter

        return view
    }


}
