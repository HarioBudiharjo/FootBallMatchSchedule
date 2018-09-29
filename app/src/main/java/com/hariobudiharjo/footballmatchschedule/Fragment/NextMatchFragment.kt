package com.hariobudiharjo.footballmatchschedule.Fragment


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
import com.hariobudiharjo.footballmatchschedule.Adapter.RVNextAdapter
import com.hariobudiharjo.footballmatchschedule.Model.matchModel
import com.hariobudiharjo.footballmatchschedule.Model.matchResponse
import com.hariobudiharjo.footballmatchschedule.Network.ApiMatch

import com.hariobudiharjo.footballmatchschedule.R
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class NextMatchFragment : Fragment() {

    private var matchs : MutableList<matchModel> = mutableListOf()
    lateinit var adapter : RVNextAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_next_match, container, false)

        var _recyclerView: RecyclerView = view.findViewById(R.id.rv_next_match)
        _recyclerView.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)

        val request = ApiMatch()
        val gson = Gson()

        var progress = ProgressDialog(context);
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show()

        doAsync {
            val data = gson.fromJson(request
                    .doRequest("https://www.thesportsdb.com/api/v1/json/1/eventsnextleague.php?id=4328"),
                    matchResponse::class.java)

            matchs.clear()
            matchs.addAll(data.events)
            Log.d("DEBUG",matchs.toString())

            uiThread {
                adapter.notifyDataSetChanged()
                progress.dismiss()
            }
        }



//        matchs.add(matchModel("123", "Persija", "Persib", "26 September 2018", "3", "2"))
//        matchs.add(matchModel("123", "Persija", "Persib", "26 September 2018", "3", "2"))
//        matchs.add(matchModel("123", "Persija", "Persib", "26 September 2018", "3", "2"))
//        matchs.add(matchModel("123", "Persija", "Persib", "26 September 2018", "3", "2"))

        adapter = RVNextAdapter(context!!, matchs)
        _recyclerView.adapter = adapter

        return view
    }


}
