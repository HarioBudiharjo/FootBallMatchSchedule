package com.hariobudiharjo.footballmatchschedule.PrevMatch


import android.app.ProgressDialog
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.MenuItemCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Spinner
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

    private lateinit var leagueName: String
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun showLoading() {
        progress.show()
    }

    override fun hideLoading() {
        progress.dismiss()
    }

    override fun showDetail(data: MutableList<matchModel>) {
        matchs.clear()
        matchs.addAll(data)
        adapter.notifyDataSetChanged()
    }

    companion object {
        fun prevMatchInstance(): PrevMatchFragment = PrevMatchFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_prev_match, container, false)

        val _recyclerView: RecyclerView = view.findViewById(R.id.rv_prev_match)
        _recyclerView.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)

        val spinnerItems = resources.getStringArray(R.array.league_event_array)
        val spinnerAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, spinnerItems)

        val spinner_prev = view.findViewById<Spinner>(R.id.spinner_prev)
        spinner_prev.adapter = spinnerAdapter

        spinner_prev.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                leagueName = spinner_prev.selectedItem.toString()
                if (leagueName == "Select League") {
                    presenter.getPrevMatchDetail()
                } else {
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                        leagueName = leagueName.replace(" ", "%20")
                    }
                    presenter.getLeague(leagueName)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

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

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        menu?.clear()
        inflater?.inflate(R.menu.search_menu, menu)
        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = MenuItemCompat.getActionView(searchItem) as SearchView
//        searchView.backgroundColorResource = R.color.colorWhite
        searchView.setQueryHint("Search Match")
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                presenter.getSearchEvent(query)
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
//                presenter.getSearchEvent(query)
                return true
            }
        })

        super.onCreateOptionsMenu(menu, inflater)
    }

}
