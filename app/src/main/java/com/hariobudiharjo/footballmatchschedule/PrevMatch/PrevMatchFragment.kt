package com.hariobudiharjo.footballmatchschedule.PrevMatch


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
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class PrevMatchFragment : Fragment(), PrevMatchView {

    var matchs = ArrayList<matchModel>()
    lateinit var adapter: RVPrevAdapter
    lateinit var progress: ProgressDialog
    lateinit var presenter: PrevMatchPresenter

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

    companion object {
        fun prevMatchInstance() : PrevMatchFragment = PrevMatchFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_prev_match, container, false)

        val _recyclerView: RecyclerView = view.findViewById(R.id.rv_prev_match)
        _recyclerView.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)


        progress = ProgressDialog(context);
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false);
        progress.show()

        val request = ApiMatch()
        val gson = Gson()

        presenter = PrevMatchPresenter(this, request, gson)
        presenter.getPrevMatchDetail()

        adapter = RVPrevAdapter(context!!, matchs)
        _recyclerView.adapter = adapter

        return view
    }


}
