package com.hariobudiharjo.footballmatchschedule.NextMatch


import android.app.ProgressDialog
import android.content.Context
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
import kotlinx.android.synthetic.main.fragment_next_match.*
import org.jetbrains.anko.support.v4.ctx

class NextMatchFragment : Fragment(), NextMatchView {

    private var matchs: MutableList<matchModel> = mutableListOf()
    lateinit var adapter: RVNextAdapter
    lateinit var progress: ProgressDialog
    lateinit var presenter: NextMatchPresenter

    private lateinit var leagueName: String

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
    }

    companion object {
        fun nextMatchInstance(): NextMatchFragment = NextMatchFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_next_match, container, false)

        val spinnerItems = resources.getStringArray(R.array.league_event_array)
        val spinnerAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, spinnerItems)

        val spinner_next = view.findViewById<Spinner>(R.id.spinner_next)
        spinner_next.adapter = spinnerAdapter

        spinner_next.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                leagueName = spinner_next.selectedItem.toString()
                if (leagueName == "Select League") {
                    presenter.getNextMatchDetail()
                } else {
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                        leagueName = leagueName.replace(" ", "%20")
                    }
                    presenter.getLeague(leagueName)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

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
