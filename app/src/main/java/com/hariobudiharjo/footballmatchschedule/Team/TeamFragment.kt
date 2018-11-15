package com.hariobudiharjo.footballmatchschedule.Team


import android.app.ProgressDialog
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.MenuItemCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Spinner
import com.google.gson.Gson
import com.hariobudiharjo.footballmatchschedule.Model.teamModel
import com.hariobudiharjo.footballmatchschedule.Network.ApiMatch

import com.hariobudiharjo.footballmatchschedule.R


class TeamFragment : Fragment(), TeamView {
    private lateinit var mToolbar: Toolbar
    private var matchs: MutableList<teamModel> = mutableListOf()
    lateinit var adapter: RVTeamAdapter
    lateinit var progress: ProgressDialog
    lateinit var presenter: TeamPresenter

    private lateinit var leagueName: String

    override fun showLoading() {
        progress.show()
    }

    override fun hideLoading() {
        progress.dismiss()
    }

    override fun showDetail(data: MutableList<teamModel>) {
        matchs.clear()
        matchs.addAll(data)
        adapter.notifyDataSetChanged()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as AppCompatActivity).setSupportActionBar(mToolbar)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_team, container, false)


        mToolbar = view.findViewById(R.id.toolbar_match)
        val spinnerItems = resources.getStringArray(R.array.league_event_array)
        val spinnerAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, spinnerItems)

        val spinner_team = view.findViewById<Spinner>(R.id.spinner_team)
        spinner_team.adapter = spinnerAdapter

        spinner_team.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                leagueName = spinner_team.selectedItem.toString()
                Log.d("DEBUG LeagueName", leagueName)
                if (leagueName == "Select League") {
                    presenter.getTeam()
                } else {
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                        leagueName = leagueName.replace(" ", "%20")
                    }
                    presenter.getLeague(leagueName)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        val _recyclerView: RecyclerView = view.findViewById(R.id.rv_team)
        _recyclerView.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)

        val request = ApiMatch()
        val gson = Gson()

        progress = ProgressDialog(context)
        progress.setTitle("Loading")
        progress.setMessage("Wait while loading...")
        progress.setCancelable(false)

        presenter = TeamPresenter(this, request, gson)
        presenter.getTeam()

        adapter = RVTeamAdapter(context!!, matchs)
        _recyclerView.adapter = adapter

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        menu?.clear()
        inflater?.inflate(R.menu.search_menu, menu)
        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = MenuItemCompat.getActionView(searchItem) as SearchView
//        searchView.backgroundColorResource = R.color.colorWhite
        searchView.setQueryHint("Search Team")
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                presenter.getSearchTeam(query)
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
//                presenter.getSearchTeam(query)
                return true
            }
        })

        super.onCreateOptionsMenu(menu, inflater)
    }

}
