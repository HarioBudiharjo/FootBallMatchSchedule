package com.hariobudiharjo.footballmatchschedule.FavoriteEvent


import android.app.ProgressDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.hariobudiharjo.footballmatchschedule.Database.database
import com.hariobudiharjo.footballmatchschedule.Model.favoriteModel

import com.hariobudiharjo.footballmatchschedule.R
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select

class FavoriteEventFragment : Fragment() {


    private var favorites: MutableList<favoriteModel> = mutableListOf()
    lateinit var adapter: RVFavoriteAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_favorite, container, false)

        val _recyclerView: RecyclerView = view.findViewById(R.id.rv_favorite)
        _recyclerView.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)

        val progress = ProgressDialog(context);
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false);
        progress.show()

        context!!.database.use {
            val result = select(favoriteModel.TABLE_FAVORITE)
            val favorite = result.parseList(classParser<favoriteModel>())
            favorites.addAll(favorite)
        }

        progress.dismiss()

        adapter = RVFavoriteAdapter(context!!, favorites)
        _recyclerView.adapter = adapter




        return view

    }

    companion object {
        fun favoriteMatchInstance() : FavoriteEventFragment = FavoriteEventFragment()
    }

}
